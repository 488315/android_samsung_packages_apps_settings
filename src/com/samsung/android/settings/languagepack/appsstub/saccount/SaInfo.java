package com.samsung.android.settings.languagepack.appsstub.saccount;

import android.text.TextUtils;

import com.android.settings.Utils$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SaInfo {
    public String appId;
    public String countryCode;
    public String token;
    public String url;

    public final boolean hasInfo() {
        return (TextUtils.isEmpty(this.appId)
                        || TextUtils.isEmpty(this.token)
                        || TextUtils.isEmpty(this.url))
                ? false
                : true;
    }

    public final void printLog() {
        StringBuilder sb = new StringBuilder("SaInfo : appId = ");
        sb.append(this.appId);
        sb.append(", token = ");
        sb.append(this.token);
        sb.append(", url = ");
        sb.append(this.url);
        sb.append(", cc = ");
        Utils$$ExternalSyntheticOutline0.m(sb, this.countryCode, "SaInfo");
    }
}
