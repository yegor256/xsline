/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;
import com.jcabi.xml.XSL;
import java.util.function.Function;

/**
 * A {@link Shift} repeated a few times, until the provided predicate is true.
 *
 * <p>Sometimes you need your {@link XSL} transformations to happen a few times
 * in order to make all required changes in the XML document. You don't
 * even know how many times, but you have a "predicate" that can look
 * at the {@link XML} document and decide whether one more XSL transformation is
 * required. This {@link Shift} is doing
 * exactly this: it evaluates the encapsulated {@link Shift} and then
 * asks the encapsulated predicate to make a decision whether one more
 * XSL transformation is required.</p>
 *
 * @since 0.1.0
 */
public final class StRepeated extends StEnvelope {

    /**
     * Ctor.
     * @param xsl The XSL
     * @param fun The predicate
     */
    public StRepeated(final XSL xsl, final Function<XML, Boolean> fun) {
        this(new StXSL(xsl), fun);
    }

    /**
     * Ctor.
     * @param shift The shift
     * @param pred The predicate
     */
    public StRepeated(final Shift shift, final Function<XML, Boolean> pred) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> {
                    XML output = xml;
                    boolean more;
                    do {
                        output = shift.apply(position, output);
                        more = pred.apply(output);
                    } while (more);
                    return output;
                }
            )
        );
    }

}
