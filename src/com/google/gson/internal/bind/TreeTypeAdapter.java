package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TreeTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
    public volatile TypeAdapter delegate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class SingleTypeFactory implements TypeAdapterFactory {
        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            typeToken.getRawType();
            throw null;
        }
    }

    public final TypeAdapter delegate$2() {
        TypeAdapter typeAdapter = this.delegate;
        typeAdapter.getClass();
        return typeAdapter;
    }

    @Override // com.google.gson.internal.bind.SerializationDelegatingTypeAdapter
    public final TypeAdapter getSerializationDelegate() {
        TypeAdapter typeAdapter = this.delegate;
        typeAdapter.getClass();
        return typeAdapter;
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        return delegate$2().read(jsonReader);
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        delegate$2().write(jsonWriter, obj);
    }
}
