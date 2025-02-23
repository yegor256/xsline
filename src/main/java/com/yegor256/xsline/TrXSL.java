/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
     *
     * @since 0.18.0
     */
    public TrXSL() {
        this(new TrDefault<>());
    }

    /**
     * Ctor.
     *
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
