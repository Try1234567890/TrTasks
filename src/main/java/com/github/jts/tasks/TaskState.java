package com.github.jts.tasks;

import com.github.jts.tasks.imlps.ConditionalTask;
import com.github.jts.tasks.imlps.IntervalTask;

/**
 * This enumerator contains all possible states that a {@link Task} can be in during its lifecycle.
 * <p>
 * For {@code iterative tasks} ({@link IntervalTask}, {@link ConditionalTask}, etc...),
 * the task state always reflects the result of the last execution.
 * The {@code iterative tasks} are all the tasks that can be executed more than 1 time.
 * <p>
 * Some TaskState are final states, meaning that the task cannot transition to another state from them.
 * For example, a task in the {@link #CANCELLED} state is not recoverable, when a task is canceled cannot be re-started.
 */
public enum TaskState {

    /**
     * The task has been created but not scheduled yet.
     */
    PENDING(),

    /**
     * The task has been scheduled for execution but not executed yet.
     */
    SCHEDULED(),

    /**
     * The task is currently executing (at the moment).
     */
    RUNNING(),

    /**
     * The task finishes the last execution successfully.
     */
    COMPLETED(),

    /**
     * The task finishes the last execution with an error.
     */
    FAILED(),

    /**
     * The task has been canceled. This state is not recoverable.
     */
    CANCELLED(),

    /**
     * The task has been paused. This state is recoverable.
     */
    PAUSED()

}
