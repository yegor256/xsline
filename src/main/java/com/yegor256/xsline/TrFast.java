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
