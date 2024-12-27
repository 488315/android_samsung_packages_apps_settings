package com.samsung.android.settings.connection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSatelliteNetworksPreferenceController extends BasePreferenceController {
    private static final String CARRIER_FEATURE_SATELLITE_TAG =
            "CarrierFeature_Common_Support_Satellite";
    private static final String KEY_SATELLITE_ATTACH_SUPPORTED_BOOL =
            "satellite_attach_supported_bool";
    private static final String KEY_SATELLITE_ESOS_SUPPORTED_BOOL = "satellite_esos_supported_bool";
    private static final String TAG = "SecSatelliteNetworksPreferenceController";
    private final String EXTRA_MULTI_SIM;
    private final String EXTRA_NET_SETTINGS_ACTION;
    private final String EXTRA_ROOT_KEY;
    private final String EXTRA_SATELLITE_CATEGOTY;
    private final String EXTRA_SUBID;
    private final String KEY_SATELLITE_NETWORKS;
    private final String PACKAGE_PATH;
    private boolean mIsAU;
    private boolean mMultiSimState;
    private SatelliteManager mSatelliteManager;
    private SecPreference mSecPreference;
    private SecSimFeatureProvider mSecSimFeatureProvider;
    private String mSimSlot0MccMnc;
    private boolean mSimSlot0SupportSatellite;
    private String mSimSlot1MccMnc;
    private boolean mSimSlot1SupportSatellite;
    private int mSubId;
    private SubscriptionManager mSubscriptionManager;
    private List<SubscriptionInfo> mSubscriptions;

    public SecSatelliteNetworksPreferenceController(Context context, String str) {
        super(context, str);
        this.KEY_SATELLITE_NETWORKS = "satellite_networks";
        this.EXTRA_NET_SETTINGS_ACTION =
                "com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS";
        this.EXTRA_SATELLITE_CATEGOTY = "SATELLITE_MESSAGING_CATEGORY";
        this.EXTRA_ROOT_KEY = "root_key";
        this.EXTRA_SUBID = "sub_id";
        this.EXTRA_MULTI_SIM = "multi_sim";
        this.PACKAGE_PATH = "com.samsung.android.app.telephonyui";
        this.mSubId = -1;
        this.mSimSlot0MccMnc = ApnSettings.MVNO_NONE;
        this.mSimSlot1MccMnc = ApnSettings.MVNO_NONE;
        this.mSimSlot0SupportSatellite = false;
        this.mSimSlot1SupportSatellite = false;
        this.mMultiSimState = false;
        this.mIsAU = false;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProvider secSimFeatureProvider = featureFactoryImpl.getSecSimFeatureProvider();
        this.mSecSimFeatureProvider = secSimFeatureProvider;
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) secSimFeatureProvider;
        secSimFeatureProviderImpl.getClass();
        secSimFeatureProviderImpl.mContext = context.getApplicationContext();
        this.mSubscriptionManager =
                (SubscriptionManager)
                        this.mContext.getSystemService("telephony_subscription_service");
        this.mSatelliteManager =
                (SatelliteManager) context.getSystemService(SatelliteManager.class);
        makeSubscriptions();
    }

    private boolean getSatelliteMessagingStatus(int i, int i2) {
        if (!isOptusSupported(i2)) {
            int integerSubscriptionProperty =
                    this.mSubscriptionManager != null
                            ? SubscriptionManager.getIntegerSubscriptionProperty(
                                    i, "satellite_entitlement_status", 0, this.mContext)
                            : 0;
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "slot : ", ", subscribed : ", i2, integerSubscriptionProperty, TAG);
            return integerSubscriptionProperty == 1;
        }
        Log.i(TAG, "slot : " + i2 + ", AU always return true");
        return true;
    }

    private boolean isOptusSupported(int i) {
        return i == 0 ? this.mSimSlot0MccMnc.equals("50502") : this.mSimSlot1MccMnc.equals("50502");
    }

    private boolean isTmobileSupported(int i) {
        return !isSupportedESOS(i);
    }

    private boolean isVerizonSatelliteMode(int i) {
        boolean z;
        SatelliteManager satelliteManager = this.mSatelliteManager;
        if (satelliteManager != null) {
            z = satelliteManager.getNtnSmsSupported();
            AbsAdapter$$ExternalSyntheticOutline0.m("verizonP2PEnabled : ", TAG, z);
        } else {
            z = false;
        }
        return isSupportedESOS(i) && z;
    }

    private void makeSubscriptions() {
        List<SubscriptionInfo> completeActiveSubscriptionInfoList =
                this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        this.mSubscriptions = completeActiveSubscriptionInfoList;
        if (completeActiveSubscriptionInfoList != null) {
            completeActiveSubscriptionInfoList.sort(
                    new Comparator() { // from class:
                                       // com.samsung.android.settings.connection.SecSatelliteNetworksPreferenceController.1
                        public final Collator mCollator = Collator.getInstance();

                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return this.mCollator.compare(
                                    Integer.toString(((SubscriptionInfo) obj).getSimSlotIndex()),
                                    Integer.toString(((SubscriptionInfo) obj2).getSimSlotIndex()));
                        }
                    });
        }
    }

    public boolean checkSatelliteMessaging(int i, int i2) {
        String str = TAG;
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "sim slot : ", str);
        if (!isSupportSatellite(i, i2)) {
            Log.i(str, "checkSatelliteMessagingSim : remove menu by CarrierFeature");
            return false;
        }
        boolean isTmobileSupported = isTmobileSupported(i2);
        boolean isVerizonSatelliteMode = isVerizonSatelliteMode(i2);
        boolean isOptusSupported = isOptusSupported(i);
        if (isOptusSupported) {
            this.mSubId = i2;
            this.mIsAU = true;
        }
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                Utils$$ExternalSyntheticOutline0.m(
                        "isT-mobile : ",
                        isTmobileSupported,
                        ", isVerizon : ",
                        isVerizonSatelliteMode,
                        ", isOptus : "),
                isOptusSupported,
                str);
        return isTmobileSupported || isVerizonSatelliteMode || isOptusSupported;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSecPreference = secPreference;
        if (secPreference != null) {
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, true);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        makeSubscriptions();
        List<SubscriptionInfo> list = this.mSubscriptions;
        if (list == null || list.isEmpty()) {
            return 2;
        }
        if (this.mSubscriptions.size() == 1) {
            this.mSubId = this.mSubscriptions.get(0).getSubscriptionId();
            int simSlotIndex = this.mSubscriptions.get(0).getSimSlotIndex();
            String str = TAG;
            MainClearConfirm$$ExternalSyntheticOutline0.m(simSlotIndex, "slotIndex : ", str);
            if (simSlotIndex == 0) {
                this.mSimSlot0MccMnc =
                        ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider).getSimOperator(0);
                if (checkSatelliteMessaging(
                        simSlotIndex, this.mSubscriptions.get(0).getSubscriptionId())) {
                    this.mSimSlot0SupportSatellite = true;
                }
            } else {
                this.mSimSlot1MccMnc =
                        ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider).getSimOperator(1);
                if (checkSatelliteMessaging(
                        simSlotIndex, this.mSubscriptions.get(0).getSubscriptionId())) {
                    this.mSimSlot1SupportSatellite = true;
                }
            }
            StringBuilder sb = new StringBuilder("mSimSlot0SupportSatellite : ");
            sb.append(this.mSimSlot0SupportSatellite);
            sb.append(", mSimSlot1SupportSatellite : ");
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    sb, this.mSimSlot1SupportSatellite, str);
        } else if (this.mSubscriptions.size() > 1) {
            this.mSubId = this.mSubscriptions.get(0).getSubscriptionId();
            this.mSimSlot0MccMnc =
                    ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider).getSimOperator(0);
            this.mSimSlot1MccMnc =
                    ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider).getSimOperator(1);
            if (checkSatelliteMessaging(
                    this.mSubscriptions.get(0).getSimSlotIndex(),
                    this.mSubscriptions.get(0).getSubscriptionId())) {
                this.mSimSlot0SupportSatellite = true;
            }
            if (checkSatelliteMessaging(
                    this.mSubscriptions.get(1).getSimSlotIndex(),
                    this.mSubscriptions.get(1).getSubscriptionId())) {
                this.mSubId = this.mSubscriptions.get(1).getSubscriptionId();
                this.mSimSlot1SupportSatellite = true;
            }
            if (this.mSimSlot0SupportSatellite && this.mSimSlot1SupportSatellite) {
                this.mSubId = this.mSubscriptions.get(0).getSubscriptionId();
                this.mMultiSimState = true;
            }
        }
        return (this.mSimSlot0SupportSatellite || this.mSimSlot1SupportSatellite) ? 0 : 2;
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

    public boolean getMultiSimState() {
        return this.mMultiSimState;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public int getSubId() {
        return this.mSubId;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String sb;
        String sb2;
        if (this.mIsAU) {
            return this.mContext.getResources().getString(R.string.sec_satellite_networks_include);
        }
        if (!this.mMultiSimState) {
            if (this.mSubscriptions.size() == 1) {
                String str = TAG;
                Log.i(str, "mSubscriptions.size() == 1");
                if (getSatelliteMessagingStatus(
                        this.mSubscriptions.get(0).getSubscriptionId(),
                        this.mSubscriptions.get(0).getSimSlotIndex())) {
                    Log.i(str, "sec_satellite_networks_include");
                    return this.mContext
                            .getResources()
                            .getString(R.string.sec_satellite_networks_include);
                }
                Log.i(str, "sec_satellite_networks_not_include");
                return this.mContext
                        .getResources()
                        .getString(R.string.sec_satellite_networks_not_include);
            }
            String str2 = TAG;
            Log.i(str2, "mSubscriptions.size() > 1");
            if ((this.mSimSlot0SupportSatellite
                            && getSatelliteMessagingStatus(
                                    this.mSubscriptions.get(0).getSubscriptionId(),
                                    this.mSubscriptions.get(0).getSimSlotIndex()))
                    || (this.mSimSlot1SupportSatellite
                            && getSatelliteMessagingStatus(
                                    this.mSubscriptions.get(1).getSubscriptionId(),
                                    this.mSubscriptions.get(1).getSimSlotIndex()))) {
                Log.i(str2, "sec_satellite_networks_include");
                return this.mContext
                        .getResources()
                        .getString(R.string.sec_satellite_networks_include);
            }
            Log.i(str2, "sec_satellite_networks_not_include");
            return this.mContext
                    .getResources()
                    .getString(R.string.sec_satellite_networks_not_include);
        }
        String simSlotName =
                ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                        .getSimSlotName(this.mSubscriptions.get(0).getSimSlotIndex());
        String simSlotName2 =
                ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                        .getSimSlotName(this.mSubscriptions.get(1).getSimSlotIndex());
        if (getSatelliteMessagingStatus(
                this.mSubscriptions.get(0).getSubscriptionId(),
                this.mSubscriptions.get(0).getSimSlotIndex())) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            simSlotName, ": ");
            m.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_satellite_networks_include));
            sb = m.toString();
        } else {
            StringBuilder m2 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            simSlotName, ": ");
            m2.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_satellite_networks_not_include));
            sb = m2.toString();
        }
        if (getSatelliteMessagingStatus(
                this.mSubscriptions.get(1).getSubscriptionId(),
                this.mSubscriptions.get(1).getSimSlotIndex())) {
            StringBuilder m3 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            simSlotName2, ": ");
            m3.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_satellite_networks_include));
            sb2 = m3.toString();
        } else {
            StringBuilder m4 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            simSlotName2, ": ");
            m4.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_satellite_networks_not_include));
            sb2 = m4.toString();
        }
        Log.i(TAG, "slot0 Name : " + sb + ", slot1 Name : " + sb2);
        return BackStackRecord$$ExternalSyntheticOutline0.m(new StringBuilder(), sb, "\n", sb2);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("satellite_networks".equals(preference.getKey())) {
            this.mContext.startActivity(
                    new Intent("com.samsung.android.app.telephonyui.action.OPEN_NET_SETTINGS")
                            .putExtra("root_key", "SATELLITE_MESSAGING_CATEGORY")
                            .putExtra("sub_id", this.mSubId)
                            .putExtra("multi_sim", this.mMultiSimState)
                            .setPackage("com.samsung.android.app.telephonyui"));
        }
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

    public boolean isSupportSatellite(int i, int i2) {
        boolean z =
                SemCarrierFeature.getInstance()
                        .getBoolean(i, CARRIER_FEATURE_SATELLITE_TAG, false, false);
        boolean isSupportedSatelliteDevice = isSupportedSatelliteDevice(i2);
        Log.i(TAG, "carrierFeature : " + z + ", boolFeature : " + isSupportedSatelliteDevice);
        return z && isSupportedSatelliteDevice;
    }

    public boolean isSupportedESOS(int i) {
        if (!Rune.isVzwModel()) {
            return false;
        }
        PersistableBundle configForSubId =
                ((CarrierConfigManager) this.mContext.getSystemService("carrier_config"))
                        .getConfigForSubId(i);
        if (configForSubId != null) {
            Log.i(
                    TAG,
                    "isSupportedESOS : "
                            + configForSubId.getBoolean(KEY_SATELLITE_ESOS_SUPPORTED_BOOL));
        }
        return configForSubId != null
                && configForSubId.getBoolean(KEY_SATELLITE_ESOS_SUPPORTED_BOOL);
    }

    public boolean isSupportedSatelliteDevice(int i) {
        PersistableBundle configForSubId =
                ((CarrierConfigManager) this.mContext.getSystemService("carrier_config"))
                        .getConfigForSubId(i);
        return configForSubId != null
                && configForSubId.getBoolean(KEY_SATELLITE_ATTACH_SUPPORTED_BOOL);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
