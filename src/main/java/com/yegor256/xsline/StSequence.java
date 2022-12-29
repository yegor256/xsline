/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
