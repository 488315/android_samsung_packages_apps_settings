package com.samsung.android.settings.eternal.provider.items;

import android.R;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.android.settings.Utils;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessories.AccessoriesUtils;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.util.SemLog;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AdvancedFeatureItem implements Recoverable {
    public static void sideKeyDoublePressRestore(Context context) {
        boolean z =
                Settings.Global.getInt(
                                context.getContentResolver(), "function_key_config_doublepress", 1)
                        == 1;
        UsefulfeatureUtils.migrationFunctionKeyDB(context, 2);
        if (z) {
            UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 2, true);
        } else {
            UsefulfeatureUtils.setSideKeyCustomizationInfo(context, 2, false);
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        str.getClass();
        switch (str) {
            case "/Settings/Advanced/XcoverTopKeyShortPress":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/FingerSensorGestures":
                if (Rune.supportFingerPrint(context)) {
                    Scene.Builder builder = new Scene.Builder(str);
                    builder.setValue(1, false);
                    Boolean bool = Boolean.TRUE;
                    Scene.Builder m =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder, "expectedResult", bool, arrayList, str);
                    m.setValue(0, false);
                    m.addAttribute(bool, "expectedResult");
                    arrayList.add(m.build());
                    break;
                }
                break;
            case "/Settings/Advanced/LabsFlexModePanel":
                String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                break;
            case "/Settings/Advanced/SideKeyDoublePress":
                Rune.supportFunctionKey();
                break;
            case "/Settings/Advanced/LabsAppSplitView":
                if (!Utils.isTablet()) {
                    String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    break;
                }
                break;
            case "/Settings/Advanced/ActiveKeyLongPress":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/EasyMute":
                if (UsefulfeatureUtils.isSupportMotionFeature(context, "easy_mute")) {
                    Scene.Builder builder2 = new Scene.Builder(str);
                    builder2.setValue(1, false);
                    builder2.addAttribute("1", "palmTouch");
                    builder2.addAttribute("1", "turnOver");
                    Boolean bool2 = Boolean.TRUE;
                    Scene.Builder m2 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder2, "expectedResult", bool2, arrayList, str);
                    m2.setValue(0, false);
                    m2.addAttribute(bool2, "expectedResult");
                    m2.addAttribute(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "palmTouch");
                    m2.addAttribute(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "turnOver");
                    arrayList.add(m2.build());
                    break;
                }
                break;
            case "/Settings/Advanced/SmartStay":
                Scene.Builder builder3 = new Scene.Builder(str);
                builder3.setValue(1, false);
                Boolean bool3 = Boolean.TRUE;
                Scene.Builder m3 =
                        AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                builder3, "expectedResult", bool3, arrayList, str);
                m3.setValue(0, false);
                m3.addAttribute(bool3, "expectedResult");
                arrayList.add(m3.build());
                break;
            case "/Settings/Advanced/LabsLandScapeViewForPortraitApps":
                String str4 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                Utils.isTablet();
                break;
            case "/Settings/Advanced/AutoScreenOn":
                if (UsefulfeatureUtils.hasFeatureAutoScreenTurnOn(context)
                        || !context.getPackageManager()
                                .hasSystemFeature("com.sec.feature.nfc_authentication_cover")) {
                    Scene.Builder builder4 = new Scene.Builder(str);
                    builder4.setValue(1, false);
                    Boolean bool4 = Boolean.TRUE;
                    Scene.Builder m4 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder4, "expectedResult", bool4, arrayList, str);
                    m4.setValue(0, false);
                    m4.addAttribute(bool4, "expectedResult");
                    arrayList.add(m4.build());
                    break;
                }
                break;
            case "/Settings/Advanced/SmartAlert":
                if (UsefulfeatureUtils.isSupportMotionFeature(context, "smart_alert")) {
                    Scene.Builder builder5 = new Scene.Builder(str);
                    builder5.setValue(1, false);
                    Boolean bool5 = Boolean.TRUE;
                    Scene.Builder m5 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder5, "expectedResult", bool5, arrayList, str);
                    m5.setValue(0, false);
                    m5.addAttribute(bool5, "expectedResult");
                    arrayList.add(m5.build());
                    break;
                }
                break;
            case "/Settings/Advanced/PalmSwipeToCapture":
                if (UsefulfeatureUtils.isSupportMotionFeature(context, "palm_swipe_to_capture")) {
                    Scene.Builder builder6 = new Scene.Builder(str);
                    builder6.setValue(1, false);
                    Boolean bool6 = Boolean.TRUE;
                    Scene.Builder m6 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder6, "expectedResult", bool6, arrayList, str);
                    m6.setValue(0, false);
                    m6.addAttribute(bool6, "expectedResult");
                    arrayList.add(m6.build());
                    break;
                }
                break;
            case "/Settings/Advanced/SideKeyLongPress":
                if (Rune.supportFunctionKey() && !Rune.supportBixbyClient()) {
                    Rune.isSupportAiAgent(context);
                    break;
                }
                break;
            case "/Settings/Advanced/OneHandedMode":
                boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
                if (!Utils.isTablet()) {
                    Scene.Builder builder7 = new Scene.Builder(str);
                    builder7.setValue(1, false);
                    builder7.addAttribute("1", "wakeupType");
                    builder7.addAttribute("1", "showHardKey");
                    Boolean bool7 = Boolean.TRUE;
                    Scene.Builder m7 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder7, "expectedResult", bool7, arrayList, str);
                    m7.setValue(0, false);
                    m7.addAttribute(bool7, "expectedResult");
                    m7.addAttribute(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "wakeupType");
                    m7.addAttribute(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "showHardKey");
                    arrayList.add(m7.build());
                    break;
                }
                break;
            case "/Settings/Advanced/FlexModePanelEnabled":
            case "/Settings/Advanced/FlexModeScrollWheelPosition":
            case "/Settings/Advanced/VideoEnhancerApp":
            case "/Settings/Advanced/PreventOnlineProcessing":
                break;
            case "/Settings/Advanced/CoverTextDirection":
                if (AccessoriesUtils.hasCoverSettingCoverOrientation(context)) {
                    Scene.Builder builder8 = new Scene.Builder(str);
                    builder8.setValue(1, false);
                    Boolean bool8 = Boolean.TRUE;
                    Scene.Builder m8 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder8, "expectedResult", bool8, arrayList, str);
                    m8.setValue(0, false);
                    m8.addAttribute(bool8, "expectedResult");
                    arrayList.add(m8.build());
                    break;
                }
                break;
            case "/Settings/Advanced/DoubleTapToSleep":
                Scene.Builder builder9 = new Scene.Builder(str);
                builder9.setValue(1, false);
                Boolean bool9 = Boolean.TRUE;
                Scene.Builder m9 =
                        AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                builder9, "expectedResult", bool9, arrayList, str);
                m9.setValue(0, false);
                m9.addAttribute(bool9, "expectedResult");
                arrayList.add(m9.build());
                break;
            case "/Settings/Advanced/ActiveKeyShortPress":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/LabsRotateApps":
                String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                break;
            case "/Settings/Advanced/LiftToWake":
                if (Rune.supportLiftToWakeSetting(context)) {
                    Scene.Builder builder10 = new Scene.Builder(str);
                    builder10.setValue(1, false);
                    Boolean bool10 = Boolean.TRUE;
                    Scene.Builder m10 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder10, "expectedResult", bool10, arrayList, str);
                    m10.setValue(0, false);
                    m10.addAttribute(bool10, "expectedResult");
                    arrayList.add(m10.build());
                    break;
                }
                break;
            case "/Settings/Advanced/XcoverTopKeyLongPress":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/SwipePopupViewCornerAreaLevel":
                context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled);
                break;
            case "/Settings/Advanced/LabsMultiWindowMenuInFullScreen":
                if (!Utils.isTablet() && Build.VERSION.SEM_PLATFORM_INT > 130100) {
                    String str6 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    break;
                }
                break;
            case "/Settings/Advanced/LabsSwipePopupView":
                context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled);
                break;
            case "/Settings/Advanced/SideKeyLongPressType":
                if (Rune.supportFunctionKey()) {
                    Rune.supportBixbyClient();
                    break;
                }
                break;
            case "/Settings/Advanced/LabsAspectRatioApps":
                String str7 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                break;
            case "/Settings/Advanced/LabsLandScapeAllApps":
                Utils.isTablet();
                break;
            case "/Settings/Advanced/DoubleTapToWake":
                if (Rune.supportDoubleTapMenu()) {
                    Scene.Builder builder11 = new Scene.Builder(str);
                    builder11.setValue(1, false);
                    Boolean bool11 = Boolean.TRUE;
                    Scene.Builder m11 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder11, "expectedResult", bool11, arrayList, str);
                    m11.setValue(0, false);
                    m11.addAttribute(bool11, "expectedResult");
                    arrayList.add(m11.build());
                    break;
                }
                break;
            case "/Settings/Advanced/DirectShare":
                Scene.Builder builder12 = new Scene.Builder(str);
                builder12.setValue(1, false);
                Boolean bool12 = Boolean.TRUE;
                Scene.Builder m12 =
                        AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                builder12, "expectedResult", bool12, arrayList, str);
                m12.setValue(0, false);
                m12.addAttribute(bool12, "expectedResult");
                arrayList.add(m12.build());
                break;
            case "/Settings/Advanced/SmartPopupView":
                if (context.getPackageManager()
                        .hasSystemFeature("android.software.freeform_window_management")) {
                    Scene.Builder builder13 = new Scene.Builder(str);
                    builder13.setValue("com.samsung:com.android", false);
                    Boolean bool13 = Boolean.TRUE;
                    Scene.Builder m13 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder13, "expectedResult", bool13, arrayList, str);
                    m13.setValue("com.samsung", false);
                    m13.addAttribute(bool13, "expectedResult");
                    arrayList.add(m13.build());
                    break;
                }
                break;
            case "/Settings/Advanced/DirectCall":
                if (UsefulfeatureUtils.isSupportMotionFeature(context, "direct_call")) {
                    Scene.Builder builder14 = new Scene.Builder(str);
                    builder14.setValue(1, false);
                    Boolean bool14 = Boolean.TRUE;
                    Scene.Builder m14 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder14, "expectedResult", bool14, arrayList, str);
                    m14.setValue(0, false);
                    m14.addAttribute(bool14, "expectedResult");
                    arrayList.add(m14.build());
                    break;
                }
                break;
            case "/Settings/Advanced/PalmTouchToSleep":
                Scene.Builder builder15 = new Scene.Builder(str);
                builder15.setValue(1, false);
                Boolean bool15 = Boolean.TRUE;
                Scene.Builder m15 =
                        AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                builder15, "expectedResult", bool15, arrayList, str);
                m15.setValue(0, false);
                m15.addAttribute(bool15, "expectedResult");
                arrayList.add(m15.build());
                break;
            case "/Settings/Advanced/ActiveKeyOnLockScreen":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/LabsSwipeSplitView":
                context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled);
                break;
            case "/Settings/Advanced/XcoverKeyPtt":
                UsefulfeatureUtils.hasVzwPttEnable(context);
                break;
            case "/Settings/Advanced/VideoEnhancerSwitch":
                Scene.Builder builder16 = new Scene.Builder(str);
                builder16.setValue(1, false);
                Boolean bool16 = Boolean.TRUE;
                Scene.Builder m16 =
                        AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                builder16, "expectedResult", bool16, arrayList, str);
                m16.setValue(0, false);
                m16.addAttribute(bool16, "expectedResult");
                arrayList.add(m16.build());
                break;
            case "/Settings/Advanced/LabsFullScreenInSplitView":
                context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled);
                break;
            case "/Settings/Advanced/XcoverTopKeyOnLockScreen":
                UsefulfeatureUtils.hasActiveKey();
                break;
            case "/Settings/Advanced/XcoverTopKeyDedicatedApp":
                UsefulfeatureUtils.hasDedicatedAppEnable$1(context);
                break;
            case "/Settings/Advanced/SideKeyDoublePressSwitch":
                if (Rune.supportFunctionKey()) {
                    Scene.Builder builder17 = new Scene.Builder(str);
                    builder17.setValue(1, false);
                    Boolean bool17 = Boolean.TRUE;
                    Scene.Builder m17 =
                            AdvancedFeatureItem$$ExternalSyntheticOutline0.m(
                                    builder17, "expectedResult", bool17, arrayList, str);
                    m17.setValue(0, false);
                    m17.addAttribute(bool17, "expectedResult");
                    arrayList.add(m17.build());
                    break;
                }
                break;
            case "/Settings/Advanced/SideKeyDoublePressOpenAppValue":
                Rune.supportFunctionKey();
                break;
            case "/Settings/Advanced/SideKeyDoublePressType":
                Rune.supportFunctionKey();
                break;
            case "/Settings/Advanced/LabsMultiWindowAllApps":
                context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled);
                break;
            case "/Settings/Advanced/XcoverKeyDedicatedApp":
                UsefulfeatureUtils.hasDedicatedAppEnable$1(context);
                break;
            default:
                SemLog.w("AdvancedFeatureItem", "Unknown key to test: ".concat(str));
                break;
        }
        int size = arrayList.size();
        SemLog.d("AdvancedFeatureItem", "Key: " + str + " - test case size: " + size);
        if (size > 0) {
            return arrayList;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:326:0x0925, code lost:

       if (r8 == r1) goto L477;
    */
    /* JADX WARN: Removed duplicated region for block: B:329:0x092e  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0930  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0a9d  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0a9f  */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(
            android.content.Context r31,
            com.samsung.android.lib.episode.SourceInfo r32,
            java.lang.String r33) {
        /*
            Method dump skipped, instructions count: 3158
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.AdvancedFeatureItem.getValue(android.content.Context,"
                    + " com.samsung.android.lib.episode.SourceInfo,"
                    + " java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:152:0x0689, code lost:

       if (com.samsung.android.settings.Rune.supportBixbyClient() == false) goto L305;
    */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.SceneResult setValue(
            android.content.Context r21,
            java.lang.String r22,
            com.samsung.android.lib.episode.Scene r23,
            com.samsung.android.lib.episode.SourceInfo r24) {
        /*
            Method dump skipped, instructions count: 3242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.AdvancedFeatureItem.setValue(android.content.Context,"
                    + " java.lang.String, com.samsung.android.lib.episode.Scene,"
                    + " com.samsung.android.lib.episode.SourceInfo):com.samsung.android.lib.episode.SceneResult");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
