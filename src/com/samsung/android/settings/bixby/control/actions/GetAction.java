package com.samsung.android.settings.bixby.control.actions;

import android.os.Bundle;
import android.util.Log;

import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GetAction extends BaseAction {
    public int RANGE_FLAG;

    public GetAction(BaseActionParam baseActionParam, String str) {
        super(baseActionParam, str);
        this.RANGE_FLAG = 0;
    }

    @Override // com.samsung.android.settings.bixby.control.actions.BaseAction
    public final Bundle executeInternal() {
        Bundle excuteAction = excuteAction(this.mExtraBundle, "get");
        String string = excuteAction.getString("result");
        try {
            int parseInt = Integer.parseInt(string);
            String str = this.mActionParam.mActionName;
            str.getClass();
            if (str.equals("bixby.settingsApp.Get_FontSize")) {
                this.RANGE_FLAG = 1;
            }
            excuteAction.putString("result", String.valueOf(parseInt + this.RANGE_FLAG));
            return excuteAction;
        } catch (NumberFormatException e) {
            Log.e("GetAction", e.getMessage());
            return BixbyUtils.buildActionResult(string);
        }
    }
}
