/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Train that consequently joins a number of trains.
 *
 * The class is immutable, you can't add any more Shifts to it.
 *
 * When constructed, an object of this class doesn't touch the encapsulated
 * Trains. Only when you call {@link #iterator()}, all Trains are checked,
 * their Shifts are retrieved, a new collection is built and its iterator
 * is returned.
 *
 * @param <T> Type of elements
 * @since 0.16.0
 */
public final class TrJoined<T> implements Train<T> {

    /**
     * The chain of trains.
     */
    private final Iterable<Train<T>> chain;

    /**
     * Ctor.
     * @param trains Chain of trains
     */
    @SafeVarargs
    public TrJoined(final Train<T>... trains) {
        this(Arrays.asList(trains));
    }

    /**
     * Ctor.
     * @param trains Chain of trains
     */
    public TrJoined(final Iterable<Train<T>> trains) {
        this.chain = trains;
    }

    @Override
    public Train<T> with(final T shift) {
        final List<Train<T>> trains = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            trains.add(train);
        }
        if (trains.isEmpty()) {
            trains.add(new TrDefault<T>().with(shift));
        } else {
            final Train<T> last = trains.get(trains.size() - 1);
            trains.remove(trains.size() - 1);
            trains.add(last.with(shift));
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Train<T> empty() {
        final List<Train<T>> trains = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            trains.add(train.empty());
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Iterator<T> iterator() {
        final Collection<T> shifts = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            for (final T shift : train) {
                shifts.add(shift);
            }
        }
        return shifts.iterator();
    }
}
