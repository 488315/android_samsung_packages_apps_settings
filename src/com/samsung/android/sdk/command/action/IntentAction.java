package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class IntentAction extends CommandAction {
    public String mIntentAction;
    public Bundle mIntentExtras;
    public String mTargetClass;
    public String mTargetPackage;

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 3;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_target_package", this.mTargetPackage);
        dataBundle.putString("key_target_class", this.mTargetClass);
        dataBundle.putString("key_intent_action", this.mIntentAction);
        dataBundle.putBundle("key_intent_extras", this.mIntentExtras);
        return dataBundle;
    }
}
