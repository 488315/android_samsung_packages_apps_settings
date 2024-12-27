package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceAttributes;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper;

import com.google.common.primitives.Ints;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HearingDeviceAudioRoutingBasePreferenceController
        extends BasePreferenceController implements Preference.OnPreferenceChangeListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "HARoutingBasePreferenceController";
    private final HearingAidAudioRoutingHelper mAudioRoutingHelper;
    private final HearingAidHelper mHearingAidHelper;

    public HearingDeviceAudioRoutingBasePreferenceController(Context context, String str) {
        this(
                context,
                str,
                new HearingAidAudioRoutingHelper(context),
                new HearingAidHelper(context));
    }

    private void trySetAudioRoutingConfig(
            int[] iArr, CachedBluetoothDevice cachedBluetoothDevice, int i) {
        List supportedStrategies = this.mAudioRoutingHelper.getSupportedStrategies(iArr);
        AudioDeviceAttributes matchedHearingDeviceAttributes =
                this.mAudioRoutingHelper.getMatchedHearingDeviceAttributes(cachedBluetoothDevice);
        if (matchedHearingDeviceAttributes == null
                || this.mAudioRoutingHelper.setPreferredDeviceRoutingStrategies(
                        supportedStrategies, matchedHearingDeviceAttributes, i)) {
            return;
        }
        Log.w(
                TAG,
                "routingMode: "
                        + ((List)
                                supportedStrategies.stream()
                                        .map(
                                                new HearingDeviceAudioRoutingBasePreferenceController$$ExternalSyntheticLambda0())
                                        .collect(Collectors.toList()))
                        + " routingValue: "
                        + i
                        + " fail to configure AudioProductStrategy");
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

    public abstract int[] getSupportedAttributeList();

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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Integer tryParse = Ints.tryParse((String) obj);
        saveRoutingValue(this.mContext, tryParse.intValue());
        if (this.mHearingAidHelper.getConnectedHearingAidDevice() == null) {
            return true;
        }
        trySetAudioRoutingConfig(
                getSupportedAttributeList(),
                this.mHearingAidHelper.getConnectedHearingAidDevice(),
                tryParse.intValue());
        return true;
    }

    public abstract int restoreRoutingValue(Context context);

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public abstract void saveRoutingValue(Context context, int i);

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((ListPreference) preference).setValue(String.valueOf(restoreRoutingValue(this.mContext)));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public HearingDeviceAudioRoutingBasePreferenceController(
            Context context,
            String str,
            HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper,
            HearingAidHelper hearingAidHelper) {
        super(context, str);
        this.mAudioRoutingHelper = hearingAidAudioRoutingHelper;
        this.mHearingAidHelper = hearingAidHelper;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
