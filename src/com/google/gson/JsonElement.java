package com.google.gson;

import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class JsonElement {
    public boolean getAsBoolean() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int getAsInt() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public final JsonObject getAsJsonObject() {
        if (this instanceof JsonObject) {
            return (JsonObject) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public String getAsString() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public final String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.lenient = true;
            TypeAdapters.JSON_ELEMENT.write(jsonWriter, this);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
