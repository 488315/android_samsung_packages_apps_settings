package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.fragment.app.FragmentActivity;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;

import com.android.settings.accessibility.FlashNotificationsUtil;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment.AnonymousClass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenFlashNotificationCustomAppPickerFragment f$0;

    public /* synthetic */ ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda1(
            ScreenFlashNotificationCustomAppPickerFragment
                    screenFlashNotificationCustomAppPickerFragment,
            int i) {
        this.$r8$classId = i;
        this.f$0 = screenFlashNotificationCustomAppPickerFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScreenFlashNotificationCustomAppPickerFragment
                screenFlashNotificationCustomAppPickerFragment = this.f$0;
        switch (i) {
            case 0:
                HashMap hashMap = ScreenFlashNotificationCustomAppPickerFragment.items;
                FragmentActivity activity =
                        screenFlashNotificationCustomAppPickerFragment.getActivity();
                if (activity != null) {
                    activity.runOnUiThread(
                            new ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda1(
                                    screenFlashNotificationCustomAppPickerFragment, 1));
                    break;
                }
                break;
            default:
                ((HashSet) screenFlashNotificationCustomAppPickerFragment.installedAppSet).clear();
                ScreenFlashNotificationCustomAppPickerFragment.items.clear();
                List screenFlashColorList =
                        FlashNotificationUtil.getScreenFlashColorList(
                                ScreenFlashNotificationCustomAppPickerFragment.context);
                List<ApplicationInfo> unmodifiableList =
                        Collections.unmodifiableList(
                                FlashNotificationUtil.getInstalledAppList(
                                        ScreenFlashNotificationCustomAppPickerFragment.context));
                ArrayList arrayList = new ArrayList();
                PackageManager packageManager =
                        ScreenFlashNotificationCustomAppPickerFragment.context.getPackageManager();
                for (final ApplicationInfo applicationInfo : unmodifiableList) {
                    ((HashSet) screenFlashNotificationCustomAppPickerFragment.installedAppSet)
                            .add(applicationInfo.packageName);
                    FlashNotificationUtil.ScreenFlashInfo screenFlashInfo =
                            (FlashNotificationUtil.ScreenFlashInfo)
                                    screenFlashColorList.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda4
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            ApplicationInfo applicationInfo2 =
                                                                    applicationInfo;
                                                            HashMap hashMap2 =
                                                                    ScreenFlashNotificationCustomAppPickerFragment
                                                                            .items;
                                                            return ((FlashNotificationUtil
                                                                                    .ScreenFlashInfo)
                                                                            obj)
                                                                    .packageName.equals(
                                                                            applicationInfo2
                                                                                    .packageName);
                                                        }
                                                    })
                                            .findFirst()
                                            .orElse(
                                                    new FlashNotificationUtil.ScreenFlashInfo(
                                                            applicationInfo.packageName,
                                                            FlashNotificationsUtil
                                                                    .DEFAULT_SCREEN_FLASH_COLOR,
                                                            false));
                    ScreenFlashNotificationCustomAppPickerFragment.items.put(
                            screenFlashInfo.packageName, screenFlashInfo);
                    String str = applicationInfo.packageName;
                    int i2 = applicationInfo.uid;
                    AppInfo.Companion companion = AppInfo.Companion;
                    arrayList.add(
                            new AppData.ListCheckBoxAppDataBuilder(
                                            AppInfo.Companion.obtain(
                                                    i2, str, ApnSettings.MVNO_NONE))
                                    .setIcon(applicationInfo.loadIcon(packageManager))
                                    .setLabel(applicationInfo.loadLabel(packageManager).toString())
                                    .setSelected(screenFlashInfo.checked)
                                    .build());
                }
                ScreenFlashNotificationCustomAppPickerFragment.appPickerListView.submitList(
                        arrayList);
                screenFlashNotificationCustomAppPickerFragment.loadingViewController.showContent(
                        false);
                screenFlashNotificationCustomAppPickerFragment.updateTitle$1();
                ScreenFlashNotificationCustomAppPickerFragment.appPickerListView
                                .mOnStateChangeListener =
                        screenFlashNotificationCustomAppPickerFragment.new AnonymousClass2();
                break;
        }
    }
}
