/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2025 Yegor Bugayenko
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

import com.jcabi.log.Logger;
import com.jcabi.xml.XML;

/**
 * A {@link Shift} that logs a warning if XSL transformation takes too long.
 *
 * <p>This {@link Shift} may help you during debugging, when
 * the train is slow, but you are not sure which of you XSL transformations
 * are the slowest ones. Decorate the train with {@link TrFast} and you will
 * see warnings in the log, provided your "slf4j" bridge
 * is configured right.</p>
 *
 * @since 0.12.0
 */
public final class StFast implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * Threshold in milliseconds.
     */
    private final long threshold;

    /**
     * Logging target.
     */
    private final Object target;

    /**
     * Ctor.
     * @param shift The shift
     */
    public StFast(final Shift shift) {
        this(shift, StFast.class);
    }

    /**
     * Ctor.
     * @param shift The shift
     * @param tgt The target to log against
     */
    public StFast(final Shift shift, final Object tgt) {
        this(shift, tgt, 100L);
    }

    /**
     * Ctor.
     * @param shift The shift
     * @param tgt The target to log against
     * @param msec Threshold in msec
     */
    public StFast(final Shift shift, final Object tgt, final long msec) {
        this.origin = shift;
        this.threshold = msec;
        this.target = tgt;
    }

    @Override
    public String uid() {
        return this.origin.uid();
    }

    @Override
    public XML apply(final int position, final XML xml) {
        final long start = System.currentTimeMillis();
        final XML out = this.origin.apply(position, xml);
        final long msec = System.currentTimeMillis() - start;
        if (msec > this.threshold) {
            Logger.warn(
                this.target,
                "XSL '%s' took %[ms]s (over %[ms]s)",
                this.uid(), msec,
                this.threshold
            );
        }
        return out;
    }
}
