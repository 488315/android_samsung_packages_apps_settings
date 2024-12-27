package com.android.settings;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.Log;

import com.android.settings.wfd.WifiDisplaySettings;

import com.samsung.android.settings.Trace;
import com.samsung.android.settings.knox.KnoxUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class SettingsActivity$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsActivity f$0;

    public /* synthetic */ SettingsActivity$$ExternalSyntheticLambda0(
            SettingsActivity settingsActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        int i = this.$r8$classId;
        SettingsActivity settingsActivity = this.f$0;
        switch (i) {
            case 0:
                settingsActivity.getClass();
                Trace.beginSection("SettingsActivityKnoxUtils.updateRestrictionState");
                KnoxUtils.updateRestrictionState(settingsActivity.getApplicationContext());
                Trace.endSection();
                break;
            default:
                PackageManager packageManager = settingsActivity.getPackageManager();
                boolean isAdminUser = UserManager.get(settingsActivity).isAdminUser();
                String packageName = settingsActivity.getPackageName();
                StringBuilder sb = new StringBuilder();
                boolean z2 =
                        settingsActivity.setTileEnabled(
                                        sb,
                                        new ComponentName(
                                                packageName,
                                                Settings.BluetoothSettingsActivity.class.getName()),
                                        packageManager.hasSystemFeature(
                                                "android.hardware.bluetooth"),
                                        isAdminUser)
                                || settingsActivity.setTileEnabled(
                                        sb,
                                        new ComponentName(
                                                packageName,
                                                Settings.WifiSettingsActivity.class.getName()),
                                        packageManager.hasSystemFeature("android.hardware.wifi"),
                                        isAdminUser);
                ComponentName componentName =
                        new ComponentName(
                                packageName, Settings.DataUsageSummaryActivity.class.getName());
                StringBuilder sb2 = Utils.sBuilder;
                try {
                    z =
                            INetworkManagementService.Stub.asInterface(
                                            ServiceManager.getService("network_management"))
                                    .isBandwidthControlEnabled();
                } catch (RemoteException unused) {
                    z = false;
                }
                boolean z3 =
                        settingsActivity.setTileEnabled(
                                        sb,
                                        new ComponentName(
                                                packageName,
                                                Settings.NfcSettingsActivity.class.getName()),
                                        packageManager.hasSystemFeature("android.hardware.nfc")
                                                || "factory"
                                                        .equalsIgnoreCase(
                                                                SystemProperties.get(
                                                                        "ro.factory.factory_binary",
                                                                        "Unknown")),
                                        isAdminUser)
                                || (settingsActivity.setTileEnabled(
                                                sb,
                                                new ComponentName(
                                                        packageName,
                                                        Settings.WifiDisplaySettingsActivity.class
                                                                .getName()),
                                                WifiDisplaySettings.isAvailable(settingsActivity),
                                                isAdminUser)
                                        || (settingsActivity.setTileEnabled(
                                                        sb, componentName, z, isAdminUser)
                                                || z2));
                if (!settingsActivity.setTileEnabled(
                                sb,
                                new ComponentName(
                                        packageName,
                                        Settings.PaymentSettingsActivity.class.getName()),
                                packageManager.hasSystemFeature("android.hardware.nfc")
                                        && packageManager.hasSystemFeature(
                                                "android.hardware.nfc.hce")
                                        && SystemProperties.getBoolean(
                                                "ro.vendor.nfc.support.othercategory", true)
                                        && NfcAdapter.getDefaultAdapter(settingsActivity) != null,
                                isAdminUser)
                        && !z3) {
                    Log.d(
                            "SettingsActivity",
                            "No enabled state changed, skipping updateCategory call");
                    break;
                } else {
                    Log.d(
                            "SettingsActivity",
                            "Enabled state changed for some tiles, reloading all categories "
                                    + sb.toString());
                    settingsActivity.mCategoryMixin.updateCategories(false);
                    break;
                }
                break;
        }
    }
}
