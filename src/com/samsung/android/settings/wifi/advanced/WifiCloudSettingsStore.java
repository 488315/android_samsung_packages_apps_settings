package com.samsung.android.settings.wifi.advanced;

import android.content.ContentResolver;
import android.os.Bundle;

import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiCloudSettingsStore {
    public boolean mIsAllowedInEdp;
    public boolean mIsConsentedToUseNetwork;
    public boolean mIsMainSyncOn = ContentResolver.getMasterSyncAutomatically();
    public boolean mIsPersonalInfoOn;
    public boolean mIsUserAgreesPolicyNotice;
    public SamsungCloudRPCSettingV2 mSetting;

    public final void setRpcSettings(SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2) {
        this.mSetting = samsungCloudRPCSettingV2;
        Bundle profile = samsungCloudRPCSettingV2.iSyncSetting.getProfile();
        int i = profile != null ? profile.getInt("precondition") : 0;
        this.mIsUserAgreesPolicyNotice = (i & 512) != 512;
        this.mIsConsentedToUseNetwork = (i & 4096) != 4096;
        this.mIsPersonalInfoOn = (i & 256) != 256;
        this.mIsAllowedInEdp = (i & 768) != 768;
    }
}
