package com.android.settings.inputmethod;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InputMethodAndSubtypeEnabler extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "InputMethodAndSubtypeEnabler";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 60;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.input_methods_subtype;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        String stringExtra = getActivity().getIntent().getStringExtra("android.intent.extra.TITLE");
        if (stringExtra == null) {
            Bundle arguments = getArguments();
            stringExtra =
                    arguments == null ? null : arguments.getString("android.intent.extra.TITLE");
        }
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        getActivity().setTitle(stringExtra);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        String stringExtra = getActivity().getIntent().getStringExtra("input_method_id");
        if (stringExtra == null) {
            Bundle arguments = getArguments();
            stringExtra = arguments == null ? null : arguments.getString("input_method_id");
        }
        ((InputMethodAndSubtypePreferenceController)
                        use(InputMethodAndSubtypePreferenceController.class))
                .initialize(this, stringExtra);
    }
}
