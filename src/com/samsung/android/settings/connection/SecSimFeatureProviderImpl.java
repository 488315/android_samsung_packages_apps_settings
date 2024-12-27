package com.samsung.android.settings.connection;

import android.content.Context;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.Utils;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSimFeatureProviderImpl implements SecSimFeatureProvider {
    public Context mContext;

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

    public final int getEnabledSimCnt() {
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1;
        boolean z2 =
                Settings.Global.getInt(this.mContext.getContentResolver(), "phone2_on", 1) == 1;
        String telephonyProperty =
                getTelephonyProperty(0, "ril.ICC_TYPE0", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        String telephonyProperty2 =
                getTelephonyProperty(1, "ril.ICC_TYPE1", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        int i = (telephonyProperty.equals(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) || !z) ? 0 : 1;
        if (!telephonyProperty2.equals(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) && z2) {
            i++;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "getEnabledSimCnt : ", "SecSimFeatureProvider");
        return i;
    }

    public final int getFirstSlotIndex() {
        if (Rune.isEnabledHidingByOpportunisticEsim(this.mContext)) {
            List<SubscriptionInfo> completeActiveSubscriptionInfoList =
                    SubscriptionManager.from(this.mContext).getCompleteActiveSubscriptionInfoList();
            return (completeActiveSubscriptionInfoList.get(0).isOpportunistic()
                            ? completeActiveSubscriptionInfoList.get(1)
                            : completeActiveSubscriptionInfoList.get(0))
                    .getSimSlotIndex();
        }
        int i =
                (!(Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1)
                                || DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(
                                        getTelephonyProperty(
                                                0,
                                                "ril.ICC_TYPE0",
                                                DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)))
                        ? (!(Settings.Global.getInt(
                                                        this.mContext.getContentResolver(),
                                                        "phone2_on",
                                                        1)
                                                == 1)
                                        || DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(
                                                getTelephonyProperty(
                                                        1,
                                                        "ril.ICC_TYPE1",
                                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)))
                                ? -1
                                : 1
                        : 0;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "getFirstSlotIndex : ", "SecSimFeatureProvider");
        return i;
    }

    public final String getSimOperator(int i) {
        String str;
        boolean z;
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId != null) {
            str = TelephonyManager.getDefault().getSimOperator(subId[0]);
            z = TelephonyManager.getDefault().isNetworkRoaming(subId[0]);
        } else {
            str = null;
            z = false;
        }
        if (!Rune.isChinaCTCModel() && z && "20404".equals(str)) {
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                    SubscriptionManager.from(this.mContext)
                            .getActiveSubscriptionInfoForSimSlotIndex(i);
            String str2 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String iccId =
                    activeSubscriptionInfoForSimSlotIndex != null
                            ? activeSubscriptionInfoForSimSlotIndex.getIccId()
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            if (iccId != null && iccId.length() > 6) {
                try {
                    str2 = iccId.substring(0, 6);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("SecSimFeatureProvider", "IndexOutOfBoundsException getIccIdBySlot" + e);
                }
            }
            if ("898603".equals(str2)
                    || "898605".equals(str2)
                    || "898611".equals(str2)
                    || "898530".equals(str2)) {
                str = "46003";
            }
        }
        Log.d("SecSimFeatureProvider", "getSimOperator : " + i + " : " + str);
        return str;
    }

    public final String getSimSlotName(int i) {
        String string =
                i == 0
                        ? Settings.Global.getString(
                                this.mContext.getContentResolver(), "select_name_1")
                        : Settings.Global.getString(
                                this.mContext.getContentResolver(), "select_name_2");
        Log.d("SecSimFeatureProvider", "slotIdx :" + i + ", slotName : " + string);
        return string;
    }

    public final String getTelephonyProperty(int i, String str, String str2) {
        String str3 = SystemProperties.get("ril.ICC_TYPE0");
        String str4 = SystemProperties.get("ril.ICC_TYPE1");
        if (str.equals("ril.ICC_TYPE0")) {
            if (str3 == null || str3.length() <= 0) {
                str3 = str2;
            }
        } else if (str.equals("ril.ICC_TYPE1")) {
            if (str4 == null || str4.length() <= 0) {
                str4 = str2;
            }
            str3 = str4;
        } else {
            str3 = null;
        }
        if (TextUtils.isEmpty(str3)) {
            str3 = TelephonyManager.getTelephonyProperty(i, str, str2);
        }
        Log.d("SecSimFeatureProvider", "getTelephonyProperty : " + i + " : " + str3);
        return str3;
    }

    public final boolean isLRASimInserted(int i) {
        String[] strArr = {"330120", "311480"};
        String telephonyProperty =
                getTelephonyProperty(i, "gsm.apn.sim.operator.numeric", ApnSettings.MVNO_NONE);
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= 2) {
                break;
            }
            if (telephonyProperty.equals(strArr[i2])) {
                z = true;
                break;
            }
            i2++;
        }
        StringBuilder sb = new StringBuilder("isLRASimInserted  : ");
        sb.append(i);
        sb.append(" : ");
        sb.append(z);
        sb.append(", ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                sb, telephonyProperty, "SecSimFeatureProvider");
        return z;
    }

    public final boolean isLraImsi(int i) {
        return isSupportGlobalRoamingLRA(i) && isLRASimInserted(i);
    }

    public final boolean isMultiSimModel() {
        String str = SemSystemProperties.get("persist.radio.multisim.config");
        Log.d("SecSimFeatureProvider", "isMultiSim : " + str);
        return "dsds".equals(str) || "tsts".equals(str) || "dsda".equals(str);
    }

    public final boolean isSPRSimInserted(int i) {
        String simOperator = getSimOperator(i);
        String salesCode = Utils.getSalesCode();
        Log.d("SecSimFeatureProvider", "salesCode : " + salesCode);
        boolean z = true;
        boolean z2 =
                ("SPR".equals(salesCode)
                                && ("310120".equals(simOperator) || "312530".equals(simOperator)))
                        || (("BST".equals(salesCode) || "SPR".equals(salesCode))
                                && "311870".equals(simOperator))
                        || ((("VMU".equals(salesCode) || "SPR".equals(salesCode))
                                        && "311490".equals(simOperator))
                                || (("XAS".equals(salesCode) || "SPR".equals(salesCode))
                                        && "310000".equals(simOperator)));
        if (!Rune.isUsOpenModel()
                || (!"310120".equals(simOperator)
                        && !"311870".equals(simOperator)
                        && !"311490".equals(simOperator)
                        && !"312530".equals(simOperator))) {
            z = z2;
        }
        StringBuilder sb = new StringBuilder("isSPRSimInserted : ");
        sb.append(i);
        sb.append(" : ");
        sb.append(z);
        sb.append(", ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, simOperator, "SecSimFeatureProvider");
        return z;
    }

    public final boolean isSupportGlobalRoamingLRA(int i) {
        String string =
                SemCarrierFeature.getInstance()
                        .getString(
                                i,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                ApnSettings.MVNO_NONE,
                                true);
        boolean z = string != null && string.contains("-LRA-GLB");
        StringBuilder sb = new StringBuilder("isSupportGlobalRoamingLRA : ");
        sb.append(i);
        sb.append(" : ");
        sb.append(z);
        sb.append(", ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, string, "SecSimFeatureProvider");
        return z;
    }

    public final boolean isUSCSimInserted(int i) {
        int parseInt;
        String simOperator = getSimOperator(i);
        boolean z =
                !TextUtils.isEmpty(simOperator)
                        && (((parseInt = Integer.parseInt(simOperator)) >= 311580
                                        && parseInt <= 311589)
                                || (parseInt >= 311220 && parseInt <= 311229));
        StringBuilder sb = new StringBuilder("isUSCSimInserted : ");
        sb.append(i);
        sb.append(" : ");
        sb.append(z);
        sb.append(", ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, simOperator, "SecSimFeatureProvider");
        return z;
    }

    public final boolean isVZWSimInserted(int i) {
        String simOperator = getSimOperator(i);
        boolean z = true;
        String string =
                SemCarrierFeature.getInstance()
                        .getString(
                                i,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                ApnSettings.MVNO_NONE,
                                true);
        if (string == null
                || !string.startsWith("VZW-")
                || (!"311480".equals(simOperator)
                        && !"999480".equals(simOperator)
                        && (!"20404".equals(simOperator) || "LRA".equals(Utils.getSalesCode())))) {
            z = false;
        }
        StringBuilder sb = new StringBuilder("isVZWSimInserted : ");
        sb.append(z);
        sb.append(", slotIdx : ");
        sb.append(i);
        sb.append(" ntcRawData : ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, string, "SecSimFeatureProvider");
        return z;
    }
}
