package com.samsung.android.settings.actions;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LaunchPayload implements Parcelable {
    public static final Parcelable.Creator<LaunchPayload> CREATOR = new AnonymousClass1();
    public Intent mIntent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.actions.LaunchPayload$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            LaunchPayload launchPayload = new LaunchPayload();
            launchPayload.mIntent =
                    (Intent) parcel.readParcelable(LaunchPayload.class.getClassLoader());
            return launchPayload;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new LaunchPayload[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mIntent, i);
    }
}
