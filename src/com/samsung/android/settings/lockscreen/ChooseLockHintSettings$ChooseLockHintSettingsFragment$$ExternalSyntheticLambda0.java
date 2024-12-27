package com.samsung.android.settings.lockscreen;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class ChooseLockHintSettings$ChooseLockHintSettingsFragment$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ ChooseLockHintSettings.ChooseLockHintSettingsFragment f$0;

    @Override // java.lang.Runnable
    public final void run() {
        ChooseLockHintSettings.ChooseLockHintSettingsFragment chooseLockHintSettingsFragment =
                this.f$0;
        chooseLockHintSettingsFragment.mHintEditText.performAccessibilityAction(128, null);
        chooseLockHintSettingsFragment.mHeaderText.performAccessibilityAction(64, null);
    }
}
