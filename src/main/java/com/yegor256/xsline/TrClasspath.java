/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Train that accepts classpath paths.
 *
 * <p>This supplementary class may be convenient when you need to add many
 * XSL stylesheets to the train, which are stored in the classpath. Instead
 * of doing this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault()
 * .with(new StClasspath("/foo/first.xsl"))
 * .with(new StClasspath("/foo/first.xsl"));</pre>
 *
 * <p>you can do this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrClasspath&lt;&gt;(new TrDefault())
 * .with("/foo/first.xsl")
 * .with("/foo/first.xsl")
 * .back();</pre>
 *
 * <p>The method {@link TrClasspath#back()} is required in order to get
 * back to the original type of the train.</p>
 *
 * @param <T> Type of elements inside
 * @since 0.4.0
 */
public final class TrClasspath<T extends Shift> implements Train<String>, Train.Temporary<T> {

    /**
     * The mapped train.
     */
    private final TrMapped<String, T> origin;

    /**
     * Ctor.
     * @param train Original
     */
    @SuppressWarnings("unchecked")
    public TrClasspath(final Train<T> train) {
        this.origin = new TrMapped<>(
            train,
            path -> (T) new StClasspath(path)
        );
    }

    /**
     * Ctor.
     * @param train Original
     * @param paths List of paths to add immediately
     * @since 0.6.0
     */
    public TrClasspath(final Train<T> train, final String... paths) {
        this(
            new TrBulk<>(
                new TrClasspath<>(train)
            ).with(Arrays.asList(paths)).back().back()
        );
    }

    /**
     * Ctor with {@link TrDefault}.
     * @param paths List of paths to add immediately
     * @since 0.16.0
     */
    public TrClasspath(final String... paths) {
        this(new TrDefault<>(), paths);
    }

    @Override
    public Train<T> back() {
        return this.origin.back();
    }

    @Override
    public TrClasspath<T> with(final String element) {
        return new TrClasspath<>(this.origin.with(element).back());
    }

    @Override
    public TrClasspath<T> empty() {
        return new TrClasspath<>(this.origin.empty().back());
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }
}
