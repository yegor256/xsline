/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StFailure}.
 *
 * @since 0.21.0
 */
final class StFailureTest {

    @Test
    void throwsException() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Xsline(
                new TrDefault<>(
                    new StClasspath("add-id.xsl"),
                    new StFailure(new IllegalArgumentException("boom"))
                )
            ).pass(new XMLDocument("<foo/>"))
        );
    }

}
