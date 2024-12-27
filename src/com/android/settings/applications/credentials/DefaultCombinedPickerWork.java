package com.android.settings.applications.credentials;

import android.os.UserManager;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultCombinedPickerWork extends DefaultCombinedPicker {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // com.android.settings.applications.credentials.DefaultCombinedPicker
    public final int getUser() {
        return Utils.getManagedProfile(UserManager.get(getContext())).getIdentifier();
    }
}
