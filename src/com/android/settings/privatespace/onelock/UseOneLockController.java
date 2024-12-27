package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UseOneLockController extends BasePreferenceController {
    private static final String TAG = "UseOneLockController";
    private final LockPatternUtils mLockPatternUtils;
    private final PrivateSpaceMaintainer mPrivateSpaceMaintainer;

    public UseOneLockController(Context context, String str) {
        super(context, str);
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(this.mContext);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
    }

    private int getCredentialTypeResId(int i) {
        int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(i);
        return credentialTypeForUser != 1
                ? credentialTypeForUser != 3
                        ? credentialTypeForUser != 4
                                ? R.string.unlock_set_unlock_mode_off
                                : R.string.unlock_set_unlock_mode_password
                        : R.string.unlock_set_unlock_mode_pin
                : R.string.unlock_set_unlock_mode_pattern;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures())
                ? 0
                : 3;
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
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        UserHandle privateProfileHandle = this.mPrivateSpaceMaintainer.getPrivateProfileHandle();
        if (privateProfileHandle != null) {
            int identifier = privateProfileHandle.getIdentifier();
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier)) {
                return this.mContext.getString(getCredentialTypeResId(identifier));
            }
        } else {
            Log.w(TAG, "Did not find Private Space.");
        }
        return this.mContext.getString(R.string.private_space_screen_lock_summary);
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
