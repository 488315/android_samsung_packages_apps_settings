package com.samsung.android.settings.bixby.control.actions;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SetAction extends BaseAction {
    public GetAction mGetAction;

    @Override // com.samsung.android.settings.bixby.control.actions.BaseAction
    public final Bundle executeInternal() {
        return TextUtils.equals(
                        this.mGetAction.execute(null).getString("result"),
                        this.mActionParam.getParameterValue("value"))
                ? AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "already_set")
                : excuteAction(this.mExtraBundle, "set");
    }
}
