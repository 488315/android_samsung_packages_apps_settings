package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzo implements ServiceConnection {
    public final /* synthetic */ zzr zza;
    public final Map zzb = new HashMap();
    public int zzc = 2;
    public boolean zzd;
    public IBinder zze;
    public final zzn zzf;
    public ComponentName zzg;

    public zzo(zzr zzrVar, zzn zznVar) {
        this.zza = zzrVar;
        this.zzf = zznVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zza.zzb) {
            try {
                this.zza.zzd.removeMessages(1, this.zzf);
                this.zze = iBinder;
                this.zzg = componentName;
                Iterator it = ((HashMap) this.zzb).values().iterator();
                while (it.hasNext()) {
                    ((ServiceConnection) it.next()).onServiceConnected(componentName, iBinder);
                }
                this.zzc = 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zza.zzb) {
            try {
                this.zza.zzd.removeMessages(1, this.zzf);
                this.zze = null;
                this.zzg = componentName;
                Iterator it = ((HashMap) this.zzb).values().iterator();
                while (it.hasNext()) {
                    ((ServiceConnection) it.next()).onServiceDisconnected(componentName);
                }
                this.zzc = 2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zze(java.lang.String r10, java.util.concurrent.Executor r11) {
        /*
            r9 = this;
            r10 = 3
            r9.zzc = r10
            com.google.android.gms.common.internal.zzr r10 = r9.zza
            com.google.android.gms.common.stats.ConnectionTracker r0 = r10.zzf
            android.content.Context r10 = r10.zzc
            com.google.android.gms.common.internal.zzn r1 = r9.zzf
            java.lang.String r2 = "ConnectionStatusConfig"
            java.lang.String r3 = r1.zzb
            r4 = 0
            if (r3 == 0) goto L6c
            boolean r5 = r1.zzf
            if (r5 == 0) goto L5d
            java.lang.String r5 = "serviceActionBundleKey"
            android.os.Bundle r5 = androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0.m(r5, r3)
            android.content.ContentResolver r6 = r10.getContentResolver()     // Catch: java.lang.IllegalArgumentException -> L2b
            android.net.Uri r7 = com.google.android.gms.common.internal.zzn.zza     // Catch: java.lang.IllegalArgumentException -> L2b
            java.lang.String r8 = "serviceIntentCall"
            android.os.Bundle r5 = r6.call(r7, r8, r4, r5)     // Catch: java.lang.IllegalArgumentException -> L2b
            goto L3a
        L2b:
            r5 = move-exception
            java.lang.String r6 = "Dynamic intent resolution failed: "
            java.lang.String r5 = r5.toString()
            java.lang.String r5 = r6.concat(r5)
            android.util.Log.w(r2, r5)
            r5 = r4
        L3a:
            if (r5 != 0) goto L3d
            goto L46
        L3d:
            java.lang.String r4 = "serviceResponseIntentKey"
            android.os.Parcelable r4 = r5.getParcelable(r4)
            android.content.Intent r4 = (android.content.Intent) r4
        L46:
            if (r4 != 0) goto L5d
            int r5 = r3.length()
            java.lang.String r6 = "Dynamic lookup for intent failed for action: "
            if (r5 == 0) goto L55
            java.lang.String r5 = r6.concat(r3)
            goto L5a
        L55:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r6)
        L5a:
            android.util.Log.w(r2, r5)
        L5d:
            if (r4 == 0) goto L60
            goto L75
        L60:
            android.content.Intent r2 = new android.content.Intent
            r2.<init>(r3)
            java.lang.String r1 = r1.zzc
            android.content.Intent r1 = r2.setPackage(r1)
            goto L76
        L6c:
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            android.content.Intent r4 = r1.setComponent(r4)
        L75:
            r1 = r4
        L76:
            com.google.android.gms.common.internal.zzn r2 = r9.zzf
            int r2 = r2.zze
            r0.getClass()
            android.content.ComponentName r0 = r1.getComponent()
            if (r0 != 0) goto L84
            goto Lad
        L84:
            java.lang.String r0 = r0.getPackageName()
            java.lang.String r3 = "com.google.android.gms"
            r3.equals(r0)
            com.google.android.gms.common.wrappers.Wrappers r3 = com.google.android.gms.common.wrappers.Wrappers.zza     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            com.google.android.gms.common.wrappers.PackageManagerWrapper r3 = r3.zza(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            android.content.Context r3 = r3.zza     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            r4 = 0
            android.content.pm.ApplicationInfo r0 = r3.getApplicationInfo(r0, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            int r0 = r0.flags     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lad
            r3 = 2097152(0x200000, float:2.938736E-39)
            r0 = r0 & r3
            if (r0 == 0) goto Lad
            java.lang.String r10 = "Attempted to bind to a service in a STOPPED package."
            java.lang.String r11 = "ConnectionTracker"
            android.util.Log.w(r11, r10)
            goto Lba
        Lad:
            if (r11 == 0) goto Lb5
            boolean r10 = r10.bindService(r1, r2, r11, r9)
        Lb3:
            r4 = r10
            goto Lba
        Lb5:
            boolean r10 = r10.bindService(r1, r9, r2)
            goto Lb3
        Lba:
            r9.zzd = r4
            if (r4 == 0) goto Ld5
            com.google.android.gms.common.internal.zzr r10 = r9.zza
            com.google.android.gms.internal.common.zzi r10 = r10.zzd
            com.google.android.gms.common.internal.zzn r11 = r9.zzf
            r0 = 1
            android.os.Message r10 = r10.obtainMessage(r0, r11)
            com.google.android.gms.common.internal.zzr r11 = r9.zza
            com.google.android.gms.internal.common.zzi r11 = r11.zzd
            com.google.android.gms.common.internal.zzr r9 = r9.zza
            long r0 = r9.zzh
            r11.sendMessageDelayed(r10, r0)
            return
        Ld5:
            r10 = 2
            r9.zzc = r10
            com.google.android.gms.common.internal.zzr r10 = r9.zza     // Catch: java.lang.IllegalArgumentException -> Le1
            com.google.android.gms.common.stats.ConnectionTracker r11 = r10.zzf     // Catch: java.lang.IllegalArgumentException -> Le1
            android.content.Context r10 = r10.zzc     // Catch: java.lang.IllegalArgumentException -> Le1
            r11.unbindService(r10, r9)     // Catch: java.lang.IllegalArgumentException -> Le1
        Le1:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.gms.common.internal.zzo.zze(java.lang.String,"
                    + " java.util.concurrent.Executor):void");
    }
}
