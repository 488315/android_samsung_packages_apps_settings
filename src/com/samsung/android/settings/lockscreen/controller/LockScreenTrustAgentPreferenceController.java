package com.samsung.android.settings.lockscreen.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.security.trustagent.TrustAgentManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockScreenTrustAgentPreferenceController extends BasePreferenceController {
    private static final int CHANGE_TRUST_AGENT_SETTINGS = 177;
    private static final String KEY_TRUST_AGENT = "lockscreen_trust_agent";
    private static final String SMART_LOCK = "Smart Lock";
    private static final String TAG = "LockScreenTrustAgentPreferenceController";
    private Context mContext;
    private Fragment mHost;
    private LockPatternUtils mLockPatternUtils;
    private SecRestrictedPreference mPreference;
    private PreferenceScreen mScreen;
    private TrustAgentManager mTrustAgentManager;
    private TrustAgentManager.TrustAgentComponentInfo mTrustAgents;
    private int mUserId;

    public LockScreenTrustAgentPreferenceController(Context context) {
        this(context, null);
    }

    private void setIntentInformation() {
        if (this.mPreference.getIntent() == null) {
            Intent intent = new Intent();
            intent.setComponent(this.mTrustAgents.componentName);
            intent.setAction("android.intent.action.MAIN");
            this.mPreference.setIntent(intent);
        }
    }

    private void setPreferenceInformation() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        TrustAgentManager trustAgentManager =
                featureFactoryImpl.getSecurityFeatureProvider().getTrustAgentManager();
        this.mTrustAgentManager = trustAgentManager;
        Context context = this.mContext;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        trustAgentManager.getClass();
        List activeTrustAgents =
                TrustAgentManager.getActiveTrustAgents(context, lockPatternUtils, true);
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) activeTrustAgents;
            if (i >= arrayList.size()) {
                return;
            }
            this.mTrustAgents = (TrustAgentManager.TrustAgentComponentInfo) arrayList.get(i);
            i++;
        }
    }

    private void updatePreferenceInformation() {
        TrustAgentManager.TrustAgentComponentInfo trustAgentComponentInfo = this.mTrustAgents;
        if (trustAgentComponentInfo != null) {
            this.mPreference.setDisabledByAdmin(trustAgentComponentInfo.admin);
            setIntentInformation();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecRestrictedPreference) preferenceScreen.findPreference(getPreferenceKey());
        updatePreferenceInformation();
        this.mScreen = preferenceScreen;
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mTrustAgents == null) {
            return 4;
        }
        return !this.mLockPatternUtils.isSecure(this.mUserId)
                ? this.mLockPatternUtils.isLockScreenDisabled(this.mUserId) ? 4 : 5
                : (Rune.isSamsungDexMode(this.mContext) || UCMUtils.isUCMKeyguardEnabled()) ? 5 : 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (this.mLockPatternUtils.isSecure(this.mUserId)) {
            return ApnSettings.MVNO_NONE;
        }
        return this.mContext
                .getResources()
                .getString(
                        Utils.isTablet()
                                ? R.string.lock_screen_smart_lock_disabled_summary_tablet
                                : R.string.lock_screen_smart_lock_disabled_summary);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey()) || this.mHost == null) {
            return false;
        }
        setIntentInformation();
        LoggingHelper.insertEventLogging(4400, "LSE4438");
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(this.mHost.getActivity(), this.mHost);
        builder.mRequestCode = 177;
        builder.mForceVerifyPath = true;
        builder.mTitle = preference.getTitle();
        if (!builder.show() && this.mPreference.getIntent() != null) {
            this.mPreference.setIntent(null);
        }
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void startTrustAgent() {
        if (this.mPreference.getIntent() != null) {
            try {
                this.mContext.startActivity(this.mPreference.getIntent());
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            this.mPreference.setIntent(null);
        }
    }

    public void updatePreference() {
        if (getAvailabilityStatus() == 0) {
            this.mPreference.setEnabled(true);
        }
        displayPreference(this.mScreen);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public LockScreenTrustAgentPreferenceController(Context context, Fragment fragment) {
        super(context, KEY_TRUST_AGENT);
        this.mUserId = UserHandle.myUserId();
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mContext = context;
        this.mHost = fragment;
        setPreferenceInformation();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
