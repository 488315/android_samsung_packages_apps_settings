package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BatteryLifePreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "BatteryLifePreferenceController";
    private final int BATTERY_LIFE_DEFAULT_VALUE;
    private int mBatteryLifeValue;
    private final boolean mSupportLegacySummary;

    public BatteryLifePreferenceController(Context context, String str) {
        super(context, str);
        this.BATTERY_LIFE_DEFAULT_VALUE = -1;
        this.mBatteryLifeValue = -1;
        this.mSupportLegacySummary =
                SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0) < 30;
    }

    private int getBatteryLife() {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(
                                            "/sys/class/power_supply/battery/fg_asoc")));
        } catch (Exception unused) {
        } catch (Throwable th2) {
            bufferedReader = null;
            th = th2;
        }
        try {
            int parseInt = Integer.parseInt(bufferedReader.readLine());
            try {
                bufferedReader.close();
                return parseInt;
            } catch (Exception unused2) {
                return parseInt;
            }
        } catch (Exception unused3) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Exception unused4) {
                }
            }
            return 0;
        } catch (Throwable th3) {
            th = th3;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception unused5) {
                }
            }
            throw th;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.isJapanModel()) {
            return 3;
        }
        if (this.mBatteryLifeValue == -1) {
            this.mBatteryLifeValue = getBatteryLife();
            TooltipPopup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("battery life: "), this.mBatteryLifeValue, LOG_TAG);
        }
        return this.mBatteryLifeValue > 0 ? 0 : 2;
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
        String string;
        super.updateState(preference);
        if (this.mSupportLegacySummary) {
            int i = this.mBatteryLifeValue;
            string =
                    i >= 80
                            ? this.mContext
                                    .getResources()
                                    .getString(R.string.battery_life_status_good)
                            : i >= 50
                                    ? this.mContext
                                            .getResources()
                                            .getString(R.string.battery_life_status_ok)
                                    : this.mContext
                                            .getResources()
                                            .getString(R.string.battery_life_status_poor);
        } else {
            int i2 = this.mBatteryLifeValue;
            string =
                    i2 >= 80
                            ? this.mContext
                                    .getResources()
                                    .getString(R.string.battery_life_summary_enough_capacity)
                            : i2 >= 50
                                    ? this.mContext
                                            .getResources()
                                            .getString(
                                                    R.string
                                                            .battery_life_summary_slightly_reduced_capacity)
                                    : this.mContext
                                            .getResources()
                                            .getString(
                                                    R.string.battery_life_summary_reduced_capacity);
        }
        preference.setSummary(string);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
