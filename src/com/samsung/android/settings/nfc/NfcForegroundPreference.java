package com.samsung.android.settings.nfc;

import android.content.Context;

import androidx.preference.SwitchPreference;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NfcForegroundPreference extends SwitchPreference {
    public final PaymentBackend mPaymentBackend;

    public NfcForegroundPreference(Context context, PaymentBackend paymentBackend) {
        super(context, null);
        this.mPaymentBackend = paymentBackend;
        setTitle(R.string.nfc_payment_open_app);
        setChecked(paymentBackend.isForegroundMode());
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        super.onClick();
        LoggingHelper.insertFlowLogging(3655);
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        if (this.mPaymentBackend.isForegroundMode() != z) {
            LoggingHelper.insertEventLogging(3653, 7016, z ? 1L : 0L);
        }
        this.mPaymentBackend.setForegroundMode(z);
    }
}
