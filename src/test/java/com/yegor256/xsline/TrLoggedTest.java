/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import java.util.logging.Level;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrLogged}.
 * @since 0.21.1
 */
final class TrLoggedTest {

    @Test
    void logsWhenChangesHappen() {
        MatcherAssert.assertThat(
            "Logged train transforms XML with added id attribute",
            new Xsline(
                new TrLogged(
                    new TrDefault<>(
                        new StClasspath("add-id.xsl")
                    ),
                    TrLoggedTest.class,
                    Level.FINE
                )
            ).pass(new XMLDocument("<foo/>")),
            XhtmlMatchers.hasXPaths("/foo[@id]")
        );
    }

    @Test
    void logsWhenNoChangesHappen() {
        MatcherAssert.assertThat(
            "Logged train keeps XML unchanged after void transformation",
            new Xsline(
                new TrLogged(
                    new TrDefault<>(
                        new StClasspath("void.xsl")
                    ),
                    TrLoggedTest.class,
                    Level.FINE
                )
            ).pass(new XMLDocument("<bar/>")),
            XhtmlMatchers.hasXPaths("/bar")
        );
    }
}
