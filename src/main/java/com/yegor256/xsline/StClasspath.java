/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.ClasspathSources;
import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import java.net.URL;

/**
 * Simple shift from a path in classpath.
 *
 * <p>Let's say, you have <tt>test.xsl</tt> stylesheet in classpath at the
 * location <tt>/foo/alpha/test.xsl</tt>. You can make an {@link Shift} out
 * of it loading the resource into a stream or an {@link URL} and then
 * {@code new StXSL(new XSLDocument(url))}, or simply this way:</p>
 *
 * <pre> new StClasspath("/foo/alpha/test.xsl")</pre>
 *
 * <p>The path provided into the constructor must always be absolute,
 * i.e. must start with a slash.</p>
 *
 * <p>The XSL created will be classpath-aware, meaning that it will be
 * possible to use external resources from inside of it, if they are
 * located in classpath. More details about it you can find in the
 * documentation of {@link ClasspathSources} class.</p>
 *
 * @since 0.4.0
 * @checkstyle AbbreviationAsWordInNameCheck (3 lines)
 */
public final class StClasspath extends StEnvelope {

    /**
     * Ctor.
     * @param path Path in classpath
     * @param args Arguments to send to the XSL separated with by a space,
     *  e.g. {@code "arg1 hello world!"} means argument {@code arg1} with the
     *  {@code "hello world!"} value.
     * @since 0.16.0
     */
    public StClasspath(final String path, final String... args) {
        super(new StXSL(StClasspath.make(path, args)));
    }

    /**
     * Make XSL safely.
     * @param path Path in classpath
     * @param args Arguments to send to the XSL
     * @return XSL
     */
    private static XSL make(final String path, final String... args) {
        final URL url = StClasspath.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Path '%s' not found in classpath", path
                )
            );
        }
        XSL xsl;
        try {
            xsl = new XSLDocument(url, path);
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Failed to read '%s' from classpath", path),
                ex
            );
        }
        xsl = xsl.with(new ClasspathSources());
        for (final String arg : args) {
            final String[] parts = arg.split(" ", 2);
            xsl = xsl.with(parts[0], parts[1]);
        }
        return xsl;
    }

}
