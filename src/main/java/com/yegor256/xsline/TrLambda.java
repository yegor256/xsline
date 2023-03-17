/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2023 Yegor Bugayenko
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

import java.util.Iterator;

/**
 * Train with a lambda expression and catching exceptions.
 *
 * <p>It's a decorator of an existing {@link Train}. The decorator makes
 * sure that all shifts will be passed through the provided lambda function
 * before their usage. Basically, it uses {@link StLambda} with the
 * provided lambda function and all shifts in the train.</p>
 *
 * @since 0.1.0
 */
public final class TrLambda implements Train<Shift> {

    /**
     * The original train.
     */
    private final Train<Shift> origin;

    /**
     * The function.
     */
    private final FunctionChecked<Shift, Shift> lambda;

    /**
     * Ctor.
     * @param fun The function
     * @since 0.18.0
     */
    public TrLambda(final FunctionChecked<Shift, Shift> fun) {
        this(new TrDefault<>(), fun);
    }

    /**
     * Ctor.
     * @param train Original
     * @param fun The function
     */
    public TrLambda(final Train<Shift> train, final FunctionChecked<Shift, Shift> fun) {
        this.origin = train;
        this.lambda = fun;
    }

    @Override
    public Train<Shift> with(final Shift element) {
        return new TrLambda(this.origin.with(element), this.lambda);
    }

    @Override
    public Train<Shift> empty() {
        return new TrLambda(this.origin.empty(), this.lambda);
    }

    @Override
    public Iterator<Shift> iterator() {
        return new Alterator<>(
            this.origin.iterator(),
            this.lambda
        );
    }
}
