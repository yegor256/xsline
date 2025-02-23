/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSL;

/**
 * Simple {@link Shift} through a single XSL.
 *
 * @since 0.1.0
 * @checkstyle AbbreviationAsWordInNameCheck (3 lines)
 */
public final class StXSL extends StEnvelope {

    /**
     * Ctor.
     * @param xsl The XSL
     */
    public StXSL(final XSL xsl) {
        super(
            new StLambda(
                () -> new XMLDocument(xsl.toString()).xpath("/*/@id").get(0),
                (integer, xml) -> xsl.transform(xml)
            )
        );
    }

}
