/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2025 Yegor Bugayenko
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
