package com.samsung.android.settings.accessibility.dexterity;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AssistantMenuPreferenceController$$ExternalSyntheticLambda2
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AssistantMenuPreferenceController f$0;

    public /* synthetic */ AssistantMenuPreferenceController$$ExternalSyntheticLambda2(
            AssistantMenuPreferenceController assistantMenuPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = assistantMenuPreferenceController;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        AssistantMenuPreferenceController assistantMenuPreferenceController = this.f$0;
        switch (i2) {
            case 0:
                assistantMenuPreferenceController.lambda$setChecked$2(dialogInterface, i);
                break;
            default:
                assistantMenuPreferenceController.lambda$showExclusiveDialog$3(dialogInterface, i);
                break;
        }
    }
}
