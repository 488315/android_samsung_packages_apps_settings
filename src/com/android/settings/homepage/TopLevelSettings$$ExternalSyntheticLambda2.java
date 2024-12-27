package com.android.settings.homepage;

import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.widget.SecInsetCategoryPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TopLevelSettings$$ExternalSyntheticLambda2
        implements TopLevelSettings.PreferenceJob {
    public final /* synthetic */ TopLevelSettings f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ TopLevelSettings$$ExternalSyntheticLambda2(
            TopLevelSettings topLevelSettings, boolean z) {
        this.f$0 = topLevelSettings;
        this.f$1 = z;
    }

    @Override // com.android.settings.homepage.TopLevelSettings.PreferenceJob
    public final void doForEach(Preference preference) {
        BaseSearchIndexProvider baseSearchIndexProvider =
                TopLevelSettings.SEARCH_INDEX_DATA_PROVIDER;
        TopLevelSettings topLevelSettings = this.f$0;
        topLevelSettings.getClass();
        if (!(preference instanceof SecInsetCategoryPreference)
                || TextUtils.equals(
                        preference.getKey(), SettingsPreferenceFragment.KEY_FOOTER_PREFERENCE)) {
            return;
        }
        boolean z = this.f$1;
        preference.setLayoutResource(
                z
                        ? R.layout.sesl_preference_category
                        : R.layout.sec_no_title_subheader_divider_layout);
        preference.seslSetSubheaderRoundedBackground(z ? 15 : 0);
        ((SecInsetCategoryPreference) preference)
                .setHeight(
                        topLevelSettings
                                .getResources()
                                .getDimensionPixelSize(
                                        z
                                                ? R.dimen.sec_widget_inset_category_height
                                                : R.dimen.sec_no_title_subheader_divider_height));
    }
}
