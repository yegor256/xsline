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
 * Test case for {@link StAfter}.
 *
 * @since 0.1.0
 */
final class StAfterTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new StAfter(
                new StClasspath("add-brackets.xsl"),
                new StClasspath("void.xsl"),
                new StClasspath("add-id.xsl")
            ).apply(0, new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths(
                "/x[text()=\"{hello}\"]",
                "/x[@id]"
            )
        );
    }

}
