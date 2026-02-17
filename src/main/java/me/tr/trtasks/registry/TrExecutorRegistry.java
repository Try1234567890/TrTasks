package me.tr.trtasks.registry;

import me.tr.trtasks.executor.TrExecutor;
import me.tr.trtasks.executor.TrExecutorEntry;
import me.tr.trtasks.executor.async.TrAsyncExecutor;
import me.tr.trtasks.executor.sync.TrSyncExecutor;
import me.tr.trtasks.tasks.TrTask;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TrExecutorRegistry extends TrRegistry<TrExecutorEntry, TrExecutor> {
    private static TrExecutorRegistry instance;
    private final Map<TrExecutorEntry, TrExecutor> registry = new ConcurrentHashMap<>();

    private TrExecutorRegistry() {
    }

    public static TrExecutorRegistry getInstance() {
        if (instance == null) {
            instance = new TrExecutorRegistry();
            instance.getRegistry()
                    .putAll(
                            Map.ofEntries(
                                    Map.entry(TrAsyncExecutor.ENTRY, new TrAsyncExecutor()),
                                    Map.entry(TrSyncExecutor.ENTRY, new TrSyncExecutor())
                            ));
        }
        return instance;
    }

    public static Optional<TrExecutor> getExecutorFor(TrTask task) {
        for (Map.Entry<TrExecutorEntry, TrExecutor> entry :
                getInstance().getRegistry().entrySet()) {
            if (entry.getKey().isExecutorFor(task)) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    protected Map<TrExecutorEntry, TrExecutor> getRegistry() {
        return registry;
    }
}
