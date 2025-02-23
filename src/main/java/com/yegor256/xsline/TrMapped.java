/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
     * @param fun Mapping function
     * @since 0.18.0
     */
    public TrMapped(final Function<I, T> fun) {
        this(new TrDefault<>(), fun);
    }

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
