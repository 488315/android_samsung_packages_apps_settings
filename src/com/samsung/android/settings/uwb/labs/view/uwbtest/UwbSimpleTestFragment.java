package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.uwb.RangingSession;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedHandler;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbSimpleTestFragment extends DashboardFragment {
    public Context mContext;
    public final List mControllers = new ArrayList();
    public UwbSimpleTestPreference mRangingPreference;
    public UwbSimpleTestFragmentController mUwbSimpleTestFragmentController;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        this.mContext = context;
        UwbSimpleTestFragmentController uwbSimpleTestFragmentController =
                new UwbSimpleTestFragmentController(context, "SimpleTestPreference");
        this.mUwbSimpleTestFragmentController = uwbSimpleTestFragmentController;
        ((ArrayList) this.mControllers).add(uwbSimpleTestFragmentController);
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        Log.d("UwbSimpleTestFragment", "getMetricsCategory");
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        Log.d("UwbSimpleTestFragment", "getPreferenceScreenResId");
        return R.xml.sec_uwb_connectivity_labs_simpletest;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("UwbSimpleTestFragment", "CREATE: UwbSimpleTestFragment");
        if (this.mContext == null) {
            this.mContext = getContext();
        }
        UwbStateChangedHandler uwbStateChangedHandler = UwbStateChangedHandler.getInstance();
        ((ArrayList) uwbStateChangedHandler.listeners)
                .add(
                        new UwbStateChangedListener() { // from class:
                                                        // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestFragment.1
                            @Override // com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener
                            public final void onUpdatedState(int i, int i2) {
                                StringBuilder sb = new StringBuilder("onUpdatedState, state: ");
                                UwbSimpleTestFragment uwbSimpleTestFragment =
                                        UwbSimpleTestFragment.this;
                                uwbSimpleTestFragment.getClass();
                                String str = ApnSettings.MVNO_NONE;
                                sb.append(
                                        i != 0
                                                ? i != 1
                                                        ? i != 2
                                                                ? i != 3
                                                                        ? ApnSettings.MVNO_NONE
                                                                        : "STATE_ENABLED_HW_IDLE"
                                                                : "STATE_ENABLED_ACTIVE"
                                                        : "STATE_ENABLED_INACTIVE"
                                                : "STATE_DISABLED");
                                sb.append(", reason: ");
                                if (i2 == 0) {
                                    str = "STATE_CHANGED_REASON_SESSION_STARTED";
                                } else if (i2 == 1) {
                                    str = "STATE_CHANGED_REASON_ALL_SESSIONS_CLOSED";
                                } else if (i2 == 2) {
                                    str = "STATE_CHANGED_REASON_SYSTEM_POLICY";
                                } else if (i2 == 3) {
                                    str = "STATE_CHANGED_REASON_SYSTEM_BOOT";
                                } else if (i2 == 4) {
                                    str = "STATE_CHANGED_REASON_ERROR_UNKNOWN";
                                } else if (i2 == 5) {
                                    str = "STATE_CHANGED_REASON_SYSTEM_REGULATION";
                                }
                                Utils$$ExternalSyntheticOutline0.m(
                                        sb, str, "UwbSimpleTestFragment");
                                if (i == 0) {
                                    UwbSimpleTestPreference uwbSimpleTestPreference =
                                            uwbSimpleTestFragment.mRangingPreference;
                                    uwbSimpleTestPreference.isUwbEnabled = false;
                                    ((Activity) uwbSimpleTestPreference.mContext)
                                            .runOnUiThread(
                                                    new Runnable() { // from class:
                                                                     // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference.3
                                                        public final /* synthetic */ boolean
                                                                val$isUwbEnabled;

                                                        public AnonymousClass3(boolean z) {
                                                            r2 = z;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            if (!r2) {
                                                                UwbSimpleTestPreference.this
                                                                        .initViewDisenabled();
                                                                return;
                                                            }
                                                            UwbSimpleTestPreference
                                                                    uwbSimpleTestPreference2 =
                                                                            UwbSimpleTestPreference
                                                                                    .this;
                                                            uwbSimpleTestPreference2.mLayout0
                                                                    .setVisibility(0);
                                                            uwbSimpleTestPreference2.mLayout1
                                                                    .setVisibility(0);
                                                            uwbSimpleTestPreference2.mLayout2
                                                                    .setVisibility(8);
                                                        }
                                                    });
                                } else {
                                    UwbSimpleTestPreference uwbSimpleTestPreference2 =
                                            uwbSimpleTestFragment.mRangingPreference;
                                    uwbSimpleTestPreference2.isUwbEnabled = true;
                                    ((Activity) uwbSimpleTestPreference2.mContext)
                                            .runOnUiThread(
                                                    new Runnable() { // from class:
                                                                     // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference.3
                                                        public final /* synthetic */ boolean
                                                                val$isUwbEnabled;

                                                        public AnonymousClass3(boolean z) {
                                                            r2 = z;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            if (!r2) {
                                                                UwbSimpleTestPreference.this
                                                                        .initViewDisenabled();
                                                                return;
                                                            }
                                                            UwbSimpleTestPreference
                                                                    uwbSimpleTestPreference22 =
                                                                            UwbSimpleTestPreference
                                                                                    .this;
                                                            uwbSimpleTestPreference22.mLayout0
                                                                    .setVisibility(0);
                                                            uwbSimpleTestPreference22.mLayout1
                                                                    .setVisibility(0);
                                                            uwbSimpleTestPreference22.mLayout2
                                                                    .setVisibility(8);
                                                        }
                                                    });
                                }
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        RangingSession rangingSession =
                UwbSimpleTestFragmentController.mUwbTestController.mRanging.mRangingSession;
        if (rangingSession != null) {
            rangingSession.close();
        }
        Log.i("UwbSimpleTestFragment", "DESTROY: UwbSimpleTestFragment");
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("UwbSimpleTestFragment", "onPause");
        RangingSession rangingSession =
                UwbSimpleTestFragmentController.mUwbTestController.mRanging.mRangingSession;
        if (rangingSession != null) {
            rangingSession.close();
        }
        UwbSimpleTestPreference uwbSimpleTestPreference = this.mRangingPreference;
        ((Activity) uwbSimpleTestPreference.mContext)
                .runOnUiThread(
                        new UwbSimpleTestPreference.AnonymousClass4(uwbSimpleTestPreference, 0));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("UwbSimpleTestFragment", "onResume");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        super.setPreferenceScreen(preferenceScreen);
        Log.d("UwbSimpleTestFragment", "setPreferenceScreen");
        UwbSimpleTestPreference uwbSimpleTestPreference =
                (UwbSimpleTestPreference) findPreference("uwb_test_simple_preference");
        this.mRangingPreference = uwbSimpleTestPreference;
        uwbSimpleTestPreference.mController = this.mUwbSimpleTestFragmentController;
    }
}
