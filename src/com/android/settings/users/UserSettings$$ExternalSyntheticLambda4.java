package com.android.settings.users;

import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSettings$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserSettings f$0;

    public /* synthetic */ UserSettings$$ExternalSyntheticLambda4(
            UserSettings userSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = userSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UserSettings userSettings = this.f$0;
        switch (i) {
            case 0:
                UserInfo createGuest =
                        userSettings.mUserManager.createGuest(userSettings.getContext());
                userSettings.mGuestCreationScheduled.set(false);
                if (createGuest == null) {
                    Log.e("UserSettings", "Unable to automatically recreate guest user");
                }
                userSettings.mHandler.sendEmptyMessage(1);
                return;
            case 1:
                synchronized (userSettings.mUserLock) {
                    userSettings.mPendingUserIcon = null;
                    userSettings.mPendingUserName = null;
                }
                return;
            case 2:
                synchronized (userSettings.mUserLock) {
                    userSettings.mPendingUserIcon = null;
                    userSettings.mPendingUserName = null;
                }
                return;
            default:
                IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                userSettings.getActivity().finish();
                return;
        }
    }
}
