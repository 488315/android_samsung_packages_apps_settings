package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Status;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zai {
    public final int zac;

    public zai(int i) {
        this.zac = i;
    }

    public static /* bridge */ /* synthetic */ Status zah(RemoteException remoteException) {
        return new Status(
                19,
                remoteException.getClass().getSimpleName()
                        + ": "
                        + remoteException.getLocalizedMessage());
    }

    public abstract void zad(Status status);

    public abstract void zae(Exception exc);

    public abstract void zaf(zabq zabqVar);

    public abstract void zag(zaad zaadVar, boolean z);
}
