package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zan implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        long j = 0;
        long j2 = 0;
        String str = null;
        String str2 = null;
        int i5 = -1;
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
                    j = SafeParcelReader.readLong(readInt, parcel);
                    break;
                case 5:
                    j2 = SafeParcelReader.readLong(readInt, parcel);
                    break;
                case 6:
                    str = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 7:
                    str2 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case '\b':
                    i4 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case '\t':
                    i5 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(readInt, parcel);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new MethodInvocation(i, i2, i3, j, j2, str, str2, i4, i5);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new MethodInvocation[i];
    }
}
