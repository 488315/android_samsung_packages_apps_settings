package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzj implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        byte[] bArr = null;
        long j = 0;
        DataHolder dataHolder = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 2) {
                str = SafeParcelReader.createString(readInt, parcel);
            } else if (c == 3) {
                dataHolder =
                        (DataHolder)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, DataHolder.CREATOR);
            } else if (c == 4) {
                parcelFileDescriptor =
                        (ParcelFileDescriptor)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, ParcelFileDescriptor.CREATOR);
            } else if (c == 5) {
                j = SafeParcelReader.readLong(readInt, parcel);
            } else if (c != 6) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                bArr = SafeParcelReader.createByteArray(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        SafeBrowsingData safeBrowsingData = new SafeBrowsingData();
        safeBrowsingData.zzb = str;
        safeBrowsingData.zzc = dataHolder;
        safeBrowsingData.zzd = parcelFileDescriptor;
        safeBrowsingData.zze = j;
        safeBrowsingData.zzf = bArr;
        return safeBrowsingData;
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new SafeBrowsingData[i];
    }
}
