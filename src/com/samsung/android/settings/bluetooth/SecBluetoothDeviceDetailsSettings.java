package com.samsung.android.settings.bluetooth;

import android.accounts.AccountManager;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.bluetooth.BluetoothFeatureProviderImpl;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.slices.BlockingSlicePrefController;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothBroadcastAssistantController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailNoiseController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsAdvancedController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsAudioTypeController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsGroupController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsHeaderController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsHearingAidController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsHearingAidsPresetsController;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController;
import com.samsung.android.settings.bluetooth.controller.SecLeAudioBluetoothDetailsHeaderController;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver;
import com.sec.ims.configuration.DATA;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothDeviceDetailsSettings extends RestrictedDashboardFragment
        implements CachedBluetoothDevice.Callback, SemBluetoothCallback, BluetoothCallback {
    public static final boolean DBG = Debug.semIsProductDev();
    public boolean isFinished;
    public SecBluetoothDetailsAdvancedController mAdvancedController;
    public SecBluetoothDetailsAudioTypeController mAudioTypeController;
    public RelativeLayout mBottomButtonLayout;
    public BottomNavigationView mBottomNavigationView;
    public SecBluetoothBroadcastAssistantController mBroadcastAssistantController;
    public CachedBluetoothDevice mCachedDevice;
    public SecBluetoothDetailsCodecController mCodecController;
    public String mDeviceAddress;
    public SecBluetoothDetailsGroupController mGroupController;
    public final Handler mHandler;
    public SecBluetoothDetailsHearingAidController mHearingAidController;
    public boolean mIsCalledFromSetting;
    public LocalBluetoothManager mManager;
    public NavigationBarItemView mNaviButton;
    public Drawable mNaviDrawable;
    public SecBluetoothDetailNoiseController mNoiseController;
    public final AnonymousClass2 mOnGlobalLayoutListener;
    public SecBluetoothDetailsProfilesController mProfilesController;
    public final AnonymousClass1 mReceiver;
    public boolean mReceiverRegistered;
    public int mRenameColor;
    public String mScreenId;
    public SettingsActivity mSettingsActivity;
    public BluetoothSmepReceiver mSmepReceiver;
    public AlertDialog mUnpairDialog;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings$4, reason: invalid class name */
    public final class AnonymousClass4 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings$2] */
    public SecBluetoothDeviceDetailsSettings() {
        super("no_config_bluetooth");
        this.mRenameColor = 0;
        this.mIsCalledFromSetting = false;
        this.isFinished = false;
        this.mReceiverRegistered = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                            if (intent.getIntExtra(
                                            "android.bluetooth.adapter.extra.STATE",
                                            Integer.MIN_VALUE)
                                    == 10) {
                                SecBluetoothDeviceDetailsSettings
                                        secBluetoothDeviceDetailsSettings =
                                                SecBluetoothDeviceDetailsSettings.this;
                                boolean z = SecBluetoothDeviceDetailsSettings.DBG;
                                secBluetoothDeviceDetailsSettings.finishActivity();
                                return;
                            }
                            return;
                        }
                        if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED"
                                .equals(action)) {
                            BluetoothDevice bluetoothDevice =
                                    (BluetoothDevice)
                                            intent.getParcelableExtra(
                                                    "android.bluetooth.device.extra.DEVICE");
                            byte[] byteArrayExtra =
                                    intent.getByteArrayExtra(
                                            "com.samsung.bluetooth.device.extra.META_DATA");
                            if (byteArrayExtra == null || byteArrayExtra.length != 4) {
                                return;
                            }
                            Log.d(
                                    "SecBluetoothDeviceDetailsSettings",
                                    "data = " + Arrays.toString(byteArrayExtra));
                            if ((((byteArrayExtra[0] & 255) | ((byteArrayExtra[1] & 255) << 8))
                                            & CustomDeviceManager.QUICK_PANEL_ALL)
                                    == SmepTag.CUSTOM_CONNECTED_WITH_PROFILE.getTag()) {
                                boolean z2 = byteArrayExtra[3] == 1;
                                if (BluetoothUtils$$ExternalSyntheticOutline0.m(
                                                        SmepTag.CUSTOM_SUPPORT_RENAME,
                                                        bluetoothDevice)
                                                == null
                                        || byteArrayExtra.length <= 3
                                        || byteArrayExtra[3] <= 0) {
                                    return;
                                }
                                SecBluetoothDeviceDetailsSettings
                                        secBluetoothDeviceDetailsSettings2 =
                                                SecBluetoothDeviceDetailsSettings.this;
                                boolean z3 = SecBluetoothDeviceDetailsSettings.DBG;
                                secBluetoothDeviceDetailsSettings2.setEnabledMenuItem(z2);
                            }
                        }
                    }
                };
        this.mOnGlobalLayoutListener =
                new ViewTreeObserver.OnGlobalLayoutListener() { // from class:
                    // com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0088  */
                    /* JADX WARN: Removed duplicated region for block: B:18:0x009e  */
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onGlobalLayout() {
                        /*
                            r7 = this;
                            com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings r0 = com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.this
                            android.view.View r0 = r0.getView()
                            if (r0 != 0) goto L9
                            return
                        L9:
                            int r1 = r0.getWidth()
                            if (r1 > 0) goto L10
                            return
                        L10:
                            com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings r1 = com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.this
                            int r2 = r0.getWidth()
                            com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings r3 = com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.this
                            boolean r4 = com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.DBG
                            android.content.Context r3 = r3.getContext()
                            r4 = 16843709(0x10103bd, float:2.369624E-38)
                            r5 = 16843710(0x10103be, float:2.3696243E-38)
                            int[] r4 = new int[]{r4, r5}
                            android.content.res.TypedArray r3 = r3.obtainStyledAttributes(r4)
                            r4 = 0
                            int r5 = r3.getDimensionPixelSize(r4, r4)
                            r6 = 1
                            int r4 = r3.getDimensionPixelSize(r6, r4)
                            int r4 = r4 + r5
                            r3.recycle()
                            int r2 = r2 - r4
                            r1.getClass()
                            com.android.settings.overlay.FeatureFactoryImpl r3 = com.android.settings.overlay.FeatureFactoryImpl._factory
                            if (r3 == 0) goto Lc8
                            com.android.settings.bluetooth.BluetoothFeatureProviderImpl r3 = r3.getBluetoothFeatureProvider()
                            java.lang.String r4 = "settings_ui"
                            java.lang.String r5 = "bt_slice_settings_enabled"
                            boolean r4 = android.provider.DeviceConfig.getBoolean(r4, r5, r6)
                            com.android.settingslib.bluetooth.CachedBluetoothDevice r5 = r1.mCachedDevice
                            android.bluetooth.BluetoothDevice r5 = r5.mDevice
                            r3.getClass()
                            java.lang.String r3 = com.android.settingslib.bluetooth.BluetoothUtils.getControlUriMetaData(r5)
                            boolean r5 = android.text.TextUtils.isEmpty(r3)
                            r6 = 0
                            if (r5 != 0) goto L7d
                            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.NullPointerException -> L75
                            r5.<init>()     // Catch: java.lang.NullPointerException -> L75
                            r5.append(r3)     // Catch: java.lang.NullPointerException -> L75
                            r5.append(r2)     // Catch: java.lang.NullPointerException -> L75
                            java.lang.String r2 = r5.toString()     // Catch: java.lang.NullPointerException -> L75
                            android.net.Uri r2 = android.net.Uri.parse(r2)     // Catch: java.lang.NullPointerException -> L75
                            goto L7e
                        L75:
                            java.lang.String r2 = "SecBluetoothDeviceDetailsSettings"
                            java.lang.String r3 = "unable to parse uri"
                            android.util.Log.d(r2, r3)
                        L7d:
                            r2 = r6
                        L7e:
                            java.lang.Class<com.android.settings.slices.SlicePreferenceController> r3 = com.android.settings.slices.SlicePreferenceController.class
                            com.android.settingslib.core.AbstractPreferenceController r3 = r1.use(r3)
                            com.android.settings.slices.SlicePreferenceController r3 = (com.android.settings.slices.SlicePreferenceController) r3
                            if (r4 == 0) goto L89
                            r6 = r2
                        L89:
                            r3.setSliceUri(r6)
                            r3.onStart()
                            androidx.preference.PreferenceScreen r2 = r1.getPreferenceScreen()
                            r3.displayPreference(r2)
                            com.android.settingslib.bluetooth.CachedBluetoothDevice r2 = r1.mCachedDevice
                            boolean r2 = r2.checkHearingAidByUuid()
                            if (r2 == 0) goto Lbc
                            java.lang.Class<com.samsung.android.settings.bluetooth.controller.SecLeAudioBluetoothDetailsHeaderController> r2 = com.samsung.android.settings.bluetooth.controller.SecLeAudioBluetoothDetailsHeaderController.class
                            com.android.settingslib.core.AbstractPreferenceController r2 = r1.use(r2)
                            com.samsung.android.settings.bluetooth.controller.SecLeAudioBluetoothDetailsHeaderController r2 = (com.samsung.android.settings.bluetooth.controller.SecLeAudioBluetoothDetailsHeaderController) r2
                            androidx.preference.PreferenceScreen r3 = r1.getPreferenceScreen()
                            r2.displayPreference(r3)
                            java.lang.Class<com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController> r2 = com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController.class
                            com.android.settingslib.core.AbstractPreferenceController r2 = r1.use(r2)
                            com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController r2 = (com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController) r2
                            androidx.preference.PreferenceScreen r1 = r1.getPreferenceScreen()
                            r2.displayPreference(r1)
                        Lbc:
                            android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                            com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings r7 = com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.this
                            com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings$2 r7 = r7.mOnGlobalLayoutListener
                            r0.removeOnGlobalLayoutListener(r7)
                            return
                        Lc8:
                            java.lang.UnsupportedOperationException r7 = new java.lang.UnsupportedOperationException
                            java.lang.String r0 = "No feature factory configured"
                            r7.<init>(r0)
                            throw r7
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.AnonymousClass2.onGlobalLayout():void");
                    }
                };
        this.mHandler =
                new Handler(
                        new Handler
                                .Callback() { // from class:
                                              // com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.6
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                CachedBluetoothDevice cachedBluetoothDevice;
                                if (message.what == 1) {
                                    SecBluetoothDetailNoiseController
                                            secBluetoothDetailNoiseController =
                                                    SecBluetoothDeviceDetailsSettings.this
                                                            .mNoiseController;
                                    if (secBluetoothDetailNoiseController != null) {
                                        secBluetoothDetailNoiseController.setDelayEnabled();
                                    }
                                    SecBluetoothDetailsAdvancedController
                                            secBluetoothDetailsAdvancedController =
                                                    SecBluetoothDeviceDetailsSettings.this
                                                            .mAdvancedController;
                                    if (secBluetoothDetailsAdvancedController != null
                                            && secBluetoothDetailsAdvancedController
                                                            .mAdvancedContainer
                                                    != null
                                            && (cachedBluetoothDevice =
                                                            secBluetoothDetailsAdvancedController
                                                                    .mCachedDevice)
                                                    != null) {
                                        boolean isConnected = cachedBluetoothDevice.isConnected();
                                        secBluetoothDetailsAdvancedController.mAdvancedContainer
                                                .setEnabled(isConnected);
                                        secBluetoothDetailsAdvancedController.setIconEnabled(
                                                isConnected);
                                    }
                                }
                                return true;
                            }
                        });
    }

    public final boolean canSetRename() {
        byte[] m;
        byte[] m2;
        BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        return !BluetoothUtils.isSupportSmep(bluetoothDevice)
                || ((m =
                                        BluetoothUtils$$ExternalSyntheticOutline0.m(
                                                SmepTag.CUSTOM_CONNECTED_WITH_PROFILE,
                                                bluetoothDevice))
                                != null
                        && m.length > 3
                        && m[3] == 1)
                || (m2 =
                                BluetoothUtils$$ExternalSyntheticOutline0.m(
                                        SmepTag.CUSTOM_SUPPORT_RENAME, bluetoothDevice))
                        == null
                || m2.length <= 3
                || m2[3] <= 0;
    }

    /* JADX WARN: Type inference failed for: r5v1, types: [com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController$1] */
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mCachedDevice != null) {
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            LocalBluetoothManager localBluetoothManager = this.mManager;
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
            final SecBluetoothDetailsProfilesController secBluetoothDetailsProfilesController =
                    new SecBluetoothDetailsProfilesController(
                            context, this, cachedBluetoothDevice, settingsLifecycle);
            secBluetoothDetailsProfilesController.mIsLeAudioToggleEnabled = false;
            secBluetoothDetailsProfilesController.mProfileDeviceMap = new HashMap();
            secBluetoothDetailsProfilesController.mIsWorkingSwitchAnimation = false;
            secBluetoothDetailsProfilesController.mNeedUiUpdate = false;
            secBluetoothDetailsProfilesController.mIsPaused = false;
            secBluetoothDetailsProfilesController.mHandler =
                    new Handler() { // from class:
                                    // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController.1
                        public AnonymousClass1() {}

                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            if (message.what != 1) {
                                return;
                            }
                            SecBluetoothDetailsProfilesController
                                    secBluetoothDetailsProfilesController2 =
                                            SecBluetoothDetailsProfilesController.this;
                            secBluetoothDetailsProfilesController2.mIsWorkingSwitchAnimation =
                                    false;
                            if (!secBluetoothDetailsProfilesController2.mNeedUiUpdate
                                    || secBluetoothDetailsProfilesController2.mIsPaused) {
                                return;
                            }
                            secBluetoothDetailsProfilesController2.refresh();
                        }
                    };
            secBluetoothDetailsProfilesController.mManager = localBluetoothManager;
            secBluetoothDetailsProfilesController.mProfileManager =
                    localBluetoothManager.mProfileManager;
            secBluetoothDetailsProfilesController.mCachedDevice = cachedBluetoothDevice;
            secBluetoothDetailsProfilesController.mAllOfCachedDevices =
                    secBluetoothDetailsProfilesController.getAllOfCachedBluetoothDevices();
            settingsLifecycle.addObserver(secBluetoothDetailsProfilesController);
            this.mProfilesController = secBluetoothDetailsProfilesController;
            this.mCodecController =
                    new SecBluetoothDetailsCodecController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle);
            arrayList.add(this.mProfilesController);
            arrayList.add(this.mCodecController);
            this.mNoiseController =
                    new SecBluetoothDetailNoiseController(
                            context,
                            this,
                            this.mCachedDevice,
                            this.mSmepReceiver,
                            settingsLifecycle);
            this.mAdvancedController =
                    new SecBluetoothDetailsAdvancedController(
                            context,
                            this,
                            this.mCachedDevice,
                            this.mSmepReceiver,
                            settingsLifecycle);
            arrayList.add(this.mNoiseController);
            arrayList.add(this.mAdvancedController);
            SecBluetoothDetailsHearingAidController secBluetoothDetailsHearingAidController =
                    new SecBluetoothDetailsHearingAidController(
                            context, this, this.mCachedDevice, settingsLifecycle);
            this.mHearingAidController = secBluetoothDetailsHearingAidController;
            arrayList.add(secBluetoothDetailsHearingAidController);
            SecBluetoothDetailsAudioTypeController secBluetoothDetailsAudioTypeController =
                    new SecBluetoothDetailsAudioTypeController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle);
            this.mAudioTypeController = secBluetoothDetailsAudioTypeController;
            arrayList.add(secBluetoothDetailsAudioTypeController);
            SecBluetoothBroadcastAssistantController secBluetoothBroadcastAssistantController =
                    new SecBluetoothBroadcastAssistantController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle);
            this.mBroadcastAssistantController = secBluetoothBroadcastAssistantController;
            arrayList.add(secBluetoothBroadcastAssistantController);
            arrayList.add(
                    new SecBluetoothDetailsHearingAidsPresetsController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle));
            String str = Build.TYPE;
            if ("eng".equals(str) || "userdebug".equals(str)) {
                LocalBluetoothManager localBluetoothManager2 = this.mManager;
                CachedBluetoothDevice cachedBluetoothDevice2 = this.mCachedDevice;
                SecBluetoothDetailsGroupController secBluetoothDetailsGroupController =
                        new SecBluetoothDetailsGroupController(
                                context, this, cachedBluetoothDevice2, settingsLifecycle);
                secBluetoothDetailsGroupController.mProfileManager =
                        localBluetoothManager2.mProfileManager;
                secBluetoothDetailsGroupController.mCachedDevice = cachedBluetoothDevice2;
                settingsLifecycle.addObserver(secBluetoothDetailsGroupController);
                this.mGroupController = secBluetoothDetailsGroupController;
                arrayList.add(secBluetoothDetailsGroupController);
            }
        }
        return arrayList;
    }

    public final void finishActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager == null || fragmentManager.getBackStackEntryCount() <= 1) {
                activity.finishAndRemoveTask();
            } else {
                activity.onBackPressed();
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecBluetoothDeviceDetailsSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_bluetooth_device_details_fragment;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        RecyclerView listView = getListView();
        if (listView != null) {
            listView.setNestedScrollingEnabled(false);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager;
        this.mScreenId = getResources().getString(R.string.screen_device_profiles_setting);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.w(
                    "SecBluetoothDeviceDetailsSettings",
                    "Activity started without bluetooth is not ready");
            this.isFinished = true;
        }
        String string = getArguments().getString("device_address");
        this.mDeviceAddress = string;
        if (string == null) {
            Bundle bundleExtra = getActivity().getIntent().getBundleExtra("device");
            if (bundleExtra != null) {
                BluetoothDevice bluetoothDevice =
                        !this.mIsCalledFromSetting
                                ? (BluetoothDevice) bundleExtra.getParcelable("device")
                                : (BluetoothDevice) getArguments().getParcelable("device");
                if (bluetoothDevice == null) {
                    Log.w(
                            "SecBluetoothDeviceDetailsSettings",
                            "Activity started without a remote bluetooth device is not ready");
                    this.isFinished = true;
                } else {
                    LocalBluetoothManager localBluetoothManager2 = this.mManager;
                    if (localBluetoothManager2 != null
                            && (cachedBluetoothDeviceManager =
                                            localBluetoothManager2.mCachedDeviceManager)
                                    != null) {
                        this.mCachedDevice =
                                cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
                    }
                }
            }
        } else {
            this.mIsCalledFromSetting = getArguments().getBoolean("isCalledFromSetting", false);
            this.mCachedDevice =
                    this.mManager.mCachedDeviceManager.findDevice(
                            this.mManager.mLocalAdapter.mAdapter.getRemoteDevice(
                                    this.mDeviceAddress));
        }
        if (this.mCachedDevice == null) {
            Log.w("SecBluetoothDeviceDetailsSettings", "onAttach() CachedDevice is null!");
            this.isFinished = true;
        }
        this.mSmepReceiver = new BluetoothSmepReceiver(getContext());
        super.onAttach(context);
        if (this.isFinished) {
            finishActivity();
            return;
        }
        if (!this.mCachedDevice.isConnectedForGroup()) {
            Message message = new Message();
            message.what = 1;
            this.mHandler.sendMessageDelayed(message, 500L);
        }
        if (this.mCachedDevice.checkHearingAidByUuid()) {
            ((SecLeAudioBluetoothDetailsHeaderController)
                            use(SecLeAudioBluetoothDetailsHeaderController.class))
                    .init(this.mCachedDevice, this.mManager);
            ((SecAdvancedBluetoothDetailsHeaderController)
                            use(SecAdvancedBluetoothDetailsHeaderController.class))
                    .init(this.mCachedDevice, this.mSmepReceiver);
        } else {
            ((SecBluetoothDetailsHeaderController) use(SecBluetoothDetailsHeaderController.class))
                    .init(this.mCachedDevice);
            ((SecAdvancedBluetoothDetailsHeaderController)
                            use(SecAdvancedBluetoothDetailsHeaderController.class))
                    .init(this.mCachedDevice, this.mSmepReceiver);
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        BluetoothFeatureProviderImpl bluetoothFeatureProvider =
                featureFactoryImpl.getBluetoothFeatureProvider();
        boolean z = DeviceConfig.getBoolean("settings_ui", "bt_slice_settings_enabled", true);
        BlockingSlicePrefController blockingSlicePrefController =
                (BlockingSlicePrefController) use(BlockingSlicePrefController.class);
        Uri uri = null;
        if (z) {
            BluetoothDevice bluetoothDevice2 = this.mCachedDevice.mDevice;
            bluetoothFeatureProvider.getClass();
            byte[] metadata = bluetoothDevice2.getMetadata(16);
            if (metadata != null) {
                uri = Uri.parse(new String(metadata));
            }
        }
        blockingSlicePrefController.setSliceUri(uri);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mNoiseController.isAvailable()) {
            this.mNoiseController.updateLayout();
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (cachedBluetoothDevice != null) {
            Log.d(
                    "SecBluetoothDeviceDetailsSettings",
                    "onConnectionStateChanged :: "
                            + cachedBluetoothDevice.getName()
                            + ", state = "
                            + i);
            if (BluetoothUtils.isSupportSmep(cachedBluetoothDevice.mDevice)) {
                boolean isConnected = cachedBluetoothDevice.isConnected();
                this.mAdvancedController.setEnabled(isConnected);
                this.mNoiseController.setEnabled(isConnected);
            }
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsActivity) {
            this.mSettingsActivity = (SettingsActivity) activity;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsBaseActivity) {
            ((SettingsBaseActivity) activity).disableExtendedAppBar();
        }
        getActivity().setTitle(ApnSettings.MVNO_NONE);
        if (this.mBottomButtonLayout == null) {
            RelativeLayout relativeLayout =
                    (RelativeLayout) this.mSettingsActivity.findViewById(R.id.button_bar);
            this.mBottomButtonLayout = relativeLayout;
            relativeLayout.setBackgroundColor(
                    getActivity()
                            .getApplicationContext()
                            .getColor(R.color.sec_widget_round_and_bgcolor));
            this.mBottomNavigationView =
                    (BottomNavigationView)
                            ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_bluetooth_device_profile_bottom_layout,
                                            (ViewGroup) this.mBottomButtonLayout,
                                            false);
            if (this.mNoiseController.isAvailable()) {
                this.mNoiseController.setBottomLayout(this.mBottomButtonLayout);
            }
            this.mBottomNavigationView.menu.clear();
            this.mBottomNavigationView.inflateMenu(R.menu.sec_bluetooth_device_detail_bottom_menu);
            this.mBottomButtonLayout.addView(this.mBottomNavigationView);
            this.mBottomButtonLayout.setVisibility(0);
            try {
                BottomNavigationMenuView bottomNavigationMenuView =
                        (BottomNavigationMenuView) this.mBottomNavigationView.getChildAt(0);
                Field declaredField =
                        bottomNavigationMenuView
                                .getClass()
                                .getSuperclass()
                                .getDeclaredField("buttons");
                declaredField.setAccessible(true);
                NavigationBarItemView[] navigationBarItemViewArr =
                        (NavigationBarItemView[]) declaredField.get(bottomNavigationMenuView);
                if (navigationBarItemViewArr.length > 0) {
                    NavigationBarItemView navigationBarItemView = navigationBarItemViewArr[0];
                    this.mNaviButton = navigationBarItemView;
                    if (navigationBarItemView != null) {
                        this.mNaviDrawable = navigationBarItemView.getBackground();
                        TextView textView =
                                (TextView)
                                        this.mNaviButton.findViewById(
                                                getResources()
                                                        .getIdentifier(
                                                                "navigation_bar_item_small_label_view",
                                                                "id",
                                                                getActivity().getPackageName()));
                        if (textView != null) {
                            this.mRenameColor = textView.getCurrentTextColor();
                        }
                    }
                }
            } catch (Exception unused) {
                Log.d("SecBluetoothDeviceDetailsSettings", "Button library is not available");
            }
            this.mBottomNavigationView.selectedListener =
                    new BottomNavigationView
                            .OnNavigationItemSelectedListener() { // from class:
                                                                  // com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.3
                        @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                        public final boolean onNavigationItemSelected(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            boolean z = false;
                            final SecBluetoothDeviceDetailsSettings
                                    secBluetoothDeviceDetailsSettings =
                                            SecBluetoothDeviceDetailsSettings.this;
                            if (itemId == R.id.unpair_button) {
                                if (secBluetoothDeviceDetailsSettings.mCachedDevice
                                        .isConnectedForGroup()) {
                                    SALogging.insertSALog(
                                            secBluetoothDeviceDetailsSettings
                                                    .getActivity()
                                                    .getResources()
                                                    .getString(R.string.screen_bluetooth_global),
                                            secBluetoothDeviceDetailsSettings
                                                    .getActivity()
                                                    .getResources()
                                                    .getString(R.string.event_bluetooth_bdsc),
                                            secBluetoothDeviceDetailsSettings
                                                    .getActivity()
                                                    .getResources()
                                                    .getString(
                                                            R.string
                                                                    .detail_bluetooth_bdsc_connect_to_unpair));
                                }
                                SALogging.insertSALog(
                                        secBluetoothDeviceDetailsSettings.mScreenId,
                                        secBluetoothDeviceDetailsSettings
                                                .getActivity()
                                                .getResources()
                                                .getString(
                                                        R.string
                                                                .event_device_profiles_setting_unpair_tab));
                                Log.d(
                                        "SecBluetoothDeviceDetailsSettings",
                                        "BottomNavigationItemSelected:: Unpair to "
                                                + secBluetoothDeviceDetailsSettings.mCachedDevice
                                                        .getNameForLog()
                                                + ", isConneceted : "
                                                + secBluetoothDeviceDetailsSettings.mCachedDevice
                                                        .isConnected());
                                CachedBluetoothDevice cachedBluetoothDevice =
                                        secBluetoothDeviceDetailsSettings.mCachedDevice;
                                ParcelUuid[] uuids = cachedBluetoothDevice.mDevice.getUuids();
                                if (uuids == null) {
                                    if (cachedBluetoothDevice.mIsRestored) {
                                        uuids = cachedBluetoothDevice.mRestoredDevice.mUuids;
                                    }
                                    if (uuids == null) {
                                        Log.e(
                                                "CachedBluetoothDevice",
                                                "isSyncableDevice():: uuids is null");
                                    }
                                }
                                if (BluetoothUtils.isSyncDevice(
                                        cachedBluetoothDevice.getManufacturerRawData(),
                                        null,
                                        uuids)) {
                                    Context applicationContext =
                                            secBluetoothDeviceDetailsSettings
                                                    .getActivity()
                                                    .getApplicationContext();
                                    boolean z2 = Utils.DEBUG;
                                    if (AccountManager.get(applicationContext)
                                                    .getAccountsByType("com.osp.app.signin")
                                                    .length
                                            > 0) {
                                        z = true;
                                    }
                                }
                                AlertDialog create =
                                        new AlertDialog.Builder(
                                                        secBluetoothDeviceDetailsSettings
                                                                .getActivity())
                                                .setTitle(
                                                        String.format(
                                                                secBluetoothDeviceDetailsSettings
                                                                        .getResources()
                                                                        .getString(
                                                                                R.string
                                                                                        .sec_bluetooth_profile_popup_unpair_title),
                                                                secBluetoothDeviceDetailsSettings
                                                                        .mCachedDevice.getName()))
                                                .setMessage(
                                                        String.format(
                                                                secBluetoothDeviceDetailsSettings
                                                                        .getResources()
                                                                        .getString(
                                                                                z
                                                                                        ? R.string
                                                                                                .sec_bluetooth_profile_popup_unpair_description
                                                                                        : R.string
                                                                                                .sec_bluetooth_profile_popup_unpair_description_common),
                                                                secBluetoothDeviceDetailsSettings
                                                                        .mCachedDevice.getName()))
                                                .setPositiveButton(
                                                        R.string.bluetooth_device_context_unpair,
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                                                     // com.samsung.android.settings.bluetooth.SecBluetoothDeviceDetailsSettings.5
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface dialogInterface,
                                                                    int i) {
                                                                SecBluetoothDeviceDetailsSettings
                                                                        secBluetoothDeviceDetailsSettings2 =
                                                                                SecBluetoothDeviceDetailsSettings
                                                                                        .this;
                                                                if (secBluetoothDeviceDetailsSettings2
                                                                        .mCachedDevice
                                                                        .isConnectedForGroup()) {
                                                                    SALogging.insertSALog(
                                                                            secBluetoothDeviceDetailsSettings2
                                                                                    .getResources()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .screen_bluetooth_global),
                                                                            secBluetoothDeviceDetailsSettings2
                                                                                    .getResources()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .event_bluetooth_bddc),
                                                                            secBluetoothDeviceDetailsSettings2
                                                                                    .getResources()
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .detail_bluetooth_bddc_settings));
                                                                }
                                                                secBluetoothDeviceDetailsSettings2
                                                                        .mCachedDevice.unpair();
                                                                dialogInterface.dismiss();
                                                                SecBluetoothDeviceDetailsSettings
                                                                        .this
                                                                        .finishActivity();
                                                            }
                                                        })
                                                .setNegativeButton(
                                                        R.string.common_cancel,
                                                        new AnonymousClass4())
                                                .create();
                                secBluetoothDeviceDetailsSettings.mUnpairDialog = create;
                                create.show();
                                secBluetoothDeviceDetailsSettings
                                        .mUnpairDialog
                                        .getButton(-1)
                                        .setTextColor(
                                                secBluetoothDeviceDetailsSettings
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_bluetooth_dialog_button_color_unpair,
                                                                null));
                            } else if (itemId == R.id.rename_button) {
                                if (!secBluetoothDeviceDetailsSettings.mCachedDevice.mIsRestored) {
                                    if (secBluetoothDeviceDetailsSettings.canSetRename()) {
                                        CachedBluetoothDevice cachedBluetoothDevice2 =
                                                secBluetoothDeviceDetailsSettings.mCachedDevice;
                                        Bundle bundle2 = new Bundle(1);
                                        bundle2.putString(
                                                "cached_device",
                                                cachedBluetoothDevice2.mDevice.getAddress());
                                        SecRemoteDeviceNameDialogFragment
                                                secRemoteDeviceNameDialogFragment =
                                                        new SecRemoteDeviceNameDialogFragment();
                                        secRemoteDeviceNameDialogFragment.setArguments(bundle2);
                                        secRemoteDeviceNameDialogFragment.show(
                                                secBluetoothDeviceDetailsSettings
                                                        .getFragmentManager(),
                                                "RemoteDeviceName");
                                    } else {
                                        Toast.makeText(
                                                        secBluetoothDeviceDetailsSettings
                                                                .getContext(),
                                                        secBluetoothDeviceDetailsSettings
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .sec_bluetooth_connect_to_rename,
                                                                        secBluetoothDeviceDetailsSettings
                                                                                .mCachedDevice
                                                                                .getName()),
                                                        0)
                                                .show();
                                    }
                                }
                                SALogging.insertSALog(
                                        secBluetoothDeviceDetailsSettings.mScreenId,
                                        secBluetoothDeviceDetailsSettings
                                                .getActivity()
                                                .getResources()
                                                .getString(
                                                        R.string
                                                                .event_device_profiles_setting_rename_tab));
                            } else if (itemId == R.id.connect_button) {
                                boolean isConnectedForGroup =
                                        secBluetoothDeviceDetailsSettings.mCachedDevice
                                                .isConnectedForGroup();
                                String str =
                                        isConnectedForGroup
                                                ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                                                : "1";
                                if (isConnectedForGroup) {
                                    menuItem.setIcon(
                                            secBluetoothDeviceDetailsSettings
                                                    .getResources()
                                                    .getDrawable(
                                                            R.drawable.ic_bluetooth_disconnect));
                                    menuItem.setTitle(
                                            secBluetoothDeviceDetailsSettings
                                                    .getResources()
                                                    .getString(R.string.sec_bluetooth_disconnect));
                                    secBluetoothDeviceDetailsSettings.mCachedDevice.disconnect();
                                } else {
                                    menuItem.setIcon(
                                            secBluetoothDeviceDetailsSettings
                                                    .getResources()
                                                    .getDrawable(R.drawable.ic_bluetooth_connect));
                                    menuItem.setTitle(
                                            secBluetoothDeviceDetailsSettings
                                                    .getResources()
                                                    .getString(R.string.sec_bluetooth_connect));
                                    CachedBluetoothDevice cachedBluetoothDevice3 =
                                            secBluetoothDeviceDetailsSettings.mCachedDevice;
                                    if (!cachedBluetoothDevice3.shouldLaunchGM(
                                            ActivityThread.currentPackageName(), false)) {
                                        if (cachedBluetoothDevice3.mBondState == 10) {
                                            cachedBluetoothDevice3.startPairing();
                                        } else {
                                            cachedBluetoothDevice3.checkLEConnectionGuide(
                                                    true, true);
                                            cachedBluetoothDevice3.mConnectAttempted =
                                                    SystemClock.elapsedRealtime();
                                            cachedBluetoothDevice3.connectDevice();
                                        }
                                    }
                                }
                                SALogging.insertSALog(
                                        secBluetoothDeviceDetailsSettings.mScreenId,
                                        secBluetoothDeviceDetailsSettings
                                                .getActivity()
                                                .getResources()
                                                .getString(
                                                        R.string
                                                                .event_device_profiles_setting_connect_button),
                                        str);
                            }
                            return true;
                        }
                    };
        }
        if (onCreateView != null) {
            onCreateView
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
        return onCreateView;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.unregisterCallback(this);
        }
        AlertDialog alertDialog = this.mUnpairDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mUnpairDialog.dismiss();
        }
        this.mHandler.removeMessages(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice.mBondState == 10 && !cachedBluetoothDevice.mIsRestored) {
            Log.d(
                    "SecBluetoothDeviceDetailsSettings",
                    "onDeviceAttributesChanged() bond state is BOND_NONE");
            try {
                finishActivity();
                return;
            } catch (IllegalStateException e) {
                Log.d(
                        "SecBluetoothDeviceDetailsSettings",
                        "onDeviceAttributesChanged() catch IllegalStateException" + e);
            }
        }
        refresh$2$1();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2 = this.mCachedDevice;
        if (cachedBluetoothDevice2.mIsSynced
                && cachedBluetoothDevice2.equals(cachedBluetoothDevice)) {
            if (DBG) {
                Log.d("SecBluetoothDeviceDetailsSettings", "onDeviceDeleted()");
            }
            finishActivity();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources()
                            .getString(R.string.event_device_profiles_setting_navigate_button));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mReceiverRegistered && this.mReceiver != null) {
            getActivity().getApplicationContext().unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.unregisterCallback(this);
        }
        LocalBluetoothManager localBluetoothManager = this.mManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterSemCallback(this);
            this.mManager.mEventManager.unregisterCallback(this);
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        BluetoothRetryDetector bluetoothRetryDetector;
        StringBuilder sb = new StringBuilder("onProfileStateChanged(), profile - ");
        sb.append(localBluetoothProfile);
        sb.append(", newState - ");
        sb.append(i);
        sb.append(", oldState - ");
        TooltipPopup$$ExternalSyntheticOutline0.m(sb, i2, "SecBluetoothDeviceDetailsSettings");
        if ((localBluetoothProfile instanceof LeAudioProfile)
                && this.mHearingAidController.mCachedDevice.isHearingAidDevice()) {
            if (i == 2) {
                this.mHearingAidController.mHearingAidContainer.setVisible(false);
            } else if (!this.mHearingAidController.isAnyLeDeviceConnected(localBluetoothProfile)) {
                this.mHearingAidController.mHearingAidContainer.setVisible(true);
            }
        }
        if (cachedBluetoothDevice.mDevice != null
                && localBluetoothProfile != null
                && (i == 2 || i == 0)) {
            AuditLog.log(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "DeviceProfilesSettings",
                    String.format(
                            i == 2
                                    ? "User has changed the bluetooth profile %s, on bluetooth"
                                          + " device %s, to enabled."
                                    : "User has changed the bluetooth profile %s, on bluetooth"
                                          + " device %s, to disabled.",
                            getActivity()
                                    .getString(
                                            localBluetoothProfile.getNameResource(
                                                    cachedBluetoothDevice.mDevice)),
                            cachedBluetoothDevice.mDevice.getAddress()),
                    ApnSettings.MVNO_NONE);
        }
        if (i2 == 1 && i == 0) {
            if (!cachedBluetoothDevice.isBusy()
                    && !cachedBluetoothDevice.isConnected()
                    && !cachedBluetoothDevice.isConnectedMembers()
                    && (bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector) != null) {
                bluetoothRetryDetector.mCount++;
            }
        } else if (i == 2 && !cachedBluetoothDevice.isBusy()) {
            cachedBluetoothDevice.resetRetryDetector();
        }
        if (BluetoothUtils.isSupportSmep(cachedBluetoothDevice.mDevice)) {
            boolean isConnected = cachedBluetoothDevice.isConnected();
            this.mAdvancedController.setEnabled(isConnected);
            this.mNoiseController.setEnabled(isConnected);
        }
        if (this.mGroupController != null) {
            refresh$2$1();
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        CachedBluetoothDevice cachedBluetoothDevice;
        super.onResume();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if ((defaultAdapter != null && !defaultAdapter.isEnabled())
                || this.isFinished
                || (cachedBluetoothDevice = this.mCachedDevice) == null) {
            finishActivity();
            return;
        }
        cachedBluetoothDevice.registerCallback(this);
        CachedBluetoothDevice cachedBluetoothDevice2 = this.mCachedDevice;
        if (cachedBluetoothDevice2.mBondState == 10 && !cachedBluetoothDevice2.mIsRestored) {
            Log.d("SecBluetoothDeviceDetailsSettings", "onResume() bond state is BOND_NONE");
            try {
                finishActivity();
                return;
            } catch (IllegalStateException e) {
                Log.d(
                        "SecBluetoothDeviceDetailsSettings",
                        "onResume() catch IllegalStateException" + e);
            }
        }
        if (this.mCachedDevice.mIsRestored) {
            this.mBottomNavigationView.menu.removeItem(R.id.rename_button);
        }
        refresh$2$1();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("com.samsung.bluetooth.device.action.META_DATA_CHANGED");
        getActivity().getApplicationContext().registerReceiver(this.mReceiver, intentFilter);
        this.mReceiverRegistered = true;
        LocalBluetoothManager localBluetoothManager = this.mManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerSemCallback(this);
            this.mManager.mEventManager.registerCallback(this);
        }
        SALogging.insertSALog(this.mScreenId);
    }

    public final void refresh$2$1() {
        MenuItem findItem;
        BottomNavigationView bottomNavigationView = this.mBottomNavigationView;
        if (bottomNavigationView != null
                && (findItem = bottomNavigationView.menu.findItem(R.id.connect_button)) != null) {
            if (this.mCachedDevice.isConnectedForGroup()) {
                findItem.setIcon(getResources().getDrawable(R.drawable.ic_bluetooth_disconnect));
                findItem.setTitle(getResources().getString(R.string.sec_bluetooth_disconnect));
            } else {
                findItem.setIcon(getResources().getDrawable(R.drawable.ic_bluetooth_connect));
                findItem.setTitle(getResources().getString(R.string.sec_bluetooth_connect));
            }
        }
        SecBluetoothDetailsAudioTypeController secBluetoothDetailsAudioTypeController =
                this.mAudioTypeController;
        if (secBluetoothDetailsAudioTypeController != null) {
            secBluetoothDetailsAudioTypeController.refresh();
        }
        SecBluetoothDetailsGroupController secBluetoothDetailsGroupController =
                this.mGroupController;
        if (secBluetoothDetailsGroupController != null) {
            secBluetoothDetailsGroupController.refresh();
        }
        SecBluetoothBroadcastAssistantController secBluetoothBroadcastAssistantController =
                this.mBroadcastAssistantController;
        if (secBluetoothBroadcastAssistantController != null) {
            secBluetoothBroadcastAssistantController.refresh();
        }
        if (canSetRename()) {
            setEnabledMenuItem(true);
        } else {
            setEnabledMenuItem(false);
        }
    }

    public final void setEnabledMenuItem(boolean z) {
        MenuItem findItem;
        BottomNavigationView bottomNavigationView = this.mBottomNavigationView;
        if (bottomNavigationView == null
                || (findItem = bottomNavigationView.menu.findItem(R.id.rename_button)) == null) {
            return;
        }
        int i = this.mRenameColor;
        if (i == 0) {
            i = getContext().getColor(R.color.sec_app_button_text_color);
        }
        int i2 = z ? 255 : 102;
        SpannableString spannableString = new SpannableString(findItem.getTitle().toString());
        spannableString.setSpan(
                new ForegroundColorSpan(
                        Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i))),
                0,
                spannableString.length(),
                0);
        findItem.setTitle(spannableString);
        Drawable drawable =
                getResources().getDrawable(R.drawable.ic_bluetooth_edit, getContext().getTheme());
        drawable.setAlpha(i2);
        findItem.setIcon(drawable);
        NavigationBarItemView navigationBarItemView = this.mNaviButton;
        if (navigationBarItemView != null) {
            if (!z) {
                navigationBarItemView.setBackground(null);
                return;
            }
            Drawable drawable2 = this.mNaviDrawable;
            if (drawable2 != null) {
                navigationBarItemView.setBackground(drawable2);
            }
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onResourceUpdated() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}
}
