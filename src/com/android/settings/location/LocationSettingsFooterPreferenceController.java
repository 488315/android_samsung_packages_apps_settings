package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationSettingsFooterPreferenceController extends LocationBasePreferenceController {
    private static final Intent INJECT_INTENT =
            new Intent("com.android.settings.location.DISPLAYED_FOOTER");
    private static final String PARAGRAPH_SEPARATOR = "<br><br>";
    private static final String TAG = "LocationFooter";
    private FooterPreference mFooterPreference;
    private String mInjectedFooterString;
    private boolean mLocationEnabled;
    private final PackageManager mPackageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FooterData {
        public final ApplicationInfo applicationInfo;
        public final int footerStringRes;

        public FooterData(int i, ApplicationInfo applicationInfo) {
            this.footerStringRes = i;
            this.applicationInfo = applicationInfo;
        }
    }

    public LocationSettingsFooterPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
    }

    private List<FooterData> getFooterData() {
        PackageManager packageManager = this.mPackageManager;
        Intent intent = INJECT_INTENT;
        List<ResolveInfo> queryBroadcastReceivers =
                packageManager.queryBroadcastReceivers(intent, 128);
        if (queryBroadcastReceivers == null) {
            Log.e(TAG, "Unable to resolve intent " + intent);
            return Collections.emptyList();
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Found broadcast receivers: " + queryBroadcastReceivers);
        }
        ArrayList arrayList = new ArrayList(queryBroadcastReceivers.size());
        for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            ApplicationInfo applicationInfo = activityInfo.applicationInfo;
            if ((applicationInfo.flags & 1) == 0) {
                Log.w(
                        TAG,
                        "Ignoring attempt to inject footer from app not in system image: "
                                + resolveInfo);
            } else {
                Bundle bundle = activityInfo.metaData;
                if (bundle != null) {
                    int i = bundle.getInt("com.android.settings.location.FOOTER_STRING");
                    if (i == 0) {
                        Log.w(
                                TAG,
                                "No mapping of integer exists for"
                                    + " com.android.settings.location.FOOTER_STRING");
                    } else {
                        arrayList.add(new FooterData(i, applicationInfo));
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("No METADATA in broadcast receiver "),
                            activityInfo.name,
                            TAG);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFooterPreference$0(View view) {
        openLocationLearnMoreLink();
    }

    private void openLocationLearnMoreLink() {
        DashboardFragment dashboardFragment = this.mFragment;
        Context context = this.mContext;
        dashboardFragment.startActivityForResult(
                HelpUtils.getHelpIntent(
                        context,
                        context.getString(R.string.location_settings_footer_learn_more_link),
                        ApnSettings.MVNO_NONE),
                0);
    }

    private void updateFooterPreference() {
        String string = this.mContext.getString(R.string.location_settings_footer_general);
        if (!this.mLocationEnabled) {
            string =
                    this.mContext.getString(R.string.location_settings_footer_location_off)
                            + PARAGRAPH_SEPARATOR
                            + string;
        } else if (!TextUtils.isEmpty(this.mInjectedFooterString)) {
            string = Html.escapeHtml(this.mInjectedFooterString) + PARAGRAPH_SEPARATOR + string;
        }
        FooterPreference footerPreference = this.mFooterPreference;
        if (footerPreference != null) {
            footerPreference.setTitle(Html.fromHtml(string));
            this.mFooterPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.location.LocationSettingsFooterPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LocationSettingsFooterPreferenceController.this
                                    .lambda$updateFooterPreference$0(view);
                        }
                    });
            this.mFooterPreference.setLearnMoreText(
                    this.mContext.getString(
                            R.string.location_settings_footer_learn_more_content_description));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mFooterPreference =
                (FooterPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    public void onLocationModeChanged(int i, boolean z) {
        this.mLocationEnabled = this.mLocationEnabler.isEnabled(i);
        updateFooterPreference();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        for (FooterData footerData : getFooterData()) {
            try {
                this.mInjectedFooterString =
                        this.mPackageManager
                                .getResourcesForApplication(footerData.applicationInfo)
                                .getString(footerData.footerStringRes);
                updateFooterPreference();
            } catch (PackageManager.NameNotFoundException unused) {
                MainClear$$ExternalSyntheticOutline0.m$1(
                        new StringBuilder("Resources not found for application "),
                        footerData.applicationInfo.packageName,
                        TAG);
            }
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
