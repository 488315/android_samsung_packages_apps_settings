package com.android.settings.backup;

import android.app.backup.IBackupManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupSettingsHelper {
    public final IBackupManager mBackupManager =
            IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
    public final Context mContext;

    public BackupSettingsHelper(Context context) {
        this.mContext = context;
    }

    public final Intent getIntentForBackupSettings() {
        if (isIntentProvidedByTransport()) {
            return getIntentForBackupSettingsFromTransport();
        }
        Log.e(
                "BackupSettingsHelper",
                "Backup transport has not provided an intent or the component for the intent is not"
                    + " found!");
        return new Intent(this.mContext, (Class<?>) Settings.PrivacySettingsActivity.class);
    }

    public Intent getIntentForBackupSettingsFromTransport() {
        boolean z;
        Intent intentFromBackupTransport = getIntentFromBackupTransport();
        if (intentFromBackupTransport != null) {
            try {
                z = this.mBackupManager.isBackupServiceActive(UserHandle.myUserId());
            } catch (Exception unused) {
                z = false;
            }
            intentFromBackupTransport.putExtra("backup_services_available", z);
        }
        return intentFromBackupTransport;
    }

    public final Intent getIntentFromBackupTransport() {
        try {
            IBackupManager iBackupManager = this.mBackupManager;
            Intent dataManagementIntent =
                    iBackupManager.getDataManagementIntent(iBackupManager.getCurrentTransport());
            if (Log.isLoggable("BackupSettingsHelper", 3)) {
                if (dataManagementIntent != null) {
                    Log.d(
                            "BackupSettingsHelper",
                            "Parsed intent from backup transport: "
                                    + dataManagementIntent.toString());
                } else {
                    Log.d("BackupSettingsHelper", "Received a null intent from backup transport");
                }
            }
            return dataManagementIntent;
        } catch (RemoteException e) {
            Log.e("BackupSettingsHelper", "Error getting data management intent", e);
            return null;
        }
    }

    public CharSequence getLabelFromBackupTransport() {
        try {
            CharSequence dataManagementLabelForUser =
                    this.mBackupManager.getDataManagementLabelForUser(
                            UserHandle.myUserId(), this.mBackupManager.getCurrentTransport());
            if (Log.isLoggable("BackupSettingsHelper", 3)) {
                Log.d(
                        "BackupSettingsHelper",
                        "Received the backup settings label from backup transport: "
                                + ((Object) dataManagementLabelForUser));
            }
            return dataManagementLabelForUser;
        } catch (RemoteException e) {
            Log.e("BackupSettingsHelper", "Error getting data management label", e);
            return null;
        }
    }

    public String getSummaryFromBackupTransport() {
        try {
            IBackupManager iBackupManager = this.mBackupManager;
            String destinationString =
                    iBackupManager.getDestinationString(iBackupManager.getCurrentTransport());
            if (Log.isLoggable("BackupSettingsHelper", 3)) {
                Log.d(
                        "BackupSettingsHelper",
                        "Received the backup settings summary from backup transport: "
                                + destinationString);
            }
            return destinationString;
        } catch (RemoteException e) {
            Log.e("BackupSettingsHelper", "Error getting data management summary", e);
            return null;
        }
    }

    public final boolean isBackupProvidedByManufacturer() {
        if (Log.isLoggable("BackupSettingsHelper", 3)) {
            Log.d("BackupSettingsHelper", "Checking if intent provided by manufacturer");
        }
        String string =
                this.mContext.getResources().getString(R.string.config_backup_settings_intent);
        return (string == null || string.isEmpty()) ? false : true;
    }

    public boolean isIntentProvidedByTransport() {
        Intent intentFromBackupTransport = getIntentFromBackupTransport();
        return (intentFromBackupTransport == null
                        || intentFromBackupTransport.resolveActivity(
                                        this.mContext.getPackageManager())
                                == null)
                ? false
                : true;
    }
}
