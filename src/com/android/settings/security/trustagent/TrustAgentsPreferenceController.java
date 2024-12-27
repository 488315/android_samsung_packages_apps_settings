package com.android.settings.security.trustagent;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IconDrawableFactory;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.security.SecurityFeatureProviderImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TrustAgentsPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnStart {
    private static final String GMS_TRUST_AGENT_CLASSNAME =
            "com.google.android.gms.auth.trustagent.GoogleTrustAgent";
    private static final Intent TRUST_AGENT_INTENT =
            new Intent("android.service.trust.TrustAgentService");
    private final ArraySet<ComponentName> mActiveAgents;
    private final ArrayMap<ComponentName, TrustAgentInfo> mAvailableAgents;
    private final DevicePolicyManager mDevicePolicyManager;
    private final IconDrawableFactory mIconDrawableFactory;
    private final LockPatternUtils mLockPatternUtils;
    private final PackageManager mPackageManager;
    private PreferenceScreen mScreen;
    private final TrustAgentManager mTrustAgentManager;

    public TrustAgentsPreferenceController(Context context, String str) {
        super(context, str);
        this.mAvailableAgents = new ArrayMap<>();
        this.mActiveAgents = new ArraySet<>();
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecurityFeatureProviderImpl securityFeatureProvider =
                featureFactoryImpl.getSecurityFeatureProvider();
        this.mTrustAgentManager = securityFeatureProvider.getTrustAgentManager();
        this.mLockPatternUtils = securityFeatureProvider.getLockPatternUtils(context);
        this.mPackageManager = context.getPackageManager();
    }

    private void findAvailableTrustAgents() {
        List<ResolveInfo> queryIntentServices =
                this.mPackageManager.queryIntentServices(TRUST_AGENT_INTENT, 128);
        this.mAvailableAgents.clear();
        for (ResolveInfo resolveInfo : queryIntentServices) {
            if (resolveInfo.serviceInfo != null) {
                TrustAgentManager trustAgentManager = this.mTrustAgentManager;
                PackageManager packageManager = this.mPackageManager;
                trustAgentManager.getClass();
                if (TrustAgentManager.shouldProvideTrust(packageManager, resolveInfo)) {
                    CharSequence loadLabel = resolveInfo.loadLabel(this.mPackageManager);
                    this.mTrustAgentManager.getClass();
                    ComponentName componentName = TrustAgentManager.getComponentName(resolveInfo);
                    this.mAvailableAgents.put(
                            componentName,
                            new TrustAgentInfo(
                                    loadLabel,
                                    componentName,
                                    this.mIconDrawableFactory.getBadgedIcon(
                                            resolveInfo.getComponentInfo().applicationInfo)));
                }
            }
        }
    }

    private void loadActiveAgents() {
        List enabledTrustAgents =
                this.mLockPatternUtils.getEnabledTrustAgents(UserHandle.myUserId());
        this.mActiveAgents.clear();
        if (enabledTrustAgents != null) {
            this.mActiveAgents.addAll(enabledTrustAgents);
        }
    }

    private void removeUselessExistingPreferences() {
        int preferenceCount = this.mScreen.getPreferenceCount();
        if (preferenceCount <= 0) {
            return;
        }
        for (int i = preferenceCount - 1; i >= 0; i--) {
            Preference preference = this.mScreen.getPreference(i);
            if (preference instanceof RestrictedSwitchPreference) {
                String[] split = TextUtils.split(preference.getKey(), "/");
                ComponentName componentName = new ComponentName(split[0], split[1]);
                if (!this.mAvailableAgents.containsKey(componentName)) {
                    this.mScreen.removePreference(preference);
                    this.mActiveAgents.remove(componentName);
                }
            }
        }
    }

    private void saveActiveAgents() {
        this.mLockPatternUtils.setEnabledTrustAgents(this.mActiveAgents, UserHandle.myUserId());
    }

    private void updateAgents() {
        findAvailableTrustAgents();
        loadActiveAgents();
        removeUselessExistingPreferences();
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        this.mContext, 16, UserHandle.myUserId());
        for (TrustAgentInfo trustAgentInfo : this.mAvailableAgents.values()) {
            ComponentName componentName = trustAgentInfo.mComponentName;
            RestrictedSwitchPreference restrictedSwitchPreference =
                    (RestrictedSwitchPreference)
                            this.mScreen.findPreference(componentName.flattenToString());
            if (restrictedSwitchPreference == null) {
                restrictedSwitchPreference =
                        new RestrictedSwitchPreference(this.mScreen.getContext());
            }
            restrictedSwitchPreference.setKey(componentName.flattenToString());
            restrictedSwitchPreference.mHelper.mDisabledSummary = true;
            restrictedSwitchPreference.setTitle(trustAgentInfo.mLabel);
            if (GMS_TRUST_AGENT_CLASSNAME.equals(trustAgentInfo.mComponentName.getClassName())) {
                restrictedSwitchPreference.setTitle(
                        this.mContext.getResources().getString(R.string.sec_extend_unlock_title));
            }
            restrictedSwitchPreference.setIcon(trustAgentInfo.mIcon);
            restrictedSwitchPreference.setOnPreferenceChangeListener(this);
            restrictedSwitchPreference.setChecked(this.mActiveAgents.contains(componentName));
            if (checkIfKeyguardFeaturesDisabled == null
                    || this.mDevicePolicyManager.getTrustAgentConfiguration(null, componentName)
                            != null) {
                restrictedSwitchPreference.setDisabledByAdmin(null);
            } else {
                restrictedSwitchPreference.setChecked(false);
                restrictedSwitchPreference.setDisabledByAdmin(checkIfKeyguardFeaturesDisabled);
            }
            this.mScreen.addPreference(restrictedSwitchPreference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof TwoStatePreference)) {
            return false;
        }
        Iterator<TrustAgentInfo> it = this.mAvailableAgents.values().iterator();
        while (it.hasNext()) {
            ComponentName componentName = it.next().mComponentName;
            if (TextUtils.equals(preference.getKey(), componentName.flattenToString())) {
                if (!((Boolean) obj).booleanValue() || this.mActiveAgents.contains(componentName)) {
                    this.mActiveAgents.remove(componentName);
                } else {
                    this.mActiveAgents.add(componentName);
                }
                saveActiveAgents();
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        updateAgents();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
