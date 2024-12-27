package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.development.OnActivityResultListener;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.presence.ServiceTuple;

import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundRingtoneController extends SecSoundSettingPrefController
        implements OnActivityResultListener {
    public static final String DOCUMENTS_UI_POLICY = "DocumentsUIPolicy";
    public static final int DOCUMENTS_UI_POLICY_DEFAULT = 0;
    public static final int DOCUMENTS_UI_POLICY_SEC = 1;
    private static final String KEY = "ringtone";
    private static final int REQUEST_AUDIO_RINGTONE = 1;
    private static final int REQUEST_AUDIO_RINGTONE_2 = 2;
    private AudioManager mAudioManager;
    private SettingsPreferenceFragment mFragment;
    private DefaultRingtonePreference mPreference;

    public SecSoundRingtoneController(Context context, String str) {
        super(context, KEY);
        this.mAudioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (DefaultRingtonePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!Rune.isLDUModel() && this.mAudioManager.shouldShowRingtoneVolume()) ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
        if (defaultRingtonePreference != null) {
            return defaultRingtonePreference.getSummary();
        }
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        Uri actualDefaultRingtoneUri =
                RingtoneManager.getActualDefaultRingtoneUri(this.mContext, 1);
        String volumeName = MediaStore.getVolumeName(actualDefaultRingtoneUri);
        String queryParameter =
                actualDefaultRingtoneUri.getQueryParameter(UniversalCredentialUtil.AGENT_TITLE);
        String queryParameter2 = actualDefaultRingtoneUri.getQueryParameter("highlight_offset");
        int checkRingtoneVolumeType =
                SoundUtils.checkRingtoneVolumeType(this.mContext, queryParameter, volumeName);
        if (checkRingtoneVolumeType == 0) {
            ControlValue.Builder builder =
                    new ControlValue.Builder(getPreferenceKey(), getControlType());
            builder.setValue(queryParameter);
            builder.mSummary = queryParameter;
            builder.mAvailabilityStatus = getAvailabilityStatusForControl();
            builder.mStatusCode = getStatusCode();
            return builder.build();
        }
        if (checkRingtoneVolumeType != 1) {
            return null;
        }
        ControlValue.Builder builder2 =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder2.setValue(queryParameter);
        builder2.mSummary = queryParameter;
        builder2.mUri = actualDefaultRingtoneUri;
        builder2.addAttribute("highlight_offset", queryParameter2);
        builder2.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder2.mStatusCode = getStatusCode();
        return builder2.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SALogging.insertSALog(String.valueOf(4011));
        try {
            DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
            defaultRingtonePreference.onPrepareRingtonePickerIntent(
                    defaultRingtonePreference.getIntent());
            this.mFragment.startActivityForResult(this.mPreference.getIntent(), 1);
        } catch (ActivityNotFoundException e) {
            Log.e("SoundSettings", "handlePreferenceTreeClick", e);
            SoundUtils.showToEnableSecSoundPickerDialog(this.mContext);
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

    @Override // com.android.settings.development.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        DefaultRingtonePreference defaultRingtonePreference;
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult: requestCode : ", ", resultCode : ", i, i2, "SoundSettings");
        if (i == 1
                && ((i2 == -1 || i2 == 1)
                        && (defaultRingtonePreference = this.mPreference) != null)) {
            defaultRingtonePreference.onActivityResult(i, i2, intent);
        }
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        int i;
        int i2 = 1;
        if (SimUtils.isMultiSimEnabled(this.mContext)) {
            DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
            if (defaultRingtonePreference != null) {
                defaultRingtonePreference.mRingtoneType = 1;
            }
            setUpdateRingtoneType(3);
        } else {
            if (SimUtils.isEnabledSIM2Only()) {
                i2 = 128;
                i = 2;
            } else {
                i = 1;
            }
            DefaultRingtonePreference defaultRingtonePreference2 = this.mPreference;
            if (defaultRingtonePreference2 != null) {
                defaultRingtonePreference2.mRingtoneType = i2;
            }
            setUpdateRingtoneType(i);
        }
        super.onResume();
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
        ControlResult.ResultCode resultCode;
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        Uri uri = controlValue.mUri;
        String value = controlValue.getValue();
        if (uri == null) {
            if (!TextUtils.isEmpty(value)) {
                Log.d("SoundSettings", "setValue ( TYPE_RINGTONE, uri = " + value + " )");
                setActualRingtoneUri(value, 1);
            }
            resultCode = ControlResult.ResultCode.FAIL;
            builder.mResultCode = resultCode;
            return builder.build();
        }
        String attribute = controlValue.getAttribute("highlight_offset");
        try {
            Uri addCustomExternalRingtone =
                    new RingtoneManager(this.mContext).addCustomExternalRingtone(uri, 1);
            if (!TextUtils.isEmpty(attribute)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("bookmark", attribute);
                this.mContext
                        .getContentResolver()
                        .update(addCustomExternalRingtone, contentValues, null, null);
                addCustomExternalRingtone =
                        addCustomExternalRingtone
                                .buildUpon()
                                .appendQueryParameter("highlight_offset", attribute)
                                .build();
            }
            if (addCustomExternalRingtone != null) {
                Log.d(
                        "SoundSettings",
                        "setValue ( TYPE_RINGTONE, custom uri = "
                                + addCustomExternalRingtone.toString()
                                + " )");
                RingtoneManager.setActualDefaultRingtoneUri(
                        this.mContext, 1, addCustomExternalRingtone);
            }
        } catch (IOException | IllegalArgumentException e) {
            Log.e("SoundSettings", "Unable to add new ringtone", e);
        }
        resultCode = ControlResult.ResultCode.FAIL;
        builder.mResultCode = resultCode;
        return builder.build();
        resultCode = ControlResult.ResultCode.SUCCESS;
        builder.mResultCode = resultCode;
        return builder.build();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        DefaultRingtonePreference defaultRingtonePreference = this.mPreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.getClass();
            SecPreferenceUtils.applySummaryColor(defaultRingtonePreference, true);
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController,
              // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecSoundRingtoneController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, lifecycle, KEY);
        this.mFragment = settingsPreferenceFragment;
        this.mAudioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }
}
