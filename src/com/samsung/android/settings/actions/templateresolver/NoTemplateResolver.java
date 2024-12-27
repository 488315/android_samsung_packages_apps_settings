package com.samsung.android.settings.actions.templateresolver;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NoTemplateResolver implements CommandTemplateResolver {
    @Override // com.samsung.android.settings.actions.templateresolver.CommandTemplateResolver
    public final void resolve(
            CommandAction commandAction,
            BasePreferenceController basePreferenceController,
            SettingsCommandActionHandler.AnonymousClass1 anonymousClass1) {
        String message;
        int i;
        try {
            int actionType = commandAction.getActionType();
            if (actionType == 5) {
                String str = ((JSONStringAction) commandAction).mNewValue;
                ControlValue.Builder builder =
                        new ControlValue.Builder(
                                basePreferenceController.getPreferenceKey(),
                                basePreferenceController.getControlType());
                builder.setValue(str);
                if (basePreferenceController.setValue(builder.build()).mResultCode
                        == ControlResult.ResultCode.FAIL) {
                    throw new CommandTemplateResolver.InvalidActionException("unknown");
                }
            } else {
                if (actionType != 98) {
                    throw new CommandTemplateResolver.InvalidActionException("invalid_action");
                }
                basePreferenceController.runDefaultAction();
            }
            i = 1;
            message = null;
        } catch (CommandTemplateResolver.InvalidActionException e) {
            message = e.getMessage();
            i = 2;
        }
        anonymousClass1.onResolved(i, message);
    }
}
