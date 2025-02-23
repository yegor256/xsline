/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.logging.Level;

/**
 * Train that logs all {@link Shift}s.
 *
 * <p>It is a decorator of an existing {@link Train}. The decorator makes
 * sure that all shifts in the train will be decorated with {@link StLogged},
 * which will log all successful and failed calls to SLF4J.</p>
 *
 * <p>You may change the logging level, by using the ctor with three
 * parameters, where the last one is an instance of {@link Level} from
 * {@code java.util.logging} JDK package.</p>
 *
 * @since 0.1.0
 */
public final class TrLogged extends TrEnvelope {

    /**
     * Ctor.
     * @since 0.18.0
     */
    public TrLogged() {
        this(new TrDefault<>());
    }

    /**
     * Ctor.
     *
     * <p>The logging target is this class itself.
     * The logging level is {@code INFO} from {@link Level} enum.</p>
     *
     * @param train Original
     */
    public TrLogged(final Train<Shift> train) {
        this(train, TrLogged.class);
    }

    /**
     * Ctor.
     *
     * <p>The logging level is {@code INFO} from {@link Level} enum.</p>
     *
     * @param train Original
     * @param target The target
     * @since 0.7.0
     */
    public TrLogged(final Train<Shift> train, final Object target) {
        this(train, target, Level.INFO);
    }

    /**
     * Ctor.
     * @param train Original
     * @param target The target
     * @param level Logging level
     * @since 0.19.0
     */
    public TrLogged(final Train<Shift> train, final Object target,
        final Level level) {
        super(
            new TrLambda(
                train,
                x -> new StLogged(x, target, level)
            )
        );
    }

}
