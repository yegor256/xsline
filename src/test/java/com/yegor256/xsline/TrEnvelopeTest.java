/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrEnvelope}.
 *
 * @since 1.0
 */
final class TrEnvelopeTest {

    @Test
    void addsShiftWithoutDecoratingIt() {
        final Shift shift = new StClasspath("void.xsl");
        Assertions.assertSame(
            shift,
            new TrEnvelope(new TrDefault<>()).with(shift).iterator().next(),
            "An envelope must delegate without adding a logging decorator"
        );
    }

    @Test
    void fastTrainPreservesShiftFailure() {
        final IllegalStateException failure = new IllegalStateException("broken shift");
        Assertions.assertSame(
            failure,
            Assertions.assertThrows(
                IllegalStateException.class,
                () -> new Xsline(
                    new TrFast().with(
                        new StLambda(
                            "failure",
                            xml -> {
                                throw failure;
                            }
                        )
                    )
                ).pass(new XMLDocument("<x/>")),
                "A fast train must propagate the original failure"
            ),
            "Timing a shift must not wrap its failure in a logging exception"
        );
    }
}
