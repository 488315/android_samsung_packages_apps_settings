package com.android.settings.applications;

import android.os.Bundle;
import android.text.BidiFormatter;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoWithHeader extends AppInfoBase {
    public boolean mCreated;

    public static void insertEventLogging(int i, int i2, String str, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("pkgname", str);
        hashMap.put("newValue", z ? "On" : "Off");
        SALogging.insertSALog(Integer.toString(i), Integer.toString(i2), hashMap, 0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mCreated) {
            Log.w("AppInfoWithHeader", "onActivityCreated: ignoring duplicate call");
            return;
        }
        this.mCreated = true;
        if (this.mPackageInfo == null) {
            return;
        }
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(getActivity(), this, null);
        entityHeaderController.setIcon(AppUtils.getIconWithoutCache(getContext(), this.mAppEntry));
        entityHeaderController.mLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        entityHeaderController.mSummary =
                getPrefContext()
                        .getString(
                                R.string.version_text,
                                BidiFormatter.getInstance()
                                        .unicodeWrap(this.mPackageInfo.versionName));
        entityHeaderController.mIsInstantApp =
                AppUtils.isInstant(this.mPackageInfo.applicationInfo);
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mUid = this.mPackageInfo.applicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        LayoutPreference done = entityHeaderController.done(getPrefContext());
        getPreferenceScreen().addPreference(done);
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference.setHeight(0);
        secInsetCategoryPreference.seslSetSubheaderRoundedBackground(3);
        secInsetCategoryPreference.setOrder(done.getOrder() + 1);
        getPreferenceScreen().addPreference(secInsetCategoryPreference);
        getListView().mDrawLastRoundedCorner = false;
    }
}
