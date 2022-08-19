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
 * Train that accepts collections instead of individual elements.
 *
 * <p>This class may be useful when you need to add many elements
 * to a train and don't want to do this with a sequence of calls to
 * {@link Train#with(Object)}. The following code will add many
 * XSL shifts from classpath to a train:</p>
 *
 * <pre> Train&lt;String&gt; empty = new TrClasspath&lt;&gt;(new TrDefault&lt;&gt;());
 * Train&lt;Shift&gt; full = new TrBulk&lt;&gt;(empty).with(
 *   Arrays.asList(
 *     "/foo/test/first.xsl",
 *     "/foo/test/second.xsl",
 *   )
 * ).back();</pre>
 *
 * <p>Here, the method {@link Train.Temporary#back()} is used to get the
 * train which was extended with shifts by {@link TrBulk}. In the example
 * above, {@code full} train will be a {@link Train} of {@link Shift}
 * objects.</p>
 *
 * @param <T> Type of elements inside
 * @param <R> Type of returning train
 * @since 0.3.0
 * @checkstyle AbbreviationAsWordInNameCheck (10 lines)
 */
public final class TrBulk<T, R extends Train<T>> implements Train<Iterable<T>>, Train.Temporary<T> {

    /**
     * The original train.
     */
    private final R origin;

    /**
     * Ctor.
     * @param train The train to start with
     */
    public TrBulk(final R train) {
        this.origin = train;
    }

    /**
     * Ctor.
     * @param train The train to start with
     * @param bulk List of elements to add immediately
     * @since 0.6.0
     */
    public TrBulk(final R train, final Iterable<T> bulk) {
        this(new TrBulk<>(train).with(bulk).back());
    }

    /**
     * Ctor.
     * @param train The train to start with
     * @param bulk List of elements to add immediately
     * @since 0.10.0
     */
    @SafeVarargs
    public TrBulk(final R train, final T... bulk) {
        this(train, Arrays.asList(bulk));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TrBulk<T, R> with(final Iterable<T> bulk) {
        R after = this.origin;
        for (final T shift : bulk) {
            after = (R) after.with(shift);
        }
        return new TrBulk<>(after);
    }

    @Override
    @SuppressWarnings("unchecked")
    public TrBulk<T, R> empty() {
        return new TrBulk<>((R) this.origin.empty());
    }

    @Override
    public Iterator<Iterable<T>> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }

    @Override
    public R back() {
        return this.origin;
    }

}
