package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.Context;
import android.content.Intent;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.sa.IDMAInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DMABinder {
    public Context context;
    public IDMAInterface dmaInterface;
    public boolean isBind;
    public boolean isTokenFail;
    public AnonymousClass1 serviceConnection;

    public final void bind() {
        if (this.isBind || this.isTokenFail) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName(
                    "com.sec.android.diagmonagent",
                    "com.sec.android.diagmonagent.sa.receiver.SALogReceiverService");
            this.isBind = this.context.bindService(intent, this.serviceConnection, 1);
            Debug.LogD("DMABinder", "bind " + this.isBind);
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }

    public final void unBind() {
        if (this.dmaInterface == null || !this.isBind) {
            return;
        }
        try {
            this.context.unbindService(this.serviceConnection);
            this.isBind = false;
            Debug.LogD("DMABinder", "unbind");
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}
