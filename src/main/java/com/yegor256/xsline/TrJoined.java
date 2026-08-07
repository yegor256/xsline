/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Train that consequently joins a number of trains.
 *
 * <p>The class is immutable, you can't add any more Shifts to it.
 *
 * <p>When constructed, an object of this class doesn't touch the encapsulated
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
        final List<Train<T>> trains = new ArrayList<>(0);
        for (final Train<T> train : this.chain) {
            trains.add(train);
        }
        if (trains.isEmpty()) {
            trains.add(new TrDefault<T>().with(shift));
        } else {
            trains.add(trains.remove(trains.size() - 1).with(shift));
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Train<T> empty() {
        final List<Train<T>> trains = new ArrayList<>(0);
        for (final Train<T> train : this.chain) {
            trains.add(train.empty());
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Iterator<T> iterator() {
        final Collection<T> shifts = new ArrayList<>(0);
        for (final Train<T> train : this.chain) {
            for (final T shift : train) {
                shifts.add(shift);
            }
        }
        return shifts.iterator();
    }
}
