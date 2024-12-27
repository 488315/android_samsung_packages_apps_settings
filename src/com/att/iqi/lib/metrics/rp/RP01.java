package com.att.iqi.lib.metrics.rp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RP01 extends Metric {
    public int m_dwSourceId;
    public int m_dwTimestamp;
    public byte m_ucFlags;
    public byte m_ucPayloadType;
    public short m_wByteCount;
    public short m_wDstPort;
    public short m_wSequenceNum;
    public static final Metric.ID ID = new Metric.ID("RP01");
    public static final Parcelable.Creator<RP01> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.rp.RP01$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<RP01> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP01 createFromParcel(Parcel parcel) {
            return new RP01(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RP01[] newArray(int i) {
            return new RP01[i];
        }
    }

    public RP01() {
        reset();
    }

    public short getByteCount() {
        return this.m_wByteCount;
    }

    public short getDstPort() {
        return this.m_wDstPort;
    }

    public byte getFlags() {
        return this.m_ucFlags;
    }

    public byte getPayloadType() {
        return this.m_ucPayloadType;
    }

    public short getSequenceNum() {
        return this.m_wSequenceNum;
    }

    public int getSourceId() {
        return this.m_dwSourceId;
    }

    public int getTimestamp() {
        return this.m_dwTimestamp;
    }

    public void reset() {
        this.m_wByteCount = (short) 0;
        this.m_wDstPort = (short) 0;
        this.m_ucFlags = (byte) 0;
        this.m_ucPayloadType = (byte) 0;
        this.m_wSequenceNum = (short) 0;
        this.m_dwTimestamp = 0;
        this.m_dwSourceId = 0;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putShort(this.m_wByteCount);
        byteBuffer.putShort(this.m_wDstPort);
        byteBuffer.put(this.m_ucFlags);
        byteBuffer.put(this.m_ucPayloadType);
        byteBuffer.putShort(this.m_wSequenceNum);
        byteBuffer.putInt(this.m_dwTimestamp);
        byteBuffer.putInt(this.m_dwSourceId);
        return byteBuffer.position();
    }

    public RP01 setByteCount(short s) {
        this.m_wByteCount = s;
        return this;
    }

    public RP01 setDstPort(short s) {
        this.m_wDstPort = s;
        return this;
    }

    public RP01 setFlags(byte b) {
        this.m_ucFlags = b;
        return this;
    }

    public RP01 setPayloadType(byte b) {
        this.m_ucPayloadType = b;
        return this;
    }

    public RP01 setSequenceNum(short s) {
        this.m_wSequenceNum = s;
        return this;
    }

    public RP01 setSourceId(int i) {
        this.m_dwSourceId = i;
        return this;
    }

    public RP01 setTimestamp(int i) {
        this.m_dwTimestamp = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_wByteCount);
        parcel.writeInt(this.m_wDstPort);
        parcel.writeByte(this.m_ucFlags);
        parcel.writeByte(this.m_ucPayloadType);
        parcel.writeInt(this.m_wSequenceNum);
        parcel.writeInt(this.m_dwTimestamp);
        parcel.writeInt(this.m_dwSourceId);
    }

    @Keep
    public RP01(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_wByteCount = (short) parcel.readInt();
            this.m_wDstPort = (short) parcel.readInt();
            this.m_ucFlags = parcel.readByte();
            this.m_ucPayloadType = parcel.readByte();
            this.m_wSequenceNum = (short) parcel.readInt();
            this.m_dwTimestamp = parcel.readInt();
            this.m_dwSourceId = parcel.readInt();
        }
    }
}