package com.samsung.android.settings.accessibility.recommend.tryitem;

import com.samsung.android.settings.knox.KnoxUtils;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FlashNotificationsTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of(
                "all_sound_off_key", "amplify_ambient_sound", "magnification_preference_screen");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return (KnoxUtils.isApplicationRestricted(
                                this.mContext,
                                List.of(
                                        "accessibility_advanced_settings",
                                        "accessibility_flash_notificaitons"))
                        || isTurnedOnState())
                ? false
                : true;
    }

    public abstract boolean isTurnedOnState();
}
