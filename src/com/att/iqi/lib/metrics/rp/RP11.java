package com.att.iqi.lib.metrics.rp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RP11 extends Metric {
    public int m_dwByteCount;
    public int m_dwDuration;
    public int m_dwPktCount;
    public int m_dwSsrc;
    public byte[] m_strIpDstAddr;
    public byte m_ucIpVersion;
    public byte m_ucMediaType;
    public short m_wDstPort;
    public short m_wMeanJitter;
    public static final Metric.ID ID = new Metric.ID("RP11");
    public static final Parcelable.Creator<RP11> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.rp.RP11$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<RP11> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP11 createFromParcel(Parcel parcel) {
            return new RP11(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP11[] newArray(int i) {
            return new RP11[i];
        }
    }

    public RP11() {
        reset();
    }

    public int getByteCount() {
        return this.m_dwByteCount;
    }

    public short getDstPort() {
        return this.m_wDstPort;
    }

    public int getDuration() {
        return this.m_dwDuration;
    }

    public byte[] getIpDstAddr() {
        return this.m_strIpDstAddr;
    }

    public byte getIpVersion() {
        return this.m_ucIpVersion;
    }

    public short getMeanJitter() {
        return this.m_wMeanJitter;
    }

    public byte getMediaType() {
        return this.m_ucMediaType;
    }

    public int getPktCount() {
        return this.m_dwPktCount;
    }

    public int getSsrc() {
        return this.m_dwSsrc;
    }

    public void reset() {
        this.m_dwSsrc = 0;
        this.m_dwDuration = 0;
        this.m_dwPktCount = 0;
        this.m_dwByteCount = 0;
        this.m_wDstPort = (short) 0;
        this.m_wMeanJitter = (short) 0;
        this.m_ucMediaType = (byte) 0;
        this.m_ucIpVersion = (byte) 0;
        this.m_strIpDstAddr = null;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.m_dwSsrc);
        byteBuffer.putInt(this.m_dwDuration);
        byteBuffer.putInt(this.m_dwPktCount);
        byteBuffer.putInt(this.m_dwByteCount);
        byteBuffer.putShort(this.m_wDstPort);
        byteBuffer.putShort(this.m_wMeanJitter);
        byteBuffer.put(this.m_ucMediaType);
        byteBuffer.put(this.m_ucIpVersion);
        byte[] bArr = this.m_strIpDstAddr;
        if (bArr != null) {
            byteBuffer.put(bArr);
        }
        return byteBuffer.position();
    }

    public RP11 setByteCount(int i) {
        this.m_dwByteCount = i;
        return this;
    }

    public RP11 setDstPort(short s) {
        this.m_wDstPort = s;
        return this;
    }

    public RP11 setDuration(int i) {
        this.m_dwDuration = i;
        return this;
    }

    public RP11 setIpDstAddr(byte[] bArr) {
        this.m_strIpDstAddr = bArr;
        return this;
    }

    public RP11 setIpVersion(byte b) {
        this.m_ucIpVersion = b;
        return this;
    }

    public RP11 setMeanJitter(short s) {
        this.m_wMeanJitter = s;
        return this;
    }

    public RP11 setMediaType(byte b) {
        this.m_ucMediaType = b;
        return this;
    }

    public RP11 setPktCount(int i) {
        this.m_dwPktCount = i;
        return this;
    }

    public RP11 setSsrc(int i) {
        this.m_dwSsrc = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_dwSsrc);
        parcel.writeInt(this.m_dwDuration);
        parcel.writeInt(this.m_dwPktCount);
        parcel.writeInt(this.m_dwByteCount);
        parcel.writeInt(this.m_wDstPort);
        parcel.writeInt(this.m_wMeanJitter);
        parcel.writeByte(this.m_ucMediaType);
        parcel.writeByte(this.m_ucIpVersion);
        byte[] bArr = this.m_strIpDstAddr;
        int length = bArr != null ? bArr.length : 0;
        if (length <= 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(length);
            parcel.writeByteArray(this.m_strIpDstAddr);
        }
    }

    @Keep
    public RP11(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_dwSsrc = parcel.readInt();
            this.m_dwDuration = parcel.readInt();
            this.m_dwPktCount = parcel.readInt();
            this.m_dwByteCount = parcel.readInt();
            this.m_wDstPort = (short) parcel.readInt();
            this.m_wMeanJitter = (short) parcel.readInt();
            this.m_ucMediaType = parcel.readByte();
            this.m_ucIpVersion = parcel.readByte();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                this.m_strIpDstAddr = bArr;
                parcel.readByteArray(bArr);
            }
        }
    }
}
