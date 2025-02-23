/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * A {@link Shift} that intentionally throws an exception.
 *
 * This may be useful for testing.
 *
 * @since 0.21.0
 */
public final class StFailure extends StEnvelope {

    /**
     * Ctor.
     * @since 0.21.1
     */
    public StFailure() {
        this("Intentional failure");
    }

    /**
     * Ctor.
     * @param msg The message to throw
     */
    public StFailure(final String msg) {
        this(new IllegalStateException(msg));
    }

    /**
     * Ctor.
     * @param error The error to throw
     */
    public StFailure(final Exception error) {
        super(
            new StLambda(
                StFailure.class.getCanonicalName(),
                (first, second) -> {
                    throw error;
                }
            )
        );
    }
}
