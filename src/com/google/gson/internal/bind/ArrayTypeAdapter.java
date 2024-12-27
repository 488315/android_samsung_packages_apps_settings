package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ArrayTypeAdapter<E> extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() { // from class:
                                       // com.google.gson.internal.bind.ArrayTypeAdapter.1
                @Override // com.google.gson.TypeAdapterFactory
                public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                    Type type = typeToken.getType();
                    boolean z = type instanceof GenericArrayType;
                    if (!z && (!(type instanceof Class) || !((Class) type).isArray())) {
                        return null;
                    }
                    Type[] typeArr = C$Gson$Types.EMPTY_TYPE_ARRAY;
                    Type genericComponentType =
                            z
                                    ? ((GenericArrayType) type).getGenericComponentType()
                                    : ((Class) type).getComponentType();
                    return new ArrayTypeAdapter(
                            gson,
                            gson.getAdapter(TypeToken.get(genericComponentType)),
                            C$Gson$Types.getRawType(genericComponentType));
                }
            };
    public final Class componentType;
    public final TypeAdapter componentTypeAdapter;

    public ArrayTypeAdapter(Gson gson, TypeAdapter typeAdapter, Class cls) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, cls);
        this.componentType = cls;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(
                    ((TypeAdapterRuntimeTypeWrapper) this.componentTypeAdapter)
                            .delegate.read(jsonReader));
        }
        jsonReader.endArray();
        int size = arrayList.size();
        if (!this.componentType.isPrimitive()) {
            return arrayList.toArray(
                    (Object[]) Array.newInstance((Class<?>) this.componentType, size));
        }
        Object newInstance = Array.newInstance((Class<?>) this.componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        if (obj == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.componentTypeAdapter.write(jsonWriter, Array.get(obj, i));
        }
        jsonWriter.endArray();
    }
}
