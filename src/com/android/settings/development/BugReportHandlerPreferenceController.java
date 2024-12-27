package com.android.settings.development;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.bugreporthandler.BugReportHandlerUtil;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BugReportHandlerPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public final BugReportHandlerUtil mBugReportHandlerUtil;
    public final UserManager mUserManager;

    public BugReportHandlerPreferenceController(Context context) {
        super(context);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mBugReportHandlerUtil = new BugReportHandlerUtil();
    }

    public CharSequence getCurrentBugReportHandlerAppLabel() {
        Context context = this.mContext;
        this.mBugReportHandlerUtil.getClass();
        String str =
                (String) BugReportHandlerUtil.getCurrentBugReportHandlerAppAndUser(context).first;
        if ("com.android.shell".equals(str)) {
            return this.mContext.getString(R.string.capability_title_canPerformGestures);
        }
        try {
            return this.mContext
                    .getPackageManager()
                    .getApplicationInfo(str, 4194304)
                    .loadLabel(this.mContext.getPackageManager());
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bug_report_handler";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!this.mUserManager.hasUserRestriction("no_debugging_features")) {
            Context context = this.mContext;
            this.mBugReportHandlerUtil.getClass();
            if (context.getResources()
                    .getBoolean(R.bool.config_cameraDoubleTapPowerGestureEnabled)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        CharSequence currentBugReportHandlerAppLabel = getCurrentBugReportHandlerAppLabel();
        if (TextUtils.isEmpty(currentBugReportHandlerAppLabel)) {
            this.mPreference.setSummary(com.android.settings.R.string.app_list_preference_none);
        } else {
            this.mPreference.setSummary(currentBugReportHandlerAppLabel);
        }
    }
}
