package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabx extends BroadcastReceiver {
    public Context zaa;
    public final zan zab;

    public zabx(zan zanVar) {
        this.zab = zanVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        if ("com.google.android.gms".equals(data != null ? data.getSchemeSpecificPart() : null)) {
            zan zanVar = this.zab;
            zap zapVar = zanVar.zab.zaa;
            zapVar.zab.set(null);
            zapVar.zac();
            if (zanVar.zaa.isShowing()) {
                zanVar.zaa.dismiss();
            }
            synchronized (this) {
                try {
                    Context context2 = this.zaa;
                    if (context2 != null) {
                        context2.unregisterReceiver(this);
                    }
                    this.zaa = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
