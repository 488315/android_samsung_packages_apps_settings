package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingPasswordPreferenceController extends BasePreferenceController
        implements ValidatedEditTextPreference.Validator {
    private static final String PREF_KEY = "audio_sharing_stream_password";
    private static final String SHARED_PREF_KEY = "default_password";
    private static final String SHARED_PREF_NAME = "audio_sharing_settings";
    private static final String TAG = "AudioSharingPasswordPreferenceController";
    private final AudioSharingPasswordValidator mAudioSharingPasswordValidator;
    private final LocalBluetoothLeBroadcast mBroadcast;
    private final LocalBluetoothManager mBtManager;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private AudioSharingPasswordPreference mPreference;

    public AudioSharingPasswordPreferenceController(Context context, String str) {
        super(context, str);
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mBtManager = localBluetoothManager;
        this.mBroadcast =
                localBluetoothManager != null
                        ? localBluetoothManager.mProfileManager.mLeAudioBroadcast
                        : null;
        this.mAudioSharingPasswordValidator = new AudioSharingPasswordValidator();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private static String getDefaultPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        if (sharedPreferences == null) {
            Log.w(TAG, "getDefaultPassword(): sharedPref is empty!");
            return ApnSettings.MVNO_NONE;
        }
        String string = sharedPreferences.getString(SHARED_PREF_KEY, ApnSettings.MVNO_NONE);
        if (string != null && string.isEmpty()) {
            Log.w(TAG, "getDefaultPassword(): default password is empty!");
        }
        return string;
    }

    private static boolean isPublicBroadcast(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$onPreferenceDataChanged$0(String str, boolean z) {
        if (this.mBroadcast == null || AudioSharingUtils.isBroadcasting(this.mBtManager)) {
            Log.w(TAG, "onPreferenceDataChanged() changing password when broadcasting or null!");
            return;
        }
        boolean isPublicBroadcast = isPublicBroadcast(this.mBroadcast.mBroadcastCode);
        if (str.equals(getDefaultPassword(this.mContext)) && isPublicBroadcast == z) {
            Log.d(TAG, "onPreferenceDataChanged() nothing changed");
            return;
        }
        persistDefaultPassword(this.mContext, str);
        this.mBroadcast.setBroadcastCode(z ? new byte[0] : str.getBytes(), true);
        updatePreference();
        this.mMetricsFeatureProvider.action(this.mContext, 1944, z ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePreference$1(String str, String str2) {
        AudioSharingPasswordPreference audioSharingPasswordPreference = this.mPreference;
        if (audioSharingPasswordPreference != null) {
            audioSharingPasswordPreference.setText(str);
            this.mPreference.setSummary(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updatePreference$2() {
        byte[] bArr = this.mBroadcast.mBroadcastCode;
        boolean isPublicBroadcast = isPublicBroadcast(bArr);
        final String defaultPassword =
                isPublicBroadcast
                        ? getDefaultPassword(this.mContext)
                        : new String(bArr, StandardCharsets.UTF_8);
        final String string =
                isPublicBroadcast
                        ? this.mContext.getString(R.string.audio_streams_no_password_summary)
                        : "********";
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingPasswordPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingPasswordPreferenceController.this.lambda$updatePreference$1(
                                defaultPassword, string);
                    }
                });
    }

    private static void persistDefaultPassword(Context context, String str) {
        if (getDefaultPassword(context).equals(str)) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        if (sharedPreferences == null) {
            Log.w(TAG, "persistDefaultPassword(): sharedPref is empty!");
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(SHARED_PREF_KEY, str);
        edit.apply();
    }

    private void updatePreference() {
        if (this.mBroadcast == null || this.mPreference == null) {
            return;
        }
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingPasswordPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingPasswordPreferenceController.this.lambda$updatePreference$2();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        AudioSharingPasswordPreference audioSharingPasswordPreference =
                (AudioSharingPasswordPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = audioSharingPasswordPreference;
        if (audioSharingPasswordPreference != null) {
            audioSharingPasswordPreference.mValidator = this;
            audioSharingPasswordPreference.mIsPassword = true;
            audioSharingPasswordPreference.mDialogLayoutResId =
                    R.layout.audio_sharing_password_dialog;
            audioSharingPasswordPreference.mOnDialogEventListener = this;
            updatePreference();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        BluetoothAdapter.getDefaultAdapter();
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return PREF_KEY;
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

    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public boolean isTextValid(String str) {
        return this.mAudioSharingPasswordValidator.isTextValid(str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onBindDialogView() {
        AudioSharingPasswordPreference audioSharingPasswordPreference = this.mPreference;
        if (audioSharingPasswordPreference == null || this.mBroadcast == null) {
            return;
        }
        boolean z = !AudioSharingUtils.isBroadcasting(this.mBtManager);
        if (audioSharingPasswordPreference.mEditText == null
                || audioSharingPasswordPreference.mCheckBox == null
                || audioSharingPasswordPreference.mDialogMessage == null) {
            Log.w("AudioSharingPasswordPreference", "setEditable() : Invalid layout");
        } else {
            audioSharingPasswordPreference.mEditable = z;
            audioSharingPasswordPreference.setEditTextEnabled(z);
            audioSharingPasswordPreference.mCheckBox.setEnabled(z);
            audioSharingPasswordPreference.mDialogMessage.setVisibility(z ? 8 : 0);
        }
        byte[] bArr = this.mBroadcast.mBroadcastCode;
        AudioSharingPasswordPreference audioSharingPasswordPreference2 = this.mPreference;
        boolean isPublicBroadcast = isPublicBroadcast(bArr);
        CheckBox checkBox = audioSharingPasswordPreference2.mCheckBox;
        if (checkBox == null) {
            Log.w("AudioSharingPasswordPreference", "setChecked() : Invalid layout");
        } else {
            checkBox.setChecked(isPublicBroadcast);
        }
    }

    public void onPreferenceDataChanged(final String str, final boolean z) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingPasswordPreferenceController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingPasswordPreferenceController.this
                                .lambda$onPreferenceDataChanged$0(str, z);
                    }
                });
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
