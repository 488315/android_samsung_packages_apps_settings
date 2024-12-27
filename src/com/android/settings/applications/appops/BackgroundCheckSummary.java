package com.android.settings.applications.appops;

import android.os.Bundle;
import android.preference.PreferenceFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.core.InstrumentedPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BackgroundCheckSummary extends InstrumentedPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 258;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.background_check_pref);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.background_check_summary, viewGroup, false);
        if (viewGroup instanceof PreferenceFrameLayout) {
            inflate.getLayoutParams().removeBorders = true;
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        BackStackRecord m =
                DialogFragment$$ExternalSyntheticOutline0.m(
                        childFragmentManager, childFragmentManager);
        AppOpsState.OpsTemplate opsTemplate = AppOpsState.RUN_IN_BACKGROUND_TEMPLATE;
        AppOpsCategory appOpsCategory = new AppOpsCategory();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("template", opsTemplate);
        appOpsCategory.setArguments(bundle2);
        m.doAddOp(R.id.appops_content, appOpsCategory, "appops", 1);
        m.commitInternal(true);
        return inflate;
    }
}
