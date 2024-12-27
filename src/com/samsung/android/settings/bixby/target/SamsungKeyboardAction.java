package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SamsungKeyboardAction extends Action {
    public final String retValue;

    public SamsungKeyboardAction(Context context, Bundle bundle) {
        super(context, bundle);
        this.retValue =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        Intent intent = new Intent();
        intent.setFlags(872415232);
        String str2 = this.retValue;
        if ("com.samsung.android.honeyboard".equals(str2)) {
            DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                    "com.samsung.android.honeyboard",
                    "com.samsung.android.honeyboard.settings.common.HoneyBoardSettingsActivity",
                    intent);
        } else if ("com.sec.android.inputmethod".equals(str2)) {
            DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                    "com.sec.android.inputmethod",
                    "com.sec.android.inputmethod.SamsungKeypadSettings",
                    intent);
        }
        Utils.setTaskIdToIntent(intent, getTaskId());
        if (intent.getComponent() != null) {
            launchSettings(intent, null);
            str = "success";
        } else {
            str = "fail";
        }
        return Action.createResult(str);
    }
}
