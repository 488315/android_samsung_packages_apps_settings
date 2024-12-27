package com.samsung.android.settings.usefulfeature.multiwindow;

import android.content.Context;
import android.provider.SearchIndexableResource;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MultiwindowSettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = MultiwindowSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_useful_feature_multi_window;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MultiwindowSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 78000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_useful_feature_multi_window;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.usefulfeature.labs.LabsSettings";
            settingsPreferenceFragmentLinkData.flowId = "68100";
            settingsPreferenceFragmentLinkData.callerMetric = 78000;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key", "multi_window_for_all_apps");
            settingsPreferenceFragmentLinkData.titleRes =
                    R.string.sec_multi_window_for_all_apps_title;
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            this.mRelativeLinkView.create(this);
        }
    }
}
