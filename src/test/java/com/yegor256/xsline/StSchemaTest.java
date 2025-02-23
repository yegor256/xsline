/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StSchema}.
 *
 * @since 0.10.0
 */
final class StSchemaTest {

    @Test
    void validatesAgainstSchema() {
        MatcherAssert.assertThat(
            new Xsline(
                new StSchema("/com/yegor256/xsline/simple.xsd")
            ).pass(new XMLDocument("<foo>42</foo>")),
            XhtmlMatchers.hasXPaths("/foo")
        );
    }

    @Test
    void shouldThrow() {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new Xsline(
                new StSchema("/com/yegor256/xsline/simple.xsd")
            ).pass(new XMLDocument("<bar/>"))
        );
    }

    @Test
    void validatesWithoutSchema() {
        final Path xsd = Paths.get("src/test/resources/com/yegor256/xsline/simple.xsd");
        Assumptions.assumeTrue(xsd.toFile().exists());
        MatcherAssert.assertThat(
            new Xsline(new StSchema()).pass(
                new XMLDocument(
                    String.join(
                        "",
                        "<foo xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' ",
                        String.format(
                            "xsi:noNamespaceSchemaLocation='file:///%s'>",
                            xsd.toFile().getAbsoluteFile().toString().replace("\\", "/")
                        ),
                        "42</foo>"
                    )
                )
            ),
            XhtmlMatchers.hasXPaths("/foo")
        );
    }

}
