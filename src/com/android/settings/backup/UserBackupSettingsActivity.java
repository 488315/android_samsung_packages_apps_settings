package com.android.settings.backup;

import android.app.backup.IBackupManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserBackupSettingsActivity extends FragmentActivity {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public FragmentManager mFragmentManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.backup.UserBackupSettingsActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            boolean z;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            try {
                z =
                        IBackupManager.Stub.asInterface(ServiceManager.getService("backup"))
                                .isBackupServiceActive(UserHandle.myUserId());
            } catch (Exception unused) {
                z = false;
            }
            if (!z) {
                ((ArrayList) nonIndexableKeys).add("Backup");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = context.getString(R.string.privacy_settings_title);
            searchIndexableRaw.screenTitle = context.getString(R.string.privacy_settings_title);
            searchIndexableRaw.keywords = context.getString(R.string.keywords_backup);
            ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                    context.getPackageName();
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    UserBackupSettingsActivity.class.getName();
            ((SearchIndexableData) searchIndexableRaw).intentAction = "android.intent.action.MAIN";
            ((SearchIndexableData) searchIndexableRaw).key = "Backup";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BackupSettingsHelper backupSettingsHelper = new BackupSettingsHelper(this);
        if (backupSettingsHelper.isBackupProvidedByManufacturer()) {
            if (Log.isLoggable("BackupSettingsActivity", 3)) {
                Log.d(
                        "BackupSettingsActivity",
                        "Manufacturer provided backup settings, showing the preference screen");
            }
            if (this.mFragmentManager == null) {
                this.mFragmentManager = getSupportFragmentManager();
            }
            FragmentManager fragmentManager = this.mFragmentManager;
            BackStackRecord m =
                    DialogFragment$$ExternalSyntheticOutline0.m(fragmentManager, fragmentManager);
            m.replace(android.R.id.content, new BackupSettingsFragment(), null);
            m.commitInternal(false);
            return;
        }
        if (Log.isLoggable("BackupSettingsActivity", 3)) {
            Log.d(
                    "BackupSettingsActivity",
                    "No manufacturer settings found, launching the backup settings directly");
        }
        Intent intentForBackupSettings = backupSettingsHelper.getIntentForBackupSettings();
        try {
            getPackageManager()
                    .setComponentEnabledSetting(intentForBackupSettings.getComponent(), 1, 1);
        } catch (SecurityException e) {
            Log.w(
                    "BackupSettingsActivity",
                    "Trying to enable activity "
                            + intentForBackupSettings.getComponent()
                            + " but couldn't: "
                            + e.getMessage());
        }
        startActivityForResult(intentForBackupSettings, 1);
        finish();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }
}
