package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceInfo;

import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ConnectedSoundBalancePreferenceController
        extends AbstractSoundBalancePreferenceController implements AccessibilityUsingFunction {
    public ConnectedSoundBalancePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getActionButtonDescription(Context context) {
        return context.getString(R.string.using_functions_equal_sound_balance_button_contentDesc);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController
    public String getDbKey() {
        return "master_balance";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return 100;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "SoundBalanceConnectedAudio";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_hearing);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getString(
                R.string.sound_balance_title_with_hps,
                context.getString(R.string.sound_balance_connected_audio));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ int getUsingFunctionType() {
        return super.getUsingFunctionType();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController
    public boolean isCorrectDeviceConnected() {
        Iterator<AudioDeviceInfo> it = getConnectedDevices().iterator();
        while (it.hasNext()) {
            if (it.next().getType() != 2) {
                return true;
            }
        }
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.AbstractSoundBalancePreferenceController, com.android.settings.core.SliderPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
