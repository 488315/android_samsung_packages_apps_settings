package com.google.protobuf;

import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ExtensionRegistryLite {
    public static final ExtensionRegistryLite EMPTY_REGISTRY_LITE;
    public static volatile ExtensionRegistryLite emptyRegistry;

    static {
        ExtensionRegistryLite extensionRegistryLite = new ExtensionRegistryLite();
        Collections.emptyMap();
        EMPTY_REGISTRY_LITE = extensionRegistryLite;
    }
}
