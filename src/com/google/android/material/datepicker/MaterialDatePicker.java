package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.SoftwareKeyboardControllerCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialDatePicker<S> extends DialogFragment {
    public MaterialShapeDrawable background;
    public MaterialCalendar calendar;
    public CalendarConstraints calendarConstraints;
    public boolean edgeToEdgeEnabled;
    public CharSequence fullTitleText;
    public boolean fullscreen;
    public TextView headerTitleTextView;
    public CheckableImageButton headerToggleButton;
    public int inputMode;
    public CharSequence negativeButtonContentDescription;
    public int negativeButtonContentDescriptionResId;
    public CharSequence negativeButtonText;
    public int negativeButtonTextResId;
    public final LinkedHashSet onCancelListeners;
    public final LinkedHashSet onDismissListeners;
    public int overrideThemeResId;
    public PickerFragment pickerFragment;
    public CharSequence positiveButtonContentDescription;
    public int positiveButtonContentDescriptionResId;
    public CharSequence positiveButtonText;
    public int positiveButtonTextResId;
    public CharSequence singleLineTitleText;
    public CharSequence titleText;
    public int titleTextResId;

    public MaterialDatePicker() {
        new LinkedHashSet();
        new LinkedHashSet();
        this.onCancelListeners = new LinkedHashSet();
        this.onDismissListeners = new LinkedHashSet();
    }

    public static int getPaddedPickerWidth(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset =
                resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
        int i = new Month(UtcDates.getTodayCalendar()).daysInWeek;
        return ((i - 1)
                        * resources.getDimensionPixelOffset(
                                R.dimen.mtrl_calendar_month_horizontal_padding))
                + (resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) * i)
                + (dimensionPixelOffset * 2);
    }

    public static boolean readMaterialCalendarStyleBoolean(Context context, int i) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        MaterialAttributes.resolveTypedValueOrThrow(
                                        context,
                                        R.attr.materialCalendarStyle,
                                        MaterialCalendar.class.getCanonicalName())
                                .data,
                        new int[] {i});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    public final void getDateSelector() {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                getArguments().getParcelable("DATE_SELECTOR_KEY"));
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnCancelListener) it.next()).onCancel(dialogInterface);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.overrideThemeResId = bundle.getInt("OVERRIDE_THEME_RES_ID");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                bundle.getParcelable("DATE_SELECTOR_KEY"));
        this.calendarConstraints =
                (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                bundle.getParcelable("DAY_VIEW_DECORATOR_KEY"));
        this.titleTextResId = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.titleText = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.inputMode = bundle.getInt("INPUT_MODE_KEY");
        this.positiveButtonTextResId = bundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.positiveButtonText = bundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.positiveButtonContentDescriptionResId =
                bundle.getInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.positiveButtonContentDescription =
                bundle.getCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        this.negativeButtonTextResId = bundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.negativeButtonText = bundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
        this.negativeButtonContentDescriptionResId =
                bundle.getInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.negativeButtonContentDescription =
                bundle.getCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        CharSequence charSequence = this.titleText;
        if (charSequence == null) {
            charSequence = requireContext().getResources().getText(this.titleTextResId);
        }
        this.fullTitleText = charSequence;
        if (charSequence != null) {
            CharSequence[] split = TextUtils.split(String.valueOf(charSequence), "\n");
            if (split.length > 1) {
                charSequence = split[0];
            }
        } else {
            charSequence = null;
        }
        this.singleLineTitleText = charSequence;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Context requireContext = requireContext();
        requireContext();
        int i = this.overrideThemeResId;
        if (i == 0) {
            getDateSelector();
            throw null;
        }
        Dialog dialog = new Dialog(requireContext, i);
        Context context = dialog.getContext();
        this.fullscreen =
                readMaterialCalendarStyleBoolean(context, android.R.attr.windowFullscreen);
        this.background =
                new MaterialShapeDrawable(context, null, R.attr.materialCalendarStyle, 2132084904);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        null,
                        R$styleable.MaterialCalendar,
                        R.attr.materialCalendarStyle,
                        2132084904);
        int color = obtainStyledAttributes.getColor(1, 0);
        obtainStyledAttributes.recycle();
        this.background.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(color));
        MaterialShapeDrawable materialShapeDrawable = this.background;
        View decorView = dialog.getWindow().getDecorView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        materialShapeDrawable.setElevation(ViewCompat.Api21Impl.getElevation(decorView));
        return dialog;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        this.fullscreen
                                ? R.layout.mtrl_picker_fullscreen
                                : R.layout.mtrl_picker_dialog,
                        viewGroup);
        Context context = inflate.getContext();
        if (this.fullscreen) {
            inflate.findViewById(R.id.mtrl_calendar_frame)
                    .setLayoutParams(
                            new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -2));
        } else {
            inflate.findViewById(R.id.mtrl_calendar_main_pane)
                    .setLayoutParams(
                            new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -1));
        }
        TextView textView = (TextView) inflate.findViewById(R.id.mtrl_picker_header_selection_text);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        textView.setAccessibilityLiveRegion(1);
        this.headerToggleButton =
                (CheckableImageButton) inflate.findViewById(R.id.mtrl_picker_header_toggle);
        this.headerTitleTextView = (TextView) inflate.findViewById(R.id.mtrl_picker_title_text);
        this.headerToggleButton.setTag("TOGGLE_BUTTON_TAG");
        CheckableImageButton checkableImageButton = this.headerToggleButton;
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(
                new int[] {android.R.attr.state_checked},
                AppCompatResources.getDrawable(
                        context, R.drawable.material_ic_calendar_black_24dp));
        stateListDrawable.addState(
                new int[0],
                AppCompatResources.getDrawable(context, R.drawable.material_ic_edit_black_24dp));
        checkableImageButton.setImageDrawable(stateListDrawable);
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, null);
        CheckableImageButton checkableImageButton2 = this.headerToggleButton;
        this.headerToggleButton.setContentDescription(
                this.inputMode == 1
                        ? checkableImageButton2
                                .getContext()
                                .getString(R.string.mtrl_picker_toggle_to_calendar_input_mode)
                        : checkableImageButton2
                                .getContext()
                                .getString(R.string.mtrl_picker_toggle_to_text_input_mode));
        this.headerToggleButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.google.android.material.datepicker.MaterialDatePicker$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MaterialDatePicker.this.getDateSelector();
                        throw null;
                    }
                });
        getDateSelector();
        throw null;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnDismissListener) it.next()).onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
        bundle.putParcelable("DATE_SELECTOR_KEY", null);
        CalendarConstraints calendarConstraints = this.calendarConstraints;
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder();
        int i = CalendarConstraints.Builder.$r8$clinit;
        int i2 = CalendarConstraints.Builder.$r8$clinit;
        long j = calendarConstraints.start.timeInMillis;
        long j2 = calendarConstraints.end.timeInMillis;
        builder.openAt = Long.valueOf(calendarConstraints.openAt.timeInMillis);
        int i3 = calendarConstraints.firstDayOfWeek;
        CalendarConstraints.DateValidator dateValidator = calendarConstraints.validator;
        MaterialCalendar materialCalendar = this.calendar;
        Month month = materialCalendar == null ? null : materialCalendar.current;
        if (month != null) {
            builder.openAt = Long.valueOf(month.timeInMillis);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("DEEP_COPY_VALIDATOR_KEY", dateValidator);
        Month create = Month.create(j);
        Month create2 = Month.create(j2);
        CalendarConstraints.DateValidator dateValidator2 =
                (CalendarConstraints.DateValidator)
                        bundle2.getParcelable("DEEP_COPY_VALIDATOR_KEY");
        Long l = builder.openAt;
        bundle.putParcelable(
                "CALENDAR_CONSTRAINTS_KEY",
                new CalendarConstraints(
                        create,
                        create2,
                        dateValidator2,
                        l == null ? null : Month.create(l.longValue()),
                        i3));
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.titleText);
        bundle.putInt("INPUT_MODE_KEY", this.inputMode);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.positiveButtonTextResId);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.positiveButtonText);
        bundle.putInt(
                "POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY",
                this.positiveButtonContentDescriptionResId);
        bundle.putCharSequence(
                "POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.positiveButtonContentDescription);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.negativeButtonTextResId);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.negativeButtonText);
        bundle.putInt(
                "NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY",
                this.negativeButtonContentDescriptionResId);
        bundle.putCharSequence(
                "NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.negativeButtonContentDescription);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        Integer num;
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
            if (!this.edgeToEdgeEnabled) {
                final View findViewById = requireView().findViewById(R.id.fullscreen_header);
                ColorStateList colorStateListOrNull =
                        DrawableUtils.getColorStateListOrNull(findViewById.getBackground());
                Integer valueOf =
                        colorStateListOrNull != null
                                ? Integer.valueOf(colorStateListOrNull.getDefaultColor())
                                : null;
                boolean z = valueOf == null || valueOf.intValue() == 0;
                Context context = window.getContext();
                TypedValue resolve =
                        MaterialAttributes.resolve(context, android.R.attr.colorBackground);
                if (resolve != null) {
                    int i = resolve.resourceId;
                    num = Integer.valueOf(i != 0 ? context.getColor(i) : resolve.data);
                } else {
                    num = null;
                }
                int intValue = num != null ? num.intValue() : EmergencyPhoneWidget.BG_COLOR;
                if (z) {
                    valueOf = Integer.valueOf(intValue);
                }
                window.setDecorFitsSystemWindows(false);
                window.getContext();
                window.getContext();
                window.setStatusBarColor(0);
                window.setNavigationBarColor(0);
                boolean z2 =
                        MaterialColors.isColorLight(0)
                                || MaterialColors.isColorLight(valueOf.intValue());
                WindowInsetsControllerCompat.Impl30 impl30 =
                        new WindowInsetsControllerCompat.Impl30(
                                window.getInsetsController(),
                                new SoftwareKeyboardControllerCompat(window.getDecorView()));
                impl30.mWindow = window;
                if (z2) {
                    Window window2 = impl30.mWindow;
                    if (window2 != null) {
                        View decorView = window2.getDecorView();
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 8192);
                    }
                    impl30.mInsetsController.setSystemBarsAppearance(8, 8);
                } else {
                    Window window3 = impl30.mWindow;
                    if (window3 != null) {
                        View decorView2 = window3.getDecorView();
                        decorView2.setSystemUiVisibility(
                                decorView2.getSystemUiVisibility() & (-8193));
                    }
                    impl30.mInsetsController.setSystemBarsAppearance(0, 8);
                }
                boolean z3 =
                        MaterialColors.isColorLight(0) || MaterialColors.isColorLight(intValue);
                WindowInsetsControllerCompat.Impl30 impl302 =
                        new WindowInsetsControllerCompat.Impl30(
                                window.getInsetsController(),
                                new SoftwareKeyboardControllerCompat(window.getDecorView()));
                impl302.mWindow = window;
                if (z3) {
                    Window window4 = impl302.mWindow;
                    if (window4 != null) {
                        View decorView3 = window4.getDecorView();
                        decorView3.setSystemUiVisibility(decorView3.getSystemUiVisibility() | 16);
                    }
                    impl302.mInsetsController.setSystemBarsAppearance(16, 16);
                } else {
                    Window window5 = impl302.mWindow;
                    if (window5 != null) {
                        View decorView4 = window5.getDecorView();
                        decorView4.setSystemUiVisibility(
                                decorView4.getSystemUiVisibility() & (-17));
                    }
                    impl302.mInsetsController.setSystemBarsAppearance(0, 16);
                }
                final int paddingTop = findViewById.getPaddingTop();
                final int i2 = findViewById.getLayoutParams().height;
                OnApplyWindowInsetsListener onApplyWindowInsetsListener =
                        new OnApplyWindowInsetsListener() { // from class:
                                                            // com.google.android.material.datepicker.MaterialDatePicker.3
                            @Override // androidx.core.view.OnApplyWindowInsetsListener
                            public final WindowInsetsCompat onApplyWindowInsets(
                                    View view, WindowInsetsCompat windowInsetsCompat) {
                                int i3 = windowInsetsCompat.mImpl.getInsets(7).top;
                                int i4 = i2;
                                if (i4 >= 0) {
                                    findViewById.getLayoutParams().height = i4 + i3;
                                    View view2 = findViewById;
                                    view2.setLayoutParams(view2.getLayoutParams());
                                }
                                View view3 = findViewById;
                                view3.setPadding(
                                        view3.getPaddingLeft(),
                                        paddingTop + i3,
                                        findViewById.getPaddingRight(),
                                        findViewById.getPaddingBottom());
                                return windowInsetsCompat;
                            }
                        };
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                        findViewById, onApplyWindowInsetsListener);
                this.edgeToEdgeEnabled = true;
            }
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset =
                    getResources()
                            .getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect =
                    new Rect(
                            dimensionPixelOffset,
                            dimensionPixelOffset,
                            dimensionPixelOffset,
                            dimensionPixelOffset);
            window.setBackgroundDrawable(
                    new InsetDrawable(
                            (Drawable) this.background,
                            dimensionPixelOffset,
                            dimensionPixelOffset,
                            dimensionPixelOffset,
                            dimensionPixelOffset));
            window.getDecorView()
                    .setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        requireContext();
        int i3 = this.overrideThemeResId;
        if (i3 == 0) {
            getDateSelector();
            throw null;
        }
        getDateSelector();
        CalendarConstraints calendarConstraints = this.calendarConstraints;
        MaterialCalendar materialCalendar = new MaterialCalendar();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", i3);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putParcelable("CURRENT_MONTH_KEY", calendarConstraints.openAt);
        materialCalendar.setArguments(bundle);
        this.calendar = materialCalendar;
        PickerFragment pickerFragment = materialCalendar;
        if (this.inputMode == 1) {
            getDateSelector();
            CalendarConstraints calendarConstraints2 = this.calendarConstraints;
            PickerFragment materialTextInputPicker = new MaterialTextInputPicker();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("THEME_RES_ID_KEY", i3);
            bundle2.putParcelable("DATE_SELECTOR_KEY", null);
            bundle2.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints2);
            materialTextInputPicker.setArguments(bundle2);
            pickerFragment = materialTextInputPicker;
        }
        this.pickerFragment = pickerFragment;
        this.headerTitleTextView.setText(
                (this.inputMode == 1 && getResources().getConfiguration().orientation == 2)
                        ? this.singleLineTitleText
                        : this.fullTitleText);
        getDateSelector();
        getContext();
        throw null;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        this.pickerFragment.onSelectionChangedListeners.clear();
        super.onStop();
    }

    public void updateHeader(String str) {
        getDateSelector();
        requireContext();
        throw null;
    }
}
