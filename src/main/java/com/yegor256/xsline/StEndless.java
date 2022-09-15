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

/**
 * A {@link Shift} repeated a few times, until changes are happening.
 *
 * <p>Sometimes it's necessary to apply the same XSL a number of times
 * to the same XML document, making repetitive changes until they stop
 * making sense (stop modifying the document). For example, an XSL stylesheet
 * is removing some elements from the XML and adds new elements. The second
 * run of the same XSL will again remove something and add something new,
 * and so on. This class will help you implement this logic:</p>
 *
 * <pre> new StEndless(new StClasspath("test.xsl"))</pre>
 *
 * <p>This class will never stop, if each application of the XSL do make
 * some changes to the XML. That's why, be aware of a risk of a potential
 * totally endless cycle.</p>
 *
 * @since 0.4.0
 */
public final class StEndless extends StEnvelope {

    /**
     * Ctor.
     * @param xsl The XSL document
     */
    public StEndless(final XSL xsl) {
        this(new StXSL(xsl));
    }

    /**
     * Ctor.
     * @param shift The shift
     */
    public StEndless(final Shift shift) {
        super(
            new StLambda(
                shift::uid,
                (position, xml) -> {
                    XML before = xml;
                    XML after;
                    boolean more;
                    do {
                        after = shift.apply(position, before);
                        more = !after.toString().equals(before.toString());
                        before = after;
                    } while (more);
                    return after;
                }
            )
        );
    }

}
