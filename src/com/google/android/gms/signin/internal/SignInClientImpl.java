package com.google.android.gms.signin.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SignInClientImpl extends GmsClient implements Api.Client {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean zab;
    public final ClientSettings zac;
    public final Bundle zad;
    public final Integer zae;

    public SignInClientImpl(
            Context context,
            Looper looper,
            ClientSettings clientSettings,
            Bundle bundle,
            GoogleApiClient.ConnectionCallbacks connectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zab = true;
        this.zac = clientSettings;
        this.zad = bundle;
        this.zae = clientSettings.zaj;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface =
                iBinder.queryLocalInterface(
                        "com.google.android.gms.signin.internal.ISignInService");
        return queryLocalInterface instanceof zaf
                ? (zaf) queryLocalInterface
                : new zaf(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Bundle getGetServiceRequestExtraArgs() {
        ClientSettings clientSettings = this.zac;
        if (!this.zzl.getPackageName().equals(clientSettings.zag)) {
            this.zad.putString(
                    "com.google.android.gms.signin.internal.realClientPackageName",
                    clientSettings.zag);
        }
        return this.zad;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient,
              // com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient,
              // com.google.android.gms.common.api.Api.Client
    public final boolean requiresSignIn() {
        return this.zab;
    }
}
