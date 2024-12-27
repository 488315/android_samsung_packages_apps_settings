package com.samsung.android.settings.security;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.internal.telephony.ISemTelephony;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.SimLockPreferenceController;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.connection.SecMultiSIMTabInterface;
import com.samsung.android.settings.connection.SecSimFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecIccLockSettings extends DashboardFragment
        implements SecMultiSIMTabInterface, SecIccLockManager.MenuControllerInterface {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass4(R.xml.sec_sim_lock_settings);
    public SecIccLockManager mIccLockManager;
    public TabLayout mTabLayout;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.security.SecIccLockSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 102) {
                        return;
                    }
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            SecIccLockSettings.SEARCH_INDEX_DATA_PROVIDER;
                    SecIccLockSettings secIccLockSettings = SecIccLockSettings.this;
                    secIccLockSettings.updatePreferenceStates();
                    if (new SimLockPreferenceController(
                                    secIccLockSettings.getActivity(), "sim_lock_settings")
                            .isSimReady()) {
                        return;
                    }
                    secIccLockSettings.getActivity().finish();
                }
            };
    public final AnonymousClass2 mSimStateReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.security.SecIccLockSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                        AnonymousClass1 anonymousClass1 = SecIccLockSettings.this.mHandler;
                        anonymousClass1.sendMessage(anonymousClass1.obtainMessage(102));
                    }
                }
            };
    public final AnonymousClass3 mAirplaneObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.security.SecIccLockSettings.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    SecIccLockSettings secIccLockSettings = SecIccLockSettings.this;
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            SecIccLockSettings.SEARCH_INDEX_DATA_PROVIDER;
                    secIccLockSettings.updatePreferenceStates();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.security.SecIccLockSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return new SimLockPreferenceController(context, "sim_lock_settings")
                            .getAvailabilityStatus()
                    == 0;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecIccLockSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 56;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_sim_lock_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        SecIccLockManager secIccLockManager = new SecIccLockManager();
        secIccLockManager.mContext = context;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProvider secSimFeatureProvider = featureFactoryImpl.getSecSimFeatureProvider();
        secIccLockManager.mSemTelephony =
                ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) secSimFeatureProvider;
        secSimFeatureProviderImpl.getClass();
        secSimFeatureProviderImpl.mContext = context.getApplicationContext();
        int firstSlotIndex = secSimFeatureProviderImpl.getFirstSlotIndex();
        secIccLockManager.mSlotIndex = firstSlotIndex;
        secIccLockManager.setSimInformation(firstSlotIndex);
        this.mIccLockManager = secIccLockManager;
        secIccLockManager.mMenuController = this;
        ((SecSimTogglePreferenceController) use(SecSimTogglePreferenceController.class))
                .init(this.mIccLockManager);
        ((SecSimPinPreferenceController) use(SecSimPinPreferenceController.class))
                .init(this.mIccLockManager);
        ((SecPersoTogglePreferenceController) use(SecPersoTogglePreferenceController.class))
                .init(this.mIccLockManager);
        ((SecPersoPasswordChangePreferenceController)
                        use(SecPersoPasswordChangePreferenceController.class))
                .init(this.mIccLockManager);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SecIccLockSettings", "onCreate()");
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            finish();
            return;
        }
        getPreferenceScreen().setPersistent();
        if (bundle != null) {
            SecIccLockManager secIccLockManager = this.mIccLockManager;
            secIccLockManager.getClass();
            if (bundle.containsKey("slotIdx")) {
                secIccLockManager.setSimInformation(bundle.getInt("slotIdx", -1));
                secIccLockManager.mIccLockMode = bundle.getInt("iccMode");
                secIccLockManager.mPin = bundle.getString("pinCode");
                secIccLockManager.mErrorMessage = bundle.getString("errorStr");
                secIccLockManager.mIccToState = bundle.getBoolean("iccToState");
                int i = secIccLockManager.mIccLockMode;
                if (i == 3) {
                    secIccLockManager.mOldPin = bundle.getString("oldPinCode");
                } else if (i == 4) {
                    secIccLockManager.mOldPin = bundle.getString("oldPinCode");
                    secIccLockManager.mNewPin = bundle.getString("newPinCode");
                }
                StringBuilder sb2 = new StringBuilder("mode : ");
                sb2.append(secIccLockManager.mIccLockMode);
                sb2.append(", icc to : ");
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        sb2, secIccLockManager.mIccToState, "SecIccLockSettings.SecIccLockManager");
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        View applyTabViewIfNeeded =
                applyTabViewIfNeeded(
                        getContext(), super.onCreateView(layoutInflater, viewGroup, bundle));
        TabLayout tabLayout = (TabLayout) applyTabViewIfNeeded.findViewById(R.id.sim_tabs);
        this.mTabLayout = tabLayout;
        if (tabLayout != null
                && bundle != null
                && bundle.containsKey("currentTab")
                && (i = bundle.getInt("currentTab", -1)) > -1) {
            this.mTabLayout.getTabAt(i).select();
        }
        return applyTabViewIfNeeded;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("SecIccLockSettings", "onDestroy()");
        SecSimPinDialog.releaseDialog();
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("SecIccLockSettings", "onPause()");
        getContext().unregisterReceiver(this.mSimStateReceiver);
        getContentResolver().unregisterContentObserver(this.mAirplaneObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("SecIccLockSettings", "onResume()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        getContext().registerReceiver(this.mSimStateReceiver, intentFilter);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("airplane_mode_on"),
                        false,
                        this.mAirplaneObserver);
        Utils.applyLandscapeFullScreen(getActivity(), getActivity().getWindow());
        if (new SimLockPreferenceController(getActivity(), "sim_lock_settings").isSimReady()) {
            return;
        }
        getActivity().finish();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        if (secIccLockManager.mIccLockMode != 0) {
            bundle.putInt("slotIdx", secIccLockManager.mSlotIndex);
            bundle.putInt("iccMode", secIccLockManager.mIccLockMode);
            SecSimPinDialog secSimPinDialog = SecSimPinDialog.sSimPinDialog;
            if (secSimPinDialog != null && secSimPinDialog.isShowing()) {
                bundle.putString(
                        "pinCode", SecSimPinDialog.sSimPinDialog.mEditText.getText().toString());
            }
            bundle.putString("errorStr", secIccLockManager.mErrorMessage);
            bundle.putBoolean("iccToState", secIccLockManager.mIccToState);
            int i = secIccLockManager.mIccLockMode;
            if (i == 3) {
                bundle.putString("oldPinCode", secIccLockManager.mOldPin);
            } else if (i == 4) {
                bundle.putString("oldPinCode", secIccLockManager.mOldPin);
                bundle.putString("newPinCode", secIccLockManager.mNewPin);
            }
        }
        TabLayout tabLayout = this.mTabLayout;
        if (tabLayout != null) {
            bundle.putInt("currentTab", tabLayout.getSelectedTabPosition());
        }
    }

    @Override // com.samsung.android.settings.connection.SecMultiSIMTabInterface
    public final void onTabChanged(TabLayout.Tab tab) {
        int intValue = ((Integer) tab.tag).intValue();
        Log.i("SecIccLockSettings", "onTabChanged slotId : " + intValue);
        this.mIccLockManager.setSimInformation(intValue);
        updatePreferenceStates();
    }
}
