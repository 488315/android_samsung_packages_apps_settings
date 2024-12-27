package com.android.settingslib;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.media.AudioManager;
import android.net.TetheringManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.Flags;
import com.android.internal.util.UserIcons;
import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.settingslib.fuelgauge.BatteryUtils;

import com.sec.ims.IMSParameter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class Utils {

    @VisibleForTesting
    static final String STORAGE_MANAGER_ENABLED_PROPERTY = "ro.storage_manager.enabled";

    public static final int[] WIFI_PIE = {
        R.drawable.sec_ic_wifi_details_signal_0,
        R.drawable.sec_ic_wifi_details_signal_1,
        R.drawable.sec_ic_wifi_details_signal_2,
        R.drawable.sec_ic_wifi_details_signal_3,
        R.drawable.sec_ic_wifi_details_signal_4
    };
    public static String mCountryCode;
    public static String sDefaultWebViewPackageName;
    public static String sPermissionControllerPackageName;
    public static String sServicesSystemSharedLibPackageName;
    public static String sSharedSystemSharedLibPackageName;
    public static Signature[] sSystemSignature;

    public static boolean containsIncompatibleChargers(Context context, String str) {
        List<UsbPort> ports;
        UsbPortStatus status;
        int[] complianceWarnings;
        try {
            if (Settings.Secure.getInt(
                            context.getContentResolver(),
                            "incompatible_charger_warning_disabled",
                            0)
                    == 1) {
                Log.d(str, "containsIncompatibleChargers: disabled");
                return false;
            }
            UsbManager usbManager = (UsbManager) context.getSystemService(UsbManager.class);
            if (usbManager != null && (ports = usbManager.getPorts()) != null && !ports.isEmpty()) {
                for (UsbPort usbPort : ports) {
                    Log.d(str, "usbPort: " + usbPort);
                    if (usbPort.supportsComplianceWarnings()
                            && (status = usbPort.getStatus()) != null
                            && status.isConnected()
                            && (complianceWarnings = status.getComplianceWarnings()) != null
                            && complianceWarnings.length != 0) {
                        for (int i : complianceWarnings) {
                            if (Flags.enableUsbDataComplianceWarning()
                                    && Flags.enableInputPowerLimitedWarning()) {
                                if (i == 2 || i == 5) {
                                    return true;
                                }
                            } else if (i == 1 || i == 2) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(str, "containsIncompatibleChargers()", e);
            return false;
        }
    }

    public static String formatPercentage(double d, boolean z) {
        return formatPercentage(z ? Math.round((float) d) : (int) d);
    }

    public static ColorFilter getAlphaInvariantColorFilterForColor(int i) {
        return new ColorMatrixColorFilter(
                new ColorMatrix(
                        new float[] {
                            0.0f,
                            0.0f,
                            0.0f,
                            0.0f,
                            Color.red(i),
                            0.0f,
                            0.0f,
                            0.0f,
                            0.0f,
                            Color.green(i),
                            0.0f,
                            0.0f,
                            0.0f,
                            0.0f,
                            Color.blue(i),
                            0.0f,
                            0.0f,
                            0.0f,
                            1.0f,
                            0.0f
                        }));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.launcher3.icons.FastBitmapDrawable getBadgedIcon(
            android.content.Context r7, android.content.pm.ApplicationInfo r8) {
        /*
            android.content.pm.PackageManager r0 = r7.getPackageManager()
            android.graphics.drawable.Drawable r0 = r8.loadUnbadgedIcon(r0)
            int r8 = r8.uid
            android.os.UserHandle r8 = android.os.UserHandle.getUserHandleForUid(r8)
            r1 = 0
            java.lang.Class<android.os.UserManager> r2 = android.os.UserManager.class
            java.lang.Object r2 = r7.getSystemService(r2)     // Catch: java.lang.Exception -> L39
            android.os.UserManager r2 = (android.os.UserManager) r2     // Catch: java.lang.Exception -> L39
            int r3 = r8.getIdentifier()     // Catch: java.lang.Exception -> L39
            android.content.pm.UserInfo r2 = r2.getUserInfo(r3)     // Catch: java.lang.Exception -> L39
            if (r2 == 0) goto L39
            boolean r3 = r2.isCloneProfile()     // Catch: java.lang.Exception -> L39
            if (r3 == 0) goto L29
            r2 = 2
            goto L3a
        L29:
            boolean r3 = r2.isManagedProfile()     // Catch: java.lang.Exception -> L39
            if (r3 == 0) goto L31
            r2 = 1
            goto L3a
        L31:
            boolean r2 = r2.isPrivateProfile()     // Catch: java.lang.Exception -> L39
            if (r2 == 0) goto L39
            r2 = 3
            goto L3a
        L39:
            r2 = r1
        L3a:
            java.lang.Object r3 = com.android.launcher3.icons.IconFactory.sPoolSync
            monitor-enter(r3)
            com.android.launcher3.icons.IconFactory r4 = com.android.launcher3.icons.IconFactory.sPool     // Catch: java.lang.Throwable -> L4a
            if (r4 == 0) goto L4c
            com.android.launcher3.icons.IconFactory r5 = r4.next     // Catch: java.lang.Throwable -> L4a
            com.android.launcher3.icons.IconFactory.sPool = r5     // Catch: java.lang.Throwable -> L4a
            r5 = 0
            r4.next = r5     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4a
            goto L67
        L4a:
            r7 = move-exception
            goto L89
        L4c:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4a
            com.android.launcher3.icons.IconFactory r4 = new com.android.launcher3.icons.IconFactory
            android.content.res.Resources r3 = r7.getResources()
            android.content.res.Configuration r3 = r3.getConfiguration()
            int r3 = r3.densityDpi
            android.content.res.Resources r5 = r7.getResources()
            r6 = 2131165957(0x7f070305, float:1.7946146E38)
            int r5 = r5.getDimensionPixelSize(r6)
            r4.<init>(r7, r3, r5)
        L67:
            com.android.launcher3.icons.BaseIconFactory$IconOptions r3 = new com.android.launcher3.icons.BaseIconFactory$IconOptions     // Catch: java.lang.Throwable -> L7f
            r3.<init>()     // Catch: java.lang.Throwable -> L7f
            com.android.launcher3.util.UserIconInfo r5 = new com.android.launcher3.util.UserIconInfo     // Catch: java.lang.Throwable -> L7f
            r5.<init>(r8, r2)     // Catch: java.lang.Throwable -> L7f
            r3.mUserIconInfo = r5     // Catch: java.lang.Throwable -> L7f
            com.android.launcher3.icons.BitmapInfo r8 = r4.createBadgedIconBitmap(r0, r3)     // Catch: java.lang.Throwable -> L7f
            com.android.launcher3.icons.FastBitmapDrawable r7 = r8.newIcon(r7, r1)     // Catch: java.lang.Throwable -> L7f
            r4.close()
            return r7
        L7f:
            r7 = move-exception
            r4.close()     // Catch: java.lang.Throwable -> L84
            goto L88
        L84:
            r8 = move-exception
            r7.addSuppressed(r8)
        L88:
            throw r7
        L89:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4a
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.Utils.getBadgedIcon(android.content.Context,"
                    + " android.content.pm.ApplicationInfo):com.android.launcher3.icons.FastBitmapDrawable");
    }

    public static int getBatteryLevel(Intent intent) {
        return (intent.getIntExtra("level", 0) * 100) / intent.getIntExtra("scale", 100);
    }

    public static String getBatteryStatus(Context context, Intent intent, boolean z) {
        int i;
        int i2;
        int intExtra = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        Resources resources = context.getResources();
        String string = resources.getString(R.string.battery_info_status_unknown);
        Optional.empty();
        int intExtra2 = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        int intExtra3 = intent.getIntExtra("plugged", 0);
        int batteryLevel = BatteryStatus.getBatteryLevel(intent);
        intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1);
        intent.getBooleanExtra("present", true);
        int intExtra4 = intent.getIntExtra("max_charging_current", -1);
        int intExtra5 = intent.getIntExtra("max_charging_voltage", -1);
        if (intExtra5 <= 0) {
            intExtra5 = 5000000;
        }
        if (intExtra4 > 0) {
            i = intExtra;
            i2 = (int) Math.round(intExtra4 * 0.001d * intExtra5 * 0.001d);
        } else {
            i = intExtra;
            i2 = -1;
        }
        if (intExtra2 == 5 || batteryLevel >= 100) {
            return resources.getString(
                    z
                            ? R.string.battery_info_status_full_charged
                            : R.string.battery_info_status_full);
        }
        int i3 = i;
        if (i3 != 2) {
            return i3 == 3
                    ? resources.getString(R.string.battery_info_status_discharging)
                    : i3 == 4
                            ? resources.getString(R.string.battery_info_status_not_charging)
                            : string;
        }
        int i4 = R.string.battery_info_status_charging;
        int i5 = R.string.battery_info_status_charging_v2;
        if (z) {
            if (BatteryUtils.isChargingStringV2Enabled()) {
                i4 = R.string.battery_info_status_charging_v2;
            }
            return resources.getString(i4);
        }
        char c = 1;
        if (intExtra3 != 1 && intExtra3 != 2) {
            if (intExtra3 == 8) {
                if (!BatteryUtils.isChargingStringV2Enabled()) {
                    i5 = R.string.battery_info_status_charging_dock;
                }
                return resources.getString(i5);
            }
            if (!BatteryUtils.isChargingStringV2Enabled()) {
                i5 = R.string.battery_info_status_charging_wireless;
            }
            return resources.getString(i5);
        }
        int integer = context.getResources().getInteger(R.integer.config_chargingSlowlyThreshold);
        int integer2 =
                context.getResources()
                        .getInteger(
                                BatteryUtils.isChargingStringV2Enabled()
                                        ? R.integer.config_chargingFastThreshold_v2
                                        : R.integer.config_chargingFastThreshold);
        if (i2 <= 0) {
            c = 65535;
        } else if (i2 < integer) {
            c = 0;
        } else if (i2 > integer2) {
            c = 2;
        }
        if (c == 0) {
            if (!BatteryUtils.isChargingStringV2Enabled()) {
                i5 = R.string.battery_info_status_charging_slow;
            }
            return resources.getString(i5);
        }
        if (c == 2) {
            return resources.getString(
                    BatteryUtils.isChargingStringV2Enabled()
                            ? R.string.battery_info_status_charging_fast_v2
                            : R.string.battery_info_status_charging_fast);
        }
        if (BatteryUtils.isChargingStringV2Enabled()) {
            i4 = R.string.battery_info_status_charging_v2;
        }
        return resources.getString(i4);
    }

    public static ColorStateList getColorAttr(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[] {i});
        try {
            return obtainStyledAttributes.getColorStateList(0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static int getColorAttrDefaultColor(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[] {i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static int getCombinedServiceState(ServiceState serviceState) {
        if (serviceState == null) {
            return 1;
        }
        int voiceRegState = serviceState.getVoiceRegState();
        if (voiceRegState == 1 || voiceRegState == 2) {
            NetworkRegistrationInfo networkRegistrationInfo =
                    serviceState.getNetworkRegistrationInfo(2, 1);
            if (networkRegistrationInfo == null ? false : networkRegistrationInfo.isInService()) {
                return 0;
            }
        }
        return voiceRegState;
    }

    public static int getDisabled(Context context, int i) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {android.R.attr.disabledAlpha});
        float f = obtainStyledAttributes.getFloat(0, 0.0f);
        obtainStyledAttributes.recycle();
        return Color.argb((int) (f * Color.alpha(i)), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static int getTetheringLabel(TetheringManager tetheringManager) {
        String[] tetherableUsbRegexs = tetheringManager.getTetherableUsbRegexs();
        String[] tetherableWifiRegexs = tetheringManager.getTetherableWifiRegexs();
        String[] tetherableBluetoothRegexs = tetheringManager.getTetherableBluetoothRegexs();
        boolean z = tetherableUsbRegexs.length != 0;
        boolean z2 = tetherableWifiRegexs.length != 0;
        boolean z3 = tetherableBluetoothRegexs.length != 0;
        return (z2 && z && z3)
                ? R.string.tether_settings_title_all
                : (z2 && z)
                        ? R.string.tether_settings_title_all
                        : (z2 && z3)
                                ? R.string.tether_settings_title_all
                                : z2
                                        ? R.string.tether_settings_title_wifi
                                        : (z && z3)
                                                ? R.string.tether_settings_title_usb_bluetooth
                                                : z
                                                        ? R.string.tether_settings_title_usb
                                                        : R.string.tether_settings_title_bluetooth;
    }

    public static Drawable getUserIcon(
            final Context context, UserManager userManager, UserInfo userInfo) {
        Bitmap userIcon;
        int i = UserIconDrawable.$r8$clinit;
        int dimensionPixelSize =
                context.getResources().getDimensionPixelSize(R.dimen.user_icon_size);
        if (userInfo.isManagedProfile() && !userInfo.isDualAppProfile()) {
            Drawable drawableForDensity =
                    ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class))
                            .getResources()
                            .getDrawableForDensity(
                                    "WORK_PROFILE_USER_ICON",
                                    "SOLID_COLORED",
                                    context.getResources().getDisplayMetrics().densityDpi,
                                    new Supplier() { // from class:
                                                     // com.android.settingslib.drawable.UserIconDrawable$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            Context context2 = context;
                                            return context2.getResources()
                                                    .getDrawableForDensity(
                                                            android.R.drawable.ic_feedback,
                                                            context2.getResources()
                                                                    .getDisplayMetrics()
                                                                    .densityDpi,
                                                            context2.getTheme());
                                        }
                                    });
            drawableForDensity.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            return drawableForDensity;
        }
        if (userInfo.isDualAppProfile()) {
            try {
                ApplicationInfo applicationInfo =
                        AppGlobals.getPackageManager()
                                .getApplicationInfo("com.samsung.android.da.daagent", 0L, 0);
                if (applicationInfo != null) {
                    return applicationInfo.loadUnbadgedIcon(context.getPackageManager());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                UserIconDrawable userIconDrawable = new UserIconDrawable(dimensionPixelSize);
                Drawable defaultUserIcon =
                        UserIcons.getDefaultUserIcon(context.getResources(), userInfo.id, false);
                Drawable drawable = userIconDrawable.mUserDrawable;
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                userIconDrawable.mUserIcon = null;
                userIconDrawable.mUserDrawable = defaultUserIcon;
                if (defaultUserIcon == null) {
                    userIconDrawable.mBitmap = null;
                } else {
                    defaultUserIcon.setCallback(userIconDrawable);
                }
                userIconDrawable.onBoundsChange(userIconDrawable.getBounds());
                userIconDrawable.bake();
                return userIconDrawable;
            }
        }
        if (userInfo.iconPath != null
                && (userIcon = userManager.getUserIcon(userInfo.id)) != null) {
            UserIconDrawable userIconDrawable2 = new UserIconDrawable(dimensionPixelSize);
            userIconDrawable2.setIcon(userIcon);
            userIconDrawable2.bake();
            return userIconDrawable2;
        }
        Drawable defaultUserIcon2 =
                UserIcons.getDefaultUserIcon(context.getResources(), userInfo.id, false);
        UserIconDrawable userIconDrawable3 = new UserIconDrawable(dimensionPixelSize);
        userIconDrawable3.setIcon(UserIcons.convertToBitmap(defaultUserIcon2));
        userIconDrawable3.bake();
        return userIconDrawable3;
    }

    public static String getUserLabel(final Context context, UserInfo userInfo) {
        String str = userInfo.name;
        if (userInfo.isDualAppProfile()) {
            try {
                ApplicationInfo applicationInfo =
                        AppGlobals.getPackageManager()
                                .getApplicationInfo("com.samsung.android.da.daagent", 0L, 0);
                if (applicationInfo != null) {
                    return (String)
                            context.getPackageManager().getApplicationLabel(applicationInfo);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                return context.getString(R.string.unknown);
            }
        } else {
            if (userInfo.isManagedProfile()) {
                return ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                "Settings.WORK_PROFILE_USER_LABEL",
                                new Supplier() { // from class:
                                                 // com.android.settingslib.Utils$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return context.getString(R.string.managed_user_title);
                                    }
                                });
            }
            if (userInfo.isGuest()) {
                str = context.getString(android.R.string.mime_type_spreadsheet_ext);
            }
        }
        if (str == null) {
            str = Integer.toString(userInfo.id);
        }
        return context.getResources().getString(R.string.running_process_item_user_label, str);
    }

    public static int getWifiIconResource(int i) {
        if (i < 0 || i >= 5) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "No Wifi icon found for level: "));
        }
        return WIFI_PIE[i];
    }

    public static boolean isAudioModeOngoingCall(Context context) {
        int mode = ((AudioManager) context.getSystemService(AudioManager.class)).getMode();
        return mode == 1 || mode == 2 || mode == 3;
    }

    public static boolean isDeviceProvisioningPackage(Resources resources, String str) {
        String string = resources.getString(android.R.string.display_manager_built_in_display_name);
        return string != null && string.equals(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007a, code lost:

       if (r5.equals(r4) == false) goto L40;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isEssentialPackage(
            android.content.res.Resources r3,
            android.content.pm.PackageManager r4,
            java.lang.String r5) {
        /*
            java.lang.String r0 = com.android.settingslib.Utils.sPermissionControllerPackageName
            if (r0 != 0) goto La
            java.lang.String r0 = r4.getPermissionControllerPackageName()
            com.android.settingslib.Utils.sPermissionControllerPackageName = r0
        La:
            java.lang.String r0 = com.android.settingslib.Utils.sServicesSystemSharedLibPackageName
            if (r0 != 0) goto L14
            java.lang.String r0 = r4.getServicesSystemSharedLibraryPackageName()
            com.android.settingslib.Utils.sServicesSystemSharedLibPackageName = r0
        L14:
            java.lang.String r0 = com.android.settingslib.Utils.sSharedSystemSharedLibPackageName
            if (r0 != 0) goto L1e
            java.lang.String r4 = r4.getSharedSystemSharedLibraryPackageName()
            com.android.settingslib.Utils.sSharedSystemSharedLibPackageName = r4
        L1e:
            java.lang.String r4 = com.android.settingslib.Utils.sPermissionControllerPackageName
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L85
            java.lang.String r4 = com.android.settingslib.Utils.sServicesSystemSharedLibPackageName
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L85
            java.lang.String r4 = com.android.settingslib.Utils.sSharedSystemSharedLibPackageName
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L85
            java.lang.String r4 = "com.android.printspooler"
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L85
            boolean r4 = android.webkit.Flags.updateServiceV2()
            if (r4 == 0) goto L7c
            java.lang.String r4 = com.android.settingslib.Utils.sDefaultWebViewPackageName
            if (r4 == 0) goto L49
            goto L76
        L49:
            boolean r4 = android.webkit.Flags.updateServiceIpcWrapper()
            r0 = 0
            if (r4 == 0) goto L5b
            android.webkit.WebViewUpdateManager r4 = android.webkit.WebViewUpdateManager.getInstance()
            if (r4 == 0) goto L6e
            android.webkit.WebViewProviderInfo r0 = r4.getDefaultWebViewPackage()
            goto L6e
        L5b:
            android.webkit.IWebViewUpdateService r4 = android.webkit.WebViewFactory.getUpdateService()     // Catch: android.os.RemoteException -> L66
            if (r4 == 0) goto L6e
            android.webkit.WebViewProviderInfo r0 = r4.getDefaultWebViewPackage()     // Catch: android.os.RemoteException -> L66
            goto L6e
        L66:
            r4 = move-exception
            java.lang.String r1 = "Utils"
            java.lang.String r2 = "RemoteException when trying to fetch default WebView package Name"
            android.util.Log.e(r1, r2, r4)
        L6e:
            if (r0 == 0) goto L74
            java.lang.String r4 = r0.packageName
            com.android.settingslib.Utils.sDefaultWebViewPackageName = r4
        L74:
            java.lang.String r4 = com.android.settingslib.Utils.sDefaultWebViewPackageName
        L76:
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L85
        L7c:
            boolean r3 = isDeviceProvisioningPackage(r3, r5)
            if (r3 == 0) goto L83
            goto L85
        L83:
            r3 = 0
            goto L86
        L85:
            r3 = 1
        L86:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.Utils.isEssentialPackage(android.content.res.Resources,"
                    + " android.content.pm.PackageManager, java.lang.String):boolean");
    }

    public static boolean isInService(ServiceState serviceState) {
        int combinedServiceState;
        return (serviceState == null
                        || (combinedServiceState = getCombinedServiceState(serviceState)) == 3
                        || combinedServiceState == 1
                        || combinedServiceState == 2)
                ? false
                : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isStorageManagerEnabled(Context context) {
        int i;
        try {
            i = SystemProperties.getBoolean(STORAGE_MANAGER_ENABLED_PROPERTY, false);
        } catch (Resources.NotFoundException unused) {
            i = 0;
        }
        return Settings.Secure.getInt(
                        context.getContentResolver(), "automatic_storage_manager_enabled", i)
                != 0;
    }

    public static boolean isSystemPackage(
            Resources resources, PackageManager packageManager, PackageInfo packageInfo) {
        Signature[] signatureArr;
        Signature signature;
        PackageInfo packageInfo2;
        Signature[] signatureArr2;
        Signature signature2 = null;
        if (sSystemSignature == null) {
            Signature[] signatureArr3 = new Signature[1];
            try {
                packageInfo2 =
                        packageManager.getPackageInfo(
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 64);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (packageInfo2 != null
                    && (signatureArr2 = packageInfo2.signatures) != null
                    && signatureArr2.length > 0) {
                signature = signatureArr2[0];
                signatureArr3[0] = signature;
                sSystemSignature = signatureArr3;
            }
            signature = null;
            signatureArr3[0] = signature;
            sSystemSignature = signatureArr3;
        }
        Signature signature3 = sSystemSignature[0];
        if (signature3 != null) {
            if (packageInfo != null
                    && (signatureArr = packageInfo.signatures) != null
                    && signatureArr.length > 0) {
                signature2 = signatureArr[0];
            }
            if (signature3.equals(signature2)) {
                return true;
            }
        }
        return isEssentialPackage(resources, packageManager, packageInfo.packageName);
    }

    public static boolean isVisibleExternalCode(Context context) {
        return context != null
                && Settings.System.getInt(context.getContentResolver(), "show_external_code", 0)
                        == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003a, code lost:

       if ("APP".equals(r0) != false) goto L11;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isVoiceCapable(android.content.Context r4) {
        /*
            java.lang.String r0 = "phone"
            java.lang.Object r4 = r4.getSystemService(r0)
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4
            java.lang.String r0 = "ro.build.characteristics"
            java.lang.String r1 = ""
            java.lang.String r0 = android.os.SystemProperties.get(r0, r1)
            java.lang.String r1 = "tablet"
            boolean r0 = r0.contains(r1)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L3e
            java.lang.String r0 = "persist.omc.sales_code"
            java.lang.String r0 = android.os.SystemProperties.get(r0)     // Catch: java.lang.Exception -> L3e
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Exception -> L3e
            if (r3 == 0) goto L2c
            java.lang.String r0 = "ro.csc.sales_code"
            java.lang.String r0 = android.os.SystemProperties.get(r0)     // Catch: java.lang.Exception -> L3e
        L2c:
            java.lang.String r3 = "ATT"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Exception -> L3e
            if (r3 != 0) goto L3c
            java.lang.String r3 = "APP"
            boolean r0 = r3.equals(r0)     // Catch: java.lang.Exception -> L3e
            if (r0 == 0) goto L3e
        L3c:
            r0 = r2
            goto L3f
        L3e:
            r0 = r1
        L3f:
            if (r4 == 0) goto L4a
            boolean r4 = r4.isVoiceCapable()
            if (r4 != 0) goto L49
            if (r0 == 0) goto L4a
        L49:
            r1 = r2
        L4a:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.Utils.isVoiceCapable(android.content.Context):boolean");
    }

    public static boolean isWifiOnly(Context context) {
        return !((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .isDataCapable();
    }

    public static Drawable secGetUserIcon(
            Context context, UserManager userManager, UserInfo userInfo) {
        Bitmap userIcon;
        if (userInfo.iconPath == null
                || (userIcon = userManager.getUserIcon(userInfo.id)) == null) {
            return UserIcons.getDefaultUserIcon(
                    context.getResources(), userInfo.isGuest() ? -10000 : userInfo.id, false);
        }
        return new CircleFramedDrawable(
                userIcon,
                context.getResources()
                        .getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
    }

    public static String formatPercentage(int i) {
        return NumberFormat.getPercentInstance().format(i / 100.0d);
    }
}
