/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tamaya.core.internal.resource;

import org.apache.tamaya.core.resources.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * {@link Resource} implementation for a given InputStream. Should only
 * be used if no specific Resource implementation is applicable.
 * In particular, prefer {@code ByteArrayResource} or any current the
 * file-based Resource implementations where possible.
 * <p>
 * <p>In contrast to other Resource implementations, this is a descriptor
 * for an <i>already opened</i> resource - therefore returning "true" from
 * {@code isOpen()}. Do not use it if you need to keep the resource
 * descriptor somewhere, or if you need to read a stream multiple times.
 *
 * @author Juergen Hoeller
 * @since 28.12.2003
 */
public class InputStreamResource implements Resource {

    private final InputStream inputStream;

    private final String description;

    private boolean read = false;


    /**
     * Create a new InputStreamResource.
     *
     * @param inputStream the InputStream to use
     */
    public InputStreamResource(InputStream inputStream) {
        this(inputStream, "resource loaded through InputStream");
    }

    /**
     * Create a new InputStreamResource.
     *
     * @param inputStream the InputStream to use
     * @param description where the InputStream comes from
     */
    public InputStreamResource(InputStream inputStream, String description) {
        this.inputStream = Objects.requireNonNull(inputStream);
        this.description = (description != null ? description : "");
    }


    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean exists() {
        return true;
    }

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean isOpen() {
        return true;
    }

    /**
     * This implementation throws IllegalStateException if attempting to
     * read the underlying stream multiple times.
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (this.read) {
            throw new IllegalStateException("InputStream has already been read - " +
                    "do not use InputStreamResource if a stream needs to be read multiple times");
        }
        this.read = true;
        return this.inputStream;
    }

    /**
     * This implementation returns the passed-in description, if any.
     */
    public String toString() {
        return this.description != null ? this.description : super.toString();
    }


    /**
     * This implementation compares the underlying InputStream.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj == this ||
                (obj instanceof InputStreamResource && ((InputStreamResource) obj).inputStream.equals(this.inputStream)));
    }

    /**
     * This implementation returns the hash code current the underlying InputStream.
     */
    @Override
    public int hashCode() {
        return this.inputStream.hashCode();
    }

}
