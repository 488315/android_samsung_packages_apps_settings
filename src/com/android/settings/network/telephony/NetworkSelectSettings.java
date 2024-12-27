package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoTdscdma;
import android.telephony.CellInfoWcdma;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Keep;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.annotations.Initializer;
import com.android.internal.telephony.OperatorInfo;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.network.telephony.scan.NetworkScanRepository;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableList;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bixby.AppContext;

import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Keep
/* loaded from: classes2.dex */
public class NetworkSelectSettings extends DashboardFragment {
    private static final int EVENT_SET_NETWORK_SELECTION_MANUALLY_DONE = 1;
    private static final String PREF_KEY_NETWORK_OPERATORS = "network_operators_preference";
    private static final String TAG = "NetworkSelectSettings";
    private CarrierConfigManager.CarrierConfigChangeListener mCarrierConfigChangeListener;
    private CarrierConfigManager mCarrierConfigManager;
    private List<String> mForbiddenPlmns;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private NetworkScanRepository mNetworkScanRepository;
    private NetworkSelectRepository mNetworkSelectRepository;
    private PreferenceCategory mPreferenceCategory;
    private View mProgressHeader;
    private SatelliteManager mSatelliteManager;
    NetworkOperatorPreference mSelectedPreference;
    private Preference mStatusMessagePreference;
    private TelephonyManager mTelephonyManager;
    List<CellInfo> mCellInfoList = ImmutableList.of();
    private int mSubId = -1;
    private boolean mShow4GForLTE = false;
    private final ExecutorService mNetworkScanExecutor = Executors.newFixedThreadPool(1);
    private AtomicBoolean mShouldFilterOutSatellitePlmn = new AtomicBoolean();
    private Job mNetworkScanJob = null;
    private final Handler mHandler =
            new Handler() { // from class:
                            // com.android.settings.network.telephony.NetworkSelectSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    NetworkSelectSettings networkSelectSettings = NetworkSelectSettings.this;
                    networkSelectSettings.setProgressBarVisible(false);
                    networkSelectSettings.enablePreferenceScreen(true);
                    NetworkOperatorPreference networkOperatorPreference =
                            networkSelectSettings.mSelectedPreference;
                    if (networkOperatorPreference != null) {
                        networkOperatorPreference.setSummary(
                                booleanValue
                                        ? R.string.network_connected
                                        : R.string.network_could_not_connect);
                    } else {
                        Log.e(NetworkSelectSettings.TAG, "No preference to update!");
                    }
                }
            };

    private void addMessagePreference(int i) {
        this.mStatusMessagePreference.setTitle(i);
        this.mPreferenceCategory.removeAll();
        this.mPreferenceCategory.addPreference(this.mStatusMessagePreference);
    }

    private void clearPreferenceSummary() {
        int preferenceCount = this.mPreferenceCategory.getPreferenceCount();
        while (preferenceCount > 0) {
            preferenceCount--;
            this.mPreferenceCategory.getPreference(preferenceCount).setSummary((CharSequence) null);
        }
    }

    private void forceUpdateConnectedPreferenceCategory(
            NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo
                    networkRegistrationAndForbiddenInfo) {
        this.mPreferenceCategory.removeAll();
        Iterator it = networkRegistrationAndForbiddenInfo.networkList.iterator();
        while (it.hasNext()) {
            CellIdentity cellIdentity = ((NetworkRegistrationInfo) it.next()).getCellIdentity();
            if (cellIdentity != null) {
                NetworkOperatorPreference networkOperatorPreference =
                        new NetworkOperatorPreference(
                                getPrefContext(),
                                networkRegistrationAndForbiddenInfo.forbiddenPlmns,
                                this.mShow4GForLTE);
                networkOperatorPreference.updateCell(null, cellIdentity);
                List list = networkOperatorPreference.forbiddenPlmns;
                CellIdentity cellIdentity2 = networkOperatorPreference.cellId;
                if (!CollectionsKt___CollectionsKt.contains(
                        list,
                        cellIdentity2 != null
                                ? CellInfoUtil.getOperatorNumeric(cellIdentity2)
                                : null)) {
                    networkOperatorPreference.setSummary(R.string.network_connected);
                    networkOperatorPreference.setIcon(4);
                    this.mPreferenceCategory.addPreference(networkOperatorPreference);
                    return;
                }
            }
        }
    }

    private void handleCarrierConfigChanged(int i) {
        boolean z =
                this.mCarrierConfigManager
                        .getConfigForSubId(i, "remove_satellite_plmn_in_manual_network_scan_bool")
                        .getBoolean("remove_satellite_plmn_in_manual_network_scan_bool", true);
        if (z != this.mShouldFilterOutSatellitePlmn.get()) {
            this.mShouldFilterOutSatellitePlmn.set(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterOutSatellitePlmn$4(
            List list, CellInfo cellInfo) {
        return !list.contains(CellInfoUtil.getOperatorNumeric(cellInfo.getCellIdentity()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$launchNetworkScan$2(
            NetworkScanRepository.NetworkScanResult networkScanResult) {
        if (isPreferenceScreenEnabled()) {
            scanResultHandler(networkScanResult);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateInitialization$0(int i, int i2, int i3, int i4) {
        handleCarrierConfigChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPreferenceTreeClick$3(OperatorInfo operatorInfo) {
        Message obtainMessage = this.mHandler.obtainMessage(1);
        obtainMessage.obj =
                Boolean.valueOf(
                        this.mTelephonyManager.setNetworkSelectionModeManual(operatorInfo, true));
        obtainMessage.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onViewCreated$1(
            NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo
                    networkRegistrationAndForbiddenInfo) {
        forceUpdateConnectedPreferenceCategory(networkRegistrationAndForbiddenInfo);
        return Unit.INSTANCE;
    }

    private void launchNetworkScan() {
        setProgressBarVisible(true);
        this.mNetworkScanJob =
                this.mNetworkScanRepository.launchNetworkScan(
                        getViewLifecycleOwner(),
                        new NetworkSelectSettings$$ExternalSyntheticLambda0(this, 1));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateAllPreferenceCategory() {
        /*
            r6 = this;
            androidx.preference.PreferenceCategory r0 = r6.mPreferenceCategory
            int r0 = r0.getPreferenceCount()
        L6:
            java.util.List<android.telephony.CellInfo> r1 = r6.mCellInfoList
            int r1 = r1.size()
            if (r0 <= r1) goto L1a
            int r0 = r0 + (-1)
            androidx.preference.PreferenceCategory r1 = r6.mPreferenceCategory
            androidx.preference.Preference r2 = r1.getPreference(r0)
            r1.removePreference(r2)
            goto L6
        L1a:
            r1 = 0
        L1b:
            java.util.List<android.telephony.CellInfo> r2 = r6.mCellInfoList
            int r2 = r2.size()
            if (r1 >= r2) goto L85
            java.util.List<android.telephony.CellInfo> r2 = r6.mCellInfoList
            java.lang.Object r2 = r2.get(r1)
            android.telephony.CellInfo r2 = (android.telephony.CellInfo) r2
            r3 = 0
            if (r1 >= r0) goto L4e
            androidx.preference.PreferenceCategory r4 = r6.mPreferenceCategory
            androidx.preference.Preference r4 = r4.getPreference(r1)
            boolean r5 = r4 instanceof com.android.settings.network.telephony.NetworkOperatorPreference
            if (r5 == 0) goto L49
            com.android.settings.network.telephony.NetworkOperatorPreference r4 = (com.android.settings.network.telephony.NetworkOperatorPreference) r4
            if (r2 == 0) goto L44
            r4.getClass()
            android.telephony.CellIdentity r5 = r2.getCellIdentity()
            goto L45
        L44:
            r5 = r3
        L45:
            r4.updateCell(r2, r5)
            goto L4f
        L49:
            androidx.preference.PreferenceCategory r5 = r6.mPreferenceCategory
            r5.removePreference(r4)
        L4e:
            r4 = r3
        L4f:
            if (r4 != 0) goto L5d
            com.android.settings.network.telephony.NetworkOperatorPreference r4 = r6.createNetworkOperatorPreference(r2)
            r4.setOrder(r1)
            androidx.preference.PreferenceCategory r2 = r6.mPreferenceCategory
            r2.addPreference(r4)
        L5d:
            android.telephony.CellIdentity r2 = r4.cellId
            if (r2 == 0) goto L66
            java.lang.String r2 = com.android.settings.network.telephony.CellInfoUtil.getNetworkTitle(r2)
            goto L67
        L66:
            r2 = r3
        L67:
            r4.setKey(r2)
            java.util.List<android.telephony.CellInfo> r2 = r6.mCellInfoList
            java.lang.Object r2 = r2.get(r1)
            android.telephony.CellInfo r2 = (android.telephony.CellInfo) r2
            boolean r2 = r2.isRegistered()
            if (r2 == 0) goto L7f
            r2 = 2132023596(0x7f14192c, float:1.9685644E38)
            r4.setSummary(r2)
            goto L82
        L7f:
            r4.setSummary(r3)
        L82:
            int r1 = r1 + 1
            goto L1b
        L85:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.NetworkSelectSettings.updateAllPreferenceCategory():void");
    }

    @Keep
    public NetworkOperatorPreference createNetworkOperatorPreference(CellInfo cellInfo) {
        if (this.mForbiddenPlmns == null) {
            updateForbiddenPlmns();
        }
        NetworkOperatorPreference networkOperatorPreference =
                new NetworkOperatorPreference(
                        getPrefContext(), this.mForbiddenPlmns, this.mShow4GForLTE);
        networkOperatorPreference.updateCell(
                cellInfo, cellInfo != null ? cellInfo.getCellIdentity() : null);
        return networkOperatorPreference;
    }

    @Keep
    public void enablePreferenceScreen(boolean z) {
        getPreferenceScreen().setEnabled(z);
    }

    public List<CellInfo> filterOutSatellitePlmn(List<CellInfo> list) {
        final List<String> satellitePlmnsForCarrierWrapper = getSatellitePlmnsForCarrierWrapper();
        return (!this.mShouldFilterOutSatellitePlmn.get()
                        || satellitePlmnsForCarrierWrapper.isEmpty())
                ? list
                : (List)
                        list.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.network.telephony.NetworkSelectSettings$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                boolean lambda$filterOutSatellitePlmn$4;
                                                lambda$filterOutSatellitePlmn$4 =
                                                        NetworkSelectSettings
                                                                .lambda$filterOutSatellitePlmn$4(
                                                                        satellitePlmnsForCarrierWrapper,
                                                                        (CellInfo) obj);
                                                return lambda$filterOutSatellitePlmn$4;
                                            }
                                        })
                                .collect(Collectors.toList());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public /* bridge */ /* synthetic */ AppContext getAppContext() throws JSONException {
        return null;
    }

    @Keep
    public CarrierConfigManager getCarrierConfigManager(Context context) {
        return (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
    }

    public /* bridge */ /* synthetic */ int getHelpResource() {
        return R.string.help_uri_default;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 1581;
    }

    @Keep
    public MetricsFeatureProvider getMetricsFeatureProvider(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return featureFactoryImpl.getMetricsFeatureProvider();
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    @Keep
    public PreferenceCategory getPreferenceCategory(String str) {
        return (PreferenceCategory) findPreference(str);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public int getPreferenceScreenResId() {
        return R.xml.choose_network;
    }

    @Keep
    public SatelliteManager getSatelliteManager(Context context) {
        return (SatelliteManager) context.getSystemService(SatelliteManager.class);
    }

    public List<String> getSatellitePlmnsForCarrierWrapper() {
        SatelliteManager satelliteManager = this.mSatelliteManager;
        if (satelliteManager != null) {
            return satelliteManager.getSatellitePlmnsForCarrier(this.mSubId);
        }
        Log.e(TAG, "mSatelliteManager is null, return empty list");
        return new ArrayList();
    }

    @Keep
    public int getSubId() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            return intent.getIntExtra("android.provider.extra.SUB_ID", -1);
        }
        return -1;
    }

    @Keep
    public TelephonyManager getTelephonyManager(Context context, int i) {
        return ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(i);
    }

    @Keep
    public boolean isPreferenceScreenEnabled() {
        return getPreferenceScreen().isEnabled();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        onCreateInitialization();
    }

    @Initializer
    @Keep
    public void onCreateInitialization() {
        Context context = getContext();
        this.mSubId = getSubId();
        this.mPreferenceCategory = getPreferenceCategory(PREF_KEY_NETWORK_OPERATORS);
        Preference preference = new Preference(context);
        this.mStatusMessagePreference = preference;
        preference.setSelectable(false);
        this.mSelectedPreference = null;
        this.mTelephonyManager = getTelephonyManager(context, this.mSubId);
        this.mSatelliteManager = getSatelliteManager(context);
        CarrierConfigManager carrierConfigManager = getCarrierConfigManager(context);
        this.mCarrierConfigManager = carrierConfigManager;
        PersistableBundle configForSubId =
                carrierConfigManager.getConfigForSubId(
                        this.mSubId,
                        "show_4g_for_lte_data_icon_bool",
                        "remove_satellite_plmn_in_manual_network_scan_bool");
        this.mShow4GForLTE = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool", false);
        this.mShouldFilterOutSatellitePlmn.set(
                configForSubId.getBoolean(
                        "remove_satellite_plmn_in_manual_network_scan_bool", true));
        this.mMetricsFeatureProvider = getMetricsFeatureProvider(context);
        CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener =
                new CarrierConfigManager
                        .CarrierConfigChangeListener() { // from class:
                                                         // com.android.settings.network.telephony.NetworkSelectSettings$$ExternalSyntheticLambda1
                    @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
                    public final void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                        NetworkSelectSettings.this.lambda$onCreateInitialization$0(i, i2, i3, i4);
                    }
                };
        this.mCarrierConfigChangeListener = carrierConfigChangeListener;
        this.mCarrierConfigManager.registerCarrierConfigChangeListener(
                this.mNetworkScanExecutor, carrierConfigChangeListener);
        this.mNetworkScanRepository = new NetworkScanRepository(context, this.mSubId);
        this.mNetworkSelectRepository = new NetworkSelectRepository(context, this.mSubId);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mNetworkScanExecutor.shutdown();
        super.onDestroy();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == this.mSelectedPreference) {
            Log.d(TAG, "onPreferenceTreeClick: preference is mSelectedPreference. Do nothing.");
            return true;
        }
        int i = 0;
        if (!(preference instanceof NetworkOperatorPreference)) {
            Log.d(TAG, "onPreferenceTreeClick: preference is not the NetworkOperatorPreference.");
            return false;
        }
        Job job = this.mNetworkScanJob;
        if (job != null) {
            job.cancel(null);
            this.mNetworkScanJob = null;
        }
        clearPreferenceSummary();
        NetworkOperatorPreference networkOperatorPreference = this.mSelectedPreference;
        if (networkOperatorPreference != null) {
            networkOperatorPreference.setSummary(R.string.network_disconnected);
        }
        NetworkOperatorPreference networkOperatorPreference2 =
                (NetworkOperatorPreference) preference;
        this.mSelectedPreference = networkOperatorPreference2;
        networkOperatorPreference2.setSummary(R.string.network_connecting);
        this.mMetricsFeatureProvider.action(getContext(), 1210, new Pair[0]);
        setProgressBarVisible(true);
        enablePreferenceScreen(false);
        NetworkOperatorPreference networkOperatorPreference3 = this.mSelectedPreference;
        networkOperatorPreference3.getClass();
        CellIdentity cellIdentity = networkOperatorPreference3.cellId;
        String objects =
                Objects.toString(
                        cellIdentity != null ? cellIdentity.getOperatorAlphaLong() : null,
                        ApnSettings.MVNO_NONE);
        CellIdentity cellIdentity2 = networkOperatorPreference3.cellId;
        String objects2 =
                Objects.toString(
                        cellIdentity2 != null ? cellIdentity2.getOperatorAlphaShort() : null,
                        ApnSettings.MVNO_NONE);
        CellIdentity cellIdentity3 = networkOperatorPreference3.cellId;
        String operatorNumeric =
                cellIdentity3 != null ? CellInfoUtil.getOperatorNumeric(cellIdentity3) : null;
        CellInfo cellInfo = networkOperatorPreference3.cellInfo;
        if (cellInfo instanceof CellInfoGsm) {
            i = 1;
        } else if (cellInfo instanceof CellInfoCdma) {
            i = 4;
        } else {
            if (cellInfo instanceof CellInfoWcdma ? true : cellInfo instanceof CellInfoTdscdma) {
                i = 2;
            } else if (cellInfo instanceof CellInfoLte) {
                i = 3;
            } else if (cellInfo instanceof CellInfoNr) {
                i = 6;
            }
        }
        final OperatorInfo operatorInfo = new OperatorInfo(objects, objects2, operatorNumeric, i);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.network.telephony.NetworkSelectSettings$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkSelectSettings.this.lambda$onPreferenceTreeClick$3(operatorInfo);
                    }
                });
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mProgressHeader =
                setPinnedHeaderView(R.layout.progress_header)
                        .findViewById(R.id.progress_bar_animation);
        NetworkSelectRepository networkSelectRepository = this.mNetworkSelectRepository;
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        NetworkSelectSettings$$ExternalSyntheticLambda0
                networkSelectSettings$$ExternalSyntheticLambda0 =
                        new NetworkSelectSettings$$ExternalSyntheticLambda0(this, 0);
        networkSelectRepository.getClass();
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(lifecycleOwner),
                null,
                null,
                new NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1(
                        lifecycleOwner,
                        networkSelectSettings$$ExternalSyntheticLambda0,
                        networkSelectRepository,
                        null),
                3);
        launchNetworkScan();
    }

    public void scanResultHandler(NetworkScanRepository.NetworkScanResult networkScanResult) {
        List<CellInfo> cellInfos = filterOutSatellitePlmn(networkScanResult.cellInfos);
        this.mCellInfoList = cellInfos;
        Intrinsics.checkNotNullParameter(cellInfos, "cellInfos");
        String lineSeparator = System.lineSeparator();
        Intrinsics.checkNotNullExpressionValue(lineSeparator, "lineSeparator(...)");
        Log.d(
                TAG,
                "CellInfoList: "
                        .concat(
                                CollectionsKt___CollectionsKt.joinToString$default(
                                        cellInfos,
                                        lineSeparator,
                                        null,
                                        null,
                                        new Function1() { // from class:
                                                          // com.android.settings.network.telephony.CellInfoUtil$cellInfoListToString$1
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                CellInfo cellInfo = (CellInfo) obj;
                                                Intrinsics.checkNotNullParameter(
                                                        cellInfo, "cellInfo");
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(
                                                        "{CellType = "
                                                                + Reflection.factory
                                                                        .getOrCreateKotlinClass(
                                                                                cellInfo.getClass())
                                                                        .getSimpleName()
                                                                + ", ");
                                                sb.append(
                                                        "isRegistered = "
                                                                + cellInfo.isRegistered()
                                                                + ", ");
                                                CellIdentity cellIdentity =
                                                        cellInfo.getCellIdentity();
                                                Intrinsics.checkNotNullExpressionValue(
                                                        cellIdentity, "getCellIdentity(...)");
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(
                                                        "mcc = "
                                                                + cellIdentity.getMccString()
                                                                + ", ");
                                                sb2.append(
                                                        "mnc = "
                                                                + cellIdentity.getMncString()
                                                                + ", ");
                                                sb2.append(
                                                        "alphaL = "
                                                                + ((Object)
                                                                        cellIdentity
                                                                                .getOperatorAlphaLong())
                                                                + ", ");
                                                sb2.append(
                                                        "alphaS = "
                                                                + ((Object)
                                                                        cellIdentity
                                                                                .getOperatorAlphaShort()));
                                                String sb3 = sb2.toString();
                                                Intrinsics.checkNotNullExpressionValue(
                                                        sb3, "toString(...)");
                                                sb.append(sb3);
                                                sb.append("}");
                                                String sb4 = sb.toString();
                                                Intrinsics.checkNotNullExpressionValue(
                                                        sb4, "toString(...)");
                                                return sb4;
                                            }
                                        },
                                        30)));
        updateAllPreferenceCategory();
        NetworkScanRepository.NetworkScanState networkScanState =
                NetworkScanRepository.NetworkScanState.ERROR;
        NetworkScanRepository.NetworkScanState networkScanState2 = networkScanResult.state;
        if (networkScanState2 == networkScanState) {
            addMessagePreference(R.string.network_query_error);
        } else if (this.mCellInfoList.isEmpty()) {
            addMessagePreference(R.string.empty_networks_list);
        }
        setProgressBarVisible(networkScanState2 == NetworkScanRepository.NetworkScanState.ACTIVE);
    }

    public void setProgressBarVisible(boolean z) {
        View view = this.mProgressHeader;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }

    @Keep
    public void updateForbiddenPlmns() {
        String[] forbiddenPlmns = this.mTelephonyManager.getForbiddenPlmns();
        this.mForbiddenPlmns =
                forbiddenPlmns != null ? Arrays.asList(forbiddenPlmns) : new ArrayList<>();
    }
}
