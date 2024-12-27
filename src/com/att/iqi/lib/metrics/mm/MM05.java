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
public class MM05 extends Metric {
    public static final byte IQ_SIP_CALL_STATE_ANSWERED = 8;
    public static final byte IQ_SIP_CALL_STATE_CONNECTED = 9;
    public static final byte IQ_SIP_CALL_STATE_DISCONNECTING = 11;
    public static final byte IQ_SIP_CALL_STATE_HELD = 10;
    public static final byte IQ_SIP_CALL_STATE_IDLE = 1;
    public static final byte IQ_SIP_CALL_STATE_INVITE = 2;
    public static final byte IQ_SIP_CALL_STATE_NEGOTIATING = 5;
    public static final byte IQ_SIP_CALL_STATE_PROGRESS = 4;
    public static final byte IQ_SIP_CALL_STATE_RINGING = 7;
    public static final byte IQ_SIP_CALL_STATE_TRYING = 3;
    public static final byte IQ_SIP_CALL_STATE_UNKNOWN = 0;
    public static final byte IQ_SIP_CALL_STATE_UPDATED = 6;
    public String m_szCallId;
    public byte m_ucCallState;
    public static final Metric.ID ID = new Metric.ID("MM05");
    public static final Parcelable.Creator<MM05> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.mm.MM05$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<MM05> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM05 createFromParcel(Parcel parcel) {
            return new MM05(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM05[] newArray(int i) {
            return new MM05[i];
        }
    }

    public MM05() {
        reset();
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    public byte getRegState() {
        return this.m_ucCallState;
    }

    public void reset() {
        this.m_ucCallState = (byte) 0;
        this.m_szCallId = ApnSettings.MVNO_NONE;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.m_ucCallState);
        stringOut(byteBuffer, this.m_szCallId);
        return byteBuffer.position();
    }

    public MM05 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public MM05 setCallState(byte b) {
        this.m_ucCallState = b;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.m_ucCallState);
        parcel.writeString(this.m_szCallId);
    }

    @Keep
    public MM05(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_ucCallState = parcel.readByte();
            this.m_szCallId = parcel.readString();
        }
    }
}
