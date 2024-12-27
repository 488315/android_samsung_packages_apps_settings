package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AODPowerSavingPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart {
    private static final String LINK_END_MARK = "99";
    private static final String LINK_START_MARK = "11";
    private static final String TAG = "AODPowerSavingPreferenceController";
    private PowerManager mPowerManager;
    private LayoutPreference mPref;

    public AODPowerSavingPreferenceController(Context context, String str) {
        super(context, str);
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPref =
                (LayoutPreference) preferenceScreen.findPreference("aod_power_saving_preference");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mPowerManager.isPowerSaveMode() && LockUtils.isAODDisabledInPSM(this.mContext))
                ? 0
                : 3;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        TextView textView =
                (TextView) this.mPref.mRootView.findViewById(R.id.sec_aod_power_saving_text);
        if (textView != null) {
            String string =
                    this.mContext.getString(
                            R.string.sec_settings_you_can_change_this_in_power_saving, "11", "99");
            int indexOf = TextUtils.indexOf(string, "11");
            int indexOf2 = TextUtils.indexOf(string, "99");
            if (indexOf == -1 || indexOf2 == -1) {
                return;
            }
            int i = indexOf2 + 2;
            if (indexOf <= i) {
                i = indexOf;
                indexOf = i;
            }
            String replaceAll =
                    string.replaceAll("11", ApnSettings.MVNO_NONE)
                            .replaceAll("99", ApnSettings.MVNO_NONE);
            int i2 = indexOf - 4;
            if (i2 > replaceAll.length()) {
                textView.setText(replaceAll);
                return;
            }
            ClickableSpan clickableSpan = new ClickableSpan() { // from class:
                        // com.samsung.android.settings.lockscreen.controller.AODPowerSavingPreferenceController.1
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            view.playSoundEffect(0);
                            Bundle call =
                                    ((AbstractPreferenceController)
                                                    AODPowerSavingPreferenceController.this)
                                            .mContext
                                            .getContentResolver()
                                            .call(
                                                    Uri.parse(
                                                            "content://com.samsung.android.sm.dcapi"),
                                                    "psm_start_power_saving_activity",
                                                    (String) null,
                                                    new Bundle());
                            boolean z = call.getBoolean("result");
                            boolean z2 = call.getBoolean("changeable");
                            if (!z) {
                                Log.d(
                                        AODPowerSavingPreferenceController.TAG,
                                        "API call has failed");
                            }
                            if (z2) {
                                Log.d(
                                        AODPowerSavingPreferenceController.TAG,
                                        "Enter in power saving");
                            }
                        }
                    };
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(replaceAll);
            spannableStringBuilder.setSpan(clickableSpan, i, i2, 0);
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(
                            this.mContext.getColor(R.color.sec_tips_description_link_text_color)),
                    i,
                    i2,
                    0);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spannableStringBuilder);
        }
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
