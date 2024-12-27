package com.samsung.android.settings.widget;

import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RadioItemDataInfo {
    public final List mImageList;
    public final List mItemValueList;
    public final List mSubTitleList;
    public final List mTitleColorList;
    public final List mTitleList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final List mItemValueList;
        public final List mTitleList;
        public List mTitleColorList = null;
        public List mSubTitleList = null;
        public List mImageList = null;

        public Builder(List list, List list2) {
            this.mItemValueList = list;
            this.mTitleList = list2;
        }

        public final RadioItemDataInfo build() {
            List list;
            try {
                if (this.mItemValueList == null || (list = this.mTitleList) == null) {
                    throw new Exception("Mandatory lists are null");
                }
                if (list.size() == this.mItemValueList.size()) {
                    return new RadioItemDataInfo(this);
                }
                throw new Exception("MandatoryLists sizes are different");
            } catch (Exception e) {
                SemLog.d("RadioItemDataInfo.Builder", "validation failed : " + e.getMessage());
                return null;
            }
        }
    }

    public RadioItemDataInfo(Builder builder) {
        this.mSubTitleList = builder.mSubTitleList;
        this.mItemValueList = builder.mItemValueList;
        this.mTitleList = builder.mTitleList;
        this.mImageList = builder.mImageList;
        this.mTitleColorList = builder.mTitleColorList;
    }
}
