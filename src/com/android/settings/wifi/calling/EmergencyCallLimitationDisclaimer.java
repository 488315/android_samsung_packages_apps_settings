package com.android.settings.wifi.calling;

import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EmergencyCallLimitationDisclaimer extends DisclaimerItem {

    @VisibleForTesting
    static final String KEY_HAS_AGREED_EMERGENCY_LIMITATION_DISCLAIMER =
            "key_has_agreed_emergency_limitation_disclaimer";

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final int getMessageId() {
        return R.string.wfc_disclaimer_emergency_limitation_desc_text;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final String getName() {
        return "EmergencyCallLimitationDisclaimer";
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final String getPrefKey() {
        return KEY_HAS_AGREED_EMERGENCY_LIMITATION_DISCLAIMER;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final int getTitleId() {
        return R.string.wfc_disclaimer_emergency_limitation_title_text;
    }

    @Override // com.android.settings.wifi.calling.DisclaimerItem
    public final boolean shouldShow() {
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(this.mSubId);
        if (configForSubId == null) {
            configForSubId = CarrierConfigManager.getDefaultConfig();
        }
        if (configForSubId.getInt("emergency_notification_delay_int") != -1) {
            return super.shouldShow();
        }
        logd("shouldShow: false due to carrier config is default(-1).");
        return false;
    }
}
