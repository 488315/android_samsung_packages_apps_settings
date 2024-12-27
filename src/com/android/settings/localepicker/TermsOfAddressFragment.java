package com.android.settings.localepicker;

import android.app.GrammaticalInflectionManager;
import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TermsOfAddressFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.terms_of_address);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TermsOfAddressFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2034;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.terms_of_address;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        TermsOfAddressHelper termsOfAddressHelper = new TermsOfAddressHelper();
        GrammaticalInflectionManager grammaticalInflectionManager =
                (GrammaticalInflectionManager)
                        context.getSystemService(GrammaticalInflectionManager.class);
        termsOfAddressHelper.mGrammaticalInflectionManager = grammaticalInflectionManager;
        termsOfAddressHelper.mSystemGrammaticalGender =
                grammaticalInflectionManager.getSystemGrammaticalGender();
        ((TermsOfAddressNotSpecifiedController) use(TermsOfAddressNotSpecifiedController.class))
                .setTermsOfAddressHelper(termsOfAddressHelper);
        ((TermsOfAddressFeminineController) use(TermsOfAddressFeminineController.class))
                .setTermsOfAddressHelper(termsOfAddressHelper);
        ((TermsOfAddressMasculineController) use(TermsOfAddressMasculineController.class))
                .setTermsOfAddressHelper(termsOfAddressHelper);
        ((TermsOfAddressNeutralController) use(TermsOfAddressNeutralController.class))
                .setTermsOfAddressHelper(termsOfAddressHelper);
    }
}
