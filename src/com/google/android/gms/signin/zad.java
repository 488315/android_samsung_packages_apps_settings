package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zad {
    public static final zaa zac;
    public static final zab zad = null;

    static {
        new Api.ClientKey();
        new Api.ClientKey();
        zac = new zaa();
        new zab();
        new Scope(1, ImsProfile.SERVICE_PROFILE);
        new Scope(1, "email");
    }
}
