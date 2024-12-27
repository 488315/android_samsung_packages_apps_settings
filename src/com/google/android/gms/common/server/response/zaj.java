package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.converter.zaa;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaj implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        int i4 = 0;
        String str = null;
        String str2 = null;
        zaa zaaVar = null;
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
                    z = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case 4:
                    i3 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 5:
                    z2 = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case 6:
                    str = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 7:
                    i4 = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case '\b':
                    str2 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case '\t':
                    zaaVar = (zaa) SafeParcelReader.createParcelable(parcel, readInt, zaa.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(readInt, parcel);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new FastJsonResponse$Field(i, i2, z, i3, z2, str, i4, str2, zaaVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new FastJsonResponse$Field[i];
    }
}
