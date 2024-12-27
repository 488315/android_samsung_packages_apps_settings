package com.google.android.gms.common;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.dynamic.ObjectWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zzi extends com.google.android.gms.internal.common.zzb implements zzz {
    public final int zza;

    public zzi(byte[] bArr) {
        super("com.google.android.gms.common.internal.ICertData");
        if (bArr.length != 25) {
            throw new IllegalArgumentException();
        }
        this.zza = Arrays.hashCode(bArr);
    }

    public static byte[] zze(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj != null && (obj instanceof zzz)) {
            try {
                zzz zzzVar = (zzz) obj;
                if (((zzi) zzzVar).zza != this.zza) {
                    return false;
                }
                return Arrays.equals(zzf(), (byte[]) new ObjectWrapper(((zzi) zzzVar).zzf()).zza);
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.common.zzb
    public final boolean zza(int i, Parcel parcel, Parcel parcel2) {
        if (i == 1) {
            ObjectWrapper objectWrapper = new ObjectWrapper(zzf());
            parcel2.writeNoException();
            int i2 = com.google.android.gms.internal.common.zzc.$r8$clinit;
            parcel2.writeStrongBinder(objectWrapper);
        } else {
            if (i != 2) {
                return false;
            }
            int i3 = this.zza;
            parcel2.writeNoException();
            parcel2.writeInt(i3);
        }
        return true;
    }

    public abstract byte[] zzf();
}
