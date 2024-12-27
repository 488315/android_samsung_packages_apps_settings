package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface LifecycleFragment {
    void addCallback(LifecycleCallback lifecycleCallback);

    Activity getLifecycleActivity();

    void startActivityForResult(Intent intent, int i);
}
