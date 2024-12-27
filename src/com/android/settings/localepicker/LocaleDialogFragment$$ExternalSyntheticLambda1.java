package com.android.settings.localepicker;

import android.util.Log;
import android.window.OnBackInvokedCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class LocaleDialogFragment$$ExternalSyntheticLambda1
        implements OnBackInvokedCallback {
    @Override // android.window.OnBackInvokedCallback
    public final void onBackInvoked() {
        int i = LocaleDialogFragment.$r8$clinit;
        Log.d("LocaleDialogFragment", "Do not back to previous page if the dialog is displaying.");
    }
}
