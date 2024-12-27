package com.samsung.android.settings.bixby.control.actionparam;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingsActionParam extends BaseActionParam {
    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final void createParamIfNeeded() {
        HashMap hashMap = new HashMap();
        this.mSpecificParamsMap = hashMap;
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", hashMap, "bixby.settingsApp.Enable_MobileData");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "bixby.settingsApp.Enable_Airplane");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "bixby.settingsApp.SetOn_Synchronize");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "bixby.settingsApp.Disable_MobileData");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "bixby.settingsApp.Disable_Airplane");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "bixby.settingsApp.SetOff_Synchronize");
        Parameter parameter = (Parameter) this.mSpecificParamsMap.get(this.mActionName);
        if (parameter != null) {
            ((ArrayList) this.mParamList).add(parameter);
        }
        if (!TextUtils.equals(this.mActionName, "bixby.settingsApp.Goto_SendSOSMessagesSetting")
                && getParameterValue("isFolded") == null) {
            List list = this.mParamList;
            Bundle bundle = this.mExtra;
            ((ArrayList) list)
                    .add(
                            new Parameter(
                                    "isFolded",
                                    String.valueOf(
                                            bundle != null
                                                    ? bundle.getBoolean("isFolded", false)
                                                    : false)));
        }
        if (!TextUtils.equals(this.mActionName, "bixby.settingsApp.Goto_SendSOSMessagesSetting")
                && getParameterValue("newDex") == null) {
            ((ArrayList) this.mParamList).add(new Parameter("newDex", String.valueOf(isNewDex())));
        }
    }

    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final List getKeysNeedConvertingKey() {
        return new ArrayList(
                Arrays.asList(
                        "fontSize",
                        "whiteBalance",
                        "screenZoom",
                        "soundMode",
                        "screenTimeout",
                        "muteDuration"));
    }
}
