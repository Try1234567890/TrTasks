package com.github.jts.executor;

import com.github.jts.tasks.Task;
import com.github.utilities.validators.Preconditions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Executes tasks synchronously, on <b>the application's main thread</b>, never on the
 * caller's thread.
 * <p>
 * A {@link com.github.jts.timer.Timer} runs its cycle on its own dedicated, blocking
 * thread. Since a synchronous task is by definition blocking, it must never run
 * directly on that cycle thread, or it would stall the whole timer (and, since the
 * cycle iterates every registered task under the same lock, every other task
 * scheduled on that timer too).
 * <p>
 * To solve this, every {@code SyncExecutor} instance shares a single queue: when
 * {@link #execute(Task)} is called from any thread other than the main thread, the
 * task's action is queued and the method returns immediately, leaving the caller
 * (typically the timer's cycle thread) free to continue. The queued action only runs
 * once the main thread calls {@link #pump()}.
 * <p>
 * <b>Integration requirement:</b> the host application must call {@link #pump()}
 * repeatedly from its own main thread (e.g. once per game-loop/render-loop/event-loop
 * iteration), or call {@link #runLoop()} once if it doesn't have a loop of its own.
 * Without this, queued synchronous tasks are never executed.
 * <p>
 * Note on task-completion semantics: because execution on the main thread happens
 * after {@link #execute(Task)} has already returned, {@link com.github.jts.tasks.AbstractTask#run()}
 * marks the task as {@code COMPLETED} (and fires {@code onSuccess}) at the moment the
 * task is handed off, not when it actually finishes running. This mirrors the existing
 * fire-and-forget behavior of {@link com.github.jts.executor.async.AsyncExecutor}.
 * If the caller happens to already be the main thread, the task instead runs truly
 * inline and its exceptions propagate normally.
 */
public class SyncExecutor implements Executor {
    public static final Logger LOGGER = Logger.getLogger(SyncExecutor.class.getName());

    // Shared by EVERY SyncExecutor instance (TaskBuilder creates a new one per task by
    // default): all synchronous tasks, regardless of which task/timer they come from,
    // are funneled onto this single queue and eventually run on the very same thread.
    private static final BlockingQueue<Runnable> PENDING = new LinkedBlockingQueue<>();
    private static volatile Thread mainThread;
    private static volatile boolean looping;

    /**
     * Binds the calling thread as "the main thread of the application".
     * Call this once, as the very first statement inside {@code main(String[] args)}.
     * If you never call this explicitly, the first thread that calls {@link #pump()}
     * is bound automatically.
     */
    public static void bindMainThread() {
        mainThread = Thread.currentThread();
    }

    private static boolean isMainThread() {
        Thread t = mainThread;
        return t != null && t == Thread.currentThread();
    }

    /**
     * Runs every synchronous task currently pending.
     * <p>
     * This MUST be called repeatedly, and ONLY from the main thread of the application
     * (e.g. once per iteration of your game loop / render loop / event loop). This is
     * the piece of cooperation the host application has to provide: the library cannot
     * guess when the main thread is free to do work.
     */
    public static void pump() {
        if (mainThread == null) mainThread = Thread.currentThread(); // auto-bind on first pump
        drainRemaining();
    }

    /**
     * Convenience for applications that don't already have a loop of their own.
     * <p>
     * Unlike a poll-based loop, this does NOT wake up on a fixed interval to check for
     * work: the calling thread is parked (via {@link BlockingQueue#take()}) and stays
     * idle, consuming no CPU, until a synchronous task is actually submitted. Only then
     * does it wake up, run the task (and anything else meanwhile queued), and go back
     * to waiting. So the "block" only happens while there is a task to execute, never
     * as a permanent busy loop.
     * <p>
     * Call this as the LAST statement of {@code main}. Stop it with {@link #stopLoop()}.
     */
    public static void runLoop() {
        bindMainThread();
        looping = true;
        try {
            while (looping) {
                Runnable task = PENDING.take(); // parks here: idle, not busy, until work arrives
                runSafely(task);
                drainRemaining(); // run anything else already queued before waiting again
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stops a running {@link #runLoop()}, waking it up immediately even if it's
     * currently idle-parked waiting for the next task.
     */
    public static void stopLoop() {
        looping = false;
        Thread t = mainThread;
        if (t != null) t.interrupt();
    }

    private static void drainRemaining() {
        Runnable next;
        while ((next = PENDING.poll()) != null) {
            runSafely(next);
        }
    }

    private static void runSafely(Runnable task) {
        task.run(); // the Runnable already catches and logs its own exceptions, see execute()
    }

    /**
     * Execute the {@code task} synchronously.
     * <p>
     * If the caller is already the main thread, the task runs immediately, in place.
     * Otherwise (typically: called from the Timer's own cycle thread) the task's action
     * is handed off to the main thread's queue and this method returns immediately,
     * so the caller (the Timer's cycle) is never blocked by this task's execution.
     *
     * @param task the task to execute
     */
    @Override
    public void execute(Task task) throws Exception {
        Preconditions.simpleParameterNotNull(task, "task");

        if (isMainThread()) {
            task.getAction().execute();
            return;
        }

        // Deferred execution: any exception thrown here happens later, on the main
        // thread, so it can no longer be propagated back to the original caller.
        // We log it instead, the same way AsyncExecutor already does for its
        // fire-and-forget virtual-thread executions.
        PENDING.add(() -> {
            try {
                task.getAction().execute();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An unexpected exception is thrown while executing " + task, e);
            }
        });
    }

    @Override
    public void shutdown() {
        // Nothing per-instance to release: the shared queue lives for the JVM's lifetime
        // and is drained by whichever thread calls pump()/runLoop().
    }
}