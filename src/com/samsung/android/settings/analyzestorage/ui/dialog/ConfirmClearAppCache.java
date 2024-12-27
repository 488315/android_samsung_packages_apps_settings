package com.samsung.android.settings.analyzestorage.ui.dialog;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ConfirmClearAppCache implements ConfirmAppInfoInterface {
    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoInterface
    public final int getPluralText() {
        return R.plurals.as_clear_apps_cache;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoInterface
    public final int getPositiveButtonText() {
        return R.string.as_clear;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoInterface
    public final int getSingularText() {
        return R.string.as_clear_app_cache;
    }
}
