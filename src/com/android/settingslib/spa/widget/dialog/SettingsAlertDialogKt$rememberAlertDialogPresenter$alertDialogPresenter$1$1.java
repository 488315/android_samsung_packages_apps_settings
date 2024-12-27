package com.android.settingslib.spa.widget.dialog;

import androidx.compose.runtime.MutableState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
        implements AlertDialogPresenter {
    public final /* synthetic */ MutableState $openDialog$delegate;

    public SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1(
            MutableState mutableState) {
        this.$openDialog$delegate = mutableState;
    }

    public final void open() {
        this.$openDialog$delegate.setValue(Boolean.TRUE);
    }
}
