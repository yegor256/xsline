/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
