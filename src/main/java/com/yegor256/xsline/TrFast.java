/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * Train that wraps all shifts in {@link StFast}.
 *
 * <p>This decorator may help you during debugging, when
 * the train is slow, but you are not sure which of you XSL transformations
 * are the slowest ones. Decorate your train and you will
 * see warnings in the log, provided your "slf4j" bridge
 * is configured right.</p>
 *
 * <p>This decorator uses {@link StFast} under the hood.</p>
 *
 * @since 0.12.0
 */
public final class TrFast extends TrEnvelope {

    /**
     * Ctor.
     * @since 0.18.0
     */
    public TrFast() {
        this(new TrDefault<>());
    }

    /**
     * Ctor.
     * @param train Original
     */
    public TrFast(final Train<Shift> train) {
        this(train, TrFast.class);
    }

    /**
     * Ctor.
     * @param train Original
     * @param target The target
     */
    public TrFast(final Train<Shift> train, final Object target) {
        super(
            new TrLambda(
                train,
                x -> new StFast(x, target)
            )
        );
    }

    /**
     * Ctor.
     * @param train Original
     * @param target The target
     * @param msec Threshold in milliseconds
     */
    public TrFast(final Train<Shift> train, final Object target, final long msec) {
        super(
            new TrLambda(
                train,
                x -> new StFast(x, target, msec)
            )
        );
    }
}
