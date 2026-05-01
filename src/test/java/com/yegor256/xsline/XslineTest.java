/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Xsline}.
 * @since 0.1.0
 */
final class XslineTest {

    @Test
    void simpleScenario() throws IOException {
        MatcherAssert.assertThat(
            "Xsline transforms XML through repeated shifts",
            new Xsline(
                new TrLogged().with(
                    new StEndless(
                        new XSLDocument(
                            this.getClass().getResource("void.xsl")
                        )
                    )
                ).with(
                    new StRepeated(
                        new XSLDocument(
                            this.getClass().getResource("add-brackets.xsl")
                        ),
                        xml -> xml.nodes("/x[starts-with(., '{{')]").isEmpty()
                    )
                )
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{{hello}}']")
        );
    }
}
