package com.android.settings.password;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ChooseLockPattern.ChooseLockPatternFragment f$0;

    public /* synthetic */ ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0(
            ChooseLockPattern.ChooseLockPatternFragment chooseLockPatternFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = chooseLockPatternFragment;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        ChooseLockPattern.ChooseLockPatternFragment chooseLockPatternFragment = this.f$0;
        switch (i) {
            case 0:
                chooseLockPatternFragment.onSkipOrClearButtonClick();
                break;
            default:
                chooseLockPatternFragment.handleRightButton();
                break;
        }
    }
}
