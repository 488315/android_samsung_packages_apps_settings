package com.samsung.android.settings.nfc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcForegroundDialog extends AlertActivity implements DialogInterface.OnClickListener {
    public static final boolean DBG = Debug.semIsProductDev();
    public PaymentBackend mBackend;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -2) {
            if (i == -1) {
                this.mBackend.setForegroundMode(true);
            } else if (DBG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i, "Illegal value = ", "NfcForegroundDialog");
            }
        } else if (DBG) {
            Log.d("NfcForegroundDialog", "DialogInterface.BUTTON_NEGATIVE");
        }
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PaymentBackend paymentBackend = new PaymentBackend(this);
        this.mBackend = paymentBackend;
        Iterator it = ((ArrayList) paymentBackend.getPaymentAppInfos()).iterator();
        PaymentBackend.PaymentAppInfo paymentAppInfo = null;
        String str = ApnSettings.MVNO_NONE;
        while (it.hasNext()) {
            PaymentBackend.PaymentAppInfo paymentAppInfo2 =
                    (PaymentBackend.PaymentAppInfo) it.next();
            if (paymentAppInfo2 != null && paymentAppInfo2.isDefault) {
                String trim =
                        paymentAppInfo2
                                .label
                                .toString()
                                .replace('\n', ' ')
                                .replace('\r', ' ')
                                .trim();
                if (trim.length() > 40) {
                    trim = trim.substring(0, 40);
                }
                str = trim;
                paymentAppInfo = paymentAppInfo2;
            }
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mNegativeButtonText = getString(R.string.common_cancel);
        alertParams.mPositiveButtonText = getString(R.string.common_ok);
        alertParams.mNegativeButtonListener = this;
        alertParams.mPositiveButtonListener = this;
        setFinishOnTouchOutside(false);
        if (paymentAppInfo != null) {
            alertParams.mMessage =
                    getString(R.string.nfc_payment_dialog_favor_open, new Object[] {str});
        } else {
            alertParams.mMessage =
                    getString(R.string.nfc_payment_dialog_favor_open_default_unknown);
        }
        if (this.mBackend.isForegroundMode()) {
            dismiss();
            return;
        }
        setupAlert();
        if (DBG) {
            Log.d("NfcForegroundDialog", "setupAlert()");
        }
    }

    public final void onResume() {
        super.onResume();
        if (DBG) {
            Log.d("NfcForegroundDialog", "onResume");
        }
    }
}
