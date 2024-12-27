package com.samsung.android.settings.smartpopupview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;

import androidx.apppickerview.widget.AppPickerView;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.appdata.GroupAppData;
import androidx.picker.widget.SeslAppPickerView;

import com.android.settings.R;

import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SmartPopupViewSettings$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SmartPopupViewSettings f$0;

    public /* synthetic */ SmartPopupViewSettings$$ExternalSyntheticLambda0(
            SmartPopupViewSettings smartPopupViewSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = smartPopupViewSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final SmartPopupViewSettings smartPopupViewSettings = this.f$0;
        switch (i) {
            case 0:
                List installedPackages =
                        AppPickerView.getInstalledPackages(smartPopupViewSettings.mAppContext);
                List packageStrListFromDB =
                        SmartPopupViewUtil.getPackageStrListFromDB(
                                smartPopupViewSettings.mAppContext);
                boolean isDifferentStringList =
                        SmartPopupViewSettings.isDifferentStringList(
                                installedPackages, smartPopupViewSettings.mInstalledPackageList);
                boolean isDifferentStringList2 =
                        SmartPopupViewSettings.isDifferentStringList(
                                packageStrListFromDB, smartPopupViewSettings.mEnabledPackageList);
                if (isDifferentStringList || isDifferentStringList2) {
                    smartPopupViewSettings.mInstalledPackageList = installedPackages;
                    smartPopupViewSettings.mEnabledPackageList = packageStrListFromDB;
                    smartPopupViewSettings.mExecutor.submit(
                            new SmartPopupViewSettings$$ExternalSyntheticLambda0(
                                    smartPopupViewSettings, 1));
                    break;
                }
            default:
                HashSet hashSet = SmartPopupViewSettings.mSuggestedPackageList;
                smartPopupViewSettings.getClass();
                final ArrayList arrayList = new ArrayList();
                Iterator it = ((ArrayList) smartPopupViewSettings.mInstalledPackageList).iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (SmartPopupViewSettings.mSuggestedPackageList.contains(str)) {
                        arrayList.add(str);
                    }
                }
                ArrayList arrayList2 = new ArrayList(smartPopupViewSettings.mInstalledPackageList);
                arrayList2.removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.smartpopupview.SmartPopupViewSettings$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return arrayList.contains((String) obj);
                            }
                        });
                final ArrayList arrayList3 = new ArrayList();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    if (SmartPopupViewSettings.mSuggestedPackageList.contains(str2)) {
                        try {
                            PackageManager packageManager =
                                    smartPopupViewSettings.getPackageManager();
                            ApplicationInfo applicationInfo =
                                    packageManager.getApplicationInfo(str2, 128);
                            int appId = UserHandle.getAppId(applicationInfo.uid);
                            String str3 = applicationInfo.packageName;
                            String str4 = applicationInfo.className;
                            AppInfo.Companion companion = AppInfo.Companion;
                            arrayList3.add(
                                    new AppData.ListSwitchAppDataBuilder(
                                                    AppInfo.Companion.obtain(appId, str3, str4))
                                            .setSelected(
                                                    smartPopupViewSettings.mEnabledPackageList
                                                            .contains(applicationInfo.packageName))
                                            .setIcon(applicationInfo.loadIcon(packageManager))
                                            .setLabel(
                                                    applicationInfo
                                                            .loadLabel(packageManager)
                                                            .toString())
                                            .build());
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                List list = smartPopupViewSettings.mEnabledPackageList;
                Context context = smartPopupViewSettings.mAppContext;
                final ArrayList arrayList4 = new ArrayList();
                PackageManager packageManager2 = context.getPackageManager();
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    String str5 = (String) it3.next();
                    Intent addCategory =
                            new Intent("android.intent.action.MAIN")
                                    .addCategory("android.intent.category.LAUNCHER");
                    try {
                        addCategory.setPackage(str5);
                        Iterator<ResolveInfo> it4 =
                                packageManager2.queryIntentActivities(addCategory, 0).iterator();
                        while (true) {
                            if (it4.hasNext()) {
                                ResolveInfo next = it4.next();
                                if ((new SemMultiWindowManager().getSupportedModes(next) & 2)
                                        != 0) {
                                    int appId2 =
                                            UserHandle.getAppId(
                                                    next.activityInfo.applicationInfo.uid);
                                    String str6 = next.activityInfo.name;
                                    AppInfo.Companion companion2 = AppInfo.Companion;
                                    arrayList4.add(
                                            new AppData.ListSwitchAppDataBuilder(
                                                            AppInfo.Companion.obtain(
                                                                    appId2, str5, str6))
                                                    .setSelected(list.contains(str5))
                                                    .setIcon(
                                                            next.activityInfo.applicationInfo
                                                                    .loadIcon(packageManager2))
                                                    .setLabel(
                                                            next.activityInfo
                                                                    .applicationInfo
                                                                    .loadLabel(packageManager2)
                                                                    .toString())
                                                    .build());
                                }
                            }
                        }
                    } catch (IllegalArgumentException unused) {
                    }
                }
                smartPopupViewSettings.mActivity.runOnUiThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.smartpopupview.SmartPopupViewSettings$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                List asList;
                                SmartPopupViewSettings smartPopupViewSettings2 =
                                        SmartPopupViewSettings.this;
                                List list2 = arrayList3;
                                List datas = arrayList4;
                                SeslAppPickerView seslAppPickerView =
                                        smartPopupViewSettings2.mAppPickerView;
                                if (list2.isEmpty()) {
                                    AppData.GroupAppDataBuilder groupAppDataBuilder =
                                            new AppData.GroupAppDataBuilder("key1");
                                    groupAppDataBuilder.label = ApnSettings.MVNO_NONE;
                                    Intrinsics.checkNotNullParameter(datas, "datas");
                                    groupAppDataBuilder.mAppInfoDataList = datas;
                                    asList = Arrays.asList(groupAppDataBuilder.build());
                                } else {
                                    String string =
                                            smartPopupViewSettings2.getActivity() == null
                                                    ? ApnSettings.MVNO_NONE
                                                    : smartPopupViewSettings2
                                                            .getActivity()
                                                            .getString(R.string.str_suggested_app);
                                    AppData.GroupAppDataBuilder groupAppDataBuilder2 =
                                            new AppData.GroupAppDataBuilder("key1");
                                    groupAppDataBuilder2.label = string;
                                    groupAppDataBuilder2.mAppInfoDataList = list2;
                                    GroupAppData build = groupAppDataBuilder2.build();
                                    AppData.GroupAppDataBuilder groupAppDataBuilder3 =
                                            new AppData.GroupAppDataBuilder("key2");
                                    groupAppDataBuilder3.label = ApnSettings.MVNO_NONE;
                                    Intrinsics.checkNotNullParameter(datas, "datas");
                                    groupAppDataBuilder3.mAppInfoDataList = datas;
                                    asList = Arrays.asList(build, groupAppDataBuilder3.build());
                                }
                                seslAppPickerView.submitList(asList);
                                smartPopupViewSettings2.mLoadingViewController.showContent(false);
                            }
                        });
                break;
        }
    }
}
