package com.google.android.gms.signin;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.internal.SignInClientImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaa extends Api.AbstractClientBuilder {
    @Override // com.google.android.gms.common.api.Api.AbstractClientBuilder
    public final Api.Client buildClient(
            Context context,
            Looper looper,
            ClientSettings clientSettings,
            Object obj,
            GoogleApiClient.ConnectionCallbacks connectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        clientSettings.getClass();
        Integer num = clientSettings.zaj;
        Bundle bundle = new Bundle();
        bundle.putParcelable(
                "com.google.android.gms.signin.internal.clientRequestedAccount",
                clientSettings.zaa);
        if (num != null) {
            bundle.putInt(
                    "com.google.android.gms.common.internal.ClientSettings.sessionId",
                    num.intValue());
        }
        bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", false);
        bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", false);
        bundle.putString("com.google.android.gms.signin.internal.serverClientId", null);
        bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
        bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", false);
        bundle.putString("com.google.android.gms.signin.internal.hostedDomain", null);
        bundle.putString("com.google.android.gms.signin.internal.logSessionId", null);
        bundle.putBoolean(
                "com.google.android.gms.signin.internal.waitForAccessTokenRefresh", false);
        return new SignInClientImpl(
                context,
                looper,
                clientSettings,
                bundle,
                connectionCallbacks,
                onConnectionFailedListener);
    }
}
