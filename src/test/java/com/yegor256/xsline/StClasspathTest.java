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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StClasspath}.
 *
 * @since 0.6.0
 */
final class StClasspathTest {

    @Test
    void shouldThrowIfResourceIsAbsent() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StClasspath("not-found")
        );
    }

    @Test
    void addsParam() {
        MatcherAssert.assertThat(
            new StClasspath("add-param.xsl", "param hello, world!").apply(
                0, new XMLDocument("<x>hello</x>")
            ),
            XhtmlMatchers.hasXPaths("/x[@param='hello, world!']")
        );
    }

    @Test
    void emitsXslError() {
        MatcherAssert.assertThat(
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new StClasspath("emit-error.xsl").apply(
                    0, new XMLDocument("<x>hello</x>")
                )
            ).getMessage(),
            Matchers.containsString("terminated by xsl:message at line 31 in emit-error.xsl")
        );
    }
}
