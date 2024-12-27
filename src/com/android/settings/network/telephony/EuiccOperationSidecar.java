package com.android.settings.network.telephony;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import android.util.Log;

import com.android.settings.SidecarFragment;
import com.android.settings.network.SwitchSlotSidecar;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EuiccOperationSidecar extends SidecarFragment
        implements SidecarFragment.Listener {
    public static final AtomicInteger sCurrentOpId =
            new AtomicInteger((int) SystemClock.elapsedRealtime());
    public int mDetailedCode;
    public EuiccManager mEuiccManager;
    public int mOpId;
    public final AnonymousClass1 mReceiver = new BroadcastReceiver() { // from class:
                // com.android.settings.network.telephony.EuiccOperationSidecar.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (EuiccOperationSidecar.this.getReceiverAction().equals(intent.getAction())
                            && EuiccOperationSidecar.this.mOpId
                                    == intent.getIntExtra("op_id", -1)) {
                        EuiccOperationSidecar.this.mResultCode = getResultCode();
                        EuiccOperationSidecar.this.mDetailedCode =
                                intent.getIntExtra(
                                        "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE",
                                        0);
                        EuiccOperationSidecar.this.getClass();
                        Log.i(
                                "EuiccOperationSidecar",
                                String.format(
                                        "Result code : %d; detailed code : %d",
                                        Integer.valueOf(EuiccOperationSidecar.this.mResultCode),
                                        Integer.valueOf(EuiccOperationSidecar.this.mDetailedCode)));
                        EuiccOperationSidecar.this.onActionReceived();
                    }
                }
            };
    public int mResultCode;
    public SwitchSlotSidecar mSwitchSlotSidecar;
    public TelephonyManager mTelephonyManager;

    public final PendingIntent createCallbackIntent() {
        this.mOpId = sCurrentOpId.incrementAndGet();
        Intent intent = new Intent(getReceiverAction());
        intent.putExtra("op_id", this.mOpId);
        return PendingIntent.getBroadcast(getContext(), 0, intent, 335544320);
    }

    public abstract String getReceiverAction();

    public void onActionReceived() {
        int i = this.mResultCode;
        if (i == 0) {
            setState(2, 0);
        } else {
            setState(3, i);
        }
    }

    @Override // com.android.settings.SidecarFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mEuiccManager = (EuiccManager) getContext().getSystemService(EuiccManager.class);
        this.mTelephonyManager =
                (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
        this.mSwitchSlotSidecar =
                (SwitchSlotSidecar)
                        SidecarFragment.get(
                                getChildFragmentManager(),
                                "SwitchSlotSidecar",
                                SwitchSlotSidecar.class);
        getContext()
                .getApplicationContext()
                .registerReceiver(
                        this.mReceiver,
                        new IntentFilter(getReceiverAction()),
                        "android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS",
                        null,
                        2);
    }

    @Override // com.android.settings.SidecarFragment, android.app.Fragment
    public final void onDestroy() {
        getContext().getApplicationContext().unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    @Override // android.app.Fragment
    public void onPause() {
        this.mSwitchSlotSidecar.removeListener(this);
        super.onPause();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.mSwitchSlotSidecar.addListener(this);
    }

    public void onStateChange(SidecarFragment sidecarFragment) {
        SwitchSlotSidecar switchSlotSidecar = this.mSwitchSlotSidecar;
        if (sidecarFragment != switchSlotSidecar) {
            Log.wtf("EuiccOperationSidecar", "Received state change from a sidecar not expected.");
            return;
        }
        int i = switchSlotSidecar.mState;
        if (i == 2) {
            switchSlotSidecar.setState(0, 0);
            Log.i("EuiccOperationSidecar", "mSwitchSlotSidecar SUCCESS");
        } else {
            if (i != 3) {
                return;
            }
            switchSlotSidecar.setState(0, 0);
            Log.i("EuiccOperationSidecar", "mSwitchSlotSidecar ERROR");
        }
    }
}
