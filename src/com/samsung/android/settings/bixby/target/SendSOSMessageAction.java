package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SendSOSMessageAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setClassName(
                "com.sec.android.app.safetyassurance",
                "com.sec.android.app.safetyassurance.settings.SafetyAssuranceSetting");
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        Context context = this.mContext;
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        boolean z2 = UserHandle.myUserId() != 0;
        String str = "com.android.mms";
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME",
                                "com.android.mms");
        if (!"com.android.mms".equals(string) && Utils.hasPackage(context, string)) {
            str = string;
        }
        String[] strArr = {
            str, "jp.softbank.mb.mail", "com.kddi.android.cmail", "com.nttdocomo.android.msg"
        };
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        boolean z3 = false;
        for (int i = 0; i < 4; i++) {
            try {
                applicationInfo = packageManager.getApplicationInfo(strArr[i], 128);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (applicationInfo != null && applicationInfo.enabled) {
                z3 = true;
            }
        }
        return (!z3
                        || !Utils.hasPackage(context, "com.sec.android.app.safetyassurance")
                        || z2
                        || Utils.isTablet())
                ? Action.createResult("false")
                : Action.createResult("true");
    }
}
