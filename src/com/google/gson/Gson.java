package com.google.gson;

import com.google.gson.TypeAdapter.AnonymousClass1;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.NumberTypeAdapter;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.internal.sql.SqlTypesSupport;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Gson {
    public final ConstructorConstructor constructorConstructor;
    public final List factories;
    public final boolean generateNonExecutableJson;
    public final boolean htmlSafe;
    public final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    public final boolean lenient;
    public final boolean prettyPrinting;
    public final boolean serializeNulls;
    public final ThreadLocal threadLocalAdapterResults;
    public final ConcurrentMap typeTokenCache;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.Gson$1, reason: invalid class name */
    class AnonymousClass1 extends TypeAdapter {
        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Double.valueOf(jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            Number number = (Number) obj;
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            double doubleValue = number.doubleValue();
            Gson.checkValidFloatingPoint(doubleValue);
            jsonWriter.value(doubleValue);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.Gson$2, reason: invalid class name */
    class AnonymousClass2 extends TypeAdapter {
        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Float.valueOf((float) jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            Number number = (Number) obj;
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            float floatValue = number.floatValue();
            Gson.checkValidFloatingPoint(floatValue);
            if (!(number instanceof Float)) {
                number = Float.valueOf(floatValue);
            }
            jsonWriter.value(number);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class FutureTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
        public TypeAdapter delegate = null;

        @Override // com.google.gson.internal.bind.SerializationDelegatingTypeAdapter
        public final TypeAdapter getSerializationDelegate() {
            TypeAdapter typeAdapter = this.delegate;
            if (typeAdapter != null) {
                return typeAdapter;
            }
            throw new IllegalStateException(
                    "Adapter for type with cyclic dependency has been used before dependency has"
                        + " been resolved");
        }

        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            TypeAdapter typeAdapter = this.delegate;
            if (typeAdapter != null) {
                return typeAdapter.read(jsonReader);
            }
            throw new IllegalStateException(
                    "Adapter for type with cyclic dependency has been used before dependency has"
                        + " been resolved");
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            TypeAdapter typeAdapter = this.delegate;
            if (typeAdapter == null) {
                throw new IllegalStateException(
                        "Adapter for type with cyclic dependency has been used before dependency"
                            + " has been resolved");
            }
            typeAdapter.write(jsonWriter, obj);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Gson() {
        /*
            r12 = this;
            com.google.gson.internal.Excluder r1 = com.google.gson.internal.Excluder.DEFAULT
            com.google.gson.FieldNamingPolicy r2 = com.google.gson.FieldNamingPolicy.IDENTITY
            java.util.Map r3 = java.util.Collections.emptyMap()
            com.google.gson.LongSerializationPolicy r7 = com.google.gson.LongSerializationPolicy.DEFAULT
            java.util.Collections.emptyList()
            java.util.Collections.emptyList()
            java.util.List r8 = java.util.Collections.emptyList()
            com.google.gson.ToNumberPolicy r9 = com.google.gson.ToNumberPolicy.DOUBLE
            com.google.gson.ToNumberPolicy r10 = com.google.gson.ToNumberPolicy.LAZILY_PARSED_NUMBER
            java.util.List r11 = java.util.Collections.emptyList()
            r5 = 0
            r6 = 1
            r4 = 1
            r0 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.gson.Gson.<init>():void");
    }

    public static void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(
                    d
                            + " is not a valid double value as per JSON specification. To override"
                            + " this behavior, use"
                            + " GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    public final Object fromJson(String str, Class cls) {
        Object fromJson = fromJson(str, TypeToken.get(cls));
        if (cls == Integer.TYPE) {
            cls = Integer.class;
        } else if (cls == Float.TYPE) {
            cls = Float.class;
        } else if (cls == Byte.TYPE) {
            cls = Byte.class;
        } else if (cls == Double.TYPE) {
            cls = Double.class;
        } else if (cls == Long.TYPE) {
            cls = Long.class;
        } else if (cls == Character.TYPE) {
            cls = Character.class;
        } else if (cls == Boolean.TYPE) {
            cls = Boolean.class;
        } else if (cls == Short.TYPE) {
            cls = Short.class;
        } else if (cls == Void.TYPE) {
            cls = Void.class;
        }
        return cls.cast(fromJson);
    }

    public final TypeAdapter getAdapter(TypeToken typeToken) {
        boolean z;
        Objects.requireNonNull(typeToken, "type must not be null");
        TypeAdapter typeAdapter =
                (TypeAdapter) ((ConcurrentHashMap) this.typeTokenCache).get(typeToken);
        if (typeAdapter != null) {
            return typeAdapter;
        }
        Map map = (Map) this.threadLocalAdapterResults.get();
        if (map == null) {
            map = new HashMap();
            this.threadLocalAdapterResults.set(map);
            z = true;
        } else {
            TypeAdapter typeAdapter2 = (TypeAdapter) map.get(typeToken);
            if (typeAdapter2 != null) {
                return typeAdapter2;
            }
            z = false;
        }
        try {
            FutureTypeAdapter futureTypeAdapter = new FutureTypeAdapter();
            map.put(typeToken, futureTypeAdapter);
            Iterator it = this.factories.iterator();
            TypeAdapter typeAdapter3 = null;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                typeAdapter3 = ((TypeAdapterFactory) it.next()).create(this, typeToken);
                if (typeAdapter3 != null) {
                    if (futureTypeAdapter.delegate != null) {
                        throw new AssertionError("Delegate is already set");
                    }
                    futureTypeAdapter.delegate = typeAdapter3;
                    map.put(typeToken, typeAdapter3);
                }
            }
            if (z) {
                this.threadLocalAdapterResults.remove();
            }
            if (typeAdapter3 != null) {
                if (z) {
                    ((ConcurrentHashMap) this.typeTokenCache).putAll(map);
                }
                return typeAdapter3;
            }
            throw new IllegalArgumentException("GSON (2.10.1) cannot handle " + typeToken);
        } catch (Throwable th) {
            if (z) {
                this.threadLocalAdapterResults.remove();
            }
            throw th;
        }
    }

    public final TypeAdapter getDelegateAdapter(
            TypeAdapterFactory typeAdapterFactory, TypeToken typeToken) {
        if (!this.factories.contains(typeAdapterFactory)) {
            typeAdapterFactory = this.jsonAdapterFactory;
        }
        boolean z = false;
        for (TypeAdapterFactory typeAdapterFactory2 : this.factories) {
            if (z) {
                TypeAdapter create = typeAdapterFactory2.create(this, typeToken);
                if (create != null) {
                    return create;
                }
            } else if (typeAdapterFactory2 == typeAdapterFactory) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public final JsonWriter newJsonWriter(Writer writer) {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.indent = "  ";
            jsonWriter.separator = ": ";
        }
        jsonWriter.htmlSafe = this.htmlSafe;
        jsonWriter.lenient = this.lenient;
        jsonWriter.serializeNulls = this.serializeNulls;
        return jsonWriter;
    }

    public final String toJson(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        try {
            toJson(obj, type, newJsonWriter(stringWriter));
            return stringWriter.toString();
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public final String toString() {
        return "{serializeNulls:"
                + this.serializeNulls
                + ",factories:"
                + this.factories
                + ",instanceCreators:"
                + this.constructorConstructor
                + "}";
    }

    public Gson(
            Excluder excluder,
            FieldNamingStrategy fieldNamingStrategy,
            Map map,
            boolean z,
            boolean z2,
            boolean z3,
            LongSerializationPolicy longSerializationPolicy,
            List list,
            ToNumberStrategy toNumberStrategy,
            ToNumberStrategy toNumberStrategy2,
            List list2) {
        final TypeAdapter typeAdapter;
        TypeAdapterFactory newFactory;
        this.threadLocalAdapterResults = new ThreadLocal();
        this.typeTokenCache = new ConcurrentHashMap();
        ConstructorConstructor constructorConstructor = new ConstructorConstructor(list2, map, z3);
        this.constructorConstructor = constructorConstructor;
        this.serializeNulls = false;
        this.generateNonExecutableJson = false;
        this.htmlSafe = z;
        this.prettyPrinting = z2;
        this.lenient = false;
        ArrayList arrayList = new ArrayList();
        arrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        arrayList.add(ObjectTypeAdapter.getFactory(toNumberStrategy));
        arrayList.add(excluder);
        arrayList.addAll(list);
        arrayList.add(TypeAdapters.STRING_FACTORY);
        arrayList.add(TypeAdapters.INTEGER_FACTORY);
        arrayList.add(TypeAdapters.BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.BYTE_FACTORY);
        arrayList.add(TypeAdapters.SHORT_FACTORY);
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            typeAdapter = TypeAdapters.LONG;
        } else {
            typeAdapter =
                    new TypeAdapter() { // from class: com.google.gson.Gson.3
                        @Override // com.google.gson.TypeAdapter
                        public final Object read(JsonReader jsonReader) {
                            if (jsonReader.peek() != JsonToken.NULL) {
                                return Long.valueOf(jsonReader.nextLong());
                            }
                            jsonReader.nextNull();
                            return null;
                        }

                        @Override // com.google.gson.TypeAdapter
                        public final void write(JsonWriter jsonWriter, Object obj) {
                            Number number = (Number) obj;
                            if (number == null) {
                                jsonWriter.nullValue();
                            } else {
                                jsonWriter.value(number.toString());
                            }
                        }
                    };
        }
        arrayList.add(TypeAdapters.newFactory(Long.TYPE, Long.class, typeAdapter));
        arrayList.add(TypeAdapters.newFactory(Double.TYPE, Double.class, new AnonymousClass1()));
        arrayList.add(TypeAdapters.newFactory(Float.TYPE, Float.class, new AnonymousClass2()));
        if (toNumberStrategy2 == ToNumberPolicy.LAZILY_PARSED_NUMBER) {
            newFactory = NumberTypeAdapter.LAZILY_PARSED_NUMBER_FACTORY;
        } else {
            newFactory = NumberTypeAdapter.newFactory(toNumberStrategy2);
        }
        arrayList.add(newFactory);
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        arrayList.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        arrayList.add(
                TypeAdapters.newFactory(
                        AtomicLong.class,
                        new TypeAdapter() { // from class: com.google.gson.Gson.4
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                return new AtomicLong(
                                        ((Number) TypeAdapter.this.read(jsonReader)).longValue());
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                TypeAdapter.this.write(
                                        jsonWriter, Long.valueOf(((AtomicLong) obj).get()));
                            }
                        }
                        .new AnonymousClass1()));
        arrayList.add(
                TypeAdapters.newFactory(
                        AtomicLongArray.class,
                        new TypeAdapter() { // from class: com.google.gson.Gson.5
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                ArrayList arrayList2 = new ArrayList();
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    arrayList2.add(
                                            Long.valueOf(
                                                    ((Number) TypeAdapter.this.read(jsonReader))
                                                            .longValue()));
                                }
                                jsonReader.endArray();
                                int size = arrayList2.size();
                                AtomicLongArray atomicLongArray = new AtomicLongArray(size);
                                for (int i = 0; i < size; i++) {
                                    atomicLongArray.set(i, ((Long) arrayList2.get(i)).longValue());
                                }
                                return atomicLongArray;
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                AtomicLongArray atomicLongArray = (AtomicLongArray) obj;
                                jsonWriter.beginArray();
                                int length = atomicLongArray.length();
                                for (int i = 0; i < length; i++) {
                                    TypeAdapter.this.write(
                                            jsonWriter, Long.valueOf(atomicLongArray.get(i)));
                                }
                                jsonWriter.endArray();
                            }
                        }
                        .new AnonymousClass1()));
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        arrayList.add(TypeAdapters.CHARACTER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
        arrayList.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        arrayList.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        arrayList.add(
                TypeAdapters.newFactory(
                        LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
        arrayList.add(TypeAdapters.URL_FACTORY);
        arrayList.add(TypeAdapters.URI_FACTORY);
        arrayList.add(TypeAdapters.UUID_FACTORY);
        arrayList.add(TypeAdapters.CURRENCY_FACTORY);
        arrayList.add(TypeAdapters.LOCALE_FACTORY);
        arrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
        arrayList.add(TypeAdapters.BIT_SET_FACTORY);
        arrayList.add(DateTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CALENDAR_FACTORY);
        if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
            arrayList.add(SqlTypesSupport.TIME_FACTORY);
            arrayList.add(SqlTypesSupport.DATE_FACTORY);
            arrayList.add(SqlTypesSupport.TIMESTAMP_FACTORY);
        }
        arrayList.add(ArrayTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CLASS_FACTORY);
        arrayList.add(new CollectionTypeAdapterFactory(constructorConstructor));
        arrayList.add(new MapTypeAdapterFactory(constructorConstructor));
        JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory =
                new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor);
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        arrayList.add(jsonAdapterAnnotationTypeAdapterFactory);
        arrayList.add(TypeAdapters.ENUM_FACTORY);
        arrayList.add(
                new ReflectiveTypeAdapterFactory(
                        constructorConstructor,
                        fieldNamingStrategy,
                        excluder,
                        jsonAdapterAnnotationTypeAdapterFactory,
                        list2));
        this.factories = Collections.unmodifiableList(arrayList);
    }

    public final void toJson(Object obj, Type type, JsonWriter jsonWriter) {
        TypeAdapter adapter = getAdapter(TypeToken.get(type));
        boolean z = jsonWriter.lenient;
        jsonWriter.lenient = true;
        boolean z2 = jsonWriter.htmlSafe;
        jsonWriter.htmlSafe = this.htmlSafe;
        boolean z3 = jsonWriter.serializeNulls;
        jsonWriter.serializeNulls = this.serializeNulls;
        try {
            try {
                try {
                    adapter.write(jsonWriter, obj);
                } catch (AssertionError e) {
                    throw new AssertionError("AssertionError (GSON 2.10.1): " + e.getMessage(), e);
                }
            } catch (IOException e2) {
                throw new JsonIOException(e2);
            }
        } finally {
            jsonWriter.lenient = z;
            jsonWriter.htmlSafe = z2;
            jsonWriter.serializeNulls = z3;
        }
    }

    public final Object fromJson(String str, Type type) {
        return fromJson(str, TypeToken.get(type));
    }

    public final Object fromJson(String str, TypeToken typeToken) {
        Object obj = null;
        if (str == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(new StringReader(str));
        boolean z = this.lenient;
        boolean z2 = true;
        jsonReader.lenient = true;
        try {
            try {
                try {
                    try {
                        jsonReader.peek();
                        z2 = false;
                        obj = getAdapter(typeToken).read(jsonReader);
                    } catch (EOFException e) {
                        if (!z2) {
                            throw new JsonSyntaxException(e);
                        }
                    } catch (IOException e2) {
                        throw new JsonSyntaxException(e2);
                    }
                    if (obj != null) {
                        try {
                            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                                throw new JsonSyntaxException(
                                        "JSON document was not fully consumed.");
                            }
                        } catch (MalformedJsonException e3) {
                            throw new JsonSyntaxException(e3);
                        } catch (IOException e4) {
                            throw new JsonIOException(e4);
                        }
                    }
                    return obj;
                } catch (AssertionError e5) {
                    throw new AssertionError(
                            "AssertionError (GSON 2.10.1): " + e5.getMessage(), e5);
                }
            } catch (IllegalStateException e6) {
                throw new JsonSyntaxException(e6);
            }
        } finally {
            jsonReader.lenient = z;
        }
    }

    public final String toJson(Object obj) {
        if (obj == null) {
            StringWriter stringWriter = new StringWriter();
            try {
                toJson(newJsonWriter(stringWriter));
                return stringWriter.toString();
            } catch (IOException e) {
                throw new JsonIOException(e);
            }
        }
        return toJson(obj, obj.getClass());
    }

    public final void toJson(JsonWriter jsonWriter) {
        JsonNull jsonNull = JsonNull.INSTANCE;
        boolean z = jsonWriter.lenient;
        jsonWriter.lenient = true;
        boolean z2 = jsonWriter.htmlSafe;
        jsonWriter.htmlSafe = this.htmlSafe;
        boolean z3 = jsonWriter.serializeNulls;
        jsonWriter.serializeNulls = this.serializeNulls;
        try {
            try {
                TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonNull);
                jsonWriter.lenient = z;
                jsonWriter.htmlSafe = z2;
                jsonWriter.serializeNulls = z3;
            } catch (IOException e) {
                throw new JsonIOException(e);
            } catch (AssertionError e2) {
                throw new AssertionError("AssertionError (GSON 2.10.1): " + e2.getMessage(), e2);
            }
        } catch (Throwable th) {
            jsonWriter.lenient = z;
            jsonWriter.htmlSafe = z2;
            jsonWriter.serializeNulls = z3;
            throw th;
        }
    }
}
