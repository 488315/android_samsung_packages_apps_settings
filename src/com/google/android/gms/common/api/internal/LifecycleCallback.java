package com.google.android.gms.common.api.internal;

import android.app.Activity;

import androidx.annotation.Keep;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LifecycleCallback {
    public final LifecycleFragment mLifecycleFragment;

    public LifecycleCallback(LifecycleFragment lifecycleFragment) {}

    @Keep
    private static LifecycleFragment getChimeraLifecycleFragmentImpl(
            LifecycleActivity lifecycleActivity) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    public final Activity getActivity() {
        Activity lifecycleActivity = this.mLifecycleFragment.getLifecycleActivity();
        Preconditions.checkNotNull(lifecycleActivity);
        return lifecycleActivity;
    }
}
