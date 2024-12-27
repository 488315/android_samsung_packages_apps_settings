package com.android.settings.network.telephony;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.biometrics.BiometricPrompt;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.telecom.PhoneAccountHandle;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsManager;
import android.telephony.ims.ImsRcsManager;
import android.telephony.ims.ProvisioningManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.MoreExecutors;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MobileNetworkUtils {
    public static final Drawable EMPTY_DRAWABLE = new ColorDrawable(0);

    public static boolean activeNetworkIsCellular(Context context) {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null
                || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork))
                        == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    public static Intent buildConfigureIntent(
            Context context, PhoneAccountHandle phoneAccountHandle, String str) {
        if (phoneAccountHandle == null
                || phoneAccountHandle.getComponentName() == null
                || TextUtils.isEmpty(phoneAccountHandle.getComponentName().getPackageName())) {
            return null;
        }
        Intent intent = new Intent(str);
        intent.setPackage(phoneAccountHandle.getComponentName().getPackageName());
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", phoneAccountHandle);
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return null;
        }
        return intent;
    }

    public static int[] getActiveSubscriptionIdList(Context context) {
        List activeSubscriptions =
                SubscriptionUtil.getActiveSubscriptions(
                        ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                                .createForAllUserProfiles());
        int i = 0;
        if (activeSubscriptions == null || activeSubscriptions.isEmpty()) {
            return new int[0];
        }
        int[] iArr = new int[activeSubscriptions.size()];
        Iterator it = activeSubscriptions.iterator();
        while (it.hasNext()) {
            iArr[i] = ((SubscriptionInfo) it.next()).getSubscriptionId();
            i++;
        }
        return iArr;
    }

    public static ImsRcsManager getImsRcsManager(ImsManager imsManager, int i) {
        if (imsManager == null) {
            return null;
        }
        try {
            return imsManager.getImsRcsManager(i);
        } catch (Exception e) {
            Log.w("MobileNetworkUtils", "Could not resolve ImsRcsManager: " + e.getMessage());
            return null;
        }
    }

    public static int getNetworkTypeFromRaf(int i) {
        if ((i & 32771) > 0) {
            i |= 32771;
        }
        if ((i & 17284) > 0) {
            i |= 17284;
        }
        if ((i & 72) > 0) {
            i |= 72;
        }
        if ((i & 10288) > 0) {
            i |= 10288;
        }
        if ((i & 266240) > 0) {
            i |= 266240;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) > 0) {
            i |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        switch (i) {
            case 72:
                return 5;
            case 10288:
                return 6;
            case 10360:
                return 4;
            case 17284:
                return 2;
            case 32771:
                return 1;
            case 50055:
                return 0;
            case 60415:
                return 7;
            case 65536:
                return 13;
            case 82820:
                return 14;
            case 98307:
                return 16;
            case 115591:
                return 18;
            case 125951:
                return 21;
            case 266240:
                return 11;
            case 276600:
                return 8;
            case 283524:
                return 12;
            case 316295:
                return 9;
            case 326655:
                return 10;
            case 331776:
                return 15;
            case 349060:
                return 19;
            case 364547:
                return 17;
            case 381831:
                return 20;
            case 392191:
                return 22;
            case NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME /* 524288 */:
                return 23;
            case 790528:
                return 24;
            case 800888:
                return 25;
            case 807812:
                return 28;
            case 840583:
                return 26;
            case 850943:
                return 27;
            case 856064:
                return 29;
            case 873348:
                return 31;
            case 888835:
                return 30;
            case 906119:
                return 32;
            case 916479:
                return 33;
            default:
                return -1;
        }
    }

    public static CharSequence getPreferredStatus(
            boolean z, Context context, boolean z2, List list) {
        String string;
        if (list == null || list.isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SubscriptionInfoEntity subscriptionInfoEntity = (SubscriptionInfoEntity) it.next();
            int size = list.size();
            String str = subscriptionInfoEntity.uniqueName;
            if (size == 1 && subscriptionInfoEntity.isValidSubscription) {
                return str;
            }
            if (z2) {
                if (subscriptionInfoEntity.getSubId()
                        == SubscriptionManager.getDefaultVoiceSubscriptionId()) {
                    string = context.getResources().getString(R.string.calls_sms_preferred);
                }
                string = ApnSettings.MVNO_NONE;
            } else {
                if (subscriptionInfoEntity.getSubId()
                        == SubscriptionManager.getDefaultSmsSubscriptionId()) {
                    string = context.getResources().getString(R.string.calls_sms_preferred);
                }
                string = ApnSettings.MVNO_NONE;
            }
            if (string.toString().isEmpty()) {
                sb.append((CharSequence) str);
            } else {
                sb.append((CharSequence) str);
                sb.append(" (");
                sb.append((CharSequence) string);
                sb.append(")");
            }
            if (subscriptionInfoEntity != PrioritySet$$ExternalSyntheticOutline0.m(1, list)) {
                sb.append(", ");
            }
            if (z) {
                sb.insert(0, "\u200f").insert(sb.length(), "\u200f");
            }
        }
        return sb;
    }

    public static long getRafFromNetworkType(int i) {
        switch (i) {
            case 0:
                return 50055L;
            case 1:
                return 32771L;
            case 2:
                return 17284L;
            case 3:
                return 50055L;
            case 4:
                return 10360L;
            case 5:
                return 72L;
            case 6:
                return 10288L;
            case 7:
                return 60415L;
            case 8:
                return 276600L;
            case 9:
                return 316295L;
            case 10:
                return 326655L;
            case 11:
                return 266240L;
            case 12:
                return 283524L;
            case 13:
                return 65536L;
            case 14:
                return 82820L;
            case 15:
                return 331776L;
            case 16:
                return 98307L;
            case 17:
                return 364547L;
            case 18:
                return 115591L;
            case 19:
                return 349060L;
            case 20:
                return 381831L;
            case 21:
                return 125951L;
            case 22:
                return 392191L;
            case 23:
                return 524288L;
            case 24:
                return 790528L;
            case 25:
                return 800888L;
            case 26:
                return 840583L;
            case 27:
                return 850943L;
            case 28:
                return 807812L;
            case 29:
                return 856064L;
            case 30:
                return 888835L;
            case 31:
                return 873348L;
            case 32:
                return 906119L;
            case 33:
                return 916479L;
            default:
                return 0L;
        }
    }

    public static Drawable getSignalStrengthIcon(
            Context context, int i, int i2, int i3, boolean z, boolean z2) {
        int i4;
        SignalDrawable signalDrawable = new SignalDrawable(context);
        if (z2) {
            i4 = (i2 << 8) | 196608;
        } else {
            i4 = i | (i2 << 8) | ((z ? 2 : 0) << 16);
        }
        signalDrawable.setLevel(i4);
        Drawable drawable =
                i3 == 0
                        ? EMPTY_DRAWABLE
                        : context.getResources().getDrawable(i3, context.getTheme());
        int dimensionPixelSize =
                context.getResources().getDimensionPixelSize(R.dimen.signal_strength_icon_size);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] {drawable, signalDrawable});
        layerDrawable.setLayerGravity(0, 51);
        layerDrawable.setLayerGravity(1, 85);
        layerDrawable.setLayerSize(1, dimensionPixelSize, dimensionPixelSize);
        layerDrawable.setTintList(Utils.getColorAttr(context, android.R.attr.colorControlNormal));
        return layerDrawable;
    }

    public static boolean isCdmaOptions(Context context, int i) {
        int networkTypeFromRaf;
        if (i == -1) {
            return false;
        }
        CarrierConfigCache.getInstance(context).getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (configForSubId != null
                && !configForSubId.getBoolean("hide_carrier_network_settings_bool")
                && configForSubId.getBoolean("world_phone_bool")) {
            return true;
        }
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        if (createForSubscriptionId.getPhoneType() == 2) {
            return true;
        }
        return isWorldMode(context, i)
                && ((networkTypeFromRaf =
                                        getNetworkTypeFromRaf(
                                                (int)
                                                        createForSubscriptionId
                                                                .getAllowedNetworkTypesForReason(
                                                                        0)))
                                == 9
                        || networkTypeFromRaf == 8
                        || networkTypeFromRaf == 26
                        || networkTypeFromRaf == 25
                        || shouldSpeciallyUpdateGsmCdma(context, i));
    }

    public static boolean isContactDiscoveryEnabled(ImsManager imsManager, int i) {
        ImsRcsManager imsRcsManager = getImsRcsManager(imsManager, i);
        if (imsRcsManager == null) {
            return false;
        }
        try {
            return imsRcsManager.getUceAdapter().isUceSettingEnabled();
        } catch (ImsException e) {
            Log.w("MobileNetworkUtils", "UCE service is not available: " + e.getMessage());
            return false;
        }
    }

    public static boolean isContactDiscoveryVisible(Context context, int i) {
        CarrierConfigCache.getInstance(context).getClass();
        if (!CarrierConfigCache.hasCarrierConfigManager()) {
            Log.w(
                    "MobileNetworkUtils",
                    "isContactDiscoveryVisible: Could not resolve carrier config");
            return false;
        }
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (configForSubId == null) {
            return false;
        }
        return configForSubId.getBoolean("use_rcs_presence_bool", false)
                || configForSubId.getBoolean("ims.rcs_bulk_capability_exchange_bool", false);
    }

    public static boolean isGsmBasicOptions(Context context, int i) {
        CarrierConfigCache.getInstance(context).getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        return !(configForSubId == null
                        || configForSubId.getBoolean("hide_carrier_network_settings_bool")
                        || !configForSubId.getBoolean("world_phone_bool"))
                || ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                                .createForSubscriptionId(i)
                                .getPhoneType()
                        == 1;
    }

    public static boolean isGsmOptions(Context context, int i) {
        if (i == -1) {
            return false;
        }
        if (isGsmBasicOptions(context, i)) {
            return true;
        }
        int networkTypeFromRaf =
                getNetworkTypeFromRaf(
                        (int)
                                ((TelephonyManager)
                                                context.getSystemService(TelephonyManager.class))
                                        .createForSubscriptionId(i)
                                        .getAllowedNetworkTypesForReason(0));
        return isWorldMode(context, i)
                && (networkTypeFromRaf == 8
                        || networkTypeFromRaf == 9
                        || networkTypeFromRaf == 25
                        || networkTypeFromRaf == 26
                        || shouldSpeciallyUpdateGsmCdma(context, i));
    }

    public static boolean isMobileDataEnabled(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (telephonyManager.isDataEnabled()) {
            return true;
        }
        TelephonyManager createForSubscriptionId =
                telephonyManager.createForSubscriptionId(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        return createForSubscriptionId != null && createForSubscriptionId.isDataEnabled();
    }

    public static boolean isMobileNetworkUserRestricted(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager != null) {
            return userManager.isGuestUser()
                    || userManager.hasUserRestriction("no_config_mobile_networks");
        }
        return false;
    }

    public static boolean isTdscdmaSupported(Context context, int i) {
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        CarrierConfigCache.getInstance(context).getClass();
        PersistableBundle config = CarrierConfigCache.getConfig();
        if (config == null) {
            return false;
        }
        if (!config.getBoolean("support_tdscdma_bool")) {
            String[] stringArray =
                    config.getStringArray("support_tdscdma_roaming_networks_string_array");
            if (stringArray == null) {
                return false;
            }
            ServiceState serviceState = createForSubscriptionId.getServiceState();
            String operatorNumeric =
                    serviceState != null ? serviceState.getOperatorNumeric() : null;
            if (operatorNumeric == null) {
                return false;
            }
            for (String str : stringArray) {
                if (!operatorNumeric.equals(str)) {}
            }
            return false;
        }
        return true;
    }

    public static boolean isWfcProvisionedOnDevice(int i) {
        ProvisioningManager createForSubscriptionId =
                ProvisioningManager.createForSubscriptionId(i);
        if (createForSubscriptionId == null) {
            return true;
        }
        return createForSubscriptionId.getProvisioningStatusForCapability(1, 1);
    }

    public static boolean isWorldMode(Context context, int i) {
        CarrierConfigCache.getInstance(context).getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (configForSubId == null) {
            return false;
        }
        return configForSubId.getBoolean("world_mode_enabled_bool");
    }

    public static void launchMobileNetworkSettings(
            Context context, SubscriptionInfo subscriptionInfo) {
        if (!SubscriptionUtil.isSimHardwareVisible(context)) {
            Log.e(
                    "MobileNetworkUtils",
                    "launchMobileNetworkSettings fail, device without such UI.");
            return;
        }
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        if (subscriptionId == -1) {
            Log.d("MobileNetworkUtils", "launchMobileNetworkSettings fail, subId is invalid.");
            return;
        }
        Log.d("MobileNetworkUtils", "launchMobileNetworkSettings for subId: " + subscriptionId);
        Bundle bundle = new Bundle();
        bundle.putInt("android.provider.extra.SUB_ID", subscriptionId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        CharSequence uniqueSubscriptionDisplayName =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(context, subscriptionInfo);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = uniqueSubscriptionDisplayName;
        launchRequest.mDestinationName = MobileNetworkSettings.class.getCanonicalName();
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    public static void setContactDiscoveryEnabled(ImsManager imsManager, int i, boolean z) {
        ImsRcsManager imsRcsManager = getImsRcsManager(imsManager, i);
        if (imsRcsManager == null) {
            return;
        }
        try {
            imsRcsManager.getUceAdapter().setUceSettingEnabled(z);
        } catch (ImsException e) {
            Log.w("MobileNetworkUtils", "UCE service is not available: " + e.getMessage());
        }
    }

    public static void setMobileDataEnabled(int i, Context context, boolean z, boolean z2) {
        List<SubscriptionInfo> activeSubscriptionInfoList;
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        SubscriptionManager createForAllUserProfiles =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "setDataEnabledForReason: ", "MobileNetworkUtils", z);
        createForSubscriptionId.setDataEnabledForReason(0, z);
        if (!z2
                || (activeSubscriptionInfoList =
                                createForAllUserProfiles.getActiveSubscriptionInfoList())
                        == null) {
            return;
        }
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
            if (subscriptionInfo.getSubscriptionId() != i && !subscriptionInfo.isOpportunistic()) {
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(subscriptionInfo.getSubscriptionId())
                        .setDataEnabledForReason(0, false);
            }
        }
    }

    public static boolean shouldDisplayNetworkSelectOptions(Context context, int i) {
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        CarrierConfigCache.getInstance(context).getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (i == -1
                || configForSubId == null
                || !configForSubId.getBoolean("operator_selection_expand_bool")
                || configForSubId.getBoolean("hide_carrier_network_settings_bool")
                || (configForSubId.getBoolean("csp_enabled_bool")
                        && !createForSubscriptionId.isManualNetworkSelectionAllowed())) {
            return false;
        }
        if (isWorldMode(context, i)) {
            int networkTypeFromRaf =
                    getNetworkTypeFromRaf(
                            (int) createForSubscriptionId.getAllowedNetworkTypesForReason(0));
            if (networkTypeFromRaf == 8 || shouldSpeciallyUpdateGsmCdma(context, i)) {
                return false;
            }
            if (networkTypeFromRaf == 9) {
                return true;
            }
        }
        return isGsmBasicOptions(context, i);
    }

    public static boolean shouldSpeciallyUpdateGsmCdma(Context context, int i) {
        if (!isWorldMode(context, i)) {
            return false;
        }
        int networkTypeFromRaf =
                getNetworkTypeFromRaf(
                        (int)
                                ((TelephonyManager)
                                                context.getSystemService(TelephonyManager.class))
                                        .createForSubscriptionId(i)
                                        .getAllowedNetworkTypesForReason(0));
        return (networkTypeFromRaf == 17
                        || networkTypeFromRaf == 20
                        || networkTypeFromRaf == 15
                        || networkTypeFromRaf == 19
                        || networkTypeFromRaf == 22
                        || networkTypeFromRaf == 10)
                && !isTdscdmaSupported(context, i);
    }

    public static boolean showEuiccSettings(final Context context) {
        if (!SubscriptionUtil.isSimHardwareVisible(context)) {
            return false;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            Boolean bool =
                    (Boolean)
                            ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                                    .submit(
                                            new Callable() { // from class:
                                                             // com.android.settings.network.telephony.MobileNetworkUtils$$ExternalSyntheticLambda0
                                                @Override // java.util.concurrent.Callable
                                                public final Object call() {
                                                    try {
                                                        return MobileNetworkUtils
                                                                .showEuiccSettingsDetecting(
                                                                        context);
                                                    } catch (Exception e) {
                                                        Log.w(
                                                                "MobileNetworkUtils",
                                                                "Accessing Euicc failure",
                                                                e);
                                                        return Boolean.FALSE;
                                                    }
                                                }
                                            })
                                    .get(3L, TimeUnit.SECONDS);
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (InterruptedException | ExecutionException | TimeoutException unused) {
            Log.w(
                    "MobileNetworkUtils",
                    "Accessing Euicc takes too long: +"
                            + (SystemClock.elapsedRealtime() - elapsedRealtime)
                            + "ms");
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00cb, code lost:

       if (r10 != false) goto L28;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Boolean showEuiccSettingsDetecting(android.content.Context r10) {
        /*
            java.lang.Class<android.telephony.euicc.EuiccManager> r0 = android.telephony.euicc.EuiccManager.class
            java.lang.Object r0 = r10.getSystemService(r0)
            android.telephony.euicc.EuiccManager r0 = (android.telephony.euicc.EuiccManager) r0
            java.lang.String r1 = "MobileNetworkUtils"
            if (r0 == 0) goto Ld4
            boolean r0 = r0.isEnabled()
            if (r0 != 0) goto L14
            goto Ld4
        L14:
            android.content.ContentResolver r0 = r10.getContentResolver()
            java.lang.String r2 = "ro.setupwizard.esim_cid_ignore"
            java.lang.String r3 = ""
            java.lang.String r2 = android.os.SystemProperties.get(r2, r3)
            java.lang.String r3 = ","
            java.lang.String[] r2 = android.text.TextUtils.split(r2, r3)
            java.util.List r2 = java.util.Arrays.asList(r2)
            java.lang.String r3 = "ro.boot.cid"
            java.lang.String r3 = android.os.SystemProperties.get(r3)
            boolean r2 = r2.contains(r3)
            java.lang.String r3 = "esim.enable_esim_system_ui_by_default"
            r4 = 1
            boolean r3 = android.os.SystemProperties.getBoolean(r3, r4)
            java.lang.String r5 = "euicc_provisioned"
            r6 = 0
            int r0 = android.provider.Settings.Global.getInt(r0, r5, r6)
            if (r0 == 0) goto L46
            r0 = r4
            goto L47
        L46:
            r0 = r6
        L47:
            boolean r5 = com.android.settingslib.development.DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(r10)
            java.lang.String r7 = "showEuiccSettings: esimIgnoredDevice: "
            java.lang.String r8 = ", enabledEsimUiByDefault: "
            java.lang.String r9 = ", euiccProvisioned: "
            java.lang.StringBuilder r7 = com.android.settings.Utils$$ExternalSyntheticOutline0.m(r7, r2, r8, r3, r9)
            r7.append(r0)
            java.lang.String r8 = ", inDeveloperMode: "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = "."
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.i(r1, r7)
            if (r0 != 0) goto Lcf
            if (r2 != 0) goto L72
            if (r5 != 0) goto Lcf
        L72:
            if (r2 != 0) goto Lce
            if (r3 == 0) goto Lce
            java.lang.Class<android.telephony.euicc.EuiccManager> r0 = android.telephony.euicc.EuiccManager.class
            java.lang.Object r0 = r10.getSystemService(r0)
            android.telephony.euicc.EuiccManager r0 = (android.telephony.euicc.EuiccManager) r0
            java.lang.Class<android.telephony.TelephonyManager> r2 = android.telephony.TelephonyManager.class
            java.lang.Object r10 = r10.getSystemService(r2)
            android.telephony.TelephonyManager r10 = (android.telephony.TelephonyManager) r10
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            r3 = r6
        L8c:
            int r5 = r10.getPhoneCount()
            if (r3 >= r5) goto La2
            java.lang.String r5 = r10.getNetworkCountryIso(r3)
            boolean r7 = android.text.TextUtils.isEmpty(r5)
            if (r7 != 0) goto L9f
            r2.add(r5)
        L9f:
            int r3 = r3 + 1
            goto L8c
        La2:
            java.util.stream.Stream r10 = r2.stream()
            java.util.Objects.requireNonNull(r0)
            com.android.settings.network.telephony.MobileNetworkUtils$$ExternalSyntheticLambda1 r3 = new com.android.settings.network.telephony.MobileNetworkUtils$$ExternalSyntheticLambda1
            r3.<init>()
            boolean r10 = r10.anyMatch(r3)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "isCurrentCountrySupported countryCodes: "
            r0.<init>(r3)
            r0.append(r2)
            java.lang.String r2 = " eSIMSupported: "
            r0.append(r2)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            android.util.Log.i(r1, r0)
            if (r10 == 0) goto Lce
            goto Lcf
        Lce:
            r4 = r6
        Lcf:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r4)
            return r10
        Ld4:
            java.lang.String r10 = "EuiccManager is not enabled."
            android.util.Log.w(r1, r10)
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.MobileNetworkUtils.showEuiccSettingsDetecting(android.content.Context):java.lang.Boolean");
    }

    public static void showLockScreen(Context context, final Runnable runnable) {
        if (!((KeyguardManager) context.getSystemService(KeyguardManager.class)).isDeviceSecure()) {
            runnable.run();
            return;
        }
        BiometricPrompt.AuthenticationCallback authenticationCallback =
                new BiometricPrompt
                        .AuthenticationCallback() { // from class:
                                                    // com.android.settings.network.telephony.MobileNetworkUtils.1
                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult authenticationResult) {
                        runnable.run();
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationError(int i, CharSequence charSequence) {}
                };
        int myUserId = UserHandle.myUserId();
        BiometricPrompt.Builder deviceCredentialAllowed =
                new BiometricPrompt.Builder(context)
                        .setTitle(context.getText(R.string.wifi_dpp_lockscreen_title))
                        .setDeviceCredentialAllowed(true);
        StringBuilder sb = com.android.settings.Utils.sBuilder;
        deviceCredentialAllowed
                .setTextForDeviceCredential(
                        null,
                        com.android.settings.Utils.getConfirmCredentialStringForUser(
                                context,
                                myUserId,
                                new LockPatternUtils(context).getCredentialTypeForUser(myUserId)),
                        null)
                .build()
                .authenticate(
                        new CancellationSignal(),
                        new MediaRoute2Provider$$ExternalSyntheticLambda0(
                                new Handler(Looper.getMainLooper())),
                        authenticationCallback);
    }
}
