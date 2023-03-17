/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2023 Yegor Bugayenko
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
import java.util.logging.Level;

/**
 * A shift that logs the process through Slf4j.
 *
 * <p>The decorator logs all transformations with {@code DEBUG} logging
 * level. It also prints the entire content of the produced XML to the log.
 * This may be pretty verbose, be careful when using this class.</p>
 *
 * <p>The decorator catches all children of {@link RuntimeException},
 * logs them, and then re-throws as instances of
 * {@link IllegalArgumentException}.</p>
 *
 * @since 0.1.0
 */
public final class StLogged implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * Logging target.
     */
    private final Object target;

    /**
     * Logging level.
     */
    private final Level level;

    /**
     * Ctor.
     *
     * <p>The logging target is this class itself.
     * The logging level is {@code INFO} from {@link Level} enum.</p>
     *
     * @param shift The shift
     */
    public StLogged(final Shift shift) {
        this(shift, StLogged.class);
    }

    /**
     * Ctor.
     *
     * <p>The logging level is {@code INFO} from {@link Level} enum.</p>
     *
     * @param shift The shift
     * @param tgt The target to log against
     * @since 0.7.0
     */
    public StLogged(final Shift shift, final Object tgt) {
        this(shift, tgt, Level.INFO);
    }

    /**
     * Ctor.
     * @param shift The shift
     * @param tgt The target to log against
     * @param lvl The logging level
     * @since 0.19.0
     */
    public StLogged(final Shift shift, final Object tgt, final Level lvl) {
        this.origin = shift;
        this.target = tgt;
        this.level = lvl;
    }

    @Override
    public String uid() {
        return this.origin.uid();
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public XML apply(final int position, final XML xml) {
        final XML out;
        try {
            if (Logger.isEnabled(this.level, this.target)) {
                final String before = xml.toString();
                out = this.origin.apply(position, xml);
                final String after = out.toString();
                if (before.equals(after)) {
                    Logger.log(
                        this.level,
                        this.target,
                        "Shift #%d via '%s' made no changes",
                        position, this.uid()
                    );
                } else {
                    Logger.log(
                        this.level,
                        this.target,
                        "Shift #%d via '%s' produced (%d->%d chars):\n%s<EOF>",
                        position,
                        this.uid(),
                        before.length(),
                        after.length(),
                        after
                            .replace("\n", "\\n\n")
                            .replace("\t", "\\t\t")
                            .replace("\r", "\\r\r")
                    );
                }
            } else {
                out = this.origin.apply(position, xml);
            }
        // @checkstyle IllegalCatchCheck (1 line)
        } catch (final RuntimeException ex) {
            Logger.error(this.target, "The error happened here:%n%s", xml);
            throw new IllegalArgumentException(
                String.format("Shift '%s' failed", this.origin),
                ex
            );
        }
        return out;
    }
}
