package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.internal.base.zaq;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zac extends zaq {
    public final /* synthetic */ GoogleApiAvailability zaa;
    public final Context zab;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zac(GoogleApiAvailability googleApiAvailability, Context context) {
        super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
        this.zaa = googleApiAvailability;
        this.zab = context.getApplicationContext();
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i != 1) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Don't know how to handle this message: ");
            sb.append(i);
            Log.w("GoogleApiAvailability", sb.toString());
            return;
        }
        Context context = this.zab;
        int i2 = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        GoogleApiAvailability googleApiAvailability = this.zaa;
        int isGooglePlayServicesAvailable =
                googleApiAvailability.isGooglePlayServicesAvailable(context, i2);
        AtomicBoolean atomicBoolean = GooglePlayServicesUtilLight.zzc;
        if (isGooglePlayServicesAvailable == 1
                || isGooglePlayServicesAvailable == 2
                || isGooglePlayServicesAvailable == 3
                || isGooglePlayServicesAvailable == 9) {
            Context context2 = this.zab;
            Intent errorResolutionIntent =
                    googleApiAvailability.getErrorResolutionIntent(
                            context2, isGooglePlayServicesAvailable, "n");
            googleApiAvailability.zae(
                    context2,
                    isGooglePlayServicesAvailable,
                    errorResolutionIntent == null
                            ? null
                            : PendingIntent.getActivity(
                                    context2, 0, errorResolutionIntent, 167772160));
        }
    }
}
