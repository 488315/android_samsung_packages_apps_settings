package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabo implements Runnable {
    public final /* synthetic */ zabp zaa;

    public zabo(zabp zabpVar) {
        this.zaa = zabpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Api.Client client = this.zaa.zaa.zac;
        client.disconnect(
                client.getClass().getName().concat(" disconnecting because it was signed out."));
    }
}
