/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StEndless}.
 *
 * @since 0.1.0
 */
final class StEndlessTest {

    @Test
    void simpleScenario() throws IOException {
        MatcherAssert.assertThat(
            new StEndless(
                new XSLDocument(
                    this.getClass().getResource("void.xsl")
                )
            ).apply(0, new XMLDocument("<hello/>")),
            XhtmlMatchers.hasXPaths("/hello")
        );
    }

    @Test
    void changesXmlOnce() {
        MatcherAssert.assertThat(
            "We expect a shift is used twice",
            new StEndless(new Dummy(2)).apply(0, new XMLDocument("<dog/>")),
            XhtmlMatchers.hasXPaths("/dummy")
        );
    }

    @Test
    void understandsDifferenceBetweenDocumentAndFirstNode() {
        MatcherAssert.assertThat(
            "We expect a shift is applied twice",
            new StEndless(new Dummy(2)).apply(
                0,
                new XMLDocument(
                    new XMLDocument("<dummy>I just do nothing</dummy>").inner().getFirstChild()
                )
            ),
            XhtmlMatchers.hasXPaths("/dummy")
        );
    }

    @Test
    void changesLargeXmlDocs() {
        final String initial = StEndlessTest.largeXml("initial");
        final String updated = StEndlessTest.largeXml("updated");
        MatcherAssert.assertThat(
            "We expect large XMLs are transformed fast",
            new StEndless(new Dummy(2, updated)).apply(0, new XMLDocument(initial)),
            XhtmlMatchers.hasXPaths("/updated")
        );
    }

    /**
     * Generate large XML.
     *
     * @param root Root element.
     * @return Large XML.
     */
    private static String largeXml(final String root) {
        final int capacity = 10_000;
        final StringBuilder xml = new StringBuilder(capacity);
        xml.append('<').append(root).append('>');
        for (int idx = 0; idx < capacity; ++idx) {
            xml.append("<item>").append(idx).append("</item>");
        }
        xml.append("</").append(root).append('>');
        return xml.toString();
    }

    /**
     * A dummy shift that does nothing and returns a constant XML.
     * However, it can be applied only twice, and then it throws an exception.
     *
     * @since 0.34
     */
    private static class Dummy implements Shift {

        /**
         * How many times are allowed to transform.
         */
        private final AtomicInteger attempts;

        /**
         * XML to return.
         */
        private final String xml;

        /**
         * Ctor.
         *
         * @param attempts How many times are allowed to transform.
         */
        Dummy(final int attempts) {
            this(attempts, "<dummy>I just do nothing</dummy>");
        }

        /**
         * Ctor.
         *
         * @param attempts How many times are allowed to transform.
         * @param xml XML to return.
         */
        Dummy(final int attempts, final String xml) {
            this(new AtomicInteger(attempts), xml);
        }

        /**
         * Ctor.
         *
         * @param attempts How many times are allowed to transform.
         * @param xml XML to return.
         */
        private Dummy(final AtomicInteger attempts, final String xml) {
            this.attempts = attempts;
            this.xml = xml;
        }

        @Override
        public String uid() {
            return "twice-dummy";
        }

        @Override
        public XML apply(final int position, final XML node) {
            if (this.attempts.decrementAndGet() >= 0) {
                return new XMLDocument(this.xml);
            }
            throw new IllegalStateException("This shift was already used, but it shouldn't");
        }
    }

}
