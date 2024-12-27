package com.android.settings;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.NetworkTemplate;
import android.net.Uri;
import android.os.IBinder;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.settings.applications.ProcStatsData;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.network.MobileNetworkRepository;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.net.DataUsageController;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.settings.ImsProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SettingsDumpService extends Service {
    static final Intent BROWSER_INTENT =
            new Intent("android.intent.action.VIEW", Uri.parse("http://"));
    static final String KEY_ANOMALY_DETECTION = "anomaly_detection";
    static final String KEY_DATAUSAGE = "datausage";
    static final String KEY_DEFAULT_BROWSER_APP = "default_browser_app";
    static final String KEY_MEMORY = "memory";
    static final String KEY_SERVICE = "service";
    static final String KEY_STORAGE = "storage";
    public boolean mShouldShowNetworkDump = false;

    @Override // android.app.Service
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        if (!this.mShouldShowNetworkDump) {
            JSONObject jSONObject = new JSONObject();
            indentingPrintWriter.println("SettingsDumpService: ");
            indentingPrintWriter.increaseIndent();
            try {
                jSONObject.put(KEY_SERVICE, "Settings State");
                jSONObject.put(KEY_STORAGE, dumpStorage());
                jSONObject.put(KEY_DATAUSAGE, dumpDataUsage());
                jSONObject.put(KEY_MEMORY, dumpMemory());
                jSONObject.put(KEY_DEFAULT_BROWSER_APP, dumpDefaultBrowser());
            } catch (Exception e) {
                Log.w("SettingsDumpService", "exception in dump: ", e);
            }
            indentingPrintWriter.println(jSONObject);
            indentingPrintWriter.flush();
            indentingPrintWriter.decreaseIndent();
            return;
        }
        MobileNetworkRepository mobileNetworkRepository = MobileNetworkRepository.getInstance(this);
        mobileNetworkRepository.getClass();
        indentingPrintWriter.println("MobileNetworkRepository: ");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(
                " availableSubInfoEntityList= "
                        + mobileNetworkRepository.mAvailableSubInfoEntityList);
        indentingPrintWriter.println(
                " activeSubInfoEntityList=" + mobileNetworkRepository.mActiveSubInfoEntityList);
        indentingPrintWriter.println(
                " mobileNetworkInfoEntityList= "
                        + mobileNetworkRepository.mMobileNetworkInfoEntityList);
        indentingPrintWriter.println(
                " uiccInfoEntityList= " + mobileNetworkRepository.mUiccInfoEntityList);
        indentingPrintWriter.println(
                " CacheSubscriptionInfoEntityMap= "
                        + MobileNetworkRepository.sCacheSubscriptionInfoEntityMap);
        indentingPrintWriter.println(
                " SubscriptionInfoMap= " + mobileNetworkRepository.mSubscriptionInfoMap);
        indentingPrintWriter.flush();
        indentingPrintWriter.decreaseIndent();
    }

    public final JSONObject dumpDataUsage() {
        JSONObject jSONObject = new JSONObject();
        DataUsageController dataUsageController = new DataUsageController(this);
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) getSystemService(SubscriptionManager.class);
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(TelephonyManager.class);
        PackageManager packageManager = getPackageManager();
        if (telephonyManager.isDataCapable()) {
            JSONArray jSONArray = new JSONArray();
            for (SubscriptionInfo subscriptionInfo :
                    subscriptionManager.getAvailableSubscriptionInfoList()) {
                JSONObject dumpDataUsage =
                        dumpDataUsage(
                                DataUsageLib.getMobileTemplateForSubId(
                                        telephonyManager, subscriptionInfo.getSubscriptionId()),
                                dataUsageController);
                dumpDataUsage.put("subId", subscriptionInfo.getSubscriptionId());
                jSONArray.put(dumpDataUsage);
            }
            jSONObject.put("cell", jSONArray);
        }
        if (packageManager.hasSystemFeature("android.hardware.wifi")) {
            jSONObject.put(
                    ImsProfile.PDN_WIFI,
                    dumpDataUsage(new NetworkTemplate.Builder(4).build(), dataUsageController));
        }
        if (packageManager.hasSystemFeature("android.hardware.ethernet")) {
            jSONObject.put(
                    "ethernet",
                    dumpDataUsage(new NetworkTemplate.Builder(5).build(), dataUsageController));
        }
        return jSONObject;
    }

    public String dumpDefaultBrowser() {
        ResolveInfo resolveActivity = getPackageManager().resolveActivity(BROWSER_INTENT, 65536);
        if (resolveActivity == null
                || resolveActivity.activityInfo.packageName.equals(
                        RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME)) {
            return null;
        }
        return resolveActivity.activityInfo.packageName;
    }

    public final JSONObject dumpMemory() {
        JSONObject jSONObject = new JSONObject();
        int i = 0;
        ProcStatsData procStatsData = new ProcStatsData(this, false);
        procStatsData.refreshStats(true);
        ProcStatsData.MemInfo memInfo = procStatsData.mMemInfo;
        jSONObject.put("used", String.valueOf(memInfo.realUsedRam));
        jSONObject.put("free", String.valueOf(memInfo.realFreeRam));
        jSONObject.put("total", String.valueOf(memInfo.realTotalRam));
        int i2 = procStatsData.mStats.mMemFactor;
        if (i2 != -1) {
            if (i2 >= 4) {
                i2 -= 4;
            }
            i = i2;
        }
        jSONObject.put("state", i);
        return jSONObject;
    }

    public final JSONObject dumpStorage() {
        JSONObject jSONObject = new JSONObject();
        for (VolumeInfo volumeInfo :
                ((StorageManager) getSystemService(StorageManager.class)).getVolumes()) {
            JSONObject jSONObject2 = new JSONObject();
            if (volumeInfo.isMountedReadable()) {
                File path = volumeInfo.getPath();
                jSONObject2.put("used", String.valueOf(path.getTotalSpace() - path.getFreeSpace()));
                jSONObject2.put("total", String.valueOf(path.getTotalSpace()));
            }
            jSONObject2.put("path", volumeInfo.getInternalPath());
            jSONObject2.put("state", volumeInfo.getState());
            jSONObject2.put("stateDesc", volumeInfo.getStateDescription());
            jSONObject2.put("description", volumeInfo.getDescription());
            jSONObject.put(volumeInfo.getId(), jSONObject2);
        }
        return jSONObject;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 3;
        }
        this.mShouldShowNetworkDump = intent.getBooleanExtra("show_network_dump", false);
        return 3;
    }

    public static JSONObject dumpDataUsage(
            NetworkTemplate networkTemplate, DataUsageController dataUsageController) {
        JSONObject jSONObject = new JSONObject();
        DataUsageController.DataUsageInfo dataUsageInfo =
                dataUsageController.getDataUsageInfo(networkTemplate);
        dataUsageInfo.getClass();
        jSONObject.put("carrier", (Object) null);
        jSONObject.put(NetworkAnalyticsConstants.DataPoints.OPEN_TIME, dataUsageInfo.startDate);
        jSONObject.put("usage", dataUsageInfo.usageLevel);
        jSONObject.put("warning", dataUsageInfo.warningLevel);
        jSONObject.put("limit", dataUsageInfo.limitLevel);
        return jSONObject;
    }
}
