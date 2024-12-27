package com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface PackageInfo {
    String getCategory();

    Drawable getIcon();

    String getKeyHint();

    Intent getLaunchIntent();

    int getOrder();

    String getPackageName();

    Intent getQIPPopupIntent();

    String getSummary();

    String getTitle();

    boolean isInstalled();

    boolean isSupportedAI();
}
