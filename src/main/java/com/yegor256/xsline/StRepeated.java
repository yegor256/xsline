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

import com.jcabi.xml.XML;
import com.jcabi.xml.XSL;
import java.util.function.Function;

/**
 * A {@link Shift} repeated a few times, until the provided predicate is true.
 *
 * <p>Sometimes you need your XSL transformations to happen a few times
 * in order to make all required changes in the XML document. You don't
 * even know how many times, but you have a "predicate" that can look
 * at the XML document and decide whether one more XSL transformation is
 * required. This {@link Shift} is doing
 * exactly this: it evaluates the encapsulated {@link Shift} and then
 * asks the encapsulated predicate to make a decision whether one more
 * XSL transformation is required.</p>
 *
 * @since 0.1.0
 */
public final class StRepeated extends StEnvelope {

    /**
     * Ctor.
     * @param xsl The XSL
     * @param fun The predicate
     */
    public StRepeated(final XSL xsl, final Function<XML, Boolean> fun) {
        this(new StXSL(xsl), fun);
    }

    /**
     * Ctor.
     * @param shift The shift
     * @param pred The predicate
     */
    public StRepeated(final Shift shift, final Function<XML, Boolean> pred) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> {
                    XML output = xml;
                    boolean more;
                    do {
                        output = shift.apply(position, output);
                        more = pred.apply(output);
                    } while (more);
                    return output;
                }
            )
        );
    }

}
