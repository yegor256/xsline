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
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StEndless}.
 *
 * @since 0.1.0
 */
final class StEndlessTest {

    @Test
    void simpleScenario() throws IOException {
        MatcherAssert.assertThat(
            new StEndless(
                new XSLDocument(
                    this.getClass().getResource("void.xsl")
                )
            ).apply(0, new XMLDocument("<hello/>")),
            XhtmlMatchers.hasXPaths("/hello")
        );
    }

    @Test
    void changesXmlOnce() {
        MatcherAssert.assertThat(
            "We expect a shift is applied twice",
            new StEndless(new Twice()).apply(0, new XMLDocument("<dog/>")),
            XhtmlMatchers.hasXPaths("/dummy")
        );
    }

    /**
     * A dummy shift that does nothing and returns a constant XML.
     * However, it can be applied only twice, and then it throws an exception.
     *
     * @since 0.34
     */
    private static class Twice implements Shift {

        /**
         * How many times are allowed to transform.
         */
        private final AtomicInteger attempts;

        Twice() {
            this.attempts = new AtomicInteger(2);
        }

        @Override
        public String uid() {
            return "twice-dummy";
        }

        @Override
        public XML apply(final int position, final XML xml) {
            if (this.attempts.decrementAndGet() >= 0) {
                return new XMLDocument("<dummy>I just do nothing</dummy>");
            }
            throw new IllegalStateException("This shift was already used, but it shouldn't");
        }
    }

}
