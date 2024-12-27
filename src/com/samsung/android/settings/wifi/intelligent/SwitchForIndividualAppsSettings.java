package com.samsung.android.settings.wifi.intelligent;

import android.app.AppGlobals;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.internal.PreferenceImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.sec.ims.settings.ImsProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SwitchForIndividualAppsSettings extends SettingsPreferenceFragment
        implements ApplicationsState.Callbacks, CompoundButton.OnCheckedChangeListener {
    public SwitchableAppsAdapter mAdapter;
    public List mApps;
    public Context mContext;
    public final AnonymousClass1 mDataRoamingObserver;
    public View mEmptyView;
    public IntentFilter mFilter;
    public final AnonymousClass1 mMobileDataObserver;
    public PackageManager mPackageManager;
    public RecyclerView mRecyclerView;
    public View mRootView;
    public ApplicationsState.Session mSession;
    public SettingsMainSwitchBar mSwitchBar;
    public ArrayList mSwitchablePackageList;
    public int mSuggestionUid = 0;
    public ArrayList mSwitchEnabledUidList = new ArrayList();
    public int mDetectionMode = 0;
    public final AnonymousClass3 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                    SwitchForIndividualAppsSettings.this);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                SwitchForIndividualAppsSettings.this);
                    } else if (action.equals("android.intent.action.SIM_STATE_CHANGED")
                            || action.equals("android.intent.action.ANY_DATA_STATE")) {
                        SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                SwitchForIndividualAppsSettings.this);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings$4, reason: invalid class name */
    public final class AnonymousClass4 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) obj;
            ApplicationsState.AppEntry appEntry2 = (ApplicationsState.AppEntry) obj2;
            String str = appEntry.label;
            if (str != null && str.equals(appEntry2.label)) {
                return 0;
            }
            String str2 = appEntry.label;
            return (str2 == null || str2.compareTo(appEntry2.label) <= 0) ? -1 : 1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppViewHolder extends RecyclerView.ViewHolder {
        public PreferenceImageView appIcon;
        public TextView appName;
        public SwitchCompat appSwitch;
        public int uid;
        public View view;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SwitchableAppsAdapter extends RecyclerView.Adapter {
        public final List mAppList = new ArrayList();
        public final Context mContext;

        public SwitchableAppsAdapter(
                SwitchForIndividualAppsSettings switchForIndividualAppsSettings, List list) {
            this.mContext = switchForIndividualAppsSettings.getContext();
            if (list != null) {
                ((ArrayList) this.mAppList).clear();
                ((ArrayList) this.mAppList).addAll(list);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mAppList).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            Drawable badgedIconForUser;
            if (viewHolder instanceof AppViewHolder) {
                final AppViewHolder appViewHolder = (AppViewHolder) viewHolder;
                ApplicationsState.AppEntry appEntry =
                        (ApplicationsState.AppEntry) ((ArrayList) this.mAppList).get(i);
                appViewHolder.uid = appEntry.info.uid;
                appViewHolder.itemView.setClickable(true);
                appViewHolder.appIcon.setTag(Integer.valueOf(appViewHolder.uid));
                appViewHolder.itemView.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.SwitchableAppsAdapter.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AppViewHolder.this.appSwitch.setChecked(!r0.isChecked());
                            }
                        });
                int i2 = appViewHolder.uid;
                SwitchForIndividualAppsSettings switchForIndividualAppsSettings =
                        SwitchForIndividualAppsSettings.this;
                if (i2 > 100000) {
                    try {
                        int i3 = i2 / 100000;
                        Log.d("SwitchForIndividualAppsSettings", "onBindViewHolder - userId " + i3);
                        UserHandle userHandle = new UserHandle(i3);
                        PackageManager packageManager = this.mContext.getPackageManager();
                        UserManager userManager =
                                (UserManager) this.mContext.getSystemService("user");
                        UserInfo userInfo = userManager.getUserInfo(i3);
                        ApplicationInfo applicationInfo =
                                AppGlobals.getPackageManager()
                                        .getApplicationInfo(appEntry.info.packageName, 0L, i3);
                        if (userInfo != null
                                && (badgedIconForUser =
                                                userManager.getBadgedIconForUser(
                                                        applicationInfo.loadIcon(
                                                                packageManager, true, 1),
                                                        userHandle))
                                        != null) {
                            appViewHolder.appIcon.setImageDrawable(badgedIconForUser);
                        }
                    } catch (RemoteException e) {
                        Log.d(
                                "SwitchForIndividualAppsSettings",
                                "Error while building userInfo for uid " + appViewHolder.uid,
                                e);
                        e.printStackTrace();
                    }
                } else if (i2 >= 100000 || appEntry.icon != null) {
                    Drawable drawable = appEntry.icon;
                    if (drawable != null) {
                        appViewHolder.appIcon.setImageDrawable(drawable);
                    }
                } else {
                    try {
                        Drawable applicationIcon =
                                switchForIndividualAppsSettings
                                        .getPackageManager()
                                        .getApplicationIcon(appEntry.info.packageName);
                        if (applicationIcon != null) {
                            appViewHolder.appIcon.setImageDrawable(applicationIcon);
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.d(
                                "SwitchForIndividualAppsSettings",
                                "onBindViewHolder - NameNotFoundException " + e2);
                        e2.printStackTrace();
                    }
                }
                appViewHolder.appName.setText(appEntry.label);
                boolean contains =
                        switchForIndividualAppsSettings.mSwitchEnabledUidList.contains(
                                Integer.valueOf(appViewHolder.uid));
                SwitchCompat switchCompat = appViewHolder.appSwitch;
                switchCompat.setChecked(contains);
                switchCompat.setOnCheckedChangeListener(
                        new CompoundButton
                                .OnCheckedChangeListener() { // from class:
                                                             // com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.SwitchableAppsAdapter.2
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(
                                    CompoundButton compoundButton, boolean z) {
                                Log.d(
                                        "SwitchForIndividualAppsSettings",
                                        "onCheckedChanged(" + appViewHolder.uid + ") - " + z);
                                if (SwitchForIndividualAppsSettings.this.mSwitchEnabledUidList
                                                .contains(Integer.valueOf(appViewHolder.uid))
                                        != z) {
                                    Log.d(
                                            "SwitchForIndividualAppsSettings",
                                            "onCheckedChanged changed");
                                    if (z) {
                                        if (SwitchForIndividualAppsSettings.this
                                                .mSwitchEnabledUidList.contains(
                                                Integer.valueOf(appViewHolder.uid))) {
                                            return;
                                        }
                                        SwitchForIndividualAppsSettings.this.mSwitchEnabledUidList
                                                .add(Integer.valueOf(appViewHolder.uid));
                                        SwitchForIndividualAppsSettings.this
                                                .sendListChangedBroadcast();
                                        return;
                                    }
                                    if (SwitchForIndividualAppsSettings.this.mSwitchEnabledUidList
                                            .contains(Integer.valueOf(appViewHolder.uid))) {
                                        ArrayList arrayList =
                                                SwitchForIndividualAppsSettings.this
                                                        .mSwitchEnabledUidList;
                                        arrayList.remove(
                                                arrayList.indexOf(
                                                        Integer.valueOf(appViewHolder.uid)));
                                        SwitchForIndividualAppsSettings.this
                                                .sendListChangedBroadcast();
                                    }
                                }
                            }
                        });
                appViewHolder.view.semSetRoundedCorners(0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.sec_preference_app, viewGroup, false);
            AppViewHolder appViewHolder = new AppViewHolder(inflate);
            appViewHolder.view = inflate;
            ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(android.R.id.widget_frame);
            if (viewGroup2 != null) {
                LayoutInflater.from(inflate.getContext())
                        .inflate(R.layout.sec_widget_preference_switch, viewGroup2, true);
                SwitchCompat switchCompat =
                        (SwitchCompat) inflate.findViewById(android.R.id.switch_widget);
                appViewHolder.appSwitch = switchCompat;
                switchCompat.setVisibility(0);
            }
            appViewHolder.appIcon = (PreferenceImageView) inflate.findViewById(android.R.id.icon);
            appViewHolder.appName = (TextView) inflate.findViewById(android.R.id.title);
            return appViewHolder;
        }
    }

    /* renamed from: -$$Nest$mupdateConfigureChanges, reason: not valid java name */
    public static void m1344$$Nest$mupdateConfigureChanges(
            SwitchForIndividualAppsSettings switchForIndividualAppsSettings) {
        WifiManager wifiManager =
                (WifiManager)
                        switchForIndividualAppsSettings
                                .getActivity()
                                .getSystemService(ImsProfile.PDN_WIFI);
        int updateSmartNetworkSwitchAvailability =
                Utils.updateSmartNetworkSwitchAvailability(
                        switchForIndividualAppsSettings.getActivity());
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                updateSmartNetworkSwitchAvailability,
                "mMobileDataObserver - ",
                "SwitchForIndividualAppsSettings");
        if ((wifiManager == null || wifiManager.isWifiEnabled())
                && updateSmartNetworkSwitchAvailability == 1) {
            return;
        }
        if (!Utils.isTablet()
                || switchForIndividualAppsSettings.getFragmentManager() == null
                || switchForIndividualAppsSettings.getFragmentManager().getBackStackEntryCount()
                        <= 0) {
            switchForIndividualAppsSettings.getActivity().finish();
        } else {
            switchForIndividualAppsSettings.getFragmentManager().popBackStack();
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings$3] */
    public SwitchForIndividualAppsSettings() {
        final int i = 0;
        this.mMobileDataObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.1
                    public final /* synthetic */ SwitchForIndividualAppsSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "mMobileDataObserver - onChange - ",
                                        "SwitchForIndividualAppsSettings",
                                        z);
                                SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                        this.this$0);
                                break;
                            default:
                                SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                        this.this$0);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDataRoamingObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.1
                    public final /* synthetic */ SwitchForIndividualAppsSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "mMobileDataObserver - onChange - ",
                                        "SwitchForIndividualAppsSettings",
                                        z);
                                SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                        this.this$0);
                                break;
                            default:
                                SwitchForIndividualAppsSettings.m1344$$Nest$mupdateConfigureChanges(
                                        this.this$0);
                                break;
                        }
                    }
                };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0107 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.io.BufferedReader] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0049 -> B:16:0x00fa). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.json.JSONObject readJSONObjectFromFile() {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.readJSONObjectFromFile():org.json.JSONObject");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x004e -> B:14:0x00ed). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList readSwitchEnabledUidInfoList() {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.SwitchForIndividualAppsSettings.readSwitchEnabledUidInfoList():java.util.ArrayList");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final PackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = this.mContext.getPackageManager();
        }
        return this.mPackageManager;
    }

    public final boolean isSwitchForIndividualAppsEnabled() {
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_switch_for_individual_apps_enabled",
                        0)
                == 1;
    }

    public final void loadAppList$1() {
        this.mApps = this.mSession.getAllApps();
        int i = 0;
        while (i < ((ArrayList) this.mApps).size()) {
            ApplicationsState.AppEntry appEntry =
                    (ApplicationsState.AppEntry) ((ArrayList) this.mApps).get(i);
            if (ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER.filterApp(appEntry)) {
                int i2 = appEntry.info.uid;
                if (i2 < 10000) {
                    ((ArrayList) this.mApps).remove(i);
                } else {
                    ArrayList arrayList = this.mSwitchablePackageList;
                    if (arrayList == null || !arrayList.contains(Integer.valueOf(i2))) {
                        ((ArrayList) this.mApps).remove(i);
                    } else {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                new StringBuilder("loadAppList - "),
                                appEntry.info.packageName,
                                "SwitchForIndividualAppsSettings");
                        i++;
                    }
                }
            } else {
                ((ArrayList) this.mApps).remove(i);
            }
            i--;
            i++;
        }
        List list = this.mApps;
        if (list == null || ((ArrayList) list).isEmpty()) {
            return;
        }
        ((ArrayList) this.mApps).sort(new AnonymousClass4());
        SwitchableAppsAdapter switchableAppsAdapter = this.mAdapter;
        List list2 = this.mApps;
        if (list2 != null) {
            ((ArrayList) switchableAppsAdapter.mAppList).clear();
            ((ArrayList) switchableAppsAdapter.mAppList).addAll(list2);
        } else {
            switchableAppsAdapter.getClass();
        }
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onSwitchChanged - ", "SwitchForIndividualAppsSettings", z);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "wifi_switch_for_individual_apps_enabled",
                z ? 1 : 0);
        if (z && this.mSuggestionUid > 0) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onSwitchChanged - enable by suggestion mode - "),
                    this.mSuggestionUid,
                    "SwitchForIndividualAppsSettings");
            if (!this.mSwitchEnabledUidList.contains(Integer.valueOf(this.mSuggestionUid))) {
                this.mSwitchEnabledUidList.add(Integer.valueOf(this.mSuggestionUid));
            }
            this.mSuggestionUid = 0;
            sendListChangedBroadcast();
        }
        updateListView$1();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mSession =
                ApplicationsState.getInstance(
                                (Application) getPrefContext().getApplicationContext())
                        .newSession(this, getLifecycle());
        this.mSwitchablePackageList = readSwitchablePackageList();
        this.mSwitchEnabledUidList = readSwitchEnabledUidInfoList();
        this.mSuggestionUid = getIntent().getIntExtra("UID", 0);
        this.mDetectionMode =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_switch_for_individual_apps_detection_mode",
                        0);
        Log.d("SwitchForIndividualAppsSettings", "mDetectionMode - " + this.mDetectionMode);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(
                    getResources()
                            .getString(
                                    R.string.wifi_smart_network_switch_switch_for_individual_apps));
        }
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.ANY_DATA_STATE");
        this.mFilter.addAction("android.intent.action.AIRPLANE_MODE");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        Log.d("SwitchForIndividualAppsSettings", "onCreateView");
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) this.mContext).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.setChecked(isSwitchForIndividualAppsEnabled());
        this.mSwitchBar.setPadding(24, 0, 24, 0);
        this.mSwitchBar.show();
        View inflate =
                layoutInflater.inflate(R.layout.wifi_switch_for_individual_apps, (ViewGroup) null);
        this.mRootView = inflate;
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.apps_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mAdapter = new SwitchableAppsAdapter(this, this.mApps);
        isSwitchForIndividualAppsEnabled();
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.seslSetGoToTopEnabled(true);
        this.mRecyclerView.seslSetFastScrollerEnabled(true);
        this.mRecyclerView.seslSetFillBottomEnabled(true);
        this.mRecyclerView.semSetRoundedCorners(15);
        this.mRecyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mRecyclerView.setNestedScrollingEnabled(true);
        this.mRecyclerView.setImportantForAccessibility(2);
        this.mEmptyView = this.mRootView.findViewById(R.id.empty_list);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        updateListView$1();
        return this.mRootView;
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {
        loadAppList$1();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.d("SwitchForIndividualAppsSettings", "onPause");
        super.onPause();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        getActivity().unregisterReceiver(this.mReceiver);
        getActivity().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
        getActivity().getContentResolver().unregisterContentObserver(this.mDataRoamingObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.d("SwitchForIndividualAppsSettings", "onResume");
        super.onResume();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.setChecked(isSwitchForIndividualAppsEnabled());
        this.mSwitchablePackageList = readSwitchablePackageList();
        this.mSwitchEnabledUidList = readSwitchEnabledUidInfoList();
        updateListView$1();
        loadAppList$1();
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        this.mFilter,
                        "android.permission.CHANGE_NETWORK_STATE",
                        null);
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mobile_data"), false, this.mMobileDataObserver);
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("data_roaming"),
                        false,
                        this.mDataRoamingObserver);
    }

    public final ArrayList readSwitchablePackageList() {
        Log.d("SwitchForIndividualAppsSettings", "readSwitchablePackageList");
        ArrayList arrayList = new ArrayList();
        try {
            JSONObject readJSONObjectFromFile = readJSONObjectFromFile();
            if (readJSONObjectFromFile != null) {
                JSONArray jSONArray = readJSONObjectFromFile.getJSONArray("Data");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    int i2 = jSONObject.getInt("UID");
                    String string = jSONObject.getString("PackageName");
                    boolean z = jSONObject.getBoolean("ChattingApp");
                    boolean z2 = jSONObject.getBoolean("Switchable");
                    boolean z3 = jSONObject.getBoolean("SystemApp");
                    int i3 = this.mDetectionMode;
                    if (i3 == 0) {
                        if (!z) {}
                        arrayList.add(Integer.valueOf(i2));
                        Log.d(
                                "SwitchForIndividualAppsSettings",
                                "readSwitchablePackageList - add " + string);
                    } else if (i3 != 1) {
                        if (i3 == 2 && z3) {
                            arrayList.add(Integer.valueOf(i2));
                            Log.d(
                                    "SwitchForIndividualAppsSettings",
                                    "readSwitchablePackageList - add " + string);
                        }
                    } else if (z2) {
                        arrayList.add(Integer.valueOf(i2));
                        Log.d(
                                "SwitchForIndividualAppsSettings",
                                "readSwitchablePackageList - add " + string);
                    }
                }
            }
        } catch (JSONException e) {
            Log.w(
                    "SwitchForIndividualAppsSettings",
                    "readSwitchablePackageList - JSONException " + e);
            e.printStackTrace();
        }
        return arrayList;
    }

    public final void sendListChangedBroadcast() {
        Intent intent =
                new Intent(
                        "com.samsung.android.net.wifi.WIFI_TCP_MONITOR_SWITCHABLE_APP_LIST_CHANGED");
        intent.putExtra("UID_LIST", this.mSwitchEnabledUidList);
        getContext().sendBroadcast(intent);
    }

    public final void updateListView$1() {
        if (this.mEmptyView == null || this.mRecyclerView == null) {
            Log.w(
                    "SwitchForIndividualAppsSettings",
                    "updateListView - null view - " + this.mEmptyView + ", " + this.mRecyclerView);
            return;
        }
        if (isSwitchForIndividualAppsEnabled()) {
            this.mEmptyView.setVisibility(8);
            this.mRecyclerView.setVisibility(0);
        } else {
            this.mRecyclerView.setVisibility(8);
            this.mEmptyView.setVisibility(0);
        }
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
