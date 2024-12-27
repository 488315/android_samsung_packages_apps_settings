package com.google.android.gms.common.internal.service;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.TelemetryLoggingOptions;
import com.google.android.gms.internal.base.zad;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zap extends GmsClient {
    public final TelemetryLoggingOptions zaa;

    public zap(
            Context context,
            Looper looper,
            ClientSettings clientSettings,
            TelemetryLoggingOptions telemetryLoggingOptions,
            ConnectionCallbacks connectionCallbacks,
            OnConnectionFailedListener onConnectionFailedListener) {
        super(
                context,
                looper,
                270,
                clientSettings,
                connectionCallbacks,
                onConnectionFailedListener);
        this.zaa = telemetryLoggingOptions;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface =
                iBinder.queryLocalInterface(
                        "com.google.android.gms.common.internal.service.IClientTelemetryService");
        return queryLocalInterface instanceof zai
                ? (zai) queryLocalInterface
                : new zai(
                        iBinder,
                        "com.google.android.gms.common.internal.service.IClientTelemetryService");
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Feature[] getApiFeatures() {
        return zad.zab;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Bundle getGetServiceRequestExtraArgs() {
        this.zaa.getClass();
        return new Bundle();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient,
              // com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 203400000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.common.internal.service.IClientTelemetryService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.common.telemetry.service.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final boolean getUseDynamicLookup() {
        return true;
    }
}
