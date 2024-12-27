package com.android.settings.accessibility;

import android.content.Context;
import android.view.View;

import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccessibilityDialogUtils$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ AccessibilityDialogUtils$$ExternalSyntheticLambda0(
            Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Context context = this.f$0;
        switch (i) {
            case 0:
                AccessibilityDialogUtils.mAlertDialog.dismiss();
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                String name = AccessibilityButtonPreferenceFragment.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                launchRequest.mSourceMetricsCategory = 1873;
                subSettingLauncher.launch();
                break;
            default:
                SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(context);
                String name2 = AccessibilityButtonFragment.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest2 =
                        subSettingLauncher2.mLaunchRequest;
                launchRequest2.mDestinationName = name2;
                launchRequest2.mSourceMetricsCategory = 1873;
                subSettingLauncher2.launch();
                break;
        }
    }
}
