package com.samsung.android.settings.deviceinfo.statusinfo.simstatus;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.ICellBroadcastService;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerImpl$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sec_platform_library.FactoryPhone;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.SecMultiSIMTabInterface;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;
import com.sec.ims.ImsManager;
import com.sec.ims.settings.ImsSettings;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSimStatus extends SettingsPreferenceFragment implements SecMultiSIMTabInterface {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final boolean mSupportSignalStrength;
    public static final String sSalesCode;
    public final AnonymousClass1 mAreaInfoReceiver;
    public CellBroadcastServiceConnection mCellBroadcastServiceConnection;
    public Context mContext;
    public Preference mImsPreference;
    public ServiceState mPreviousServiceState;
    public Resources mRes;
    public boolean mShowLatestAreaInfo;
    public final AnonymousClass1 mSimHotSwapReceiver;
    public SubscriptionInfo mSubscriptionInfo;
    public SubscriptionManager mSubscriptionManager;
    public SimStatusDialogTelephonyCallback mTelephonyCallback;
    public TelephonyDisplayInfo mTelephonyDisplayInfo;
    public TelephonyManager mTelephonyManager;
    public int mSimSlotCount = 0;
    public final ImsManager[] mImsManager = new ImsManager[2];
    public final boolean[] mIsRegistered = new boolean[2];
    public final boolean[] mIsShowImsStatus = new boolean[2];
    public FactoryPhone mFactoryPhone = null;
    public int mCurrentSlotId = 0;
    public int mCurrentSubId = -1;
    public boolean mIsRegisteredListener = false;
    public final AnonymousClass2 mOnSubscriptionsChangedListener =
            new SubscriptionManager
                    .OnSubscriptionsChangedListener() { // from class:
                                                        // com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.2
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public final void onSubscriptionsChanged() {
                    TooltipPopup$$ExternalSyntheticOutline0.m(
                            new StringBuilder("onSubscriptionsChanged() - mCurrentSlotId: "),
                            SecSimStatus.this.mCurrentSlotId,
                            "SecSimStatus");
                    SecSimStatus secSimStatus = SecSimStatus.this;
                    secSimStatus.mSubscriptionInfo =
                            SubscriptionManager.from(secSimStatus.mContext)
                                    .getActiveSubscriptionInfoForSimSlotIndex(
                                            secSimStatus.mCurrentSlotId);
                    SecSimStatus secSimStatus2 = SecSimStatus.this;
                    SubscriptionInfo subscriptionInfo = secSimStatus2.mSubscriptionInfo;
                    if (subscriptionInfo == null) {
                        Log.i("SecSimStatus", "mSubscriptionInfo == null");
                        SecSimStatus.this.updateSubscriptionStatusNotAvailable();
                    } else {
                        secSimStatus2.mCurrentSubId = subscriptionInfo.getSubscriptionId();
                        TooltipPopup$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mCurrentSubId: "),
                                SecSimStatus.this.mCurrentSubId,
                                "SecSimStatus");
                        SecSimStatus.this.updateSubscriptionStatus$1();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            boolean z;
            SubscriptionInfo subscriptionInfo;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            List<SubscriptionInfo> activeSubscriptionInfoList =
                    SubscriptionManager.from(context).getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                activeSubscriptionInfoList.sort(
                        new Comparator() { // from class:
                                           // com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.3.1
                            public final Collator mCollator = Collator.getInstance();

                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                return this.mCollator.compare(
                                        Integer.toString(
                                                ((SubscriptionInfo) obj).getSimSlotIndex()),
                                        Integer.toString(
                                                ((SubscriptionInfo) obj2).getSimSlotIndex()));
                            }
                        });
            }
            int simCount = ((TelephonyManager) context.getSystemService("phone")).getSimCount();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(simCount, "simCount: ", "SecSimStatus");
            boolean[] zArr = new boolean[2];
            int i = 0;
            for (int i2 = 0; i2 < simCount && i2 < 2; i2++) {
                Log.d("SecSimStatus", "slotId: " + i2);
                zArr[i2] = SecSimStatus.isShowImsStatus(context, i2);
            }
            boolean z2 = zArr[0];
            if (activeSubscriptionInfoList != null) {
                int size = activeSubscriptionInfoList.size();
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(size, "subSize: ", "SecSimStatus");
                if (size >= 2) {
                    z2 = zArr[0] || zArr[1];
                } else if (size == 1
                        && (subscriptionInfo = activeSubscriptionInfoList.get(0)) != null) {
                    int simSlotIndex = subscriptionInfo.getSimSlotIndex();
                    boolean z3 = zArr[simSlotIndex];
                    Log.i(
                            "SecSimStatus",
                            "currentSlotId: " + simSlotIndex + ", isImsVisible: " + z3);
                    z2 = z3;
                }
            }
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isImsVisible: ", "SecSimStatus", z2);
            if (!z2) {
                ((ArrayList) nonIndexableKeys).add("ims_reg");
            }
            if (!SecSimStatus.mSupportSignalStrength) {
                ((ArrayList) nonIndexableKeys).add("signal_strength");
            }
            if (activeSubscriptionInfoList != null) {
                boolean z4 = false;
                z = false;
                while (i < activeSubscriptionInfoList.size()) {
                    if (activeSubscriptionInfoList.get(i) != null) {
                        if (SecSimStatus.supportIccId(
                                i, context, activeSubscriptionInfoList.get(i))) {
                            z4 = true;
                            z = true;
                        } else {
                            z4 = true;
                        }
                    }
                    Log.i(
                            "SecSimStatus",
                            "currentSlotId: "
                                    + i
                                    + ", isOperatorInfoAvailable: "
                                    + z4
                                    + ", isIccIdAvailable: "
                                    + z);
                    i++;
                }
                i = z4 ? 1 : 0;
            } else {
                z = false;
            }
            if (i == 0) {
                ((ArrayList) nonIndexableKeys).add("latest_area_info");
            }
            if (!z) {
                ((ArrayList) nonIndexableKeys).add("icc_id");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ((UserManager) context.getSystemService(UserManager.class)).isAdminUser()
                    && !Utils.isWifiOnly(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CellBroadcastServiceConnection implements ServiceConnection {
        public IBinder mService;

        public CellBroadcastServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            this.mService = null;
            Log.d("SecSimStatus", "Binding died");
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            this.mService = null;
            Log.d("SecSimStatus", "Null binding");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SecSimStatus", "connected to CellBroadcastService");
            this.mService = iBinder;
            SecSimStatus secSimStatus = SecSimStatus.this;
            boolean z = SecSimStatus.mSupportSignalStrength;
            secSimStatus.updateAreaInfoText$1();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            this.mService = null;
            Log.d("SecSimStatus", "mICellBroadcastService has disconnected unexpectedly");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class IndianOperators {
        public static final /* synthetic */ IndianOperators[] $VALUES;
        public static final IndianOperators AIRTEL;
        public static final IndianOperators OTHERS;
        public static final IndianOperators RELIANCE;

        static {
            IndianOperators indianOperators = new IndianOperators("AIRTEL", 0);
            AIRTEL = indianOperators;
            IndianOperators indianOperators2 = new IndianOperators("RELIANCE", 1);
            RELIANCE = indianOperators2;
            IndianOperators indianOperators3 = new IndianOperators("OTHERS", 2);
            OTHERS = indianOperators3;
            $VALUES = new IndianOperators[] {indianOperators, indianOperators2, indianOperators3};
        }

        public static IndianOperators valueOf(String str) {
            return (IndianOperators) Enum.valueOf(IndianOperators.class, str);
        }

        public static IndianOperators[] values() {
            return (IndianOperators[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimStatusDialogTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.DataConnectionStateListener,
                    TelephonyCallback.SignalStrengthsListener,
                    TelephonyCallback.ServiceStateListener,
                    TelephonyCallback.DisplayInfoListener {
        public SimStatusDialogTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            ServiceState currentServiceState;
            int voiceRegState;
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "onDataConnectionStateChanged() : state =",
                    " networkType = ",
                    i,
                    i2,
                    "SecSimStatus");
            SecSimStatus secSimStatus = SecSimStatus.this;
            String string =
                    i != 0
                            ? i != 1
                                    ? i != 2
                                            ? i != 3
                                                    ? secSimStatus.mRes.getString(
                                                            R.string.radioInfo_unknown)
                                                    : secSimStatus.mRes.getString(
                                                            R.string.radioInfo_data_suspended)
                                            : secSimStatus.mRes.getString(
                                                    R.string.radioInfo_data_connected)
                                    : secSimStatus.mRes.getString(
                                            R.string.radioInfo_data_connecting)
                            : secSimStatus.mRes.getString(R.string.radioInfo_data_disconnected);
            String str = SecSimStatus.sSalesCode;
            if (("VZW".equals(str) || "VPP".equals(str))
                    && (currentServiceState = secSimStatus.getCurrentServiceState()) != null
                    && ((voiceRegState = currentServiceState.getVoiceRegState()) == 1
                                    || voiceRegState == 2
                            ? currentServiceState.getDataRegState() == 0
                            : voiceRegState != 3)) {
                string = secSimStatus.mRes.getString(R.string.radioInfo_data_connected);
            }
            secSimStatus.setSummaryText("data_state", string);
            SecSimStatus.this.updateNetworkType$1();
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            Log.i("SecSimStatus", "onDisplayInfoChanged()");
            SecSimStatus secSimStatus = SecSimStatus.this;
            secSimStatus.mTelephonyDisplayInfo = telephonyDisplayInfo;
            secSimStatus.updateNetworkType$1();
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            Log.i("SecSimStatus", "onServiceStateChanged()");
            SecSimStatus secSimStatus = SecSimStatus.this;
            boolean z = SecSimStatus.mSupportSignalStrength;
            secSimStatus.updateNetworkProvider$1();
            SecSimStatus.this.updateServiceState$1(serviceState);
            SecSimStatus.this.updateRoamingStatus$1(serviceState);
            SecSimStatus.this.mPreviousServiceState = serviceState;
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Log.i("SecSimStatus", "onSignalStrengthsChanged()");
            SecSimStatus secSimStatus = SecSimStatus.this;
            boolean z = SecSimStatus.mSupportSignalStrength;
            secSimStatus.updateSignalStrength(signalStrength);
        }
    }

    static {
        mSupportSignalStrength = (Rune.isDomesticModel() || Rune.isJapanDCMModel()) ? false : true;
        sSalesCode = com.android.settings.Utils.getSalesCode();
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3(R.xml.sec_device_info_status_sim);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus$1] */
    public SecSimStatus() {
        final int i = 0;
        this.mSimHotSwapReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.1
                    public final /* synthetic */ SecSimStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                if ("com.samsung.intent.action.SIMHOTSWAP"
                                        .equals(intent.getAction())) {
                                    Log.i(
                                            "SecSimStatus",
                                            "com.samsung.intent.action.SIMHOTSWAP, call finish()");
                                    this.this$0.finish();
                                    break;
                                }
                                break;
                            default:
                                Log.i(
                                        "SecSimStatus",
                                        "IntentFilter"
                                            + " CellBroadcastIntents.ACTION_AREA_INFO_UPDATED");
                                if ("android.telephony.action.AREA_INFO_UPDATED"
                                        .equals(intent.getAction())) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.telephony.extra.SLOT_INDEX", 0);
                                    SecSimStatus secSimStatus = this.this$0;
                                    if (intExtra == secSimStatus.mCurrentSlotId) {
                                        secSimStatus.updateAreaInfoText$1();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mAreaInfoReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.1
                    public final /* synthetic */ SecSimStatus this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                if ("com.samsung.intent.action.SIMHOTSWAP"
                                        .equals(intent.getAction())) {
                                    Log.i(
                                            "SecSimStatus",
                                            "com.samsung.intent.action.SIMHOTSWAP, call finish()");
                                    this.this$0.finish();
                                    break;
                                }
                                break;
                            default:
                                Log.i(
                                        "SecSimStatus",
                                        "IntentFilter"
                                            + " CellBroadcastIntents.ACTION_AREA_INFO_UPDATED");
                                if ("android.telephony.action.AREA_INFO_UPDATED"
                                        .equals(intent.getAction())) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.telephony.extra.SLOT_INDEX", 0);
                                    SecSimStatus secSimStatus = this.this$0;
                                    if (intExtra == secSimStatus.mCurrentSlotId) {
                                        secSimStatus.updateAreaInfoText$1();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public static String getDisplayNetworkProvider(
            Context context,
            CharSequence charSequence,
            TelephonyManager telephonyManager,
            SubscriptionInfo subscriptionInfo) {
        String charSequence2 = !TextUtils.isEmpty(charSequence) ? charSequence.toString() : null;
        String str = sSalesCode;
        if ("KDI".equals(str) && "kddi".equalsIgnoreCase(charSequence2)) {
            charSequence2 = "au";
        }
        if ("CCT".equals(str)) {
            boolean z =
                    Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0)
                            != 0;
            boolean isWifiCallingAvailable = telephonyManager.isWifiCallingAvailable();
            if (z && !isWifiCallingAvailable) {
                charSequence2 = context.getString(R.string.airplane_mode);
            }
        }
        if (subscriptionInfo != null) {
            int phoneId = SubscriptionManager.getPhoneId(subscriptionInfo.getSubscriptionId());
            if (SecDeviceInfoUtils.Operator.UK_VIRGIN.isInsertedSim(phoneId)
                    && "WiFiCall".equalsIgnoreCase(charSequence2)) {
                charSequence2 =
                        TelephonyManager.semGetTelephonyProperty(
                                phoneId, "gsm.operator.alpha", ApnSettings.MVNO_NONE);
            }
        }
        return TextUtils.isEmpty(charSequence2)
                ? context.getString(R.string.device_info_default)
                : charSequence2;
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

    public static boolean isShowImsStatus(Context context, int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "isShowImsStatus() START, slotId: ", "SecSimStatus");
        Cursor query =
                context.getContentResolver()
                        .query(
                                ImsSettings.GLOBAL_CONTENT_URI
                                        .buildUpon()
                                        .fragment("simslot" + i)
                                        .build(),
                                new String[] {"show_regi_info_in_sec_settings"},
                                null,
                                null,
                                null);
        if (query == null || query.getCount() == 0) {
            Log.d("SecSimStatus", "isShowImsStatus() not found");
            if (query != null) {}
            return true;
        }
        ContentValues contentValues = new ContentValues();
        try {
            try {
                if (query.moveToFirst()) {
                    DatabaseUtils.cursorRowToContentValues(query, contentValues);
                }
            } catch (RuntimeException e) {
                Log.d("SecSimStatus", e.getMessage());
            }
            Integer asInteger = contentValues.getAsInteger("show_regi_info_in_sec_settings");
            Log.d("SecSimStatus", "isShowImsStatus() obj: " + asInteger);
            boolean z = asInteger != null && asInteger.intValue() == 1;
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isShowImsStatus() isShow: ", "SecSimStatus", z);
            return z;
        } finally {
            query.close();
        }
    }

    public static boolean supportIccId(int i, Context context, SubscriptionInfo subscriptionInfo) {
        boolean z;
        Log.i("SecSimStatus", "supportIccId() START");
        boolean isPhoneTypeCdma = SecDeviceInfoUtils.isPhoneTypeCdma(context, i);
        boolean z2 = true;
        if (subscriptionInfo == null) {
            z = false;
        } else {
            z =
                    ((TelephonyManager) context.getSystemService("phone"))
                                    .getLteOnCdmaMode(subscriptionInfo.getSubscriptionId())
                            == 1;
            AbsAdapter$$ExternalSyntheticOutline0.m("LteOnCdma : ", "SecDeviceInfoUtils", z);
        }
        if ((!isPhoneTypeCdma || !z) && !Rune.isUSA()) {
            z2 = false;
        }
        Log.i("SecSimStatus", "showIccId: " + z2 + "\nsupportIccId() END");
        return z2;
    }

    public final ServiceState getCurrentServiceState() {
        Log.i("SecSimStatus", "getCurrentServiceState()");
        TelephonyManager telephonyManager = this.mTelephonyManager;
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        return telephonyManager.getServiceStateForSubscriber(
                subscriptionInfo != null ? subscriptionInfo.getSubscriptionId() : -1);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 43;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_device_info_status_sim;
    }

    public final void initialize$6() {
        String str;
        Log.i("SecSimStatus", "initialize() START");
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        if (subscriptionInfo == null) {
            Log.i("SecSimStatus", "mSubscriptionInfo == null");
            updateSubscriptionStatusNotAvailable();
            return;
        }
        this.mCurrentSlotId = subscriptionInfo.getSimSlotIndex();
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("mCurrentSlotId: "), this.mCurrentSlotId, "SecSimStatus");
        this.mCurrentSubId = this.mSubscriptionInfo.getSubscriptionId();
        Log.i("SecSimStatus", "mCurrentSubId: " + this.mCurrentSubId);
        this.mTelephonyCallback = new SimStatusDialogTelephonyCallback();
        Log.i("SecSimStatus", "updateLatestAreaInfo() START");
        this.mShowLatestAreaInfo =
                Resources.getSystem()
                                .getBoolean(
                                        android.R.bool.config_smma_notification_supported_over_ims)
                        && !SecDeviceInfoUtils.isPhoneTypeCdma(this.mContext, this.mCurrentSlotId);
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("mShowLatestAreaInfo: "),
                this.mShowLatestAreaInfo,
                "SecSimStatus");
        if (this.mShowLatestAreaInfo) {
            this.mCellBroadcastServiceConnection = new CellBroadcastServiceConnection();
            Intent intent = new Intent("android.telephony.CellBroadcastService");
            PackageManager packageManager = this.mContext.getPackageManager();
            List<ResolveInfo> queryIntentServices =
                    packageManager.queryIntentServices(
                            new Intent("android.telephony.CellBroadcastService"), 1048576);
            if (queryIntentServices.size() != 1) {
                Log.e(
                        "SecSimStatus",
                        "getCellBroadcastServicePackageName: found "
                                + queryIntentServices.size()
                                + " CBS packages");
            }
            Iterator<ResolveInfo> it = queryIntentServices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    Log.e(
                            "SecSimStatus",
                            "getCellBroadcastServicePackageName: package name not found");
                    str = null;
                    break;
                }
                ServiceInfo serviceInfo = it.next().serviceInfo;
                if (serviceInfo != null) {
                    str = serviceInfo.packageName;
                    if (TextUtils.isEmpty(str)) {
                        Log.e(
                                "SecSimStatus",
                                "getCellBroadcastServicePackageName: found a CBS package but"
                                    + " packageName is null/empty");
                    } else {
                        if (packageManager.checkPermission(
                                        "android.permission.READ_PRIVILEGED_PHONE_STATE", str)
                                == 0) {
                            DialogFragment$$ExternalSyntheticOutline0.m(
                                    "getCellBroadcastServicePackageName: ", str, "SecSimStatus");
                            break;
                        }
                        SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                                "getCellBroadcastServicePackageName: ",
                                str,
                                " does not have READ_PRIVILEGED_PHONE_STATE permission",
                                "SecSimStatus");
                    }
                }
            }
            if (!TextUtils.isEmpty(str)) {
                intent.setPackage(str);
                CellBroadcastServiceConnection cellBroadcastServiceConnection =
                        this.mCellBroadcastServiceConnection;
                if (cellBroadcastServiceConnection == null
                        || cellBroadcastServiceConnection.mService != null) {
                    Log.d("SecSimStatus", "skipping bindService because connection already exists");
                } else if (!this.mContext.bindService(intent, cellBroadcastServiceConnection, 1)) {
                    Log.e("SecSimStatus", "Unable to bind to service");
                }
            }
        } else {
            removePreference("latest_area_info");
        }
        Log.i("SecSimStatus", "updateLatestAreaInfo() END");
        updateSubscriptionStatus$1();
        Log.i("SecSimStatus", "initialize() END");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SecSimStatus", "onCreate()");
        Log.d("SecSimStatus", "SalesCode: " + sSalesCode);
        Context context = getContext();
        this.mContext = context;
        this.mRes = context.getResources();
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        this.mSubscriptionManager =
                (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        this.mImsPreference = findPreference("ims_reg");
        this.mSimSlotCount = this.mTelephonyManager.getSimCount();
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("mSimSlotCount: "), this.mSimSlotCount, "SecSimStatus");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("SecSimStatus", "onCreateView()");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        int firstSlotIndex =
                ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                        .getFirstSlotIndex();
        if (firstSlotIndex != -1) {
            this.mSubscriptionInfo =
                    SubscriptionManager.from(this.mContext)
                            .getActiveSubscriptionInfoForSimSlotIndex(firstSlotIndex);
        }
        return applyTabViewIfNeeded(
                this.mContext, super.onCreateView(layoutInflater, viewGroup, bundle));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("SecSimStatus", "onDestroy()");
        if (this.mShowLatestAreaInfo) {
            CellBroadcastServiceConnection cellBroadcastServiceConnection =
                    this.mCellBroadcastServiceConnection;
            if (cellBroadcastServiceConnection != null
                    && cellBroadcastServiceConnection.mService != null) {
                this.mContext.unbindService(cellBroadcastServiceConnection);
            }
            this.mCellBroadcastServiceConnection = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("SecSimStatus", "onPause()");
        this.mContext.unregisterReceiver(this.mSimHotSwapReceiver);
        for (int i = 0; i < this.mSimSlotCount && i < 2; i++) {
            ImsManager imsManager = this.mImsManager[i];
            if (imsManager != null) {
                imsManager.disconnectService();
                this.mImsManager[i] = null;
                this.mIsRegistered[i] = false;
            }
        }
        if (this.mSubscriptionInfo == null) {
            if (this.mIsRegisteredListener) {
                this.mSubscriptionManager.removeOnSubscriptionsChangedListener(
                        this.mOnSubscriptionsChangedListener);
                this.mTelephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
                if (this.mShowLatestAreaInfo) {
                    this.mContext.unregisterReceiver(this.mAreaInfoReceiver);
                }
                this.mIsRegisteredListener = false;
                return;
            }
            return;
        }
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(
                this.mOnSubscriptionsChangedListener);
        this.mTelephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
        if (this.mShowLatestAreaInfo) {
            this.mContext.unregisterReceiver(this.mAreaInfoReceiver);
        }
        if (this.mFactoryPhone != null) {
            setRssiNoti(0);
            this.mFactoryPhone.disconnectFromRilService();
            this.mFactoryPhone = null;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("SecSimStatus", "onResume()");
        this.mContext.registerReceiver(
                this.mSimHotSwapReceiver,
                new IntentFilter("com.samsung.intent.action.SIMHOTSWAP"),
                2);
        for (final int i = 0; i < this.mSimSlotCount && i < 2; i++) {
            this.mIsShowImsStatus[i] = isShowImsStatus(this.mContext, i);
            if (this.mIsShowImsStatus[i]) {
                Context applicationContext = this.mContext.getApplicationContext();
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        i, "createImsService() START, slotId: ", "SecSimStatus");
                this.mImsManager[i] =
                        new ImsManager(
                                applicationContext,
                                new ImsManager
                                        .ConnectionListener() { // from class:
                                                                // com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.5
                                    @Override // com.sec.ims.ImsManager.ConnectionListener
                                    public final void onConnected() {
                                        boolean z;
                                        SecSimStatus secSimStatus = SecSimStatus.this;
                                        boolean[] zArr = secSimStatus.mIsRegistered;
                                        secSimStatus.getClass();
                                        StringBuilder sb =
                                                new StringBuilder(
                                                        "isImsRegistered() START, slotId: ");
                                        int i2 = i;
                                        sb.append(i2);
                                        Log.i("SecSimStatus", sb.toString());
                                        ImsManager imsManager = secSimStatus.mImsManager[i2];
                                        if (imsManager == null
                                                || imsManager.getRegistrationInfoByServiceType(
                                                                "volte")
                                                        == null) {
                                            Log.i(
                                                    "SecSimStatus",
                                                    "slotId: " + i2 + ", isImsRegistered: false");
                                            z = false;
                                        } else {
                                            Log.i(
                                                    "SecSimStatus",
                                                    "slotId: " + i2 + ", isImsRegistered: true");
                                            z = true;
                                        }
                                        zArr[i2] = z;
                                        secSimStatus.updateImsStatus();
                                    }

                                    @Override // com.sec.ims.ImsManager.ConnectionListener
                                    public final void onDisconnected() {
                                        SecSimStatus.this.mImsManager[i] = null;
                                    }
                                },
                                i);
                ImsManager imsManager = this.mImsManager[i];
                if (imsManager != null) {
                    imsManager.connectService();
                }
            }
        }
        updateImsStatus();
        if (this.mSubscriptionInfo == null) {
            return;
        }
        if (this.mFactoryPhone != null) {
            setRssiNoti(1);
        }
        this.mTelephonyManager.registerTelephonyCallback(
                this.mContext.getMainExecutor(), this.mTelephonyCallback);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(
                this.mContext.getMainExecutor(), this.mOnSubscriptionsChangedListener);
        if (this.mShowLatestAreaInfo) {
            updateAreaInfoText$1();
            this.mContext.registerReceiver(
                    this.mAreaInfoReceiver,
                    new IntentFilter("android.telephony.action.AREA_INFO_UPDATED"),
                    2);
        }
        this.mIsRegisteredListener = true;
    }

    @Override // com.samsung.android.settings.connection.SecMultiSIMTabInterface
    public final void onTabChanged(TabLayout.Tab tab) {
        SubscriptionInfo subscriptionInfo;
        Log.i("SecSimStatus", "TabChanged()");
        int intValue = ((Integer) tab.tag).intValue();
        if (this.mFactoryPhone != null) {
            setRssiNoti(0);
        }
        if (this.mTelephonyCallback != null
                && (subscriptionInfo = this.mSubscriptionInfo) != null) {
            this.mTelephonyManager
                    .createForSubscriptionId(subscriptionInfo.getSubscriptionId())
                    .unregisterTelephonyCallback(this.mTelephonyCallback);
        }
        this.mSubscriptionInfo =
                SubscriptionManager.from(this.mContext)
                        .getActiveSubscriptionInfoForSimSlotIndex(intValue);
        initialize$6();
        updateImsStatus();
        if (isResumed()) {
            this.mTelephonyManager.registerTelephonyCallback(
                    this.mContext.getMainExecutor(), this.mTelephonyCallback);
        }
        if (this.mShowLatestAreaInfo) {
            updateAreaInfoText$1();
        }
        if (this.mFactoryPhone != null) {
            setRssiNoti(1);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d("SecSimStatus", "onViewCreated()");
        initialize$6();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean removePreference(String str) {
        AbsAdapter$$ExternalSyntheticOutline0.m("removePreference: ", str, "SecSimStatus");
        return super.removePreference(str);
    }

    public final void setRssiNoti(int i) {
        Log.d("SecSimStatus", "setRssiNoti() : " + i);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            try {
                dataOutputStream.writeByte(24);
                dataOutputStream.writeByte(1);
                dataOutputStream.writeShort(5);
                dataOutputStream.writeByte(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FactoryPhone factoryPhone = new FactoryPhone(this.mContext);
            this.mFactoryPhone = factoryPhone;
            factoryPhone.invokeOemRilRequestRaw(byteArrayOutputStream.toByteArray(), null);
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException unused) {
                Log.secE("SecSimStatus", "setRssiNoti : dos.close() error");
            }
        }
    }

    public final void setSummaryText(String str, String str2) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = this.mRes.getString(R.string.device_info_not_available);
        }
        findPreference.setSummary(str2);
    }

    public final void updateAreaInfoText$1() {
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
            setSummaryText(
                    "latest_area_info",
                    String.valueOf(asInterface.getCellBroadcastAreaInfo(this.mCurrentSlotId)));
        } catch (RemoteException e) {
            Log.d("SecSimStatus", "Can't get area info. e=" + e);
        }
    }

    public final void updateImsStatus() {
        if (this.mImsPreference == null) {
            return;
        }
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        int simSlotIndex = subscriptionInfo == null ? 0 : subscriptionInfo.getSimSlotIndex();
        if (!this.mIsShowImsStatus[simSlotIndex]) {
            this.mImsPreference.setVisible(false);
            return;
        }
        this.mImsPreference.setVisible(true);
        boolean z = this.mIsRegistered[simSlotIndex];
        setSummaryText(
                "ims_reg",
                this.mRes.getString(
                        "VZW".equals(com.android.settings.Utils.getSalesCode())
                                ? z ? R.string.switch_on_text : R.string.switch_off_text
                                : z ? R.string.status_registered : R.string.status_not_registered));
    }

    public final void updateNetworkProvider$1() {
        Log.i("SecSimStatus", "updateNetworkProvider() START");
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        setSummaryText(
                "operator_name",
                getDisplayNetworkProvider(
                        this.mContext,
                        subscriptionInfo != null ? subscriptionInfo.getCarrierName() : null,
                        this.mTelephonyManager,
                        this.mSubscriptionInfo));
        Log.i("SecSimStatus", "updateNetworkProvider() END");
    }

    public final void updateNetworkType$1() {
        String str;
        String str2;
        String str3;
        IndianOperators indianOperators;
        Log.i("SecSimStatus", "updateNetworkType() START (Mobile Voice/Data network type)");
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        if (subscriptionInfo == null) {
            setSummaryText(
                    "voice_network_type", this.mContext.getString(R.string.device_info_default));
            setSummaryText(
                    "data_network_type", this.mContext.getString(R.string.device_info_default));
            return;
        }
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        int phoneId = SubscriptionManager.getPhoneId(subscriptionId);
        int simSlotIndex = this.mSubscriptionInfo.getSimSlotIndex();
        TooltipPopup$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "subId: ", ", phoneId: ", subscriptionId, phoneId, ", slotId: "),
                simSlotIndex,
                "SecSimStatus");
        int dataNetworkType = this.mTelephonyManager.getDataNetworkType();
        int voiceNetworkType = this.mTelephonyManager.getVoiceNetworkType();
        TelephonyDisplayInfo telephonyDisplayInfo = this.mTelephonyDisplayInfo;
        int overrideNetworkType =
                telephonyDisplayInfo == null ? 0 : telephonyDisplayInfo.getOverrideNetworkType();
        String telephonyProperty =
                TelephonyManager.getTelephonyProperty(
                        phoneId,
                        "gsm.network.type",
                        this.mContext.getString(R.string.device_info_default));
        String string =
                SemCarrierFeature.getInstance()
                        .getString(
                                simSlotIndex,
                                "CarrierFeature_Setting_CustNetworkSelMenu4",
                                ApnSettings.MVNO_NONE,
                                false);
        Log.i(
                "SecSimStatus",
                "networkType: " + telephonyProperty + ", networkNameDisplayType: " + string);
        if (Rune.isChinaCTCModel()) {
            Log.i("SecSimStatus", "isChinaCTCModel");
            str2 = getNetworkTypeName(voiceNetworkType);
            if (!"Unknown".equals(telephonyProperty)) {
                telephonyProperty =
                        ("CDMA-IS95A".equals(telephonyProperty)
                                        || "CDMA-IS95B".equals(telephonyProperty)
                                        || "1xRTT".equals(telephonyProperty))
                                ? "CDMA 1x"
                                : ("EvDo-rev.0".equals(telephonyProperty)
                                                || "EvDo-rev.A".equals(telephonyProperty)
                                                || "EvDo-rev.B".equals(telephonyProperty))
                                        ? "CDMA EVDO"
                                        : "UMTS".equals(telephonyProperty)
                                                ? "WCDMA"
                                                : ("HSDPA".equals(telephonyProperty)
                                                                || "HSUPA"
                                                                        .equals(telephonyProperty))
                                                        ? "HSPA"
                                                        : "HSPAP".equals(telephonyProperty)
                                                                ? "HSPA+"
                                                                : this.mContext.getString(
                                                                        R.string
                                                                                .device_info_default);
            } else if (!"Unknown".equals(str2)) {
                Log.i(
                        "SecSimStatus",
                        "Replace networkType to be same as voiceNetworkType (" + str2 + ")");
                telephonyProperty = str2;
            }
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "isChinaCTCModel => changed networkType: ", telephonyProperty, "SecSimStatus");
        } else {
            if (dataNetworkType != 0) {
                ServiceState currentServiceState = getCurrentServiceState();
                Log.i(
                        "SecSimStatus",
                        "getOptionalRadioTech: " + currentServiceState.getOptionalRadioTech());
                String simOperatorNameForPhone =
                        TelephonyManager.getDefault().getSimOperatorNameForPhone(simSlotIndex);
                IndianOperators indianOperators2 = IndianOperators.AIRTEL;
                if (simOperatorNameForPhone != null) {
                    String lowerCase = simOperatorNameForPhone.toLowerCase();
                    if (lowerCase.contains("airtel")) {
                        indianOperators = indianOperators2;
                    } else if (lowerCase.contains("jio")) {
                        indianOperators = IndianOperators.RELIANCE;
                    }
                    Log.i("SecSimStatus", "indianOperator: " + indianOperators);
                    str =
                            (!"HSPAP".equals(telephonyProperty)
                                            && currentServiceState.getOptionalRadioTech() == 1
                                            && indianOperators == indianOperators2)
                                    ? "DC"
                                    : getNetworkTypeName(dataNetworkType);
                }
                indianOperators = IndianOperators.OTHERS;
                Log.i("SecSimStatus", "indianOperator: " + indianOperators);
                if (!"HSPAP".equals(telephonyProperty)) {}
            } else {
                str = null;
            }
            String networkTypeName =
                    voiceNetworkType != 0 ? getNetworkTypeName(voiceNetworkType) : null;
            StringBuilder sb = new StringBuilder("actualDataNetworkType: ");
            sb.append(dataNetworkType);
            sb.append(", dataNetworkTypeName: ");
            sb.append(str);
            sb.append("\nactualVoiceNetworkType: ");
            ComposerImpl$$ExternalSyntheticOutline0.m(
                    sb,
                    voiceNetworkType,
                    ", voiceNetworkTypeName: ",
                    networkTypeName,
                    "\noverrideNetworkType: ");
            TooltipPopup$$ExternalSyntheticOutline0.m(sb, overrideNetworkType, "SecSimStatus");
            String str4 =
                    (dataNetworkType == 13
                                    && (overrideNetworkType == 5 || overrideNetworkType == 3))
                            ? "NR NSA"
                            : str;
            String str5 = sSalesCode;
            if ("VZW".equals(str5) || "VPP".equals(str5)) {
                Log.i("SecSimStatus", "For VZW");
                ServiceState currentServiceState2 = getCurrentServiceState();
                if (currentServiceState2 != null) {
                    int nrFrequencyRange = currentServiceState2.getNrFrequencyRange();
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            nrFrequencyRange, "nrFrequencyRange: ", "SecSimStatus");
                    if (nrFrequencyRange == 4) {
                        str4 = "5G UW";
                    } else if (nrFrequencyRange == 1
                            || nrFrequencyRange == 2
                            || nrFrequencyRange == 3) {
                        str4 = "5G";
                    }
                }
                if ("false".equalsIgnoreCase(SystemProperties.get("ro.ril.svlte1x"))
                        && this.mTelephonyManager.getDataState() == 3) {
                    if (!TextUtils.isEmpty(str4) && "LTE".contains(str4)) {
                        str4 = "1x RTT";
                    }
                    if (!TextUtils.isEmpty(networkTypeName) && "LTE".contains(networkTypeName)) {
                        telephonyProperty = str4;
                        str2 = "1x RTT";
                    }
                }
                String str6 = networkTypeName;
                telephonyProperty = str4;
                str2 = str6;
            } else if ("NUMERIC".equalsIgnoreCase(string)) {
                if (!TextUtils.isEmpty(str4)) {
                    Log.i("SecSimStatus", "Replace dataNetworkTypeName LTE to 4G");
                    str4 = str4.replace("LTE", "4G");
                }
                if (!TextUtils.isEmpty(networkTypeName)) {
                    Log.i("SecSimStatus", "Replace voiceNetworkTypeName LTE to 4G");
                    str3 = networkTypeName.replace("LTE", "4G");
                    telephonyProperty = str4;
                    str2 = str3;
                }
                String str62 = networkTypeName;
                telephonyProperty = str4;
                str2 = str62;
            } else {
                if (Rune.isChinaModel()) {
                    Log.i("SecSimStatus", "For isChinaModel");
                    ServiceState currentServiceState3 = getCurrentServiceState();
                    if (currentServiceState3 != null
                            && currentServiceState3.getNrState() == 3
                            && !TextUtils.isEmpty(str4)
                            && "LTE".contains(str4)) {
                        Log.i("SecSimStatus", "Replace dataNetworkTypeName to LTE, NR ");
                        str4 = "LTE, NR";
                    }
                } else if ("CCT".equals(str5)) {
                    boolean isWifiCallingAvailable =
                            this.mTelephonyManager.isWifiCallingAvailable();
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "isWifiCallingAvailable: ", "SecSimStatus", isWifiCallingAvailable);
                    if (isWifiCallingAvailable) {
                        str3 = "IWLAN";
                        telephonyProperty = str4;
                        str2 = str3;
                    }
                }
                String str622 = networkTypeName;
                telephonyProperty = str4;
                str2 = str622;
            }
        }
        if (TextUtils.isEmpty(str2) || "Unknown".equalsIgnoreCase(str2)) {
            Log.i("SecSimStatus", "voice network type is Empty or Unknown, Set to default text");
            str2 = this.mContext.getString(R.string.device_info_default);
        }
        if (TextUtils.isEmpty(telephonyProperty) || "Unknown".equalsIgnoreCase(telephonyProperty)) {
            Log.i("SecSimStatus", "data network type is Empty or Unknown, Set to default text");
            telephonyProperty = this.mContext.getString(R.string.device_info_default);
        }
        Log.i(
                "SecSimStatus",
                "voiceNetworkTypeName: " + str2 + ", dataNetworkTypeName: " + telephonyProperty);
        setSummaryText("voice_network_type", str2);
        setSummaryText("data_network_type", telephonyProperty);
        Log.i("SecSimStatus", "updateNetworkType() END (Mobile Voice/Data network type)");
    }

    public final void updateRoamingStatus$1(ServiceState serviceState) {
        Log.i("SecSimStatus", "updateRoamingStatus() START");
        if (serviceState == null) {
            setSummaryText("roaming_state", this.mRes.getString(R.string.radioInfo_unknown));
        } else {
            if (serviceState.getRoaming()) {
                setSummaryText("roaming_state", this.mRes.getString(R.string.radioInfo_roaming_in));
            } else {
                setSummaryText(
                        "roaming_state", this.mRes.getString(R.string.radioInfo_roaming_not));
            }
            Log.i("SecSimStatus", "updateRoamingStatus() - Roaming : " + serviceState.getRoaming());
        }
        Log.i("SecSimStatus", "updateRoamingStatus() END");
    }

    public final void updateServiceState$1(ServiceState serviceState) {
        Log.i("SecSimStatus", "updateServiceState() START");
        int combinedServiceState = Utils.getCombinedServiceState(serviceState);
        if (!Utils.isInService(serviceState)) {
            setSummaryText(
                    "signal_strength", this.mRes.getString(R.string.sim_signal_strength, -113, 0));
        } else if (!Utils.isInService(this.mPreviousServiceState)) {
            updateSignalStrength(this.mTelephonyManager.getSignalStrength());
        }
        setSummaryText(
                "service_state",
                combinedServiceState != 0
                        ? (combinedServiceState == 1 || combinedServiceState == 2)
                                ? this.mRes.getString(R.string.radioInfo_service_out)
                                : combinedServiceState != 3
                                        ? this.mRes.getString(R.string.radioInfo_unknown)
                                        : this.mRes.getString(R.string.radioInfo_service_off)
                        : this.mRes.getString(R.string.radioInfo_service_in));
        Log.i("SecSimStatus", "updateServiceState() END");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSignalStrength(android.telephony.SignalStrength r17) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.statusinfo.simstatus.SecSimStatus.updateSignalStrength(android.telephony.SignalStrength):void");
    }

    public final void updateSubscriptionStatus$1() {
        Log.i("SecSimStatus", "updateSubscriptionStatus() START");
        this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(this.mCurrentSubId);
        Log.i("SecSimStatus", "TM updated with SubId(" + this.mCurrentSubId + ")");
        updateNetworkProvider$1();
        ServiceState serviceState = this.mTelephonyManager.getServiceState();
        SignalStrength signalStrength = this.mTelephonyManager.getSignalStrength();
        updateServiceState$1(serviceState);
        updateSignalStrength(signalStrength);
        updateNetworkType$1();
        updateRoamingStatus$1(serviceState);
        Log.i("SecSimStatus", "updateIccidNumber() START");
        if (supportIccId(this.mCurrentSlotId, this.mContext, this.mSubscriptionInfo)) {
            String simSerialNumber = this.mTelephonyManager.getSimSerialNumber();
            String str = sSalesCode;
            if ("VZW".equals(str) || "VPP".equals(str)) {
                simSerialNumber = SecDeviceInfoUtils.addWhitespaceEvery4digits(simSerialNumber);
            }
            setSummaryText("icc_id", simSerialNumber);
        } else {
            removePreference("icc_id");
        }
        Log.i("SecSimStatus", "updateIccidNumber() END");
        Log.i("SecSimStatus", "updateSubscriptionStatus() END");
    }

    public final void updateSubscriptionStatusNotAvailable() {
        Log.i("SecSimStatus", "updateSubscriptionStatusNotAvailable()");
        setSummaryText("operator_name", null);
        setSummaryText("data_state", null);
        setSummaryText("latest_area_info", null);
        setSummaryText("service_state", null);
        if (mSupportSignalStrength) {
            setSummaryText("signal_strength", null);
        } else {
            removePreference("signal_strength");
        }
        setSummaryText("voice_network_type", null);
        setSummaryText("data_network_type", null);
        setSummaryText("roaming_state", null);
        if (supportIccId(0, this.mContext, null)) {
            setSummaryText("icc_id", null);
        } else {
            removePreference("icc_id");
        }
    }
}
