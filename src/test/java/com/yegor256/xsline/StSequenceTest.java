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

/**
 * Test case for {@link StSequence}.
 *
 * @since 0.14.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class StSequenceTest {

    @Test
    void shouldStopPipeline() {
        MatcherAssert.assertThat(
            new Xsline(
                new StSequence(
                    xml -> xml.nodes("/x[text()='{{{hello}}}']").isEmpty(),
                    new TrClasspath<>()
                        .with("add-brackets.xsl")
                        .with("add-brackets.xsl")
                        .with("add-brackets.xsl")
                        .with("add-brackets.xsl")
                        .with("add-brackets.xsl")
                        .back()
                )
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[text()='{{{hello}}}']")
        );
    }

    @Test
    void processesTrainAsShift() {
        MatcherAssert.assertThat(
            new Xsline(
                new StSequence(
                    new TrWith(
                        new TrDefault<>(),
                        new StClasspath("add-brackets.xsl")
                    )
                )
            ).pass(new XMLDocument("<x>hello</x>")),
            XhtmlMatchers.hasXPaths("/x[.='{hello}']")
        );
    }

}
