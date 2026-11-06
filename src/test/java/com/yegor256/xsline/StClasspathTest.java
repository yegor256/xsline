/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test case for {@link StClasspath}.
 *
 * @since 0.6.0
 */
final class StClasspathTest {

    @Test
    void throwsIfResourceIsAbsent() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StClasspath("not-found")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"debug", ""})
    void rejectsArgumentsWithoutValues(final String arg) {
        MatcherAssert.assertThat(
            "Malformed parameters must identify the argument and its required format",
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new StClasspath("add-param.xsl", arg),
                "Malformed parameters must report an invalid argument"
            ).getMessage(),
            Matchers.allOf(
                Matchers.containsString(String.format("Argument '%s'", arg)),
                Matchers.containsString("name value")
            )
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
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StClasspath("emit-error.xsl").apply(
                0, new XMLDocument("<x>hello</x>")
            )
        );
    }
}
