package com.samsung.android.settings.accessibility.recommend.tryitem;

import com.android.settings.R;
import com.android.settings.accessibility.FlashNotificationsUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScreenFlashNotificationTryItem extends FlashNotificationsTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.flash_notification_screen);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.FlashNotificationsTryItem
    public final boolean isTurnedOnState() {
        int flashNotificationsState =
                FlashNotificationsUtil.getFlashNotificationsState(this.mContext);
        return flashNotificationsState == 3 || flashNotificationsState == 2;
    }
}
