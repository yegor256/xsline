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
import java.io.BufferedReader;
import java.io.StringReader;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrLambda}.
 *
 * @since 0.4.0
 */
final class TrLambdaTest {

    /**
     * The shift to be used in tests.
     */
    private static final Shift ADD_ID = new StClasspath("add-id.xsl");

    /**
     * The shift to be used in tests.
     */
    private static final Shift ADD_BRACKETS = new StClasspath("add-brackets.xsl");

    /**
     * The shift to be used in tests.
     */
    private static final Shift VOID = new StClasspath("void.xsl");

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new Xsline(
                new TrWith(
                    new TrLambda(
                        new TrDefault<>(),
                        StLogged::new
                    ),
                    TrLambdaTest.ADD_ID
                )
            ).pass(new XMLDocument("<x>foo</x>")),
            XhtmlMatchers.hasXPaths("/x[@id]")
        );
    }

    @Test
    void withStLambda() {
        final Train<Shift> train = new TrLambda(
            new TrDefault<>(),
            shift -> new StLambda(
                shift::uid,
                (pos, xml) -> TrLambdaTest.ADD_ID.apply(0, xml)
            )
        ).with(TrLambdaTest.VOID);
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<a>test</a>")),
            XhtmlMatchers.hasXPaths("/a[@id]")
        );
    }

    @Test
    void withListOfPostProcessing() {
        final Train<Shift> train = new TrLambda(
            shift -> new StAfter(
                shift,
                new StLambda(
                    shift::uid,
                    (pos, xml) -> TrLambdaTest.ADD_ID.apply(0, xml)
                ),
                new StLambda(
                    shift::uid,
                    (pos, xml) -> TrLambdaTest.ADD_BRACKETS.apply(1, xml)
                )
            )
        ).with(TrLambdaTest.VOID);
        MatcherAssert.assertThat(
            new Xsline(train).pass(new XMLDocument("<a>test</a>")),
            XhtmlMatchers.hasXPaths(
                "/a[@id]",
                "/a[text()=\"{test}\"]"
            )
        );
    }

    @Test
    void shouldReturnEmptyTrain() {
        MatcherAssert.assertThat(
            new TrLambda(
                shift -> new StLambda(
                    shift::uid,
                    (pos, xml) -> TrLambdaTest.ADD_ID.apply(0, xml)
                )
            ).with(TrLambdaTest.ADD_ID).empty(),
            Matchers.iterableWithSize(0)
        );
    }

    @Test
    void shouldThrowsExceptions() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Xsline(
                new TrLambda(
                    shift -> new StLambda(
                        (pos, xml) -> new StClasspath("not-found").apply(pos, xml)
                    )
                ).with(TrLambdaTest.VOID)
            ).pass(new XMLDocument("<a>test</a>"))
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new Xsline(
                new TrLambda(
                    shift -> {
                        final BufferedReader inp = new BufferedReader(new StringReader("test"));
                        inp.close();
                        inp.readLine();
                        return shift;
                    }
                ).with(TrLambdaTest.VOID)
            ).pass(new XMLDocument("<a>test</a>"))
        );
    }

}
