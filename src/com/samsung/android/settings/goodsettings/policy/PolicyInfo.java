package com.samsung.android.settings.goodsettings.policy;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PolicyInfo {

    @SerializedName(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
    public String mKey;

    @SerializedName("type")
    public String mType;

    @SerializedName("value")
    public String mValue;

    public PolicyInfo(String str, String str2, String str3) {
        this.mKey = str;
        this.mType = str2;
        this.mValue = str3;
    }

    public final String toString() {
        return "key = " + this.mKey + ", type = " + this.mType + ", value = " + this.mValue;
    }
}
