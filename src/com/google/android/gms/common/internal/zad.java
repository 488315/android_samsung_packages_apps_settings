package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zad extends zag {
    public final /* synthetic */ Intent zaa;
    public final /* synthetic */ Activity zab;
    public final /* synthetic */ int zac = 2;

    public zad(Activity activity, Intent intent) {
        this.zaa = intent;
        this.zab = activity;
    }

    @Override // com.google.android.gms.common.internal.zag
    public final void zaa() {
        Intent intent = this.zaa;
        if (intent != null) {
            this.zab.startActivityForResult(intent, this.zac);
        }
    }
}
