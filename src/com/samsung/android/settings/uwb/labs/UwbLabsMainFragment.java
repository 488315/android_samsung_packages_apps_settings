package com.samsung.android.settings.uwb.labs;

import android.os.Bundle;
import android.util.Log;
import android.uwb.UwbManager;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedHandler;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener;
import com.samsung.android.settings.uwb.labs.view.statistics.StatisticsSummaryFragment;
import com.samsung.android.settings.uwb.labs.view.statistics.WeeklyReportFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbLabsMainFragment extends DashboardFragment {
    public UwbLabsMainFragment$$ExternalSyntheticLambda0 mAdapterStateCallback;
    public UwbManager mUwbManager;
    public UwbStateChangedHandler uwbStateHandler;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_uwb_connectivity_labs_settings;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.uwb.UwbManager$AdapterStateCallback, com.samsung.android.settings.uwb.labs.UwbLabsMainFragment$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("UwbLabsMainFragment", "CREATE: UwbLabsMainFragment");
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.mUwbManager = (UwbManager) getContext().getSystemService(UwbManager.class);
        this.uwbStateHandler = UwbStateChangedHandler.getInstance();
        UwbManager uwbManager = this.mUwbManager;
        if (uwbManager != null) {
            ?? r1 = new UwbManager.AdapterStateCallback() { // from class: com.samsung.android.settings.uwb.labs.UwbLabsMainFragment$$ExternalSyntheticLambda0
                public final void onStateChanged(int i, int i2) {
                    UwbLabsMainFragment uwbLabsMainFragment = UwbLabsMainFragment.this;
                    uwbLabsMainFragment.getClass();
                    Log.i("UwbLabsMainFragment", "+++ UWB Adapter state callback");
                    Iterator it = ((ArrayList) uwbLabsMainFragment.uwbStateHandler.listeners).iterator();
                    while (it.hasNext()) {
                        ((UwbStateChangedListener) it.next()).onUpdatedState(i, i2);
                    }
                }
            };
            this.mAdapterStateCallback = r1;
            uwbManager.registerAdapterStateCallback(newSingleThreadExecutor, (UwbManager.AdapterStateCallback) r1);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        Log.i("UwbLabsMainFragment", "DESTROY: UwbLabsMainFragment");
        UwbManager uwbManager = this.mUwbManager;
        if (uwbManager != null) {
            uwbManager.unregisterAdapterStateCallback(this.mAdapterStateCallback);
            ((ArrayList) this.uwbStateHandler.listeners).clear();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.i("UwbLabsMainFragment", "PAUSE: UwbLabsMainFragment");
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("UwbLabsMainFragment", "RESUME: UwbLabsMainFragment");
        UwbLabsUtils.mDarkMode = (getResources().getConfiguration().uiMode & 48) == 32;
        StatisticsSummaryFragment statisticsSummaryFragment = (StatisticsSummaryFragment) findPreference("uwb_statistics_summary_preference");
        if (statisticsSummaryFragment != null) {
            statisticsSummaryFragment.updatePreference();
        }
        WeeklyReportFragment weeklyReportFragment = (WeeklyReportFragment) findPreference("uwb_weekly_report_preference");
        if (weeklyReportFragment != null) {
            weeklyReportFragment.updatePreference();
        }
    }
}
