package com.att.iqi.lib.metrics.mm;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MM06 extends Metric {
    public static final byte IQ_SIP_ORIGINATED = 0;
    public static final byte IQ_SIP_TERMINATED = 1;
    public short m_shResult;
    public String m_szCallId;
    public static final Metric.ID ID = new Metric.ID("MM06");
    public static final Parcelable.Creator<MM06> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.mm.MM06$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<MM06> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM06 createFromParcel(Parcel parcel) {
            return new MM06(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM06[] newArray(int i) {
            return new MM06[i];
        }
    }

    public MM06() {
        reset();
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    public short getResponseCode() {
        return (short) (this.m_shResult & Short.MAX_VALUE);
    }

    public byte getTerminationDirection() {
        return (byte) (this.m_shResult >> 15);
    }

    public void reset() {
        this.m_shResult = (short) 0;
        this.m_szCallId = ApnSettings.MVNO_NONE;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putShort(this.m_shResult);
        stringOut(byteBuffer, this.m_szCallId);
        return byteBuffer.position();
    }

    public MM06 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public MM06 setResponseCode(short s) {
        this.m_shResult = (short) ((s & Short.MAX_VALUE) | (this.m_shResult & Short.MIN_VALUE));
        return this;
    }

    public MM06 setTerminationDirection(byte b) {
        this.m_shResult = (short) ((b << 15) | (this.m_shResult & Short.MAX_VALUE));
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_shResult);
        parcel.writeString(this.m_szCallId);
    }

    @Keep
    public MM06(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_shResult = (short) parcel.readInt();
            this.m_szCallId = parcel.readString();
        }
    }
}
