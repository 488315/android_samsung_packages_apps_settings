package com.android.settings.wifi.calling;

import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocationPolicyDisclaimer extends DisclaimerItem {

    @VisibleForTesting
    static final String KEY_HAS_AGREED_LOCATION_DISCLAIMER = "key_has_agreed_location_disclaimer";

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final int getMessageId() {
        return R.string.wfc_disclaimer_location_desc_text;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final String getName() {
        return "LocationPolicyDisclaimer";
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final String getPrefKey() {
        return KEY_HAS_AGREED_LOCATION_DISCLAIMER;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final int getTitleId() {
        return R.string.wfc_disclaimer_location_title_text;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final boolean shouldShow() {
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(this.mSubId);
        if (configForSubId == null) {
            configForSubId = CarrierConfigManager.getDefaultConfig();
        }
        if (!configForSubId.getBoolean("show_wfc_location_privacy_policy_bool")) {
            logd("shouldShow: false due to carrier config is false.");
            return false;
        }
        if (!configForSubId.getBoolean("carrier_default_wfc_ims_enabled_bool")) {
            return super.shouldShow();
        }
        logd("shouldShow: false due to WFC is on as default.");
        return false;
    }
}
