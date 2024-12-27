package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Debug;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureSetDataLimitPreferenceController extends BasePreferenceController
        implements LifecycleEventObserver {
    private static final boolean DBG = Debug.semIsProductDev();
    public static final String KEY_PREFERENCE = "set_data_limit_preference";
    private static final String TAG = "WifiApConfigureSetDataLimitPreferenceController";
    private final BigDecimal GB;
    private final BigDecimal KB;
    private final BigDecimal MB;
    private DialogInterface.OnDismissListener dismissListener;
    private final ApMobileData mApMobileData;
    private Context mContext;
    private WifiApSetDataLimitDialog mDataLimitDialog;
    private SemWifiManager.SemWifiApDataUsageListener mDataUsageListener;
    private ContentResolver mResolver;
    private Resources mResources;
    private SemWifiManager mSemWifiManager;
    private SecPreference mThisPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ApMobileData {
        public final Context apContext;
        public boolean isLimited;
        public String limitDataStr;
        public String limitText;
        public String limitUnit;
        public BigDecimal limitValue;
        public String usageText;
        public String usageUnit;
        public BigDecimal usageValue;

        public ApMobileData(Context context) {
            this.apContext = context;
        }

        public static String getBigDecimalValue(
                BigDecimal bigDecimal, BigDecimal bigDecimal2, int i) {
            if (i <= 0) {
                return String.format(
                        "%d", Long.valueOf(bigDecimal.divide(bigDecimal2, i, 6).longValue()));
            }
            return String.format(
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "%.", "f"),
                    Double.valueOf(bigDecimal.divide(bigDecimal2, i, 6).doubleValue()));
        }
    }

    public WifiApConfigureSetDataLimitPreferenceController(Context context, String str) {
        super(context, str);
        this.KB = BigDecimal.valueOf(1000L);
        BigDecimal valueOf = BigDecimal.valueOf(1000000L);
        this.MB = valueOf;
        BigDecimal valueOf2 = BigDecimal.valueOf(1000000000L);
        this.GB = valueOf2;
        this.mDataUsageListener =
                new SemWifiManager
                        .SemWifiApDataUsageListener() { // from class:
                                                        // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSetDataLimitPreferenceController.1
                    public final void onDataUsageChanged(String str2) {
                        ApMobileData apMobileData =
                                WifiApConfigureSetDataLimitPreferenceController.this.mApMobileData;
                        apMobileData.getClass();
                        BigDecimal bigDecimal =
                                str2 != null ? new BigDecimal(str2) : BigDecimal.valueOf(0L);
                        apMobileData.usageValue = bigDecimal;
                        WifiApConfigureSetDataLimitPreferenceController
                                wifiApConfigureSetDataLimitPreferenceController =
                                        WifiApConfigureSetDataLimitPreferenceController.this;
                        if (bigDecimal.compareTo(wifiApConfigureSetDataLimitPreferenceController.MB)
                                < 0) {
                            apMobileData.usageText =
                                    ApMobileData.getBigDecimalValue(
                                            apMobileData.usageValue,
                                            wifiApConfigureSetDataLimitPreferenceController.KB,
                                            2);
                            apMobileData.usageUnit =
                                    apMobileData
                                            .apContext
                                            .getResources()
                                            .getString(R.string.data_usage_display_kb);
                        } else if (apMobileData.usageValue.compareTo(
                                        wifiApConfigureSetDataLimitPreferenceController.GB)
                                < 0) {
                            apMobileData.usageText =
                                    ApMobileData.getBigDecimalValue(
                                            apMobileData.usageValue,
                                            wifiApConfigureSetDataLimitPreferenceController.MB,
                                            2);
                            apMobileData.usageUnit =
                                    apMobileData
                                            .apContext
                                            .getResources()
                                            .getString(R.string.data_usage_display_mb);
                        } else {
                            apMobileData.usageText =
                                    ApMobileData.getBigDecimalValue(
                                            apMobileData.usageValue,
                                            wifiApConfigureSetDataLimitPreferenceController.GB,
                                            2);
                            apMobileData.usageUnit =
                                    apMobileData
                                            .apContext
                                            .getResources()
                                            .getString(R.string.data_usage_display_gb);
                        }
                        WifiApConfigureSetDataLimitPreferenceController
                                wifiApConfigureSetDataLimitPreferenceController2 =
                                        WifiApConfigureSetDataLimitPreferenceController.this;
                        wifiApConfigureSetDataLimitPreferenceController2.updateState(
                                wifiApConfigureSetDataLimitPreferenceController2.mThisPreference);
                    }
                };
        this.dismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSetDataLimitPreferenceController.2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        WifiApConfigureSetDataLimitPreferenceController.this.mDataLimitDialog =
                                null;
                    }
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mResources = this.mContext.getResources();
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        Context context2 = this.mContext;
        ApMobileData apMobileData = new ApMobileData(context2);
        this.mApMobileData = apMobileData;
        boolean z =
                Settings.Secure.getInt(
                                context2.getContentResolver(), "wifi_ap_mobile_data_limit", 0)
                        == 1;
        apMobileData.isLimited = z;
        apMobileData.limitDataStr =
                z
                        ? Settings.Secure.getString(
                                context2.getContentResolver(), "wifi_ap_mobile_data_limit_value")
                        : null;
        String str2 = TAG;
        StringBuilder sb = new StringBuilder("limited? ");
        sb.append(apMobileData.isLimited);
        sb.append(" limit Value:");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, apMobileData.limitDataStr, str2);
        if (apMobileData.limitDataStr != null) {
            BigDecimal bigDecimal = new BigDecimal(apMobileData.limitDataStr);
            apMobileData.limitValue = bigDecimal;
            if (bigDecimal.compareTo(valueOf2) < 0) {
                apMobileData.limitText =
                        ApMobileData.getBigDecimalValue(apMobileData.limitValue, valueOf, 0);
                apMobileData.limitUnit =
                        context2.getResources().getString(R.string.data_usage_display_mb);
            } else {
                apMobileData.limitText =
                        ApMobileData.getBigDecimalValue(apMobileData.limitValue, valueOf2, 2);
                apMobileData.limitUnit =
                        context2.getResources().getString(R.string.data_usage_display_gb);
            }
        }
    }

    private synchronized String getApDataLimitSummary() {
        String string;
        try {
            if (isMobileApOn()) {
                ApMobileData apMobileData = this.mApMobileData;
                string =
                        apMobileData.isLimited
                                ? this.mResources.getString(
                                        R.string.wifi_ap_limited_data_usage,
                                        apMobileData.usageText,
                                        apMobileData.usageUnit,
                                        apMobileData.limitText,
                                        apMobileData.limitUnit)
                                : this.mResources.getString(
                                        R.string.wifi_ap_unlimited_data_usage,
                                        apMobileData.usageText,
                                        apMobileData.usageUnit);
            } else {
                ApMobileData apMobileData2 = this.mApMobileData;
                string =
                        apMobileData2.isLimited
                                ? this.mResources.getString(
                                        R.string.wifi_ap_limit_data_value,
                                        apMobileData2.limitText,
                                        apMobileData2.limitUnit)
                                : this.mResources.getString(R.string.wifi_ap_data_limit_default);
            }
            if (DBG) {
                Log.d(TAG, "Data Limit summary: " + string);
            }
        } catch (Throwable th) {
            throw th;
        }
        return string;
    }

    private boolean isMobileApOn() {
        SemWifiManager semWifiManager = this.mSemWifiManager;
        if (semWifiManager == null) {
            Log.e(TAG, "Wifi Manager is null");
            return false;
        }
        int wifiApState = semWifiManager.getWifiApState();
        return wifiApState == 13 || wifiApState == 12;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mThisPreference = (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext) ? 0 : 3;
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

    /* JADX WARN: Type inference failed for: r3v2, types: [com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSetDataLimitPreferenceController$3] */
    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        String str = TAG;
        Log.i(str, "handlePreferenceTreeClick - Triggered");
        WifiApSetDataLimitDialog wifiApSetDataLimitDialog = this.mDataLimitDialog;
        if (wifiApSetDataLimitDialog != null && wifiApSetDataLimitDialog.isShowing()) {
            Log.d(str, "Dialog is showing, ignore");
            return true;
        }
        SALogging.insertSALog("TETH_012", "8015");
        Context context = this.mContext;
        ApMobileData apMobileData = this.mApMobileData;
        BigDecimal bigDecimal = this.MB;
        BigDecimal bigDecimal2 = apMobileData.limitValue;
        WifiApSetDataLimitDialog wifiApSetDataLimitDialog2 =
                new WifiApSetDataLimitDialog(
                        context,
                        bigDecimal2 != null
                                ? bigDecimal2.divide(bigDecimal, 0, 6).toString()
                                : ApnSettings.MVNO_NONE,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSetDataLimitPreferenceController.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                String str2 = null;
                                if (i == -1) {
                                    SALogging.insertSALog(1L, "TETH_010", "8028", (String) null);
                                    ApMobileData apMobileData2 =
                                            WifiApConfigureSetDataLimitPreferenceController.this
                                                    .mApMobileData;
                                    WifiApSetDataLimitDialog wifiApSetDataLimitDialog3 =
                                            (WifiApSetDataLimitDialog) dialogInterface;
                                    if (wifiApSetDataLimitDialog3.mEtDataLimit.getText().length()
                                                    > 0
                                            && wifiApSetDataLimitDialog3
                                                            .mEtDataLimit
                                                            .getText()
                                                            .toString()
                                                            .trim()
                                                            .length()
                                                    > 0) {
                                        str2 =
                                                wifiApSetDataLimitDialog3
                                                        .mEtDataLimit
                                                        .getText()
                                                        .toString();
                                    }
                                    if (str2 != null) {
                                        apMobileData2.isLimited = true;
                                        BigDecimal bigDecimal3 = new BigDecimal(str2);
                                        WifiApConfigureSetDataLimitPreferenceController
                                                wifiApConfigureSetDataLimitPreferenceController =
                                                        WifiApConfigureSetDataLimitPreferenceController
                                                                .this;
                                        BigDecimal multiply =
                                                bigDecimal3.multiply(
                                                        wifiApConfigureSetDataLimitPreferenceController
                                                                .MB);
                                        apMobileData2.limitValue = multiply;
                                        apMobileData2.limitDataStr = multiply.toString();
                                        if (apMobileData2.limitValue.compareTo(
                                                        wifiApConfigureSetDataLimitPreferenceController
                                                                .GB)
                                                < 0) {
                                            apMobileData2.limitText =
                                                    ApMobileData.getBigDecimalValue(
                                                            apMobileData2.limitValue,
                                                            wifiApConfigureSetDataLimitPreferenceController
                                                                    .MB,
                                                            0);
                                            apMobileData2.limitUnit =
                                                    apMobileData2
                                                            .apContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string.data_usage_display_mb);
                                        } else {
                                            apMobileData2.limitText =
                                                    ApMobileData.getBigDecimalValue(
                                                            apMobileData2.limitValue,
                                                            wifiApConfigureSetDataLimitPreferenceController
                                                                    .GB,
                                                            2);
                                            apMobileData2.limitUnit =
                                                    apMobileData2
                                                            .apContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string.data_usage_display_gb);
                                        }
                                    } else {
                                        apMobileData2.getClass();
                                    }
                                } else if (i == -2) {
                                    SALogging.insertSALog(2L, "TETH_010", "8028", (String) null);
                                    ApMobileData apMobileData3 =
                                            WifiApConfigureSetDataLimitPreferenceController.this
                                                    .mApMobileData;
                                    apMobileData3.isLimited = false;
                                    apMobileData3.limitDataStr = null;
                                    apMobileData3.limitValue = null;
                                } else if (i == -3) {
                                    SALogging.insertSALog(0L, "TETH_010", "8028", (String) null);
                                }
                                WifiApConfigureSetDataLimitPreferenceController
                                        wifiApConfigureSetDataLimitPreferenceController2 =
                                                WifiApConfigureSetDataLimitPreferenceController
                                                        .this;
                                wifiApConfigureSetDataLimitPreferenceController2.updateState(
                                        wifiApConfigureSetDataLimitPreferenceController2
                                                .mThisPreference);
                            }
                        });
        this.mDataLimitDialog = wifiApSetDataLimitDialog2;
        wifiApSetDataLimitDialog2.setOnDismissListener(this.dismissListener);
        this.mDataLimitDialog.show();
        return true;
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
        if (event == Lifecycle.Event.ON_RESUME) {
            this.mSemWifiManager.registerWifiApDataUsageListener(
                    this.mDataUsageListener, this.mContext.getMainExecutor());
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            this.mSemWifiManager.unRegisterWifiApDataUsageListener(this.mDataUsageListener);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void saveDataLimitChange() {
        if (isAvailable()) {
            ApMobileData apMobileData = this.mApMobileData;
            apMobileData.getClass();
            String str = TAG;
            StringBuilder sb = new StringBuilder("save DataLimited: ");
            sb.append(apMobileData.isLimited);
            sb.append("  ");
            MainClearConfirm$$ExternalSyntheticOutline0.m(sb, apMobileData.limitDataStr, str);
            if (apMobileData.isLimited) {
                Settings.Secure.putInt(
                        apMobileData.apContext.getContentResolver(),
                        "wifi_ap_mobile_data_limit",
                        1);
            } else {
                Settings.Secure.putInt(
                        apMobileData.apContext.getContentResolver(),
                        "wifi_ap_mobile_data_limit",
                        0);
            }
            Settings.Secure.putString(
                    apMobileData.apContext.getContentResolver(),
                    "wifi_ap_mobile_data_limit_value",
                    apMobileData.limitDataStr);
        }
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApConfigureSettings = wifiApEditSettings;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(getApDataLimitSummary());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
