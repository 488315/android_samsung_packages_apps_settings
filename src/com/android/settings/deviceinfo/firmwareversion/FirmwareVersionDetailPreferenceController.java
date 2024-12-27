package com.android.settings.deviceinfo.firmwareversion;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.internal.app.PlatLogoActivity;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FirmwareVersionDetailPreferenceController extends BasePreferenceController {
    private static final int ACTIVITY_TRIGGER_COUNT = 3;
    private static final int DELAY_TIMER_MILLIS = 500;
    private static final String TAG = "firmwareDialogCtrl";
    private RestrictedLockUtils.EnforcedAdmin mFunDisallowedAdmin;
    private boolean mFunDisallowedBySystem;
    private final long[] mHits;
    private final UserManager mUserManager;

    public FirmwareVersionDetailPreferenceController(Context context, String str) {
        super(context, str);
        this.mHits = new long[3];
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        initializeAdminPermissions();
    }

    public void arrayCopy() {
        long[] jArr = this.mHits;
        System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()) {
            return false;
        }
        arrayCopy();
        long[] jArr = this.mHits;
        jArr[jArr.length - 1] = SystemClock.uptimeMillis();
        if (this.mHits[0] >= SystemClock.uptimeMillis() - 500) {
            if (this.mUserManager.hasUserRestriction("no_fun")) {
                RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.mFunDisallowedAdmin;
                if (enforcedAdmin != null && !this.mFunDisallowedBySystem) {
                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                            this.mContext, enforcedAdmin);
                }
                Log.d(TAG, "Sorry, no fun for you!");
                return true;
            }
            Intent addFlags =
                    new Intent("android.intent.action.MAIN")
                            .setClassName(
                                    RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME,
                                    PlatLogoActivity.class.getName())
                            .addFlags(268435456);
            try {
                this.mContext.startActivity(addFlags);
            } catch (Exception unused) {
                Log.e(TAG, "Unable to start activity " + addFlags.toString());
            }
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initializeAdminPermissions() {
        this.mFunDisallowedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_fun");
        this.mFunDisallowedBySystem =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        this.mContext, UserHandle.myUserId(), "no_fun");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return true;
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
    public boolean useDynamicSliceSummary() {
        return true;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
