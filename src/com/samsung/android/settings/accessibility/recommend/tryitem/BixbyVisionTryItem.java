package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Intent;

import com.android.settings.R;
import com.android.settings.Utils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BixbyVisionTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return new Intent("samsung.intentfilter.visionintelligence.camera")
                .addFlags(67108864)
                .putExtra("LAUNCH_MODE", 99);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("talkback_preference");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.bixby_vision_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return Utils.hasPackage(this.mContext, "com.samsung.android.visionintelligence");
    }
}
