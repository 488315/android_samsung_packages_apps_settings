package com.samsung.android.settings.eternal.policy.provider;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PolicyItem {

    @SerializedName(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
    private final String mKey;

    @SerializedName("minOneUI")
    private final String mMinOneUI = "min";

    @SerializedName("maxOneUI")
    private final String mMaxOneUI = "max";

    @SerializedName("deviceType")
    private final String mDeviceType = "common";

    @SerializedName("minProvider")
    private final String mProviderMin = "min";

    @SerializedName("maxProvider")
    private final String mProviderMax = "max";

    public PolicyItem(String str) {
        this.mKey = str;
    }

    public final String getDeviceType() {
        return this.mDeviceType;
    }

    public final String getKey() {
        return this.mKey;
    }

    public final String getMaxOneUI() {
        return this.mMaxOneUI;
    }

    public final String getMinOneUI() {
        return this.mMinOneUI;
    }

    public final String getProviderMax() {
        String str = this.mProviderMax;
        return str == null ? "max" : str;
    }

    public final String getProviderMin() {
        String str = this.mProviderMin;
        return str == null ? "min" : str;
    }
}
