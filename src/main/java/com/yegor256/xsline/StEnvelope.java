/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;

/**
 * An envelope for {@link Shift}.
 *
 * <p>This supplementary class helps making new classes implementing
 * interface {@link Shift} without writing too much of code. See how
 * {@link StBefore}, {@link StClasspath}, and {@link StXSL} are using
 * this class.</p>
 *
 * @see <a href="https://www.yegor256.com/2017/01/31/decorating-envelopes.html">Blog post about "Decorating Envelopes"</a>
 * @since 0.4.0
 */
public class StEnvelope implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * Ctor.
     * @param shift The shift
     */
    public StEnvelope(final Shift shift) {
        this.origin = shift;
    }

    @Override
    public final String uid() {
        return this.origin.uid();
    }

    @Override
    public final XML apply(final int position, final XML xml) {
        return this.origin.apply(position, xml);
    }
}
