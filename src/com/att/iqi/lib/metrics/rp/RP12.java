package com.att.iqi.lib.metrics.rp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RP12 extends Metric {
    public int m_dwByteCount;
    public int m_dwDuration;
    public int m_dwPktCount;
    public int m_dwPktLoss;
    public int m_dwSsrc;
    public byte[] m_strIpSrcAddr;
    public byte m_ucIpVersion;
    public byte m_ucMediaType;
    public short m_wCumAvgPktSize;
    public short m_wDstPort;
    public short m_wMaxDelta;
    public short m_wMaxJitter;
    public short m_wMeanJitter;
    public static final Metric.ID ID = new Metric.ID("RP12");
    public static final Parcelable.Creator<RP12> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.rp.RP12$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<RP12> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP12 createFromParcel(Parcel parcel) {
            return new RP12(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP12[] newArray(int i) {
            return new RP12[i];
        }
    }

    public RP12() {
        reset();
    }

    public int getByteCount() {
        return this.m_dwByteCount;
    }

    public short getCumAvgPktSize() {
        return this.m_wCumAvgPktSize;
    }

    public short getDstPort() {
        return this.m_wDstPort;
    }

    public int getDuration() {
        return this.m_dwDuration;
    }

    public byte[] getIpSrcAddr() {
        return this.m_strIpSrcAddr;
    }

    public byte getIpVersion() {
        return this.m_ucIpVersion;
    }

    public short getMaxDelta() {
        return this.m_wMaxDelta;
    }

    public short getMaxJitter() {
        return this.m_wMaxJitter;
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

    public int getPktLoss() {
        return this.m_dwPktLoss;
    }

    public int getSsrc() {
        return this.m_dwSsrc;
    }

    public void reset() {
        this.m_dwSsrc = 0;
        this.m_dwDuration = 0;
        this.m_dwPktCount = 0;
        this.m_dwPktLoss = 0;
        this.m_dwByteCount = 0;
        this.m_wDstPort = (short) 0;
        this.m_wMeanJitter = (short) 0;
        this.m_wMaxJitter = (short) 0;
        this.m_wMaxDelta = (short) 0;
        this.m_wCumAvgPktSize = (short) 0;
        this.m_ucMediaType = (byte) 0;
        this.m_ucIpVersion = (byte) 0;
        this.m_strIpSrcAddr = null;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.m_dwSsrc);
        byteBuffer.putInt(this.m_dwDuration);
        byteBuffer.putInt(this.m_dwPktCount);
        byteBuffer.putInt(this.m_dwPktLoss);
        byteBuffer.putInt(this.m_dwByteCount);
        byteBuffer.putShort(this.m_wDstPort);
        byteBuffer.putShort(this.m_wMeanJitter);
        byteBuffer.putShort(this.m_wMaxJitter);
        byteBuffer.putShort(this.m_wMaxDelta);
        byteBuffer.putShort(this.m_wCumAvgPktSize);
        byteBuffer.put(this.m_ucMediaType);
        byteBuffer.put(this.m_ucIpVersion);
        byte[] bArr = this.m_strIpSrcAddr;
        if (bArr != null) {
            byteBuffer.put(bArr);
        }
        return byteBuffer.position();
    }

    public RP12 setByteCount(int i) {
        this.m_dwByteCount = i;
        return this;
    }

    public RP12 setCumAvgPktSize(short s) {
        this.m_wCumAvgPktSize = s;
        return this;
    }

    public RP12 setDstPort(short s) {
        this.m_wDstPort = s;
        return this;
    }

    public RP12 setDuration(int i) {
        this.m_dwDuration = i;
        return this;
    }

    public RP12 setIpSrcAddr(byte[] bArr) {
        this.m_strIpSrcAddr = bArr;
        return this;
    }

    public RP12 setIpVersion(byte b) {
        this.m_ucIpVersion = b;
        return this;
    }

    public RP12 setMaxDelta(short s) {
        this.m_wMaxDelta = s;
        return this;
    }

    public RP12 setMaxJitter(short s) {
        this.m_wMaxJitter = s;
        return this;
    }

    public RP12 setMeanJitter(short s) {
        this.m_wMeanJitter = s;
        return this;
    }

    public RP12 setMediaType(byte b) {
        this.m_ucMediaType = b;
        return this;
    }

    public RP12 setPktCount(int i) {
        this.m_dwPktCount = i;
        return this;
    }

    public RP12 setPktLoss(int i) {
        this.m_dwPktLoss = i;
        return this;
    }

    public RP12 setSsrc(int i) {
        this.m_dwSsrc = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_dwSsrc);
        parcel.writeInt(this.m_dwDuration);
        parcel.writeInt(this.m_dwPktCount);
        parcel.writeInt(this.m_dwPktLoss);
        parcel.writeInt(this.m_dwByteCount);
        parcel.writeInt(this.m_wDstPort);
        parcel.writeInt(this.m_wMeanJitter);
        parcel.writeInt(this.m_wMaxJitter);
        parcel.writeInt(this.m_wMaxDelta);
        parcel.writeInt(this.m_wCumAvgPktSize);
        parcel.writeByte(this.m_ucMediaType);
        parcel.writeByte(this.m_ucIpVersion);
        byte[] bArr = this.m_strIpSrcAddr;
        int length = bArr != null ? bArr.length : 0;
        if (length <= 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(length);
            parcel.writeByteArray(this.m_strIpSrcAddr);
        }
    }

    @Keep
    public RP12(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_dwSsrc = parcel.readInt();
            this.m_dwDuration = parcel.readInt();
            this.m_dwPktCount = parcel.readInt();
            this.m_dwPktLoss = parcel.readInt();
            this.m_dwByteCount = parcel.readInt();
            this.m_wDstPort = (short) parcel.readInt();
            this.m_wMeanJitter = (short) parcel.readInt();
            this.m_wMaxJitter = (short) parcel.readInt();
            this.m_wMaxDelta = (short) parcel.readInt();
            this.m_wCumAvgPktSize = (short) parcel.readInt();
            this.m_ucMediaType = parcel.readByte();
            this.m_ucIpVersion = parcel.readByte();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                this.m_strIpSrcAddr = bArr;
                parcel.readByteArray(bArr);
            }
        }
    }
}
