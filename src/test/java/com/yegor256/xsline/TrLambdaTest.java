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
 * Test case for {@link TrLambda}.
 *
 * @since 0.4.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TrLambdaTest {

    /**
     * The shift to be used in tests.
     */
    private static final Shift SHIFT = new StClasspath("add-id.xsl");

    @Test
    public void simpleScenario() {
        final Train<Shift> train = new TrLambda(
            new TrDefault<>(),
            StLogged::new
        ).with(TrLambdaTest.SHIFT);
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<x>foo</x>")),
            XhtmlMatchers.hasXPaths("/x[@id]")
        );
    }

    @Test
    public void withStLambda() {
        final Train<Shift> train = new TrLambda(
            new TrDefault<>(),
            shift -> new StLambda(
                shift::uid,
                (pos, xml) -> TrLambdaTest.SHIFT.apply(0, xml)
            )
        ).with(TrLambdaTest.SHIFT);
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<a>test</a>")),
            XhtmlMatchers.hasXPaths("/a[@id]")
        );
    }

}
