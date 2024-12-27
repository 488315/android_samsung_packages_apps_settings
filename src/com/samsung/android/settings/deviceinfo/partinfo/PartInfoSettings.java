package com.samsung.android.settings.deviceinfo.partinfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.logging.LoggingHelper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PartInfoSettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_part_info_settings);
    public boolean isExistUnknownPart = false;
    public FragmentActivity mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.partinfo.PartInfoSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return PartInfoUtils.validCheckReplacementFileName(str);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        PreferenceCategoryController preferenceCategoryController =
                new PreferenceCategoryController(context, "part_screen_category");
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new ScreenPreferenceController(context, "part_screen"));
        arrayList2.add(new CoverScreenPreferenceController(context, "part_cover_screen"));
        arrayList2.add(new MainScreenPreferenceController(context, "part_main_screen"));
        arrayList.add(preferenceCategoryController.setChildren(arrayList2));
        PreferenceCategoryController preferenceCategoryController2 =
                new PreferenceCategoryController(context, "part_camera_category");
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new FrontCameraPreferenceController(context, "part_front_camera"));
        arrayList3.add(
                new FrontUltraWideCameraPreferenceController(
                        context, "part_front_ultra_wide_camera"));
        arrayList3.add(new RearWideCameraPreferenceController(context, "part_rear_wide_camera"));
        arrayList3.add(
                new RearUltraWideCameraPreferenceController(
                        context, "part_rear_ultra_wide_camera"));
        arrayList3.add(new RearTeleCameraPreferenceController(context, "part_rear_tele_camera"));
        arrayList3.add(
                new RearSuperTeleCameraPreferenceController(
                        context, "part_rear_super_tele_camera"));
        arrayList3.add(new RearMacroCameraPreferenceController(context, "part_rear_macro_camera"));
        arrayList3.add(new RearDepthCameraPreferenceController(context, "part_rear_depth_camera"));
        arrayList3.add(new InnerCameraPreferenceController(context, "part_inner_camera"));
        arrayList.add(preferenceCategoryController2.setChildren(arrayList3));
        PreferenceCategoryController preferenceCategoryController3 =
                new PreferenceCategoryController(context, "part_other_category");
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new MainBoardPreferenceController(context, "part_main_board"));
        arrayList.add(preferenceCategoryController3.setChildren(arrayList4));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PartInfoSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 57051;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_part_info_settings;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b7 A[LOOP:0: B:7:0x003c->B:25:0x00b7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008e  */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r12) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.partinfo.PartInfoSettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.isExistUnknownPart) {
            menu.add(0, 16, 0, R.string.part_info_option_refresh);
        }
        if (Utils.isSupportContactUs(this.mContext, 170001000L)) {
            menu.add(0, 17, 0, R.string.contact_us_title).setVisible(true).setShowAsAction(0);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16) {
            this.mContext.sendBroadcast(
                    new Intent()
                            .setPackage("com.samsung.android.svcagent")
                            .setAction(
                                    "com.samsung.android.service.svcagent.INTENT_ACTION_VOC_REQUEST"));
            LoggingHelper.insertEventLogging(57051, 57052);
            FragmentActivity fragmentActivity = this.mContext;
            Toast.makeText(
                            fragmentActivity,
                            fragmentActivity.getString(R.string.part_info_toast_refresh),
                            1)
                    .show();
            requireActivity().finish();
            return true;
        }
        if (itemId != 17) {
            return super.onOptionsItemSelected(menuItem);
        }
        LoggingHelper.insertEventLogging(57051, 57053);
        try {
            this.mContext.startActivity(
                    Utils.getContactUsIntent(
                            "com.samsung.android.svcagent",
                            "Part Replacement History",
                            "ruybwmwaqy"));
        } catch (ActivityNotFoundException e) {
            Log.e("PartInfoSettings", "can not link to Contact Us : " + e.getMessage());
        }
        return true;
    }
}
