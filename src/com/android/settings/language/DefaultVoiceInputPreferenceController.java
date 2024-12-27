package com.android.settings.language;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.applications.defaultapps.DefaultAppPreferenceController;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultVoiceInputPreferenceController extends DefaultAppPreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    public Context mContext;
    public VoiceInputHelper mHelper;
    public Preference mPreference;
    public PreferenceScreen mScreen;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mScreen = preferenceScreen;
        this.mPreference = preferenceScreen.findPreference("voice_input_settings");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        VoiceInputHelper voiceInputHelper = this.mHelper;
        ComponentName componentName = voiceInputHelper.mCurrentRecognizer;
        String flattenToShortString =
                componentName == null ? null : componentName.flattenToShortString();
        if (flattenToShortString == null) {
            return null;
        }
        Iterator it = voiceInputHelper.mAvailableRecognizerInfos.iterator();
        while (it.hasNext()) {
            VoiceInputHelper.RecognizerInfo recognizerInfo =
                    (VoiceInputHelper.RecognizerInfo) it.next();
            if (TextUtils.equals(flattenToShortString, recognizerInfo.mKey)) {
                DefaultVoiceInputPicker.VoiceInputDefaultAppInfo voiceInputDefaultAppInfo =
                        new DefaultVoiceInputPicker.VoiceInputDefaultAppInfo(
                                this.mContext,
                                this.mPackageManager,
                                this.mUserId,
                                recognizerInfo.mComponentName,
                                0);
                voiceInputDefaultAppInfo.mInfo = recognizerInfo;
                return voiceInputDefaultAppInfo;
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "voice_input_settings";
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        DefaultAppInfo defaultAppInfo2 = getDefaultAppInfo();
        if (defaultAppInfo2 == null) {
            return null;
        }
        VoiceInputHelper.RecognizerInfo recognizerInfo =
                ((DefaultVoiceInputPicker.VoiceInputDefaultAppInfo) defaultAppInfo2).mInfo;
        if (recognizerInfo.mSettings == null) {
            return null;
        }
        return new Intent("android.intent.action.MAIN").setComponent(recognizerInfo.mSettings);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext
                .getPackageManager()
                .hasSystemFeature("android.software.voice_recognizers");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        updatePreference$4$1();
    }

    public final void updatePreference$4$1() {
        if (this.mPreference == null) {
            return;
        }
        this.mHelper.buildUi();
        if (!isAvailable()) {
            this.mScreen.removePreference(this.mPreference);
        } else if (this.mScreen.findPreference("voice_input_settings") == null) {
            this.mScreen.addPreference(this.mPreference);
        }
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(this.mPreference);
        updatePreference$4$1();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {}
}
