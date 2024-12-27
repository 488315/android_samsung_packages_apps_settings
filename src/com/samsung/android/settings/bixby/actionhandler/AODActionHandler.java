package com.samsung.android.settings.bixby.actionhandler;

import com.samsung.android.settings.bixby.control.ControlFactory;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AODActionHandler extends BaseActionHandler {
    public static final String[] ACTIONS = {
        "bixby.settingsApp.GetOnOff_AOD",
        "bixby.settingsApp.SetOnOff_AOD",
        "bixby.settingsApp.Goto_AODSetting"
    };

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final String executeActionInternal(BaseActionParam baseActionParam) {
        return this.mResultConverter.convert(
                ControlFactory.LazyHolder.sControlFactory
                        .createControl(baseActionParam)
                        .execute("com.android.settings.command"),
                baseActionParam.mActionName);
    }
}