package com.samsung.android.scloud.lib.platform.api;

import android.os.Bundle;

import com.samsung.android.scloud.lib.platform.data.ScpmDataSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Registration extends AbstractApi {
    public final ScpmDataSet register(String str) {
        String str2 = this.TAG;
        LOG.i(str2, "register : com.samsung.android.app.settings.bixby, appId : sv080do9zh");
        try {
            Bundle bundle = new Bundle();
            bundle.putString("packageName", "com.samsung.android.app.settings.bixby");
            bundle.putString("appId", "sv080do9zh");
            bundle.putString("appSignature", str);
            return DataSetFactory.newScpmDataSet(
                    call("register", this.context.getPackageName(), bundle));
        } catch (Exception e) {
            LOG.e(str2, "cannot register package : " + e.getMessage());
            return new ScpmDataSet(
                    2,
                    90000000,
                    "There is an exception, please check  { " + e.getMessage() + "}",
                    null);
        }
    }
}
