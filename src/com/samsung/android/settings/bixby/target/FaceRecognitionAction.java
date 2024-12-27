package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.biometrics.face.FaceSettingsPreferenceController;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FaceRecognitionAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setAction("android.settings.FACE_SETTINGS");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Bundle bundle = new Bundle();
        bundle.putString(
                ":settings:fragment_args_key", FaceSettingsPreferenceController.KEY_FACE_SETTINGS);
        intent.putExtra(":settings:show_fragment_args", bundle);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return Action.createResult("true");
    }
}
