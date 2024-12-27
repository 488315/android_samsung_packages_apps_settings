package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApClientConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApDailyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApProgressBarEntryConfig;
import com.samsung.android.wifi.SemWifiApClientDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApConnectedDeviceUtils {
    public static WifiApDailyStackedProgressBarEntryConfig getProgressBarEntryConfigForToday(
            Context context) {
        ArrayList arrayList = new ArrayList();
        List<SemWifiApClientDetails> topHotspotClientsToday =
                WifiApFrameworkUtils.getSemWifiManager(context).getTopHotspotClientsToday(3, 3);
        int i = 1;
        for (SemWifiApClientDetails semWifiApClientDetails : topHotspotClientsToday) {
            Log.i(
                    "WifiApConnectedDeviceUtils",
                    "getTopHotspotClientsToday: i: "
                            + i
                            + semWifiApClientDetails.toString()
                            + "\ngetClientDeviceName(): "
                            + semWifiApClientDetails.getClientDeviceName()
                            + "\ngetClientIpAddress(): "
                            + semWifiApClientDetails.getClientIpAddress()
                            + "\nisClientConnected(): "
                            + semWifiApClientDetails.isClientConnected()
                            + "\ngetClientDataLimit(): "
                            + semWifiApClientDetails.getClientDataLimit()
                            + "\ngetClientTimeLimit(): "
                            + semWifiApClientDetails.getClientTimeLimit()
                            + "\ngetClientActiveSessionMobileDataConsumed(): "
                            + semWifiApClientDetails.getClientActiveSessionMobileDataConsumed()
                            + "\ngetClientTodayTotalMobileDataUsage(): "
                            + semWifiApClientDetails.getClientTodayTotalMobileDataUsage()
                            + "\nisClientDataPausedByUser(): "
                            + semWifiApClientDetails.isClientDataPausedByUser()
                            + "\nisClientInternetPaused(): "
                            + semWifiApClientDetails.isClientInternetPaused()
                            + "\ngetClientTodayTotalTime(): "
                            + semWifiApClientDetails.getClientTodayTotalTime()
                            + "\ngetClientRecentConnectionTimeStamp(): "
                            + semWifiApClientDetails.getClientRecentConnectionTimeStamp());
            i++;
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (SemWifiApClientDetails semWifiApClientDetails2 : topHotspotClientsToday) {
            arrayList.add(
                    new WifiApProgressBarEntryConfig(
                            semWifiApClientDetails2.getClientTodayTotalMobileDataUsage(),
                            semWifiApClientDetails2.getClientDeviceName()));
        }
        WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig =
                new WifiApDailyStackedProgressBarEntryConfig(arrayList, currentTimeMillis);
        wifiApDailyStackedProgressBarEntryConfig.mTotalUsed = getWifiApTodayTotalDataUsage(context);
        return wifiApDailyStackedProgressBarEntryConfig;
    }

    public static List getTopHotspotClientConfigListToday(Context context, List list) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            Log.e(
                    "WifiApConnectedDeviceUtils",
                    "getTopHotspotClientConfigListToday semWifiApClientDetailsList null");
            return arrayList;
        }
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getTopHotspotClientConfigListToday semWifiApClientDetailsList Size:  "
                        + list.size());
        Iterator it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            SemWifiApClientDetails semWifiApClientDetails = (SemWifiApClientDetails) it.next();
            int i2 =
                    context.getResources()
                            .getIntArray(R.array.wifi_ap_top_10_progressbar_color_array)[i - 1];
            WifiApClientConfig wifiApClientConfig = new WifiApClientConfig(semWifiApClientDetails);
            wifiApClientConfig.mSemWifiApClientDetails = semWifiApClientDetails;
            String clientMacAddress = semWifiApClientDetails.getClientMacAddress();
            wifiApClientConfig.mMac = clientMacAddress;
            if (TextUtils.isEmpty(clientMacAddress)) {
                wifiApClientConfig.mDeviceName = context.getString(R.string.wifi_ap_other_devices);
                wifiApClientConfig.mProgressbarColorId =
                        context.getColor(R.color.wifi_ap_progressbar_color_others);
            } else {
                wifiApClientConfig.mDeviceName = semWifiApClientDetails.getClientDeviceName();
                wifiApClientConfig.mProgressbarColorId = i2;
            }
            wifiApClientConfig.mProgressbarOrderFromLeft = i;
            wifiApClientConfig.mIp = semWifiApClientDetails.getClientIpAddress();
            wifiApClientConfig.mDataUsageForTodayInBytes =
                    semWifiApClientDetails.getClientTodayTotalMobileDataUsage();
            wifiApClientConfig.mDataLimitForEachDayInBytes =
                    semWifiApClientDetails.getClientDataLimit();
            wifiApClientConfig.mIsDataPausedByDataLimitCondition =
                    semWifiApClientDetails.isClientDataPausedByDataLimit();
            wifiApClientConfig.mTimeUsageForTodayInMillis =
                    semWifiApClientDetails.getClientTodayTotalTime();
            wifiApClientConfig.mTimeLimitForEachDayInMillis =
                    semWifiApClientDetails.getClientTimeLimit();
            wifiApClientConfig.mIsDataPausedByTimeLimitCondition =
                    semWifiApClientDetails.isClientDataPauseByTimeLimit();
            wifiApClientConfig.mIsDataPausedByUserManually =
                    semWifiApClientDetails.isClientDataPausedByUser();
            wifiApClientConfig.mIsDeviceCurrentlyConnected =
                    semWifiApClientDetails.isClientConnected();
            wifiApClientConfig.mDeviceType = semWifiApClientDetails.getDeviceType();
            arrayList.add(wifiApClientConfig);
            Log.i(
                    "WifiApConnectedDeviceUtils",
                    "getTopHotspotClientsToday: i: "
                            + i
                            + ", wifiApClientConfig: "
                            + wifiApClientConfig.toString());
            i++;
        }
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getTopHotspotClientConfigListToday Size:  " + arrayList.size());
        return arrayList;
    }

    public static WifiApDataUsageConfig getWifiApActiveSessionDataLimit(Context context) {
        long wifiApDailyDataLimit =
                WifiApFrameworkUtils.getSemWifiManager(context).getWifiApDailyDataLimit();
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getWifiApActiveSessionDataLimit: " + wifiApDailyDataLimit);
        return new WifiApDataUsageConfig(
                System.currentTimeMillis(), System.currentTimeMillis(), wifiApDailyDataLimit);
    }

    public static WifiApDataUsageConfig getWifiApClientDataLimitDetail(
            Context context, String str) {
        return new WifiApDataUsageConfig(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                getWifiApClientDetails(context, str).getClientDataLimit());
    }

    public static SemWifiApClientDetails getWifiApClientDetails(Context context, String str) {
        Log.i("WifiApConnectedDeviceUtils", "getWifiApClientDetails requesting mac: " + str);
        SemWifiApClientDetails wifiApClientDetails =
                WifiApFrameworkUtils.getSemWifiManager(context).getWifiApClientDetails(str);
        if (wifiApClientDetails == null) {
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "getWifiApClientDetails is NULL: for mac", str, "WifiApConnectedDeviceUtils");
        } else {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "getWifiApClientDetails sent mac: ", str, " SemWifiApClientDetails: ");
            m.append(wifiApClientDetails.toString());
            Log.i("WifiApConnectedDeviceUtils", m.toString());
        }
        return wifiApClientDetails;
    }

    public static WifiApDataUsageConfig getWifiApClientMobileDataConsumedDetail(
            Context context, String str) {
        return new WifiApDataUsageConfig(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                getWifiApClientDetails(context, str).getClientTodayTotalMobileDataUsage());
    }

    public static long getWifiApClientSetTimeLimit(Context context, String str) {
        long clientTimeLimit = getWifiApClientDetails(context, str).getClientTimeLimit();
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getWifiApClientTimeLimit - mac: " + str + ", timeInMillis" + clientTimeLimit);
        return clientTimeLimit;
    }

    public static long getWifiApClientUsedTimeToday(Context context, String str) {
        long clientTodayTotalTime = getWifiApClientDetails(context, str).getClientTodayTotalTime();
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getWifiApClientTodayTotalTime - mac: "
                        + str
                        + ", timeInMillis"
                        + clientTodayTotalTime);
        return clientTodayTotalTime;
    }

    public static WifiApDataUsageConfig getWifiApTodayTotalDataUsage(Context context) {
        long wifiApTodaysTotalDataUsage =
                WifiApFrameworkUtils.getSemWifiManager(context).getWifiApTodaysTotalDataUsage();
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getWifiApTodayTotalDataUsage (Bytes): " + wifiApTodaysTotalDataUsage);
        return new WifiApDataUsageConfig(
                System.currentTimeMillis(), System.currentTimeMillis(), wifiApTodaysTotalDataUsage);
    }

    public static void setWifiApActiveSessionDataLimit(Context context, long j) {
        Log.i("WifiApConnectedDeviceUtils", "setWifiApActiveSessionDataLimit: " + j);
        WifiApFrameworkUtils.getSemWifiManager(context).setWifiApDailyDataLimit(j);
    }

    public static void setWifiApClientTimeLimit(Context context, String str, long j) {
        Log.i(
                "WifiApConnectedDeviceUtils",
                "setWifiApClientTimeLimit - mac: " + str + ", timeInMillis: " + j);
        WifiApFrameworkUtils.getSemWifiManager(context).setWifiApClientTimeLimit(str, j);
    }
}
