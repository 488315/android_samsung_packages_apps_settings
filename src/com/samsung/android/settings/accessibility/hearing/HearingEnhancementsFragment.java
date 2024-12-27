package com.samsung.android.settings.accessibility.hearing;

import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;
import com.sec.ims.settings.ImsProfile;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HearingEnhancementsFragment extends AccessibilityDashboardFragment {
    public static final List CATEGORY_KEY_LIST =
            List.of("hearing_category_1", "hearing_category_2");
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_hearing_enhancements);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean supportDynamicRawIndexingWithController() {
            return true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "A11y_Hearing";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return ImsProfile.DEFAULT_DEREG_TIMEOUT;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_hearing_enhancements;
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
