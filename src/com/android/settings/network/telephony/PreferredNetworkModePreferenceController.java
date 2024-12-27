package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.network.CarrierConfigCache;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PreferredNetworkModePreferenceController extends TelephonyBasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "PrefNetworkModeCtrl";
    private CarrierConfigCache mCarrierConfigCache;
    private boolean mIsGlobalCdma;
    private TelephonyManager mTelephonyManager;

    public PreferredNetworkModePreferenceController(Context context, String str) {
        super(context, str);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
    }

    private int getPreferredNetworkMode() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            return MobileNetworkUtils.getNetworkTypeFromRaf(
                    (int) telephonyManager.getAllowedNetworkTypesForReason(0));
        }
        Log.w(TAG, "TelephonyManager is null");
        return -1;
    }

    private int getPreferredNetworkModeSummaryResId(int i) {
        switch (i) {
            case 0:
                return R.string.preferred_network_mode_wcdma_perf_summary;
            case 1:
                return R.string.preferred_network_mode_gsm_only_summary;
            case 2:
                return R.string.preferred_network_mode_wcdma_only_summary;
            case 3:
                return R.string.preferred_network_mode_gsm_wcdma_summary;
            case 4:
                return this.mTelephonyManager.isLteCdmaEvdoGsmWcdmaEnabled()
                        ? R.string.preferred_network_mode_cdma_summary
                        : R.string.preferred_network_mode_cdma_evdo_summary;
            case 5:
                return R.string.preferred_network_mode_cdma_only_summary;
            case 6:
                return R.string.preferred_network_mode_evdo_only_summary;
            case 7:
                return R.string.preferred_network_mode_cdma_evdo_gsm_wcdma_summary;
            case 8:
                return R.string.preferred_network_mode_lte_cdma_evdo_summary;
            case 9:
                return R.string.preferred_network_mode_lte_gsm_wcdma_summary;
            case 10:
                return (this.mTelephonyManager.getPhoneType() == 2
                                || this.mIsGlobalCdma
                                || MobileNetworkUtils.isWorldMode(this.mContext, this.mSubId))
                        ? R.string.preferred_network_mode_lte_cdma_evdo_gsm_wcdma_summary
                        : R.string.preferred_network_mode_lte_summary;
            case 11:
                return R.string.preferred_network_mode_lte_summary;
            case 12:
                return R.string.preferred_network_mode_lte_wcdma_summary;
            case 13:
                return R.string.preferred_network_mode_tdscdma_summary;
            case 14:
                return R.string.preferred_network_mode_tdscdma_wcdma_summary;
            case 15:
                return R.string.preferred_network_mode_lte_tdscdma_summary;
            case 16:
                return R.string.preferred_network_mode_tdscdma_gsm_summary;
            case 17:
                return R.string.preferred_network_mode_lte_tdscdma_gsm_summary;
            case 18:
                return R.string.preferred_network_mode_tdscdma_gsm_wcdma_summary;
            case 19:
                return R.string.preferred_network_mode_lte_tdscdma_wcdma_summary;
            case 20:
                return R.string.preferred_network_mode_lte_tdscdma_gsm_wcdma_summary;
            case 21:
                return R.string.preferred_network_mode_tdscdma_cdma_evdo_gsm_wcdma_summary;
            case 22:
                return R.string.preferred_network_mode_lte_tdscdma_cdma_evdo_gsm_wcdma_summary;
            case 23:
                return R.string.preferred_network_mode_nr_only_summary;
            case 24:
                return R.string.preferred_network_mode_nr_lte_summary;
            case 25:
                return R.string.preferred_network_mode_nr_lte_cdma_evdo_summary;
            case 26:
                return R.string.preferred_network_mode_nr_lte_gsm_wcdma_summary;
            case 27:
            default:
                return R.string.preferred_network_mode_global_summary;
            case 28:
                return R.string.preferred_network_mode_nr_lte_wcdma_summary;
            case 29:
                return R.string.preferred_network_mode_nr_lte_tdscdma_summary;
            case 30:
                return R.string.preferred_network_mode_nr_lte_tdscdma_gsm_summary;
            case 31:
                return R.string.preferred_network_mode_nr_lte_tdscdma_wcdma_summary;
            case 32:
                return R.string.preferred_network_mode_nr_lte_tdscdma_gsm_wcdma_summary;
            case 33:
                return R.string.preferred_network_mode_nr_lte_tdscdma_cdma_evdo_gsm_wcdma_summary;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        return (i == -1
                        || configForSubId == null
                        || configForSubId.getBoolean("hide_carrier_network_settings_bool")
                        || configForSubId.getBoolean("hide_preferred_network_type_bool")
                        || !configForSubId.getBoolean("world_phone_bool"))
                ? 2
                : 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i) {
        this.mSubId = i;
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubId);
        this.mTelephonyManager = createForSubscriptionId;
        this.mIsGlobalCdma =
                createForSubscriptionId.isLteCdmaEvdoGsmWcdmaEnabled()
                        && configForSubId.getBoolean("show_cdma_choices_bool");
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt((String) obj);
        this.mTelephonyManager.setAllowedNetworkTypesForReason(
                0, MobileNetworkUtils.getRafFromNetworkType(parseInt));
        ((ListPreference) preference).setSummary(getPreferredNetworkModeSummaryResId(parseInt));
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ListPreference listPreference = (ListPreference) preference;
        int preferredNetworkMode = getPreferredNetworkMode();
        listPreference.setValue(Integer.toString(preferredNetworkMode));
        listPreference.setSummary(getPreferredNetworkModeSummaryResId(preferredNetworkMode));
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
