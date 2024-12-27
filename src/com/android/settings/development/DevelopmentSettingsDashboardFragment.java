package com.android.settings.development;

import android.app.ActivityManager;
import android.app.blob.BlobStoreManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.CrossWindowBlurListeners;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.internal.display.RefreshRateSettingsUtils;
import com.android.server.display.feature.flags.Flags;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.development.autofill.AutofillCategoryController;
import com.android.settings.development.autofill.AutofillLoggingLevelPreferenceController;
import com.android.settings.development.autofill.AutofillResetOptionsPreferenceController;
import com.android.settings.development.bluetooth.AbstractBluetoothPreferenceController;
import com.android.settings.development.bluetooth.BluetoothBitPerSampleDialogPreferenceController;
import com.android.settings.development.bluetooth.BluetoothChannelModeDialogPreferenceController;
import com.android.settings.development.bluetooth.BluetoothCodecDialogPreferenceController;
import com.android.settings.development.bluetooth.BluetoothCodecListPreferenceController;
import com.android.settings.development.bluetooth.BluetoothHDAudioPreferenceController;
import com.android.settings.development.bluetooth.BluetoothQualityDialogPreferenceController;
import com.android.settings.development.bluetooth.BluetoothSampleRateDialogPreferenceController;
import com.android.settings.development.bluetooth.BluetoothStackLogPreferenceController;
import com.android.settings.development.graphicsdriver.GraphicsDriverEnableAngleAsSystemDriverController;
import com.android.settings.development.qstile.DevelopmentTiles;
import com.android.settings.development.storage.SharedDataPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.actionbar.SearchMenuController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.MinorModeUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.development.SystemPropPoker;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.development.BluetoothDisableAvcBlocklistPreferenceController;
import com.samsung.android.settings.development.BluetoothDualModeDisablePreferenceController;
import com.samsung.android.settings.development.BluetoothHfpCodecPriorityController;
import com.samsung.android.settings.development.BluetoothHfpDisablePreferenceController;
import com.samsung.android.settings.development.BluetoothSapActivationController;
import com.samsung.android.settings.development.CloPreferenceController;
import com.samsung.android.settings.development.CountrySelectionPreferenceController;
import com.samsung.android.settings.development.DevNetworkModeController;
import com.samsung.android.settings.development.DisableMessageSandboxingPreferenceController;
import com.samsung.android.settings.development.EnableMessageSandboxingImageTaggingPreferenceController;
import com.samsung.android.settings.development.ImsDataChannelPreferenceController;
import com.samsung.android.settings.development.ImsTS32ProfilePreferenceController;
import com.samsung.android.settings.development.ImsVCRBTPreferenceController;
import com.samsung.android.settings.development.MnxbPreferenceController;
import com.samsung.android.settings.development.MultiCorePacketSchedulerPreferenceController;
import com.samsung.android.settings.development.SaLoggingCheckingPreferenceController;
import com.samsung.android.settings.development.ShowExternalCodeController;
import com.samsung.android.settings.development.ShowTextIdPreferenceController;
import com.samsung.android.settings.development.SimVonrCallsPreferenceController;
import com.samsung.android.settings.development.SiopModePreferenceController;
import com.samsung.android.settings.development.StatusLoggingResultPreferenceController;
import com.samsung.android.settingslib.SettingsLibRune;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DevelopmentSettingsDashboardFragment extends RestrictedDashboardFragment
        implements CompoundButton.OnCheckedChangeListener,
                OemUnlockDialogHost,
                AdbDialogHost,
                AdbClearKeysDialogHost,
                LogPersistDialogHost,
                BluetoothRebootDialog.OnRebootDialogListener,
                AbstractBluetoothPreferenceController.Callback,
                NfcRebootDialog.OnNfcRebootDialogConfirmedListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass6(R.xml.development_settings);
    public BluetoothA2dp mBluetoothA2dp;
    public final BluetoothA2dpConfigStore mBluetoothA2dpConfigStore;
    public final AnonymousClass1 mBluetoothA2dpReceiver;
    public final AnonymousClass3 mBluetoothA2dpServiceListener;
    public final Uri mDevelopEnabled;
    public final AnonymousClass5 mDeveloperSettingsObserver;
    public final AnonymousClass1 mEnableAdbReceiver;
    public boolean mIsAvailable;
    public List mPreferenceControllers;
    public SettingsMainSwitchBar mSwitchBar;
    public DevelopmentSwitchBarController mSwitchBarController;
    public final AnonymousClass4 mSystemPropertiesChanged;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DevelopmentSettingsDashboardFragment$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {}

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (this) {
                try {
                    FragmentActivity activity =
                            DevelopmentSettingsDashboardFragment.this.getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(
                                new DevelopmentSettingsDashboardFragment$4$$ExternalSyntheticLambda0(
                                        0, this));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DevelopmentSettingsDashboardFragment$5, reason: invalid class name */
    public final class AnonymousClass5 extends ContentObserver {
        public AnonymousClass5(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            boolean isDevelopmentSettingsEnabled =
                    DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(
                            DevelopmentSettingsDashboardFragment.this.getContext());
            if (isDevelopmentSettingsEnabled
                    != ((SeslSwitchBar) DevelopmentSettingsDashboardFragment.this.mSwitchBar)
                            .mSwitch.isChecked()) {
                if (!isDevelopmentSettingsEnabled) {
                    DevelopmentSettingsDashboardFragment.this.disableDeveloperOptions();
                    DevelopmentSettingsDashboardFragment.this
                            .getActivity()
                            .runOnUiThread(
                                    new DevelopmentSettingsDashboardFragment$4$$ExternalSyntheticLambda0(
                                            1, this));
                    return;
                }
                DevelopmentSettingsDashboardFragment.this.mSwitchBar.setChecked(true);
                Iterator it =
                        ((ArrayList)
                                        DevelopmentSettingsDashboardFragment.this
                                                .mPreferenceControllers)
                                .iterator();
                while (it.hasNext()) {
                    AbstractPreferenceController abstractPreferenceController =
                            (AbstractPreferenceController) it.next();
                    if (abstractPreferenceController
                            instanceof DeveloperOptionsPreferenceController) {
                        ((DeveloperOptionsPreferenceController) abstractPreferenceController)
                                .onDeveloperOptionsEnabled();
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DevelopmentSettingsDashboardFragment$6, reason: invalid class name */
    public final class AnonymousClass6 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return DevelopmentSettingsDashboardFragment.buildPreferenceControllers(
                    context, null, null, null, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("color_temperature");
            if (!Debug.semIsProductDev()) {
                arrayList.add("bluetooth_hfp_codec_priority");
                arrayList.add("bluetooth_hfp_disable");
                arrayList.add("bluetooth_dualmode_disable");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.development.DevelopmentSettingsDashboardFragment$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.development.DevelopmentSettingsDashboardFragment$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.development.DevelopmentSettingsDashboardFragment$3] */
    public DevelopmentSettingsDashboardFragment() {
        super("no_debugging_features");
        this.mBluetoothA2dpConfigStore = new BluetoothA2dpConfigStore();
        this.mIsAvailable = true;
        this.mPreferenceControllers = new ArrayList();
        final int i = 0;
        this.mEnableAdbReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.settings.development.DevelopmentSettingsDashboardFragment.1
                    public final /* synthetic */ DevelopmentSettingsDashboardFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                for (AbstractPreferenceController abstractPreferenceController :
                                        this.this$0.mPreferenceControllers) {
                                    if (abstractPreferenceController
                                            instanceof VerifyAppsOverUsbPreferenceController) {
                                        VerifyAppsOverUsbPreferenceController
                                                verifyAppsOverUsbPreferenceController =
                                                        (VerifyAppsOverUsbPreferenceController)
                                                                abstractPreferenceController;
                                        if (verifyAppsOverUsbPreferenceController.isAvailable()) {
                                            verifyAppsOverUsbPreferenceController.updateState(
                                                    verifyAppsOverUsbPreferenceController
                                                            .mPreference);
                                        }
                                    }
                                }
                                break;
                            default:
                                Log.d(
                                        "DevSettingsDashboard",
                                        "mBluetoothA2dpReceiver.onReceive intent=" + intent);
                                if ("android.bluetooth.a2dp.profile.action.CODEC_CONFIG_CHANGED"
                                        .equals(intent.getAction())) {
                                    Log.d(
                                            "DevSettingsDashboard",
                                            "Received BluetoothCodecStatus="
                                                    + ((BluetoothCodecStatus)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.extra.CODEC_STATUS")));
                                    for (Object obj : this.this$0.mPreferenceControllers) {
                                        if (obj instanceof BluetoothServiceConnectionListener) {
                                            ((BluetoothServiceConnectionListener) obj)
                                                    .onBluetoothCodecUpdated();
                                        }
                                    }
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mBluetoothA2dpReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.settings.development.DevelopmentSettingsDashboardFragment.1
                    public final /* synthetic */ DevelopmentSettingsDashboardFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                for (AbstractPreferenceController abstractPreferenceController :
                                        this.this$0.mPreferenceControllers) {
                                    if (abstractPreferenceController
                                            instanceof VerifyAppsOverUsbPreferenceController) {
                                        VerifyAppsOverUsbPreferenceController
                                                verifyAppsOverUsbPreferenceController =
                                                        (VerifyAppsOverUsbPreferenceController)
                                                                abstractPreferenceController;
                                        if (verifyAppsOverUsbPreferenceController.isAvailable()) {
                                            verifyAppsOverUsbPreferenceController.updateState(
                                                    verifyAppsOverUsbPreferenceController
                                                            .mPreference);
                                        }
                                    }
                                }
                                break;
                            default:
                                Log.d(
                                        "DevSettingsDashboard",
                                        "mBluetoothA2dpReceiver.onReceive intent=" + intent);
                                if ("android.bluetooth.a2dp.profile.action.CODEC_CONFIG_CHANGED"
                                        .equals(intent.getAction())) {
                                    Log.d(
                                            "DevSettingsDashboard",
                                            "Received BluetoothCodecStatus="
                                                    + ((BluetoothCodecStatus)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.extra.CODEC_STATUS")));
                                    for (Object obj : this.this$0.mPreferenceControllers) {
                                        if (obj instanceof BluetoothServiceConnectionListener) {
                                            ((BluetoothServiceConnectionListener) obj)
                                                    .onBluetoothCodecUpdated();
                                        }
                                    }
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mBluetoothA2dpServiceListener =
                new BluetoothProfile
                        .ServiceListener() { // from class:
                                             // com.android.settings.development.DevelopmentSettingsDashboardFragment.3
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceConnected(
                            int i3, BluetoothProfile bluetoothProfile) {
                        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment;
                        synchronized (
                                DevelopmentSettingsDashboardFragment.this
                                        .mBluetoothA2dpConfigStore) {
                            developmentSettingsDashboardFragment =
                                    DevelopmentSettingsDashboardFragment.this;
                            developmentSettingsDashboardFragment.mBluetoothA2dp =
                                    (BluetoothA2dp) bluetoothProfile;
                        }
                        for (Object obj :
                                developmentSettingsDashboardFragment.mPreferenceControllers) {
                            if (obj instanceof BluetoothServiceConnectionListener) {
                                ((BluetoothServiceConnectionListener) obj)
                                        .onBluetoothServiceConnected(
                                                DevelopmentSettingsDashboardFragment.this
                                                        .mBluetoothA2dp);
                            }
                        }
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceDisconnected(int i3) {
                        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment;
                        synchronized (
                                DevelopmentSettingsDashboardFragment.this
                                        .mBluetoothA2dpConfigStore) {
                            developmentSettingsDashboardFragment =
                                    DevelopmentSettingsDashboardFragment.this;
                            developmentSettingsDashboardFragment.mBluetoothA2dp = null;
                        }
                        for (Object obj :
                                developmentSettingsDashboardFragment.mPreferenceControllers) {
                            if (obj instanceof BluetoothServiceConnectionListener) {
                                ((BluetoothServiceConnectionListener) obj)
                                        .onBluetoothServiceDisconnected();
                            }
                        }
                    }
                };
        this.mSystemPropertiesChanged = new AnonymousClass4();
        this.mDevelopEnabled = Settings.Global.getUriFor("development_settings_enabled");
        this.mDeveloperSettingsObserver = new AnonymousClass5(new Handler(Looper.getMainLooper()));
    }

    public static List buildPreferenceControllers(
            Context context,
            FragmentActivity fragmentActivity,
            Lifecycle lifecycle,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment,
            BluetoothA2dpConfigStore bluetoothA2dpConfigStore) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MemoryUsagePreferenceController(context));
        arrayList.add(new BugReportPreferenceController(context));
        arrayList.add(new BugReportHandlerPreferenceController(context));
        arrayList.add(new SystemServerHeapDumpPreferenceController(context));
        arrayList.add(new DevelopmentMemtagPagePreferenceController(context));
        arrayList.add(new LocalBackupPasswordPreferenceController(context));
        StayAwakePreferenceController stayAwakePreferenceController =
                new StayAwakePreferenceController(context);
        if (lifecycle != null) {
            lifecycle.addObserver(stayAwakePreferenceController);
        }
        arrayList.add(stayAwakePreferenceController);
        arrayList.add(new HdcpCheckingPreferenceController(context));
        arrayList.add(
                new BluetoothSnoopLogPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(new BluetoothStackLogPreferenceController(context));
        arrayList.add(
                new DefaultLaunchPreferenceController(context, "snoop_logger_filters_dashboard"));
        arrayList.add(new BluetoothSnoopLogFilterProfilePbapPreferenceController(context));
        arrayList.add(new BluetoothSnoopLogFilterProfileMapPreferenceController(context));
        arrayList.add(
                new OemUnlockPreferenceController(
                        context, fragmentActivity, developmentSettingsDashboardFragment));
        arrayList.add(
                new Enable16kPagesPreferenceController(
                        context, developmentSettingsDashboardFragment));
        PictureColorModePreferenceController pictureColorModePreferenceController =
                new PictureColorModePreferenceController(context);
        if (lifecycle != null) {
            lifecycle.addObserver(pictureColorModePreferenceController);
        }
        arrayList.add(pictureColorModePreferenceController);
        arrayList.add(new WebViewAppPreferenceController(context));
        arrayList.add(new CoolColorTemperaturePreferenceController(context));
        arrayList.add(new DisableAutomaticUpdatesPreferenceController(context));
        arrayList.add(new ShowTextIdPreferenceController(context));
        arrayList.add(new SaLoggingCheckingPreferenceController(context));
        arrayList.add(new StatusLoggingResultPreferenceController(context));
        if (SettingsLibRune.MENU_SHOW_EXTERNAL_CODE) {
            arrayList.add(new ShowExternalCodeController(context));
        }
        arrayList.add(
                new AdbPreferenceController(
                        context, developmentSettingsDashboardFragment, lifecycle));
        arrayList.add(
                new ClearAdbKeysPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(new AtcmdPreferenceController(context));
        arrayList.add(new WirelessDebuggingPreferenceController(context, lifecycle));
        arrayList.add(new AdbAuthorizationTimeoutPreferenceController(context));
        LocalTerminalPreferenceController localTerminalPreferenceController =
                new LocalTerminalPreferenceController(context);
        localTerminalPreferenceController.mUserManager =
                (UserManager) context.getSystemService("user");
        arrayList.add(localTerminalPreferenceController);
        arrayList.add(new BugReportInPowerPreferenceController(context));
        arrayList.add(new AutomaticSystemServerHeapDumpPreferenceController(context));
        arrayList.add(
                new MockLocationAppPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(new MockModemPreferenceController(context));
        arrayList.add(new DebugViewAttributesPreferenceController(context));
        arrayList.add(
                new SelectDebugAppPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(new WaitForDebuggerPreferenceController(context));
        arrayList.add(new EnableGpuDebugLayersPreferenceController(context));
        arrayList.add(
                new GraphicsDriverEnableAngleAsSystemDriverController(
                        context, developmentSettingsDashboardFragment));
        ForcePeakRefreshRatePreferenceController forcePeakRefreshRatePreferenceController =
                new ForcePeakRefreshRatePreferenceController(context);
        forcePeakRefreshRatePreferenceController.mPeakRefreshRate =
                Flags.backUpSmoothDisplayAndForcePeakRefreshRate()
                        ? RefreshRateSettingsUtils.findHighestRefreshRateAmongAllDisplays(context)
                        : RefreshRateSettingsUtils.findHighestRefreshRateForDefaultDisplay(context);
        Log.d(
                "ForcePeakRefreshRateCtr",
                "DEFAULT_REFRESH_RATE : 60.0 mPeakRefreshRate : "
                        + forcePeakRefreshRatePreferenceController.mPeakRefreshRate);
        arrayList.add(forcePeakRefreshRatePreferenceController);
        SiopModePreferenceController siopModePreferenceController =
                new SiopModePreferenceController(context);
        siopModePreferenceController.mContext = context;
        arrayList.add(siopModePreferenceController);
        EnableVerboseVendorLoggingPreferenceController
                enableVerboseVendorLoggingPreferenceController =
                        new EnableVerboseVendorLoggingPreferenceController(context);
        enableVerboseVendorLoggingPreferenceController.mDumpstateHalVersion = -1;
        arrayList.add(enableVerboseVendorLoggingPreferenceController);
        arrayList.add(new VerifyAppsOverUsbPreferenceController(context));
        arrayList.add(new ArtVerifierPreferenceController(context));
        arrayList.add(new LogdSizePreferenceController(context));
        arrayList.add(new EnableSamsungLogPreferenceController(context));
        arrayList.add(
                new LogPersistPreferenceController(
                        context, developmentSettingsDashboardFragment, lifecycle));
        arrayList.add(new CameraLaserSensorPreferenceController(context));
        arrayList.add(new WifiDisplayCertificationPreferenceController(context));
        arrayList.add(new WifiVerboseLoggingPreferenceController(context));
        arrayList.add(new WifiScanThrottlingPreferenceController(context));
        arrayList.add(new WifiNonPersistentMacRandomizationPreferenceController(context));
        arrayList.add(new MobileDataAlwaysOnPreferenceController(context));
        arrayList.add(new MnxbPreferenceController(context));
        arrayList.add(new CloPreferenceController(context));
        arrayList.add(new MultiCorePacketSchedulerPreferenceController(context));
        arrayList.add(new TetheringHardwareAccelPreferenceController(context));
        BluetoothDeviceNoNamePreferenceController bluetoothDeviceNoNamePreferenceController =
                new BluetoothDeviceNoNamePreferenceController(context);
        bluetoothDeviceNoNamePreferenceController.mContext = context;
        arrayList.add(bluetoothDeviceNoNamePreferenceController);
        BluetoothDeviceNoInterestingUuidPreferenceController
                bluetoothDeviceNoInterestingUuidPreferenceController =
                        new BluetoothDeviceNoInterestingUuidPreferenceController(context);
        bluetoothDeviceNoInterestingUuidPreferenceController.mContext = context;
        arrayList.add(bluetoothDeviceNoInterestingUuidPreferenceController);
        arrayList.add(new BluetoothAbsoluteVolumePreferenceController(context));
        arrayList.add(new BluetoothDisableAvcBlocklistPreferenceController(context));
        arrayList.add(new BluetoothAvrcpVersionPreferenceController(context));
        arrayList.add(
                new BluetoothLeAudioPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(
                new BluetoothLeAudioModePreferenceController(
                        context, developmentSettingsDashboardFragment));
        BluetoothLeAudioDeviceDetailsPreferenceController
                bluetoothLeAudioDeviceDetailsPreferenceController =
                        new BluetoothLeAudioDeviceDetailsPreferenceController(context);
        bluetoothLeAudioDeviceDetailsPreferenceController.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
        bluetoothLeAudioDeviceDetailsPreferenceController.mLeAudioEnabledByDefault =
                SystemProperties.getBoolean(
                        "ro.bluetooth.leaudio.le_audio_connection_by_default", true);
        arrayList.add(bluetoothLeAudioDeviceDetailsPreferenceController);
        BluetoothLeAudioAllowListPreferenceController
                bluetoothLeAudioAllowListPreferenceController =
                        new BluetoothLeAudioAllowListPreferenceController(context);
        bluetoothLeAudioAllowListPreferenceController.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
        bluetoothLeAudioAllowListPreferenceController.mLeAudioConnectionByDefault =
                SystemProperties.getBoolean(
                        "ro.bluetooth.leaudio.le_audio_connection_by_default", true);
        arrayList.add(bluetoothLeAudioAllowListPreferenceController);
        BluetoothA2dpHwOffloadPreferenceController bluetoothA2dpHwOffloadPreferenceController =
                new BluetoothA2dpHwOffloadPreferenceController(context);
        bluetoothA2dpHwOffloadPreferenceController.mChanged = false;
        arrayList.add(bluetoothA2dpHwOffloadPreferenceController);
        arrayList.add(
                new BluetoothLeAudioHwOffloadPreferenceController(
                        context, developmentSettingsDashboardFragment));
        BluetoothMaxConnectedAudioDevicesPreferenceController
                bluetoothMaxConnectedAudioDevicesPreferenceController =
                        new BluetoothMaxConnectedAudioDevicesPreferenceController(context);
        bluetoothMaxConnectedAudioDevicesPreferenceController.mDefaultMaxConnectedAudioDevices = 0;
        bluetoothMaxConnectedAudioDevicesPreferenceController.mDefaultMaxConnectedAudioDevices =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class))
                        .getAdapter()
                        .getMaxConnectedAudioDevices();
        arrayList.add(bluetoothMaxConnectedAudioDevicesPreferenceController);
        arrayList.add(new BluetoothHfpCodecPriorityController(context));
        arrayList.add(new BluetoothHfpDisablePreferenceController(context));
        arrayList.add(new BluetoothDualModeDisablePreferenceController(context));
        arrayList.add(new BluetoothSapActivationController(context));
        arrayList.add(new DevNetworkModeController(context, "dev_sim_network_mode0"));
        arrayList.add(new DevNetworkModeController(context, "dev_sim_network_mode1"));
        arrayList.add(new NetworkModeOf5gTypeController(context));
        arrayList.add(new SimVonrCallsPreferenceController(context, "sim_vonr_calls0"));
        arrayList.add(new SimVonrCallsPreferenceController(context, "sim_vonr_calls1"));
        arrayList.add(new ImsDataChannelPreferenceController(context));
        arrayList.add(new ImsVCRBTPreferenceController(context));
        arrayList.add(new ImsTS32ProfilePreferenceController(context));
        arrayList.add(
                new NfcSnoopLogPreferenceController(context, developmentSettingsDashboardFragment));
        arrayList.add(
                new NfcVerboseVendorLogPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(new SatelliteCommunicationPreferenceController(context));
        arrayList.add(new ShowTapsPreferenceController(context));
        arrayList.add(new PointerLocationPreferenceController(context));
        arrayList.add(new ShowKeyPressesPreferenceController(context));
        arrayList.add(new ShowSurfaceUpdatesPreferenceController(context));
        arrayList.add(new ShowLayoutBoundsPreferenceController(context));
        arrayList.add(new ShowHdrSdrRatioPreferenceController(context));
        arrayList.add(new ShowRefreshRatePreferenceController(context));
        arrayList.add(new RtlLayoutPreferenceController(context));
        arrayList.add(new WindowAnimationScalePreferenceController(context));
        arrayList.add(
                new EmulateDisplayCutoutPreferenceController(
                        context,
                        context.getPackageManager(),
                        IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"))));
        arrayList.add(new TransparentNavigationBarPreferenceController(context));
        arrayList.add(new TransitionAnimationScalePreferenceController(context));
        arrayList.add(new AnimatorDurationScalePreferenceController(context));
        arrayList.add(new SecondaryDisplayPreferenceController(context));
        arrayList.add(new GpuViewUpdatesPreferenceController(context));
        arrayList.add(new HardwareLayersUpdatesPreferenceController(context));
        arrayList.add(new DebugGpuOverdrawPreferenceController(context));
        arrayList.add(new DebugNonRectClipOperationsPreferenceController(context));
        arrayList.add(new GameDefaultFrameRatePreferenceController(context));
        arrayList.add(new ForceDarkPreferenceController(context));
        arrayList.add(
                new EnableBlursPreferenceController(
                        context, CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED));
        arrayList.add(new ForceMSAAPreferenceController(context));
        arrayList.add(new HardwareOverlaysPreferenceController(context));
        arrayList.add(new SimulateColorSpacePreferenceController(context));
        arrayList.add(new UsbAudioRoutingPreferenceController(context));
        arrayList.add(new StrictModePreferenceController(context));
        arrayList.add(new ProfileGpuRenderingPreferenceController(context));
        arrayList.add(new KeepActivitiesPreferenceController(context));
        arrayList.add(new BackgroundProcessLimitPreferenceController(context));
        arrayList.add(new CachedAppsFreezerPreferenceController(context));
        arrayList.add(new ShowFirstCrashDialogPreferenceController(context));
        arrayList.add(new AppsNotRespondingPreferenceController(context));
        arrayList.add(new NotificationChannelWarningsPreferenceController(context));
        arrayList.add(new AllowAppsOnExternalPreferenceController(context));
        arrayList.add(new ResizableActivityPreferenceController(context));
        arrayList.add(
                new FreeformWindowsPreferenceController(
                        context, developmentSettingsDashboardFragment));
        arrayList.add(
                new DesktopModePreferenceController(context, developmentSettingsDashboardFragment));
        arrayList.add(new NonResizableMultiWindowPreferenceController(context));
        arrayList.add(new ShortcutManagerThrottlingPreferenceController(context));
        arrayList.add(new EnableGnssRawMeasFullTrackingPreferenceController(context));
        arrayList.add(new DefaultLaunchPreferenceController(context, "running_apps"));
        arrayList.add(new DefaultLaunchPreferenceController(context, "demo_mode"));
        arrayList.add(new DefaultLaunchPreferenceController(context, "quick_settings_tiles"));
        arrayList.add(new DefaultLaunchPreferenceController(context, "feature_flags_dashboard"));
        arrayList.add(new DefaultUsbConfigurationPreferenceController(context));
        arrayList.add(new DefaultLaunchPreferenceController(context, "density"));
        arrayList.add(new DefaultLaunchPreferenceController(context, "background_check"));
        arrayList.add(new DefaultLaunchPreferenceController(context, "inactive_apps"));
        arrayList.add(new AutofillCategoryController(context, lifecycle));
        arrayList.add(new AutofillLoggingLevelPreferenceController(context, lifecycle));
        arrayList.add(new AutofillResetOptionsPreferenceController(context));
        arrayList.add(
                new BluetoothCodecDialogPreferenceController(
                        context,
                        lifecycle,
                        bluetoothA2dpConfigStore,
                        developmentSettingsDashboardFragment));
        arrayList.add(
                new BluetoothCodecListPreferenceController(
                        context, lifecycle, bluetoothA2dpConfigStore));
        arrayList.add(
                new BluetoothSampleRateDialogPreferenceController(
                        context, lifecycle, bluetoothA2dpConfigStore));
        arrayList.add(
                new BluetoothBitPerSampleDialogPreferenceController(
                        context, lifecycle, bluetoothA2dpConfigStore));
        arrayList.add(
                new BluetoothQualityDialogPreferenceController(
                        context, lifecycle, bluetoothA2dpConfigStore));
        arrayList.add(
                new BluetoothChannelModeDialogPreferenceController(
                        context, lifecycle, bluetoothA2dpConfigStore));
        arrayList.add(
                new BluetoothHDAudioPreferenceController(
                        context, lifecycle, developmentSettingsDashboardFragment));
        SharedDataPreferenceController sharedDataPreferenceController =
                new SharedDataPreferenceController(context);
        sharedDataPreferenceController.mBlobStoreManager =
                (BlobStoreManager) context.getSystemService(BlobStoreManager.class);
        arrayList.add(sharedDataPreferenceController);
        arrayList.add(new OverlaySettingsPreferenceController(context));
        arrayList.add(new StylusHandwritingPreferenceController(context));
        arrayList.add(new IngressRateLimitPreferenceController(context));
        arrayList.add(new DisableMessageSandboxingPreferenceController(context, lifecycle));
        arrayList.add(new EnableMessageSandboxingImageTaggingPreferenceController(context));
        if (developmentSettingsDashboardFragment != null) {
            arrayList.add(
                    new BackAnimationPreferenceController(
                            context, developmentSettingsDashboardFragment));
        }
        arrayList.add(new PhantomProcessPreferenceController(context));
        arrayList.add(new ForceEnableNotesRolePreferenceController(context));
        arrayList.add(
                new GrammaticalGenderPreferenceController(context, ActivityManager.getService()));
        arrayList.add(new SensitiveContentProtectionPreferenceController(context));
        CountrySelectionPreferenceController countrySelectionPreferenceController =
                new CountrySelectionPreferenceController(context);
        SemLog.d(
                "CountrySelectionPreferenceController",
                "CountrySelectionPreferenceController : tss_country_chooser");
        arrayList.add(countrySelectionPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            this.mPreferenceControllers = new ArrayList();
            return null;
        }
        List buildPreferenceControllers =
                buildPreferenceControllers(
                        context,
                        getActivity(),
                        getSettingsLifecycle(),
                        this,
                        new BluetoothA2dpConfigStore());
        this.mPreferenceControllers = buildPreferenceControllers;
        return buildPreferenceControllers;
    }

    public final void disableDeveloperOptions() {
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        if (Enable16kUtils.isPageAgnosticModeOn(getContext())) {
            Context context = getContext();
            context.startActivityAsUser(
                    new Intent(context, (Class<?>) PageAgnosticWarningActivity.class),
                    UserHandle.SYSTEM);
            this.mSwitchBar.setChecked(true);
            return;
        }
        DevelopmentSettingsEnabler.setDevelopmentSettingsEnabled(getContext(), false);
        SystemPropPoker systemPropPoker = SystemPropPoker.sInstance;
        systemPropPoker.mBlockPokes = true;
        Iterator it = ((ArrayList) this.mPreferenceControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController instanceof DeveloperOptionsPreferenceController) {
                ((DeveloperOptionsPreferenceController) abstractPreferenceController)
                        .onDeveloperOptionsDisabled();
            }
        }
        systemPropPoker.mBlockPokes = false;
        systemPropPoker.poke();
    }

    public final void enableDeveloperOptions() {
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        DevelopmentSettingsEnabler.setDevelopmentSettingsEnabled(getContext(), true);
        if (MinorModeUtils.hasCHNMinorModePreventDeveloperOptionsFromBeingTurnedOn(getContext())) {
            this.mSwitchBar.setChecked(false);
            return;
        }
        Iterator it = ((ArrayList) this.mPreferenceControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController instanceof DeveloperOptionsPreferenceController) {
                ((DeveloperOptionsPreferenceController) abstractPreferenceController)
                        .onDeveloperOptionsEnabled();
            }
        }
    }

    public <T extends AbstractPreferenceController> T getDevelopmentOptionsController(
            Class<T> cls) {
        return (T) use(cls);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DevSettingsDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 39;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        StringBuilder sb = Utils.sBuilder;
        return ActivityManager.isUserAMonkey()
                ? R.xml.placeholder_prefs
                : R.xml.development_settings;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mOnlyAvailableForAdmins = true;
        if (isUiRestricted() || !WizardManagerHelper.isDeviceProvisioned(getActivity())) {
            this.mIsAvailable = false;
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(R.string.development_settings_not_available);
            }
            getPreferenceScreen().removeAll();
            return;
        }
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        if (this.mSwitchBarController != null) {
            getSettingsLifecycle().removeObserver(this.mSwitchBarController);
        }
        this.mSwitchBarController =
                new DevelopmentSwitchBarController(
                        this, this.mSwitchBar, this.mIsAvailable, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z = false;
        for (Object obj : this.mPreferenceControllers) {
            if (obj instanceof OnActivityResultListener) {
                z |= ((OnActivityResultListener) obj).onActivityResult(i, i2, intent);
            }
        }
        if (z) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x007b, code lost:

       if (r0 != false) goto L25;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ce, code lost:

       if (android.text.TextUtils.isEmpty(android.os.SystemProperties.get("persist.graphics.egl", com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE)) != false) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b9, code lost:

       if (r8 == false) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x009b, code lost:

       if (r0 == false) goto L42;
    */
    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCheckedChanged(android.widget.CompoundButton r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.development.DevelopmentSettingsDashboardFragment.onCheckedChanged(android.widget.CompoundButton,"
                    + " boolean):void");
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getSettingsLifecycle().addObserver(new SearchMenuController(this, 39));
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            getActivity().finish();
            return;
        }
        Context requireContext = requireContext();
        if (!((UserManager) getSystemService("user")).isAdminUser()) {
            Toast.makeText(requireContext, R.string.dev_settings_available_to_admin_only_warning, 0)
                    .show();
            finish();
        } else {
            if (DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(requireContext)) {
                return;
            }
            Toast.makeText(requireContext, R.string.dev_settings_disabled_warning, 0).show();
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(
                        this.mEnableAdbReceiver,
                        new IntentFilter(
                                "com.android.settingslib.development.AbstractEnableAdbController.ENABLE_ADB_STATE_CHANGED"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CODEC_CONFIG_CHANGED");
        getActivity().registerReceiver(this.mBluetoothA2dpReceiver, intentFilter);
        SystemProperties.addChangeCallback(this.mSystemPropertiesChanged);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(getActivity(), this.mBluetoothA2dpServiceListener, 2);
        }
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (android.view.flags.Flags.sensitiveContentAppProtectionApi()) {
            onCreateView.setContentSensitivity(1);
        }
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.mEnableAdbReceiver);
        getActivity().unregisterReceiver(this.mBluetoothA2dpReceiver);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.closeProfileProxy(2, this.mBluetoothA2dp);
            this.mBluetoothA2dp = null;
        }
        SystemProperties.removeChangeCallback(this.mSystemPropertiesChanged);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        this.mDevelopEnabled, false, this.mDeveloperSettingsObserver);
        if (!DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(getContext())) {
            disableDeveloperOptions();
            return;
        }
        enableDeveloperOptions();
        Intent intent = getActivity().getIntent();
        if (intent == null
                || !TextUtils.equals(
                        "android.service.quicksettings.action.QS_TILE_PREFERENCES",
                        intent.getAction())) {
            return;
        }
        Log.d("DevSettingsDashboard", "Developer options started from qstile long-press");
        ComponentName componentName =
                (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName != null
                && DevelopmentTiles.WirelessDebugging.class
                        .getName()
                        .equals(componentName.getClassName())
                && ((WirelessDebuggingPreferenceController)
                                getDevelopmentOptionsController(
                                        WirelessDebuggingPreferenceController.class))
                        .isAvailable()) {
            Log.d("DevSettingsDashboard", "Long press from wireless debugging qstile");
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
            String name = WirelessDebuggingFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = 1831;
            subSettingLauncher.launch();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContext()
                .getContentResolver()
                .unregisterContentObserver(this.mDeveloperSettingsObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean shouldSkipForInitialSUW() {
        return true;
    }
}
