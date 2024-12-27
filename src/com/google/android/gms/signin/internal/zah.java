package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zah implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList<String> arrayList = null;
        String str = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                int readSize = SafeParcelReader.readSize(readInt, parcel);
                int dataPosition = parcel.dataPosition();
                if (readSize == 0) {
                    arrayList = null;
                } else {
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.setDataPosition(dataPosition + readSize);
                    arrayList = createStringArrayList;
                }
            } else if (c != 2) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                str = SafeParcelReader.createString(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new zag(str, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zag[i];
    }
}