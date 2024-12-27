package com.samsung.android.settings.accessibility.recommend;

import android.content.Context;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UsingFunctionsProvider {
    public static UsingFunctionsProvider INSTANCE;
    public static final Map USING_FUNCTION_CONTROLLERS =
            Map.ofEntries(
                    Map.entry(
                            "daltonizer_preference_screen",
                            "com.android.settings.accessibility.DaltonizerPreferenceController"),
                    Map.entry(
                            "accessibility_control_timeout_preference_fragment",
                            "com.samsung.android.settings.accessibility.advanced.controller.SecAccessibilityTimeoutPreferenceController"),
                    Map.entry(
                            "camera_flash",
                            "com.samsung.android.settings.accessibility.advanced.flashnotification.SecCameraFlashNotificationPreferenceController"),
                    Map.entry(
                            "screen_flash",
                            "com.samsung.android.settings.accessibility.advanced.flashnotification.SecScreenFlashNotificationPreferenceController"),
                    Map.entry(
                            "assistant_menu_preference",
                            "com.samsung.android.settings.accessibility.dexterity.AssistantMenuPreferenceController"),
                    Map.entry(
                            AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString(),
                            "com.samsung.android.settings.accessibility.dexterity.UniversalSwitchPreferenceController"),
                    Map.entry(
                            "auto_action_preference_screen",
                            "com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceController"),
                    Map.entry(
                            "bounce_keys",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.BounceKeysPreferenceController"),
                    Map.entry(
                            "ignore_repeat_key",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.IgnoreRepeatPreferenceController"),
                    Map.entry(
                            "slow_keys",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.SlowKeysPreferenceController"),
                    Map.entry(
                            "sticky_keys",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.StickyKeysPreferenceController"),
                    Map.entry(
                            "tap_duration_key",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.TapDurationPreferenceController"),
                    Map.entry(
                            "select_long_press_timeout_preference",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.TouchAndHoldPreferenceController"),
                    Map.entry(
                            "voice_access_preference",
                            "com.samsung.android.settings.accessibility.dexterity.controllers.VoiceAccessPreferenceController"),
                    Map.entry(
                            "amplify_ambient_sound",
                            "com.samsung.android.settings.accessibility.hearing.controllers.AmplifyAmbientSoundPreferenceController"),
                    Map.entry(
                            "connected_audio_balance",
                            "com.samsung.android.settings.accessibility.hearing.controllers.ConnectedSoundBalancePreferenceController"),
                    Map.entry(
                            "all_sound_off_key",
                            "com.samsung.android.settings.accessibility.hearing.controllers.MuteAllSoundsPreferenceController"),
                    Map.entry(
                            "call_hearing_aid",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController"),
                    Map.entry(
                            "call_hearing_aid_in_hearing",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SecSecondDepthHearingAidCompatibilityPreferenceController"),
                    Map.entry(
                            "mono_audio_key",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController"),
                    Map.entry(
                            "mono_audio_support_dual_spk_key",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoHasDepthPreferenceController"),
                    Map.entry(
                            "speaker_audio_balance",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SpeakerSoundBalancePreferenceController"),
                    Map.entry(
                            "live_caption",
                            "com.samsung.android.settings.accessibility.hearing.controllers.SecLiveCaptionPreferenceController"),
                    Map.entry(
                            "talkback_preference",
                            "com.samsung.android.settings.accessibility.home.TalkBackPreferenceController"),
                    Map.entry(
                            "color_adjustment_preference_screen",
                            "com.samsung.android.settings.accessibility.vision.controllers.ColorAdjustmentPreferenceController"),
                    Map.entry(
                            "toggle_inversion_preference",
                            "com.samsung.android.settings.accessibility.vision.controllers.ColorInversionPreferenceController"),
                    Map.entry(
                            "color_lens_preference_screen",
                            "com.samsung.android.settings.accessibility.vision.controllers.ColorLensPreferenceController"),
                    Map.entry(
                            "toggle_high_text_contrast_preference",
                            "com.samsung.android.settings.accessibility.vision.controllers.HighContrastFontPreferenceController"),
                    Map.entry(
                            "high_keyboard_contrast_preference_screen",
                            "com.samsung.android.settings.accessibility.vision.controllers.HighContrastKeyboardPreferenceController"),
                    Map.entry(
                            "high_light_buttons_preference_screen",
                            "com.samsung.android.settings.accessibility.vision.controllers.HighLightButtonPreferenceController"),
                    Map.entry(
                            "magnification_preference_screen",
                            "com.samsung.android.settings.accessibility.vision.controllers.MagnificationPreferenceController"),
                    Map.entry(
                            "reduce_bright_colors_preference",
                            "com.samsung.android.settings.accessibility.vision.controllers.ReduceBrightColorsPreferenceController"),
                    Map.entry(
                            "toggle_reduce_transparency",
                            "com.samsung.android.settings.accessibility.vision.controllers.ReduceTransparencyPreferenceController"),
                    Map.entry(
                            "toggle_relumino_preference",
                            "com.samsung.android.settings.accessibility.vision.controllers.ReluminoPreferenceController"),
                    Map.entry(
                            "toggle_remove_animations",
                            "com.samsung.android.settings.accessibility.vision.controllers.RemoveAnimationPreferenceController"),
                    Map.entry(
                            "toggle_audio_description",
                            "com.samsung.android.settings.accessibility.vision.controllers.SecAudioDescriptionPreferenceController"),
                    Map.entry(
                            "speak_keyboard_input_aloud",
                            "com.samsung.android.settings.accessibility.vision.controllers.SpeakKeyboardPreferenceController"));
    public final List usingFunctionItems;

    public UsingFunctionsProvider(final Context context) {
        this.usingFunctionItems =
                USING_FUNCTION_CONTROLLERS.entrySet().stream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.accessibility.recommend.UsingFunctionsProvider$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Map.Entry entry = (Map.Entry) obj;
                                        return SecAccessibilityUtils.getPreferenceController(
                                                context,
                                                (String) entry.getKey(),
                                                (String) entry.getValue());
                                    }
                                })
                        .filter(new UsingFunctionsProvider$$ExternalSyntheticLambda1())
                        .map(new UsingFunctionsProvider$$ExternalSyntheticLambda2())
                        .toList();
    }

    public static UsingFunctionsProvider getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UsingFunctionsProvider(context.getApplicationContext());
        }
        return INSTANCE;
    }
}
