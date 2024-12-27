package com.samsung.android.settings.eternal.policy.provider;

import android.content.Context;
import android.util.Log;

import com.samsung.android.settings.eternal.log.EternalFileLog;

import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LibraryPolicy extends PolicyProvider {
    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyId() {
        return "LibraryPolicy";
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyVersion() {
        return this.mVersion;
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getRawPolicy() {
        Context context = this.mContext;
        try {
            InputStream open = context.getAssets().open("eternalPolicy.json");
            try {
                byte[] bArr = new byte[open.available()];
                if (open.read(bArr) <= 0) {
                    Log.e(
                            "Eternal/FileUtils",
                            "jsonToString() : Fail json convert to String. buffer is empty. -"
                                + " eternalPolicy.json");
                }
                String str = new String(bArr, "UTF-8");
                open.close();
                return str;
            } finally {
            }
        } catch (Exception e) {
            EternalFileLog.w(
                    "Eternal/FileUtils",
                    "getStringFromAsset() " + context.getPackageName() + " / " + e.toString());
            return null;
        }
    }
}
