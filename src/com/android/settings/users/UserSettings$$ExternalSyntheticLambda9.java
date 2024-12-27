package com.android.settings.users;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.android.internal.util.UserIcons;
import com.android.settings.R;
import com.android.settingslib.users.UserCreatingDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSettings$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ UserSettings f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ UserInfo f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ UserSettings$$ExternalSyntheticLambda9(
            UserSettings userSettings, Drawable drawable, Resources resources, UserInfo userInfo) {
        this.f$0 = userSettings;
        this.f$1 = drawable;
        this.f$3 = resources;
        this.f$2 = userInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserSettings userSettings = this.f$0;
                UserCreatingDialog userCreatingDialog = (UserCreatingDialog) this.f$1;
                UserInfo userInfo = this.f$2;
                Context context = (Context) this.f$3;
                IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                userSettings.getClass();
                userCreatingDialog.dismiss();
                if (userInfo != null) {
                    userSettings.openUserDetails(userInfo, true, context);
                    break;
                } else {
                    Toast.makeText(context, R.string.add_guest_failed, 0).show();
                    userSettings.mAddGuest.setEnabled(true);
                    break;
                }
            default:
                UserSettings userSettings2 = this.f$0;
                Drawable drawable = (Drawable) this.f$1;
                Resources resources = (Resources) this.f$3;
                UserInfo userInfo2 = this.f$2;
                IntentFilter intentFilter2 = UserSettings.USER_REMOVED_INTENT_FILTER;
                userSettings2.getClass();
                if (drawable != null) {
                    userSettings2.mUserManager.setUserIcon(
                            userInfo2.id, UserIcons.convertToBitmap(drawable));
                    break;
                } else {
                    userSettings2.mUserManager.setUserIcon(
                            userInfo2.id,
                            UserIcons.convertToBitmapAtUserIconSize(
                                    resources,
                                    UserIcons.getDefaultUserIcon(resources, userInfo2.id, false)));
                    break;
                }
        }
    }

    public /* synthetic */ UserSettings$$ExternalSyntheticLambda9(
            UserSettings userSettings,
            UserCreatingDialog userCreatingDialog,
            UserInfo userInfo,
            Context context) {
        this.f$0 = userSettings;
        this.f$1 = userCreatingDialog;
        this.f$2 = userInfo;
        this.f$3 = context;
    }
}
