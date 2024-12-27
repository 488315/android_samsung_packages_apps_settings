package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter {
    public final Gson context;
    public final TypeAdapter delegate;
    public final Type type;

    public TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        return this.delegate.read(jsonReader);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:

       if ((r0 instanceof com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter) == false) goto L25;
    */
    @Override // com.google.gson.TypeAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void write(com.google.gson.stream.JsonWriter r4, java.lang.Object r5) {
        /*
            r3 = this;
            java.lang.reflect.Type r0 = r3.type
            if (r5 == 0) goto L10
            boolean r1 = r0 instanceof java.lang.Class
            if (r1 != 0) goto Lc
            boolean r1 = r0 instanceof java.lang.reflect.TypeVariable
            if (r1 == 0) goto L10
        Lc:
            java.lang.Class r0 = r5.getClass()
        L10:
            java.lang.reflect.Type r1 = r3.type
            com.google.gson.TypeAdapter r2 = r3.delegate
            if (r0 == r1) goto L3c
            com.google.gson.Gson r3 = r3.context
            com.google.gson.reflect.TypeToken r0 = com.google.gson.reflect.TypeToken.get(r0)
            com.google.gson.TypeAdapter r3 = r3.getAdapter(r0)
            boolean r0 = r3 instanceof com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
            if (r0 != 0) goto L25
            goto L3b
        L25:
            r0 = r2
        L26:
            boolean r1 = r0 instanceof com.google.gson.internal.bind.SerializationDelegatingTypeAdapter
            if (r1 == 0) goto L36
            r1 = r0
            com.google.gson.internal.bind.SerializationDelegatingTypeAdapter r1 = (com.google.gson.internal.bind.SerializationDelegatingTypeAdapter) r1
            com.google.gson.TypeAdapter r1 = r1.getSerializationDelegate()
            if (r1 != r0) goto L34
            goto L36
        L34:
            r0 = r1
            goto L26
        L36:
            boolean r0 = r0 instanceof com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
            if (r0 != 0) goto L3b
            goto L3c
        L3b:
            r2 = r3
        L3c:
            r2.write(r4, r5)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper.write(com.google.gson.stream.JsonWriter,"
                    + " java.lang.Object):void");
    }
}
