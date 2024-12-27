package com.android.settings.fuelgauge.batterysaver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatterySaverScheduleSettings extends RadioButtonPickerFragment {
    Context mContext;
    public BatterySaverScheduleRadioButtonsController mRadioButtonController;
    public int mSaverPercentage;
    public String mSaverScheduleKey;
    public BatterySaverScheduleSeekBarController mSeekBarController;
    final ContentObserver mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.android.settings.fuelgauge.batterysaver.BatterySaverScheduleSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    BatterySaverScheduleSettings.this.getPreferenceScreen().removeAll();
                    BatterySaverScheduleSettings.this.updateCandidates();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatterySaverScheduleCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public BatterySaverScheduleCandidateInfo(String str, CharSequence charSequence) {
            super(true);
            this.mLabel = charSequence;
            this.mKey = str;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        this.mSeekBarController.updateSeekBar$2();
        BatterySaverScheduleSeekBarController batterySaverScheduleSeekBarController =
                this.mSeekBarController;
        batterySaverScheduleSeekBarController.mSeekBarPreference.setOrder(100);
        preferenceScreen.addPreference(batterySaverScheduleSeekBarController.mSeekBarPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {}

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Context context = getContext();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new BatterySaverScheduleCandidateInfo(
                        "key_battery_saver_no_schedule",
                        context.getText(R.string.battery_saver_auto_no_schedule)));
        BatterySaverUtils.revertScheduleToNoneIfNeeded(context);
        arrayList.add(
                new BatterySaverScheduleCandidateInfo(
                        "key_battery_saver_percentage",
                        context.getText(R.string.battery_saver_auto_percentage)));
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return BatterySaverUtils.getBatterySaverScheduleKey(this.mContext);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1977;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.battery_saver_schedule_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        BatterySaverScheduleSeekBarController batterySaverScheduleSeekBarController =
                new BatterySaverScheduleSeekBarController();
        batterySaverScheduleSeekBarController.mContext = context;
        SeekBarPreference seekBarPreference = new SeekBarPreference(context);
        batterySaverScheduleSeekBarController.mSeekBarPreference = seekBarPreference;
        seekBarPreference.setLayoutResource(R.layout.preference_widget_seekbar_settings);
        batterySaverScheduleSeekBarController.mSeekBarPreference.setIconSpaceReserved(false);
        batterySaverScheduleSeekBarController.mSeekBarPreference.setOnPreferenceChangeListener(
                batterySaverScheduleSeekBarController);
        batterySaverScheduleSeekBarController.mSeekBarPreference.setOnSeekBarChangeListener(
                batterySaverScheduleSeekBarController);
        SeekBarPreference seekBarPreference2 =
                batterySaverScheduleSeekBarController.mSeekBarPreference;
        seekBarPreference2.mContinuousUpdates = true;
        seekBarPreference2.setMax(15);
        batterySaverScheduleSeekBarController.mSeekBarPreference.setMin(2);
        batterySaverScheduleSeekBarController.mSeekBarPreference.setKey("battery_saver_seek_bar");
        batterySaverScheduleSeekBarController.mSeekBarPreference.mHapticFeedbackMode = 1;
        batterySaverScheduleSeekBarController.updateSeekBar$2();
        this.mSeekBarController = batterySaverScheduleSeekBarController;
        BatterySaverScheduleRadioButtonsController batterySaverScheduleRadioButtonsController =
                new BatterySaverScheduleRadioButtonsController();
        batterySaverScheduleRadioButtonsController.mContext = context;
        batterySaverScheduleRadioButtonsController.mSeekBarController =
                batterySaverScheduleSeekBarController;
        this.mRadioButtonController = batterySaverScheduleRadioButtonsController;
        this.mContext = context;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.settings.fuelgauge.batterysaver.BatterySaverScheduleSettings$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatterySaverScheduleSettings batterySaverScheduleSettings =
                                BatterySaverScheduleSettings.this;
                        int i =
                                Settings.Global.getInt(
                                        batterySaverScheduleSettings.mContext.getContentResolver(),
                                        "low_power_trigger_level",
                                        -1);
                        String batterySaverScheduleKey =
                                BatterySaverUtils.getBatterySaverScheduleKey(
                                        batterySaverScheduleSettings.mContext);
                        if (batterySaverScheduleSettings.mSaverScheduleKey.equals(
                                        batterySaverScheduleKey)
                                && batterySaverScheduleSettings.mSaverPercentage == i) {
                            return;
                        }
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        featureFactoryImpl
                                .getMetricsFeatureProvider()
                                .action(52, 1784, 1785, i, batterySaverScheduleKey);
                    }
                });
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("low_power_warning_acknowledged"),
                        false,
                        this.mSettingsObserver);
        this.mSaverScheduleKey = BatterySaverUtils.getBatterySaverScheduleKey(this.mContext);
        this.mSaverPercentage =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "low_power_trigger_level", -1);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(new ColorDrawable(0));
        setDividerHeight(0);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        int i;
        BatterySaverScheduleRadioButtonsController batterySaverScheduleRadioButtonsController =
                this.mRadioButtonController;
        if (str == null) {
            batterySaverScheduleRadioButtonsController.getClass();
            return false;
        }
        ContentResolver contentResolver =
                batterySaverScheduleRadioButtonsController.mContext.getContentResolver();
        Bundle bundle = new Bundle(3);
        if (str.equals("key_battery_saver_no_schedule")) {
            i = 0;
        } else {
            if (!str.equals("key_battery_saver_percentage")) {
                throw new IllegalStateException(
                        "Not a valid key for BatterySaverScheduleRadioButtonsController");
            }
            bundle.putBoolean("extra_confirm_only", true);
            bundle.putInt("extra_power_save_mode_trigger", 0);
            i = 20;
            bundle.putInt("extra_power_save_mode_trigger_level", 20);
        }
        if (!TextUtils.equals(str, "key_battery_saver_no_schedule")
                && BatterySaverUtils.maybeShowBatterySaverConfirmation(
                        batterySaverScheduleRadioButtonsController.mContext, bundle)) {
            i = 0;
        }
        Settings.Global.putInt(contentResolver, "automatic_power_save_mode", 0);
        Settings.Global.putInt(contentResolver, "low_power_trigger_level", i);
        if (i != 0) {
            Settings.Secure.putInt(
                    batterySaverScheduleRadioButtonsController.mContext.getContentResolver(),
                    "suppress_auto_battery_saver_suggestion",
                    1);
        }
        BatterySaverScheduleSeekBarController batterySaverScheduleSeekBarController =
                batterySaverScheduleRadioButtonsController.mSeekBarController;
        if (batterySaverScheduleSeekBarController != null) {
            batterySaverScheduleSeekBarController.updateSeekBar$2();
        }
        return true;
    }
}
