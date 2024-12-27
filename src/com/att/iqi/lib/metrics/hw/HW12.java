package com.att.iqi.lib.metrics.hw;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HW12 extends Metric {
    public byte ucCause;
    public byte ucProcessor;
    public static final Metric.ID ID = new Metric.ID("HW12");
    public static final Parcelable.Creator<HW12> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.hw.HW12$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<HW12> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HW12 createFromParcel(Parcel parcel) {
            return new HW12(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HW12[] newArray(int i) {
            return new HW12[i];
        }
    }

    public HW12() {}

    public short getCause() {
        return this.ucCause;
    }

    public byte getProcessor() {
        return this.ucProcessor;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.ucCause);
        byteBuffer.put(this.ucProcessor);
        return byteBuffer.position();
    }

    public HW12 setCause(byte b) {
        this.ucCause = b;
        return this;
    }

    public HW12 setProcessor(byte b) {
        this.ucProcessor = b;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.ucCause);
        parcel.writeByte(this.ucProcessor);
    }

    @Keep
    public HW12(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.ucCause = parcel.readByte();
            this.ucProcessor = parcel.readByte();
        }
    }
}
