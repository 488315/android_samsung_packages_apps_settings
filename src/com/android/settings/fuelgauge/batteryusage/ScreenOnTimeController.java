package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenOnTimeController extends BasePreferenceController {
    private static final String ROOT_PREFERENCE_KEY = "screen_on_time_category";
    private static final String SCREEN_ON_TIME_TEXT_PREFERENCE_KEY = "screen_on_time_text";
    private static final String TAG = "ScreenOnTimeController";

    @VisibleForTesting Context mPrefContext;

    @VisibleForTesting PreferenceCategory mRootPreference;

    @VisibleForTesting TextViewPreference mScreenOnTimeTextPreference;

    @VisibleForTesting String mScreenTimeCategoryLastFullChargeText;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[\\d]*[\\.,]?[\\d]+");
    private static final Locale IW_LOCALE = new Locale("iw");

    public ScreenOnTimeController(Context context) {
        super(context, ROOT_PREFERENCE_KEY);
    }

    @VisibleForTesting
    public static CharSequence enlargeFontOfNumberIfNeeded(
            Context context, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return ApnSettings.MVNO_NONE;
        }
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        if (locale != null && IW_LOCALE.getLanguage().equals(locale.getLanguage())) {
            return charSequence;
        }
        SpannableString spannableString = new SpannableString(charSequence);
        Matcher matcher = NUMBER_PATTERN.matcher(charSequence);
        while (matcher.find()) {
            spannableString.setSpan(
                    new AbsoluteSizeSpan(36, true), matcher.start(), matcher.end(), 33);
        }
        return spannableString;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPrefContext = preferenceScreen.getContext();
        this.mRootPreference =
                (PreferenceCategory) preferenceScreen.findPreference(ROOT_PREFERENCE_KEY);
        this.mScreenOnTimeTextPreference =
                (TextViewPreference)
                        preferenceScreen.findPreference(SCREEN_ON_TIME_TEXT_PREFERENCE_KEY);
        this.mScreenTimeCategoryLastFullChargeText =
                this.mPrefContext.getString(R.string.screen_time_category_last_full_charge);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public void handleScreenOnTimeUpdated(Long l, String str, String str2) {
        if (l == null) {
            this.mRootPreference.setVisible(false);
            this.mScreenOnTimeTextPreference.setVisible(false);
        } else {
            showCategoryTitle(str, str2);
            showScreenOnTimeText(l);
        }
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

    @VisibleForTesting
    public void showCategoryTitle(String str, String str2) {
        this.mRootPreference.setTitle(
                Utils.createAccessibleSequence(
                        str2 == null
                                ? this.mScreenTimeCategoryLastFullChargeText
                                : this.mPrefContext.getString(
                                        R.string.screen_time_category_for_slot, str2),
                        str == null
                                ? this.mScreenTimeCategoryLastFullChargeText
                                : this.mPrefContext.getString(
                                        R.string.screen_time_category_for_slot, str)));
        this.mRootPreference.setVisible(true);
    }

    @VisibleForTesting
    public void showScreenOnTimeText(Long l) {
        String replaceAll =
                ((SpannableStringBuilder)
                                StringUtil.formatElapsedTime(
                                        this.mPrefContext, l.longValue(), false, false))
                        .toString()
                        .replaceAll(",", ApnSettings.MVNO_NONE);
        TextViewPreference textViewPreference = this.mScreenOnTimeTextPreference;
        textViewPreference.mText = enlargeFontOfNumberIfNeeded(this.mPrefContext, replaceAll);
        textViewPreference.notifyChanged();
        this.mScreenOnTimeTextPreference.setVisible(true);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
