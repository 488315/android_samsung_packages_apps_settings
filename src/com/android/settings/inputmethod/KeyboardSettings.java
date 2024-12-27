package com.android.settings.inputmethod;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.language.PointerSpeedController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.keyboard_settings);

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        VirtualKeyboardPreferenceController virtualKeyboardPreferenceController =
                new VirtualKeyboardPreferenceController(context);
        PhysicalKeyboardPreferenceController physicalKeyboardPreferenceController =
                new PhysicalKeyboardPreferenceController(context, settingsLifecycle);
        arrayList.add(virtualKeyboardPreferenceController);
        arrayList.add(physicalKeyboardPreferenceController);
        arrayList.add(
                new PreferenceCategoryController(context, "keyboards_category")
                        .setChildren(
                                Arrays.asList(
                                        virtualKeyboardPreferenceController,
                                        physicalKeyboardPreferenceController)));
        PointerSpeedController pointerSpeedController = new PointerSpeedController(context);
        arrayList.add(pointerSpeedController);
        arrayList.add(
                new PreferenceCategoryController(context, "pointer_category")
                        .setChildren(Arrays.asList(pointerSpeedController)));
        arrayList.add(new SpellCheckerPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "KeyboardSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1960;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.keyboard_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "language_and_input_for_work_category",
                "Settings.WORK_PROFILE_KEYBOARDS_AND_TOOLS",
                R.string.language_and_input_for_work_category_title);
        replaceEnterpriseStringTitle(
                "spellcheckers_settings_for_work_pref",
                "Settings.SPELL_CHECKER_FOR_WORK",
                R.string.spellcheckers_settings_for_work_title);
        replaceEnterpriseStringTitle(
                "user_dictionary_settings_for_work_pref",
                "Settings.PERSONAL_DICTIONARY_FOR_WORK",
                R.string.user_dict_settings_for_work_title);
    }
}
