package com.android.settings.applications;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.IUsbManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ClearDefaultsPreference extends Preference implements View.OnKeyListener {
    public Button mActivitiesButton;
    public ApplicationsState.AppEntry mAppEntry;
    public final AppWidgetManager mAppWidgetManager;
    public final RestrictedLockUtils.EnforcedAdmin mAppsControlDisallowedAdmin;
    public final boolean mAppsControlDisallowedBySystem;
    public String mPackageName;
    public final PackageManager mPm;
    public PreferenceViewHolder mRootView;
    public final IUsbManager mUsbManager;

    public ClearDefaultsPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_app_preferred_settings);
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
        this.mPm = context.getPackageManager();
        this.mUsbManager = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
        this.mAppsControlDisallowedBySystem =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        getContext(), UserHandle.myUserId(), "no_control_apps");
        this.mAppsControlDisallowedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getContext(), UserHandle.myUserId(), "no_control_apps");
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Button button = (Button) preferenceViewHolder.findViewById(R.id.clear_activities_button);
        this.mActivitiesButton = button;
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.ClearDefaultsPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ClearDefaultsPreference clearDefaultsPreference =
                                ClearDefaultsPreference.this;
                        if (clearDefaultsPreference.mAppsControlDisallowedAdmin != null
                                && !clearDefaultsPreference.mAppsControlDisallowedBySystem) {
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    clearDefaultsPreference.getContext(),
                                    ClearDefaultsPreference.this.mAppsControlDisallowedAdmin);
                        } else {
                            clearDefaultsPreference.resetAppDefaults();
                            LoggingHelper.insertEventLogging(143, 3871);
                        }
                    }
                });
        updateUI(preferenceViewHolder);
        this.mRootView = preferenceViewHolder;
        preferenceViewHolder.itemView.semSetRoundedCorners(3);
        this.mRootView.itemView.semSetRoundedCornerColor(
                3, getContext().getColor(R.color.sec_widget_round_and_bgcolor));
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || !this.mActivitiesButton.isEnabled()) {
            return false;
        }
        if (keyEvent.getKeyCode() != 66 && keyEvent.getKeyCode() != 23) {
            return false;
        }
        resetAppDefaults();
        return false;
    }

    public final void resetAppDefaults() {
        if (this.mUsbManager != null) {
            int myUserId = UserHandle.myUserId();
            this.mPm.clearPackagePreferredActivities(this.mPackageName);
            PackageManager packageManager = this.mPm;
            String str = this.mPackageName;
            Intent intent = AppUtils.sBrowserIntent;
            if (str.equals(
                    packageManager.getDefaultBrowserPackageNameAsUser(UserHandle.myUserId()))) {
                this.mPm.setDefaultBrowserPackageNameAsUser(null, myUserId);
            }
            try {
                this.mUsbManager.clearDefaults(this.mPackageName, myUserId);
            } catch (RemoteException e) {
                Log.e("ClearDefaultsPreference", "mUsbManager.clearDefaults", e);
            }
            this.mAppWidgetManager.setBindAppWidgetPermission(this.mPackageName, false);
            this.mActivitiesButton.setEnabled(false);
        }
    }

    public final void updateUI(PreferenceViewHolder preferenceViewHolder) {
        ApplicationInfo applicationInfo;
        String enterprisePolicyStringValue;
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null || (applicationInfo = appEntry.info) == null) {
            Log.e("ClearDefaultsPreference", "updateUI : mAppEntry or mAppEntry.info is null");
            return;
        }
        boolean hasBindAppWidgetPermission =
                this.mAppWidgetManager.hasBindAppWidgetPermission(applicationInfo.packageName);
        boolean z = false;
        if (!AppUtils.hasPreferredActivities(this.mPm, this.mPackageName)) {
            if (!TextUtils.equals(
                            this.mPackageName,
                            getContext()
                                    .getPackageManager()
                                    .getDefaultBrowserPackageNameAsUser(UserHandle.myUserId()))
                    && !AppUtils.hasUsbDefaults(this.mUsbManager, this.mPackageName)
                    && !hasBindAppWidgetPermission) {
                this.mActivitiesButton.setEnabled(false);
                return;
            }
        }
        this.mActivitiesButton.setEnabled(true);
        boolean z2 =
                Utils.getEnterprisePolicyEnabled(
                                        getContext(),
                                        "content://com.sec.knox.provider2/KioskMode",
                                        "isKioskModeEnabled")
                                == 1
                        && (enterprisePolicyStringValue =
                                        Utils.getEnterprisePolicyStringValue(getContext()))
                                != null
                        && enterprisePolicyStringValue.equals(this.mPackageName);
        boolean z3 =
                Utils.getEnterprisePolicyEnabled(
                                getContext(),
                                "content://com.sec.knox.provider2/ApplicationPolicy",
                                "isApplicationSetToDefault",
                                new String[] {
                                    this.mPackageName, String.valueOf(UserHandle.myUserId())
                                })
                        == 1;
        Button button = this.mActivitiesButton;
        if (!z2 && !z3) {
            z = true;
        }
        button.setEnabled(z);
    }

    public ClearDefaultsPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ClearDefaultsPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public ClearDefaultsPreference(Context context) {
        this(context, null);
    }
}
