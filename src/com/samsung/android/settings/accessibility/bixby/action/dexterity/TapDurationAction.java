package com.samsung.android.settings.accessibility.bixby.action.dexterity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityNotificationUtil;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.accessibility.dexterity.TapDurationUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TapDurationAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doChangeAction(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result",
                BixbyUtils.doSetFloatValueMaxOrMin(
                        parsedBundle, TapDurationUtils.getTapDurationValue(context)));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        Settings.Secure.putInt(context.getContentResolver(), "tap_duration_enabled", z ? 1 : 0);
        AccessibilityNotificationUtil.updateTapDurationNotification(context);
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
                        parsedBundle.menuValue, TapDurationUtils.isTapDurationEnabled(context)));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        Bundle bundle = new Bundle();
        String exclusivepopupDescription =
                BixbyUtils.getExclusivepopupDescription(
                        context, "tap_duration", context.getString(R.string.tap_duration_title));
        bundle.putString("result", exclusivepopupDescription.isEmpty() ? "false" : "true");
        bundle.putString("description", exclusivepopupDescription);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.dexterity.controllers.TapDurationPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.tap_duration_title;
    }
}
