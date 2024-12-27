package com.samsung.android.settings.eternal.providercall;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.samsung.android.settings.eternal.log.EternalFileLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ProviderCallExecutorService {
    public ExecutorService mMultipleExecutorService;
    public ExecutorService mSingleExecutorService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ProviderCallExecutorServiceHolder {
        public static final ProviderCallExecutorService INSTANCE;

        static {
            ProviderCallExecutorService providerCallExecutorService =
                    new ProviderCallExecutorService();
            providerCallExecutorService.mSingleExecutorService =
                    Executors.newSingleThreadExecutor();
            providerCallExecutorService.mMultipleExecutorService = Executors.newCachedThreadPool();
            INSTANCE = providerCallExecutorService;
        }
    }

    public static Bundle getResultBundle(ProviderCallTask providerCallTask) {
        String str = providerCallTask.mUid;
        if (TextUtils.isEmpty(str)) {
            str = providerCallTask.mUri.toString();
        }
        try {
            return (Bundle) providerCallTask.get(10000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m("##### [", str, "] ");
            m.append(providerCallTask.mMethod);
            m.append(" - ");
            m.append(e);
            EternalFileLog.e("Eternal/ProviderCallExecutorService", m.toString());
            return null;
        }
    }

    public static HashMap getResultByUid(List list) {
        HashMap hashMap = new HashMap();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ProviderCallTask providerCallTask = (ProviderCallTask) it.next();
            hashMap.put(providerCallTask.mUid, getResultBundle(providerCallTask));
        }
        return hashMap;
    }
}
