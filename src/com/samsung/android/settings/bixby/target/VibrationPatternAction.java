package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.sec.ims.im.ImIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibrationPatternAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setClassName(
                this.mContext.getPackageName(),
                "com.samsung.android.settings.asbase.vibration.VibPickerContainer");
        intent.putExtra(ImIntent.Extras.EXTRA_FROM, "bixby");
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return ((Utils.isVoiceCapable(this.mContext) || Rune.supportSoftphone())
                        && VibUtils.hasVibrator(this.mContext))
                ? Action.createResult("true")
                : Action.createResult("false");
    }
}
