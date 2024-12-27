package com.android.settings.users;

import android.content.Intent;

import com.android.settingslib.users.ActivityStarter;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSettings$$ExternalSyntheticLambda10
        implements ActivityStarter {
    public final /* synthetic */ UserSettings f$0;

    @Override // com.android.settingslib.users.ActivityStarter
    public final void startActivityForResult(Intent intent) {
        this.f$0.startActivityForResult(
                intent, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
    }
}
