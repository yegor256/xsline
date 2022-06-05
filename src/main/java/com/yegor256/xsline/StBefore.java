/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Yegor Bugayenko
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

/**
 * A shift that makes another shift before itself.
 *
 * <p>This decorator may be useful if you need to do the same shift
 * over and over again before your other shifts. You can decorate
 * your {@link Train} with {@link TrBefore}, which will use this
 * shift inside. We don't recommend using this decorator directly.</p>
 *
 * @since 0.4.0
 */
public final class StBefore extends StEnvelope {

    /**
     * Ctor.
     * @param shift Original shift
     * @param prev Previous one
     */
    public StBefore(final Shift shift, final Shift prev) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> shift.apply(position, prev.apply(position, xml))
            )
        );
    }

}
