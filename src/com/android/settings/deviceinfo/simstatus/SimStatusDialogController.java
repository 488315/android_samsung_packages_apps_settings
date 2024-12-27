package com.android.settings.deviceinfo.simstatus;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.telephony.CarrierConfigManager;
import android.telephony.ICellBroadcastService;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;

import com.android.settings.R;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimStatusDialogController implements DefaultLifecycleObserver {
    static final int CELLULAR_NETWORK_STATE = 2131362970;
    static final int CELL_DATA_NETWORK_TYPE_VALUE_ID = 2131362962;
    static final int CELL_VOICE_NETWORK_TYPE_VALUE_ID = 2131366132;
    static final int ICCID_INFO_LABEL_ID = 2131363713;
    static final int ICCID_INFO_VALUE_ID = 2131363714;
    static final int IMS_REGISTRATION_STATE_LABEL_ID = 2131363788;
    static final int IMS_REGISTRATION_STATE_VALUE_ID = 2131363789;
    static final int MAX_PHONE_COUNT_SINGLE_SIM = 1;
    static final int NETWORK_PROVIDER_VALUE_ID = 2131364566;
    static final int OPERATOR_INFO_LABEL_ID = 2131363944;
    static final int OPERATOR_INFO_VALUE_ID = 2131363945;
    static final int PHONE_NUMBER_VALUE_ID = 2131364519;
    static final int ROAMING_INFO_VALUE_ID = 2131365023;
    static final int SERVICE_STATE_VALUE_ID = 2131365273;
    static final int SIGNAL_STRENGTH_LABEL_ID = 2131365424;
    static final int SIGNAL_STRENGTH_VALUE_ID = 2131365425;
    public final CarrierConfigManager mCarrierConfigManager;
    public CellBroadcastServiceConnection mCellBroadcastServiceConnection;
    public final Context mContext;
    public final SimStatusDialogFragment mDialog;
    public final Resources mRes;
    public boolean mShowLatestAreaInfo;
    public final int mSlotIndex;
    public SubscriptionInfo mSubscriptionInfo;
    public final SubscriptionManager mSubscriptionManager;
    protected SimStatusDialogTelephonyCallback mTelephonyCallback;
    public TelephonyDisplayInfo mTelephonyDisplayInfo;
    public TelephonyManager mTelephonyManager;
    public final AnonymousClass1 mOnSubscriptionsChangedListener =
            new SubscriptionManager
                    .OnSubscriptionsChangedListener() { // from class:
                                                        // com.android.settings.deviceinfo.simstatus.SimStatusDialogController.1
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public final void onSubscriptionsChanged() {
                    SimStatusDialogController simStatusDialogController =
                            SimStatusDialogController.this;
                    simStatusDialogController.mSubscriptionInfo =
                            SubscriptionManager.from(simStatusDialogController.mContext)
                                    .getActiveSubscriptionInfoForSimSlotIndex(
                                            simStatusDialogController.mSlotIndex);
                    SimStatusDialogController.this.updateSubscriptionStatus();
                }
            };
    public boolean mIsRegisteredListener = false;
    public final AnonymousClass2 mAreaInfoReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.deviceinfo.simstatus.SimStatusDialogController.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.telephony.action.AREA_INFO_UPDATED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0);
                        SimStatusDialogController simStatusDialogController =
                                SimStatusDialogController.this;
                        if (intExtra == simStatusDialogController.mSlotIndex) {
                            simStatusDialogController.updateAreaInfoText();
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CellBroadcastServiceConnection implements ServiceConnection {
        public IBinder mService;

        public CellBroadcastServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            this.mService = null;
            Log.d("SimStatusDialogCtrl", "Binding died");
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            this.mService = null;
            Log.d("SimStatusDialogCtrl", "Null binding");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SimStatusDialogCtrl", "connected to CellBroadcastService");
            this.mService = iBinder;
            SimStatusDialogController.this.updateAreaInfoText();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            this.mService = null;
            Log.d("SimStatusDialogCtrl", "mICellBroadcastService has disconnected unexpectedly");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SimStatusDialogTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.DataConnectionStateListener,
                    TelephonyCallback.ServiceStateListener,
                    TelephonyCallback.DisplayInfoListener {
        public SimStatusDialogTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            SimStatusDialogController simStatusDialogController = SimStatusDialogController.this;
            simStatusDialogController.mDialog.setText(
                    SimStatusDialogController.CELLULAR_NETWORK_STATE,
                    i != 0
                            ? i != 1
                                    ? i != 2
                                            ? i != 3
                                                    ? simStatusDialogController.mRes.getString(
                                                            R.string.radioInfo_unknown)
                                                    : simStatusDialogController.mRes.getString(
                                                            R.string.radioInfo_data_suspended)
                                            : simStatusDialogController.mRes.getString(
                                                    R.string.radioInfo_data_connected)
                                    : simStatusDialogController.mRes.getString(
                                            R.string.radioInfo_data_connecting)
                            : simStatusDialogController.mRes.getString(
                                    R.string.radioInfo_data_disconnected));
            SimStatusDialogController.this.updateNetworkType();
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            SimStatusDialogController simStatusDialogController = SimStatusDialogController.this;
            simStatusDialogController.mTelephonyDisplayInfo = telephonyDisplayInfo;
            simStatusDialogController.updateNetworkType();
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            SimStatusDialogController simStatusDialogController = SimStatusDialogController.this;
            SubscriptionInfo subscriptionInfo = simStatusDialogController.mSubscriptionInfo;
            simStatusDialogController.mDialog.setText(
                    SimStatusDialogController.NETWORK_PROVIDER_VALUE_ID,
                    subscriptionInfo != null ? subscriptionInfo.getCarrierName() : null);
            SimStatusDialogController.this.updateServiceState(serviceState);
            SimStatusDialogController.this.updateRoamingStatus(serviceState);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.deviceinfo.simstatus.SimStatusDialogController$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.deviceinfo.simstatus.SimStatusDialogController$2] */
    public SimStatusDialogController(
            SimStatusDialogFragment simStatusDialogFragment, Lifecycle lifecycle, int i) {
        this.mDialog = simStatusDialogFragment;
        Context context = simStatusDialogFragment.getContext();
        this.mContext = context;
        this.mSlotIndex = i;
        this.mSubscriptionInfo =
                SubscriptionManager.from(context).getActiveSubscriptionInfoForSimSlotIndex(i);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mRes = context.getResources();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public static String getNetworkTypeName(int i) {
        switch (i) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA - EvDo rev. 0";
            case 6:
                return "CDMA - EvDo rev. A";
            case 7:
                return "CDMA - 1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDEN";
            case 12:
                return "CDMA - EvDo rev. B";
            case 13:
                return "LTE";
            case 14:
                return "CDMA - eHRPD";
            case 15:
                return "HSPA+";
            case 16:
                return "GSM";
            case 17:
                return "TD_SCDMA";
            case 18:
                return "IWLAN";
            case 19:
            default:
                return "UNKNOWN";
            case 20:
                return "NR SA";
        }
    }

    public TelephonyManager getTelephonyManager() {
        return this.mTelephonyManager;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onPause(LifecycleOwner lifecycleOwner) {
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        AnonymousClass2 anonymousClass2 = this.mAreaInfoReceiver;
        AnonymousClass1 anonymousClass1 = this.mOnSubscriptionsChangedListener;
        if (subscriptionInfo != null) {
            this.mSubscriptionManager.removeOnSubscriptionsChangedListener(anonymousClass1);
            getTelephonyManager().unregisterTelephonyCallback(this.mTelephonyCallback);
            if (this.mShowLatestAreaInfo) {
                this.mContext.unregisterReceiver(anonymousClass2);
                return;
            }
            return;
        }
        if (this.mIsRegisteredListener) {
            this.mSubscriptionManager.removeOnSubscriptionsChangedListener(anonymousClass1);
            getTelephonyManager().unregisterTelephonyCallback(this.mTelephonyCallback);
            if (this.mShowLatestAreaInfo) {
                this.mContext.unregisterReceiver(anonymousClass2);
            }
            this.mIsRegisteredListener = false;
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onResume(LifecycleOwner lifecycleOwner) {
        if (this.mSubscriptionInfo == null) {
            return;
        }
        this.mTelephonyManager =
                getTelephonyManager()
                        .createForSubscriptionId(this.mSubscriptionInfo.getSubscriptionId());
        getTelephonyManager()
                .registerTelephonyCallback(
                        this.mContext.getMainExecutor(), this.mTelephonyCallback);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(
                this.mContext.getMainExecutor(), this.mOnSubscriptionsChangedListener);
        SimStatusDialogRepository simStatusDialogRepository =
                new SimStatusDialogRepository(this.mContext);
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.deviceinfo.simstatus.SimStatusDialogController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        SimStatusDialogRepository.SimStatusDialogInfo simStatusDialogInfo =
                                (SimStatusDialogRepository.SimStatusDialogInfo) obj;
                        SimStatusDialogController simStatusDialogController =
                                SimStatusDialogController.this;
                        simStatusDialogController.getClass();
                        String str = simStatusDialogInfo.signalStrength;
                        boolean z = str != null;
                        int i = SimStatusDialogController.SIGNAL_STRENGTH_LABEL_ID;
                        SimStatusDialogFragment simStatusDialogFragment =
                                simStatusDialogController.mDialog;
                        simStatusDialogFragment.setSettingVisibility(i, z);
                        int i2 = SimStatusDialogController.SIGNAL_STRENGTH_VALUE_ID;
                        simStatusDialogFragment.setSettingVisibility(i2, z);
                        simStatusDialogFragment.setText(i2, str);
                        Boolean bool = simStatusDialogInfo.imsRegistered;
                        boolean z2 = bool != null;
                        simStatusDialogFragment.setSettingVisibility(
                                SimStatusDialogController.IMS_REGISTRATION_STATE_LABEL_ID, z2);
                        int i3 = SimStatusDialogController.IMS_REGISTRATION_STATE_VALUE_ID;
                        simStatusDialogFragment.setSettingVisibility(i3, z2);
                        simStatusDialogFragment.setText(
                                i3,
                                simStatusDialogController.mRes.getString(
                                        Boolean.TRUE.equals(bool)
                                                ? R.string.ims_reg_status_registered
                                                : R.string.ims_reg_status_not_registered));
                        return Unit.INSTANCE;
                    }
                };
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(lifecycleOwner),
                null,
                null,
                new SimStatusDialogRepository$collectSimStatusDialogInfo$1(
                        lifecycleOwner,
                        simStatusDialogRepository,
                        this.mSlotIndex,
                        function1,
                        null),
                3);
        if (this.mShowLatestAreaInfo) {
            updateAreaInfoText();
            this.mContext.registerReceiver(
                    this.mAreaInfoReceiver,
                    new IntentFilter("android.telephony.action.AREA_INFO_UPDATED"),
                    2);
        }
        this.mIsRegisteredListener = true;
    }

    public final void updateAreaInfoText() {
        CellBroadcastServiceConnection cellBroadcastServiceConnection;
        ICellBroadcastService asInterface;
        if (!this.mShowLatestAreaInfo
                || (cellBroadcastServiceConnection = this.mCellBroadcastServiceConnection) == null
                || (asInterface =
                                ICellBroadcastService.Stub.asInterface(
                                        cellBroadcastServiceConnection.mService))
                        == null) {
            return;
        }
        try {
            this.mDialog.setText(
                    OPERATOR_INFO_VALUE_ID, asInterface.getCellBroadcastAreaInfo(this.mSlotIndex));
        } catch (RemoteException e) {
            Log.d("SimStatusDialogCtrl", "Can't get area info. e=" + e);
        }
    }

    public final void updateNetworkType() {
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        SimStatusDialogFragment simStatusDialogFragment = this.mDialog;
        if (subscriptionInfo == null) {
            String networkTypeName = getNetworkTypeName(0);
            simStatusDialogFragment.setText(CELL_VOICE_NETWORK_TYPE_VALUE_ID, networkTypeName);
            simStatusDialogFragment.setText(CELL_DATA_NETWORK_TYPE_VALUE_ID, networkTypeName);
            return;
        }
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        int dataNetworkType = getTelephonyManager().getDataNetworkType();
        int voiceNetworkType = getTelephonyManager().getVoiceNetworkType();
        TelephonyDisplayInfo telephonyDisplayInfo = this.mTelephonyDisplayInfo;
        int overrideNetworkType =
                telephonyDisplayInfo == null ? 0 : telephonyDisplayInfo.getOverrideNetworkType();
        String networkTypeName2 = dataNetworkType != 0 ? getNetworkTypeName(dataNetworkType) : null;
        String networkTypeName3 =
                voiceNetworkType != 0 ? getNetworkTypeName(voiceNetworkType) : null;
        boolean z = overrideNetworkType == 5 || overrideNetworkType == 3;
        if (dataNetworkType == 13 && z) {
            networkTypeName2 = "NR NSA";
        }
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(subscriptionId);
        if (configForSubId != null
                ? configForSubId.getBoolean("show_4g_for_lte_data_icon_bool")
                : false) {
            if ("LTE".equals(networkTypeName2)) {
                networkTypeName2 = "4G";
            }
            if ("LTE".equals(networkTypeName3)) {
                networkTypeName3 = "4G";
            }
        }
        simStatusDialogFragment.setText(CELL_VOICE_NETWORK_TYPE_VALUE_ID, networkTypeName3);
        simStatusDialogFragment.setText(CELL_DATA_NETWORK_TYPE_VALUE_ID, networkTypeName2);
    }

    public void updatePhoneNumber() {
        this.mDialog.setText(
                PHONE_NUMBER_VALUE_ID,
                SubscriptionUtil.getBidiFormattedPhoneNumber(
                        this.mContext, this.mSubscriptionInfo));
    }

    public final void updateRoamingStatus(ServiceState serviceState) {
        SimStatusDialogFragment simStatusDialogFragment = this.mDialog;
        if (serviceState == null) {
            simStatusDialogFragment.setText(
                    ROAMING_INFO_VALUE_ID, this.mRes.getString(R.string.radioInfo_unknown));
        } else if (serviceState.getRoaming()) {
            simStatusDialogFragment.setText(
                    ROAMING_INFO_VALUE_ID, this.mRes.getString(R.string.radioInfo_roaming_in));
        } else {
            simStatusDialogFragment.setText(
                    ROAMING_INFO_VALUE_ID, this.mRes.getString(R.string.radioInfo_roaming_not));
        }
    }

    public final void updateServiceState(ServiceState serviceState) {
        int combinedServiceState = Utils.getCombinedServiceState(serviceState);
        this.mDialog.setText(
                SERVICE_STATE_VALUE_ID,
                combinedServiceState != 0
                        ? (combinedServiceState == 1 || combinedServiceState == 2)
                                ? this.mRes.getString(R.string.radioInfo_service_out)
                                : combinedServiceState != 3
                                        ? this.mRes.getString(R.string.radioInfo_unknown)
                                        : this.mRes.getString(R.string.radioInfo_service_off)
                        : this.mRes.getString(R.string.radioInfo_service_in));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSubscriptionStatus() {
        /*
            r3 = this;
            android.telephony.SubscriptionInfo r0 = r3.mSubscriptionInfo
            if (r0 == 0) goto L9
            java.lang.CharSequence r0 = r0.getCarrierName()
            goto La
        L9:
            r0 = 0
        La:
            int r1 = com.android.settings.deviceinfo.simstatus.SimStatusDialogController.NETWORK_PROVIDER_VALUE_ID
            com.android.settings.deviceinfo.simstatus.SimStatusDialogFragment r2 = r3.mDialog
            r2.setText(r1, r0)
            android.telephony.TelephonyManager r0 = r3.getTelephonyManager()
            android.telephony.ServiceState r0 = r0.getServiceState()
            r3.updatePhoneNumber()
            r3.updateServiceState(r0)
            r3.updateNetworkType()
            r3.updateRoamingStatus(r0)
            android.telephony.SubscriptionInfo r0 = r3.mSubscriptionInfo
            if (r0 == 0) goto L3c
            int r0 = r0.getSubscriptionId()
            android.telephony.CarrierConfigManager r1 = r3.mCarrierConfigManager
            android.os.PersistableBundle r0 = r1.getConfigForSubId(r0)
            if (r0 == 0) goto L3c
            java.lang.String r1 = "show_iccid_in_sim_status_bool"
            boolean r0 = r0.getBoolean(r1)
            goto L3d
        L3c:
            r0 = 0
        L3d:
            if (r0 != 0) goto L4a
            int r3 = com.android.settings.deviceinfo.simstatus.SimStatusDialogController.ICCID_INFO_LABEL_ID
            r2.removeSettingFromScreen(r3)
            int r3 = com.android.settings.deviceinfo.simstatus.SimStatusDialogController.ICCID_INFO_VALUE_ID
            r2.removeSettingFromScreen(r3)
            goto L57
        L4a:
            int r0 = com.android.settings.deviceinfo.simstatus.SimStatusDialogController.ICCID_INFO_VALUE_ID
            android.telephony.TelephonyManager r3 = r3.getTelephonyManager()
            java.lang.String r3 = r3.getSimSerialNumber()
            r2.setText(r0, r3)
        L57:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.deviceinfo.simstatus.SimStatusDialogController.updateSubscriptionStatus():void");
    }
}
