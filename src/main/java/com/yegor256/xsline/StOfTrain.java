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
 * A {@link Shift} made of {@link Train}.
 *
 * <p>Sometimes you need to use a {@link Train} inside another {@link Train},
 * which is expecting a {@link Shift}. This wrapper will help you out.</p>
 *
 * @since 0.9.0
 */
public final class StOfTrain extends StEnvelope {

    /**
     * Ctor.
     * @param train The train
     */
    public StOfTrain(final Train<Shift> train) {
        super(
            new StLambda(
                (position, xml) -> new Xsline(train).pass(xml)
            )
        );
    }

    /**
     * Ctor.
     * @param uid The UID to use
     * @param train The train
     */
    public StOfTrain(final String uid, final Train<Shift> train) {
        super(
            new StLambda(
                uid,
                (position, xml) -> new Xsline(train).pass(xml)
            )
        );
    }

}
