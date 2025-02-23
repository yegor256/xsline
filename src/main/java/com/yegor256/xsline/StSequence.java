/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;

/**
 * A {@link Shift} that apply another shifts,
 * while provided predicate is true.
 *
 * <p>Sometimes you need to apply some collection of {@link Shift}-s
 * as long as a certain condition is true.
 * This wrapper will help you out.</p>
 *
 * @since 0.14.0
 */
public final class StSequence extends StEnvelope {

    /**
     * Ctor.
     * @param train The train
     */
    public StSequence(final Iterable<Shift> train) {
        this(
            xml -> true,
            train
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param train The train
     */
    public StSequence(final String uid, final Iterable<Shift> train) {
        this(
            uid,
            xml -> true,
            train
        );
    }

    /**
     * Ctor.
     * @param shifts Shifts to apply
     */
    public StSequence(final Shift... shifts) {
        this(
            xml -> true,
            shifts
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param shifts Shifts to apply
     */
    public StSequence(final String uid, final Shift... shifts) {
        this(
            uid,
            xml -> true,
            shifts
        );
    }

    /**
     * Ctor.
     * @param fun The predicate
     * @param shifts Shifts to apply
     */
    public StSequence(final FunctionChecked<XML, Boolean> fun,
        final Shift... shifts) {
        this(
            fun,
            new TrBulk<>(
                new TrDefault<>(),
                shifts
            ).back()
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param fun The predicate
     * @param shifts Shifts to apply
     */
    public StSequence(final String uid,
        final FunctionChecked<XML, Boolean> fun,
        final Shift... shifts) {
        this(
            uid,
            fun,
            new TrBulk<>(
                new TrDefault<>(),
                shifts
            ).back()
        );
    }

    /**
     * Ctor.
     * @param fun The predicate
     * @param train The train
     */
    public StSequence(final FunctionChecked<XML, Boolean> fun,
        final Iterable<Shift> train) {
        super(
            new StLambda(
                StSequence.apply(fun, train)
            )
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param fun The predicate
     * @param train The train
     */
    public StSequence(final String uid,
        final FunctionChecked<XML, Boolean> fun,
        final Iterable<Shift> train) {
        super(
            new StLambda(
                uid,
                StSequence.apply(fun, train)
            )
        );
    }

    /**
     * Applies {@link Shift}-s, while provided predicate is true.
     * @param fun The predicate
     * @param train The train
     * @return BiFunction that sets behavior for {@link StLambda}
     */
    private static BiFunctionChecked<Integer, XML, XML> apply(
        final FunctionChecked<XML, Boolean> fun,
        final Iterable<Shift> train) {
        return (position, xml) -> {
            int pos = 0;
            for (final Shift shift : train) {
                if (!fun.apply(xml)) {
                    break;
                }
                xml = shift.apply(pos, xml);
                ++pos;
            }
            return xml;
        };
    }

}
