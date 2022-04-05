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

import com.jcabi.log.Logger;
import com.jcabi.xml.XML;

/**
 * A shift that logs the process through Slf4j.
 *
 * @since 0.1.0
 */
public final class StLogged implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * Ctor.
     * @param shift The shift
     */
    public StLogged(final Shift shift) {
        this.origin = shift;
    }

    @Override
    public String uid() {
        return this.origin.uid();
    }

    @Override
    public XML apply(final int position, final XML xml) {
        try {
            final String before = xml.toString();
            final XML out = this.origin.apply(position, xml);
            final String after = out.toString();
            if (before.equals(after)) {
                Logger.debug(
                    this,
                    "Shift #%d via '%s' made no changes",
                    position, this.uid()
                );
            } else {
                Logger.debug(
                    this,
                    "Shift #%d via '%s' produced:\n%s<EOF>",
                    position,
                    this.uid(),
                    after
                        .replace("\n", "\\n\n")
                        .replace("\t", "\\t\t")
                        .replace("\r", "\\r\r")
                );
            }
            return out;
        } catch (final IllegalArgumentException ex) {
            Logger.error(this, "The error happened here:%n%s", xml);
            throw new IllegalArgumentException(
                String.format("Shift '%s' failed", this.origin),
                ex
            );
        }
    }
}
