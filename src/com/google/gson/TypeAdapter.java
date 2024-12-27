package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TypeAdapter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.TypeAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends TypeAdapter {
        public AnonymousClass1() {}

        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return TypeAdapter.this.read(jsonReader);
            }
            jsonReader.nextNull();
            return null;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            if (obj == null) {
                jsonWriter.nullValue();
            } else {
                TypeAdapter.this.write(jsonWriter, obj);
            }
        }
    }

    public final TypeAdapter nullSafe() {
        return new AnonymousClass1();
    }

    public abstract Object read(JsonReader jsonReader);

    public abstract void write(JsonWriter jsonWriter, Object obj);
}
