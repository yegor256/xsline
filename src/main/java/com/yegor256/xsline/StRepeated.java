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
import java.util.function.Function;

/**
 * A shift repeated a few times.
 *
 * @since 0.1.0
 */
public final class StRepeated implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * The predicate (repeats if it's TRUE).
     */
    private final Function<XML, Boolean> predicate;

    /**
     * Ctor.
     * @param shift The shift
     * @param fun The predicate
     */
    public StRepeated(final Shift shift, final Function<XML, Boolean> fun) {
        this.origin = shift;
        this.predicate = fun;
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }

    @Override
    public XML apply(final int position, final XML xml) {
        XML before = xml;
        XML after;
        boolean more;
        do {
            after = this.origin.apply(position, before);
            more = this.predicate.apply(after);
            before = after;
        } while (more);
        return after;
    }
}
