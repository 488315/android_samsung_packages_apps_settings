package com.google.android.gms.common.api.internal;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.Preconditions;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zao implements Runnable {
    public final /* synthetic */ zap zaa;
    public final zam zab;

    public zao(zap zapVar, zam zamVar) {
        this.zaa = zapVar;
        this.zab = zamVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zaa.zaa) {
            ConnectionResult connectionResult = this.zab.zab;
            if ((connectionResult.zzb == 0 || connectionResult.zzc == null) ? false : true) {
                zap zapVar = this.zaa;
                LifecycleFragment lifecycleFragment = zapVar.mLifecycleFragment;
                Activity activity = zapVar.getActivity();
                PendingIntent pendingIntent = connectionResult.zzc;
                Preconditions.checkNotNull(pendingIntent);
                int i = this.zab.zaa;
                int i2 = GoogleApiActivity.$r8$clinit;
                Intent intent = new Intent(activity, (Class<?>) GoogleApiActivity.class);
                intent.putExtra("pending_intent", pendingIntent);
                intent.putExtra("failing_client_id", i);
                intent.putExtra("notify_manager", false);
                lifecycleFragment.startActivityForResult(intent, 1);
                return;
            }
            zap zapVar2 = this.zaa;
            if (zapVar2.zac.getErrorResolutionIntent(
                            zapVar2.getActivity(), connectionResult.zzb, null)
                    != null) {
                zap zapVar3 = this.zaa;
                GoogleApiAvailability googleApiAvailability = zapVar3.zac;
                Activity activity2 = zapVar3.getActivity();
                zap zapVar4 = this.zaa;
                googleApiAvailability.zag(
                        activity2, zapVar4.mLifecycleFragment, connectionResult.zzb, zapVar4);
                return;
            }
            if (connectionResult.zzb != 18) {
                zap zapVar5 = this.zaa;
                int i3 = this.zab.zaa;
                zapVar5.zab.set(null);
                zapVar5.zab(connectionResult, i3);
                return;
            }
            zap zapVar6 = this.zaa;
            GoogleApiAvailability googleApiAvailability2 = zapVar6.zac;
            Activity activity3 = zapVar6.getActivity();
            zap zapVar7 = this.zaa;
            googleApiAvailability2.getClass();
            ProgressBar progressBar =
                    new ProgressBar(activity3, null, R.attr.progressBarStyleLarge);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity3);
            builder.setView(progressBar);
            builder.setMessage(com.google.android.gms.common.internal.zac.zad(activity3, 18));
            builder.setPositiveButton(
                    ApnSettings.MVNO_NONE, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            GoogleApiAvailability.zad(
                    activity3, create, "GooglePlayServicesUpdatingDialog", zapVar7);
            zap zapVar8 = this.zaa;
            GoogleApiAvailability googleApiAvailability3 = zapVar8.zac;
            Context applicationContext = zapVar8.getActivity().getApplicationContext();
            zan zanVar = new zan(this, create);
            googleApiAvailability3.getClass();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            zabx zabxVar = new zabx(zanVar);
            applicationContext.registerReceiver(zabxVar, intentFilter);
            zabxVar.zaa = applicationContext;
            if (GooglePlayServicesUtilLight.zza(applicationContext)) {
                return;
            }
            zap zapVar9 = this.zaa;
            zapVar9.zab.set(null);
            zapVar9.zac();
            if (create.isShowing()) {
                create.dismiss();
            }
            synchronized (zabxVar) {
                try {
                    Context context = zabxVar.zaa;
                    if (context != null) {
                        context.unregisterReceiver(zabxVar);
                    }
                    zabxVar.zaa = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
