package com.android.settings.security;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedEncryptionPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final UserHandle mUserHandle;
    public final UserManager mUserManager;
    public final String mUserRestriction;

    public RestrictedEncryptionPreferenceController(Context context) {
        super(context);
        this.mUserHandle = UserHandle.of(UserHandle.myUserId());
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mUserRestriction = "no_config_credentials";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mUserManager.hasBaseUserRestriction(this.mUserRestriction, this.mUserHandle);
    }
}
