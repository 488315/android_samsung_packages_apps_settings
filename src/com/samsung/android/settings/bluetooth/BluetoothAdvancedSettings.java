package com.samsung.android.settings.bluetooth;

import android.R;
import android.accounts.Account;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothAdvancedSettings extends DashboardFragment
        implements Preference.OnPreferenceChangeListener, BluetoothCallback {
    public SwitchPreferenceCompat mAskToUsePreference;
    public SecPreference mCloudNonSwitchPreference;
    public SwitchPreferenceCompat mCloudSwitchPreference;
    public Context mContext;
    public SecPreference mControlHistoryPreference;
    public SwitchPreferenceCompat mIBRPreference;
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public LocalBluetoothManager mLocalBluetoothManager;
    public SecPreference mReceivedFilesPreference;
    public SecPreference mRenamePreference;
    public SecPreference mScanActivityPreference;
    public String mScreenId;
    public static final List mAdvancedDeltaTargets =
            new ArrayList(Arrays.asList("SPR", "VMU", "BST", "XAS", "VZW"));
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass4();
    public boolean mIsDisableSyncMenu = false;
    public boolean mIsScloudInstalled = false;
    public boolean mIsSCMenuUpdated = false;
    public boolean mIsSCSupportV2 = false;
    public SamsungCloudRPCSettingV2 mSamsungCloudRPCSettingV2 = null;
    public boolean mReceiverRegistered = false;
    public final AnonymousClass1 mIBRDialogClickListener =
            new DialogInterface.OnClickListener() { // from class:
                // com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -2) {
                        Log.i(
                                "BluetoothAdvancedSettings",
                                "AlertDialog OnClickListener :: Negative ");
                        dialogInterface.dismiss();
                        return;
                    }
                    if (i != -1) {
                        return;
                    }
                    Log.i("BluetoothAdvancedSettings", "AlertDialog OnClickListener :: Positive ");
                    int state =
                            BluetoothAdvancedSettings.this.mLocalBluetoothAdapter.mAdapter
                                    .getState();
                    if ((state == 11 || state == 12)
                            && BluetoothAdvancedSettings.this.mLocalBluetoothAdapter.mAdapter
                                    .disable()) {
                        SharedPreferences.Editor edit =
                                BluetoothAdvancedSettings.this
                                        .getContext()
                                        .getSharedPreferences("bluetooth_restart", 0)
                                        .edit();
                        edit.putBoolean("key_bluetooth_restart", true);
                        edit.apply();
                    }
                    BluetoothAdvancedSettings.this.getClass();
                    SystemProperties.set(
                            "persist.bluetooth.disableinbandringing",
                            BluetoothAdvancedSettings.isInBandRingtoneEnabled() ? "true" : "false");
                    BluetoothAdvancedSettings.this.getClass();
                    boolean isInBandRingtoneEnabled =
                            BluetoothAdvancedSettings.isInBandRingtoneEnabled();
                    Intent intent =
                            new Intent(
                                    "com.samsung.bt.hfp.intent.action.INBAND_RINGTONE_STATE_CHANGED");
                    intent.putExtra(
                            "com.samsung.bt.hfp.intent.extra.INBAND_RINGTONE_STATE",
                            isInBandRingtoneEnabled);
                    intent.addFlags(16777216);
                    BluetoothAdvancedSettings.this
                            .getContext()
                            .sendBroadcastAsUser(
                                    intent, UserHandle.ALL, "android.permission.BLUETOOTH_CONNECT");
                    dialogInterface.dismiss();
                }
            };
    public final AnonymousClass2 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String string;
                    String action = intent.getAction();
                    if (action == null) {
                        Log.d(
                                "BluetoothAdvancedSettings",
                                "onReceive :: Intent.getAction() is return null");
                        return;
                    }
                    Log.d("BluetoothAdvancedSettings", "onReceive :: getAction = ".concat(action));
                    if ("com.android.settings.DEVICE_NAME_CHANGED".equals(action)) {
                        BluetoothAdvancedSettings bluetoothAdvancedSettings =
                                BluetoothAdvancedSettings.this;
                        if (bluetoothAdvancedSettings.mLocalBluetoothAdapter == null
                                || bluetoothAdvancedSettings.mRenamePreference == null
                                || (string =
                                                Settings.Global.getString(
                                                        bluetoothAdvancedSettings
                                                                .getActivity()
                                                                .getContentResolver(),
                                                        "device_name"))
                                        == null) {
                            return;
                        }
                        bluetoothAdvancedSettings.mRenamePreference.setSummary(string);
                        return;
                    }
                    if ("com.android.sync.SYNC_CONN_STATUS_CHANGED".equals(action)) {
                        BluetoothAdvancedSettings bluetoothAdvancedSettings2 =
                                BluetoothAdvancedSettings.this;
                        if (bluetoothAdvancedSettings2.mIsSCSupportV2) {
                            SecPreference secPreference =
                                    bluetoothAdvancedSettings2.mCloudNonSwitchPreference;
                            if (secPreference != null) {
                                secPreference.setOnPreferenceChangeListener(null);
                            }
                            BluetoothAdvancedSettings bluetoothAdvancedSettings3 =
                                    BluetoothAdvancedSettings.this;
                            bluetoothAdvancedSettings3.mIsSCMenuUpdated = false;
                            bluetoothAdvancedSettings3.new AnonymousClass5().execute(new Void[0]);
                        }
                    }
                }
            };
    public final AnonymousClass6 mCloudStateChange =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings.6
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    Log.d("BluetoothAdvancedSettings", "contentObserver_CloudStateChanged");
                    BluetoothAdvancedSettings.m1125$$Nest$mupdateCloudPreference(
                            BluetoothAdvancedSettings.this);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new BluetoothCastPreferenceController(context, "key_bluetooth_cast"));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            String str = SystemProperties.get("persist.bluetooth.enableinbandringing");
            boolean z = context.getResources().getBoolean(R.bool.config_windowNoTitleDefault);
            boolean isSupportSoftPhone = BluetoothIBRSettings.isSupportSoftPhone();
            Log.d(
                    "BluetoothAdvancedSettings",
                    "getNonIndexableKeys:: isSupportIBR - "
                            + str
                            + " , isVoiceCapable - "
                            + z
                            + " , isSupportSoftphone - "
                            + isSupportSoftPhone);
            if (ApnSettings.MVNO_NONE.equals(str)) {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_ibr");
            } else if (!z && !isSupportSoftPhone) {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_ibr");
            }
            if (!SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_cast");
            }
            if (!BluetoothAdvancedSettings.isUseDeltaOptionMenu()) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("key_bluetooth_rename");
                arrayList.add("key_bluetooth_received_files");
            }
            if (!Rune.isChinaModel()) {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_ask_to_use");
            }
            if (BluetoothAdvancedSettings.isDisableSyncOptionMenu(context)) {
                ArrayList arrayList2 = (ArrayList) nonIndexableKeys;
                arrayList2.add("key_bluetooth_cloud_none_switch");
                arrayList2.add("key_bluetooth_cloud_switch");
            } else if (Utils.getSamsungAccount(context) != null) {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_cloud_none_switch");
            } else {
                ((ArrayList) nonIndexableKeys).add("key_bluetooth_cloud_switch");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            String salesCode = com.android.settings.Utils.getSalesCode();
            int i =
                    ("VZW".equals(salesCode) || "VPP".equals(salesCode))
                            ? com.android.settings.R.xml.sec_bluetooth_advanced_settings_vzw
                            : com.android.settings.R.xml.sec_bluetooth_advanced_settings;
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = i;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            boolean z =
                    !SystemProperties.getBoolean("persist.bluetooth.disableinbandringing", false);
            String valueOf = String.valueOf(20101);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            return Arrays.asList(statusData);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends AsyncTask {
        public AnonymousClass5() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2;
            Log.w(
                    "BluetoothAdvancedSettings",
                    "asyncUpdateCloudPreference() SC support V2 : "
                            + BluetoothAdvancedSettings.this.mIsSCSupportV2
                            + ", mIsSCMenuUpdated : "
                            + BluetoothAdvancedSettings.this.mIsSCMenuUpdated);
            BluetoothAdvancedSettings bluetoothAdvancedSettings = BluetoothAdvancedSettings.this;
            if (!bluetoothAdvancedSettings.mIsSCMenuUpdated
                    && (samsungCloudRPCSettingV2 =
                                    bluetoothAdvancedSettings.mSamsungCloudRPCSettingV2)
                            != null) {
                Bundle profile = samsungCloudRPCSettingV2.iSyncSetting.getProfile();
                if (profile == null) {
                    Log.d(
                            "BluetoothAdvancedSettings",
                            "mSamsungCloudRPCSettingV2.getProfile() return null");
                    return 4194304;
                }
                int i = profile.getInt("precondition");
                Log.w("BluetoothAdvancedSettings", "preCondition : " + i);
                return Integer.valueOf(i);
            }
            return 4194304;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Integer num = (Integer) obj;
            Log.d("BluetoothAdvancedSettings", "onPostExecute");
            BluetoothAdvancedSettings bluetoothAdvancedSettings = BluetoothAdvancedSettings.this;
            List list = BluetoothAdvancedSettings.mAdvancedDeltaTargets;
            if (bluetoothAdvancedSettings.isFinishingOrDestroyed()) {
                Log.d("BluetoothAdvancedSettings", "isFinishingOrDestroyed return");
                return;
            }
            BluetoothAdvancedSettings bluetoothAdvancedSettings2 = BluetoothAdvancedSettings.this;
            if (bluetoothAdvancedSettings2.mIsSCMenuUpdated) {
                Log.d("BluetoothAdvancedSettings", "mIsSCMenuUpdated is true");
            } else if (num == null) {
                Log.d("BluetoothAdvancedSettings", "preCondition is null");
            } else {
                BluetoothAdvancedSettings.m1125$$Nest$mupdateCloudPreference(
                        bluetoothAdvancedSettings2);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateCloudPreference, reason: not valid java name */
    public static void m1125$$Nest$mupdateCloudPreference(
            BluetoothAdvancedSettings bluetoothAdvancedSettings) {
        SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2 =
                bluetoothAdvancedSettings.mSamsungCloudRPCSettingV2;
        if (samsungCloudRPCSettingV2 == null) {
            Log.d("BluetoothAdvancedSettings", "mSamsungCloudRPCSettingV2 is null");
            return;
        }
        if (samsungCloudRPCSettingV2.iSyncSetting.getProfile() == null) {
            Log.d("BluetoothAdvancedSettings", "mSamsungCloudRPCSettingV2.getProfile() is null");
            return;
        }
        int i =
                bluetoothAdvancedSettings
                        .mSamsungCloudRPCSettingV2
                        .iSyncSetting
                        .getProfile()
                        .getInt("precondition");
        boolean masterSyncAutomatically = ContentResolver.getMasterSyncAutomatically();
        Log.d(
                "BluetoothAdvancedSettings",
                "get sc preCondition : " + i + ", isMasterSyncOn : " + masterSyncAutomatically);
        SecPreference secPreference = bluetoothAdvancedSettings.mCloudNonSwitchPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, false);
        boolean hasFlags =
                ((UserManager) bluetoothAdvancedSettings.mContext.getSystemService("user"))
                        .semGetSemUserInfo(UserHandle.semGetMyUserId())
                        .hasFlags(16777216);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isDigitalLegacyMode :", "BluetoothAdvancedSettings", hasFlags);
        if (hasFlags) {
            bluetoothAdvancedSettings
                    .getPreferenceScreen()
                    .removePreference(bluetoothAdvancedSettings.mCloudSwitchPreference);
            bluetoothAdvancedSettings
                    .getPreferenceScreen()
                    .addPreference(bluetoothAdvancedSettings.mCloudNonSwitchPreference);
            bluetoothAdvancedSettings.mCloudNonSwitchPreference.setEnabled(false);
            bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                    com.android.settings.R.string
                            .sec_bluetooth_advanced_digital_legacy_mode_summary);
        } else {
            if (i != 0 || !masterSyncAutomatically) {
                if (bluetoothAdvancedSettings
                                .getPreferenceScreen()
                                .findPreference("key_bluetooth_cloud_switch")
                        != null) {
                    bluetoothAdvancedSettings.mCloudSwitchPreference.setOnPreferenceChangeListener(
                            null);
                    bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .removePreference(bluetoothAdvancedSettings.mCloudSwitchPreference);
                }
                if (bluetoothAdvancedSettings
                                .getPreferenceScreen()
                                .findPreference("key_bluetooth_cloud_none_switch")
                        == null) {
                    bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .addPreference(bluetoothAdvancedSettings.mCloudNonSwitchPreference);
                }
                boolean isChinaModel = Rune.isChinaModel();
                if ((i & 1) == 1) {
                    Log.d("BluetoothAdvancedSettings", "SA not logged");
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            bluetoothAdvancedSettings.getString(
                                    com.android.settings.R.string
                                            .sec_bluetooth_advanced_sign_in_sacount_to_sync,
                                    bluetoothAdvancedSettings.getString(
                                            com.android.settings.R.string.bluetooth_settings)));
                    return;
                }
                if (i == 768) {
                    bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .removePreference(bluetoothAdvancedSettings.mCloudSwitchPreference);
                    bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .addPreference(bluetoothAdvancedSettings.mCloudNonSwitchPreference);
                    if (com.android.settings.Utils.isTablet()) {
                        bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                                bluetoothAdvancedSettings.getString(
                                        com.android.settings.R.string
                                                .sec_bluetooth_advanced_sync_with_cloud_encryption_tablet));
                    } else {
                        bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                                bluetoothAdvancedSettings.getString(
                                        com.android.settings.R.string
                                                .sec_bluetooth_advanced_sync_with_cloud_encryption_phone));
                    }
                    SecPreference secPreference2 =
                            bluetoothAdvancedSettings.mCloudNonSwitchPreference;
                    secPreference2.getClass();
                    SecPreferenceUtils.applySummaryColor(secPreference2, true);
                    return;
                }
                if (isChinaModel && (i & 4096) == 4096) {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            com.android.settings.R.string
                                    .sec_bluetooth_advanced_sync_no_consent_to_use_network_chn);
                    return;
                }
                if (isChinaModel && (i & 256) == 256) {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            com.android.settings.R.string.sec_bluetooth_advanced_scloud_personal);
                    return;
                }
                if ((i & 16) == 16) {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            com.android.settings.R.string.sec_bluetooth_advanced_scloud_personal);
                    return;
                }
                if (!masterSyncAutomatically) {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            com.android.settings.R.string.sec_bluetooth_advanced_sync_turned_off);
                    return;
                } else if ((i & 512) == 512) {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            com.android.settings.R.string
                                    .sec_bluetooth_advanced_sync_no_privacy_notice_chn);
                    return;
                } else {
                    bluetoothAdvancedSettings.mCloudNonSwitchPreference.setSummary(
                            ApnSettings.MVNO_NONE);
                    return;
                }
            }
            if (bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .findPreference("key_bluetooth_cloud_none_switch")
                    != null) {
                bluetoothAdvancedSettings
                        .getPreferenceScreen()
                        .removePreference(bluetoothAdvancedSettings.mCloudNonSwitchPreference);
            }
            if (bluetoothAdvancedSettings
                            .getPreferenceScreen()
                            .findPreference("key_bluetooth_cloud_switch")
                    == null) {
                bluetoothAdvancedSettings
                        .getPreferenceScreen()
                        .addPreference(bluetoothAdvancedSettings.mCloudSwitchPreference);
            }
            boolean autoSync = bluetoothAdvancedSettings.mSamsungCloudRPCSettingV2.getAutoSync();
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "getAutoSync :", "BluetoothAdvancedSettings", autoSync);
            bluetoothAdvancedSettings.mCloudSwitchPreference.setChecked(autoSync);
            SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                    (SecSwitchPreferenceScreen) bluetoothAdvancedSettings.mCloudSwitchPreference;
            secSwitchPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, autoSync);
            if (autoSync) {
                bluetoothAdvancedSettings.mCloudSwitchPreference.setSummary(
                        Utils.getSamsungAccount(bluetoothAdvancedSettings.getContext()).name);
            } else {
                bluetoothAdvancedSettings.mCloudSwitchPreference.setSummary(
                        com.android.settings.R.string.sec_bluetooth_advanced_auto_sync_disabled);
            }
            bluetoothAdvancedSettings.mCloudSwitchPreference.setOnPreferenceChangeListener(
                    bluetoothAdvancedSettings);
        }
        bluetoothAdvancedSettings.mIsSCMenuUpdated = true;
    }

    public static boolean isDisableSyncOptionMenu(Context context) {
        if (Rune.isChinaModel()) {
            return false;
        }
        boolean isEnabledUltraPowerSaving = BluetoothUtils.isEnabledUltraPowerSaving(context);
        UserManager userManager = (UserManager) context.getSystemService("user");
        return isEnabledUltraPowerSaving
                || userManager.getUserInfo(userManager.getUserHandle()).isKnoxWorkspace()
                || !com.android.settings.Utils.hasPackage(context, "com.samsung.android.scloud");
    }

    public static boolean isInBandRingtoneEnabled() {
        return !SystemProperties.getBoolean("persist.bluetooth.disableinbandringing", false);
    }

    public static boolean isUseDeltaOptionMenu() {
        String salesCode = com.android.settings.Utils.getSalesCode();
        return salesCode != null && ((ArrayList) mAdvancedDeltaTargets).contains(salesCode);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothAdvancedSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        String salesCode = com.android.settings.Utils.getSalesCode();
        return ("VZW".equals(salesCode) || "VPP".equals(salesCode))
                ? com.android.settings.R.xml.sec_bluetooth_advanced_settings_vzw
                : com.android.settings.R.xml.sec_bluetooth_advanced_settings;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        Log.d("BluetoothAdvancedSettings", "onBluetoothStateChanged :: state =" + i);
        updateSwitch$2();
        updateOppTransferHistory();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("BluetoothAdvancedSettings", "onConfigurationChanged :: ");
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        updateSwitch$2();
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0197, code lost:

       if (530002000 <= r4) goto L46;
    */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r8) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.BluetoothAdvancedSettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources()
                            .getString(
                                    com.android.settings.R.string
                                            .event_bluetooth_advanced_settings_navigate_button));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
        this.mIBRPreference.setOnPreferenceChangeListener(null);
        this.mAskToUsePreference.setOnPreferenceChangeListener(null);
        if (this.mReceiverRegistered && this.mReceiver != null) {
            getActivity().unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
        SecPreference secPreference = this.mCloudNonSwitchPreference;
        if (secPreference != null) {
            secPreference.setOnPreferenceChangeListener(null);
        }
        this.mIsSCMenuUpdated = false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue;
        booleanValue = ((Boolean) obj).booleanValue();
        Log.d(
                "BluetoothAdvancedSettings",
                "onPreferenceChange() :: preference - "
                        + preference.getKey()
                        + "isChecked - "
                        + booleanValue);
        String key = preference.getKey();
        key.getClass();
        switch (key) {
            case "key_bluetooth_ask_to_use":
                String string =
                        booleanValue
                                ? getResources()
                                        .getString(com.android.settings.R.string.detail_switch_on)
                                : getResources()
                                        .getString(com.android.settings.R.string.detail_switch_off);
                if (booleanValue) {
                    Settings.Global.putInt(
                            getActivity().getContentResolver(), "bluetooth_security_on_check", 1);
                } else {
                    Settings.Global.putInt(
                            getActivity().getContentResolver(), "bluetooth_security_on_check", 0);
                }
                SALogging.insertSALog(
                        this.mScreenId,
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_setting_china_popup_check_box),
                        string);
                Log.d(
                        "BluetoothAdvancedSettings",
                        "KEY_ASK_TO_USE - "
                                + Settings.Global.getInt(
                                        getActivity().getContentResolver(),
                                        "bluetooth_security_on_check",
                                        1));
                return true;
            case "key_bluetooth_cloud_switch":
                Account samsungAccount = Utils.getSamsungAccount(getContext());
                if (samsungAccount != null) {
                    SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2 =
                            this.mSamsungCloudRPCSettingV2;
                    if (samsungCloudRPCSettingV2 != null) {
                        samsungCloudRPCSettingV2.setAutoSync(booleanValue);
                    }
                    preference.setSummary(
                            booleanValue
                                    ? samsungAccount.name
                                    : getString(
                                            com.android.settings.R.string
                                                    .sec_bluetooth_advanced_auto_sync_disabled));
                    SecPreferenceUtils.applySummaryColor(
                            (SecSwitchPreferenceScreen) preference, booleanValue);
                    Settings.Global.putInt(
                            this.mContext.getContentResolver(),
                            "scloud_bluetooth_sync_enabled",
                            booleanValue ? 1 : 0);
                }
                return true;
            case "key_bluetooth_ibr":
                if ((booleanValue && isInBandRingtoneEnabled())
                        || (!booleanValue && !isInBandRingtoneEnabled())) {
                    Log.e(
                            "BluetoothAdvancedSettings",
                            "switchStateChange :: It is not user interaction");
                    return false;
                }
                int connectionState = this.mLocalBluetoothAdapter.mAdapter.getConnectionState();
                if (connectionState == 2 || connectionState == 1) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(
                                    getResources()
                                            .getString(
                                                    com.android.settings.R.string
                                                            .sec_bluetooth_ibr_ask_popup_title))
                            .setMessage(
                                    getResources()
                                            .getString(
                                                    com.android.settings.R.string
                                                            .sec_bluetooth_ibr_ask_popup_content))
                            .setPositiveButton(
                                    getResources()
                                            .getString(com.android.settings.R.string.common_ok),
                                    this.mIBRDialogClickListener)
                            .setNegativeButton(
                                    getResources()
                                            .getString(com.android.settings.R.string.common_cancel),
                                    this.mIBRDialogClickListener)
                            .create()
                            .show();
                    return false;
                }
                SALogging.insertSALog(
                        this.mScreenId,
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_advanced_settings_ringtone_sync_onoff),
                        booleanValue
                                ? getResources()
                                        .getString(com.android.settings.R.string.detail_switch_on)
                                : getResources()
                                        .getString(
                                                com.android.settings.R.string.detail_switch_off));
                SystemProperties.set(
                        "persist.bluetooth.disableinbandringing", !booleanValue ? "true" : "false");
                Intent intent =
                        new Intent(
                                "com.samsung.bt.hfp.intent.action.INBAND_RINGTONE_STATE_CHANGED");
                intent.putExtra(
                        "com.samsung.bt.hfp.intent.extra.INBAND_RINGTONE_STATE", booleanValue);
                intent.addFlags(16777216);
                getContext()
                        .sendBroadcastAsUser(
                                intent, UserHandle.ALL, "android.permission.BLUETOOTH_CONNECT");
                return true;
            default:
                Log.e("BluetoothAdvancedSettings", "Wrong preference key!");
                return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String str;
        String key = preference.getKey();
        key.getClass();
        switch (key) {
            case "key_bluetooth_received_files":
                String string =
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_advanced_settings_received_files);
                Intent intent = new Intent();
                intent.setClassName(
                        "com.android.bluetooth",
                        "com.android.bluetooth.opp.BluetoothOppTransferHistory");
                intent.setFlags(335544320);
                intent.putExtra("direction", 1);
                intent.putExtra("android.btopp.intent.extra.SHOW_ALL", true);
                startActivity(intent);
                str = string;
                break;
            case "key_bluetooth_cloud_switch":
                SamsungCloudRPCSettingV2 samsungCloudRPCSettingV2 = this.mSamsungCloudRPCSettingV2;
                if (samsungCloudRPCSettingV2 != null) {
                    samsungCloudRPCSettingV2.showSetting(getActivity());
                }
                str = null;
                break;
            case "key_bluetooth_cloud_none_switch":
                if (!this.mIsScloudInstalled) {
                    Intent intent2 = new Intent();
                    intent2.setData(
                            Uri.parse(
                                    "samsungapps://ProductDetail/com.samsung.android.scloud/?source=Samsung"
                                        + " Cloud"));
                    intent2.putExtra("type", "cover");
                    intent2.addFlags(335544352);
                    startActivity(intent2);
                } else if (Utils.getSamsungAccount(getContext()) == null) {
                    Context context = getContext();
                    boolean z = AccountUtils.SupportTwoPhone;
                    AccountUtils.addSamsungAccount(context, this, 1001, 0, UserHandle.myUserId());
                } else {
                    SamsungCloudRPCSettingV2 samsungCloudRPCSettingV22 =
                            this.mSamsungCloudRPCSettingV2;
                    if (samsungCloudRPCSettingV22 != null) {
                        samsungCloudRPCSettingV22.showSetting(getActivity());
                    }
                }
                str = null;
                break;
            case "key_bluetooth_control_history":
                str =
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_advanced_settings_control_history);
                break;
            case "key_bluetooth_scan_activity":
                str =
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_advanced_settings_scan_activity);
                break;
            case "key_bluetooth_rename":
                str =
                        getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .event_bluetooth_advanced_settings_rename_phone);
                DeviceNameEditor deviceNameEditor = new DeviceNameEditor();
                Bundle bundle = new Bundle();
                bundle.putInt(
                        UniversalCredentialUtil.AGENT_TITLE,
                        com.android.settings.R.string.phone_name_title);
                deviceNameEditor.setArguments(bundle);
                deviceNameEditor.show(getFragmentManager(), "dialog");
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            SALogging.insertSALog(this.mScreenId, str);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this);
        }
        if (BluetoothUtils.isEnabledUltraPowerSaving(getActivity())) {
            this.mControlHistoryPreference.setEnabled(false);
        } else {
            this.mControlHistoryPreference.setEnabled(true);
        }
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
        if (localBluetoothAdapter != null
                && !localBluetoothAdapter.mAdapter.isBleScanHistorySupported()) {
            getPreferenceScreen().removePreference(this.mScanActivityPreference);
        }
        boolean hasPackage =
                com.android.settings.Utils.hasPackage(getContext(), "com.samsung.android.scloud");
        this.mIsScloudInstalled = hasPackage;
        if (this.mIsDisableSyncMenu) {
            if (getPreferenceScreen().findPreference("key_bluetooth_cloud_none_switch") != null) {
                getPreferenceScreen().removePreference(this.mCloudNonSwitchPreference);
            }
            if (getPreferenceScreen().findPreference("key_bluetooth_cloud_switch") != null) {
                getPreferenceScreen().removePreference(this.mCloudSwitchPreference);
            }
        } else if (!hasPackage) {
            if (getPreferenceScreen().findPreference("key_bluetooth_cloud_none_switch") == null) {
                getPreferenceScreen().addPreference(this.mCloudNonSwitchPreference);
            }
            if (getPreferenceScreen().findPreference("key_bluetooth_cloud_switch") != null) {
                getPreferenceScreen().removePreference(this.mCloudSwitchPreference);
            }
            this.mCloudNonSwitchPreference.setSummary(
                    getString(
                            com.android.settings.R.string
                                    .sec_bluetooth_advanced_scloud_not_installed,
                            getString(
                                    com.android.settings.R.string
                                            .sec_bluetooth_advanced_scloud_app_name)));
        } else if (this.mIsSCSupportV2) {
            new AnonymousClass5().execute(new Void[0]);
        }
        updateSwitch$2();
        updateOppTransferHistory();
        if (isUseDeltaOptionMenu()) {
            String string =
                    Settings.Global.getString(getActivity().getContentResolver(), "device_name");
            if (string != null) {
                this.mRenamePreference.setSummary(string);
            }
            SecPreference secPreference = this.mRenamePreference;
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, true);
        }
        this.mIBRPreference.setOnPreferenceChangeListener(this);
        this.mAskToUsePreference.setOnPreferenceChangeListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.settings.DEVICE_NAME_CHANGED");
        intentFilter.addAction("com.android.sync.SYNC_CONN_STATUS_CHANGED");
        getActivity().registerReceiver(this.mReceiver, intentFilter);
        this.mReceiverRegistered = true;
        SALogging.insertSALog(this.mScreenId);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("scloud_notify_edp_state_changed"),
                        false,
                        this.mCloudStateChange);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("scloud_bluetooth_sync_enabled"),
                        false,
                        this.mCloudStateChange);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mCloudStateChange);
    }

    public final void updateOppTransferHistory() {
        if (isUseDeltaOptionMenu()) {
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
            if (localBluetoothAdapter == null) {
                Log.d("BluetoothAdvancedSettings", "Adapter is null");
                this.mReceivedFilesPreference.setEnabled(false);
            } else if (localBluetoothAdapter.mAdapter.getState() == 12) {
                this.mReceivedFilesPreference.setEnabled(true);
            } else {
                this.mReceivedFilesPreference.setEnabled(false);
            }
        }
    }

    public final void updateSwitch$2() {
        int state;
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
        if (localBluetoothAdapter != null
                && ((state = localBluetoothAdapter.mAdapter.getState()) == 13 || state == 10)) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(
                            state,
                            "onResume :: Bluetooth Dual Play Settings will finish, adapter state ="
                                + " ",
                            "BluetoothAdvancedSettings");
        }
        if (com.android.settings.Utils.isTablet()) {
            this.mIBRPreference.setSummary(
                    getContext()
                            .getString(
                                    com.android.settings.R.string
                                            .sec_bluetooth_advanced_ibr_summary_tablet));
        } else {
            this.mIBRPreference.setSummary(
                    getContext()
                            .getString(
                                    com.android.settings.R.string
                                            .sec_bluetooth_advanced_ibr_summary));
        }
        this.mIBRPreference.setEnabled(true);
        this.mIBRPreference.setChecked(isInBandRingtoneEnabled());
        if (Rune.isChinaModel()) {
            this.mAskToUsePreference.setChecked(
                    Settings.Global.getInt(
                                    getActivity().getContentResolver(),
                                    "bluetooth_security_on_check",
                                    1)
                            == 1);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
