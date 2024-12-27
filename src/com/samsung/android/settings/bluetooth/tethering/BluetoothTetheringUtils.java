package com.samsung.android.settings.bluetooth.tethering;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothPan;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.bluetooth.RestrictionUtils;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.tether.SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BluetoothTetheringUtils {
    public static final AtomicReference mDataSaverBackend = new AtomicReference(null);
    public static final AtomicReference mRestrictionUtils = new AtomicReference(null);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public final Runnable runnableConnected;
        public final Runnable runnableDisconnected;

        public NetworkCallback(
                Runnable runnable,
                SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0
                        secBluetoothTetherPreferenceController$$ExternalSyntheticLambda0) {
            this.runnableConnected = runnable;
            this.runnableDisconnected =
                    secBluetoothTetherPreferenceController$$ExternalSyntheticLambda0;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            Runnable runnable = this.runnableConnected;
            if (runnable != null) {
                runnable.run();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            Runnable runnable = this.runnableDisconnected;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public static List getConnectedDevices(Context context) {
        if (context == null) {
            return new ArrayList();
        }
        BluetoothPan bluetoothPan =
                LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback)
                        .mProfileManager
                        .mPanProfile
                        .mService;
        return bluetoothPan == null ? Collections.emptyList() : bluetoothPan.getConnectedDevices();
    }

    public static String getPanStateMessage(int i) {
        return i != 0
                ? i != 1
                        ? i != 2 ? i != 3 ? "unknown" : "disconnecting" : "connected"
                        : "connecting"
                : "disconnected";
    }

    public static boolean isBluetoothErrored(Context context) {
        TetheringManager tetheringManager;
        if (context == null
                || (tetheringManager = (TetheringManager) context.getSystemService("tethering"))
                        == null) {
            return false;
        }
        String[] tetheringErroredIfaces = tetheringManager.getTetheringErroredIfaces();
        String[] tetherableBluetoothRegexs = tetheringManager.getTetherableBluetoothRegexs();
        for (String str : tetheringErroredIfaces) {
            for (String str2 : tetherableBluetoothRegexs) {
                if (str.matches(str2)) {
                    Log.w("BluetoothTetheringUtils", "isBluetoothErrored: found");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOperatorBlocked(Context context) {
        if (context == null) {
            return false;
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (!"VZW".equals(Utils.getSalesCode())) {
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if ((telephonyManager == null || telephonyManager.getSimState() == 5) ? false : true) {
            Log.i("BluetoothTetheringUtils", "isOperatorBlocked: SIM is not ready (VZW)");
            return true;
        }
        if (!isWiFiConnected(context)) {
            return false;
        }
        Log.i("BluetoothTetheringUtils", "isOperatorBlocked: WiFi connected (VZW)");
        return true;
    }

    public static boolean isTetheringAllowed(Context context, boolean z) {
        boolean restrictBackground;
        String string;
        int enterprisePolicyEnabled;
        int enterprisePolicyEnabled2;
        ServiceState serviceState;
        if (isOperatorBlocked(context) && !z) {
            Log.i("BluetoothTetheringUtils", "isTetheringAllowed: isOperatorBlocked");
            return false;
        }
        if (context == null) {
            restrictBackground = false;
        } else {
            AtomicReference atomicReference = mDataSaverBackend;
            atomicReference.compareAndSet(null, new DataSaverBackend(context));
            restrictBackground =
                    ((DataSaverBackend) atomicReference.get())
                            .mPolicyManager.getRestrictBackground();
        }
        if (restrictBackground) {
            Log.i("BluetoothTetheringUtils", "isTetheringAllowed: isDataSaverEnabled");
            return false;
        }
        if (!DataUsageUtils.isDataAllowed(context)) {
            Log.i("BluetoothTetheringUtils", "isTetheringAllowed: isDataUsageNotAllowed");
            return false;
        }
        try {
        } catch (Exception e) {
            Log.w("BluetoothTetheringUtils", e.toString());
        }
        if (!SemCarrierFeature.getInstance()
                .getBoolean(0, "CarrierFeature_Common_Support_Satellite", false, false)) {
            if (SemCarrierFeature.getInstance()
                    .getBoolean(1, "CarrierFeature_Common_Support_Satellite", false, false)) {}
            string =
                    Settings.Global.getString(
                            context.getContentResolver(), "satellite_mode_radios");
            if (string == null
                    && string.contains("bluetooth")
                    && Settings.Global.getInt(
                                    context.getContentResolver(), "satellite_mode_enabled", 0)
                            == 1) {
                Log.i("BluetoothTetheringUtils", "isTetheringAllowed: isSatelliteModeOn");
                return false;
            }
            enterprisePolicyEnabled =
                    Utils.getEnterprisePolicyEnabled(
                            context,
                            "content://com.sec.knox.provider/BluetoothPolicy",
                            "isBluetoothEnabled",
                            new String[] {"false"});
            enterprisePolicyEnabled2 =
                    Utils.getEnterprisePolicyEnabled(
                            context,
                            "content://com.sec.knox.provider/RestrictionPolicy1",
                            "isBluetoothTetheringEnabled");
            if (enterprisePolicyEnabled != 0 || enterprisePolicyEnabled2 == 0) {
                Log.w(
                        "BluetoothTetheringUtils",
                        "Bluetooth enabled: "
                                + enterprisePolicyEnabled
                                + ", Tethering enabled: "
                                + enterprisePolicyEnabled2);
                Log.i(
                        "BluetoothTetheringUtils",
                        "isTetheringAllowed: isTetheringBlockedByEnterprisePolicy");
                return false;
            }
            if (isTetheringBlockedByMdm(context)) {
                Log.i("BluetoothTetheringUtils", "isTetheringAllowed: isTetheringBlockedByMdm");
                return false;
            }
            AtomicReference atomicReference2 = mRestrictionUtils;
            atomicReference2.compareAndSet(null, new RestrictionUtils());
            ((RestrictionUtils) atomicReference2.get()).getClass();
            RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            context, UserHandle.myUserId(), "no_bluetooth");
            if (checkIfRestrictionEnforced == null) {
                ((RestrictionUtils) atomicReference2.get()).getClass();
                checkIfRestrictionEnforced =
                        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                context, UserHandle.myUserId(), "no_config_bluetooth");
            }
            if (checkIfRestrictionEnforced == null) {
                return true;
            }
            Log.w("BluetoothTetheringUtils", "Restrict by admin");
            Log.i("BluetoothTetheringUtils", "isTetheringAllowed: maybeEnforceRestrictions");
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null
                && (serviceState = telephonyManager.getServiceState()) != null) {
            if (serviceState.isUsingNonTerrestrialNetwork()) {
                Log.i(
                        "BluetoothTetheringUtils",
                        "isTetheringAllowed: isUsingNonTerrestrialNetwork");
                return false;
            }
        }
        string = Settings.Global.getString(context.getContentResolver(), "satellite_mode_radios");
        if (string == null) {}
        enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/BluetoothPolicy",
                        "isBluetoothEnabled",
                        new String[] {"false"});
        enterprisePolicyEnabled2 =
                Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isBluetoothTetheringEnabled");
        if (enterprisePolicyEnabled != 0) {}
        Log.w(
                "BluetoothTetheringUtils",
                "Bluetooth enabled: "
                        + enterprisePolicyEnabled
                        + ", Tethering enabled: "
                        + enterprisePolicyEnabled2);
        Log.i(
                "BluetoothTetheringUtils",
                "isTetheringAllowed: isTetheringBlockedByEnterprisePolicy");
        return false;
    }

    public static boolean isTetheringBlockedByMdm(Context context) {
        DevicePolicyManager devicePolicyManager;
        if (context == null
                || (devicePolicyManager =
                                (DevicePolicyManager) context.getSystemService("device_policy"))
                        == null) {
            return false;
        }
        boolean semGetAllowInternetSharing = devicePolicyManager.semGetAllowInternetSharing(null);
        int semGetAllowBluetoothMode = devicePolicyManager.semGetAllowBluetoothMode(null);
        if (semGetAllowInternetSharing && semGetAllowBluetoothMode == 2) {
            return false;
        }
        Log.w("BluetoothTetheringUtils", "Bluetooth tethering is restricted by MDM");
        return true;
    }

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connectivityManager;
        NetworkCapabilities networkCapabilities;
        return (context == null
                        || (connectivityManager =
                                        (ConnectivityManager)
                                                context.getSystemService("connectivity"))
                                == null
                        || (networkCapabilities =
                                        connectivityManager.getNetworkCapabilities(
                                                connectivityManager.getActiveNetwork()))
                                == null
                        || !networkCapabilities.hasTransport(1))
                ? false
                : true;
    }

    public static NetworkCallback registerNetworkCallback(
            Context context,
            Runnable runnable,
            SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0
                    secBluetoothTetherPreferenceController$$ExternalSyntheticLambda0) {
        ConnectivityManager connectivityManager;
        if (context == null
                || (connectivityManager =
                                (ConnectivityManager) context.getSystemService("connectivity"))
                        == null) {
            return null;
        }
        NetworkCallback networkCallback =
                new NetworkCallback(
                        runnable, secBluetoothTetherPreferenceController$$ExternalSyntheticLambda0);
        connectivityManager.registerNetworkCallback(
                new NetworkRequest.Builder().addTransportType(1).build(),
                networkCallback,
                new Handler(Looper.getMainLooper()));
        return networkCallback;
    }

    public static void startProvisioning(Activity activity, Context context) {
        Log.i("BluetoothTetheringUtils", "startProvisioning");
        if (activity == null || context == null) {
            return;
        }
        String[] stringArray = context.getResources().getStringArray(17236257);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(stringArray[0], stringArray[1]);
        intent.putExtra("TETHER_TYPE", 2);
        try {
            activity.startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException unused) {
            Log.w("BluetoothTetheringUtils", "startProvisioning: Activity not found");
        }
    }
}
