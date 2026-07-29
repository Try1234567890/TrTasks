package com.github.jts.tasks.imlps.conditional;

import com.github.jts.tasks.Task;

import java.util.function.BooleanSupplier;

public interface ConditionalTask extends Task {

    BooleanSupplier condition();

}
