package com.samsung.android.settings.bixby.target;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.Utils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScreenSaverAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        boolean z =
                Settings.Secure.getInt(context.getContentResolver(), "screensaver_enabled", 0) != 0;
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(z));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent();
        intent.setAction("android.settings.DREAM_SETTINGS");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.addFlags(268468224);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        Context context = this.mContext;
        boolean booleanValue = Boolean.valueOf(getValue()).booleanValue();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        Settings.Secure.putInt(
                context.getContentResolver(), "screensaver_enabled", booleanValue ? 1 : 0);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return (SemPersonaManager.isKnoxId(UserHandle.myUserId())
                        || !this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_earcFeatureDisabled_default))
                ? Action.createResult("false")
                : Action.createResult("true");
    }
}
