package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Log;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.safetynet.zzn;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zae extends zai {
    public final BaseImplementation$ApiMethodImpl zaa;

    public zae(zzn zznVar) {
        super(0);
        this.zaa = zznVar;
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zad(Status status) {
        try {
            this.zaa.setFailedResult(status);
        } catch (IllegalStateException e) {
            Log.w("ApiCallRunner", "Exception reporting failure", e);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zae(Exception exc) {
        String simpleName = exc.getClass().getSimpleName();
        String localizedMessage = exc.getLocalizedMessage();
        try {
            this.zaa.setFailedResult(
                    new Status(
                            10,
                            BackStackRecord$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(
                                            simpleName.length()
                                                    + 2
                                                    + String.valueOf(localizedMessage).length()),
                                    simpleName,
                                    ": ",
                                    localizedMessage)));
        } catch (IllegalStateException e) {
            Log.w("ApiCallRunner", "Exception reporting failure", e);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zaf(zabq zabqVar) {
        try {
            BaseImplementation$ApiMethodImpl baseImplementation$ApiMethodImpl = this.zaa;
            Api.Client client = zabqVar.zac;
            baseImplementation$ApiMethodImpl.getClass();
            try {
                try {
                    baseImplementation$ApiMethodImpl.doExecute(client);
                } catch (DeadObjectException e) {
                    baseImplementation$ApiMethodImpl.setFailedResult(
                            new Status(1, 8, e.getLocalizedMessage(), null, null));
                    throw e;
                }
            } catch (RemoteException e2) {
                baseImplementation$ApiMethodImpl.setFailedResult(
                        new Status(1, 8, e2.getLocalizedMessage(), null, null));
            }
        } catch (RuntimeException e3) {
            zae(e3);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zag(zaad zaadVar, boolean z) {
        Map map = zaadVar.zaa;
        Boolean valueOf = Boolean.valueOf(z);
        BaseImplementation$ApiMethodImpl baseImplementation$ApiMethodImpl = this.zaa;
        map.put(baseImplementation$ApiMethodImpl, valueOf);
        baseImplementation$ApiMethodImpl.addStatusListener(
                new zaab(zaadVar, (zzn) baseImplementation$ApiMethodImpl));
    }
}
