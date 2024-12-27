package com.samsung.android.settings.eyecomfort;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.configuration.DATA;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener,
                EyeComfortSpinnerPreference.onSpinnerItemClickListener,
                EyeComfortTimeSettingDialogFragment.onTimeDialogDismissListener,
                EyeComfortRadioButtonPreference.OnClickListener {
    public static int mAnchorView;
    public final boolean isNightModeAvailable;
    public EyeComfortRadioButtonPreference mAdaptivePreference;
    public EyeComfortAntiGlarePreference mAntiGlarePreference;
    public EyeComfortSeekBarPreference mColorSeekBarPreference;
    public Context mContext;
    public EyeComfortRadioButtonPreference mCustomPreference;
    public LayoutPreference mDivider;
    public SecInsetCategoryPreference mEnhanceComfortDivider;
    public SecUnclickablePreference mEyeComfortDescPreference;
    public final AnonymousClass1 mEyeComfortScheduledReceiver;
    public EyeComfortNightDimPreference mNightDimPreference;
    public int mOrientation;
    public EyeComfortSpinnerPreference mSetSchedulePreference;
    public static final int[] mBlueLightFilterScheduleSummaryString = {
        R.string.sec_eye_comfort_schedule_always_on,
        R.string.sec_eye_comfort_schedule_sunset_to_sunrise,
        R.string.sec_eye_comfort_custom
    };
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass6(R.xml.sec_eyecomfort_settings);
    public SettingsMainSwitchBar mSwitchBar = null;
    public boolean mSetAnchorByView = false;
    public AlertDialog mLocationOnDialog = null;
    public EyeComfortTimeSettingDialogFragment mTimeSettingDialog = null;
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.eyecomfort.EyeComfortSettings$6, reason: invalid class name */
    public final class AnonymousClass6 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("eye_comfort_description");
            arrayList.add("eye_comfort_adaptive_mode");
            arrayList.add("eye_comfort_custom_mode");
            if (Settings.System.getInt(
                            context.getContentResolver(), "blue_light_filter_adaptive_mode", 0)
                    != 0) {
                arrayList.add("eye_comfort_set_schedule");
                arrayList.add("eye_comfort_seekbar_color_temperature");
            }
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            arrayList.add("eye_comfort_anti_glare");
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM")
                    <= 0) {
                arrayList.add("eye_comfort_night_dim");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM")
                    > 0) {
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.sec_eye_comfort_title);
                searchIndexableRaw.keywords = resources.getString(R.string.sec_enhanced_comfort);
                searchIndexableRaw.title = resources.getString(R.string.sec_enhanced_comfort);
                ((SearchIndexableData) searchIndexableRaw).key = "eye_comfort_night_dim";
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI;
        public final Uri BLUE_LIGHT_FILTER_ANTI_GLARE_URI;
        public final Uri BLUE_LIGHT_FILTER_OPACITY_URI;
        public final Uri BLUE_LIGHT_FILTER_SCHEDULED_URI;
        public final Uri BLUE_LIGHT_FILTER_TYPE_URI;
        public final Uri BLUE_LIGHT_FILTER_URI;
        public final Uri NEGATIVE_COLOR_TYPE_URI;
        public final Uri NIGHT_DIM_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BLUE_LIGHT_FILTER_URI = Settings.System.getUriFor("blue_light_filter");
            this.BLUE_LIGHT_FILTER_OPACITY_URI =
                    Settings.System.getUriFor("blue_light_filter_opacity");
            this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI =
                    Settings.System.getUriFor("blue_light_filter_anti_glare");
            this.BLUE_LIGHT_FILTER_SCHEDULED_URI =
                    Settings.System.getUriFor("blue_light_filter_scheduled");
            this.BLUE_LIGHT_FILTER_TYPE_URI = Settings.System.getUriFor("blue_light_filter_type");
            this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI =
                    Settings.System.getUriFor("blue_light_filter_adaptive_mode");
            this.NEGATIVE_COLOR_TYPE_URI = Settings.System.getUriFor("high_contrast");
            this.NIGHT_DIM_URI = Settings.System.getUriFor("blue_light_filter_night_dim");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            boolean z2;
            if (this.BLUE_LIGHT_FILTER_URI.equals(uri)) {
                int i = EyeComfortSettings.mAnchorView;
                Log.i(
                        "EyeComfortSettings",
                        "onChange BLUE_LIGHT_FILTER_URI isEnabled() : "
                                + EyeComfortSettings.this.isEnabled$3());
                EyeComfortSettings eyeComfortSettings = EyeComfortSettings.this;
                eyeComfortSettings.mSwitchBar.setChecked(eyeComfortSettings.isEnabled$3());
                EyeComfortSettings eyeComfortSettings2 = EyeComfortSettings.this;
                eyeComfortSettings2.updateEnableStatus(eyeComfortSettings2.isEnabled$3());
                EyeComfortSettings.this.updateDescriptionPreference();
                return;
            }
            if (this.BLUE_LIGHT_FILTER_OPACITY_URI.equals(uri)) {
                EyeComfortSettings eyeComfortSettings3 = EyeComfortSettings.this;
                eyeComfortSettings3.mColorSeekBarPreference.setProgress(
                        Settings.System.getInt(
                                eyeComfortSettings3.getContentResolver(),
                                "blue_light_filter_opacity",
                                5));
                return;
            }
            if (this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI.equals(uri)) {
                EyeComfortAntiGlarePreference eyeComfortAntiGlarePreference =
                        EyeComfortSettings.this.mAntiGlarePreference;
                eyeComfortAntiGlarePreference.setChecked(
                        Settings.System.getInt(
                                        eyeComfortAntiGlarePreference
                                                .getContext()
                                                .getContentResolver(),
                                        "blue_light_filter_anti_glare",
                                        0)
                                == 1);
                return;
            }
            if (this.BLUE_LIGHT_FILTER_SCHEDULED_URI.equals(uri)) {
                int i2 = EyeComfortSettings.mAnchorView;
                Log.i("EyeComfortSettings", "onChange BLUE_LIGHT_FILTER_SCHEDULED_URI");
                boolean z3 =
                        Settings.System.getInt(
                                        EyeComfortSettings.this.getContentResolver(),
                                        "blue_light_filter_scheduled",
                                        0)
                                != 0;
                EyeComfortSettings.this.getClass();
                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                    z2 =
                            Settings.System.getInt(
                                            EyeComfortSettings.this.getContentResolver(),
                                            "blue_light_filter_adaptive_mode",
                                            0)
                                    != 0;
                    if (z3 && z2) {
                        Settings.System.putInt(
                                EyeComfortSettings.this.getContentResolver(),
                                "blue_light_filter_adaptive_mode",
                                0);
                        Log.i(
                                "EyeComfortSettings",
                                "onChange BLUE_LIGHT_FILTER_ADAPTIVE_MODE: set OFF");
                    }
                    EyeComfortSettings.this.updatePreferenceStatus();
                    return;
                }
                return;
            }
            if (this.BLUE_LIGHT_FILTER_TYPE_URI.equals(uri)) {
                int i3 = EyeComfortSettings.mAnchorView;
                Log.i("EyeComfortSettings", "onChange BLUE_LIGHT_FILTER_TYPE_URI");
                Settings.System.getInt(
                        EyeComfortSettings.this.getContentResolver(), "blue_light_filter_type", 0);
                EyeComfortSettings.this.getClass();
                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                    return;
                }
                EyeComfortSettings.this.updateCustomSettingPreference();
                return;
            }
            if (!this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI.equals(uri)) {
                if (this.NEGATIVE_COLOR_TYPE_URI.equals(uri)) {
                    if (Settings.System.getInt(
                                    EyeComfortSettings.this.mContext.getContentResolver(),
                                    "high_contrast",
                                    0)
                            != 0) {
                        EyeComfortSettings.this.finishFragment();
                        return;
                    }
                    return;
                } else {
                    if (this.NIGHT_DIM_URI.equals(uri)) {
                        EyeComfortSettings.this.mNightDimPreference.setChecked(
                                Settings.System.getInt(
                                                EyeComfortSettings.this.mContext
                                                        .getContentResolver(),
                                                "blue_light_filter_night_dim",
                                                0)
                                        == 1);
                        return;
                    }
                    return;
                }
            }
            int i4 = EyeComfortSettings.mAnchorView;
            Log.i("EyeComfortSettings", "onChange BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI");
            EyeComfortSettings eyeComfortSettings4 = EyeComfortSettings.this;
            if (eyeComfortSettings4.mAdaptivePreference != null) {
                boolean z4 =
                        Settings.System.getInt(
                                        eyeComfortSettings4.getContentResolver(),
                                        "blue_light_filter_adaptive_mode",
                                        0)
                                != 0;
                EyeComfortSettings.this.mAdaptivePreference.setChecked(z4);
                z2 =
                        Settings.System.getInt(
                                        EyeComfortSettings.this.getContentResolver(),
                                        "blue_light_filter_scheduled",
                                        0)
                                != 0;
                if (z4 && z2) {
                    Settings.System.putInt(
                            EyeComfortSettings.this.getContentResolver(),
                            "blue_light_filter_scheduled",
                            0);
                    Log.i("EyeComfortSettings", "onChange BLUE_LIGHT_FILTER_SCHEDULED :set OFF ");
                }
                EyeComfortSettings.this.updatePreferenceStatus();
            }
        }

        public final void setListening(boolean z) {
            EyeComfortSettings eyeComfortSettings = EyeComfortSettings.this;
            int i = EyeComfortSettings.mAnchorView;
            ContentResolver contentResolver = eyeComfortSettings.getContentResolver();
            if (!z) {
                contentResolver.unregisterContentObserver(this);
                return;
            }
            contentResolver.registerContentObserver(this.BLUE_LIGHT_FILTER_URI, false, this);
            contentResolver.registerContentObserver(
                    this.BLUE_LIGHT_FILTER_OPACITY_URI, false, this);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            contentResolver.registerContentObserver(
                    this.BLUE_LIGHT_FILTER_SCHEDULED_URI, false, this);
            contentResolver.registerContentObserver(this.BLUE_LIGHT_FILTER_TYPE_URI, false, this);
            Uri uri = this.NIGHT_DIM_URI;
            if (uri != null) {
                contentResolver.registerContentObserver(uri, false, this);
            }
            EyeComfortSettings.this.getClass();
            if (Rune.supportBlueLightFilterAdaptiveMode()) {
                contentResolver.registerContentObserver(
                        this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI, false, this);
            }
            contentResolver.registerContentObserver(this.NEGATIVE_COLOR_TYPE_URI, true, this);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.eyecomfort.EyeComfortSettings$1] */
    public EyeComfortSettings() {
        this.isNightModeAvailable =
                SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM")
                        > 0;
        this.mEyeComfortScheduledReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.eyecomfort.EyeComfortSettings.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        int i = EyeComfortSettings.mAnchorView;
                        Log.i("EyeComfortSettings", "onReceive : action = " + action);
                        if ("com.android.settings.ECS_SCHEDULE_START".equals(action)
                                || "com.android.settings.ECS_SCHEDULE_END".equals(action)) {
                            EyeComfortSettings.this.updateDescriptionPreference();
                        }
                    }
                };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.mSwitchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "eyeComfortEnabeld",
                ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "EyeComfortSettings", "[]")
                        : "EyeComfortSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_eye_comfort_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4222;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final boolean isEnabled$3() {
        return Settings.System.getInt(getContentResolver(), "blue_light_filter", 0) != 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(isEnabled$3());
            updateEnableStatus(isEnabled$3());
            this.mSwitchBar.addOnSwitchChangeListener(this);
            this.mSwitchBar.show();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
        byte b =
                Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0) != 0;
        if (Settings.Secure.getInt(getContentResolver(), "location_mode", 0) == 0
                && i == 2
                && b == true
                && z) {
            showLocationOnDialog(0);
            this.mSetAnchorByView = true;
            return;
        }
        Settings.System.putInt(getContentResolver(), "blue_light_filter", z ? 1 : 0);
        Intent intent = new Intent();
        DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                "com.samsung.android.bluelightfilter",
                "com.samsung.android.bluelightfilter.BlueLightFilterService",
                intent);
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", z ? 24 : 25);
        this.mContext.startService(intent);
        LoggingHelper.insertEventLogging(4222, 7410, z ? 1L : 0L);
        updateDescriptionPreference();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Rune.supportBlueLightFilterAdaptiveMode()) {
            setAnimationAllowed(true);
            setAutoRemoveInsetCategory(false);
        } else {
            setAutoRemoveInsetCategory(true);
        }
        addPreferencesFromResource(R.xml.sec_eyecomfort_settings);
        Context applicationContext = getActivity().getApplicationContext();
        this.mContext = applicationContext;
        this.mOrientation = applicationContext.getResources().getConfiguration().orientation;
        this.mContext.registerReceiver(
                this.mEyeComfortScheduledReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "com.android.settings.ECS_SCHEDULE_START",
                        "com.android.settings.ECS_SCHEDULE_END"),
                "com.samsung.android.permission.SEC_EYECOMFORT_BROADCAST",
                null,
                2);
        SecUnclickablePreference secUnclickablePreference =
                (SecUnclickablePreference) findPreference("eye_comfort_description");
        this.mEyeComfortDescPreference = secUnclickablePreference;
        secUnclickablePreference.mPositionMode = 1;
        this.mAdaptivePreference =
                (EyeComfortRadioButtonPreference) findPreference("eye_comfort_adaptive_mode");
        this.mCustomPreference =
                (EyeComfortRadioButtonPreference) findPreference("eye_comfort_custom_mode");
        this.mDivider = (LayoutPreference) findPreference("divider");
        EyeComfortNightDimPreference eyeComfortNightDimPreference =
                (EyeComfortNightDimPreference) findPreference("eye_comfort_night_dim");
        this.mNightDimPreference = eyeComfortNightDimPreference;
        if (this.isNightModeAvailable) {
            eyeComfortNightDimPreference.setVisible(true);
            EyeComfortNightDimPreference eyeComfortNightDimPreference2 = this.mNightDimPreference;
            eyeComfortNightDimPreference2.setChecked(eyeComfortNightDimPreference2.isChecked());
        } else {
            eyeComfortNightDimPreference.setVisible(false);
        }
        if (Rune.supportBlueLightFilterAdaptiveMode()) {
            this.mAdaptivePreference.mListener = this;
            this.mCustomPreference.mListener = this;
        } else {
            getPreferenceScreen().removePreference(this.mAdaptivePreference);
            getPreferenceScreen().removePreference(this.mDivider);
            getPreferenceScreen().removePreference(this.mCustomPreference);
        }
        EyeComfortSpinnerPreference eyeComfortSpinnerPreference =
                (EyeComfortSpinnerPreference) findPreference("eye_comfort_set_schedule");
        this.mSetSchedulePreference = eyeComfortSpinnerPreference;
        eyeComfortSpinnerPreference.setEntries(
                new CharSequence[] {
                    this.mContext.getString(R.string.sec_eye_comfort_schedule_always_on),
                    this.mContext.getString(R.string.sec_eye_comfort_schedule_sunset_to_sunrise),
                    this.mContext.getString(R.string.sec_eye_comfort_custom)
                });
        EyeComfortSpinnerPreference eyeComfortSpinnerPreference2 = this.mSetSchedulePreference;
        eyeComfortSpinnerPreference2.mEntryValues =
                new CharSequence[] {DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "1", "2"};
        eyeComfortSpinnerPreference2.mListener = this;
        EyeComfortSeekBarPreference eyeComfortSeekBarPreference =
                (EyeComfortSeekBarPreference)
                        findPreference("eye_comfort_seekbar_color_temperature");
        this.mColorSeekBarPreference = eyeComfortSeekBarPreference;
        eyeComfortSeekBarPreference.setProgress(
                Settings.System.getInt(getContentResolver(), "blue_light_filter_opacity", 5));
        EyeComfortAntiGlarePreference eyeComfortAntiGlarePreference =
                (EyeComfortAntiGlarePreference) findPreference("eye_comfort_anti_glare");
        this.mAntiGlarePreference = eyeComfortAntiGlarePreference;
        if (eyeComfortAntiGlarePreference != null) {
            getPreferenceScreen().removePreference(this.mAntiGlarePreference);
        }
        this.mEnhanceComfortDivider =
                (SecInsetCategoryPreference) findPreference("enhance_comfort_divider");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mContext.unregisterReceiver(this.mEyeComfortScheduledReceiver);
        this.mSettingsObserver.setListening(false);
        AlertDialog alertDialog = this.mLocationOnDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mLocationOnDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if ((Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext))
                || SecDisplayUtils.isAccessibilityVisionEnabled(this.mContext)) {
            getActivity().finish();
        }
        this.mSettingsObserver.setListening(true);
        updatePreferenceStatus();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Fragment findFragmentByTag;
        Log.i("EyeComfortSettings", "onStart()");
        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName(
                        "com.samsung.android.bluelightfilter",
                        "com.samsung.android.bluelightfilter.BlueLightFilterService"));
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 31);
        this.mContext.startService(intent);
        if (this.mTimeSettingDialog == null
                && (findFragmentByTag =
                                getActivity()
                                        .getSupportFragmentManager()
                                        .findFragmentByTag("EyeComfortTimeSettingDialogFragment"))
                        != null) {
            this.mTimeSettingDialog = (EyeComfortTimeSettingDialogFragment) findFragmentByTag;
            Log.d("EyeComfortSettings", "EyeComfortTimeSettingDialogFragment found");
            this.mTimeSettingDialog.mTimeDialogDismissListener = this;
        }
        super.onStart();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.i("EyeComfortSettings", "onStop()");
        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName(
                        "com.samsung.android.bluelightfilter",
                        "com.samsung.android.bluelightfilter.BlueLightFilterService"));
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 32);
        this.mContext.startService(intent);
        super.onStop();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view.addOnLayoutChangeListener(
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.samsung.android.settings.eyecomfort.EyeComfortSettings$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view2,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        EyeComfortSettings eyeComfortSettings = EyeComfortSettings.this;
                        Context context = eyeComfortSettings.mContext;
                        if (context == null
                                || eyeComfortSettings.mOrientation
                                        == context.getResources().getConfiguration().orientation) {
                            return;
                        }
                        AlertDialog alertDialog = eyeComfortSettings.mLocationOnDialog;
                        if (alertDialog != null && alertDialog.isShowing()) {
                            if (eyeComfortSettings.mSetAnchorByView) {
                                eyeComfortSettings.mLocationOnDialog.semSetAnchor(
                                        eyeComfortSettings.mSwitchBar);
                            } else {
                                eyeComfortSettings.setLocationDialogAnchor(
                                        EyeComfortSettings.mAnchorView != 1
                                                ? eyeComfortSettings.mSetSchedulePreference
                                                : eyeComfortSettings.mCustomPreference);
                            }
                        }
                        EyeComfortTimeSettingDialogFragment eyeComfortTimeSettingDialogFragment =
                                eyeComfortSettings.mTimeSettingDialog;
                        if (eyeComfortTimeSettingDialogFragment != null) {
                            eyeComfortTimeSettingDialogFragment.setDialogAnchor(
                                    eyeComfortSettings.mSetSchedulePreference);
                        }
                        eyeComfortSettings.mOrientation =
                                eyeComfortSettings
                                        .mContext
                                        .getResources()
                                        .getConfiguration()
                                        .orientation;
                    }
                });
    }

    public final void setLocationDialogAnchor(Preference preference) {
        if (preference == null) {
            return;
        }
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        int i = rect.left;
        int m = AbsActionBarView$$ExternalSyntheticOutline0.m(rect.right, i, 2, i);
        int i2 = rect.bottom;
        AlertDialog alertDialog = this.mLocationOnDialog;
        if (alertDialog != null) {
            alertDialog.semSetAnchor(m, i2);
        }
    }

    public final void showLocationOnDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = true;
        builder.setTitle(R.string.sec_blue_light_filter_dlg_turn_on_location_title);
        builder.setMessage(R.string.sec_blue_light_filter_dlg_turn_on_location);
        final int i2 = 1;
        builder.setNegativeButton(
                R.string.dlg_cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.eyecomfort.EyeComfortSettings.4
                    public final /* synthetic */ EyeComfortSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        switch (i2) {
                            case 0:
                                EyeComfortSettings eyeComfortSettings = this.this$0;
                                int i4 = EyeComfortSettings.mAnchorView;
                                Settings.Secure.putInt(
                                        eyeComfortSettings.getContentResolver(),
                                        "location_mode",
                                        3);
                                int i5 = i;
                                if (i5 != 2) {
                                    if (i5 != 1) {
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter",
                                                1);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 7410, 1L);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter_scheduled",
                                                1);
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter_adaptive_mode",
                                                0);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 7411, 1L);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 40200, 0L);
                                        break;
                                    }
                                } else {
                                    Settings.System.putInt(
                                            this.this$0.getContentResolver(),
                                            "blue_light_filter_type",
                                            2);
                                    this.this$0.getClass();
                                    LoggingHelper.insertEventLogging(4222, 4225, 1L);
                                    break;
                                }
                            default:
                                int i6 = i;
                                if (i6 != 0) {
                                    if (i6 == 2) {
                                        EyeComfortSettings eyeComfortSettings2 = this.this$0;
                                        int i7 = EyeComfortSettings.mAnchorView;
                                        if (Settings.System.getInt(
                                                        eyeComfortSettings2.getContentResolver(),
                                                        "blue_light_filter_type",
                                                        0)
                                                == 2) {
                                            Settings.System.putInt(
                                                    this.this$0.getContentResolver(),
                                                    "blue_light_filter_type",
                                                    0);
                                            break;
                                        }
                                    }
                                } else {
                                    this.this$0.mSwitchBar.setChecked(false);
                                    break;
                                }
                                break;
                        }
                    }
                });
        final int i3 = 0;
        builder.setPositiveButton(
                R.string.sec_dlg_turn_on,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.eyecomfort.EyeComfortSettings.4
                    public final /* synthetic */ EyeComfortSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        switch (i3) {
                            case 0:
                                EyeComfortSettings eyeComfortSettings = this.this$0;
                                int i4 = EyeComfortSettings.mAnchorView;
                                Settings.Secure.putInt(
                                        eyeComfortSettings.getContentResolver(),
                                        "location_mode",
                                        3);
                                int i5 = i;
                                if (i5 != 2) {
                                    if (i5 != 1) {
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter",
                                                1);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 7410, 1L);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter_scheduled",
                                                1);
                                        Settings.System.putInt(
                                                this.this$0.getContentResolver(),
                                                "blue_light_filter_adaptive_mode",
                                                0);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 7411, 1L);
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(4222, 40200, 0L);
                                        break;
                                    }
                                } else {
                                    Settings.System.putInt(
                                            this.this$0.getContentResolver(),
                                            "blue_light_filter_type",
                                            2);
                                    this.this$0.getClass();
                                    LoggingHelper.insertEventLogging(4222, 4225, 1L);
                                    break;
                                }
                            default:
                                int i6 = i;
                                if (i6 != 0) {
                                    if (i6 == 2) {
                                        EyeComfortSettings eyeComfortSettings2 = this.this$0;
                                        int i7 = EyeComfortSettings.mAnchorView;
                                        if (Settings.System.getInt(
                                                        eyeComfortSettings2.getContentResolver(),
                                                        "blue_light_filter_type",
                                                        0)
                                                == 2) {
                                            Settings.System.putInt(
                                                    this.this$0.getContentResolver(),
                                                    "blue_light_filter_type",
                                                    0);
                                            break;
                                        }
                                    }
                                } else {
                                    this.this$0.mSwitchBar.setChecked(false);
                                    break;
                                }
                                break;
                        }
                    }
                });
        alertParams.mOnCancelListener =
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.eyecomfort.EyeComfortSettings.3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        int i4 = i;
                        if (i4 == 0) {
                            EyeComfortSettings.this.mSwitchBar.setChecked(false);
                            return;
                        }
                        if (i4 == 2) {
                            EyeComfortSettings eyeComfortSettings = EyeComfortSettings.this;
                            int i5 = EyeComfortSettings.mAnchorView;
                            if (Settings.System.getInt(
                                            eyeComfortSettings.getContentResolver(),
                                            "blue_light_filter_type",
                                            0)
                                    == 2) {
                                Settings.System.putInt(
                                        EyeComfortSettings.this.getContentResolver(),
                                        "blue_light_filter_type",
                                        0);
                            }
                        }
                    }
                };
        alertParams.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.eyecomfort.EyeComfortSettings.2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        if (i == 0) {
                            EyeComfortSettings.this.mSetAnchorByView = false;
                        }
                        EyeComfortSettings eyeComfortSettings = EyeComfortSettings.this;
                        int i4 = EyeComfortSettings.mAnchorView;
                        eyeComfortSettings.updatePreferenceStatus();
                        EyeComfortSettings.this.mLocationOnDialog = null;
                    }
                };
        AlertDialog create = builder.create();
        this.mLocationOnDialog = create;
        if (i == 0) {
            create.semSetAnchor(this.mSwitchBar);
        } else {
            setLocationDialogAnchor(i != 1 ? this.mSetSchedulePreference : this.mCustomPreference);
        }
        mAnchorView = i;
        this.mLocationOnDialog.show();
    }

    public final void updateCustomSettingPreference() {
        if (this.mSetSchedulePreference == null || this.mColorSeekBarPreference == null) {
            return;
        }
        char c = 0;
        if (Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0) == 0) {
            if (Rune.supportBlueLightFilterAdaptiveMode()) {
                getPreferenceScreen().removePreference(this.mSetSchedulePreference);
                getPreferenceScreen().removePreference(this.mColorSeekBarPreference);
                return;
            }
            return;
        }
        if (Rune.supportBlueLightFilterAdaptiveMode()) {
            getPreferenceScreen().addPreference(this.mSetSchedulePreference);
            getPreferenceScreen().addPreference(this.mColorSeekBarPreference);
            getPreferenceScreen().addPreference(this.mEnhanceComfortDivider);
        }
        int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
        EyeComfortSpinnerPreference eyeComfortSpinnerPreference = this.mSetSchedulePreference;
        eyeComfortSpinnerPreference.mScheduleTypeIndex = i != 1 ? i != 2 ? 0 : 1 : 2;
        eyeComfortSpinnerPreference.setValueIndex(i != 1 ? i != 2 ? 0 : 1 : 2);
        if (i == 1) {
            updateTimeSummary();
        } else {
            EyeComfortSpinnerPreference eyeComfortSpinnerPreference2 = this.mSetSchedulePreference;
            int[] iArr = mBlueLightFilterScheduleSummaryString;
            if (i == 1) {
                c = 2;
            } else if (i == 2) {
                c = 1;
            }
            eyeComfortSpinnerPreference2.setSummary(iArr[c]);
        }
        EyeComfortSpinnerPreference eyeComfortSpinnerPreference3 = this.mSetSchedulePreference;
        eyeComfortSpinnerPreference3.getClass();
        SecPreferenceUtils.applySummaryColor(eyeComfortSpinnerPreference3, true);
        this.mColorSeekBarPreference.setProgress(
                Settings.System.getInt(getContentResolver(), "blue_light_filter_opacity", 5));
    }

    public final void updateDescriptionPreference() {
        boolean z = false;
        boolean z2 =
                Settings.System.getInt(getContentResolver(), "blue_light_filter_adaptive_mode", 0)
                        != 0;
        boolean z3 =
                Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0) != 0;
        int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
        if (z3 && i == 0) {
            z = true;
        }
        if (!isEnabled$3() || z2 || !z3 || z) {
            this.mEyeComfortDescPreference.setTitle(R.string.sec_eye_comfort_description);
            return;
        }
        long longForUser =
                Settings.System.getLongForUser(
                        getContentResolver(), "blue_light_filter_on_time", 1140L, -2);
        long longForUser2 =
                Settings.System.getLongForUser(
                        getContentResolver(), "blue_light_filter_off_time", 420L, -2);
        String stringFromMillis = SecDisplayUtils.getStringFromMillis(this.mContext, longForUser);
        String stringFromMillis2 = SecDisplayUtils.getStringFromMillis(this.mContext, longForUser2);
        String string =
                EyeComfortTimeUtils.duringScheduleTime(this.mContext, i)
                        ? i == 2
                                ? this.isNightModeAvailable
                                        ? this.mContext.getString(
                                                R.string.sec_limit_blue_light_night_after_sunset)
                                        : this.mContext.getString(
                                                R.string.sec_eye_comfort_description_auto_during)
                                : this.isNightModeAvailable
                                        ? this.mContext.getString(
                                                R.string.sec_limit_blue_light_night_on_ps_msg,
                                                stringFromMillis2)
                                        : this.mContext.getString(
                                                R.string.sec_eye_comfort_description_custom_during,
                                                stringFromMillis2)
                        : i == 2
                                ? this.isNightModeAvailable
                                        ? this.mContext.getString(
                                                R.string.sec_limit_blue_light_night_before_sunset)
                                        : this.mContext.getString(
                                                R.string.sec_eye_comfort_description_auto_before)
                                : this.isNightModeAvailable
                                        ? this.mContext.getString(
                                                R.string.sec_limit_blue_light_night_off_ps_msg,
                                                stringFromMillis)
                                        : this.mContext.getString(
                                                R.string.sec_eye_comfort_description_custom_before,
                                                stringFromMillis);
        if (string.isEmpty()) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        spannableStringBuilder.setSpan(
                new StyleSpan(1), string.indexOf("\n\n") + 2, string.length(), 33);
        this.mEyeComfortDescPreference.setTitle(spannableStringBuilder);
    }

    public final void updateEnableStatus(boolean z) {
        EyeComfortTimeSettingDialogFragment eyeComfortTimeSettingDialogFragment;
        Dialog dialog;
        if (Rune.supportBlueLightFilterAdaptiveMode()) {
            this.mAdaptivePreference.setEnabled(z);
            this.mCustomPreference.setEnabled(z);
        }
        this.mSetSchedulePreference.setEnabled(z);
        this.mColorSeekBarPreference.setEnabled(z);
        this.mNightDimPreference.setEnabled(z);
        if (z
                || (eyeComfortTimeSettingDialogFragment = this.mTimeSettingDialog) == null
                || (dialog = ((DialogFragment) eyeComfortTimeSettingDialogFragment).mDialog) == null
                || !dialog.isShowing()) {
            return;
        }
        dialog.dismiss();
    }

    public final void updatePreferenceStatus() {
        if (isAdded()) {
            updateDescriptionPreference();
            if (Rune.supportBlueLightFilterAdaptiveMode()
                    && this.mAdaptivePreference != null
                    && this.mCustomPreference != null) {
                if (Settings.System.getInt(
                                getContentResolver(), "blue_light_filter_adaptive_mode", 0)
                        != 0) {
                    this.mAdaptivePreference.setChecked(true);
                    this.mCustomPreference.setChecked(false);
                    if (Settings.System.getInt(
                                    getContentResolver(), "blue_light_filter_scheduled", 0)
                            != 0) {
                        Settings.System.putInt(
                                getContentResolver(), "blue_light_filter_scheduled", 0);
                        Log.e(
                                "EyeComfortSettings",
                                "updateModePreference : change the scheduled value in an abnormal"
                                    + " case");
                    }
                } else {
                    this.mAdaptivePreference.setChecked(false);
                    this.mCustomPreference.setChecked(true);
                }
            }
            updateCustomSettingPreference();
        }
    }

    public final void updateTimeSummary() {
        if (this.mSetSchedulePreference != null) {
            Context context = this.mContext;
            int customTimeToInt = EyeComfortTimeUtils.getCustomTimeToInt(context, true);
            int customTimeToInt2 = EyeComfortTimeUtils.getCustomTimeToInt(context, false);
            int i = customTimeToInt / 60;
            int i2 = customTimeToInt % 60;
            int i3 = customTimeToInt2 / 60;
            int i4 = customTimeToInt2 % 60;
            StringBuilder sb = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(EyeComfortTimeUtils.is24HourModeEnabled(context) ? 11 : 10, i);
            calendar.set(12, i2);
            sb.append(
                    DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis())));
            sb.append(" ~ ");
            calendar.clear();
            calendar.set(EyeComfortTimeUtils.is24HourModeEnabled(context) ? 11 : 10, i3);
            calendar.set(12, i4);
            if (customTimeToInt >= customTimeToInt2) {
                sb.append(
                        context.getResources()
                                .getString(
                                        R.string
                                                .sec_blue_light_filter_off_time_next_day_summary_format,
                                        DateFormat.getTimeFormat(context)
                                                .format(new Date(calendar.getTimeInMillis()))));
            } else {
                sb.append(
                        DateFormat.getTimeFormat(context)
                                .format(new Date(calendar.getTimeInMillis())));
            }
            this.mSetSchedulePreference.setSummary(sb.toString());
            updateDescriptionPreference();
        }
    }
}
