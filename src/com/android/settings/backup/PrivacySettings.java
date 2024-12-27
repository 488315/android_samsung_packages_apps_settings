package com.android.settings.backup;

import android.app.backup.IBackupManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.gtscell.data.FieldName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivacySettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.privacy_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.backup.PrivacySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            BackupSettingsHelper backupSettingsHelper = new BackupSettingsHelper(context);
            return (backupSettingsHelper.isBackupProvidedByManufacturer()
                            || backupSettingsHelper.isIntentProvidedByTransport())
                    ? false
                    : true;
        }
    }

    public static void updatePrivacySettingsConfigData(Context context) {
        if (UserManager.get(context).isAdminUser()) {
            PrivacySettingsConfigData privacySettingsConfigData =
                    PrivacySettingsConfigData.getInstance();
            IBackupManager asInterface =
                    IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
            try {
                privacySettingsConfigData.mBackupEnabled = asInterface.isBackupEnabled();
                String currentTransport = asInterface.getCurrentTransport();
                privacySettingsConfigData.mConfigIntent =
                        PrivacySettingsUtils.validatedActivityIntent(
                                context,
                                asInterface.getConfigurationIntent(currentTransport),
                                FieldName.CONFIG);
                privacySettingsConfigData.mConfigSummary =
                        asInterface.getDestinationString(currentTransport);
                privacySettingsConfigData.mManageIntent =
                        PrivacySettingsUtils.validatedActivityIntent(
                                context,
                                asInterface.getDataManagementIntent(currentTransport),
                                "management");
                privacySettingsConfigData.mManageLabel =
                        asInterface.getDataManagementLabelForUser(
                                UserHandle.myUserId(), currentTransport);
                privacySettingsConfigData.mBackupGray = false;
            } catch (RemoteException unused) {
                privacySettingsConfigData.mBackupGray = true;
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PrivacySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 81;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.privacy_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        updatePrivacySettingsConfigData(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        updatePrivacySettingsConfigData(getContext());
        super.updatePreferenceStates();
    }
}
