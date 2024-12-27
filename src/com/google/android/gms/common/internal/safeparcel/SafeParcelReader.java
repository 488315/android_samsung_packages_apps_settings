package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SafeParcelReader {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ParseException extends RuntimeException {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ParseException(java.lang.String r4, android.os.Parcel r5) {
            /*
                r3 = this;
                int r0 = r5.dataPosition()
                int r5 = r5.dataSize()
                java.lang.String r1 = java.lang.String.valueOf(r4)
                int r1 = r1.length()
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                int r1 = r1 + 41
                r2.<init>(r1)
                r2.append(r4)
                java.lang.String r4 = " Parcel: pos="
                r2.append(r4)
                r2.append(r0)
                java.lang.String r4 = " size="
                r2.append(r4)
                r2.append(r5)
                java.lang.String r4 = r2.toString()
                r3.<init>(r4)
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException.<init>(java.lang.String,"
                        + " android.os.Parcel):void");
        }
    }

    public static BigDecimal createBigDecimal(int i, Parcel parcel) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        int readInt = parcel.readInt();
        parcel.setDataPosition(dataPosition + readSize);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    public static Bundle createBundle(int i, Parcel parcel) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        Bundle readBundle = parcel.readBundle();
        parcel.setDataPosition(dataPosition + readSize);
        return readBundle;
    }

    public static byte[] createByteArray(int i, Parcel parcel) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(dataPosition + readSize);
        return createByteArray;
    }

    public static int[] createIntArray(int i, Parcel parcel) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        int[] createIntArray = parcel.createIntArray();
        parcel.setDataPosition(dataPosition + readSize);
        return createIntArray;
    }

    public static Parcelable createParcelable(Parcel parcel, int i, Parcelable.Creator creator) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        Parcelable parcelable = (Parcelable) creator.createFromParcel(parcel);
        parcel.setDataPosition(dataPosition + readSize);
        return parcelable;
    }

    public static String createString(int i, Parcel parcel) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        String readString = parcel.readString();
        parcel.setDataPosition(dataPosition + readSize);
        return readString;
    }

    public static Object[] createTypedArray(Parcel parcel, int i, Parcelable.Creator creator) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        Object[] createTypedArray = parcel.createTypedArray(creator);
        parcel.setDataPosition(dataPosition + readSize);
        return createTypedArray;
    }

    public static ArrayList createTypedList(Parcel parcel, int i, Parcelable.Creator creator) {
        int readSize = readSize(i, parcel);
        int dataPosition = parcel.dataPosition();
        if (readSize == 0) {
            return null;
        }
        ArrayList createTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(dataPosition + readSize);
        return createTypedArrayList;
    }

    public static void ensureAtEnd(int i, Parcel parcel) {
        if (parcel.dataPosition() == i) {
            return;
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(i);
        throw new ParseException(sb.toString(), parcel);
    }

    public static boolean readBoolean(int i, Parcel parcel) {
        zzb(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static int readInt(int i, Parcel parcel) {
        zzb(parcel, i, 4);
        return parcel.readInt();
    }

    public static long readLong(int i, Parcel parcel) {
        zzb(parcel, i, 8);
        return parcel.readLong();
    }

    public static int readSize(int i, Parcel parcel) {
        return (i & (-65536)) != -65536 ? (char) (i >> 16) : parcel.readInt();
    }

    public static void skipUnknownField(int i, Parcel parcel) {
        parcel.setDataPosition(parcel.dataPosition() + readSize(i, parcel));
    }

    public static int validateObjectHeader(Parcel parcel) {
        int readInt = parcel.readInt();
        int readSize = readSize(readInt, parcel);
        int dataPosition = parcel.dataPosition();
        if (((char) readInt) != 20293) {
            String valueOf = String.valueOf(Integer.toHexString(readInt));
            throw new ParseException(
                    valueOf.length() != 0
                            ? "Expected object header. Got 0x".concat(valueOf)
                            : new String("Expected object header. Got 0x"),
                    parcel);
        }
        int i = readSize + dataPosition;
        if (i >= dataPosition && i <= parcel.dataSize()) {
            return i;
        }
        StringBuilder sb = new StringBuilder(54);
        sb.append("Size read is invalid start=");
        sb.append(dataPosition);
        sb.append(" end=");
        sb.append(i);
        throw new ParseException(sb.toString(), parcel);
    }

    public static void zzb(Parcel parcel, int i, int i2) {
        int readSize = readSize(i, parcel);
        if (readSize == i2) {
            return;
        }
        String hexString = Integer.toHexString(readSize);
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 46);
        sb.append("Expected size ");
        sb.append(i2);
        sb.append(" got ");
        sb.append(readSize);
        throw new ParseException(
                BackStackRecord$$ExternalSyntheticOutline0.m(sb, " (0x", hexString, ")"), parcel);
    }
}
