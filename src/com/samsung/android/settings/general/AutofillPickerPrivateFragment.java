package com.samsung.android.settings.general;

import android.content.Context;
import android.credentials.CredentialManager;

import androidx.lifecycle.LifecycleObserver;

import com.android.settings.R;
import com.android.settings.applications.autofill.PasswordsPreferenceController;
import com.android.settings.applications.credentials.CredentialManagerPreferenceController;
import com.android.settings.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutofillPickerPrivateFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        AutofillPicker.buildAutofillPreferenceControllers(context, arrayList);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AutofillPickerPrivateFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 792;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return (getContext() == null || !CredentialManager.isServiceEnabled(getContext()))
                ? R.xml.sec_default_autofill_picker_private_settings
                : R.xml.sec_default_autofill_picker_private_settings_credman;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (!CredentialManager.isServiceEnabled(context)) {
            getSettingsLifecycle()
                    .addObserver((LifecycleObserver) use(PasswordsPreferenceController.class));
            return;
        }
        ((CredentialManagerPreferenceController) use(CredentialManagerPreferenceController.class))
                .init(
                        this,
                        getFragmentManager(),
                        getIntent(),
                        new CredentialManagerPreferenceController
                                .Delegate() { // from class:
                                              // com.samsung.android.settings.general.AutofillPickerPrivateFragment.1
                            @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.Delegate
                            public final void forceDelegateRefresh() {
                                AutofillPickerPrivateFragment.this.forceUpdatePreferences();
                            }

                            @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.Delegate
                            public final void setActivityResult(int i) {
                                AutofillPickerPrivateFragment.this.getActivity().setResult(i);
                            }
                        },
                        false);
    }
}
