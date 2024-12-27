package com.samsung.android.settings;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.SemUserInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.general.GeneralUtils;
import com.samsung.android.settings.goodsettings.policy.PolicyManager;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateUtils;
import com.samsung.android.util.SemLog;
import com.sec.ims.configuration.DATA;
import com.sec.ims.scab.CABContract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Rune {
    public static final String COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static final boolean COVERSCREEN_SETTINGS;
    public static final boolean FEATURE_BATTERY_INFO_REGULATORY;
    public static final boolean FEATURE_DEEP_LINK_HOMEPAGE;
    public static final boolean FEATURE_ESIM_CHECKBOX_DEFAULT_VALUE;
    public static final boolean FEATURE_INTELLIGENCE_SERVICE;
    public static final boolean FEATURE_LEGAL_INFO_PARENTAL_CONTROL_DECLARATION;
    public static final boolean FEATURE_MANAGE_UNKNOWN_APPS;
    public static final boolean FEATURE_NATIONAL_NAME_CHINA;
    public static final boolean FEATURE_NA_ESIM_RESET;
    public static final boolean FEATURE_OFFLINE_LANGUAGE_PACK;
    public static final boolean FEATURE_REMOVE_TZ_WESTERN_SAHARA_IN_MOROCCO;
    public static final boolean FEATURE_SIDE_BUTTON_SUPPORT_AI_AGENT_APP;
    public static final boolean FEATURE_SIM_LOCK_KOR;
    public static final boolean FEATURE_STATUS_INFO_REGULATORY_MANUFACTURE_INFO;
    public static final boolean FRONT_COVER_SCREEN_ENABLE_TO_SHOW_SETTINGS;
    public static final boolean IFW_WIRELESS_KEYBOARD_MOUSE_SHARE;
    public static final boolean LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON;
    public static final boolean NOTIS_DND_DEFAULT_VALUE;
    public static final boolean NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED;
    public static final boolean NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON;
    public static final boolean SEC_FEATURE_HOMEHUB;
    public static final boolean STATUS_NETWORK_SIM_MOBILITY;
    public static final boolean SUPPORT_AOD;
    public static final boolean SUPPORT_BATTERY_CHARGING_ESTIMATE_TIME;
    public static final boolean SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA;
    public static final boolean SUPPORT_DISABLE_ACCOUNTS_SETTINGS;
    public static final boolean SUPPORT_DUAL_DESKTOP_MODE;
    public static final boolean SUPPORT_EDGE_MUM;
    public static final boolean SUPPORT_EXTRA_BRIGHT;
    public static final boolean SUPPORT_FEATURE_INAPP_NOTI_WITH_BUNDLE;
    public static final boolean SUPPORT_FIXED_ZOOM_DESKTOP_MODE;
    public static final boolean SUPPORT_FLOATING_TASKBAR;
    public static final boolean SUPPORT_LARGE_FRONT_SUBDISPLAY;
    public static final boolean SUPPORT_REDUCED_BRIGHTNESS_LIMIT;
    public static final boolean SUPPORT_SMARTMANAGER_CN;
    public static final boolean SUPPORT_STANDALONE_DESKTOP_MODE;
    public static final boolean SUPPORT_TASKBAR;
    public static final boolean SUPPORT_TEXT_REQUEST_APPS_OVERLAY_WINDOW_TITLE_BY_VZW;
    public static final boolean SUPPORT_TEXT_REQUEST_APPS_WRITE_SETTING_TITLE_BY_VZW;
    public static final boolean SUPPORT_TEXT_REQUEST_REGION_CHINA_TO_CHINA_MAINLAND;
    public static final boolean SUPPORT_TEXT_REQUEST_REGION_HONGKONG_TO_CHINA;
    public static final boolean SUPPORT_TEXT_REQUEST_REGION_TAIWAN_TO_CHINA;
    public static final boolean SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY;
    public static final boolean SUPPORT_TEXT_REQUEST_TIMEZONE_SHANGHAI_TO_BEIJING_BY_CHINA;
    public static final boolean SUPPORT_WIRELESS_DESKTOP_MODE;
    public static Boolean mSupportDesktopMode;
    public static Boolean mSupportDisplayCut;
    public static Boolean mSupportLEDIndicator;
    public static int supportMultiSIM;

    static {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        FEATURE_DEEP_LINK_HOMEPAGE = i >= 150000;
        FEATURE_SIDE_BUTTON_SUPPORT_AI_AGENT_APP =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT");
        mSupportDesktopMode = null;
        supportMultiSIM = -1;
        SUPPORT_DUAL_DESKTOP_MODE =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE", "dual");
        SUPPORT_STANDALONE_DESKTOP_MODE =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE", "standalone");
        SUPPORT_WIRELESS_DESKTOP_MODE =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE", "wireless");
        SUPPORT_FIXED_ZOOM_DESKTOP_MODE =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE", "fixedzoom");
        SUPPORT_FEATURE_INAPP_NOTI_WITH_BUNDLE = i >= 110500;
        mSupportDisplayCut = null;
        Integer.parseInt(DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE);
        SUPPORT_BATTERY_CHARGING_ESTIMATE_TIME =
                new File("/sys/class/power_supply/battery/time_to_full_now").exists();
        SUPPORT_AOD =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "aodversion");
        SUPPORT_EXTRA_BRIGHT =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS");
        SUPPORT_REDUCED_BRIGHTNESS_LIMIT = i >= 110100;
        SUPPORT_EDGE_MUM = i >= 140500;
        SUPPORT_TEXT_REQUEST_APPS_OVERLAY_WINDOW_TITLE_BY_VZW =
                "VZW".equals(Utils.getSalesCode()) || "VPP".equals(Utils.getSalesCode());
        SUPPORT_TEXT_REQUEST_APPS_WRITE_SETTING_TITLE_BY_VZW =
                "VZW".equals(Utils.getSalesCode()) || "VPP".equals(Utils.getSalesCode());
        "ZVV".equals(Utils.getSalesCode());
        FEATURE_MANAGE_UNKNOWN_APPS =
                "KR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
        FEATURE_SIM_LOCK_KOR = isDomesticModel();
        isChinaModel();
        FEATURE_NA_ESIM_RESET = GeneralUtils.isNAEsimResetConcept();
        SUPPORT_TEXT_REQUEST_REGION_CHINA_TO_CHINA_MAINLAND = !isChinaModel();
        SUPPORT_TEXT_REQUEST_TIMEZONE_SHANGHAI_TO_BEIJING_BY_CHINA =
                isChinaModel() || isChinaHKTWModel();
        SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY =
                "TR".equalsIgnoreCase(Utils.readCountryCode());
        SUPPORT_TEXT_REQUEST_REGION_HONGKONG_TO_CHINA = isChinaModel();
        SUPPORT_TEXT_REQUEST_REGION_TAIWAN_TO_CHINA = isChinaModel();
        isChinaModel();
        FEATURE_REMOVE_TZ_WESTERN_SAHARA_IN_MOROCCO =
                "MWD".equals(Utils.getSalesCode()) || "FWD".equals(Utils.getSalesCode());
        FEATURE_NATIONAL_NAME_CHINA = !isChinaModel();
        FEATURE_OFFLINE_LANGUAGE_PACK =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
        FEATURE_INTELLIGENCE_SERVICE =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
        FEATURE_ESIM_CHECKBOX_DEFAULT_VALUE = isJapanDCMModel();
        SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA = isChinaModel();
        FEATURE_STATUS_INFO_REGULATORY_MANUFACTURE_INFO =
                "TW".equalsIgnoreCase(Utils.readCountryCode());
        FEATURE_LEGAL_INFO_PARENTAL_CONTROL_DECLARATION =
                "FR".equalsIgnoreCase(Utils.readCountryCode());
        FEATURE_BATTERY_INFO_REGULATORY =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_BSOH_SETTINGS");
        NOTIS_DND_DEFAULT_VALUE = i >= 100500;
        NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED",
                        "support");
        NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED",
                        "defaulton");
        LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05");
        boolean m =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "WATCHFACE");
        FRONT_COVER_SCREEN_ENABLE_TO_SHOW_SETTINGS = m;
        COVERSCREEN_SETTINGS = m;
        SUPPORT_DISABLE_ACCOUNTS_SETTINGS =
                "K06".equals(Utils.getSalesCode())
                        || "K01".equals(Utils.getSalesCode())
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05");
        if (!"ATT".equals(Utils.getSalesCode())) {
            "AIO".equals(Utils.getSalesCode());
        }
        SUPPORT_LARGE_FRONT_SUBDISPLAY =
                Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN");
        boolean z =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR", false);
        SUPPORT_TASKBAR = z;
        SUPPORT_FLOATING_TASKBAR = z && i >= 160000;
        "ORANGE"
                .equals(
                        SemCscFeature.getInstance()
                                .getString(
                                        "CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon",
                                        ApnSettings.MVNO_NONE));
        STATUS_NETWORK_SIM_MOBILITY =
                SemSystemProperties.getBoolean("mdc.singlesku", false)
                        && SemSystemProperties.getBoolean("mdc.unified", false);
        IFW_WIRELESS_KEYBOARD_MOUSE_SHARE = Utils.isTablet();
        mSupportLEDIndicator = null;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB");
        SEC_FEATURE_HOMEHUB = (string == null || string.isEmpty()) ? false : true;
        SUPPORT_SMARTMANAGER_CN =
                "com.samsung.android.sm_cn"
                        .equals(
                                SemFloatingFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME"));
    }

    public static String getCarrierInfoLogo() {
        int slotIndex;
        return (!isMultiSimSupported()
                        || (slotIndex =
                                        SubscriptionManager.getSlotIndex(
                                                SubscriptionManager.getDefaultSubscriptionId()))
                                < 0
                        || slotIndex >= 2)
                ? SemCarrierFeature.getInstance()
                        .getString(
                                0,
                                "CarrierFeature_SystemUI_ConfigPolicyDisplayOpLogo",
                                ApnSettings.MVNO_NONE,
                                false)
                : SemCarrierFeature.getInstance()
                        .getString(
                                SubscriptionManager.getSlotIndex(
                                        SubscriptionManager.getDefaultSubscriptionId()),
                                "CarrierFeature_SystemUI_ConfigPolicyDisplayOpLogo",
                                ApnSettings.MVNO_NONE,
                                false);
    }

    public static boolean isAllNAVendor() {
        return "US".equalsIgnoreCase(Utils.readCountryCode());
    }

    public static boolean isChinaCTCModel() {
        return "CTC".equals(Utils.getSalesCode());
    }

    public static boolean isChinaHKTWModel() {
        String readCountryCode = Utils.readCountryCode();
        return "TW".equalsIgnoreCase(readCountryCode) || "HK".equalsIgnoreCase(readCountryCode);
    }

    public static boolean isChinaModel() {
        return "CN".equalsIgnoreCase(Utils.readCountryCode());
    }

    public static boolean isChinaOpen() {
        String salesCode = Utils.getSalesCode();
        return "CHN".equals(salesCode) || "CHC".equals(salesCode);
    }

    public static boolean isDisableIsraelCountry() {
        return SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Setting_DisableIsraelCountry", false)
                && !SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY;
    }

    public static boolean isDomesticKTTModel() {
        String salesCode = Utils.getSalesCode();
        return "KTT".equals(salesCode) || "KTC".equals(salesCode) || "KTO".equals(salesCode);
    }

    public static boolean isDomesticLGTModel() {
        String salesCode = Utils.getSalesCode();
        return "LGT".equals(salesCode) || "LUC".equals(salesCode) || "LUO".equals(salesCode);
    }

    public static boolean isDomesticModel() {
        return "KR".equalsIgnoreCase(Utils.readCountryCode());
    }

    public static boolean isDomesticSKTModel() {
        String salesCode = Utils.getSalesCode();
        return "SKT".equals(salesCode) || "SKC".equals(salesCode) || "SKO".equals(salesCode);
    }

    public static boolean isDualFolderType(Context context) {
        return context.getPackageManager().hasSystemFeature("com.sec.feature.folder_type")
                && context.getPackageManager().hasSystemFeature("com.sec.feature.dual_lcd");
    }

    public static boolean isEdgeLightingDefaultOff() {
        String string =
                SemCscFeature.getInstance()
                        .getString("CscFeature_Framework_ConfigDefStatusEdgeLighting");
        String string2 =
                SemCarrierFeature.getInstance()
                        .getString(
                                0,
                                "CarrierFeature_SystemUI_ConfigDefStatusEdgeLighting",
                                ApnSettings.MVNO_NONE,
                                false);
        return (string != null && string.contains("-defaulton"))
                || (string2 != null && string2.contains("-defaulton"));
    }

    public static boolean isEnabledHidingByOpportunisticEsim(Context context) {
        List<SubscriptionInfo> completeActiveSubscriptionInfoList;
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        if (subscriptionManager != null
                && (completeActiveSubscriptionInfoList =
                                subscriptionManager.getCompleteActiveSubscriptionInfoList())
                        != null) {
            SemLog.i(
                    "Rune",
                    "getCompleteActiveSubscriptionInfoList Size : "
                            + completeActiveSubscriptionInfoList.size());
            if (completeActiveSubscriptionInfoList.size() == 2
                    && completeActiveSubscriptionInfoList.get(0) != null
                    && completeActiveSubscriptionInfoList.get(1) != null) {
                SubscriptionInfo subscriptionInfo = completeActiveSubscriptionInfoList.get(0);
                SubscriptionInfo subscriptionInfo2 = completeActiveSubscriptionInfoList.get(1);
                boolean z =
                        completeActiveSubscriptionInfoList.get(0).isOpportunistic()
                                || subscriptionInfo2.isOpportunistic();
                SemLog.i("Rune", "subInfo1.getGroupUuid(): " + subscriptionInfo.getGroupUuid());
                SemLog.i("Rune", "subInfo2.getGroupUuid(): " + subscriptionInfo2.getGroupUuid());
                SemLog.i(
                        "Rune",
                        "subInfo1.isOpportunistic(): " + subscriptionInfo.isOpportunistic());
                SemLog.i(
                        "Rune",
                        "subInfo2.isOpportunistic(): " + subscriptionInfo2.isOpportunistic());
                if (subscriptionInfo.getGroupUuid() != null
                        && subscriptionInfo2.getGroupUuid() != null
                        && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid())
                        && z) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGpFelicaSupported(Context context) {
        return context.getPackageManager().hasSystemFeature("com.samsung.android.nfc.gpfelica");
    }

    public static boolean isJapanDCMModel() {
        return "DCM".equals(Utils.getSalesCode());
    }

    public static boolean isJapanKDIModel() {
        return "KDI".equals(Utils.getSalesCode());
    }

    public static boolean isJapanModel() {
        return "JP".equalsIgnoreCase(Utils.readCountryCode());
    }

    public static boolean isLDUModel() {
        String str = SystemProperties.get("ril.product_code", ApnSettings.MVNO_NONE);
        if (str.length() >= 11 && (str.charAt(10) == '8' || str.charAt(10) == '9')) {
            return true;
        }
        String salesCode = Utils.getSalesCode();
        return "PAP".equals(salesCode) || "FOP".equals(salesCode) || "LDU".equals(salesCode);
    }

    public static boolean isMaintenanceMode() {
        return ActivityManager.getCurrentUser() == 77;
    }

    public static boolean isMultiSimSupported() {
        if (supportMultiSIM == -1) {
            supportMultiSIM = TelephonyManager.getDefault().getPhoneCount() > 1 ? 1 : 0;
        }
        return supportMultiSIM == 1;
    }

    public static boolean isNavigationBarEnabled(Context context) {
        return (Utils.isNewDexMode(context)
                        || !(isSamsungDexMode(context) || Utils.isDesktopStandaloneMode(context)))
                && UserHandle.myUserId() == 0;
    }

    public static boolean isOllehSettingsSupport(Context context) {
        if (SemCscFeature.getInstance()
                .getBoolean("CscFeature_VoiceCall_SupportCallerRingBackTone")) {
            return true;
        }
        if (ActivityManager.getCurrentUser() == 0
                && ConnectionsUtils.isSupportMptcp()
                && SemSystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                        < 30
                && !ConnectionsUtils.isSupport5GConcept()) {
            return true;
        }
        boolean z = AccountUtils.SupportTwoPhone;
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.game.gametools.action.KT_PLAY_GAME_FROM_SETTINGS");
        return Utils.isIntentAvailable(context, intent)
                || Utils.hasPackage(context, "com.kt.olleh.servicemenu")
                || AccountUtils.SupportTwoPhone;
    }

    public static boolean isSamsungDexMode(Context context) {
        return context != null
                && supportDesktopMode()
                && 1 == context.getResources().getConfiguration().semDesktopModeEnabled;
    }

    public static boolean isSamsungDexOnPCMode(Context context) {
        return context != null
                && supportDesktopMode()
                && Settings.System.getInt(
                                context.getContentResolver(), "dexonpc_connection_state", 0)
                        == 3;
    }

    public static boolean isShopDemo(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
    }

    public static boolean isShowMobileNetworkWarning(Context context) {
        return !SemCarrierFeature.getInstance()
                .getBoolean(
                        SoftwareUpdateUtils.getDataSimSlotId(context),
                        "CarrierFeature_SyncML_DisableWarning4DataCostDuringFota",
                        false,
                        false);
    }

    public static boolean isSimMissing(Context context) {
        boolean z;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        boolean z2 = true;
        if (telephonyManager == null
                || !(telephonyManager.getSimState() == 1 || telephonyManager.getSimState() == 0)) {
            z = false;
        } else {
            SemLog.d("Rune", "telephonyManager.getSimState() : " + telephonyManager.getSimState());
            z = true;
        }
        if (!isMultiSimSupported()) {
            z2 = z;
        } else if (SubscriptionManager.from(context).getActiveSubscriptionInfoCount() == 0) {
            SemLog.d("Settings", "MultiSimManager isNoSIM");
        } else {
            SemLog.d("Settings", "MultiSimManager !isNoSIM");
            z2 = false;
        }
        SemLog.d("Rune", "isSimMissing : " + z2);
        return z2;
    }

    public static boolean isSimReady() {
        boolean z = true;
        if (isMultiSimSupported() && isChinaCTCModel()) {
            String semGetTelephonyProperty =
                    TelephonyManager.semGetTelephonyProperty(
                            0, "gsm.sim.currentcardstatus", DATA.DM_FIELD_INDEX.SMS_FORMAT);
            String semGetTelephonyProperty2 =
                    TelephonyManager.semGetTelephonyProperty(
                            1, "gsm.sim.currentcardstatus", DATA.DM_FIELD_INDEX.SMS_FORMAT);
            int parseInt = Integer.parseInt(semGetTelephonyProperty);
            int parseInt2 = Integer.parseInt(semGetTelephonyProperty2);
            if (parseInt != 3 && parseInt2 != 3) {
                z = false;
            }
            SemLog.d("Rune", "isSimReady : " + z);
        }
        return z;
    }

    public static boolean isSprModel() {
        String salesCode = Utils.getSalesCode();
        return "XAS".equals(salesCode)
                || "SPR".equals(salesCode)
                || "VMU".equals(salesCode)
                || "BST".equals(salesCode);
    }

    public static boolean isSupportAiAgent(Context context) {
        RoleManager roleManager;
        if (FEATURE_SIDE_BUTTON_SUPPORT_AI_AGENT_APP
                && (roleManager =
                                (RoleManager)
                                        context.getSystemService(
                                                CABContract.CABBusinessContactOrgan.ROLE))
                        != null) {
            return roleManager
                    .getRoleHolders("android.app.role.ASSISTANT")
                    .contains("com.google.android.googlequicksearchbox");
        }
        return false;
    }

    public static boolean isSupportAndroidBeam(Context context) {
        if (context != null) {
            return context.getPackageManager().hasSystemFeature("android.sofware.nfc.beam");
        }
        android.util.Log.e("Rune", "context is null");
        return false;
    }

    public static boolean isSupportEUiccSwp(Context context) {
        if (context.getPackageManager().hasSystemFeature("com.samsung.android.nfc.euicc")) {
            SemLog.i("Rune", "eUICC swp supported");
            return true;
        }
        SemLog.d("Rune", "eUICC not supported euicc.xml missing");
        return false;
    }

    public static boolean isSupportGoodSettingsV2(Context context) {
        if (!PkgUtils.hasPackage(
                        context, context.getString(R.string.config_galaxy_registry_package))
                || new Intent("com.samsung.android.app.galaxyregistry.HOMEPAGE")
                                .resolveActivity(context.getPackageManager())
                        == null) {
            return false;
        }
        PolicyManager policyManager = PolicyManager.getInstance(context);
        policyManager.setSupportGoodSettingsVersion(PolicyManager.Version.V2);
        return policyManager.isEnabled(context);
    }

    public static boolean isSupportRoutingSettings(FragmentActivity fragmentActivity) {
        String str = SystemProperties.get("ro.csc.countryiso_code");
        String salesCode = Utils.getSalesCode();
        return (("HK".equalsIgnoreCase(str) && "TGY".equalsIgnoreCase(salesCode))
                        || (("CN".equalsIgnoreCase(str) && "CHC".equalsIgnoreCase(salesCode))
                                || ("TW".equalsIgnoreCase(str)
                                        && "BRI".equalsIgnoreCase(salesCode))))
                && (fragmentActivity != null
                        && (fragmentActivity
                                        .getPackageManager()
                                        .hasSystemFeature("android.hardware.nfc.uicc")
                                || fragmentActivity
                                        .getPackageManager()
                                        .hasSystemFeature("android.hardware.nfc.ese")));
    }

    public static boolean isTmobileConcept() {
        String salesCode = Utils.getSalesCode();
        return "TMO".equalsIgnoreCase(salesCode)
                || "TMB".equalsIgnoreCase(salesCode)
                || "ARS".equalsIgnoreCase(salesCode)
                || ConnectionsUtils.isMetroPCS();
    }

    public static boolean isUSA() {
        return "US".equalsIgnoreCase(Utils.readCountryCode());
    }

    public static boolean isUsOpenModel() {
        String salesCode = Utils.getSalesCode();
        return ("Global".equals(salesCode) || "XAA".equals(salesCode))
                && "US".equals(Utils.readCountryCode());
    }

    public static boolean isVzwConceptModel() {
        String salesCode = Utils.getSalesCode();
        if (!"VZW".equals(salesCode)
                && !"VPP".equals(salesCode)
                && !"CCT".equals(salesCode)
                && !"CHA".equals(salesCode)
                && !"TFV".equals(salesCode)
                && !"FKR".equals(salesCode)
                && !"TFN".equals(salesCode)) {
            return false;
        }
        SemLog.d("Rune", "isVZWConcept() return true");
        return true;
    }

    public static boolean isVzwDemoMode(Context context) {
        String salesCode = Utils.getSalesCode();
        return ("VZW".equals(salesCode) || "VPP".equals(salesCode))
                && Settings.Secure.getInt(
                                context.getContentResolver(), "verizonwireless_store_demo_mode", 0)
                        != 0;
    }

    public static boolean isVzwModel() {
        return "VZW".equals(Utils.getSalesCode());
    }

    public static boolean isWifiDedicated(Context context) {
        return com.android.settingslib.Utils.isWifiOnly(context)
                || Settings.System.getInt(context.getContentResolver(), "SMLDM_BEARER", 0) == 1
                || "wifi_dedicated"
                        .equalsIgnoreCase(
                                SemCarrierFeature.getInstance()
                                        .getString(
                                                SoftwareUpdateUtils.getDataSimSlotId(context),
                                                "CarrierFeature_SyncML_DeltaBinaryDownVia",
                                                "Not_Define",
                                                false));
    }

    public static String readFile(FileInputStream fileInputStream) {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(ApnSettings.MVNO_NONE);
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean supportAmoledDisplay() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
    }

    public static boolean supportAutoBrightness(Context context) {
        return supportLightSensor(context) || supportCameraSensor(context);
    }

    public static boolean supportBixbyClient() {
        return !SemCscFeature.getInstance().getBoolean("CscFeature_Common_DisableBixby", false)
                && SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BIXBY");
    }

    public static boolean supportBlueLightFilterAdaptiveMode() {
        return SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE")
                > 0;
    }

    public static boolean supportCameraSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        return (sensorManager == null
                        || sensorManager.getDefaultSensor(5) != null
                        || sensorManager.getDefaultSensor(65604) == null)
                ? false
                : true;
    }

    public static boolean supportDataRole() {
        int i = ConnectionsUtils.$r8$clinit;
        boolean m =
                SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                        "/sys/class/dual_role_usb");
        boolean m2 =
                SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m("/sys/class/typec");
        boolean z =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEM_SUPPORT_ROLE_SWAP_DISABLE");
        StringBuilder m3 =
                Utils$$ExternalSyntheticOutline0.m("support Role swap : ", m, ", (", m2, ", ");
        m3.append(z);
        m3.append(")");
        android.util.Log.d("ConnectionsUtils", m3.toString());
        return m || (m2 && !z);
    }

    public static boolean supportDesktopMode() {
        if (mSupportDesktopMode == null) {
            mSupportDesktopMode =
                    Boolean.valueOf(
                            SemFloatingFeature.getInstance()
                                    .getBoolean(
                                            "SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP"));
        }
        return mSupportDesktopMode.booleanValue();
    }

    public static boolean supportDoubleTapMenu() {
        return InputManager.getInstance() != null
                && (InputManager.getInstance().semCheckInputFeature() & 1) == 1;
    }

    public static boolean supportEasyMode(Context context) {
        return SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_EASY_MODE")
                && UserHandle.myUserId() == 0
                && Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0)
                        != 1;
    }

    public static boolean supportFingerPrint(Context context) {
        SemLog.d("FingerSensorGesture", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_FINGERPRINT");
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context);
        return fingerprintManagerOrNull != null && fingerprintManagerOrNull.semHasFeature(1);
    }

    public static boolean supportFingerSensorGestureSpay(Context context) {
        boolean z = false;
        try {
            z =
                    context.getPackageManager()
                            .getApplicationInfo(
                                    Utils.hasPackage(context, "com.samsung.android.spay")
                                            ? "com.samsung.android.spay"
                                            : "com.samsung.android.spaymini",
                                    128)
                            .metaData
                            .getBoolean("com.samsung.android.spay.quickgesture", false);
            SemLog.d("Rune", "Samsung Pay supports finger gesture : " + z);
            return z;
        } catch (PackageManager.NameNotFoundException e) {
            SemLog.e("Rune", "Failed to load meta-data, NameNotFound: " + e.getMessage());
            return z;
        } catch (NullPointerException e2) {
            SemLog.e("Rune", "Failed to load meta-data, NullPointer: " + e2.getMessage());
            return z;
        }
    }

    public static boolean supportFunctionKey() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU");
    }

    public static boolean supportGoodSettings(Context context) {
        return PkgUtils.hasPackage(
                        context, context.getString(R.string.config_galaxy_registry_package))
                && PolicyManager.getInstance(context).isSupportGoodSettingsV1()
                && PolicyManager.getInstance(context).isEnabled(context)
                && !isSamsungDexMode(context);
    }

    public static boolean supportHighRefreshRate(Context context, int i) {
        return SecDisplayUtils.getHighRefreshRateSeamlessType(i) != 0
                && (!isSamsungDexMode(context)
                        || Utils.isDesktopStandaloneMode(context)
                        || Utils.isNewDexMode(context));
    }

    public static boolean supportLedIndicator() {
        if (mSupportLEDIndicator == null) {
            mSupportLEDIndicator = Boolean.TRUE;
            if (!new File("/sys/class/sec/led/led_blink").isFile()) {
                mSupportLEDIndicator = Boolean.FALSE;
            }
        }
        return mSupportLEDIndicator.booleanValue();
    }

    public static boolean supportLiftToWakeSetting(Context context) {
        SensorManager sensorManager;
        return (Utils.isTablet()
                        || (sensorManager = (SensorManager) context.getSystemService("sensor"))
                                == null
                        || sensorManager.getDefaultSensor(65590) == null)
                ? false
                : true;
    }

    public static boolean supportLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        if (sensorManager != null) {
            List<Sensor> sensorList = sensorManager.getSensorList(-1);
            for (int i = 0; i < sensorList.size(); i++) {
                int type = sensorList.get(i).getType();
                if (type == 5 || type == 65601) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean supportNaturalModeWithoutWcgMode() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_NATURAL_SCREEN_MODE");
    }

    public static boolean supportNavigationBar() {
        return !SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME")
                .isEmpty();
    }

    public static boolean supportNavigationBarForHardKey() {
        if (supportNavigationBar()) {
            return Rune$$ExternalSyntheticOutline0.m(
                    "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME",
                    "SupportHardKeyNavigationBar");
        }
        return false;
    }

    public static boolean supportPocketMode(Context context) {
        SemMotionRecognitionManager semMotionRecognitionManager =
                (SemMotionRecognitionManager) context.getSystemService("motion_recognition");
        if (semMotionRecognitionManager == null) {
            SemLog.d("Rune", "isSupportPocketMode : false. smrmService is null");
            return false;
        }
        boolean isAvailable = semMotionRecognitionManager.isAvailable(8388608);
        SemLog.d("Rune", "isSupportPocketMode : " + isAvailable);
        return isAvailable;
    }

    public static boolean supportRelativeLink() {
        return (SemPersonaManager.isSecureFolderId(UserHandle.getCallingUserId())
                        || SemPersonaManager.isAppSeparationUserId(UserHandle.getCallingUserId()))
                ? false
                : true;
    }

    public static boolean supportScreenMode() {
        if (UserHandle.myUserId() != 0) {
            return false;
        }
        return supportWcgModeOnAmoled() || supportNaturalModeWithoutWcgMode();
    }

    public static boolean supportSmartSwitch(Context context) {
        SemUserInfo semGetSemUserInfo;
        String string =
                SemCscFeature.getInstance()
                        .getString(
                                "CscFeature_Common_ConfigSmartSwitchFunction",
                                ApnSettings.MVNO_NONE);
        boolean z =
                UserHandle.myUserId() == 0
                        || !(UserHandle.myUserId() == 0
                                || (semGetSemUserInfo =
                                                UserManager.get(context)
                                                        .semGetSemUserInfo(UserHandle.myUserId()))
                                        == null
                                || !semGetSemUserInfo.isSecondNumberMode());
        boolean z2 = TextUtils.isEmpty(string) || string.contains("settings");
        Utils$$ExternalSyntheticOutline0.m653m(
                "isAvailableUser: ", z, " isSupportSmartSwitch:", z2, "Rune");
        return z && z2;
    }

    public static boolean supportSoftphone() {
        if (SystemProperties.get("ro.build.characteristics", ApnSettings.MVNO_NONE)
                .contains("tablet")) {
            try {
                String str = SystemProperties.get("persist.omc.sales_code");
                if (TextUtils.isEmpty(str)) {
                    str = SystemProperties.get("ro.csc.sales_code");
                }
                if (!"ATT".equals(str)) {
                    if ("APP".equals(str)) {}
                }
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static boolean supportTaskBar(Context context) {
        return SUPPORT_TASKBAR
                && Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0)
                        != 1;
    }

    public static boolean supportUserSettings(Context context) {
        boolean z =
                SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");
        boolean supportsMultipleUsers = UserManager.supportsMultipleUsers();
        boolean isMaintenanceMode = isMaintenanceMode();
        boolean isShopDemo = isShopDemo(context);
        boolean isUserAMonkey = ActivityManager.isUserAMonkey();
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "supportUserSettings /twoPhoneService : ",
                        z,
                        " /mu_enabled : true /supportsMultipleUsers : ",
                        supportsMultipleUsers,
                        " /mainenanceMode : ");
        m.append(isMaintenanceMode);
        m.append(" /shopDemo : ");
        m.append(isShopDemo);
        m.append(" /monkeyTest : ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(m, isUserAMonkey, "Rune");
        return (z || !supportsMultipleUsers || isShopDemo || isUserAMonkey || isMaintenanceMode)
                ? false
                : true;
    }

    public static boolean supportVividPlusMode() {
        return SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS")
                == 1;
    }

    public static boolean supportVividness() {
        return SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDNESS")
                == 1;
    }

    public static boolean supportWcgModeOnAmoled() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT");
    }
}
