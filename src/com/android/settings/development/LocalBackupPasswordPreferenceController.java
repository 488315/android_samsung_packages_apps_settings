package com.android.settings.development;

import android.app.backup.IBackupManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBackupPasswordPreferenceController
        extends DeveloperOptionsPreferenceController implements PreferenceControllerMixin {
    public final IBackupManager mBackupManager;
    public final UserManager mUserManager;

    public LocalBackupPasswordPreferenceController(Context context) {
        super(context);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mBackupManager = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "local_backup_password";
    }

    public boolean isAdminUser() {
        return this.mUserManager.isAdminUser();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setEnabled(isAdminUser() && this.mBackupManager != null);
        IBackupManager iBackupManager = this.mBackupManager;
        if (iBackupManager == null) {
            return;
        }
        try {
            if (iBackupManager.hasBackupPassword()) {
                preference.setSummary(R.string.local_backup_password_summary_change);
            } else {
                preference.setSummary(R.string.local_backup_password_summary_none);
            }
        } catch (RemoteException unused) {
        }
    }
}
