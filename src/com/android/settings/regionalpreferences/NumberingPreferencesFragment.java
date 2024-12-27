package com.android.settings.regionalpreferences;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.app.LocaleHelper;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NumberingPreferencesFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        NumberingSystemItemController numberingSystemItemController =
                new NumberingSystemItemController(context, getArguments());
        numberingSystemItemController.setParentFragment(this);
        ArrayList arrayList = new ArrayList();
        arrayList.add(numberingSystemItemController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NumberingPreferencesFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return getArguments()
                        .getString("arg_key_regional_preference", ApnSettings.MVNO_NONE)
                        .equals("arg_value_language_select")
                ? 2012
                : 2013;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.regional_preference_numbering_system_page;
    }

    public String initTitle() {
        String string =
                getArguments().getString("arg_key_regional_preference", ApnSettings.MVNO_NONE);
        if (string.isEmpty()) {
            Log.d("NumberingPreferencesFragment", "Option is empty.");
            return ApnSettings.MVNO_NONE;
        }
        Log.i(
                "NumberingPreferencesFragment",
                "[NumberingPreferencesFragment] option is ".concat(string));
        if (string.equals("arg_value_language_select")) {
            return getContext().getString(R.string.numbers_preferences_title);
        }
        if (!string.equals("arg_value_numbering_system_select")) {
            Log.w("NumberingPreferencesFragment", "Incorrect option : ".concat(string));
            return ApnSettings.MVNO_NONE;
        }
        String string2 = getArguments().getString("key_selected_language", ApnSettings.MVNO_NONE);
        if (string2.isEmpty()) {
            Log.w("NumberingPreferencesFragment", "No selected language.");
            return ApnSettings.MVNO_NONE;
        }
        Locale forLanguageTag = Locale.forLanguageTag(string2);
        return LocaleHelper.getDisplayName(forLanguageTag.stripExtensions(), forLanguageTag, true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String initTitle = initTitle();
        if (initTitle().isEmpty()) {
            finish();
        } else {
            getActivity().setTitle(initTitle);
        }
    }
}
