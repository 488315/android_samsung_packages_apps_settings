package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FingerPrintsAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setAction("android.settings.FINGERPRINT_SETTINGS");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Bundle bundle = new Bundle();
        bundle.putString(
                ":settings:fragment_args_key",
                FingerprintSettingsPreferenceController.KEY_FINGER_SCANNER);
        intent.putExtra(":settings:show_fragment_args", bundle);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return SecurityUtils.hasFingerprintFeature(this.mContext)
                ? Action.createResult("true")
                : Action.createResult("false");
    }
}
