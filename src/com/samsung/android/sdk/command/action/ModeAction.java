package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ModeAction extends CommandAction {
    public String mExtraValue;
    public int mNewMode;

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final String getActionTemplateId() {
        return null;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 6;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt("key_new_mode", this.mNewMode);
        String str = this.mExtraValue;
        if (str != null) {
            dataBundle.putString("key_extra_value", str);
        }
        return dataBundle;
    }
}
