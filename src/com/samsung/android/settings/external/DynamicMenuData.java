package com.samsung.android.settings.external;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DynamicMenuData implements Parcelable {
    public static final Parcelable.Creator<DynamicMenuData> CREATOR = new AnonymousClass1();
    public boolean mIsChecked;
    public boolean mIsEnabled;
    public boolean mIsSummaryPrimaryDark;
    public boolean mIsVisible;
    public String mKey;
    public String mSummary;
    public String mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.external.DynamicMenuData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DynamicMenuData dynamicMenuData = new DynamicMenuData();
            dynamicMenuData.mKey = parcel.readString();
            dynamicMenuData.mTitle = parcel.readString();
            dynamicMenuData.mSummary = parcel.readString();
            dynamicMenuData.mIsChecked = parcel.readInt() == 1;
            dynamicMenuData.mIsEnabled = parcel.readInt() == 1;
            dynamicMenuData.mIsVisible = parcel.readInt() == 1;
            dynamicMenuData.mIsSummaryPrimaryDark = parcel.readInt() == 1;
            return dynamicMenuData;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DynamicMenuData[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InvalidMenuDataException extends RuntimeException {
        public InvalidMenuDataException(String str) {
            super(str);
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mSummary);
        parcel.writeInt(this.mIsChecked ? 1 : 0);
        parcel.writeInt(this.mIsEnabled ? 1 : 0);
        parcel.writeInt(this.mIsVisible ? 1 : 0);
        parcel.writeInt(this.mIsSummaryPrimaryDark ? 1 : 0);
    }
}
