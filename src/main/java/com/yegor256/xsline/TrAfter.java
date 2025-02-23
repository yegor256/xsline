/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Iterator;

/**
 * Train with a post-processing shifts inserted automatically
 * after each shift, which you add to it.
 *
 * @since 0.3.0
 */
public final class TrAfter implements Train<Shift> {

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
    public TrAfter(final Shift fun) {
        this(new TrDefault<>(), fun);
    }

    /**
     * Ctor.
     * @param train Original
     * @param fun The shift
     */
    public TrAfter(final Train<Shift> train, final Shift fun) {
        this.origin = train;
        this.shift = fun;
    }

    @Override
    public Train<Shift> with(final Shift element) {
        return new TrAfter(
            this.origin.with(element),
            this.shift
        );
    }

    @Override
    public Train<Shift> empty() {
        return new TrAfter(this.origin.empty(), this.shift);
    }

    @Override
    public Iterator<Shift> iterator() {
        return new Alterator<>(
            this.origin.iterator(),
            next -> new StAfter(next, this.shift)
        );
    }
}
