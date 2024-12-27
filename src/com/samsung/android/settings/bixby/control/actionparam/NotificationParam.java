package com.samsung.android.settings.bixby.control.actionparam;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationParam extends BaseActionParam {
    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final void createParamIfNeeded() {
        HashMap hashMap = new HashMap();
        this.mSpecificParamsMap = hashMap;
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", hashMap, "viv.accessibilityApp.TurnOnNotificationReminder");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value",
                "false",
                this.mSpecificParamsMap,
                "viv.accessibilityApp.TurnOffNotificationReminder");
        Parameter parameter = (Parameter) this.mSpecificParamsMap.get(this.mActionName);
        if (parameter != null) {
            this.mParamList.add(parameter);
        }
    }
}
