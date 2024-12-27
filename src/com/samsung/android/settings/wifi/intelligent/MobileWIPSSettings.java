package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.SQLException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.settings.ImsProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MobileWIPSSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final Uri detectionUri =
            Uri.parse("content://com.samsung.android.server.wifi.mobilewips/detection");
    public AnonymousClass2 mContentObserver;
    public Context mContext;
    public PreferenceCategory mHistoryListUpside;
    public IntentFilter mIntentFilter;
    public ArrayList mNewItemID;
    public SettingsMainSwitchBar mSwitchBar;
    public Preference mWIPSDesc;
    public WifiManager mWifiManager;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            MobileWIPSSettings mobileWIPSSettings = MobileWIPSSettings.this;
                            Uri uri = MobileWIPSSettings.detectionUri;
                            WifiManager wifiManager = mobileWIPSSettings.mWifiManager;
                            if (wifiManager == null || wifiManager.isWifiEnabled()) {
                                return;
                            }
                            Log.d(
                                    "MobileWIPSSettings",
                                    "MobileWIPSSettings:finishMobileWipsSettings");
                            mobileWIPSSettings.getActivity().finish();
                        }
                    }
                }
            };
    public final AnonymousClass2 mWipsObserver = new AnonymousClass2(this, new Handler(), 0);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ MobileWIPSSettings this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass2(
                MobileWIPSSettings mobileWIPSSettings, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = mobileWIPSSettings;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    if (Settings.Secure.getInt(
                                    this.this$0.mContext.getContentResolver(), "wifi_mwips", 0)
                            < 2) {
                        this.this$0.setMobileWipsSwitch();
                        break;
                    }
                    break;
                default:
                    Log.d("MobileWIPSSettings", "Detection db was updated");
                    MobileWIPSSettings mobileWIPSSettings = this.this$0;
                    Uri uri = MobileWIPSSettings.detectionUri;
                    mobileWIPSSettings.setHistoryListUp();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("wips_desc");
            if (UserHandle.myUserId() != 0) {
                Log.i("MobileWIPSSettings", "Not a System User: Add MobileWIPS key");
                arrayList.add("MobileWIPS");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            if (UserHandle.myUserId() == 0) {
                Log.i("MobileWIPSSettings", "System User: Should be searchable");
                searchIndexableResource.className = MobileWIPSSettings.class.getName();
                searchIndexableResource.xmlResId = R.xml.wifi_wips_settings;
            } else {
                Log.i("MobileWIPSSettings", "Non System User should not be searchable");
            }
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MWIPSHistoryPreference extends Preference {
        public LinearLayout btn;
        public boolean newbadge;

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            this.btn = (LinearLayout) preferenceViewHolder.findViewById(R.id.wifi_new_button);
            if (this.newbadge) {
                this.btn.setVisibility(0);
            } else {
                this.btn.setVisibility(8);
            }
            super.onBindViewHolder(preferenceViewHolder);
        }
    }

    static {
        Debug.semIsProductDev();
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
    }

    public static boolean isYMDFormat() {
        String bestDateTimePattern =
                DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyyMMMdd");
        char[] cArr = new char[3];
        int length = bestDateTimePattern.length();
        int i = 0;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (i < length) {
            char charAt = bestDateTimePattern.charAt(i);
            if (charAt == 'd' || charAt == 'L' || charAt == 'M' || charAt == 'y') {
                if (charAt == 'd' && !z) {
                    cArr[i2] = 'd';
                    i2++;
                    z = true;
                } else if ((charAt == 'L' || charAt == 'M') && !z2) {
                    cArr[i2] = 'M';
                    i2++;
                    z2 = true;
                } else if (charAt == 'y' && !z3) {
                    cArr[i2] = 'y';
                    i2++;
                    z3 = true;
                }
            } else if (charAt == 'G') {
                continue;
            } else {
                if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                    throw new IllegalArgumentException(
                            "Bad pattern character '" + charAt + "' in " + bestDateTimePattern);
                }
                if (charAt != '\'') {
                    continue;
                } else {
                    if (i < length - 1) {
                        int i3 = i + 1;
                        if (bestDateTimePattern.charAt(i3) == '\'') {
                            i = i3;
                        }
                    }
                    int indexOf = bestDateTimePattern.indexOf(39, i + 1);
                    if (indexOf == -1) {
                        throw new IllegalArgumentException(
                                "Bad quoting in ".concat(bestDateTimePattern));
                    }
                    i = indexOf + 1;
                }
            }
            i++;
        }
        return new String(cArr).equalsIgnoreCase("YMD");
    }

    public final MWIPSHistoryPreference addPref(ArrayList arrayList) {
        boolean z = Long.parseLong((String) arrayList.get(3)) == 0;
        MWIPSHistoryPreference mWIPSHistoryPreference = new MWIPSHistoryPreference(this.mContext);
        mWIPSHistoryPreference.setLayoutResource(R.layout.sec_preference_mwips_history);
        mWIPSHistoryPreference.newbadge = z;
        mWIPSHistoryPreference.setKey((String) arrayList.get(0));
        mWIPSHistoryPreference.setTitle((CharSequence) arrayList.get(1));
        String str = (String) arrayList.get(2);
        int parseInt = Integer.parseInt((String) arrayList.get(4));
        mWIPSHistoryPreference.setSummary(str);
        try {
            Date parse = new SimpleDateFormat("yyMMddHHmmss").parse(str);
            String format =
                    !isYMDFormat()
                            ? new SimpleDateFormat("dd/MM/yyyy").format(parse)
                            : new SimpleDateFormat("yyyy/MM/dd").format(parse);
            if (parseInt >= 200) {
                format =
                        format
                                + ", "
                                + this.mContext.getString(
                                        R.string.wifi_mobile_wips_added_to_exception);
            } else if (parseInt >= 100) {
                format =
                        format
                                + ", "
                                + this.mContext.getString(R.string.wifi_mobile_wips_disconnected);
            }
            mWIPSHistoryPreference.setSummary(format);
        } catch (Exception unused) {
        }
        if (Long.parseLong((String) arrayList.get(3)) == 0) {
            this.mNewItemID.add((String) arrayList.get(0));
        }
        mWIPSHistoryPreference.setSelectable(false);
        return mWIPSHistoryPreference;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0074, code lost:

       if (r2 != null) goto L15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0076, code lost:

       r2.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x009b, code lost:

       if (r1.size() != 0) goto L26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009d, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x009e, code lost:

       return r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0094, code lost:

       if (r2 == null) goto L23;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a1  */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getMobileWIPSDetection() {
        /*
            r8 = this;
            java.lang.String r0 = "MobileWIPSSettings"
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.content.Context r8 = r8.mContext
            android.content.ContentResolver r2 = r8.getContentResolver()
            java.lang.String r8 = "seen_time"
            java.lang.String r3 = "attack_type"
            java.lang.String r4 = "history_id"
            java.lang.String r5 = "ssid_name"
            java.lang.String r6 = "time_stamp"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5, r6, r8, r3}
            r8 = 0
            android.net.Uri r3 = com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings.detectionUri     // Catch: java.lang.Throwable -> L7a android.database.SQLException -> L7e
            r6 = 0
            r7 = 0
            r5 = 0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L7a android.database.SQLException -> L7e
            if (r2 == 0) goto L6f
            r2.moveToFirst()     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
        L2d:
            boolean r3 = r2.isAfterLast()     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            if (r3 != 0) goto L74
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.<init>()     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r4 = 0
            java.lang.String r5 = r2.getString(r4)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.add(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r5 = 1
            java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            java.lang.String r5 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.add(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r5 = 2
            java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.add(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r5 = 3
            java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.add(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r5 = 4
            java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r3.add(r5)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r1.add(r4, r3)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            r2.moveToNext()     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
            goto L2d
        L6b:
            r8 = move-exception
            goto L9f
        L6d:
            r3 = move-exception
            goto L80
        L6f:
            java.lang.String r3 = "MobileWips don't have detection history"
            android.util.Log.i(r0, r3)     // Catch: java.lang.Throwable -> L6b android.database.SQLException -> L6d
        L74:
            if (r2 == 0) goto L97
        L76:
            r2.close()
            goto L97
        L7a:
            r0 = move-exception
            r2 = r8
            r8 = r0
            goto L9f
        L7e:
            r3 = move-exception
            r2 = r8
        L80:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6b
            r4.<init>()     // Catch: java.lang.Throwable -> L6b
            java.lang.String r5 = "error getMobileWIPSDetection() can't get detectionlist"
            r4.append(r5)     // Catch: java.lang.Throwable -> L6b
            r4.append(r3)     // Catch: java.lang.Throwable -> L6b
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L6b
            android.util.Log.e(r0, r3)     // Catch: java.lang.Throwable -> L6b
            if (r2 == 0) goto L97
            goto L76
        L97:
            int r0 = r1.size()
            if (r0 != 0) goto L9e
            return r8
        L9e:
            return r1
        L9f:
            if (r2 == 0) goto La4
            r2.close()
        La4:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.MobileWIPSSettings.getMobileWIPSDetection():java.util.ArrayList");
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.setEnabled(true);
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "MobileWIPSSettings:onCheckedChanged -> isChecked = ", "MobileWIPSSettings", z);
        Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_mwips", z ? 3 : 2);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_205", "1343");
        this.mSwitchBar.setEnabled(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        addPreferencesFromResource(R.xml.wifi_wips_settings);
        this.mHistoryListUpside = (PreferenceCategory) findPreference("wips_history_up");
        setMobileWipsDescriptionTitle(true);
        this.mNewItemID = new ArrayList();
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        setHasOptionsMenu(false);
        ((SettingsActivity) getActivity()).getClass();
        this.mContentObserver = new AnonymousClass2(this, new Handler(), 1);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        Iterator it = this.mNewItemID.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            long parseLong =
                    Long.parseLong(
                            new SimpleDateFormat("yyMMddHHmmss")
                                    .format(new Date(System.currentTimeMillis())));
            String[] strArr = {str};
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("seen_time", Long.valueOf(parseLong));
                contentResolver.update(detectionUri, contentValues, "history_id=?", strArr);
            } catch (SQLException e) {
                Log.e(
                        "MobileWIPSSettings",
                        "error getMobileWIPSDetection() can't get detectionlist" + e);
            }
        }
        Log.d("MobileWIPSSettings", "MobileWIPSSettings:onDestroyView");
        this.mSwitchBar.hide();
        ((SettingsActivity) getActivity()).getClass();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        setMobileWipsSwitch();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("MobileWIPSSettings", "MobileWIPSSettings:onPause");
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mContext.getContentResolver().unregisterContentObserver(this.mWipsObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            Log.d("MobileWIPSSettings", "MobileWIPSSettings:finishMobileWipsSettings");
            getActivity().finish();
        }
        setHistoryListUp();
        try {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(detectionUri, true, this.mContentObserver);
        } catch (SecurityException e) {
            Log.e("MobileWIPSSettings", "registerContentObserver, Exception" + e);
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("wifi_mwips"), false, this.mWipsObserver);
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        this.mIntentFilter,
                        "android.permission.CHANGE_NETWORK_STATE",
                        null,
                        2);
        setMobileWipsSwitch();
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SALogging.insertSALog("WIFI_205");
    }

    public final void setHistoryListUp() {
        this.mHistoryListUpside.removeAll();
        try {
            ArrayList mobileWIPSDetection = getMobileWIPSDetection();
            if (mobileWIPSDetection != null && !mobileWIPSDetection.isEmpty()) {
                this.mWIPSDesc.setLayoutResource(
                        R.layout.wips_widget_preference_unclickable_with_history);
                setMobileWipsDescriptionTitle(true);
                Iterator it = mobileWIPSDetection.iterator();
                for (int i = 0; it.hasNext() && i < 5; i++) {
                    this.mHistoryListUpside.addPreference(addPref((ArrayList) it.next()));
                }
                return;
            }
            Log.d("MobileWIPSSettings", "History is empty. Set No history layout.");
            this.mHistoryListUpside.setVisible(false);
        } catch (Exception e) {
            Log.e("MobileWIPSSettings", "getDetectionLIST, Exception" + e);
            e.printStackTrace();
        }
    }

    public final void setMobileWipsDescriptionTitle(boolean z) {
        Preference preference = this.mWIPSDesc;
        if (preference == null) {
            if (preference == null && z) {
                this.mWIPSDesc = findPreference("wips_desc");
                setMobileWipsDescriptionTitle(false);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.wifi_mobile_wips_detail));
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageManager.getPackageInfo("com.samsung.android.fast", 0);
            if (packageManager.checkSignatures(
                            RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME,
                            "com.samsung.android.fast")
                    == 0) {
                sb.append("\n");
                sb.append(this.mContext.getString(R.string.wifi_mobile_wips_with_securewifi));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        this.mWIPSDesc.setTitle(sb);
    }

    public final void setMobileWipsSwitch() {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_mwips", 0);
        this.mSwitchBar.setEnabled(i <= 1);
        Log.d("MobileWIPSSettings", "MobileWIPSSettings:setMobileWipsSwitch -> isEnabled = " + i);
        if (i == 1 || i == 3) {
            this.mSwitchBar.setChecked(true);
        } else {
            this.mSwitchBar.setChecked(false);
        }
    }
}
