package com.samsung.android.settings.softwareupdate;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.secutil.Log;

import com.android.settings.R;
import com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.settings.ImsProfile;

import java.util.HashMap;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AutoDownloadPreferenceType {
    public static final /* synthetic */ AutoDownloadPreferenceType[] $VALUES;
    public static final AutoDownloadPreferenceType AUTO_DOWNLOAD;
    public static final AutoDownloadPreferenceType AUTO_DOWNLOAD_OVER_WIFI;
    public static final AutoDownloadPreferenceType NOTHING;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AutoDownload {
        public static final HashMap map;

        static {
            HashMap hashMap = new HashMap(3);
            map = hashMap;
            LottieColorUtils$$ExternalSyntheticOutline0.m(
                    0, hashMap, "key_never", 1, "key_wifi_only");
            hashMap.put("key_wifi_or_mobile", 2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AutoDownloadOverWifi {
        public static final HashMap map;

        static {
            HashMap hashMap = new HashMap(2);
            map = hashMap;
            hashMap.put(Boolean.FALSE, 0);
            hashMap.put(Boolean.TRUE, 1);
        }
    }

    static {
        AutoDownloadPreferenceType autoDownloadPreferenceType =
                new AutoDownloadPreferenceType() { // from class:
                    // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType.1
                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getKey() {
                        return "key_auto_download_over_wifi";
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getSummary(Context context) {
                        return (Rune.isShowMobileNetworkWarning(context)
                                        || Rune.isWifiDedicated(context))
                                ? context.getResources()
                                        .getString(
                                                R.string
                                                        .sec_software_update_auto_download_over_wifi_summary)
                                : context.getResources()
                                        .getString(
                                                R.string
                                                        .sec_software_update_auto_download_over_wifi_no_charge_summary);
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final int getTitleId() {
                        return R.string.sec_software_update_auto_download_over_wifi_title;
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void setDBAndLogging(Context context, int i) {
                        Settings.System.putInt(
                                context.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", i);
                        LoggingHelper.insertEventLogging(8106, 8106, i);
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void updateDBIfNeeded(Context context) {
                        int i =
                                Settings.System.getInt(
                                        context.getContentResolver(),
                                        "SOFTWARE_UPDATE_WIFI_ONLY2",
                                        -1);
                        int i2 = 1;
                        if (i != -1) {
                            if (i != 2) {
                                return;
                            }
                            Settings.System.putInt(
                                    context.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", 1);
                        } else {
                            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            String string =
                                    SemCarrierFeature.getInstance()
                                            .getString(
                                                    SoftwareUpdateUtils.getDataSimSlotId(context),
                                                    "CarrierFeature_SyncML_ConfigDefStatusAutoDownload",
                                                    ImsProfile.PDN_WIFI,
                                                    false);
                            if (!TextUtils.isEmpty(string) && string.contains("off")) {
                                i2 = 0;
                            }
                            Settings.System.putInt(
                                    context.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", i2);
                        }
                    }
                };
        AUTO_DOWNLOAD_OVER_WIFI = autoDownloadPreferenceType;
        AutoDownloadPreferenceType autoDownloadPreferenceType2 =
                new AutoDownloadPreferenceType() { // from class:
                    // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType.2
                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getKey() {
                        return "key_auto_download";
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getSummary(Context context) {
                        int i =
                                Settings.System.getInt(
                                        context.getContentResolver(),
                                        "SOFTWARE_UPDATE_WIFI_ONLY2",
                                        -1);
                        return i != 0
                                ? i != 1
                                        ? i != 2
                                                ? context.getString(
                                                        R.string
                                                                .sec_software_update_auto_download_option_using_wifi_only)
                                                : context.getString(
                                                        R.string
                                                                .sec_software_update_auto_download_option_using_wifi_or_mobile_data)
                                        : context.getString(
                                                R.string
                                                        .sec_software_update_auto_download_option_using_wifi_only)
                                : context.getString(
                                        R.string.sec_software_update_auto_download_option_never);
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final int getTitleId() {
                        return R.string.sec_software_update_auto_download_title;
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void setDBAndLogging(Context context, int i) {
                        Settings.System.putInt(
                                context.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", i);
                        LoggingHelper.insertEventLogging(8106, 8106, getSummary(context));
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void updateDBIfNeeded(Context context) {
                        int i = 2;
                        if (Settings.System.getInt(
                                        context.getContentResolver(),
                                        "SOFTWARE_UPDATE_WIFI_ONLY2",
                                        -1)
                                == -1) {
                            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            String string =
                                    SemCarrierFeature.getInstance()
                                            .getString(
                                                    SoftwareUpdateUtils.getDataSimSlotId(context),
                                                    "CarrierFeature_SyncML_ConfigDefStatusAutoDownload",
                                                    ImsProfile.PDN_WIFI,
                                                    false);
                            string.getClass();
                            switch (string) {
                                case "remove":
                                case "wifiandcelluar":
                                    break;
                                case "off":
                                case "remove,off":
                                    i = 0;
                                    break;
                                default:
                                    i = 1;
                                    break;
                            }
                            Settings.System.putInt(
                                    context.getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", i);
                        }
                    }
                };
        AUTO_DOWNLOAD = autoDownloadPreferenceType2;
        AutoDownloadPreferenceType autoDownloadPreferenceType3 =
                new AutoDownloadPreferenceType() { // from class:
                                                   // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType.3
                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getKey() {
                        return "key_nothing";
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final String getSummary(Context context) {
                        return null;
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final int getTitleId() {
                        return 0;
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void setDBAndLogging(Context context, int i) {
                        throw null;
                    }

                    @Override // com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType
                    public final void updateDBIfNeeded(Context context) {}
                };
        NOTHING = autoDownloadPreferenceType3;
        $VALUES =
                new AutoDownloadPreferenceType[] {
                    autoDownloadPreferenceType,
                    autoDownloadPreferenceType2,
                    autoDownloadPreferenceType3
                };
    }

    public static AutoDownloadPreferenceType of(Context context) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z = false;
        boolean z2 =
                SemCarrierFeature.getInstance()
                        .getBoolean(
                                SoftwareUpdateUtils.getDataSimSlotId(context),
                                "CarrierFeature_SyncML_EnableNotiDeltaBinarySizeBeforeDownload",
                                false,
                                false);
        AutoDownloadPreferenceType autoDownloadPreferenceType = NOTHING;
        if (z2) {
            Log.i(
                    "SoftwareUpdateSettings",
                    "Disable both auto download menus. isEnableNotiDeltaBinarySizeBeforeDownload : "
                            + SemCarrierFeature.getInstance()
                                    .getBoolean(
                                            SoftwareUpdateUtils.getDataSimSlotId(context),
                                            "CarrierFeature_SyncML_EnableNotiDeltaBinarySizeBeforeDownload",
                                            false,
                                            false));
            return autoDownloadPreferenceType;
        }
        String string =
                SemCarrierFeature.getInstance()
                        .getString(
                                SoftwareUpdateUtils.getDataSimSlotId(context),
                                "CarrierFeature_SyncML_ConfigDefStatusAutoDownload",
                                ImsProfile.PDN_WIFI,
                                false);
        if (!TextUtils.isEmpty(string) && string.contains("remove")) {
            StringBuilder sb =
                    new StringBuilder(
                            "Disable both auto download menus. needToRemoveAutoDownloadMenu : ");
            String string2 =
                    SemCarrierFeature.getInstance()
                            .getString(
                                    SoftwareUpdateUtils.getDataSimSlotId(context),
                                    "CarrierFeature_SyncML_ConfigDefStatusAutoDownload",
                                    ImsProfile.PDN_WIFI,
                                    false);
            if (!TextUtils.isEmpty(string2) && string2.contains("remove")) {
                z = true;
            }
            sb.append(z);
            Log.i("SoftwareUpdateSettings", sb.toString());
            return autoDownloadPreferenceType;
        }
        boolean isWifiDedicated = Rune.isWifiDedicated(context);
        AutoDownloadPreferenceType autoDownloadPreferenceType2 = AUTO_DOWNLOAD_OVER_WIFI;
        if (isWifiDedicated) {
            Log.i(
                    "SoftwareUpdateSettings",
                    "Enable Auto download over Wi-Fi menu. isWifiDedicated : "
                            + Rune.isWifiDedicated(context));
            return autoDownloadPreferenceType2;
        }
        if (Rune.isShowMobileNetworkWarning(context)) {
            Log.i("SoftwareUpdateSettings", "Enable auto download menu.");
            return AUTO_DOWNLOAD;
        }
        Log.i(
                "SoftwareUpdateSettings",
                "Enable auto download over Wi-Fi menu. isShowMobileNetworkWarning : "
                        + Rune.isShowMobileNetworkWarning(context));
        return autoDownloadPreferenceType2;
    }

    public static AutoDownloadPreferenceType valueOf(String str) {
        return (AutoDownloadPreferenceType) Enum.valueOf(AutoDownloadPreferenceType.class, str);
    }

    public static AutoDownloadPreferenceType[] values() {
        return (AutoDownloadPreferenceType[]) $VALUES.clone();
    }

    public abstract String getKey();

    public abstract String getSummary(Context context);

    public abstract int getTitleId();

    public abstract void setDBAndLogging(Context context, int i);

    public abstract void updateDBIfNeeded(Context context);
}
