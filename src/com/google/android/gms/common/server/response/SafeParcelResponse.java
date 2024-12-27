package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.SparseArray;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.JsonUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    public static final Parcelable.Creator<SafeParcelResponse> CREATOR = new zaq();
    public final int zaa;
    public final Parcel zab;
    public final int zac;
    public final zan zad;
    public final String zae;
    public int zaf;
    public int zag;

    public SafeParcelResponse(int i, Parcel parcel, zan zanVar) {
        this.zaa = i;
        Preconditions.checkNotNull(parcel);
        this.zab = parcel;
        this.zac = 2;
        this.zad = zanVar;
        this.zae = zanVar == null ? null : zanVar.zac;
        this.zaf = 2;
    }

    public static void zaH(StringBuilder sb, Map map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry entry : map.entrySet()) {
            sparseArray.put(((FastJsonResponse$Field) entry.getValue()).zaf, entry);
        }
        sb.append('{');
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z = false;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            Map.Entry entry2 = (Map.Entry) sparseArray.get((char) readInt);
            if (entry2 != null) {
                if (z) {
                    sb.append(",");
                }
                String str = (String) entry2.getKey();
                FastJsonResponse$Field fastJsonResponse$Field =
                        (FastJsonResponse$Field) entry2.getValue();
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                BigInteger bigInteger = null;
                BigInteger bigInteger2 = null;
                Parcel[] parcelArr = null;
                String[] strArr = null;
                boolean[] zArr = null;
                BigDecimal[] bigDecimalArr = null;
                double[] dArr = null;
                float[] fArr = null;
                long[] jArr = null;
                BigInteger[] bigIntegerArr = null;
                Parcel parcel2 = null;
                if (fastJsonResponse$Field.zak != null) {
                    int i = fastJsonResponse$Field.zac;
                    switch (i) {
                        case 0:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            Integer.valueOf(
                                                    SafeParcelReader.readInt(readInt, parcel))));
                            break;
                        case 1:
                            int readSize = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition = parcel.dataPosition();
                            if (readSize != 0) {
                                byte[] createByteArray = parcel.createByteArray();
                                parcel.setDataPosition(dataPosition + readSize);
                                bigInteger2 = new BigInteger(createByteArray);
                            }
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field, bigInteger2));
                            break;
                        case 2:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            Long.valueOf(
                                                    SafeParcelReader.readLong(readInt, parcel))));
                            break;
                        case 3:
                            SafeParcelReader.zzb(parcel, readInt, 4);
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            Float.valueOf(parcel.readFloat())));
                            break;
                        case 4:
                            SafeParcelReader.zzb(parcel, readInt, 8);
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            Double.valueOf(parcel.readDouble())));
                            break;
                        case 5:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            SafeParcelReader.createBigDecimal(readInt, parcel)));
                            break;
                        case 6:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            Boolean.valueOf(
                                                    SafeParcelReader.readBoolean(
                                                            readInt, parcel))));
                            break;
                        case 7:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            SafeParcelReader.createString(readInt, parcel)));
                            break;
                        case 8:
                        case 9:
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field,
                                            SafeParcelReader.createByteArray(readInt, parcel)));
                            break;
                        case 10:
                            Bundle createBundle = SafeParcelReader.createBundle(readInt, parcel);
                            HashMap hashMap = new HashMap();
                            for (String str2 : createBundle.keySet()) {
                                String string = createBundle.getString(str2);
                                Preconditions.checkNotNull(string);
                                hashMap.put(str2, string);
                            }
                            zaJ(
                                    sb,
                                    fastJsonResponse$Field,
                                    FastSafeParcelableJsonResponse.zaD(
                                            fastJsonResponse$Field, hashMap));
                            break;
                        case 11:
                            throw new IllegalArgumentException(
                                    "Method does not accept concrete type.");
                        default:
                            StringBuilder sb2 = new StringBuilder(36);
                            sb2.append("Unknown field out type = ");
                            sb2.append(i);
                            throw new IllegalArgumentException(sb2.toString());
                    }
                } else if (fastJsonResponse$Field.zad) {
                    sb.append("[");
                    switch (fastJsonResponse$Field.zac) {
                        case 0:
                            int[] createIntArray = SafeParcelReader.createIntArray(readInt, parcel);
                            int length = createIntArray.length;
                            for (int i2 = 0; i2 < length; i2++) {
                                if (i2 != 0) {
                                    sb.append(",");
                                }
                                sb.append(Integer.toString(createIntArray[i2]));
                            }
                            break;
                        case 1:
                            int readSize2 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition2 = parcel.dataPosition();
                            if (readSize2 != 0) {
                                int readInt2 = parcel.readInt();
                                bigIntegerArr = new BigInteger[readInt2];
                                for (int i3 = 0; i3 < readInt2; i3++) {
                                    bigIntegerArr[i3] = new BigInteger(parcel.createByteArray());
                                }
                                parcel.setDataPosition(dataPosition2 + readSize2);
                            }
                            int length2 = bigIntegerArr.length;
                            for (int i4 = 0; i4 < length2; i4++) {
                                if (i4 != 0) {
                                    sb.append(",");
                                }
                                sb.append(bigIntegerArr[i4]);
                            }
                            break;
                        case 2:
                            int readSize3 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition3 = parcel.dataPosition();
                            if (readSize3 != 0) {
                                jArr = parcel.createLongArray();
                                parcel.setDataPosition(dataPosition3 + readSize3);
                            }
                            int length3 = jArr.length;
                            for (int i5 = 0; i5 < length3; i5++) {
                                if (i5 != 0) {
                                    sb.append(",");
                                }
                                sb.append(Long.toString(jArr[i5]));
                            }
                            break;
                        case 3:
                            int readSize4 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition4 = parcel.dataPosition();
                            if (readSize4 != 0) {
                                fArr = parcel.createFloatArray();
                                parcel.setDataPosition(dataPosition4 + readSize4);
                            }
                            int length4 = fArr.length;
                            for (int i6 = 0; i6 < length4; i6++) {
                                if (i6 != 0) {
                                    sb.append(",");
                                }
                                sb.append(Float.toString(fArr[i6]));
                            }
                            break;
                        case 4:
                            int readSize5 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition5 = parcel.dataPosition();
                            if (readSize5 != 0) {
                                dArr = parcel.createDoubleArray();
                                parcel.setDataPosition(dataPosition5 + readSize5);
                            }
                            int length5 = dArr.length;
                            for (int i7 = 0; i7 < length5; i7++) {
                                if (i7 != 0) {
                                    sb.append(",");
                                }
                                sb.append(Double.toString(dArr[i7]));
                            }
                            break;
                        case 5:
                            int readSize6 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition6 = parcel.dataPosition();
                            if (readSize6 != 0) {
                                int readInt3 = parcel.readInt();
                                bigDecimalArr = new BigDecimal[readInt3];
                                for (int i8 = 0; i8 < readInt3; i8++) {
                                    bigDecimalArr[i8] =
                                            new BigDecimal(
                                                    new BigInteger(parcel.createByteArray()),
                                                    parcel.readInt());
                                }
                                parcel.setDataPosition(dataPosition6 + readSize6);
                            }
                            int length6 = bigDecimalArr.length;
                            for (int i9 = 0; i9 < length6; i9++) {
                                if (i9 != 0) {
                                    sb.append(",");
                                }
                                sb.append(bigDecimalArr[i9]);
                            }
                            break;
                        case 6:
                            int readSize7 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition7 = parcel.dataPosition();
                            if (readSize7 != 0) {
                                zArr = parcel.createBooleanArray();
                                parcel.setDataPosition(dataPosition7 + readSize7);
                            }
                            int length7 = zArr.length;
                            for (int i10 = 0; i10 < length7; i10++) {
                                if (i10 != 0) {
                                    sb.append(",");
                                }
                                sb.append(Boolean.toString(zArr[i10]));
                            }
                            break;
                        case 7:
                            int readSize8 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition8 = parcel.dataPosition();
                            if (readSize8 != 0) {
                                strArr = parcel.createStringArray();
                                parcel.setDataPosition(dataPosition8 + readSize8);
                            }
                            int length8 = strArr.length;
                            for (int i11 = 0; i11 < length8; i11++) {
                                if (i11 != 0) {
                                    sb.append(",");
                                }
                                sb.append("\"");
                                sb.append(strArr[i11]);
                                sb.append("\"");
                            }
                            break;
                        case 8:
                        case 9:
                        case 10:
                            throw new UnsupportedOperationException(
                                    "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not"
                                        + " supported");
                        case 11:
                            int readSize9 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition9 = parcel.dataPosition();
                            if (readSize9 != 0) {
                                int readInt4 = parcel.readInt();
                                Parcel[] parcelArr2 = new Parcel[readInt4];
                                for (int i12 = 0; i12 < readInt4; i12++) {
                                    int readInt5 = parcel.readInt();
                                    if (readInt5 != 0) {
                                        int dataPosition10 = parcel.dataPosition();
                                        Parcel obtain = Parcel.obtain();
                                        obtain.appendFrom(parcel, dataPosition10, readInt5);
                                        parcelArr2[i12] = obtain;
                                        parcel.setDataPosition(dataPosition10 + readInt5);
                                    } else {
                                        parcelArr2[i12] = null;
                                    }
                                }
                                parcel.setDataPosition(dataPosition9 + readSize9);
                                parcelArr = parcelArr2;
                            }
                            int length9 = parcelArr.length;
                            for (int i13 = 0; i13 < length9; i13++) {
                                if (i13 > 0) {
                                    sb.append(",");
                                }
                                parcelArr[i13].setDataPosition(0);
                                Preconditions.checkNotNull(fastJsonResponse$Field.zah);
                                Preconditions.checkNotNull(fastJsonResponse$Field.zaj);
                                Map map2 =
                                        (Map)
                                                fastJsonResponse$Field.zaj.zab.get(
                                                        fastJsonResponse$Field.zah);
                                Preconditions.checkNotNull(map2);
                                zaH(sb, map2, parcelArr[i13]);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out.");
                    }
                    sb.append("]");
                } else {
                    switch (fastJsonResponse$Field.zac) {
                        case 0:
                            sb.append(SafeParcelReader.readInt(readInt, parcel));
                            break;
                        case 1:
                            int readSize10 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition11 = parcel.dataPosition();
                            if (readSize10 != 0) {
                                byte[] createByteArray2 = parcel.createByteArray();
                                parcel.setDataPosition(dataPosition11 + readSize10);
                                bigInteger = new BigInteger(createByteArray2);
                            }
                            sb.append(bigInteger);
                            break;
                        case 2:
                            sb.append(SafeParcelReader.readLong(readInt, parcel));
                            break;
                        case 3:
                            SafeParcelReader.zzb(parcel, readInt, 4);
                            sb.append(parcel.readFloat());
                            break;
                        case 4:
                            SafeParcelReader.zzb(parcel, readInt, 8);
                            sb.append(parcel.readDouble());
                            break;
                        case 5:
                            sb.append(SafeParcelReader.createBigDecimal(readInt, parcel));
                            break;
                        case 6:
                            sb.append(SafeParcelReader.readBoolean(readInt, parcel));
                            break;
                        case 7:
                            String createString = SafeParcelReader.createString(readInt, parcel);
                            sb.append("\"");
                            sb.append(JsonUtils.escapeString(createString));
                            sb.append("\"");
                            break;
                        case 8:
                            byte[] createByteArray3 =
                                    SafeParcelReader.createByteArray(readInt, parcel);
                            sb.append("\"");
                            sb.append(
                                    createByteArray3 != null
                                            ? Base64.encodeToString(createByteArray3, 0)
                                            : null);
                            sb.append("\"");
                            break;
                        case 9:
                            byte[] createByteArray4 =
                                    SafeParcelReader.createByteArray(readInt, parcel);
                            sb.append("\"");
                            sb.append(
                                    createByteArray4 != null
                                            ? Base64.encodeToString(createByteArray4, 10)
                                            : null);
                            sb.append("\"");
                            break;
                        case 10:
                            Bundle createBundle2 = SafeParcelReader.createBundle(readInt, parcel);
                            Set<String> keySet = createBundle2.keySet();
                            sb.append("{");
                            boolean z2 = true;
                            for (String str3 : keySet) {
                                if (!z2) {
                                    sb.append(",");
                                }
                                sb.append("\"");
                                sb.append(str3);
                                sb.append("\":\"");
                                sb.append(JsonUtils.escapeString(createBundle2.getString(str3)));
                                sb.append("\"");
                                z2 = false;
                            }
                            sb.append("}");
                            break;
                        case 11:
                            int readSize11 = SafeParcelReader.readSize(readInt, parcel);
                            int dataPosition12 = parcel.dataPosition();
                            if (readSize11 != 0) {
                                parcel2 = Parcel.obtain();
                                parcel2.appendFrom(parcel, dataPosition12, readSize11);
                                parcel.setDataPosition(dataPosition12 + readSize11);
                            }
                            parcel2.setDataPosition(0);
                            Preconditions.checkNotNull(fastJsonResponse$Field.zah);
                            Preconditions.checkNotNull(fastJsonResponse$Field.zaj);
                            Map map3 =
                                    (Map)
                                            fastJsonResponse$Field.zaj.zab.get(
                                                    fastJsonResponse$Field.zah);
                            Preconditions.checkNotNull(map3);
                            zaH(sb, map3, parcel2);
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out");
                    }
                }
                z = true;
            }
        }
        if (parcel.dataPosition() == validateObjectHeader) {
            sb.append('}');
            return;
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("Overread allowed size end=");
        sb3.append(validateObjectHeader);
        throw new SafeParcelReader.ParseException(sb3.toString(), parcel);
    }

    public static final void zaI(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"");
                Preconditions.checkNotNull(obj);
                sb.append(JsonUtils.escapeString(obj.toString()));
                sb.append("\"");
                return;
            case 8:
                sb.append("\"");
                byte[] bArr = (byte[]) obj;
                sb.append(bArr != null ? Base64.encodeToString(bArr, 0) : null);
                sb.append("\"");
                return;
            case 9:
                sb.append("\"");
                byte[] bArr2 = (byte[]) obj;
                sb.append(bArr2 != null ? Base64.encodeToString(bArr2, 10) : null);
                sb.append("\"");
                return;
            case 10:
                Preconditions.checkNotNull(obj);
                HashMap hashMap = (HashMap) obj;
                sb.append("{");
                boolean z = true;
                for (String str : hashMap.keySet()) {
                    if (!z) {
                        sb.append(",");
                    }
                    String str2 = (String) hashMap.get(str);
                    sb.append("\"");
                    sb.append(str);
                    sb.append("\":");
                    if (str2 == null) {
                        sb.append("null");
                    } else {
                        sb.append("\"");
                        sb.append(str2);
                        sb.append("\"");
                    }
                    z = false;
                }
                sb.append("}");
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder sb2 = new StringBuilder(26);
                sb2.append("Unknown type = ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static final void zaJ(
            StringBuilder sb, FastJsonResponse$Field fastJsonResponse$Field, Object obj) {
        if (!fastJsonResponse$Field.zab) {
            zaI(sb, fastJsonResponse$Field.zaa, obj);
            return;
        }
        ArrayList arrayList = (ArrayList) obj;
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            zaI(sb, fastJsonResponse$Field.zaa, arrayList.get(i));
        }
        sb.append("]");
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
    public final Object getValueObject(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse
    public final boolean isPrimitiveFieldSet(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final String toString() {
        Preconditions.checkNotNull(this.zad, "Cannot convert to JSON on client side.");
        Parcel zaE = zaE();
        zaE.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zan zanVar = this.zad;
        String str = this.zae;
        Preconditions.checkNotNull(str);
        Map map = (Map) zanVar.zab.get(str);
        Preconditions.checkNotNull(map);
        zaH(sb, map, zaE);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        Parcel zaE = zaE();
        if (zaE != null) {
            int zza2 = SafeParcelWriter.zza(2, parcel);
            parcel.appendFrom(zaE, 0, zaE.dataSize());
            SafeParcelWriter.zzb(zza2, parcel);
        }
        int i3 = this.zac;
        SafeParcelWriter.writeParcelable(
                parcel, 3, i3 != 0 ? i3 != 1 ? this.zad : this.zad : null, i);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public final Parcel zaE() {
        int i = this.zaf;
        if (i == 0) {
            int zza = SafeParcelWriter.zza(20293, this.zab);
            this.zag = zza;
            SafeParcelWriter.zzb(zza, this.zab);
            this.zaf = 2;
        } else if (i == 1) {
            SafeParcelWriter.zzb(this.zag, this.zab);
            this.zaf = 2;
        }
        return this.zab;
    }
}
