package com.samsung.android.settings.eternal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.provider.items.Recoverable;
import com.samsung.android.settings.eternal.provider.items.RecoverableItemFactory;
import com.samsung.android.settings.eternal.utils.EternalUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SettingsEpisodeProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean mIsUserBuild = "user".equals(Build.TYPE);

    public static List getErrorSceneList(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            SceneResult sceneResult = (SceneResult) it.next();
            if (sceneResult.mResultType == SceneResult.ResultType.RESULT_FAIL) {
                Scene.Builder builder = new Scene.Builder(sceneResult.mKey);
                int ordinal = sceneResult.mErrorType.ordinal();
                builder.addAttribute(
                        ordinal != 1
                                ? ordinal != 2
                                        ? ordinal != 3
                                                ? ordinal != 5
                                                        ? ordinal != 6
                                                                ? ordinal != 8
                                                                        ? ApnSettings.MVNO_NONE
                                                                        : "UNSUPPORTED_DEVICE_TYPE"
                                                                : "PERMISSION"
                                                        : "FEATURE"
                                                : "UNKNOWN"
                                        : "STORAGE_FULL"
                                : "INVALID_DATA",
                        "errorType");
                arrayList.add(builder.build());
            }
        }
        return arrayList;
    }

    public static List getKeySet() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/Settings/Connections/Location");
        arrayList.add("/Settings/Connections/LocationMethod");
        arrayList.add("/Settings/Connections/LocationWifiScan");
        arrayList.add("/Settings/Connections/LocationBluetoothScan");
        arrayList.add("/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Enabled");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/Aggressive",
                "/Settings/Connections/WiFi/Advanced/SwitchToMobileData/IndividualApps",
                "/Settings/Connections/WiFi/Advanced/AutoWifi",
                "/Settings/Connections/WiFi/Advanced/Hotspot20/Enabled");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Connections/WiFi/Advanced/AllowWepNetworks",
                "/Settings/Connections/WiFi/Advanced/WIPS",
                "/Settings/Connections/WiFi/Advanced/WifiOffload",
                "/Settings/Connections/WiFi/Advanced/WifiNotifications");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifi",
                "/Settings/Connections/WiFi/Intelligent/SwitchToBetterWifiWhileScreenIsOn",
                "/Settings/Connections/WiFi/Intelligent/RealtimeDataPriorityMode",
                "/Settings/Connections/Nfc");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Connections/Uwb",
                "/Settings/Connections/NearbyScanning",
                "/Settings/Connections/PrivateDnsMode",
                "/Settings/Connections/PrivateDnsSpecifier");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Connections/VpnProfiles",
                "/Settings/Connections/Bluetooth/Advanced/MusicShare",
                "/Settings/Connections/Bluetooth/Advanced/RingtoneSync",
                "/Settings/Connections/Bluetooth/Moremenu/Auracast");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/AppIconBadge",
                "/Settings/Notifications/AppIconBadgeStyle",
                "/Settings/Notifications/AppIconBadgeShowNotifications",
                "/Settings/Notifications/ShowSnoozeOption");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/DndDuration",
                "/Settings/Notifications/AdvancedSettings/NotificationHistory",
                "/Settings/Notifications/AdvancedSettings/FloatingIcons",
                "/Settings/Notifications/AdvancedSettings/NetworkSpeed");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/NotificationPopUpStyle",
                "/Settings/Notifications/BriefPopupSettings/ShowEvenWhileScreenIsOff",
                "/Settings/Notifications/BriefPopupSettings/ColorByKeyword",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/Effect");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorType",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorIndex",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorCustom",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorRecent");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedThickness",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedTransparency",
                "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedDuration",
                "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowReminderTime");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowVibration",
                "/Settings/Notifications/AdvancedSettings/NotificationReminderSelectable",
                "/Settings/Notifications/LockscreenSettings/ShowContentWhenUnlocked",
                "/Settings/Notifications/SortNotifications");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/AdvancedSettings/ShowAppIcon",
                "/Settings/Notifications/AdvancedSettings/ShowNotificationCategorySettings",
                "/Settings/Notifications/AppNotification/FavoriteAppNotifications",
                "Settings/Notifications/StatusbarNotificationStyle");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Notifications/GalaxyAI/ChatAssist/SuggestResponses",
                "/Settings/Advanced/AutoScreenOn",
                "/Settings/Advanced/SmartStay",
                "/Settings/Advanced/PalmSwipeToCapture");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/DirectCall",
                "/Settings/Advanced/SmartAlert",
                "/Settings/Advanced/EasyMute",
                "/Settings/Advanced/SwipeToCallOrSendMessage");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/DirectShare",
                "/Settings/Advanced/LiftToWake",
                "/Settings/Advanced/DoubleTapToWake",
                "/Settings/Advanced/DoubleTapToSleep");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/PalmTouchToSleep",
                "/Settings/Advanced/OneHandedMode",
                "/Settings/Advanced/FingerSensorGestures",
                "/Settings/Advanced/CoverTextDirection");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/VideoEnhancerSwitch",
                "/Settings/Advanced/VideoEnhancerApp",
                "/Settings/Advanced/ActiveKeyShortPress",
                "/Settings/Advanced/ActiveKeyLongPress");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/ActiveKeyOnLockScreen",
                "/Settings/Advanced/XcoverKeyPtt",
                "/Settings/Advanced/XcoverKeyDedicatedApp",
                "/Settings/Advanced/SideKeyDoublePressSwitch");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/SideKeyDoublePressOpenAppValue",
                "/Settings/Advanced/SideKeyDoublePressType",
                "/Settings/Advanced/SideKeyDoublePress",
                "/Settings/Advanced/SideKeyLongPressType");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/XcoverTopKeyShortPress",
                "/Settings/Advanced/XcoverTopKeyLongPress",
                "/Settings/Advanced/XcoverTopKeyOnLockScreen",
                "/Settings/Advanced/XcoverTopKeyDedicatedApp");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/ContinueAppsOnOtherDevices",
                "/Settings/Advanced/SmartPopupView",
                "/Settings/Advanced/LabsMultiWindowAllApps",
                "/Settings/Advanced/LabsRotateAllApps");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/LabsRotateApps",
                "/Settings/Advanced/LabsLandScapeAllApps",
                "/Settings/Advanced/LabsLandScapeViewForPortraitApps",
                "/Settings/Advanced/LabsAspectRatioApps");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/LabsAppSplitView",
                "/Settings/Advanced/FlexModePanelEnabled",
                "/Settings/Advanced/FlexModeScrollWheelPosition",
                "/Settings/Advanced/LabsFlexModePanel");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/LabsFullScreenInSplitView",
                "/Settings/Advanced/LabsMultiWindowMenuInFullScreen",
                "/Settings/Advanced/LabsSwipePopupView",
                "/Settings/Advanced/SwipePopupViewCornerAreaLevel");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/LabsSwipeSplitView",
                "/Settings/Advanced/PreventOnlineProcessing",
                "/Settings/Advanced/LabsSynchronizingMainNavigationStyleOnWidget",
                "/Settings/Advanced/LabsAppAllowedOnCoverScreen");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Advanced/DarkModeApps",
                "/Settings/Sound/NotificationSound",
                "/Settings/Sound/NotificationSoundSim2",
                "/Settings/Sound/ChargingSound");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/KeyboardSound",
                "/Settings/Sound/VibrationWhileRinging",
                "/Settings/Sound/UseVolumeKeyForMedia",
                "/Settings/Sound/VoipExtraVolume");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/VibrationPattern",
                "/Settings/Sound/NotificationVibrationPattern",
                "/Settings/Sound/TouchSound",
                "/Settings/Sound/ScreenLockSound");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/VibrationFeedback",
                "/Settings/Sound/DialingKeypadTone",
                "/Settings/Sound/KeyboardVibration",
                "/Settings/Sound/DialingKeypadVibration");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/NavigationGesturesVibration",
                "/Settings/Sound/CameraFeedbackVibration",
                "/Settings/Sound/ChargingVibration",
                "/Settings/Sound/RingerMode");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/SeparateAppSoundName",
                "/Settings/Sound/SeparateAppSoundDevice",
                "/Settings/Sound/SeparateMultiAppsSoundName",
                "/Settings/Sound/SeparateMultiAppSoundDevice");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/VibrationSoundEnabled",
                "/Settings/Sound/VibrationPatternSim2",
                "/Settings/Sound/SyncWithRingtone",
                "/Settings/Sound/SyncWithRingtoneSim2");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/SyncWithNotification",
                "/Settings/Sound/VolumeLimiterLevel",
                "/Settings/Sound/VolumeLimiterPassword",
                "/Settings/Sound/SystemSoundTheme");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/RingVolume",
                "/Settings/Sound/MediaVolume",
                "/Settings/Sound/NotificationVolume",
                "/Settings/Sound/SystemVolume");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/BixbyVolume",
                "/Settings/Sound/WaitingToneVolume",
                "/Settings/Sound/AccessibilityVolume",
                "/Settings/Sound/AlarmVolume");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/CallVibrationMagnitude",
                "/Settings/Sound/NotificationVibrationMagnitude",
                "/Settings/Sound/TouchVibrationMagnitude",
                "/Settings/Sound/HardPressVibrationMagnitude");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Sound/MediaVibrationMagnitude",
                "/Settings/Sound/ChromeCastModeEnabled",
                "/Settings/Display/FontSize",
                "/Settings/Display/FontStyle");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/FontBold",
                "/Settings/Display/AutoBrightness",
                "/Settings/Display/BluelightFilter",
                "/Settings/Display/AdaptiveColorTone");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/ShowRecentNotificationOnly",
                "/Settings/Display/ButtonLayout",
                "/Settings/Display/ScreenTimeout",
                "/Settings/Display/BlockAccidentalTouches");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/ScreenZoom",
                "/Settings/Display/NightMode",
                "/Settings/Display/RefreshRate",
                "/Settings/Display/ScreenMode");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/ScreenResolution",
                "/Settings/Display/NavigationTypes",
                "/Settings/Display/NavigationGestureHint",
                "/Settings/Display/ScreenSaver");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/TouchSensetivity",
                "/Settings/Display/ShowChargingInformation",
                "/Settings/Display/ShowButtonToHideKeyboard",
                "/Settings/Display/BlockGesturesWithSPen");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/ButtonPosition",
                "/Settings/Display/SwitchAppsWhenHintHidden",
                "/Settings/Display/SwitchAppsToFrontScreen",
                "/Settings/Display/RotateSuggestionEnabled");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/EasyModeSwitch",
                "/Settings/Display/TaskBar",
                "/Settings/Display/TaskbarRecentAppsCount",
                "/Settings/Display/TaskbarRecentApps");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Display/TaskbarType",
                "/Settings/Display/FullScreenApps",
                "/Settings/Display/CameraCutout",
                "/Settings/LockScreen/RoamingClock");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/RoamingClockPosition",
                "/Settings/LockScreen/RoamingClockHomeCity",
                "/Settings/LockScreen/FaceWidget",
                "/Settings/LockScreen/FaceWidgetPosition");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/ShowNotification",
                "/Settings/LockScreen/ShowNotificationOnKeyguard",
                "/Settings/LockScreen/HideContent",
                "/Settings/LockScreen/NotificationIconOnly");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/Transparency",
                "/Settings/LockScreen/TransparencyDarkMode",
                "/Settings/LockScreen/ShowOnAOD",
                "/Settings/LockScreen/Shortcut");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/ShortcutLayout",
                "/Settings/LockScreen/LunarCalendar",
                "/Settings/LockScreen/HijriCalendar",
                "/Settings/LockScreen/AutoReverseTextColor");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/ContactInformation",
                "/Settings/LockScreen/NotificationsToShow",
                "/Settings/LockScreen/FaceWidgetCover",
                "/Settings/LockScreen/ShowShortcut");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/VisiblePattern",
                "/Settings/LockScreen/LockAfterTimeout",
                "/Settings/LockScreen/PowerInstantlyLocks",
                "/Settings/LockScreen/LockInstantlyWithFolding");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/LockScreen/AutoFactoryReset",
                "/Settings/LockScreen/LockNetworkAndSecurity",
                "/Settings/LockScreen/ShowLockDownOption",
                "/Settings/Security/MakePasswordVisible");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Security/PinWindows",
                "/Settings/Security/AskPinBeforeUnpinning",
                "/Settings/Security/GalaxySystemAppUpdate",
                "/Settings/Security/GalaxySystemAppUpdateUseWiFiOnly");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Accessibility/PreferredEngine",
                "/Settings/Accessibility/Rate",
                "/Settings/Accessibility/Pitch",
                "/Settings/General/ShowKeyboardButton");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/ChangeLanguageShortcut",
                "/Settings/General/ShowOnScreenKeyboard",
                "/Settings/General/Autofill",
                "/Settings/General/CredentialServicePrimary");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/CredentialServices",
                "/Settings/General/TimeFormat",
                "/Settings/General/AutoTime",
                "/Settings/General/AutoTimeZone");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/RedirectVibration",
                "/Settings/General/LocationTimeZoneDetection",
                "/Settings/General/SystemLocale",
                "/Settings/General/PrimaryMouseKey");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/MouseSecondaryKey",
                "/Settings/General/MouseMiddleKey",
                "/Settings/General/AdditionalKey1",
                "/Settings/General/AdditionalKey2");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/PointerSpeed",
                "/Settings/General/WheelScrollingSpeed",
                "/Settings/General/EnhancePointerPrecision",
                "/Settings/General/PointerSize");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/PointerColor",
                "/Settings/General/AppShortcutsCommandA",
                "/Settings/General/AppShortcutsCommandB",
                "/Settings/General/AppShortcutsCommandC");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/AppShortcutsCommandD",
                "/Settings/General/AppShortcutsCommandE",
                "/Settings/General/AppShortcutsCommandF",
                "/Settings/General/AppShortcutsCommandH");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/AppShortcutsCommandI",
                "/Settings/General/AppShortcutsCommandJ",
                "/Settings/General/AppShortcutsCommandK",
                "/Settings/General/AppShortcutsCommandM");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/AppShortcutsCommandP",
                "/Settings/General/AppShortcutsCommandR",
                "/Settings/General/AppShortcutsCommandS",
                "/Settings/General/AppShortcutsCommandZ");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/General/AppLanguage",
                "/Settings/Accounts/SamsungAccountID",
                "/Settings/Accounts/GoogleAccountID",
                "/Settings/Accounts/GoogleCoreControl");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Accounts/AutoSyncData",
                "/Settings/Biometrics/FaceOpenEyes",
                "/Settings/Biometrics/FaceStayOnLockScreen",
                "/Settings/Biometrics/FaceBrightenScreen");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Biometrics/FaceRecognizeMask",
                "/Settings/Biometrics/FingerShowIconAod",
                "/Settings/Biometrics/FingerTapToShowIcon",
                "/Settings/Biometrics/FingerShowIconLockscreen");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "/Settings/Biometrics/FingerShowAnimation",
                "/Settings/Biometrics/FingerStayOnLockScreen",
                "/Settings/Biometrics/UnlockTransitionEffect",
                "/Settings/ManageStorage/UnusedApps");
        return arrayList;
    }

    public static List getSceneListFromBundle(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        for (String str : bundle.keySet()) {
            new Bundle();
            ArrayList arrayList2 = new ArrayList();
            Bundle bundle2 = bundle.getBundle(str);
            if (!arrayList2.isEmpty()) {
                bundle2.putString(
                        "compressedEternalItems", EpisodeUtils.convertListToString(arrayList2));
            }
            Scene scene = null;
            if (bundle2 != null && !bundle2.isEmpty() && !TextUtils.isEmpty(str)) {
                Scene scene2 = new Scene();
                scene2.mSceneKey = str;
                scene2.mSceneValue = bundle2;
                scene2.mIsDefault = null;
                scene2.mDefaultType = (byte) 0;
                scene = scene2;
            }
            arrayList.add(scene);
        }
        return arrayList;
    }

    public static SourceInfo getSourceInfoFromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(FieldName.VERSION);
        String string = bundle2 != null ? bundle2.getString(FieldName.VERSION) : null;
        Bundle bundle3 = bundle.getBundle("deviceType");
        String string2 = bundle3 != null ? bundle3.getString("value") : null;
        bundle.remove(FieldName.VERSION);
        bundle.remove("deviceType");
        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.mRestoreViaFastTrack = false;
        sourceInfo.mRequestFrom = -1;
        sourceInfo.mPackageList = null;
        sourceInfo.mManufacturer = -1;
        sourceInfo.mDeviceType = string2;
        sourceInfo.mVersion = string;
        sourceInfo.mDTDVersion = ApnSettings.MVNO_NONE;
        return sourceInfo;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:142:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x04b3  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle call(
            java.lang.String r21, java.lang.String r22, android.os.Bundle r23) {
        /*
            Method dump skipped, instructions count: 1492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.SettingsEpisodeProvider.call(java.lang.String,"
                    + " java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public final Recoverable getRecoverableItem(String str) {
        String[] split;
        if (str != null && !str.isEmpty() && (split = str.split("/")) != null && split.length > 2) {
            try {
                return RecoverableItemFactory.getItem(getContext(), split[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    public final String getVersion() {
        double d;
        if (EternalUtils.isSemAvailable(getContext())) {
            int i = Build.VERSION.SEM_PLATFORM_INT - 90000;
            d =
                    Double.parseDouble(
                            (i / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                    + "."
                                    + ((i % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100));
        } else {
            d = 1.0d;
        }
        return String.valueOf(d);
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public final List setValues(SourceInfo sourceInfo, List list) {
        if (list != null) {
            list.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.eternal.provider.SettingsEpisodeProvider$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SettingsEpisodeProvider settingsEpisodeProvider =
                                    SettingsEpisodeProvider.this;
                            int i = SettingsEpisodeProvider.$r8$clinit;
                            settingsEpisodeProvider.getClass();
                            EternalFileLog.i(
                                    "SettingsEpisodeProvider",
                                    "setValues() " + ((Scene) obj).mSceneKey);
                        }
                    });
        }
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Scene scene = (Scene) it.next();
                String str = scene.mSceneKey;
                String[] split = str.split("/");
                if (split != null && split.length > 2) {
                    try {
                        Recoverable item = RecoverableItemFactory.getItem(getContext(), split[2]);
                        if (item != null) {
                            if (scene.isDefault() && item.followRestoreSkipPolicy(scene)) {
                                SceneResult.Builder builder = new SceneResult.Builder(str);
                                builder.mResultType = SceneResult.ResultType.RESULT_SKIP;
                                builder.mErrorType = SceneResult.ErrorType.DEFAULT_VALUE;
                                arrayList.add(builder.build());
                            } else {
                                SceneResult value =
                                        item.setValue(getContext(), str, scene, sourceInfo);
                                if (value != null) {
                                    arrayList.add(value);
                                }
                            }
                        }
                    } catch (Exception e) {
                        CloneBackend$$ExternalSyntheticOutline0.m(
                                e,
                                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                        "setValueViaEternal() exception ", str, "\n"),
                                "SettingsEpisodeProvider");
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
