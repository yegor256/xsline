/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
