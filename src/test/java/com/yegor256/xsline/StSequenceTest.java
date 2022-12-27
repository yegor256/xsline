/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
