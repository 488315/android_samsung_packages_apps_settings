package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.utils.BudsPluginConstants;
import com.samsung.android.settings.asbase.utils.SystemBooleanSettingObserver;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundEffectSoundController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String BUDS_CONNECTION_STATE = "buds_enable";
    private static final String KEY = "sound_effect";
    private static final String TAG = "AudioSetting.SecSoundEffectSoundController";
    private final SettingsPreferenceFragment mFragment;
    private Preference mPreference;
    private final SystemBooleanSettingObserver mSettingObserver;

    /* JADX WARN: Type inference failed for: r4v1, types: [com.samsung.android.settings.asbase.audio.SecSoundEffectSoundController$$ExternalSyntheticLambda0] */
    public SecSoundEffectSoundController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, KEY);
        lifecycle.addObserver(this);
        this.mFragment = settingsPreferenceFragment;
        this.mSettingObserver =
                new SystemBooleanSettingObserver(
                        context,
                        new Function1() { // from class:
                                          // com.samsung.android.settings.asbase.audio.SecSoundEffectSoundController$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit lambda$new$0;
                                lambda$new$0 =
                                        SecSoundEffectSoundController.this.lambda$new$0(
                                                (Boolean) obj);
                                return lambda$new$0;
                            }
                        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$new$0(Boolean bool) {
        Preference preference = this.mPreference;
        if (preference == null) {
            return null;
        }
        updateState(preference);
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        SemLog.d(TAG, "displayPreference : mPreference=" + this.mPreference);
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            SemLog.d(
                    TAG,
                    "handlePreferenceTreeClick : preference="
                            + preference
                            + ", isSupportBudsSettingJump="
                            + BudsPluginConstants.isSupportBudsSettingJump(this.mContext));
            if (BudsPluginConstants.isSupportBudsSettingJump(this.mContext)) {
                Context context = this.mContext;
                context.startActivity(
                        BudsPluginConstants.getBudsSoundEffectActivityIntent(context));
                return true;
            }
            Intent intent = new Intent("android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL");
            intent.putExtra("android.media.extra.AUDIO_SESSION", 0);
            try {
                this.mFragment.startActivityForResult(intent, 0);
                LoggingHelper.insertEventLogging(FileType.SDOCX, 7231);
                return true;
            } catch (ActivityNotFoundException unused) {
            }
        }
        return false;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        SemLog.d(TAG, "onStart : mPreference=" + this.mPreference);
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
        SystemBooleanSettingObserver systemBooleanSettingObserver = this.mSettingObserver;
        systemBooleanSettingObserver.getClass();
        Log.d("SoundCraft.SystemBooleanSettingObserver", "register");
        systemBooleanSettingObserver.updateValue();
        systemBooleanSettingObserver
                .context
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor(systemBooleanSettingObserver.systemSettingName),
                        false,
                        systemBooleanSettingObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        SemLog.d(TAG, "onStop : mPreference=" + this.mPreference);
        SystemBooleanSettingObserver systemBooleanSettingObserver = this.mSettingObserver;
        systemBooleanSettingObserver.getClass();
        Log.d("SoundCraft.SystemBooleanSettingObserver", "unreigster");
        systemBooleanSettingObserver
                .context
                .getContentResolver()
                .unregisterContentObserver(systemBooleanSettingObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        SemLog.d(
                TAG,
                "updateState : preference="
                        + preference
                        + ", isSupportBudsSettingJump="
                        + BudsPluginConstants.isSupportBudsSettingJump(this.mContext));
        boolean z =
                (!Rune.isSamsungDexMode(this.mContext)
                                || Utils.isDesktopStandaloneMode(this.mContext)
                                || Utils.isNewDexMode(this.mContext))
                        ? false
                        : true;
        super.updateState(preference);
        preference.setEnabled(!z);
        if (!BudsPluginConstants.isSupportBudsSettingJump(this.mContext)) {
            preference.setSummary((CharSequence) null);
        } else {
            preference.setSummary(
                    R.string
                            .keywords_sound_and_vibrations_settings_sound_quality_and_effects_buds_sub);
            SecPreferenceUtils.applySummaryColor((SecPreferenceScreen) preference, true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
