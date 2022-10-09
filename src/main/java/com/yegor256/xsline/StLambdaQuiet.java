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
import java.util.function.Supplier;

/**
 * Same as {@link StLambdaQuiet}, but with catching all checked exceptions.
 *
 * <p>This decorator may be useful if you don't want to create a new
 * class for your shift, but just want a simple piece of code to
 * do the transformation without catching exceptions.</p>
 *
 * @since 0.13.0
 * @checkstyle IllegalCatchCheck (500 lines)
 */
public final class StLambdaQuiet implements Shift {

    /**
     * The UID.
     * @todo #40:30m Create TrLambdaQuiet and TrLambdaQuietTest
     */
    private final Supplier<String> name;

    /**
     * The function.
     */
    private final BiFuncChecked<Integer, XML, XML> lambda;

    /**
     * Ctor.
     * @param fun The function
     * @since 0.13.0
     */
    @SuppressWarnings({"PMD.AvoidRethrowingException", "PMD.AvoidCatchingGenericException"})
    public StLambdaQuiet(final FuncChecked<XML, XML> fun) {
        this((integer, xml) -> fun.apply(xml));
    }

    /**
     * Ctor.
     * @param uid The ID
     * @param fun The function
     * @since 0.13.0
     */
    @SuppressWarnings({"PMD.AvoidRethrowingException", "PMD.AvoidCatchingGenericException"})
    public StLambdaQuiet(final String uid, final FuncChecked<XML, XML> fun) {
        this(uid, (integer, xml) -> fun.apply(xml));
    }

    /**
     * Ctor.
     * @param fun The function
     */
    public StLambdaQuiet(final BiFuncChecked<Integer, XML, XML> fun) {
        this(
            new Supplier<String>() {
                @Override
                public String get() {
                    return String.format("λ-%x", this.hashCode());
                }
            },
            fun
        );
    }

    /**
     * Ctor.
     * @param uid The ID
     * @param fun The function
     */
    public StLambdaQuiet(final String uid, final BiFuncChecked<Integer, XML, XML> fun) {
        this(() -> uid, fun);
    }

    /**
     * Ctor.
     * @param uid The ID
     * @param fun The function
     */
    public StLambdaQuiet(final Supplier<String> uid, final BiFuncChecked<Integer, XML, XML> fun) {
        this.name = uid;
        this.lambda = fun;
    }

    @Override
    public String uid() {
        return this.name.get();
    }

    @Override
    @SuppressWarnings({"PMD.AvoidRethrowingException", "PMD.AvoidCatchingGenericException"})
    public XML apply(final int position, final XML xml) {
        try {
            return this.lambda.apply(position, xml);
        } catch (final RuntimeException ex) {
            throw ex;
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

}
