package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.provider.Settings;

import com.android.settings.R;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LiveCaptionTryItem extends AbstractTryItem {
    public static final Intent LIVE_CAPTION_INTENT =
            new Intent("com.android.settings.action.live_caption");

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return LIVE_CAPTION_INTENT;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("all_sound_off_key");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.live_caption_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        List<ResolveInfo> queryIntentActivities =
                this.mContext.getPackageManager().queryIntentActivities(LIVE_CAPTION_INTENT, 0);
        return (queryIntentActivities == null
                        || queryIntentActivities.isEmpty()
                        || Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "odi_captions_enabled",
                                        0)
                                != 0)
                ? false
                : true;
    }
}
