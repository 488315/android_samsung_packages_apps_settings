package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EthernetMacAddressPreferenceController extends BasePreferenceController {
    private static final String ETH0_MAC_FILE = "/sys/class/net/eth0/address";
    private static final String LOG_TAG = "EthernetMacAddressPreferenceController";
    private static final boolean SUPPORT_ETHERNET_MAC_ADDRESS =
            ConnectionsUtils.isSupportEthernet();

    public EthernetMacAddressPreferenceController(Context context, String str) {
        super(context, str);
    }

    public static String getEth0MacAddressFromFile() {
        String str;
        byte[] bArr = new byte[32];
        DataInputStream dataInputStream = null;
        String str2 = null;
        dataInputStream = null;
        if (!SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(ETH0_MAC_FILE)) {
            Log.d(LOG_TAG, "Eth0MacAddress not exists file : /sys/class/net/eth0/address");
            return null;
        }
        try {
            try {
                DataInputStream dataInputStream2 =
                        new DataInputStream(
                                new BufferedInputStream(new FileInputStream(ETH0_MAC_FILE)));
                try {
                    try {
                        if (dataInputStream2.read(bArr) >= 17) {
                            str = new String(bArr, 0, 17);
                            try {
                                Log.d(LOG_TAG, "Eth0MacAddress : ".concat(str));
                                str2 = str.toUpperCase();
                            } catch (IOException e) {
                                e = e;
                                dataInputStream = dataInputStream2;
                                Log.d(
                                        LOG_TAG,
                                        "Eth0MacAddress Failed to get MAC address from"
                                            + " /sys/class/net/eth0/address, "
                                                + e);
                                if (dataInputStream != null) {
                                    try {
                                        dataInputStream.close();
                                    } catch (IOException unused) {
                                        Log.e(LOG_TAG, "Failed to close .mac.info file");
                                    }
                                }
                                return str;
                            }
                        }
                        try {
                            dataInputStream2.close();
                            return str2;
                        } catch (IOException unused2) {
                            Log.e(LOG_TAG, "Failed to close .mac.info file");
                            return str2;
                        }
                    } catch (Throwable th) {
                        th = th;
                        dataInputStream = dataInputStream2;
                        if (dataInputStream != null) {
                            try {
                                dataInputStream.close();
                            } catch (IOException unused3) {
                                Log.e(LOG_TAG, "Failed to close .mac.info file");
                            }
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    str = null;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
            str = null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SUPPORT_ETHERNET_MAC_ADDRESS ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        String eth0MacAddressFromFile = getEth0MacAddressFromFile();
        if (TextUtils.isEmpty(eth0MacAddressFromFile)) {
            preference.setSummary(R.string.status_unavailable);
        } else {
            preference.setSummary(eth0MacAddressFromFile);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
