package com.samsung.android.sdk.mobileservice.social.social;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GroupDetailRequest {
    public final String mAppId = "4r5b3r1h6a";
    public final List mFeatureIdList;
    public final String mGroupId;
    public final int mMaxGroupMemberCount;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final String mGroupId;
        public int mMaxGroupMemberCount = -1;
        public List mFeatureIdList = null;

        public Builder(String str) {
            this.mGroupId = str;
        }
    }

    public GroupDetailRequest(Builder builder) {
        this.mGroupId = builder.mGroupId;
        this.mFeatureIdList = builder.mFeatureIdList;
        this.mMaxGroupMemberCount = builder.mMaxGroupMemberCount;
    }

    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("app_id", this.mAppId);
        bundle.putString("group_id", this.mGroupId);
        int i = this.mMaxGroupMemberCount;
        if (i != -1) {
            bundle.putInt("max_group_member_count", i);
        }
        if (!TextUtils.isEmpty(null)) {
            bundle.putString("space_name", null);
        }
        List list = this.mFeatureIdList;
        if (list != null && !list.isEmpty()) {
            bundle.putString("feature_id", TextUtils.join(";", this.mFeatureIdList));
        }
        return bundle;
    }
}
