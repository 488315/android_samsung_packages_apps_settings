package com.samsung.android.settings.accessibility.bixby.action.advanced;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TimeToTakeAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        return isApplicationRestricted(context)
                ? AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "DeviceChangeNeeded")
                : super.doGetSupportFeature(context, parsedBundle);
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doSetAction(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        int parseInt = Integer.parseInt(parsedBundle.menuValue);
        Settings.Secure.putString(
                context.getContentResolver(),
                "accessibility_non_interactive_ui_timeout_ms",
                Integer.toString(parseInt));
        Settings.Secure.putString(
                context.getContentResolver(),
                "accessibility_interactive_ui_timeout_ms",
                Integer.toString(parseInt));
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.advanced.controller.SecAccessibilityTimeoutPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final List getTargetRestrictionKeys() {
        return List.of("accessibility_advanced_settings");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.accessibility_control_timeout_preference_title;
    }
}
