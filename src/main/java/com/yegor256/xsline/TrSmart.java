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

import com.jcabi.xml.XSL;
import java.util.Iterator;

/**
 * Train that accepts many types.
 *
 * @since 0.3.0
 */
public final class TrSmart implements Train<Shift> {

    /**
     * The original train.
     */
    private final Train<Shift> origin;

    /**
     * Ctor.
     * @param train Original
     */
    public TrSmart(final Train<Shift> train) {
        this.origin = train;
    }

    @Override
    public Train<Shift> with(final Shift element) {
        return this.origin.with(element);
    }

    @Override
    public Train<Shift> empty() {
        return this.origin.empty();
    }

    @Override
    public Iterator<Shift> iterator() {
        return this.origin.iterator();
    }

    /**
     * Add this shift.
     * @param shift New shift
     * @return New smart train
     */
    public TrSmart add(final Shift shift) {
        return new TrSmart(
            this.origin.with(shift)
        );
    }

    /**
     * With this XSL.
     * @param xsl New XSL
     * @return New train
     */
    public TrSmart add(final XSL xsl) {
        return new TrSmart(
            this.origin.with(new StXSL(xsl))
        );
    }

    /**
     * With all these XSL.
     * @param list New XSLs
     * @return New train
     */
    public TrSmart addAll(final Iterable<Shift> list) {
        Train<Shift> after = this.origin;
        for (final Shift shift : list) {
            after = after.with(shift);
        }
        return new TrSmart(after);
    }

}
