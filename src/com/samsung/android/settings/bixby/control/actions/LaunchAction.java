package com.samsung.android.settings.bixby.control.actions;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LaunchAction extends BaseAction {
    @Override // com.samsung.android.settings.bixby.control.actions.BaseAction
    public final Bundle executeInternal() {
        return excuteAction(this.mExtraBundle, "goto");
    }
}
