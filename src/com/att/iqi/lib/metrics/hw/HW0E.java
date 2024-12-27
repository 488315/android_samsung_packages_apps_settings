package com.att.iqi.lib.metrics.hw;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HW0E extends Metric {
    public byte ucBatteryEvent;
    public static final Metric.ID ID = new Metric.ID("HW0E");
    public static final Parcelable.Creator<HW0E> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.hw.HW0E$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<HW0E> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HW0E createFromParcel(Parcel parcel) {
            return new HW0E(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HW0E[] newArray(int i) {
            return new HW0E[i];
        }
    }

    public HW0E() {}

    public byte getEvent() {
        return this.ucBatteryEvent;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.ucBatteryEvent);
        return byteBuffer.position();
    }

    public HW0E setEvent(byte b) {
        this.ucBatteryEvent = b;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.ucBatteryEvent);
    }

    @Keep
    public HW0E(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.ucBatteryEvent = parcel.readByte();
        }
    }
}
