/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Default train.
 *
 * <p>This is the default implementation of {@link Train}. You are supposed
 * to use it almost always, for example like this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault&lt;&gt;()
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")));</pre>
 *
 * @param <T> Type of element
 * @since 0.1.0
 */
public final class TrDefault<T> implements Train<T> {

    /**
     * The list of elements.
     */
    private final Iterable<T> list;

    /**
     * Ctor.
     */
    public TrDefault() {
        this(new ArrayList<>(0));
    }

    /**
     * Ctor.
     * @param items Items
     */
    @SafeVarargs
    public TrDefault(final T... items) {
        this(Arrays.asList(items));
    }

    /**
     * Ctor.
     * @param items Items
     */
    public TrDefault(final Iterable<T> items) {
        this.list = items;
    }

    @Override
    public Train<T> with(final T element) {
        final Collection<T> items = new LinkedList<>();
        for (final T item : this.list) {
            items.add(item);
        }
        items.add(element);
        return new TrDefault<>(items);
    }

    @Override
    public Train<T> empty() {
        return new TrDefault<>();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }
}
