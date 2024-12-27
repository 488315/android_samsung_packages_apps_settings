package com.samsung.android.settings.accessibility.vision;

import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.provider.SearchIndexableData;

import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VisibilityEnhancementsFragment extends AccessibilityDashboardFragment {
    public static final List CATEGORY_KEY_LIST =
            List.of(
                    "customized_display_mode_category",
                    "view_clear_category",
                    "view_larger_category",
                    "spoken_assistance_category");
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_visibility_enhancements);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            searchIndexableRaw.title = resources.getString(R.string.sec_screen_zoom_title);
            searchIndexableRaw.screenTitle = resources.getString(R.string.accessibility_settings);
            searchIndexableRaw.keywords = resources.getString(R.string.keywords_screen_zoom);
            ((SearchIndexableData) searchIndexableRaw).key = "sec_screen_size_a11y";
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            searchIndexableRaw2.title =
                    AccessibilityRune.isAtLeastOneUI_6_1()
                            ? resources.getString(R.string.reduce_animations_title)
                            : resources.getString(R.string.accessibility_disable_animations);
            searchIndexableRaw2.screenTitle = resources.getString(R.string.accessibility_settings);
            searchIndexableRaw2.keywords = resources.getString(R.string.keywords_remove_animation);
            ((SearchIndexableData) searchIndexableRaw2).key = "toggle_remove_animations";
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "A11y_Vision";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_visibility_enhancements;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        super.updatePreferenceStates();
        Iterator it = CATEGORY_KEY_LIST.iterator();
        while (it.hasNext()) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) findPreference((String) it.next());
            if (preferenceCategory != null) {
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= preferenceCategory.getPreferenceCount()) {
                        z = true;
                        break;
                    } else if (preferenceCategory.getPreference(i).isVisible()) {
                        break;
                    } else {
                        i++;
                    }
                }
                preferenceCategory.setVisible(!z);
            }
        }
    }
}
