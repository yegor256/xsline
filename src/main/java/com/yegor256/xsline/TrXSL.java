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
 * Train that accepts XSL.
 *
 * <p>This supplementary class may be convenient when you need to add many
 * XSL stylesheets to the train, which are all instances of {@link XSL}. Instead
 * of doing this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault&lt;&gt;()
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")));</pre>
 *
 * <p>you can do this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault()
 * .with(new XSLDocument("&lt;stylesheet&gt;..."))
 * .with(new XSLDocument("&lt;stylesheet&gt;..."))
 * .back();</pre>
 *
 * <p>The method {@link TrXSL#back()} is required in order to get
 * back to the original type of the train.</p>
 *
 * @param <T> Type of elements inside
 * @since 0.3.0
 * @checkstyle AbbreviationAsWordInNameCheck (10 lines)
 */
public final class TrXSL<T extends Shift> implements Train<XSL>, Train.Temporary<T> {

    /**
     * The original train.
     */
    private final Train<T> origin;

    /**
     * Ctor.
     * @param train Original
     */
    public TrXSL(final Train<T> train) {
        this.origin = train;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TrXSL<T> with(final XSL element) {
        return new TrXSL<>(this.origin.with((T) new StXSL(element)));
    }

    @Override
    public TrXSL<T> empty() {
        return new TrXSL<>(this.origin.empty());
    }

    @Override
    public Iterator<XSL> iterator() {
        throw new UnsupportedOperationException(
            "Don't iterate here, call back() first"
        );
    }

    @Override
    public Train<T> back() {
        return this.origin;
    }

    /**
     * Add shift.
     * @param shift The shift to add
     * @return New train
     */
    @SuppressWarnings("unchecked")
    public TrXSL<T> with(final Shift shift) {
        return new TrXSL<>(this.origin.with((T) shift));
    }

}
