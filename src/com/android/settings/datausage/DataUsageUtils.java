package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkTemplate;
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.BidiFormatter;
import android.text.format.Formatter;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.SecSimFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;

import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DataUsageUtils {
    public static CharSequence formatDataUsage(Context context, long j) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Formatter.BytesResult formatBytes =
                featureFactoryImpl
                        .getDataUsageFeatureProvider()
                        .formatBytes(context.getResources(), j, 4);
        return BidiFormatter.getInstance()
                .unicodeWrap(
                        context.getString(
                                R.string.fileSizeSuffix,
                                ApnSettings.MVNO_NONE,
                                formatBytes.value,
                                formatBytes.units));
    }

    public static int getDefaultSubscriptionId(Context context) {
        if (Rune.isEnabledHidingByOpportunisticEsim(context)) {
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager)
                            context.getSystemService("telephony_subscription_service");
            int subscriptionId =
                    subscriptionManager
                                    .getActiveSubscriptionInfoForSimSlotIndex(0)
                                    .isOpportunistic()
                            ? subscriptionManager
                                    .getActiveSubscriptionInfoForSimSlotIndex(0)
                                    .getSubscriptionId()
                            : subscriptionManager
                                    .getActiveSubscriptionInfoForSimSlotIndex(1)
                                    .getSubscriptionId();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    subscriptionId, "opportunisticSubId: ", "DataUsageUtils");
            return subscriptionId;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            return defaultDataSubscriptionId;
        }
        ProxySubscriptionManager proxySubscriptionManager =
                ProxySubscriptionManager.getInstance(context);
        List activeSubscriptionsInfo =
                proxySubscriptionManager.mSubscriptionMonitor.getActiveSubscriptionsInfo();
        if (activeSubscriptionsInfo == null || activeSubscriptionsInfo.size() <= 0) {
            activeSubscriptionsInfo =
                    proxySubscriptionManager
                            .mSubscriptionMonitor
                            .getSubscriptionManager()
                            .getAvailableSubscriptionInfoList();
        }
        if (activeSubscriptionsInfo == null || activeSubscriptionsInfo.size() <= 0) {
            return -1;
        }
        return ((SubscriptionInfo) activeSubscriptionsInfo.get(0)).getSubscriptionId();
    }

    public static NetworkTemplate getDefaultTemplate(Context context, int i) {
        return (SubscriptionManager.isValidSubscriptionId(i) && hasMobileData(context))
                ? DataUsageLib.getMobileTemplate(context, i)
                : hasWifiRadio(context)
                        ? new NetworkTemplate.Builder(4).build()
                        : new NetworkTemplate.Builder(5).build();
    }

    public static Optional getMobileNetworkTemplateFromSubId(Context context, Intent intent) {
        if (intent == null || !intent.hasExtra("android.provider.extra.SUB_ID")) {
            return Optional.empty();
        }
        int intExtra = intent.getIntExtra("android.provider.extra.SUB_ID", -1);
        return (SubscriptionManager.isValidSubscriptionId(intExtra) && hasMobileData(context))
                ? Optional.of(DataUsageLib.getMobileTemplate(context, intExtra))
                : Optional.empty();
    }

    public static String getSimName(Context context, int i) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProvider secSimFeatureProvider = featureFactoryImpl.getSecSimFeatureProvider();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("CTC".equals(Utils.getSalesCode())) {
            String telephonyProperty =
                    ConnectionsUtils.getTelephonyProperty(0, "gsm.sim.state", "UNKNOWN");
            String telephonyProperty2 =
                    ConnectionsUtils.getTelephonyProperty(1, "gsm.sim.state", "UNKNOWN");
            if (!"READY".equals(telephonyProperty) || !"READY".equals(telephonyProperty2)) {
                return context.getResources()
                        .getString(i == 0 ? R.string.slot1_tab : R.string.slot2_tab);
            }
        }
        return ((SecSimFeatureProviderImpl) secSimFeatureProvider).getSimSlotName(i);
    }

    public static boolean hasActiveSim(Context context) {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                ((SubscriptionManager) context.getSystemService("telephony_subscription_service"))
                        .getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
            Log.i("DataUsageUtils", "has Sim True");
            return true;
        }
        int simState =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class)).getSimState();
        MainClearConfirm$$ExternalSyntheticOutline0.m(simState, "simState : ", "DataUsageUtils");
        return (simState == 1 || simState == 0 || simState == 6) ? false : true;
    }

    public static boolean hasMobile(Context context) {
        boolean hasMobileData = hasMobileData(context);
        boolean hasActiveSim = hasActiveSim(context);
        Log.i(
                "DataUsageUtils",
                "hasMobileData : " + hasMobileData + ", hasSimCard : " + hasActiveSim);
        return hasMobileData && hasActiveSim;
    }

    public static boolean hasMobileData(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        Log.i("DataUsageUtils", "hasMobileData : " + telephonyManager.isDataCapable());
        return telephonyManager.isDataCapable();
    }

    public static boolean hasWifiRadio(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            Log.i(
                    "DataUsageUtils",
                    "hasWifiRadio" + packageManager.hasSystemFeature("android.hardware.wifi"));
        }
        return packageManager != null && packageManager.hasSystemFeature("android.hardware.wifi");
    }

    public static boolean isDataAllowed(Context context) {
        SubscriptionInfo activeSubscriptionInfo =
                ((SubscriptionManager) context.getSystemService("telephony_subscription_service"))
                        .getActiveSubscriptionInfo(
                                SubscriptionManager.getDefaultDataSubscriptionId());
        if (activeSubscriptionInfo == null) {
            return true;
        }
        int simSlotIndex = activeSubscriptionInfo.getSimSlotIndex();
        Log.d("DataUsageUtils", "Restriction in Settings Mobile Data On");
        EnterpriseDeviceManager enterpriseDeviceManager =
                EnterpriseDeviceManager.getInstance(context);
        try {
            Log.d("DataUsageUtils", "isDataAllowedFromSimSlot(slotId)");
            if (enterpriseDeviceManager != null) {
                return enterpriseDeviceManager
                        .getPhoneRestrictionPolicy()
                        .isDataAllowedFromSimSlot(simSlotIndex);
            }
            return true;
        } catch (SecurityException e) {
            Log.w("DataUsageUtils", "SecurityException: " + e);
            return true;
        }
    }

    public static boolean isDataRoaming(int i) {
        if (i == -1) {
            return false;
        }
        Log.i("DataUsageUtils", "isRoaming : " + TelephonyManager.getDefault().isNetworkRoaming(i));
        return TelephonyManager.getDefault().isNetworkRoaming(i);
    }

    public static boolean isSupportATTMobileDataAndRoaming() {
        String str = SystemProperties.get("ro.csc.sales_code");
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z =
                "ATT".equalsIgnoreCase(str)
                        || "AIO".equalsIgnoreCase(str)
                        || "APP".equalsIgnoreCase(str)
                        || "DSA".equalsIgnoreCase(str)
                        || "XAR".equalsIgnoreCase(str);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isSupportATTMobileDataAndRoaming : ", "DataUsageUtils", z);
        return z;
    }
}
