package com.samsung.android.settings.bixby.control.actions;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ChangeAction extends BaseAction {
    public int RANGE_FLAG;
    public GetAction mGetAction;

    /* JADX WARN: Removed duplicated region for block: B:31:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0173  */
    @Override // com.samsung.android.settings.bixby.control.actions.BaseAction
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle executeInternal() {
        /*
            Method dump skipped, instructions count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.control.actions.ChangeAction.executeInternal():android.os.Bundle");
    }

    public final Bundle setSize(int i) {
        this.mExtraBundle.putString("value", String.valueOf(i));
        return excuteAction(this.mExtraBundle, "change");
    }
}
