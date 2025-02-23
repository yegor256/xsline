/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;

/**
 * A single shift from XML to XML.
 *
 * <p>You are not supposed to instantiate this interface directly, but
 * of course you can. Instead, it is recommended to use already existing
 * instances, such as {@link StClasspath} and {@link StXSL}.</p>
 *
 * @since 0.1.0
 */
public interface Shift {

    /**
     * Unique (as much as it's possible) ID of the shift.
     *
     * <p>Usually, it's the {@code id} attribute of the <tt>stylesheet</tt>
     * highest level XML element in the XSL stylesheet.</p>
     *
     * @return Unique name of the shift
     */
    String uid();

    /**
     * Apply it to an XML and get a new one back.
     *
     * @param position The position of this shift run in the pipeline line
     * @param xml The XML document
     * @return New XML after the modifications applied
     */
    XML apply(int position, XML xml);

}
