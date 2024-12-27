package com.samsung.android.settings.external;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DynamicSearchData implements Parcelable {
    public static final Parcelable.Creator<DynamicSearchData> CREATOR = new AnonymousClass1();
    public String mKey;
    public String mKeywords;
    public String mSearchTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.external.DynamicSearchData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DynamicSearchData dynamicSearchData = new DynamicSearchData();
            dynamicSearchData.mKey = parcel.readString();
            dynamicSearchData.mKeywords = parcel.readString();
            dynamicSearchData.mSearchTitle = parcel.readString();
            return dynamicSearchData;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DynamicSearchData[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        parcel.writeString(this.mKeywords);
        parcel.writeString(this.mSearchTitle);
    }
}
