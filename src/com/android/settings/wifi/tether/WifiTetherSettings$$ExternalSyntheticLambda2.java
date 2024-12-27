package com.android.settings.wifi.tether;

import androidx.preference.Preference;

import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiTetherSettings$$ExternalSyntheticLambda2
        implements Preference.OnPreferenceClickListener,
                WifiTetherBasePreferenceController.OnTetherConfigUpdateListener {
    public final /* synthetic */ WifiTetherSettings f$0;

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        SharedConnectivityRepository sharedConnectivityRepository =
                this.f$0.mWifiTetherViewModel.mSharedConnectivityRepository;
        sharedConnectivityRepository.mWorkerExecutor.execute(
                sharedConnectivityRepository.mLaunchSettingsRunnable);
        return true;
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
    public void onTetherConfigUpdated(AbstractPreferenceController abstractPreferenceController) {
        this.f$0.onTetherConfigUpdated(abstractPreferenceController);
    }
}
