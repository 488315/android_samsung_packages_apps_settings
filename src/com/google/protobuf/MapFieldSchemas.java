package com.google.protobuf;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MapFieldSchemas {
    public static final MapFieldSchemaLite FULL_SCHEMA;
    public static final MapFieldSchemaLite LITE_SCHEMA;

    static {
        MapFieldSchemaLite mapFieldSchemaLite = null;
        try {
            Class[] clsArr = new Class[0];
            mapFieldSchemaLite =
                    (MapFieldSchemaLite)
                            Class.forName("com.google.protobuf.MapFieldSchemaFull")
                                    .getDeclaredConstructor(null)
                                    .newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = mapFieldSchemaLite;
        LITE_SCHEMA = new MapFieldSchemaLite();
    }
}
