package com.android.settings.datausage;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.os.Bundle;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.NetworkPolicyEditor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DataUsageBaseFragment extends DashboardFragment {
    public final TemplatePreference.NetworkServices services =
            new TemplatePreference.NetworkServices();

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.services.mPolicyManager = (NetworkPolicyManager) context.getSystemService("netpolicy");
        TemplatePreference.NetworkServices networkServices = this.services;
        networkServices.mPolicyEditor = new NetworkPolicyEditor(networkServices.mPolicyManager);
        TemplatePreference.NetworkServices networkServices2 = this.services;
        networkServices2.getClass();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.services.mPolicyEditor.read();
    }
}
