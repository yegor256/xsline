/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XML;
import java.util.function.Supplier;

/**
 * A shift that executes the provided bi-function
 * and catches all checked exceptions.
 *
 * <p>This decorator may be useful if you don't want to create a new
 * class for your shift, but just want a simple piece of code to
 * do the transformation.</p>
 *
 * @since 0.13.0
 * @checkstyle IllegalCatchCheck (500 lines)
 */
public final class StLambda implements Shift {

    /**
     * The UID.
     */
    private final Supplier<String> name;

    /**
     * The function.
     */
    private final BiFunctionChecked<Integer, XML, XML> lambda;

    /**
     * Ctor.
     * @param fun The function
     * @since 0.13.0
     */
    public StLambda(final FunctionChecked<XML, XML> fun) {
        this((integer, xml) -> fun.apply(xml));
    }

    /**
     * Ctor.
     * @param uid The ID
     * @param fun The function
     * @since 0.13.0
     */
    public StLambda(final String uid, final FunctionChecked<XML, XML> fun) {
        this(uid, (integer, xml) -> fun.apply(xml));
    }

    /**
     * Ctor.
     * @param fun The function
     */
    public StLambda(final BiFunctionChecked<Integer, XML, XML> fun) {
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
    public StLambda(final String uid, final BiFunctionChecked<Integer, XML, XML> fun) {
        this(() -> uid, fun);
    }

    /**
     * Ctor.
     * @param uid The ID
     * @param fun The function
     */
    public StLambda(final Supplier<String> uid, final BiFunctionChecked<Integer, XML, XML> fun) {
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
