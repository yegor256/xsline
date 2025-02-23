/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * A shift that makes another shifts after itself.
 *
 * <p>This decorator may be useful if you need to do the same shifts
 * over and over again after your other shifts. You can decorate
 * your {@link Train} with {@link TrAfter}, which will use this
 * shift inside. We don't recommend using this decorator directly.</p>
 *
 * @since 0.4.0
 */
public final class StAfter extends StEnvelope {

    /**
     * Ctor.
     * @param shift Original shift
     * @param after Collection of Shifts to be applied after original Shift
     */
    public StAfter(final Shift shift, final Shift... after) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> {
                    xml = shift.apply(position, xml);
                    for (final Shift next : after) {
                        xml = next.apply(position, xml);
                    }
                    return xml;
                }
            )
        );
    }

}
