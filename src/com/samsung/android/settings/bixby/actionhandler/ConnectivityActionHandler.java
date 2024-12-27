package com.samsung.android.settings.bixby.actionhandler;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ConnectivityActionHandler extends BaseActionHandler {
    public static final String[] ACTIONS = {
        "bixby.settingsApp.GetOnOff_Wifi",
        "bixby.settingsApp.SetOnOff_Wifi",
        "bixby.settingsApp.Disable_Wifi",
        "bixby.settingsApp.Connect_Wifi",
        "bixby.settingsApp.Goto_WifiSetting",
        "bixby.settingsApp.Available_WifiQRCode",
        "bixby.settingsApp.Goto_WifiQRCode",
        "bixby.settingsApp.Goto_WifiP2pSetting",
        "bixby.settingsApp.GetOnOff_Bluetooth",
        "bixby.settingsApp.SetOnOff_Bluetooth",
        "bixby.settingsApp.Goto_BluetoothSetting",
        "bixby.settingsApp.Connect_Bluetooth",
        "bixby.settingsApp.GetList_Bluetooth",
        "bixby.settingsApp.TryToConnect_Bluetooth",
        "bixby.settingsApp.TryToDisconnect_Bluetooth",
        "bixby.settingsApp.GetOnOff_MusicShare",
        "bixby.settingsApp.SetOnOff_MusicShare",
        "bixby.settingsApp.Goto_MusicShareSetting",
        "bixby.settingsApp.MusicShareAll",
        "bixby.settingsApp.GetOnOff_NFC",
        "bixby.settingsApp.SetOnOff_NFC",
        "bixby.settingsApp.Goto_NFCSetting",
        "bixby.settingsApp.ChangeMode_NFC",
        "bixby.settingsApp.GetOnOff_Hotspot",
        "bixby.settingsApp.SetOnOff_Hotspot",
        "bixby.settingsApp.Goto_HotspotSetting"
    };
    public Bundle mParamBundle;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x043e, code lost:

       if (r1.equals(r6) == false) goto L223;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String executeActionInternal(
            com.samsung.android.settings.bixby.control.actionparam.BaseActionParam r48) {
        /*
            Method dump skipped, instructions count: 1384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.actionhandler.ConnectivityActionHandler.executeActionInternal(com.samsung.android.settings.bixby.control.actionparam.BaseActionParam):java.lang.String");
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final boolean isAffectedByKnoxPolicy() {
        return false;
    }

    public final void setDeviceId(Bundle bundle) {
        String str;
        HashMap hashMap = (HashMap) bundle.getSerializable("params");
        String str2 = null;
        if (hashMap == null || hashMap.isEmpty()) {
            str = null;
        } else {
            str = null;
            for (String str3 : hashMap.keySet()) {
                if ("device_id".equalsIgnoreCase(str3)) {
                    Collection collection = (Collection) hashMap.get(str3);
                    if (!((collection != null ? collection.size() : 0) == 0)) {
                        str = (String) ((List) hashMap.get(str3)).get(0);
                        str2 = "value";
                    }
                    DialogFragment$$ExternalSyntheticOutline0.m(
                            "setDeviceId, featureKey: ", str2, "ConnectivityActionHandler");
                }
            }
        }
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        this.mParamBundle.putString(str2, str);
    }

    public final void setEnabled(Bundle bundle) {
        String str;
        HashMap hashMap = (HashMap) bundle.getSerializable("params");
        String str2 = null;
        if (hashMap == null || hashMap.isEmpty()) {
            str = null;
        } else {
            str = null;
            for (String str3 : hashMap.keySet()) {
                if ("onoff".equalsIgnoreCase(str3)) {
                    Collection collection = (Collection) hashMap.get(str3);
                    if (!((collection != null ? collection.size() : 0) == 0)) {
                        str = (String) ((List) hashMap.get(str3)).get(0);
                        str2 = "value";
                    }
                    Log.d(
                            "ConnectivityActionHandler",
                            "setEnabled, featureKey: " + str2 + ", featureValue: " + str);
                }
            }
        }
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        this.mParamBundle.putString(str2, str);
    }
}
