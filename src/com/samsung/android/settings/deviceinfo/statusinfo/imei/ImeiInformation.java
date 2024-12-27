package com.samsung.android.settings.deviceinfo.statusinfo.imei;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;
import com.samsung.android.settings.deviceinfo.statusinfo.StatusInfoSettings;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ImeiInformation extends SettingsPreferenceFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final boolean SUPPORT_IMEI_SV;
    public static final String sSalesCode;
    public Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.statusinfo.imei.ImeiInformation$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            int simCount = ((TelephonyManager) context.getSystemService("phone")).getSimCount();
            String string = context.getResources().getString(R.string.imei_information_title);
            for (int i = 0; i < simCount; i++) {
                String imeiTitleSuffix = SecDeviceInfoUtils.getImeiTitleSuffix(context, i);
                boolean z = ImeiInformation.SUPPORT_IMEI_SV;
                if (SecDeviceInfoUtils.isPhoneTypeCdma(context, i) && !Rune.isDomesticModel()) {
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw).key =
                            ImeiInformation.getNewKey(i, "min_number");
                    searchIndexableRaw.screenTitle = string;
                    searchIndexableRaw.title =
                            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(),
                                    context.getResources().getBoolean(R.bool.config_msid_enable)
                                            ? context.getResources()
                                                    .getString(R.string.status_msid_number)
                                            : context.getResources()
                                                    .getString(R.string.status_min_number),
                                    imeiTitleSuffix);
                    arrayList.add(searchIndexableRaw);
                }
                if (SecDeviceInfoUtils.isPhoneTypeCdma(context, i) && !Rune.isDomesticModel()) {
                    SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw2).key =
                            ImeiInformation.getNewKey(i, "prl_version");
                    searchIndexableRaw2.screenTitle = string;
                    searchIndexableRaw2.title =
                            context.getResources().getString(R.string.status_prl_version)
                                    + imeiTitleSuffix;
                    arrayList.add(searchIndexableRaw2);
                }
                if ((Rune.isChinaOpen() || Rune.isChinaCTCModel())
                        ? false
                        : SecDeviceInfoUtils.isPhoneTypeCdma(context, i)) {
                    SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw3).key =
                            ImeiInformation.getNewKey(i, "meid_number");
                    searchIndexableRaw3.screenTitle = string;
                    searchIndexableRaw3.title =
                            context.getResources().getString(R.string.status_meid_number)
                                    + imeiTitleSuffix;
                    arrayList.add(searchIndexableRaw3);
                }
                SearchIndexableRaw searchIndexableRaw4 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw4).key =
                        ImeiInformation.getNewKey(i, "imei");
                searchIndexableRaw4.screenTitle = string;
                searchIndexableRaw4.title =
                        context.getResources().getString(R.string.status_imei) + imeiTitleSuffix;
                arrayList.add(searchIndexableRaw4);
                if (ImeiInformation.SUPPORT_IMEI_SV) {
                    SearchIndexableRaw searchIndexableRaw5 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw5).key =
                            ImeiInformation.getNewKey(i, "imei_sv");
                    searchIndexableRaw5.screenTitle = string;
                    searchIndexableRaw5.title =
                            context.getResources().getString(R.string.status_imei_sv)
                                    + imeiTitleSuffix;
                    arrayList.add(searchIndexableRaw5);
                }
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ((UserManager) context.getSystemService(UserManager.class)).isAdminUser()
                    && !Utils.isWifiOnly(context);
        }
    }

    static {
        SUPPORT_IMEI_SV = (Rune.isDomesticModel() || Rune.isJapanDCMModel()) ? false : true;
        sSalesCode = com.android.settings.Utils.getSalesCode();
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    }

    public static String getNewKey(int i, String str) {
        return "_" + str + i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addImeiPreference(int i) {
        String str;
        String str2;
        StringBuilder sb;
        int i2;
        String str3;
        Preference findPreference;
        ImeiInformation imeiInformation = this;
        imeiInformation.addPreferencesFromResource(R.xml.sec_device_info_status_imei);
        if (SecDeviceInfoUtils.isPhoneTypeCdma(imeiInformation.mContext, i)
                && !Rune.isDomesticModel()) {
            Context context = imeiInformation.mContext;
            String string =
                    context.getResources().getBoolean(R.bool.config_msid_enable)
                            ? context.getResources().getString(R.string.status_msid_number)
                            : context.getResources().getString(R.string.status_min_number);
            Preference findPreference2 = imeiInformation.findPreference("min_number");
            if (findPreference2 != null) {
                findPreference2.setTitle(string);
            }
            Context context2 = imeiInformation.mContext;
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                    ((SubscriptionManager) context2.getSystemService(SubscriptionManager.class))
                            .getActiveSubscriptionInfoForSimSlotIndex(i);
            String cdmaMin =
                    activeSubscriptionInfoForSimSlotIndex != null
                            ? ((TelephonyManager) context2.getSystemService(TelephonyManager.class))
                                    .createForSubscriptionId(
                                            activeSubscriptionInfoForSimSlotIndex
                                                    .getSubscriptionId())
                                    .getCdmaMin(
                                            activeSubscriptionInfoForSimSlotIndex
                                                    .getSubscriptionId())
                            : ApnSettings.MVNO_NONE;
            if (TextUtils.isEmpty(cdmaMin)) {
                cdmaMin = context2.getResources().getString(R.string.device_info_default);
            }
            imeiInformation.setSummaryText$2("min_number", cdmaMin);
        } else {
            imeiInformation.removePreference("min_number");
        }
        if (SecDeviceInfoUtils.isPhoneTypeCdma(imeiInformation.mContext, i)
                && !Rune.isDomesticModel()) {
            Context context3 = imeiInformation.mContext;
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 =
                    ((SubscriptionManager) context3.getSystemService(SubscriptionManager.class))
                            .getActiveSubscriptionInfoForSimSlotIndex(i);
            String cdmaPrlVersion =
                    activeSubscriptionInfoForSimSlotIndex2 != null
                            ? ((TelephonyManager) context3.getSystemService(TelephonyManager.class))
                                    .createForSubscriptionId(
                                            activeSubscriptionInfoForSimSlotIndex2
                                                    .getSubscriptionId())
                                    .getCdmaPrlVersion(
                                            activeSubscriptionInfoForSimSlotIndex2
                                                    .getSubscriptionId())
                            : ApnSettings.MVNO_NONE;
            if (TextUtils.isEmpty(cdmaPrlVersion)) {
                cdmaPrlVersion = context3.getResources().getString(R.string.device_info_default);
            }
            imeiInformation.setSummaryText$2("prl_version", cdmaPrlVersion);
        } else {
            imeiInformation.removePreference("prl_version");
        }
        if ((Rune.isChinaOpen() || Rune.isChinaCTCModel())
                ? false
                : SecDeviceInfoUtils.isPhoneTypeCdma(imeiInformation.mContext, i)) {
            Context context4 = imeiInformation.mContext;
            String string2 = context4.getResources().getString(R.string.device_info_default);
            String str4 = sSalesCode;
            if ("VZW".equals(str4)
                    || "VPP".equals(str4)
                    || SecDeviceInfoUtils.isMetroPCS(str4)
                    || Rune.isDomesticModel()) {
                str = ApnSettings.MVNO_NONE;
                str2 = "meid_number";
                Log.i("ImeiInformation", "SetSummary - Default");
            } else {
                TelephonyManager telephonyManager =
                        (TelephonyManager)
                                imeiInformation.mContext.getSystemService(TelephonyManager.class);
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex3 =
                        ((SubscriptionManager)
                                        imeiInformation.mContext.getSystemService(
                                                SubscriptionManager.class))
                                .getActiveSubscriptionInfoForSimSlotIndex(i);
                if (activeSubscriptionInfoForSimSlotIndex3 != null) {
                    telephonyManager =
                            ((TelephonyManager)
                                            imeiInformation.mContext.getSystemService(
                                                    TelephonyManager.class))
                                    .createForSubscriptionId(
                                            activeSubscriptionInfoForSimSlotIndex3
                                                    .getSubscriptionId());
                }
                if (SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Setting_EnableConversion4MEIDAndESN")) {
                    Log.i("ImeiInformation", "SetSummary - getDeviceId");
                    string2 = telephonyManager.getDeviceId(i);
                    if (TextUtils.isEmpty(string2)) {
                        str = ApnSettings.MVNO_NONE;
                        str2 = "meid_number";
                    } else {
                        StringBuilder sb2 = new StringBuilder("Dec:");
                        int length = string2.length();
                        if (length == 8) {
                            str = ApnSettings.MVNO_NONE;
                            String l = Long.toString(Long.parseLong(string2.substring(0, 2), 16));
                            String l2 =
                                    Long.toString(
                                            Long.parseLong(
                                                    string2.substring(2, string2.length()), 16));
                            sb = new StringBuilder();
                            int length2 = l.length();
                            str2 = "meid_number";
                            if (length2 == 1) {
                                sb.append("00");
                            } else if (length2 == 2) {
                                sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                            }
                            sb.append(l);
                            switch (l2.length()) {
                                case 1:
                                    sb.append("0000000");
                                    break;
                                case 2:
                                    sb.append("000000");
                                    break;
                                case 3:
                                    sb.append("00000");
                                    break;
                                case 4:
                                    sb.append("0000");
                                    break;
                                case 5:
                                    sb.append("000");
                                    break;
                                case 6:
                                    sb.append("00");
                                    break;
                                case 7:
                                    sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                    break;
                            }
                            sb.append(l2);
                        } else {
                            str = ApnSettings.MVNO_NONE;
                            str2 = "meid_number";
                            if (length == 14) {
                                String l3 =
                                        Long.toString(Long.parseLong(string2.substring(0, 8), 16));
                                String l4 =
                                        Long.toString(
                                                Long.parseLong(
                                                        string2.substring(8, string2.length()),
                                                        16));
                                sb = new StringBuilder();
                                switch (l3.length()) {
                                    case 1:
                                        sb.append("000000000");
                                        break;
                                    case 2:
                                        sb.append("00000000");
                                        break;
                                    case 3:
                                        sb.append("0000000");
                                        break;
                                    case 4:
                                        sb.append("000000");
                                        break;
                                    case 5:
                                        sb.append("00000");
                                        break;
                                    case 6:
                                        sb.append("0000");
                                        break;
                                    case 7:
                                        sb.append("000");
                                        break;
                                    case 8:
                                        sb.append("00");
                                        break;
                                    case 9:
                                        sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                        break;
                                }
                                sb.append(l3);
                                switch (l4.length()) {
                                    case 1:
                                        sb.append("0000000");
                                        break;
                                    case 2:
                                        sb.append("000000");
                                        break;
                                    case 3:
                                        sb.append("00000");
                                        break;
                                    case 4:
                                        sb.append("0000");
                                        break;
                                    case 5:
                                        sb.append("000");
                                        break;
                                    case 6:
                                        sb.append("00");
                                        break;
                                    case 7:
                                        sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                        break;
                                }
                                sb.append(l4);
                            } else {
                                sb = null;
                            }
                        }
                        sb2.append((CharSequence) sb);
                        sb2.append("\nHex:0x");
                        sb2.append(string2);
                        string2 = sb2.toString();
                    }
                } else {
                    str = ApnSettings.MVNO_NONE;
                    str2 = "meid_number";
                    Log.i("ImeiInformation", "SetSummary - getMeid");
                    string2 = telephonyManager.getMeid(i);
                }
                if (TextUtils.isEmpty(string2)) {
                    string2 = context4.getResources().getString(R.string.device_info_default);
                }
            }
            imeiInformation = this;
            imeiInformation.setSummaryText$2(str2, string2);
        } else {
            imeiInformation.removePreference("meid_number");
            str = ApnSettings.MVNO_NONE;
        }
        Context context5 = imeiInformation.mContext;
        String imei = SecDeviceInfoUtils.getImei(context5, i, true);
        if (TextUtils.isEmpty(imei)) {
            imei = context5.getResources().getString(R.string.device_info_default);
        }
        imeiInformation.setSummaryText$2("imei", imei);
        if (Rune.isJapanDCMModel()
                && (findPreference = imeiInformation.findPreference("imei")) != null) {
            findPreference.setEnabled(true);
            findPreference.setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() { // from class:
                        // com.samsung.android.settings.deviceinfo.statusinfo.imei.ImeiInformation.2
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            Intent intent = new Intent();
                            intent.putExtra("imei_barcode_value", preference.getSummary());
                            intent.setClassName(
                                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                    "com.samsung.android.settings.deviceinfo.statusinfo.imei.IMEIBarcodeDisplayActivity");
                            ImeiInformation.this.mContext.startActivity(intent);
                            return true;
                        }
                    });
        }
        if (SUPPORT_IMEI_SV) {
            Context context6 = imeiInformation.mContext;
            TelephonyManager telephonyManager2 =
                    (TelephonyManager) context6.getSystemService(TelephonyManager.class);
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex4 =
                    ((SubscriptionManager)
                                    imeiInformation.mContext.getSystemService(
                                            SubscriptionManager.class))
                            .getActiveSubscriptionInfoForSimSlotIndex(i);
            if (activeSubscriptionInfoForSimSlotIndex4 != null) {
                telephonyManager2 =
                        ((TelephonyManager)
                                        imeiInformation.mContext.getSystemService(
                                                TelephonyManager.class))
                                .createForSubscriptionId(
                                        activeSubscriptionInfoForSimSlotIndex4.getSubscriptionId());
            }
            String deviceSoftwareVersion = telephonyManager2.getDeviceSoftwareVersion(i);
            if (TextUtils.isEmpty(deviceSoftwareVersion)) {
                str3 = str;
                i2 = 0;
            } else if (TextUtils.isDigitsOnly(deviceSoftwareVersion)) {
                SpannableStringBuilder spannableStringBuilder =
                        new SpannableStringBuilder(deviceSoftwareVersion);
                i2 = 0;
                spannableStringBuilder.setSpan(
                        new TtsSpan.DigitsBuilder(deviceSoftwareVersion.toString()).build(),
                        0,
                        spannableStringBuilder.length(),
                        33);
                str3 = spannableStringBuilder;
            } else {
                i2 = 0;
                str3 = deviceSoftwareVersion;
            }
            imeiInformation.setSummaryText$2(
                    "imei_sv",
                    TextUtils.isEmpty(str3)
                            ? context6.getResources().getString(R.string.device_info_default)
                            : str3.toString());
        } else {
            imeiInformation.removePreference("imei_sv");
            i2 = 0;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int preferenceCount = preferenceScreen.getPreferenceCount();
        String imeiTitleSuffix = SecDeviceInfoUtils.getImeiTitleSuffix(imeiInformation.mContext, i);
        while (i2 < preferenceCount) {
            Preference preference = preferenceScreen.getPreference(i2);
            String key = preference.getKey();
            if (!key.startsWith("_")) {
                preference.setKey(getNewKey(i, key));
                preference.setTitle(((Object) preference.getTitle()) + imeiTitleSuffix);
            }
            i2++;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.imei_information_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return StatusInfoSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 41;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_about_phone";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        int simCount = telephonyManager.getSimCount();
        if (!SystemProperties.getBoolean("ril.support.dynamic_imei", false)) {
            Log.d("ImeiInformation", "Dynamic IMEI is False");
            for (int i = 0; i < simCount; i++) {
                addImeiPreference(i);
            }
            return;
        }
        Log.d("ImeiInformation", "Dynamic IMEI is TRUE");
        if (simCount == 1) {
            addImeiPreference(0);
            return;
        }
        try {
            str = telephonyManager.getPrimaryImei();
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getPrimaryImei() Exception e : "), "ImeiInformation");
            str = null;
        }
        Log.d("ImeiInformation", "primaryImei = " + SecDeviceInfoUtils.getPrintableImei(str));
        if (TextUtils.isEmpty(str)) {
            for (int i2 = 0; i2 < simCount; i2++) {
                String imei = SecDeviceInfoUtils.getImei(this.mContext, i2, false);
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "Imei for slot", " == ");
                m.append(SecDeviceInfoUtils.getPrintableImei(imei));
                Log.d("ImeiInformation", m.toString());
                addImeiPreference(i2);
            }
            return;
        }
        for (int i3 = 0; i3 < simCount; i3++) {
            String imei2 = SecDeviceInfoUtils.getImei(this.mContext, i3, false);
            StringBuilder m2 =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i3, "Imei for slot", " = ");
            m2.append(SecDeviceInfoUtils.getPrintableImei(imei2));
            Log.d("ImeiInformation", m2.toString());
            if (str.equals(imei2)) {
                addImeiPreference(i3);
            }
        }
        for (int i4 = 0; i4 < simCount; i4++) {
            if (!str.equals(SecDeviceInfoUtils.getImei(this.mContext, i4, false))) {
                addImeiPreference(i4);
            }
        }
    }

    public final void setSummaryText$2(String str, String str2) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = this.mContext.getResources().getString(R.string.device_info_default);
        }
        findPreference.setSummary(str2);
    }
}
