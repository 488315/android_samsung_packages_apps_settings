package com.samsung.android.settings.smartpopupview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.model.AppInfo;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerView;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.LoadingViewController;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmartPopupViewSettings extends SettingsPreferenceFragment {
    public FragmentActivity mActivity;
    public Context mAppContext;
    public SeslAppPickerView mAppPickerView;
    public LoadingViewController mLoadingViewController;
    public static final HashSet mSuggestedPackageList =
            new HashSet(
                    Arrays.asList(
                            "com.samsung.android.messaging",
                            "com.verizon.messaging.vzmsgs",
                            "com.kakao.talk",
                            "jp.naver.line.android",
                            "com.sds.mysinglesquare",
                            "com.samsung.knox.message",
                            "com.whatsapp",
                            "com.google.android.talk",
                            "com.sgiggle.production",
                            "com.nimbuzz",
                            "kik.android",
                            "com.bsb.hike",
                            "com.viber.voip",
                            "com.tencent.mm",
                            "org.telegram.messenger",
                            "com.skype.raider"));
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    public List mEnabledPackageList = new ArrayList();
    public List mInstalledPackageList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.smartpopupview.SmartPopupViewSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            boolean z;
            List packageStrListFromDB = SmartPopupViewUtil.getPackageStrListFromDB(context);
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(67030);
            String valueOf2 = String.valueOf(packageStrListFromDB.size());
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            Iterator it = packageStrListFromDB.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (SmartPopupViewSettings.mSuggestedPackageList.contains((String) it.next())) {
                    z = true;
                    break;
                }
            }
            String valueOf3 = String.valueOf(67031);
            String str = z ? "On" : "Off";
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str;
            statusData2.mStatusKey = valueOf3;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    public static boolean isDifferentStringList(List list, List list2) {
        if (list.size() != list2.size()) {
            return true;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!((String) list.get(size)).equals(list2.get(size))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4350;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_smart_popup_view_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mAppContext = activity.getApplicationContext();
        SeslAppPickerView seslAppPickerView =
                (SeslAppPickerView)
                        ((LayoutPreference) findPreference("smart_popup_view_list"))
                                .mRootView.findViewById(R.id.app_picker_view);
        this.mAppPickerView = seslAppPickerView;
        seslAppPickerView.setAppListOrder(1);
        SeslAppPickerView seslAppPickerView2 = this.mAppPickerView;
        seslAppPickerView2.mOnStateChangeListener = new AnonymousClass2();
        seslAppPickerView2.setItemAnimator(null);
        View findViewById = getView().findViewById(R.id.loading_container);
        findViewById.semSetRoundedCorners(15);
        findViewById.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        LoadingViewController loadingViewController =
                new LoadingViewController(findViewById, getListView(), null);
        this.mLoadingViewController = loadingViewController;
        loadingViewController.mFgHandler.postDelayed(
                loadingViewController.mShowLoadingContainerRunnable, 100L);
        if (getListView() != null) {
            getListView().seslSetGoToTopEnabled(true);
            getListView().setVerticalScrollBarEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.sec_manage_apps, menu);
        if (menu != null) {
            menu.findItem(R.id.manage_memory).setVisible(false);
            menu.findItem(R.id.manage_perms).setVisible(false);
            menu.findItem(R.id.perms_usage).setVisible(false);
            menu.findItem(R.id.special_access).setVisible(false);
            menu.findItem(R.id.sort_order).setVisible(false);
            menu.findItem(R.id.sort_order_alpha).setVisible(false);
            menu.findItem(R.id.sort_order_size).setVisible(false);
            menu.findItem(R.id.show_system).setVisible(false);
            menu.findItem(R.id.hide_system).setVisible(false);
            menu.findItem(R.id.reset_app_preferences).setVisible(false);
            menu.findItem(R.id.sort_order_recent_notification).setVisible(false);
            menu.findItem(R.id.sort_order_frequent_notification).setVisible(false);
            menu.findItem(R.id.delete_all_app_clones).setVisible(false);
        }
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
        if (findItem != null) {
            findItem.setShowAsAction(10);
            findItem.getIcon()
                    .setColorFilter(
                            getResources()
                                    .getColor(R.color.sec_search_magnifier_icon_tint_color, null),
                            PorterDuff.Mode.SRC_IN);
            SearchView searchView = (SearchView) findItem.getActionView();
            LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
            if (linearLayout != null) {
                linearLayout.setPadding(
                        0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
            }
            searchView.setQueryHint(getString(R.string.sec_app_search_title));
            searchView.mOnQueryChangeListener = new AnonymousClass2();
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mExecutor.shutdown();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mExecutor.submit(new SmartPopupViewSettings$$ExternalSyntheticLambda0(this, 0));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.smartpopupview.SmartPopupViewSettings$2, reason: invalid class name */
    public final class AnonymousClass2
            implements AppPickerState$OnStateChangeListener, SearchView.OnQueryTextListener {
        public /* synthetic */ AnonymousClass2() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            SmartPopupViewSettings smartPopupViewSettings = SmartPopupViewSettings.this;
            if (smartPopupViewSettings.mAppPickerView.getAdapter() == null) {
                return false;
            }
            smartPopupViewSettings.mAppPickerView.setSearchFilter(str, null);
            return false;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            SmartPopupViewSettings smartPopupViewSettings = SmartPopupViewSettings.this;
            if (smartPopupViewSettings.mAppPickerView.getAdapter() != null) {
                smartPopupViewSettings.mAppPickerView.setSearchFilter(str, null);
            }
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            String str = appInfo.packageName;
            SmartPopupViewSettings smartPopupViewSettings = SmartPopupViewSettings.this;
            if (z) {
                if (!smartPopupViewSettings.mEnabledPackageList.contains(str)) {
                    smartPopupViewSettings.mEnabledPackageList.add(str);
                }
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "[SmartPopupViewSettings] package on: ", str, "SmartPopupViewSettings");
            } else {
                smartPopupViewSettings.mEnabledPackageList.remove(str);
                Log.i("SmartPopupViewSettings", "[SmartPopupViewSettings] package off: " + str);
                while (smartPopupViewSettings.mEnabledPackageList.contains(str)) {
                    smartPopupViewSettings.mEnabledPackageList.remove(str);
                }
            }
            Context context = smartPopupViewSettings.mAppContext;
            List list = smartPopupViewSettings.mEnabledPackageList;
            StringBuilder sb = new StringBuilder();
            for (String str2 : CollectionUtils.emptyIfNull(list)) {
                if (str2 != null && !str2.equals(ApnSettings.MVNO_NONE)) {
                    sb.append(str2);
                    sb.append(":");
                }
            }
            Settings.Secure.putStringForUser(
                    context.getContentResolver(),
                    "floating_noti_package_list",
                    sb.toString(),
                    UserHandle.myUserId());
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {}
    }
}
