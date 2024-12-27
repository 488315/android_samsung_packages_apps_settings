package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.view.accessibility.CaptioningManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.base.search.AccessibilityBaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CaptioningAppearanceFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(
                    R.xml.captioning_appearance,
                    "com.android.settings.accessibility.CaptioningPropertiesFragment",
                    "captioning_appearance");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.CaptioningAppearanceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityBaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ContentResolver contentResolver = context.getContentResolver();
            if (((CaptioningManager) context.getSystemService(CaptioningManager.class))
                            .getRawUserStyle()
                    == -1) {
                if (CaptioningManager.CaptionStyle.getCustomStyle(contentResolver).edgeType == 0) {
                    ((ArrayList) nonIndexableKeys).add("captioning_edge_color");
                }
                CaptioningManager.CaptionStyle customStyle =
                        CaptioningManager.CaptionStyle.getCustomStyle(contentResolver);
                if (!CaptioningManager.CaptionStyle.hasColor(
                        customStyle.hasWindowColor() ? customStyle.windowColor : 16777215)) {
                    ((ArrayList) nonIndexableKeys).add("captioning_window_opacity");
                }
            } else {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "captioning_typeface",
                        "captioning_foreground_color",
                        "captioning_foreground_opacity",
                        "captioning_edge_type");
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "captioning_edge_color",
                        "captioning_background_color",
                        "captioning_background_opacity",
                        "captioning_window_color");
                arrayList.add("captioning_window_opacity");
            }
            return nonIndexableKeys;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "CaptioningAppearanceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1819;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.captioning_appearance;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        CaptioningPreviewPreferenceController captioningPreviewPreferenceController =
                (CaptioningPreviewPreferenceController)
                        use(CaptioningPreviewPreferenceController.class);
        captioningPreviewPreferenceController.updateState(
                findPreference(captioningPreviewPreferenceController.getPreferenceKey()));
    }
}
