package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MediaControlTemplate extends CommandTemplate {
    public int mCurrentActiveMode;
    public String mMediaInfo;
    public int mModeFlags;

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt("key_current_active_mode", this.mCurrentActiveMode);
        dataBundle.putInt("key_mode_flags", this.mModeFlags);
        dataBundle.putString("key_media_info", this.mMediaInfo);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 7;
    }
}
