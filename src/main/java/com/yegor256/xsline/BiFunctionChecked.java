/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * Bi-Function that accepts two arguments.
 *
 * Same as {@link java.util.function.BiFunction}, but throws
 * an Exception.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.13.0
 */
@FunctionalInterface
public interface BiFunctionChecked<X, Y, Z> {

    /**
     * Apply it.
     * @param first The first argument
     * @param second The second argument
     * @return The result
     * @throws Exception If fails
     */
    Z apply(X first, Y second) throws Exception;
}
