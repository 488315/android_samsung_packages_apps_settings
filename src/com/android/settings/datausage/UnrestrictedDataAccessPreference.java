package com.android.settings.datausage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreferenceHelper;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AppSwitchPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UnrestrictedDataAccessPreference extends AppSwitchPreference
        implements DataSaverBackend.Listener {
    public Drawable mCacheIcon;
    public final DataSaverBackend mDataSaverBackend;
    public AppStateDataUsageBridge.DataUsageState mDataUsageState;
    public final ApplicationsState.AppEntry mEntry;
    public final RestrictedPreferenceHelper mHelper;
    public final DashboardFragment mParentFragment;

    public UnrestrictedDataAccessPreference(
            Context context,
            ApplicationsState.AppEntry appEntry,
            DataSaverBackend dataSaverBackend,
            DashboardFragment dashboardFragment) {
        super(context);
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context, this, null);
        this.mHelper = restrictedPreferenceHelper;
        this.mEntry = appEntry;
        this.mDataUsageState = (AppStateDataUsageBridge.DataUsageState) appEntry.extraInfo;
        appEntry.ensureLabel(context);
        this.mDataSaverBackend = dataSaverBackend;
        this.mParentFragment = dashboardFragment;
        ApplicationInfo applicationInfo = appEntry.info;
        restrictedPreferenceHelper.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfMeteredDataUsageUserControlDisabled(
                        context,
                        UserHandle.getUserId(applicationInfo.uid),
                        applicationInfo.packageName));
        restrictedPreferenceHelper.checkEcmRestrictionAndSetDisabled(
                "android:unrestricted_data_access", appEntry.info.packageName);
        updateState();
        setKey(appEntry.info.packageName + "|" + appEntry.info.uid);
        Drawable iconFromCache = AppUtils.getIconFromCache(appEntry);
        this.mCacheIcon = iconFromCache;
        if (iconFromCache != null) {
            setIcon(iconFromCache);
        } else {
            setIcon(R.drawable.empty_icon);
        }
    }

    public boolean isDisabledByEcm() {
        return this.mHelper.mDisabledByEcm;
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {
        AppStateDataUsageBridge.DataUsageState dataUsageState = this.mDataUsageState;
        if (dataUsageState == null || this.mEntry.info.uid != i) {
            return;
        }
        dataUsageState.isDataSaverAllowlisted = z;
        updateState();
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mDataSaverBackend.addListener(this);
    }

    @Override // com.android.settingslib.widget.AppSwitchPreference,
              // androidx.preference.SwitchPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (this.mCacheIcon == null) {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.datausage.UnrestrictedDataAccessPreference$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final UnrestrictedDataAccessPreference
                                    unrestrictedDataAccessPreference =
                                            UnrestrictedDataAccessPreference.this;
                            final Drawable icon =
                                    AppUtils.getIcon(
                                            unrestrictedDataAccessPreference.getContext(),
                                            unrestrictedDataAccessPreference.mEntry);
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.datausage.UnrestrictedDataAccessPreference$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            UnrestrictedDataAccessPreference
                                                    unrestrictedDataAccessPreference2 =
                                                            UnrestrictedDataAccessPreference.this;
                                            Drawable drawable = icon;
                                            unrestrictedDataAccessPreference2.setIcon(drawable);
                                            unrestrictedDataAccessPreference2.mCacheIcon = drawable;
                                        }
                                    });
                        }
                    });
        }
        boolean z = this.mHelper.mDisabledByAdmin;
        View findViewById = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        if (z) {
            findViewById.setVisibility(0);
        } else {
            AppStateDataUsageBridge.DataUsageState dataUsageState = this.mDataUsageState;
            findViewById.setVisibility(
                    (dataUsageState == null || !dataUsageState.isDataSaverDenylisted) ? 0 : 4);
        }
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.itemView.findViewById(R.id.summary_container);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.appendix);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        if (textView != null) {
            textView.setVisibility(8);
        }
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        AppStateDataUsageBridge.DataUsageState dataUsageState = this.mDataUsageState;
        if (dataUsageState == null || !dataUsageState.isDataSaverDenylisted) {
            super.onClick();
        } else {
            AppInfoDashboardFragment.startAppInfoFragment(
                    AppDataUsage.class,
                    R.string.data_usage_app_summary_title,
                    null,
                    this.mParentFragment,
                    this.mEntry);
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {
        AppStateDataUsageBridge.DataUsageState dataUsageState = this.mDataUsageState;
        if (dataUsageState == null || this.mEntry.info.uid != i) {
            return;
        }
        dataUsageState.isDataSaverDenylisted = z;
        updateState();
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        this.mDataSaverBackend.remListener(this);
        super.onDetached();
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    public final void updateState() {
        setTitle(this.mEntry.label);
        AppStateDataUsageBridge.DataUsageState dataUsageState =
                (AppStateDataUsageBridge.DataUsageState) this.mEntry.extraInfo;
        this.mDataUsageState = dataUsageState;
        if (dataUsageState != null) {
            setChecked(dataUsageState.isDataSaverAllowlisted);
            if (this.mHelper.mDisabledByAdmin) {
                setSummary(R.string.disabled_by_admin);
            } else if (this.mDataUsageState.isDataSaverDenylisted) {
                setSummary(R.string.restrict_background_blocklisted);
            } else if (!isDisabledByEcm()) {
                setSummary(ApnSettings.MVNO_NONE);
            }
        }
        notifyChanged();
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {}
}
