package com.samsung.android.settings.connection.tether;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface SecTetherControllerInterface {
    void onDataSaverChanged(boolean z);

    default void startProvisioningIfNecessary(SecTetherSettings secTetherSettings, int i) {
        if (!SecTetherUtils.isProvisioningNeeded(secTetherSettings.getActivity())) {
            startTethering();
            return;
        }
        SecTetherSettings.sTetherChoice = i;
        String[] stringArray = secTetherSettings.getResources().getStringArray(17236257);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(stringArray[0], stringArray[1]);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(" : Provision pkg : ");
        sb.append(stringArray[0]);
        sb.append(", Provision activity : ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                sb, stringArray[1], "SecTetherControllerInterface");
        intent.putExtra("TETHER_TYPE", i);
        try {
            secTetherSettings.startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException unused) {
            Log.d(
                    "SecTetherControllerInterface",
                    "startProvisioningIfNecessary: Activity Not Found");
        }
    }

    void startTethering();

    void updateController();
}
