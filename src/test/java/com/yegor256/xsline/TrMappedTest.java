/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import java.util.function.Function;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrMapped}.
 *
 * @since 0.16.0
 */
final class TrMappedTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new Xsline(
                new TrMapped<>(
                    (Function<String, Shift>) StClasspath::new
                ).with("add-brackets.xsl").back()
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{hello}']")
        );
    }
}
