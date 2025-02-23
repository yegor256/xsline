/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * A shift that makes another shift before itself.
 *
 * <p>This decorator may be useful if you need to do the same shift
 * over and over again before your other shifts. You can decorate
 * your {@link Train} with {@link TrBefore}, which will use this
 * shift inside. We don't recommend using this decorator directly.</p>
 *
 * @since 0.4.0
 */
public final class StBefore extends StEnvelope {

    /**
     * Ctor.
     * @param shift Original shift
     * @param prev Previous one
     */
    public StBefore(final Shift shift, final Shift prev) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> shift.apply(position, prev.apply(position, xml))
            )
        );
    }

}
