package com.samsung.android.settings.eternal.providercall;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.settings.eternal.log.EternalFileLog;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ProviderCallTask extends FutureTask {
    public String mMethod;
    public String mUid;
    public Uri mUri;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProviderCallWorker implements Callable {
        public String mArgs;
        public Context mContext;
        public Bundle mExtras;
        public String mMethod;
        public String mUid;
        public Uri mUri;

        @Override // java.util.concurrent.Callable
        public final Object call() {
            ContentProviderClient acquireUnstableContentProviderClient;
            String str = this.mMethod;
            String str2 = this.mUid;
            if (TextUtils.isEmpty(str2)) {
                str2 = this.mUri.toString();
            }
            try {
                acquireUnstableContentProviderClient =
                        this.mContext
                                .getContentResolver()
                                .acquireUnstableContentProviderClient(this.mUri);
                try {
                } finally {
                }
            } catch (Exception e) {
                EternalFileLog.e(
                        "Eternal/ProviderCallWorker", str + " [" + str2 + "] " + e.getMessage());
            }
            if (acquireUnstableContentProviderClient != null) {
                Bundle call =
                        acquireUnstableContentProviderClient.call(str, this.mArgs, this.mExtras);
                acquireUnstableContentProviderClient.close();
                return call;
            }
            EternalFileLog.e(
                    "Eternal/ProviderCallWorker",
                    str + " [" + str2 + "] contentProviderClient is null");
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
            return null;
        }
    }

    public static ProviderCallTask createTask(
            Context context, String str, Uri uri, String str2, String str3, Bundle bundle) {
        ProviderCallWorker providerCallWorker = new ProviderCallWorker();
        providerCallWorker.mContext = context;
        providerCallWorker.mUid = str;
        providerCallWorker.mUri = uri;
        providerCallWorker.mMethod = str2;
        providerCallWorker.mArgs = str3;
        providerCallWorker.mExtras = bundle;
        ProviderCallTask providerCallTask = new ProviderCallTask(providerCallWorker);
        providerCallTask.mUid = str;
        providerCallTask.mUri = uri;
        providerCallTask.mMethod = str2;
        return providerCallTask;
    }
}
