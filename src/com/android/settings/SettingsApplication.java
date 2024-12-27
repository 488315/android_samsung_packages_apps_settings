package com.android.settings;

import android.app.Application;
import android.app.backup.BackupHelper;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.biometrics.fingerprint2.BiometricsEnvironment;
import com.android.settings.core.instrumentation.ElapsedTimeUtils;
import com.android.settings.development.DeveloperOptionsActivityLifecycle;
import com.android.settings.fuelgauge.BatterySettingsStorage;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.network.telephony.ToggleSubscriptionDialogActivity;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.applications.AppIconCacheManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.datastore.BackupRestoreFileStorage;
import com.android.settingslib.datastore.BackupRestoreStorage;
import com.android.settingslib.datastore.BackupRestoreStorageManager;
import com.android.settingslib.datastore.BackupRestoreStorageManager.StorageWrapper;
import com.android.settingslib.datastore.KeyedObservable;
import com.android.settingslib.datastore.Observable;
import com.android.settingslib.datastore.SharedPreferencesStorage;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.sdk.bixby2.AppMetaInfo;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.command.CommandSdk;
import com.samsung.android.sdk.routines.v3.internal.HandlerProvider;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.accessibility.recommend.UsingFunctionsProvider;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;
import com.samsung.android.settings.bixby.actionhandler.AODActionHandler;
import com.samsung.android.settings.bixby.actionhandler.AccessibilityActionHandler;
import com.samsung.android.settings.bixby.actionhandler.ConnectivityActionHandler;
import com.samsung.android.settings.bixby.actionhandler.DeviceQnAActionHandler;
import com.samsung.android.settings.bixby.actionhandler.EdgeActionHandler;
import com.samsung.android.settings.bixby.actionhandler.NotificationActionHandler;
import com.samsung.android.settings.bixby.actionhandler.SettingsActionHandler;
import com.samsung.android.settings.bixby.actionhandler.SystemUIActionHandler;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.homepage.SecTopLevelFeature;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.StatusLoggingUtils;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.wifi.mobileap.routine.WifiApConditionHandler;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;

