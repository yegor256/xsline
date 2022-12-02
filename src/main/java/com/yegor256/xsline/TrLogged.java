/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
