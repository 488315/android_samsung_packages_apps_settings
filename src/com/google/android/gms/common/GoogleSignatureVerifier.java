package com.google.android.gms.common;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GoogleSignatureVerifier {
    public static GoogleSignatureVerifier zza;

    public static final boolean zzb(PackageInfo packageInfo) {
        if (packageInfo == null || packageInfo.signatures == null) {
            return false;
        }
        zzi[] zziVarArr = zzl.zza;
        Signature[] signatureArr = packageInfo.signatures;
        zzi zziVar = null;
        if (signatureArr != null) {
            if (signatureArr.length != 1) {
                Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            } else {
                int i = 0;
                zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
                while (true) {
                    if (i >= zziVarArr.length) {
                        break;
                    }
                    if (zziVarArr[i].equals(zzjVar)) {
                        zziVar = zziVarArr[i];
                        break;
                    }
                    i++;
                }
            }
        }
        return zziVar != null;
    }
}
