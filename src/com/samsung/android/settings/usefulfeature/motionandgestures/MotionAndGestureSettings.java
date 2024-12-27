package com.samsung.android.settings.usefulfeature.motionandgestures;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.usefulfeature.motionandgestures.easymute.EasyMutePreferenceController;
import com.samsung.android.settings.usefulfeature.motionandgestures.palmswipetocapture.PalmSwipeToCapturePreferenceController;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MotionAndGestureSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_ADVANCED;
            hashMap.put("double_tab_to_wake_up", gtsGroup.getGroupName());
            hashMap.put("double_tap_to_sleep", gtsGroup.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "easy_mute";
            searchIndexableRaw.title =
                    String.valueOf(
                            EasyMutePreferenceController.getTitleForMutePauseSwitch(context));
            searchIndexableRaw.screenTitle =
                    context.getResources().getString(R.string.motion_and_gesture_title);
            searchIndexableRaw.keywords =
                    context.getResources().getString(R.string.keywords_easy_mute_settings);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_motion_and_gesture_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MotionAndGestureSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7604;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_motion_and_gesture_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((PalmSwipeToCapturePreferenceController) use(PalmSwipeToCapturePreferenceController.class))
                .updatePreference();
        setLinkedDataView$10();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        setLinkedDataView$10();
    }

    public final void setLinkedDataView$10() {
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            if (Rune.isSamsungDexMode(getContext())
                    || Utils.isDesktopStandaloneMode(getContext())) {
                return;
            }
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.flowId = "95016";
            settingsPreferenceFragmentLinkData.callerMetric = 7604;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_display";
            settingsPreferenceFragmentLinkData.titleRes = R.string.intelligent_sleep_title;
            settingsPreferenceFragmentLinkData.fragment =
                    "com.samsung.android.settings.display.ScreenTimeoutActivity";
            settingsPreferenceFragmentLinkData.extras =
                    AbsAdapter$1$$ExternalSyntheticOutline0.m(
                            ":settings:fragment_args_key", "smart_stay");
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            this.mRelativeLinkView.create(this);
        }
    }
}
