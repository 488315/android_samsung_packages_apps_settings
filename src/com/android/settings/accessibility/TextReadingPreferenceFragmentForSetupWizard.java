package com.android.settings.accessibility;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settingslib.Utils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifPreferenceLayout;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TextReadingPreferenceFragmentForSetupWizard extends TextReadingPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void addPreferencesFromResource(int i) {
        super.addPreferencesFromResource(i);
        adjustPreviewPaddingsForSetupWizard();
    }

    @VisibleForTesting
    public void adjustPreviewPaddingsForSetupWizard() {
        TextReadingPreviewPreference textReadingPreviewPreference =
                (TextReadingPreviewPreference) findPreference("preview");
        textReadingPreviewPreference.mLayoutMinHorizontalPadding =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.text_reading_preview_layout_padding_horizontal_min_suw);
        textReadingPreviewPreference.mBackgroundMinHorizontalPadding =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.text_reading_preview_background_padding_horizontal_min_suw);
    }

    @Override // com.android.settings.accessibility.TextReadingPreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1915;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return viewGroup instanceof GlifPreferenceLayout
                ? ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView
                : super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.accessibility.TextReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (view instanceof GlifPreferenceLayout) {
            GlifPreferenceLayout glifPreferenceLayout = (GlifPreferenceLayout) view;
            String string =
                    getContext().getString(R.string.accessibility_text_reading_options_title);
            Drawable drawable = getContext().getDrawable(R.drawable.ic_accessibility_visibility);
            drawable.setTintList(Utils.getColorAttr(getContext(), android.R.attr.colorPrimary));
            AccessibilitySetupWizardUtils.updateGlifPreferenceLayout(
                    getContext(), glifPreferenceLayout, string, null, drawable);
            FooterBarMixin footerBarMixin =
                    (FooterBarMixin) glifPreferenceLayout.getMixin(FooterBarMixin.class);
            final int i = 0;
            AccessibilitySetupWizardUtils.setPrimaryButton(
                    getContext(),
                    footerBarMixin,
                    new Runnable(
                            this) { // from class:
                                    // com.android.settings.accessibility.TextReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0
                        public final /* synthetic */ TextReadingPreferenceFragmentForSetupWizard
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            TextReadingPreferenceFragmentForSetupWizard
                                    textReadingPreferenceFragmentForSetupWizard = this.f$0;
                            switch (i2) {
                                case 0:
                                    int i3 = TextReadingPreferenceFragmentForSetupWizard.$r8$clinit;
                                    textReadingPreferenceFragmentForSetupWizard.setResult(0);
                                    textReadingPreferenceFragmentForSetupWizard.finish();
                                    break;
                                default:
                                    int i4 = TextReadingPreferenceFragmentForSetupWizard.$r8$clinit;
                                    textReadingPreferenceFragmentForSetupWizard.showDialog(
                                            EnterpriseContainerCallback
                                                    .CONTAINER_PACKAGE_UNINSTALL_FAILURE);
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            footerBarMixin.setSecondaryButton(
                    new FooterButton(
                            getContext()
                                    .getString(
                                            R.string.accessibility_text_reading_reset_button_title),
                            new AccessibilitySetupWizardUtils$$ExternalSyntheticLambda0(
                                    (TextReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0)
                                            new Runnable(
                                                    this) { // from class:
                                                            // com.android.settings.accessibility.TextReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0
                                                public final /* synthetic */
                                                TextReadingPreferenceFragmentForSetupWizard f$0;

                                                {
                                                    this.f$0 = this;
                                                }

                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i22 = i2;
                                                    TextReadingPreferenceFragmentForSetupWizard
                                                            textReadingPreferenceFragmentForSetupWizard =
                                                                    this.f$0;
                                                    switch (i22) {
                                                        case 0:
                                                            int i3 =
                                                                    TextReadingPreferenceFragmentForSetupWizard
                                                                            .$r8$clinit;
                                                            textReadingPreferenceFragmentForSetupWizard
                                                                    .setResult(0);
                                                            textReadingPreferenceFragmentForSetupWizard
                                                                    .finish();
                                                            break;
                                                        default:
                                                            int i4 =
                                                                    TextReadingPreferenceFragmentForSetupWizard
                                                                            .$r8$clinit;
                                                            textReadingPreferenceFragmentForSetupWizard
                                                                    .showDialog(
                                                                            EnterpriseContainerCallback
                                                                                    .CONTAINER_PACKAGE_UNINSTALL_FAILURE);
                                                            break;
                                                    }
                                                }
                                            }),
                            3,
                            2132083806),
                    false);
        }
    }
}
