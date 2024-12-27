package com.android.settings.network.telephony;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;

import androidx.preference.Preference;

import com.android.settings.network.CarrierConfigCache;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CarrierPreferenceController extends TelephonyBasePreferenceController {
    CarrierConfigCache mCarrierConfigCache;

    public CarrierPreferenceController(Context context, String str) {
        super(context, str);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
    }

    private Intent getCarrierSettingsActivityIntent(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        String str = ApnSettings.MVNO_NONE;
        if (configForSubId != null) {
            str =
                    configForSubId.getString(
                            "carrier_settings_activity_component_name_string",
                            ApnSettings.MVNO_NONE);
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(unflattenFromString);
        intent.setFlags(268435456);
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        if (this.mContext.getPackageManager().resolveActivity(intent, 0) != null) {
            return intent;
        }
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        return (configForSubId != null
                        && configForSubId.getBoolean("carrier_settings_enable_bool")
                        && (MobileNetworkUtils.isCdmaOptions(this.mContext, i)
                                || MobileNetworkUtils.isGsmOptions(this.mContext, i)))
                ? 0
                : 2;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Intent carrierSettingsActivityIntent = getCarrierSettingsActivityIntent(this.mSubId);
        if (carrierSettingsActivityIntent == null) {
            return true;
        }
        this.mContext.startActivity(carrierSettingsActivityIntent);
        return true;
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

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
