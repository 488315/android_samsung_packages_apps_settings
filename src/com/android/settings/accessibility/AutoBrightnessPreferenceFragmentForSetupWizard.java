package com.android.settings.accessibility;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.display.AutoBrightnessSettings;
import com.android.settingslib.Utils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifPreferenceLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoBrightnessPreferenceFragmentForSetupWizard extends AutoBrightnessSettings {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.android.settings.display.AutoBrightnessSettings,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2083;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return viewGroup instanceof GlifPreferenceLayout
                ? ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView
                : super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (view instanceof GlifPreferenceLayout) {
            GlifPreferenceLayout glifPreferenceLayout = (GlifPreferenceLayout) view;
            String string = getContext().getString(R.string.auto_brightness_title);
            Drawable drawable = getContext().getDrawable(R.drawable.ic_accessibility_visibility);
            drawable.setTintList(Utils.getColorAttr(getContext(), android.R.attr.colorPrimary));
            AccessibilitySetupWizardUtils.updateGlifPreferenceLayout(
                    getContext(), glifPreferenceLayout, string, null, drawable);
            AccessibilitySetupWizardUtils.setPrimaryButton(
                    getContext(),
                    (FooterBarMixin) glifPreferenceLayout.getMixin(FooterBarMixin.class),
                    new Runnable() { // from class:
                                     // com.android.settings.accessibility.AutoBrightnessPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AutoBrightnessPreferenceFragmentForSetupWizard
                                    autoBrightnessPreferenceFragmentForSetupWizard =
                                            AutoBrightnessPreferenceFragmentForSetupWizard.this;
                            int i = AutoBrightnessPreferenceFragmentForSetupWizard.$r8$clinit;
                            autoBrightnessPreferenceFragmentForSetupWizard.setResult(0);
                            autoBrightnessPreferenceFragmentForSetupWizard.finish();
                        }
                    });
        }
    }
}
