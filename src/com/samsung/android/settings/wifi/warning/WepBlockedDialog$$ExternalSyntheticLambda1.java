package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WepBlockedDialog$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WepBlockedDialog f$0;

    public /* synthetic */ WepBlockedDialog$$ExternalSyntheticLambda1(
            WepBlockedDialog wepBlockedDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = wepBlockedDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        WepBlockedDialog wepBlockedDialog = this.f$0;
        switch (i2) {
            case 0:
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(wepBlockedDialog.mActivity);
                String canonicalName = ConfigureWifiSettings.class.getCanonicalName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = canonicalName;
                launchRequest.mArguments = null;
                launchRequest.mSourceMetricsCategory = FileType.XLSB;
                launchRequest.mTitle =
                        wepBlockedDialog
                                .mActivity
                                .getResources()
                                .getString(R.string.wifi_menu_advanced_button);
                subSettingLauncher.launch();
                wepBlockedDialog.mActivity.setResult(0);
                wepBlockedDialog.mActivity.finish();
                break;
            default:
                wepBlockedDialog.mActivity.setResult(0);
                wepBlockedDialog.mActivity.finish();
                break;
        }
    }
}
