package com.samsung.android.settings.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.desktopmode.SemDesktopModeManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetSettingsPreferenceFragment extends SettingsPreferenceFragment {
    public abstract String getResetButtonTitle();

    public abstract void onResetButtonClick();

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!Utils.isDesktopModeEnabled(requireContext())) {
            SemDesktopModeManager semDesktopModeManager =
                    (SemDesktopModeManager) requireContext().getSystemService("desktopmode");
            if (!(semDesktopModeManager != null
                    ? semDesktopModeManager.isDeviceConnected()
                    : false)) {
                return;
            }
        }
        requireActivity().finish();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
        View inflate =
                LayoutInflater.from(getActivity())
                        .inflate(R.layout.sec_reset_button_bottom_layout, (ViewGroup) null);
        Button button = (Button) inflate.findViewById(R.id.reset_button);
        button.setText(getResetButtonTitle());
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.general.ResetSettingsPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        ResetSettingsPreferenceFragment.this.onResetButtonClick();
                    }
                });
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams.gravity = 8388613;
            viewGroup.addView(inflate, layoutParams);
            viewGroup.setVisibility(0);
        }
    }
}
