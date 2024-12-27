package com.android.settings.backup;

import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivacySettingsConfigData {
    public static PrivacySettingsConfigData sInstance;
    public boolean mBackupEnabled;
    public boolean mBackupGray;
    public Intent mConfigIntent;
    public String mConfigSummary;
    public Intent mManageIntent;
    public CharSequence mManageLabel;

    public static PrivacySettingsConfigData getInstance() {
        if (sInstance == null) {
            PrivacySettingsConfigData privacySettingsConfigData = new PrivacySettingsConfigData();
            privacySettingsConfigData.mBackupEnabled = false;
            privacySettingsConfigData.mBackupGray = false;
            privacySettingsConfigData.mConfigIntent = null;
            privacySettingsConfigData.mConfigSummary = null;
            privacySettingsConfigData.mManageIntent = null;
            privacySettingsConfigData.mManageLabel = null;
            sInstance = privacySettingsConfigData;
        }
        return sInstance;
    }
}
