package com.samsung.android.settings.development.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PbapDashboard extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.pbap_settings);
    public Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.development.bluetooth.PbapDashboard$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PbapDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 39;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.pbap_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mContext = getPreferenceScreen().getContext();
        Preference findPreference = getPreferenceManager().findPreference("bluetooth_apply_button");
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.samsung.android.settings.development.bluetooth.PbapDashboard.1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            int state;
                            PbapDashboard pbapDashboard = PbapDashboard.this;
                            BluetoothAdapter adapter =
                                    ((BluetoothManager)
                                                    pbapDashboard.mContext.getSystemService(
                                                            BluetoothManager.class))
                                            .getAdapter();
                            if (adapter != null
                                    && (((state = adapter.getState()) == 11 || state == 12)
                                            && adapter.disable())) {
                                SharedPreferences.Editor edit =
                                        pbapDashboard
                                                .mContext
                                                .getSharedPreferences("bluetooth_restart", 0)
                                                .edit();
                                edit.putBoolean("key_bluetooth_restart", true);
                                edit.apply();
                            }
                            return true;
                        }
                    });
        }
    }
}
