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

import com.jcabi.log.Logger;
import com.jcabi.xml.XML;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Chain of XSL transformations.
 *
 * <p>Use it like this:
 *
 * <pre> XML output = new Xsline()
 *   .with(StXSL(new XSLDocument("...")));
 *   .with(StXSL(new XSLDocument("...")));
 *   .pass(input);
 * </pre>
 *
 * <p>See all implementations of {@link Shift} to learn the functionality
 * this package provides.</p>
 *
 * @since 0.1.0
 */
public final class Xsline {

    /**
     * Collection of shifts.
     */
    private final Collection<Shift> shifts;

    /**
     * Ctor.
     */
    public Xsline() {
        this(new ArrayList<>(0));
    }

    /**
     * Ctor.
     * @param list List of shifts
     */
    public Xsline(final Collection<Shift> list) {
        this.shifts = Collections.unmodifiableCollection(list);
    }

    /**
     * With this new shift.
     * @param shift The shift
     * @return New instance of this class
     */
    public Xsline with(final Shift shift) {
        final Collection<Shift> list = new ArrayList<>(this.shifts.size() + 1);
        list.addAll(this.shifts);
        list.add(shift);
        return new Xsline(list);
    }

    /**
     * Run it all with the given XML.
     * @param input The input XML
     * @return The output XML
     */
    @SuppressWarnings("PMD.GuardLogStatement")
    public XML pass(final XML input) {
        final long start = System.currentTimeMillis();
        XML before = input;
        XML after = before;
        int pos = 0;
        for (final Shift shift : this.shifts) {
            after = shift.apply(pos, before);
            ++pos;
            before = after;
        }
        Logger.debug(
            this, "Transformed XML through %d shifts in %[ms]s",
            this.shifts.size(),
            System.currentTimeMillis() - start
        );
        return after;
    }

}
