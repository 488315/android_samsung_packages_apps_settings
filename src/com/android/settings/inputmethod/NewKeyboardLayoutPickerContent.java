package com.android.settings.inputmethod;

import android.content.Context;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NewKeyboardLayoutPickerContent extends DashboardFragment {
    public NewKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0 mControllerUpdateCallback;
    public NewKeyboardLayoutPickerController mNewKeyboardLayoutPickerController;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "KeyboardLayoutPicker";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1958;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.new_keyboard_layout_picker_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        InputManager inputManager =
                (InputManager) getContext().getSystemService(InputManager.class);
        InputDeviceIdentifier parcelable = getArguments().getParcelable("input_device_identifier");
        if (parcelable == null
                || NewKeyboardSettingsUtils.getInputDevice(inputManager, parcelable) == null) {
            getActivity().finish();
            return;
        }
        NewKeyboardLayoutPickerController newKeyboardLayoutPickerController =
                (NewKeyboardLayoutPickerController) use(NewKeyboardLayoutPickerController.class);
        this.mNewKeyboardLayoutPickerController = newKeyboardLayoutPickerController;
        newKeyboardLayoutPickerController.initialize(this);
        NewKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0
                newKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0 =
                        this.mControllerUpdateCallback;
        if (newKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0 != null) {
            NewKeyboardLayoutPickerController newKeyboardLayoutPickerController2 =
                    this.mNewKeyboardLayoutPickerController;
            NewKeyboardLayoutPickerFragment newKeyboardLayoutPickerFragment =
                    newKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0.f$0;
            if (newKeyboardLayoutPickerController2 != null) {
                newKeyboardLayoutPickerController2.registerKeyboardSelectedCallback(
                        newKeyboardLayoutPickerFragment.mKeyboardLayoutSelectedCallback);
            } else {
                newKeyboardLayoutPickerFragment.getClass();
            }
        }
    }
}
