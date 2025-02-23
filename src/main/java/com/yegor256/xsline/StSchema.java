/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.log.Logger;
import com.jcabi.xml.ClasspathResolver;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.xml.sax.SAXParseException;

/**
 * A {@link Shift} that validates incoming XML against encapsulated XSD schema.
 *
 * <p>If you want to validate XML documents after each shift in your
 * train, you can use {@link TrAfter} in combination with {@link StSchema}.</p>
 *
 * <p>If the location of the XSD schema is embedded into the XML document
 * itself, either with the help of the {@code xsi:schemaLocation} attribute
 * or the {@code xsi:noNamespaceSchemaLocation} attribute, you can use
 * {@link StSchema} constructor without any arguments. If this constructor
 * is used but the XML document doesn't contain any schema location hints,
 * a runtime exception will be thrown.</p>
 *
 * @since 0.10.0
 */
public final class StSchema extends StEnvelope {

    /**
     * Ctor.
     */
    public StSchema() {
        this((XML) null);
    }

    /**
     * Ctor.
     *
     * @param path The path of XSD document in classpath
     */
    public StSchema(final String path) {
        this(StSchema.make(path));
    }

    /**
     * Ctor.
     *
     * @param path The path of XSD document
     * @throws FileNotFoundException If file isn't found
     */
    public StSchema(final Path path) throws FileNotFoundException {
        this(new XMLDocument(path));
    }

    /**
     * Ctor.
     *
     * @param schema The schema
     */
    public StSchema(final XML schema) {
        super(
            new StLambda(
                "xsd-schema",
                xml -> StSchema.validate(schema, xml)
            )
        );
    }

    /**
     * Validate it.
     *
     * @param schema The schema
     * @param xml The XML
     * @return The same XML
     */
    private static XML validate(final XML schema, final XML xml) {
        final Collection<SAXParseException> violations;
        if (Objects.isNull(schema)) {
            violations = xml.validate(new ClasspathResolver());
        } else {
            violations = xml.validate(schema);
        }
        if (!violations.isEmpty()) {
            final Collection<String> msgs = new ArrayList<>(violations.size());
            for (final SAXParseException violation : violations) {
                msgs.add(StSchema.asMessage(violation));
            }
            if (Logger.isDebugEnabled(StSchema.class)) {
                Logger.debug(
                    StSchema.class,
                    "There are %d XSD violation(s) in this XML %[list]s:%n%s",
                    violations.size(), msgs,
                    xml
                );
            }
            throw new IllegalStateException(
                String.format(
                    "There are %d XSD violation(s): %s",
                    violations.size(),
                    String.join("; ", msgs)
                )
            );
        }
        return xml;
    }

    /**
     * Turn violation into a message.
     *
     * @param violation The violation
     * @return The message
     */
    private static String asMessage(final SAXParseException violation) {
        final StringBuilder msg = new StringBuilder(100);
        if (violation.getLineNumber() >= 0) {
            msg.append('#').append(violation.getLineNumber());
            if (violation.getColumnNumber() >= 0) {
                msg.append(':').append(violation.getColumnNumber());
            }
            msg.append(' ');
        }
        msg.append(violation.getLocalizedMessage());
        if (violation.getException() != null) {
            msg.append(" (")
                .append(violation.getException().getClass().getSimpleName())
                .append(')');
        }
        return msg.toString();
    }

    /**
     * Make XSD safely.
     *
     * @param path Path in classpath
     * @return XSD
     */
    private static XML make(final String path) {
        final URL url = StClasspath.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Path '%s' not found in classpath", path
                )
            );
        }
        try {
            return new XMLDocument(url);
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Failed to read '%s' from classpath", path),
                ex
            );
        }
    }

}
