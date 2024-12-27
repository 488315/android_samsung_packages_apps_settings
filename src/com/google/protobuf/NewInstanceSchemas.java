package com.google.protobuf;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class NewInstanceSchemas {
    public static final NewInstanceSchemaLite FULL_SCHEMA;
    public static final NewInstanceSchemaLite LITE_SCHEMA;

    static {
        NewInstanceSchemaLite newInstanceSchemaLite = null;
        try {
            Class[] clsArr = new Class[0];
            newInstanceSchemaLite =
                    (NewInstanceSchemaLite)
                            Class.forName("com.google.protobuf.NewInstanceSchemaFull")
                                    .getDeclaredConstructor(null)
                                    .newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = newInstanceSchemaLite;
        LITE_SCHEMA = new NewInstanceSchemaLite();
    }
}
