package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HardPressAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        if (Utils.isTalkBackEnabled(this.mContext)) {
            str = "temporary_unavailable";
        } else {
            Intent intent = new Intent();
            intent.addFlags(268468224);
            intent.setClassName(
                    this.mContext.getPackageName(),
                    "com.android.settings.Settings$NavigationBarHardPressSettingsActivity");
            Utils.setTaskIdToIntent(intent, getTaskId());
            launchSettings(intent, null);
            str = "success";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return Rune$$ExternalSyntheticOutline0.m(
                        "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME",
                        "SupportForceTouch")
                ? Action.createResult("true")
                : Action.createResult("false");
    }
}
