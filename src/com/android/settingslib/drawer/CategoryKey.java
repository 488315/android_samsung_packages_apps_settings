package com.android.settingslib.drawer;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CategoryKey {
    public static final Map KEY_COMPAT_MAP;

    static {
        HashMap hashMap = new HashMap();
        KEY_COMPAT_MAP = hashMap;
        hashMap.put(
                "com.android.settings.category.wireless",
                "com.android.settings.category.ia.wireless");
        hashMap.put(
                "com.android.settings.category.device", "com.android.settings.category.ia.system");
        hashMap.put(
                "com.android.settings.category.personal",
                "com.android.settings.category.ia.system");
        hashMap.put(
                "com.android.settings.category.system", "com.android.settings.category.ia.system");
    }
}
