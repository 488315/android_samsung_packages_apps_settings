package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Intent;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface TryItem {
    Intent getLaunchIntent();

    Set getMappedUsingFunctionKeySet();

    CharSequence getTitle();

    boolean isAvailable();
}
