package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccessibilitySettingsContentObserver extends ContentObserver {
    public final Map mUriToKey;
    public final Map mUrisToCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ContentObserverCallback {
        void onChange(String str);
    }

    public AccessibilitySettingsContentObserver(Handler handler) {
        super(handler);
        HashMap hashMap = new HashMap(2);
        this.mUriToKey = hashMap;
        this.mUrisToCallback = new HashMap();
        hashMap.put(Settings.Secure.getUriFor("accessibility_enabled"), "accessibility_enabled");
        hashMap.put(
                Settings.Secure.getUriFor("enabled_accessibility_services"),
                "enabled_accessibility_services");
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        String str = (String) ((HashMap) this.mUriToKey).get(uri);
        if (str == null) {
            Log.w(
                    "AccessibilitySettingsContentObserver",
                    "AccessibilitySettingsContentObserver can not find the key for uri: " + uri);
            return;
        }
        for (List list : ((HashMap) this.mUrisToCallback).keySet()) {
            if ("accessibility_enabled".equals(str)
                    || "enabled_accessibility_services".equals(str)
                    || list.contains(str)) {
                ((ContentObserverCallback) ((HashMap) this.mUrisToCallback).get(list))
                        .onChange(str);
            }
        }
    }

    public final void register(ContentResolver contentResolver) {
        Iterator it = ((HashMap) this.mUriToKey).keySet().iterator();
        while (it.hasNext()) {
            contentResolver.registerContentObserver((Uri) it.next(), false, this);
        }
    }

    public final void registerKeysToObserverCallback(
            List list, ContentObserverCallback contentObserverCallback) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ((HashMap) this.mUriToKey).put(Settings.Secure.getUriFor(str), str);
        }
        ((HashMap) this.mUrisToCallback).put(list, contentObserverCallback);
    }
}
