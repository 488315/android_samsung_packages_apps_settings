package com.android.settings.dashboard.profileselector;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.core.OnActivityResultListener;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectGalaxyAI extends ProfileSelectFragment
        implements OnActivityResultListener {
    public ViewGroup mContentView;
    public Context mContext;
    public boolean mPersonalAccRegistered;
    public int mSelectedTab = 0;
    public int mUserId;
    public boolean mWorkProfileAccRegistered;
    public int mWorkUserId;

    public final void finishOrReturnToPlaceHolder() {
        FragmentActivity activity = getActivity();
        if (ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity)) {
            Intent intent = new Intent("com.samsung.android.settings.CONNECTIONS");
            intent.addFlags(603979776);
            startActivity(intent);
        }
        finish();
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        Bundle deepCopy = getArguments() != null ? getArguments().deepCopy() : new Bundle();
        deepCopy.putInt(ImsProfile.SERVICE_PROFILE, 2);
        IntelligenceServiceSettings intelligenceServiceSettings = new IntelligenceServiceSettings();
        intelligenceServiceSettings.setArguments(deepCopy);
        Bundle arguments = getArguments() != null ? getArguments() : new Bundle();
        arguments.putInt(ImsProfile.SERVICE_PROFILE, 1);
        IntelligenceServiceSettings intelligenceServiceSettings2 =
                new IntelligenceServiceSettings();
        intelligenceServiceSettings2.setArguments(arguments);
        return new Fragment[] {intelligenceServiceSettings2, intelligenceServiceSettings};
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        SemLog.i(
                "ProfileSelectGalaxyAI",
                "onActivityResult: requestCode : " + i + " resultCode : " + i2);
        if (i == 2002) {
            if (i2 != -1) {
                finishOrReturnToPlaceHolder();
                return;
            }
            boolean isSamsungAccountExistsAsUser =
                    AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mWorkUserId);
            this.mWorkProfileAccRegistered = isSamsungAccountExistsAsUser;
            if (!isSamsungAccountExistsAsUser || AccountUtils.isChildAccount(this.mContext)) {
                finishOrReturnToPlaceHolder();
                return;
            } else {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(),
                        "ai_info_confirmed",
                        1,
                        this.mWorkUserId);
                return;
            }
        }
        if (i == 2001) {
            if (i2 != -1) {
                finishOrReturnToPlaceHolder();
                return;
            }
            boolean isSamsungAccountExistsAsUser2 =
                    AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mWorkUserId);
            this.mWorkProfileAccRegistered = isSamsungAccountExistsAsUser2;
            if (isSamsungAccountExistsAsUser2) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(),
                        "ai_info_confirmed",
                        1,
                        this.mWorkUserId);
                return;
            }
            try {
                AccountUtils.addSamsungAccount(
                        this.mContext,
                        this,
                        VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F,
                        1,
                        this.mWorkUserId);
                return;
            } catch (ActivityNotFoundException e) {
                SemLog.e("ProfileSelectGalaxyAI", "ActivityNotFoundException" + e.getMessage());
                return;
            }
        }
        if (i == 1002) {
            if (i2 != -1) {
                finishOrReturnToPlaceHolder();
                return;
            }
            boolean isSamsungAccountExists = AccountUtils.isSamsungAccountExists(this.mContext);
            this.mPersonalAccRegistered = isSamsungAccountExists;
            if (!isSamsungAccountExists || AccountUtils.isChildAccount(this.mContext)) {
                finishOrReturnToPlaceHolder();
                return;
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "ai_info_confirmed", 1);
                return;
            }
        }
        if (i == 1001) {
            if (i2 != -1) {
                finishOrReturnToPlaceHolder();
                return;
            }
            boolean isSamsungAccountExistsAsUser3 =
                    AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mUserId);
            this.mPersonalAccRegistered = isSamsungAccountExistsAsUser3;
            if (isSamsungAccountExistsAsUser3) {
                Settings.System.putInt(this.mContext.getContentResolver(), "ai_info_confirmed", 1);
                return;
            }
            try {
                Log.i("ProfileSelectGalaxyAI", "addSamsungAccount");
                AccountUtils.addSamsungAccount(this.mContext, this, 1002, 1, this.mUserId);
            } catch (ActivityNotFoundException e2) {
                Log.e("ProfileSelectGalaxyAI", "ActivityNotFoundException" + e2.getMessage());
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            this.mSelectedTab = getArguments().getInt(":settings:show_fragment_tab", 0);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (!UsefulfeatureUtils.isSupportedIntelligenceService(getContext())) {
            Log.i("ProfileSelectGalaxyAI", "not support IntelligenceService");
            finish();
            return;
        }
        enableAutoFlowLogging(false);
        this.mContext = getContext();
        this.mUserId = UserHandle.myUserId();
        this.mWorkUserId = UsefulfeatureUtils.getManagedProfileId(this.mContext);
        this.mPersonalAccRegistered = AccountUtils.isSamsungAccountExists(this.mContext);
        Context context = this.mContext;
        this.mWorkProfileAccRegistered =
                AccountUtils.isSamsungAccountExistsAsUser(
                        context, UsefulfeatureUtils.getManagedProfileId(context));
        super.onCreate(bundle);
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mViewPager.registerOnPageChangeCallback(
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.android.settings.dashboard.profileselector.ProfileSelectGalaxyAI.1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        ProfileSelectGalaxyAI.this.refreshUI(i);
                    }
                });
        if (this.mPersonalAccRegistered) {
            ((TabLayout) this.mContentView.findViewById(R.id.tab_container).findViewById(R.id.tabs))
                    .getTabAt(0)
                    .select();
        } else if (this.mWorkProfileAccRegistered) {
            ((TabLayout) this.mContentView.findViewById(R.id.tab_container).findViewById(R.id.tabs))
                    .getTabAt(1)
                    .select();
        } else {
            refreshUI(this.mSelectedTab);
        }
        return this.mContentView;
    }

    public final void refreshUI(int i) {
        if (i == 0) {
            if (this.mPersonalAccRegistered || Rune.isShopDemo(this.mContext)) {
                return;
            }
            Intent intent =
                    new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS");
            intent.addFlags(603979776);
            getActivity().startActivityForResultAsUser(intent, 1001, UserHandle.of(this.mUserId));
            return;
        }
        if (i != 1) {
            SemLog.e("ProfileSelectGalaxyAI", "refreshUI() postion error : " + i);
        } else {
            if (this.mWorkProfileAccRegistered || Rune.isShopDemo(this.mContext)) {
                return;
            }
            Intent intent2 =
                    new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS");
            intent2.addFlags(603979776);
            getActivity()
                    .startActivityForResultAsUser(
                            intent2,
                            VolteConstants.ErrorCode.CALL_FORBIDDEN,
                            UserHandle.of(this.mWorkUserId));
        }
    }
}
