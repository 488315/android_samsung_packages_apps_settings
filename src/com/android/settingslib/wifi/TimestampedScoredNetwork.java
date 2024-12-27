package com.android.settingslib.wifi;

import android.net.ScoredNetwork;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimestampedScoredNetwork implements Parcelable {
    public static final Parcelable.Creator<TimestampedScoredNetwork> CREATOR =
            new AnonymousClass1();
    public ScoredNetwork mScore;
    public long mUpdatedTimestampMillis;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.wifi.TimestampedScoredNetwork$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TimestampedScoredNetwork timestampedScoredNetwork = new TimestampedScoredNetwork();
            timestampedScoredNetwork.mScore =
                    parcel.readParcelable(ScoredNetwork.class.getClassLoader());
            timestampedScoredNetwork.mUpdatedTimestampMillis = parcel.readLong();
            return timestampedScoredNetwork;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TimestampedScoredNetwork[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mScore, i);
        parcel.writeLong(this.mUpdatedTimestampMillis);
    }
}
