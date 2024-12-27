package com.samsung.android.settingslib.bluetooth.detector;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BluetoothRetryDetector {
    public int mCount;
    public FailCase mFailCase;
    public boolean mIsForRestored;
    public final int mMaxCount;
    public HashMap mRestoredDeviceList;
    public Intent mTipsIntent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum FailCase {
        SCANNING_FAILURE(2),
        CONNECTION_FAILURE(1),
        PAIRING_FAILURE(1),
        CONNECTION_FAILURE_HOGP(1),
        CONNECTION_FAILURE_LE(1),
        CONNECTION_FAILURE_WATCH(1),
        CONNECTION_FAILURE_RING(1);

        private final int maxCount;

        FailCase(int i) {
            this.maxCount = i;
        }

        public final int getMaxCount() {
            return this.maxCount;
        }
    }

    public BluetoothRetryDetector(FailCase failCase) {
        Intent intent = new Intent();
        this.mTipsIntent = intent;
        intent.setClassName(
                "com.samsung.android.net.wifi.wifiguider",
                "com.samsung.android.net.wifi.wifiguider.activity.bluetooth.BluetoothGuideActivity");
        this.mIsForRestored = false;
        this.mFailCase = failCase;
        this.mMaxCount = failCase.getMaxCount();
    }

    public final void fireTips(Context context) {
        Log.d("BluetoothRetryDetector", "Launching tips:" + this.mFailCase.name());
        this.mTipsIntent.putExtra("disableReason", this.mFailCase.name());
        this.mTipsIntent.setFlags(67108864);
        context.startActivity(this.mTipsIntent);
    }

    public final void reset() {
        this.mCount = 0;
        if (this.mIsForRestored) {
            this.mRestoredDeviceList.clear();
        }
    }

    public final void setFailCase(FailCase failCase) {
        Log.d("BluetoothRetryDetector", "Setting failcase:" + this.mFailCase.name());
        this.mFailCase = failCase;
    }
}
