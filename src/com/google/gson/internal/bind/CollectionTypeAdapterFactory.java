package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    public final ConstructorConstructor constructorConstructor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Adapter<E> extends TypeAdapter {
        public final ObjectConstructor constructor;
        public final TypeAdapter elementTypeAdapter;

        public Adapter(
                Gson gson,
                Type type,
                TypeAdapter typeAdapter,
                ObjectConstructor objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.constructor = objectConstructor;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection collection = (Collection) this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                collection.add(
                        ((TypeAdapterRuntimeTypeWrapper) this.elementTypeAdapter)
                                .delegate.read(jsonReader));
            }
            jsonReader.endArray();
            return collection;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            Collection collection = (Collection) obj;
            if (collection == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            Iterator<E> it = collection.iterator();
            while (it.hasNext()) {
                this.elementTypeAdapter.write(jsonWriter, it.next());
            }
            jsonWriter.endArray();
        }
    }

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
        Type type = typeToken.getType();
        Class rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type[] typeArr = C$Gson$Types.EMPTY_TYPE_ARRAY;
        if (type instanceof WildcardType) {
            type = ((WildcardType) type).getUpperBounds()[0];
        }
        C$Gson$Preconditions.checkArgument(Collection.class.isAssignableFrom(rawType));
        Type resolve =
                C$Gson$Types.resolve(
                        type,
                        rawType,
                        C$Gson$Types.getGenericSupertype(type, rawType, Collection.class),
                        new HashMap());
        Class cls =
                resolve instanceof ParameterizedType
                        ? ((ParameterizedType) resolve).getActualTypeArguments()[0]
                        : Object.class;
        return new Adapter(
                gson,
                cls,
                gson.getAdapter(TypeToken.get(cls)),
                this.constructorConstructor.get(typeToken));
    }
}
