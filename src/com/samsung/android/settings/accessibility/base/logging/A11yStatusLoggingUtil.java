package com.samsung.android.settings.accessibility.base.logging;

import android.content.ComponentName;
import android.content.Context;

import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.accessibility.PreferredShortcuts;

import com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionHandler;

import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class A11yStatusLoggingUtil {
    public static final ComponentName A11Y_LOGGING_RECEIVER_COMPONENT_NAME =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".salogging.StatusLogBroadcastReceiver");
    public static final Set PROVIDER_CLASSNAME_SET =
            Set.of(
                    (Object[])
                            new String[] {
                                "com.samsung.android.settings.accessibility.home.TalkBackPreferenceController",
                                "com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController",
                                "com.samsung.android.settings.accessibility.home.UsingFunctionsExpandablePreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.autoaction.CornerActionTypePreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.BounceKeysPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.IgnoreRepeatPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.SlowKeysPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.StickyKeysPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.TapDurationPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.TouchAndHoldPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.VoiceAccessPreferenceController",
                                "com.samsung.android.settings.accessibility.dexterity.controllers.EasyScreenTurnOnPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.SecAudioDescriptionPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ColorLensPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ColorAdjustmentPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.HighContrastFontPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.HighContrastThemePreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ReluminoPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.HighLightButtonPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.RemoveAnimationPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ReduceTransparencyPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.CursorThicknessPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.MagnifierCameraPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ColorInversionPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.ReduceBrightColorsPreferenceController",
                                "com.samsung.android.settings.accessibility.vision.controllers.MagnificationPreferenceController",
                                "com.android.settings.accessibility.DaltonizerPreferenceController",
                                "com.android.settings.accessibility.ReduceBrightColorsPersistencePreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.SecLiveCaptionPreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoHasDepthPreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.MuteAllSoundsPreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.AmplifyAmbientSoundPreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.SpeakerSoundBalancePreferenceController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.AdaptSoundController",
                                "com.samsung.android.settings.accessibility.hearing.controllers.SecAccessibilityHearingAidPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.controller.SecAccessibilityTimeoutPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.shortcut.FloatingMenuOpacityPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonAppPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonLocationNavigationBarPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.shortcut.SideAndVolumeUpGuidePreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.shortcut.VolumeUpAndDownGuidePreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationAllAppsPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppsPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.flashnotification.SecCameraFlashNotificationPreferenceController",
                                "com.samsung.android.settings.accessibility.advanced.flashnotification.SecScreenFlashNotificationPreferenceController"
                            });

    public static Map getShortcutStatusMap(Context context, ComponentName componentName) {
        boolean hasValuesInSettings =
                AccessibilityUtil.hasValuesInSettings(context, 543, componentName);
        int retrieveUserShortcutType =
                PreferredShortcuts.retrieveUserShortcutType(
                        context, 0, componentName.flattenToString());
        return Map.of(
                ReduceBrightnessRoutineActionHandler.KEY_SWITCH,
                hasValuesInSettings ? "on" : "off",
                "a11yBtn",
                (retrieveUserShortcutType & 1) != 0 ? "y" : "n",
                "sideAndVolUp",
                (retrieveUserShortcutType & 512) != 0 ? "y" : "n",
                "volUpAndDown",
                (retrieveUserShortcutType & 2) != 0 ? "y" : "n",
                "tripleTap",
                "n");
    }

    public static String getSwitchStatus(boolean z) {
        return z ? "On" : "Off";
    }
}
