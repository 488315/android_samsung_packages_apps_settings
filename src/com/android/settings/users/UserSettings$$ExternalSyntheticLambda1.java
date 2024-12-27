package com.android.settings.users;

import android.content.IntentFilter;
import android.content.pm.UserInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSettings$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        UserInfo userInfo = (UserInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                return (userInfo.isGuest() || userInfo.isProfile()) ? false : true;
            case 1:
                return userInfo.supportsSwitchToByUser();
            default:
                return userInfo.isGuest();
        }
    }
}
