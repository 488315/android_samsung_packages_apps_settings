package com.samsung.android.settings.goodsettings.policy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PolicyData {

    @SerializedName("policy")
    public ArrayList<PolicyInfo> mPolicies;

    @SerializedName("policy_locale")
    public String mPolicyLocale;

    @SerializedName("policy_version")
    public long mPolicyVersion = 5;

    public PolicyData(String str, ArrayList arrayList) {
        this.mPolicyLocale = str;
        this.mPolicies = arrayList;
    }
}
