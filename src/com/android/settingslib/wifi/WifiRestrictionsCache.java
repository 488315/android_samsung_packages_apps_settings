package com.android.settingslib.wifi;

import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiRestrictionsCache {
    protected static final SparseArray<WifiRestrictionsCache> sInstances = new SparseArray<>();
    protected final Map<String, Boolean> mRestrictions = new HashMap();
    protected UserManager mUserManager;
    protected Bundle mUserRestrictions;

    public WifiRestrictionsCache(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        if (userManager != null) {
            this.mUserRestrictions = userManager.getUserRestrictions();
        }
    }

    public static WifiRestrictionsCache getInstance(Context context) {
        int userId = context.getUserId();
        SparseArray<WifiRestrictionsCache> sparseArray = sInstances;
        synchronized (sparseArray) {
            try {
                if (sparseArray.indexOfKey(userId) >= 0) {
                    return sparseArray.get(userId);
                }
                WifiRestrictionsCache wifiRestrictionsCache = new WifiRestrictionsCache(context);
                sparseArray.put(context.getUserId(), wifiRestrictionsCache);
                return wifiRestrictionsCache;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Boolean isConfigWifiAllowed() {
        Boolean bool;
        if (this.mUserRestrictions == null) {
            bool = Boolean.FALSE;
        } else {
            synchronized (this.mRestrictions) {
                try {
                    if (this.mRestrictions.containsKey("no_config_wifi")) {
                        bool = this.mRestrictions.get("no_config_wifi");
                    } else {
                        Boolean valueOf =
                                Boolean.valueOf(
                                        this.mUserRestrictions.getBoolean("no_config_wifi"));
                        this.mRestrictions.put("no_config_wifi", valueOf);
                        bool = valueOf;
                    }
                } finally {
                }
            }
        }
        return Boolean.valueOf(!bool.booleanValue());
    }
}
