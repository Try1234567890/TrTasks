package com.github.jts.tasks;

/**
 * Stato del ciclo di vita di una {@link Task}.
 * Per le task ricorrenti (TimeTask, ConditionTask con repeat=true) lo stato
 * riflette sempre l'esito dell'ULTIMA esecuzione: dopo un ciclo completato
 * con successo, alla run successiva si ripassa da RUNNING.
 */
public enum TaskState {

    /**
     * Creata ma non ancora registrata/schedulata.
     */
    PENDING(false),

    /**
     * Registrata nello Scheduler e affidata al TimerService.
     */
    SCHEDULED(true),

    /**
     * In esecuzione in questo momento.
     */
    RUNNING(true),

    /**
     * Ultima esecuzione completata con successo.
     */
    COMPLETED(true),

    /**
     * Ultima esecuzione terminata con un'eccezione.
     */
    FAILED(false),

    /**
     * Cancellata esplicitamente, non verrà più eseguita.
     */
    CANCELLED(false),

    /**
     * Cancellata esplicitamente, non verrà più eseguita fino a
     * quando non viene riattivata.
     */
    PAUSED(false);


    final boolean isScheduled;


    TaskState(boolean isScheduled) {
        this.isScheduled = isScheduled;
    }
}
