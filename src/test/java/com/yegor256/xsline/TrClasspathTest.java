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

import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrXSL}.
 *
 * @since 0.1.0
 */
final class TrClasspathTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new TrClasspath<>()
                .with("add-brackets.xsl")
                .back()
                .with(new StEndless(new StClasspath("void.xsl"))),
            Matchers.iterableWithSize(2)
        );
    }

    @Test
    void withCtor() {
        MatcherAssert.assertThat(
            new TrClasspath<>(
                "add-brackets.xsl",
                "add-id.xsl"
            ).back().with(new StEndless(new StClasspath("void.xsl"))),
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void emitsXslError() {
        MatcherAssert.assertThat(
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Xsline(new TrClasspath<>("emit-error.xsl").back()).pass(
                    new XMLDocument("<x>hello</x>")
                )
            ).getMessage(),
            Matchers.containsString("terminated by xsl:message at line 32 in emit-error.xsl")
        );
    }

    @Test
    void emitsXslErrorWhenDefault() {
        MatcherAssert.assertThat(
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Xsline(
                    new TrClasspath<>(
                        new TrDefault<>(),
                        "add-id.xsl",
                        "emit-error.xsl"
                    ).back()
                ).pass(new XMLDocument("<bar/>"))
            ).getMessage(),
            Matchers.containsString("by xsl:message at line 32 in emit-error.xsl")
        );
    }
}
