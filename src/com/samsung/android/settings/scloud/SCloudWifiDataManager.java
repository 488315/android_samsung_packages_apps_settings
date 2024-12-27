package com.samsung.android.settings.scloud;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;
import com.samsung.android.scloud.lib.setting.SyncSettingProviderClient;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.sec.ims.settings.ImsProfile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SCloudWifiDataManager {
    public static SCloudWifiDataManager sInstance;
    public final Context mContext;
    public HashMap mSavedConfigs;
    public final SharedPreferences mSharedPreference;
    public final WifiManager mWifiManager;
    public SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2;
    public final Handler syncHandler =
            new Handler(
                    Looper.getMainLooper(),
                    new Handler
                            .Callback() { // from class:
                                          // com.samsung.android.settings.scloud.SCloudWifiDataManager.2
                        /* JADX WARN: Type inference failed for: r3v3, types: [com.samsung.android.scloud.lib.setting.SyncSettingProviderClient$1] */
                        @Override // android.os.Handler.Callback
                        public final boolean handleMessage(Message message) {
                            int i = message.what;
                            if (i == 1) {
                                SCloudWifiDataManager.this.syncHandler.removeMessages(1);
                                SCloudWifiDataManager.this.syncHandler.removeMessages(2);
                                SCloudWifiDataManager.this.syncHandler.removeMessages(3);
                                SCloudWifiDataManager sCloudWifiDataManager =
                                        SCloudWifiDataManager.this;
                                Log.i(
                                        "WifiSettings.SCloudMgr",
                                        "makeSamsungCloudRPCSettingV2 : "
                                                + sCloudWifiDataManager.samsungCloudRPCSettingV2);
                                if (sCloudWifiDataManager.samsungCloudRPCSettingV2 == null) {
                                    SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2 =
                                            new SamsungCloudRPCSettingV2(
                                                    sCloudWifiDataManager.mContext,
                                                    "com.android.settings.wifiprofilesync",
                                                    "com.android.settings.wifiprofilesetting");
                                    sCloudWifiDataManager.samsungCloudRPCSettingV2 =
                                            samsungCloudRPCSettingV2;
                                    final AnonymousClass1 anonymousClass1 =
                                            sCloudWifiDataManager.new AnonymousClass1();
                                    SyncSettingProviderClient syncSettingProviderClient =
                                            samsungCloudRPCSettingV2.iSyncSetting;
                                    if (syncSettingProviderClient != null
                                            && syncSettingProviderClient.syncStatusObserver
                                                    == null) {
                                        syncSettingProviderClient.syncStatusObserver =
                                                new ContentObserver(
                                                        new Handler(
                                                                Looper
                                                                        .getMainLooper())) { // from
                                                                                             // class: com.samsung.android.scloud.lib.setting.SyncSettingProviderClient.1
                                                    @Override // android.database.ContentObserver
                                                    public final void onChange(boolean z, Uri uri) {
                                                        try {
                                                            Log.i(
                                                                    SyncSettingProviderClient.TAG,
                                                                    "syncStatusObserver : "
                                                                            + uri.toString());
                                                            String str =
                                                                    uri.getPathSegments().get(0);
                                                            if ("complete".equals(str)) {
                                                                Bundle bundle = new Bundle();
                                                                bundle.putString(
                                                                        "rcode",
                                                                        uri.getPathSegments()
                                                                                .get(1));
                                                                anonymousClass1.onComplete(bundle);
                                                            } else if ("progress".equals(str)) {
                                                                anonymousClass1.getClass();
                                                                Log.i(
                                                                        "WifiSettings.SCloudMgr",
                                                                        "onProgress: ");
                                                            } else if (NetworkAnalyticsConstants
                                                                    .DataPoints.OPEN_TIME
                                                                    .equals(str)) {
                                                                anonymousClass1.getClass();
                                                                Log.i(
                                                                        "WifiSettings.SCloudMgr",
                                                                        "onStart: ");
                                                            } else if ("cancel".equals(str)) {
                                                                anonymousClass1.getClass();
                                                                Log.i(
                                                                        "WifiSettings.SCloudMgr",
                                                                        "onCancel: ");
                                                            }
                                                        } catch (Exception e) {
                                                            Log.e(
                                                                    SyncSettingProviderClient.TAG,
                                                                    e.getMessage());
                                                        }
                                                    }
                                                };
                                        syncSettingProviderClient
                                                .context
                                                .getContentResolver()
                                                .registerContentObserver(
                                                        Uri.parse(
                                                                "content://"
                                                                        + syncSettingProviderClient
                                                                                .settingProvider),
                                                        true,
                                                        syncSettingProviderClient
                                                                .syncStatusObserver);
                                    }
                                }
                                Handler handler = sCloudWifiDataManager.syncHandler;
                                handler.sendMessageDelayed(handler.obtainMessage(2), 100L);
                            } else if (i == 2) {
                                SyncSettingProviderClient syncSettingProviderClient2 =
                                        SCloudWifiDataManager.this
                                                .samsungCloudRPCSettingV2
                                                .iSyncSetting;
                                if (syncSettingProviderClient2 != null) {
                                    try {
                                        Log.i(SyncSettingProviderClient.TAG, "requestSync");
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                "authority", syncSettingProviderClient2.authority);
                                        bundle.putLong("library_version", 20170L);
                                        String str = syncSettingProviderClient2.settingProvider;
                                        if (str != null) {
                                            bundle.putString(
                                                    "callback_uri", "content://".concat(str));
                                        }
                                        syncSettingProviderClient2
                                                .context
                                                .getContentResolver()
                                                .call(
                                                        syncSettingProviderClient2.rpcUri,
                                                        "start_sync",
                                                        (String) null,
                                                        bundle);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                Handler handler2 = SCloudWifiDataManager.this.syncHandler;
                                handler2.sendMessageDelayed(handler2.obtainMessage(3), 10000L);
                            } else if (i != 3) {
                                Log.d("WifiSettings.SCloudMgr", "unhandled message: " + message);
                            } else {
                                Log.i("WifiSettings.SCloudMgr", "FAST_SYNC_FINISH");
                                if (SCloudWifiDataManager.this.syncHandler.hasMessages(1)
                                        || SCloudWifiDataManager.this.syncHandler.hasMessages(2)) {
                                    Log.w(
                                            "WifiSettings.SCloudMgr",
                                            "Pending job is exist. Do not close.");
                                    return false;
                                }
                                SamsungCloudRPCSettingV2 samsungCloudRPCSettingV22 =
                                        SCloudWifiDataManager.this.samsungCloudRPCSettingV2;
                                if (samsungCloudRPCSettingV22 != null) {
                                    SyncSettingProviderClient syncSettingProviderClient3 =
                                            samsungCloudRPCSettingV22.iSyncSetting;
                                    if (syncSettingProviderClient3 != null) {
                                        try {
                                            if (syncSettingProviderClient3.syncStatusObserver
                                                    != null) {
                                                syncSettingProviderClient3
                                                        .context
                                                        .getContentResolver()
                                                        .unregisterContentObserver(
                                                                syncSettingProviderClient3
                                                                        .syncStatusObserver);
                                            }
                                        } catch (Exception e2) {
                                            Log.e(SyncSettingProviderClient.TAG, e2.getMessage());
                                        }
                                    }
                                    SCloudWifiDataManager.this.samsungCloudRPCSettingV2 = null;
                                }
                                Log.d(
                                        "WifiSettings.SCloudMgr",
                                        "samsungCloudRPCSettingV2="
                                                + SCloudWifiDataManager.this
                                                        .samsungCloudRPCSettingV2);
                            }
                            return false;
                        }
                    });
    public final HashMap mUpdatedProfiles = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.scloud.SCloudWifiDataManager$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void onComplete(Bundle bundle) {
            Log.i("WifiSettings.SCloudMgr", "onComplete: " + bundle.getString("rcode"));
            SCloudWifiDataManager sCloudWifiDataManager = SCloudWifiDataManager.this;
            sCloudWifiDataManager.syncHandler.removeMessages(3);
            Handler handler = sCloudWifiDataManager.syncHandler;
            handler.sendMessageDelayed(handler.obtainMessage(3), 10000L);
        }
    }

    public SCloudWifiDataManager(Context context) {
        this.mContext = context;
        this.mSharedPreference = context.getSharedPreferences("SettingsSCloudWifi", 0);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    public static synchronized SCloudWifiDataManager getInstance(Context context) {
        SCloudWifiDataManager sCloudWifiDataManager;
        synchronized (SCloudWifiDataManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SCloudWifiDataManager(context);
                }
                sCloudWifiDataManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sCloudWifiDataManager;
    }

    public static int getSecurityType(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 4;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)
                || wifiConfiguration.allowedKeyManagement.get(4)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return 1;
        }
        if (!wifiConfiguration.allowedKeyManagement.get(0)) {
            return -1;
        }
        String[] strArr = wifiConfiguration.wepKeys;
        return (strArr == null || strArr[0] == null) ? 0 : 2;
    }

    public static boolean validateConfig(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration == null) {
            return false;
        }
        String str = wifiConfiguration.SSID;
        if (TextUtils.isEmpty(str)) {
            Log.e("WifiSettings.SCloudMgr", "empty SSID");
            return false;
        }
        if (!str.contains("\t")) {
            return (getSecurityType(wifiConfiguration) == -1
                            || wifiConfiguration.isEphemeral()
                            || wifiConfiguration.isPasspoint()
                            || wifiConfiguration.meteredHint
                            || wifiConfiguration.carrierId != -1)
                    ? false
                    : true;
        }
        Log.e("WifiSettings.SCloudMgr", "SSID has tab");
        return false;
    }

    public final void addOrUpdateNetwork(JSONObject jSONObject) {
        boolean z;
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        boolean z2 = true;
        try {
            wifiConfiguration.SSID = jSONObject.getString("s");
            int i = jSONObject.getInt("k");
            if (i == 1) {
                wifiConfiguration.setSecurityParams(6);
            } else if (i == 2) {
                wifiConfiguration.setSecurityParams(1);
                wifiConfiguration.wepKeys[0] = jSONObject.getString("p");
            } else if (i == 3) {
                wifiConfiguration.setSecurityParams(2);
                wifiConfiguration.preSharedKey = jSONObject.getString("p");
            } else if (i == 4) {
                wifiConfiguration.setSecurityParams(4);
                wifiConfiguration.preSharedKey = jSONObject.getString("p");
            }
            if (jSONObject.has("a")) {
                wifiConfiguration.allowAutojoin = jSONObject.getInt("a") == 1;
            }
            if (jSONObject.has("h")) {
                wifiConfiguration.hiddenSSID = jSONObject.getInt("h") == 1;
            }
            if (jSONObject.has("m")) {
                wifiConfiguration.macRandomizationSetting = jSONObject.getInt("m");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndUpdateSavedNetworks();
        WifiConfiguration wifiConfiguration2 =
                (WifiConfiguration) this.mSavedConfigs.get(wifiConfiguration.getKey());
        if (wifiConfiguration2 != null) {
            int securityType = getSecurityType(wifiConfiguration2);
            if (securityType != 2) {
                if ((securityType == 3 || securityType == 4)
                        && !TextUtils.equals(
                                wifiConfiguration2.preSharedKey, wifiConfiguration.preSharedKey)) {
                    wifiConfiguration2.preSharedKey = wifiConfiguration.preSharedKey;
                    z = true;
                }
                z = false;
            } else {
                if (!TextUtils.equals(
                        wifiConfiguration2.wepKeys[0], wifiConfiguration.wepKeys[0])) {
                    wifiConfiguration2.wepKeys[0] = wifiConfiguration.wepKeys[0];
                    z = true;
                }
                z = false;
            }
            boolean z3 = wifiConfiguration2.allowAutojoin;
            boolean z4 = wifiConfiguration.allowAutojoin;
            if (z3 != z4) {
                wifiConfiguration2.allowAutojoin = z4;
                z = true;
            }
            int i2 = wifiConfiguration2.macRandomizationSetting;
            int i3 = wifiConfiguration.macRandomizationSetting;
            if (i2 != i3) {
                wifiConfiguration2.macRandomizationSetting = i3;
            } else {
                z2 = z;
            }
            if (!z2) {
                wifiConfiguration2 = null;
            }
            wifiConfiguration = wifiConfiguration2;
        }
        if (wifiConfiguration != null) {
            Log.i("WifiSettings.SCloudMgr", "addorupdated : " + wifiConfiguration.getKey());
            int addNetwork = this.mWifiManager.addNetwork(wifiConfiguration);
            this.mWifiManager.allowAutojoin(addNetwork, wifiConfiguration.allowAutojoin);
            this.mWifiManager.enableNetwork(addNetwork, false);
        }
    }

    public final void addToSharedPreference(String str, Profile profile) {
        Set lastConfigKeys = getLastConfigKeys(str);
        HashSet hashSet = (HashSet) lastConfigKeys;
        hashSet.remove(profile);
        hashSet.add(profile);
        Log.v("WifiSettings.SCloudMgr", "append " + profile + " into " + str);
        setLastConfigKeys(str, lastConfigKeys);
    }

    public final void checkAndUpdateSavedNetworks() {
        if (this.mSavedConfigs == null) {
            this.mSavedConfigs = new HashMap();
            for (WifiConfiguration wifiConfiguration :
                    this.mWifiManager.getPrivilegedConfiguredNetworks()) {
                if (validateConfig(wifiConfiguration)) {
                    this.mSavedConfigs.put(wifiConfiguration.getKey(), wifiConfiguration);
                    Profile profile = new Profile(wifiConfiguration);
                    this.mUpdatedProfiles.put(profile.recordId, profile);
                }
            }
        }
    }

    public final Set getLastConfigKeys(String str) {
        int hashCode;
        String str2;
        String string = this.mSharedPreference.getString(str, ApnSettings.MVNO_NONE);
        HashSet hashSet = new HashSet();
        if (!TextUtils.isEmpty(string)) {
            for (String str3 : string.split("\n")) {
                try {
                    hashCode = str.hashCode();
                } catch (NumberFormatException unused) {
                    Log.d("WifiSettings.SCloudMgr", "NumberFormatException");
                } catch (IllegalArgumentException unused2) {
                    Log.d("WifiSettings.SCloudMgr", "IllegalArgumentException");
                }
                if (hashCode == -1653488582) {
                    str.equals("sCloudLastAddOrUpdatedWifiConfigKeys");
                } else if (hashCode == 958150003 && str.equals("sCloudLastRemovedWifiConfigKeys")) {
                    str2 = "delete";
                    hashSet.add(new Profile(str3, str2));
                }
                str2 = "normal";
                hashSet.add(new Profile(str3, str2));
            }
        }
        return hashSet;
    }

    public final void removeFromSharedPreference(String str, Profile profile) {
        Set lastConfigKeys = getLastConfigKeys(str);
        HashSet hashSet = (HashSet) lastConfigKeys;
        if (hashSet.contains(profile)) {
            hashSet.remove(profile);
            Log.v("WifiSettings.SCloudMgr", "remove " + profile + " from " + str);
            setLastConfigKeys(str, lastConfigKeys);
        }
    }

    public final void setLastConfigKeys(String str, Set set) {
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        boolean z = true;
        while (it.hasNext()) {
            Profile profile = (Profile) it.next();
            if (z) {
                z = false;
            } else {
                sb.append("\n");
            }
            sb.append(profile);
        }
        SharedPreferences.Editor edit = this.mSharedPreference.edit();
        edit.putString(str, sb.toString());
        edit.apply();
    }

    public final void syncToAddOrUpdated(WifiConfiguration wifiConfiguration) {
        if (validateConfig(wifiConfiguration)) {
            Log.d("WifiSettings.SCloudMgr", "syncToAddOrUpdate " + wifiConfiguration.getKey());
            Profile profile = new Profile(wifiConfiguration);
            if (getSecurityType(wifiConfiguration) == 4
                    || getSecurityType(wifiConfiguration) == 3) {
                wifiConfiguration.allowedKeyManagement.clear();
                wifiConfiguration.allowedKeyManagement.set(
                        getSecurityType(wifiConfiguration) == 3 ? 8 : 1);
                Profile profile2 = new Profile(wifiConfiguration);
                removeFromSharedPreference("sCloudLastRemovedWifiConfigKeys", profile2);
                addToSharedPreference("sCloudLastAddOrUpdatedWifiConfigKeys", profile2);
            }
            removeFromSharedPreference("sCloudLastRemovedWifiConfigKeys", profile);
            addToSharedPreference("sCloudLastAddOrUpdatedWifiConfigKeys", profile);
            Handler handler = this.syncHandler;
            handler.sendMessageDelayed(handler.obtainMessage(1), 500L);
        }
    }

    public final void syncToRemove(WifiConfiguration wifiConfiguration) {
        if (validateConfig(wifiConfiguration)) {
            Log.d("WifiSettings.SCloudMgr", "syncToRemove " + wifiConfiguration.getKey());
            Profile profile = new Profile(wifiConfiguration);
            if (getSecurityType(wifiConfiguration) == 4
                    || getSecurityType(wifiConfiguration) == 3) {
                wifiConfiguration.allowedKeyManagement.clear();
                wifiConfiguration.allowedKeyManagement.set(
                        getSecurityType(wifiConfiguration) == 3 ? 8 : 1);
                Profile profile2 = new Profile(wifiConfiguration);
                removeFromSharedPreference("sCloudLastAddOrUpdatedWifiConfigKeys", profile2);
                addToSharedPreference("sCloudLastRemovedWifiConfigKeys", profile2);
            }
            removeFromSharedPreference("sCloudLastAddOrUpdatedWifiConfigKeys", profile);
            addToSharedPreference("sCloudLastRemovedWifiConfigKeys", profile);
            Handler handler = this.syncHandler;
            handler.sendMessageDelayed(handler.obtainMessage(1), 500L);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Profile {
        public final String configKey;
        public final String recordId;
        public final String status;
        public final long timestamp;

        public Profile(String str, String str2) {
            String[] split = str.split("\t");
            if (split.length != 2) {
                throw new IllegalArgumentException("invalid text");
            }
            String str3 = split[0];
            this.configKey = str3;
            this.recordId = Header.makeRecordId(str3);
            this.timestamp = Long.parseLong(split[1]);
            this.status = str2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Profile)) {
                return false;
            }
            return TextUtils.equals(this.configKey, ((Profile) obj).configKey);
        }

        public final int hashCode() {
            return this.configKey.hashCode();
        }

        public final String toString() {
            return this.configKey + "\t" + this.timestamp;
        }

        public Profile(WifiConfiguration wifiConfiguration) {
            String key = wifiConfiguration.getKey();
            this.configKey = key;
            this.recordId = Header.makeRecordId(key);
            this.timestamp = System.currentTimeMillis();
            this.status = "normal";
        }
    }
}
