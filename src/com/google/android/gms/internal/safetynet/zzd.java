package com.google.android.gms.internal.safetynet;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

import com.google.android.gms.common.api.Status;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zzd extends Binder implements zzg, IInterface {
    public zzd() {
        attachInterface(this, "com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i <= 16777215) {
            parcel.enforceInterface(getInterfaceDescriptor());
        } else if (super.onTransact(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i == 1) {
            throw new UnsupportedOperationException();
        }
        if (i == 2) {
            parcel.readString();
            throw new UnsupportedOperationException();
        }
        if (i == 3) {
            throw new UnsupportedOperationException();
        }
        if (i == 4) {
            zzb((Status) zzc.zza(parcel, Status.CREATOR), parcel.readInt() != 0);
            return true;
        }
        if (i == 6) {
            throw new UnsupportedOperationException();
        }
        if (i == 8) {
            zzg(
                    (Status) zzc.zza(parcel, Status.CREATOR),
                    (com.google.android.gms.safetynet.zzd)
                            zzc.zza(parcel, com.google.android.gms.safetynet.zzd.CREATOR));
            return true;
        }
        if (i == 10) {
            parcel.readInt();
            throw new UnsupportedOperationException();
        }
        if (i == 11) {
            throw new UnsupportedOperationException();
        }
        if (i == 15) {
            throw new UnsupportedOperationException();
        }
        if (i != 16) {
            return false;
        }
        parcel.readString();
        parcel.readInt();
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.safetynet.zzg
    public void zzb(Status status, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.safetynet.zzg
    public void zzg(Status status, com.google.android.gms.safetynet.zzd zzdVar) {
        throw new UnsupportedOperationException();
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
