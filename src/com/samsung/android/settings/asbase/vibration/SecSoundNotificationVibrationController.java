package com.samsung.android.settings.asbase.vibration;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundNotificationVibrationController extends SecSoundSettingPrefController {
    private static final String EV_SYNC_SOUND = "-1";
    private static final String KEY = "notification_vibration_pattern";
    private SettingsPreferenceFragment mFragment;
    private SecVibrationSimHelper mSimHelper;

    public SecSoundNotificationVibrationController(Context context, String str) {
        super(context, KEY);
    }

    private boolean isAvailablePattern(String str) {
        boolean z = false;
        try {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    Uri.parse(
                                            "content://com.android.settings.personalvibration.PersonalVibrationProvider/notification"),
                                    null,
                                    "vibration_pattern=?",
                                    new String[] {str},
                                    null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        z = true;
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (VibRune.SUPPORT_SEC_VIBRATION_PICKER && VibUtils.hasVibrator(this.mContext))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        String num =
                Settings.System.getInt(
                                        this.mContext.getContentResolver(),
                                        "sync_vibration_with_notification",
                                        0)
                                == 1
                        ? EV_SYNC_SOUND
                        : Integer.toString(
                                Settings.System.getInt(
                                        this.mContext.getContentResolver(),
                                        "notification_vibration_sep_index",
                                        50034));
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(num);
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        builder.mSummary = this.mSimHelper.getVibrationPatternSummary();
        return builder.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SALogging.insertSALog(String.valueOf(30400));
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.putExtra("vibration_type", 1);
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.samsung.android.settings.asbase.vibration.VibPickerContainer");
            this.mFragment.startActivityForResult(this.mSimHelper.mSimType.getIntent(), 0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        String value = controlValue.getValue();
        boolean isEmpty = TextUtils.isEmpty(value);
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (isEmpty) {
            builder.mResultCode = resultCode;
            return builder.build();
        }
        boolean equals = EV_SYNC_SOUND.equals(value);
        ControlResult.ErrorCode errorCode = ControlResult.ErrorCode.NOT_SUPPORT_DEVICE;
        if (!equals) {
            if (VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "sync_vibration_with_notification", 0);
            }
            if (!isAvailablePattern(value)) {
                builder.mResultCode = resultCode;
                builder.mErrorCode = errorCode;
                return builder.build();
            }
            SecSoundDeviceVibrationController$$ExternalSyntheticOutline0.m(
                    this.mContext.getContentResolver(), value, "notification_vibration_sep_index");
        } else {
            if (!VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                builder.mResultCode = resultCode;
                builder.mErrorCode = errorCode;
                return builder.build();
            }
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "sync_vibration_with_notification", 1);
        }
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        SecPreferenceScreen secPreferenceScreen = (SecPreferenceScreen) preference;
        secPreferenceScreen.setSummary(this.mSimHelper.getVibrationPatternSummary());
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecSoundNotificationVibrationController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, KEY);
        this.mFragment = settingsPreferenceFragment;
        this.mSimHelper = new SecVibrationSimHelper(context, 1);
    }
}
