/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.util.logging.Level;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void wrapsRuntimeFailureAsIllegalStateWithCause() {
        final IllegalStateException thrown = Assertions.assertThrows(
            IllegalStateException.class,
            () -> new StLogged(
                new Shift() {
                    @Override
                    public String uid() {
                        return "boom";
                    }

                    @Override
                    public XML apply(final int position, final XML xml) {
                        throw new IllegalArgumentException("downstream failure");
                    }
                }
            ).apply(0, new XMLDocument("<x/>")),
            "StLogged must rewrap downstream RuntimeException as IllegalStateException"
        );
        MatcherAssert.assertThat(
            "Original RuntimeException must be preserved as the cause",
            thrown.getCause(),
            Matchers.instanceOf(IllegalArgumentException.class)
        );
        MatcherAssert.assertThat(
            "Original exception message must be reachable through getCause()",
            thrown.getCause().getMessage(),
            Matchers.equalTo("downstream failure")
        );
    }
}
