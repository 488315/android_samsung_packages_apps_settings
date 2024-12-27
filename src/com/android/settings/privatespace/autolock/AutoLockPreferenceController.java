package com.android.settings.privatespace.autolock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoLockPreferenceController extends BasePreferenceController {
    private static final String TAG = "AutoLockPreferenceCtrl";
    private final CharSequence[] mAutoLockRadioOptions;
    private final PrivateSpaceMaintainer mPrivateSpaceMaintainer;

    public AutoLockPreferenceController(Context context, String str) {
        super(context, str);
        this.mPrivateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(context);
        this.mAutoLockRadioOptions =
                context.getResources().getStringArray(R.array.private_space_auto_lock_options);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.allowPrivateProfile()
                        && android.multiuser.Flags.supportAutolockForPrivateSpace()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures())
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
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        try {
            CharSequence[] charSequenceArr = this.mAutoLockRadioOptions;
            PrivateSpaceMaintainer privateSpaceMaintainer = this.mPrivateSpaceMaintainer;
            privateSpaceMaintainer.getClass();
            return charSequenceArr[
                    (Flags.allowPrivateProfile()
                                    && android.multiuser.Flags.supportAutolockForPrivateSpace()
                                    && android.multiuser.Flags.enablePrivateSpaceFeatures())
                            ? Settings.Secure.getInt(
                                    privateSpaceMaintainer.mContext.getContentResolver(),
                                    "private_space_auto_lock",
                                    0)
                            : 0];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "Invalid private space auto lock setting value" + e.getMessage());
            return this.mAutoLockRadioOptions[0];
        }
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
