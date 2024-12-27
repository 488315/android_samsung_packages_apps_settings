package com.samsung.android.scloud.lib.platform.api;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbstractApi {
    public final String TAG;
    public final Context context;

    public AbstractApi(Context context, String str) {
        this.context = context.getApplicationContext();
        this.TAG = str;
    }

    public final Bundle call(String str, String str2, Bundle bundle) {
        String str3 = this.TAG;
        try {
            LOG.i(str3, "Method : " + str + ", Argument : " + str2);
            return this.context.getContentResolver().call(ScpmApiContract.URI, str, str2, bundle);
        } catch (Throwable unused) {
            LOG.e(str3, "Unknown exception");
            return new Bundle();
        }
    }

    public final ParcelFileDescriptor openFile(String str) {
        String str2 = this.TAG;
        LOG.i(str2, "openFile : BNR_ETERNAL_POLICY");
        String str3 = "token : " + str;
        if (LOG.ENABLED && str3 != null) {
            Log.d("[SCPMLIB_1.0.0]" + str2, str3);
        }
        try {
            return this.context
                    .getContentResolver()
                    .openFileDescriptor(
                            Uri.parse(
                                    "content://com.samsung.android.scpm.policy/"
                                            + str
                                            + "/BNR_ETERNAL_POLICY"),
                            "r");
        } catch (Throwable th) {
            LOG.e(str2, "Unknown exception : " + th.getMessage());
            return null;
        }
    }
}
