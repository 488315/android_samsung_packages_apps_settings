package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class JSONStringAction extends CommandAction {
    public String mNewValue;

    public JSONStringAction(String str) {
        this.mNewValue = str;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 5;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_new_value", this.mNewValue);
        return dataBundle;
    }
}
