/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrLogged}.
 *
 * @since 0.21.1
 */
final class TrLoggedTest {

    @Test
    void logsWhenChangesHappen() {
        new Xsline(
            new TrLogged(
                new TrDefault<>(
                    new StClasspath("add-id.xsl")
                ),
                StLoggedTest.class,
                Level.FINE
            )
        ).pass(new XMLDocument("<foo/>"));
    }

    @Test
    void logsWhenNoChangesHappen() {
        new Xsline(
            new TrLogged(
                new TrDefault<>(
                    new StClasspath("void.xsl")
                ),
                StLoggedTest.class,
                Level.FINE
            )
        ).pass(new XMLDocument("<bar/>"));
    }

}
