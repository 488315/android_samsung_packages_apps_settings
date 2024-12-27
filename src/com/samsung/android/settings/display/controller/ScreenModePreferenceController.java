package com.samsung.android.settings.display.controller;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;
import com.samsung.android.settings.display.NewModePreview;
import com.samsung.android.settings.display.SecDisplayUtils;

import org.json.JSONObject;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenModePreferenceController extends SecCustomPreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    public static final String ACTION_ROUTINE = "com.android.settings.SCREEN_MODE_ROUTINE_ACTION";
    private static final String KEY_BLUE = "blue";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_GREEN = "green";
    private static final String KEY_RED = "red";
    private static final String KEY_SCREEN_MODE = "screen_mode";
    private static final String KEY_SCREEN_MODE_VALUE = "screen_mode_value";
    private static final String KEY_WHITE_BALANCE = "white_balance";
    private static final String SCREEN_MODE_AUTOMATIC_SETTING = "screen_mode_automatic_setting";
    private static final String SCREEN_MODE_SETTING = "screen_mode_setting";
    private static final String TAG = "ScreenModePreferenceController";
    private int mBlueBal;
    private ContentObserver mContentObserver;
    private boolean mEnabled;
    private int mGreenBal;
    private SemMdnieManager mMdnie;
    private DisplayDisabledAppearancePreference mPreference;
    private boolean mPreferenceEnabled;
    private int mRedBal;
    private String mResult;
    private BroadcastReceiver mScreenModeChangeReceiver;
    private int mScreenModeValue;
    boolean mSupportNaturalModeWithoutWcgMode;
    boolean mSupportVividPlusMode;
    boolean mSupportWcgModeOnAmoled;
    private int mWhiteBal;
    private static final Uri BLUE_LIGHT_FILTER_URI = Settings.System.getUriFor("blue_light_filter");
    private static final Uri BLUE_LIGHT_FILTER_TYPE_URI =
            Settings.System.getUriFor("blue_light_filter_type");
    private static final Uri GREY_SCALE_URI = Settings.System.getUriFor("greyscale_mode");
    private static final Uri HIGH_CONTRAST_URI = Settings.System.getUriFor("high_contrast");
    private static final Uri COLOR_BLIND_URI = Settings.System.getUriFor("color_blind");
    private static final Uri COLOR_LENDS_URI = Settings.Secure.getUriFor("color_lens_switch");
    private static final Uri LOCATION_ALLOWED_URI =
            Settings.Secure.getUriFor("location_providers_allowed");
    private static final Uri COLOR_INVERSION =
            Settings.Secure.getUriFor("accessibility_display_inversion_enabled");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.controller.ScreenModePreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ScreenModePreferenceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass2(
                ScreenModePreferenceController screenModePreferenceController,
                Handler handler,
                int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = screenModePreferenceController;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 0:
                    super.onChange(z, uri);
                    ScreenModePreferenceController screenModePreferenceController = this.this$0;
                    screenModePreferenceController.updateState(
                            screenModePreferenceController.mPreference);
                    break;
                default:
                    super.onChange(z, uri);
                    ScreenModePreferenceController screenModePreferenceController2 = this.this$0;
                    screenModePreferenceController2.updateState(
                            screenModePreferenceController2.mPreference);
                    break;
            }
        }
    }

    public ScreenModePreferenceController(Context context, String str) {
        super(context, str);
        this.mScreenModeValue = 0;
        this.mWhiteBal = 0;
        this.mRedBal = 0;
        this.mGreenBal = 0;
        this.mBlueBal = 0;
        this.mSupportWcgModeOnAmoled = false;
        this.mSupportVividPlusMode = false;
        this.mSupportNaturalModeWithoutWcgMode = false;
        this.mScreenModeChangeReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.display.controller.ScreenModePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Log.i(
                                ScreenModePreferenceController.TAG,
                                "[ScreenModePreferenceController]onReceive : action = " + action);
                        if (ScreenModePreferenceController.ACTION_ROUTINE.equals(action)) {
                            ScreenModePreferenceController.this.updateScreenModeSummary();
                            if (ScreenModePreferenceController.this.mPreference != null) {
                                Log.d(
                                        ScreenModePreferenceController.TAG,
                                        "[mScreenModeChangeReceiver] updateState ??? "
                                                + ScreenModePreferenceController.this.mPreference
                                                + " / Yes");
                                ScreenModePreferenceController screenModePreferenceController =
                                        ScreenModePreferenceController.this;
                                screenModePreferenceController.updateState(
                                        screenModePreferenceController.mPreference);
                            }
                        }
                    }
                };
        this.mContentObserver = new AnonymousClass2(this, new Handler(Looper.getMainLooper()), 0);
        ensureBroadcastReceiver();
        this.mSupportWcgModeOnAmoled = Rune.supportWcgModeOnAmoled();
        this.mSupportVividPlusMode = Rune.supportVividPlusMode();
        this.mSupportNaturalModeWithoutWcgMode = Rune.supportNaturalModeWithoutWcgMode();
        this.mMdnie = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
    }

    private void collectData() {
        this.mScreenModeValue =
                NewModePreview.getCurrentScreenMode(this.mContext.getContentResolver());
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mWhiteBal =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sec_display_preset_index", 2);
        this.mGreenBal =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_green", 0);
        this.mRedBal =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_red", 0);
        this.mBlueBal =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_blue", 0);
        this.mEnabled = isEnabled();
    }

    private void ensureBroadcastReceiver() {
        this.mContext.registerReceiver(
                this.mScreenModeChangeReceiver,
                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(ACTION_ROUTINE),
                2);
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private boolean isEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "greyscale_mode", 0) == 0
                && Settings.System.getInt(this.mContext.getContentResolver(), "high_contrast", 0)
                        == 0
                && Settings.System.getInt(this.mContext.getContentResolver(), "color_blind", 0) == 0
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "blue_light_filter", 0)
                        == 0;
    }

    private void setScreenMode(int i) {
        if (i == 4) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), SCREEN_MODE_AUTOMATIC_SETTING, 1);
            Settings.System.putInt(this.mContext.getContentResolver(), SCREEN_MODE_SETTING, 4);
        } else {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), SCREEN_MODE_AUTOMATIC_SETTING, 0);
            Settings.System.putInt(this.mContext.getContentResolver(), SCREEN_MODE_SETTING, i);
        }
        if (this.mSupportWcgModeOnAmoled) {
            if (i == 3) {
                android.util.secutil.Log.secE(TAG, "set Mdnie ScreenMode WCG : 3 to 0");
                if (!this.mSupportVividPlusMode) {
                    SecDisplayUtils.setDisplayColor(0);
                }
                this.mMdnie.setScreenMode(0);
                return;
            }
            android.util.secutil.Log.secE(TAG, "set Mdnie ScreenMode : " + i);
            if (!this.mSupportVividPlusMode) {
                SecDisplayUtils.setDisplayColor(1);
            }
            this.mMdnie.setScreenMode(i);
            return;
        }
        if (!this.mSupportNaturalModeWithoutWcgMode) {
            android.util.secutil.Log.secE(TAG, "set Mdnie ScreenMode : " + i);
            this.mMdnie.setScreenMode(i);
            return;
        }
        if (i == 2) {
            android.util.secutil.Log.secE(TAG, "set Mdnie ScreenMode to Natural without WCG : 2");
            this.mMdnie.setScreenMode(2);
        } else {
            android.util.secutil.Log.secE(TAG, "set Mdnie ScreenMode : " + i);
            this.mMdnie.setScreenMode(i);
        }
    }

    private void setScreenModeValues() {
        if (this.mEnabled) {
            setScreenMode(this.mScreenModeValue);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "sec_display_preset_index", this.mWhiteBal);
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "sec_display_temperature_green",
                    this.mGreenBal);
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "sec_display_temperature_red",
                    this.mRedBal);
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "sec_display_temperature_blue",
                    this.mBlueBal);
        }
    }

    private void updateScreenModeState() {
        boolean z = Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext);
        boolean z2 =
                (SecDisplayUtils.isAccessibilityVisionEnabled(this.mContext) || z) ? false : true;
        this.mPreferenceEnabled = z2;
        this.mPreference.setEnabledAppearance(z2);
        this.mPreference.setFragment(
                this.mPreferenceEnabled ? NewModePreview.class.getCanonicalName() : null);
        String accessibilityVisionMessage =
                SecDisplayUtils.getAccessibilityVisionMessage(this.mContext);
        if (z) {
            this.mPreference.setEnabled(false);
        } else {
            if (TextUtils.isEmpty(accessibilityVisionMessage)) {
                return;
            }
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                    this.mPreference;
            Context context = this.mContext;
            displayDisabledAppearancePreference.mMsg =
                    context.getString(
                            R.string.blue_light_disable_reason,
                            accessibilityVisionMessage,
                            context.getString(R.string.sec_screen_mode_setting));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence updateScreenModeSummary() {
        boolean isTablet = Utils.isTablet();
        String str = ApnSettings.MVNO_NONE;
        if (isTablet) {
            if ((Rune.supportAmoledDisplay()
                            && (Rune.supportWcgModeOnAmoled()
                                    || Rune.supportNaturalModeWithoutWcgMode()))
                    || (!Rune.supportAmoledDisplay()
                            && SemFloatingFeature.getInstance()
                                            .getInt(
                                                    "SEC_FLOATING_FEATURE_LCD_SUPPORT_SCREEN_MODE_TYPE")
                                    == 1)) {
                int currentScreenMode = NewModePreview.getCurrentScreenMode(getContentResolver());
                return (currentScreenMode == 2 || currentScreenMode == 3)
                        ? this.mContext.getString(R.string.sec_screen_mode_wcg_natural)
                        : currentScreenMode != 4
                                ? ApnSettings.MVNO_NONE
                                : this.mContext.getString(R.string.sec_screen_mode_wcg_vivid);
            }
            if (Settings.System.getInt(getContentResolver(), SCREEN_MODE_AUTOMATIC_SETTING, 1)
                    == 1) {
                return this.mContext.getString(R.string.sec_screen_mode_auto_adaptive);
            }
            int i = Settings.System.getInt(getContentResolver(), SCREEN_MODE_SETTING, 1);
            if (i == 0) {
                str = this.mContext.getString(R.string.sec_screen_mode_premium_movie);
            } else if (i == 1) {
                str = this.mContext.getString(R.string.sec_screen_mode_amoled_photo);
            } else if (i == 2) {
                str = this.mContext.getString(R.string.sec_screen_mode_basic);
            } else if (i == 4) {
                str = this.mContext.getString(R.string.sec_screen_mode_auto_adaptive);
            } else if (i == 5) {
                str = this.mContext.getString(R.string.sec_screen_mode_reading_mode);
            }
            Log.d(TAG, "[1]screenModeController idx :" + i + " / summary :" + str);
            return str;
        }
        int currentScreenMode2 = NewModePreview.getCurrentScreenMode(getContentResolver());
        if (Rune.supportAmoledDisplay()) {
            if (currentScreenMode2 == 0) {
                str = this.mContext.getString(R.string.sec_screen_mode_premium_movie);
            } else if (currentScreenMode2 == 1) {
                str = this.mContext.getString(R.string.sec_screen_mode_amoled_photo);
            } else if (currentScreenMode2 == 2) {
                str =
                        Rune.supportNaturalModeWithoutWcgMode()
                                ? this.mContext.getString(R.string.sec_screen_mode_wcg_natural)
                                : this.mContext.getString(R.string.sec_screen_mode_basic);
            } else if (currentScreenMode2 == 3) {
                str = this.mContext.getString(R.string.sec_screen_mode_wcg_natural);
            } else if (currentScreenMode2 == 4) {
                str =
                        (Rune.supportWcgModeOnAmoled() || Rune.supportNaturalModeWithoutWcgMode())
                                ? this.mContext.getString(R.string.sec_screen_mode_wcg_vivid)
                                : this.mContext.getString(R.string.sec_screen_mode_auto_adaptive);
            }
            Log.d(
                    TAG,
                    "[2]screenModeController selectedMode :"
                            + currentScreenMode2
                            + " / summary :"
                            + str);
            return str;
        }
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_SCREEN_MODE_TYPE")
                != 2) {
            return ApnSettings.MVNO_NONE;
        }
        if (currentScreenMode2 == 0) {
            str = this.mContext.getString(R.string.sec_screen_mode_movie);
        } else if (currentScreenMode2 == 1) {
            str = this.mContext.getString(R.string.sec_screen_mode_photo);
        } else if (currentScreenMode2 == 2) {
            str =
                    Rune.supportNaturalModeWithoutWcgMode()
                            ? this.mContext.getString(R.string.sec_screen_mode_wcg_natural)
                            : this.mContext.getString(R.string.sec_screen_mode_basic);
        } else if (currentScreenMode2 == 4) {
            str =
                    (Rune.supportWcgModeOnAmoled() || Rune.supportNaturalModeWithoutWcgMode())
                            ? this.mContext.getString(R.string.sec_screen_mode_wcg_vivid)
                            : this.mContext.getString(R.string.sec_screen_mode_auto_adaptive);
        }
        Log.d(
                TAG,
                "[3]screenModeController selectedMode :"
                        + currentScreenMode2
                        + " / summary :"
                        + str);
        return str;
    }

    private boolean writeJSONData() {
        this.mResult = ApnSettings.MVNO_NONE;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_SCREEN_MODE_VALUE, this.mScreenModeValue);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            jSONObject.put(KEY_WHITE_BALANCE, this.mWhiteBal);
            jSONObject.put(KEY_RED, this.mRedBal);
            jSONObject.put(KEY_GREEN, this.mGreenBal);
            jSONObject.put(KEY_BLUE, this.mBlueBal);
            jSONObject.put("enabled", isEnabled());
            this.mResult = ApnSettings.MVNO_NONE + jSONObject;
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                (DisplayDisabledAppearancePreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = displayDisabledAppearancePreference;
        if (displayDisabledAppearancePreference != null) {
            displayDisabledAppearancePreference.getClass();
            SecPreferenceUtils.applySummaryColor(displayDisabledAppearancePreference, true);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Rune.supportScreenMode() ? 0 : 3;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$NewModePreviewActivity");
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SCREEN_MODE;
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
        return updateScreenModeSummary();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        collectData();
        if (!writeJSONData()) {
            return super.getValue();
        }
        String charSequence = getSummary().toString();
        ControlValue.Builder builder = new ControlValue.Builder(KEY_SCREEN_MODE, getControlType());
        builder.mAvailabilityStatus = getAvailabilityStatus();
        Boolean bool = Boolean.TRUE;
        builder.mForceChange = bool;
        builder.setControlId(getControlId());
        builder.mIsDefault = bool;
        builder.mStatusCode = getStatusCode();
        builder.mSummary = charSequence;
        builder.setValue(this.mResult);
        if (charSequence == null || charSequence.equals(ApnSettings.MVNO_NONE) || !isEnabled()) {
            builder.mAvailabilityStatus = 5;
        }
        return builder.build();
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
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mScreenModeChangeReceiver);
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        ensureBroadcastReceiver();
        if (this.mContentObserver == null) {
            this.mContentObserver =
                    new AnonymousClass2(this, new Handler(Looper.getMainLooper()), 1);
        }
        getContentResolver()
                .registerContentObserver(BLUE_LIGHT_FILTER_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(BLUE_LIGHT_FILTER_TYPE_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(GREY_SCALE_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(HIGH_CONTRAST_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(COLOR_BLIND_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(COLOR_LENDS_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(LOCATION_ALLOWED_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(COLOR_INVERSION, false, this.mContentObserver);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (controlValue != null && controlValue.mKey.equals(KEY_SCREEN_MODE)) {
            if (getAvailabilityStatus() != 0) {
                Log.e(TAG, "screen mode unsupported");
                ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
                builder.mResultCode = ControlResult.ResultCode.FAIL;
                builder.mErrorCode = ControlResult.ErrorCode.DEPENDENT_SETTING;
                builder.setErrorMsg(this.mContext.getString(R.string.unsupported_setting_summary));
                return new ControlResult(builder);
            }
            this.mResult = controlValue.getValue();
            try {
                JSONObject jSONObject = new JSONObject(this.mResult);
                this.mScreenModeValue = jSONObject.getInt(KEY_SCREEN_MODE_VALUE);
                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                this.mWhiteBal = jSONObject.getInt(KEY_WHITE_BALANCE);
                this.mRedBal = jSONObject.getInt(KEY_RED);
                this.mGreenBal = jSONObject.getInt(KEY_GREEN);
                this.mBlueBal = jSONObject.getInt(KEY_BLUE);
                this.mEnabled = jSONObject.getBoolean("enabled");
                setScreenModeValues();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        ControlResult.Builder builder2 = new ControlResult.Builder(getPreferenceKey());
        builder2.mResultCode = ControlResult.ResultCode.SUCCESS;
        return new ControlResult(builder2);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        updateScreenModeState();
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ScreenModePreferenceController(Context context) {
        super(context, KEY_SCREEN_MODE);
        this.mScreenModeValue = 0;
        this.mWhiteBal = 0;
        this.mRedBal = 0;
        this.mGreenBal = 0;
        this.mBlueBal = 0;
        this.mSupportWcgModeOnAmoled = false;
        this.mSupportVividPlusMode = false;
        this.mSupportNaturalModeWithoutWcgMode = false;
        this.mScreenModeChangeReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.display.controller.ScreenModePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Log.i(
                                ScreenModePreferenceController.TAG,
                                "[ScreenModePreferenceController]onReceive : action = " + action);
                        if (ScreenModePreferenceController.ACTION_ROUTINE.equals(action)) {
                            ScreenModePreferenceController.this.updateScreenModeSummary();
                            if (ScreenModePreferenceController.this.mPreference != null) {
                                Log.d(
                                        ScreenModePreferenceController.TAG,
                                        "[mScreenModeChangeReceiver] updateState ??? "
                                                + ScreenModePreferenceController.this.mPreference
                                                + " / Yes");
                                ScreenModePreferenceController screenModePreferenceController =
                                        ScreenModePreferenceController.this;
                                screenModePreferenceController.updateState(
                                        screenModePreferenceController.mPreference);
                            }
                        }
                    }
                };
        this.mContentObserver = new AnonymousClass2(this, new Handler(Looper.getMainLooper()), 0);
        ensureBroadcastReceiver();
        this.mSupportWcgModeOnAmoled = Rune.supportWcgModeOnAmoled();
        this.mSupportNaturalModeWithoutWcgMode = Rune.supportNaturalModeWithoutWcgMode();
        this.mMdnie = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
    }
}
