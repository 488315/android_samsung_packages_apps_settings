package com.android.settings.accessibility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settingslib.widget.TopIntroPreference;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifPreferenceLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleScreenMagnificationPreferenceFragmentForSetupWizard
        extends ToggleScreenMagnificationPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment,
              // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 368;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return viewGroup instanceof GlifPreferenceLayout
                ? ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView
                : super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Bundle arguments = getArguments();
        if (arguments != null
                && arguments.containsKey("checked")
                && this.mToggleServiceSwitchPreference.mChecked
                        != arguments.getBoolean("checked")) {
            this.mMetricsFeatureProvider.action(
                    getContext(), 368, this.mToggleServiceSwitchPreference.mChecked);
        }
        super.onStop();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (view instanceof GlifPreferenceLayout) {
            GlifPreferenceLayout glifPreferenceLayout = (GlifPreferenceLayout) view;
            AccessibilitySetupWizardUtils.updateGlifPreferenceLayout(
                    getContext(),
                    glifPreferenceLayout,
                    getContext().getString(R.string.accessibility_screen_magnification_title),
                    getContext().getString(R.string.accessibility_screen_magnification_intro_text),
                    getContext().getDrawable(R.drawable.ic_accessibility_visibility));
            AccessibilitySetupWizardUtils.setPrimaryButton(
                    getContext(),
                    (FooterBarMixin) glifPreferenceLayout.getMixin(FooterBarMixin.class),
                    new Runnable() { // from class:
                                     // com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ToggleScreenMagnificationPreferenceFragmentForSetupWizard
                                    toggleScreenMagnificationPreferenceFragmentForSetupWizard =
                                            ToggleScreenMagnificationPreferenceFragmentForSetupWizard
                                                    .this;
                            int i =
                                    ToggleScreenMagnificationPreferenceFragmentForSetupWizard
                                            .$r8$clinit;
                            toggleScreenMagnificationPreferenceFragmentForSetupWizard.setResult(0);
                            toggleScreenMagnificationPreferenceFragmentForSetupWizard.finish();
                        }
                    });
        }
        TopIntroPreference topIntroPreference = this.mTopIntroPreference;
        if (topIntroPreference != null) {
            topIntroPreference.setVisible(false);
        }
        Preference preference = this.mSettingsPreference;
        if (preference != null) {
            preference.setVisible(false);
        }
    }
}
