package com.android.settings.accessibility;

import android.content.Context;
import android.content.DialogInterface;

import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda2
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ToggleAccessibilityServicePreferenceFragment f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda2(
            ToggleAccessibilityServicePreferenceFragment
                    toggleAccessibilityServicePreferenceFragment,
            int i,
            Context context) {
        this.f$0 = toggleAccessibilityServicePreferenceFragment;
        this.f$2 = i;
        this.f$1 = context;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                ToggleAccessibilityServicePreferenceFragment
                        toggleAccessibilityServicePreferenceFragment = this.f$0;
                Context context = this.f$1;
                int i2 = this.f$2;
                toggleAccessibilityServicePreferenceFragment.mIsDialogShown.set(false);
                if (AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                        context,
                        toggleAccessibilityServicePreferenceFragment.getExclusiveTaskName())) {
                    if (i2 == 9004) {
                        toggleAccessibilityServicePreferenceFragment.showPopupDialog(9002);
                    } else {
                        toggleAccessibilityServicePreferenceFragment.showPopupDialog(9003);
                    }
                } else if (i2 == 9004) {
                    toggleAccessibilityServicePreferenceFragment.handleConfirmServiceEnabled(true);
                } else {
                    toggleAccessibilityServicePreferenceFragment.enableShortcut(context);
                }
                toggleAccessibilityServicePreferenceFragment.mWarningDialog.dismiss();
                break;
            default:
                ToggleAccessibilityServicePreferenceFragment
                        toggleAccessibilityServicePreferenceFragment2 = this.f$0;
                int i3 = this.f$2;
                Context context2 = this.f$1;
                int i4 = ToggleAccessibilityServicePreferenceFragment.$r8$clinit;
                if (i3 == 9002) {
                    toggleAccessibilityServicePreferenceFragment2.handleConfirmServiceEnabled(true);
                } else {
                    toggleAccessibilityServicePreferenceFragment2.enableShortcut(context2);
                }
                toggleAccessibilityServicePreferenceFragment2.mWarningDialog.dismiss();
                break;
        }
    }

    public /* synthetic */ ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda2(
            ToggleAccessibilityServicePreferenceFragment
                    toggleAccessibilityServicePreferenceFragment,
            Context context,
            int i) {
        this.f$0 = toggleAccessibilityServicePreferenceFragment;
        this.f$1 = context;
        this.f$2 = i;
    }
}
