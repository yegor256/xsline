/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

/**
 * Function that accepts one argument.
 *
 * Same as {@link java.util.function.Function}, but throws
 * an Exception.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.21.0
 */
@FunctionalInterface
public interface FunctionChecked<X, Y> {

    /**
     * Apply it.
     * @param input The argument
     * @return The result
     * @throws Exception If fails
     */
    Y apply(X input) throws Exception;
}
