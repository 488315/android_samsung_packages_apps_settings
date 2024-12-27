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
public class MM04 extends Metric {
    public String m_szCallId;
    public String m_szDialedString;
    public String m_szOriginatingUri;
    public String m_szTerminatingUri;
    public static final Metric.ID ID = new Metric.ID("MM04");
    public static final Parcelable.Creator<MM04> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.mm.MM04$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<MM04> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM04 createFromParcel(Parcel parcel) {
            return new MM04(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MM04[] newArray(int i) {
            return new MM04[i];
        }
    }

    public MM04() {
        reset();
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    public String getDialedString() {
        return this.m_szDialedString;
    }

    public String getOriginatingUri() {
        return this.m_szOriginatingUri;
    }

    public String getTerminatingUri() {
        return this.m_szTerminatingUri;
    }

    public void reset() {
        this.m_szDialedString = ApnSettings.MVNO_NONE;
        this.m_szCallId = ApnSettings.MVNO_NONE;
        this.m_szOriginatingUri = ApnSettings.MVNO_NONE;
        this.m_szTerminatingUri = ApnSettings.MVNO_NONE;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        stringOut(byteBuffer, this.m_szDialedString);
        stringOut(byteBuffer, this.m_szCallId);
        stringOut(byteBuffer, this.m_szOriginatingUri);
        stringOut(byteBuffer, this.m_szTerminatingUri);
        return byteBuffer.position();
    }

    public MM04 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public MM04 setDialedString(String str) {
        this.m_szDialedString = str;
        return this;
    }

    public MM04 setOriginatingUri(String str) {
        this.m_szOriginatingUri = str;
        return this;
    }

    public MM04 setTerminatingUri(String str) {
        this.m_szTerminatingUri = str;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.m_szDialedString);
        parcel.writeString(this.m_szCallId);
        parcel.writeString(this.m_szOriginatingUri);
        parcel.writeString(this.m_szTerminatingUri);
    }

    @Keep
    public MM04(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_szDialedString = parcel.readString();
            this.m_szCallId = parcel.readString();
            this.m_szOriginatingUri = parcel.readString();
            this.m_szTerminatingUri = parcel.readString();
        }
    }
}
