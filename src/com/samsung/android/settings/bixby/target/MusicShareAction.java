package com.samsung.android.settings.bixby.target;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MusicShareAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doChangeAction() {
        String value = getValue();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "doChangeAction() :: newValue - ", value, "MusicShareAction");
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 0)
                == 0) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 1);
        }
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "bluetooth_cast_range", 0);
        String str = "success";
        if ("true".equals(value)) {
            if (i != 1) {
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(), "bluetooth_cast_range", 1);
            }
        } else if (!"false".equals(value)) {
            str = "fail";
        } else if (i != 0) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "bluetooth_cast_range", 0);
        }
        return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        return Action.createResult(
                Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 0)
                                == 1
                        ? "true"
                        : "false");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        Log.d("MusicShareAction", "gotoMusicShareSettings()");
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$BluetoothCastSettingsActivity");
        intent.setAction("com.samsung.settings.BLUETOOTH_CAST_SETTINGS");
        intent.setFlags(268468224);
        Integer taskId = getTaskId();
        Log.d("MusicShareAction", "gotoBluetoothSettings() :: TargetTask is existed");
        Utils.setTaskIdToIntent(intent, taskId);
        try {
            launchSettings(intent, null);
            str = "success";
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "MusicShareAction",
                    "ActivityNotFoundException :: Can not found BluetoothSettings");
            str = "not_supported_device";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String value = getValue();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "doSetAction() :: newValue - ", value, "MusicShareAction");
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "bluetooth_cast_mode", 0);
        String str = "success";
        if ("true".equals(value)) {
            if (i == 1) {
                str = "already_on";
            } else {
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(), "bluetooth_cast_mode", 1);
            }
        } else if (!"false".equals(value)) {
            str = "fail";
        } else if (i == 0) {
            str = "already_off";
        } else {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 0);
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return SemBluetoothCastAdapter.isBluetoothCastSupported()
                ? Action.createResult("true")
                : Action.createResult("false");
    }
}
