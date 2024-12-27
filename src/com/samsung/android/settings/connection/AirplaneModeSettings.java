package com.samsung.android.settings.connection;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.WirelessUtils;

import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AirplaneModeSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public SecUnclickablePreference mAirplaneModeDesc;
    public AirplaneModeEnabler mEnabler;
    public SettingsMainSwitchBar mSwitchBar = null;
    public final AnonymousClass1 mAirplaneModeObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.connection.AirplaneModeSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    AirplaneModeSettings.this.setDescription$1$1();
                    AirplaneModeSettings airplaneModeSettings = AirplaneModeSettings.this;
                    SettingsMainSwitchBar settingsMainSwitchBar = airplaneModeSettings.mSwitchBar;
                    if (settingsMainSwitchBar != null) {
                        settingsMainSwitchBar.setChecked(
                                WirelessUtils.isAirplaneModeOn(airplaneModeSettings.getContext()));
                    }
                }
            };

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.mSwitchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "airPlaneModeEnabeld",
                ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "AirplaneModeSettings", "[]")
                        : "AirplaneModeSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3300;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_airplane_mode_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mSwitchBar = ((SettingsActivity) getActivity()).mMainSwitch;
        this.mEnabler = new AirplaneModeEnabler(getContext(), null);
        setHasOptionsMenu(true);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            int i =
                    (WirelessUtils.isAirplaneModeOn(getContext()) || !Utils.isOnCall(getContext()))
                            ? -1
                            : R.string.airplane_mode_toast_impossible_during_call;
            if (Settings.System.getInt(
                            getContext().getContentResolver(), "emergency_message_working_state", 0)
                    == 1) {
                i = R.string.airplane_mode_toast_impossible_while_emergency_sharing;
            }
            if (i != -1) {
                Toast.makeText(getContext(), i, 1).show();
                SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
                if (settingsMainSwitchBar != null) {
                    settingsMainSwitchBar.setChecked(false);
                    return;
                }
                return;
            }
        }
        if (WirelessUtils.isAirplaneModeOn(getContext()) || !Utils.isOnCall(getContext()) || !z) {
            AirplaneModeEnabler airplaneModeEnabler = this.mEnabler;
            if (airplaneModeEnabler != null) {
                airplaneModeEnabler.setAirplaneMode(z);
                return;
            }
            return;
        }
        Toast.makeText(getContext(), R.string.airplane_mode_toast_impossible_during_call, 1).show();
        SettingsMainSwitchBar settingsMainSwitchBar2 = this.mSwitchBar;
        if (settingsMainSwitchBar2 != null) {
            settingsMainSwitchBar2.setChecked(false);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAirplaneModeDesc =
                (SecUnclickablePreference)
                        getPreferenceScreen().findPreference("airplane_mode_desc_key");
        setDescription$1$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        AirplaneModeEnabler airplaneModeEnabler = this.mEnabler;
        if (airplaneModeEnabler != null) {
            airplaneModeEnabler.close();
        }
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        getActivity().getContentResolver().unregisterContentObserver(this.mAirplaneModeObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        int myUserId = UserHandle.myUserId();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getContext(), myUserId, "no_airplane_mode");
        boolean hasBaseUserRestriction =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        getContext(), myUserId, "no_airplane_mode");
        boolean isAirplaneModeOn = WirelessUtils.isAirplaneModeOn(this.mEnabler.mContext);
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        getContext(),
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isAirplaneModeAllowed",
                        new String[] {"false"});
        if (hasBaseUserRestriction || checkIfRestrictionEnforced == null) {
            SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                if (enterprisePolicyEnabled == -1 || enterprisePolicyEnabled == 1) {
                    settingsMainSwitchBar.setChecked(isAirplaneModeOn);
                    this.mSwitchBar.setEnabled(true);
                } else {
                    settingsMainSwitchBar.setEnabled(false);
                    this.mSwitchBar.setChecked(false);
                }
            }
        } else {
            SettingsMainSwitchBar settingsMainSwitchBar2 = this.mSwitchBar;
            if (settingsMainSwitchBar2 != null) {
                settingsMainSwitchBar2.setDisabledByAdmin(checkIfRestrictionEnforced);
            }
        }
        SettingsMainSwitchBar settingsMainSwitchBar3 = this.mSwitchBar;
        if (settingsMainSwitchBar3 != null) {
            settingsMainSwitchBar3.addOnSwitchChangeListener(this);
        }
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("airplane_mode_on"),
                        true,
                        this.mAirplaneModeObserver);
        if (SemGateConfig.isGateEnabled()) {
            if (Settings.Global.getInt(getActivity().getContentResolver(), "airplane_mode_on", 0)
                    == 0) {
                Log.i("GATE", "<GATE-M> AIRPLANE_MODE_STATUS_OFF </GATE-M>");
            } else {
                Log.i("GATE", "<GATE-M> AIRPLANE_MODE_STATUS_ON </GATE-M>");
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        }
        AirplaneModeEnabler airplaneModeEnabler = this.mEnabler;
        if (airplaneModeEnabler != null) {
            airplaneModeEnabler.start();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        AirplaneModeEnabler airplaneModeEnabler = this.mEnabler;
        if (airplaneModeEnabler != null) {
            airplaneModeEnabler.stop();
        }
    }

    public final void setDescription$1$1() {
        if (getActivity() == null) {
            Log.d("AirplaneModeSettings", "setDescription() - getActivity() is null now.");
        } else {
            this.mAirplaneModeDesc.setTitle(
                    (Utils.isTablet() && com.android.settingslib.Utils.isWifiOnly(getContext()))
                            ? R.string.airplane_mode_description_wifi_only
                            : Utils.isTablet()
                                    ? R.string.airplane_mode_description_tablet
                                    : R.string.airplane_mode_description_phone);
        }
    }
}
