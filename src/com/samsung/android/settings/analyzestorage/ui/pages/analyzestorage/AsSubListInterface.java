package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface AsSubListInterface {
    int getIconResId();

    int getItemViewResId();

    String getListSubTitle(Context context);

    int getListTitleResId();

    int getSAEvent();

    default boolean needDivider(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }

    default boolean visibleSubList(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }
}
