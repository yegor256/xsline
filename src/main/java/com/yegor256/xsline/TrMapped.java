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

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Train that accepts elements of any type and turns them into
 * elements of type {@link Shift}.
 *
 * @param <I> Type of input elements, for example a {@link String}
 * @param <T> Type of elements inside, usually {@link Shift}
 * @since 0.12.0
 */
public final class TrMapped<I, T extends Shift> implements Train<I>, Train.Temporary<T> {

    /**
     * The original train.
     */
    private final Train<T> origin;

    /**
     * The mapping function.
     */
    private final Function<I, T> func;

    /**
     * Ctor.
     * @param train Original
     * @param fun Mapping function
     */
    public TrMapped(final Train<T> train, final Function<I, T> fun) {
        this.origin = train;
        this.func = fun;
    }

    /**
     * Ctor.
     * @param train Original
     * @param fun Mapping function
     * @param items List of items to add immediately
     */
    @SafeVarargs
    public TrMapped(final Train<T> train, final Function<I, T> fun,
        final I... items) {
        this(
            new TrBulk<>(
                new TrMapped<>(train, fun)
            ).with(Arrays.asList(items)).back().back(),
            fun
        );
    }

    /**
     * Ctor.
     * @param fun Mapping function
     * @param items List of items to add immediately
     */
    @SafeVarargs
    public TrMapped(final Function<I, T> fun, final I... items) {
        this(new TrDefault<>(), fun, items);
    }

    @Override
    public TrMapped<I, T> with(final I item) {
        return new TrMapped<>(
            this.origin.with(this.func.apply(item)), this.func
        );
    }

    @Override
    public TrMapped<I, T> empty() {
        return new TrMapped<>(this.origin.empty(), this.func);
    }

    @Override
    public Iterator<I> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }

    @Override
    public Train<T> back() {
        return this.origin;
    }

}
