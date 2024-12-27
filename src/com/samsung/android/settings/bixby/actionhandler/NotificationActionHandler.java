package com.samsung.android.settings.bixby.actionhandler;

import com.samsung.android.settings.bixby.control.ControlFactory;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.control.actionparam.NotificationParam;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationActionHandler extends BaseActionHandler {
    public static final String[] ACTIONS = {
        "viv.accessibilityApp.OpenNotificationReminder",
        "viv.accessibilityApp.TurnOnNotificationReminder",
        "viv.accessibilityApp.TurnOffNotificationReminder"
    };

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final String executeActionInternal(BaseActionParam baseActionParam) {
        NotificationParam notificationParam =
                new NotificationParam(
                        baseActionParam.mContext,
                        baseActionParam.mActionName,
                        baseActionParam.mExtra);
        return this.mResultConverter.convert(
                ControlFactory.LazyHolder.sControlFactory
                        .createControl(notificationParam)
                        .execute("com.android.settings.command"),
                notificationParam.mActionName);
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final boolean isAffectedByKnoxPolicy() {
        return false;
    }
}
