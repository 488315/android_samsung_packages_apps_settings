package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.safetynet.zzn;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaab implements PendingResult.StatusListener {
    public final /* synthetic */ BasePendingResult zaa;
    public final /* synthetic */ zaad zab;

    public zaab(zaad zaadVar, zzn zznVar) {
        this.zab = zaadVar;
        this.zaa = zznVar;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        this.zab.zaa.remove(this.zaa);
    }
}
