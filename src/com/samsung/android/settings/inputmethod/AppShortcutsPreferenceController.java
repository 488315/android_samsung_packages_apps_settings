package com.samsung.android.settings.inputmethod;

import android.app.AlertDialog;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.helper.SeslAppInfoDataHelper;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.AppInfoDataImpl;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.SeslAppPickerGridView;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppShortcutsPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceClickListener, LifecycleObserver {
    private static final String TAG = "AppShortcutsPreferenceController";
    List<AppInfoData> mAppInfoData;
    AlertDialog mDialog;
    private boolean mInitialLaunch;
    List<AppInfoData> mIntentAppInfoData;
    private PackageManager mPM;
    private Fragment mParent;
    private SecPreferenceScreen mPrefCmdA;
    private SecPreferenceScreen mPrefCmdB;
    private SecPreferenceScreen mPrefCmdC;
    private SecPreferenceScreen mPrefCmdD;
    private SecPreferenceScreen mPrefCmdE;
    private SecPreferenceScreen mPrefCmdF;
    private SecPreferenceScreen mPrefCmdH;
    private SecPreferenceScreen mPrefCmdI;
    private SecPreferenceScreen mPrefCmdJ;
    private SecPreferenceScreen mPrefCmdK;
    private SecPreferenceScreen mPrefCmdM;
    private SecPreferenceScreen mPrefCmdP;
    private SecPreferenceScreen mPrefCmdR;
    private SecPreferenceScreen mPrefCmdS;
    private SecPreferenceScreen mPrefCmdZ;
    private List<SecPreferenceScreen> mPrefList;
    private PreferenceScreen mScreen;
    private SecPreferenceScreen mSelectedPref;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((AppInfoData) obj).getLabel().compareTo(((AppInfoData) obj2).getLabel());
        }
    }

    public AppShortcutsPreferenceController(Context context, String str) {
        super(context, str);
        this.mInitialLaunch = false;
        this.mPrefList = null;
        this.mPM = this.mContext.getPackageManager();
        initAppInfoData();
    }

    private void addFinder() {
        AppInfoDataImpl appInfoDataImpl =
                new AppInfoDataImpl(
                        AppInfo.obtain(
                                "com.sec.android.app.launcher",
                                "com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity"));
        appInfoDataImpl.icon =
                getIconForFinder("com.sec.android.app.launcher").loadDrawable(this.mContext);
        appInfoDataImpl.label = this.mContext.getString(R.string.app_shortcuts_applications_search);
        this.mAppInfoData.add(0, appInfoDataImpl);
    }

    private void addHome() {
        try {
            AppInfoDataImpl appInfoDataImpl =
                    new AppInfoDataImpl(
                            AppInfo.obtain(
                                    "com.sec.android.app.launcher",
                                    "com.sec.android.app.launcher/com.sec.android.app.launcher.activities.LauncherActivity"));
            appInfoDataImpl.icon = this.mPM.getApplicationIcon("com.sec.android.app.launcher");
            appInfoDataImpl.label =
                    (String)
                            this.mPM
                                    .getApplicationInfo("com.sec.android.app.launcher", 0)
                                    .loadLabel(this.mPM);
            this.mAppInfoData.add(0, appInfoDataImpl);
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.w(TAG, "failed to get launcher icon and label");
        }
    }

    private void addNone(SeslAppInfoDataHelper seslAppInfoDataHelper) {
        AppInfoData appInfoData =
                (AppInfoData)
                        ((ArrayList) seslAppInfoDataHelper.getPackages(Arrays.asList("None")))
                                .get(0);
        appInfoData.setIcon(this.mContext.getDrawable(R.drawable.none_icon));
        appInfoData.setLabel(this.mContext.getString(R.string.app_shortcuts_select_none));
        this.mAppInfoData.add(0, appInfoData);
    }

    private String getDefaultPackageName(String str) {
        List roleHolders =
                ((RoleManager) this.mContext.getSystemService(RoleManager.class))
                        .getRoleHolders(str);
        if (roleHolders.isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "getDefaultPackageName role : ", str, " default : ");
        m.append((String) roleHolders.get(0));
        SemLog.d(TAG, m.toString());
        return (String) roleHolders.get(0);
    }

    private Icon getIconForFinder(String str) {
        try {
            try {
                int identifier =
                        this.mPM
                                .getResourcesForApplication(str)
                                .getIdentifier("finder_search_icon", "mipmap", str);
                if (identifier != 0) {
                    return Icon.createWithResource(str, identifier);
                }
                return null;
            } catch (PackageManager.NameNotFoundException unused) {
                SemLog.e(TAG, str + " not found, failed to get app icon");
                return null;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    private String getPackageNameForIntentCategory(String str) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(str);
        ResolveInfo resolveActivity =
                this.mContext.getPackageManager().resolveActivity(intent, 65536);
        return (resolveActivity == null
                        || "com.android.internal.app.ResolverActivity"
                                .equals(resolveActivity.activityInfo.name))
                ? ApnSettings.MVNO_NONE
                : resolveActivity.activityInfo.applicationInfo.packageName;
    }

    private void initAppInfoData() {
        SeslAppInfoDataHelper seslAppInfoDataHelper = new SeslAppInfoDataHelper(this.mContext);
        List<AppInfoData> dataList =
                seslAppInfoDataHelper.appDataListFactory.getDataList(
                        seslAppInfoDataHelper.mItemType);
        LogTagHelperKt.info(seslAppInfoDataHelper, "getPackages=" + dataList.size());
        this.mAppInfoData = dataList;
        final int i = 0;
        dataList.removeIf(
                new Predicate() { // from class:
                                  // com.samsung.android.settings.inputmethod.AppShortcutsPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$initAppInfoData$0;
                        boolean lambda$initAppInfoData$1;
                        AppInfoData appInfoData = (AppInfoData) obj;
                        switch (i) {
                            case 0:
                                lambda$initAppInfoData$0 =
                                        AppShortcutsPreferenceController.lambda$initAppInfoData$0(
                                                appInfoData);
                                return lambda$initAppInfoData$0;
                            default:
                                lambda$initAppInfoData$1 =
                                        AppShortcutsPreferenceController.lambda$initAppInfoData$1(
                                                appInfoData);
                                return lambda$initAppInfoData$1;
                        }
                    }
                });
        final int i2 = 1;
        this.mAppInfoData.removeIf(
                new Predicate() { // from class:
                                  // com.samsung.android.settings.inputmethod.AppShortcutsPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$initAppInfoData$0;
                        boolean lambda$initAppInfoData$1;
                        AppInfoData appInfoData = (AppInfoData) obj;
                        switch (i2) {
                            case 0:
                                lambda$initAppInfoData$0 =
                                        AppShortcutsPreferenceController.lambda$initAppInfoData$0(
                                                appInfoData);
                                return lambda$initAppInfoData$0;
                            default:
                                lambda$initAppInfoData$1 =
                                        AppShortcutsPreferenceController.lambda$initAppInfoData$1(
                                                appInfoData);
                                return lambda$initAppInfoData$1;
                        }
                    }
                });
        addHome();
        addFinder();
        this.mAppInfoData.sort(new AppComparator());
        addNone(seslAppInfoDataHelper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$initAppInfoData$0(AppInfoData appInfoData) {
        return appInfoData.getPackageName().equals("com.google.android.apps.googleassistant");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$initAppInfoData$1(AppInfoData appInfoData) {
        return appInfoData.getPackageName().equals("com.google.android.googlequicksearchbox")
                && !appInfoData
                        .getActivityName()
                        .equals("com.google.android.googlequicksearchbox.SearchActivity");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$showDialog$2(View view, AppInfo appInfo) {
        try {
            this.mSelectedPref.setSummary(
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo(appInfo.packageName, 0)
                            .loadLabel(this.mContext.getPackageManager()));
            if ("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity"
                    .equals(appInfo.activityName)) {
                Settings.System.putString(
                        this.mContext.getContentResolver(),
                        (String)
                                ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                        .get(this.mPrefList.indexOf(this.mSelectedPref)),
                        "com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity");
            } else if ("com.sec.android.app.launcher".equals(appInfo.packageName)) {
                Settings.System.putString(
                        this.mContext.getContentResolver(),
                        (String)
                                ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                        .get(this.mPrefList.indexOf(this.mSelectedPref)),
                        "com.sec.android.app.launcher");
            } else {
                Settings.System.putString(
                        this.mContext.getContentResolver(),
                        (String)
                                ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                        .get(this.mPrefList.indexOf(this.mSelectedPref)),
                        appInfo.packageName);
            }
            refreshUi();
        } catch (PackageManager.NameNotFoundException e) {
            SemLog.w(TAG, "item click : " + e);
            if (appInfo.packageName.equals("None")) {
                this.mSelectedPref.setSummary(R.string.app_shortcuts_select_none);
                Settings.System.putString(
                        this.mContext.getContentResolver(),
                        (String)
                                ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                        .get(this.mPrefList.indexOf(this.mSelectedPref)),
                        "None");
            } else if (appInfo.packageName.equals("More")) {
                this.mDialog.dismiss();
                showDialog(this.mAppInfoData);
                return false;
            }
        }
        this.mDialog.dismiss();
        return false;
    }

    private void showDialog(List<AppInfoData> list) {
        View inflate =
                ((LayoutInflater) this.mContext.getSystemService("layout_inflater"))
                        .inflate(R.layout.app_shortcuts_select_app_layout, (ViewGroup) null);
        SeslAppPickerGridView seslAppPickerGridView =
                (SeslAppPickerGridView) inflate.findViewById(R.id.app_picker_view);
        seslAppPickerGridView.seslSetFastScrollerEnabled(true);
        seslAppPickerGridView.seslSetGoToTopEnabled(true);
        seslAppPickerGridView.seslSetFillBottomEnabled(false);
        seslAppPickerGridView.submitList(list);
        seslAppPickerGridView.setOnItemClickEventListener(
                new AppPickerEvent$OnItemClickEventListener() { // from class:
                                                                // com.samsung.android.settings.inputmethod.AppShortcutsPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
                    public final boolean onClick(View view, AppInfo appInfo) {
                        boolean lambda$showDialog$2;
                        lambda$showDialog$2 =
                                AppShortcutsPreferenceController.this.lambda$showDialog$2(
                                        view, appInfo);
                        return lambda$showDialog$2;
                    }
                });
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setView(inflate)
                        .setTitle(R.string.app_shortcuts_select_app_title)
                        .create();
        this.mDialog = create;
        create.show();
    }

    private void showIntentDialog(Intent intent) {
        SeslAppInfoDataHelper seslAppInfoDataHelper = new SeslAppInfoDataHelper(this.mContext);
        this.mIntentAppInfoData = new ArrayList();
        List<ResolveInfo> queryIntentActivities =
                this.mContext.getPackageManager().queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = queryIntentActivities.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().activityInfo.packageName);
        }
        List packages = seslAppInfoDataHelper.getPackages(arrayList);
        this.mIntentAppInfoData = packages;
        if (((ArrayList) packages).isEmpty()) {
            showDialog(this.mAppInfoData);
            return;
        }
        AppInfoData appInfoData =
                (AppInfoData)
                        ((ArrayList) seslAppInfoDataHelper.getPackages(Arrays.asList("More")))
                                .get(0);
        this.mIntentAppInfoData.add(appInfoData);
        appInfoData.setIcon(this.mContext.getDrawable(R.drawable.ic_more));
        appInfoData.setLabel(this.mContext.getString(R.string.app_shortcuts_select_more));
        showDialog(this.mIntentAppInfoData);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mParent == null) {
            return;
        }
        this.mScreen = preferenceScreen;
        refreshUi();
        this.mInitialLaunch = true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mInitialLaunch = false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        SecPreferenceScreen secPreferenceScreen = (SecPreferenceScreen) preference;
        this.mSelectedPref = secPreferenceScreen;
        if (!secPreferenceScreen
                .getSummary()
                .equals(
                        this.mContext.getString(
                                R.string.app_shortcuts_need_to_select_app_summary))) {
            showDialog(this.mAppInfoData);
            return true;
        }
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(),
                        (String)
                                ((ArrayList) AppShortcutsConstants.SETTINGS_LIST)
                                        .get(this.mPrefList.indexOf(this.mSelectedPref)));
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(string);
        showIntentDialog(intent);
        return true;
    }

    public void refreshUi() {
        this.mPrefCmdA = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_a");
        this.mPrefCmdB = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_b");
        this.mPrefCmdC = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_c");
        this.mPrefCmdD = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_d");
        this.mPrefCmdE = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_e");
        this.mPrefCmdF = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_f");
        this.mPrefCmdH = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_h");
        this.mPrefCmdI = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_i");
        this.mPrefCmdJ = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_j");
        this.mPrefCmdK = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_k");
        this.mPrefCmdM = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_m");
        this.mPrefCmdP = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_p");
        this.mPrefCmdR = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_r");
        this.mPrefCmdS = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_s");
        this.mPrefCmdZ = (SecPreferenceScreen) this.mScreen.findPreference("app_shortcuts_cmd_z");
        ArrayList<SecPreferenceScreen> arrayList =
                new ArrayList(
                        Arrays.asList(
                                this.mPrefCmdA,
                                this.mPrefCmdB,
                                this.mPrefCmdC,
                                this.mPrefCmdD,
                                this.mPrefCmdE,
                                this.mPrefCmdF,
                                this.mPrefCmdH,
                                this.mPrefCmdI,
                                this.mPrefCmdJ,
                                this.mPrefCmdK,
                                this.mPrefCmdM,
                                this.mPrefCmdP,
                                this.mPrefCmdR,
                                this.mPrefCmdS,
                                this.mPrefCmdZ));
        this.mPrefList = arrayList;
        for (SecPreferenceScreen secPreferenceScreen : arrayList) {
            if (secPreferenceScreen != null) {
                secPreferenceScreen.setOnPreferenceClickListener(this);
                SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
            }
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Iterator it = ((ArrayList) AppShortcutsConstants.SETTINGS_LIST).iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            SecPreferenceScreen secPreferenceScreen2 = this.mPrefList.get(i);
            if (secPreferenceScreen2 != null) {
                String string = Settings.System.getString(this.mContext.getContentResolver(), str);
                if (!TextUtils.isEmpty(string)) {
                    if (string.equals(
                            "com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
                        secPreferenceScreen2.setSummary(R.string.app_shortcuts_applications_search);
                    } else {
                        if (string.contains("android.intent.category.")) {
                            string = getPackageNameForIntentCategory(string);
                        } else if (string.contains("android.app.role.")) {
                            string = getDefaultPackageName(string);
                        }
                        try {
                            secPreferenceScreen2.setSummary(
                                    packageManager
                                            .getApplicationInfo(string, 0)
                                            .loadLabel(packageManager));
                        } catch (PackageManager.NameNotFoundException unused) {
                            if (string.equals("None")) {
                                secPreferenceScreen2.setSummary(R.string.app_shortcuts_select_none);
                            } else {
                                secPreferenceScreen2.setSummary(
                                        R.string.app_shortcuts_need_to_select_app_summary);
                            }
                        }
                    }
                }
            }
            i++;
        }
        SecPreferenceScreen secPreferenceScreen3 = this.mPrefCmdA;
        if (secPreferenceScreen3 != null) {
            this.mScreen.removePreference(secPreferenceScreen3);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(Fragment fragment) {
        this.mParent = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mInitialLaunch) {
            return;
        }
        refreshUi();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
