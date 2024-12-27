package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.wifi.develop.OnTabSelectedListener;
import com.samsung.android.util.SemLog;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ConnectionInfoTabFragment extends SettingsPreferenceFragment {
    public ConnectionInfoRepo mConnectionInfoRepo;
    public ConnectionL2InfoFragment mConnectionL2InfoFragment;
    public ConnectionL3InfoFragment mConnectionL3InfoFragment;
    public ConnectionStatusFragment mConnectionStatusFragment;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_develop_connection_info_main_fragment;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SemLog.d("ConnectionInfoTabFragment", "onCreate: create ConnectionInfoRepo");
        this.mConnectionInfoRepo = new ConnectionInfoRepo(getContext(), getSettingsLifecycle());
        ConnectionInfoTabPreference connectionInfoTabPreference =
                (ConnectionInfoTabPreference) findPreference("connection_info_tab_preference");
        if (connectionInfoTabPreference != null) {
            connectionInfoTabPreference.mOnTabSelectedListener =
                    new OnTabSelectedListener() { // from class:
                                                  // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoTabFragment$$ExternalSyntheticLambda0
                        @Override // com.samsung.android.settings.wifi.develop.OnTabSelectedListener
                        public final void onTabSelected(int i) {
                            ConnectionInfoTabFragment connectionInfoTabFragment =
                                    ConnectionInfoTabFragment.this;
                            FragmentManager childFragmentManager =
                                    connectionInfoTabFragment.getChildFragmentManager();
                            BackStackRecord m =
                                    DialogFragment$$ExternalSyntheticOutline0.m(
                                            childFragmentManager, childFragmentManager);
                            connectionInfoTabFragment.mConnectionStatusFragment =
                                    (ConnectionStatusFragment)
                                            childFragmentManager.findFragmentByTag(
                                                    IMSParameter.CALL.STATUS);
                            connectionInfoTabFragment.mConnectionL2InfoFragment =
                                    (ConnectionL2InfoFragment)
                                            childFragmentManager.findFragmentByTag("l2");
                            connectionInfoTabFragment.mConnectionL3InfoFragment =
                                    (ConnectionL3InfoFragment)
                                            childFragmentManager.findFragmentByTag("l3");
                            ConnectionStatusFragment connectionStatusFragment =
                                    connectionInfoTabFragment.mConnectionStatusFragment;
                            if (connectionStatusFragment != null) {
                                m.detach(connectionStatusFragment);
                            }
                            ConnectionL2InfoFragment connectionL2InfoFragment =
                                    connectionInfoTabFragment.mConnectionL2InfoFragment;
                            if (connectionL2InfoFragment != null) {
                                m.detach(connectionL2InfoFragment);
                            }
                            ConnectionL3InfoFragment connectionL3InfoFragment =
                                    connectionInfoTabFragment.mConnectionL3InfoFragment;
                            if (connectionL3InfoFragment != null) {
                                m.detach(connectionL3InfoFragment);
                            }
                            if (i == 0) {
                                ConnectionStatusFragment connectionStatusFragment2 =
                                        connectionInfoTabFragment.mConnectionStatusFragment;
                                if (connectionStatusFragment2 == null) {
                                    ConnectionInfoRepo connectionInfoRepo =
                                            connectionInfoTabFragment.mConnectionInfoRepo;
                                    Log.i("ConnectionStatusFragment", "newInstance");
                                    ConnectionStatusFragment connectionStatusFragment3 =
                                            new ConnectionStatusFragment();
                                    connectionStatusFragment3.mConnectionInfoRepo =
                                            connectionInfoRepo;
                                    m.doAddOp(
                                            R.id.tabcontent,
                                            connectionStatusFragment3,
                                            IMSParameter.CALL.STATUS,
                                            1);
                                } else {
                                    m.attach(connectionStatusFragment2);
                                }
                            } else if (i == 1) {
                                ConnectionL2InfoFragment connectionL2InfoFragment2 =
                                        connectionInfoTabFragment.mConnectionL2InfoFragment;
                                if (connectionL2InfoFragment2 == null) {
                                    ConnectionInfoRepo connectionInfoRepo2 =
                                            connectionInfoTabFragment.mConnectionInfoRepo;
                                    Log.i("ConnectionL2InfoFragment", "newInstance");
                                    ConnectionL2InfoFragment connectionL2InfoFragment3 =
                                            new ConnectionL2InfoFragment();
                                    connectionL2InfoFragment3.mConnectionInfoRepo =
                                            connectionInfoRepo2;
                                    m.doAddOp(R.id.tabcontent, connectionL2InfoFragment3, "l2", 1);
                                } else {
                                    m.attach(connectionL2InfoFragment2);
                                }
                            } else if (i == 2) {
                                ConnectionL3InfoFragment connectionL3InfoFragment2 =
                                        connectionInfoTabFragment.mConnectionL3InfoFragment;
                                if (connectionL3InfoFragment2 == null) {
                                    ConnectionInfoRepo connectionInfoRepo3 =
                                            connectionInfoTabFragment.mConnectionInfoRepo;
                                    Log.i("ConnectionL3InfoFragment", "newInstance");
                                    ConnectionL3InfoFragment connectionL3InfoFragment3 =
                                            new ConnectionL3InfoFragment();
                                    connectionL3InfoFragment3.mConnectionInfoRepo =
                                            connectionInfoRepo3;
                                    m.doAddOp(R.id.tabcontent, connectionL3InfoFragment3, "l3", 1);
                                } else {
                                    m.attach(connectionL3InfoFragment2);
                                }
                            }
                            m.commitInternal(false);
                        }
                    };
        }
    }
}
