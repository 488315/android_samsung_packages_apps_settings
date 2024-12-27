package com.samsung.android.settings.accessibility.recommend;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityNotificationUtil;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RecommendedForYouReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null
                || !"com.samsung.accessibility.action.UPDATE_PROFILE".equals(intent.getAction())
                || intent.getExtras() == null) {
            return;
        }
        final String stringExtra = intent.getStringExtra("component");
        DialogFragment$$ExternalSyntheticOutline0.m(
                "onReceive - componentName : ", stringExtra, "RecommendedForYouReceiver");
        if (AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK
                        .flattenToString()
                        .equals(stringExtra)
                || AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK
                        .flattenToString()
                        .equals(stringExtra)) {
            boolean anyMatch =
                    AccessibilityUtils.getEnabledServicesFromSettings(context).stream()
                            .anyMatch(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.accessibility.recommend.RecommendedForYouReceiver$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            String str = stringExtra;
                                            int i = RecommendedForYouReceiver.$r8$clinit;
                                            return ((ComponentName) obj)
                                                    .flattenToString()
                                                    .equals(str);
                                        }
                                    });
            int i = RecommendedForYouUtils.$r8$clinit;
            if (SecAccessibilityUtils.getAccessibilitySharedPreferences(context)
                                    .getLong("enable_time_screen_reader", 0L)
                            <= 0
                    || !anyMatch) {
                RecommendedForYouUtils.updateFeatureConditionForProfile(
                        context, "enable_time_screen_reader", "use_count_screen_reader", anyMatch);
                return;
            }
            return;
        }
        if (AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME
                .flattenToString()
                .equals(stringExtra)) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    context, null, "use_count_magnification", true);
            return;
        }
        if (AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME
                .flattenToString()
                .equals(stringExtra)) {
            if (Settings.Secure.getInt(
                            context.getContentResolver(),
                            "accessibility_display_inversion_enabled",
                            0)
                    == 1) {
                RecommendedForYouUtils.updateFeatureConditionForProfile(
                        context, null, "use_count_color_inversion", true);
                return;
            }
            return;
        }
        if (AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT
                .flattenToString()
                .equals(stringExtra)) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    context,
                    "enable_time_mute_all_sounds",
                    "use_count_mute_all_sounds",
                    Settings.Global.getInt(context.getContentResolver(), "all_sound_off", 0) == 1);
            return;
        }
        if (AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                .flattenToString()
                .equals(stringExtra)) {
            if (Settings.System.getInt(context.getContentResolver(), "run_amplify_ambient_sound", 0)
                    == 1) {
                RecommendedForYouUtils.updateFeatureConditionForProfile(
                        context, null, "use_count_amplify_ambient_sound", true);
                return;
            }
            return;
        }
        ComponentName componentName = AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH;
        if (componentName.flattenToString().equals(stringExtra)) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    context,
                    "enable_time_universal_switch",
                    "use_count_universal_switch",
                    AccessibilityUtils.getEnabledServicesFromSettings(context)
                            .contains(componentName));
            return;
        }
        ComponentName componentName2 = AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS;
        if (componentName2.flattenToString().equals(stringExtra)) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    context,
                    "enable_time_voice_access",
                    "use_count_voice_access",
                    AccessibilityUtils.getEnabledServicesFromSettings(context)
                            .contains(componentName2));
        } else if ("tap_duration".equals(stringExtra)) {
            AccessibilityNotificationUtil.updateTapDurationNotification(context);
        }
    }
}
