package com.samsung.android.settings.softwareupdate;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.secutil.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecSwitchPreference;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.softwareupdate.AutoDownloadPreferenceType;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoftwareUpdateSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {
    public final AnonymousClass2 mHandler;
    public final AnonymousClass3 mSecPhoneServiceConnection;
    public final Messenger mSvcModeMessenger;
    public static final boolean mSupportDualLockDSH = Arrays.asList("SM-A515U", "SM-A125U", "SM-A526U", "SM-A205U", "SM-G973U", "SM-A025U", "SM-A102U", "SM-A215U", "SM-G981U").contains(SystemProperties.get("ro.product.model", ApnSettings.MVNO_NONE));
    public static boolean mDisplayDC = false;
    public static boolean mEnableMenu = false;
    public static boolean mEnableMenuUICC = false;
    public static boolean mDisplayPRL = false;
    public static boolean mDisplayUICC = false;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass5();
    public SecPreference mSoftwareUpdatePref = null;
    public SecSwitchPreference mWifiAutoDownloadPref = null;
    public SecPreference mAutoDownloadPref = null;
    public SecPreference mLastUpdateInfoPref = null;
    public AnonymousClass4 mLastUpdateCheckTimeReceiver = null;
    public int mWaitingMessage = 0;
    public Messenger mServiceMessenger = null;
    public final AnonymousClass1 mNoResponseTimer = new CountDownTimer() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.1
        @Override // android.os.CountDownTimer
        public final void onFinish() {
            Log.d("SoftwareUpdateSettings", "mNoResponseTimer timed out when waiting for message " + SoftwareUpdateSettings.this.mWaitingMessage);
            SoftwareUpdateSettings softwareUpdateSettings = SoftwareUpdateSettings.this;
            Message obtainMessage = softwareUpdateSettings.mHandler.obtainMessage(softwareUpdateSettings.mWaitingMessage);
            obtainMessage.arg1 = -1;
            sendMessage(obtainMessage);
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            Log.d("SoftwareUpdateSettings", "mNoResponseTimer " + this + " onTick + seconds remaining: " + (j / 1000));
        }
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            int i = 0;
            if (!Utils.isPackageEnabled(context, "com.wssyncmldm")) {
                SoftwareUpdateMenu[] values = SoftwareUpdateMenu.values();
                int length = values.length;
                while (i < length) {
                    ((ArrayList) nonIndexableKeys).add(values[i].getKey(context));
                    i++;
                }
                return nonIndexableKeys;
            }
            SoftwareUpdateMenu[] values2 = SoftwareUpdateMenu.values();
            int length2 = values2.length;
            while (i < length2) {
                SoftwareUpdateMenu softwareUpdateMenu = values2[i];
                if (!softwareUpdateMenu.shouldEnable()) {
                    ((ArrayList) nonIndexableKeys).add(softwareUpdateMenu.getKey(context));
                }
                i++;
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Log.secD("SoftwareUpdateSettings", "getRawDataToIndex in SoftwareUpdateSettings");
            ArrayList arrayList = new ArrayList();
            for (SoftwareUpdateMenu softwareUpdateMenu : SoftwareUpdateMenu.values()) {
                softwareUpdateMenu.getClass();
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = softwareUpdateMenu.getKey(context);
                searchIndexableRaw.title = String.valueOf(softwareUpdateMenu.getTitleId(context));
                searchIndexableRaw.screenTitle = context.getString(R.string.sec_software_update_title);
                searchIndexableRaw.summaryOn = softwareUpdateMenu.getSummary(context);
                ((SearchIndexableData) searchIndexableRaw).iconResId = softwareUpdateMenu.getIconResId();
                searchIndexableRaw.keywords = softwareUpdateMenu.getKeywords(context);
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static abstract class SoftwareUpdateMenu {
        public static final /* synthetic */ SoftwareUpdateMenu[] $VALUES;
        public static final AnonymousClass3 AUTO_DOWNLOAD;
        public static final AnonymousClass2 DOWNLOAD_AND_INSTALL = null;
        public static final AnonymousClass4 LAST_UPDATE = null;
        public static final AnonymousClass1 SOFTWARE_UPDATE_TITLE = null;
        public static final AnonymousClass7 UICC_UNLOCK = null;
        public static final AnonymousClass6 UPDATE_PRL = null;
        public static final AnonymousClass5 UPDATE_PROFILE = null;

        /* JADX INFO: Fake field, exist only in values array */
        SoftwareUpdateMenu EF0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$SoftwareUpdateMenu$3, reason: invalid class name */
        public enum AnonymousClass3 extends SoftwareUpdateMenu {
            public AnonymousClass3() {
                super("AUTO_DOWNLOAD", 2);
            }

            @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
            public final String getKey(Context context) {
                return AutoDownloadPreferenceType.of(context).getKey();
            }

            @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
            public final String getSummary(Context context) {
                return AutoDownloadPreferenceType.of(context).getSummary(context);
            }

            @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
            public final int getTitleId(Context context) {
                return AutoDownloadPreferenceType.of(context).getTitleId();
            }
        }

        static {
            SoftwareUpdateMenu softwareUpdateMenu = new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.1
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getIconResId() {
                    return R.drawable.sec_ic_settings_software_update;
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "software_update_screen";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKeywords(Context context) {
                    return context.getResources().getString(R.string.keywords_software_update_settings);
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.sec_software_update_title;
                }
            };
            SoftwareUpdateMenu softwareUpdateMenu2 = new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.2
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "key_update";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getSummary(Context context) {
                    return context.getResources().getString(R.string.sec_software_update_download_updates_manually_summary_for_search);
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.sec_software_update_download_updates_manually_title;
                }
            };
            AnonymousClass3 anonymousClass3 = new AnonymousClass3();
            AUTO_DOWNLOAD = anonymousClass3;
            $VALUES = new SoftwareUpdateMenu[]{softwareUpdateMenu, softwareUpdateMenu2, anonymousClass3, new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.4
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "key_last_update";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getSummary(Context context) {
                    return context.getResources().getString(R.string.sec_software_update_last_software_information_summary_for_search);
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.sec_software_update_last_software_information_title;
                }
            }, new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.5
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "update_profile";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.update_profile;
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final boolean shouldEnable() {
                    return SoftwareUpdateSettings.mDisplayDC;
                }
            }, new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.6
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "update_prl";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.update_prl;
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final boolean shouldEnable() {
                    return SoftwareUpdateSettings.mDisplayPRL;
                }
            }, new SoftwareUpdateMenu() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu.7
                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final String getKey(Context context) {
                    return "uicc_unlock";
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final int getTitleId(Context context) {
                    return R.string.uicc_unlock;
                }

                @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.SoftwareUpdateMenu
                public final boolean shouldEnable() {
                    return SoftwareUpdateSettings.mDisplayUICC;
                }
            }};
        }

        public static SoftwareUpdateMenu valueOf(String str) {
            return (SoftwareUpdateMenu) Enum.valueOf(SoftwareUpdateMenu.class, str);
        }

        public static SoftwareUpdateMenu[] values() {
            return (SoftwareUpdateMenu[]) $VALUES.clone();
        }

        public int getIconResId() {
            return 0;
        }

        public abstract String getKey(Context context);

        public String getKeywords(Context context) {
            return null;
        }

        public String getSummary(Context context) {
            return null;
        }

        public abstract int getTitleId(Context context);

        public boolean shouldEnable() {
            return true;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.os.Handler, com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$3] */
    public SoftwareUpdateSettings() {
        ?? r0 = new Handler() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                Log.d("SoftwareUpdateSettings", "Handle Message : " + message.what);
                Log.d("SoftwareUpdateSettings", "Handle Message msg.arg1: " + message.arg1);
                boolean z = SoftwareUpdateSettings.mSupportDualLockDSH;
                SoftwareUpdateSettings softwareUpdateSettings = SoftwareUpdateSettings.this;
                softwareUpdateSettings.getClass();
                Log.d("SoftwareUpdateSettings", "handleGetSimLockResponse" + message);
                byte[] byteArray = message.getData().getByteArray("response");
                if (byteArray == null || byteArray.length == 0) {
                    Log.d("SoftwareUpdateSettings", "response is null or empty");
                    return;
                }
                byte b = byteArray[0];
                String string = b != 0 ? b != 1 ? b != 2 ? ApnSettings.MVNO_NONE : softwareUpdateSettings.getResources().getString(R.string.unlocked_for_any_sim_card) : softwareUpdateSettings.getResources().getString(R.string.locked_to_sprint_or_international_sims) : softwareUpdateSettings.getResources().getString(R.string.locked_to_sprint_sims_only);
                softwareUpdateSettings.getContext();
                Preference findPreference = softwareUpdateSettings.findPreference("uicc_unlock");
                if (findPreference != null) {
                    findPreference.setSummary(string);
                }
                Log.d("SoftwareUpdateSettings", "handleGetSimLockResponse buf.length " + byteArray.length + " buf is ");
            }
        };
        this.mHandler = r0;
        this.mSvcModeMessenger = new Messenger((Handler) r0);
        this.mSecPhoneServiceConnection = new ServiceConnection() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.3
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("SoftwareUpdateSettings", "onServiceConnected()");
                SoftwareUpdateSettings.this.mServiceMessenger = new Messenger(iBinder);
                SoftwareUpdateSettings.this.getOemSimLock();
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Log.d("SoftwareUpdateSettings", "onServiceDisconnected()");
                SoftwareUpdateSettings.this.mServiceMessenger = null;
            }
        };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8100;
    }

    public final void getOemSimLock() {
        Log.d("SoftwareUpdateSettings", "getOemSimLock");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            try {
                try {
                    dataOutputStream.writeByte(81);
                    dataOutputStream.writeByte(66);
                    dataOutputStream.writeShort(4);
                    sendRilRequest(byteArrayOutputStream.toByteArray());
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                    Log.e("SoftwareUpdateSettings", "getOemSimLock exception occurred during operation");
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                }
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    Log.e("SoftwareUpdateSettings", e.getMessage());
                }
                throw th;
            }
        } catch (IOException e2) {
            Log.e("SoftwareUpdateSettings", e2.getMessage());
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_software_update_settings;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b8  */
    /* JADX WARN: Type inference failed for: r13v9, types: [com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$4] */
    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.samsung.helphub", 0);
            if (packageInfo == null || packageInfo.versionCode == 1) {
                return;
            }
            menu.add(0, 0, 1, R.string.help_title).setShowAsAction(0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("SoftwareUpdateSettings", "NameNotFoundException in onCreateOptionsMenu");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mLastUpdateCheckTimeReceiver != null) {
            getActivity().unregisterReceiver(this.mLastUpdateCheckTimeReceiver);
            this.mLastUpdateCheckTimeReceiver = null;
        }
        if (this.mServiceMessenger != null) {
            getActivity().unbindService(this.mSecPhoneServiceConnection);
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 0) {
            if (itemId == 16908332) {
                LoggingHelper.insertEventLogging(8100, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
            }
            return super.onOptionsItemSelected(menuItem);
        }
        Intent intent = new Intent("com.samsung.helphub.HELP");
        if (Utils.isSupportHelpMenu(getActivity())) {
            intent.putExtra("helphub:section", "upgrade");
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.secD("SoftwareUpdateSettings", "key: " + preference.getKey() + "/" + obj);
        if (!"key_auto_download_over_wifi".equals(preference.getKey())) {
            return true;
        }
        Optional.ofNullable((Integer) AutoDownloadPreferenceType.AutoDownloadOverWifi.map.get((Boolean) obj)).ifPresent(new Consumer() { // from class: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                SoftwareUpdateSettings softwareUpdateSettings = SoftwareUpdateSettings.this;
                boolean z = SoftwareUpdateSettings.mSupportDualLockDSH;
                AutoDownloadPreferenceType.AUTO_DOWNLOAD_OVER_WIFI.setDBAndLogging(softwareUpdateSettings.getContext(), ((Integer) obj2).intValue());
            }
        });
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        Context context = getContext();
        if ("key_update".equals(key)) {
            Log.d("SoftwareUpdateSettings", "Software Update Checked");
            LoggingHelper.insertEventLogging(8100, 4800);
            SoftwareUpdateUtils.checkNewSoftwareUpdate(getActivity());
        } else if (SoftwareUpdateMenu.AUTO_DOWNLOAD.getKey(context).equals(key)) {
            Log.d("SoftwareUpdateSettings", "Auto Download is clicked");
        } else if ("key_last_update".equals(key)) {
            Log.d("SoftwareUpdateSettings", "Last Update is clicked");
            LoggingHelper.insertEventLogging(8100, 4803);
            Intent intent = new Intent("com.wssyncmldm.LastUpdateActivity");
            intent.setPackage("com.wssyncmldm");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Log.e("SoftwareUpdateSettings", "LastUpdateActivity is not found");
                this.mLastUpdateInfoPref.setSummary(getActivity().getString(R.string.sec_software_update_last_software_information_summary_for_nodata));
                this.mLastUpdateInfoPref.setEnabled(false);
            }
        } else if ("update_prl".equals(key)) {
            Log.d("SoftwareUpdateSettings", "Update PRL is clicked");
            Intent intent2 = new Intent();
            intent2.setClassName("com.sec.omadmspr", "com.sec.omadmspr.sdm.SDMService");
            intent2.setAction("com.sprint.samsung.OMADMExtension.HFA");
            intent2.putExtra("EXTRA_HFA_TYPE", "START_PRL");
            intent2.addFlags(32);
            getActivity().startService(intent2);
        } else if ("update_profile".equals(key)) {
            Log.d("SoftwareUpdateSettings", "Update Profile is clicked");
            Intent intent3 = new Intent();
            intent3.setClassName("com.sec.omadmspr", "com.sec.omadmspr.sdm.SDMService");
            intent3.setAction("com.sprint.samsung.OMADMExtension.HFA");
            intent3.putExtra("EXTRA_HFA_TYPE", "START_DP");
            intent3.addFlags(32);
            getActivity().startService(intent3);
        } else if ("uicc_unlock".equals(key)) {
            Log.d("SoftwareUpdateSettings", "UICC Unlock is clicked");
            Intent intent4 = new Intent();
            intent4.setClassName("com.sec.omadmspr", "com.sec.omadmspr.sdm.SDMService");
            intent4.setAction("com.sprint.samsung.OMADMExtension.HFA");
            intent4.putExtra("EXTRA_HFA_TYPE", "SIM_UNLOCK");
            intent4.addFlags(32);
            getActivity().startService(intent4);
        }
        return super.onPreferenceTreeClick(preference);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02cc  */
    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 720
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.softwareupdate.SoftwareUpdateSettings.onResume():void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.d("SoftwareUpdateSettings", "onStop Software Update");
        super.onStop();
        cancel();
        if (this.mServiceMessenger != null) {
            removeCallbacksAndMessages(null);
        }
    }

    public final void sendRilRequest(byte[] bArr) {
        Log.d("SoftwareUpdateSettings", "sendRilRequest invokeOemRilRequestRaw message 109");
        this.mWaitingMessage = 109;
        start();
        Message obtainMessage = obtainMessage(109);
        Bundle data = obtainMessage.getData();
        data.putByteArray("request", bArr);
        obtainMessage.setData(data);
        obtainMessage.replyTo = this.mSvcModeMessenger;
        try {
            Messenger messenger = this.mServiceMessenger;
            if (messenger != null) {
                messenger.send(obtainMessage);
            } else {
                Log.d("SoftwareUpdateSettings", "mServiceMessenger is null. Do nothing.");
            }
        } catch (RemoteException unused) {
        }
    }

    public final void updateLastCheckedDate() {
        long j;
        if (this.mSoftwareUpdatePref == null) {
            return;
        }
        try {
            j = Settings.System.getLong(getContentResolver(), "SOFTWARE_UPDATE_LAST_CHECKED_DATE");
        } catch (Settings.SettingNotFoundException unused) {
            Log.e("SoftwareUpdateSettings", "SettingNotFoundException in updateLastCheckedDate");
            j = 0;
        }
        String str = ApnSettings.MVNO_NONE;
        String m = (j == 0 || j > System.currentTimeMillis()) ? ApnSettings.MVNO_NONE : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(getResources().getString(R.string.sec_software_update_last_checked_on), " ", DateFormat.getLongDateFormat(getActivity()).format(Long.valueOf(j)));
        String string = getResources().getString(R.string.sec_software_update_download_updates_manually_summary_for_charge);
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (("KOO".equals(Utils.getSalesCode()) && ("wifi-only".equalsIgnoreCase(SemSystemProperties.get("ro.carrier", "Unknown").trim()) || "yes".equalsIgnoreCase(SemSystemProperties.get("ro.radio.noril", "no").trim()))) || (Rune.isShowMobileNetworkWarning(getContext()) && !Rune.isWifiDedicated(getActivity()))) {
            str = string;
        }
        if (!TextUtils.isEmpty(m) && !TextUtils.isEmpty(str)) {
            m = m.concat("\n");
        }
        this.mSoftwareUpdatePref.setSummary(m + str);
    }

    public final void updateSprPrefMenu(String str, boolean z, boolean z2) {
        Log.secD("SoftwareUpdateSettings", "updateSprPrefMenu() Key: " + str + "; display? " + z + "; enable? " + z2);
        Preference findPreference = findPreference(str);
        if (findPreference != null) {
            if (z) {
                findPreference.setEnabled(z2);
            } else {
                removePreference(str);
            }
        }
    }
}
