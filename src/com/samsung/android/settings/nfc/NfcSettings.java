package com.samsung.android.settings.nfc;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.nfc.search.NfcExcludeSearchListCreator;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.util.SemLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NfcSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final boolean isJapanKDIModel;
    public NfcAntennaAnimation antennaAnimation;
    public SecInsetCategoryPreference mEmptyCategory;
    public SecInsetCategoryPreference mEmptyCategoryBelowNfcExplain;
    public SecInsetCategoryPreference mEmptyPaymentSim;
    public ForwardingPreference mForwardingPref;
    public SharedPreferences mNfcPreferences;
    public PreferenceScreen mNfcSettingPref;
    public SecPreferenceScreen mOtherService;
    public SecSwitchPreference mReaderMode;
    public PreferenceScreen mScreen;
    public SecSwitchPreference mSecureNfcDCM;
    public SecSwitchPreference mSecureNfcKDI;
    public SecPreference mTapPay;
    public SecPreference mTapPayDCM;
    public static final boolean DBG = Debug.semIsProductDev();
    public static boolean mIsGpFelicaSupported = false;
    public Context mContext = null;
    public FragmentActivity mActivity = null;
    public SettingsMainSwitchBar mSwitchBar = null;
    public NfcAdapter mNfcAdapter = null;
    public NfcEnabler mNfcEnabler = null;
    public AlertDialog mAlertDialog = null;
    public final IntentFilter mEUiccIntentFilter = new IntentFilter();
    public LayoutPreference mExplainLayout = null;
    public boolean mIsEmergencyMode = false;
    public PaymentDropDownPreference mPaymentSim = null;
    public final AnonymousClass1 mEUiccReceiver =
            new BroadcastReceiver() { // from class: com.samsung.android.settings.nfc.NfcSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("NfcSettings", "mEUiccReceiver : " + intent.getAction());
                    if (NfcSettings.this.mNfcPreferences.getBoolean("showNfcEuiccMenu", false)) {
                        NfcSettings.this.updatePaymentSim();
                    }
                }
            };
    public final AnonymousClass2 sharedPreferenceChangeListener =
            new SharedPreferences
                    .OnSharedPreferenceChangeListener() { // from class:
                                                          // com.samsung.android.settings.nfc.NfcSettings.2
                @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                public final void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String str) {
                    Log.d("NfcSettings", "nfc pref changed");
                    if (!TextUtils.isEmpty(str)
                            && str.equals("showNfcEuiccMenu")
                            && NfcSettings.this.mNfcPreferences.getBoolean(
                                    "showNfcEuiccMenu", false)) {
                        NfcSettings.this.createPreferenceForPaymentSim();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.nfc.NfcSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            NfcAdapter defaultAdapter;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            NfcExcludeSearchListCreator.deleteKeyList = nonIndexableKeys;
            NfcExcludeSearchListCreator.mContext = context;
            ((ArrayList) nonIndexableKeys).add("nfc_mode_general");
            NfcExcludeSearchListCreator.deleteKeyList.add("mode_nfc");
            Context context2 = NfcExcludeSearchListCreator.mContext;
            int i = ConnectionsUtils.$r8$clinit;
            NfcAdapter defaultAdapter2 = NfcAdapter.getDefaultAdapter(context2);
            if (!(defaultAdapter2 == null ? false : defaultAdapter2.isReaderOptionSupported())) {
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_mode_reader");
            }
            NfcExcludeSearchListCreator.deleteKeyList.add("default_payment_app");
            NfcExcludeSearchListCreator.deleteKeyList.add(
                    com.android.settings.nfc.NfcPreferenceController.KEY_TOGGLE_NFC);
            NfcExcludeSearchListCreator.deleteKeyList.add("nfc_secure_settings");
            NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_sim");
            if (!Rune.isSupportAndroidBeam(NfcExcludeSearchListCreator.mContext)) {
                NfcExcludeSearchListCreator.deleteKeyList.add("android_beam_settings");
            }
            if (Rune.isVzwModel()
                    && (defaultAdapter =
                                    NfcAdapter.getDefaultAdapter(
                                            NfcExcludeSearchListCreator.mContext))
                            != null
                    && defaultAdapter.getAdapterState() == 1) {
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_settings");
            }
            NfcExcludeSearchListCreator.deleteKeyList.add("nfc_osaifukeitai_settings");
            if (Rune.isGpFelicaSupported(NfcExcludeSearchListCreator.mContext)) {
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_setting_title");
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_settings_jpn");
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_settings");
                if (Rune.isJapanKDIModel()) {
                    NfcExcludeSearchListCreator.deleteKeyList.add("secure_nfc_settings_dcm");
                } else {
                    NfcExcludeSearchListCreator.deleteKeyList.add("secure_nfc_settings_kdi");
                }
            } else {
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_setting_title");
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_settings_gp");
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_settings_jpn");
                NfcExcludeSearchListCreator.deleteKeyList.add("nfc_payment_settings_dcm");
                NfcExcludeSearchListCreator.deleteKeyList.add("secure_nfc_settings_kdi");
                NfcExcludeSearchListCreator.deleteKeyList.add("secure_nfc_settings_dcm");
            }
            return NfcExcludeSearchListCreator.deleteKeyList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (!context.getPackageManager().hasSystemFeature("android.hardware.nfc")) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            searchIndexableResource.xmlResId =
                    context.getPackageManager().hasSystemFeature("com.samsung.android.nfc.gpfelica")
                            ? R.xml.sec_nfc_settings_jpn
                            : R.xml.sec_nfc_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ForwardingPreference {
        public SecPreferenceScreen otherService;
        public PaymentDropDownPreference paymentSim;
        public SecSwitchPreference readerMode;
        public SecSwitchPreference secureNfc;
        public SecPreference tapPay;
    }

    static {
        Rune.isJapanDCMModel();
        isJapanKDIModel = Rune.isJapanKDIModel();
        "JAPAN".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
        File file = new File("/prism");
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            try {
                try {
                    try {
                        BufferedReader bufferedReader2 =
                                new BufferedReader(new FileReader(file + "/etc/code"));
                        while (true) {
                            try {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                Log.d("Multi CSC", "value = " + readLine);
                                if (!readLine.toUpperCase().equalsIgnoreCase("QOL")
                                        && !readLine.toUpperCase().equalsIgnoreCase("SJP")) {
                                    readLine.toUpperCase().equalsIgnoreCase("QOP");
                                }
                            } catch (Exception e) {
                                e = e;
                                bufferedReader = bufferedReader2;
                                e.printStackTrace();
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        bufferedReader2.close();
                        bufferedReader2.close();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
    }

    public final void createPreferenceForPaymentSim() {
        SecInsetCategoryPreference secInsetCategoryPreference =
                (SecInsetCategoryPreference) this.mScreen.findPreference("empty_nfc_payment_sim");
        PaymentDropDownPreference paymentDropDownPreference =
                (PaymentDropDownPreference) this.mScreen.findPreference("nfc_payment_sim");
        if (secInsetCategoryPreference == null || paymentDropDownPreference == null) {
            Log.d("NfcSettings", "eUICC removed from mScreen, re-adding");
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            this.mScreen = preferenceScreen;
            preferenceScreen.addPreference(this.mEmptyPaymentSim);
            this.mScreen.addPreference(this.mPaymentSim);
        }
        this.mPaymentSim.setTitle(R.string.sim_to_use_for_nfc);
        SecInsetCategoryPreference secInsetCategoryPreference2 = this.mEmptyPaymentSim;
        if (secInsetCategoryPreference2 != null) {
            secInsetCategoryPreference2.seslSetSubheaderRoundedBackground(15);
        }
        this.mForwardingPref.paymentSim = this.mPaymentSim;
        updatePaymentSim();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.mSwitchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "nfcEnabled",
                ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m("NFCSettings", "[]")
                        : "NFCSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return Rune.isGpFelicaSupported(context)
                ? R.string.nfc_osaifukeitai_title
                : R.string.nfcpayment_quick_toggle_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return SemEmergencyManager.isEmergencyMode(context)
                ? TopLevelSettings.class.getName()
                : ConnectionsSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NfcSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3650;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return Rune.isGpFelicaSupported(getActivity().getApplicationContext())
                ? R.xml.sec_nfc_settings_jpn
                : R.xml.sec_nfc_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(this.mNfcAdapter.isEnabled());
            this.mSwitchBar.show();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        populateViewForOrientation(this.mExplainLayout);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        setHasOptionsMenu(false);
        this.mNfcPreferences = this.mContext.getSharedPreferences("nfcSettingsPrefs", 0);
        mIsGpFelicaSupported = Rune.isGpFelicaSupported(this.mContext);
        this.mEUiccIntentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.antennaAnimation = new NfcAntennaAnimation(this.mContext);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mScreen = preferenceScreen;
        this.mForwardingPref = new ForwardingPreference();
        this.mExplainLayout = (LayoutPreference) preferenceScreen.findPreference("nfc_explain");
        this.mReaderMode = (SecSwitchPreference) this.mScreen.findPreference("nfc_mode_reader");
        this.mEmptyCategory =
                (SecInsetCategoryPreference) this.mScreen.findPreference("empty_general");
        this.mEmptyCategoryBelowNfcExplain =
                (SecInsetCategoryPreference) this.mScreen.findPreference("empty_nfc_explain");
        this.mEmptyPaymentSim =
                (SecInsetCategoryPreference) this.mScreen.findPreference("empty_nfc_payment_sim");
        this.mTapPay = (SecPreference) this.mScreen.findPreference("nfc_payment_settings");
        this.mTapPayDCM = (SecPreference) this.mScreen.findPreference("nfc_payment_settings_dcm");
        this.mOtherService =
                (SecPreferenceScreen) this.mScreen.findPreference("nfc_other_services");
        this.mPaymentSim =
                (PaymentDropDownPreference) this.mScreen.findPreference("nfc_payment_sim");
        this.mSecureNfcDCM =
                (SecSwitchPreference) this.mScreen.findPreference("secure_nfc_settings_dcm");
        this.mSecureNfcKDI =
                (SecSwitchPreference) this.mScreen.findPreference("secure_nfc_settings_kdi");
        this.mNfcSettingPref = this.mScreen;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (this.mNfcAdapter != null && Rune.isSupportRoutingSettings(getActivity())) {
            menu.add(0, 1, 0, R.string.nfc_settings_advanced_setting).setShowAsAction(0);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mActivity);
        String name = NfcAdvancedRoutingSetting.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.nfc_settings_advanced_setting, null);
        launchRequest.mSourceMetricsCategory = 3657;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        AlertDialog alertDialog;
        super.onPause();
        NfcEnabler nfcEnabler = this.mNfcEnabler;
        if (nfcEnabler != null) {
            nfcEnabler.pause();
        }
        if (this.mIsEmergencyMode && (alertDialog = this.mAlertDialog) != null) {
            alertDialog.dismiss();
        }
        if (Rune.isSupportEUiccSwp(this.mContext)) {
            this.mNfcPreferences.unregisterOnSharedPreferenceChangeListener(
                    this.sharedPreferenceChangeListener);
            this.mActivity.unregisterReceiver(this.mEUiccReceiver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        NfcAdapter nfcAdapter;
        MenuItem findItem = menu.findItem(1);
        if (findItem == null || (nfcAdapter = this.mNfcAdapter) == null) {
            return;
        }
        if (nfcAdapter.getAdapterState() == 1) {
            findItem.setEnabled(false);
        } else {
            findItem.setEnabled(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mNfcAdapter.isReaderOptionSupported()) {
            SecInsetCategoryPreference secInsetCategoryPreference =
                    this.mEmptyCategoryBelowNfcExplain;
            if (secInsetCategoryPreference != null) {
                secInsetCategoryPreference.seslSetSubheaderRoundedBackground(3);
            }
            SecSwitchPreference secSwitchPreference = this.mReaderMode;
            if (secSwitchPreference != null) {
                this.mForwardingPref.readerMode = secSwitchPreference;
                secSwitchPreference.setChecked(this.mNfcAdapter.isReaderOptionEnabled());
                this.mForwardingPref.readerMode.setSummary(R.string.nfc_reader_mode_desc_common);
                this.mReaderMode.seslSetSubheaderRoundedBackground(3);
            }
        } else {
            this.mForwardingPref.readerMode = null;
            SecSwitchPreference secSwitchPreference2 = this.mReaderMode;
            if (secSwitchPreference2 != null) {
                this.mScreen.removePreference(secSwitchPreference2);
            }
            SecInsetCategoryPreference secInsetCategoryPreference2 =
                    this.mEmptyCategoryBelowNfcExplain;
            if (secInsetCategoryPreference2 != null) {
                this.mScreen.removePreference(secInsetCategoryPreference2);
            }
            SecInsetCategoryPreference secInsetCategoryPreference3 = this.mEmptyCategory;
            if (secInsetCategoryPreference3 != null) {
                secInsetCategoryPreference3.seslSetSubheaderRoundedBackground(3);
            }
        }
        SecPreference secPreference = this.mTapPay;
        if (secPreference != null) {
            if (mIsGpFelicaSupported) {
                this.mForwardingPref.tapPay = this.mTapPayDCM;
                this.mScreen.removePreference(secPreference);
            } else {
                this.mForwardingPref.tapPay = secPreference;
                SecPreference secPreference2 = this.mTapPayDCM;
                if (secPreference2 != null) {
                    this.mScreen.removePreference(secPreference2);
                    this.mTapPayDCM = null;
                }
            }
        }
        SecPreferenceScreen secPreferenceScreen = this.mOtherService;
        if (secPreferenceScreen != null) {
            this.mForwardingPref.otherService = secPreferenceScreen;
        }
        if (mIsGpFelicaSupported) {
            if (!isJapanKDIModel) {
                String str = SystemProperties.get("persist.omc.sales_code");
                String str2 = SystemProperties.get("ro.csc.sales_code");
                if (str == null || str.equals(ApnSettings.MVNO_NONE)) {
                    str = str2;
                }
                if (!"UQM".equals(str) && !"JCO".equals(str)) {
                    this.mForwardingPref.secureNfc = this.mSecureNfcDCM;
                    SecSwitchPreference secSwitchPreference3 = this.mSecureNfcKDI;
                    if (secSwitchPreference3 != null) {
                        this.mScreen.removePreference(secSwitchPreference3);
                    }
                }
            }
            this.mForwardingPref.secureNfc = this.mSecureNfcKDI;
            SecSwitchPreference secSwitchPreference4 = this.mSecureNfcDCM;
            if (secSwitchPreference4 != null) {
                this.mScreen.removePreference(secSwitchPreference4);
            }
        }
        if (!Rune.isSupportEUiccSwp(this.mContext)) {
            removePreferenceForPaymentSim();
        } else if (this.mNfcPreferences.getBoolean("showNfcEuiccMenu", false)) {
            createPreferenceForPaymentSim();
        } else {
            removePreferenceForPaymentSim();
        }
        ForwardingPreference forwardingPreference = this.mForwardingPref;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        forwardingPreference.getClass();
        PreferenceScreen preferenceScreen = this.mNfcSettingPref;
        if (mIsGpFelicaSupported) {
            NfcEnabler.Builder builder = new NfcEnabler.Builder(this.mActivity);
            builder.NfcSettingToggle = settingsMainSwitchBar;
            builder.ReaderMode = forwardingPreference.readerMode;
            builder.tapPay = forwardingPreference.tapPay;
            builder.PaymentSim = forwardingPreference.paymentSim;
            builder.otherService = forwardingPreference.otherService;
            builder.screenPref = preferenceScreen;
            builder.NfcJapanPreferenceSecureNfc = forwardingPreference.secureNfc;
            this.mNfcEnabler = new NfcEnabler(builder);
        } else {
            NfcEnabler.Builder builder2 = new NfcEnabler.Builder(this.mActivity);
            builder2.NfcSettingToggle = settingsMainSwitchBar;
            builder2.ReaderMode = forwardingPreference.readerMode;
            builder2.tapPay = forwardingPreference.tapPay;
            builder2.PaymentSim = forwardingPreference.paymentSim;
            builder2.otherService = forwardingPreference.otherService;
            builder2.screenPref = preferenceScreen;
            this.mNfcEnabler = new NfcEnabler(builder2);
        }
        populateViewForOrientation(this.mExplainLayout);
        setAutoRemoveInsetCategory(false);
        SecInsetCategoryPreference secInsetCategoryPreference4 =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference4.setHeight(0);
        secInsetCategoryPreference4.seslSetSubheaderRoundedBackground(12);
        getPreferenceScreen().addPreference(secInsetCategoryPreference4);
        SecInsetCategoryPreference secInsetCategoryPreference5 =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference5.setHeight(1);
        secInsetCategoryPreference5.seslSetSubheaderRoundedBackground(0);
        LayoutPreference layoutPreference = this.mExplainLayout;
        if (layoutPreference != null) {
            secInsetCategoryPreference5.setOrder(layoutPreference.getOrder() - 1);
        }
        getPreferenceScreen().addPreference(secInsetCategoryPreference5);
        NfcEnabler nfcEnabler = this.mNfcEnabler;
        if (nfcEnabler != null) {
            nfcEnabler.resume();
        }
        if (Rune.isSupportEUiccSwp(this.mContext)) {
            this.mNfcPreferences.registerOnSharedPreferenceChangeListener(
                    this.sharedPreferenceChangeListener);
            this.mActivity.registerReceiverAsUser(
                    this.mEUiccReceiver, UserHandle.ALL, this.mEUiccIntentFilter, null, null);
            PaymentDropDownPreference paymentDropDownPreference = this.mPaymentSim;
            if (paymentDropDownPreference != null) {
                paymentDropDownPreference.dismissListIfShowing();
            }
            PaymentDropDownPreference paymentDropDownPreference2 = this.mForwardingPref.paymentSim;
            if (paymentDropDownPreference2 != null) {
                paymentDropDownPreference2.dismissListIfShowing();
            }
        }
        if (SemEmergencyManager.getInstance(this.mContext) == null || !this.mIsEmergencyMode) {
            return;
        }
        this.mIsEmergencyMode = SemEmergencyManager.isEmergencyMode(this.mContext);
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this.mActivity, 5);
        if (Settings.System.getInt(getContentResolver(), "ultra_powersaving_mode", 0) == 1) {
            builder3.setTitle(R.string.nfc_show_alert_dialog_title);
        } else {
            builder3.setTitle(R.string.emergency_mode_title);
        }
        builder3.setMessage(
                this.mActivity
                        .getResources()
                        .getString(R.string.nfc_show_alert_dialog_message, "NFC"));
        builder3.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        builder3.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.nfc.NfcSettings.3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        FragmentActivity fragmentActivity;
                        NfcSettings nfcSettings = NfcSettings.this;
                        if (!nfcSettings.mIsEmergencyMode
                                || (fragmentActivity = nfcSettings.mActivity) == null) {
                            return;
                        }
                        fragmentActivity.finish();
                    }
                });
        AlertDialog create = builder3.create();
        this.mAlertDialog = create;
        create.show();
    }

    public final void populateViewForOrientation(LayoutPreference layoutPreference) {
        LinearLayout linearLayout;
        if (layoutPreference == null) {
            if (DBG) {
                Log.e("NfcSettings", "layoutPref is null");
                return;
            }
            return;
        }
        LinearLayout linearLayout2 =
                (LinearLayout) layoutPreference.mRootView.findViewById(R.id.nfc_settings_vert);
        LinearLayout linearLayout3 =
                (LinearLayout) layoutPreference.mRootView.findViewById(R.id.nfc_settings_land);
        boolean z = getResources().getConfiguration().orientation == 2;
        if (z) {
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(0);
            linearLayout =
                    (LinearLayout)
                            layoutPreference.mRootView.findViewById(R.id.nfc_settings_land_layout);
        } else {
            linearLayout2.setVisibility(0);
            linearLayout3.setVisibility(8);
            linearLayout =
                    (LinearLayout)
                            layoutPreference.mRootView.findViewById(R.id.nfc_settings_vert_layout);
        }
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        NfcAntennaAnimation nfcAntennaAnimation = this.antennaAnimation;
        nfcAntennaAnimation.mLayoutPref = layoutPreference;
        nfcAntennaAnimation.device_jpn_model_land =
                (ImageView) layoutPreference.mRootView.findViewById(R.id.device_jpn_model_land);
        nfcAntennaAnimation.device_jpn_model_vert =
                (ImageView)
                        nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                R.id.device_jpn_model_vert);
        nfcAntennaAnimation.jpnVertAnimation =
                (LottieAnimationView)
                        nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                R.id.felica_antenna_vert);
        nfcAntennaAnimation.jpnLandAnimation =
                (LottieAnimationView)
                        nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                R.id.felica_antenna_land);
        nfcAntennaAnimation.animation =
                (LottieAnimationView)
                        nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                R.id.antenna_guide_image_land);
        nfcAntennaAnimation.isJpn =
                nfcAntennaAnimation
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("com.samsung.android.nfc.gpfelica");
        boolean z2 = nfcAntennaAnimation.isJpnPos;
        if (z) {
            nfcAntennaAnimation.tv =
                    (TextView)
                            nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                    R.id.nfc_settings_land_desc);
            if (nfcAntennaAnimation.isJpn && z2) {
                nfcAntennaAnimation.iv =
                        (LottieAnimationView)
                                nfcAntennaAnimation.mLayoutPref.mRootView.findViewById(
                                        R.id.felica_antenna_land);
                nfcAntennaAnimation.device_jpn_model_land.setVisibility(0);
                nfcAntennaAnimation.device_jpn_model_vert.setVisibility(8);
                nfcAntennaAnimation.jpnLandAnimation.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.nfc.NfcAntennaAnimation.1
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ NfcAntennaAnimation this$0;

                            public /* synthetic */ AnonymousClass1(
                                    NfcAntennaAnimation nfcAntennaAnimation2, int i) {
                                r2 = i;
                                r1 = nfcAntennaAnimation2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (r2) {
                                    case 0:
                                        r1.jpnAnimationDimension =
                                                r0.jpnLandAnimation.getMeasuredWidth() / 2.0f;
                                        NfcAntennaAnimation nfcAntennaAnimation2 = r1;
                                        ImageView imageView =
                                                nfcAntennaAnimation2.device_jpn_model_land;
                                        int i = nfcAntennaAnimation2.deviceType;
                                        imageView.setImageResource(
                                                i == 0
                                                        ? R.drawable.nfc_felica_phone
                                                        : i == 1
                                                                ? R.drawable.nfc_felica_flip
                                                                : R.drawable.nfc_felica_fold);
                                        imageView.post(
                                                new Runnable() { // from class:
                                                                 // com.samsung.android.settings.nfc.NfcAntennaAnimation.3
                                                    public final /* synthetic */ ImageView
                                                            val$deviceImg;

                                                    public AnonymousClass3(ImageView imageView2) {
                                                        r2 = imageView2;
                                                    }

                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        int width = r2.getWidth();
                                                        int height = r2.getHeight();
                                                        NfcAntennaAnimation nfcAntennaAnimation3 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation3.iv.setX(
                                                                ((width
                                                                                        / nfcAntennaAnimation3
                                                                                                .deviceImgWidth)
                                                                                * nfcAntennaAnimation3
                                                                                        .felicaAntennaX)
                                                                        - nfcAntennaAnimation3
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation nfcAntennaAnimation4 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation4.iv.setY(
                                                                ((height
                                                                                        / nfcAntennaAnimation3
                                                                                                .deviceImgHeight)
                                                                                * nfcAntennaAnimation3
                                                                                        .felicaAntennaY)
                                                                        - nfcAntennaAnimation4
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation.this.iv.setAnimation(
                                                                R.raw.felica);
                                                        NfcAntennaAnimation.this.iv
                                                                .playAnimation$1();
                                                    }
                                                });
                                        break;
                                    default:
                                        r1.jpnAnimationDimension =
                                                r0.jpnVertAnimation.getMeasuredWidth() / 2.0f;
                                        NfcAntennaAnimation nfcAntennaAnimation3 = r1;
                                        ImageView imageView2 =
                                                nfcAntennaAnimation3.device_jpn_model_vert;
                                        int i2 = nfcAntennaAnimation3.deviceType;
                                        imageView2.setImageResource(
                                                i2 == 0
                                                        ? R.drawable.nfc_felica_phone
                                                        : i2 == 1
                                                                ? R.drawable.nfc_felica_flip
                                                                : R.drawable.nfc_felica_fold);
                                        imageView2.post(
                                                new Runnable() { // from class:
                                                                 // com.samsung.android.settings.nfc.NfcAntennaAnimation.3
                                                    public final /* synthetic */ ImageView
                                                            val$deviceImg;

                                                    public AnonymousClass3(ImageView imageView22) {
                                                        r2 = imageView22;
                                                    }

                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        int width = r2.getWidth();
                                                        int height = r2.getHeight();
                                                        NfcAntennaAnimation nfcAntennaAnimation32 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation32.iv.setX(
                                                                ((width
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgWidth)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaX)
                                                                        - nfcAntennaAnimation32
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation nfcAntennaAnimation4 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation4.iv.setY(
                                                                ((height
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgHeight)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaY)
                                                                        - nfcAntennaAnimation4
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation.this.iv.setAnimation(
                                                                R.raw.felica);
                                                        NfcAntennaAnimation.this.iv
                                                                .playAnimation$1();
                                                    }
                                                });
                                        break;
                                }
                            }
                        });
            } else {
                nfcAntennaAnimation2.iv =
                        (LottieAnimationView)
                                nfcAntennaAnimation2.mLayoutPref.mRootView.findViewById(
                                        R.id.antenna_guide_image_land);
            }
        } else {
            nfcAntennaAnimation2.tv =
                    (TextView)
                            nfcAntennaAnimation2.mLayoutPref.mRootView.findViewById(
                                    R.id.nfc_settings_vert_desc);
            if (nfcAntennaAnimation2.isJpn && z2) {
                nfcAntennaAnimation2.iv =
                        (LottieAnimationView)
                                nfcAntennaAnimation2.mLayoutPref.mRootView.findViewById(
                                        R.id.felica_antenna_vert);
                nfcAntennaAnimation2.device_jpn_model_land.setVisibility(8);
                nfcAntennaAnimation2.device_jpn_model_vert.setVisibility(0);
                nfcAntennaAnimation2.jpnVertAnimation.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.nfc.NfcAntennaAnimation.1
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ NfcAntennaAnimation this$0;

                            public /* synthetic */ AnonymousClass1(
                                    NfcAntennaAnimation nfcAntennaAnimation2, int i) {
                                r2 = i;
                                r1 = nfcAntennaAnimation2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (r2) {
                                    case 0:
                                        r1.jpnAnimationDimension =
                                                r0.jpnLandAnimation.getMeasuredWidth() / 2.0f;
                                        NfcAntennaAnimation nfcAntennaAnimation2 = r1;
                                        ImageView imageView2 =
                                                nfcAntennaAnimation2.device_jpn_model_land;
                                        int i = nfcAntennaAnimation2.deviceType;
                                        imageView2.setImageResource(
                                                i == 0
                                                        ? R.drawable.nfc_felica_phone
                                                        : i == 1
                                                                ? R.drawable.nfc_felica_flip
                                                                : R.drawable.nfc_felica_fold);
                                        imageView2.post(
                                                new Runnable() { // from class:
                                                                 // com.samsung.android.settings.nfc.NfcAntennaAnimation.3
                                                    public final /* synthetic */ ImageView
                                                            val$deviceImg;

                                                    public AnonymousClass3(ImageView imageView22) {
                                                        r2 = imageView22;
                                                    }

                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        int width = r2.getWidth();
                                                        int height = r2.getHeight();
                                                        NfcAntennaAnimation nfcAntennaAnimation32 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation32.iv.setX(
                                                                ((width
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgWidth)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaX)
                                                                        - nfcAntennaAnimation32
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation nfcAntennaAnimation4 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation4.iv.setY(
                                                                ((height
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgHeight)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaY)
                                                                        - nfcAntennaAnimation4
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation.this.iv.setAnimation(
                                                                R.raw.felica);
                                                        NfcAntennaAnimation.this.iv
                                                                .playAnimation$1();
                                                    }
                                                });
                                        break;
                                    default:
                                        r1.jpnAnimationDimension =
                                                r0.jpnVertAnimation.getMeasuredWidth() / 2.0f;
                                        NfcAntennaAnimation nfcAntennaAnimation3 = r1;
                                        ImageView imageView22 =
                                                nfcAntennaAnimation3.device_jpn_model_vert;
                                        int i2 = nfcAntennaAnimation3.deviceType;
                                        imageView22.setImageResource(
                                                i2 == 0
                                                        ? R.drawable.nfc_felica_phone
                                                        : i2 == 1
                                                                ? R.drawable.nfc_felica_flip
                                                                : R.drawable.nfc_felica_fold);
                                        imageView22.post(
                                                new Runnable() { // from class:
                                                                 // com.samsung.android.settings.nfc.NfcAntennaAnimation.3
                                                    public final /* synthetic */ ImageView
                                                            val$deviceImg;

                                                    public AnonymousClass3(ImageView imageView222) {
                                                        r2 = imageView222;
                                                    }

                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        int width = r2.getWidth();
                                                        int height = r2.getHeight();
                                                        NfcAntennaAnimation nfcAntennaAnimation32 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation32.iv.setX(
                                                                ((width
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgWidth)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaX)
                                                                        - nfcAntennaAnimation32
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation nfcAntennaAnimation4 =
                                                                NfcAntennaAnimation.this;
                                                        nfcAntennaAnimation4.iv.setY(
                                                                ((height
                                                                                        / nfcAntennaAnimation32
                                                                                                .deviceImgHeight)
                                                                                * nfcAntennaAnimation32
                                                                                        .felicaAntennaY)
                                                                        - nfcAntennaAnimation4
                                                                                .jpnAnimationDimension);
                                                        NfcAntennaAnimation.this.iv.setAnimation(
                                                                R.raw.felica);
                                                        NfcAntennaAnimation.this.iv
                                                                .playAnimation$1();
                                                    }
                                                });
                                        break;
                                }
                            }
                        });
            } else {
                nfcAntennaAnimation2.iv =
                        (LottieAnimationView)
                                nfcAntennaAnimation2.mLayoutPref.mRootView.findViewById(
                                        R.id.antenna_guide_image_vert);
            }
        }
        if (Utils.isTablet()) {
            nfcAntennaAnimation2.tv.setText(R.string.nfc_antenna_position_explain_all_tablet);
            nfcAntennaAnimation2.tv.setContentDescription(
                    nfcAntennaAnimation2.mContext.getString(
                            R.string.nfc_antenna_position_description_tablet));
        } else {
            nfcAntennaAnimation2.tv.setText(R.string.nfc_antenna_position_explain_all);
            nfcAntennaAnimation2.tv.setContentDescription(
                    nfcAntennaAnimation2.mContext.getString(
                            R.string.nfc_antenna_position_description_phone));
        }
        NfcAntennaInfo nfcAntennaInfo =
                NfcAntennaInfo.values()[SystemProperties.getInt("ro.vendor.nfc.info.antpos", 0)];
        if (nfcAntennaAnimation2.isJpn && z2) {
            nfcAntennaAnimation2.jpnVertAnimation.setVisibility(0);
            nfcAntennaAnimation2.jpnLandAnimation.setVisibility(0);
            nfcAntennaAnimation2.animation.setVisibility(8);
        } else {
            nfcAntennaAnimation2.jpnVertAnimation.setVisibility(8);
            nfcAntennaAnimation2.jpnLandAnimation.setVisibility(8);
            nfcAntennaAnimation2.animation.setVisibility(0);
            nfcAntennaAnimation2.device_jpn_model_land.setVisibility(8);
            nfcAntennaAnimation2.device_jpn_model_vert.setVisibility(8);
            SemLog.i("NfcAntennaAnimation", "Antenna Position : " + nfcAntennaInfo.name());
            nfcAntennaAnimation2.iv.setAnimation(nfcAntennaInfo.animationJSONValue);
            nfcAntennaAnimation2.iv.playAnimation$1();
        }
        ((LinearLayout) layoutPreference.mRootView.findViewById(R.id.gpfelica_jpn_desc))
                .setVisibility(8);
    }

    public final void removePreferenceForPaymentSim() {
        this.mForwardingPref.paymentSim = null;
        if (Rune.isGpFelicaSupported(this.mContext)) {
            return;
        }
        SecInsetCategoryPreference secInsetCategoryPreference = this.mEmptyPaymentSim;
        if (secInsetCategoryPreference != null) {
            this.mScreen.removePreference(secInsetCategoryPreference);
        }
        PaymentDropDownPreference paymentDropDownPreference = this.mPaymentSim;
        if (paymentDropDownPreference != null) {
            this.mScreen.removePreference(paymentDropDownPreference);
        }
    }

    public final void updatePaymentSim() {
        this.mForwardingPref.paymentSim.dismissListIfShowing();
        if (this.mNfcAdapter.getAdapterState() == 1) {
            this.mPaymentSim.setEnabled(false);
            this.mPaymentSim.setSummary((CharSequence) null);
            return;
        }
        PaymentDropDownPreference paymentDropDownPreference = this.mPaymentSim;
        SubscriptionManager subscriptionManager =
                (SubscriptionManager)
                        paymentDropDownPreference.mContext.getSystemService(
                                "telephony_subscription_service");
        paymentDropDownPreference.mActiveSubscriptionList = new HashMap();
        for (int i = 1; i <= subscriptionManager.getAllSubscriptionInfoList().size(); i++) {
            SubscriptionInfo activeSubscriptionInfo =
                    subscriptionManager.getActiveSubscriptionInfo(i);
            if (activeSubscriptionInfo != null) {
                if (((TelephonyManager)
                                        paymentDropDownPreference.mContext.getSystemService(
                                                "phone"))
                                .getSimState(activeSubscriptionInfo.getSimSlotIndex())
                        == 5) {
                    if (activeSubscriptionInfo.isEmbedded()
                            && !paymentDropDownPreference.mHasActiveEmbeddedSim) {
                        paymentDropDownPreference.mHasActiveEmbeddedSim = true;
                    }
                    paymentDropDownPreference.mActiveSubscriptionList.put(
                            Integer.valueOf(
                                    activeSubscriptionInfo.isEmbedded()
                                            ? activeSubscriptionInfo.getSubscriptionId()
                                            : -1),
                            activeSubscriptionInfo);
                } else {
                    Log.d(
                            "PaymentDropDownPreference",
                            "sim not ready for slot == "
                                    + activeSubscriptionInfo.getSimSlotIndex());
                }
            }
        }
        Log.d(
                "PaymentDropDownPreference",
                "subscription count : " + paymentDropDownPreference.mActiveSubscriptionList.size());
        this.mPaymentSim.initSubscriptionDetails();
    }
}
