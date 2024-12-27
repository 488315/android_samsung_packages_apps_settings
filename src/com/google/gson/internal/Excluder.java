package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    public double version = -1.0d;
    public final int modifiers = 136;
    public final boolean serializeInnerClasses = true;
    public final List serializationStrategies = Collections.emptyList();
    public final List deserializationStrategies = Collections.emptyList();

    public static boolean isAnonymousOrNonStaticLocal(Class cls) {
        return !Enum.class.isAssignableFrom(cls)
                && (cls.getModifiers() & 8) == 0
                && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final boolean z;
        final boolean z2;
        boolean excludeClassChecks = excludeClassChecks(typeToken.getRawType());
        if (excludeClassChecks) {
            z = true;
        } else {
            excludeClassInStrategy(true);
            z = false;
        }
        if (excludeClassChecks) {
            z2 = true;
        } else {
            excludeClassInStrategy(false);
            z2 = false;
        }
        if (z || z2) {
            return new TypeAdapter() { // from class: com.google.gson.internal.Excluder.1
                public TypeAdapter delegate;

                @Override // com.google.gson.TypeAdapter
                public final Object read(JsonReader jsonReader) {
                    if (z2) {
                        jsonReader.skipValue();
                        return null;
                    }
                    TypeAdapter typeAdapter = this.delegate;
                    if (typeAdapter == null) {
                        typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                        this.delegate = typeAdapter;
                    }
                    return typeAdapter.read(jsonReader);
                }

                @Override // com.google.gson.TypeAdapter
                public final void write(JsonWriter jsonWriter, Object obj) {
                    if (z) {
                        jsonWriter.nullValue();
                        return;
                    }
                    TypeAdapter typeAdapter = this.delegate;
                    if (typeAdapter == null) {
                        typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                        this.delegate = typeAdapter;
                    }
                    typeAdapter.write(jsonWriter, obj);
                }
            };
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002f, code lost:

       if (r7.version < r2.value()) goto L13;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0023, code lost:

       if (r7.version >= r0.value()) goto L8;
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0032, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean excludeClassChecks(java.lang.Class r8) {
        /*
            r7 = this;
            double r0 = r7.version
            r2 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            if (r0 == 0) goto L33
            java.lang.Class<com.google.gson.annotations.Since> r0 = com.google.gson.annotations.Since.class
            java.lang.annotation.Annotation r0 = r8.getAnnotation(r0)
            com.google.gson.annotations.Since r0 = (com.google.gson.annotations.Since) r0
            java.lang.Class<com.google.gson.annotations.Until> r2 = com.google.gson.annotations.Until.class
            java.lang.annotation.Annotation r2 = r8.getAnnotation(r2)
            com.google.gson.annotations.Until r2 = (com.google.gson.annotations.Until) r2
            if (r0 == 0) goto L25
            double r3 = r0.value()
            double r5 = r7.version
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 < 0) goto L32
        L25:
            if (r2 == 0) goto L33
            double r2 = r2.value()
            double r4 = r7.version
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 >= 0) goto L32
            goto L33
        L32:
            return r1
        L33:
            boolean r7 = r7.serializeInnerClasses
            if (r7 != 0) goto L47
            boolean r7 = r8.isMemberClass()
            if (r7 == 0) goto L47
            int r7 = r8.getModifiers()
            r7 = r7 & 8
            if (r7 == 0) goto L46
            goto L47
        L46:
            return r1
        L47:
            boolean r7 = isAnonymousOrNonStaticLocal(r8)
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.Excluder.excludeClassChecks(java.lang.Class):boolean");
    }

    public final void excludeClassInStrategy(boolean z) {
        Iterator it =
                (z ? this.serializationStrategies : this.deserializationStrategies).iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final Excluder m1066clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