import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SettingsApplication extends Application {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BiometricsEnvironment mBiometricsEnvironment;
    public WeakReference mHomeActivity = new WeakReference(null);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeviceProvisionedObserver extends ContentObserver {
        public final Uri mDeviceProvisionedUri;

        public DeviceProvisionedObserver() {
            super(null);
            this.mDeviceProvisionedUri = Settings.Secure.getUriFor("user_setup_complete");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.mDeviceProvisionedUri.equals(uri)) {
                SettingsApplication.this.getContentResolver().unregisterContentObserver(this);
                if (WizardManagerHelper.isUserSetupComplete(SettingsApplication.this)) {
                    SecTopLevelFeature.getInstance().setBoolean("flag_user_setup_complete", true);
                    SecTopLevelFeature.getInstance()
                            .setBoolean("flag_settings_support_large_screen", true);
                }
                new ActivityEmbeddingRulesController(SettingsApplication.this).initRules();
            }
        }
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        FeatureFactoryImpl featureFactoryImpl = new FeatureFactoryImpl();
        FeatureFactoryImpl._appContext = this;
        FeatureFactoryImpl._factory = featureFactoryImpl;
    }

    public final SettingsHomepageActivity getHomeActivity() {
        return (SettingsHomepageActivity) this.mHomeActivity.get();
    }

    @Override // android.app.Application
    public final void onCreate() {
        boolean isSettingsSplitEnabled;
        final int i = 2;
        final int i2 = 0;
        final int i3 = 1;
        Trace.beginSection("SettingsApplication#onCreate");
        super.onCreate();
        if ("com.android.settings:remote".equals(Application.getProcessName())) {
            Trace.endSection();
            return;
        }
        ThreadUtils.postOnBackgroundThread(
                new Runnable(this) { // from class:
                    // com.android.settings.SettingsApplication$$ExternalSyntheticLambda0
                    public final /* synthetic */ SettingsApplication f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z;
                        int i4 = i2;
                        SettingsApplication settingsApplication = this.f$0;
                        switch (i4) {
                            case 0:
                                int i5 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection(
                                        "SettingsApplication#LoadTopLevelPreferenceXmlMeta");
                                SecTopLevelFeature.getInstance()
                                        .loadTopLevelPreferenceXmlMeta(
                                                settingsApplication.getApplicationContext());
                                Trace.endSection();
                                return;
                            case 1:
                                int i6 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#ElapsedTimeUtils");
                                if (settingsApplication.getApplicationContext() != null) {
                                    Context applicationContext =
                                            settingsApplication.getApplicationContext();
                                    Optional optional = ElapsedTimeUtils.sSuwFinishedTimeStamp;
                                    if (applicationContext
                                                    .getApplicationContext()
                                                    .getSharedPreferences("elapsed_time_info", 0)
                                                    .contains("suw_finished_time_ms")
                                            && !ElapsedTimeUtils.sSuwFinishedTimeStamp
                                                    .isPresent()) {
                                        ElapsedTimeUtils.sSuwFinishedTimeStamp =
                                                Optional.of(
                                                        Long.valueOf(
                                                                ElapsedTimeUtils
                                                                        .getSuwFinishedTimestamp(
                                                                                applicationContext)));
                                    }
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#setSpaEnvironment");
                                SpaEnvironmentFactory.spaEnvironment =
                                        new SettingsSpaEnvironment(settingsApplication);
                                Log.d(
                                        "SpaEnvironment",
                                        UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#CommandSdk.setHandler");
                                CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
                                SettingsCommandActionHandler settingsCommandActionHandler =
                                        new SettingsCommandActionHandler();
                                settingsCommandActionHandler.mCachedCommandMap = null;
                                settingsCommandActionHandler.mContext = settingsApplication;
                                Object obj = CommandSdk.sWaitLock;
                                synchronized (obj) {
                                    commandSdk.mActionHandler = settingsCommandActionHandler;
                                    Log.d("[CmdL-2.0.8]CommandSdk", "set the action handler");
                                    obj.notifyAll();
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BixbyRoutineActionHandler");
                                RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                                BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                        BixbyRoutineActionHandler.getInstance();
                                routineSdkImpl.getClass();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setHandler - conditionHandler=null, actionHandler="
                                                + bixbyRoutineActionHandler);
                                final HandlerProvider handlerProvider = routineSdkImpl.d;
                                handlerProvider.a = null;
                                handlerProvider
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                final HandlerProvider handlerProvider2 = routineSdkImpl.e;
                                handlerProvider2.a = bixbyRoutineActionHandler;
                                handlerProvider2
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                WifiApConditionHandler wifiApConditionHandler =
                                        new WifiApConditionHandler();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setConditionHandler - tag=hotspot_condition_tag,"
                                            + " conditionHandler="
                                                + wifiApConditionHandler);
                                HandlerProvider handlerProvider3 = routineSdkImpl.d;
                                handlerProvider3.b.put(
                                        "hotspot_condition_tag", wifiApConditionHandler);
                                handlerProvider3.b("hotspot_condition_tag");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#printSamsungAnalytics");
                                Log.i("Settings_SA", "Settings LoggingHelper init");
                                Configuration configuration = new Configuration();
                                configuration.enableAutoDeviceId = false;
                                configuration.auidType = -1;
                                configuration.trackingId = "759-399-5199102";
                                settingsApplication.getApplicationContext();
                                int i7 = Build.VERSION.SEM_PLATFORM_INT;
                                configuration.version =
                                        (i7 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                                + "."
                                                + ((i7
                                                                % EnterpriseContainerConstants
                                                                        .SYSTEM_SIGNED_APP)
                                                        / 100);
                                configuration.enableAutoDeviceId = true;
                                SamsungAnalytics.getInstanceAndConfig(
                                        settingsApplication, configuration);
                                final SALogging sALogging =
                                        SALogging.getInstance(settingsApplication);
                                sALogging.getClass();
                                int i8 = StatusLoggingUtils.$r8$clinit;
                                long j =
                                        Settings.System.getLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                0L);
                                int i9 =
                                        Settings.System.getInt(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                0);
                                long currentTimeMillis = System.currentTimeMillis();
                                Long valueOf = Long.valueOf(currentTimeMillis);
                                if (j == 0) {
                                    Settings.System.putLong(
                                            settingsApplication.getContentResolver(),
                                            "last_status_logging",
                                            currentTimeMillis);
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "Status Logging time is not set : " + valueOf);
                                }
                                if (Settings.Secure.getInt(
                                                settingsApplication.getContentResolver(),
                                                "user_setup_complete",
                                                0)
                                        == 1) {
                                    if (currentTimeMillis - j
                                            > StatusLoggingUtils.A_WEEK_TO_MILLISECOND) {
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                currentTimeMillis);
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                i9 + 1);
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "checkLoggingInternal current time : "
                                                    + valueOf
                                                    + " saved time : "
                                                    + j);
                                } else {
                                    Log.i("Settings_SA_Utils", "SetupWizard is not completed");
                                    z = false;
                                }
                                if (z) {
                                    AsyncTask.execute(
                                            new Runnable() { // from class:
                                                // com.samsung.android.settings.logging.SALogging$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SALogging.this.processStatusLogging(false);
                                                }
                                            });
                                } else {
                                    Log.i(
                                            "Settings_SA_SALogging",
                                            "It has not been a week since last logging");
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Sbixby.initialize");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby = Sbixby.getInstance();
                                int i10 = SystemProperties.getInt("ro.build.version.oneui", 0);
                                sbixby.getClass();
                                if (TextUtils.isEmpty(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                                    throw new IllegalArgumentException(
                                            "capsuleId cannot be null or empty");
                                }
                                if (Sbixby.appMetaInfoMap == null) {
                                    Sbixby.appMetaInfoMap = new HashMap();
                                }
                                ((HashMap) Sbixby.appMetaInfoMap)
                                        .put(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                new AppMetaInfo(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        i10));
                                Trace.endSection();
                                Trace.beginSection(
                                        "SettingsApplication#Sbixby.initializeBixbyActionHandler");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby2 = Sbixby.getInstance();
                                AODActionHandler aODActionHandler =
                                        new AODActionHandler(settingsApplication);
                                String[] strArr = AODActionHandler.ACTIONS;
                                for (int i11 = 0; i11 < 3; i11++) {
                                    String str = strArr[i11];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str, aODActionHandler);
                                }
                                EdgeActionHandler edgeActionHandler =
                                        new EdgeActionHandler(settingsApplication);
                                String[] strArr2 = EdgeActionHandler.ACTIONS;
                                for (int i12 = 0; i12 < 3; i12++) {
                                    String str2 = strArr2[i12];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str2, edgeActionHandler);
                                }
                                SettingsActionHandler settingsActionHandler =
                                        new SettingsActionHandler(settingsApplication);
                                String[] strArr3 = SettingsActionHandler.ACTIONS;
                                for (int i13 = 0; i13 < 113; i13++) {
                                    String str3 = strArr3[i13];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str3, settingsActionHandler);
                                }
                                ConnectivityActionHandler connectivityActionHandler =
                                        new ConnectivityActionHandler(settingsApplication);
                                connectivityActionHandler.mParamBundle = null;
                                String[] strArr4 = ConnectivityActionHandler.ACTIONS;
                                for (int i14 = 0; i14 < 26; i14++) {
                                    String str4 = strArr4[i14];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str4, connectivityActionHandler);
                                }
                                AccessibilityActionHandler accessibilityActionHandler =
                                        new AccessibilityActionHandler(settingsApplication);
                                accessibilityActionHandler.mParamBundle = null;
                                accessibilityActionHandler.mFeatureKey = null;
                                accessibilityActionHandler.mFeatureValue = null;
                                accessibilityActionHandler.mAccessibilityMenu = null;
                                HashMap hashMap = new HashMap();
                                accessibilityActionHandler.mActionHashMap = hashMap;
                                hashMap.put(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        ToggleSubscriptionDialogActivity.ARG_enable);
                                hashMap.put(
                                        "viv.accessibilityApp.DisableAccessibilityMenu", "disable");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnDirectAccess", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnFlashNotification", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch", "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnVoiceAssistant", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnMuteAllSounds", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        "turn_off");
                                hashMap.put(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentColor", "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetDoorBellRecordStatus", "get");
                                hashMap.put("viv.accessibilityApp.GetExclusivePopup", "get");
                                hashMap.put("viv.accessibilityApp.GetPermissionPopup", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetSupportFeature", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetVoiceRecorderStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue", "set");
                                hashMap.put(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        "adjust");
                                sbixby2.getClass();
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.DisableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccess",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnMuteAllSounds",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnVoiceAssistant",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentColor",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDoorBellRecordStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetExclusivePopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPermissionPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetSupportFeature",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetVoiceRecorderStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnSoundDetector",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDisclaimerPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.CheckSoundDetectorCondition",
                                        accessibilityActionHandler);
                                SystemUIActionHandler systemUIActionHandler =
                                        new SystemUIActionHandler(settingsApplication);
                                String[] strArr5 = SystemUIActionHandler.ACTIONS;
                                for (int i15 = 0; i15 < 99; i15++) {
                                    Sbixby.addActionHandler(strArr5[i15], systemUIActionHandler);
                                }
                                NotificationActionHandler notificationActionHandler =
                                        new NotificationActionHandler(settingsApplication);
                                String[] strArr6 = NotificationActionHandler.ACTIONS;
                                for (int i16 = 0; i16 < 3; i16++) {
                                    Sbixby.addActionHandler(
                                            strArr6[i16], notificationActionHandler);
                                }
                                DeviceQnAActionHandler deviceQnAActionHandler =
                                        new DeviceQnAActionHandler(settingsApplication);
                                Sbixby.addActionHandler(
                                        "CheckAvailableDeeplink", deviceQnAActionHandler);
                                Sbixby.addActionHandler(
                                        "LaunchQnADeeplink", deviceQnAActionHandler);
                                Trace.endSection();
                                return;
                            default:
                                int i17 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#initializeClasses");
                                Trace.beginSection("SettingsApplication#Rune_Init");
                                String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                SystemProperties.get("ro.csc.country_code");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Utils_Init");
                                Utils.readCountryCode();
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#LockUtils_Init");
                                LockUtils.isCameraCoverAttached(settingsApplication);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#KnoxUtils_Init");
                                KnoxUtils.checkKnoxCustomSettingsHiddenItem(1024);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BT_Init");
                                if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(
                                        settingsApplication)) {
                                    LocalBluetoothManager.getInstance(
                                            settingsApplication,
                                            com.android.settings.bluetooth.Utils.mOnInitCallback);
                                }
                                Trace.endSection();
                                Trace.endSection();
                                return;
                        }
                    }
                });
        BackupRestoreStorageManager backupRestoreStorageManager =
                BackupRestoreStorageManager.getInstance(this);
        BackupRestoreStorage[] backupRestoreStorageArr = {
            new BatterySettingsStorage(this), new SharedPreferencesStorage(this)
        };
        for (int i4 = 0; i4 < 2; i4++) {
            BackupRestoreStorage storage = backupRestoreStorageArr[i4];
            Intrinsics.checkNotNullParameter(storage, "storage");
            if (storage instanceof BackupRestoreFileStorage) {
                BackupRestoreFileStorage backupRestoreFileStorage =
                        (BackupRestoreFileStorage) storage;
                if (backupRestoreFileStorage.storageFilePath.length() == 0
                        || backupRestoreFileStorage.storageFilePath.charAt(0)
                                == File.separatorChar) {
                    throw new IllegalArgumentException(
                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                    backupRestoreFileStorage.storageFilePath,
                                    " is not valid path"));
                }
                if (!backupRestoreFileStorage.getBackupFile().isAbsolute()) {
                    throw new IllegalArgumentException("backupFile is not absolute");
                }
                if (!backupRestoreFileStorage.getBackupFile().isAbsolute()) {
                    throw new IllegalArgumentException("restoreFile is not absolute");
                }
            }
            String name = storage.getName();
            BackupRestoreStorageManager.StorageWrapper storageWrapper =
                    (BackupRestoreStorageManager.StorageWrapper)
                            backupRestoreStorageManager.storageWrappers.put(
                                    name, backupRestoreStorageManager.new StorageWrapper(storage));
            BackupRestoreStorage backupRestoreStorage =
                    storageWrapper != null ? storageWrapper.storage : null;
            if (backupRestoreStorage != null) {
                throw new IllegalStateException(
                        "Storage name '"
                                + name
                                + "' conflicts:\n\told: "
                                + backupRestoreStorage
                                + "\n\tnew: "
                                + storage);
            }
        }
        if (SecurityUtils.hasFingerprintFeature(this)) {
            this.mBiometricsEnvironment = new BiometricsEnvironment(this);
        }
        Trace.beginSection("SettingsApplication#ActivityEmbeddingUtils");
        boolean z = ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION;
        if (SecTopLevelFeature.getInstance().getBoolean("isSettingsSplitEnabled")) {
            isSettingsSplitEnabled = true;
        } else {
            isSettingsSplitEnabled = ActivityEmbeddingUtils.isSettingsSplitEnabled(this);
            if (isSettingsSplitEnabled) {
                SecTopLevelFeature.getInstance().setBoolean("isSettingsSplitEnabled", true);
            }
        }
        if (isSettingsSplitEnabled
                && FeatureFlagUtils.isEnabled(this, "settings_support_large_screen")) {
            Trace.beginSection("SettingsApplication#SettingsManager.getForceSingleView");
            SettingsManager settingsManager = SettingsManager.getInstance();
            SecTopLevelFeature.getInstance()
                    .setBoolean(
                            "flag_force_single_view",
                            settingsManager != null && settingsManager.getForceSingleView());
            Trace.endSection();
            if (WizardManagerHelper.isUserSetupComplete(this)) {
                SecTopLevelFeature.getInstance().setBoolean("flag_user_setup_complete", true);
                SecTopLevelFeature.getInstance()
                        .setBoolean("flag_settings_support_large_screen", true);
                new ActivityEmbeddingRulesController(this).initRules();
            } else {
                DeviceProvisionedObserver deviceProvisionedObserver =
                        new DeviceProvisionedObserver();
                getContentResolver()
                        .registerContentObserver(
                                deviceProvisionedObserver.mDeviceProvisionedUri,
                                false,
                                deviceProvisionedObserver);
            }
        }
        DeveloperOptionsActivityLifecycle developerOptionsActivityLifecycle =
                new DeveloperOptionsActivityLifecycle();
        developerOptionsActivityLifecycle.mFragmentCallback =
                new DeveloperOptionsActivityLifecycle.AnonymousClass1();
        registerActivityLifecycleCallbacks(developerOptionsActivityLifecycle);
        Trace.endSection();
        ThreadUtils.postOnBackgroundThread(
                new Runnable(this) { // from class:
                    // com.android.settings.SettingsApplication$$ExternalSyntheticLambda0
                    public final /* synthetic */ SettingsApplication f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z2;
                        int i42 = i3;
                        SettingsApplication settingsApplication = this.f$0;
                        switch (i42) {
                            case 0:
                                int i5 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection(
                                        "SettingsApplication#LoadTopLevelPreferenceXmlMeta");
                                SecTopLevelFeature.getInstance()
                                        .loadTopLevelPreferenceXmlMeta(
                                                settingsApplication.getApplicationContext());
                                Trace.endSection();
                                return;
                            case 1:
                                int i6 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#ElapsedTimeUtils");
                                if (settingsApplication.getApplicationContext() != null) {
                                    Context applicationContext =
                                            settingsApplication.getApplicationContext();
                                    Optional optional = ElapsedTimeUtils.sSuwFinishedTimeStamp;
                                    if (applicationContext
                                                    .getApplicationContext()
                                                    .getSharedPreferences("elapsed_time_info", 0)
                                                    .contains("suw_finished_time_ms")
                                            && !ElapsedTimeUtils.sSuwFinishedTimeStamp
                                                    .isPresent()) {
                                        ElapsedTimeUtils.sSuwFinishedTimeStamp =
                                                Optional.of(
                                                        Long.valueOf(
                                                                ElapsedTimeUtils
                                                                        .getSuwFinishedTimestamp(
                                                                                applicationContext)));
                                    }
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#setSpaEnvironment");
                                SpaEnvironmentFactory.spaEnvironment =
                                        new SettingsSpaEnvironment(settingsApplication);
                                Log.d(
                                        "SpaEnvironment",
                                        UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#CommandSdk.setHandler");
                                CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
                                SettingsCommandActionHandler settingsCommandActionHandler =
                                        new SettingsCommandActionHandler();
                                settingsCommandActionHandler.mCachedCommandMap = null;
                                settingsCommandActionHandler.mContext = settingsApplication;
                                Object obj = CommandSdk.sWaitLock;
                                synchronized (obj) {
                                    commandSdk.mActionHandler = settingsCommandActionHandler;
                                    Log.d("[CmdL-2.0.8]CommandSdk", "set the action handler");
                                    obj.notifyAll();
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BixbyRoutineActionHandler");
                                RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                                BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                        BixbyRoutineActionHandler.getInstance();
                                routineSdkImpl.getClass();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setHandler - conditionHandler=null, actionHandler="
                                                + bixbyRoutineActionHandler);
                                final HandlerProvider handlerProvider = routineSdkImpl.d;
                                handlerProvider.a = null;
                                handlerProvider
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                final HandlerProvider handlerProvider2 = routineSdkImpl.e;
                                handlerProvider2.a = bixbyRoutineActionHandler;
                                handlerProvider2
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                WifiApConditionHandler wifiApConditionHandler =
                                        new WifiApConditionHandler();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setConditionHandler - tag=hotspot_condition_tag,"
                                            + " conditionHandler="
                                                + wifiApConditionHandler);
                                HandlerProvider handlerProvider3 = routineSdkImpl.d;
                                handlerProvider3.b.put(
                                        "hotspot_condition_tag", wifiApConditionHandler);
                                handlerProvider3.b("hotspot_condition_tag");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#printSamsungAnalytics");
                                Log.i("Settings_SA", "Settings LoggingHelper init");
                                Configuration configuration = new Configuration();
                                configuration.enableAutoDeviceId = false;
                                configuration.auidType = -1;
                                configuration.trackingId = "759-399-5199102";
                                settingsApplication.getApplicationContext();
                                int i7 = Build.VERSION.SEM_PLATFORM_INT;
                                configuration.version =
                                        (i7 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                                + "."
                                                + ((i7
                                                                % EnterpriseContainerConstants
                                                                        .SYSTEM_SIGNED_APP)
                                                        / 100);
                                configuration.enableAutoDeviceId = true;
                                SamsungAnalytics.getInstanceAndConfig(
                                        settingsApplication, configuration);
                                final SALogging sALogging =
                                        SALogging.getInstance(settingsApplication);
                                sALogging.getClass();
                                int i8 = StatusLoggingUtils.$r8$clinit;
                                long j =
                                        Settings.System.getLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                0L);
                                int i9 =
                                        Settings.System.getInt(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                0);
                                long currentTimeMillis = System.currentTimeMillis();
                                Long valueOf = Long.valueOf(currentTimeMillis);
                                if (j == 0) {
                                    Settings.System.putLong(
                                            settingsApplication.getContentResolver(),
                                            "last_status_logging",
                                            currentTimeMillis);
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "Status Logging time is not set : " + valueOf);
                                }
                                if (Settings.Secure.getInt(
                                                settingsApplication.getContentResolver(),
                                                "user_setup_complete",
                                                0)
                                        == 1) {
                                    if (currentTimeMillis - j
                                            > StatusLoggingUtils.A_WEEK_TO_MILLISECOND) {
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                currentTimeMillis);
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                i9 + 1);
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "checkLoggingInternal current time : "
                                                    + valueOf
                                                    + " saved time : "
                                                    + j);
                                } else {
                                    Log.i("Settings_SA_Utils", "SetupWizard is not completed");
                                    z2 = false;
                                }
                                if (z2) {
                                    AsyncTask.execute(
                                            new Runnable() { // from class:
                                                // com.samsung.android.settings.logging.SALogging$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SALogging.this.processStatusLogging(false);
                                                }
                                            });
                                } else {
                                    Log.i(
                                            "Settings_SA_SALogging",
                                            "It has not been a week since last logging");
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Sbixby.initialize");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby = Sbixby.getInstance();
                                int i10 = SystemProperties.getInt("ro.build.version.oneui", 0);
                                sbixby.getClass();
                                if (TextUtils.isEmpty(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                                    throw new IllegalArgumentException(
                                            "capsuleId cannot be null or empty");
                                }
                                if (Sbixby.appMetaInfoMap == null) {
                                    Sbixby.appMetaInfoMap = new HashMap();
                                }
                                ((HashMap) Sbixby.appMetaInfoMap)
                                        .put(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                new AppMetaInfo(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        i10));
                                Trace.endSection();
                                Trace.beginSection(
                                        "SettingsApplication#Sbixby.initializeBixbyActionHandler");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby2 = Sbixby.getInstance();
                                AODActionHandler aODActionHandler =
                                        new AODActionHandler(settingsApplication);
                                String[] strArr = AODActionHandler.ACTIONS;
                                for (int i11 = 0; i11 < 3; i11++) {
                                    String str = strArr[i11];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str, aODActionHandler);
                                }
                                EdgeActionHandler edgeActionHandler =
                                        new EdgeActionHandler(settingsApplication);
                                String[] strArr2 = EdgeActionHandler.ACTIONS;
                                for (int i12 = 0; i12 < 3; i12++) {
                                    String str2 = strArr2[i12];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str2, edgeActionHandler);
                                }
                                SettingsActionHandler settingsActionHandler =
                                        new SettingsActionHandler(settingsApplication);
                                String[] strArr3 = SettingsActionHandler.ACTIONS;
                                for (int i13 = 0; i13 < 113; i13++) {
                                    String str3 = strArr3[i13];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str3, settingsActionHandler);
                                }
                                ConnectivityActionHandler connectivityActionHandler =
                                        new ConnectivityActionHandler(settingsApplication);
                                connectivityActionHandler.mParamBundle = null;
                                String[] strArr4 = ConnectivityActionHandler.ACTIONS;
                                for (int i14 = 0; i14 < 26; i14++) {
                                    String str4 = strArr4[i14];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str4, connectivityActionHandler);
                                }
                                AccessibilityActionHandler accessibilityActionHandler =
                                        new AccessibilityActionHandler(settingsApplication);
                                accessibilityActionHandler.mParamBundle = null;
                                accessibilityActionHandler.mFeatureKey = null;
                                accessibilityActionHandler.mFeatureValue = null;
                                accessibilityActionHandler.mAccessibilityMenu = null;
                                HashMap hashMap = new HashMap();
                                accessibilityActionHandler.mActionHashMap = hashMap;
                                hashMap.put(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        ToggleSubscriptionDialogActivity.ARG_enable);
                                hashMap.put(
                                        "viv.accessibilityApp.DisableAccessibilityMenu", "disable");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnDirectAccess", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnFlashNotification", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch", "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnVoiceAssistant", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnMuteAllSounds", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        "turn_off");
                                hashMap.put(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentColor", "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetDoorBellRecordStatus", "get");
                                hashMap.put("viv.accessibilityApp.GetExclusivePopup", "get");
                                hashMap.put("viv.accessibilityApp.GetPermissionPopup", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetSupportFeature", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetVoiceRecorderStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue", "set");
                                hashMap.put(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        "adjust");
                                sbixby2.getClass();
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.DisableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccess",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnMuteAllSounds",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnVoiceAssistant",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentColor",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDoorBellRecordStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetExclusivePopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPermissionPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetSupportFeature",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetVoiceRecorderStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnSoundDetector",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDisclaimerPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.CheckSoundDetectorCondition",
                                        accessibilityActionHandler);
                                SystemUIActionHandler systemUIActionHandler =
                                        new SystemUIActionHandler(settingsApplication);
                                String[] strArr5 = SystemUIActionHandler.ACTIONS;
                                for (int i15 = 0; i15 < 99; i15++) {
                                    Sbixby.addActionHandler(strArr5[i15], systemUIActionHandler);
                                }
                                NotificationActionHandler notificationActionHandler =
                                        new NotificationActionHandler(settingsApplication);
                                String[] strArr6 = NotificationActionHandler.ACTIONS;
                                for (int i16 = 0; i16 < 3; i16++) {
                                    Sbixby.addActionHandler(
                                            strArr6[i16], notificationActionHandler);
                                }
                                DeviceQnAActionHandler deviceQnAActionHandler =
                                        new DeviceQnAActionHandler(settingsApplication);
                                Sbixby.addActionHandler(
                                        "CheckAvailableDeeplink", deviceQnAActionHandler);
                                Sbixby.addActionHandler(
                                        "LaunchQnADeeplink", deviceQnAActionHandler);
                                Trace.endSection();
                                return;
                            default:
                                int i17 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#initializeClasses");
                                Trace.beginSection("SettingsApplication#Rune_Init");
                                String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                SystemProperties.get("ro.csc.country_code");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Utils_Init");
                                Utils.readCountryCode();
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#LockUtils_Init");
                                LockUtils.isCameraCoverAttached(settingsApplication);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#KnoxUtils_Init");
                                KnoxUtils.checkKnoxCustomSettingsHiddenItem(1024);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BT_Init");
                                if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(
                                        settingsApplication)) {
                                    LocalBluetoothManager.getInstance(
                                            settingsApplication,
                                            com.android.settings.bluetooth.Utils.mOnInitCallback);
                                }
                                Trace.endSection();
                                Trace.endSection();
                                return;
                        }
                    }
                });
        ThreadUtils.postOnBackgroundThread(
                new Runnable(this) { // from class:
                    // com.android.settings.SettingsApplication$$ExternalSyntheticLambda0
                    public final /* synthetic */ SettingsApplication f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z2;
                        int i42 = i;
                        SettingsApplication settingsApplication = this.f$0;
                        switch (i42) {
                            case 0:
                                int i5 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection(
                                        "SettingsApplication#LoadTopLevelPreferenceXmlMeta");
                                SecTopLevelFeature.getInstance()
                                        .loadTopLevelPreferenceXmlMeta(
                                                settingsApplication.getApplicationContext());
                                Trace.endSection();
                                return;
                            case 1:
                                int i6 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#ElapsedTimeUtils");
                                if (settingsApplication.getApplicationContext() != null) {
                                    Context applicationContext =
                                            settingsApplication.getApplicationContext();
                                    Optional optional = ElapsedTimeUtils.sSuwFinishedTimeStamp;
                                    if (applicationContext
                                                    .getApplicationContext()
                                                    .getSharedPreferences("elapsed_time_info", 0)
                                                    .contains("suw_finished_time_ms")
                                            && !ElapsedTimeUtils.sSuwFinishedTimeStamp
                                                    .isPresent()) {
                                        ElapsedTimeUtils.sSuwFinishedTimeStamp =
                                                Optional.of(
                                                        Long.valueOf(
                                                                ElapsedTimeUtils
                                                                        .getSuwFinishedTimestamp(
                                                                                applicationContext)));
                                    }
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#setSpaEnvironment");
                                SpaEnvironmentFactory.spaEnvironment =
                                        new SettingsSpaEnvironment(settingsApplication);
                                Log.d(
                                        "SpaEnvironment",
                                        UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#CommandSdk.setHandler");
                                CommandSdk commandSdk = CommandSdk.LazyHolder.INSTANCE;
                                SettingsCommandActionHandler settingsCommandActionHandler =
                                        new SettingsCommandActionHandler();
                                settingsCommandActionHandler.mCachedCommandMap = null;
                                settingsCommandActionHandler.mContext = settingsApplication;
                                Object obj = CommandSdk.sWaitLock;
                                synchronized (obj) {
                                    commandSdk.mActionHandler = settingsCommandActionHandler;
                                    Log.d("[CmdL-2.0.8]CommandSdk", "set the action handler");
                                    obj.notifyAll();
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BixbyRoutineActionHandler");
                                RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                                BixbyRoutineActionHandler bixbyRoutineActionHandler =
                                        BixbyRoutineActionHandler.getInstance();
                                routineSdkImpl.getClass();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setHandler - conditionHandler=null, actionHandler="
                                                + bixbyRoutineActionHandler);
                                final HandlerProvider handlerProvider = routineSdkImpl.d;
                                handlerProvider.a = null;
                                handlerProvider
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                final HandlerProvider handlerProvider2 = routineSdkImpl.e;
                                handlerProvider2.a = bixbyRoutineActionHandler;
                                handlerProvider2
                                        .c
                                        .keySet()
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.sdk.routines.v3.internal.HandlerProvider$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj2) {
                                                        HandlerProvider.this.b((String) obj2);
                                                    }
                                                });
                                WifiApConditionHandler wifiApConditionHandler =
                                        new WifiApConditionHandler();
                                com.samsung.android.sdk.routines.v3.internal.Log.b(
                                        "RoutineSdkImpl",
                                        "setConditionHandler - tag=hotspot_condition_tag,"
                                            + " conditionHandler="
                                                + wifiApConditionHandler);
                                HandlerProvider handlerProvider3 = routineSdkImpl.d;
                                handlerProvider3.b.put(
                                        "hotspot_condition_tag", wifiApConditionHandler);
                                handlerProvider3.b("hotspot_condition_tag");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#printSamsungAnalytics");
                                Log.i("Settings_SA", "Settings LoggingHelper init");
                                Configuration configuration = new Configuration();
                                configuration.enableAutoDeviceId = false;
                                configuration.auidType = -1;
                                configuration.trackingId = "759-399-5199102";
                                settingsApplication.getApplicationContext();
                                int i7 = Build.VERSION.SEM_PLATFORM_INT;
                                configuration.version =
                                        (i7 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                                + "."
                                                + ((i7
                                                                % EnterpriseContainerConstants
                                                                        .SYSTEM_SIGNED_APP)
                                                        / 100);
                                configuration.enableAutoDeviceId = true;
                                SamsungAnalytics.getInstanceAndConfig(
                                        settingsApplication, configuration);
                                final SALogging sALogging =
                                        SALogging.getInstance(settingsApplication);
                                sALogging.getClass();
                                int i8 = StatusLoggingUtils.$r8$clinit;
                                long j =
                                        Settings.System.getLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                0L);
                                int i9 =
                                        Settings.System.getInt(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                0);
                                long currentTimeMillis = System.currentTimeMillis();
                                Long valueOf = Long.valueOf(currentTimeMillis);
                                if (j == 0) {
                                    Settings.System.putLong(
                                            settingsApplication.getContentResolver(),
                                            "last_status_logging",
                                            currentTimeMillis);
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "Status Logging time is not set : " + valueOf);
                                }
                                if (Settings.Secure.getInt(
                                                settingsApplication.getContentResolver(),
                                                "user_setup_complete",
                                                0)
                                        == 1) {
                                    if (currentTimeMillis - j
                                            > StatusLoggingUtils.A_WEEK_TO_MILLISECOND) {
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "last_status_logging",
                                                currentTimeMillis);
                                        Settings.System.putLong(
                                                settingsApplication.getContentResolver(),
                                                "status_logging_cnt",
                                                i9 + 1);
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    Log.i(
                                            "Settings_SA_Utils",
                                            "checkLoggingInternal current time : "
                                                    + valueOf
                                                    + " saved time : "
                                                    + j);
                                } else {
                                    Log.i("Settings_SA_Utils", "SetupWizard is not completed");
                                    z2 = false;
                                }
                                if (z2) {
                                    AsyncTask.execute(
                                            new Runnable() { // from class:
                                                // com.samsung.android.settings.logging.SALogging$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SALogging.this.processStatusLogging(false);
                                                }
                                            });
                                } else {
                                    Log.i(
                                            "Settings_SA_SALogging",
                                            "It has not been a week since last logging");
                                }
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Sbixby.initialize");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby = Sbixby.getInstance();
                                int i10 = SystemProperties.getInt("ro.build.version.oneui", 0);
                                sbixby.getClass();
                                if (TextUtils.isEmpty(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                                    throw new IllegalArgumentException(
                                            "capsuleId cannot be null or empty");
                                }
                                if (Sbixby.appMetaInfoMap == null) {
                                    Sbixby.appMetaInfoMap = new HashMap();
                                }
                                ((HashMap) Sbixby.appMetaInfoMap)
                                        .put(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                new AppMetaInfo(
                                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                        i10));
                                Trace.endSection();
                                Trace.beginSection(
                                        "SettingsApplication#Sbixby.initializeBixbyActionHandler");
                                Sbixby.initialize(settingsApplication);
                                Sbixby sbixby2 = Sbixby.getInstance();
                                AODActionHandler aODActionHandler =
                                        new AODActionHandler(settingsApplication);
                                String[] strArr = AODActionHandler.ACTIONS;
                                for (int i11 = 0; i11 < 3; i11++) {
                                    String str = strArr[i11];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str, aODActionHandler);
                                }
                                EdgeActionHandler edgeActionHandler =
                                        new EdgeActionHandler(settingsApplication);
                                String[] strArr2 = EdgeActionHandler.ACTIONS;
                                for (int i12 = 0; i12 < 3; i12++) {
                                    String str2 = strArr2[i12];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str2, edgeActionHandler);
                                }
                                SettingsActionHandler settingsActionHandler =
                                        new SettingsActionHandler(settingsApplication);
                                String[] strArr3 = SettingsActionHandler.ACTIONS;
                                for (int i13 = 0; i13 < 113; i13++) {
                                    String str3 = strArr3[i13];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str3, settingsActionHandler);
                                }
                                ConnectivityActionHandler connectivityActionHandler =
                                        new ConnectivityActionHandler(settingsApplication);
                                connectivityActionHandler.mParamBundle = null;
                                String[] strArr4 = ConnectivityActionHandler.ACTIONS;
                                for (int i14 = 0; i14 < 26; i14++) {
                                    String str4 = strArr4[i14];
                                    sbixby2.getClass();
                                    Sbixby.addActionHandler(str4, connectivityActionHandler);
                                }
                                AccessibilityActionHandler accessibilityActionHandler =
                                        new AccessibilityActionHandler(settingsApplication);
                                accessibilityActionHandler.mParamBundle = null;
                                accessibilityActionHandler.mFeatureKey = null;
                                accessibilityActionHandler.mFeatureValue = null;
                                accessibilityActionHandler.mAccessibilityMenu = null;
                                HashMap hashMap = new HashMap();
                                accessibilityActionHandler.mActionHashMap = hashMap;
                                hashMap.put(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        ToggleSubscriptionDialogActivity.ARG_enable);
                                hashMap.put(
                                        "viv.accessibilityApp.DisableAccessibilityMenu", "disable");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnDirectAccess", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnFlashNotification", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch", "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnVoiceAssistant", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        "turn_on");
                                hashMap.put("viv.accessibilityApp.TurnOnMuteAllSounds", "turn_on");
                                hashMap.put(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        "turn_off");
                                hashMap.put(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentColor", "get");
                                hashMap.put("viv.accessibilityApp.GetCurrentStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetDoorBellRecordStatus", "get");
                                hashMap.put("viv.accessibilityApp.GetExclusivePopup", "get");
                                hashMap.put("viv.accessibilityApp.GetPermissionPopup", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetSupportFeature", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        "get");
                                hashMap.put("viv.accessibilityApp.GetVoiceRecorderStatus", "get");
                                hashMap.put(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue", "set");
                                hashMap.put(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        "adjust");
                                sbixby2.getClass();
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.EnableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.DisableAccessibilityMenu",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnClickAfterPointerStops",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccess",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnDirectAccessOptions",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnMuteAllSounds",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnUniversalSwitch",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnVoiceAssistant",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnAccessibilityMenuWithConfirmation",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOffFlashNotification",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetColorAdjustmentCurrentValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentColor",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDirectAccessOptionsCurrentStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDoorBellRecordStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetExclusivePopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPermissionPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetPersonalizedColorSetUpStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetSupportFeature",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetUniversalSwitchCurrentstatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetVoiceRecorderStatus",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.AdjustAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.SetAccessibilityMenuValue",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.TurnOnSoundDetector",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.GetDisclaimerPopup",
                                        accessibilityActionHandler);
                                Sbixby.addActionHandler(
                                        "viv.accessibilityApp.CheckSoundDetectorCondition",
                                        accessibilityActionHandler);
                                SystemUIActionHandler systemUIActionHandler =
                                        new SystemUIActionHandler(settingsApplication);
                                String[] strArr5 = SystemUIActionHandler.ACTIONS;
                                for (int i15 = 0; i15 < 99; i15++) {
                                    Sbixby.addActionHandler(strArr5[i15], systemUIActionHandler);
                                }
                                NotificationActionHandler notificationActionHandler =
                                        new NotificationActionHandler(settingsApplication);
                                String[] strArr6 = NotificationActionHandler.ACTIONS;
                                for (int i16 = 0; i16 < 3; i16++) {
                                    Sbixby.addActionHandler(
                                            strArr6[i16], notificationActionHandler);
                                }
                                DeviceQnAActionHandler deviceQnAActionHandler =
                                        new DeviceQnAActionHandler(settingsApplication);
                                Sbixby.addActionHandler(
                                        "CheckAvailableDeeplink", deviceQnAActionHandler);
                                Sbixby.addActionHandler(
                                        "LaunchQnADeeplink", deviceQnAActionHandler);
                                Trace.endSection();
                                return;
                            default:
                                int i17 = SettingsApplication.$r8$clinit;
                                settingsApplication.getClass();
                                Trace.beginSection("SettingsApplication#initializeClasses");
                                Trace.beginSection("SettingsApplication#Rune_Init");
                                String str5 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                SystemProperties.get("ro.csc.country_code");
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#Utils_Init");
                                Utils.readCountryCode();
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#LockUtils_Init");
                                LockUtils.isCameraCoverAttached(settingsApplication);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#KnoxUtils_Init");
                                KnoxUtils.checkKnoxCustomSettingsHiddenItem(1024);
                                Trace.endSection();
                                Trace.beginSection("SettingsApplication#BT_Init");
                                if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(
                                        settingsApplication)) {
                                    LocalBluetoothManager.getInstance(
                                            settingsApplication,
                                            com.android.settings.bluetooth.Utils.mOnInitCallback);
                                }
                                Trace.endSection();
                                Trace.endSection();
                                return;
                        }
                    }
                });
        Trace.endSection();
    }

    @Override // android.app.Application
    public final void onTerminate() {
        BackupRestoreStorageManager backupRestoreStorageManager =
                BackupRestoreStorageManager.getInstance(this);
        Iterator it = backupRestoreStorageManager.storageWrappers.entrySet().iterator();
        while (it.hasNext()) {
            String name = (String) ((Map.Entry) it.next()).getKey();
            Intrinsics.checkNotNullParameter(name, "name");
            BackupRestoreStorageManager.StorageWrapper storageWrapper =
                    (BackupRestoreStorageManager.StorageWrapper)
                            backupRestoreStorageManager.storageWrappers.remove(name);
            if (storageWrapper != null) {
                BackupHelper backupHelper = storageWrapper.storage;
                if (backupHelper instanceof KeyedObservable) {
                    ((KeyedObservable) backupHelper).removeObserver(storageWrapper);
                } else if (backupHelper instanceof Observable) {
                    ((Observable) backupHelper).removeObserver$1(storageWrapper);
                }
            }
        }
        super.onTerminate();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        AppIconCacheManager appIconCacheManager;
        super.onTrimMemory(i);
        AppIconCacheManager.getInstance().getClass();
        if (i >= 40) {
            AppIconCacheManager appIconCacheManager2 = AppIconCacheManager.sAppIconCacheManager;
            if (appIconCacheManager2 != null) {
                appIconCacheManager2.mDrawableCache.trimToSize(0);
            }
        } else if ((i >= 20 || i == 15)
                && (appIconCacheManager = AppIconCacheManager.sAppIconCacheManager) != null) {
            AppIconCacheManager.sAppIconCacheManager.mDrawableCache.trimToSize(
                    appIconCacheManager.mDrawableCache.maxSize() / 2);
        }
        if (i < 40) {
            Map map = UsingFunctionsProvider.USING_FUNCTION_CONTROLLERS;
        } else if (UsingFunctionsProvider.INSTANCE != null) {
            UsingFunctionsProvider.INSTANCE = null;
        }
    }
}
