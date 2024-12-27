package com.google.protobuf;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ExtensionSchemas {
    public static final ExtensionSchemaLite FULL_SCHEMA;
    public static final ExtensionSchemaLite LITE_SCHEMA = new ExtensionSchemaLite();

    static {
        ExtensionSchemaLite extensionSchemaLite = null;
        try {
            Class[] clsArr = new Class[0];
            extensionSchemaLite =
                    (ExtensionSchemaLite)
                            Class.forName("com.google.protobuf.ExtensionSchemaFull")
                                    .getDeclaredConstructor(null)
                                    .newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = extensionSchemaLite;
    }
}
