package com.android.settings.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupHelper;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.shortcut.CreateShortcutPreferenceController;
import com.android.settingslib.datastore.BackupRestoreFileArchiver;
import com.android.settingslib.datastore.BackupRestoreFileStorage;
import com.android.settingslib.datastore.BackupRestoreStorage;
import com.android.settingslib.datastore.BackupRestoreStorageManager;
import com.android.settingslib.datastore.KeyedObservable;
import com.android.settingslib.datastore.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsBackupHelper extends BackupAgentHelper {
    @Override // android.app.backup.BackupAgent
    public final void onCreate() {
        super.onCreate();
        BackupRestoreStorageManager backupRestoreStorageManager =
                BackupRestoreStorageManager.getInstance(this);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : backupRestoreStorageManager.storageWrappers.entrySet()) {
            String str = (String) entry.getKey();
            BackupRestoreStorage backupRestoreStorage =
                    ((BackupRestoreStorageManager.StorageWrapper) entry.getValue()).storage;
            if (backupRestoreStorage instanceof BackupRestoreFileStorage) {
                arrayList.add(backupRestoreStorage);
            } else {
                addHelper(str, backupRestoreStorage);
            }
        }
        BackupRestoreFileArchiver backupRestoreFileArchiver =
                new BackupRestoreFileArchiver(
                        backupRestoreStorageManager.application, arrayList, "file_archiver");
        addHelper(backupRestoreFileArchiver.name, backupRestoreFileArchiver);
        if (FeatureFactoryImpl._factory == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
    }

    @Override // android.app.backup.BackupAgent
    public final void onRestoreFinished() {
        super.onRestoreFinished();
        Iterator it =
                BackupRestoreStorageManager.getInstance(this).storageWrappers.values().iterator();
        while (it.hasNext()) {
            BackupHelper backupHelper =
                    ((BackupRestoreStorageManager.StorageWrapper) it.next()).storage;
            if (backupHelper instanceof KeyedObservable) {
                ((KeyedObservable) backupHelper).notifyChange(3);
            } else if (backupHelper instanceof Observable) {
                ((Observable) backupHelper).notifyChange(3);
            }
        }
        CreateShortcutPreferenceController.updateRestoredShortcuts(this);
    }
}
