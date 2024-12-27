package com.samsung.android.settings.actions.templateresolver;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.StringAction;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SingleChoiceTemplateResolver implements CommandTemplateResolver {
    public static void setSingleChoiceValue(
            BasePreferenceController basePreferenceController, String str) {
        if (!(basePreferenceController instanceof SecSingleChoicePreferenceController)) {
            throw new CommandTemplateResolver.InvalidActionException("invalid_action");
        }
        SecSingleChoicePreferenceController secSingleChoicePreferenceController =
                (SecSingleChoicePreferenceController) basePreferenceController;
        ArrayList<String> entryValues = secSingleChoicePreferenceController.getEntryValues();
        if (entryValues == null || entryValues.isEmpty()) {
            throw new CommandTemplateResolver.InvalidActionException("entry_is_not_ready");
        }
        Iterator<String> it = entryValues.iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                if (str.equals(secSingleChoicePreferenceController.getSelectedValue())) {
                    throw new CommandTemplateResolver.InvalidActionException("already_set");
                }
                if (!secSingleChoicePreferenceController.setSelectedValue(str)) {
                    throw new CommandTemplateResolver.InvalidActionException("unknown");
                }
                return;
            }
        }
        throw new CommandTemplateResolver.InvalidActionException("invalid_value");
    }

    @Override // com.samsung.android.settings.actions.templateresolver.CommandTemplateResolver
    public final void resolve(
            CommandAction commandAction,
            BasePreferenceController basePreferenceController,
            SettingsCommandActionHandler.AnonymousClass1 anonymousClass1) {
        String message;
        int i;
        try {
            setSingleChoiceValue(
                    basePreferenceController, ((StringAction) commandAction).mNewValue);
            i = 1;
            message = null;
        } catch (CommandTemplateResolver.InvalidActionException e) {
            message = e.getMessage();
            i = 2;
        }
        anonymousClass1.onResolved(i, message);
    }
}
