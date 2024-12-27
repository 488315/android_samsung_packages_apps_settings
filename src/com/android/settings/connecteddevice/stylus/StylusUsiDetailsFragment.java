package com.android.settings.connecteddevice.stylus;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.InputDevice;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StylusUsiDetailsFragment extends DashboardFragment {
    InputDevice mInputDevice;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mInputDevice != null) {
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            arrayList.add(new StylusUsiHeaderController(context, this.mInputDevice));
            arrayList.add(
                    new StylusDevicesController(
                            context, this.mInputDevice, null, settingsLifecycle));
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "StylusUsiDetailsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2021;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.stylus_usi_details_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mInputDevice =
                ((InputManager) context.getSystemService(InputManager.class))
                        .getInputDevice(getArguments().getInt("device_input_id"));
        super.onAttach(context);
        if (this.mInputDevice == null) {
            finish();
        }
    }
}
