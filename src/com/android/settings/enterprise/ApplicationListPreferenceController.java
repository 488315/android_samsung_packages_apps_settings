package com.android.settings.enterprise;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.IconDrawableFactory;

import androidx.preference.PreferenceScreen;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.UserAppInfo;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.AppPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApplicationListPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final SettingsPreferenceFragment mParent;
    public final PackageManager mPm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ApplicationListBuilder {
        void buildApplicationList(
                ApplicationListPreferenceController applicationListPreferenceController);
    }

    public ApplicationListPreferenceController(
            Context context,
            ApplicationListBuilder applicationListBuilder,
            PackageManager packageManager,
            SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context);
        this.mPm = packageManager;
        this.mParent = settingsPreferenceFragment;
        applicationListBuilder.buildApplicationList(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public final void onListOfAppsResult(List list) {
        SettingsPreferenceFragment settingsPreferenceFragment = this.mParent;
        PreferenceScreen preferenceScreen = settingsPreferenceFragment.getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        IconDrawableFactory newInstance = IconDrawableFactory.newInstance(this.mContext);
        Context context = settingsPreferenceFragment.getPreferenceManager().mContext;
        for (int i = 0; i < list.size(); i++) {
            UserAppInfo userAppInfo = (UserAppInfo) list.get(i);
            AppPreference appPreference = new AppPreference(context);
            appPreference.setTitle(userAppInfo.appInfo.loadLabel(this.mPm));
            appPreference.setIcon(newInstance.getBadgedIcon(userAppInfo.appInfo));
            appPreference.setOrder(i);
            appPreference.setSelectable(false);
            preferenceScreen.addPreference(appPreference);
        }
    }
}
