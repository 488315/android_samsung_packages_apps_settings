package com.google.gson.internal.bind;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    public final ConstructorConstructor constructorConstructor;
    public final Excluder excluder;
    public final FieldNamingStrategy fieldNamingPolicy;
    public final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    public final List reflectionFilters;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final boolean deserialized;
        public final Field field;
        public final String fieldName;
        public final String name;
        public final boolean serialized;
        public final /* synthetic */ Method val$accessor;
        public final /* synthetic */ boolean val$blockInaccessible;
        public final /* synthetic */ Gson val$context;
        public final /* synthetic */ TypeToken val$fieldType;
        public final /* synthetic */ boolean val$isPrimitive;
        public final /* synthetic */ boolean val$isStaticFinalField;
        public final /* synthetic */ boolean val$jsonAdapterPresent;
        public final /* synthetic */ TypeAdapter val$typeAdapter;

        public AnonymousClass1(
                String str,
                Field field,
                boolean z,
                boolean z2,
                boolean z3,
                Method method,
                boolean z4,
                TypeAdapter typeAdapter,
                Gson gson,
                TypeToken typeToken,
                boolean z5,
                boolean z6) {
            this.val$blockInaccessible = z3;
            this.val$accessor = method;
            this.val$jsonAdapterPresent = z4;
            this.val$typeAdapter = typeAdapter;
            this.val$context = gson;
            this.val$fieldType = typeToken;
            this.val$isPrimitive = z5;
            this.val$isStaticFinalField = z6;
            this.name = str;
            this.field = field;
            this.fieldName = field.getName();
            this.serialized = z;
            this.deserialized = z2;
        }

        public final void write(JsonWriter jsonWriter, Object obj) {
            Object obj2;
            if (this.serialized) {
                if (this.val$blockInaccessible) {
                    Method method = this.val$accessor;
                    if (method == null) {
                        ReflectiveTypeAdapterFactory.m1067$$Nest$smcheckAccessible(obj, this.field);
                    } else {
                        ReflectiveTypeAdapterFactory.m1067$$Nest$smcheckAccessible(obj, method);
                    }
                }
                Method method2 = this.val$accessor;
                if (method2 != null) {
                    try {
                        obj2 = method2.invoke(obj, null);
                    } catch (InvocationTargetException e) {
                        throw new JsonIOException(
                                ComposerKt$$ExternalSyntheticOutline0.m(
                                        "Accessor ",
                                        ReflectionHelper.getAccessibleObjectDescription(
                                                this.val$accessor, false),
                                        " threw exception"),
                                e.getCause());
                    }
                } else {
                    obj2 = this.field.get(obj);
                }
                if (obj2 == obj) {
                    return;
                }
                jsonWriter.name(this.name);
                boolean z = this.val$jsonAdapterPresent;
                TypeAdapter typeAdapter = this.val$typeAdapter;
                if (!z) {
                    typeAdapter =
                            new TypeAdapterRuntimeTypeWrapper(
                                    this.val$context, typeAdapter, this.val$fieldType.getType());
                }
                typeAdapter.write(jsonWriter, obj2);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Adapter<T, A> extends TypeAdapter {
        public final Map boundFields;

        public Adapter(Map map) {
            this.boundFields = map;
        }

        public abstract Object createAccumulator();

        public abstract Object finalize(Object obj);

        @Override // com.google.gson.TypeAdapter
        public final Object read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Object createAccumulator = createAccumulator();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    AnonymousClass1 anonymousClass1 =
                            (AnonymousClass1) this.boundFields.get(jsonReader.nextName());
                    if (anonymousClass1 != null && anonymousClass1.deserialized) {
                        readField(createAccumulator, jsonReader, anonymousClass1);
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return finalize(createAccumulator);
            } catch (IllegalAccessException e) {
                ReflectionHelper.RecordHelper recordHelper = ReflectionHelper.RECORD_HELPER;
                throw new RuntimeException(
                        "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain"
                            + " ReflectionAccessFilter features require Java >= 9 to work"
                            + " correctly. If you are not using ReflectionAccessFilter, report this"
                            + " to the Gson maintainers.",
                        e);
            } catch (IllegalStateException e2) {
                throw new JsonSyntaxException(e2);
            }
        }

        public abstract void readField(
                Object obj, JsonReader jsonReader, AnonymousClass1 anonymousClass1);

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            if (obj == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                Iterator it = this.boundFields.values().iterator();
                while (it.hasNext()) {
                    ((AnonymousClass1) it.next()).write(jsonWriter, obj);
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                ReflectionHelper.RecordHelper recordHelper = ReflectionHelper.RECORD_HELPER;
                throw new RuntimeException(
                        "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain"
                            + " ReflectionAccessFilter features require Java >= 9 to work"
                            + " correctly. If you are not using ReflectionAccessFilter, report this"
                            + " to the Gson maintainers.",
                        e);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class RecordAdapter<T> extends Adapter<T, Object[]> {
        public static final Map PRIMITIVE_DEFAULTS;
        public final Map componentIndices;
        public final Constructor constructor;
        public final Object[] constructorArgsDefaults;

        static {
            HashMap hashMap = new HashMap();
            hashMap.put(Byte.TYPE, (byte) 0);
            hashMap.put(Short.TYPE, (short) 0);
            hashMap.put(Integer.TYPE, 0);
            hashMap.put(Long.TYPE, 0L);
            hashMap.put(Float.TYPE, Float.valueOf(0.0f));
            hashMap.put(Double.TYPE, Double.valueOf(0.0d));
            hashMap.put(Character.TYPE, (char) 0);
            hashMap.put(Boolean.TYPE, Boolean.FALSE);
            PRIMITIVE_DEFAULTS = hashMap;
        }

        public RecordAdapter(Class cls, Map map) {
            super(map);
            this.componentIndices = new HashMap();
            ReflectionHelper.RecordHelper recordHelper = ReflectionHelper.RECORD_HELPER;
            Constructor canonicalRecordConstructor =
                    recordHelper.getCanonicalRecordConstructor(cls);
            this.constructor = canonicalRecordConstructor;
            ReflectionHelper.makeAccessible(canonicalRecordConstructor);
            String[] recordComponentNames = recordHelper.getRecordComponentNames(cls);
            for (int i = 0; i < recordComponentNames.length; i++) {
                ((HashMap) this.componentIndices).put(recordComponentNames[i], Integer.valueOf(i));
            }
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i2 = 0; i2 < parameterTypes.length; i2++) {
                this.constructorArgsDefaults[i2] =
                        ((HashMap) PRIMITIVE_DEFAULTS).get(parameterTypes[i2]);
            }
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final Object createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final Object finalize(Object obj) {
            Object[] objArr = (Object[]) obj;
            try {
                return this.constructor.newInstance(objArr);
            } catch (IllegalAccessException e) {
                ReflectionHelper.RecordHelper recordHelper = ReflectionHelper.RECORD_HELPER;
                throw new RuntimeException(
                        "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain"
                            + " ReflectionAccessFilter features require Java >= 9 to work"
                            + " correctly. If you are not using ReflectionAccessFilter, report this"
                            + " to the Gson maintainers.",
                        e);
            } catch (IllegalArgumentException | InstantiationException e2) {
                throw new RuntimeException(
                        "Failed to invoke constructor '"
                                + ReflectionHelper.constructorToString(this.constructor)
                                + "' with args "
                                + Arrays.toString(objArr),
                        e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(
                        "Failed to invoke constructor '"
                                + ReflectionHelper.constructorToString(this.constructor)
                                + "' with args "
                                + Arrays.toString(objArr),
                        e3.getCause());
            }
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final void readField(
                Object obj, JsonReader jsonReader, AnonymousClass1 anonymousClass1) {
            Object[] objArr = (Object[]) obj;
            HashMap hashMap = (HashMap) this.componentIndices;
            String str = anonymousClass1.fieldName;
            Integer num = (Integer) hashMap.get(str);
            if (num == null) {
                throw new IllegalStateException(
                        "Could not find the index in the constructor '"
                                + ReflectionHelper.constructorToString(this.constructor)
                                + "' for field with name '"
                                + str
                                + "', unable to determine which argument in the constructor the"
                                + " field corresponds to. This is unexpected behavior, as we expect"
                                + " the RecordComponents to have the same names as the fields in"
                                + " the Java class, and that the order of the RecordComponents is"
                                + " the same as the order of the canonical constructor"
                                + " parameters.");
            }
            int intValue = num.intValue();
            Object read = anonymousClass1.val$typeAdapter.read(jsonReader);
            if (read != null || !anonymousClass1.val$isPrimitive) {
                objArr[intValue] = read;
            } else {
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "null is not allowed as value for record component '",
                                str,
                                "' of primitive type; at path ");
                m.append(jsonReader.getPath(false));
                throw new JsonParseException(m.toString());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: -$$Nest$smcheckAccessible, reason: not valid java name */
    public static void m1067$$Nest$smcheckAccessible(
            Object obj, AccessibleObject accessibleObject) {
        if (Modifier.isStatic(((Member) accessibleObject).getModifiers())) {
            obj = null;
        }
        if (!ReflectionAccessFilterHelper.AccessChecker.INSTANCE.canAccess(obj, accessibleObject)) {
            throw new JsonIOException(
                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                            ReflectionHelper.getAccessibleObjectDescription(accessibleObject, true),
                            " is not accessible and ReflectionAccessFilter does not permit making"
                                + " it accessible. Register a TypeAdapter for the declaring type,"
                                + " adjust the access filter or increase the visibility of the"
                                + " element and its declaring type."));
        }
    }

    public ReflectiveTypeAdapterFactory(
            ConstructorConstructor constructorConstructor,
            FieldNamingStrategy fieldNamingStrategy,
            Excluder excluder,
            JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory,
            List list) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        this.reflectionFilters = list;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
        Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters);
        return ReflectionHelper.RECORD_HELPER.isRecord(rawType)
                ? new RecordAdapter(rawType, getBoundFields(gson, typeToken, rawType, true))
                : new FieldReflectionAdapter(
                        this.constructorConstructor.get(typeToken),
                        getBoundFields(gson, typeToken, rawType, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00bf  */
    /* JADX WARN: Type inference failed for: r2v37 */
    /* JADX WARN: Type inference failed for: r2v40, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [int] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map getBoundFields(
            com.google.gson.Gson r34,
            com.google.gson.reflect.TypeToken r35,
            java.lang.Class r36,
            boolean r37) {
        /*
            Method dump skipped, instructions count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(com.google.gson.Gson,"
                    + " com.google.gson.reflect.TypeToken, java.lang.Class,"
                    + " boolean):java.util.Map");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003c, code lost:

       if (r6.version >= r0.value()) goto L13;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:

       if (r6.version < r1.value()) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0065, code lost:

       if ((r0.getModifiers() & 8) == 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean includeField(java.lang.reflect.Field r7, boolean r8) {
        /*
            r6 = this;
            java.lang.Class r0 = r7.getType()
            com.google.gson.internal.Excluder r6 = r6.excluder
            boolean r0 = r6.excludeClassChecks(r0)
            if (r0 != 0) goto L95
            r6.excludeClassInStrategy(r8)
            int r0 = r6.modifiers
            int r1 = r7.getModifiers()
            r0 = r0 & r1
            if (r0 == 0) goto L1a
            goto L95
        L1a:
            double r0 = r6.version
            r2 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L4a
            java.lang.Class<com.google.gson.annotations.Since> r0 = com.google.gson.annotations.Since.class
            java.lang.annotation.Annotation r0 = r7.getAnnotation(r0)
            com.google.gson.annotations.Since r0 = (com.google.gson.annotations.Since) r0
            java.lang.Class<com.google.gson.annotations.Until> r1 = com.google.gson.annotations.Until.class
            java.lang.annotation.Annotation r1 = r7.getAnnotation(r1)
            com.google.gson.annotations.Until r1 = (com.google.gson.annotations.Until) r1
            if (r0 == 0) goto L3e
            double r2 = r0.value()
            double r4 = r6.version
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 < 0) goto L95
        L3e:
            if (r1 == 0) goto L4a
            double r0 = r1.value()
            double r2 = r6.version
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 >= 0) goto L95
        L4a:
            boolean r0 = r7.isSynthetic()
            if (r0 == 0) goto L51
            goto L95
        L51:
            boolean r0 = r6.serializeInnerClasses
            if (r0 != 0) goto L67
            java.lang.Class r0 = r7.getType()
            boolean r1 = r0.isMemberClass()
            if (r1 == 0) goto L67
            int r0 = r0.getModifiers()
            r0 = r0 & 8
            if (r0 == 0) goto L95
        L67:
            java.lang.Class r7 = r7.getType()
            boolean r7 = com.google.gson.internal.Excluder.isAnonymousOrNonStaticLocal(r7)
            if (r7 == 0) goto L72
            goto L95
        L72:
            if (r8 == 0) goto L77
            java.util.List r6 = r6.serializationStrategies
            goto L79
        L77:
            java.util.List r6 = r6.deserializationStrategies
        L79:
            boolean r7 = r6.isEmpty()
            if (r7 != 0) goto L93
            java.util.Iterator r6 = r6.iterator()
            boolean r7 = r6.hasNext()
            if (r7 != 0) goto L8a
            goto L93
        L8a:
            java.lang.Object r6 = r6.next()
            androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(r6)
            r6 = 0
            throw r6
        L93:
            r6 = 1
            goto L96
        L95:
            r6 = 0
        L96:
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.includeField(java.lang.reflect.Field,"
                    + " boolean):boolean");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        public final ObjectConstructor constructor;

        public FieldReflectionAdapter(ObjectConstructor objectConstructor, Map map) {
            super(map);
            this.constructor = objectConstructor;
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final Object createAccumulator() {
            return this.constructor.construct();
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final void readField(
                Object obj, JsonReader jsonReader, AnonymousClass1 anonymousClass1) {
            Object read = anonymousClass1.val$typeAdapter.read(jsonReader);
            if (read == null && anonymousClass1.val$isPrimitive) {
                return;
            }
            if (anonymousClass1.val$blockInaccessible) {
                ReflectiveTypeAdapterFactory.m1067$$Nest$smcheckAccessible(
                        obj, anonymousClass1.field);
            } else if (anonymousClass1.val$isStaticFinalField) {
                throw new JsonIOException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "Cannot set value of 'static final' ",
                                ReflectionHelper.getAccessibleObjectDescription(
                                        anonymousClass1.field, false)));
            }
            anonymousClass1.field.set(obj, read);
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public final Object finalize(Object obj) {
            return obj;
        }
    }
}
