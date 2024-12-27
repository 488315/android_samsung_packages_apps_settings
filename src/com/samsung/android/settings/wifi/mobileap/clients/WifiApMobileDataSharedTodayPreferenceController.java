package com.samsung.android.settings.wifi.mobileap.clients;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetDataLimitDialog.AnonymousClass1;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApClientConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.progressbar.StackProgressbar;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApMobileDataSharedTodayPreferenceController extends BasePreferenceController
        implements LifecycleEventObserver {
    public static final String ACTION_WIFI_AP_STATE_CHANGED =
            "android.net.wifi.WIFI_AP_STATE_CHANGED";
    private static final IntentFilter INTENT_FILTER =
            new IntentFilter(ACTION_WIFI_AP_STATE_CHANGED);
    private static final String TAG = "WifiApMobileDataSharedTodayPreferenceController";
    private DialogInterface.OnDismissListener dismissListener;
    private final BroadcastReceiver mBroadcastReceiver;
    private RecyclerView mClientsRecyclerView;
    private WifiApClientsRecyclerViewAdapter mClientsRecyclerViewAdapter;
    private TextView mConnectedClientsSummaryTextView;
    int mConnectedDeviceCount;
    private LinearLayout mContainerLinearLayout;
    private Context mContext;
    private ImageView mDataLimitAlertImageView;
    private WifiApClientSetDataLimitDialog mDataLimitDialog;
    private LinearLayout mDataLimitLayout;
    private TextView mDataLimitTextView;
    private TextView mDataLimitWarningTextView;
    private TextView mDataUsageTextView;
    private boolean mIsAnimateProgressBarRequired;
    private boolean mIsClientsListViewVisible;
    private boolean mIsDataLimitAlertIconToBeShown;
    private boolean mIsLockUnlockNetworkButtonToBeShown;
    private boolean mIsPreferenceClickEnabled;
    private boolean mIsSetOrEditButtonViewVisible;
    private boolean mIsTop10ColorsToBeShown;
    private AlertDialog mLockNetworkDialog;
    private int mMaxNumberOfClientsRequired;
    private int mMaxPositionToIncludeDisconnectedInList;
    private SemWifiManager mSemWifiManager;
    private Button mSetOrEditLimitButton;
    private FrameLayout mSetOrEditLimitFrameLayout;
    private StackProgressbar mStackProgressbar;
    private LayoutPreference mThisLayoutPreference;
    private WifiApClientsManageMobileHotspot mWifiApClientsManageMobileHotspot;
    private Button mWifiApLockUnlockButton;
    private FrameLayout mWifiApLockUnlockFrameLayout;
    private ImageView mWifiApLockedImageView;
    private WifiApSettings mWifiApSettings;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnDismissListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApMobileDataSharedTodayPreferenceController this$0;

        public /* synthetic */ AnonymousClass2(
                WifiApMobileDataSharedTodayPreferenceController
                        wifiApMobileDataSharedTodayPreferenceController,
                int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApMobileDataSharedTodayPreferenceController;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mDataLimitDialog = null;
                    WifiApMobileDataSharedTodayPreferenceController
                            wifiApMobileDataSharedTodayPreferenceController = this.this$0;
                    wifiApMobileDataSharedTodayPreferenceController.updateState(
                            wifiApMobileDataSharedTodayPreferenceController.mThisLayoutPreference);
                    break;
                default:
                    WifiApMobileDataSharedTodayPreferenceController
                            wifiApMobileDataSharedTodayPreferenceController2 = this.this$0;
                    wifiApMobileDataSharedTodayPreferenceController2.updateState(
                            wifiApMobileDataSharedTodayPreferenceController2.mThisLayoutPreference);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$3, reason: invalid class name */
    public final class AnonymousClass3 implements View.OnClickListener {
        public AnonymousClass3() {}

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            Log.i(
                    WifiApMobileDataSharedTodayPreferenceController.TAG,
                    "Set/Edit data limit button clicked");
            SALogging.insertSALog("TETH_013", "8074");
            WifiApMobileDataSharedTodayPreferenceController
                    wifiApMobileDataSharedTodayPreferenceController =
                            WifiApMobileDataSharedTodayPreferenceController.this;
            Context context = WifiApMobileDataSharedTodayPreferenceController.this.mContext;
            WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                    WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(
                            WifiApMobileDataSharedTodayPreferenceController.this.mContext);
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(1, this);
            WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog =
                    new WifiApClientSetDataLimitDialog(context, 0);
            wifiApClientSetDataLimitDialog.mDataLimitEditTextWatcher =
                    wifiApClientSetDataLimitDialog.new AnonymousClass1();
            wifiApClientSetDataLimitDialog.mContext = context;
            wifiApClientSetDataLimitDialog.mListener = anonymousClass4;
            wifiApClientSetDataLimitDialog.mWifiApDataUsageConfig = wifiApActiveSessionDataLimit;
            wifiApClientSetDataLimitDialog.mMacAddress = ApnSettings.MVNO_NONE;
            wifiApClientSetDataLimitDialog.setTitle(R.string.data_usage_disable_mobile_limit);
            wifiApClientSetDataLimitDialog.mDialogMessage =
                    context.getString(
                            WifiApUtils.getStringID(
                                    R.string.wifi_ap_when_data_limit_you_set_reached_description));
            wifiApMobileDataSharedTodayPreferenceController.mDataLimitDialog =
                    wifiApClientSetDataLimitDialog;
            WifiApMobileDataSharedTodayPreferenceController.this.mDataLimitDialog
                    .setOnDismissListener(
                            WifiApMobileDataSharedTodayPreferenceController.this.dismissListener);
            WifiApMobileDataSharedTodayPreferenceController.this.mDataLimitDialog.show();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$4, reason: invalid class name */
    public final class AnonymousClass4 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass4(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    Log.i(
                            WifiApMobileDataSharedTodayPreferenceController.TAG,
                            "Lock network dialog Lock button clicked");
                    SALogging.insertSALog(1L, "TETH_013", "8099", (String) null);
                    SemWifiManager semWifiManager =
                            ((WifiApMobileDataSharedTodayPreferenceController) this.this$0)
                                    .mSemWifiManager;
                    Log.i("WifiApFrameworkUtils", "setHotspotNetworkLocked : true");
                    semWifiManager.setSmartMHSLocked(1);
                    break;
                case 1:
                    WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog =
                            (WifiApClientSetDataLimitDialog) dialogInterface;
                    if (i != -1) {
                        if (i != -2) {
                            if (i == -3) {
                                if (WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(
                                                        WifiApMobileDataSharedTodayPreferenceController
                                                                .this
                                                                .mContext)
                                                .getUsageValueInMB()
                                        <= 0.0d) {
                                    SALogging.insertSALog(0L, "TETH_014", "8081", (String) null);
                                    break;
                                } else {
                                    SALogging.insertSALog(0L, "TETH_014", "8082", (String) null);
                                    break;
                                }
                            }
                        } else {
                            SALogging.insertSALog(2L, "TETH_014", "8082", (String) null);
                            WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                    WifiApMobileDataSharedTodayPreferenceController.this.mContext,
                                    0L);
                            break;
                        }
                    } else {
                        if (WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(
                                                WifiApMobileDataSharedTodayPreferenceController.this
                                                        .mContext)
                                        .getUsageValueInMB()
                                > 0.0d) {
                            SALogging.insertSALog(1L, "TETH_013", "8082", (String) null);
                        } else {
                            SALogging.insertSALog(1L, "TETH_013", "8081", (String) null);
                        }
                        WifiApDataUsageConfig inputDataInDataUsageConfig =
                                wifiApClientSetDataLimitDialog.getInputDataInDataUsageConfig();
                        if (inputDataInDataUsageConfig.getUsageValueInMB()
                                >= WifiApConnectedDeviceUtils.getWifiApTodayTotalDataUsage(
                                                WifiApMobileDataSharedTodayPreferenceController.this
                                                        .mContext)
                                        .getUsageValueInMB()) {
                            Log.i(
                                    WifiApMobileDataSharedTodayPreferenceController.TAG,
                                    "Settings Global data limit : "
                                            + (inputDataInDataUsageConfig.mUsageValueInBytes
                                                    / 1000.0d));
                            WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                    WifiApMobileDataSharedTodayPreferenceController.this.mContext,
                                    (long)
                                            ((double)
                                                    inputDataInDataUsageConfig.mUsageValueInBytes));
                            break;
                        } else {
                            Log.i(
                                    WifiApMobileDataSharedTodayPreferenceController.TAG,
                                    "Error Settings Global data limit : "
                                            + inputDataInDataUsageConfig.getUsageValueInMB());
                            Toast.makeText(
                                            WifiApMobileDataSharedTodayPreferenceController.this
                                                    .mContext,
                                            R.string.wifi_ap_data_limit_less_than_amount_used,
                                            0)
                                    .show();
                            break;
                        }
                    }
                    break;
                default:
                    if (((AnonymousClass7) this.this$0).val$finalPercentageDataLimitUsedForToday
                            < 100) {
                        SALogging.insertSALog("TETH_013", "8076");
                        break;
                    } else {
                        SALogging.insertSALog("TETH_013", "8077");
                        break;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            Log.i(
                    WifiApMobileDataSharedTodayPreferenceController.TAG,
                    "Lock network dialog cancel button clicked");
            SALogging.insertSALog(0L, "TETH_013", "8099");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$7, reason: invalid class name */
    public final class AnonymousClass7 implements View.OnClickListener {
        public final /* synthetic */ int val$finalPercentageDataLimitUsedForToday;

        public AnonymousClass7(int i) {
            this.val$finalPercentageDataLimitUsedForToday = i;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            AlertDialog.Builder positiveButton =
                    new AlertDialog.Builder(
                                    WifiApMobileDataSharedTodayPreferenceController.this.mContext)
                            .setPositiveButton(R.string.ok, new AnonymousClass4(2, this));
            if (this.val$finalPercentageDataLimitUsedForToday >= 100) {
                positiveButton.setTitle(R.string.wifi_ap_data_limit_reached);
                positiveButton.setMessage(
                        WifiApMobileDataSharedTodayPreferenceController.this.mContext.getString(
                                R.string.wifi_ap_data_limit_reached_message_4));
            } else {
                positiveButton.setTitle(R.string.wifi_ap_data_limit_almost_reached);
                positiveButton.setMessage(
                        String.format(
                                WifiApMobileDataSharedTodayPreferenceController.this.mContext
                                        .getString(R.string.wifi_ap_data_limit_reached_message_3),
                                Integer.valueOf(this.val$finalPercentageDataLimitUsedForToday)));
            }
            positiveButton.create().show();
        }
    }

    public WifiApMobileDataSharedTodayPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsPreferenceClickEnabled = true;
        this.mIsAnimateProgressBarRequired = true;
        this.mIsClientsListViewVisible = true;
        this.mIsSetOrEditButtonViewVisible = false;
        this.mIsDataLimitAlertIconToBeShown = false;
        this.mIsLockUnlockNetworkButtonToBeShown = false;
        this.mMaxPositionToIncludeDisconnectedInList = 3;
        this.mMaxNumberOfClientsRequired = 3;
        this.mIsTop10ColorsToBeShown = false;
        this.mConnectedDeviceCount = 0;
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                                "Broadcast Received: ",
                                intent.getAction(),
                                WifiApMobileDataSharedTodayPreferenceController.TAG,
                                WifiApMobileDataSharedTodayPreferenceController
                                        .ACTION_WIFI_AP_STATE_CHANGED)) {
                            int intExtra = intent.getIntExtra("wifi_state", 14);
                            if (WifiApMobileDataSharedTodayPreferenceController.this
                                                    .mDataLimitDialog
                                            != null
                                    && WifiApMobileDataSharedTodayPreferenceController.this
                                            .mDataLimitDialog.isShowing()) {
                                WifiApMobileDataSharedTodayPreferenceController.this
                                        .mDataLimitDialog.dismiss();
                            }
                            if (intExtra == 11 || intExtra == 13) {
                                WifiApMobileDataSharedTodayPreferenceController
                                        wifiApMobileDataSharedTodayPreferenceController =
                                                WifiApMobileDataSharedTodayPreferenceController
                                                        .this;
                                wifiApMobileDataSharedTodayPreferenceController.updateState(
                                        wifiApMobileDataSharedTodayPreferenceController
                                                .mThisLayoutPreference);
                            }
                        }
                    }
                };
        this.dismissListener = new AnonymousClass2(this, 0);
        this.mContext = context;
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$displayPreference$0(View view) {
        boolean isHotspotNetworkLocked =
                WifiApFrameworkUtils.isHotspotNetworkLocked(this.mSemWifiManager);
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Lock/Unlock Button clicked, Current lock state: ", TAG, isHotspotNetworkLocked);
        if (!isHotspotNetworkLocked) {
            if (this.mConnectedDeviceCount > 0) {
                SALogging.insertSALog(0L, "TETH_013", "8095", (String) null);
                showLockNetworkDialog();
                return;
            }
            return;
        }
        SemWifiManager semWifiManager = this.mSemWifiManager;
        Log.i("WifiApFrameworkUtils", "setHotspotNetworkLocked : false");
        semWifiManager.setSmartMHSLocked(2);
        updateState(this.mThisLayoutPreference);
        SALogging.insertSALog(1L, "TETH_013", "8095", (String) null);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisLayoutPreference = layoutPreference;
        this.mDataUsageTextView =
                (TextView) layoutPreference.mRootView.findViewById(android.R.id.title);
        this.mConnectedClientsSummaryTextView =
                (TextView) this.mThisLayoutPreference.mRootView.findViewById(android.R.id.summary);
        this.mDataLimitTextView =
                (TextView)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.data_limit_text_view);
        this.mDataLimitAlertImageView =
                (ImageView)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.data_limit_alert_icon);
        this.mDataLimitLayout =
                (LinearLayout)
                        this.mThisLayoutPreference.mRootView.findViewById(R.id.data_limit_layout);
        this.mDataLimitWarningTextView =
                (TextView)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.data_limit_warning_text_view);
        this.mSetOrEditLimitFrameLayout =
                (FrameLayout)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.data_limit_button_frame_layout);
        this.mWifiApLockedImageView =
                (ImageView)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.wifi_ap_network_lock_icon);
        this.mWifiApLockUnlockButton =
                (Button) this.mThisLayoutPreference.mRootView.findViewById(R.id.lock_button);
        this.mWifiApLockUnlockFrameLayout =
                (FrameLayout)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.lock_button_frame_layout);
        Button button =
                (Button) this.mThisLayoutPreference.mRootView.findViewById(R.id.data_limit_button);
        this.mSetOrEditLimitButton = button;
        button.setOnClickListener(new AnonymousClass3());
        StackProgressbar stackProgressbar =
                (StackProgressbar)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.mobile_data_used_progress_bar);
        this.mStackProgressbar = stackProgressbar;
        boolean z = this.mIsTop10ColorsToBeShown;
        stackProgressbar.getClass();
        Log.i("StackProgressbar", "setIsTop3ColorsToBeShown - " + z);
        stackProgressbar.mIsTop10ColorsToBeShown = z;
        RecyclerView recyclerView =
                (RecyclerView)
                        this.mThisLayoutPreference.mRootView.findViewById(
                                R.id.progress_bar_clients_recycler_view);
        this.mClientsRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        Context context = this.mContext;
        ArrayList arrayList = new ArrayList();
        WifiApClientsRecyclerViewAdapter wifiApClientsRecyclerViewAdapter =
                new WifiApClientsRecyclerViewAdapter();
        new ArrayList();
        wifiApClientsRecyclerViewAdapter.mContext = context;
        wifiApClientsRecyclerViewAdapter.mTopHotspotWifiApClientConfigList = arrayList;
        wifiApClientsRecyclerViewAdapter.mWarningColor =
                context.getResources().getColor(R.color.wifi_ap_warning_color);
        wifiApClientsRecyclerViewAdapter.mSummaryColor =
                WifiApSettingsUtils.getColorFromAttribute(context, android.R.attr.textColorPrimary);
        this.mClientsRecyclerViewAdapter = wifiApClientsRecyclerViewAdapter;
        this.mClientsRecyclerView.setAdapter(wifiApClientsRecyclerViewAdapter);
        this.mClientsRecyclerView.suppressLayout(true);
        this.mContainerLinearLayout =
                (LinearLayout)
                        this.mThisLayoutPreference.mRootView.findViewById(R.id.container_layout);
        TypedValue typedValue = new TypedValue();
        if (this.mIsPreferenceClickEnabled) {
            this.mContext
                    .getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        } else {
            this.mContext
                    .getTheme()
                    .resolveAttribute(android.R.attr.itemBackground, typedValue, true);
        }
        this.mContainerLinearLayout.setBackgroundResource(typedValue.resourceId);
        if (this.mIsSetOrEditButtonViewVisible) {
            this.mSetOrEditLimitFrameLayout.setVisibility(0);
        } else {
            this.mSetOrEditLimitFrameLayout.setVisibility(8);
        }
        this.mWifiApLockUnlockButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiApMobileDataSharedTodayPreferenceController.this
                                .lambda$displayPreference$0(view);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext) ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())
                || !this.mIsPreferenceClickEnabled) {
            return false;
        }
        WifiApSettings wifiApSettings = this.mWifiApSettings;
        wifiApSettings.getClass();
        SALogging.insertSALog("TETH_010", "8070");
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(wifiApSettings.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mDestinationName = WifiApClientsManageMobileHotspot.class.getCanonicalName();
        subSettingLauncher.launch();
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_START) {
            this.mContext.registerReceiver(this.mBroadcastReceiver, INTENT_FILTER, 2);
            return;
        }
        if (event == Lifecycle.Event.ON_RESUME) {
            this.mIsAnimateProgressBarRequired = true;
            updateState(this.mThisLayoutPreference);
        } else if (event == Lifecycle.Event.ON_STOP) {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            AlertDialog alertDialog = this.mLockNetworkDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                return;
            }
            this.mLockNetworkDialog.dismiss();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(WifiApSettings wifiApSettings) {
        this.mWifiApSettings = wifiApSettings;
        this.mIsPreferenceClickEnabled = true;
        this.mIsClientsListViewVisible = true;
        this.mIsSetOrEditButtonViewVisible = false;
        if (WifiApFeatureUtils.isWifiApLockNetworkSupported()) {
            this.mIsLockUnlockNetworkButtonToBeShown = false;
        }
        this.mMaxPositionToIncludeDisconnectedInList = 3;
        this.mMaxNumberOfClientsRequired = 3;
        this.mIsTop10ColorsToBeShown = false;
        this.mIsDataLimitAlertIconToBeShown = false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void showLockNetworkDialog() {
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        builder.setMessage(R.string.wifi_ap_lock_dialog_description);
        builder.setPositiveButton(R.string.wifi_ap_lock, new AnonymousClass4(0, this));
        builder.setNegativeButton(R.string.cancel, new AnonymousClass5());
        builder.setOnDismissListener(new AnonymousClass2(this, 1));
        builder.setTitle(R.string.wifi_ap_lock_mobile_hotspot);
        AlertDialog create = builder.create();
        this.mLockNetworkDialog = create;
        create.show();
    }

    public void updateState(
            List<WifiApClientConfig> list, WifiApDataUsageConfig wifiApDataUsageConfig) {
        int i;
        String quantityString;
        WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(this.mContext);
        String usageValueInLocaleString =
                wifiApDataUsageConfig.getUsageValueInLocaleString(this.mContext);
        String usageValueInLocaleString2 =
                wifiApActiveSessionDataLimit.getUsageValueInLocaleString(this.mContext);
        boolean z = ((double) wifiApActiveSessionDataLimit.mUsageValueInBytes) > 0.0d;
        List wifiApStaListDetail =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).getWifiApStaListDetail();
        if (wifiApStaListDetail != null) {
            this.mConnectedDeviceCount = wifiApStaListDetail.size();
        }
        this.mDataUsageTextView.setText(usageValueInLocaleString);
        this.mDataLimitTextView.setText(usageValueInLocaleString2);
        if (z) {
            this.mSetOrEditLimitButton.setText(R.string.wifi_ap_edit_data_limit);
            this.mDataLimitLayout.setVisibility(0);
            this.mDataLimitTextView.setText(usageValueInLocaleString2);
            i =
                    (int)
                            ((wifiApDataUsageConfig.getUsageValueInMB()
                                            / wifiApActiveSessionDataLimit.getUsageValueInMB())
                                    * 100.0d);
        } else {
            this.mSetOrEditLimitButton.setText(R.string.wifi_ap_set_data_limit);
            this.mDataLimitLayout.setVisibility(8);
            i = 0;
        }
        if (i >= 100) {
            this.mDataLimitWarningTextView.setVisibility(0);
            this.mDataLimitWarningTextView.setText(
                    this.mContext.getString(R.string.wifi_ap_data_limit_reached));
            if (this.mIsDataLimitAlertIconToBeShown) {
                this.mDataLimitAlertImageView.setVisibility(0);
            }
        } else if (i >= 90) {
            this.mDataLimitWarningTextView.setVisibility(0);
            this.mDataLimitWarningTextView.setText(
                    this.mContext.getString(R.string.wifi_ap_data_limit_almost_reached));
            if (this.mIsDataLimitAlertIconToBeShown) {
                this.mDataLimitAlertImageView.setVisibility(0);
            }
        } else {
            this.mDataLimitWarningTextView.setVisibility(8);
            this.mDataLimitAlertImageView.setVisibility(8);
        }
        if (this.mDataLimitAlertImageView.getVisibility() == 0) {
            this.mDataLimitAlertImageView.setOnClickListener(new AnonymousClass7(i));
        }
        if (this.mConnectedDeviceCount == 0) {
            quantityString =
                    wifiApDataUsageConfig.getUsageValueInMB() <= 0.0d
                            ? this.mContext.getString(
                                    WifiApUtils.getStringID(
                                            R.string.wifi_ap_devices_that_use_your_hotspot_shown))
                            : this.mContext.getString(R.string.wifi_ap_no_devices_connected);
        } else {
            Resources resources = this.mContext.getResources();
            int i2 = this.mConnectedDeviceCount;
            quantityString =
                    resources.getQuantityString(
                            R.plurals.wifi_ap_device_connected, i2, Integer.valueOf(i2));
        }
        this.mConnectedClientsSummaryTextView.setText(quantityString);
        int size = list.size();
        MainClearConfirm$$ExternalSyntheticOutline0.m(size, "hotspotClientList.size(): ", TAG);
        Parcelable.Creator<WifiApClientConfig> creator = WifiApClientConfig.CREATOR;
        int size2 = list.size();
        float[] fArr = new float[size2];
        for (int i3 = 0; i3 < list.size(); i3++) {
            fArr[i3] =
                    (float)
                            new WifiApDataUsageConfig(list.get(i3).mDataUsageForTodayInBytes)
                                    .getUsageValueInMB();
        }
        float f = 0.0f;
        for (int i4 = 0; i4 < size2; i4++) {
            f += fArr[i4];
        }
        this.mStackProgressbar.setProgressbar(
                fArr,
                f
                        + (((double)
                                                WifiApConnectedDeviceUtils
                                                        .getWifiApActiveSessionDataLimit(
                                                                this.mContext)
                                                        .mUsageValueInBytes)
                                        > 0.0d
                                ? (float)
                                        (wifiApActiveSessionDataLimit.getUsageValueInMB()
                                                - wifiApDataUsageConfig.getUsageValueInMB())
                                : 0.0f),
                this.mIsAnimateProgressBarRequired);
        this.mIsAnimateProgressBarRequired = false;
        if (this.mIsClientsListViewVisible) {
            this.mClientsRecyclerView.suppressLayout(false);
            WifiApClientsRecyclerViewAdapter wifiApClientsRecyclerViewAdapter =
                    this.mClientsRecyclerViewAdapter;
            wifiApClientsRecyclerViewAdapter.mTopHotspotWifiApClientConfigList = list;
            wifiApClientsRecyclerViewAdapter.notifyDataSetChanged();
            this.mClientsRecyclerView.suppressLayout(true);
            if (size > 0) {
                this.mClientsRecyclerView.setVisibility(0);
            } else {
                this.mClientsRecyclerView.setVisibility(8);
            }
        } else {
            this.mClientsRecyclerView.setVisibility(8);
        }
        if (WifiApFeatureUtils.isWifiApLockNetworkSupported()) {
            if (WifiApFrameworkUtils.isHotspotNetworkLocked(this.mSemWifiManager)) {
                this.mWifiApLockedImageView.setVisibility(0);
                if (!this.mIsLockUnlockNetworkButtonToBeShown) {
                    this.mWifiApLockUnlockFrameLayout.setVisibility(8);
                    return;
                } else {
                    this.mWifiApLockUnlockButton.setText(R.string.wifi_ap_unlock_hotspot);
                    this.mWifiApLockUnlockFrameLayout.setVisibility(0);
                    return;
                }
            }
            if (this.mConnectedDeviceCount <= 0) {
                this.mWifiApLockedImageView.setVisibility(8);
                this.mWifiApLockUnlockFrameLayout.setVisibility(8);
                return;
            }
            this.mWifiApLockedImageView.setVisibility(8);
            if (!this.mIsLockUnlockNetworkButtonToBeShown) {
                this.mWifiApLockUnlockFrameLayout.setVisibility(8);
            } else {
                this.mWifiApLockUnlockButton.setText(R.string.wifi_ap_lock_hotspot);
                this.mWifiApLockUnlockFrameLayout.setVisibility(0);
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void setHost(WifiApClientsManageMobileHotspot wifiApClientsManageMobileHotspot) {
        this.mWifiApClientsManageMobileHotspot = wifiApClientsManageMobileHotspot;
        this.mIsPreferenceClickEnabled = false;
        this.mIsClientsListViewVisible = false;
        this.mIsSetOrEditButtonViewVisible = true;
        if (WifiApFeatureUtils.isWifiApLockNetworkSupported()) {
            this.mIsLockUnlockNetworkButtonToBeShown = true;
        }
        this.mMaxPositionToIncludeDisconnectedInList = 10;
        this.mMaxNumberOfClientsRequired = 20;
        this.mIsTop10ColorsToBeShown = true;
        this.mIsDataLimitAlertIconToBeShown = true;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        Context context = this.mContext;
        int i = this.mMaxPositionToIncludeDisconnectedInList;
        int i2 = this.mMaxNumberOfClientsRequired;
        Log.i(
                "WifiApConnectedDeviceUtils",
                "getTopHotspotClientConfigListToday - topConnectedAndDisconnected: "
                        + i
                        + ", maxListLength: "
                        + i2);
        new ArrayList();
        updateState(
                WifiApConnectedDeviceUtils.getTopHotspotClientConfigListToday(
                        context,
                        WifiApFrameworkUtils.getSemWifiManager(context)
                                .getTopHotspotClientsToday(i, i2)),
                WifiApConnectedDeviceUtils.getWifiApTodayTotalDataUsage(this.mContext));
    }
}
