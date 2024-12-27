package com.android.settings.slices;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.SubSettings;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SliderPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SliceBuilderUtils {
    public static Slice buildIntentSlice(
            Context context,
            BasePreferenceController basePreferenceController,
            SliceData sliceData) {
        PendingIntent activity =
                PendingIntent.getActivity(
                        context, 0, getContentIntent(context, sliceData), 67108864);
        IconCompat safeIcon = getSafeIcon(context, sliceData);
        CharSequence subtitleText = getSubtitleText(context, basePreferenceController, sliceData);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, R.attr.colorAccent);
        Set buildSliceKeywords = buildSliceKeywords(sliceData);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        String str = sliceData.mTitle;
        rowBuilder.mTitle = str;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mPrimaryAction = SliceAction.createDeeplink(activity, safeIcon, 0, str);
        if (!com.android.settings.Utils.isSettingsIntelligence(context)) {
            rowBuilder.setSubtitle(subtitleText);
        }
        ListBuilder listBuilder = new ListBuilder(context, sliceData.mUri);
        listBuilder.mImpl.setColor(colorAttrDefaultColor);
        listBuilder.mImpl.addRow(rowBuilder);
        listBuilder.mImpl.setKeywords(buildSliceKeywords);
        return listBuilder.build();
    }

    public static Intent buildSearchResultPageIntent(
            int i, int i2, Context context, String str, String str2, String str3) {
        String str4;
        Bundle bundle = new Bundle();
        if (i2 != 0) {
            str4 = context.getString(i2);
            if (TextUtils.isEmpty(str4)) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "Invalid menu key res from: ", str3, "SliceBuilder");
            }
        } else {
            str4 = null;
        }
        bundle.putString(":settings:fragment_args_key", str2);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = bundle;
        launchRequest.mTitle = str3;
        launchRequest.mSourceMetricsCategory = i;
        Intent intent = subSettingLauncher.toIntent();
        intent.putExtra(":settings:fragment_args_key", str2)
                .putExtra("is_from_slice", true)
                .putExtra(
                        "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                        str4)
                .setAction("com.android.settings.SEARCH_RESULT_TRAMPOLINE")
                .setComponent(null);
        intent.addFlags(335544320);
        return intent;
    }

    public static Slice buildSlice(Context context, SliceData sliceData) {
        BasePreferenceController createInstance;
        StringBuilder sb = new StringBuilder("Creating slice for: ");
        String str = sliceData.mPreferenceController;
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, str, "SliceBuilder");
        try {
            createInstance = BasePreferenceController.createInstance(context, str);
        } catch (IllegalStateException unused) {
            createInstance = BasePreferenceController.createInstance(context, str, sliceData.mKey);
        }
        if (!createInstance.isAvailable()) {
            return null;
        }
        int availabilityStatus = createInstance.getAvailabilityStatus();
        String str2 = sliceData.mTitle;
        if (availabilityStatus == 5) {
            Set buildSliceKeywords = buildSliceKeywords(sliceData);
            int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, R.attr.colorAccent);
            CharSequence charSequence = sliceData.mUnavailableSliceSubtitle;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence =
                        context.getText(
                                com.android.settings.R.string.disabled_dependent_setting_summary);
            }
            IconCompat safeIcon = getSafeIcon(context, sliceData);
            SliceAction createDeeplink =
                    SliceAction.createDeeplink(
                            PendingIntent.getActivity(
                                    context, 0, getContentIntent(context, sliceData), 67108864),
                            safeIcon,
                            0,
                            str2);
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
            rowBuilder.mTitle = str2;
            rowBuilder.mTitleLoading = false;
            rowBuilder.setTitleItem(safeIcon);
            rowBuilder.mPrimaryAction = createDeeplink;
            if (!com.android.settings.Utils.isSettingsIntelligence(context)) {
                rowBuilder.setSubtitle(charSequence);
            }
            ListBuilder listBuilder = new ListBuilder(context, sliceData.mUri);
            listBuilder.mImpl.setColor(colorAttrDefaultColor);
            listBuilder.mImpl.addRow(rowBuilder);
            listBuilder.mImpl.setKeywords(buildSliceKeywords);
            return listBuilder.build();
        }
        String str3 = sliceData.mUserRestriction;
        if (!TextUtils.isEmpty(str3)
                && RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                context, UserHandle.myUserId(), str3)
                        != null) {
            return buildIntentSlice(context, createInstance, sliceData);
        }
        int i = sliceData.mSliceType;
        if (i == 0) {
            return buildIntentSlice(context, createInstance, sliceData);
        }
        if (i == 1) {
            PendingIntent activity =
                    PendingIntent.getActivity(
                            context, 0, getContentIntent(context, sliceData), 67108864);
            IconCompat safeIcon2 = getSafeIcon(context, sliceData);
            CharSequence subtitleText = getSubtitleText(context, createInstance, sliceData);
            int colorAttrDefaultColor2 =
                    Utils.getColorAttrDefaultColor(context, R.attr.colorAccent);
            SliceAction sliceAction =
                    new SliceAction(
                            getActionIntent(
                                    context,
                                    "com.android.settings.slice.action.TOGGLE_CHANGED",
                                    sliceData),
                            null,
                            ((TogglePreferenceController) createInstance).getThreadEnabled());
            Set buildSliceKeywords2 = buildSliceKeywords(sliceData);
            ListBuilder.RowBuilder rowBuilder2 = new ListBuilder.RowBuilder();
            rowBuilder2.mTitle = str2;
            rowBuilder2.mTitleLoading = false;
            rowBuilder2.mPrimaryAction = SliceAction.createDeeplink(activity, safeIcon2, 0, str2);
            rowBuilder2.addEndItem(sliceAction);
            if (!com.android.settings.Utils.isSettingsIntelligence(context)) {
                rowBuilder2.setSubtitle(subtitleText);
            }
            ListBuilder listBuilder2 = new ListBuilder(context, sliceData.mUri);
            listBuilder2.mImpl.setColor(colorAttrDefaultColor2);
            listBuilder2.mImpl.addRow(rowBuilder2);
            listBuilder2.mImpl.setKeywords(buildSliceKeywords2);
            return listBuilder2.build();
        }
        if (i != 2) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "Slice type passed was invalid: "));
        }
        SliderPreferenceController sliderPreferenceController =
                (SliderPreferenceController) createInstance;
        if (sliderPreferenceController.getMax() <= sliderPreferenceController.getMin()) {
            Log.e(
                    "SliceBuilder",
                    "Invalid sliderController: " + sliderPreferenceController.getPreferenceKey());
            return null;
        }
        PendingIntent actionIntent =
                getActionIntent(
                        context, "com.android.settings.slice.action.SLIDER_CHANGED", sliceData);
        PendingIntent activity2 =
                PendingIntent.getActivity(
                        context, 0, getContentIntent(context, sliceData), 67108864);
        IconCompat safeIcon3 = getSafeIcon(context, sliceData);
        int colorAttrDefaultColor3 = Utils.getColorAttrDefaultColor(context, R.attr.colorAccent);
        CharSequence subtitleText2 = getSubtitleText(context, createInstance, sliceData);
        SliceAction createDeeplink2 = SliceAction.createDeeplink(activity2, safeIcon3, 0, str2);
        Set buildSliceKeywords3 = buildSliceKeywords(sliceData);
        int sliderPosition = sliderPreferenceController.getSliderPosition();
        if (sliderPosition < sliderPreferenceController.getMin()) {
            sliderPosition = sliderPreferenceController.getMin();
        }
        if (sliderPosition > sliderPreferenceController.getMax()) {
            sliderPosition = sliderPreferenceController.getMax();
        }
        ListBuilder.InputRangeBuilder inputRangeBuilder = new ListBuilder.InputRangeBuilder();
        inputRangeBuilder.mTitle = str2;
        inputRangeBuilder.mPrimaryAction = createDeeplink2;
        inputRangeBuilder.mMax = sliderPreferenceController.getMax();
        inputRangeBuilder.mMin = sliderPreferenceController.getMin();
        inputRangeBuilder.mValueSet = true;
        inputRangeBuilder.mValue = sliderPosition;
        inputRangeBuilder.mInputAction = actionIntent;
        if (sliceData.mIconResource != 0) {
            inputRangeBuilder.mTitleIcon = safeIcon3;
            colorAttrDefaultColor3 = -1;
        }
        if (!com.android.settings.Utils.isSettingsIntelligence(context)) {
            inputRangeBuilder.mSubtitle = subtitleText2;
        }
        SliceAction sliceEndItem = sliderPreferenceController.getSliceEndItem(context);
        if (sliceEndItem != null) {
            if (inputRangeBuilder.mHasDefaultToggle) {
                throw new IllegalStateException(
                        "Only one non-custom toggle can be added in a single row. If you would like"
                            + " to include multiple toggles in a row, set a custom icon for each"
                            + " toggle.");
            }
            ((ArrayList) inputRangeBuilder.mEndItems).add(sliceEndItem);
            ((ArrayList) inputRangeBuilder.mEndTypes).add(2);
            ((ArrayList) inputRangeBuilder.mEndLoads).add(Boolean.FALSE);
            inputRangeBuilder.mHasDefaultToggle = sliceEndItem.mSliceAction.isDefaultToggle();
        }
        ListBuilder listBuilder3 = new ListBuilder(context, sliceData.mUri);
        listBuilder3.mImpl.setColor(colorAttrDefaultColor3);
        listBuilder3.mImpl.addInputRange(inputRangeBuilder);
        listBuilder3.mImpl.setKeywords(buildSliceKeywords3);
        return listBuilder3.build();
    }

    public static Set buildSliceKeywords(SliceData sliceData) {
        ArraySet arraySet = new ArraySet();
        String str = sliceData.mTitle;
        arraySet.add(str);
        if (!TextUtils.isEmpty(sliceData.mScreenTitle)
                && !TextUtils.equals(str, sliceData.mScreenTitle)) {
            arraySet.add(sliceData.mScreenTitle.toString());
        }
        String str2 = sliceData.mKeywords;
        if (str2 != null) {
            arraySet.addAll(
                    (List)
                            Arrays.stream(str2.split(","))
                                    .map(new SliceBuilderUtils$$ExternalSyntheticLambda0())
                                    .collect(Collectors.toList()));
        }
        return arraySet;
    }

    public static PendingIntent getActionIntent(Context context, String str, SliceData sliceData) {
        return PendingIntent.getBroadcast(
                context,
                0,
                new Intent(str)
                        .setData(sliceData.mUri)
                        .setClass(context, SliceBroadcastReceiver.class)
                        .putExtra("com.android.settings.slice.extra.key", sliceData.mKey),
                167772160);
    }

    public static Intent getContentIntent(Context context, SliceData sliceData) {
        Uri build = new Uri.Builder().appendPath(sliceData.mKey).build();
        Intent buildSearchResultPageIntent =
                buildSearchResultPageIntent(
                        0,
                        sliceData.mHighlightMenuRes,
                        context,
                        sliceData.mFragmentClassName,
                        sliceData.mKey,
                        TextUtils.isEmpty(sliceData.mScreenTitle)
                                ? null
                                : sliceData.mScreenTitle.toString());
        buildSearchResultPageIntent.setClassName(
                context.getPackageName(), SubSettings.class.getName());
        buildSearchResultPageIntent.setData(build);
        return buildSearchResultPageIntent;
    }

    public static Pair getPathData(Uri uri) {
        String[] split = uri.getPath().split("/", 3);
        if (split.length != 3) {
            return null;
        }
        return new Pair(Boolean.valueOf(TextUtils.equals("intent", split[1])), split[2]);
    }

    public static IconCompat getSafeIcon(Context context, SliceData sliceData) {
        int i = sliceData.mIconResource;
        if (i == 0) {
            i = com.android.settings.R.drawable.ic_settings_accent;
        }
        try {
            return IconCompat.createWithResource(context, i);
        } catch (Exception e) {
            Log.w(
                    "SliceBuilder",
                    "Falling back to settings icon because there is an error getting slice icon "
                            + sliceData.mUri,
                    e);
            return IconCompat.createWithResource(
                    context, com.android.settings.R.drawable.ic_settings_accent);
        }
    }

    public static CharSequence getSubtitleText(
            Context context,
            BasePreferenceController basePreferenceController,
            SliceData sliceData) {
        if (basePreferenceController.useDynamicSliceSummary()) {
            return basePreferenceController.getSummary();
        }
        String str = sliceData.mSummary;
        if (isValidSummary(context, str)) {
            return str;
        }
        CharSequence charSequence = sliceData.mScreenTitle;
        return (!isValidSummary(context, charSequence)
                        || TextUtils.equals(charSequence, sliceData.mTitle))
                ? ApnSettings.MVNO_NONE
                : charSequence;
    }

    public static boolean isValidSummary(Context context, CharSequence charSequence) {
        if (charSequence == null || TextUtils.isEmpty(charSequence.toString().trim())) {
            return false;
        }
        return (TextUtils.equals(
                                charSequence,
                                context.getText(com.android.settings.R.string.summary_placeholder))
                        || TextUtils.equals(
                                charSequence,
                                context.getText(
                                        com.android.settings.R.string
                                                .summary_two_lines_placeholder)))
                ? false
                : true;
    }
}
