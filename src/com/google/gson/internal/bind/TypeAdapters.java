package com.google.gson.internal.bind;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

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
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TypeAdapters {
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
    public static final TypeAdapter BIG_DECIMAL;
    public static final TypeAdapter BIG_INTEGER;
    public static final TypeAdapter BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapterFactory CURRENCY_FACTORY;
    public static final TypeAdapter DOUBLE = null;
    public static final TypeAdapterFactory ENUM_FACTORY;
    public static final TypeAdapter FLOAT = null;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapter LAZILY_PARSED_NUMBER;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter LONG;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapterFactory UUID_FACTORY;
    public static final TypeAdapterFactory CLASS_FACTORY =
            new AnonymousClass31(
                    Class.class,
                    new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.1
                        @Override // com.google.gson.TypeAdapter
                        public final Object read(JsonReader jsonReader) {
                            throw new UnsupportedOperationException(
                                    "Attempted to deserialize a java.lang.Class. Forgot to register"
                                        + " a type adapter?");
                        }

                        @Override // com.google.gson.TypeAdapter
                        public final void write(JsonWriter jsonWriter, Object obj) {
                            throw new UnsupportedOperationException(
                                    "Attempted to serialize java.lang.Class: "
                                            + ((Class) obj).getName()
                                            + ". Forgot to register a type adapter?");
                        }
                    }.nullSafe());
    public static final TypeAdapterFactory BIT_SET_FACTORY =
            new AnonymousClass31(
                    BitSet.class,
                    new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.2
                        @Override // com.google.gson.TypeAdapter
                        public final Object read(JsonReader jsonReader) {
                            boolean z;
                            BitSet bitSet = new BitSet();
                            jsonReader.beginArray();
                            JsonToken peek = jsonReader.peek();
                            int i = 0;
                            while (peek != JsonToken.END_ARRAY) {
                                int ordinal = peek.ordinal();
                                if (ordinal == 5 || ordinal == 6) {
                                    int nextInt = jsonReader.nextInt();
                                    if (nextInt == 0) {
                                        z = false;
                                    } else {
                                        if (nextInt != 1) {
                                            StringBuilder m =
                                                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                            nextInt,
                                                            "Invalid bitset value ",
                                                            ", expected 0 or 1; at path ");
                                            m.append(jsonReader.getPath(true));
                                            throw new JsonSyntaxException(m.toString());
                                        }
                                        z = true;
                                    }
                                } else {
                                    if (ordinal != 7) {
                                        throw new JsonSyntaxException(
                                                "Invalid bitset value type: "
                                                        + peek
                                                        + "; at path "
                                                        + jsonReader.getPath(false));
                                    }
                                    z = jsonReader.nextBoolean();
                                }
                                if (z) {
                                    bitSet.set(i);
                                }
                                i++;
                                peek = jsonReader.peek();
                            }
                            jsonReader.endArray();
                            return bitSet;
                        }

                        @Override // com.google.gson.TypeAdapter
                        public final void write(JsonWriter jsonWriter, Object obj) {
                            BitSet bitSet = (BitSet) obj;
                            jsonWriter.beginArray();
                            int length = bitSet.length();
                            for (int i = 0; i < length; i++) {
                                jsonWriter.value(bitSet.get(i) ? 1L : 0L);
                            }
                            jsonWriter.endArray();
                        }
                    }.nullSafe());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$30, reason: invalid class name */
    class AnonymousClass30 implements TypeAdapterFactory {
        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            typeToken.equals(null);
            return null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$31, reason: invalid class name */
    class AnonymousClass31 implements TypeAdapterFactory {
        public final /* synthetic */ Class val$type;
        public final /* synthetic */ TypeAdapter val$typeAdapter;

        public AnonymousClass31(Class cls, TypeAdapter typeAdapter) {
            this.val$type = cls;
            this.val$typeAdapter = typeAdapter;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == this.val$type) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public final String toString() {
            return "Factory[type="
                    + this.val$type.getName()
                    + ",adapter="
                    + this.val$typeAdapter
                    + "]";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$32, reason: invalid class name */
    class AnonymousClass32 implements TypeAdapterFactory {
        public final /* synthetic */ Class val$boxed;
        public final /* synthetic */ TypeAdapter val$typeAdapter;
        public final /* synthetic */ Class val$unboxed;

        public AnonymousClass32(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$unboxed = cls;
            this.val$boxed = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class rawType = typeToken.getRawType();
            if (rawType == this.val$unboxed || rawType == this.val$boxed) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public final String toString() {
            return "Factory[type="
                    + this.val$boxed.getName()
                    + "+"
                    + this.val$unboxed.getName()
                    + ",adapter="
                    + this.val$typeAdapter
                    + "]";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter {
        public final Map nameToConstant = new HashMap();
        public final Map stringToConstant = new HashMap();
        public final Map constantToName = new HashMap();

        public EnumTypeAdapter(final Class cls) {
            try {
                for (Field field :
                        (Field[])
                                AccessController.doPrivileged(
                                        new PrivilegedAction() { // from class:
                                                                 // com.google.gson.internal.bind.TypeAdapters.EnumTypeAdapter.1
                                            @Override // java.security.PrivilegedAction
                                            public final Object run() {
                                                Field[] declaredFields = cls.getDeclaredFields();
                                                ArrayList arrayList =
                                                        new ArrayList(declaredFields.length);
                                                for (Field field2 : declaredFields) {
                                                    if (field2.isEnumConstant()) {
                                                        arrayList.add(field2);
                                                    }
                                                }
                                                Field[] fieldArr =
                                                        (Field[]) arrayList.toArray(new Field[0]);
                                                AccessibleObject.setAccessible(fieldArr, true);
                                                return fieldArr;
                                            }
                                        })) {
                    Enum r4 = (Enum) field.get(null);
                    String name = r4.name();
                    String str = r4.toString();
                    SerializedName serializedName =
                            (SerializedName) field.getAnnotation(SerializedName.class);
                    if (serializedName != null) {
                        name = serializedName.value();
                        for (String str2 : serializedName.alternate()) {
                            ((HashMap) this.nameToConstant).put(str2, r4);
                        }
                    }
                    ((HashMap) this.nameToConstant).put(name, r4);
                    ((HashMap) this.stringToConstant).put(str, r4);
                    ((HashMap) this.constantToName).put(r4, name);
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            Enum r0 = (Enum) ((HashMap) this.nameToConstant).get(nextString);
            return r0 == null ? (Enum) ((HashMap) this.stringToConstant).get(nextString) : r0;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            Enum r2 = (Enum) obj;
            jsonWriter.value(r2 == null ? null : (String) ((HashMap) this.constantToName).get(r2));
        }
    }

    static {
        TypeAdapter typeAdapter =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.3
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        JsonToken peek = jsonReader.peek();
                        if (peek != JsonToken.NULL) {
                            return peek == JsonToken.STRING
                                    ? Boolean.valueOf(Boolean.parseBoolean(jsonReader.nextString()))
                                    : Boolean.valueOf(jsonReader.nextBoolean());
                        }
                        jsonReader.nextNull();
                        return null;
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        jsonWriter.value((Boolean) obj);
                    }
                };
        BOOLEAN_AS_STRING =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.4
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() != JsonToken.NULL) {
                            return Boolean.valueOf(jsonReader.nextString());
                        }
                        jsonReader.nextNull();
                        return null;
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        Boolean bool = (Boolean) obj;
                        jsonWriter.value(bool == null ? "null" : bool.toString());
                    }
                };
        BOOLEAN_FACTORY = new AnonymousClass32(Boolean.TYPE, Boolean.class, typeAdapter);
        BYTE_FACTORY =
                new AnonymousClass32(
                        Byte.TYPE,
                        Byte.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.5
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                try {
                                    int nextInt = jsonReader.nextInt();
                                    if (nextInt <= 255 && nextInt >= -128) {
                                        return Byte.valueOf((byte) nextInt);
                                    }
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    nextInt,
                                                    "Lossy conversion from ",
                                                    " to byte; at path ");
                                    m.append(jsonReader.getPath(true));
                                    throw new JsonSyntaxException(m.toString());
                                } catch (NumberFormatException e) {
                                    throw new JsonSyntaxException(e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                if (((Number) obj) == null) {
                                    jsonWriter.nullValue();
                                } else {
                                    jsonWriter.value(r4.byteValue());
                                }
                            }
                        });
        SHORT_FACTORY =
                new AnonymousClass32(
                        Short.TYPE,
                        Short.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.6
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                try {
                                    int nextInt = jsonReader.nextInt();
                                    if (nextInt <= 65535 && nextInt >= -32768) {
                                        return Short.valueOf((short) nextInt);
                                    }
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    nextInt,
                                                    "Lossy conversion from ",
                                                    " to short; at path ");
                                    m.append(jsonReader.getPath(true));
                                    throw new JsonSyntaxException(m.toString());
                                } catch (NumberFormatException e) {
                                    throw new JsonSyntaxException(e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                if (((Number) obj) == null) {
                                    jsonWriter.nullValue();
                                } else {
                                    jsonWriter.value(r4.shortValue());
                                }
                            }
                        });
        INTEGER_FACTORY =
                new AnonymousClass32(
                        Integer.TYPE,
                        Integer.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.7
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                try {
                                    return Integer.valueOf(jsonReader.nextInt());
                                } catch (NumberFormatException e) {
                                    throw new JsonSyntaxException(e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                if (((Number) obj) == null) {
                                    jsonWriter.nullValue();
                                } else {
                                    jsonWriter.value(r4.intValue());
                                }
                            }
                        });
        ATOMIC_INTEGER_FACTORY =
                new AnonymousClass31(
                        AtomicInteger.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.8
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                try {
                                    return new AtomicInteger(jsonReader.nextInt());
                                } catch (NumberFormatException e) {
                                    throw new JsonSyntaxException(e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                jsonWriter.value(((AtomicInteger) obj).get());
                            }
                        }.nullSafe());
        ATOMIC_BOOLEAN_FACTORY =
                new AnonymousClass31(
                        AtomicBoolean.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.9
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                return new AtomicBoolean(jsonReader.nextBoolean());
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                jsonWriter.value(((AtomicBoolean) obj).get());
                            }
                        }.nullSafe());
        ATOMIC_INTEGER_ARRAY_FACTORY =
                new AnonymousClass31(
                        AtomicIntegerArray.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.10
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                ArrayList arrayList = new ArrayList();
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    try {
                                        arrayList.add(Integer.valueOf(jsonReader.nextInt()));
                                    } catch (NumberFormatException e) {
                                        throw new JsonSyntaxException(e);
                                    }
                                }
                                jsonReader.endArray();
                                int size = arrayList.size();
                                AtomicIntegerArray atomicIntegerArray =
                                        new AtomicIntegerArray(size);
                                for (int i = 0; i < size; i++) {
                                    atomicIntegerArray.set(
                                            i, ((Integer) arrayList.get(i)).intValue());
                                }
                                return atomicIntegerArray;
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                jsonWriter.beginArray();
                                int length = ((AtomicIntegerArray) obj).length();
                                for (int i = 0; i < length; i++) {
                                    jsonWriter.value(r5.get(i));
                                }
                                jsonWriter.endArray();
                            }
                        }.nullSafe());
        LONG =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.11
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            jsonReader.nextNull();
                            return null;
                        }
                        try {
                            return Long.valueOf(jsonReader.nextLong());
                        } catch (NumberFormatException e) {
                            throw new JsonSyntaxException(e);
                        }
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        Number number = (Number) obj;
                        if (number == null) {
                            jsonWriter.nullValue();
                        } else {
                            jsonWriter.value(number.longValue());
                        }
                    }
                };
        new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.12
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
                if (!(number instanceof Float)) {
                    number = Float.valueOf(number.floatValue());
                }
                jsonWriter.value(number);
            }
        };
        new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.13
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
                } else {
                    jsonWriter.value(number.doubleValue());
                }
            }
        };
        CHARACTER_FACTORY =
                new AnonymousClass32(
                        Character.TYPE,
                        Character.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.14
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                String nextString = jsonReader.nextString();
                                if (nextString.length() == 1) {
                                    return Character.valueOf(nextString.charAt(0));
                                }
                                StringBuilder m =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "Expecting character, got: ", nextString, "; at ");
                                m.append(jsonReader.getPath(true));
                                throw new JsonSyntaxException(m.toString());
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                Character ch = (Character) obj;
                                jsonWriter.value(ch == null ? null : String.valueOf(ch));
                            }
                        });
        TypeAdapter typeAdapter2 =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.15
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        JsonToken peek = jsonReader.peek();
                        if (peek != JsonToken.NULL) {
                            return peek == JsonToken.BOOLEAN
                                    ? Boolean.toString(jsonReader.nextBoolean())
                                    : jsonReader.nextString();
                        }
                        jsonReader.nextNull();
                        return null;
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        jsonWriter.value((String) obj);
                    }
                };
        BIG_DECIMAL =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.16
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            jsonReader.nextNull();
                            return null;
                        }
                        String nextString = jsonReader.nextString();
                        try {
                            return new BigDecimal(nextString);
                        } catch (NumberFormatException e) {
                            StringBuilder m =
                                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                            "Failed parsing '",
                                            nextString,
                                            "' as BigDecimal; at path ");
                            m.append(jsonReader.getPath(true));
                            throw new JsonSyntaxException(m.toString(), e);
                        }
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        jsonWriter.value((BigDecimal) obj);
                    }
                };
        BIG_INTEGER =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.17
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            jsonReader.nextNull();
                            return null;
                        }
                        String nextString = jsonReader.nextString();
                        try {
                            return new BigInteger(nextString);
                        } catch (NumberFormatException e) {
                            StringBuilder m =
                                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                            "Failed parsing '",
                                            nextString,
                                            "' as BigInteger; at path ");
                            m.append(jsonReader.getPath(true));
                            throw new JsonSyntaxException(m.toString(), e);
                        }
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        jsonWriter.value((BigInteger) obj);
                    }
                };
        LAZILY_PARSED_NUMBER =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.18
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() != JsonToken.NULL) {
                            return new LazilyParsedNumber(jsonReader.nextString());
                        }
                        jsonReader.nextNull();
                        return null;
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        jsonWriter.value((LazilyParsedNumber) obj);
                    }
                };
        STRING_FACTORY = new AnonymousClass31(String.class, typeAdapter2);
        STRING_BUILDER_FACTORY =
                new AnonymousClass31(
                        StringBuilder.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.19
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() != JsonToken.NULL) {
                                    return new StringBuilder(jsonReader.nextString());
                                }
                                jsonReader.nextNull();
                                return null;
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                StringBuilder sb = (StringBuilder) obj;
                                jsonWriter.value(sb == null ? null : sb.toString());
                            }
                        });
        STRING_BUFFER_FACTORY =
                new AnonymousClass31(
                        StringBuffer.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.20
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() != JsonToken.NULL) {
                                    return new StringBuffer(jsonReader.nextString());
                                }
                                jsonReader.nextNull();
                                return null;
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                StringBuffer stringBuffer = (StringBuffer) obj;
                                jsonWriter.value(
                                        stringBuffer == null ? null : stringBuffer.toString());
                            }
                        });
        URL_FACTORY =
                new AnonymousClass31(
                        URL.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.21
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                String nextString = jsonReader.nextString();
                                if ("null".equals(nextString)) {
                                    return null;
                                }
                                return new URL(nextString);
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                URL url = (URL) obj;
                                jsonWriter.value(url == null ? null : url.toExternalForm());
                            }
                        });
        URI_FACTORY =
                new AnonymousClass31(
                        URI.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.22
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                try {
                                    String nextString = jsonReader.nextString();
                                    if ("null".equals(nextString)) {
                                        return null;
                                    }
                                    return new URI(nextString);
                                } catch (URISyntaxException e) {
                                    throw new JsonIOException(e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                URI uri = (URI) obj;
                                jsonWriter.value(uri == null ? null : uri.toASCIIString());
                            }
                        });
        final TypeAdapter typeAdapter3 =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.23
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() != JsonToken.NULL) {
                            return InetAddress.getByName(jsonReader.nextString());
                        }
                        jsonReader.nextNull();
                        return null;
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        InetAddress inetAddress = (InetAddress) obj;
                        jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
                    }
                };
        final Class<InetAddress> cls = InetAddress.class;
        INET_ADDRESS_FACTORY =
                new TypeAdapterFactory() { // from class:
                                           // com.google.gson.internal.bind.TypeAdapters.34
                    @Override // com.google.gson.TypeAdapterFactory
                    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                        final Class<?> rawType = typeToken.getRawType();
                        if (cls.isAssignableFrom(rawType)) {
                            return new TypeAdapter() { // from class:
                                                       // com.google.gson.internal.bind.TypeAdapters.34.1
                                @Override // com.google.gson.TypeAdapter
                                public final Object read(JsonReader jsonReader) {
                                    Object read = typeAdapter3.read(jsonReader);
                                    if (read == null || rawType.isInstance(read)) {
                                        return read;
                                    }
                                    throw new JsonSyntaxException(
                                            "Expected a "
                                                    + rawType.getName()
                                                    + " but was "
                                                    + read.getClass().getName()
                                                    + "; at path "
                                                    + jsonReader.getPath(true));
                                }

                                @Override // com.google.gson.TypeAdapter
                                public final void write(JsonWriter jsonWriter, Object obj) {
                                    typeAdapter3.write(jsonWriter, obj);
                                }
                            };
                        }
                        return null;
                    }

                    public final String toString() {
                        return "Factory[typeHierarchy="
                                + cls.getName()
                                + ",adapter="
                                + typeAdapter3
                                + "]";
                    }
                };
        UUID_FACTORY =
                new AnonymousClass31(
                        UUID.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.24
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                String nextString = jsonReader.nextString();
                                try {
                                    return UUID.fromString(nextString);
                                } catch (IllegalArgumentException e) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "Failed parsing '",
                                                    nextString,
                                                    "' as UUID; at path ");
                                    m.append(jsonReader.getPath(true));
                                    throw new JsonSyntaxException(m.toString(), e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                UUID uuid = (UUID) obj;
                                jsonWriter.value(uuid == null ? null : uuid.toString());
                            }
                        });
        CURRENCY_FACTORY =
                new AnonymousClass31(
                        Currency.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.25
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                String nextString = jsonReader.nextString();
                                try {
                                    return Currency.getInstance(nextString);
                                } catch (IllegalArgumentException e) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "Failed parsing '",
                                                    nextString,
                                                    "' as Currency; at path ");
                                    m.append(jsonReader.getPath(true));
                                    throw new JsonSyntaxException(m.toString(), e);
                                }
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                jsonWriter.value(((Currency) obj).getCurrencyCode());
                            }
                        }.nullSafe());
        final TypeAdapter typeAdapter4 =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.26
                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            jsonReader.nextNull();
                            return null;
                        }
                        jsonReader.beginObject();
                        int i = 0;
                        int i2 = 0;
                        int i3 = 0;
                        int i4 = 0;
                        int i5 = 0;
                        int i6 = 0;
                        while (jsonReader.peek() != JsonToken.END_OBJECT) {
                            String nextName = jsonReader.nextName();
                            int nextInt = jsonReader.nextInt();
                            if ("year".equals(nextName)) {
                                i = nextInt;
                            } else if ("month".equals(nextName)) {
                                i2 = nextInt;
                            } else if ("dayOfMonth".equals(nextName)) {
                                i3 = nextInt;
                            } else if ("hourOfDay".equals(nextName)) {
                                i4 = nextInt;
                            } else if ("minute".equals(nextName)) {
                                i5 = nextInt;
                            } else if ("second".equals(nextName)) {
                                i6 = nextInt;
                            }
                        }
                        jsonReader.endObject();
                        return new GregorianCalendar(i, i2, i3, i4, i5, i6);
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final void write(JsonWriter jsonWriter, Object obj) {
                        if (((Calendar) obj) == null) {
                            jsonWriter.nullValue();
                            return;
                        }
                        jsonWriter.beginObject();
                        jsonWriter.name("year");
                        jsonWriter.value(r4.get(1));
                        jsonWriter.name("month");
                        jsonWriter.value(r4.get(2));
                        jsonWriter.name("dayOfMonth");
                        jsonWriter.value(r4.get(5));
                        jsonWriter.name("hourOfDay");
                        jsonWriter.value(r4.get(11));
                        jsonWriter.name("minute");
                        jsonWriter.value(r4.get(12));
                        jsonWriter.name("second");
                        jsonWriter.value(r4.get(13));
                        jsonWriter.endObject();
                    }
                };
        final Class<Calendar> cls2 = Calendar.class;
        final Class<GregorianCalendar> cls3 = GregorianCalendar.class;
        CALENDAR_FACTORY =
                new TypeAdapterFactory() { // from class:
                                           // com.google.gson.internal.bind.TypeAdapters.33
                    @Override // com.google.gson.TypeAdapterFactory
                    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                        Class rawType = typeToken.getRawType();
                        if (rawType == cls2 || rawType == cls3) {
                            return typeAdapter4;
                        }
                        return null;
                    }

                    public final String toString() {
                        return "Factory[type="
                                + cls2.getName()
                                + "+"
                                + cls3.getName()
                                + ",adapter="
                                + typeAdapter4
                                + "]";
                    }
                };
        LOCALE_FACTORY =
                new AnonymousClass31(
                        Locale.class,
                        new TypeAdapter() { // from class:
                                            // com.google.gson.internal.bind.TypeAdapters.27
                            @Override // com.google.gson.TypeAdapter
                            public final Object read(JsonReader jsonReader) {
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                    return null;
                                }
                                StringTokenizer stringTokenizer =
                                        new StringTokenizer(jsonReader.nextString(), "_");
                                String nextToken =
                                        stringTokenizer.hasMoreElements()
                                                ? stringTokenizer.nextToken()
                                                : null;
                                String nextToken2 =
                                        stringTokenizer.hasMoreElements()
                                                ? stringTokenizer.nextToken()
                                                : null;
                                String nextToken3 =
                                        stringTokenizer.hasMoreElements()
                                                ? stringTokenizer.nextToken()
                                                : null;
                                return (nextToken2 == null && nextToken3 == null)
                                        ? new Locale(nextToken)
                                        : nextToken3 == null
                                                ? new Locale(nextToken, nextToken2)
                                                : new Locale(nextToken, nextToken2, nextToken3);
                            }

                            @Override // com.google.gson.TypeAdapter
                            public final void write(JsonWriter jsonWriter, Object obj) {
                                Locale locale = (Locale) obj;
                                jsonWriter.value(locale == null ? null : locale.toString());
                            }
                        });
        final TypeAdapter typeAdapter5 =
                new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.28
                    public static JsonElement readTerminal(
                            JsonReader jsonReader, JsonToken jsonToken) {
                        int ordinal = jsonToken.ordinal();
                        if (ordinal == 5) {
                            return new JsonPrimitive(jsonReader.nextString());
                        }
                        if (ordinal == 6) {
                            return new JsonPrimitive(
                                    new LazilyParsedNumber(jsonReader.nextString()));
                        }
                        if (ordinal == 7) {
                            return new JsonPrimitive(Boolean.valueOf(jsonReader.nextBoolean()));
                        }
                        if (ordinal == 8) {
                            jsonReader.nextNull();
                            return JsonNull.INSTANCE;
                        }
                        throw new IllegalStateException("Unexpected token: " + jsonToken);
                    }

                    public static void write(JsonWriter jsonWriter, JsonElement jsonElement) {
                        if (jsonElement == null || (jsonElement instanceof JsonNull)) {
                            jsonWriter.nullValue();
                            return;
                        }
                        boolean z = jsonElement instanceof JsonPrimitive;
                        if (z) {
                            if (!z) {
                                throw new IllegalStateException(
                                        "Not a JSON Primitive: " + jsonElement);
                            }
                            JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement;
                            Object obj = jsonPrimitive.value;
                            if (obj instanceof Number) {
                                jsonWriter.value(jsonPrimitive.getAsNumber());
                                return;
                            } else if (obj instanceof Boolean) {
                                jsonWriter.value(jsonPrimitive.getAsBoolean());
                                return;
                            } else {
                                jsonWriter.value(jsonPrimitive.getAsString());
                                return;
                            }
                        }
                        boolean z2 = jsonElement instanceof JsonArray;
                        if (z2) {
                            jsonWriter.beginArray();
                            if (!z2) {
                                throw new IllegalStateException("Not a JSON Array: " + jsonElement);
                            }
                            Iterator it = ((JsonArray) jsonElement).elements.iterator();
                            while (it.hasNext()) {
                                write(jsonWriter, (JsonElement) it.next());
                            }
                            jsonWriter.endArray();
                            return;
                        }
                        if (!(jsonElement instanceof JsonObject)) {
                            throw new IllegalArgumentException(
                                    "Couldn't write " + jsonElement.getClass());
                        }
                        jsonWriter.beginObject();
                        Iterator it2 =
                                ((LinkedTreeMap.EntrySet)
                                                jsonElement.getAsJsonObject().members.entrySet())
                                        .iterator();
                        while (((LinkedTreeMap.KeySet.AnonymousClass1) it2).hasNext()) {
                            LinkedTreeMap.Node nextNode =
                                    ((LinkedTreeMap.KeySet.AnonymousClass1) it2).nextNode();
                            jsonWriter.name((String) nextNode.getKey());
                            write(jsonWriter, (JsonElement) nextNode.getValue());
                        }
                        jsonWriter.endObject();
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final Object read(JsonReader jsonReader) {
                        JsonElement jsonArray;
                        JsonElement jsonArray2;
                        JsonToken peek = jsonReader.peek();
                        int ordinal = peek.ordinal();
                        if (ordinal == 0) {
                            jsonReader.beginArray();
                            jsonArray = new JsonArray();
                        } else if (ordinal != 2) {
                            jsonArray = null;
                        } else {
                            jsonReader.beginObject();
                            jsonArray = new JsonObject();
                        }
                        if (jsonArray == null) {
                            return readTerminal(jsonReader, peek);
                        }
                        ArrayDeque arrayDeque = new ArrayDeque();
                        while (true) {
                            if (jsonReader.hasNext()) {
                                String nextName =
                                        jsonArray instanceof JsonObject
                                                ? jsonReader.nextName()
                                                : null;
                                JsonToken peek2 = jsonReader.peek();
                                int ordinal2 = peek2.ordinal();
                                if (ordinal2 == 0) {
                                    jsonReader.beginArray();
                                    jsonArray2 = new JsonArray();
                                } else if (ordinal2 != 2) {
                                    jsonArray2 = null;
                                } else {
                                    jsonReader.beginObject();
                                    jsonArray2 = new JsonObject();
                                }
                                boolean z = jsonArray2 != null;
                                if (jsonArray2 == null) {
                                    jsonArray2 = readTerminal(jsonReader, peek2);
                                }
                                if (jsonArray instanceof JsonArray) {
                                    ((JsonArray) jsonArray).add(jsonArray2);
                                } else {
                                    ((JsonObject) jsonArray).add(nextName, jsonArray2);
                                }
                                if (z) {
                                    arrayDeque.addLast(jsonArray);
                                    jsonArray = jsonArray2;
                                }
                            } else {
                                if (jsonArray instanceof JsonArray) {
                                    jsonReader.endArray();
                                } else {
                                    jsonReader.endObject();
                                }
                                if (arrayDeque.isEmpty()) {
                                    return jsonArray;
                                }
                                jsonArray = (JsonElement) arrayDeque.removeLast();
                            }
                        }
                    }

                    @Override // com.google.gson.TypeAdapter
                    public final /* bridge */ /* synthetic */ void write(
                            JsonWriter jsonWriter, Object obj) {
                        write(jsonWriter, (JsonElement) obj);
                    }
                };
        JSON_ELEMENT = typeAdapter5;
        final Class<JsonElement> cls4 = JsonElement.class;
        JSON_ELEMENT_FACTORY =
                new TypeAdapterFactory() { // from class:
                                           // com.google.gson.internal.bind.TypeAdapters.34
                    @Override // com.google.gson.TypeAdapterFactory
                    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                        final Class rawType = typeToken.getRawType();
                        if (cls4.isAssignableFrom(rawType)) {
                            return new TypeAdapter() { // from class:
                                                       // com.google.gson.internal.bind.TypeAdapters.34.1
                                @Override // com.google.gson.TypeAdapter
                                public final Object read(JsonReader jsonReader) {
                                    Object read = typeAdapter5.read(jsonReader);
                                    if (read == null || rawType.isInstance(read)) {
                                        return read;
                                    }
                                    throw new JsonSyntaxException(
                                            "Expected a "
                                                    + rawType.getName()
                                                    + " but was "
                                                    + read.getClass().getName()
                                                    + "; at path "
                                                    + jsonReader.getPath(true));
                                }

                                @Override // com.google.gson.TypeAdapter
                                public final void write(JsonWriter jsonWriter, Object obj) {
                                    typeAdapter5.write(jsonWriter, obj);
                                }
                            };
                        }
                        return null;
                    }

                    public final String toString() {
                        return "Factory[typeHierarchy="
                                + cls4.getName()
                                + ",adapter="
                                + typeAdapter5
                                + "]";
                    }
                };
        ENUM_FACTORY =
                new TypeAdapterFactory() { // from class:
                                           // com.google.gson.internal.bind.TypeAdapters.29
                    @Override // com.google.gson.TypeAdapterFactory
                    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                        Class rawType = typeToken.getRawType();
                        if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                            return null;
                        }
                        if (!rawType.isEnum()) {
                            rawType = rawType.getSuperclass();
                        }
                        return new EnumTypeAdapter(rawType);
                    }
                };
    }

    public static TypeAdapterFactory newFactory(Class cls, TypeAdapter typeAdapter) {
        return new AnonymousClass31(cls, typeAdapter);
    }

    public static TypeAdapterFactory newFactory(Class cls, Class cls2, TypeAdapter typeAdapter) {
        return new AnonymousClass32(cls, cls2, typeAdapter);
    }
}
