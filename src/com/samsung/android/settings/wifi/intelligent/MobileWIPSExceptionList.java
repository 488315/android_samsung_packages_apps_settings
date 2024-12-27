package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MobileWIPSExceptionList extends InstrumentedPreferenceFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final Uri exceptionUri =
            Uri.parse("content://com.samsung.android.server.wifi.mobilewips/allowedList");
    public static final boolean isWipsEnabled;
    public AnonymousClass2 mContentObserver;
    public Context mContext;
    public PreferenceGroup mExceptionList;
    public IntentFilter mIntentFilter;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            MobileWIPSExceptionList mobileWIPSExceptionList =
                                    MobileWIPSExceptionList.this;
                            Uri uri = MobileWIPSExceptionList.exceptionUri;
                            WifiManager wifiManager = mobileWIPSExceptionList.mWifiManager;
                            if (wifiManager == null || wifiManager.isWifiEnabled()) {
                                return;
                            }
                            Log.d(
                                    "MobileWIPSExceptionList",
                                    "MobileWIPSExceptionList:finishMobileWipsException");
                            mobileWIPSExceptionList.getActivity().finish();
                        }
                    }
                }
            };
    public Preference mWIPSDesc;
    public WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            boolean z = UserHandle.myUserId() == 0;
            if (MobileWIPSExceptionList.isWipsEnabled
                    && wifiManager != null
                    && wifiManager.isWifiEnabled()
                    && z) {
                Log.d("MobileWIPSExceptionList", "Key should remain added in search index");
            } else {
                ((ArrayList) nonIndexableKeys).add("wifi_wips_exception_networks_settings");
            }
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MWIPSPreference extends Preference {
        public MWIPSPreference(Context context) {
            super(context);
            setLayoutResource(R.layout.wifi_wips_exception_preference);
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            LinearLayout linearLayout =
                    (LinearLayout)
                            preferenceViewHolder.findViewById(R.id.wifi_ap_add_or_delete_button);
            ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.button_img);
            imageView.setImageResource(R.drawable.sec_display_ic_minus_mtrl);
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList.MWIPSPreference.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ContentResolver contentResolver =
                                    MobileWIPSExceptionList.this.mContext.getContentResolver();
                            String[] strArr = {MWIPSPreference.this.getKey()};
                            Log.d(
                                    "MobileWIPSExceptionList",
                                    "allow list delete " + MWIPSPreference.this.getKey());
                            try {
                                contentResolver.delete(
                                        MobileWIPSExceptionList.exceptionUri, "mac_addr=?", strArr);
                                MWIPSPreference mWIPSPreference = MWIPSPreference.this;
                                MobileWIPSExceptionList.this.mExceptionList.removePreference(
                                        mWIPSPreference.findPreferenceInHierarchy(
                                                mWIPSPreference.getKey()));
                            } catch (Exception e) {
                                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                                        "delete & removePreference ", e, "MobileWIPSExceptionList");
                            }
                            SALogging.insertSALog("WIFI_205", "1344");
                        }
                    });
            linearLayout.setVisibility(0);
            super.onBindViewHolder(preferenceViewHolder);
        }
    }

    static {
        Debug.semIsProductDev();
        isWipsEnabled = Utils.isWipsEnabled();
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();
    }

    public final MWIPSPreference addPref(ArrayList arrayList) {
        MWIPSPreference mWIPSPreference = new MWIPSPreference(this.mContext);
        mWIPSPreference.setKey((String) arrayList.get(0));
        mWIPSPreference.setTitle((CharSequence) arrayList.get(1));
        mWIPSPreference.setSummary("MAC address : " + ((String) arrayList.get(0)));
        mWIPSPreference.setSelectable(false);
        return mWIPSPreference;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0061, code lost:

       if (r0.isEmpty() == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0068, code lost:

       return new java.util.ArrayList();
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0069, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005a, code lost:

       if (r7 == null) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getMobileWIPSException() {
        /*
            r7 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.content.Context r7 = r7.mContext
            android.content.ContentResolver r1 = r7.getContentResolver()
            java.lang.String r7 = "exception_type"
            java.lang.String r2 = "ssid_name"
            java.lang.String r3 = "mac_addr"
            java.lang.String[] r3 = new java.lang.String[]{r3, r7, r2}
            r7 = 0
            android.net.Uri r2 = com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList.exceptionUri     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r5 = 0
            r6 = 0
            r4 = 0
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            if (r7 == 0) goto L4d
            r7.moveToFirst()     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
        L25:
            boolean r1 = r7.isAfterLast()     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            if (r1 != 0) goto L4d
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r1.<init>()     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r2 = 0
            java.lang.String r3 = r7.getString(r2)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r1.add(r3)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r3 = 2
            java.lang.String r3 = r7.getString(r3)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            java.lang.String r3 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r3)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r1.add(r3)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r0.add(r2, r1)     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            r7.moveToNext()     // Catch: java.lang.Throwable -> L4b android.database.SQLException -> L53
            goto L25
        L4b:
            r0 = move-exception
            goto L6a
        L4d:
            if (r7 == 0) goto L5d
        L4f:
            r7.close()
            goto L5d
        L53:
            java.lang.String r1 = "MobileWIPSExceptionList"
            java.lang.String r2 = "error getMobileWIPSException() can't get allowlist"
            android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> L4b
            if (r7 == 0) goto L5d
            goto L4f
        L5d:
            boolean r7 = r0.isEmpty()
            if (r7 == 0) goto L69
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            return r7
        L69:
            return r0
        L6a:
            if (r7 == 0) goto L6f
            r7.close()
        L6f:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList.getMobileWIPSException():java.util.ArrayList");
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    /* JADX WARN: Type inference failed for: r2v14, types: [com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList$2] */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.wifi_wips_exception_networks_settings);
        this.mExceptionList = (PreferenceGroup) findPreference("wips_exception_up");
        Preference findPreference = findPreference("wips_exception_desc");
        this.mWIPSDesc = findPreference;
        findPreference.setTitle(R.string.wifi_mobile_wips_exception_summary);
        this.mContext = getContext();
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mExceptionList.removeAll();
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        setHasOptionsMenu(true);
        this.mContentObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.MobileWIPSExceptionList.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        Log.d("MobileWIPSExceptionList", "AllowList db was updated");
                        MobileWIPSExceptionList mobileWIPSExceptionList =
                                MobileWIPSExceptionList.this;
                        Uri uri = MobileWIPSExceptionList.exceptionUri;
                        mobileWIPSExceptionList.setExceptionListUp();
                    }
                };
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 1) {
            if (itemId == 16908332) {
                Log.i("MobileWIPSExceptionList", "Action bar up button");
            }
            return super.onOptionsItemSelected(menuItem);
        }
        Log.d("MobileWIPSExceptionList", "open Exception List");
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MobileWIPSExceptionList.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.wifi_mobile_wips_exception, null);
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("MobileWIPSExceptionList", "MobileWIPSExceptionList:onPause");
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            Log.d("MobileWIPSExceptionList", "MobileWIPSExceptionList:finishMobileWipsException");
            getActivity().finish();
        }
        setExceptionListUp();
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        this.mIntentFilter,
                        "android.permission.CHANGE_NETWORK_STATE",
                        null,
                        2);
        try {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(exceptionUri, true, this.mContentObserver);
        } catch (SecurityException e) {
            Log.e("MobileWIPSExceptionList", "registerContentObserver, Exception" + e);
        }
    }

    public final void setExceptionListUp() {
        this.mExceptionList.removeAll();
        try {
            ArrayList mobileWIPSException = getMobileWIPSException();
            if (mobileWIPSException.isEmpty()) {
                Log.d(
                        "MobileWIPSExceptionList",
                        "ExceptionList is empty. Set No exception layout.");
                this.mExceptionList.setVisible(false);
                this.mWIPSDesc.setLayoutResource(
                        R.layout.wips_widget_preference_unclickable_no_exception_list);
                return;
            }
            this.mWIPSDesc.setLayoutResource(
                    R.layout.wips_widget_preference_unclickable_with_exception);
            this.mWIPSDesc.setTitle(R.string.wifi_mobile_wips_exception_summary);
            this.mExceptionList.setVisible(true);
            Iterator it = mobileWIPSException.iterator();
            while (it.hasNext()) {
                this.mExceptionList.addPreference(addPref((ArrayList) it.next()));
            }
        } catch (Exception e) {
            Log.e("MobileWIPSExceptionList", "getMobileWIPSException, Exception" + e);
            e.printStackTrace();
        }
    }
}
