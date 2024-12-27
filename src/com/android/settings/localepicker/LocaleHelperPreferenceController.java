package com.android.settings.localepicker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocaleHelperPreferenceController extends AbstractPreferenceController {
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public static void $r8$lambda$VIMCSqwUCCGuTK9WZq5s2JCdp0E(
            LocaleHelperPreferenceController localeHelperPreferenceController) {
        Context context = localeHelperPreferenceController.mContext;
        Intent helpIntent =
                HelpUtils.getHelpIntent(
                        context,
                        context.getString(R.string.link_locale_picker_footer_learn_more),
                        localeHelperPreferenceController.mContext.getClass().getName());
        if (helpIntent == null) {
            Log.w("LocaleHelperPreferenceController", "HelpIntent is null");
            return;
        }
        localeHelperPreferenceController.mMetricsFeatureProvider.action(
                localeHelperPreferenceController.mContext, 1830, new Pair[0]);
        localeHelperPreferenceController.mContext.startActivity(helpIntent);
    }

    public LocaleHelperPreferenceController(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        updateFooterPreference(
                (FooterPreference) preferenceScreen.findPreference("footer_languages_picker"));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "footer_languages_picker";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public void updateFooterPreference(FooterPreference footerPreference) {
        if (footerPreference != null) {
            footerPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.localepicker.LocaleHelperPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LocaleHelperPreferenceController.$r8$lambda$VIMCSqwUCCGuTK9WZq5s2JCdp0E(
                                    LocaleHelperPreferenceController.this);
                        }
                    });
            footerPreference.setLearnMoreText(
                    this.mContext.getString(R.string.desc_locale_helper_footer_general));
        }
    }
}
