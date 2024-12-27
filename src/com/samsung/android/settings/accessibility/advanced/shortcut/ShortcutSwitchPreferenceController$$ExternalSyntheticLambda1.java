package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ShortcutSwitchPreferenceController$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutSwitchPreferenceController f$0;

    public /* synthetic */ ShortcutSwitchPreferenceController$$ExternalSyntheticLambda1(
            ShortcutSwitchPreferenceController shortcutSwitchPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = shortcutSwitchPreferenceController;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        ShortcutSwitchPreferenceController shortcutSwitchPreferenceController = this.f$0;
        switch (i2) {
            case 0:
                shortcutSwitchPreferenceController.lambda$onAllowButtonFromEnableToggleClicked$4(
                        dialogInterface, i);
                break;
            case 1:
                shortcutSwitchPreferenceController.lambda$checkExclusiveDialog$6(
                        dialogInterface, i);
                break;
            default:
                shortcutSwitchPreferenceController.lambda$showExclusiveDialog$5(dialogInterface, i);
                break;
        }
    }
}
