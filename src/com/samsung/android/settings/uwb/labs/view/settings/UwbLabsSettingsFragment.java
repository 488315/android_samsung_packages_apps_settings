package com.samsung.android.settings.uwb.labs.view.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.uwb.labs.data.StatisticsDataManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbLabsSettingsFragment extends SettingsPreferenceFragment {
    public AnonymousClass1 mBtnClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.uwb.labs.view.settings.UwbLabsSettingsFragment.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Toast.makeText(
                                    UwbLabsSettingsFragment.this.getContext(),
                                    R.string.sec_development_reset_settings_completed,
                                    1)
                            .show();
                    UwbLabsSettingsFragment.this.finish();
                }
            };
    public View mContentView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StatisticsDataManager.getInstance(getContext());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_uwb_labs_setting_preference, (ViewGroup) null);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mBtnClickListener != null) {
            this.mBtnClickListener = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ((Button) this.mContentView.findViewById(R.id.execute_development_reset_settings))
                .setOnClickListener(this.mBtnClickListener);
    }
}
