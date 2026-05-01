/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * A {@link Shift} made of {@link Train}.
 *
 * <p>This class is no longer supported.
 * Use {@link StSequence} instead.</p>
 *
 * @since 0.9.0
 * @deprecated Use {@link StSequence} instead
 */
@Deprecated
public final class StOfTrain extends StEnvelope {

    /**
     * Ctor.
     * @param train The train
     */
    public StOfTrain(final Train<Shift> train) {
        // @checkstyle ConstructorsCodeFreeCheck (3 lines)
        super(
            new StSequence(train)
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param train The train
     */
    // @checkstyle ConstructorsOrderCheck (5 lines)
    public StOfTrain(final String uid, final Train<Shift> train) {
        // @checkstyle ConstructorsCodeFreeCheck (3 lines)
        super(
            new StSequence(uid, train)
        );
    }
}
