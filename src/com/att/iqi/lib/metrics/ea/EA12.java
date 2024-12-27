package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class EA12 extends Metric {
    public final int lCid;
    public final int lCmasCategory;
    public final int lCmasCertainty;
    public final int lCmasMessageClass;
    public final int lCmasResponseType;
    public final int lCmasSeverity;
    public final int lCmasUrgency;
    public final int lEtwsWarningType;
    public final int lGeographicalScope;
    public final int lLac;
    public final int lSerialNumber;
    public final int lServiceCategory;
    public final long llReceivedTimeMillis;
    public final String szGeometries;
    public final String szLanguage;
    public final String szPlmn;
    public final byte ucFlags;
    public static final Metric.ID ID = new Metric.ID("EA12");
    public static final Parcelable.Creator<EA12> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.ea.EA12$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EA12> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA12 createFromParcel(Parcel parcel) {
            return new EA12(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA12[] newArray(int i) {
            return new EA12[i];
        }
    }

    @Keep
    public EA12(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lGeographicalScope = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lLac = parcel.readInt();
        this.lCid = parcel.readInt();
        byte readByte = parcel.readByte();
        this.ucFlags = readByte;
        byte b = (byte) (readByte & (-129));
        if (b == 1) {
            this.lEtwsWarningType = parcel.readInt();
        } else if (b == 2) {
            this.lCmasMessageClass = parcel.readInt();
            this.lCmasCategory = parcel.readInt();
            this.lCmasResponseType = parcel.readInt();
            this.lCmasSeverity = parcel.readInt();
            this.lCmasUrgency = parcel.readInt();
            this.lCmasCertainty = parcel.readInt();
        }
        this.szPlmn = parcel.readString();
        this.szLanguage = parcel.readString();
        if ((readByte & Byte.MIN_VALUE) == 0) {
            this.szGeometries = ApnSettings.MVNO_NONE;
        } else {
            this.llReceivedTimeMillis = parcel.readLong();
            this.szGeometries = parcel.readString();
        }
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.lServiceCategory);
        byteBuffer.putInt(this.lSerialNumber);
        byteBuffer.putInt(this.lGeographicalScope);
        byteBuffer.putInt(this.lLac);
        byteBuffer.putInt(this.lCid);
        byteBuffer.put(this.ucFlags);
        byte b = (byte) (this.ucFlags & (-129));
        if (b == 1) {
            byteBuffer.putInt(this.lEtwsWarningType);
        } else if (b == 2) {
            byteBuffer.putInt(this.lCmasMessageClass);
            byteBuffer.putInt(this.lCmasCategory);
            byteBuffer.putInt(this.lCmasResponseType);
            byteBuffer.putInt(this.lCmasSeverity);
            byteBuffer.putInt(this.lCmasUrgency);
            byteBuffer.putInt(this.lCmasCertainty);
        }
        stringOut(byteBuffer, this.szPlmn);
        stringOut(byteBuffer, this.szLanguage);
        if ((this.ucFlags & Byte.MIN_VALUE) != 0) {
            byteBuffer.putLong(this.llReceivedTimeMillis);
            stringOut(byteBuffer, this.szGeometries);
        }
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.lGeographicalScope);
        parcel.writeInt(this.lSerialNumber);
        parcel.writeInt(this.lServiceCategory);
        parcel.writeInt(this.lLac);
        parcel.writeInt(this.lCid);
        parcel.writeByte(this.ucFlags);
        byte b = (byte) (this.ucFlags & (-129));
        if (b == 1) {
            parcel.writeInt(this.lEtwsWarningType);
        } else if (b == 2) {
            parcel.writeInt(this.lCmasMessageClass);
            parcel.writeInt(this.lCmasCategory);
            parcel.writeInt(this.lCmasResponseType);
            parcel.writeInt(this.lCmasSeverity);
            parcel.writeInt(this.lCmasUrgency);
            parcel.writeInt(this.lCmasCertainty);
        }
        parcel.writeString(this.szPlmn);
        parcel.writeString(this.szLanguage);
        if ((this.ucFlags & Byte.MIN_VALUE) != 0) {
            parcel.writeLong(this.llReceivedTimeMillis);
            parcel.writeString(this.szGeometries);
        }
    }

    public EA12(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        byte b = 0;
        parcelable.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        obtain.readInt();
        this.lGeographicalScope = obtain.readInt();
        this.lSerialNumber = obtain.readInt();
        this.szPlmn = obtain.readString();
        this.lLac = obtain.readInt();
        this.lCid = obtain.readInt();
        this.lServiceCategory = obtain.readInt();
        this.szLanguage = obtain.readString();
        obtain.readInt();
        obtain.readString();
        obtain.readInt();
        int readInt = obtain.readInt();
        if (readInt == 67) {
            this.lCmasMessageClass = obtain.readInt();
            this.lCmasCategory = obtain.readInt();
            this.lCmasResponseType = obtain.readInt();
            this.lCmasSeverity = obtain.readInt();
            this.lCmasUrgency = obtain.readInt();
            this.lCmasCertainty = obtain.readInt();
            b = 2;
        } else if (readInt == 69) {
            this.lEtwsWarningType = obtain.readInt();
            obtain.readInt();
            obtain.readInt();
            obtain.readInt();
            obtain.createByteArray();
            b = 1;
        }
        long readLong = obtain.readLong();
        String readString = obtain.readString();
        if (readLong != 0 && readString != null) {
            b = (byte) (b | Byte.MIN_VALUE);
            this.llReceivedTimeMillis = readLong;
            this.szGeometries = readString;
        } else {
            this.szGeometries = ApnSettings.MVNO_NONE;
        }
        this.ucFlags = b;
        obtain.recycle();
    }
}
