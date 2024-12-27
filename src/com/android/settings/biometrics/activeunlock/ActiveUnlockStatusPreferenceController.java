package com.android.settings.biometrics.activeunlock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricStatusPreferenceController;
import com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener;
import com.android.settings.biometrics.combination.CombinedBiometricStatusUtils;
import com.android.settingslib.RestrictedPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ActiveUnlockStatusPreferenceController extends BiometricStatusPreferenceController implements LifecycleObserver {
    public static final String KEY_ACTIVE_UNLOCK_SETTINGS = "biometric_active_unlock_settings";
    private final ActiveUnlockDeviceNameListener mActiveUnlockDeviceNameListener;
    private final ActiveUnlockStatusUtils mActiveUnlockStatusUtils;
    private final ActiveUnlockSummaryListener mActiveUnlockSummaryListener;
    private final CombinedBiometricStatusUtils mCombinedBiometricStatusUtils;
    private final boolean mIsAvailable;
    private RestrictedPreference mPreference;
    private PreferenceScreen mPreferenceScreen;
    private String mSummary;

    public ActiveUnlockStatusPreferenceController(Context context) {
        this(context, KEY_ACTIVE_UNLOCK_SETTINGS);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        RestrictedPreference restrictedPreference = (RestrictedPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = restrictedPreference;
        updateState(restrictedPreference);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mActiveUnlockStatusUtils.getAvailability();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSettingsClassName() {
        return ActiveUnlockRequireBiometricSetup.class.getName();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSummaryText() {
        this.mActiveUnlockStatusUtils.getClass();
        String str = this.mSummary;
        return str == null ? " " : str;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isDeviceSupported() {
        return this.mIsAvailable;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isHardwareSupported() {
        return Utils.hasFaceHardware(this.mContext) || Utils.hasFingerprintHardware(this.mContext);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen != null) {
            displayPreference(preferenceScreen);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (this.mIsAvailable) {
            this.mActiveUnlockSummaryListener.mContentListener.subscribe();
            this.mActiveUnlockDeviceNameListener.mActiveUnlockContentListener.subscribe();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (this.mIsAvailable) {
            this.mActiveUnlockSummaryListener.mContentListener.unsubscribe();
            this.mActiveUnlockDeviceNameListener.mActiveUnlockContentListener.unsubscribe();
        }
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.settings.biometrics.activeunlock.ActiveUnlockStatusPreferenceController$1] */
    public ActiveUnlockStatusPreferenceController(Context context, String str) {
        super(context, str);
        ActiveUnlockStatusUtils activeUnlockStatusUtils = new ActiveUnlockStatusUtils(context);
        this.mActiveUnlockStatusUtils = activeUnlockStatusUtils;
        this.mIsAvailable = activeUnlockStatusUtils.isAvailable();
        this.mCombinedBiometricStatusUtils = new CombinedBiometricStatusUtils(context, getUserId());
        final int i = 0;
        ?? r4 = new ActiveUnlockContentListener.OnContentChangedListener(this) { // from class: com.android.settings.biometrics.activeunlock.ActiveUnlockStatusPreferenceController.1
            public final /* synthetic */ ActiveUnlockStatusPreferenceController this$0;

            {
                this.this$0 = this;
            }

            @Override // com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener.OnContentChangedListener
            public final void onContentChanged(String str2) {
                switch (i) {
                    case 0:
                        ActiveUnlockStatusPreferenceController activeUnlockStatusPreferenceController = this.this$0;
                        activeUnlockStatusPreferenceController.mSummary = str2;
                        if (activeUnlockStatusPreferenceController.mPreference != null) {
                            activeUnlockStatusPreferenceController.mPreference.setSummary(activeUnlockStatusPreferenceController.getSummaryText());
                            break;
                        }
                        break;
                    default:
                        ActiveUnlockStatusPreferenceController activeUnlockStatusPreferenceController2 = this.this$0;
                        if (activeUnlockStatusPreferenceController2.mPreference != null) {
                            activeUnlockStatusPreferenceController2.mPreference.setSummary(activeUnlockStatusPreferenceController2.getSummaryText());
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        ActiveUnlockContentListener.OnContentChangedListener onContentChangedListener = new ActiveUnlockContentListener.OnContentChangedListener(this) { // from class: com.android.settings.biometrics.activeunlock.ActiveUnlockStatusPreferenceController.1
            public final /* synthetic */ ActiveUnlockStatusPreferenceController this$0;

            {
                this.this$0 = this;
            }

            @Override // com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener.OnContentChangedListener
            public final void onContentChanged(String str2) {
                switch (i2) {
                    case 0:
                        ActiveUnlockStatusPreferenceController activeUnlockStatusPreferenceController = this.this$0;
                        activeUnlockStatusPreferenceController.mSummary = str2;
                        if (activeUnlockStatusPreferenceController.mPreference != null) {
                            activeUnlockStatusPreferenceController.mPreference.setSummary(activeUnlockStatusPreferenceController.getSummaryText());
                            break;
                        }
                        break;
                    default:
                        ActiveUnlockStatusPreferenceController activeUnlockStatusPreferenceController2 = this.this$0;
                        if (activeUnlockStatusPreferenceController2.mPreference != null) {
                            activeUnlockStatusPreferenceController2.mPreference.setSummary(activeUnlockStatusPreferenceController2.getSummaryText());
                            break;
                        }
                        break;
                }
            }
        };
        this.mActiveUnlockSummaryListener = new ActiveUnlockSummaryListener(context, r4);
        this.mActiveUnlockDeviceNameListener = new ActiveUnlockDeviceNameListener(context, onContentChangedListener);
    }
}
