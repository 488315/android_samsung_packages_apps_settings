package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ObjectTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory DOUBLE_FACTORY =
            new AnonymousClass1(ToNumberPolicy.DOUBLE);
    public final Gson gson;
    public final ToNumberStrategy toNumberStrategy;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.bind.ObjectTypeAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements TypeAdapterFactory {
        public final /* synthetic */ ToNumberStrategy val$toNumberStrategy;

        public AnonymousClass1(ToNumberStrategy toNumberStrategy) {
            this.val$toNumberStrategy = toNumberStrategy;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == Object.class) {
                return new ObjectTypeAdapter(gson, this.val$toNumberStrategy);
            }
            return null;
        }
    }

    public ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
        this.gson = gson;
        this.toNumberStrategy = toNumberStrategy;
    }

    public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
        return toNumberStrategy == ToNumberPolicy.DOUBLE
                ? DOUBLE_FACTORY
                : new AnonymousClass1(toNumberStrategy);
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        Object arrayList;
        Object arrayList2;
        JsonToken peek = jsonReader.peek();
        int ordinal = peek.ordinal();
        if (ordinal == 0) {
            jsonReader.beginArray();
            arrayList = new ArrayList();
        } else if (ordinal != 2) {
            arrayList = null;
        } else {
            jsonReader.beginObject();
            arrayList = new LinkedTreeMap();
        }
        if (arrayList == null) {
            return readTerminal(jsonReader, peek);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        while (true) {
            if (jsonReader.hasNext()) {
                String nextName = arrayList instanceof Map ? jsonReader.nextName() : null;
                JsonToken peek2 = jsonReader.peek();
                int ordinal2 = peek2.ordinal();
                if (ordinal2 == 0) {
                    jsonReader.beginArray();
                    arrayList2 = new ArrayList();
                } else if (ordinal2 != 2) {
                    arrayList2 = null;
                } else {
                    jsonReader.beginObject();
                    arrayList2 = new LinkedTreeMap();
                }
                boolean z = arrayList2 != null;
                if (arrayList2 == null) {
                    arrayList2 = readTerminal(jsonReader, peek2);
                }
                if (arrayList instanceof List) {
                    ((List) arrayList).add(arrayList2);
                } else {
                    ((Map) arrayList).put(nextName, arrayList2);
                }
                if (z) {
                    arrayDeque.addLast(arrayList);
                    arrayList = arrayList2;
                }
            } else {
                if (arrayList instanceof List) {
                    jsonReader.endArray();
                } else {
                    jsonReader.endObject();
                }
                if (arrayDeque.isEmpty()) {
                    return arrayList;
                }
                arrayList = arrayDeque.removeLast();
            }
        }
    }

    public final Object readTerminal(JsonReader jsonReader, JsonToken jsonToken) {
        int ordinal = jsonToken.ordinal();
        if (ordinal == 5) {
            return jsonReader.nextString();
        }
        if (ordinal == 6) {
            return this.toNumberStrategy.readNumber(jsonReader);
        }
        if (ordinal == 7) {
            return Boolean.valueOf(jsonReader.nextBoolean());
        }
        if (ordinal == 8) {
            jsonReader.nextNull();
            return null;
        }
        throw new IllegalStateException("Unexpected token: " + jsonToken);
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        if (obj == null) {
            jsonWriter.nullValue();
            return;
        }
        Class<?> cls = obj.getClass();
        Gson gson = this.gson;
        gson.getClass();
        TypeAdapter adapter = gson.getAdapter(TypeToken.get((Class) cls));
        if (!(adapter instanceof ObjectTypeAdapter)) {
            adapter.write(jsonWriter, obj);
        } else {
            jsonWriter.beginObject();
            jsonWriter.endObject();
        }
    }
}
