package com.samsung.android.settings.actions.templateresolver;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.sdk.command.action.BooleanAction;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ToggleTemplateResolver implements CommandTemplateResolver {
    public static void setToggleValue(
            BasePreferenceController basePreferenceController, boolean z) {
        if (!(basePreferenceController instanceof TogglePreferenceController)) {
            throw new CommandTemplateResolver.InvalidActionException("invalid_action");
        }
        TogglePreferenceController togglePreferenceController =
                (TogglePreferenceController) basePreferenceController;
        if (togglePreferenceController.getThreadEnabled() == z) {
            throw new CommandTemplateResolver.InvalidActionException("already_set");
        }
        if (!togglePreferenceController.setCheckedForAction(z)) {
            throw new CommandTemplateResolver.InvalidActionException("unknown");
        }
    }

    @Override // com.samsung.android.settings.actions.templateresolver.CommandTemplateResolver
    public final void resolve(
            CommandAction commandAction,
            BasePreferenceController basePreferenceController,
            SettingsCommandActionHandler.AnonymousClass1 anonymousClass1) {
        String message;
        int i;
        try {
            setToggleValue(basePreferenceController, ((BooleanAction) commandAction).mNewState);
            i = 1;
            message = null;
        } catch (CommandTemplateResolver.InvalidActionException e) {
            message = e.getMessage();
            i = 2;
        }
        anonymousClass1.onResolved(i, message);
    }
}
