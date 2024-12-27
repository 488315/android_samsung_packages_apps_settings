package com.android.settings.homepage;

import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HighlightableMenu {
    public static boolean sXmlParsed;
    public static final Map MENU_TO_PREFERENCE_KEY_MAP = new ArrayMap();
    public static final Map MENU_KEY_COMPAT_MAP = new ArrayMap();

    public static synchronized void addMenuKey(String str) {
        synchronized (HighlightableMenu.class) {
            Log.d("HighlightableMenu", "add menu key: " + str);
            ((ArrayMap) MENU_TO_PREFERENCE_KEY_MAP).put(str, str);
        }
    }
}
