/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrJoined}.
 *
 * @since 0.16.0
 */
final class TrJoinedTest {

    @Test
    void iteratesOverTrains() {
        MatcherAssert.assertThat(
            new Xsline(
                new TrJoined<>(
                    new TrClasspath<>("void.xsl").back(),
                    new TrClasspath<>("add-brackets.xsl").back()
                )
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{hello}']")
        );
    }

    @Test
    void appendsToTheEnd() {
        MatcherAssert.assertThat(
            new Xsline(
                new TrJoined<>(new TrClasspath<>("void.xsl").back()).with(
                    new StClasspath("add-brackets.xsl")
                )
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{hello}']")
        );
    }

    @Test
    void cleansTheChain() {
        MatcherAssert.assertThat(
            new Xsline(
                new TrJoined<>(new TrClasspath<>("void.xsl").back()).empty().with(
                    new StClasspath("add-brackets.xsl")
                )
            ).pass(new XMLDocument("<x>boom</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{boom}']")
        );
    }

}
