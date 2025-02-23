/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Arrays;

/**
 * Train that joins existing train and a few shifts.
 *
 * @since 0.8.0
 */
public final class TrWith extends TrEnvelope {

    /**
     * Ctor.
     * @param train Original
     * @param shifts Shifts to add
     */
    public TrWith(final Train<Shift> train, final Shift... shifts) {
        super(new TrBulk<>(train).with(Arrays.asList(shifts)).back());
    }
}
