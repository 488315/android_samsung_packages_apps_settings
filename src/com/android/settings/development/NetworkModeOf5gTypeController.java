package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkModeOf5gTypeController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin, OnDestroy {
    public final Context mContext;
    public final String[] mListSummaries;
    public final String[] mListValues;
    public int mStateMode;

    public NetworkModeOf5gTypeController(Context context) {
        super(context);
        this.mStateMode = -1;
        this.mContext = context;
        this.mListValues =
                context.getResources().getStringArray(R.array.network_mode_of_5g_activation_values);
        this.mListSummaries =
                context.getResources()
                        .getStringArray(R.array.network_mode_of_5g_activation_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "network_mode_of_5g_activation";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (ConnectionsUtils.isNoSIM()) {
            Log.d("NetworkModeOf5gTypeController", "isAvailable : menu disable because of no sim ");
            return false;
        }
        boolean z = SystemProperties.getInt("persist.ril.supportNrModefromCp", -1) == 0;
        if (!Utils.isWifiOnly(this.mContext) && ConnectionsUtils.isSupport5GConcept() && z) {
            return Rune.isChinaModel()
                    || Rune.isChinaHKTWModel()
                    || Rune.isDomesticSKTModel()
                    || Rune.isDomesticKTTModel()
                    || Rune.isDomesticLGTModel();
        }
        return false;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        super.onDeveloperOptionsEnabled();
        if (!isAvailable()) {
            Log.i("NetworkModeOf5gTypeController", "onDeveloperOptionsEnabled : state = FALSE");
            this.mPreference.setVisible(false);
            return;
        }
        Log.i("NetworkModeOf5gTypeController", "onDeveloperOptionsEnabled : state = TRUE");
        this.mPreference.setVisible(true);
        Log.d("NetworkModeOf5gTypeController", "[getNrMode]");
        try {
            this.mStateMode =
                    ((TelephonyManager) this.mContext.getSystemService("phone")).semGetNrMode();
        } catch (Exception e) {
            Log.e("NetworkModeOf5gTypeController", e.toString());
        }
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d("NetworkModeOf5gTypeController", "onPreferenceChange: newValue = " + obj.toString());
        if ("NSA".equals(obj.toString())) {
            Log.d("NetworkModeOf5gTypeController", "[NSA mode]");
            setNrMode(1);
        } else if ("SA".equals(obj.toString())) {
            Log.d("NetworkModeOf5gTypeController", "[SA mode]");
            setNrMode(2);
        } else if ("BOTH".equals(obj.toString())) {
            Log.d("NetworkModeOf5gTypeController", "[SA+NSA mode]");
            setNrMode(0);
        }
        return true;
    }

    public final void setNrMode(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "[setNrMode] - mode : ", "NetworkModeOf5gTypeController");
        try {
            ((TelephonyManager) this.mContext.getSystemService("phone")).semSetNrMode(i);
            this.mStateMode = i;
        } catch (Exception e) {
            Log.e("NetworkModeOf5gTypeController", e.toString());
        }
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        if (listPreference == null) {
            return;
        }
        int i = this.mStateMode;
        char c = 2;
        boolean z = true;
        if (i != 0) {
            if (i == 1) {
                c = 0;
            } else if (i != 2) {
                return;
            } else {
                c = 1;
            }
        }
        listPreference.setValue(this.mListValues[c]);
        listPreference.setSummary(this.mListSummaries[c]);
        if (Rune.isDomesticSKTModel() || Rune.isDomesticLGTModel()) {
            EngineeringModeManager engineeringModeManager =
                    new EngineeringModeManager(this.mContext);
            if (engineeringModeManager.isConnected()) {
                int status = engineeringModeManager.getStatus(50);
                r2 = status == 1;
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        status, "isEmTokenAllowed() emStatus = ", "NetworkModeOf5gTypeController");
            }
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "isEmTokenAllowed() returnValue = ", "NetworkModeOf5gTypeController", r2);
            z = r2;
        }
        preference.setEnabled(z);
        Log.i(
                "NetworkModeOf5gTypeController",
                "updateState: mStateMode = " + this.mStateMode + " , bEnabled = " + z);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {}
}
