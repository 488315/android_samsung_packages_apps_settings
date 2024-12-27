package com.samsung.android.scloud.oem.lib.common;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.scloud.oem.lib.LOG;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class IClientHelper {
    public abstract Object getClient();

    public abstract IServiceHandler getServiceHandler(String str);

    public final Bundle handleRequest(Context context, String str, String str2, Bundle bundle) {
        IServiceHandler serviceHandler = getServiceHandler(str);
        if (serviceHandler != null) {
            return serviceHandler.handleServiceAction(context, getClient(), str2, bundle);
        }
        LOG.i("IClientHelper", "handleRequest can't find method:" + str);
        return null;
    }
}
