package com.samsung.android.settings.nfc;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PaymentDefaultDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    public PaymentBackend mBackend;
    public PaymentBackend.PaymentInfo mNewDefault;

    static {
        boolean z = PaymentSettings.DBG;
    }

    public static String sanitizePaymentAppCaption(String str) {
        String trim = str.replace('\n', ' ').replace('\r', ' ').trim();
        return trim.length() > 40 ? trim.substring(0, 40) : trim;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        PaymentBackend paymentBackend = this.mBackend;
        PaymentBackend.PaymentInfo paymentInfo = this.mNewDefault;
        paymentBackend.setDefaultPaymentApp(paymentInfo.componentName, paymentInfo.userId);
        setResult(-1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mBackend = new PaymentBackend(this);
        Intent intent = getIntent();
        ComponentName componentName = (ComponentName) intent.getParcelableExtra("component");
        String stringExtra = intent.getStringExtra("category");
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
        int myUserId = userHandle == null ? UserHandle.myUserId() : userHandle.getIdentifier();
        setResult(0);
        if (componentName == null || stringExtra == null) {
            Log.e("PaymentDefaultDialog", "Component or category are null");
        } else if ("payment".equals(stringExtra)) {
            Iterator it = ((ArrayList) this.mBackend.getPaymentAppInfos()).iterator();
            PaymentBackend.PaymentAppInfo paymentAppInfo = null;
            PaymentBackend.PaymentAppInfo paymentAppInfo2 = null;
            while (it.hasNext()) {
                PaymentBackend.PaymentAppInfo paymentAppInfo3 =
                        (PaymentBackend.PaymentAppInfo) it.next();
                if (componentName.equals(paymentAppInfo3.componentName)
                        && paymentAppInfo3.userHandle.getIdentifier() == myUserId) {
                    paymentAppInfo = paymentAppInfo3;
                }
                if (paymentAppInfo3.isDefault
                        && paymentAppInfo3.userHandle.getIdentifier() == myUserId) {
                    paymentAppInfo2 = paymentAppInfo3;
                }
            }
            if (paymentAppInfo == null) {
                Log.e(
                        "PaymentDefaultDialog",
                        "Component " + componentName + " is not a registered payment service.");
            } else {
                PaymentBackend.PaymentInfo defaultPaymentApp = this.mBackend.getDefaultPaymentApp();
                if (defaultPaymentApp == null
                        || !defaultPaymentApp.componentName.equals(componentName)
                        || defaultPaymentApp.userId != myUserId) {
                    PaymentBackend.PaymentInfo paymentInfo = new PaymentBackend.PaymentInfo();
                    this.mNewDefault = paymentInfo;
                    paymentInfo.componentName = componentName;
                    paymentInfo.userId = myUserId;
                    AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
                    if (paymentAppInfo2 == null) {
                        alertParams.mMessage =
                                String.format(
                                        getString(R.string.nfc_payment_set_default),
                                        sanitizePaymentAppCaption(paymentAppInfo.label.toString()));
                        alertParams.mTitle = getString(R.string.nfc_payment_set_default_label);
                        alertParams.mPositiveButtonText = getString(R.string.common_ok);
                    } else {
                        alertParams.mMessage =
                                String.format(
                                        getString(R.string.nfc_payment_set_default_instead_of),
                                        sanitizePaymentAppCaption(paymentAppInfo.label.toString()),
                                        sanitizePaymentAppCaption(
                                                paymentAppInfo2.label.toString()));
                        alertParams.mTitle = getString(R.string.nfc_payment_replace_default_label);
                        alertParams.mPositiveButtonText = getString(R.string.nfc_payment_replace);
                    }
                    alertParams.mNegativeButtonText = getString(R.string.no);
                    alertParams.mPositiveButtonListener = this;
                    alertParams.mNegativeButtonListener = this;
                    setupAlert();
                    return;
                }
                Log.e(
                        "PaymentDefaultDialog",
                        "Component " + componentName + " is already default.");
            }
        } else {
            Log.e(
                    "PaymentDefaultDialog",
                    "Don't support defaults for category ".concat(stringExtra));
        }
        finish();
    }
}
