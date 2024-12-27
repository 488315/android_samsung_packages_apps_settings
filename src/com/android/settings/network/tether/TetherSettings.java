package com.android.settings.network.tether;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.TetheringManager;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.wifi.tether.WifiTetherPreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TetherSettings extends RestrictedSettingsFragment
        implements DataSaverBackend.Listener {
    static final String KEY_ENABLE_BLUETOOTH_TETHERING = "enable_bluetooth_tethering";
    static final String KEY_TETHER_PREFS_SCREEN = "tether_prefs_screen";
    static final String KEY_TETHER_PREFS_TOP_INTRO = "tether_prefs_top_intro";
    static final String KEY_USB_TETHER_SETTINGS = "usb_tether_settings";
    static final String KEY_WIFI_TETHER = "wifi_tether";
    public final HashSet mAvailableInterfaces;
    public boolean mBluetoothEnableForTether;
    public final AtomicReference mBluetoothPan;
    public TetherChangeReceiver mBluetoothStateReceiver;
    TwoStatePreference mBluetoothTether;
    public ConnectivityManager mCm;
    Context mContext;
    public DataSaverBackend mDataSaverBackend;
    public boolean mDataSaverEnabled;
    Preference mDataSaverFooter;
    public EthernetManager mEm;
    public EthernetListener mEthernetListener;
    TwoStatePreference mEthernetTether;
    public final Handler mHandler;
    public boolean mMassStorageActive;
    public final AnonymousClass1 mProfileServiceListener;
    public OnStartTetheringCallback mStartTetheringCallback;
    public TetherChangeReceiver mTetherChangeReceiver;
    TetheringManager mTm;
    public boolean mUsbConnected;
    String[] mUsbRegexs;
    RestrictedSwitchPreference mUsbTether;
    WifiTetherPreferenceController mWifiTetherPreferenceController;
    public static final boolean DEBUG = Log.isLoggable("TetheringSettings", 3);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.tether.TetherSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        /* JADX WARN: Code restructure failed: missing block: B:11:0x003b, code lost:

           if (android.app.ActivityManager.isUserAMonkey() != false) goto L14;
        */
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getNonIndexableKeys(android.content.Context r4) {
            /*
                r3 = this;
                java.util.List r3 = super.getNonIndexableKeys(r4)
                java.lang.Class<android.net.TetheringManager> r0 = android.net.TetheringManager.class
                java.lang.Object r0 = r4.getSystemService(r0)
                android.net.TetheringManager r0 = (android.net.TetheringManager) r0
                boolean r1 = com.android.settingslib.TetherUtil.isTetherAvailable(r4)
                if (r1 != 0) goto L1a
                java.lang.String r1 = "tether_prefs_screen"
                r2 = r3
                java.util.ArrayList r2 = (java.util.ArrayList) r2
                r2.add(r1)
            L1a:
                boolean r1 = com.android.settings.wifi.WifiUtils.canShowWifiHotspot(r4)
                if (r1 == 0) goto L26
                boolean r1 = com.android.settingslib.TetherUtil.isTetherAvailable(r4)
                if (r1 != 0) goto L2e
            L26:
                java.lang.String r1 = "wifi_tether"
                r2 = r3
                java.util.ArrayList r2 = (java.util.ArrayList) r2
                r2.add(r1)
            L2e:
                java.lang.String[] r1 = r0.getTetherableUsbRegexs()
                int r1 = r1.length
                if (r1 == 0) goto L3d
                java.lang.StringBuilder r1 = com.android.settings.Utils.sBuilder
                boolean r1 = android.app.ActivityManager.isUserAMonkey()
                if (r1 == 0) goto L45
            L3d:
                java.lang.String r1 = "usb_tether_settings"
                r2 = r3
                java.util.ArrayList r2 = (java.util.ArrayList) r2
                r2.add(r1)
            L45:
                java.lang.String[] r0 = r0.getTetherableBluetoothRegexs()
                int r0 = r0.length
                if (r0 == 0) goto L4d
                goto L55
            L4d:
                java.lang.String r0 = "enable_bluetooth_tethering"
                r1 = r3
                java.util.ArrayList r1 = (java.util.ArrayList) r1
                r1.add(r0)
            L55:
                java.lang.Class<android.net.EthernetManager> r0 = android.net.EthernetManager.class
                java.lang.Object r4 = r4.getSystemService(r0)
                android.net.EthernetManager r4 = (android.net.EthernetManager) r4
                if (r4 == 0) goto L60
                goto L68
            L60:
                java.lang.String r4 = "enable_ethernet_tethering"
                r0 = r3
                java.util.ArrayList r0 = (java.util.ArrayList) r0
                r0.add(r4)
            L68:
                return r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.network.tether.TetherSettings.AnonymousClass2.getNonIndexableKeys(android.content.Context):java.util.List");
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.tether_prefs;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EthernetListener implements EthernetManager.InterfaceStateListener {
        public final WeakReference mTetherSettings;

        public EthernetListener(TetherSettings tetherSettings) {
            this.mTetherSettings = new WeakReference(tetherSettings);
        }

        public final void onInterfaceStateChanged(
                String str, int i, int i2, IpConfiguration ipConfiguration) {
            TetherSettings tetherSettings = (TetherSettings) this.mTetherSettings.get();
            if (tetherSettings == null) {
                return;
            }
            if (i == 2) {
                tetherSettings.mAvailableInterfaces.add(str);
            } else {
                tetherSettings.mAvailableInterfaces.remove(str);
            }
            tetherSettings.updateBluetoothAndEthernetState();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnStartTetheringCallback
            extends ConnectivityManager.OnStartTetheringCallback {
        public final WeakReference mTetherSettings;

        public OnStartTetheringCallback(TetherSettings tetherSettings) {
            this.mTetherSettings = new WeakReference(tetherSettings);
        }

        public final void onTetheringFailed() {
            TetherSettings tetherSettings = (TetherSettings) this.mTetherSettings.get();
            if (tetherSettings != null) {
                boolean z = TetherSettings.DEBUG;
                tetherSettings.updateBluetoothAndEthernetState();
            }
        }

        public final void onTetheringStarted() {
            TetherSettings tetherSettings = (TetherSettings) this.mTetherSettings.get();
            if (tetherSettings != null) {
                boolean z = TetherSettings.DEBUG;
                tetherSettings.updateBluetoothAndEthernetState();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TetherChangeReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ TetherSettings this$0;

        public /* synthetic */ TetherChangeReceiver(TetherSettings tetherSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = tetherSettings;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    if (TetherSettings.DEBUG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "onReceive() action : ", action, "TetheringSettings");
                    }
                    if (!action.equals("android.net.conn.TETHER_STATE_CHANGED")) {
                        if (!action.equals("android.intent.action.MEDIA_SHARED")) {
                            if (!action.equals("android.intent.action.MEDIA_UNSHARED")) {
                                if (!action.equals("android.hardware.usb.action.USB_STATE")) {
                                    if (!action.equals(
                                            "android.bluetooth.adapter.action.STATE_CHANGED")) {
                                        if (action.equals(
                                                "android.bluetooth.action.TETHERING_STATE_CHANGED")) {
                                            this.this$0.updateBluetoothAndEthernetState();
                                            break;
                                        }
                                    } else {
                                        if (this.this$0.mBluetoothEnableForTether) {
                                            int intExtra =
                                                    intent.getIntExtra(
                                                            "android.bluetooth.adapter.extra.STATE",
                                                            Integer.MIN_VALUE);
                                            if (intExtra == Integer.MIN_VALUE || intExtra == 10) {
                                                this.this$0.mBluetoothEnableForTether = false;
                                            } else if (intExtra == 12) {
                                                this.this$0.startTethering$1(2);
                                                this.this$0.mBluetoothEnableForTether = false;
                                            }
                                        }
                                        this.this$0.updateBluetoothAndEthernetState();
                                        break;
                                    }
                                } else {
                                    this.this$0.mUsbConnected =
                                            intent.getBooleanExtra("connected", false);
                                    this.this$0.updateBluetoothAndEthernetState();
                                    this.this$0.updateUsbPreference();
                                    break;
                                }
                            } else {
                                TetherSettings tetherSettings = this.this$0;
                                tetherSettings.mMassStorageActive = false;
                                tetherSettings.updateBluetoothAndEthernetState();
                                this.this$0.updateUsbPreference();
                                break;
                            }
                        } else {
                            TetherSettings tetherSettings2 = this.this$0;
                            tetherSettings2.mMassStorageActive = true;
                            tetherSettings2.updateBluetoothAndEthernetState();
                            this.this$0.updateUsbPreference();
                            break;
                        }
                    } else {
                        ArrayList<String> stringArrayListExtra =
                                intent.getStringArrayListExtra("availableArray");
                        ArrayList<String> stringArrayListExtra2 =
                                intent.getStringArrayListExtra("tetherArray");
                        this.this$0.updateBluetoothState$1();
                        this.this$0.updateEthernetState(
                                (String[])
                                        stringArrayListExtra.toArray(
                                                new String[stringArrayListExtra.size()]),
                                (String[])
                                        stringArrayListExtra2.toArray(
                                                new String[stringArrayListExtra2.size()]));
                        break;
                    }
                    break;
                default:
                    String action2 = intent.getAction();
                    Log.i("TetheringSettings", "onReceive: action: " + action2);
                    if (TextUtils.equals(
                            action2, "android.bluetooth.adapter.action.STATE_CHANGED")) {
                        int intExtra2 =
                                intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                        Log.i(
                                "TetheringSettings",
                                "onReceive: state: " + BluetoothAdapter.nameForState(intExtra2));
                        BluetoothProfile bluetoothProfile =
                                (BluetoothProfile) this.this$0.mBluetoothPan.get();
                        if (intExtra2 == 12) {
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (bluetoothProfile == null && defaultAdapter != null) {
                                TetherSettings tetherSettings3 = this.this$0;
                                defaultAdapter.getProfileProxy(
                                        tetherSettings3.mContext,
                                        tetherSettings3.mProfileServiceListener,
                                        5);
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.network.tether.TetherSettings$1] */
    public TetherSettings() {
        super("no_config_tethering");
        this.mBluetoothPan = new AtomicReference();
        this.mHandler = new Handler();
        this.mAvailableInterfaces = new HashSet();
        this.mProfileServiceListener =
                new BluetoothProfile
                        .ServiceListener() { // from class:
                                             // com.android.settings.network.tether.TetherSettings.1
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                        if (TetherSettings.this.mBluetoothPan.get() == null) {
                            TetherSettings.this.mBluetoothPan.set((BluetoothPan) bluetoothProfile);
                            TetherSettings.this.updateBluetoothState$1();
                        }
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceDisconnected(int i) {}
                };
    }

    public int getBluetoothState() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return Integer.MIN_VALUE;
        }
        return defaultAdapter.getState();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 90;
    }

    public boolean isBluetoothTetheringOn() {
        BluetoothPan bluetoothPan = (BluetoothPan) this.mBluetoothPan.get();
        return bluetoothPan != null && bluetoothPan.isTetheringOn();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ad, code lost:

       if (android.app.ActivityManager.isUserAMonkey() != false) goto L27;
    */
    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r6) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.tether.TetherSettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {
        this.mDataSaverEnabled = z;
        this.mWifiTetherPreferenceController.setDataSaverEnabled(z);
        this.mUsbTether.setEnabled(!this.mDataSaverEnabled);
        this.mBluetoothTether.setEnabled(!this.mDataSaverEnabled);
        this.mEthernetTether.setEnabled(!this.mDataSaverEnabled);
        this.mDataSaverFooter.setVisible(this.mDataSaverEnabled);
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (isUiRestricted()) {
            super.onDestroy();
            return;
        }
        this.mDataSaverBackend.remListener(this);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothProfile bluetoothProfile = (BluetoothProfile) this.mBluetoothPan.getAndSet(null);
        if (bluetoothProfile != null && defaultAdapter != null) {
            defaultAdapter.closeProfileProxy(5, bluetoothProfile);
        }
        TetherChangeReceiver tetherChangeReceiver = this.mBluetoothStateReceiver;
        if (tetherChangeReceiver != null) {
            this.mContext.unregisterReceiver(tetherChangeReceiver);
            this.mBluetoothStateReceiver = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        RestrictedSwitchPreference restrictedSwitchPreference = this.mUsbTether;
        if (preference != restrictedSwitchPreference) {
            TwoStatePreference twoStatePreference = this.mBluetoothTether;
            if (preference != twoStatePreference) {
                TwoStatePreference twoStatePreference2 = this.mEthernetTether;
                if (preference == twoStatePreference2) {
                    if (twoStatePreference2.isChecked()) {
                        startTethering$1(5);
                    } else {
                        this.mCm.stopTethering(5);
                    }
                }
            } else if (twoStatePreference.isChecked()) {
                startTethering$1(2);
            } else {
                this.mCm.stopTethering(2);
            }
        } else if (restrictedSwitchPreference.mChecked) {
            startTethering$1(1);
        } else {
            this.mCm.stopTethering(1);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (isUiRestricted()) {
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(R.string.tethering_settings_not_available);
            }
            getPreferenceScreen().removeAll();
            return;
        }
        this.mStartTetheringCallback = new OnStartTetheringCallback(this);
        this.mMassStorageActive = "shared".equals(Environment.getExternalStorageState());
        registerReceiver();
        this.mEthernetListener = new EthernetListener(this);
        EthernetManager ethernetManager = this.mEm;
        if (ethernetManager != null) {
            ethernetManager.addInterfaceStateListener(
                    this.mContext.getApplicationContext().getMainExecutor(),
                    this.mEthernetListener);
        }
        updateUsbState(this.mTm.getTetheredIfaces());
        updateBluetoothAndEthernetState();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (isUiRestricted()) {
            return;
        }
        getActivity().unregisterReceiver(this.mTetherChangeReceiver);
        EthernetManager ethernetManager = this.mEm;
        if (ethernetManager != null) {
            ethernetManager.removeInterfaceStateListener(this.mEthernetListener);
        }
        this.mTetherChangeReceiver = null;
        this.mStartTetheringCallback = null;
    }

    public void registerReceiver() {
        FragmentActivity activity = getActivity();
        this.mTetherChangeReceiver = new TetherChangeReceiver(this, 0);
        Intent registerReceiver =
                activity.registerReceiver(
                        this.mTetherChangeReceiver,
                        new IntentFilter("android.net.conn.TETHER_STATE_CHANGED"));
        activity.registerReceiver(
                this.mTetherChangeReceiver,
                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                        "android.hardware.usb.action.USB_STATE"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_SHARED");
        intentFilter.addAction("android.intent.action.MEDIA_UNSHARED");
        intentFilter.addDataScheme("file");
        activity.registerReceiver(this.mTetherChangeReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.action.TETHERING_STATE_CHANGED");
        activity.registerReceiver(this.mTetherChangeReceiver, intentFilter2);
        if (registerReceiver != null) {
            this.mTetherChangeReceiver.onReceive(activity, registerReceiver);
        }
    }

    public void setTopIntroPreferenceTitle() {
        Preference findPreference = findPreference(KEY_TETHER_PREFS_TOP_INTRO);
        if (((WifiManager) this.mContext.getSystemService(WifiManager.class))
                .isStaApConcurrencySupported()) {
            findPreference.setTitle(R.string.tethering_footer_info_sta_ap_concurrency);
        } else {
            findPreference.setTitle(R.string.tethering_footer_info);
        }
    }

    public void setupTetherPreference() {
        this.mUsbTether = (RestrictedSwitchPreference) findPreference(KEY_USB_TETHER_SETTINGS);
        this.mBluetoothTether = (TwoStatePreference) findPreference(KEY_ENABLE_BLUETOOTH_TETHERING);
        this.mEthernetTether = (TwoStatePreference) findPreference("enable_ethernet_tethering");
    }

    public void setupViewModel() {
        boolean z;
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(TetheringManagerModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        TetheringManagerModel tetheringManagerModel =
                (TetheringManagerModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        Context context = getContext();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        WifiManager wifiManager =
                (WifiManager) context.getApplicationContext().getSystemService(WifiManager.class);
        if (WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(context, "no_wifi_tethering")) {
            Log.w("WifiEntResUtils", "Wi-Fi Tethering isn't available due to user restriction.");
            z = false;
        } else {
            z = true;
        }
        this.mWifiTetherPreferenceController =
                new WifiTetherPreferenceController(
                        context, settingsLifecycle, wifiManager, true, z, tetheringManagerModel);
        this.mTm = tetheringManagerModel.mTetheringManager;
        Transformations.distinctUntilChanged(tetheringManagerModel.mTetheredInterfaces)
                .observe(
                        this,
                        new Observer() { // from class:
                                         // com.android.settings.network.tether.TetherSettings$$ExternalSyntheticLambda0
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                List list = (List) obj;
                                TetherSettings tetherSettings = TetherSettings.this;
                                tetherSettings.getClass();
                                Log.d(
                                        "TetheringSettings",
                                        "onTetheredInterfacesChanged() interfaces : "
                                                + list.toString());
                                String[] strArr = (String[]) list.toArray(new String[list.size()]);
                                tetherSettings.updateUsbState(strArr);
                                String[] tetherableIfaces =
                                        tetherSettings.mTm.getTetherableIfaces();
                                tetherSettings.updateBluetoothState$1();
                                tetherSettings.updateEthernetState(tetherableIfaces, strArr);
                            }
                        });
    }

    public final void startTethering$1(int i) {
        if (i == 2) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter.getState() == 10) {
                this.mBluetoothEnableForTether = true;
                defaultAdapter.enable();
                this.mBluetoothTether.setEnabled(false);
                return;
            }
        }
        this.mCm.startTethering(i, true, this.mStartTetheringCallback, this.mHandler);
    }

    public final void updateBluetoothAndEthernetState() {
        String[] tetheredIfaces = this.mTm.getTetheredIfaces();
        String[] tetherableIfaces = this.mTm.getTetherableIfaces();
        updateBluetoothState$1();
        updateEthernetState(tetherableIfaces, tetheredIfaces);
    }

    public final void updateBluetoothState$1() {
        int bluetoothState = getBluetoothState();
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    bluetoothState, "updateBluetoothState() btState : ", "TetheringSettings");
        }
        if (bluetoothState == Integer.MIN_VALUE) {
            Log.w("TetheringSettings", "updateBluetoothState() Bluetooth state is error!");
            return;
        }
        if (bluetoothState == 13) {
            this.mBluetoothTether.setEnabled(false);
            return;
        }
        if (bluetoothState == 11) {
            this.mBluetoothTether.setEnabled(false);
            return;
        }
        if (bluetoothState == 12 && isBluetoothTetheringOn()) {
            this.mBluetoothTether.setChecked(true);
            this.mBluetoothTether.setEnabled(!this.mDataSaverEnabled);
        } else {
            this.mBluetoothTether.setEnabled(!this.mDataSaverEnabled);
            this.mBluetoothTether.setChecked(false);
        }
    }

    public void updateEthernetState(String[] strArr, String[] strArr2) {
        boolean z = false;
        for (String str : strArr) {
            if (this.mAvailableInterfaces.contains(str)) {
                z = true;
            }
        }
        boolean z2 = false;
        for (String str2 : strArr2) {
            if (this.mAvailableInterfaces.contains(str2)) {
                z2 = true;
            }
        }
        if (DEBUG) {
            Utils$$ExternalSyntheticOutline0.m653m(
                    "updateEthernetState() isAvailable : ",
                    z,
                    ", isTethered : ",
                    z2,
                    "TetheringSettings");
        }
        if (z2) {
            this.mEthernetTether.setEnabled(!this.mDataSaverEnabled);
            this.mEthernetTether.setChecked(true);
        } else if (this.mAvailableInterfaces.size() > 0) {
            this.mEthernetTether.setEnabled(!this.mDataSaverEnabled);
            this.mEthernetTether.setChecked(false);
        } else {
            this.mEthernetTether.setEnabled(false);
            this.mEthernetTether.setChecked(false);
        }
    }

    public final void updateUsbPreference() {
        boolean z = this.mUsbConnected && !this.mMassStorageActive;
        RestrictedLockUtils.EnforcedAdmin checkIfUsbDataSignalingIsDisabled =
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId());
        if (checkIfUsbDataSignalingIsDisabled != null) {
            this.mUsbTether.setDisabledByAdmin(checkIfUsbDataSignalingIsDisabled);
        } else if (z) {
            this.mUsbTether.setEnabled(!this.mDataSaverEnabled);
        } else {
            this.mUsbTether.setEnabled(false);
        }
    }

    public void updateUsbState(String[] strArr) {
        boolean z = false;
        for (String str : strArr) {
            for (String str2 : this.mUsbRegexs) {
                if (str.matches(str2)) {
                    z = true;
                }
            }
        }
        if (DEBUG) {
            Log.d(
                    "TetheringSettings",
                    "updateUsbState() mUsbConnected : "
                            + this.mUsbConnected
                            + ", mMassStorageActive : "
                            + this.mMassStorageActive
                            + ", usbTethered : "
                            + z);
        }
        if (!z) {
            this.mUsbTether.setChecked(false);
            updateUsbPreference();
            return;
        }
        this.mUsbTether.setEnabled(!this.mDataSaverEnabled);
        this.mUsbTether.setChecked(true);
        RestrictedLockUtils.EnforcedAdmin checkIfUsbDataSignalingIsDisabled =
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId());
        if (checkIfUsbDataSignalingIsDisabled != null) {
            this.mUsbTether.setDisabledByAdmin(checkIfUsbDataSignalingIsDisabled);
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {}
}
