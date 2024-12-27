package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Intent intent;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AccessibilityShortcutEditFragment accessibilityShortcutEditFragment =
                        (AccessibilityShortcutEditFragment) obj;
                AccessibilityDialogUtils.TTSLanguageDialogInfo tTSLanguageDialogInfo =
                        accessibilityShortcutEditFragment.languageDialogInfo;
                if (tTSLanguageDialogInfo != null && tTSLanguageDialogInfo.shouldShowDialog) {
                    accessibilityShortcutEditFragment.mIsDialogShown.set(false);
                    accessibilityShortcutEditFragment.showPopupDialog$1(9004);
                    Dialog dialog = accessibilityShortcutEditFragment.mWarningDialog;
                    if (dialog != null && dialog.isShowing()) {
                        accessibilityShortcutEditFragment.mWarningDialog.dismiss();
                        break;
                    }
                } else if (!AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                        accessibilityShortcutEditFragment.mContext,
                        accessibilityShortcutEditFragment.exclusiveTaskFeature)) {
                    accessibilityShortcutEditFragment.switchBar.setCheckedInternal(true);
                    accessibilityShortcutEditFragment.mIsDialogShown.set(false);
                    accessibilityShortcutEditFragment.mWarningDialog.dismiss();
                    break;
                } else {
                    accessibilityShortcutEditFragment.mIsDialogShown.set(false);
                    accessibilityShortcutEditFragment.showPopupDialog$1(9002);
                    accessibilityShortcutEditFragment.mWarningDialog.dismiss();
                    break;
                }
                break;
            case 1:
                AccessibilityShortcutEditFragment accessibilityShortcutEditFragment2 =
                        (AccessibilityShortcutEditFragment) obj;
                accessibilityShortcutEditFragment2.switchBar.setChecked(false);
                accessibilityShortcutEditFragment2.updateCheckEnableStatus(false);
                accessibilityShortcutEditFragment2.mIsDialogShown.set(false);
                accessibilityShortcutEditFragment2.mWarningDialog.dismiss();
                break;
            case 2:
                AccessibilityShortcutEditFragment accessibilityShortcutEditFragment3 =
                        (AccessibilityShortcutEditFragment) obj;
                accessibilityShortcutEditFragment3.mIsDialogShown.set(false);
                accessibilityShortcutEditFragment3.mWarningDialog.dismiss();
                AccessibilityServiceInfo accessibilityServiceInfo =
                        accessibilityShortcutEditFragment3.mAccessibilityServiceInfo;
                if (accessibilityServiceInfo == null) {
                    Log.w(
                            "AccessibilityShortcutEditFragment",
                            "createUnInstallIntent -- invalid a11yServiceInfo");
                    intent = null;
                } else {
                    intent =
                            new Intent(
                                    "android.intent.action.UNINSTALL_PACKAGE",
                                    Uri.parse(
                                            "package:"
                                                    + accessibilityServiceInfo.getResolveInfo()
                                                            .serviceInfo
                                                            .applicationInfo
                                                            .packageName));
                }
                if (intent != null) {
                    accessibilityShortcutEditFragment3.startActivity(intent);
                    break;
                }
                break;
            default:
                Context context = (Context) obj;
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                String string =
                        context.getString(
                                R.string.accessibility_shortcut_accessibility_button_title);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mTitle = string;
                launchRequest.mDestinationName =
                        "com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonPreferenceFragment";
                launchRequest.mSourceMetricsCategory = 0;
                subSettingLauncher.launch();
                break;
        }
    }
}
