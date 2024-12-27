package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UseOneLockSettingsFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new UseOneLockControllerSwitch(context, this));
        arrayList.add(new PrivateSpaceLockController(context, this));
        boolean isFaceNotConvenienceBiometric = Utils.isFaceNotConvenienceBiometric(context);
        if (Utils.isMultipleBiometricsSupported(context) && isFaceNotConvenienceBiometric) {
            arrayList.add(new FaceFingerprintUnlockController(context, getSettingsLifecycle()));
        } else if (Utils.hasFingerprintHardware(context)) {
            arrayList.add(
                    new PrivateSpaceFingerprintPreferenceController(
                            context, "private_space_biometrics", getSettingsLifecycle()));
        } else if (Utils.hasFaceHardware(context) && isFaceNotConvenienceBiometric) {
            arrayList.add(
                    new PrivateSpaceFacePreferenceController(
                            context, "private_space_biometrics", getSettingsLifecycle()));
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "UseOneLockSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.privatespace_one_lock;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (((UseOneLockControllerSwitch) use(UseOneLockControllerSwitch.class))
                .handleActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onCreate(bundle);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (PrivateSpaceMaintainer.getInstance(getContext()).isPrivateSpaceLocked()) {
            finish();
        }
    }
}
