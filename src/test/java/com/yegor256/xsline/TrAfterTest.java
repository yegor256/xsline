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
import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * Test case for {@link TrAfter}.
 *
 * @since 0.4.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TrAfterTest {

    @Test
    public void simpleScenario() throws IOException {
        final XSL xsl = new XSLDocument(
            this.getClass().getResource("add-brackets.xsl")
        );
        final Train<Shift> train = new TrAfter(
            new TrDefault<>(),
            (position, xml) -> new XMLDocument(
                new Xembler(
                    new Directives().xpath("/*").attr("a", 1).set("boom")
                ).applyQuietly(xml.node())
            )
        ).with(new StXSL(xsl));
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<x>test</x>")),
            XhtmlMatchers.hasXPaths("/x[@a and .='boom']")
        );
    }

}
