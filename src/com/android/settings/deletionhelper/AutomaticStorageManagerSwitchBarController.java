package com.android.settings.deletionhelper;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutomaticStorageManagerSwitchBarController
        implements CompoundButton.OnCheckedChangeListener {
    public Context mContext;
    public Preference mDaysToRetainPreference;
    public FragmentManager mFragmentManager;
    public MetricsFeatureProvider mMetrics;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.mMetrics.action(this.mContext, 489, z);
        this.mDaysToRetainPreference.setEnabled(z);
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "automatic_storage_manager_enabled", z ? 1 : 0);
        if (!z || SystemProperties.getBoolean("ro.storage_manager.enabled", false)) {
            return;
        }
        new ActivationWarningFragment().show(this.mFragmentManager, "ActivationWarningFragment");
    }
}
