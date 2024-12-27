package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.MessageFormat;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import android.speech.tts.Voice;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.text.BidiFormatter;
import androidx.core.text.TextDirectionHeuristicsCompat;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonGestureUtil;
import com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonMode;
import com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityDialogUtils {
    public static AlertDialog mAlertDialog;
    public static CharSequence mFeatureName;
    public static String mShortcutKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TTSLanguageDialogInfo {
        public TextToSpeech textToSpeech;
        public boolean shouldShowDialog = false;
        public String currentTTSLocale = null;

        public TTSLanguageDialogInfo(
                final Context context, AccessibilityServiceInfo accessibilityServiceInfo) {
            if ((accessibilityServiceInfo.feedbackType & 1) == 1) {
                this.textToSpeech =
                        new TextToSpeech(
                                context.getApplicationContext(),
                                new TextToSpeech
                                        .OnInitListener() { // from class:
                                                            // com.android.settings.accessibility.AccessibilityDialogUtils$TTSLanguageDialogInfo$$ExternalSyntheticLambda0
                                    @Override // android.speech.tts.TextToSpeech.OnInitListener
                                    public final void onInit(int i) {
                                        Locale locale;
                                        AccessibilityDialogUtils.TTSLanguageDialogInfo
                                                tTSLanguageDialogInfo =
                                                        AccessibilityDialogUtils
                                                                .TTSLanguageDialogInfo.this;
                                        Context context2 = context;
                                        tTSLanguageDialogInfo.getClass();
                                        Log.d(
                                                "AccessibilityDialogUtils",
                                                "createCheckTTSLanguageDialog - onInit : " + i);
                                        TextToSpeech textToSpeech =
                                                tTSLanguageDialogInfo.textToSpeech;
                                        if (textToSpeech != null) {
                                            Voice defaultVoice = textToSpeech.getDefaultVoice();
                                            boolean z = true;
                                            if (defaultVoice == null) {
                                                Log.d(
                                                        "AccessibilityDialogUtils",
                                                        "tts default voice is null");
                                            } else {
                                                Log.d(
                                                        "AccessibilityDialogUtils",
                                                        "voice: " + defaultVoice.getName());
                                                Locale locale2 = defaultVoice.getLocale();
                                                Locale locale3 =
                                                        context2.getResources()
                                                                .getConfiguration()
                                                                .getLocales()
                                                                .get(0);
                                                if (textToSpeech.isLanguageAvailable(locale3) >= 0
                                                        && locale2.getISO3Country()
                                                                .equals(locale3.getISO3Country())
                                                        && locale2.getISO3Language()
                                                                .equals(
                                                                        locale3
                                                                                .getISO3Language())) {
                                                    z = false;
                                                }
                                            }
                                            tTSLanguageDialogInfo.shouldShowDialog = z;
                                            TextToSpeech textToSpeech2 =
                                                    tTSLanguageDialogInfo.textToSpeech;
                                            TtsEngines ttsEngines =
                                                    new TtsEngines(
                                                            context2.getApplicationContext());
                                            Voice defaultVoice2 = textToSpeech2.getDefaultVoice();
                                            if (defaultVoice2 != null) {
                                                locale = defaultVoice2.getLocale();
                                            } else {
                                                locale = Locale.US;
                                                ttsEngines.updateLocalePrefForEngine(
                                                        textToSpeech2.getDefaultEngine(), locale);
                                            }
                                            tTSLanguageDialogInfo.currentTTSLocale =
                                                    TtsEngines.normalizeTTSLocale(
                                                                    new Locale(
                                                                            locale
                                                                                    .getISO3Language(),
                                                                            locale
                                                                                    .getISO3Country()))
                                                            .getDisplayName();
                                            tTSLanguageDialogInfo.textToSpeech.stop();
                                            tTSLanguageDialogInfo.textToSpeech.shutdown();
                                            tTSLanguageDialogInfo.textToSpeech = null;
                                        }
                                    }
                                });
            }
        }
    }

    public static AlertDialog createCustomDialog(
            Context context,
            CharSequence charSequence,
            View view,
            CharSequence charSequence2,
            DialogInterface.OnClickListener onClickListener,
            CharSequence charSequence3,
            MagnificationModePreferenceController$$ExternalSyntheticLambda1
                    magnificationModePreferenceController$$ExternalSyntheticLambda1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = charSequence;
        alertParams.mCancelable = true;
        builder.setPositiveButton(charSequence2, onClickListener);
        builder.setNegativeButton(
                charSequence3, magnificationModePreferenceController$$ExternalSyntheticLambda1);
        AlertDialog create = builder.create();
        if ((view instanceof ScrollView) || (view instanceof AbsListView)) {
            view.setScrollIndicators(3, 3);
        }
        return create;
    }

    public static AlertDialog createDialog(
            int i,
            Context context,
            DialogInterface.OnClickListener onClickListener,
            CharSequence charSequence) {
        View inflate;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService("layout_inflater");
        if (i == 0) {
            inflate =
                    layoutInflater.inflate(R.layout.accessibility_edit_shortcut, (ViewGroup) null);
            initSoftwareShortcut(context, inflate);
            initHardwareShortcut(context, inflate);
        } else if (i == 1) {
            inflate =
                    layoutInflater.inflate(R.layout.accessibility_edit_shortcut, (ViewGroup) null);
            initSoftwareShortcutForSUW(context, inflate);
            initHardwareShortcut(context, inflate);
        } else if (i == 2) {
            inflate =
                    layoutInflater.inflate(
                            R.layout.accessibility_edit_shortcut_magnification, (ViewGroup) null);
            initSoftwareShortcut(context, inflate);
            initHardwareShortcut(context, inflate);
            initMagnifyShortcut(context, inflate);
            final LinearLayout linearLayout =
                    (LinearLayout) inflate.findViewById(R.id.advanced_shortcut);
            final View findViewById = inflate.findViewById(R.id.triple_tap_shortcut);
            linearLayout.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda11
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LinearLayout linearLayout2 = linearLayout;
                            View view2 = findViewById;
                            linearLayout2.setVisibility(8);
                            view2.setVisibility(0);
                        }
                    });
        } else if (i == 3) {
            inflate =
                    layoutInflater.inflate(
                            R.layout.accessibility_edit_shortcut_magnification, (ViewGroup) null);
            initSoftwareShortcutForSUW(context, inflate);
            initHardwareShortcut(context, inflate);
            initMagnifyShortcut(context, inflate);
            final LinearLayout linearLayout2 =
                    (LinearLayout) inflate.findViewById(R.id.advanced_shortcut);
            final View findViewById2 = inflate.findViewById(R.id.triple_tap_shortcut);
            linearLayout2.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda11
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LinearLayout linearLayout22 = linearLayout2;
                            View view2 = findViewById2;
                            linearLayout22.setVisibility(8);
                            view2.setVisibility(0);
                        }
                    });
        } else if (i == 90) {
            inflate =
                    layoutInflater.inflate(
                            R.layout.sec_accessibility_edit_shortcut, (ViewGroup) null);
            initQuickSettingsShortcutForSec(context, inflate);
            initSoftwareShortcutForSec(context, inflate);
            View findViewById3 = inflate.findViewById(R.id.direct_shortcut);
            ((CheckBox) findViewById3.findViewById(R.id.checkbox))
                    .setText(context.getText(R.string.accessibility_shortcut_press_side_volume_up));
            View findViewById4 = inflate.findViewById(R.id.hardware_shortcut);
            ((CheckBox) findViewById4.findViewById(R.id.checkbox))
                    .setText(
                            context.getText(
                                    R.string.accessibility_shortcut_press_hold_volume_up_down));
        } else {
            if (i != 91) {
                throw new IllegalArgumentException();
            }
            inflate =
                    layoutInflater.inflate(
                            R.layout.sec_accessibility_edit_shortcut, (ViewGroup) null);
            initSoftwareShortcutForSec(context, inflate);
            View findViewById5 = inflate.findViewById(R.id.direct_shortcut);
            ((CheckBox) findViewById5.findViewById(R.id.checkbox))
                    .setText(context.getText(R.string.accessibility_shortcut_press_side_volume_up));
            View findViewById6 = inflate.findViewById(R.id.hardware_shortcut);
            ((CheckBox) findViewById6.findViewById(R.id.checkbox))
                    .setText(
                            context.getText(
                                    R.string.accessibility_shortcut_press_hold_volume_up_down));
            View findViewById7 = inflate.findViewById(R.id.triple_tap_shortcut);
            View findViewById8 = inflate.findViewById(R.id.hardware_divider);
            findViewById7.setVisibility(0);
            findViewById8.setVisibility(0);
            ((CheckBox) findViewById7.findViewById(R.id.checkbox))
                    .setText(context.getText(R.string.accessibility_shortcut_triple_tap));
        }
        builder.setView(inflate);
        builder.P.mTitle = charSequence;
        builder.setPositiveButton(R.string.sec_common_ok, onClickListener);
        builder.setNegativeButton(
                R.string.cancel, new AccessibilityDialogUtils$$ExternalSyntheticLambda3());
        return builder.create();
    }

    public static AlertDialog createExclusiveDialog(
            final Context context,
            String str,
            DialogInterface.OnClickListener onClickListener,
            AccessibilityDialogActivity$$ExternalSyntheticLambda3
                    accessibilityDialogActivity$$ExternalSyntheticLambda3) {
        int i;
        List exclusiveTaskInfoList =
                AccessibilityExclusiveUtil.getExclusiveTaskInfoList(context, str);
        String str2 = null;
        if (exclusiveTaskInfoList.isEmpty()) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "wrong taskName entered : ", str));
        }
        CharSequence taskTitle =
                ((ExclusiveTaskInfo)
                                AccessibilityExclusiveUtil.INFO_SET.stream()
                                        .filter(
                                                new AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(
                                                        str, 2))
                                        .findAny()
                                        .orElseThrow())
                        .getTaskTitle(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        int size = exclusiveTaskInfoList.size();
        AlertController.AlertParams alertParams = builder.P;
        if (size == 1) {
            alertParams.mTitle =
                    context.getString(
                            R.string.single_exclusive_feature_title,
                            ((ExclusiveTaskInfo) exclusiveTaskInfoList.get(0))
                                    .getTaskTitle(context));
            alertParams.mMessage =
                    context.getString(
                            R.string.single_exclusive_feature_message,
                            taskTitle,
                            ((ExclusiveTaskInfo) exclusiveTaskInfoList.get(0))
                                    .getTaskTitle(context));
            i = 9110;
        } else {
            alertParams.mTitle = context.getString(R.string.multi_exclusive_feature_title);
            View inflate =
                    LayoutInflater.from(context)
                            .inflate(R.layout.accessibility_exclusive_popup, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.dialog_desc_string);
            TextView textView2 =
                    (TextView) inflate.findViewById(R.id.dialog_desc_accessibility_sub_title);
            TextView textView3 =
                    (TextView) inflate.findViewById(R.id.dialog_desc_accessibility_list);
            TextView textView4 = (TextView) inflate.findViewById(R.id.dialog_desc_others_sub_title);
            TextView textView5 = (TextView) inflate.findViewById(R.id.dialog_desc_others_list);
            final int i2 = 0;
            List list =
                    exclusiveTaskInfoList.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda7
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            ExclusiveTaskInfo exclusiveTaskInfo =
                                                    (ExclusiveTaskInfo) obj;
                                            switch (i2) {
                                                case 0:
                                                    return exclusiveTaskInfo.isAccessibilityTask();
                                                default:
                                                    return !exclusiveTaskInfo.isAccessibilityTask();
                                            }
                                        }
                                    })
                            .toList();
            if (list.isEmpty()) {
                textView2.setVisibility(8);
                textView3.setVisibility(8);
            } else {
                final int i3 = 0;
                textView3.setText(
                        (CharSequence)
                                list.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda8
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        int i4 = i3;
                                                        Context context2 = context;
                                                        ExclusiveTaskInfo exclusiveTaskInfo =
                                                                (ExclusiveTaskInfo) obj;
                                                        switch (i4) {
                                                            case 0:
                                                                return "• "
                                                                        + ((Object)
                                                                                exclusiveTaskInfo
                                                                                        .getTaskTitle(
                                                                                                context2));
                                                            default:
                                                                return "• "
                                                                        + ((Object)
                                                                                exclusiveTaskInfo
                                                                                        .getTaskTitle(
                                                                                                context2));
                                                        }
                                                    }
                                                })
                                        .collect(Collectors.joining("\n")));
            }
            final int i4 = 1;
            List list2 =
                    exclusiveTaskInfoList.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda7
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            ExclusiveTaskInfo exclusiveTaskInfo =
                                                    (ExclusiveTaskInfo) obj;
                                            switch (i4) {
                                                case 0:
                                                    return exclusiveTaskInfo.isAccessibilityTask();
                                                default:
                                                    return !exclusiveTaskInfo.isAccessibilityTask();
                                            }
                                        }
                                    })
                            .toList();
            if (list2.isEmpty()) {
                textView4.setVisibility(8);
                textView5.setVisibility(8);
            } else {
                final int i5 = 1;
                textView5.setText(
                        (CharSequence)
                                list2.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda8
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        int i42 = i5;
                                                        Context context2 = context;
                                                        ExclusiveTaskInfo exclusiveTaskInfo =
                                                                (ExclusiveTaskInfo) obj;
                                                        switch (i42) {
                                                            case 0:
                                                                return "• "
                                                                        + ((Object)
                                                                                exclusiveTaskInfo
                                                                                        .getTaskTitle(
                                                                                                context2));
                                                            default:
                                                                return "• "
                                                                        + ((Object)
                                                                                exclusiveTaskInfo
                                                                                        .getTaskTitle(
                                                                                                context2));
                                                        }
                                                    }
                                                })
                                        .collect(Collectors.joining("\n")));
            }
            String string = context.getString(R.string.multi_exclusive_feature_message, taskTitle);
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal
                    textDirectionHeuristicInternal =
                            Utils.isRTL(context)
                                    ? TextDirectionHeuristicsCompat.RTL
                                    : TextDirectionHeuristicsCompat.LTR;
            if (string == null) {
                bidiFormatter.getClass();
            } else {
                str2 =
                        ((SpannableStringBuilder)
                                        bidiFormatter.unicodeWrap(
                                                string, textDirectionHeuristicInternal))
                                .toString();
            }
            textView.setText(str2);
            builder.setView(inflate);
            i = 9120;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().visible(context, 0, i, 0);
        builder.setPositiveButton(
                R.string.accessibility_turn_off,
                new AccessibilityDialogUtils$$ExternalSyntheticLambda1(0, onClickListener));
        builder.setNegativeButton(
                R.string.cancel,
                new AccessibilityDialogUtils$$ExternalSyntheticLambda1(
                        accessibilityDialogActivity$$ExternalSyntheticLambda3));
        return builder.create();
    }

    public static AlertDialog createTTSLanguageCheckDialog(
            Context context,
            TTSLanguageDialogInfo tTSLanguageDialogInfo,
            DialogInterface.OnClickListener onClickListener) {
        String spannableStringBuilder;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().visible(context, 0, 9130, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String string = context.getString(R.string.tts_warning_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        String string2 =
                context.getString(R.string.tts_warning, tTSLanguageDialogInfo.currentTTSLocale);
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal
                textDirectionHeuristicInternal =
                        Utils.isRTL(context)
                                ? TextDirectionHeuristicsCompat.RTL
                                : TextDirectionHeuristicsCompat.LTR;
        if (string2 == null) {
            bidiFormatter.getClass();
            spannableStringBuilder = null;
        } else {
            spannableStringBuilder =
                    ((SpannableStringBuilder)
                                    bidiFormatter.unicodeWrap(
                                            string2, textDirectionHeuristicInternal))
                            .toString();
        }
        alertParams.mMessage = spannableStringBuilder;
        alertParams.mCancelable = true;
        builder.setNegativeButton(
                R.string.service_manage,
                new AccessibilityDialogUtils$$ExternalSyntheticLambda1(3, context));
        builder.setNeutralButton(android.R.string.cancel, null);
        builder.setPositiveButton(
                R.string.common_ok,
                new AccessibilityDialogUtils$$ExternalSyntheticLambda1(2, onClickListener));
        return builder.create();
    }

    public static CharSequence getCustomizeAccessibilityButtonLink(Context context) {
        return AnnotationSpan.linkify(
                context.getText(
                        R.string.accessibility_shortcut_edit_dialog_summary_software_floating),
                new AnnotationSpan.LinkInfo(
                        new AccessibilityDialogUtils$$ExternalSyntheticLambda0(context, 1)));
    }

    public static SpannableString getSummaryStringWithIcon(Context context, int i) {
        String string =
                context.getString(R.string.accessibility_shortcut_edit_dialog_summary_software);
        SpannableString valueOf = SpannableString.valueOf(string);
        int indexOf = string.indexOf("%s");
        Drawable drawable = context.getDrawable(R.drawable.ic_accessibility_new);
        ImageSpan imageSpan = new ImageSpan(drawable);
        imageSpan.setContentDescription(ApnSettings.MVNO_NONE);
        drawable.setBounds(0, 0, i, i);
        valueOf.setSpan(imageSpan, indexOf, indexOf + 2, 33);
        return valueOf;
    }

    public static void initHardwareShortcut(Context context, View view) {
        View findViewById = view.findViewById(R.id.hardware_shortcut);
        setupShortcutWidgetWithTitleAndSummary(
                findViewById,
                context.getText(R.string.accessibility_shortcut_edit_dialog_title_hardware),
                context.getText(R.string.accessibility_shortcut_edit_dialog_summary_hardware));
        ((ImageView) findViewById.findViewById(R.id.image))
                .setImageResource(R.drawable.a11y_shortcut_type_hardware);
    }

    public static void initMagnifyShortcut(Context context, View view) {
        View findViewById = view.findViewById(R.id.triple_tap_shortcut);
        setupShortcutWidgetWithTitleAndSummary(
                findViewById,
                context.getText(R.string.accessibility_shortcut_edit_dialog_title_triple_tap),
                MessageFormat.format(
                        context.getString(
                                R.string.accessibility_shortcut_edit_dialog_summary_triple_tap),
                        3));
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) findViewById.findViewById(R.id.image);
        lottieAnimationView.failureListener =
                new LottieListener() { // from class:
                                       // com.android.settings.accessibility.AccessibilityDialogUtils$$ExternalSyntheticLambda6
                    public final /* synthetic */ int f$0 = R.raw.a11y_shortcut_type_triple_tap;

                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        Log.w(
                                "AccessibilityDialogUtils",
                                "Invalid image raw resource id: " + this.f$0,
                                (Throwable) obj);
                    }
                };
        lottieAnimationView.setAnimation(R.raw.a11y_shortcut_type_triple_tap);
        lottieAnimationView.setRepeatCount(-1);
        LottieColorUtils.applyDynamicColors(context, lottieAnimationView);
        lottieAnimationView.playAnimation$1();
    }

    public static void initQuickSettingsShortcutForSec(Context context, View view) {
        View findViewById = view.findViewById(R.id.quick_settings_shortcut);
        View findViewById2 = view.findViewById(R.id.quick_settings_divider);
        CharSequence text =
                context.getText(R.string.accessibility_shortcut_edit_dialog_title_quick_settings);
        if (SecAccessibilityUtils.isSupportQuickSettings(mShortcutKey)) {
            findViewById.setVisibility(0);
            findViewById2.setVisibility(0);
            ((CheckBox) findViewById.findViewById(R.id.checkbox)).setText(text);
        }
    }

    public static void initSoftwareShortcut(Context context, View view) {
        View findViewById = view.findViewById(R.id.software_shortcut);
        int lineHeight = ((TextView) findViewById.findViewById(R.id.summary)).getLineHeight();
        boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(context);
        int i = R.string.accessibility_shortcut_edit_dialog_title_software;
        if (!isFloatingMenuEnabled && AccessibilityUtil.isGestureNavigateEnabled(context)) {
            i = R.string.accessibility_shortcut_edit_dialog_title_software_by_gesture;
        }
        CharSequence text = context.getText(i);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (AccessibilityUtil.isFloatingMenuEnabled(context)) {
            spannableStringBuilder.append(getCustomizeAccessibilityButtonLink(context));
        } else if (AccessibilityUtil.isGestureNavigateEnabled(context)) {
            spannableStringBuilder.append(
                    context.getText(
                            AccessibilityUtil.isTouchExploreEnabled(context)
                                    ? R.string
                                            .accessibility_shortcut_edit_dialog_summary_software_gesture_talkback
                                    : R.string
                                            .accessibility_shortcut_edit_dialog_summary_software_gesture));
            spannableStringBuilder.append((CharSequence) "\n\n");
            spannableStringBuilder.append(getCustomizeAccessibilityButtonLink(context));
        } else {
            spannableStringBuilder.append(
                    (CharSequence) getSummaryStringWithIcon(context, lineHeight));
            spannableStringBuilder.append((CharSequence) "\n\n");
            spannableStringBuilder.append(getCustomizeAccessibilityButtonLink(context));
        }
        int retrieveSoftwareShortcutImageResId = retrieveSoftwareShortcutImageResId(context);
        setupShortcutWidgetWithTitleAndSummary(findViewById, text, spannableStringBuilder);
        ((ImageView) findViewById.findViewById(R.id.image))
                .setImageResource(retrieveSoftwareShortcutImageResId);
    }

    public static void initSoftwareShortcutForSUW(Context context, View view) {
        View findViewById = view.findViewById(R.id.software_shortcut);
        CharSequence text =
                context.getText(R.string.accessibility_shortcut_edit_dialog_title_software);
        int lineHeight = ((TextView) findViewById.findViewById(R.id.summary)).getLineHeight();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (!AccessibilityUtil.isFloatingMenuEnabled(context)) {
            spannableStringBuilder.append(
                    (CharSequence) getSummaryStringWithIcon(context, lineHeight));
        }
        int retrieveSoftwareShortcutImageResId = retrieveSoftwareShortcutImageResId(context);
        setupShortcutWidgetWithTitleAndSummary(findViewById, text, spannableStringBuilder);
        ((ImageView) findViewById.findViewById(R.id.image))
                .setImageResource(retrieveSoftwareShortcutImageResId);
    }

    public static void initSoftwareShortcutForSec(Context context, View view) {
        CharSequence text;
        int i;
        View findViewById = view.findViewById(R.id.software_shortcut);
        AppCompatImageButton appCompatImageButton =
                (AppCompatImageButton) view.findViewById(R.id.image_button);
        appCompatImageButton.setOnClickListener(
                new AccessibilityDialogUtils$$ExternalSyntheticLambda0(context, 0));
        if (!WizardManagerHelper.isUserSetupComplete(context)) {
            appCompatImageButton.setVisibility(8);
            view.findViewById(R.id.divider).setVisibility(8);
        }
        if (AccessibilityUtil.isFloatingMenuEnabled(context)) {
            text = context.getText(R.string.accessibility_shortcut_edit_dialog_title_software);
            i = R.drawable.use_accessibility_button_floating;
        } else if (AccessibilityUtil.isGestureNavigateEnabled(context)) {
            text = context.getText(R.string.accessibility_shortcut_summary_accessibility_gesture);
            i =
                    AccessibilityUtil.isTouchExploreEnabled(context)
                            ? R.drawable.use_accessibility_gesture_3
                            : R.drawable.use_accessibility_gesture_2;
        } else {
            text = context.getText(R.string.accessibility_shortcut_edit_dialog_title_software);
            i = R.drawable.use_accessibility_button;
        }
        CharSequence accessibilityButtonShortcutSummary =
                SecAccessibilityUtils.getAccessibilityButtonShortcutSummary(
                        context, mShortcutKey, mFeatureName);
        setupShortcutWidgetWithTitleAndSummary(
                findViewById, text, accessibilityButtonShortcutSummary);
        TextView textView = (TextView) findViewById.findViewById(R.id.summary);
        if (!TextUtils.isEmpty(accessibilityButtonShortcutSummary)
                && AccessibilityButtonMode.NAVIGATION_BAR_BUTTON.equals(
                        AccessibilityButtonGestureUtil.getMode(context))) {
            textView.setContentDescription(
                    context.getString(
                            R.string.accessibility_shortcut_edit_description_software_start_stop,
                            mFeatureName,
                            context.getString(R.string.accessibility_settings)));
        }
        ((ImageView) findViewById.findViewById(R.id.image)).setImageResource(i);
    }

    public static int retrieveSoftwareShortcutImageResId(Context context) {
        return AccessibilityUtil.isFloatingMenuEnabled(context)
                ? R.drawable.a11y_shortcut_type_software_floating
                : AccessibilityUtil.isGestureNavigateEnabled(context)
                        ? AccessibilityUtil.isTouchExploreEnabled(context)
                                ? R.drawable.a11y_shortcut_type_software_gesture_talkback
                                : R.drawable.a11y_shortcut_type_software_gesture
                        : R.drawable.a11y_shortcut_type_software;
    }

    public static void setupShortcutWidgetWithTitleAndSummary(
            View view, CharSequence charSequence, CharSequence charSequence2) {
        ((CheckBox) view.findViewById(R.id.checkbox)).setText(charSequence);
        TextView textView = (TextView) view.findViewById(R.id.summary);
        if (TextUtils.isEmpty(charSequence2)) {
            textView.setVisibility(8);
            return;
        }
        textView.setText(charSequence2);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setFocusable(false);
    }

    public static AlertDialog showEditShortcutDialog(
            Context context,
            int i,
            CharSequence charSequence,
            DialogInterface.OnClickListener onClickListener,
            String str,
            CharSequence charSequence2) {
        mShortcutKey = str;
        mFeatureName = charSequence2;
        AlertDialog createDialog = createDialog(i, context, onClickListener, charSequence);
        createDialog.show();
        ((ScrollView) createDialog.findViewById(R.id.container_layout)).setScrollIndicators(3, 3);
        createDialog.setCanceledOnTouchOutside(false);
        mAlertDialog = createDialog;
        return createDialog;
    }

    public static void startDialogActivity(Context context, int i, String str) {
        context.startActivity(
                new Intent("android.intent.action.MAIN")
                        .setFlags(268435456)
                        .setComponent(
                                new ComponentName(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                        "com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity"))
                        .putExtra("type", i)
                        .putExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, str));
    }
}
