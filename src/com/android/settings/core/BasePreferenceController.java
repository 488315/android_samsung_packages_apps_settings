package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.slices.Sliceable;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.cube.CubeCallbackManager;
import com.sec.ims.IMSParameter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BasePreferenceController extends AbstractPreferenceController
        implements Sliceable {
    public static final int AVAILABLE = 0;
    public static final int AVAILABLE_UNSEARCHABLE = 1;
    public static final int CONDITIONALLY_UNAVAILABLE = 2;
    public static final int DISABLED_DEPENDENT_SETTING = 5;
    public static final int DISABLED_FOR_USER = 4;
    private static final String TAG = "SettingsPrefController";
    public static final int UNSUPPORTED_ON_DEVICE = 3;
    private ControlResult.ErrorCode mControlErrorCode;
    private String mControlErrorMessage;
    private String mControlId;
    private boolean mIsForWork;
    public boolean mLastAvailable;
    public boolean mLastAvailableChecked;
    private int mMetricsCategory;
    private boolean mPrefVisibility;
    protected final String mPreferenceKey;
    private String mStatusCode;
    protected UiBlockListener mUiBlockListener;
    protected boolean mUiBlockerFinished;
    private UserHandle mWorkProfileUser;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface UiBlockListener {
        void onBlockerWorkFinished(BasePreferenceController basePreferenceController);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface UiBlocker {}

    public BasePreferenceController(Context context, String str) {
        super(context);
        this.mControlErrorCode = ControlResult.ErrorCode.UNKNOWN;
        this.mControlErrorMessage = null;
        this.mPreferenceKey = str;
        this.mPrefVisibility = true;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Preference key must be set");
        }
    }

    public static BasePreferenceController createInstance(
            Context context, String str, String str2) {
        try {
            return (BasePreferenceController)
                    Class.forName(str)
                            .getConstructor(Context.class, String.class)
                            .newInstance(context, str2);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | IllegalArgumentException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid preference controller: ", str),
                    e);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference;
        super.displayPreference(preferenceScreen);
        if (getAvailabilityStatus() != 5
                || (findPreference = preferenceScreen.findPreference(getPreferenceKey())) == null) {
            return;
        }
        findPreference.setEnabled(false);
    }

    public abstract int getAvailabilityStatus();

    public int getAvailabilityStatusForControl() {
        if (this.mIsForWork && this.mWorkProfileUser == null) {
            return 4;
        }
        return getAvailabilityStatus();
    }

    public final ControlResult.ErrorCode getControlErrorCode() {
        return this.mControlErrorCode;
    }

    public final String getControlErrorMessage() {
        return this.mControlErrorMessage;
    }

    public final String getControlId() {
        return this.mControlId;
    }

    public int getControlType() {
        return 0;
    }

    public Uri getControlUri() {
        return null;
    }

    public abstract Intent getLaunchIntent();

    public int getMetricsCategory() {
        return this.mMetricsCategory;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    public boolean getSavedPrefVisibility() {
        return this.mPrefVisibility;
    }

    public int getSliceType() {
        return 0;
    }

    public Uri getSliceUri() {
        return new Uri.Builder()
                .scheme("content")
                .authority("com.android.settings.slices")
                .appendPath(IMSParameter.CALL.ACTION)
                .appendPath(getPreferenceKey())
                .build();
    }

    public final String getStatusCode() {
        return this.mStatusCode;
    }

    public abstract String getStatusText();

    public abstract ControlValue getValue();

    public UserHandle getWorkProfileUser() {
        return this.mWorkProfileUser;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())
                || !this.mIsForWork
                || this.mWorkProfileUser == null) {
            return false;
        }
        preference
                .getExtras()
                .putInt("android.intent.extra.USER_ID", this.mWorkProfileUser.getIdentifier());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(preference.getContext());
        String fragment = preference.getFragment();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragment;
        launchRequest.mSourceMetricsCategory = preference.getExtras().getInt("category", 0);
        launchRequest.mArguments = preference.getExtras();
        launchRequest.mUserHandle = this.mWorkProfileUser;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mIsForWork && this.mWorkProfileUser == null) {
            return false;
        }
        int availabilityStatus = getAvailabilityStatus();
        this.mLastAvailableChecked = true;
        boolean z = availabilityStatus == 0 || availabilityStatus == 1 || availabilityStatus == 5;
        this.mLastAvailable = z;
        return z;
    }

    public abstract boolean isControllable();

    public final boolean isSupported() {
        return getAvailabilityStatus() != 3;
    }

    public final boolean isUiThread() {
        return Thread.currentThread().equals(this.mContext.getMainLooper().getThread());
    }

    public abstract Controllable$ControllableType needUserInteraction(Object obj);

    public final void notifyControlCancel() {
        CubeCallbackManager cubeCallbackManager = CubeCallbackManager.LazyHolder.INSTANCE;
        String controlId = getControlId();
        cubeCallbackManager.getClass();
        String parsePreferenceKey = CubeCallbackManager.parsePreferenceKey(controlId);
        if (TextUtils.isEmpty(parsePreferenceKey)) {
            return;
        }
        ControlResult.Builder builder = new ControlResult.Builder(parsePreferenceKey);
        builder.mResultCode = ControlResult.ResultCode.FAIL;
        builder.mErrorCode = ControlResult.ErrorCode.CANCELED_BY_USER;
        cubeCallbackManager.notifyControlResult(controlId, new ControlResult(builder));
    }

    public final void notifyControlFailure(ControlResult.ErrorCode errorCode, String str) {
        CubeCallbackManager cubeCallbackManager = CubeCallbackManager.LazyHolder.INSTANCE;
        String controlId = getControlId();
        cubeCallbackManager.getClass();
        ControlResult.Builder builder =
                new ControlResult.Builder(CubeCallbackManager.parsePreferenceKey(controlId));
        builder.mResultCode = ControlResult.ResultCode.FAIL;
        builder.mErrorCode = errorCode;
        if (TextUtils.isEmpty(str)) {
            str = ApnSettings.MVNO_NONE;
        }
        builder.setErrorMsg(str);
        cubeCallbackManager.notifyControlResult(controlId, new ControlResult(builder));
    }

    public final void notifyControlSuccess() {
        CubeCallbackManager cubeCallbackManager = CubeCallbackManager.LazyHolder.INSTANCE;
        String controlId = getControlId();
        cubeCallbackManager.getClass();
        String parsePreferenceKey = CubeCallbackManager.parsePreferenceKey(controlId);
        if (TextUtils.isEmpty(parsePreferenceKey)) {
            return;
        }
        ControlResult.Builder builder = new ControlResult.Builder(parsePreferenceKey);
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        cubeCallbackManager.notifyControlResult(controlId, new ControlResult(builder));
    }

    public abstract boolean runDefaultAction();

    public final void setControlError(ControlResult.ErrorCode errorCode, String str) {
        this.mControlErrorCode = errorCode;
    }

    public final void setControlId(ControlValue controlValue) {
        this.mControlId = controlValue.mControlId;
    }

    public void setForWork(boolean z) {
        this.mIsForWork = z;
        if (z) {
            this.mWorkProfileUser = Utils.getManagedProfile(UserManager.get(this.mContext));
        }
    }

    public void setMetricsCategory(int i) {
        this.mMetricsCategory = i;
    }

    public final void setStatusCode(String str) {
        this.mStatusCode = str;
    }

    public void setUiBlockListener(UiBlockListener uiBlockListener) {
        this.mUiBlockListener = uiBlockListener;
    }

    public void setUiBlockerFinished(boolean z) {
        this.mUiBlockerFinished = z;
    }

    public abstract ControlResult setValue(ControlValue controlValue);

    public final void startPopupActivity(Intent intent) {
        intent.putExtra("controlId", getControlId());
        this.mContext.startActivity(intent);
    }

    public void updateNonIndexableKeys(List<String> list) {
        if (!isAvailable() || getAvailabilityStatus() == 1) {
            String preferenceKey = getPreferenceKey();
            if (TextUtils.isEmpty(preferenceKey)) {
                Log.w(TAG, "Skipping updateNonIndexableKeys due to empty key " + toString());
            } else {
                if (!list.contains(preferenceKey)) {
                    list.add(preferenceKey);
                    return;
                }
                Log.w(TAG, "Skipping updateNonIndexableKeys, key already in list. " + toString());
            }
        }
    }

    public void updatePreferenceVisibilityDelegate(Preference preference, boolean z) {
        if (this.mUiBlockerFinished) {
            preference.setVisible(z);
            return;
        }
        this.mPrefVisibility = z;
        if (z) {
            return;
        }
        preference.setVisible(false);
    }

    public static BasePreferenceController createInstance(Context context, String str) {
        try {
            return (BasePreferenceController)
                    Class.forName(str).getConstructor(Context.class).newInstance(context);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | IllegalArgumentException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid preference controller: ", str),
                    e);
        }
    }

    public static BasePreferenceController createInstance(
            Context context, String str, String str2, boolean z) {
        try {
            BasePreferenceController basePreferenceController =
                    (BasePreferenceController)
                            Class.forName(str)
                                    .getConstructor(Context.class, String.class)
                                    .newInstance(context, str2);
            basePreferenceController.setForWork(z);
            return basePreferenceController;
        } catch (ClassNotFoundException
                | IllegalAccessException
                | IllegalArgumentException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid preference controller: ", str),
                    e);
        }
    }

    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {}

    public void updateRawDataToIndex(List<SearchIndexableRaw> list) {}
}
