package com.android.settings.backup;

import android.R;
import android.app.backup.IBackupManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleBackupSettingFragment extends SettingsPreferenceFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public IBackupManager mBackupManager;
    public AlertDialog mConfirmDialog;
    public AnonymousClass1 mSummaryPreference;
    public SettingsMainSwitchBar mSwitchBar;
    public boolean mWaitingForConfirmationDialog = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.backup.ToggleBackupSettingFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends Preference {
        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            ((TextView) preferenceViewHolder.findViewById(R.id.summary)).setText(getSummary());
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 81;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mSwitchBar.setOnBeforeCheckedChangeListener(
                new SettingsMainSwitchBar
                        .OnBeforeCheckedChangeListener() { // from class:
                                                           // com.android.settings.backup.ToggleBackupSettingFragment.2
                    @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                    public final boolean onBeforeCheckedChanged(boolean z) {
                        ToggleBackupSettingFragment toggleBackupSettingFragment =
                                ToggleBackupSettingFragment.this;
                        if (z) {
                            toggleBackupSettingFragment.setBackupEnabled(true);
                            toggleBackupSettingFragment.mSwitchBar.setCheckedInternal(true);
                            return true;
                        }
                        CharSequence text =
                                Settings.Secure.getInt(
                                                        toggleBackupSettingFragment
                                                                .getContentResolver(),
                                                        "user_full_data_backup_aware",
                                                        0)
                                                != 0
                                        ? toggleBackupSettingFragment
                                                .getResources()
                                                .getText(
                                                        com.android.settings.R.string
                                                                .fullbackup_erase_dialog_message)
                                        : toggleBackupSettingFragment
                                                .getResources()
                                                .getText(
                                                        com.android.settings.R.string
                                                                .backup_erase_dialog_message);
                        toggleBackupSettingFragment.mWaitingForConfirmationDialog = true;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(toggleBackupSettingFragment.getActivity());
                        AlertController.AlertParams alertParams = builder.P;
                        alertParams.mMessage = text;
                        builder.setTitle(com.android.settings.R.string.backup_erase_dialog_title);
                        builder.setPositiveButton(R.string.ok, toggleBackupSettingFragment);
                        builder.setNegativeButton(R.string.cancel, toggleBackupSettingFragment);
                        alertParams.mOnDismissListener = toggleBackupSettingFragment;
                        toggleBackupSettingFragment.mConfirmDialog = builder.show();
                        return true;
                    }
                });
        this.mSwitchBar.show();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this.mWaitingForConfirmationDialog = false;
            setBackupEnabled(false);
            this.mSwitchBar.setCheckedInternal(false);
        } else if (i == -2) {
            this.mWaitingForConfirmationDialog = false;
            setBackupEnabled(true);
            this.mSwitchBar.setCheckedInternal(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBackupManager = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
        PreferenceScreen createPreferenceScreen =
                getPreferenceManager().createPreferenceScreen(getActivity());
        setPreferenceScreen(createPreferenceScreen);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(getPrefContext());
        this.mSummaryPreference = anonymousClass1;
        anonymousClass1.setPersistent();
        this.mSummaryPreference.setLayoutResource(
                com.android.settings.R.layout.text_description_preference);
        createPreferenceScreen.addPreference(this.mSummaryPreference);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.setOnBeforeCheckedChangeListener(null);
        this.mSwitchBar.hide();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mWaitingForConfirmationDialog) {
            setBackupEnabled(true);
            this.mSwitchBar.setCheckedInternal(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        try {
            IBackupManager iBackupManager = this.mBackupManager;
            this.mSwitchBar.setCheckedInternal(
                    iBackupManager == null ? false : iBackupManager.isBackupEnabled());
        } catch (RemoteException unused) {
            this.mSwitchBar.setEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        AlertDialog alertDialog = this.mConfirmDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mConfirmDialog.dismiss();
        }
        this.mConfirmDialog = null;
        super.onStop();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mSwitchBar = ((SettingsActivity) getActivity()).mMainSwitch;
        if (Settings.Secure.getInt(getContentResolver(), "user_full_data_backup_aware", 0) != 0) {
            this.mSummaryPreference.setSummary(
                    com.android.settings.R.string.fullbackup_data_summary);
        } else {
            this.mSummaryPreference.setSummary(com.android.settings.R.string.backup_data_summary);
        }
        try {
            IBackupManager iBackupManager = this.mBackupManager;
            this.mSwitchBar.setCheckedInternal(
                    iBackupManager == null ? false : iBackupManager.isBackupEnabled());
        } catch (RemoteException unused) {
            this.mSwitchBar.setEnabled(false);
        }
        getActivity().setTitle(com.android.settings.R.string.backup_data_title);
    }

    public final void setBackupEnabled(boolean z) {
        IBackupManager iBackupManager = this.mBackupManager;
        if (iBackupManager != null) {
            try {
                iBackupManager.setBackupEnabled(z);
            } catch (RemoteException e) {
                Log.e("ToggleBackupSettingFragment", "Error communicating with BackupManager", e);
            }
        }
    }
}
