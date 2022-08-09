/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
     * The original train.
     */
    private final Train<T> origin;

    /**
     * Ctor.
     * @param train Original
     */
    public TrClasspath(final Train<T> train) {
        this.origin = train;
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
            ).with(Arrays.asList(paths[0])).back().back()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public TrClasspath<T> with(final String path) {
        return new TrClasspath<>(this.origin.with((T) new StClasspath(path)));
    }

    @Override
    public TrClasspath<T> empty() {
        return new TrClasspath<>(this.origin.empty());
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }

    @Override
    public Train<T> back() {
        return this.origin;
    }

}
