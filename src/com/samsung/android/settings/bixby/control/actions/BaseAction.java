package com.samsung.android.settings.bixby.control.actions;

import android.os.Bundle;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.bixby.ActionCommand;
import com.samsung.android.settings.bixby.control.Control;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.control.actionparam.Parameter;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BaseAction implements Control {
    public final String mActionKey;
    public final BaseActionParam mActionParam;
    public int MAX_INDEX = -1;
    public int MIN_INDEX = 1000;
    public final Bundle mExtraBundle = new Bundle();

    public BaseAction(BaseActionParam baseActionParam, String str) {
        this.mActionParam = baseActionParam;
        this.mActionKey = str;
        Iterator it = ((ArrayList) baseActionParam.mParamList).iterator();
        while (it.hasNext()) {
            Parameter parameter = (Parameter) it.next();
            this.mExtraBundle.putString(parameter.key, parameter.value);
        }
    }

    public final Bundle excuteAction(Bundle bundle, String str) {
        return ActionCommand.getInstance(this.mActionParam.mContext)
                .execute(str, bundle, this.mActionKey);
    }

    @Override // com.samsung.android.settings.bixby.control.Control
    public final Bundle execute(String str) {
        return this.mActionParam.mIsCheckSupport
                ? excuteAction(null, "supported")
                : executeInternal();
    }

    public Bundle executeInternal() {
        return excuteAction(null, SignalSeverity.NONE);
    }
}
