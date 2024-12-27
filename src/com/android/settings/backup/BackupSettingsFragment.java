package com.android.settings.backup;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BackupSettingsFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider();

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Intent intent;
        ArrayList arrayList = new ArrayList();
        BackupSettingsPreferenceController backupSettingsPreferenceController =
                new BackupSettingsPreferenceController(context);
        BackupSettingsHelper backupSettingsHelper = new BackupSettingsHelper(context);
        backupSettingsPreferenceController.mBackupSettingsIntent =
                backupSettingsHelper.getIntentForBackupSettings();
        CharSequence labelFromBackupTransport = backupSettingsHelper.getLabelFromBackupTransport();
        if (TextUtils.isEmpty(labelFromBackupTransport)) {
            labelFromBackupTransport =
                    backupSettingsHelper.mContext.getString(R.string.privacy_settings_title);
        }
        backupSettingsPreferenceController.mBackupSettingsTitle = labelFromBackupTransport;
        String summaryFromBackupTransport = backupSettingsHelper.getSummaryFromBackupTransport();
        if (summaryFromBackupTransport == null) {
            summaryFromBackupTransport =
                    backupSettingsHelper.mContext.getString(
                            R.string.backup_configure_account_default_summary);
        }
        backupSettingsPreferenceController.mBackupSettingsSummary = summaryFromBackupTransport;
        if (Log.isLoggable("BackupSettingsHelper", 3)) {
            Log.d(
                    "BackupSettingsHelper",
                    "Getting a backup settings intent provided by manufacturer");
        }
        String string =
                backupSettingsHelper
                        .mContext
                        .getResources()
                        .getString(R.string.config_backup_settings_intent);
        if (string != null && !string.isEmpty()) {
            try {
                intent = Intent.parseUri(string, 0);
            } catch (URISyntaxException e) {
                Log.e("BackupSettingsHelper", "Invalid intent provided by the manufacturer.", e);
            }
            backupSettingsPreferenceController.mManufacturerIntent = intent;
            backupSettingsPreferenceController.mManufacturerLabel =
                    backupSettingsHelper
                            .mContext
                            .getResources()
                            .getString(R.string.config_backup_settings_label);
            arrayList.add(backupSettingsPreferenceController);
            return arrayList;
        }
        intent = null;
        backupSettingsPreferenceController.mManufacturerIntent = intent;
        backupSettingsPreferenceController.mManufacturerLabel =
                backupSettingsHelper
                        .mContext
                        .getResources()
                        .getString(R.string.config_backup_settings_label);
        arrayList.add(backupSettingsPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BackupSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 818;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.backup_settings;
    }
}
