package com.android.settings.development;

import android.content.Context;
import android.debug.PairDevice;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdbDeviceDetailsFragment extends DashboardFragment
        implements DeveloperOptionAwareMixin {
    public PairDevice mPairedDevice;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AdbDeviceDetailsHeaderController(this.mPairedDevice, context, this));
        arrayList.add(new AdbDeviceDetailsActionController(this.mPairedDevice, context, this));
        PairDevice pairDevice = this.mPairedDevice;
        AdbDeviceDetailsFingerprintController adbDeviceDetailsFingerprintController =
                new AdbDeviceDetailsFingerprintController(context);
        adbDeviceDetailsFingerprintController.mPairedDevice = pairDevice;
        arrayList.add(adbDeviceDetailsFingerprintController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AdbDeviceDetailsFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1836;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.adb_device_details_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Bundle arguments = getArguments();
        if (arguments.containsKey("paired_device")) {
            this.mPairedDevice = arguments.getParcelable("paired_device");
        }
        super.onAttach(context);
    }
}
