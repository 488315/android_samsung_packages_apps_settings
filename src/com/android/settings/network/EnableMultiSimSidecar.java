package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;
import android.util.ArraySet;
import android.util.Log;

import com.android.settings.AsyncTaskSidecar;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnableMultiSimSidecar extends AsyncTaskSidecar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TelephonyManager mTelephonyManager;
    public final CountDownLatch mSimCardStateChangedLatch = new CountDownLatch(1);
    public int mNumOfActiveSim = 0;
    public final AnonymousClass1 mCarrierConfigChangeReceiver =
            new BroadcastReceiver() { // from class:
                // com.android.settings.network.EnableMultiSimSidecar.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Set set;
                    EnableMultiSimSidecar enableMultiSimSidecar = EnableMultiSimSidecar.this;
                    int activeModemCount =
                            enableMultiSimSidecar.mTelephonyManager.getActiveModemCount();
                    UiccSlotInfo[] uiccSlotsInfo =
                            enableMultiSimSidecar.mTelephonyManager.getUiccSlotsInfo();
                    int i = 0;
                    if (uiccSlotsInfo == null) {
                        set = Collections.emptySet();
                    } else {
                        ArraySet arraySet = new ArraySet();
                        for (UiccSlotInfo uiccSlotInfo : uiccSlotsInfo) {
                            if (uiccSlotInfo != null) {
                                for (UiccPortInfo uiccPortInfo : uiccSlotInfo.getPorts()) {
                                    if (uiccPortInfo.isActive() && uiccSlotInfo.isRemovable()) {
                                        arraySet.add(
                                                Integer.valueOf(
                                                        uiccPortInfo.getLogicalSlotIndex()));
                                    }
                                }
                            }
                        }
                        set = arraySet;
                    }
                    int i2 = 0;
                    for (int i3 = 0; i3 < activeModemCount; i3++) {
                        int simState = enableMultiSimSidecar.mTelephonyManager.getSimState(i3);
                        if (simState == 5
                                || simState == 6
                                || simState == 10
                                || (simState == 1 && set.contains(Integer.valueOf(i3)))) {
                            i2++;
                        }
                    }
                    UiccSlotInfo[] uiccSlotsInfo2 =
                            EnableMultiSimSidecar.this.mTelephonyManager.getUiccSlotsInfo();
                    if (uiccSlotsInfo2 != null) {
                        int length = uiccSlotsInfo2.length;
                        int i4 = 0;
                        while (i < length) {
                            UiccSlotInfo uiccSlotInfo2 = uiccSlotsInfo2[i];
                            if (uiccSlotInfo2 != null) {
                                Iterator it = uiccSlotInfo2.getPorts().iterator();
                                while (it.hasNext()) {
                                    if (((UiccPortInfo) it.next()).isActive()) {
                                        i4++;
                                    }
                                }
                            }
                            i++;
                        }
                        i = i4;
                    }
                    int i5 = EnableMultiSimSidecar.this.mNumOfActiveSim;
                    if (i2 == i5 && i == i5) {
                        Log.i(
                                "EnableMultiSimSidecar",
                                String.format(
                                        "%d ports are active and ready.", Integer.valueOf(i5)));
                        EnableMultiSimSidecar.this.mSimCardStateChangedLatch.countDown();
                    } else {
                        Log.i(
                                "EnableMultiSimSidecar",
                                String.format(
                                        "%d ports are active and %d SIMs are ready. Keep waiting"
                                            + " until timeout.",
                                        Integer.valueOf(i), Integer.valueOf(i2)));
                    }
                }
            };

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.AsyncTaskSidecar
    public final Object doInBackground(SwitchSlotSidecar.Param param) {
        boolean z = false;
        try {
            try {
                getContext()
                        .registerReceiver(
                                this.mCarrierConfigChangeReceiver,
                                new IntentFilter(
                                        "android.telephony.action.CARRIER_CONFIG_CHANGED"));
                this.mTelephonyManager.switchMultiSimConfig(this.mNumOfActiveSim);
            } catch (InterruptedException e) {
                Log.e(
                        "EnableMultiSimSidecar",
                        "Failed to enable multiple SIM due to InterruptedException",
                        e);
            }
            if (!this.mSimCardStateChangedLatch.await(
                    Settings.Global.getLong(
                            getContext().getContentResolver(),
                            "enable_multi_slot_timeout_millis",
                            40000L),
                    TimeUnit.MILLISECONDS)) {
                Log.e("EnableMultiSimSidecar", "Timeout for waiting SIM status.");
                return Boolean.valueOf(z);
            }
            Log.i("EnableMultiSimSidecar", "Multi SIM were successfully enabled.");
            getContext().unregisterReceiver(this.mCarrierConfigChangeReceiver);
            z = true;
            return Boolean.valueOf(z);
        } finally {
            getContext().unregisterReceiver(this.mCarrierConfigChangeReceiver);
        }
    }

    @Override // com.android.settings.AsyncTaskSidecar
    public final void onPostExecute$1(Object obj) {
        if (((Boolean) obj).booleanValue()) {
            setState(2, 0);
        } else {
            setState(3, 0);
        }
    }

    public final void run$1() {
        TelephonyManager telephonyManager =
                (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
        this.mTelephonyManager = telephonyManager;
        this.mNumOfActiveSim = 2;
        if (2 > telephonyManager.getSupportedModemCount()) {
            Log.e(
                    "EnableMultiSimSidecar",
                    "Requested number of active SIM is greater than supported modem count.");
            setState(3, 0);
        } else if (!this.mTelephonyManager.doesSwitchMultiSimConfigTriggerReboot()) {
            run(null);
        } else {
            Log.e("EnableMultiSimSidecar", "The device does not support reboot free DSDS.");
            setState(3, 0);
        }
    }
}
