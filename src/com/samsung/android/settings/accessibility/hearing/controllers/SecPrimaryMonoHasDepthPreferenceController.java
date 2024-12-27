package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecPrimaryMonoHasDepthPreferenceController extends SecPrimaryMonoPreferenceController
        implements A11yStatusLoggingProvider {
    public SecPrimaryMonoHasDepthPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (SecAccessibilityUtils.isSupportStereoSpeaker()) {
            return getMonoAudioAvailability();
        }
        return 3;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    public int getMonoAudioType() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "mono_audio_type", 0);
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (getAvailabilityStatus() == 3) {
            return Map.of();
        }
        return Map.of(
                "A11YS4005",
                getThreadEnabled()
                        ? SecAccessibilityUtils.isSupportStereoSpeaker()
                                ? getMonoAudioType() == 0
                                        ? "ConnectedAudio"
                                        : "ConnectedAudioAndDeviceSpeaker"
                                : "NoOptions"
                        : "Off");
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (getThreadEnabled()) {
            return this.mContext.getText(
                    getMonoAudioType() == 0
                            ? R.string.mono_audio_type_connected_audio
                            : R.string.mono_audio_type_all);
        }
        return this.mContext.getText(R.string.mono_audio_summary);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        ArrayList arrayList = new ArrayList(super.getUriList());
        arrayList.add(Settings.System.getUriFor("mono_audio_type"));
        return arrayList;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ int getUsingFunctionType() {
        return super.getUsingFunctionType();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
        if (preference != null) {
            preference.seslSetSummaryColor(
                    this.mContext.getColorStateList(
                            getThreadEnabled()
                                    ? R.color.text_color_primary_dark
                                    : R.color.text_color_secondary));
        }
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecPrimaryMonoPreferenceController, com.android.settings.accessibility.PrimaryMonoPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
