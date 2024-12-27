package com.samsung.android.settings.display;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.secutil.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SecNewModeSeekBar;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecWrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAdaptiveDisplaySettings extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass24();
    public View mAdaptiveDisplayView;
    public final AnonymousClass23 mBatteryInfoReceiver;
    public TextView mBlueLabel;
    public final AnonymousClass3 mBluelightFilterObserver;
    public Context mContext;
    public SeekBar mCustomSeekBarBlue;
    public final AnonymousClass16 mCustomSeekBarBlueFocusChangeListener;
    public final AnonymousClass17 mCustomSeekBarBlueSeekBarChangeListener;
    public SeekBar mCustomSeekBarGreen;
    public final AnonymousClass16 mCustomSeekBarGreenFocusChangeListener;
    public final AnonymousClass17 mCustomSeekBarGreenSeekBarChangeListener;
    public SeekBar mCustomSeekBarRed;
    public final AnonymousClass16 mCustomSeekBarRedFocusChangeListener;
    public final AnonymousClass17 mCustomSeekBarRedSeekBarChangeListener;
    public final AnonymousClass3 mDTBlueObserver;
    public final AnonymousClass3 mDTGreenObserver;
    public final AnonymousClass3 mDTRedObserver;
    public DisplayMetrics mDisplayMetrics;
    public final AnonymousClass3 mEadObserver;
    public LinearLayout mEdgeColorBalance;
    public TextView mEdgeColorBalanceSubText;
    public TextView mEdgeColorBalanceTitleText;
    public TextView mGreenLabel;
    public NestedScrollView mNestedScrollView;
    public LinearLayout mPointArea;
    public RelativeLayout mPreviewLayout;
    public AnimationDrawable mProgressBlueExpandAnimation;
    public AnimationDrawable mProgressBlueShrinkAnimation;
    public AnimationDrawable mProgressGreenExpandAnimation;
    public AnimationDrawable mProgressGreenShrinkAnimation;
    public AnimationDrawable mProgressRedExpandAnimation;
    public AnimationDrawable mProgressRedShrinkAnimation;
    public TextView mRedLabel;
    public AnimationDrawable mThumbBlueFadeInAnimation;
    public AnimationDrawable mThumbBlueFadeOutAnimation;
    public AnimationDrawable mThumbGreenFadeInAnimation;
    public AnimationDrawable mThumbGreenFadeOutAnimation;
    public AnimationDrawable mThumbRedFadeInAnimation;
    public AnimationDrawable mThumbRedFadeOutAnimation;
    public AnimationDrawable mTickBlueFadeInAnimation;
    public AnimationDrawable mTickBlueFadeOutAnimation;
    public AnimationDrawable mTickGreenFadeInAnimation;
    public AnimationDrawable mTickGreenFadeOutAnimation;
    public AnimationDrawable mTickRedFadeInAnimation;
    public AnimationDrawable mTickRedFadeOutAnimation;
    public SecWrapContentHeightViewPager mViewPager;
    public ScreenModePreviewPagerAdapter mViewPagerAdapter;
    public LinearLayout mVividnessIntensity;
    public SecNewModeSeekBar mVividnessIntensitySeekbar;
    public boolean mIsFocusableRed = false;
    public boolean mIsFocusableGreen = false;
    public boolean mIsFocusableBlue = false;
    public AlertDialog mEdgeColorBalanceBatteryCheckDialog = null;
    public int mBatteryLevel = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecAdaptiveDisplaySettings$22, reason: invalid class name */
    public final class AnonymousClass22 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecAdaptiveDisplaySettings$24, reason: invalid class name */
    public final class AnonymousClass24 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (Rune.supportVividness()) {
                boolean z = Rune.isSamsungDexMode(context) && Utils.isDesktopDualMode(context);
                boolean z2 =
                        Settings.System.getInt(context.getContentResolver(), "blue_light_filter", 0)
                                != 0;
                boolean isAccessibilityVisionEnabled =
                        SecDisplayUtils.isAccessibilityVisionEnabled(context);
                boolean z3 =
                        Settings.System.getInt(context.getContentResolver(), "ead_enabled", 0) != 0;
                if (z || z2 || isAccessibilityVisionEnabled || z3) {
                    ((ArrayList) nonIndexableKeys).add("vividness_intensity");
                }
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            new SearchIndexableRaw(context);
            if (Rune.supportVividness()) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                searchIndexableRaw.title = String.valueOf(R.string.sec_screen_mode_vividness_title);
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.sec_screen_mode_advanced_settings);
                ((SearchIndexableData) searchIndexableRaw).key = "vividness_intensity";
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.supportScreenMode();
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$3] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$17] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$16] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$17] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$16] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$17] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$23] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$3] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$3] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$3] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.samsung.android.settings.display.SecAdaptiveDisplaySettings$16] */
    public SecAdaptiveDisplaySettings() {
        final int i = 0;
        this.mBluelightFilterObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_red",
                                                        0)
                                                + 11);
                                break;
                            case 2:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings2.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_green",
                                                        0)
                                                + 11);
                                break;
                            case 3:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings3.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_blue",
                                                        0)
                                                + 11);
                                break;
                            case 4:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 5:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 6:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            default:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                        }
                    }
                };
        final int i2 = 4;
        this.mEadObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_red",
                                                        0)
                                                + 11);
                                break;
                            case 2:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings2.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_green",
                                                        0)
                                                + 11);
                                break;
                            case 3:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings3.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_blue",
                                                        0)
                                                + 11);
                                break;
                            case 4:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 5:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 6:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            default:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                        }
                    }
                };
        final int i3 = 5;
        new ContentObserver(
                this,
                new Handler()) { // from class:
                                 // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
            public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

            {
                this.this$0 = this;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                switch (i3) {
                    case 0:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 1:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                        secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_red",
                                                0)
                                        + 11);
                        break;
                    case 2:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 = this.this$0;
                        secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings2.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_green",
                                                0)
                                        + 11);
                        break;
                    case 3:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 = this.this$0;
                        secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings3.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0)
                                        + 11);
                        break;
                    case 4:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 5:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 6:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    default:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                }
            }
        };
        final int i4 = 6;
        new ContentObserver(
                this,
                new Handler()) { // from class:
                                 // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
            public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

            {
                this.this$0 = this;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                switch (i4) {
                    case 0:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 1:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                        secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_red",
                                                0)
                                        + 11);
                        break;
                    case 2:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 = this.this$0;
                        secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings2.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_green",
                                                0)
                                        + 11);
                        break;
                    case 3:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 = this.this$0;
                        secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings3.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0)
                                        + 11);
                        break;
                    case 4:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 5:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 6:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    default:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                }
            }
        };
        final int i5 = 7;
        new ContentObserver(
                this,
                new Handler()) { // from class:
                                 // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
            public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

            {
                this.this$0 = this;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                switch (i5) {
                    case 0:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 1:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                        secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_red",
                                                0)
                                        + 11);
                        break;
                    case 2:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 = this.this$0;
                        secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings2.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_green",
                                                0)
                                        + 11);
                        break;
                    case 3:
                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 = this.this$0;
                        secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                Settings.System.getInt(
                                                secAdaptiveDisplaySettings3.mContext
                                                        .getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0)
                                        + 11);
                        break;
                    case 4:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 5:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    case 6:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                    default:
                        this.this$0.updateTemperatureSettingsState();
                        break;
                }
            }
        };
        final int i6 = 1;
        this.mDTRedObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i6) {
                            case 0:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_red",
                                                        0)
                                                + 11);
                                break;
                            case 2:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings2.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_green",
                                                        0)
                                                + 11);
                                break;
                            case 3:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings3.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_blue",
                                                        0)
                                                + 11);
                                break;
                            case 4:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 5:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 6:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            default:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                        }
                    }
                };
        final int i7 = 2;
        this.mDTGreenObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i7) {
                            case 0:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_red",
                                                        0)
                                                + 11);
                                break;
                            case 2:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings2.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_green",
                                                        0)
                                                + 11);
                                break;
                            case 3:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings3.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_blue",
                                                        0)
                                                + 11);
                                break;
                            case 4:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 5:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 6:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            default:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                        }
                    }
                };
        final int i8 = 3;
        this.mDTBlueObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.3
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i8) {
                            case 0:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mCustomSeekBarRed.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_red",
                                                        0)
                                                + 11);
                                break;
                            case 2:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarGreen.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings2.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_green",
                                                        0)
                                                + 11);
                                break;
                            case 3:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarBlue.setProgress(
                                        Settings.System.getInt(
                                                        secAdaptiveDisplaySettings3.mContext
                                                                .getContentResolver(),
                                                        "sec_display_temperature_blue",
                                                        0)
                                                + 11);
                                break;
                            case 4:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 5:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            case 6:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                            default:
                                this.this$0.updateTemperatureSettingsState();
                                break;
                        }
                    }
                };
        final int i9 = 0;
        this.mCustomSeekBarRedFocusChangeListener =
                new View.OnFocusChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.16
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        switch (i9) {
                            case 0:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mIsFocusableRed = z;
                                if (!z && secAdaptiveDisplaySettings.mCustomSeekBarRed != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            this.this$0.mCustomSeekBarRed.getProgress() - 11);
                                    break;
                                }
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mIsFocusableGreen = z;
                                if (!z && secAdaptiveDisplaySettings2.mCustomSeekBarGreen != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            this.this$0.mCustomSeekBarGreen.getProgress() - 11);
                                    break;
                                }
                                break;
                            default:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mIsFocusableBlue = z;
                                if (!z && secAdaptiveDisplaySettings3.mCustomSeekBarBlue != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            this.this$0.mCustomSeekBarBlue.getProgress() - 11);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mCustomSeekBarRedSeekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.17
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i10, boolean z) {
                        switch (i9) {
                            case 0:
                                int i11 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_red",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                boolean z2 = secAdaptiveDisplaySettings.mIsFocusableRed;
                                if (z2 || (!z2 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            seekBar.getProgress() - 11);
                                }
                                if (i11 != i10 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            case 1:
                                int i12 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_green",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                boolean z3 = secAdaptiveDisplaySettings2.mIsFocusableGreen;
                                if (z3 || (!z3 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            seekBar.getProgress() - 11);
                                }
                                if (i12 != i10 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            default:
                                int i13 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                boolean z4 = secAdaptiveDisplaySettings3.mIsFocusableBlue;
                                if (z4 || (!z4 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            seekBar.getProgress() - 11);
                                }
                                if (i13 != i10 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        switch (i9) {
                            case 0:
                                this.this$0.mIsFocusableRed = true;
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = true;
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = true;
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        switch (i9) {
                            case 0:
                                this.this$0.mIsFocusableRed = false;
                                int progress = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_red",
                                        progress);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7451, progress);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                        secAdaptiveDisplaySettings.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mRedLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mRedLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarRed.setTickMark(
                                        secAdaptiveDisplaySettings2.mTickRedFadeOutAnimation);
                                this.this$0.mTickRedFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarRed.setProgressDrawable(
                                        secAdaptiveDisplaySettings3.mProgressRedShrinkAnimation);
                                this.this$0.mProgressRedShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                        this.this$0;
                                secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                        secAdaptiveDisplaySettings4.mThumbRedFadeInAnimation);
                                this.this$0.mThumbRedFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                        this.this$0;
                                secAdaptiveDisplaySettings5.mProgressRedExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings5.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                        this.this$0;
                                secAdaptiveDisplaySettings6.mProgressRedShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings6.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                        this.this$0;
                                secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings7.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                        this.this$0;
                                secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings8.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                        this.this$0;
                                secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings9.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings10 =
                                        this.this$0;
                                secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings10.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_out_animation);
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = false;
                                int progress2 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_green",
                                        progress2);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7452, progress2);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                        this.this$0;
                                secAdaptiveDisplaySettings11.mGreenLabel.setTextColor(
                                        secAdaptiveDisplaySettings11.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mGreenLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mGreenLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                        this.this$0;
                                secAdaptiveDisplaySettings12.mCustomSeekBarGreen.setTickMark(
                                        secAdaptiveDisplaySettings12.mTickGreenFadeOutAnimation);
                                this.this$0.mTickGreenFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                        this.this$0;
                                secAdaptiveDisplaySettings13.mCustomSeekBarGreen
                                        .setProgressDrawable(
                                                secAdaptiveDisplaySettings13
                                                        .mProgressGreenShrinkAnimation);
                                this.this$0.mProgressGreenShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                        this.this$0;
                                secAdaptiveDisplaySettings14.mCustomSeekBarGreen.setThumb(
                                        secAdaptiveDisplaySettings14.mThumbGreenFadeInAnimation);
                                this.this$0.mThumbGreenFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings15 =
                                        this.this$0;
                                secAdaptiveDisplaySettings15.mProgressGreenExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings15.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings16 =
                                        this.this$0;
                                secAdaptiveDisplaySettings16.mProgressGreenShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings16.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings17 =
                                        this.this$0;
                                secAdaptiveDisplaySettings17.mThumbGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings17.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings18 =
                                        this.this$0;
                                secAdaptiveDisplaySettings18.mThumbGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings18.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings19 =
                                        this.this$0;
                                secAdaptiveDisplaySettings19.mTickGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings19.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings20 =
                                        this.this$0;
                                secAdaptiveDisplaySettings20.mTickGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings20.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_out_animation);
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = false;
                                int progress3 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_blue",
                                        progress3);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7453, progress3);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings21 =
                                        this.this$0;
                                secAdaptiveDisplaySettings21.mBlueLabel.setTextColor(
                                        secAdaptiveDisplaySettings21.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mBlueLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mBlueLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings22 =
                                        this.this$0;
                                secAdaptiveDisplaySettings22.mCustomSeekBarBlue.setTickMark(
                                        secAdaptiveDisplaySettings22.mTickBlueFadeOutAnimation);
                                this.this$0.mTickBlueFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings23 =
                                        this.this$0;
                                secAdaptiveDisplaySettings23.mCustomSeekBarBlue.setProgressDrawable(
                                        secAdaptiveDisplaySettings23.mProgressBlueShrinkAnimation);
                                this.this$0.mProgressBlueShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings24 =
                                        this.this$0;
                                secAdaptiveDisplaySettings24.mCustomSeekBarBlue.setThumb(
                                        secAdaptiveDisplaySettings24.mThumbBlueFadeInAnimation);
                                this.this$0.mThumbBlueFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                        this.this$0;
                                secAdaptiveDisplaySettings25.mProgressBlueExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings25.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                        this.this$0;
                                secAdaptiveDisplaySettings26.mProgressBlueShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings26.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                        this.this$0;
                                secAdaptiveDisplaySettings27.mThumbBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings27.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                        this.this$0;
                                secAdaptiveDisplaySettings28.mThumbBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings29 =
                                        this.this$0;
                                secAdaptiveDisplaySettings29.mTickBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings29.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings30 =
                                        this.this$0;
                                secAdaptiveDisplaySettings30.mTickBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings30.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                break;
                        }
                    }
                };
        final int i10 = 1;
        this.mCustomSeekBarGreenFocusChangeListener =
                new View.OnFocusChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.16
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        switch (i10) {
                            case 0:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mIsFocusableRed = z;
                                if (!z && secAdaptiveDisplaySettings.mCustomSeekBarRed != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            this.this$0.mCustomSeekBarRed.getProgress() - 11);
                                    break;
                                }
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mIsFocusableGreen = z;
                                if (!z && secAdaptiveDisplaySettings2.mCustomSeekBarGreen != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            this.this$0.mCustomSeekBarGreen.getProgress() - 11);
                                    break;
                                }
                                break;
                            default:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mIsFocusableBlue = z;
                                if (!z && secAdaptiveDisplaySettings3.mCustomSeekBarBlue != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            this.this$0.mCustomSeekBarBlue.getProgress() - 11);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mCustomSeekBarGreenSeekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.17
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i102, boolean z) {
                        switch (i10) {
                            case 0:
                                int i11 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_red",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                boolean z2 = secAdaptiveDisplaySettings.mIsFocusableRed;
                                if (z2 || (!z2 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            seekBar.getProgress() - 11);
                                }
                                if (i11 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            case 1:
                                int i12 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_green",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                boolean z3 = secAdaptiveDisplaySettings2.mIsFocusableGreen;
                                if (z3 || (!z3 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            seekBar.getProgress() - 11);
                                }
                                if (i12 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            default:
                                int i13 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                boolean z4 = secAdaptiveDisplaySettings3.mIsFocusableBlue;
                                if (z4 || (!z4 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            seekBar.getProgress() - 11);
                                }
                                if (i13 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        switch (i10) {
                            case 0:
                                this.this$0.mIsFocusableRed = true;
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = true;
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = true;
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        switch (i10) {
                            case 0:
                                this.this$0.mIsFocusableRed = false;
                                int progress = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_red",
                                        progress);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7451, progress);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                        secAdaptiveDisplaySettings.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mRedLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mRedLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarRed.setTickMark(
                                        secAdaptiveDisplaySettings2.mTickRedFadeOutAnimation);
                                this.this$0.mTickRedFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarRed.setProgressDrawable(
                                        secAdaptiveDisplaySettings3.mProgressRedShrinkAnimation);
                                this.this$0.mProgressRedShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                        this.this$0;
                                secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                        secAdaptiveDisplaySettings4.mThumbRedFadeInAnimation);
                                this.this$0.mThumbRedFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                        this.this$0;
                                secAdaptiveDisplaySettings5.mProgressRedExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings5.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                        this.this$0;
                                secAdaptiveDisplaySettings6.mProgressRedShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings6.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                        this.this$0;
                                secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings7.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                        this.this$0;
                                secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings8.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                        this.this$0;
                                secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings9.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings10 =
                                        this.this$0;
                                secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings10.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_out_animation);
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = false;
                                int progress2 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_green",
                                        progress2);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7452, progress2);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                        this.this$0;
                                secAdaptiveDisplaySettings11.mGreenLabel.setTextColor(
                                        secAdaptiveDisplaySettings11.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mGreenLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mGreenLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                        this.this$0;
                                secAdaptiveDisplaySettings12.mCustomSeekBarGreen.setTickMark(
                                        secAdaptiveDisplaySettings12.mTickGreenFadeOutAnimation);
                                this.this$0.mTickGreenFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                        this.this$0;
                                secAdaptiveDisplaySettings13.mCustomSeekBarGreen
                                        .setProgressDrawable(
                                                secAdaptiveDisplaySettings13
                                                        .mProgressGreenShrinkAnimation);
                                this.this$0.mProgressGreenShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                        this.this$0;
                                secAdaptiveDisplaySettings14.mCustomSeekBarGreen.setThumb(
                                        secAdaptiveDisplaySettings14.mThumbGreenFadeInAnimation);
                                this.this$0.mThumbGreenFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings15 =
                                        this.this$0;
                                secAdaptiveDisplaySettings15.mProgressGreenExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings15.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings16 =
                                        this.this$0;
                                secAdaptiveDisplaySettings16.mProgressGreenShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings16.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings17 =
                                        this.this$0;
                                secAdaptiveDisplaySettings17.mThumbGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings17.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings18 =
                                        this.this$0;
                                secAdaptiveDisplaySettings18.mThumbGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings18.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings19 =
                                        this.this$0;
                                secAdaptiveDisplaySettings19.mTickGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings19.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings20 =
                                        this.this$0;
                                secAdaptiveDisplaySettings20.mTickGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings20.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_out_animation);
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = false;
                                int progress3 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_blue",
                                        progress3);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7453, progress3);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings21 =
                                        this.this$0;
                                secAdaptiveDisplaySettings21.mBlueLabel.setTextColor(
                                        secAdaptiveDisplaySettings21.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mBlueLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mBlueLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings22 =
                                        this.this$0;
                                secAdaptiveDisplaySettings22.mCustomSeekBarBlue.setTickMark(
                                        secAdaptiveDisplaySettings22.mTickBlueFadeOutAnimation);
                                this.this$0.mTickBlueFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings23 =
                                        this.this$0;
                                secAdaptiveDisplaySettings23.mCustomSeekBarBlue.setProgressDrawable(
                                        secAdaptiveDisplaySettings23.mProgressBlueShrinkAnimation);
                                this.this$0.mProgressBlueShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings24 =
                                        this.this$0;
                                secAdaptiveDisplaySettings24.mCustomSeekBarBlue.setThumb(
                                        secAdaptiveDisplaySettings24.mThumbBlueFadeInAnimation);
                                this.this$0.mThumbBlueFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                        this.this$0;
                                secAdaptiveDisplaySettings25.mProgressBlueExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings25.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                        this.this$0;
                                secAdaptiveDisplaySettings26.mProgressBlueShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings26.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                        this.this$0;
                                secAdaptiveDisplaySettings27.mThumbBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings27.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                        this.this$0;
                                secAdaptiveDisplaySettings28.mThumbBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings29 =
                                        this.this$0;
                                secAdaptiveDisplaySettings29.mTickBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings29.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings30 =
                                        this.this$0;
                                secAdaptiveDisplaySettings30.mTickBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings30.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                break;
                        }
                    }
                };
        final int i11 = 2;
        this.mCustomSeekBarBlueFocusChangeListener =
                new View.OnFocusChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.16
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        switch (i11) {
                            case 0:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mIsFocusableRed = z;
                                if (!z && secAdaptiveDisplaySettings.mCustomSeekBarRed != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            this.this$0.mCustomSeekBarRed.getProgress() - 11);
                                    break;
                                }
                                break;
                            case 1:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mIsFocusableGreen = z;
                                if (!z && secAdaptiveDisplaySettings2.mCustomSeekBarGreen != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            this.this$0.mCustomSeekBarGreen.getProgress() - 11);
                                    break;
                                }
                                break;
                            default:
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mIsFocusableBlue = z;
                                if (!z && secAdaptiveDisplaySettings3.mCustomSeekBarBlue != null) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            this.this$0.mCustomSeekBarBlue.getProgress() - 11);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mCustomSeekBarBlueSeekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.17
                    public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i102, boolean z) {
                        switch (i11) {
                            case 0:
                                int i112 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_red",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                boolean z2 = secAdaptiveDisplaySettings.mIsFocusableRed;
                                if (z2 || (!z2 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_red",
                                            seekBar.getProgress() - 11);
                                }
                                if (i112 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            case 1:
                                int i12 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_green",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                boolean z3 = secAdaptiveDisplaySettings2.mIsFocusableGreen;
                                if (z3 || (!z3 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings2.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_green",
                                            seekBar.getProgress() - 11);
                                }
                                if (i12 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                            default:
                                int i13 =
                                        Settings.System.getInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "sec_display_temperature_blue",
                                                0);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                boolean z4 = secAdaptiveDisplaySettings3.mIsFocusableBlue;
                                if (z4 || (!z4 && z)) {
                                    Settings.System.putInt(
                                            secAdaptiveDisplaySettings3.mContext
                                                    .getContentResolver(),
                                            "sec_display_temperature_blue",
                                            seekBar.getProgress() - 11);
                                }
                                if (i13 != i102 - 11) {
                                    seekBar.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                    break;
                                }
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {
                        switch (i11) {
                            case 0:
                                this.this$0.mIsFocusableRed = true;
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = true;
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = true;
                                break;
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        switch (i11) {
                            case 0:
                                this.this$0.mIsFocusableRed = false;
                                int progress = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_red",
                                        progress);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7451, progress);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings = this.this$0;
                                secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                        secAdaptiveDisplaySettings.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mRedLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mRedLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                        this.this$0;
                                secAdaptiveDisplaySettings2.mCustomSeekBarRed.setTickMark(
                                        secAdaptiveDisplaySettings2.mTickRedFadeOutAnimation);
                                this.this$0.mTickRedFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                        this.this$0;
                                secAdaptiveDisplaySettings3.mCustomSeekBarRed.setProgressDrawable(
                                        secAdaptiveDisplaySettings3.mProgressRedShrinkAnimation);
                                this.this$0.mProgressRedShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                        this.this$0;
                                secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                        secAdaptiveDisplaySettings4.mThumbRedFadeInAnimation);
                                this.this$0.mThumbRedFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                        this.this$0;
                                secAdaptiveDisplaySettings5.mProgressRedExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings5.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                        this.this$0;
                                secAdaptiveDisplaySettings6.mProgressRedShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings6.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_red_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                        this.this$0;
                                secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings7.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                        this.this$0;
                                secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings8.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                        this.this$0;
                                secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings9.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings10 =
                                        this.this$0;
                                secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings10.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_red_fade_out_animation);
                                break;
                            case 1:
                                this.this$0.mIsFocusableGreen = false;
                                int progress2 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_green",
                                        progress2);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7452, progress2);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                        this.this$0;
                                secAdaptiveDisplaySettings11.mGreenLabel.setTextColor(
                                        secAdaptiveDisplaySettings11.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mGreenLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mGreenLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                        this.this$0;
                                secAdaptiveDisplaySettings12.mCustomSeekBarGreen.setTickMark(
                                        secAdaptiveDisplaySettings12.mTickGreenFadeOutAnimation);
                                this.this$0.mTickGreenFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                        this.this$0;
                                secAdaptiveDisplaySettings13.mCustomSeekBarGreen
                                        .setProgressDrawable(
                                                secAdaptiveDisplaySettings13
                                                        .mProgressGreenShrinkAnimation);
                                this.this$0.mProgressGreenShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                        this.this$0;
                                secAdaptiveDisplaySettings14.mCustomSeekBarGreen.setThumb(
                                        secAdaptiveDisplaySettings14.mThumbGreenFadeInAnimation);
                                this.this$0.mThumbGreenFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings15 =
                                        this.this$0;
                                secAdaptiveDisplaySettings15.mProgressGreenExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings15.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings16 =
                                        this.this$0;
                                secAdaptiveDisplaySettings16.mProgressGreenShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings16.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_green_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings17 =
                                        this.this$0;
                                secAdaptiveDisplaySettings17.mThumbGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings17.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings18 =
                                        this.this$0;
                                secAdaptiveDisplaySettings18.mThumbGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings18.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings19 =
                                        this.this$0;
                                secAdaptiveDisplaySettings19.mTickGreenFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings19.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings20 =
                                        this.this$0;
                                secAdaptiveDisplaySettings20.mTickGreenFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings20.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_green_fade_out_animation);
                                break;
                            default:
                                this.this$0.mIsFocusableBlue = false;
                                int progress3 = seekBar.getProgress() - 11;
                                Settings.System.putInt(
                                        this.this$0.mContext.getContentResolver(),
                                        "sec_display_temperature_blue",
                                        progress3);
                                this.this$0.getClass();
                                LoggingHelper.insertEventLogging(7450, 7453, progress3);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings21 =
                                        this.this$0;
                                secAdaptiveDisplaySettings21.mBlueLabel.setTextColor(
                                        secAdaptiveDisplaySettings21.mContext.getColor(
                                                R.color
                                                        .sec_display_color_balance_label_text_color));
                                this.this$0.mBlueLabel.setTypeface(Typeface.create("sec", 0));
                                this.this$0.mBlueLabel.setBackground(null);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings22 =
                                        this.this$0;
                                secAdaptiveDisplaySettings22.mCustomSeekBarBlue.setTickMark(
                                        secAdaptiveDisplaySettings22.mTickBlueFadeOutAnimation);
                                this.this$0.mTickBlueFadeOutAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings23 =
                                        this.this$0;
                                secAdaptiveDisplaySettings23.mCustomSeekBarBlue.setProgressDrawable(
                                        secAdaptiveDisplaySettings23.mProgressBlueShrinkAnimation);
                                this.this$0.mProgressBlueShrinkAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings24 =
                                        this.this$0;
                                secAdaptiveDisplaySettings24.mCustomSeekBarBlue.setThumb(
                                        secAdaptiveDisplaySettings24.mThumbBlueFadeInAnimation);
                                this.this$0.mThumbBlueFadeInAnimation.start();
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                        this.this$0;
                                secAdaptiveDisplaySettings25.mProgressBlueExpandAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings25.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_expand_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                        this.this$0;
                                secAdaptiveDisplaySettings26.mProgressBlueShrinkAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings26.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_blue_shrink_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                        this.this$0;
                                secAdaptiveDisplaySettings27.mThumbBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings27.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                        this.this$0;
                                secAdaptiveDisplaySettings28.mThumbBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings29 =
                                        this.this$0;
                                secAdaptiveDisplaySettings29.mTickBlueFadeInAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings29.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                SecAdaptiveDisplaySettings secAdaptiveDisplaySettings30 =
                                        this.this$0;
                                secAdaptiveDisplaySettings30.mTickBlueFadeOutAnimation =
                                        (AnimationDrawable)
                                                secAdaptiveDisplaySettings30.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                break;
                        }
                    }
                };
        this.mBatteryInfoReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.23
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                            SecAdaptiveDisplaySettings.this.mBatteryLevel =
                                    com.android.settingslib.Utils.getBatteryLevel(intent);
                        }
                    }
                };
    }

    public final View createAdaptiveDisplayView(
            LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View findViewById;
        final int i = 0;
        final int i2 = 2;
        final int i3 = 1;
        Log.d("SecAdaptiveDisplaySettings", "createAdaptiveDisplayView()");
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
        }
        View inflate =
                layoutInflater.inflate(R.layout.sec_display_temperature_list_item, viewGroup);
        this.mAdaptiveDisplayView = inflate;
        inflate.semSetRoundedCorners(15);
        this.mAdaptiveDisplayView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        boolean z = getContext().getResources().getConfiguration().orientation == 2;
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        if (z) {
            ((LinearLayout)
                            this.mAdaptiveDisplayView.findViewById(
                                    R.id.new_mode_advanced_settings_layout))
                    .setPaddingRelative(listHorizontalPadding, 0, 0, 0);
            this.mNestedScrollView =
                    (NestedScrollView)
                            this.mAdaptiveDisplayView.findViewById(R.id.screen_mode_text_container);
        } else {
            this.mNestedScrollView =
                    (NestedScrollView)
                            this.mAdaptiveDisplayView.findViewById(
                                    R.id.new_mode_advanced_settings_layout);
        }
        this.mNestedScrollView.setPaddingRelative(
                listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mNestedScrollView.semSetRoundedCorners(15);
        this.mNestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mNestedScrollView.semSetRoundedCornerOffset(listHorizontalPadding);
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mViewPager =
                (SecWrapContentHeightViewPager) this.mAdaptiveDisplayView.findViewById(R.id.pager);
        ScreenModePreviewPagerAdapter screenModePreviewPagerAdapter =
                new ScreenModePreviewPagerAdapter(this.mContext);
        this.mViewPagerAdapter = screenModePreviewPagerAdapter;
        this.mViewPager.setAdapter(screenModePreviewPagerAdapter);
        this.mViewPager.setOffscreenPageLimit(this.mViewPagerAdapter.mDescriptions.size());
        this.mViewPager.setOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i4) {
                        int i5 = 0;
                        while (true) {
                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings =
                                    SecAdaptiveDisplaySettings.this;
                            if (i5 >= secAdaptiveDisplaySettings.mPointArea.getChildCount()) {
                                ((ImageView) secAdaptiveDisplaySettings.mPointArea.getChildAt(i4))
                                        .setImageResource(
                                                R.drawable.sec_screen_mode_page_indicator_selected);
                                return;
                            } else {
                                ((ImageView) secAdaptiveDisplaySettings.mPointArea.getChildAt(i5))
                                        .setImageResource(
                                                R.drawable
                                                        .sec_screen_mode_page_indicator_unselected);
                                i5++;
                            }
                        }
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i4) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i4, int i5, float f) {}
                });
        RelativeLayout relativeLayout =
                (RelativeLayout) this.mAdaptiveDisplayView.findViewById(R.id.preview_layout);
        this.mPreviewLayout = relativeLayout;
        if (relativeLayout != null) {
            ViewGroup.LayoutParams layoutParams2 = relativeLayout.getLayoutParams();
            if (Utils.isTablet()) {
                layoutParams2.height = Math.round(this.mDisplayMetrics.heightPixels * 0.45f);
                this.mPreviewLayout.setLayoutParams(layoutParams2);
            } else {
                layoutParams2.height = Math.round(this.mDisplayMetrics.heightPixels * 0.35f);
                this.mPreviewLayout.setLayoutParams(layoutParams2);
            }
            this.mPreviewLayout.semSetRoundedCorners(15);
            this.mPreviewLayout.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mPointArea = (LinearLayout) this.mAdaptiveDisplayView.findViewById(R.id.point_area);
        int size = this.mViewPagerAdapter.mDescriptions.size();
        final int i4 = 0;
        while (i4 < size) {
            ImageView imageView =
                    (ImageView)
                            layoutInflater.inflate(
                                    R.layout.sec_screen_mode_pager_circle, (ViewGroup) null);
            int i5 = i4 + 1;
            imageView.setContentDescription(
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.page_description_template),
                            Integer.valueOf(i5),
                            Integer.valueOf(size)));
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecAdaptiveDisplaySettings.this.mViewPager.setCurrentItem(i4);
                        }
                    });
            this.mPointArea.addView(imageView);
            i4 = i5;
        }
        if (this.mPointArea.getChildCount() > 0) {
            ((ImageView) this.mPointArea.getChildAt(0))
                    .setImageResource(R.drawable.sec_screen_mode_page_indicator_selected);
        }
        if (this.mPointArea.getChildCount() == 1) {
            this.mPointArea.setVisibility(8);
        }
        if (Utils.isRTL(this.mContext)) {
            this.mViewPager.setCurrentItem(size);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        Log.d("SecAdaptiveDisplaySettings", "onCreate():  mIsCustomEdgeBalanceSupported=false");
        int i6 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_custom", -1);
        Log.d("SecAdaptiveDisplaySettings", "init DB : " + i6);
        if (i6 == -1) {
            int i7 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "sec_display_temperature_red", 0);
            int i8 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "sec_display_temperature_green", 0);
            int i9 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "sec_display_temperature_blue", 0);
            StringBuilder m =
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "init DB : ", ", ", i7, i8, ", ");
            m.append(i9);
            Log.d("SecAdaptiveDisplaySettings", m.toString());
            if (i7 == 0 && i8 == 0 && i9 == 0) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_custom", 0);
            } else {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "sec_display_temperature_custom", 1);
            }
        }
        this.mRedLabel = (TextView) this.mAdaptiveDisplayView.findViewById(R.id.red_label);
        SeekBar seekBar = (SeekBar) this.mAdaptiveDisplayView.findViewById(R.id.custom_seekbar_red);
        this.mCustomSeekBarRed = seekBar;
        if (seekBar != null) {
            int i10 =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_red",
                                    0)
                            + 11;
            this.mCustomSeekBarRed.incrementProgressBy(1);
            this.mCustomSeekBarRed.setMax(11);
            this.mCustomSeekBarRed.setOnFocusChangeListener(
                    this.mCustomSeekBarRedFocusChangeListener);
            this.mCustomSeekBarRed.setOnSeekBarChangeListener(
                    this.mCustomSeekBarRedSeekBarChangeListener);
            this.mCustomSeekBarRed.setProgress(i10);
            this.mCustomSeekBarRed.setOnTouchListener(
                    new View.OnTouchListener(
                            this) { // from class:
                                    // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.9
                        public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (i) {
                                case 0:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                                    secAdaptiveDisplaySettings.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mRedLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mRedLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings2.mCustomSeekBarRed
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings2
                                                                    .mTickRedFadeOutAnimation);
                                            this.this$0.mTickRedFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings3.mCustomSeekBarRed
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings3
                                                                    .mProgressRedShrinkAnimation);
                                            this.this$0.mProgressRedShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                                    secAdaptiveDisplaySettings4
                                                            .mThumbRedFadeInAnimation);
                                            this.this$0.mThumbRedFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings5
                                                            .mProgressRedExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings5.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_expand_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings6
                                                            .mProgressRedShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings6.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_shrink_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings7.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings8.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings9.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings10 = this.this$0;
                                            secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings10.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mRedLabel.setTextColor(-2347202);
                                        this.this$0.mRedLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings11.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings11
                                                        .mThumbRedFadeOutAnimation);
                                        this.this$0.mThumbRedFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings12.mCustomSeekBarRed.setTickMark(
                                                secAdaptiveDisplaySettings12
                                                        .mTickRedFadeInAnimation);
                                        this.this$0.mTickRedFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings13.mCustomSeekBarRed
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings13
                                                                .mProgressRedExpandAnimation);
                                        this.this$0.mProgressRedExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings14.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings14.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                case 1:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings15 = this.this$0;
                                            secAdaptiveDisplaySettings15.mGreenLabel.setTextColor(
                                                    secAdaptiveDisplaySettings15.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mGreenLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mGreenLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings16 = this.this$0;
                                            secAdaptiveDisplaySettings16.mCustomSeekBarGreen
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings16
                                                                    .mTickGreenFadeOutAnimation);
                                            this.this$0.mTickGreenFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings17 = this.this$0;
                                            secAdaptiveDisplaySettings17.mCustomSeekBarGreen
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings17
                                                                    .mProgressGreenShrinkAnimation);
                                            this.this$0.mProgressGreenShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings18 = this.this$0;
                                            secAdaptiveDisplaySettings18.mCustomSeekBarGreen
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings18
                                                                    .mThumbGreenFadeInAnimation);
                                            this.this$0.mThumbGreenFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings19 = this.this$0;
                                            secAdaptiveDisplaySettings19
                                                            .mProgressGreenExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings19.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings20 = this.this$0;
                                            secAdaptiveDisplaySettings20
                                                            .mProgressGreenShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings20.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings21 = this.this$0;
                                            secAdaptiveDisplaySettings21
                                                            .mThumbGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings21.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings22 = this.this$0;
                                            secAdaptiveDisplaySettings22
                                                            .mThumbGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings22.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings23 = this.this$0;
                                            secAdaptiveDisplaySettings23.mTickGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings23.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings24 = this.this$0;
                                            secAdaptiveDisplaySettings24
                                                            .mTickGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings24.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mGreenLabel.setTextColor(-11096751);
                                        this.this$0.mGreenLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings25.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings25
                                                        .mThumbGreenFadeOutAnimation);
                                        this.this$0.mThumbGreenFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings26.mCustomSeekBarGreen
                                                .setTickMark(
                                                        secAdaptiveDisplaySettings26
                                                                .mTickGreenFadeInAnimation);
                                        this.this$0.mTickGreenFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings27.mCustomSeekBarGreen
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings27
                                                                .mProgressGreenExpandAnimation);
                                        this.this$0.mProgressGreenExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings28.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                default:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings29 = this.this$0;
                                            secAdaptiveDisplaySettings29.mBlueLabel.setTextColor(
                                                    secAdaptiveDisplaySettings29.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mBlueLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mBlueLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings30 = this.this$0;
                                            secAdaptiveDisplaySettings30.mCustomSeekBarBlue
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings30
                                                                    .mTickBlueFadeOutAnimation);
                                            this.this$0.mTickBlueFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings31 = this.this$0;
                                            secAdaptiveDisplaySettings31.mCustomSeekBarBlue
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings31
                                                                    .mProgressBlueShrinkAnimation);
                                            this.this$0.mProgressBlueShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings32 = this.this$0;
                                            secAdaptiveDisplaySettings32.mCustomSeekBarBlue
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings32
                                                                    .mThumbBlueFadeInAnimation);
                                            this.this$0.mThumbBlueFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings33 = this.this$0;
                                            secAdaptiveDisplaySettings33
                                                            .mProgressBlueExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings33.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings34 = this.this$0;
                                            secAdaptiveDisplaySettings34
                                                            .mProgressBlueShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings34.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings35 = this.this$0;
                                            secAdaptiveDisplaySettings35.mThumbBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings35.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings36 = this.this$0;
                                            secAdaptiveDisplaySettings36
                                                            .mThumbBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings36.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings37 = this.this$0;
                                            secAdaptiveDisplaySettings37.mTickBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings37.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings38 = this.this$0;
                                            secAdaptiveDisplaySettings38.mTickBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings38.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mBlueLabel.setTextColor(-12683018);
                                        this.this$0.mBlueLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings39 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings39.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings39
                                                        .mThumbBlueFadeOutAnimation);
                                        this.this$0.mThumbBlueFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings40 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings40.mCustomSeekBarBlue.setTickMark(
                                                secAdaptiveDisplaySettings40
                                                        .mTickBlueFadeInAnimation);
                                        this.this$0.mTickBlueFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings41 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings41.mCustomSeekBarBlue
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings41
                                                                .mProgressBlueExpandAnimation);
                                        this.this$0.mProgressBlueExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings42 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings42.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings42.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
        }
        this.mGreenLabel = (TextView) this.mAdaptiveDisplayView.findViewById(R.id.green_label);
        SeekBar seekBar2 =
                (SeekBar) this.mAdaptiveDisplayView.findViewById(R.id.custom_seekbar_green);
        this.mCustomSeekBarGreen = seekBar2;
        if (seekBar2 != null) {
            int i11 =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_green",
                                    0)
                            + 11;
            this.mCustomSeekBarGreen.incrementProgressBy(1);
            this.mCustomSeekBarGreen.setMax(11);
            this.mCustomSeekBarGreen.setOnFocusChangeListener(
                    this.mCustomSeekBarGreenFocusChangeListener);
            this.mCustomSeekBarGreen.setOnSeekBarChangeListener(
                    this.mCustomSeekBarGreenSeekBarChangeListener);
            this.mCustomSeekBarGreen.setProgress(i11);
            this.mCustomSeekBarGreen.setOnTouchListener(
                    new View.OnTouchListener(
                            this) { // from class:
                                    // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.9
                        public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (i3) {
                                case 0:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                                    secAdaptiveDisplaySettings.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mRedLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mRedLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings2.mCustomSeekBarRed
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings2
                                                                    .mTickRedFadeOutAnimation);
                                            this.this$0.mTickRedFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings3.mCustomSeekBarRed
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings3
                                                                    .mProgressRedShrinkAnimation);
                                            this.this$0.mProgressRedShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                                    secAdaptiveDisplaySettings4
                                                            .mThumbRedFadeInAnimation);
                                            this.this$0.mThumbRedFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings5
                                                            .mProgressRedExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings5.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_expand_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings6
                                                            .mProgressRedShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings6.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_shrink_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings7.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings8.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings9.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings10 = this.this$0;
                                            secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings10.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mRedLabel.setTextColor(-2347202);
                                        this.this$0.mRedLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings11.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings11
                                                        .mThumbRedFadeOutAnimation);
                                        this.this$0.mThumbRedFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings12.mCustomSeekBarRed.setTickMark(
                                                secAdaptiveDisplaySettings12
                                                        .mTickRedFadeInAnimation);
                                        this.this$0.mTickRedFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings13.mCustomSeekBarRed
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings13
                                                                .mProgressRedExpandAnimation);
                                        this.this$0.mProgressRedExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings14.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings14.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                case 1:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings15 = this.this$0;
                                            secAdaptiveDisplaySettings15.mGreenLabel.setTextColor(
                                                    secAdaptiveDisplaySettings15.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mGreenLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mGreenLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings16 = this.this$0;
                                            secAdaptiveDisplaySettings16.mCustomSeekBarGreen
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings16
                                                                    .mTickGreenFadeOutAnimation);
                                            this.this$0.mTickGreenFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings17 = this.this$0;
                                            secAdaptiveDisplaySettings17.mCustomSeekBarGreen
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings17
                                                                    .mProgressGreenShrinkAnimation);
                                            this.this$0.mProgressGreenShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings18 = this.this$0;
                                            secAdaptiveDisplaySettings18.mCustomSeekBarGreen
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings18
                                                                    .mThumbGreenFadeInAnimation);
                                            this.this$0.mThumbGreenFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings19 = this.this$0;
                                            secAdaptiveDisplaySettings19
                                                            .mProgressGreenExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings19.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings20 = this.this$0;
                                            secAdaptiveDisplaySettings20
                                                            .mProgressGreenShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings20.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings21 = this.this$0;
                                            secAdaptiveDisplaySettings21
                                                            .mThumbGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings21.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings22 = this.this$0;
                                            secAdaptiveDisplaySettings22
                                                            .mThumbGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings22.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings23 = this.this$0;
                                            secAdaptiveDisplaySettings23.mTickGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings23.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings24 = this.this$0;
                                            secAdaptiveDisplaySettings24
                                                            .mTickGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings24.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mGreenLabel.setTextColor(-11096751);
                                        this.this$0.mGreenLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings25.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings25
                                                        .mThumbGreenFadeOutAnimation);
                                        this.this$0.mThumbGreenFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings26.mCustomSeekBarGreen
                                                .setTickMark(
                                                        secAdaptiveDisplaySettings26
                                                                .mTickGreenFadeInAnimation);
                                        this.this$0.mTickGreenFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings27.mCustomSeekBarGreen
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings27
                                                                .mProgressGreenExpandAnimation);
                                        this.this$0.mProgressGreenExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings28.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                default:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings29 = this.this$0;
                                            secAdaptiveDisplaySettings29.mBlueLabel.setTextColor(
                                                    secAdaptiveDisplaySettings29.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mBlueLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mBlueLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings30 = this.this$0;
                                            secAdaptiveDisplaySettings30.mCustomSeekBarBlue
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings30
                                                                    .mTickBlueFadeOutAnimation);
                                            this.this$0.mTickBlueFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings31 = this.this$0;
                                            secAdaptiveDisplaySettings31.mCustomSeekBarBlue
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings31
                                                                    .mProgressBlueShrinkAnimation);
                                            this.this$0.mProgressBlueShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings32 = this.this$0;
                                            secAdaptiveDisplaySettings32.mCustomSeekBarBlue
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings32
                                                                    .mThumbBlueFadeInAnimation);
                                            this.this$0.mThumbBlueFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings33 = this.this$0;
                                            secAdaptiveDisplaySettings33
                                                            .mProgressBlueExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings33.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings34 = this.this$0;
                                            secAdaptiveDisplaySettings34
                                                            .mProgressBlueShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings34.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings35 = this.this$0;
                                            secAdaptiveDisplaySettings35.mThumbBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings35.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings36 = this.this$0;
                                            secAdaptiveDisplaySettings36
                                                            .mThumbBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings36.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings37 = this.this$0;
                                            secAdaptiveDisplaySettings37.mTickBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings37.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings38 = this.this$0;
                                            secAdaptiveDisplaySettings38.mTickBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings38.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mBlueLabel.setTextColor(-12683018);
                                        this.this$0.mBlueLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings39 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings39.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings39
                                                        .mThumbBlueFadeOutAnimation);
                                        this.this$0.mThumbBlueFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings40 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings40.mCustomSeekBarBlue.setTickMark(
                                                secAdaptiveDisplaySettings40
                                                        .mTickBlueFadeInAnimation);
                                        this.this$0.mTickBlueFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings41 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings41.mCustomSeekBarBlue
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings41
                                                                .mProgressBlueExpandAnimation);
                                        this.this$0.mProgressBlueExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings42 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings42.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings42.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
        }
        this.mBlueLabel = (TextView) this.mAdaptiveDisplayView.findViewById(R.id.blue_label);
        SeekBar seekBar3 =
                (SeekBar) this.mAdaptiveDisplayView.findViewById(R.id.custom_seekbar_blue);
        this.mCustomSeekBarBlue = seekBar3;
        if (seekBar3 != null) {
            int i12 =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_blue",
                                    0)
                            + 11;
            this.mCustomSeekBarBlue.incrementProgressBy(1);
            this.mCustomSeekBarBlue.setMax(11);
            this.mCustomSeekBarBlue.setOnFocusChangeListener(
                    this.mCustomSeekBarBlueFocusChangeListener);
            this.mCustomSeekBarBlue.setOnSeekBarChangeListener(
                    this.mCustomSeekBarBlueSeekBarChangeListener);
            this.mCustomSeekBarBlue.setProgress(i12);
            this.mCustomSeekBarBlue.setOnTouchListener(
                    new View.OnTouchListener(
                            this) { // from class:
                                    // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.9
                        public final /* synthetic */ SecAdaptiveDisplaySettings this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (i2) {
                                case 0:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings.mRedLabel.setTextColor(
                                                    secAdaptiveDisplaySettings.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mRedLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mRedLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings2 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings2.mCustomSeekBarRed
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings2
                                                                    .mTickRedFadeOutAnimation);
                                            this.this$0.mTickRedFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings3 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings3.mCustomSeekBarRed
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings3
                                                                    .mProgressRedShrinkAnimation);
                                            this.this$0.mProgressRedShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings4 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings4.mCustomSeekBarRed.setThumb(
                                                    secAdaptiveDisplaySettings4
                                                            .mThumbRedFadeInAnimation);
                                            this.this$0.mThumbRedFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings5 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings5
                                                            .mProgressRedExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings5.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_expand_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings6 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings6
                                                            .mProgressRedShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings6.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_red_shrink_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings7 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings7.mThumbRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings7.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings8 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings8.mThumbRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings8.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_red_fade_out_animation);
                                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings9 =
                                                    this.this$0;
                                            secAdaptiveDisplaySettings9.mTickRedFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings9.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings10 = this.this$0;
                                            secAdaptiveDisplaySettings10.mTickRedFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings10.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_red_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mRedLabel.setTextColor(-2347202);
                                        this.this$0.mRedLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings11 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings11.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings11
                                                        .mThumbRedFadeOutAnimation);
                                        this.this$0.mThumbRedFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings12 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings12.mCustomSeekBarRed.setTickMark(
                                                secAdaptiveDisplaySettings12
                                                        .mTickRedFadeInAnimation);
                                        this.this$0.mTickRedFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings13 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings13.mCustomSeekBarRed
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings13
                                                                .mProgressRedExpandAnimation);
                                        this.this$0.mProgressRedExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings14 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings14.mCustomSeekBarRed.setThumb(
                                                secAdaptiveDisplaySettings14.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                case 1:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings15 = this.this$0;
                                            secAdaptiveDisplaySettings15.mGreenLabel.setTextColor(
                                                    secAdaptiveDisplaySettings15.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mGreenLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mGreenLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings16 = this.this$0;
                                            secAdaptiveDisplaySettings16.mCustomSeekBarGreen
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings16
                                                                    .mTickGreenFadeOutAnimation);
                                            this.this$0.mTickGreenFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings17 = this.this$0;
                                            secAdaptiveDisplaySettings17.mCustomSeekBarGreen
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings17
                                                                    .mProgressGreenShrinkAnimation);
                                            this.this$0.mProgressGreenShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings18 = this.this$0;
                                            secAdaptiveDisplaySettings18.mCustomSeekBarGreen
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings18
                                                                    .mThumbGreenFadeInAnimation);
                                            this.this$0.mThumbGreenFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings19 = this.this$0;
                                            secAdaptiveDisplaySettings19
                                                            .mProgressGreenExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings19.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings20 = this.this$0;
                                            secAdaptiveDisplaySettings20
                                                            .mProgressGreenShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings20.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_green_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings21 = this.this$0;
                                            secAdaptiveDisplaySettings21
                                                            .mThumbGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings21.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings22 = this.this$0;
                                            secAdaptiveDisplaySettings22
                                                            .mThumbGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings22.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_green_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings23 = this.this$0;
                                            secAdaptiveDisplaySettings23.mTickGreenFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings23.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings24 = this.this$0;
                                            secAdaptiveDisplaySettings24
                                                            .mTickGreenFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings24.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_green_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mGreenLabel.setTextColor(-11096751);
                                        this.this$0.mGreenLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings25 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings25.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings25
                                                        .mThumbGreenFadeOutAnimation);
                                        this.this$0.mThumbGreenFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings26 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings26.mCustomSeekBarGreen
                                                .setTickMark(
                                                        secAdaptiveDisplaySettings26
                                                                .mTickGreenFadeInAnimation);
                                        this.this$0.mTickGreenFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings27 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings27.mCustomSeekBarGreen
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings27
                                                                .mProgressGreenExpandAnimation);
                                        this.this$0.mProgressGreenExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings28 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings28.mCustomSeekBarGreen.setThumb(
                                                secAdaptiveDisplaySettings28.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                                default:
                                    if (motionEvent.getAction() != 0) {
                                        if (motionEvent.getAction() == 1
                                                || motionEvent.getAction() == 3) {
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings29 = this.this$0;
                                            secAdaptiveDisplaySettings29.mBlueLabel.setTextColor(
                                                    secAdaptiveDisplaySettings29.mContext.getColor(
                                                            R.color
                                                                    .sec_display_color_balance_label_text_color));
                                            this.this$0.mBlueLabel.setTypeface(
                                                    Typeface.create("sec", 0));
                                            this.this$0.mBlueLabel.setBackground(null);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings30 = this.this$0;
                                            secAdaptiveDisplaySettings30.mCustomSeekBarBlue
                                                    .setTickMark(
                                                            secAdaptiveDisplaySettings30
                                                                    .mTickBlueFadeOutAnimation);
                                            this.this$0.mTickBlueFadeOutAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings31 = this.this$0;
                                            secAdaptiveDisplaySettings31.mCustomSeekBarBlue
                                                    .setProgressDrawable(
                                                            secAdaptiveDisplaySettings31
                                                                    .mProgressBlueShrinkAnimation);
                                            this.this$0.mProgressBlueShrinkAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings32 = this.this$0;
                                            secAdaptiveDisplaySettings32.mCustomSeekBarBlue
                                                    .setThumb(
                                                            secAdaptiveDisplaySettings32
                                                                    .mThumbBlueFadeInAnimation);
                                            this.this$0.mThumbBlueFadeInAnimation.start();
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings33 = this.this$0;
                                            secAdaptiveDisplaySettings33
                                                            .mProgressBlueExpandAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings33.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_expand_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings34 = this.this$0;
                                            secAdaptiveDisplaySettings34
                                                            .mProgressBlueShrinkAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings34.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_blue_shrink_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings35 = this.this$0;
                                            secAdaptiveDisplaySettings35.mThumbBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings35.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings36 = this.this$0;
                                            secAdaptiveDisplaySettings36
                                                            .mThumbBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings36.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_thumb_blue_fade_out_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings37 = this.this$0;
                                            secAdaptiveDisplaySettings37.mTickBlueFadeInAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings37.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_in_animation);
                                            SecAdaptiveDisplaySettings
                                                    secAdaptiveDisplaySettings38 = this.this$0;
                                            secAdaptiveDisplaySettings38.mTickBlueFadeOutAnimation =
                                                    (AnimationDrawable)
                                                            secAdaptiveDisplaySettings38.mContext
                                                                    .getDrawable(
                                                                            R.drawable
                                                                                    .sec_screen_mode_progress_tick_blue_fade_out_animation);
                                            break;
                                        }
                                    } else {
                                        this.this$0.mBlueLabel.setTextColor(-12683018);
                                        this.this$0.mBlueLabel.setTypeface(
                                                Typeface.create("sec", 1));
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings39 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings39.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings39
                                                        .mThumbBlueFadeOutAnimation);
                                        this.this$0.mThumbBlueFadeOutAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings40 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings40.mCustomSeekBarBlue.setTickMark(
                                                secAdaptiveDisplaySettings40
                                                        .mTickBlueFadeInAnimation);
                                        this.this$0.mTickBlueFadeInAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings41 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings41.mCustomSeekBarBlue
                                                .setProgressDrawable(
                                                        secAdaptiveDisplaySettings41
                                                                .mProgressBlueExpandAnimation);
                                        this.this$0.mProgressBlueExpandAnimation.start();
                                        SecAdaptiveDisplaySettings secAdaptiveDisplaySettings42 =
                                                this.this$0;
                                        secAdaptiveDisplaySettings42.mCustomSeekBarBlue.setThumb(
                                                secAdaptiveDisplaySettings42.mContext.getDrawable(
                                                        R.drawable
                                                                .sec_screen_mode_progress_thumb_end));
                                        break;
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
        }
        this.mEdgeColorBalance =
                (LinearLayout) this.mAdaptiveDisplayView.findViewById(R.id.edge_color_balance);
        this.mEdgeColorBalanceTitleText =
                (TextView) this.mAdaptiveDisplayView.findViewById(R.id.edge_color_balance_title);
        TextView textView =
                (TextView) this.mAdaptiveDisplayView.findViewById(R.id.edge_color_balance_sub);
        this.mEdgeColorBalanceSubText = textView;
        if (textView != null) {
            textView.setText(
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.sec_edge_color_balance_summary),
                            10));
        }
        LinearLayout linearLayout = this.mEdgeColorBalance;
        if (linearLayout != null) {
            linearLayout.setClickable(true);
            this.mEdgeColorBalance.semSetRoundedCorners(15);
            this.mEdgeColorBalance.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            this.mEdgeColorBalance.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.12
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecAdaptiveDisplaySettings secAdaptiveDisplaySettings =
                                    SecAdaptiveDisplaySettings.this;
                            if (secAdaptiveDisplaySettings.mBatteryLevel >= 30) {
                                Intent intent = new Intent();
                                intent.setAction("com.samsung.settings.EDGE_COLOR_BALANCE_SETTING");
                                secAdaptiveDisplaySettings.mContext.startActivity(intent);
                                return;
                            }
                            AlertDialog alertDialog =
                                    secAdaptiveDisplaySettings.mEdgeColorBalanceBatteryCheckDialog;
                            if (alertDialog == null || !alertDialog.isShowing()) {
                                secAdaptiveDisplaySettings.mEdgeColorBalanceBatteryCheckDialog =
                                        null;
                                AlertDialog create =
                                        new AlertDialog.Builder(secAdaptiveDisplaySettings.mContext)
                                                .setCancelable(true)
                                                .setTitle(
                                                        secAdaptiveDisplaySettings
                                                                .mContext
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .sec_edge_color_balance_not_enough_battery_title)
                                                                .toUpperCase())
                                                .setMessage(
                                                        String.format(
                                                                secAdaptiveDisplaySettings
                                                                        .mContext
                                                                        .getResources()
                                                                        .getString(
                                                                                R.string
                                                                                        .sec_edge_color_balance_not_enough_battery),
                                                                30))
                                                .setPositiveButton(
                                                        secAdaptiveDisplaySettings
                                                                .mContext
                                                                .getResources()
                                                                .getString(R.string.sec_common_ok)
                                                                .toUpperCase(),
                                                        new AnonymousClass22())
                                                .create();
                                secAdaptiveDisplaySettings.mEdgeColorBalanceBatteryCheckDialog =
                                        create;
                                create.show();
                            }
                        }
                    });
        }
        LinearLayout linearLayout2 =
                (LinearLayout) this.mAdaptiveDisplayView.findViewById(R.id.custom_color_area);
        if (linearLayout2 != null) {
            linearLayout2.semSetRoundedCorners(15);
            linearLayout2.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        if (Rune.supportVividness()) {
            LinearLayout linearLayout3 =
                    (LinearLayout) this.mAdaptiveDisplayView.findViewById(R.id.vividness_intensity);
            this.mVividnessIntensity = linearLayout3;
            linearLayout3.setVisibility(0);
            ((LinearLayout)
                            this.mAdaptiveDisplayView.findViewById(
                                    R.id.vividness_intensity_inset_category))
                    .setVisibility(0);
            SecNewModeSeekBar secNewModeSeekBar =
                    (SecNewModeSeekBar)
                            this.mAdaptiveDisplayView.findViewById(
                                    R.id.vividness_intensity_seekbar);
            this.mVividnessIntensitySeekbar = secNewModeSeekBar;
            if (secNewModeSeekBar != null) {
                secNewModeSeekBar.setMode(5);
                this.mVividnessIntensitySeekbar.setMin(0);
                this.mVividnessIntensitySeekbar.setMax(2);
                this.mVividnessIntensitySeekbar.setTickMark(
                        this.mContext.getDrawable(
                                R.drawable.sec_screenzoom_seekbar_tickmark_drawable));
                this.mVividnessIntensitySeekbar.setProgress(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(), "vividness_intensity", 0));
                this.mVividnessIntensitySeekbar.setOnSeekBarChangeListener(
                        new SeslSeekBar
                                .OnSeekBarChangeListener() { // from class:
                                                             // com.samsung.android.settings.display.SecAdaptiveDisplaySettings.8
                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onProgressChanged(
                                    SeslSeekBar seslSeekBar, int i13, boolean z2) {
                                Settings.System.putInt(
                                        SecAdaptiveDisplaySettings.this.mContext
                                                .getContentResolver(),
                                        "vividness_intensity",
                                        i13);
                                LoggingHelper.insertEventLogging(7450, 7454, i13);
                            }

                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {}

                            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {}
                        });
            }
        }
        LinearLayout linearLayout4 = this.mEdgeColorBalance;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
            View findViewById2 = this.mAdaptiveDisplayView.findViewById(R.id.sub_header);
            if (findViewById2 != null) {
                findViewById2.setVisibility(8);
            }
        }
        return this.mAdaptiveDisplayView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return NewModePreview.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7450;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mContext = getContext();
        this.mDisplayMetrics = getContext().getResources().getDisplayMetrics();
        createAdaptiveDisplayView(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.main_content));
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        updateTemperatureSettingsState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mProgressRedExpandAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_red_expand_animation);
        this.mProgressRedShrinkAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_red_shrink_animation);
        this.mThumbRedFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_red_fade_in_animation);
        this.mThumbRedFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_red_fade_out_animation);
        this.mTickRedFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_red_fade_in_animation);
        this.mTickRedFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_red_fade_out_animation);
        this.mProgressGreenExpandAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_green_expand_animation);
        this.mProgressGreenShrinkAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_green_shrink_animation);
        this.mThumbGreenFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_green_fade_in_animation);
        this.mThumbGreenFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_green_fade_out_animation);
        this.mTickGreenFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_green_fade_in_animation);
        this.mTickGreenFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_green_fade_out_animation);
        this.mProgressBlueExpandAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_blue_expand_animation);
        this.mProgressBlueShrinkAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_blue_shrink_animation);
        this.mThumbBlueFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_blue_fade_in_animation);
        this.mThumbBlueFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_blue_fade_out_animation);
        this.mTickBlueFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_blue_fade_in_animation);
        this.mTickBlueFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_tick_blue_fade_out_animation);
        Log.d("SecAdaptiveDisplaySettings", "onCreate()");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return createAdaptiveDisplayView(layoutInflater, null);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.main_content);
        if (viewGroup != null) {
            viewGroup.removeView(getView());
            View findViewById = viewGroup.findViewById(R.id.new_mode_advanced_settings_layout);
            if (findViewById == null) {
                Log.d("SecAdaptiveDisplaySettings", "onDestroyView: layout = null");
                return;
            }
            Log.d("SecAdaptiveDisplaySettings", "onDestroyView: layout = " + findViewById);
            viewGroup.removeView(findViewById);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mContext.getContentResolver().unregisterContentObserver(this.mDTRedObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mDTGreenObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mDTBlueObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mBluelightFilterObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mEadObserver);
        this.mContext.unregisterReceiver(this.mBatteryInfoReceiver);
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.d("SecAdaptiveDisplaySettings", "onResume()");
        if (Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext)) {
            finish();
        }
        updateTemperatureSettingsState();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("blue_light_filter"),
                        false,
                        this.mBluelightFilterObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("ead_enabled"), false, this.mEadObserver);
        this.mContext.registerReceiver(
                this.mBatteryInfoReceiver,
                new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        super.onResume();
    }

    public final void updateTemperatureSettingsState() {
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "greyscale_mode", 0) == 0
                        && Settings.System.getInt(
                                        this.mContext.getContentResolver(), "high_contrast", 0)
                                == 0
                        && Settings.System.getInt(
                                        this.mContext.getContentResolver(), "color_blind", 0)
                                == 0
                        && Settings.System.getInt(
                                        this.mContext.getContentResolver(), "blue_light_filter", 0)
                                == 0
                        && Settings.System.getInt(
                                        this.mContext.getContentResolver(), "ead_enabled", 0)
                                == 0;
        TextView textView = this.mRedLabel;
        if (textView != null) {
            textView.setEnabled(z);
        }
        SeekBar seekBar = this.mCustomSeekBarRed;
        if (seekBar != null) {
            seekBar.setEnabled(z);
        }
        TextView textView2 = this.mGreenLabel;
        if (textView2 != null) {
            textView2.setEnabled(z);
        }
        SeekBar seekBar2 = this.mCustomSeekBarGreen;
        if (seekBar2 != null) {
            seekBar2.setEnabled(z);
        }
        TextView textView3 = this.mBlueLabel;
        if (textView3 != null) {
            textView3.setEnabled(z);
        }
        SeekBar seekBar3 = this.mCustomSeekBarBlue;
        if (seekBar3 != null) {
            seekBar3.setEnabled(z);
        }
        LinearLayout linearLayout = this.mEdgeColorBalance;
        if (linearLayout != null) {
            linearLayout.setEnabled(z);
        }
        TextView textView4 = this.mEdgeColorBalanceTitleText;
        if (textView4 != null) {
            textView4.setEnabled(z);
        }
        TextView textView5 = this.mEdgeColorBalanceSubText;
        if (textView5 != null) {
            textView5.setEnabled(z);
        }
        LinearLayout linearLayout2 = this.mVividnessIntensity;
        if (linearLayout2 != null) {
            linearLayout2.setEnabled(z);
        }
        SecNewModeSeekBar secNewModeSeekBar = this.mVividnessIntensitySeekbar;
        if (secNewModeSeekBar != null) {
            secNewModeSeekBar.setEnabled(z);
        }
        if (!z) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mDTRedObserver);
            this.mContext.getContentResolver().unregisterContentObserver(this.mDTGreenObserver);
            this.mContext.getContentResolver().unregisterContentObserver(this.mDTBlueObserver);
        } else {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("sec_display_temperature_red"),
                            true,
                            this.mDTRedObserver);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("sec_display_temperature_green"),
                            true,
                            this.mDTGreenObserver);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("sec_display_temperature_blue"),
                            true,
                            this.mDTBlueObserver);
            this.mCustomSeekBarRed.setProgress(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_red",
                                    0)
                            + 11);
            this.mCustomSeekBarGreen.setProgress(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_green",
                                    0)
                            + 11);
            this.mCustomSeekBarBlue.setProgress(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "sec_display_temperature_blue",
                                    0)
                            + 11);
        }
    }
}
