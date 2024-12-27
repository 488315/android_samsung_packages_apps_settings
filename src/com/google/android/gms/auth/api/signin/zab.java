package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zab implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Uri uri = null;
        String str5 = null;
        String str6 = null;
        ArrayList arrayList = null;
        String str7 = null;
        String str8 = null;
        long j = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            switch ((char) readInt) {
                case 1:
                    i = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 2:
                    str = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 3:
                    str2 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 4:
                    str3 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 5:
                    str4 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case 6:
                    uri = (Uri) SafeParcelReader.createParcelable(parcel, readInt, Uri.CREATOR);
                    break;
                case 7:
                    str5 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case '\b':
                    j = SafeParcelReader.readLong(readInt, parcel);
                    break;
                case '\t':
                    str6 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case '\n':
                    arrayList = SafeParcelReader.createTypedList(parcel, readInt, Scope.CREATOR);
                    break;
                case 11:
                    str7 = SafeParcelReader.createString(readInt, parcel);
                    break;
                case '\f':
                    str8 = SafeParcelReader.createString(readInt, parcel);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(readInt, parcel);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new GoogleSignInAccount(
                i, str, str2, str3, str4, uri, str5, j, str6, arrayList, str7, str8);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInAccount[i];
    }
}
