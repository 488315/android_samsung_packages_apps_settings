package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DateValidatorPointForward implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<DateValidatorPointForward> CREATOR =
            new AnonymousClass1();
    public final long point;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.datepicker.DateValidatorPointForward$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new DateValidatorPointForward(parcel.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DateValidatorPointForward[i];
        }
    }

    public DateValidatorPointForward(long j) {
        this.point = j;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DateValidatorPointForward)
                && this.point == ((DateValidatorPointForward) obj).point;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {Long.valueOf(this.point)});
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.point);
    }
}
