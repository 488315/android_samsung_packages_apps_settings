package com.samsung.android.settings.accessibility.bixby.action.hearing;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.sec.ims.presence.ServiceTuple;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HearingAidSupportAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        Settings.System.putInt(context.getContentResolver(), "hearing_aid", z ? 1 : 0);
        ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO))
                .setParameters(z ? "HACSetting=ON" : "HACSetting=OFF");
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result",
                BixbyUtils.getStateAlreadyChecked(
                        parsedBundle.menuValue,
                        Settings.System.getInt(context.getContentResolver(), "hearing_aid", 0)
                                == 1));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        LocalBluetoothProfileManager localBluetoothProfileManager =
                Utils.getLocalBluetoothManager(context).mProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean z = false;
        if (defaultAdapter != null) {
            List supportedProfiles = defaultAdapter.getSupportedProfiles();
            if (supportedProfiles.contains(21) || supportedProfiles.contains(28)) {
                z = true;
            }
        }
        return z
                ? "com.samsung.android.settings.accessibility.hearing.controllers.HearingAidsPreferenceController"
                : "com.samsung.android.settings.accessibility.hearing.controllers.SecSecondDepthHearingAidCompatibilityPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.hearing.HearingAidPreferenceFragment";
    }
}
