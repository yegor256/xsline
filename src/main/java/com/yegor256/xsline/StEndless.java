/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;
import com.jcabi.xml.XSL;

/**
 * A {@link Shift} repeated a few times, until changes are happening.
 *
 * <p>Sometimes it's necessary to apply the same XSL a number of times
 * to the same XML document, making repetitive changes until they stop
 * making sense (stop modifying the document). For example, an XSL stylesheet
 * is removing some elements from the XML and adds new elements. The second
 * run of the same XSL will again remove something and add something new,
 * and so on. This class will help you implement this logic:</p>
 *
 * <pre> new StEndless(new StClasspath("test.xsl"))</pre>
 *
 * <p>This class will never stop, if each application of the XSL do make
 * some changes to the XML. That's why, be aware of a risk of a potential
 * totally endless cycle.</p>
 *
 * @since 0.4.0
 */
public final class StEndless extends StEnvelope {

    /**
     * Ctor.
     * @param xsl The XSL document
     */
    public StEndless(final XSL xsl) {
        this(new StXSL(xsl));
    }

    /**
     * Ctor.
     * @param shift The shift
     */
    public StEndless(final Shift shift) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> {
                    XML before = xml;
                    XML after;
                    boolean more;
                    do {
                        after = shift.apply(position, before);
                        more = !after.inner().isEqualNode(before.inner());
                        before = after;
                    } while (more);
                    return after;
                }
            )
        );
    }

}
