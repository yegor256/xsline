/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StLambda}.
 * @since 0.13.0
 */
final class StLambdaTest {

    @Test
    void returnsFormattedUid() {
        MatcherAssert.assertThat(
            new StLambda((integer, xml) -> xml).uid().startsWith("λ-"),
            Matchers.is(true)
        );
    }

    @Test
    void returnsUidFromCtor() {
        final String uuid = UUID.randomUUID().toString();
        MatcherAssert.assertThat(
            "The UID must be the same as specified in constructor",
            new StLambda(uuid, (integer, xml) -> xml).uid(),
            Matchers.is(uuid)
        );
    }

    @Test
    void throwsExceptionOnMissingClasspath() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StLambda(
                (pos, xml) -> new StClasspath("not-found").apply(pos, xml)
            ).apply(0, new XMLDocument("<x>test</x>"))
        );
    }

    @Test
    void throwsExceptionOnIoError() {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new StLambda(
                xml -> {
                    final BufferedReader inp = new BufferedReader(new StringReader("test"));
                    inp.close();
                    inp.readLine();
                    return xml;
                }
            ).apply(0, new XMLDocument("<x>test</x>"))
        );
    }
}
