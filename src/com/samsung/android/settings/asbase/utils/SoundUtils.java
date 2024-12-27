package com.samsung.android.settings.asbase.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SoundUtils extends Utils {
    public static boolean checkDialer(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
        String str = ApnSettings.MVNO_NONE;
        String systemDialerPackage =
                telecomManager != null
                        ? telecomManager.getSystemDialerPackage()
                        : ApnSettings.MVNO_NONE;
        if (telecomManager != null) {
            str = telecomManager.getDefaultDialerPackage();
        }
        Intent intent = new Intent("android.telecom.InCallService");
        if (str != null) {
            intent.setPackage(str);
        }
        boolean z =
                context.getPackageManager().queryIntentServices(intent, 128) != null
                        ? !r7.isEmpty()
                        : false;
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "checkDialer() / system = ",
                        systemDialerPackage,
                        " , default = ",
                        str,
                        ", existCallSvc = ");
        m.append(z);
        Log.d("SoundUtils", m.toString());
        return TextUtils.equals(systemDialerPackage, str) || !z;
    }

    public static int checkRingtoneVolumeType(Context context, String str, String str2) {
        String string = context.getString(R.string.sec_current_theme_font);
        if (TextUtils.isEmpty(str2) || !TextUtils.equals(str2, "internal")) {
            return 1;
        }
        return !TextUtils.equals(str, string) ? 0 : -1;
    }

    public static boolean isBluetoothLeBroadcastEnabled(Context context) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        LocalBluetoothManager localBluetoothManager =
                com.android.settings.bluetooth.Utils.getLocalBluetoothManager(context);
        if (localBluetoothManager == null
                || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null
                || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast)
                        == null) {
            return false;
        }
        return localBluetoothLeBroadcast.isEnabled(null);
    }

    public static boolean isCallBGEnabled(Context context) {
        List<ComponentName> activeAdmins;
        try {
            if (!context.getPackageManager()
                            .getApplicationInfo("com.samsung.android.callbgprovider", 0)
                            .enabled
                    || Rune.isSamsungDexMode(context)) {
                return false;
            }
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) context.getSystemService("device_policy");
            if (devicePolicyManager != null
                    && (activeAdmins = devicePolicyManager.getActiveAdmins()) != null) {
                Iterator<ComponentName> it = activeAdmins.iterator();
                while (it.hasNext()) {
                    if (devicePolicyManager.isDeviceOwnerApp(it.next().getPackageName())) {
                        Log.d("WorkUtils", "isKnoxDoModeImpl true");
                        return false;
                    }
                }
            }
            Log.d("WorkUtils", "isKnoxDoModeImpl false");
            return UserHandle.myUserId() == 0;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("SoundUtils", "isCallBGEnabled:: NameNotFoundException");
            return false;
        }
    }

    public static boolean isInstalledClockApp(Context context) {
        long longVersionCode;
        try {
            longVersionCode =
                    context.getPackageManager()
                            .getPackageInfo("com.sec.android.app.clockpackage", 0)
                            .getLongVersionCode();
            Log.d("SoundUtils", "isInstalledClockApp versionCode: " + longVersionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("SoundUtils", "NameNotFoundException");
        }
        return longVersionCode >= 1240203000;
    }

    public static boolean isMuteAllSoundEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "all_sound_off", 0) != 0;
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null
                || (networkCapabilities =
                                connectivityManager.getNetworkCapabilities(
                                        connectivityManager.getActiveNetwork()))
                        == null) {
            return false;
        }
        return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0);
    }

    public static boolean isRTL(Context context, String str) {
        if (com.android.settings.Utils.isRTL(context)) {
            return true;
        }
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            byte directionality = Character.getDirectionality(str.charAt(i));
            if (directionality == 1
                    || directionality == 2
                    || directionality == 16
                    || directionality == 17) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRingtoneMenuSupported(Context context) {
        return !Rune.isLDUModel()
                && (com.android.settings.Utils.isVoiceCapable(context)
                        || Rune.supportSoftphone()
                        || com.android.settings.Utils.isCMCAvailable(context));
    }

    public static boolean isUseGlobalAlarmVolume(Context context) {
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    Uri.parse(
                                            "content://com.samsung.sec.android.clockpackage/alarm"),
                                    "isSameVolumeForAllAlarms",
                                    (String) null,
                                    (Bundle) null);
            if (call != null) {
                return call.getBoolean("isSameVolumeForAllAlarms", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isZenModeEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "zen_mode", 0) != 0;
    }

    public static void showToEnableSecSoundPickerDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.sec_need_to_enable_secsoundpicker_msg);
        builder.setPositiveButton(
                R.string.settings_label,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.asbase.utils.SoundUtils.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent =
                                    new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:com.samsung.android.secsoundpicker"));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException unused) {
                            context.startActivity(
                                    new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS"));
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.P.mCancelable = true;
        builder.show();
    }
}
