package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FloatAction extends CommandAction {
    public final float mNewValue;

    public FloatAction(Bundle bundle) {
        super(bundle);
        this.mNewValue = bundle.getFloat("key_new_value");
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 2;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putFloat("key_new_value", this.mNewValue);
        return dataBundle;
    }

    public FloatAction(float f) {
        this.mTemplateId = "float";
        this.mCommandParam = new CommandParam();
        this.mNewValue = f;
    }
}
