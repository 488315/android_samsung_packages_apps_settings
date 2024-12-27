package com.android.settings.fuelgauge.datasaver;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkPolicyManager;
import android.util.ArraySet;
import android.util.Log;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.Arrays;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DynamicDenylistManager {
    static final String PREF_KEY_MANUAL_DENYLIST_SYNCED = "manual_denylist_synced";
    public static DynamicDenylistManager sInstance;
    public final Context mContext;
    public final Object mLock = new Object();
    public final NetworkPolicyManager mNetworkPolicyManager;

    public DynamicDenylistManager(Context context, NetworkPolicyManager networkPolicyManager) {
        this.mContext = context.getApplicationContext();
        this.mNetworkPolicyManager = networkPolicyManager;
        if (getManualDenylistPref().contains(PREF_KEY_MANUAL_DENYLIST_SYNCED)) {
            Log.i("DynamicDenylistManager", "syncPolicyIfNeeded() ignore synced manual denylist");
            return;
        }
        SharedPreferences.Editor edit = getManualDenylistPref().edit();
        int[] uidsWithPolicy = networkPolicyManager.getUidsWithPolicy(1);
        if (uidsWithPolicy != null && uidsWithPolicy.length != 0) {
            for (int i : uidsWithPolicy) {
                edit.putInt(String.valueOf(i), 1);
            }
        }
        edit.putInt(PREF_KEY_MANUAL_DENYLIST_SYNCED, 0).apply();
    }

    public static Set getDenylistAllUids(SharedPreferences sharedPreferences) {
        ArraySet arraySet = new ArraySet();
        for (String str : sharedPreferences.getAll().keySet()) {
            if (!PREF_KEY_MANUAL_DENYLIST_SYNCED.equals(str)) {
                try {
                    arraySet.add(Integer.valueOf(Integer.parseInt(str)));
                } catch (NumberFormatException unused) {
                    Log.e(
                            "DynamicDenylistManager",
                            "getDenylistAllUids() unexpected format for " + str);
                }
            }
        }
        return arraySet;
    }

    public static DynamicDenylistManager getInstance(Context context) {
        DynamicDenylistManager dynamicDenylistManager;
        synchronized (DynamicDenylistManager.class) {
            try {
                if (sInstance == null) {
                    sInstance =
                            new DynamicDenylistManager(context, NetworkPolicyManager.from(context));
                }
                dynamicDenylistManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dynamicDenylistManager;
    }

    public SharedPreferences getDynamicDenylistPref() {
        return this.mContext.getSharedPreferences("dynamic_denylist_preference", 0);
    }

    public SharedPreferences getManualDenylistPref() {
        return this.mContext.getSharedPreferences("manual_denylist_preference", 0);
    }

    public final void resetDenylistIfNeeded(String str, boolean z) {
        if (!z && !KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)) {
            Log.w("DynamicDenylistManager", "resetDenylistIfNeeded: invalid conditions");
            return;
        }
        synchronized (this.mLock) {
            try {
                int[] uidsWithPolicy = this.mNetworkPolicyManager.getUidsWithPolicy(1);
                if (uidsWithPolicy == null || uidsWithPolicy.length == 0) {
                    Log.w(
                            "DynamicDenylistManager",
                            "resetDenylistIfNeeded: there is no valid UIDs");
                } else {
                    Log.i(
                            "DynamicDenylistManager",
                            "resetDenylistIfNeeded: " + Arrays.toString(uidsWithPolicy));
                    int length = uidsWithPolicy.length;
                    for (int i = 0; i < length; i++) {
                        int i2 = uidsWithPolicy[i];
                        if (!((ArraySet) getDenylistAllUids(getManualDenylistPref()))
                                .contains(Integer.valueOf(i2))) {
                            this.mNetworkPolicyManager.setUidPolicy(i2, 0);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Log.i("DynamicDenylistManager", "clearSharedPreferences()");
        getManualDenylistPref().edit().clear().apply();
        getDynamicDenylistPref().edit().clear().apply();
    }

    public final void setUidPolicyLocked(int i, int i2) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "setUidPolicyLocked: uid=", " policy=", i, i2, "DynamicDenylistManager");
        synchronized (this.mLock) {
            this.mNetworkPolicyManager.setUidPolicy(i, i2);
        }
        String valueOf = String.valueOf(i);
        if (i2 != 1) {
            getManualDenylistPref().edit().remove(valueOf).apply();
        } else {
            getManualDenylistPref().edit().putInt(valueOf, i2).apply();
        }
        getDynamicDenylistPref().edit().remove(valueOf).apply();
    }
}
