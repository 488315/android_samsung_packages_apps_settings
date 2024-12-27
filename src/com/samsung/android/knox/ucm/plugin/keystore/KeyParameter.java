package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;

import java.security.KeyStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KeyParameter implements KeyStore.ProtectionParameter {
    public boolean mIsManaged;
    public Bundle mOptions;
    public int mSourceUid;

    public KeyParameter(int i, boolean z, Bundle bundle) {
        this.mSourceUid = i;
        this.mIsManaged = z;
        this.mOptions = bundle;
    }

    public Bundle getOptions() {
        return this.mOptions;
    }

    public int getSourceUid() {
        return this.mSourceUid;
    }

    public boolean isManaged() {
        return this.mIsManaged;
    }
}
