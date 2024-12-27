package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
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

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    public final boolean complexMapKeySerialization = false;
    public final ConstructorConstructor constructorConstructor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Adapter<K, V> extends TypeAdapter {
        public final ObjectConstructor constructor;
        public final TypeAdapter keyTypeAdapter;
        public final TypeAdapter valueTypeAdapter;

        public Adapter(
                Gson gson,
                Type type,
                TypeAdapter typeAdapter,
                Type type2,
                TypeAdapter typeAdapter2,
                ObjectConstructor objectConstructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, type2);
            this.constructor = objectConstructor;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map map = (Map) this.constructor.construct();
            JsonToken jsonToken = JsonToken.BEGIN_ARRAY;
            TypeAdapter typeAdapter = this.valueTypeAdapter;
            TypeAdapter typeAdapter2 = this.keyTypeAdapter;
            if (peek == jsonToken) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    Object read =
                            ((TypeAdapterRuntimeTypeWrapper) typeAdapter2)
                                    .delegate.read(jsonReader);
                    if (map.put(
                                    read,
                                    ((TypeAdapterRuntimeTypeWrapper) typeAdapter)
                                            .delegate.read(jsonReader))
                            != null) {
                        throw new JsonSyntaxException("duplicate key: " + read);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    JsonReader.AnonymousClass1.INSTANCE.getClass();
                    int i = jsonReader.peeked;
                    if (i == 0) {
                        i = jsonReader.doPeek();
                    }
                    if (i == 13) {
                        jsonReader.peeked = 9;
                    } else if (i == 12) {
                        jsonReader.peeked = 8;
                    } else {
                        if (i != 14) {
                            throw new IllegalStateException(
                                    "Expected a name but was "
                                            + jsonReader.peek()
                                            + jsonReader.locationString());
                        }
                        jsonReader.peeked = 10;
                    }
                    Object read2 =
                            ((TypeAdapterRuntimeTypeWrapper) typeAdapter2)
                                    .delegate.read(jsonReader);
                    if (map.put(
                                    read2,
                                    ((TypeAdapterRuntimeTypeWrapper) typeAdapter)
                                            .delegate.read(jsonReader))
                            != null) {
                        throw new JsonSyntaxException("duplicate key: " + read2);
                    }
                }
                jsonReader.endObject();
            }
            return map;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            String str;
            Map map = (Map) obj;
            if (map == null) {
                jsonWriter.nullValue();
                return;
            }
            boolean z = MapTypeAdapterFactory.this.complexMapKeySerialization;
            TypeAdapter typeAdapter = this.valueTypeAdapter;
            if (!z) {
                jsonWriter.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    typeAdapter.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
                return;
            }
            ArrayList arrayList = new ArrayList(map.size());
            ArrayList arrayList2 = new ArrayList(map.size());
            int i = 0;
            boolean z2 = false;
            for (Map.Entry<K, V> entry2 : map.entrySet()) {
                TypeAdapter typeAdapter2 = this.keyTypeAdapter;
                K key = entry2.getKey();
                typeAdapter2.getClass();
                try {
                    JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
                    typeAdapter2.write(jsonTreeWriter, key);
                    if (!((ArrayList) jsonTreeWriter.stack).isEmpty()) {
                        throw new IllegalStateException(
                                "Expected one JSON element but was " + jsonTreeWriter.stack);
                    }
                    JsonElement jsonElement = jsonTreeWriter.product;
                    arrayList.add(jsonElement);
                    arrayList2.add(entry2.getValue());
                    jsonElement.getClass();
                    z2 |= (jsonElement instanceof JsonArray) || (jsonElement instanceof JsonObject);
                } catch (IOException e) {
                    throw new JsonIOException(e);
                }
            }
            if (z2) {
                jsonWriter.beginArray();
                int size = arrayList.size();
                while (i < size) {
                    jsonWriter.beginArray();
                    TypeAdapters.JSON_ELEMENT.write(jsonWriter, (JsonElement) arrayList.get(i));
                    typeAdapter.write(jsonWriter, arrayList2.get(i));
                    jsonWriter.endArray();
                    i++;
                }
                jsonWriter.endArray();
                return;
            }
            jsonWriter.beginObject();
            int size2 = arrayList.size();
            while (i < size2) {
                JsonElement jsonElement2 = (JsonElement) arrayList.get(i);
                jsonElement2.getClass();
                boolean z3 = jsonElement2 instanceof JsonPrimitive;
                if (z3) {
                    if (!z3) {
                        throw new IllegalStateException("Not a JSON Primitive: " + jsonElement2);
                    }
                    JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement2;
                    Object obj2 = jsonPrimitive.value;
                    if (obj2 instanceof Number) {
                        str = String.valueOf(jsonPrimitive.getAsNumber());
                    } else if (obj2 instanceof Boolean) {
                        str = Boolean.toString(jsonPrimitive.getAsBoolean());
                    } else {
                        if (!(obj2 instanceof String)) {
                            throw new AssertionError();
                        }
                        str = jsonPrimitive.getAsString();
                    }
                } else {
                    if (!(jsonElement2 instanceof JsonNull)) {
                        throw new AssertionError();
                    }
                    str = "null";
                }
                jsonWriter.name(str);
                typeAdapter.write(jsonWriter, arrayList2.get(i));
                i++;
            }
            jsonWriter.endObject();
        }
    }

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
        Type[] actualTypeArguments;
        Type type = typeToken.getType();
        Class rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type[] typeArr = C$Gson$Types.EMPTY_TYPE_ARRAY;
        if (type == Properties.class) {
            actualTypeArguments = new Type[] {String.class, String.class};
        } else {
            if (type instanceof WildcardType) {
                type = ((WildcardType) type).getUpperBounds()[0];
            }
            C$Gson$Preconditions.checkArgument(Map.class.isAssignableFrom(rawType));
            Type resolve =
                    C$Gson$Types.resolve(
                            type,
                            rawType,
                            C$Gson$Types.getGenericSupertype(type, rawType, Map.class),
                            new HashMap());
            actualTypeArguments =
                    resolve instanceof ParameterizedType
                            ? ((ParameterizedType) resolve).getActualTypeArguments()
                            : new Type[] {Object.class, Object.class};
        }
        Type type2 = actualTypeArguments[0];
        return new Adapter(
                gson,
                actualTypeArguments[0],
                (type2 == Boolean.TYPE || type2 == Boolean.class)
                        ? TypeAdapters.BOOLEAN_AS_STRING
                        : gson.getAdapter(TypeToken.get(type2)),
                actualTypeArguments[1],
                gson.getAdapter(TypeToken.get(actualTypeArguments[1])),
                this.constructorConstructor.get(typeToken));
    }
}
