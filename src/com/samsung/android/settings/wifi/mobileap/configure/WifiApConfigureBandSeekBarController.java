package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApBandConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureBandSeekBarController extends SliderPreferenceController
        implements LifecycleEventObserver {
    private static final String BUNDLE_KEY_BAND_SEEKBAR_VALUE = "bundle_key_band_seekbar_value";
    public static final String KEY_PREFERENCE = "band_seekbar_preference";
    private static final String TAG = "WifiApConfigureBandSeekBarController";
    private Context mContext;
    private WifiApSeekBarPreference.OnPreferenceItemsCLickListener mPreferenceItemsCLickListener;
    private WifiApSeekBarPreference mThisSeekBarPreference;
    private WifiApBandConfig mWifiApBandConfig;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureBandSeekBarController(Context context, String str) {
        super(context, str);
        this.mPreferenceItemsCLickListener = new AnonymousClass1();
        this.mContext = context;
        this.mWifiApBandConfig = WifiApSoftApUtils.getWifiApBandConfig(context);
    }

    private String[] getBandDisplayTextArray(List<WifiApBandConfig> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<WifiApBandConfig> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mDisplayText);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        WifiApSeekBarPreference wifiApSeekBarPreference =
                (WifiApSeekBarPreference) preferenceScreen.findPreference(KEY_PREFERENCE);
        this.mThisSeekBarPreference = wifiApSeekBarPreference;
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_wifi_ap_info_detail);
        String string = this.mContext.getString(R.string.help_title);
        wifiApSeekBarPreference.getClass();
        SemLog.i("WifiApSeekBarPreference", "setSecondaryIcon() - Triggered, label: " + string);
        wifiApSeekBarPreference.mSecondaryIconDrawable = drawable;
        wifiApSeekBarPreference.mSecondaryIconLabel = string;
        wifiApSeekBarPreference.notifyChanged();
        WifiApSeekBarPreference wifiApSeekBarPreference2 = this.mThisSeekBarPreference;
        wifiApSeekBarPreference2.mLabels =
                getBandDisplayTextArray(WifiApSoftApUtils.getSupportedBandList(this.mContext));
        wifiApSeekBarPreference2.setMax(r1.length - 1);
        WifiApSeekBarPreference wifiApSeekBarPreference3 = this.mThisSeekBarPreference;
        WifiApSeekBarPreference.OnPreferenceItemsCLickListener onPreferenceItemsCLickListener =
                this.mPreferenceItemsCLickListener;
        wifiApSeekBarPreference3.getClass();
        SemLog.i("WifiApSeekBarPreference", "setOnPreferenceItemsCLickListener() - Triggered");
        wifiApSeekBarPreference3.mOnPreferenceItemsCLickListener = onPreferenceItemsCLickListener;
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = TAG;
        Log.i(str, "getAvailabilityStatus:");
        if (WifiApFrameworkUtils.isBandSeekBarUxSupported(this.mContext)) {
            Log.i(str, "AVAILABLE");
            return 0;
        }
        Log.i(str, "CONDITIONALLY_UNAVAILABLE:");
        return 2;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return getBandDisplayTextArray(WifiApSoftApUtils.getSupportedBandList(this.mContext))
                .length;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        return this.mThisSeekBarPreference.mProgress;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public WifiApBandConfig getWifiApBandConfig() {
        return this.mWifiApBandConfig;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public boolean isBandTypeModified() {
        WifiApBandConfig wifiApBandConfig = WifiApSoftApUtils.getWifiApBandConfig(this.mContext);
        return !Arrays.equals(wifiApBandConfig.mBandArray, this.mWifiApBandConfig.mBandArray);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_BAND_SEEKBAR_VALUE)) {
            int i = bundle.getInt(BUNDLE_KEY_BAND_SEEKBAR_VALUE);
            MainClearConfirm$$ExternalSyntheticOutline0.m(i, "onRestoreInstanceState: ", TAG);
            this.mWifiApBandConfig =
                    (WifiApBandConfig)
                            ((ArrayList) WifiApSoftApUtils.getSupportedBandList(this.mContext))
                                    .get(i);
            this.mThisSeekBarPreference.setProgress(i);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putInt(BUNDLE_KEY_BAND_SEEKBAR_VALUE, this.mThisSeekBarPreference.mProgress);
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        if (isAvailable()) {
            this.mWifiApConfigureSettings = wifiApEditSettings;
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Set Slider new position:", TAG);
        WifiApBandConfig wifiApBandConfig =
                (WifiApBandConfig)
                        ((ArrayList) WifiApSoftApUtils.getSupportedBandList(this.mContext)).get(i);
        SALogging.insertSALog(
                wifiApBandConfig.mSaLoggingEventId, "TETH_011", "8013", (String) null);
        this.mWifiApBandConfig = wifiApBandConfig;
        WifiApEditSettings wifiApEditSettings = this.mWifiApConfigureSettings;
        wifiApEditSettings.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings, 4), 10L);
        WifiApEditSettings wifiApEditSettings2 = this.mWifiApConfigureSettings;
        wifiApEditSettings2.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings2, 5), 10L);
        WifiApEditSettings wifiApEditSettings3 = this.mWifiApConfigureSettings;
        wifiApEditSettings3.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings3, 3), 10L);
        WifiApEditSettings wifiApEditSettings4 = this.mWifiApConfigureSettings;
        wifiApEditSettings4.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings4, 1), 10L);
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mThisSeekBarPreference.setProgress(this.mWifiApBandConfig.mDisplayValueIndex);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBandSeekBarController$1, reason: invalid class name */
    public final class AnonymousClass1
            implements WifiApSeekBarPreference.OnPreferenceItemsCLickListener {
        public AnonymousClass1() {}

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBandSeekBarController$1$1, reason: invalid class name and collision with other inner class name */
        public final class DialogInterfaceOnClickListenerC00671
                implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {}
        }
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
