package com.samsung.android.settings.notification;

import com.android.settings.notification.app.NotificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBubbleCategoryPreferenceController extends NotificationPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_category";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (super.isAvailable()) {
            return isFloatingIconBubble();
        }
        return false;
    }
}
