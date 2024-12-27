package com.samsung.android.settings.account;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.backup.controller.SamsungBackupPreferenceController;
import com.samsung.android.settings.backup.controller.SamsungRestorePreferenceController;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CloudAccountSettings extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_cloud_account_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public Context mContext;
    public final CloudAccountSettings$$ExternalSyntheticLambda0 mDesktopModeListener =
            new SemDesktopModeManager
                    .DesktopModeListener() { // from class:
                                             // com.samsung.android.settings.account.CloudAccountSettings$$ExternalSyntheticLambda0
                public final void onDesktopModeStateChanged(
                        SemDesktopModeState semDesktopModeState) {
                    CloudAccountSettings cloudAccountSettings = CloudAccountSettings.this;
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            CloudAccountSettings.SEARCH_INDEX_DATA_PROVIDER;
                    cloudAccountSettings
                            .getActivity()
                            .runOnUiThread(
                                    new CloudAccountSettings$$ExternalSyntheticLambda1(
                                            0, cloudAccountSettings));
                }
            };
    public SemDesktopModeManager mDesktopModeManager;
    public Handler mHandler;
    public SecRelativeLinkView mRelativeLinkView;
    public SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.account.CloudAccountSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "pref_backUp";
            searchIndexableRaw.title =
                    context.getResources().getString(R.string.samsung_backup_search_title);
            searchIndexableRaw.keywords =
                    context.getResources().getString(R.string.samsung_backup_search_title);
            searchIndexableRaw.screenTitle =
                    context.getResources().getString(R.string.cloud_and_accounts_title);
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "google_backup_settings";
            searchIndexableRaw2.title =
                    context.getResources().getString(R.string.google_backup_search_title);
            searchIndexableRaw2.keywords =
                    context.getResources().getString(R.string.google_backup_search_title);
            searchIndexableRaw2.screenTitle =
                    context.getResources().getString(R.string.cloud_and_accounts_title);
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !Rune.SUPPORT_DISABLE_ACCOUNTS_SETTINGS;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.account.CloudAccountSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        /* JADX WARN: Removed duplicated region for block: B:9:0x0052  */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getStatusLoggingData(android.content.Context r7) {
            /*
                r6 = this;
                java.lang.String r6 = "CloudAccountSettings"
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>()
                android.os.UserHandle r0 = android.os.Process.myUserHandle()
                int r0 = r0.getIdentifier()
                boolean r0 = android.content.ContentResolver.getMasterSyncAutomaticallyAsUser(r0)
                r1 = 4651(0x122b, float:6.517E-42)
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r2 = "0"
                java.lang.String r3 = "1"
                if (r0 == 0) goto L21
                r4 = r3
                goto L22
            L21:
                r4 = r2
            L22:
                com.samsung.android.settings.logging.status.StatusData r5 = new com.samsung.android.settings.logging.status.StatusData
                r5.<init>()
                r5.mStatusValue = r4
                r5.mStatusKey = r1
                r7.add(r5)
                java.lang.String r1 = "backup"
                android.os.IBinder r1 = android.os.ServiceManager.getService(r1)     // Catch: java.lang.SecurityException -> L3d android.os.RemoteException -> L43
                android.app.backup.IBackupManager r1 = android.app.backup.IBackupManager.Stub.asInterface(r1)     // Catch: java.lang.SecurityException -> L3d android.os.RemoteException -> L43
                boolean r1 = r1.isBackupEnabled()     // Catch: java.lang.SecurityException -> L3d android.os.RemoteException -> L43
                goto L4a
            L3d:
                java.lang.String r1 = "SecurityException in backup manager"
                android.util.Log.i(r6, r1)
                goto L49
            L43:
                java.lang.String r1 = "remoteException in backup manager"
                android.util.Log.i(r6, r1)
            L49:
                r1 = 0
            L4a:
                r4 = 4654(0x122e, float:6.522E-42)
                java.lang.String r4 = java.lang.String.valueOf(r4)
                if (r1 == 0) goto L53
                r2 = r3
            L53:
                com.samsung.android.settings.logging.status.StatusData r3 = new com.samsung.android.settings.logging.status.StatusData
                r3.<init>()
                r3.mStatusValue = r2
                r3.mStatusKey = r4
                r7.add(r3)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "Cloud and Account logging 1: "
                r2.<init>(r3)
                r2.append(r0)
                java.lang.String r0 = " 2: "
                r2.append(r0)
                androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(r2, r1, r6)
                return r7
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.account.CloudAccountSettings.AnonymousClass2.getStatusLoggingData(android.content.Context):java.util.List");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Context mContext;

        public SettingsObserver(Handler handler, Context context) {
            super(handler);
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            FragmentActivity activity = CloudAccountSettings.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new CloudAccountSettings$$ExternalSyntheticLambda1(1, this));
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "CloudAccountSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7900;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_cloud_account_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ((SamsungBackupPreferenceController) use(SamsungBackupPreferenceController.class))
                .onActivityResult(i, i2, intent);
        ((SamsungRestorePreferenceController) use(SamsungRestorePreferenceController.class))
                .onActivityResult(i, i2, intent);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SamsungBackupPreferenceController) use(SamsungBackupPreferenceController.class))
                .init(this);
        ((SamsungRestorePreferenceController) use(SamsungRestorePreferenceController.class))
                .init(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        KnoxUtils.removeKnoxCustomSettingsHiddenItems(this);
        Context baseContext = getActivity().getBaseContext();
        this.mContext = baseContext;
        this.mDesktopModeManager =
                (SemDesktopModeManager) baseContext.getSystemService("desktopmode");
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler, this.mContext);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver.mContext.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            if (!Rune.isShopDemo(this.mContext)
                    && !Rune.isLDUModel()
                    && UserHandle.myUserId() == 0) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.flowId = "10016";
                settingsPreferenceFragmentLinkData.callerMetric = 7900;
                Intent intent = new Intent();
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$ResetDashboardFragmentActivity");
                intent.putExtra("menu", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                settingsPreferenceFragmentLinkData.intent = intent;
                settingsPreferenceFragmentLinkData.titleRes = R.string.reset;
                settingsPreferenceFragmentLinkData.topLevelKey = "top_level_general";
                if (!KnoxUtils.checkKnoxCustomSettingsHiddenItem(4096)) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                }
            }
            if (AccountUtils.checkSamsungBackup(this.mContext)) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.flowId = "9019";
                settingsPreferenceFragmentLinkData2.callerMetric = 7900;
                settingsPreferenceFragmentLinkData2.intent =
                        new Intent("com.samsung.android.scloud.SCLOUD_MAIN");
                if (!ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)) {
                    settingsPreferenceFragmentLinkData2.intent.addFlags(268468224);
                }
                settingsPreferenceFragmentLinkData2.titleRes = R.string.cloud_title;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            LinearLayout linearLayout = this.mRelativeLinkView.mLinkContainer;
            if (linearLayout == null || linearLayout.getChildCount() > 0) {
                this.mRelativeLinkView.create(this);
            } else {
                Log.d("RelativeLinkView", "The current screen doesn't have link data.");
            }
        }
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(this.mDesktopModeListener);
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver
                .mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("samsung_cloud_switch_china_delta"),
                        false,
                        settingsObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Trace.beginSection("CloudAccountSettings#onViewCreated");
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                R.id.icon_frame,
                                android.R.id.icon));
        Trace.endSection();
    }
}
