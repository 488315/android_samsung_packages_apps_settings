package com.google.android.gms.common.internal.service;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.TelemetryLoggingOptions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zan extends Api.AbstractClientBuilder {
    @Override // com.google.android.gms.common.api.Api.AbstractClientBuilder
    public final /* synthetic */ Api.Client buildClient(
            Context context,
            Looper looper,
            ClientSettings clientSettings,
            Object obj,
            ConnectionCallbacks connectionCallbacks,
            OnConnectionFailedListener onConnectionFailedListener) {
        return new zap(
                context,
                looper,
                clientSettings,
                (TelemetryLoggingOptions) obj,
                connectionCallbacks,
                onConnectionFailedListener);
    }
}
