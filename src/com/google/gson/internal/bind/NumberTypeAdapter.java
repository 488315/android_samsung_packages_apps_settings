package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NumberTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory LAZILY_PARSED_NUMBER_FACTORY =
            newFactory(ToNumberPolicy.LAZILY_PARSED_NUMBER);
    public final ToNumberStrategy toNumberStrategy;

    public NumberTypeAdapter(ToNumberStrategy toNumberStrategy) {
        this.toNumberStrategy = toNumberStrategy;
    }

    public static TypeAdapterFactory newFactory(ToNumberStrategy toNumberStrategy) {
        return new TypeAdapterFactory() { // from class:
                                          // com.google.gson.internal.bind.NumberTypeAdapter.1
            @Override // com.google.gson.TypeAdapterFactory
            public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                if (typeToken.getRawType() == Number.class) {
                    return NumberTypeAdapter.this;
                }
                return null;
            }
        };
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        JsonToken peek = jsonReader.peek();
        int ordinal = peek.ordinal();
        if (ordinal == 5 || ordinal == 6) {
            return this.toNumberStrategy.readNumber(jsonReader);
        }
        if (ordinal == 8) {
            jsonReader.nextNull();
            return null;
        }
        throw new JsonSyntaxException(
                "Expecting number, got: " + peek + "; at path " + jsonReader.getPath(false));
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        jsonWriter.value((Number) obj);
    }
}
