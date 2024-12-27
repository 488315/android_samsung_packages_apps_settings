package com.samsung.android.settings.bixby.target;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settings.notification.zen.ZenModeBackend;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NoDisturbAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0) != 0;
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(z));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent("android.settings.ZEN_MODE_SETTINGS");
        intent.addFlags(268468224);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        if (Boolean.valueOf(getValue()).booleanValue()) {
            int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_duration", 0);
            if (i == -1 || i == 0) {
                NotificationManager.from(this.mContext).setZenMode(1, null, "NoDisturbAction");
            } else {
                new ZenModeBackend(this.mContext).setZenModeForDuration(i);
            }
        } else {
            NotificationManager.from(this.mContext).setZenMode(0, null, "NoDisturbAction");
        }
        return Action.createResult("success");
    }
}
