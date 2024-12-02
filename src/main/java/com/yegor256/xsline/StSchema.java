/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2024 Yegor Bugayenko
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
import com.jcabi.xml.XMLDocument;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import org.xml.sax.SAXParseException;

/**
 * A {@link Shift} that validates incoming XML against encapsulated XSD schema.
 *
 * <p>If you want to validate XML documents after each shift in your
 * train, you can use {@link TrAfter} in combination with {@link StSchema}.</p>
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
     * @param path The path of XSD document in classpath
     */
    public StSchema(final String path) {
        this(StSchema.make(path));
    }

    /**
     * Ctor.
     * @param path The path of XSD document
     * @throws FileNotFoundException If file not found
     */
    public StSchema(final Path path) throws FileNotFoundException {
        this(new XMLDocument(path));
    }

    /**
     * Ctor.
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
     * @param schema The schema
     * @param xml The XML
     * @return The same XML
     */
    private static XML validate(final XML schema, final XML xml) {
        final Collection<SAXParseException> violations;
        if (schema == null) {
            violations = xml.validate();
        } else {
            violations = xml.validate(schema);
        }
        if (!violations.isEmpty()) {
            final Collection<String> msgs = new ArrayList<>(violations.size());
            for (final SAXParseException violation : violations) {
                msgs.add(
                    String.format(
                        "#%d:%d %s",
                        violation.getLineNumber(),
                        violation.getColumnNumber(),
                        violation.getLocalizedMessage()
                    )
                );
            }
            if (Logger.isDebugEnabled(StSchema.class)) {
                Logger.debug(
                    StSchema.class,
                    "There are %d XSD violation(s) in this XML:%n%s",
                    violations.size(),
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
     * Make XSD safely.
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
