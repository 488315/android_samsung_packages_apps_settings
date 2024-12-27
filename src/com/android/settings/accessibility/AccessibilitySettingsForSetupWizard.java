package com.android.settings.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.display.AutoBrightnessPreferenceController;
import com.android.settings.display.BrightnessLevelPreferenceController;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifPreferenceLayout;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilitySettingsForSetupWizard extends AccessibilityDashboardFragment
        implements Preference.OnPreferenceChangeListener {
    static final String SCREEN_READER_PACKAGE_NAME = "com.google.android.marvin.talkback";
    static final String SCREEN_READER_SERVICE_NAME =
            "com.google.android.marvin.talkback.TalkBackService";
    static final String SELECT_TO_SPEAK_PACKAGE_NAME = "com.google.android.marvin.talkback";
    static final String SELECT_TO_SPEAK_SERVICE_NAME =
            "com.google.android.accessibility.selecttospeak.SelectToSpeakService";

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        BrightnessLevelPreferenceController brightnessLevelPreferenceController =
                new BrightnessLevelPreferenceController(context, getSettingsLifecycle());
        brightnessLevelPreferenceController.setInSetupWizard(true);
        arrayList.add(brightnessLevelPreferenceController);
        AutoBrightnessPreferenceController autoBrightnessPreferenceController =
                new AutoBrightnessPreferenceController(
                        context, context.getString(R.string.preference_key_auto_brightness));
        autoBrightnessPreferenceController.setInSetupWizard(true);
        arrayList.add(autoBrightnessPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccessibilitySettingsForSetupWizard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 367;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_for_setup_wizard;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(false);
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return viewGroup instanceof GlifPreferenceLayout
                ? ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView
                : super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference != null) {
            return super.onPreferenceTreeClick(preference);
        }
        throw null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (view instanceof GlifPreferenceLayout) {
            GlifPreferenceLayout glifPreferenceLayout = (GlifPreferenceLayout) view;
            AccessibilitySetupWizardUtils.updateGlifPreferenceLayout(
                    getContext(),
                    glifPreferenceLayout,
                    getContext().getString(R.string.vision_settings_title),
                    getContext().getString(R.string.vision_settings_description),
                    getContext().getDrawable(R.drawable.ic_accessibility_visibility));
            AccessibilitySetupWizardUtils.setPrimaryButton(
                    getContext(),
                    (FooterBarMixin) glifPreferenceLayout.getMixin(FooterBarMixin.class),
                    new Runnable() { // from class:
                                     // com.android.settings.accessibility.AccessibilitySettingsForSetupWizard$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AccessibilitySettingsForSetupWizard
                                    accessibilitySettingsForSetupWizard =
                                            AccessibilitySettingsForSetupWizard.this;
                            accessibilitySettingsForSetupWizard.setResult(0);
                            accessibilitySettingsForSetupWizard.finish();
                        }
                    });
        }
    }
}
