package com.android.settings.nfc;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.CustomDialogPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NfcPaymentPreference extends CustomDialogPreferenceCompat {
    public NfcPaymentPreferenceController mListener;

    public NfcPaymentPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        NfcPaymentPreferenceController nfcPaymentPreferenceController = this.mListener;
        if (nfcPaymentPreferenceController != null) {
            nfcPaymentPreferenceController.onBindViewHolder(preferenceViewHolder);
        }
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        NfcPaymentPreferenceController nfcPaymentPreferenceController = this.mListener;
        if (nfcPaymentPreferenceController != null) {
            nfcPaymentPreferenceController.onPrepareDialogBuilder(builder, onClickListener);
        }
    }

    public NfcPaymentPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public NfcPaymentPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
