package com.android.settings.bluetooth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Process;
import android.os.UserHandle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.presence.ServiceTuple;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class Utils {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public static final AnonymousClass2 mOnInitCallback = new AnonymousClass2();

    public static String findBluetoothPackageName(Context context) {
        PackageManager packageManager =
                context.createContextAsUser(UserHandle.SYSTEM, 0).getPackageManager();
        String str = null;
        for (String str2 : packageManager.getPackagesForUid(1002)) {
            ActivityInfo[] activityInfoArr =
                    packageManager.getPackageInfo(str2, 4203009).activities;
            if (activityInfoArr != null) {
                for (ActivityInfo activityInfo : activityInfoArr) {
                    if ("com.android.bluetooth.opp.BluetoothOppLauncherActivity"
                            .equals(activityInfo.name)) {
                        if (str != null) {
                            throw new PackageManager.NameNotFoundException(
                                    "multiple main bluetooth packages found");
                        }
                        str = str2;
                    }
                }
            }
        }
        if (str != null) {
            return str;
        }
        throw new PackageManager.NameNotFoundException("Could not find main bluetooth package");
    }

    public static List getAllOfCachedBluetoothDevices(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        if (cachedBluetoothDevice == null) {
            Log.e("BluetoothUtils", "getAllOfCachedBluetoothDevices: no cachedBluetoothDevice");
            return arrayList;
        }
        final int i = cachedBluetoothDevice.mGroupId;
        if (i == -1) {
            arrayList.add(cachedBluetoothDevice);
            return arrayList;
        }
        if (localBluetoothManager == null) {
            Log.e("BluetoothUtils", "getAllOfCachedBluetoothDevices: no LocalBluetoothManager");
            return arrayList;
        }
        CachedBluetoothDevice cachedBluetoothDevice2 =
                (CachedBluetoothDevice)
                        localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.bluetooth.Utils$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((CachedBluetoothDevice) obj).mGroupId == i;
                                            }
                                        })
                                .findFirst()
                                .orElse(null);
        if (cachedBluetoothDevice2 == null) {
            Log.e(
                    "BluetoothUtils",
                    "getAllOfCachedBluetoothDevices: groupId = " + i + ", no main device.");
            return arrayList;
        }
        arrayList.add(cachedBluetoothDevice2);
        Iterator it = cachedBluetoothDevice2.mMemberDevices.iterator();
        while (it.hasNext()) {
            arrayList.add((CachedBluetoothDevice) it.next());
        }
        Log.d(
                "BluetoothUtils",
                "getAllOfCachedBluetoothDevices: groupId = "
                        + i
                        + " , cachedBluetoothDevice = "
                        + cachedBluetoothDevice2
                        + " , deviceList = "
                        + arrayList);
        return arrayList;
    }

    public static LocalBluetoothManager getLocalBluetoothManager(final Context context) {
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.android.settings.bluetooth.Utils$$ExternalSyntheticLambda1
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                return LocalBluetoothManager.getInstance(
                                        context, Utils.mOnInitCallback);
                            }
                        });
        try {
            futureTask.run();
            return (LocalBluetoothManager) futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.w("BluetoothUtils", "Error getting LocalBluetoothManager.", e);
            return null;
        }
    }

    public static Account getSamsungAccount(Context context) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public static void insertMDMEventLog(Context context, int i) {
        Uri parse = Uri.parse("content://com.sec.knox.provider/AuditLog");
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                NetworkAnalyticsConstants.DataPoints.UID, Integer.valueOf(Process.myPid()));
        contentValues.put("tag", Integer.valueOf(i));
        try {
            context.getContentResolver().insert(parse, contentValues);
        } catch (IllegalArgumentException e) {
            Log.e("BluetoothUtils", "IllegalArgumentException : " + e);
        }
    }

    public static void insertMDMLog(
            Context context, boolean z, int i, String str, String str2, int i2) {
        Uri parse = Uri.parse("content://com.sec.knox.provider/AuditLog");
        ContentValues contentValues = new ContentValues();
        contentValues.put("severity", (Integer) 5);
        contentValues.put("group", (Integer) 5);
        contentValues.put("outcome", Boolean.valueOf(z));
        contentValues.put(NetworkAnalyticsConstants.DataPoints.UID, Integer.valueOf(i));
        contentValues.put("component", str);
        contentValues.put("message", str2);
        contentValues.put("redactedMessage", ApnSettings.MVNO_NONE);
        contentValues.put("userid", Integer.valueOf(i2));
        try {
            context.getContentResolver().insert(parse, contentValues);
        } catch (IllegalArgumentException e) {
            Log.e("BluetoothUtils", "IllegalArgumentException : " + e);
        }
    }

    public static void makeNotiSound(Context context) {
        try {
            AudioManager audioManager =
                    (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
            Ringtone ringtone =
                    RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(2));
            ringtone.setStreamType(5);
            ringtone.play();
            if (audioManager.getRingerMode() == 1) {
                ((Vibrator) context.getSystemService("vibrator")).vibrate(1000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showConnectingError(
            Context context, String str, LocalBluetoothManager localBluetoothManager) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().visible(context, 0, 869, 0);
        String string = context.getString(R.string.bluetooth_connecting_error_message, str);
        WeakReference weakReference = localBluetoothManager.mForegroundActivity;
        Context context2 = weakReference == null ? null : (Context) weakReference.get();
        if (!localBluetoothManager.isForegroundActivity()) {
            Toast.makeText(context, string, 0).show();
            return;
        }
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context2);
            builder.setTitle(R.string.bluetooth_error_title);
            builder.P.mMessage = string;
            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
            builder.show();
        } catch (Exception e) {
            Log.e("BluetoothUtils", "Cannot show error dialog.", e);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.bluetooth.Utils$2, reason: invalid class name */
    public final class AnonymousClass2 implements LocalBluetoothManager.BluetoothManagerCallback {
        @Override // com.android.settingslib.bluetooth.LocalBluetoothManager.BluetoothManagerCallback
        public final void onBluetoothManagerInitialized() {}
    }
}
