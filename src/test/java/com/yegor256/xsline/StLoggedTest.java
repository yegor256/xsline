/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StLogged}.
 *
 * @since 0.21.1
 */
final class StLoggedTest {

    @Test
    void logsWhenChangesHappen() {
        new Xsline(
            new TrDefault<>(
                new StLogged(
                    new StClasspath("add-id.xsl"),
                    StLoggedTest.class,
                    Level.FINE
                )
            )
        ).pass(new XMLDocument("<foo/>"));
    }

    @Test
    void logsWhenNoChangesHappen() {
        new Xsline(
            new TrDefault<>(
                new StLogged(
                    new StClasspath("void.xsl"),
                    StLoggedTest.class,
                    Level.FINE
                )
            )
        ).pass(new XMLDocument("<bar/>"));
    }

}
