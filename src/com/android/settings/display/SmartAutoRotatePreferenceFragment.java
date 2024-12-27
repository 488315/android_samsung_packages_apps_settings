package com.android.settings.display;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.widget.FooterPreference;

import com.google.common.collect.ImmutableList;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartAutoRotatePreferenceFragment extends DashboardFragment {
    static final String AUTO_ROTATE_MAIN_SWITCH_PREFERENCE_KEY = "auto_rotate_main_switch";
    static final String AUTO_ROTATE_SWITCH_PREFERENCE_KEY = "auto_rotate_switch";
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.auto_rotate_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.SmartAutoRotatePreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ImmutableList createPreferenceControllers =
                    DeviceStateAutoRotationHelper.createPreferenceControllers(context);
            ArrayList arrayList = new ArrayList();
            ImmutableList.Itr listIterator = createPreferenceControllers.listIterator(0);
            while (listIterator.hasNext()) {
                ((BasePreferenceController) ((AbstractPreferenceController) listIterator.next()))
                        .updateRawDataToIndex(arrayList);
            }
            return arrayList;
        }
    }

    public void addHelpLink() {
        FooterPreference footerPreference =
                (FooterPreference) findPreference("auto_rotate_footer_preference");
        if (footerPreference != null) {
            footerPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.display.SmartAutoRotatePreferenceFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SmartAutoRotatePreferenceFragment smartAutoRotatePreferenceFragment =
                                    SmartAutoRotatePreferenceFragment.this;
                            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                    SmartAutoRotatePreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
                            smartAutoRotatePreferenceFragment.startActivityForResult(
                                    HelpUtils.getHelpIntent(
                                            smartAutoRotatePreferenceFragment.getContext(),
                                            smartAutoRotatePreferenceFragment.getString(
                                                    R.string.help_url_auto_rotate_settings),
                                            ApnSettings.MVNO_NONE),
                                    0);
                        }
                    });
            footerPreference.setLearnMoreText(getString(R.string.auto_rotate_link_a11y));
        }
    }

    public void createHeader(SettingsActivity settingsActivity) {
        boolean isDeviceStateRotationEnabled =
                DeviceStateAutoRotationHelper.isDeviceStateRotationEnabled(settingsActivity);
        if (!SmartAutoRotateController.isRotationResolverServiceAvailable(settingsActivity)
                || isDeviceStateRotationEnabled) {
            findPreference(AUTO_ROTATE_MAIN_SWITCH_PREFERENCE_KEY).setVisible(false);
        } else {
            findPreference(AUTO_ROTATE_SWITCH_PREFERENCE_KEY).setVisible(false);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return DeviceStateAutoRotationHelper.createPreferenceControllers(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SmartAutoRotatePreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1867;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.auto_rotate_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Lifecycle lifecycle = getLifecycle();
        Iterator it = useAll(DeviceStateAutoRotateSettingController.class).iterator();
        while (it.hasNext()) {
            ((DeviceStateAutoRotateSettingController) it.next()).init(lifecycle);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        createHeader(settingsActivity);
        Preference findPreference = findPreference("auto_rotate_footer_preference");
        if (findPreference != null) {
            findPreference.setVisible(
                    SmartAutoRotateController.isRotationResolverServiceAvailable(settingsActivity));
            setupFooter();
        }
        return onCreateView;
    }

    public void setupFooter() {
        if (TextUtils.isEmpty(getString(R.string.help_url_auto_rotate_settings))) {
            return;
        }
        addHelpLink();
    }
}
