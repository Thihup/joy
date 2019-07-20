/*
 * Copyright 2019 the Joy Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.joy.internal;

import static org.leadpony.joy.internal.Requirements.requireNonNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

/**
 * An implementation of {@link JsonParserFactory}.
 *
 * @author leadpony
 */
class JsonParserFactoryImpl implements JsonParserFactory, InputStreamReaderFactory {

    private final Map<String, ?> configInUse;
    private final CharBufferFactory bufferFactory;

    JsonParserFactoryImpl(Map<String, ?> config, CharBufferFactory bufferFactory) {
        this.configInUse = Collections.emptyMap();
        this.bufferFactory = bufferFactory;
    }

    @Override
    public JsonParser createParser(Reader reader) {
        requireNonNull(reader, "reader");
        return new BasicJsonParser(reader, bufferFactory);
    }

    @Override
    public JsonParser createParser(InputStream in) {
        requireNonNull(in, "in");
        Reader reader = createStreamReader(in);
        return new BasicJsonParser(reader, bufferFactory);
    }

    @Override
    public JsonParser createParser(InputStream in, Charset charset) {
        requireNonNull(in, "in");
        requireNonNull(charset, "charset");
        Reader reader = new InputStreamReader(in, charset);
        return new BasicJsonParser(reader, bufferFactory);
    }

    @Override
    public JsonParser createParser(JsonObject obj) {
        requireNonNull(obj, "obj");
        return new JsonValueParser(obj);
    }

    @Override
    public JsonParser createParser(JsonArray array) {
        requireNonNull(array, "array");
        return new JsonValueParser(array);
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return configInUse;
    }
}
