package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzm implements Parcelable.Creator {
    public static void zza(GetServiceRequest getServiceRequest, Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = getServiceRequest.zza;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        int i3 = getServiceRequest.zzb;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i3);
        int i4 = getServiceRequest.zzc;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(i4);
        SafeParcelWriter.writeString(parcel, 4, getServiceRequest.zzd);
        IBinder iBinder = getServiceRequest.zze;
        if (iBinder != null) {
            int zza2 = SafeParcelWriter.zza(5, parcel);
            parcel.writeStrongBinder(iBinder);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        SafeParcelWriter.writeTypedArray(parcel, 6, getServiceRequest.zzf, i);
        SafeParcelWriter.writeBundle(parcel, 7, getServiceRequest.zzg);
        SafeParcelWriter.writeParcelable(parcel, 8, getServiceRequest.zzh, i);
        SafeParcelWriter.writeTypedArray(parcel, 10, getServiceRequest.zzi, i);
        SafeParcelWriter.writeTypedArray(parcel, 11, getServiceRequest.zzj, i);
        boolean z = getServiceRequest.zzk;
        SafeParcelWriter.zzc(parcel, 12, 4);
        parcel.writeInt(z ? 1 : 0);
        int i5 = getServiceRequest.zzl;
        SafeParcelWriter.zzc(parcel, 13, 4);
        parcel.writeInt(i5);
        boolean z2 = getServiceRequest.zzm;
        SafeParcelWriter.zzc(parcel, 14, 4);
        parcel.writeInt(z2 ? 1 : 0);
        SafeParcelWriter.writeString(parcel, 15, getServiceRequest.zzn);
        SafeParcelWriter.zzb(zza, parcel);
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        int i4 = 0;
        boolean z2 = false;
        String str = null;
        IBinder iBinder = null;
        Scope[] scopeArr = null;
        Bundle bundle = null;
        Account account = null;
        Feature[] featureArr = null;
        Feature[] featureArr2 = null;
        String str2 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            switch ((char) readInt) {
                case 1:
                    i = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 2:
                    i2 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 3:
                    i3 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 4:
                    str = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 5:
                    int readSize = SafeParcelReader.readSize(readInt, parcel);
                    int dataPosition = parcel.dataPosition();
                    if (readSize != 0) {
                        iBinder = parcel.readStrongBinder();
                        parcel.setDataPosition(dataPosition + readSize);
                        break;
                    } else {
                        iBinder = null;
                        break;
                    }
                case 6:
                    scopeArr =
                            (Scope[])
                                    SafeParcelReader.createTypedArray(
                                            parcel, readInt, Scope.CREATOR);
                    break;
                case 7:
                    bundle = SafeParcelReader.createBundle(readInt, parcel);
                    break;
                case '\b':
                    account =
                            (Account)
                                    SafeParcelReader.createParcelable(
                                            parcel, readInt, Account.CREATOR);
                    break;
                case '\t':
                default:
                    SafeParcelReader.skipUnknownField(readInt, parcel);
                    break;
                case '\n':
                    featureArr =
                            (Feature[])
                                    SafeParcelReader.createTypedArray(
                                            parcel, readInt, Feature.CREATOR);
                    break;
                case 11:
                    featureArr2 =
                            (Feature[])
                                    SafeParcelReader.createTypedArray(
                                            parcel, readInt, Feature.CREATOR);
                    break;
                case '\f':
                    z = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case '\r':
                    i4 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 14:
                    z2 = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case 15:
                    str2 = SafeParcelReader.createString(readInt, parcel);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new GetServiceRequest(
                i,
                i2,
                i3,
                str,
                iBinder,
                scopeArr,
                bundle,
                account,
                featureArr,
                featureArr2,
                z,
                i4,
                z2,
                str2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new GetServiceRequest[i];
    }
}
