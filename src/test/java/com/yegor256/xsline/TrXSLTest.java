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

import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrXSL}.
 *
 * @since 0.6.0
 * @checkstyle AbbreviationAsWordInNameCheck (500 lines)
 */
final class TrXSLTest {

    @Test
    void testPipe() throws IOException {
        final XSL xsl = new XSLDocument(this.getClass().getResource("void.xsl"));
        final Train<Shift> train = new TrXSL<>(new TrDefault<>())
            .with(xsl)
            .back();
        MatcherAssert.assertThat(train, Matchers.iterableWithSize(1));
    }

    @Test
    void shouldThrow() throws IOException {
        final XSL xsl = new XSLDocument(this.getClass().getResource("add-brackets.xsl"));
        Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> new TrXSL<>(new TrDefault<>())
                .with(xsl)
                .iterator()
        );
    }

}
