package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationReminderAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "notification_reminder_selectable",
                                0)
                        == 1;
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(z));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setClassName(
                this.mContext,
                "com.samsung.android.settings.notification.reminder.NotificationReminderActivity");
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String str;
        String value = getValue();
        SemLog.d("NotificationReminderAction", "doSetAction() :: newValue - " + value);
        boolean equals = "true".equals(value);
        if (equals
                != Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "notification_reminder_selectable",
                        0)) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "notification_reminder_selectable",
                    equals ? 1 : 0);
            str = "success";
        } else {
            str = "already_set";
        }
        return Action.createResult(str);
    }
}
