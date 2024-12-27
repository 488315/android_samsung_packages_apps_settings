package com.android.settings.applications;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppStorageSettings$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppStorageSettings f$0;

    public /* synthetic */ AppStorageSettings$$ExternalSyntheticLambda0(
            AppStorageSettings appStorageSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = appStorageSettings;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        AppStorageSettings appStorageSettings = this.f$0;
        switch (i) {
            case 0:
                String str = AppStorageSettings.INTERNAL_STORAGE_TEXT;
                appStorageSettings.handleClearDataClick();
                break;
            case 1:
                String str2 = AppStorageSettings.INTERNAL_STORAGE_TEXT;
                appStorageSettings.handleClearCacheClick();
                break;
            default:
                String str3 = AppStorageSettings.INTERNAL_STORAGE_TEXT;
                appStorageSettings.handleClearDataClick();
                break;
        }
    }
}
