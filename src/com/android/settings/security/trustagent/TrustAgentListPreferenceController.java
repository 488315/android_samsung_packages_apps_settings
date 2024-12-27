package com.android.settings.security.trustagent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.security.SecurityFeatureProviderImpl;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TrustAgentListPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnSaveInstanceState,
                OnCreate,
                OnResume {
    public static final int MY_USER_ID = UserHandle.myUserId();
    static final String PREF_KEY_SECURITY_CATEGORY = "security_category";
    static final String PREF_KEY_TRUST_AGENT = "trust_agent";
    public final SettingsPreferenceFragment mHost;
    public final LockPatternUtils mLockPatternUtils;
    public PreferenceCategory mSecurityCategory;
    public Intent mTrustAgentClickIntent;
    public final TrustAgentManager mTrustAgentManager;
    final List<String> mTrustAgentsKeyList;

    public TrustAgentListPreferenceController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecurityFeatureProviderImpl securityFeatureProvider =
                featureFactoryImpl.getSecurityFeatureProvider();
        this.mHost = settingsPreferenceFragment;
        this.mLockPatternUtils = securityFeatureProvider.getLockPatternUtils(context);
        this.mTrustAgentManager = securityFeatureProvider.getTrustAgentManager();
        this.mTrustAgentsKeyList = new ArrayList();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSecurityCategory =
                (PreferenceCategory) preferenceScreen.findPreference(PREF_KEY_SECURITY_CATEGORY);
        updateTrustAgents();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return PREF_KEY_TRUST_AGENT;
    }

    public final boolean handleActivityResult(int i, int i2) {
        if (i != 126 || i2 != -1) {
            return false;
        }
        Intent intent = this.mTrustAgentClickIntent;
        if (intent == null) {
            return true;
        }
        this.mHost.startActivity(intent);
        this.mTrustAgentClickIntent = null;
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        Intent intent;
        if (!this.mTrustAgentsKeyList.contains(preference.getKey())) {
            return false;
        }
        SettingsPreferenceFragment settingsPreferenceFragment = this.mHost;
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(
                        settingsPreferenceFragment.getActivity(), settingsPreferenceFragment);
        builder.mRequestCode = 126;
        builder.mTitle = preference.getTitle();
        boolean show = builder.show();
        Intent intent2 = preference.getIntent();
        this.mTrustAgentClickIntent = intent2;
        intent2.putExtra("page_transition_type", 1);
        if (!show && (intent = this.mTrustAgentClickIntent) != null) {
            settingsPreferenceFragment.startActivity(intent);
            this.mTrustAgentClickIntent = null;
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_trust_agent_click_intent);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("trust_agent_click_intent")) {
            return;
        }
        this.mTrustAgentClickIntent = (Intent) bundle.getParcelable("trust_agent_click_intent");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        updateTrustAgents();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        Intent intent = this.mTrustAgentClickIntent;
        if (intent != null) {
            bundle.putParcelable("trust_agent_click_intent", intent);
        }
    }

    public final void updateTrustAgents() {
        if (this.mSecurityCategory != null && isAvailable()) {
            Context context = this.mContext;
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            this.mTrustAgentManager.getClass();
            ArrayList arrayList =
                    (ArrayList)
                            TrustAgentManager.getActiveTrustAgents(context, lockPatternUtils, true);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Preference findPreference =
                        this.mSecurityCategory.findPreference(
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        i, PREF_KEY_TRUST_AGENT));
                if (findPreference == null) {
                    break;
                }
                this.mSecurityCategory.removePreference(findPreference);
            }
            this.mTrustAgentsKeyList.clear();
            boolean isSecure = this.mLockPatternUtils.isSecure(MY_USER_ID);
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                RestrictedPreference restrictedPreference =
                        new RestrictedPreference(this.mSecurityCategory.getContext());
                TrustAgentManager.TrustAgentComponentInfo trustAgentComponentInfo =
                        (TrustAgentManager.TrustAgentComponentInfo) arrayList.get(i2);
                this.mTrustAgentsKeyList.add(PREF_KEY_TRUST_AGENT + i2);
                restrictedPreference.setKey(PREF_KEY_TRUST_AGENT + i2);
                restrictedPreference.setTitle(trustAgentComponentInfo.title);
                restrictedPreference.setSummary(trustAgentComponentInfo.summary);
                restrictedPreference.setIntent(
                        new Intent("android.intent.action.MAIN")
                                .setComponent(trustAgentComponentInfo.componentName));
                restrictedPreference.setDisabledByAdmin(trustAgentComponentInfo.admin);
                boolean z = restrictedPreference.mHelper.mDisabledByAdmin;
                if (z) {
                    restrictedPreference.setSummary(
                            TextUtils.firstNotEmpty(trustAgentComponentInfo.summary, " "));
                    restrictedPreference.mHelper.mDisabledSummary = true;
                } else if (!z && !isSecure) {
                    restrictedPreference.setEnabled(false);
                    restrictedPreference.setSummary(R.string.disabled_because_no_backup_security);
                }
                this.mSecurityCategory.addPreference(restrictedPreference);
            }
        }
    }
}
