package com.samsung.android.settings.connection;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.secutil.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSimToolkitSettings extends SettingsPreferenceFragment {
    public SimStateChangeReceiver mSimStateChangeReceiver;
    public SecPreferenceCategory[] mSimToolkitCategory;
    public SecPreference mSingleSimPreference;
    public TelephonyManager mTelephonyManager;
    public static final String[] KEY_SIM_TOOLKIT_CATEGORY = {
        "slot1_sim_toolkit_category", "slot2_sim_toolkit_category"
    };
    public static final String[] KEY_SIM_TOOLKIT = {
        "key_slot1_sim_toolkit", "key_slot2_sim_toolkit"
    };
    public static final String[] PROPERTY_STK_TITLE = {"gsm.STK_SETUP_MENU", "gsm.STK_SETUP_MENU2"};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimStateChangeReceiver extends BroadcastReceiver {
        public SimStateChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("SecSimToolkitSettings", "action: " + action);
            if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                SecSimToolkitSettings.this.updateState$5();
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 110;
    }

    public final boolean isStkComponentEnabled(int i) {
        TelephonyManager telephonyManager;
        ComponentName componentName;
        if (i == 0) {
            componentName =
                    new ComponentName("com.android.stk", "com.android.stk.StkLauncherActivity_Chn");
        } else {
            if (i != 1
                    || (telephonyManager = this.mTelephonyManager) == null
                    || !telephonyManager.isMultiSimEnabled()) {
                Log.d("SecSimToolkitSettings", "Single SIM model do not has Stk2");
                return false;
            }
            componentName =
                    new ComponentName(
                            "com.android.stk2", "com.android.stk2.StkLauncherActivity_Chn");
        }
        boolean z = getContext().getPackageManager().getComponentEnabledSetting(componentName) == 1;
        Log.d("SecSimToolkitSettings", "isStkComponentEnabled: " + z);
        return z;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SecSimToolkitSettings", "onCreate");
        this.mTelephonyManager =
                (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
        addPreferencesFromResource(R.xml.sec_sim_toolkit_settings);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SecPreferenceCategory[] secPreferenceCategoryArr = new SecPreferenceCategory[2];
        this.mSimToolkitCategory = secPreferenceCategoryArr;
        String[] strArr = KEY_SIM_TOOLKIT_CATEGORY;
        secPreferenceCategoryArr[0] =
                (SecPreferenceCategory) preferenceScreen.findPreference(strArr[0]);
        this.mSimToolkitCategory[1] =
                (SecPreferenceCategory) preferenceScreen.findPreference(strArr[1]);
        this.mSingleSimPreference =
                (SecPreference) preferenceScreen.findPreference("key_single_sim_toolkit");
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null && telephonyManager.isMultiSimEnabled()) {
            if (getPreferenceScreen().findPreference("key_single_sim_toolkit") != null) {
                getPreferenceScreen().removePreference(this.mSingleSimPreference);
            }
        } else if (this.mSimToolkitCategory != null) {
            for (int i = 0; i < this.mSimToolkitCategory.length; i++) {
                if (getPreferenceScreen().findPreference(strArr[i]) != null) {
                    getPreferenceScreen().removePreference(this.mSimToolkitCategory[i]);
                }
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateState$5();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mSimStateChangeReceiver == null) {
            this.mSimStateChangeReceiver = new SimStateChangeReceiver();
            getContext()
                    .registerReceiver(
                            this.mSimStateChangeReceiver,
                            AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                    "android.intent.action.SIM_STATE_CHANGED"));
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (this.mSimStateChangeReceiver != null) {
            getContext().unregisterReceiver(this.mSimStateChangeReceiver);
            this.mSimStateChangeReceiver = null;
        }
    }

    public final void updateState$5() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        String[] strArr = PROPERTY_STK_TITLE;
        if (telephonyManager == null || !telephonyManager.isMultiSimEnabled()) {
            boolean isStkComponentEnabled = isStkComponentEnabled(0);
            String str = SystemProperties.get(strArr[0]);
            Log.d(
                    "SecSimToolkitSettings",
                    "updateState enabled:" + isStkComponentEnabled + ", title:" + str);
            if (!isStkComponentEnabled || TextUtils.isEmpty(str)) {
                finish();
                return;
            } else {
                this.mSingleSimPreference.setTitle(str);
                return;
            }
        }
        if (this.mSimToolkitCategory != null) {
            for (int i = 0; i < this.mSimToolkitCategory.length; i++) {
                boolean isStkComponentEnabled2 = isStkComponentEnabled(i);
                String str2 = SystemProperties.get(strArr[i]);
                Log.d(
                        "SecSimToolkitSettings",
                        "updateState slotId:"
                                + i
                                + ", enabled:"
                                + isStkComponentEnabled2
                                + ", title:"
                                + str2);
                String[] strArr2 = KEY_SIM_TOOLKIT_CATEGORY;
                if (isStkComponentEnabled2 && !TextUtils.isEmpty(str2)) {
                    if (getPreferenceScreen().findPreference(strArr2[i]) == null) {
                        getPreferenceScreen().addPreference(this.mSimToolkitCategory[i]);
                    }
                    ((SecPreference) this.mSimToolkitCategory[i].findPreference(KEY_SIM_TOOLKIT[i]))
                            .setTitle(str2);
                } else if (getPreferenceScreen().findPreference(strArr2[i]) != null) {
                    getPreferenceScreen().removePreference(this.mSimToolkitCategory[i]);
                }
                if (getPreferenceScreen().getPreferenceCount() == 1) {
                    finish();
                }
            }
        }
    }
}
