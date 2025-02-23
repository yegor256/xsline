/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Iterator;

/**
 * Iterator that modifies its items on retrieval.
 *
 * @param <T> Type of items
 * @since 0.4.0
 * @checkstyle IllegalCatchCheck (500 lines)
 */
final class Alterator<T> implements Iterator<T> {

    /**
     * The original iterator.
     */
    private final Iterator<T> origin;

    /**
     * The function.
     */
    private final FunctionChecked<T, T> lambda;

    /**
     * Ctor.
     * @param iterator Original
     * @param fun The function
     */
    Alterator(final Iterator<T> iterator, final FunctionChecked<T, T> fun) {
        this.origin = iterator;
        this.lambda = fun;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    @SuppressWarnings({"PMD.AvoidRethrowingException", "PMD.AvoidCatchingGenericException"})
    public T next() {
        try {
            return this.lambda.apply(this.origin.next());
        } catch (final RuntimeException ex) {
            throw ex;
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
