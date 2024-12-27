package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaf implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        String[] strArr = null;
        CursorWindow[] cursorWindowArr = null;
        Bundle bundle = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                int readSize = SafeParcelReader.readSize(readInt, parcel);
                int dataPosition = parcel.dataPosition();
                if (readSize == 0) {
                    strArr = null;
                } else {
                    String[] createStringArray = parcel.createStringArray();
                    parcel.setDataPosition(dataPosition + readSize);
                    strArr = createStringArray;
                }
            } else if (c == 2) {
                cursorWindowArr =
                        (CursorWindow[])
                                SafeParcelReader.createTypedArray(
                                        parcel, readInt, CursorWindow.CREATOR);
            } else if (c == 3) {
                i3 = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 4) {
                bundle = SafeParcelReader.createBundle(readInt, parcel);
            } else if (c != 1000) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                i2 = SafeParcelReader.readInt(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i3, bundle);
        dataHolder.zab = new Bundle();
        int i4 = 0;
        while (true) {
            String[] strArr2 = dataHolder.zag;
            if (i4 >= strArr2.length) {
                break;
            }
            dataHolder.zab.putInt(strArr2[i4], i4);
            i4++;
        }
        dataHolder.zac = new int[dataHolder.zah.length];
        int i5 = 0;
        while (true) {
            CursorWindow[] cursorWindowArr2 = dataHolder.zah;
            if (i >= cursorWindowArr2.length) {
                return dataHolder;
            }
            dataHolder.zac[i] = i5;
            i5 += dataHolder.zah[i].getNumRows() - (i5 - cursorWindowArr2[i].getStartPosition());
            i++;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new DataHolder[i];
    }
}
