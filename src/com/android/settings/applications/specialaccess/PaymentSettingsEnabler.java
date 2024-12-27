package com.android.settings.applications.specialaccess;

import android.content.Context;
import android.nfc.cardemulation.CardEmulation;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.nfc.BaseNfcEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PaymentSettingsEnabler extends BaseNfcEnabler {
    public final CardEmulation mCardEmuManager;
    public boolean mIsPaymentAvailable;
    public final Preference mPreference;

    public PaymentSettingsEnabler(Context context, Preference preference) {
        super(context);
        this.mCardEmuManager = CardEmulation.getInstance(this.mNfcAdapter);
        this.mIsPaymentAvailable = false;
        this.mPreference = preference;
    }

    @Override // com.android.settings.nfc.BaseNfcEnabler
    public final void handleNfcStateChanged(int i) {
        Preference preference = this.mPreference;
        if (i == 1) {
            preference.setSummary(R.string.nfc_and_payment_settings_payment_off_nfc_off_summary);
            preference.setEnabled(false);
        } else {
            if (i != 3) {
                return;
            }
            if (this.mIsPaymentAvailable) {
                preference.setSummary((CharSequence) null);
                preference.setEnabled(true);
            } else {
                preference.setSummary(
                        R.string.nfc_and_payment_settings_no_payment_installed_summary);
                preference.setEnabled(false);
            }
        }
    }
}
