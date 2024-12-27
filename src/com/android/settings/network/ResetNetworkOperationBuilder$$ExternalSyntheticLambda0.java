package com.android.settings.network;

import android.content.ContentProviderClient;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ResetNetworkOperationBuilder$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ResetNetworkOperationBuilder f$0;

    public /* synthetic */ ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
            ResetNetworkOperationBuilder resetNetworkOperationBuilder, int i) {
        this.$r8$classId = i;
        this.f$0 = resetNetworkOperationBuilder;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ContentProviderClient unstableTelephonyContentProviderClient;
        int i = this.$r8$classId;
        ResetNetworkOperationBuilder resetNetworkOperationBuilder = this.f$0;
        switch (i) {
            case 0:
                resetNetworkOperationBuilder.getClass();
                try {
                    unstableTelephonyContentProviderClient =
                            resetNetworkOperationBuilder
                                    .getUnstableTelephonyContentProviderClient();
                    if (unstableTelephonyContentProviderClient != null) {
                        try {
                            unstableTelephonyContentProviderClient.call("restartRild", null, null);
                            Log.i("ResetNetworkOpBuilder", "RILD was restarted.");
                        } finally {
                            try {
                                unstableTelephonyContentProviderClient.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                    if (unstableTelephonyContentProviderClient != null) {
                        unstableTelephonyContentProviderClient.close();
                        return;
                    }
                    return;
                } catch (RemoteException e) {
                    Log.w("ResetNetworkOpBuilder", "Fail to restart RILD: " + e);
                    return;
                }
            case 1:
                ((ArrayList) resetNetworkOperationBuilder.mResetSequence)
                        .forEach(new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(2));
                return;
            default:
                resetNetworkOperationBuilder.getClass();
                try {
                    unstableTelephonyContentProviderClient =
                            resetNetworkOperationBuilder
                                    .getUnstableTelephonyContentProviderClient();
                    if (unstableTelephonyContentProviderClient != null) {
                        try {
                            unstableTelephonyContentProviderClient.call(
                                    "restartPhoneProcess", null, null);
                            Log.i("ResetNetworkOpBuilder", "Phone process was restarted.");
                        } finally {
                        }
                    }
                    if (unstableTelephonyContentProviderClient != null) {
                        unstableTelephonyContentProviderClient.close();
                        return;
                    }
                    return;
                } catch (RemoteException e2) {
                    Log.i("ResetNetworkOpBuilder", "Phone process has been restarted: " + e2);
                    return;
                }
        }
    }
}
