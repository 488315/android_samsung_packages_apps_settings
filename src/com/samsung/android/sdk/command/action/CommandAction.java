package com.samsung.android.sdk.command.action;

import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.UUID;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CommandAction {
    public static final AnonymousClass1 ERROR_ACTION;
    public String mActionId;
    public CommandParam mCommandParam;
    public String mTemplateId;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.sdk.command.action.CommandAction$1] */
    static {
        final int i = 0;
        ERROR_ACTION = new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.1
            @Override // com.samsung.android.sdk.command.action.CommandAction
            public final int getActionType() {
                switch (i) {
                    case 0:
                        return 0;
                    default:
                        return 98;
                }
            }
        };
        final int i2 = 1;
        new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.1
            @Override // com.samsung.android.sdk.command.action.CommandAction
            public final int getActionType() {
                switch (i2) {
                    case 0:
                        return 0;
                    default:
                        return 98;
                }
            }
        };
    }

    public CommandAction() {
        this.mTemplateId = ApnSettings.MVNO_NONE;
        this.mCommandParam = new CommandParam();
    }

    public static CommandAction createActionFromBundle(Bundle bundle) {
        AnonymousClass1 anonymousClass1 = ERROR_ACTION;
        if (bundle == null) {
            return anonymousClass1;
        }
        int i = bundle.getInt("key_action_type", 0);
        try {
            if (i == 98) {
                return new DefaultAction(bundle);
            }
            if (i == 99) {
                return new TriggerAction(bundle);
            }
            switch (i) {
                case 1:
                    BooleanAction booleanAction = new BooleanAction(bundle);
                    booleanAction.mNewState = bundle.getBoolean("key_new_state");
                    break;
                case 3:
                    IntentAction intentAction = new IntentAction(bundle);
                    intentAction.mTargetPackage = bundle.getString("key_target_package");
                    intentAction.mTargetClass = bundle.getString("key_target_class");
                    intentAction.mIntentAction = bundle.getString("key_intent_action");
                    intentAction.mIntentExtras = bundle.getBundle("key_intent_extras");
                    break;
                case 4:
                    StringAction stringAction = new StringAction(bundle);
                    stringAction.mNewValue = bundle.getString("key_new_value");
                    break;
                case 5:
                    JSONStringAction jSONStringAction = new JSONStringAction(bundle);
                    jSONStringAction.mNewValue = bundle.getString("key_new_value");
                    break;
                case 6:
                    ModeAction modeAction = new ModeAction(bundle);
                    modeAction.mNewMode = bundle.getInt("key_new_mode");
                    modeAction.mExtraValue = bundle.containsKey("key_extra_value") ? bundle.getString("key_extra_value") : null;
                    break;
            }
            return anonymousClass1;
        }
    }

    public String getActionTemplateId() {
        return this.mTemplateId;
    }

    public abstract int getActionType();

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        if (this.mActionId == null) {
            this.mActionId = UUID.randomUUID().toString();
        }
        bundle.putString("key_action_id", this.mActionId);
        bundle.putInt("key_action_type", getActionType());
        bundle.putString("key_template_id", getActionTemplateId());
        CommandParam commandParam = this.mCommandParam;
        commandParam.getClass();
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("dex_mode", commandParam.mDexMode);
        bundle.putBundle("command_param", bundle2);
        return bundle;
    }

    public CommandAction(Bundle bundle) {
        this.mActionId = bundle.getString("key_action_id");
        this.mTemplateId = bundle.getString("key_template_id");
        CommandParam commandParam = new CommandParam();
        Bundle bundle2 = bundle.getBundle("command_param");
        if (bundle2 == null) {
            Log.w("CommandLib/CommandParam", "commandParamBundle is empty");
        } else {
            commandParam.mDexMode = bundle2.getBoolean("dex_mode", false);
        }
        this.mCommandParam = commandParam;
    }
}
