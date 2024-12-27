package com.samsung.android.settings.batterywidget;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BatteryManagerReceiver extends BroadcastReceiver {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryInfoTask extends AsyncTask {
        public final SemCompanionDeviceBatteryInfo mBatteryInfo;
        public final WeakReference mContextWeakReference;
        public final BroadcastReceiver.PendingResult mPendingResult;
        public final Intent mSourceIntent;
        public final long mStart = System.currentTimeMillis();

        public BatteryInfoTask(
                Context context, BroadcastReceiver.PendingResult pendingResult, Intent intent) {
            this.mContextWeakReference = new WeakReference(context);
            this.mPendingResult = pendingResult;
            this.mSourceIntent = intent;
            this.mBatteryInfo =
                    BatteryManagerUtil.convertDeviceTypeIfNeeded(
                            intent.getParcelableExtra("com.samsung.battery.EXTRA_BATTERY_INFO"));
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String str;
            String action = this.mSourceIntent.getAction();
            action.getClass();
            switch (action) {
                case "com.samsung.battery.ACTION_BATTERY_INFO_ADDED":
                    str = "com.samsung.android.settings.intelligence.ACTION_BATTERY_INFO_ADDED";
                    break;
                case "com.samsung.battery.ACTION_BATTERY_INFO_CHANGED":
                    str = "com.samsung.android.settings.intelligence.ACTION_BATTERY_INFO_CHANGED";
                    break;
                case "com.samsung.battery.ACTION_BATTERY_INFO_REMOVED":
                    str = "com.samsung.android.settings.intelligence.ACTION_BATTERY_INFO_REMOVED";
                    break;
                default:
                    str = null;
                    break;
            }
            Intent intent = new Intent(str);
            intent.putExtra(
                    "com.samsung.battery.EXTRA_BATTERY_INFO", (Parcelable) this.mBatteryInfo);
            intent.setComponent(
                    new ComponentName(
                            "com.android.settings.intelligence",
                            "com.samsung.android.settings.intelligence.widget.receiver.BatteryWidgetReceiver"));
            intent.addFlags(268435456);
            if (this.mContextWeakReference.get() != null) {
                ((Context) this.mContextWeakReference.get())
                        .sendBroadcastAsUser(intent, UserHandle.ALL);
                return Boolean.TRUE;
            }
            Log.e(
                    "BatteryWidget/BatteryManagerReceiver",
                    "BatteryInfoTask() : "
                            + this.mBatteryInfo.getDeviceName()
                            + " - Failed to send battery info.");
            return Boolean.FALSE;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Log.i(
                    "BatteryWidget/BatteryManagerReceiver",
                    "BatteryInfoTask() finished / result : "
                            + ((Boolean) obj)
                            + " / DeviceName : "
                            + this.mBatteryInfo.getDeviceName()
                            + " took "
                            + (System.currentTimeMillis() - this.mStart));
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            this.mPendingResult.finish();
            Log.i(
                    "BatteryWidget/BatteryManagerReceiver",
                    "BatteryInfoTask() onPreExecute  / DeviceName : "
                            + this.mBatteryInfo.getDeviceName()
                            + " took "
                            + (System.currentTimeMillis() - this.mStart));
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo;
        long currentTimeMillis = System.currentTimeMillis();
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Log.w("BatteryWidget/BatteryManagerReceiver", "onReceive() action is empty");
            return;
        }
        action.getClass();
        switch (action) {
            case "com.samsung.battery.ACTION_BATTERY_INFO_ADDED":
            case "com.samsung.battery.ACTION_BATTERY_INFO_CHANGED":
            case "com.samsung.battery.ACTION_BATTERY_INFO_REMOVED":
                Log.i("BatteryWidget/BatteryManagerReceiver", "onReceive() action ".concat(action));
                SemCompanionDeviceBatteryInfo parcelableExtra =
                        intent.getParcelableExtra("com.samsung.battery.EXTRA_BATTERY_INFO");
                if (parcelableExtra == null) {
                    Log.i(
                            "BatteryWidget/BatteryManagerReceiver",
                            "onReceive() batteryInfo is null");
                } else {
                    new BatteryInfoTask(context, goAsync(), intent)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                }
                semCompanionDeviceBatteryInfo = parcelableExtra;
                break;
            default:
                Log.w(
                        "BatteryWidget/BatteryManagerReceiver",
                        "onReceive() Unsupported action : ".concat(action));
                semCompanionDeviceBatteryInfo = null;
                break;
        }
        StringBuilder sb = new StringBuilder("onReceive() DeviceName : ");
        sb.append(
                semCompanionDeviceBatteryInfo == null
                        ? "null"
                        : semCompanionDeviceBatteryInfo.getDeviceName());
        sb.append(" took ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        Log.i("BatteryWidget/BatteryManagerReceiver", sb.toString());
    }
}
