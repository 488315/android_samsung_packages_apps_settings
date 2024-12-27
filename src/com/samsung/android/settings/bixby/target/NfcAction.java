package com.samsung.android.settings.bixby.target;

import android.app.ActivityThread;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NfcAction extends Action {
    public final NfcAdapter mNfcAdapter;

    public NfcAction(Context context, Bundle bundle) {
        super(context, bundle);
        this.mNfcAdapter = null;
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
    }

    public static boolean hasNfcFeature() {
        IPackageManager packageManager = ActivityThread.getPackageManager();
        if (packageManager == null) {
            Log.e("NfcAction", "Cannot get package manager, assuming no NFC feature");
            return false;
        }
        try {
            return packageManager.hasSystemFeature("android.hardware.nfc", 0);
        } catch (RemoteException e) {
            Log.e("NfcAction", "Package manager query failed, assuming no NFC feature", e);
            return false;
        }
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doChangeAction() {
        String str;
        String value = getValue();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "doChangeAction() :: newValue - ", value, "NfcAction");
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter != null) {
            int adapterState = nfcAdapter.getAdapterState();
            str = "already_on";
            if ("NfcStandardMode".equals(value)) {
                if (adapterState == 1) {
                    this.mNfcAdapter.enable();
                } else if (!this.mNfcAdapter.isReaderOptionEnabled()) {
                    this.mNfcAdapter.enableReaderOption(true);
                }
                str = "success";
            } else if ("NfcCardMode".equals(value)) {
                if (adapterState != 3) {
                    this.mNfcAdapter.enable();
                } else if (this.mNfcAdapter.isReaderOptionEnabled()) {
                    this.mNfcAdapter.enableReaderOption(false);
                }
                str = "success";
            }
            return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
        }
        str = "false";
        return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        Log.d("NfcAction", "doGetAction()");
        if (!hasNfcFeature()) {
            return Action.createResult("not_supported_device");
        }
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        return (nfcAdapter == null || nfcAdapter.getAdapterState() != 3)
                ? Action.createResult("false")
                : Action.createResult("true");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        Log.d("NfcAction", "doGotoAction()");
        if (this.mNfcAdapter == null) {
            return Action.createResult("false");
        }
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$NfcSettingsActivity");
        intent.setAction("android.settings.NFC_SETTINGS");
        intent.setFlags(268468224);
        Integer taskId = getTaskId();
        Log.d("NfcAction", "doGotoAction() :: TargetTask is existed");
        Utils.setTaskIdToIntent(intent, taskId);
        try {
            launchSettings(
                    intent,
                    Utils.isDesktopModeEnabled(this.mContext)
                            ? BixbyUtils.getDeXDisplay(this.mContext)
                            : null);
            str = "success";
        } catch (ActivityNotFoundException unused) {
            str = "not_supported_device";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String value = getValue();
        Log.d("NfcAction", "doSetAction() :: newValue - " + value);
        if (!hasNfcFeature()) {
            return Action.createResult("not_supported_device");
        }
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter == null) {
            return Action.createResult("false");
        }
        int adapterState = nfcAdapter.getAdapterState();
        String str = "success";
        if ("true".equals(value)) {
            if (adapterState == 1) {
                Log.d("NfcAction", "Enter NFC Settings On");
                this.mNfcAdapter.enable();
            } else {
                str = "already_on";
            }
        } else if (!"false".equals(value)) {
            str = null;
        } else if (adapterState == 3) {
            Log.d("NfcAction", "Enter NFC Settings Off");
            this.mNfcAdapter.disable();
        } else {
            str = "already_off";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return hasNfcFeature() ? Action.createResult("true") : Action.createResult("false");
    }
}
