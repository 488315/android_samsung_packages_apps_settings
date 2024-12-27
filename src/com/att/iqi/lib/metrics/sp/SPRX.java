package com.att.iqi.lib.metrics.sp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SPRX extends Metric {
    public int m_dwCSeq;
    public int m_dwTransId;
    public String m_strMessage;
    public static final Metric.ID ID = new Metric.ID("SPRX");
    public static final Parcelable.Creator<SPRX> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.sp.SPRX$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<SPRX> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SPRX createFromParcel(Parcel parcel) {
            return new SPRX(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SPRX[] newArray(int i) {
            return new SPRX[i];
        }
    }

    public SPRX() {
        reset();
    }

    public int getCSeq() {
        return this.m_dwCSeq;
    }

    public String getMessage() {
        return this.m_strMessage;
    }

    public int getTransId() {
        return this.m_dwTransId;
    }

    public void reset() {
        this.m_dwTransId = 0;
        this.m_dwCSeq = 0;
        this.m_strMessage = ApnSettings.MVNO_NONE;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.m_dwTransId);
        byteBuffer.putInt(this.m_dwCSeq);
        String str = this.m_strMessage;
        int length = str == null ? 0 : str.length();
        byteBuffer.putInt(length);
        if (length > 0) {
            byteBuffer.put(this.m_strMessage.getBytes(StandardCharsets.US_ASCII));
        }
        return byteBuffer.position();
    }

    public SPRX setCSeq(int i) {
        this.m_dwCSeq = i;
        return this;
    }

    public SPRX setMessage(String str) {
        this.m_strMessage = str;
        return this;
    }

    public SPRX setTransId(int i) {
        this.m_dwTransId = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_dwTransId);
        parcel.writeInt(this.m_dwCSeq);
        parcel.writeString(this.m_strMessage);
    }

    @Keep
    public SPRX(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_dwTransId = parcel.readInt();
            this.m_dwCSeq = parcel.readInt();
            this.m_strMessage = parcel.readString();
        }
    }
}
