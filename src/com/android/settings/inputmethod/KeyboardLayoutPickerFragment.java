package com.android.settings.inputmethod;

import android.content.Context;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardLayoutPickerFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "KeyboardLayoutPicker";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 58;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.keyboard_layout_picker_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        InputDeviceIdentifier parcelableExtra =
                getActivity().getIntent().getParcelableExtra("input_device_identifier");
        if (NewKeyboardSettingsUtils.getInputDevice(
                        (InputManager) context.getSystemService(InputManager.class),
                        parcelableExtra)
                == null) {
            return;
        }
        ((KeyboardLayoutPickerController) use(KeyboardLayoutPickerController.class))
                .initialize(this, parcelableExtra);
    }
}
