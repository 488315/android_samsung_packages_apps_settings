package com.samsung.android.settings.actions.templateresolver;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface CommandTemplateResolver {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InvalidActionException extends RuntimeException {
        public InvalidActionException(String str) {
            super(str);
        }
    }

    void resolve(
            CommandAction commandAction,
            BasePreferenceController basePreferenceController,
            SettingsCommandActionHandler.AnonymousClass1 anonymousClass1);
}
