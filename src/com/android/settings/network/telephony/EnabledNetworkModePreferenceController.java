package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.network.AllowedNetworkTypesListener;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.SubscriptionsChangeListener;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnabledNetworkModePreferenceController extends TelephonyBasePreferenceController
        implements Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    private static final String LOG_TAG = "EnabledNetworkMode";
    private AllowedNetworkTypesListener mAllowedNetworkTypesListener;
    private PreferenceEntriesBuilder mBuilder;
    private int mCallState;
    private CarrierConfigCache mCarrierConfigCache;
    private FragmentManager mFragmentManager;
    private Preference mPreference;
    private PreferenceScreen mPreferenceScreen;
    private SubscriptionsChangeListener mSubscriptionsListener;
    private PhoneCallStateTelephonyCallback mTelephonyCallback;
    private TelephonyManager mTelephonyManager;
    private LifecycleOwner mViewLifecycleOwner;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PhoneCallStateTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public TelephonyManager mTelephonyManager;

        public PhoneCallStateTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onCallStateChanged:", EnabledNetworkModePreferenceController.LOG_TAG);
            EnabledNetworkModePreferenceController.this.mCallState = i;
            EnabledNetworkModePreferenceController.this.mBuilder.updateConfig();
            EnabledNetworkModePreferenceController.this.updatePreference();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PreferenceEntriesBuilder {
        public boolean mAllowed5gNetworkType;
        public final CarrierConfigCache mCarrierConfigCache;
        public final Context mContext;
        public boolean mDisplay2gOptions;
        public boolean mDisplay3gOptions;
        public final List mEntries = new ArrayList();
        public final List mEntriesValue = new ArrayList();
        public boolean mIs5gEntryDisplayed;
        public boolean mIsGlobalCdma;
        public boolean mLteEnabled;
        public int mSelectedEntry;
        public boolean mShow4gForLTE;
        public final int mSubId;
        public String mSummary;
        public boolean mSupported5gRadioAccessFamily;
        public TelephonyManager mTelephonyManager;

        public PreferenceEntriesBuilder(Context context, int i) {
            this.mContext = context;
            this.mSubId = i;
            this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
            this.mTelephonyManager =
                    ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                            .createForSubscriptionId(i);
            updateConfig();
        }

        public static int addNrToLteNetworkType(int i) {
            switch (i) {
                case 8:
                    return 25;
                case 9:
                    return 26;
                case 10:
                    return 27;
                case 11:
                    return 24;
                case 12:
                    return 28;
                case 13:
                case 14:
                case 16:
                case 18:
                case 21:
                default:
                    return i;
                case 15:
                    return 29;
                case 17:
                    return 30;
                case 19:
                    return 31;
                case 20:
                    return 32;
                case 22:
                    return 33;
            }
        }

        public final void add4gEntry(int i) {
            boolean showNrList = showNrList();
            EnabledNetworkModePreferenceController enabledNetworkModePreferenceController =
                    EnabledNetworkModePreferenceController.this;
            if (showNrList) {
                ((ArrayList) this.mEntries)
                        .add(
                                enabledNetworkModePreferenceController
                                        .getResourcesForSubId()
                                        .getString(R.string.network_4G_pure));
            } else {
                ((ArrayList) this.mEntries)
                        .add(
                                enabledNetworkModePreferenceController
                                        .getResourcesForSubId()
                                        .getString(R.string.network_4G));
            }
            ((ArrayList) this.mEntriesValue).add(Integer.valueOf(i));
        }

        public final void add5gEntry(int i) {
            boolean z = i >= 23;
            if (showNrList() && z) {
                ((ArrayList) this.mEntries)
                        .add(
                                EnabledNetworkModePreferenceController.this
                                        .getResourcesForSubId()
                                        .getString(R.string.network_5G_recommended));
                ((ArrayList) this.mEntriesValue).add(Integer.valueOf(i));
                this.mIs5gEntryDisplayed = true;
                return;
            }
            this.mIs5gEntryDisplayed = false;
            Log.d(
                    EnabledNetworkModePreferenceController.LOG_TAG,
                    "Hide 5G option.  supported5GRadioAccessFamily: "
                            + this.mSupported5gRadioAccessFamily
                            + " allowed5GNetworkType: "
                            + this.mAllowed5gNetworkType
                            + " isNRValue: "
                            + z);
        }

        public final void addLteEntry(int i) {
            boolean showNrList = showNrList();
            EnabledNetworkModePreferenceController enabledNetworkModePreferenceController =
                    EnabledNetworkModePreferenceController.this;
            if (showNrList) {
                ((ArrayList) this.mEntries)
                        .add(
                                enabledNetworkModePreferenceController
                                        .getResourcesForSubId()
                                        .getString(R.string.network_lte_pure));
            } else {
                ((ArrayList) this.mEntries)
                        .add(
                                enabledNetworkModePreferenceController
                                        .getResourcesForSubId()
                                        .getString(R.string.network_lte));
            }
            ((ArrayList) this.mEntriesValue).add(Integer.valueOf(i));
        }

        public final int getPreferredNetworkMode() {
            int networkTypeFromRaf =
                    MobileNetworkUtils.getNetworkTypeFromRaf(
                            (int) this.mTelephonyManager.getAllowedNetworkTypesForReason(0));
            if (!showNrList()) {
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        networkTypeFromRaf,
                        "Network mode :",
                        " reduce NR",
                        EnabledNetworkModePreferenceController.LOG_TAG);
                switch (networkTypeFromRaf) {
                    case 24:
                        networkTypeFromRaf = 11;
                        break;
                    case 25:
                        networkTypeFromRaf = 8;
                        break;
                    case 26:
                        networkTypeFromRaf = 9;
                        break;
                    case 27:
                        networkTypeFromRaf = 10;
                        break;
                    case 28:
                        networkTypeFromRaf = 12;
                        break;
                    case 29:
                        networkTypeFromRaf = 15;
                        break;
                    case 30:
                        networkTypeFromRaf = 17;
                        break;
                    case 31:
                        networkTypeFromRaf = 19;
                        break;
                    case 32:
                        networkTypeFromRaf = 20;
                        break;
                    case 33:
                        networkTypeFromRaf = 22;
                        break;
                }
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    networkTypeFromRaf,
                    "getPreferredNetworkMode: ",
                    EnabledNetworkModePreferenceController.LOG_TAG);
            return networkTypeFromRaf;
        }

        public final void setPreferenceValueAndSummary(int i) {
            setSelectedEntry(i);
            int i2 = R.string.network_4G;
            int i3 = R.string.network_4G_pure;
            int i4 = R.string.network_lte;
            int i5 = this.mSubId;
            EnabledNetworkModePreferenceController enabledNetworkModePreferenceController =
                    EnabledNetworkModePreferenceController.this;
            switch (i) {
                case 0:
                case 2:
                case 3:
                    if (this.mIsGlobalCdma) {
                        setSelectedEntry(10);
                        setSummary(R.string.network_global);
                        return;
                    } else {
                        setSelectedEntry(0);
                        setSummary(R.string.network_3G);
                        return;
                    }
                case 1:
                    if (this.mIsGlobalCdma) {
                        setSelectedEntry(10);
                        setSummary(R.string.network_global);
                        return;
                    } else {
                        setSelectedEntry(1);
                        setSummary(R.string.network_2G);
                        return;
                    }
                case 4:
                case 6:
                case 7:
                    setSelectedEntry(4);
                    setSummary(R.string.network_3G);
                    return;
                case 5:
                    setSelectedEntry(5);
                    setSummary(R.string.network_1x);
                    return;
                case 8:
                    if (MobileNetworkUtils.isWorldMode(this.mContext, i5)) {
                        setSummary(R.string.preferred_network_mode_lte_cdma_summary);
                        return;
                    }
                    setSelectedEntry(8);
                    if (this.mIs5gEntryDisplayed) {
                        i4 = R.string.network_lte_pure;
                    }
                    setSummary(i4);
                    return;
                case 9:
                    if (MobileNetworkUtils.isWorldMode(this.mContext, i5)) {
                        setSummary(R.string.preferred_network_mode_lte_gsm_umts_summary);
                        return;
                    }
                    break;
                case 10:
                case 15:
                case 17:
                case 19:
                case 20:
                case 22:
                    if (MobileNetworkUtils.isTdscdmaSupported(this.mContext, i5)) {
                        setSelectedEntry(22);
                        if (this.mIs5gEntryDisplayed) {
                            i4 = R.string.network_lte_pure;
                        }
                        setSummary(i4);
                        return;
                    }
                    setSelectedEntry(10);
                    if (this.mTelephonyManager.getPhoneType() == 2
                            || this.mIsGlobalCdma
                            || MobileNetworkUtils.isWorldMode(this.mContext, i5)) {
                        setSummary(R.string.network_global);
                        return;
                    }
                    if (this.mIs5gEntryDisplayed) {
                        if (!this.mShow4gForLTE) {
                            i3 = R.string.network_lte_pure;
                        }
                        setSummary(i3);
                        return;
                    } else {
                        if (!this.mShow4gForLTE) {
                            i2 = R.string.network_lte;
                        }
                        setSummary(i2);
                        return;
                    }
                case 11:
                case 12:
                    break;
                case 13:
                    setSelectedEntry(13);
                    setSummary(R.string.network_3G);
                    return;
                case 14:
                case 16:
                case 18:
                    setSelectedEntry(18);
                    setSummary(R.string.network_3G);
                    return;
                case 21:
                    setSelectedEntry(21);
                    setSummary(R.string.network_3G);
                    return;
                case 23:
                case 24:
                case 26:
                case 28:
                    setSelectedEntry(26);
                    this.mSummary =
                            enabledNetworkModePreferenceController
                                    .getResourcesForSubId()
                                    .getString(R.string.network_5G_recommended);
                    return;
                case 25:
                    setSelectedEntry(25);
                    this.mSummary =
                            enabledNetworkModePreferenceController
                                    .getResourcesForSubId()
                                    .getString(R.string.network_5G_recommended);
                    return;
                case 27:
                    setSelectedEntry(27);
                    if (this.mTelephonyManager.getPhoneType() == 2
                            || this.mIsGlobalCdma
                            || MobileNetworkUtils.isWorldMode(this.mContext, i5)) {
                        setSummary(R.string.network_global);
                        return;
                    } else {
                        this.mSummary =
                                enabledNetworkModePreferenceController
                                        .getResourcesForSubId()
                                        .getString(R.string.network_5G_recommended);
                        return;
                    }
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                    setSelectedEntry(33);
                    this.mSummary =
                            enabledNetworkModePreferenceController
                                    .getResourcesForSubId()
                                    .getString(R.string.network_5G_recommended);
                    return;
                default:
                    this.mSummary =
                            enabledNetworkModePreferenceController
                                    .getResourcesForSubId()
                                    .getString(
                                            R.string.mobile_network_mode_error, Integer.valueOf(i));
                    return;
            }
            if (this.mIsGlobalCdma) {
                setSelectedEntry(10);
                setSummary(R.string.network_global);
                return;
            }
            setSelectedEntry(9);
            if (this.mIs5gEntryDisplayed) {
                if (!this.mShow4gForLTE) {
                    i3 = R.string.network_lte_pure;
                }
                setSummary(i3);
            } else {
                if (!this.mShow4gForLTE) {
                    i2 = R.string.network_lte;
                }
                setSummary(i2);
            }
        }

        public final void setSelectedEntry(final int i) {
            if (this.mEntriesValue.stream()
                    .anyMatch(
                            new Predicate() { // from class:
                                              // com.android.settings.network.telephony.EnabledNetworkModePreferenceController$PreferenceEntriesBuilder$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return ((Integer) obj).intValue() == i;
                                }
                            })) {
                this.mSelectedEntry = i;
            } else if (((ArrayList) this.mEntriesValue).size() > 0) {
                this.mSelectedEntry =
                        ((Integer) ((ArrayList) this.mEntriesValue).get(0)).intValue();
            } else {
                Log.e(EnabledNetworkModePreferenceController.LOG_TAG, "entriesValue is empty");
            }
        }

        public final void setSummary(int i) {
            this.mSummary =
                    EnabledNetworkModePreferenceController.this.getResourcesForSubId().getString(i);
        }

        public final boolean showNrList() {
            return this.mSupported5gRadioAccessFamily && this.mAllowed5gNetworkType;
        }

        public final void updateConfig() {
            TelephonyManager telephonyManager = this.mTelephonyManager;
            int i = this.mSubId;
            this.mTelephonyManager = telephonyManager.createForSubscriptionId(i);
            this.mCarrierConfigCache.getClass();
            PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
            boolean z = false;
            this.mAllowed5gNetworkType =
                    (this.mTelephonyManager.getAllowedNetworkTypesForReason(2) & 524288) > 0;
            this.mSupported5gRadioAccessFamily =
                    (524288 & this.mTelephonyManager.getSupportedRadioAccessFamily()) > 0;
            if (configForSubId != null) {
                if (this.mTelephonyManager.isLteCdmaEvdoGsmWcdmaEnabled()
                        && configForSubId.getBoolean("show_cdma_choices_bool")) {
                    z = true;
                }
                this.mIsGlobalCdma = z;
                this.mShow4gForLTE = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                this.mDisplay2gOptions = configForSubId.getBoolean("prefer_2g_bool");
                this.mDisplay3gOptions = configForSubId.getBoolean("prefer_3g_visibility_bool");
                this.mLteEnabled = configForSubId.getBoolean("lte_enabled_bool");
            }
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i,
                            "PreferenceEntriesBuilder: subId",
                            " ,Supported5gRadioAccessFamily :");
            m.append(this.mSupported5gRadioAccessFamily);
            m.append(" ,mAllowed5gNetworkType :");
            m.append(this.mAllowed5gNetworkType);
            m.append(" ,IsGlobalCdma :");
            m.append(this.mIsGlobalCdma);
            m.append(" ,Display2gOptions:");
            m.append(this.mDisplay2gOptions);
            m.append(" ,Display3gOptions:");
            m.append(this.mDisplay3gOptions);
            m.append(" ,Display4gOptions");
            m.append(this.mLteEnabled);
            m.append(" ,Show4gForLTE :");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    m, this.mShow4gForLTE, EnabledNetworkModePreferenceController.LOG_TAG);
        }
    }

    public EnabledNetworkModePreferenceController(Context context, String str) {
        super(context, str);
        this.mCallState = 0;
        this.mSubscriptionsListener = new SubscriptionsChangeListener(context, this);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
        if (this.mTelephonyCallback == null) {
            this.mTelephonyCallback = new PhoneCallStateTelephonyCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.mBuilder.updateConfig();
        updatePreference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreference() {
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen != null) {
            displayPreference(preferenceScreen);
        }
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (i == -1
                || configForSubId == null
                || !CarrierConfigManager.isConfigForIdentifiedCarrier(configForSubId)
                || configForSubId.getBoolean("hide_carrier_network_settings_bool")
                || configForSubId.getBoolean("hide_preferred_network_type_bool")
                || configForSubId.getBoolean("world_phone_bool")) {
            return 2;
        }
        return !isCallStateIdle() ? 1 : 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public PhoneCallStateTelephonyCallback getTelephonyCallback() {
        return this.mTelephonyCallback;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i, FragmentManager fragmentManager) {
        this.mSubId = i;
        this.mFragmentManager = fragmentManager;
        this.mTelephonyManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubId);
        this.mBuilder = new PreferenceEntriesBuilder(this.mContext, this.mSubId);
        if (this.mAllowedNetworkTypesListener == null) {
            AllowedNetworkTypesListener allowedNetworkTypesListener =
                    new AllowedNetworkTypesListener(this.mContext.getMainExecutor());
            this.mAllowedNetworkTypesListener = allowedNetworkTypesListener;
            allowedNetworkTypesListener.setAllowedNetworkTypesListener(
                    new AllowedNetworkTypesListener
                            .OnAllowedNetworkTypesListener() { // from class:
                                                               // com.android.settings.network.telephony.EnabledNetworkModePreferenceController$$ExternalSyntheticLambda0
                        @Override // com.android.settings.network.AllowedNetworkTypesListener.OnAllowedNetworkTypesListener
                        public final void onAllowedNetworkTypesChanged() {
                            EnabledNetworkModePreferenceController.this.lambda$init$0();
                        }
                    });
        }
    }

    public boolean isCallStateIdle() {
        return this.mCallState == 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt((String) obj);
        ListPreference listPreference = (ListPreference) preference;
        this.mBuilder.setPreferenceValueAndSummary(parseInt);
        listPreference.setValue(Integer.toString(this.mBuilder.mSelectedEntry));
        listPreference.setSummary(this.mBuilder.mSummary);
        TelephonyManager telephonyManager = this.mTelephonyManager;
        LifecycleOwner viewLifecycleOwner = this.mViewLifecycleOwner;
        Intrinsics.checkNotNullParameter(telephonyManager, "<this>");
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                Dispatchers.Default,
                null,
                new EnabledNetworkModePreferenceControllerHelperKt$setAllowedNetworkTypes$1(
                        telephonyManager, parseInt, null),
                2);
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mSubscriptionsListener.start();
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        if (allowedNetworkTypesListener == null || this.mTelephonyCallback == null) {
            return;
        }
        Context context = this.mContext;
        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(this.mSubId)
                .registerTelephonyCallback(
                        allowedNetworkTypesListener.mExecutor, allowedNetworkTypesListener);
        PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback = this.mTelephonyCallback;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        int i = this.mSubId;
        phoneCallStateTelephonyCallback.mTelephonyManager = telephonyManager;
        try {
            EnabledNetworkModePreferenceController.this.mCallState =
                    telephonyManager.getCallState(i);
        } catch (UnsupportedOperationException unused) {
            EnabledNetworkModePreferenceController.this.mCallState = 0;
        }
        phoneCallStateTelephonyCallback.mTelephonyManager.registerTelephonyCallback(
                EnabledNetworkModePreferenceController.this.mContext.getMainExecutor(),
                EnabledNetworkModePreferenceController.this.mTelephonyCallback);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mSubscriptionsListener.stop();
        AllowedNetworkTypesListener allowedNetworkTypesListener = this.mAllowedNetworkTypesListener;
        if (allowedNetworkTypesListener == null || this.mTelephonyCallback == null) {
            return;
        }
        Context context = this.mContext;
        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(this.mSubId)
                .unregisterTelephonyCallback(allowedNetworkTypesListener);
        PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback = this.mTelephonyCallback;
        EnabledNetworkModePreferenceController.this.mCallState = 0;
        TelephonyManager telephonyManager = phoneCallStateTelephonyCallback.mTelephonyManager;
        if (telephonyManager != null) {
            telephonyManager.unregisterTelephonyCallback(phoneCallStateTelephonyCallback);
        }
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public void onSubscriptionsChanged() {
        PreferenceEntriesBuilder preferenceEntriesBuilder = this.mBuilder;
        if (preferenceEntriesBuilder != null) {
            preferenceEntriesBuilder.updateConfig();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner lifecycleOwner) {
        this.mViewLifecycleOwner = lifecycleOwner;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a9  */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(androidx.preference.Preference r13) {
        /*
            Method dump skipped, instructions count: 728
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.EnabledNetworkModePreferenceController.updateState(androidx.preference.Preference):void");
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public void onAirplaneModeChanged(boolean z) {}
}
