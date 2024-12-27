package com.samsung.android.settings.scloud;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.lib.storage.data.Body;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.samsung.android.scloud.lib.storage.data.RecordDataSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SCloudWifiConfigSyncImpl {
    public SCloudWifiDataManager mDataManager;

    public final void checkAndGetDataManager(Context context) {
        if (this.mDataManager == null) {
            this.mDataManager = SCloudWifiDataManager.getInstance(context.getApplicationContext());
        }
    }

    public final void finish(Context context, String str, Bundle bundle) {
        Log.i("WifiSettings.SCloudSync", "finish type: ".concat(str));
        checkAndGetDataManager(context);
        long j = bundle.getLong("last_sync_time", 0L);
        boolean z = bundle.getBoolean("is_success");
        String string = bundle.getString("error_code");
        if (!z) {
            Log.e("WifiSettings.SCloudSync", "sync fail reason : " + string);
            return;
        }
        SCloudWifiDataManager sCloudWifiDataManager = this.mDataManager;
        sCloudWifiDataManager.getClass();
        sCloudWifiDataManager.setLastConfigKeys(
                "sCloudLastRemovedWifiConfigKeys", Collections.emptySet());
        sCloudWifiDataManager.setLastConfigKeys(
                "sCloudLastAddOrUpdatedWifiConfigKeys", Collections.emptySet());
        sCloudWifiDataManager.mUpdatedProfiles.clear();
        sCloudWifiDataManager.mSavedConfigs = null;
        SharedPreferences.Editor edit = this.mDataManager.mSharedPreference.edit();
        edit.putLong("sCloudLastSyncTimeStamp", j);
        edit.apply();
    }

    public final List getData(Context context, List list) {
        String str;
        RecordDataSet recordDataSet;
        checkAndGetDataManager(context);
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            SCloudWifiDataManager sCloudWifiDataManager = this.mDataManager;
            SCloudWifiDataManager.Profile profile =
                    (SCloudWifiDataManager.Profile)
                            sCloudWifiDataManager.mUpdatedProfiles.get(str2);
            Log.i("WifiSettings.SCloudMgr", "getNetworkInfo " + profile);
            if (profile == null) {
                SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                        "network not found ", str2, " profile", "WifiSettings.SCloudMgr");
                recordDataSet = null;
            } else {
                WifiConfiguration wifiConfiguration =
                        (WifiConfiguration)
                                sCloudWifiDataManager.mSavedConfigs.get(profile.configKey);
                if (wifiConfiguration != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("s", wifiConfiguration.SSID);
                        int securityType = SCloudWifiDataManager.getSecurityType(wifiConfiguration);
                        jSONObject.put("k", securityType);
                        if (securityType == 2) {
                            jSONObject.put("p", wifiConfiguration.wepKeys[0]);
                        } else if (securityType == 3 || securityType == 4) {
                            jSONObject.put("p", wifiConfiguration.preSharedKey);
                        }
                        jSONObject.put("a", wifiConfiguration.allowAutojoin ? 1 : 0);
                        jSONObject.put("h", wifiConfiguration.hiddenSSID ? 1 : 0);
                        jSONObject.put("m", wifiConfiguration.macRandomizationSetting);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    str = jSONObject.toString();
                } else {
                    Log.e("WifiSettings.SCloudMgr", "network not found (maybe removed) " + profile);
                    str = ApnSettings.MVNO_NONE;
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("record_id", profile.recordId);
                    jSONObject2.put(PhoneRestrictionPolicy.TIMESTAMP, profile.timestamp);
                    jSONObject2.put("data", str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                Body body = new Body(jSONObject2, new ArrayList());
                String str3 = profile.status;
                String str4 = profile.recordId;
                recordDataSet =
                        new RecordDataSet(new Header(profile.timestamp, str4, str4, str3), body);
            }
            if (recordDataSet != null) {
                arrayList.add(recordDataSet);
            } else {
                Log.e("WifiSettings.SCloudSync", "getData failed, there is no info " + str2);
            }
        }
        return arrayList;
    }

    public final List getHeader(Context context, Bundle bundle) {
        checkAndGetDataManager(context);
        boolean z = bundle.getBoolean("need_cold_start", false);
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "getHeader needColdStart : ", "WifiSettings.SCloudSync", z);
        SCloudWifiDataManager sCloudWifiDataManager = this.mDataManager;
        sCloudWifiDataManager.getClass();
        ArrayList arrayList = new ArrayList();
        sCloudWifiDataManager.checkAndUpdateSavedNetworks();
        if (z) {
            Iterator it =
                    ((HashSet)
                                    sCloudWifiDataManager.getLastConfigKeys(
                                            "sCloudLastAddOrUpdatedWifiConfigKeys"))
                            .iterator();
            while (it.hasNext()) {
                SCloudWifiDataManager.Profile profile = (SCloudWifiDataManager.Profile) it.next();
                sCloudWifiDataManager.mUpdatedProfiles.put(profile.recordId, profile);
            }
            for (SCloudWifiDataManager.Profile profile2 :
                    sCloudWifiDataManager.mUpdatedProfiles.values()) {
                long j = profile2.timestamp;
                String str = profile2.status;
                String str2 = profile2.recordId;
                arrayList.add(new Header(j, str2, str2, str));
            }
        } else {
            Iterator it2 =
                    ((HashSet)
                                    sCloudWifiDataManager.getLastConfigKeys(
                                            "sCloudLastRemovedWifiConfigKeys"))
                            .iterator();
            while (it2.hasNext()) {
                SCloudWifiDataManager.Profile profile3 = (SCloudWifiDataManager.Profile) it2.next();
                sCloudWifiDataManager.mUpdatedProfiles.put(profile3.recordId, profile3);
                String str3 = profile3.status;
                String str4 = profile3.recordId;
                arrayList.add(new Header(profile3.timestamp, str4, str4, str3));
            }
            Iterator it3 =
                    ((HashSet)
                                    sCloudWifiDataManager.getLastConfigKeys(
                                            "sCloudLastAddOrUpdatedWifiConfigKeys"))
                            .iterator();
            while (it3.hasNext()) {
                SCloudWifiDataManager.Profile profile4 = (SCloudWifiDataManager.Profile) it3.next();
                sCloudWifiDataManager.mUpdatedProfiles.put(profile4.recordId, profile4);
                String str5 = profile4.status;
                String str6 = profile4.recordId;
                arrayList.add(new Header(profile4.timestamp, str6, str6, str5));
            }
        }
        return arrayList;
    }

    public final Bundle prepare(Context context, String str) {
        Log.i("WifiSettings.SCloudSync", "prepare type: ".concat(str));
        checkAndGetDataManager(context);
        Bundle bundle = new Bundle();
        bundle.putLong(
                "last_sync_time",
                this.mDataManager.mSharedPreference.getLong("sCloudLastSyncTimeStamp", 0L));
        this.mDataManager.getClass();
        bundle.putString("data_version", "1.0");
        return bundle;
    }

    public final void setData(Context context, List list) {
        RecordDataSet recordDataSet;
        SCloudWifiDataManager sCloudWifiDataManager;
        SCloudWifiDataManager.Profile profile;
        WifiConfiguration wifiConfiguration;
        checkAndGetDataManager(context);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            recordDataSet = (RecordDataSet) it.next();
            sCloudWifiDataManager = this.mDataManager;
            sCloudWifiDataManager.getClass();
            Header header = recordDataSet.header;
            profile =
                    (SCloudWifiDataManager.Profile)
                            sCloudWifiDataManager.mUpdatedProfiles.get(header.recordId);
            Log.i("WifiSettings.SCloudMgr", "syncNetwork " + profile);
            String str = header.statue;
            str.getClass();
            switch (str) {
                case "delete":
                    Log.i("WifiSettings.SCloudMgr", "removed : " + profile);
                    if (profile != null
                            && (wifiConfiguration =
                                            (WifiConfiguration)
                                                    sCloudWifiDataManager.mSavedConfigs.get(
                                                            profile.configKey))
                                    != null) {
                        sCloudWifiDataManager.mWifiManager.removeNetwork(
                                wifiConfiguration.networkId);
                        break;
                    }
                    break;
                case "uploadComplete":
                    Log.i("WifiSettings.SCloudMgr", "sync upload completed network " + profile);
                    break;
                case "normal":
                    try {
                        String string = recordDataSet.body.itemData.getString("data");
                        if (!TextUtils.isEmpty(string)) {
                            sCloudWifiDataManager.addOrUpdateNetwork(new JSONObject(string));
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
            }
        }
    }
}
