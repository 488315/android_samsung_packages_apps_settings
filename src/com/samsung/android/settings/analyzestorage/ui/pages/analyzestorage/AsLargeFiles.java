package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsLargeFiles implements AsSubListInterface {
    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final int getIconResId() {
        return R.drawable.as_sub_list_large_files;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final int getItemViewResId() {
        return R.id.as_sub_list_large_file;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final String getListSubTitle(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final int getListTitleResId() {
        return R.string.as_large_files;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final int getSAEvent() {
        return 8821;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListInterface
    public final boolean needDivider(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return StorageUsageManager.needToShowAppCacheList(context);
    }
}
