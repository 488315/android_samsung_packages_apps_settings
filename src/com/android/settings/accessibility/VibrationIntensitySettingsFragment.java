package com.android.settings.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VibrationIntensitySettingsFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.accessibility_vibration_intensity_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.VibrationIntensitySettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return VibrationIntensitySettingsFragment.isPageSearchEnabled(context);
        }
    }

    public static boolean isPageSearchEnabled(Context context) {
        return ((Vibrator) context.getSystemService(Vibrator.class)).hasVibrator()
                && context.getResources()
                                .getInteger(R.integer.config_vibration_supported_intensity_levels)
                        > 1;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "VibrationIntensitySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1292;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_vibration_intensity_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        Resources resources = onCreateView.getResources();
        RecyclerView listView = getListView();
        if (listView != null) {
            listView.setPaddingRelative(
                    listView.getPaddingStart(),
                    listView.getPaddingTop(),
                    listView.getPaddingEnd(),
                    listView.getPaddingBottom()
                            + resources.getDimensionPixelSize(
                                    R.dimen.settingslib_listPreferredItemPaddingEnd));
        }
        return onCreateView;
    }
}
