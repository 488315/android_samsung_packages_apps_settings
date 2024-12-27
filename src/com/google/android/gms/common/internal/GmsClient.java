package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GmsClient extends BaseGmsClient implements Api.Client, zaj {
    public final Set zac;
    public final Account zad;

    public GmsClient(Context context, Handler handler, int i, ClientSettings clientSettings) {
        super(
                context,
                handler,
                GmsClientSupervisor.getInstance(context),
                GoogleApiAvailability.zab,
                i,
                null,
                null);
        Preconditions.checkNotNull(clientSettings);
        this.zad = clientSettings.zaa;
        Set set = clientSettings.zac;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!set.contains((Scope) it.next())) {
                throw new IllegalStateException(
                        "Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        this.zac = set;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Account getAccount() {
        return this.zad;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Set getScopes() {
        return this.zac;
    }

    @Override // com.google.android.gms.common.api.Api.Client
    public final Set getScopesForConnectionlessNonSignIn() {
        return requiresSignIn() ? this.zac : Collections.emptySet();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GmsClient(
            android.content.Context r10,
            android.os.Looper r11,
            int r12,
            com.google.android.gms.common.internal.ClientSettings r13,
            com.google.android.gms.common.api.internal.ConnectionCallbacks r14,
            com.google.android.gms.common.api.internal.OnConnectionFailedListener r15) {
        /*
            r9 = this;
            com.google.android.gms.common.internal.zzr r3 = com.google.android.gms.common.internal.GmsClientSupervisor.getInstance(r10)
            com.google.android.gms.common.GoogleApiAvailability r4 = com.google.android.gms.common.GoogleApiAvailability.zab
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r14)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r15)
            r0 = r9
            r1 = r10
            r2 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            r8 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.gms.common.internal.GmsClient.<init>(android.content.Context,"
                    + " android.os.Looper, int,"
                    + " com.google.android.gms.common.internal.ClientSettings,"
                    + " com.google.android.gms.common.api.internal.ConnectionCallbacks,"
                    + " com.google.android.gms.common.api.internal.OnConnectionFailedListener):void");
    }

    public GmsClient(
            Context context,
            Looper looper,
            GmsClientSupervisor gmsClientSupervisor,
            GoogleApiAvailability googleApiAvailability,
            int i,
            ClientSettings clientSettings,
            ConnectionCallbacks connectionCallbacks,
            OnConnectionFailedListener onConnectionFailedListener) {
        super(
                context,
                looper,
                gmsClientSupervisor,
                googleApiAvailability,
                i,
                connectionCallbacks == null ? null : new zah(connectionCallbacks),
                onConnectionFailedListener == null ? null : new zai(onConnectionFailedListener),
                clientSettings.zah);
        this.zad = clientSettings.zaa;
        Set set = clientSettings.zac;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!set.contains((Scope) it.next())) {
                throw new IllegalStateException(
                        "Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        this.zac = set;
    }
}
