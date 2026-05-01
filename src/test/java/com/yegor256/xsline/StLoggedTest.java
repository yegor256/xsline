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
 * Test case for {@link StLogged}.
 * @since 0.21.1
 */
final class StLoggedTest {

    @Test
    void logsWhenChangesHappen() {
        MatcherAssert.assertThat(
            "XML must be transformed with added id attribute",
            new Xsline(
                new TrDefault<>(
                    new StLogged(
                        new StClasspath("add-id.xsl"),
                        StLoggedTest.class,
                        Level.FINE
                    )
                )
            ).pass(new XMLDocument("<foo/>")),
            XhtmlMatchers.hasXPaths("/foo[@id]")
        );
    }

    @Test
    void logsWhenNoChangesHappen() {
        MatcherAssert.assertThat(
            "XML must remain unchanged after void transformation",
            new Xsline(
                new TrDefault<>(
                    new StLogged(
                        new StClasspath("void.xsl"),
                        StLoggedTest.class,
                        Level.FINE
                    )
                )
            ).pass(new XMLDocument("<bar/>")),
            XhtmlMatchers.hasXPaths("/bar")
        );
    }
}
