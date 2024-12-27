package com.android.settings.users;

import android.content.Context;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;

import com.android.internal.util.UserIcons;
import com.android.settingslib.users.UserCreatingDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSettings$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserSettings f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ UserSettings$$ExternalSyntheticLambda5(
            UserSettings userSettings, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = userSettings;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserSettings.$r8$lambda$H_6GDMyHVA7xwG1CoJJq21CpTnc(
                        this.f$0, (Context) this.f$1, (UserCreatingDialog) this.f$2);
                break;
            default:
                this.f$0.mUserManager.setUserIcon(
                        ((UserInfo) this.f$1).id, UserIcons.convertToBitmap((Drawable) this.f$2));
                break;
        }
    }
}
