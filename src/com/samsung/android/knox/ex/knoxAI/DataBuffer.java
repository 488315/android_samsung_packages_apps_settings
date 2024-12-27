package com.samsung.android.knox.ex.knoxAI;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.util.Half;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DataBuffer implements Parcelable {
    public static final Parcelable.Creator<DataBuffer> CREATOR = new AnonymousClass1();
    public byte dataFormat;
    public float[] dataOriginal;
    public SharedMemory dataShared;
    public byte dataSource;
    public byte dataType;
    public FileDescriptor filedesc;
    public int[] shape;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ex.knoxAI.DataBuffer$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<DataBuffer> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataBuffer createFromParcel(Parcel parcel) {
            return new DataBuffer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataBuffer[] newArray(int i) {
            return new DataBuffer[i];
        }
    }

    public DataBuffer() {}

    public static Half readFloat16FromBytes(byte[] bArr, int i) {
        int i2;
        return new Half(
                (short)
                        ((i < 0 || (i2 = i + 1) >= bArr.length)
                                ? 0
                                : (bArr[i] & 255) | ((bArr[i2] & 255) << 8)));
    }

    public static float readFloatFromBytes(byte[] bArr, int i) {
        int i2;
        if (i < 4 || i - 1 >= bArr.length) {
            return 0.0f;
        }
        return Float.intBitsToFloat(
                (bArr[i - 4] & 255)
                        | (bArr[i2] << 24)
                        | ((bArr[i - 2] & 255) << 16)
                        | ((bArr[i - 3] & 255) << 8));
    }

    public static byte[] readFloatToBytes(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        byte[] bArr = new byte[fArr.length * 4];
        for (int i = 0; i < fArr.length; i++) {
            int floatToIntBits = Float.floatToIntBits(fArr[i]);
            int i2 = i * 4;
            bArr[i2 + 3] = (byte) (floatToIntBits >> 24);
            bArr[i2 + 2] = (byte) (floatToIntBits >> 16);
            bArr[i2 + 1] = (byte) (floatToIntBits >> 8);
            bArr[i2] = (byte) floatToIntBits;
        }
        return bArr;
    }

    public static int readIntFromBytes(byte[] bArr, int i) {
        int i2;
        if (i < 0 || (i2 = i + 3) >= bArr.length) {
            return 0;
        }
        return (bArr[i] & 255)
                | (bArr[i2] << 24)
                | ((bArr[i + 2] & 255) << 16)
                | ((bArr[i + 1] & 255) << 8);
    }

    public static byte[] readIntToBytes(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        byte[] bArr = new byte[iArr.length * 4];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 4;
            int i3 = iArr[i];
            bArr[i2 + 3] = (byte) (i3 >> 24);
            bArr[i2 + 2] = (byte) (i3 >> 16);
            bArr[i2 + 1] = (byte) (i3 >> 8);
            bArr[i2] = (byte) i3;
        }
        return bArr;
    }

    public static JSONObject readJSONObjectFromBytes(byte[] bArr) {
        try {
            return new JSONObject(new String(bArr));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long readLongFromBytes(byte[] bArr, int i) {
        long j = 0;
        if (i >= 0 && i + 7 < bArr.length) {
            for (int i2 = 0; i2 < 8; i2++) {
                j += (bArr[i + i2] & 255) << (i2 * 8);
            }
        }
        return j;
    }

    public static byte[] readStringToBytes(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        for (String str : strArr) {
            length += str.length();
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        for (String str2 : strArr) {
            allocate.put(str2.getBytes(StandardCharsets.UTF_8));
            allocate.put((byte) 0);
        }
        return allocate.array();
    }

    public void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte getDataFormat() {
        return this.dataFormat;
    }

    public float[] getDataOriginal() {
        return this.dataOriginal;
    }

    public SharedMemory getDataShared() {
        return this.dataShared;
    }

    public byte getDataSource() {
        return this.dataSource;
    }

    public byte getDataType() {
        return this.dataType;
    }

    public FileDescriptor getFileDesc() {
        return this.filedesc;
    }

    public int[] getShape() {
        return this.shape;
    }

    public void setDataFormat(byte b) {
        this.dataFormat = b;
    }

    public void setDataOriginal(float[] fArr) {
        this.dataOriginal = fArr;
    }

    public void setDataShared(SharedMemory sharedMemory) {
        this.dataShared = sharedMemory;
    }

    public void setDataSource(byte b) {
        this.dataSource = b;
    }

    public void setDataType(byte b) {
        this.dataType = b;
    }

    public void setFileDesc(FileDescriptor fileDescriptor) {
        this.filedesc = fileDescriptor;
    }

    public void setShape(int[] iArr) {
        this.shape = iArr;
    }

    @NonNull
    public String toString() {
        return "DBufr ["
                + ((int) this.dataType)
                + ","
                + ((int) this.dataFormat)
                + "],shp=["
                + Arrays.toString(this.shape)
                + "],["
                + Arrays.toString(this.dataOriginal)
                + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.dataType);
        parcel.writeByte(this.dataFormat);
        int[] iArr = this.shape;
        if (iArr == null || iArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(iArr.length);
            parcel.writeIntArray(this.shape);
        }
        parcel.writeByte(this.dataSource);
        byte b = this.dataSource;
        if (b != 0) {
            if (b == 1) {
                parcel.writeFileDescriptor(this.filedesc);
                return;
            } else {
                if (b == 2) {
                    parcel.writeParcelable(this.dataShared, 0);
                    return;
                }
                return;
            }
        }
        float[] fArr = this.dataOriginal;
        if (fArr == null || fArr.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(fArr.length);
            parcel.writeFloatArray(this.dataOriginal);
        }
    }

    public DataBuffer(Parcel parcel) {
        this.dataType = parcel.readByte();
        this.dataFormat = parcel.readByte();
        int readInt = parcel.readInt();
        if (readInt != 0) {
            int[] iArr = new int[readInt];
            this.shape = iArr;
            parcel.readIntArray(iArr);
        }
        byte readByte = parcel.readByte();
        this.dataSource = readByte;
        if (readByte == 0) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                float[] fArr = new float[readInt2];
                this.dataOriginal = fArr;
                parcel.readFloatArray(fArr);
                return;
            }
            return;
        }
        if (readByte == 1) {
            this.filedesc = parcel.readFileDescriptor().getFileDescriptor();
        } else if (readByte == 2) {
            this.dataShared =
                    (SharedMemory) parcel.readParcelable(SharedMemory.class.getClassLoader());
        }
    }
}
