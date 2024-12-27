package com.samsung.android.settings.accessibility.bixby.action.dexterity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatUtils;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class IgnoredRepeatedTouchesAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doChangeAction(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        bundle.putString(
                "result",
                BixbyUtils.doSetFloatValueMaxOrMin(
                        parsedBundle,
                        Settings.Secure.getFloat(
                                context.getContentResolver(), "touch_blocking_period", 0.1f)));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        Settings.Secure.putInt(context.getContentResolver(), "touch_blocking_enabled", z ? 1 : 0);
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result",
                BixbyUtils.getStateAlreadyChecked(
                        parsedBundle.menuValue, IgnoreRepeatUtils.isIgnoreRepeatEnabled(context)));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        Bundle bundle = new Bundle();
        String exclusivepopupDescription =
                BixbyUtils.getExclusivepopupDescription(
                        context,
                        "ignore_repeated_touches",
                        context.getString(R.string.accessibility_ignore_repeat));
        bundle.putString("result", exclusivepopupDescription.isEmpty() ? "false" : "true");
        bundle.putString("description", exclusivepopupDescription);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.dexterity.controllers.IgnoreRepeatPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.accessibility_ignore_repeat;
    }
}
