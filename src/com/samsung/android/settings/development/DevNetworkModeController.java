package com.samsung.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DevNetworkModeController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin, OnDestroy {
    public final Context mContext;
    public int mCurMode;
    public final int mDisableNrMode;
    public final int mEnableNrMode;
    public final String[] mListSummaries;
    public final String[] mListValues;
    public final String mPreferenceKey;
    public final int mSlotId;
    public int mSubId;
    public TelephonyManager mTelephonyManager;

    public DevNetworkModeController(Context context, String str) {
        super(context);
        this.mSlotId = 0;
        this.mSubId = -1;
        this.mCurMode = -1;
        this.mContext = context;
        this.mPreferenceKey = str;
        this.mSlotId = str.endsWith("1") ? 1 : 0;
        this.mListValues = context.getResources().getStringArray(R.array.dev_network_mode_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.dev_network_mode_entries);
        boolean z = SystemProperties.getInt("ro.telephony.default_network", 26) == 28;
        this.mEnableNrMode = z ? 28 : 26;
        this.mDisableNrMode = z ? 12 : 9;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    public final TelephonyManager getTelephonyManager$1() {
        StringBuilder sb = new StringBuilder("getTelephonyManager mSlotId:");
        int i = this.mSlotId;
        sb.append(i);
        Log.d("DevNetworkModeController", sb.toString());
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId == null) {
            Log.d("DevNetworkModeController", "tm is null because subIds is null");
            return null;
        }
        if (subId[0] != this.mSubId) {
            this.mTelephonyManager = new TelephonyManager(this.mContext, subId[0]);
            this.mSubId = subId[0];
        }
        return this.mTelephonyManager;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!Rune.isChinaModel()) {
            return false;
        }
        TelephonyManager telephonyManager$1 = getTelephonyManager$1();
        if (telephonyManager$1 == null) {
            Log.d("DevNetworkModeController", "isAvailable() tm is null");
            return false;
        }
        int supportedModemCount = telephonyManager$1.getSupportedModemCount();
        if (supportedModemCount == 0) {
            return false;
        }
        int i = this.mSlotId;
        if (i != 1 || supportedModemCount >= 2) {
            return !Utils.isWifiOnly(this.mContext)
                    && (telephonyManager$1.getSimState(i) != 1)
                    && (SystemProperties.getInt("ril.supportSA", -1) != -1)
                    && SemCarrierFeature.getInstance()
                            .getString(
                                    i,
                                    "CarrierFeature_VoiceCall_ConfigOpStyleMobileNetworkSettingMenu",
                                    ApnSettings.MVNO_NONE,
                                    false)
                            .contains("-preferrednetworkmode");
        }
        return false;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        super.onDeveloperOptionsEnabled();
        if (!isAvailable()) {
            Log.i("DevNetworkModeController", "onDeveloperOptionsEnabled : state = FALSE");
            this.mPreference.setVisible(false);
            return;
        }
        Log.i("DevNetworkModeController", "onDeveloperOptionsEnabled : state = TRUE");
        this.mPreference.setVisible(true);
        Log.d("DevNetworkModeController", "[getNetworkMode]");
        TelephonyManager telephonyManager$1 = getTelephonyManager$1();
        if (telephonyManager$1 == null) {
            Log.d("DevNetworkModeController", "[getNetworkMode] tm is null");
        } else {
            this.mCurMode = telephonyManager$1.getPreferredNetworkType(this.mSubId);
            updateState(this.mPreference);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (DATA.DM_FIELD_INDEX.CAP_CACHE_EXP.equals(obj.toString())) {
            Log.d("DevNetworkModeController", "5/4/3/2G");
            return setNetworkMode(this.mEnableNrMode);
        }
        if (!DATA.DM_FIELD_INDEX.SMS_FORMAT.equals(obj.toString())) {
            return false;
        }
        Log.d("DevNetworkModeController", "4/3/2G");
        return setNetworkMode(this.mDisableNrMode);
    }

    public final boolean setNetworkMode(int i) {
        Log.d("DevNetworkModeController", "[setNetworkMode] - mode : " + i);
        TelephonyManager telephonyManager$1 = getTelephonyManager$1();
        if (telephonyManager$1 == null
                || !telephonyManager$1.setPreferredNetworkType(this.mSubId, i)) {
            Log.d("DevNetworkModeController", "[setNetworkMode] failed ");
            return false;
        }
        this.mCurMode = i;
        TelephonyManager.putIntAtIndex(
                this.mContext.getContentResolver(), "preferred_network_mode", this.mSlotId, i);
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "preferred_network_mode" + this.mSubId, i);
        updateState(this.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        char c;
        ListPreference listPreference = (ListPreference) preference;
        if (listPreference == null) {
            return;
        }
        int i = this.mCurMode;
        if (i == this.mEnableNrMode) {
            c = 0;
        } else if (i != this.mDisableNrMode) {
            return;
        } else {
            c = 1;
        }
        listPreference.setValue(this.mListValues[c]);
        listPreference.setSummary(this.mListSummaries[c]);
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("updateState: mCurMode = "),
                this.mCurMode,
                "DevNetworkModeController");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {}
}
