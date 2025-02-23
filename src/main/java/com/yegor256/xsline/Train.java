/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * An immutable extendable vector of shifts (or maybe not only them).
 *
 * <p>You are not supposed to implement this interface
 * (even though you can), but instead use already existing
 * instances of it. For example, you can
 * use {@link TrDefault} to build a train of XSL shifts:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault&lt;&gt;()
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")));</pre>
 *
 * @param <T> Type of element
 * @since 0.1.0
 */
public interface Train<T> extends Iterable<T> {

    /**
     * Add new element and return a new train.
     *
     * @param element New element
     * @return New train
     */
    Train<T> with(T element);

    /**
     * Return an empty train, with no elements inside.
     *
     * <p>The returned train will have all the properties of the original
     * one, but won't have any elements inside. This may be useful, when
     * a train is already configured, but the elements inside must be
     * replaced with new ones. Instead of creating of a new train, just
     * make an empty one using this method.</p>
     *
     * @return New train, an empty one
     */
    Train<T> empty();

    /**
     * Temporary train.
     *
     * @param <X> Type of elements inside
     * @since 0.4.0
     */
    interface Temporary<X> {
        /**
         * Return the original one.
         *
         * @return Original train
         */
        Train<X> back();
    }

}
