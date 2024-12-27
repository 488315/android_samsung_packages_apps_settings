package com.android.settings.wifi.tether;

import android.app.Service;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.TetheringManager;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TetherService extends Service {
    public static final boolean DEBUG = Log.isLoggable("TetherService", 3);
    public static final String EXTRA_RESULT = "EntitlementResult";
    public static final String EXTRA_TETHER_PROVISIONING_RESPONSE =
            "android.net.extra.TETHER_PROVISIONING_RESPONSE";
    public static final String EXTRA_TETHER_SILENT_PROVISIONING_ACTION =
            "android.net.extra.TETHER_SILENT_PROVISIONING_ACTION";
    public static final String EXTRA_TETHER_SUBID = "android.net.extra.TETHER_SUBID";
    public ArrayList mCurrentTethers;
    public int mCurrentTypeIndex;
    public boolean mInProvisionCheck;
    public ArrayMap mPendingCallbacks;
    public String mProvisionAction;
    public TetherServiceWrapper mWrapper;
    public String mExpectedProvisionResponseAction = null;
    public int mSubId = -1;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.wifi.tether.TetherService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (TetherService.DEBUG) {
                        Log.d("TetherService", "Got provision result " + intent);
                    }
                    if (!intent.getAction()
                            .equals(TetherService.this.mExpectedProvisionResponseAction)) {
                        StringBuilder sb =
                                new StringBuilder(
                                        "Received provisioning response for unexpected action=");
                        sb.append(intent.getAction());
                        sb.append(", expected=");
                        MainClear$$ExternalSyntheticOutline0.m(
                                sb,
                                TetherService.this.mExpectedProvisionResponseAction,
                                "TetherService");
                        return;
                    }
                    TetherService tetherService = TetherService.this;
                    if (!tetherService.mInProvisionCheck) {
                        Log.e(
                                "TetherService",
                                "Unexpected provisioning response when not in provisioning check"
                                        + intent);
                        return;
                    }
                    Integer num =
                            (Integer)
                                    tetherService.mCurrentTethers.get(
                                            tetherService.mCurrentTypeIndex);
                    int intValue = num.intValue();
                    TetherService.this.mInProvisionCheck = false;
                    int intExtra = intent.getIntExtra(TetherService.EXTRA_RESULT, 0);
                    if (intExtra != -1) {
                        TetherService tetherService2 = TetherService.this;
                        tetherService2.getClass();
                        Log.w("TetherService", "Disable tethering, type:" + intValue);
                        ((TetheringManager) tetherService2.getSystemService("tethering"))
                                .stopTethering(intValue);
                    }
                    List<ResultReceiver> list =
                            (List) TetherService.this.mPendingCallbacks.get(num);
                    if (list != null) {
                        int i = intExtra != -1 ? 11 : 0;
                        for (ResultReceiver resultReceiver : list) {
                            if (TetherService.DEBUG) {
                                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0
                                        .m(i, "Firing result: ", " to callback", "TetherService");
                            }
                            resultReceiver.send(i, null);
                        }
                        list.clear();
                    }
                    TetherService tetherService3 = TetherService.this;
                    int i2 = tetherService3.mCurrentTypeIndex + 1;
                    tetherService3.mCurrentTypeIndex = i2;
                    if (i2 >= tetherService3.mCurrentTethers.size()) {
                        TetherService.this.stopSelf();
                    } else {
                        TetherService tetherService4 = TetherService.this;
                        tetherService4.startProvisioning(tetherService4.mCurrentTypeIndex);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class TetherServiceWrapper {
        public final UsageStatsManager mUsageStatsManager;

        public TetherServiceWrapper(Context context) {
            this.mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        }
    }

    public final Intent getProvisionBroadcastIntent(int i) {
        if (this.mProvisionAction == null) {
            Log.wtf("TetherService", "null provisioning action");
        }
        Intent intent = new Intent(this.mProvisionAction);
        intent.putExtra("TETHER_TYPE", ((Integer) this.mCurrentTethers.get(i)).intValue());
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", this.mSubId);
        intent.setFlags(285212672);
        return intent;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        if (DEBUG) {
            Log.d("TetherService", "Creating TetherService");
        }
        String string =
                getSharedPreferences("tetherPrefs", 0)
                        .getString("currentTethers", ApnSettings.MVNO_NONE);
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split(",")) {
                arrayList.add(Integer.valueOf(Integer.parseInt(str)));
            }
        }
        this.mCurrentTethers = arrayList;
        this.mCurrentTypeIndex = 0;
        ArrayMap arrayMap = new ArrayMap(3);
        this.mPendingCallbacks = arrayMap;
        arrayMap.put(0, new ArrayList());
        this.mPendingCallbacks.put(1, new ArrayList());
        this.mPendingCallbacks.put(2, new ArrayList());
        this.mPendingCallbacks.put(5, new ArrayList());
    }

    @Override // android.app.Service
    public final void onDestroy() {
        if (this.mInProvisionCheck) {
            Log.e(
                    "TetherService",
                    "TetherService getting destroyed while mid-provisioning"
                            + this.mCurrentTethers.get(this.mCurrentTypeIndex));
        }
        SharedPreferences.Editor edit = getSharedPreferences("tetherPrefs", 0).edit();
        ArrayList arrayList = this.mCurrentTethers;
        StringBuffer stringBuffer = new StringBuffer();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(arrayList.get(i));
        }
        edit.putString("currentTethers", stringBuffer.toString()).commit();
        if (this.mExpectedProvisionResponseAction != null) {
            unregisterReceiver(this.mReceiver);
            this.mExpectedProvisionResponseAction = null;
        }
        if (DEBUG) {
            Log.d("TetherService", "Destroying TetherService");
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        if (intent.hasExtra(EXTRA_TETHER_SUBID)) {
            int intExtra = intent.getIntExtra(EXTRA_TETHER_SUBID, -1);
            if (this.mWrapper == null) {
                this.mWrapper = new TetherServiceWrapper(this);
            }
            this.mWrapper.getClass();
            int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
            if (intExtra != activeDataSubscriptionId) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(
                                activeDataSubscriptionId,
                                "This Provisioning request is outdated, current subId: ",
                                "TetherService");
                if (!this.mInProvisionCheck) {
                    stopSelf();
                }
                return 2;
            }
            this.mSubId = activeDataSubscriptionId;
        }
        if (intent.hasExtra("extraAddTetherType")) {
            int intExtra2 = intent.getIntExtra("extraAddTetherType", -1);
            ResultReceiver resultReceiver =
                    (ResultReceiver) intent.getParcelableExtra("extraProvisionCallback");
            if (resultReceiver != null) {
                List list = (List) this.mPendingCallbacks.get(Integer.valueOf(intExtra2));
                if (list == null) {
                    Log.e("TetherService", "Invalid tethering type " + intExtra2 + ", stopping");
                    resultReceiver.send(1, null);
                    stopSelf();
                    return 2;
                }
                list.add(resultReceiver);
            }
            if (!this.mCurrentTethers.contains(Integer.valueOf(intExtra2))) {
                if (DEBUG) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            intExtra2, "Adding tether ", "TetherService");
                }
                this.mCurrentTethers.add(Integer.valueOf(intExtra2));
            }
        }
        String stringExtra = intent.getStringExtra(EXTRA_TETHER_SILENT_PROVISIONING_ACTION);
        this.mProvisionAction = stringExtra;
        if (stringExtra == null) {
            Log.e("TetherService", "null provisioning action, stop ");
            stopSelf();
            return 2;
        }
        String stringExtra2 = intent.getStringExtra(EXTRA_TETHER_PROVISIONING_RESPONSE);
        if (stringExtra2 == null) {
            Log.e("TetherService", "null provisioning response, stop ");
            stopSelf();
            return 2;
        }
        if (!stringExtra2.equals(this.mExpectedProvisionResponseAction)) {
            if (this.mExpectedProvisionResponseAction != null) {
                unregisterReceiver(this.mReceiver);
            }
            registerReceiver(
                    this.mReceiver,
                    new IntentFilter(stringExtra2),
                    "android.permission.TETHER_PRIVILEGED",
                    null,
                    2);
            this.mExpectedProvisionResponseAction = stringExtra2;
            if (DEBUG) {
                Log.d("TetherService", "registerReceiver ".concat(stringExtra2));
            }
        }
        if (intent.hasExtra("extraRemTetherType")) {
            if (!this.mInProvisionCheck) {
                int intExtra3 = intent.getIntExtra("extraRemTetherType", -1);
                int indexOf = this.mCurrentTethers.indexOf(Integer.valueOf(intExtra3));
                boolean z = DEBUG;
                if (z) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "Removing tether ", ", index ", intExtra3, indexOf, "TetherService");
                }
                if (indexOf >= 0) {
                    this.mCurrentTethers.remove(indexOf);
                    if (z) {
                        Preference$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mCurrentTypeIndex: "),
                                this.mCurrentTypeIndex,
                                "TetherService");
                    }
                    int i3 = this.mCurrentTypeIndex;
                    if (indexOf <= i3 && i3 > 0) {
                        this.mCurrentTypeIndex = i3 - 1;
                    }
                }
            } else if (DEBUG) {
                Log.d("TetherService", "Don't remove tether type during provisioning");
            }
        }
        if (intent.getBooleanExtra("extraRunProvision", false)) {
            startProvisioning(this.mCurrentTypeIndex);
            return 3;
        }
        if (this.mInProvisionCheck) {
            return 3;
        }
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i2, "Stopping self.  startid: ", "TetherService");
        }
        stopSelf();
        return 2;
    }

    public void setTetherServiceWrapper(TetherServiceWrapper tetherServiceWrapper) {
        this.mWrapper = tetherServiceWrapper;
    }

    public final void startProvisioning(int i) {
        if (i >= this.mCurrentTethers.size()) {
            return;
        }
        Intent provisionBroadcastIntent = getProvisionBroadcastIntent(i);
        List<ResolveInfo> queryBroadcastReceivers =
                getPackageManager().queryBroadcastReceivers(getProvisionBroadcastIntent(i), 131072);
        if (queryBroadcastReceivers.isEmpty()) {
            Log.e("TetherService", "No found BroadcastReceivers for provision intent.");
        } else {
            for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
                if (resolveInfo.activityInfo.applicationInfo.isSystemApp()) {
                    String str = resolveInfo.activityInfo.packageName;
                    if (this.mWrapper == null) {
                        this.mWrapper = new TetherServiceWrapper(this);
                    }
                    this.mWrapper.mUsageStatsManager.setAppInactive(str, false);
                }
            }
        }
        if (DEBUG) {
            Log.d(
                    "TetherService",
                    "Sending provisioning broadcast: "
                            + provisionBroadcastIntent.getAction()
                            + " type: "
                            + this.mCurrentTethers.get(i));
        }
        sendBroadcast(provisionBroadcastIntent);
        this.mInProvisionCheck = true;
    }
}
