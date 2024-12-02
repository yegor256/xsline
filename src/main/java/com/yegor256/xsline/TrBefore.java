/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2024 Yegor Bugayenko
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
 * Train with a pre-processing shift being inserted before each
 * shift you add to the train.
 *
 * @since 0.3.0
 */
public final class TrBefore implements Train<Shift> {

    /**
     * The original train.
     */
    private final Train<Shift> origin;

    /**
     * The shift.
     */
    private final Shift shift;

    /**
     * Ctor.
     * @param fun The shift
     * @since 0.18.0
     */
    public TrBefore(final Shift fun) {
        this(new TrDefault<>(), fun);
    }

    /**
     * Ctor.
     * @param train Original
     * @param fun The shift
     */
    public TrBefore(final Train<Shift> train, final Shift fun) {
        this.origin = train;
        this.shift = fun;
    }

    @Override
    public Train<Shift> with(final Shift element) {
        return new TrBefore(
            this.origin.with(element),
            this.shift
        );
    }

    @Override
    public Train<Shift> empty() {
        return new TrBefore(this.origin.empty(), this.shift);
    }

    @Override
    public Iterator<Shift> iterator() {
        return new Alterator<>(
            this.origin.iterator(),
            next -> new StBefore(next, this.shift)
        );
    }
}
