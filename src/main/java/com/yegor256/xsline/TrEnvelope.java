/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Iterator;

/**
 * An envelope for {@link Train}.
 *
 * <p>This supplementary class helps making new classes implementing
 * interface {@link Train} without writing too much of code. See how
 * {@link TrLogged} is using this class.</p>
 *
 * @see <a href="https://www.yegor256.com/2017/01/31/decorating-envelopes.html">Blog post about "Decorating Envelopes"</a>
 * @since 0.4.0
 */
public class TrEnvelope implements Train<Shift> {

    /**
     * The original train.
     */
    private final Train<Shift> origin;

    /**
     * Ctor.
     * @param train Original
     */
    public TrEnvelope(final Train<Shift> train) {
        this.origin = train;
    }

    @Override
    public final Train<Shift> with(final Shift element) {
        return this.origin.with(new StLogged(element));
    }

    @Override
    public final Train<Shift> empty() {
        return this.origin.empty();
    }

    @Override
    public final Iterator<Shift> iterator() {
        return this.origin.iterator();
    }
}
