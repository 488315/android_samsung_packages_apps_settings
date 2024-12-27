package com.google.android.material.timepicker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TimeModel implements Parcelable {
    public static final Parcelable.Creator<TimeModel> CREATOR = new AnonymousClass1();
    public final int format;
    public final int hour;
    public final int minute;
    public final int selection;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.timepicker.TimeModel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new TimeModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TimeModel[i];
        }
    }

    public TimeModel(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        this.hour = readInt;
        this.minute = readInt2;
        this.selection = readInt3;
        this.format = readInt4;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimeModel)) {
            return false;
        }
        TimeModel timeModel = (TimeModel) obj;
        return this.hour == timeModel.hour
                && this.minute == timeModel.minute
                && this.format == timeModel.format
                && this.selection == timeModel.selection;
    }

    public final int hashCode() {
        return Arrays.hashCode(
                new Object[] {
                    Integer.valueOf(this.format),
                    Integer.valueOf(this.hour),
                    Integer.valueOf(this.minute),
                    Integer.valueOf(this.selection)
                });
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.selection);
        parcel.writeInt(this.format);
    }
}
