package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ToggleTemplate extends CommandTemplate {
    public boolean mIsChecked;

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean("key_new_value", this.mIsChecked);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 2;
    }
}
