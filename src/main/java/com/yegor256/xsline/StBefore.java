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

import com.jcabi.xml.XML;

/**
 * A shift that makes another shift before itself.
 *
 * @since 0.4.0
 */
public final class StBefore implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * The shift before.
     */
    private final Shift before;

    /**
     * Ctor.
     * @param shift Original shift
     * @param prev Previous one
     */
    public StBefore(final Shift shift, final Shift prev) {
        this.origin = shift;
        this.before = prev;
    }

    @Override
    public String uid() {
        return this.origin.uid();
    }

    @Override
    public XML apply(final int position, final XML xml) {
        return this.origin.apply(position, this.before.apply(position, xml));
    }
}
