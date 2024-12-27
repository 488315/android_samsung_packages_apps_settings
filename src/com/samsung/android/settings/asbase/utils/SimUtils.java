package com.samsung.android.settings.asbase.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SimUtils extends Utils {
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0108, code lost:

       if (r1 == 0) goto L7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x011b, code lost:

       if (r1 < r0) goto L7;
    */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0121 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getLogicalSlotIdByDisplayPosition(android.content.Context r16, int r17) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.utils.SimUtils.getLogicalSlotIdByDisplayPosition(android.content.Context,"
                    + " int):int");
    }

    public static String getSimName(Context context, int i) {
        String string;
        ContentResolver contentResolver = context.getContentResolver();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("CTC".equals(com.android.settings.Utils.getSalesCode())) {
            string =
                    i == 0
                            ? !"READY".equals(getTelephonyProperty(0))
                                    ? context.getResources().getString(R.string.sec_sim1)
                                    : Settings.Global.getString(contentResolver, "select_name_1")
                            : !"READY".equals(getTelephonyProperty(1))
                                    ? context.getResources().getString(R.string.sec_sim2)
                                    : Settings.Global.getString(contentResolver, "select_name_2");
        } else {
            string =
                    i == 0
                            ? Settings.Global.getString(contentResolver, "select_name_1")
                            : Settings.Global.getString(contentResolver, "select_name_2");
        }
        if (TextUtils.isEmpty(string)) {
            string =
                    i == 0
                            ? context.getString(R.string.sec_sim1)
                            : context.getString(R.string.sec_sim2);
        }
        return SoundUtils.isRTL(context, string)
                ? ComposerKt$$ExternalSyntheticOutline0.m("\u200f", string, "\u200f")
                : string;
    }

    public static int getSubId(Context context, int i) {
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                subscriptionManager != null
                        ? subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i)
                        : null;
        if (activeSubscriptionInfoForSimSlotIndex != null) {
            return activeSubscriptionInfoForSimSlotIndex.getSubscriptionId();
        }
        return -1;
    }

    public static String getTelephonyProperty(int i) {
        SystemProperties.get("ril.ICC_TYPE0");
        SystemProperties.get("ril.ICC_TYPE1");
        return TelephonyManager.getTelephonyProperty(i, "gsm.sim.state", "UNKNOWN");
    }

    public static boolean isEnabledSIM2Only() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider();
        return secSimFeatureProviderImpl.isMultiSimModel()
                && secSimFeatureProviderImpl.getFirstSlotIndex() > 0;
    }

    public static boolean isMultiSimEnabled(Context context) {
        if (!isVoiceCapable(context)) {
            return false;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider();
        if (!secSimFeatureProviderImpl.isMultiSimModel()) {
            return false;
        }
        int enabledSimCnt = secSimFeatureProviderImpl.getEnabledSimCnt();
        int i = 0;
        for (int i2 = 0; i2 < enabledSimCnt; i2++) {
            if (Rune.isEnabledHidingByOpportunisticEsim(context)) {
                Log.i("SimUtils", "Hide IMEI_Info for Opportunistic subscription");
            } else {
                i++;
            }
        }
        return i >= 2;
    }

    public static boolean isMultiSimModel() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                    .isMultiSimModel();
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    public static boolean isVoiceCapable(Context context) {
        boolean z =
                context.getResources()
                        .getBoolean(
                                Resources.getSystem()
                                        .getIdentifier(
                                                "config_voice_capable",
                                                "bool",
                                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("config_voice_capable = ", "SimUtils", z);
        return z || Rune.supportSoftphone();
    }
}
