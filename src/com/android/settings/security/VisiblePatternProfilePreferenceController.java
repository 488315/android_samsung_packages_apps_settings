package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VisiblePatternProfilePreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_VISIBLE_PATTERN_PROFILE = "visiblepattern_profile";
    private static final String TAG = "VisPtnProfPrefCtrl";
    private final LockPatternUtils mLockPatternUtils;
    private Preference mPreference;
    private final int mProfileChallengeUserId;
    private final UserManager mUm;
    private final int mUserId;

    public VisiblePatternProfilePreferenceController(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getAvailabilityStatus$0() throws Exception {
        return (this.mLockPatternUtils.isSecure(this.mProfileChallengeUserId)
                        && (this.mLockPatternUtils.getKeyguardStoredPasswordQuality(
                                        this.mProfileChallengeUserId)
                                == 65536))
                ? 0
                : 4;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.android.settings.security.VisiblePatternProfilePreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                Integer lambda$getAvailabilityStatus$0;
                                lambda$getAvailabilityStatus$0 =
                                        VisiblePatternProfilePreferenceController.this
                                                .lambda$getAvailabilityStatus$0();
                                return lambda$getAvailabilityStatus$0;
                            }
                        });
        try {
            futureTask.run();
            return ((Integer) futureTask.get()).intValue();
        } catch (InterruptedException | ExecutionException unused) {
            Log.w(TAG, "Error getting lock pattern state.");
            return 4;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_security;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mLockPatternUtils.isVisiblePatternEnabled(this.mProfileChallengeUserId);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(isAvailable());
            if (this.mLockPatternUtils.isVisiblePatternDisabledByMDMAsUser(
                    this.mProfileChallengeUserId)) {
                setChecked(false);
                this.mPreference.setEnabled(false);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (Utils.startQuietModeDialogIfNecessary(
                this.mContext, this.mUm, this.mProfileChallengeUserId)) {
            return false;
        }
        this.mLockPatternUtils.setVisiblePatternEnabled(z, this.mProfileChallengeUserId);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public VisiblePatternProfilePreferenceController(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, KEY_VISIBLE_PATTERN_PROFILE);
    }

    public VisiblePatternProfilePreferenceController(
            Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUm = userManager;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            this.mLockPatternUtils =
                    featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
            this.mProfileChallengeUserId = Utils.getManagedProfileId(userManager, myUserId);
            if (lifecycle != null) {
                lifecycle.addObserver(this);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }
}
