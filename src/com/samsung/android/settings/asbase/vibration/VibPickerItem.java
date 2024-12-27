package com.samsung.android.settings.asbase.vibration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerItem {
    public final String mCategory;
    public final String mData;
    public final int mId;
    public final int mIsCustom;
    public final int mItemType;
    public final String mName;
    public final int mPattern;
    public int mPosition;
    public final String mTitle;
    public final int mVibType;

    public VibPickerItem(
            int i,
            int i2,
            String str,
            int i3,
            int i4,
            String str2,
            String str3,
            int i5,
            String str4,
            int i6) {
        this.mId = i;
        this.mPosition = i2;
        this.mVibType = i3;
        this.mItemType = i4;
        this.mTitle = str;
        this.mName = str2;
        this.mCategory = str3;
        this.mPattern = i5;
        this.mData = str4;
        this.mIsCustom = i6;
    }

    public final String toString() {
        return "id="
                + this.mId
                + " / position="
                + this.mPosition
                + " / vibType="
                + this.mVibType
                + " / itemType="
                + this.mItemType
                + " / title="
                + this.mTitle
                + " / name="
                + this.mName
                + " / category="
                + this.mCategory
                + " / patternIndex="
                + this.mPattern
                + " / custom="
                + this.mIsCustom;
    }
}
