package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UserManualAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent userManualSearchURLIntent =
                Utils.getUserManualSearchURLIntent(this.mContext, getValue());
        userManualSearchURLIntent.addFlags(268435456);
        Utils.setTaskIdToIntent(userManualSearchURLIntent, getTaskId());
        launchSettings(userManualSearchURLIntent, null);
        return Action.createResult("success");
    }
}
