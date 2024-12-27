package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TriggerAction extends CommandAction {
    public final CommandAction mTargetCommandAction;
    public final boolean mTriggerState;

    public TriggerAction(Bundle bundle) {
        super(bundle);
        this.mTriggerState = bundle.getBoolean("key_trigger_state");
        if (bundle.containsKey("key_target_command_action")) {
            this.mTargetCommandAction =
                    CommandAction.createActionFromBundle(
                            bundle.getBundle("key_target_command_action"));
        }
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final String getActionTemplateId() {
        return null;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 99;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean("key_trigger_state", this.mTriggerState);
        CommandAction commandAction = this.mTargetCommandAction;
        if (commandAction != null) {
            dataBundle.putBundle("key_target_command_action", commandAction.getDataBundle());
        }
        return dataBundle;
    }
}
