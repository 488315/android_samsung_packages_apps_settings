package com.samsung.android.settings.usefulfeature.videoenhancer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class VideoEnhancerPreferenceController extends SecCustomPreferenceController
        implements LifecycleObserver, OnStart, OnStop, OnResume {
    public static final String KEY_HDR_EFFECT = "hdr_settings";
    private Handler mHandler;
    private Usefulfeature mHost;
    private SecPreference mPreference;
    private SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BRIGHTEN_UP_VIDEO;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BRIGHTEN_UP_VIDEO = Settings.System.getUriFor("hdr_effect");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (VideoEnhancerPreferenceController.this.mPreference != null) {
                VideoEnhancerPreferenceController.this.mPreference.setSummary(
                        Settings.System.getInt(
                                                ((AbstractPreferenceController)
                                                                VideoEnhancerPreferenceController
                                                                        .this)
                                                        .mContext.getContentResolver(),
                                                "hdr_effect",
                                                0)
                                        != 0
                                ? R.string.hdr_setting_bright
                                : R.string.hdr_setting_normal);
            }
        }

        public final void setListening(boolean z) {
            if (z) {
                ((AbstractPreferenceController) VideoEnhancerPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(this.BRIGHTEN_UP_VIDEO, false, this);
            } else {
                ((AbstractPreferenceController) VideoEnhancerPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(this);
            }
        }
    }

    public VideoEnhancerPreferenceController(Context context, Usefulfeature usefulfeature) {
        this(context, KEY_HDR_EFFECT);
        this.mHost = usefulfeature;
        if (usefulfeature != null) {
            usefulfeature.getSettingsLifecycle().addObserver(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(KEY_HDR_EFFECT);
        this.mPreference = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return UsefulfeatureUtils.isVideoBrightnessMenuDisabled(this.mContext) ? 5 : 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public List<String> getBackupKeys() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/Settings/Advanced/VideoEnhancerSwitch");
        arrayList.add("/Settings/Advanced/VideoEnhancerApp");
        return arrayList;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = HDReffectSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 4376;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.hdr_setting, 268468224);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                Settings.System.getInt(this.mContext.getContentResolver(), "hdr_effect", 0) != 0
                        ? R.string.hdr_setting_bright
                        : R.string.hdr_setting_normal);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Usefulfeature usefulfeature;
        if (!KEY_HDR_EFFECT.equals(preference.getKey())
                || this.mPreference == null
                || (usefulfeature = this.mHost) == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        usefulfeature.getActivity();
        Bundle bundle = new Bundle();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = HDReffectSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.hdr_setting, null);
        this.mHost.getClass();
        BaseSearchIndexProvider baseSearchIndexProvider = Usefulfeature.SEARCH_INDEX_DATA_PROVIDER;
        launchRequest.mSourceMetricsCategory = 4350;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mPreference.setSummary(
                Settings.System.getInt(this.mContext.getContentResolver(), "hdr_effect", 0) != 0
                        ? R.string.hdr_setting_bright
                        : R.string.hdr_setting_normal);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void updatePreference() {
        if (this.mPreference != null) {
            if (UsefulfeatureUtils.isVideoBrightnessMenuDisabled(this.mContext)) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
        }
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public VideoEnhancerPreferenceController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }
}
