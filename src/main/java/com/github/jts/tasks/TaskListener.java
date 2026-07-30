package com.github.jts.tasks;

public interface TaskListener {

    default void onSchedule(Task task) {

    }

    default void onPause(Task task) {

    }

    default void onStart(Task task) {

    }

    default void onSuccess(Task task) {

    }

    default void onFinish(Task task) {

    }

    default void onResume(Task task) {

    }

    default void onFailure(Task task, Exception e) {

    }

    default void onCancel(Task task) {

    }
}
