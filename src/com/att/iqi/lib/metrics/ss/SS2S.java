package com.att.iqi.lib.metrics.ss;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

import com.att.iqi.lib.Metric;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SS2S extends Metric {
    public static final byte SERVICE_SHOULD_NOT_RUN = 1;
    public static final byte SERVICE_SHOULD_RUN = 0;
    public static final int SHOULD_SERVICE_RUN = 0;
    public byte ucSettings;
    public static final Metric.ID ID = new Metric.ID("SS2S");
    public static final Parcelable.Creator<SS2S> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.ss.SS2S$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<SS2S> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SS2S createFromParcel(Parcel parcel) {
            return new SS2S(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SS2S[] newArray(int i) {
            return new SS2S[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface IQISettings {}

    public SS2S() {
        this.ucSettings = (byte) 0;
    }

    public byte getSetting(int i) {
        if (i == 0) {
            return (byte) (this.ucSettings & 1);
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Invalid setting ID "));
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.ucSettings);
        return byteBuffer.position();
    }

    public SS2S setSetting(int i, byte b) {
        if (i == 0) {
            if (b != 0 && b != 1) {
                throw new IllegalArgumentException(
                        HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                                b, i, "Illegal value ", " for setting ID "));
            }
            this.ucSettings = (byte) (((byte) (this.ucSettings & (-2))) | b);
        }
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.ucSettings);
    }

    @Keep
    public SS2S(Parcel parcel) {
        super(parcel);
        this.ucSettings = (byte) 0;
        if (parcel.readInt() >= 2) {
            this.ucSettings = parcel.readByte();
        }
    }
}
