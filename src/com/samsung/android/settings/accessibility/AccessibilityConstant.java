package com.samsung.android.settings.accessibility;

import android.content.ComponentName;
import android.net.Uri;
import android.view.HapticFeedbackConstants;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.voiceinput.VoiceInputUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityConstant {
    public static final Uri URI_ACCESSIBILITY_PROVIDER =
            Uri.parse("content://com.samsung.accessibility.provider/a11ysettings");
    public static final ComponentName COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.AccessibilityHomepageActivityShortcut");
    public static final ComponentName COMPONENT_NAME_GOOGLE_TALKBACK =
            ComponentName.createRelative("com.google.android.marvin.talkback", ".TalkBackService");
    public static final ComponentName COMPONENT_NAME_SAMSUNG_TALKBACK =
            new ComponentName(
                    "com.samsung.android.accessibility.talkback",
                    "com.samsung.android.marvin.talkback.TalkBackService");
    public static final ComponentName COMPONENT_NAME_SAMSUNG_TALKBACK_SETTING_ACTIVITY =
            new ComponentName(
                    "com.samsung.android.accessibility.talkback",
                    "com.android.talkback.TalkBackPreferencesActivity");
    public static final ComponentName COMPONENT_NAME_SELECT_TO_SPEAK =
            new ComponentName(
                    "com.samsung.android.accessibility.talkback",
                    "com.google.android.accessibility.selecttospeak.SelectToSpeakService");
    public static final ComponentName COMPONENT_NAME_GOOGLE_SELECT_TO_SPEAK =
            ComponentName.createRelative(
                    "com.google.android.marvin.talkback",
                    "com.google.android.accessibility.selecttospeak.SelectToSpeakService");
    public static final ComponentName COMPONENT_NAME_ACCESSIBILITY_MENU =
            ComponentName.createRelative(
                    "com.google.android.marvin.talkback",
                    "com.google.android.accessibility.accessibilitymenu.AccessibilityMenuService");
    public static final ComponentName COMPONENT_NAME_SWITCH_ACCESS =
            ComponentName.createRelative(
                    "com.google.android.accessibility.switchaccess",
                    "com.android.switchaccess.SwitchAccessService");
    public static final ComponentName COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD =
            ComponentName.createRelative(
                    "com.samsung.android.honeyboard",
                    ".settings.swipetouchandfeedback.speakkeyboardinputaloud.SpeakKeyboardInputAloudShortcut");
    public static final ComponentName COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.MagnifierCameraShortcut");
    public static final ComponentName COMPONENT_NAME_HIGH_CONTRAST_FONT_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.HighContrastFontsShortcut");
    public static final ComponentName COMPONENT_NAME_COLOR_LENS_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.ColorLensShortcut");
    public static final ComponentName COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.ColorAdjustmentShortcut");
    public static final ComponentName COMPONENT_NAME_RELUMINO_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.ReluminoShortcut");
    public static final ComponentName COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.HearingAidSupportShortcut");
    public static final ComponentName COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.AmplifyShortcut");
    public static final ComponentName COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SERVICE =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".amplifyambientsound.AmplifyAmbientSoundService");
    public static final ComponentName COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
    public static final ComponentName COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT =
            new ComponentName(
                    "com.google.audio.hearing.visualization.accessibility.scribe",
                    "com.google.audio.hearing.visualization.accessibility.dolphin.ui.visualizer.TimelineActivity");
    public static final ComponentName COMPONENT_NAME_LIVE_TRANSCRIBE =
            ComponentName.createRelative(
                    "com.google.audio.hearing.visualization.accessibility.scribe",
                    ".SpeechToTextAccessibilityService");
    public static final ComponentName COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT =
            ComponentName.createRelative(
                    "com.google.audio.hearing.visualization.accessibility.scribe", ".MainActivity");
    public static final ComponentName COMPONENT_NAME_UNIVERSAL_SWITCH =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".universalswitch.UniversalSwitchService");
    public static final ComponentName COMPONENT_NAME_ASSISTANT_MENU =
            ComponentName.createRelative(
                    "com.samsung.accessibility",
                    ".assistantmenu.serviceframework.AssistantMenuService");
    public static final ComponentName COMPONENT_NAME_VOICE_ACCESS =
            new ComponentName(
                    "com.google.android.apps.accessibility.voiceaccess",
                    "com.google.android.apps.accessibility.voiceaccess.JustSpeakService");
    public static final ComponentName COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT =
            ComponentName.createRelative(
                    "com.samsung.accessibility", ".shortcut.InteractionControlShortcut");
    public static final ComponentName COMPONENT_NAME_BIXBY_AGENT =
            new ComponentName(
                    VoiceInputUtils.BIXBY_PACKAGE_NAME,
                    "com.samsung.android.bixby.agent2.actorcore.ActorNormalAccessibilityService");
    public static final int HAPTIC_CONSTANT_CURSOR_MOVE =
            HapticFeedbackConstants.semGetVibrationIndex(41);
}
