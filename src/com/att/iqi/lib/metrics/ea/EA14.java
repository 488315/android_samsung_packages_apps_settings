package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class EA14 extends Metric {
    public final int lSerialNumber;
    public final int lServiceCategory;
    public static final Metric.ID ID = new Metric.ID("EA14");
    public static final Parcelable.Creator<EA14> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.ea.EA14$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EA14> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA14 createFromParcel(Parcel parcel) {
            return new EA14(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA14[] newArray(int i) {
            return new EA14[i];
        }
    }

    @Keep
    public EA14(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.lServiceCategory);
        byteBuffer.putInt(this.lSerialNumber);
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.lServiceCategory);
        parcel.writeInt(this.lSerialNumber);
    }

    public EA14(int i, int i2) {
        this.lServiceCategory = i;
        this.lSerialNumber = i2;
    }
}
