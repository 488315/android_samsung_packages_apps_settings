package com.android.settings.fuelgauge.batterytip;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.TipCardPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryTipPreferenceController extends BasePreferenceController {
    private static final String KEY_BATTERY_TIPS = "key_battery_tips";
    public static final String PREF_NAME = "battery_tip";
    private static final int REQUEST_ANOMALY_ACTION = 0;
    private static final String TAG = "BatteryTipPreferenceController";
    private BatteryTipListener mBatteryTipListener;
    private Map<String, BatteryTip> mBatteryTipMap;
    private List<BatteryTip> mBatteryTips;
    TipCardPreference mCardPreference;
    InstrumentedPreferenceFragment mFragment;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private boolean mNeedUpdate;
    Context mPrefContext;
    private SettingsActivity mSettingsActivity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BatteryTipListener {
        void onBatteryTipHandled(BatteryTip batteryTip);
    }

    public BatteryTipPreferenceController(Context context, String str) {
        super(context, str);
        this.mBatteryTipMap = new ArrayMap();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mNeedUpdate = true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPrefContext = preferenceScreen.getContext();
        TipCardPreference tipCardPreference =
                (TipCardPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mCardPreference = tipCardPreference;
        tipCardPreference.setVisible(false);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public BatteryTip getCurrentBatteryTip() {
        List<BatteryTip> list = this.mBatteryTips;
        if (list == null) {
            return null;
        }
        return list.stream()
                .filter(new BatteryTipPreferenceController$$ExternalSyntheticLambda0())
                .findFirst()
                .orElse(null);
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        BatteryTip batteryTip = this.mBatteryTipMap.get(preference.getKey());
        if (batteryTip == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (batteryTip.mShowDialog) {
            int metricsCategory = this.mFragment.getMetricsCategory();
            BatteryTipDialogFragment batteryTipDialogFragment = new BatteryTipDialogFragment();
            Bundle bundle = new Bundle(1);
            bundle.putParcelable(PREF_NAME, batteryTip);
            bundle.putInt("metrics_key", metricsCategory);
            batteryTipDialogFragment.setArguments(bundle);
            batteryTipDialogFragment.setTargetFragment(this.mFragment, 0);
            batteryTipDialogFragment.show(this.mFragment.getFragmentManager(), TAG);
        } else {
            BatteryTipAction actionForBatteryTip =
                    BatteryTipUtils.getActionForBatteryTip(
                            batteryTip, this.mSettingsActivity, this.mFragment);
            if (actionForBatteryTip != null) {
                actionForBatteryTip.handlePositiveAction(this.mFragment.getMetricsCategory());
            }
            BatteryTipListener batteryTipListener = this.mBatteryTipListener;
            if (batteryTipListener != null) {
                batteryTipListener.onBatteryTipHandled(batteryTip);
            }
        }
        return true;
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

    public boolean needUpdate() {
        return this.mNeedUpdate;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            updateBatteryTips(bundle.getParcelableArrayList(KEY_BATTERY_TIPS));
        } catch (BadParcelableException e) {
            Log.e(TAG, "failed to invoke restoreInstanceState()", e);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void saveInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            bundle.putParcelableList(KEY_BATTERY_TIPS, this.mBatteryTips);
        } catch (BadParcelableException e) {
            Log.e(TAG, "failed to invoke saveInstanceState()", e);
        }
    }

    public void setActivity(SettingsActivity settingsActivity) {
        this.mSettingsActivity = settingsActivity;
    }

    public void setBatteryTipListener(BatteryTipListener batteryTipListener) {
        this.mBatteryTipListener = batteryTipListener;
    }

    public void setFragment(InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        this.mFragment = instrumentedPreferenceFragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateBatteryTips(List<BatteryTip> list) {
        if (list == null) {
            return;
        }
        this.mBatteryTips = list;
        this.mCardPreference.setVisible(false);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            BatteryTip batteryTip = this.mBatteryTips.get(i);
            batteryTip.validateCheck(this.mContext);
            if (batteryTip.mState != 2) {
                this.mCardPreference.setVisible(true);
                batteryTip.updatePreference(this.mCardPreference);
                this.mBatteryTipMap.put(this.mCardPreference.getKey(), batteryTip);
                batteryTip.log(this.mContext, this.mMetricsFeatureProvider);
                this.mNeedUpdate = batteryTip.mNeedUpdate;
                return;
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
