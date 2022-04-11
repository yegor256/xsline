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

import java.util.Iterator;

/**
 * Train that accepts classpath paths.
 *
 * @since 0.4.0
 */
public final class TrClasspath implements Train<String> {

    /**
     * The original train.
     */
    private final Train<Shift> origin;

    /**
     * Ctor.
     * @param train Original
     */
    public TrClasspath(final Train<Shift> train) {
        this.origin = train;
    }

    @Override
    public TrClasspath with(final String path) {
        return new TrClasspath(this.origin.with(new StClasspath(path)));
    }

    @Override
    public TrClasspath empty() {
        return new TrClasspath(this.origin.empty());
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }

    /**
     * Get back to original train.
     * @return Original train
     */
    public Train<Shift> back() {
        return this.origin;
    }

}
