package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AppPickerView;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.smartpopupview.SmartPopupViewUtil;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingPopupAppsPreferenceController extends BasePreferenceController
        implements FloatingIconDependentControllerFieldListener {
    private static final String TAG = "FloatingPopupAppsPreferenceController";
    private final Context mContext;
    private SecPreference mPreference;

    public FloatingPopupAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private CharSequence getAppLableFromPackageName(String str)
            throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo =
                this.mContext.getPackageManager().getApplicationInfo(str, 0);
        return applicationInfo != null
                ? applicationInfo.loadLabel(this.mContext.getPackageManager())
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.software.freeform_window_management")
                ? 3
                : 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Context context = this.mContext;
        List installedPackages = AppPickerView.getInstalledPackages(context);
        List<String> packageStrListFromDB = SmartPopupViewUtil.getPackageStrListFromDB(context);
        ArrayList arrayList = new ArrayList();
        for (String str : packageStrListFromDB) {
            if (((ArrayList) installedPackages).contains(str)) {
                arrayList.add(str);
            }
        }
        List filterMultiWindowSupportPackages =
                SmartPopupViewUtil.filterMultiWindowSupportPackages(context, arrayList);
        List filterMultiWindowSupportPackages2 =
                SmartPopupViewUtil.filterMultiWindowSupportPackages(
                        this.mContext, AppPickerView.getInstalledPackages(this.mContext));
        ArrayList arrayList2 = (ArrayList) filterMultiWindowSupportPackages;
        int size = arrayList2.size();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                size, "smart popup view enabled app size: ", TAG);
        try {
            return ((ArrayList) filterMultiWindowSupportPackages2).size() == size
                    ? this.mContext.getString(R.string.notification_floating_included_app_list_all)
                    : size != 0
                            ? size != 1
                                    ? size != 2
                                            ? this.mContext.getString(
                                                    R.string
                                                            .notification_floating_included_app_list_other,
                                                    getAppLableFromPackageName(
                                                            (String) arrayList2.get(0)),
                                                    getAppLableFromPackageName(
                                                            (String) arrayList2.get(1)),
                                                    Integer.valueOf(size - 2))
                                            : this.mContext.getString(
                                                    R.string
                                                            .notification_floating_included_app_list_two,
                                                    getAppLableFromPackageName(
                                                            (String) arrayList2.get(0)),
                                                    getAppLableFromPackageName(
                                                            (String) arrayList2.get(1)))
                                    : TextUtils.isEmpty((CharSequence) arrayList2.get(0))
                                            ? this.mContext.getString(
                                                    R.string
                                                            .notification_floating_included_app_list_zero)
                                            : this.mContext.getString(
                                                    R.string
                                                            .notification_floating_included_app_list_one,
                                                    getAppLableFromPackageName(
                                                            (String) arrayList2.get(0)))
                            : this.mContext.getString(
                                    R.string.notification_floating_included_app_list_zero);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return ApnSettings.MVNO_NONE;
        }
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setVisible(
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(), "notification_bubbles", 1, -2)
                        == 2);
    }

    @Override // com.samsung.android.settings.notification.FloatingIconDependentControllerFieldListener
    public void updateValues(int i) {
        this.mPreference.setVisible(i == 2);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
