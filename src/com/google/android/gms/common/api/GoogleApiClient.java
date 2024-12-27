package com.google.android.gms.common.api;

import java.util.Collections;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GoogleApiClient {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConnectionCallbacks
            extends com.google.android.gms.common.api.internal.ConnectionCallbacks {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnConnectionFailedListener
            extends com.google.android.gms.common.api.internal.OnConnectionFailedListener {}

    static {
        Collections.newSetFromMap(new WeakHashMap());
    }
}
