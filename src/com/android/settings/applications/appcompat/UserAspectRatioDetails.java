package com.android.settings.applications.appcompat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.widget.ActionButtonsPreference;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserAspectRatioDetails extends AppInfoBase
        implements RadioWithImagePreference.OnClickListener {

    @VisibleForTesting static final String KEY_HEADER_BUTTONS = "header_view";

    @VisibleForTesting static final String KEY_PREF_3_2 = "3_2_pref";

    @VisibleForTesting static final String KEY_PREF_DEFAULT = "app_default_pref";

    @VisibleForTesting static final String KEY_PREF_FULLSCREEN = "fullscreen_pref";
    public boolean mIsOverrideToFullscreenEnabled;
    public UserAspectRatioManager mUserAspectRatioManager;

    @VisibleForTesting String mSelectedKey = KEY_PREF_DEFAULT;

    @VisibleForTesting final BiMap mKeyToAspectRatioMap = HashBiMap.create();
    public final List mAspectRatioPreferences = new ArrayList();

    public final void addPreference(int i, String str) {
        RadioWithImagePreference radioWithImagePreference =
                (RadioWithImagePreference) findPreference(str);
        if (radioWithImagePreference == null) {
            return;
        }
        if (!getAspectRatioManager().hasAspectRatioOption(i, this.mPackageName)) {
            radioWithImagePreference.setVisible(false);
            return;
        }
        radioWithImagePreference.setTitle(
                this.mUserAspectRatioManager.getAccessibleEntry(i, this.mPackageName));
        radioWithImagePreference.setOrder(getAspectRatioManager().mUserAspectRatioOrder.get(i));
        radioWithImagePreference.mListener = this;
        this.mKeyToAspectRatioMap.put(str, Integer.valueOf(i));
        ((ArrayList) this.mAspectRatioPreferences).add(radioWithImagePreference);
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @VisibleForTesting
    public UserAspectRatioManager getAspectRatioManager() {
        return this.mUserAspectRatioManager;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2054;
    }

    @VisibleForTesting
    public int getSelectedUserMinAspectRatio(String str) {
        Integer num = (Integer) this.mKeyToAspectRatioMap.getOrDefault(KEY_PREF_DEFAULT, 0);
        num.intValue();
        return ((Integer) this.mKeyToAspectRatioMap.getOrDefault(str, num)).intValue();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(getActivity(), this, null);
        entityHeaderController.setIcon(
                Utils.getBadgedIcon(getContext(), this.mPackageInfo.applicationInfo));
        entityHeaderController.mLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        entityHeaderController.mIsInstantApp =
                AppUtils.isInstant(this.mPackageInfo.applicationInfo);
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mUid = this.mPackageInfo.applicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        getPreferenceScreen().addPreference(entityHeaderController.done(getPrefContext()));
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mPackageName == null) {
            Log.e("UserAspectRatioDetails", "package name is null");
            return;
        }
        this.mUserAspectRatioManager = new UserAspectRatioManager(getContext());
        this.mIsOverrideToFullscreenEnabled =
                getAspectRatioManager()
                        .isOverrideToFullscreenEnabled(this.mUserId, this.mPackageName);
        addPreferencesFromResource(R.xml.user_aspect_ratio_details);
        findPreference("app_aspect_ratio_summary")
                .setTitle(
                        getContext()
                                .getResources()
                                .getString(R.string.aspect_ratio_main_summary, Build.MODEL));
        ActionButtonsPreference actionButtonsPreference =
                (ActionButtonsPreference) findPreference(KEY_HEADER_BUTTONS);
        actionButtonsPreference.setButton1Text(R.string.launch_instant_app);
        actionButtonsPreference.setButton1Icon(R.drawable.ic_settings_open);
        actionButtonsPreference.setButton1OnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.appcompat.UserAspectRatioDetails$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        UserAspectRatioDetails userAspectRatioDetails = UserAspectRatioDetails.this;
                        String str = UserAspectRatioDetails.KEY_HEADER_BUTTONS;
                        Intent addFlags =
                                userAspectRatioDetails
                                        .mPm
                                        .getLaunchIntentForPackage(
                                                userAspectRatioDetails.mPackageName)
                                        .addFlags(335544320);
                        if (addFlags != null) {
                            userAspectRatioDetails
                                    .getContext()
                                    .startActivityAsUser(
                                            addFlags,
                                            new UserHandle(userAspectRatioDetails.mUserId));
                        }
                    }
                });
        if (this.mIsOverrideToFullscreenEnabled) {
            addPreference(7, KEY_PREF_DEFAULT);
        } else {
            addPreference(0, KEY_PREF_DEFAULT);
        }
        int i = 6;
        addPreference(6, KEY_PREF_FULLSCREEN);
        addPreference(2, "display_size_pref");
        addPreference(1, "half_screen_pref");
        addPreference(4, "16_9_pref");
        addPreference(3, "4_3_pref");
        addPreference(5, KEY_PREF_3_2);
        try {
            UserAspectRatioManager aspectRatioManager = getAspectRatioManager();
            String str = this.mPackageName;
            int userMinAspectRatio =
                    aspectRatioManager.mIPm.getUserMinAspectRatio(str, this.mUserId);
            if (!aspectRatioManager.hasAspectRatioOption(userMinAspectRatio, str)) {
                userMinAspectRatio = 0;
            }
            String str2 =
                    (String) this.mKeyToAspectRatioMap.inverse().getOrDefault(0, KEY_PREF_DEFAULT);
            if (userMinAspectRatio != 0 || !this.mIsOverrideToFullscreenEnabled) {
                i = userMinAspectRatio;
            }
            this.mSelectedKey =
                    (String)
                            this.mKeyToAspectRatioMap
                                    .inverse()
                                    .getOrDefault(Integer.valueOf(i), str2);
        } catch (RemoteException unused) {
            Log.e("UserAspectRatioDetails", "Unable to get user min aspect ratio");
        }
        refreshUi();
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return false;
        }
        String str = this.mSelectedKey;
        Iterator it = ((ArrayList) this.mAspectRatioPreferences).iterator();
        while (it.hasNext()) {
            RadioWithImagePreference radioWithImagePreference =
                    (RadioWithImagePreference) it.next();
            radioWithImagePreference.setChecked(str.equals(radioWithImagePreference.getKey()));
        }
        return true;
    }
}
