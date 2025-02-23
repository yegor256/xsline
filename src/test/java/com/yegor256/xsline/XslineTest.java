/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Xsline}.
 *
 * @since 0.1.0
 */
final class XslineTest {

    @Test
    void simpleScenario() throws IOException {
        final XSL xsl = new XSLDocument(
            this.getClass().getResource("add-brackets.xsl")
        );
        final Train<Shift> train = new TrLogged()
            .with(
                new StEndless(
                    new XSLDocument(
                        this.getClass().getResource("void.xsl")
                    )
                )
            )
            .with(
                new StRepeated(
                    xsl,
                    xml -> xml.nodes("/x[starts-with(., '{{')]").isEmpty()
                )
            );
        final XML output = new Xsline(train).pass(
            new XMLDocument("<x>hello</x>")
        );
        MatcherAssert.assertThat(
            output,
            XhtmlMatchers.hasXPaths("/x[.='{{hello}}']")
        );
    }

}
