/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * Test case for {@link TrAfter}.
 *
 * @since 0.4.0
 */
final class TrAfterTest {

    @Test
    void simpleScenario() {
        final Train<Shift> train = new TrAfter(
            new StLambda(
                (position, xml) -> new XMLDocument(
                    new Xembler(
                        new Directives().xpath("/*").attr("a", 1).set("boom")
                    ).applyQuietly(xml.inner())
                )
            )
        ).with(new StClasspath("add-id.xsl")).with(new StClasspath("add-brackets.xsl"));
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<x>test</x>")),
            XhtmlMatchers.hasXPaths(
                "/x[@a and .='boom']",
                "/x[@id]"
            )
        );
    }

}
