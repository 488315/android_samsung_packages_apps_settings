package com.samsung.android.settings.notification.zen;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.AutomaticZenRule;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.service.notification.ZenModeConfig;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.settings.R;
import com.android.settings.notification.zen.ZenModeScheduleRuleSettings;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecZenScheduleRepeatButton extends RelativeLayout {
    public static SparseBooleanArray sRepeatSelectItems;
    public String mAlarmRepeatString;
    public final boolean[] mCheckedRepeatItems;
    public final int[] mColorSet;
    public int[] mColorValues;
    public Context mContext;
    public final SimpleDateFormat mDayFormat;
    public int mDragState;
    public float mEndValue;
    public boolean mIsDragging;
    public DrawRepeatCircleView[] mRepeatAnimatingView;
    public ToggleButton[] mRepeatBtn;
    public ZenModeScheduleRuleSettings.AnonymousClass4 mRepeatClickListener;
    public TextView mRepeatTextView;
    public float mStartValue;
    public int mTouchIndex;
    public View mView;
    public static final int[] VALUES_DAYS = {0, 1, 2, 3, 4, 5, 6};
    public static final int[] REPEAT_DAYS = {16777216, 1048576, 65536, 4096, 256, 16, 1};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DrawRepeatCircleView extends View {
        public final Paint mPaint;
        public final int mRadius;
        public View mRepeatToggleButton;
        public float mSelectionRatio;
        public final int mStartMargin;

        public DrawRepeatCircleView(Context context) {
            super(context);
            this.mRadius =
                    ((View) this)
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_zen_mode_schedule_repeat_btn_select_circle_radius);
            int dimensionPixelSize =
                    ((View) this)
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_zen_mode_schedule_repeat_btn_select_circle_stroke);
            this.mStartMargin =
                    ((View) this)
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen
                                            .sec_zen_mode_schedule_repeat_btn_layout_start_end_margin);
            this.mSelectionRatio = 1.0f;
            this.mRepeatToggleButton = null;
            setWillNotDraw(false);
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setColor(
                    ((View) this)
                            .mContext.getColor(R.color.sec_widget_color_round_button_bg_color));
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStrokeWidth(dimensionPixelSize);
            this.mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override // android.view.View
        public final void onDraw(Canvas canvas) {
            View view = this.mRepeatToggleButton;
            if (view == null || SecZenScheduleRepeatButton.this.mRepeatTextView == null) {
                return;
            }
            canvas.drawCircle(
                    (this.mRepeatToggleButton.getWidth() / 2.0f) + view.getX() + this.mStartMargin,
                    (this.mRepeatToggleButton.getHeight() / 2.0f)
                            + SecZenScheduleRepeatButton.this.mRepeatTextView.getBottom(),
                    this.mRadius * this.mSelectionRatio,
                    this.mPaint);
            super.onDraw(canvas);
        }
    }

    public SecZenScheduleRepeatButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCheckedRepeatItems = new boolean[7];
        this.mColorSet =
                new int[] {
                    R.color.sec_zen_mode_schedule_repeat_toggle_btn_text_color_normal,
                    R.color.sec_zen_mode_schedule_repeat_saturday_color,
                    R.color.sec_zen_mode_schedule_repeat_sunday_color
                };
        this.mDayFormat = new SimpleDateFormat("EEE");
        this.mTouchIndex = -1;
        this.mColorValues = new int[7];
        this.mStartValue = 0.0f;
        this.mEndValue = 1.0f;
        init(context);
    }

    public static String getDayOfWeekString(Context context, int i) {
        switch (i) {
            case 1:
                return context.getResources().getString(R.string.sec_dnd_sunday);
            case 2:
                return context.getResources().getString(R.string.sec_dnd_monday);
            case 3:
                return context.getResources().getString(R.string.sec_dnd_tuesday);
            case 4:
                return context.getResources().getString(R.string.sec_dnd_wednesday);
            case 5:
                return context.getResources().getString(R.string.sec_dnd_thursday);
            case 6:
                return context.getResources().getString(R.string.sec_dnd_friday);
            case 7:
                return context.getResources().getString(R.string.sec_dnd_saturday);
            default:
                Log.secE("ZenScheduleRepeatButton", "1. day value is invalid : " + i);
                return null;
        }
    }

    public static Interpolator getInterpolatorStyle() {
        return new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f);
    }

    public static void setTextSize(Context context, TextView textView, float f) {
        if (textView != null) {
            float f2 = context.getResources().getConfiguration().fontScale;
            Log.secD(
                    "ZenScheduleRepeatButton",
                    "setLargeTextSize fontScale : " + f2 + ", " + f + ", " + (f / f2));
            if (f2 > 1.2f) {
                f2 = 1.2f;
            }
            try {
                textView.setTextSize(0, (float) Math.ceil(r0 * f2));
            } catch (Exception unused) {
                Log.secE("ZenScheduleRepeatButton", "Exception ");
            }
        }
    }

    public final void clickRepeatButton() {
        int i;
        int[] iArr = REPEAT_DAYS;
        int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek() - 1;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= 7) {
                break;
            }
            ToggleButton[] toggleButtonArr = this.mRepeatBtn;
            if (toggleButtonArr != null && toggleButtonArr[i2].isChecked()) {
                i3 |= iArr[(firstDayOfWeek + i2) % 7];
            }
            i2++;
        }
        int firstDayOfWeek2 = Calendar.getInstance().getFirstDayOfWeek() - 1;
        for (int i4 = 0; i4 < 7; i4++) {
            boolean[] zArr = this.mCheckedRepeatItems;
            int i5 = iArr[(firstDayOfWeek2 + i4) % 7];
            zArr[i4] = (i3 & i5) == i5;
        }
        if (sRepeatSelectItems != null) {
            for (int i6 = 0; i6 < 7; i6++) {
                if (this.mCheckedRepeatItems[i6]) {
                    sRepeatSelectItems.put(i6, true);
                    this.mRepeatBtn[i6].setChecked(true);
                    this.mRepeatAnimatingView[i6].setVisibility(0);
                    this.mRepeatBtn[i6].setTextColor(
                            this.mContext.getColor(
                                    R.color
                                            .sec_zen_mode_schedule_repeat_toggle_btn_text_color_select));
                    if (Locale.getDefault().getLanguage().equals("ko")) {
                        this.mRepeatBtn[i6].setTypeface(Typeface.create("sans-serif", 1));
                    } else {
                        this.mRepeatBtn[i6].setTypeface(
                                Typeface.create(Typeface.create("sec", 1), 600, false));
                    }
                } else {
                    sRepeatSelectItems.put(i6, false);
                    this.mRepeatBtn[i6].setChecked(false);
                    this.mRepeatBtn[i6].setTextColor(this.mContext.getColor(this.mColorValues[i6]));
                    this.mRepeatBtn[i6].setTypeface(
                            Typeface.create(Typeface.create("sec", 0), 600, false));
                }
            }
            int size = sRepeatSelectItems.size();
            if (size == 0) {
                this.mAlarmRepeatString = ApnSettings.MVNO_NONE;
            } else {
                StringBuilder sb = new StringBuilder();
                int firstDayOfWeek3 = Calendar.getInstance().getFirstDayOfWeek();
                int i7 = (14 - firstDayOfWeek3) % 7;
                int i8 = (8 - firstDayOfWeek3) % 7;
                Calendar calendar = Calendar.getInstance();
                int i9 = 0;
                int i10 = 0;
                for (i = 7; i9 < i; i = 7) {
                    if (sRepeatSelectItems.get(i9)) {
                        if (i10 > 0) {
                            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
                                sb.append("، ");
                            } else if (Locale.getDefault()
                                    .getLanguage()
                                    .equals(Locale.JAPAN.getLanguage())) {
                                sb.append("、");
                            } else {
                                sb.append(", ");
                            }
                        }
                        calendar.set(7, (firstDayOfWeek3 + i9) % 7);
                        sb.append(this.mDayFormat.format(calendar.getTime()));
                        i10++;
                    }
                    i9++;
                }
                Log.secD(
                        "ZenScheduleRepeatButton",
                        "setRepeatSubText() - size = " + size + ", checkCnt = " + i10);
                if (i10 == 0) {
                    this.mAlarmRepeatString = ApnSettings.MVNO_NONE;
                } else if (i10 == 7) {
                    this.mAlarmRepeatString =
                            this.mContext.getResources().getString(R.string.sec_dnd_every_day);
                } else if (i10 == 5 && !sRepeatSelectItems.get(i8) && !sRepeatSelectItems.get(i7)) {
                    this.mAlarmRepeatString =
                            this.mContext.getResources().getString(R.string.sec_dnd_week_days);
                } else if (i10 == 2 && sRepeatSelectItems.get(i8) && sRepeatSelectItems.get(i7)) {
                    this.mAlarmRepeatString =
                            this.mContext.getResources().getString(R.string.sec_dnd_week_ends);
                } else {
                    this.mAlarmRepeatString = sb.toString();
                }
            }
            this.mRepeatTextView.setTypeface(
                    Typeface.create(Typeface.create("sec", 0), 600, false));
            this.mRepeatTextView.setText(this.mAlarmRepeatString);
        }
        ZenModeScheduleRuleSettings.AnonymousClass4 anonymousClass4 = this.mRepeatClickListener;
        if (anonymousClass4 == null) {
            Log.secE(
                    "ZenScheduleRepeatButton",
                    "mRepeatClickListener is null. not able to click Repeat button.");
            return;
        }
        ZenModeScheduleRuleSettings zenModeScheduleRuleSettings = ZenModeScheduleRuleSettings.this;
        if (i3 == 0) {
            ZenModeConfig.ScheduleInfo scheduleInfo = zenModeScheduleRuleSettings.mSchedule;
            scheduleInfo.days = new int[0];
            zenModeScheduleRuleSettings.updateScheduleRule(scheduleInfo);
            zenModeScheduleRuleSettings.mDaysEmpty = true;
        } else {
            int i11 = 0;
            for (int i12 = 0; i12 < 7; i12++) {
                if ((iArr[i12] & i3) != 0) {
                    i11++;
                }
            }
            int[] iArr2 = new int[i11];
            int i13 = 1;
            int i14 = 0;
            int i15 = 7;
            for (int i16 = 0; i16 < i15; i16++) {
                if ((iArr[i13] & i3) != 0) {
                    iArr2[i14] = i13 + 1;
                    i14++;
                }
                i15 = 7;
                i13 = (i13 + 1) % 7;
            }
            ZenModeConfig.ScheduleInfo scheduleInfo2 = zenModeScheduleRuleSettings.mSchedule;
            scheduleInfo2.days = iArr2;
            zenModeScheduleRuleSettings.updateScheduleRule(scheduleInfo2);
            zenModeScheduleRuleSettings.mDaysEmpty = false;
        }
        AutomaticZenRule automaticZenRule = zenModeScheduleRuleSettings.mRule;
        if (automaticZenRule != null
                && automaticZenRule.getName() != null
                && zenModeScheduleRuleSettings.mRule.getName().length() > 20) {
            zenModeScheduleRuleSettings.mNameEmpty = true;
        }
        zenModeScheduleRuleSettings.updateSaveButton();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i;
        if (!isEnabled()) {
            return true;
        }
        int x = (int) motionEvent.getX();
        int actionMasked = motionEvent.getActionMasked();
        int dimensionPixelSize =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.sec_zen_mode_schedule_repeat_btn_layout_start_end_margin);
        int width = (x - dimensionPixelSize) / ((getWidth() - (dimensionPixelSize * 2)) / 7);
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1
                && (width = 6 - width) <= 0) {
            width = 0;
        }
        if (width >= 7) {
            width = 6;
        } else if (width < 0) {
            width = 0;
        }
        if (actionMasked == 0) {
            this.mIsDragging = false;
            this.mTouchIndex = width;
        } else if (actionMasked == 1) {
            if (Math.abs(this.mTouchIndex - width) > 1) {
                int i2 = this.mDragState;
                if (i2 == 1) {
                    for (int i3 = this.mTouchIndex + 1; i3 <= width; i3++) {
                        this.mRepeatBtn[i3].performClick();
                    }
                } else if (i2 == -1) {
                    for (int i4 = this.mTouchIndex - 1; i4 >= width; i4--) {
                        this.mRepeatBtn[i4].performClick();
                    }
                }
            }
            this.mIsDragging = false;
            this.mDragState = 0;
        } else if (actionMasked == 2 && (i = this.mTouchIndex) != width) {
            if (i < width) {
                if (this.mDragState == -1) {
                    this.mIsDragging = false;
                }
                this.mDragState = 1;
            } else if (i > width) {
                if (this.mDragState == 1) {
                    this.mIsDragging = false;
                }
                this.mDragState = -1;
            }
            if (!this.mIsDragging) {
                this.mRepeatBtn[i].performClick();
            }
            if (Math.abs(this.mTouchIndex - width) > 1) {
                int i5 = this.mDragState;
                if (i5 == 1) {
                    for (int i6 = this.mTouchIndex + 1; i6 < width; i6++) {
                        this.mRepeatBtn[i6].performClick();
                    }
                } else if (i5 == -1) {
                    for (int i7 = this.mTouchIndex - 1; i7 > width; i7--) {
                        this.mRepeatBtn[i7].performClick();
                    }
                }
            }
            this.mRepeatBtn[width].performClick();
            this.mTouchIndex = width;
            this.mIsDragging = true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void init(Context context) {
        setWillNotDraw(false);
        this.mContext = context;
        View inflate =
                ((LayoutInflater) context.getSystemService("layout_inflater"))
                        .inflate(R.layout.sec_zen_schedule_repeat_button, (ViewGroup) this, false);
        this.mView = inflate;
        addView(inflate);
        this.mRepeatBtn = new ToggleButton[7];
        this.mRepeatAnimatingView = new DrawRepeatCircleView[7];
        int i = R.id.repeat_0;
        for (int i2 = 0; i2 < 7; i2++) {
            this.mRepeatBtn[i2] = (ToggleButton) findViewById(i);
            this.mRepeatAnimatingView[i2] = new DrawRepeatCircleView(this.mContext);
            DrawRepeatCircleView drawRepeatCircleView = this.mRepeatAnimatingView[i2];
            drawRepeatCircleView.mRepeatToggleButton = this.mRepeatBtn[i2];
            drawRepeatCircleView.setMinimumWidth(this.mView.getWidth());
            this.mRepeatAnimatingView[i2].setMinimumHeight(this.mView.getHeight());
            addView(this.mRepeatAnimatingView[i2]);
            this.mRepeatBtn[i2].setTag(Integer.valueOf(i2));
            this.mRepeatBtn[i2].setChecked(false);
            i++;
            this.mCheckedRepeatItems[i2] = false;
            this.mRepeatAnimatingView[i2].setVisibility(8);
        }
        int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek() - 1;
        int[] iArr = {
            R.string.sec_dnd_sun,
            R.string.sec_dnd_mon,
            R.string.sec_dnd_tue,
            R.string.sec_dnd_wed,
            R.string.sec_dnd_thu,
            R.string.sec_dnd_fri,
            R.string.sec_dnd_sat
        };
        final int[] iArr2 = {
            R.string.sec_dnd_sunday,
            R.string.sec_dnd_monday,
            R.string.sec_dnd_tuesday,
            R.string.sec_dnd_wednesday,
            R.string.sec_dnd_thursday,
            R.string.sec_dnd_friday,
            R.string.sec_dnd_saturday
        };
        int[] iArr3 = this.mColorSet;
        int i3 = 2;
        if (iArr3 == null || iArr3.length != 3) {
            iArr3 = new int[] {EmergencyPhoneWidget.BG_COLOR, -16776961, -65536};
        }
        int[] iArr4 = new int[7];
        for (int i4 = 0; i4 < 7; i4++) {
            char charAt = "XXXXXXR".charAt(i4);
            iArr4[i4] = charAt == 'R' ? iArr3[2] : charAt == 'B' ? iArr3[1] : iArr3[0];
        }
        if (firstDayOfWeek == 0) {
            i3 = 1;
        } else if (firstDayOfWeek == 2) {
            i3 = 6;
        } else if (firstDayOfWeek == 3) {
            i3 = 5;
        } else if (firstDayOfWeek == 4) {
            i3 = 4;
        } else if (firstDayOfWeek == 5) {
            i3 = 3;
        } else if (firstDayOfWeek != 6) {
            i3 = 0;
        }
        int[] iArr5 = new int[7];
        for (int i5 = 0; i5 < 7; i5++) {
            iArr5[(i5 + i3) % 7] = iArr4[i5];
        }
        this.mColorValues = iArr5;
        for (int i6 = 0; i6 < 7; i6++) {
            final int i7 = (firstDayOfWeek + i6) % 7;
            try {
                SpannableString spannableString =
                        new SpannableString(getResources().getString(iArr[i7]));
                this.mRepeatBtn[i6].setTextColor(this.mContext.getColor(this.mColorValues[i6]));
                this.mRepeatBtn[i6].setTextOn(spannableString);
                this.mRepeatBtn[i6].setTextOff(spannableString);
                this.mRepeatBtn[i6].setAccessibilityDelegate(
                        new View
                                .AccessibilityDelegate() { // from class:
                                                           // com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton.1
                            @Override // android.view.View.AccessibilityDelegate
                            public final void onInitializeAccessibilityNodeInfo(
                                    View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                super.onInitializeAccessibilityNodeInfo(
                                        view, accessibilityNodeInfo);
                                accessibilityNodeInfo.setText(
                                        SecZenScheduleRepeatButton.this
                                                .getResources()
                                                .getString(iArr2[i7]));
                            }
                        });
                setTextSize(
                        this.mContext,
                        this.mRepeatBtn[i6],
                        getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_zen_mode_schedule_repeat_toggle_btn_text_size));
            } catch (NoSuchMethodError unused) {
            }
        }
        for (final int i8 = 0; i8 < 7; i8++) {
            this.mRepeatBtn[i8].setOnClickListener(
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if (SecZenScheduleRepeatButton.this.mRepeatBtn[i8].isChecked()) {
                                Log.secD(
                                        "ZenScheduleRepeatButton",
                                        "mRepeatBtn[buttonNumber].isChecked() - true, buttonNumber"
                                            + " = "
                                                + i8);
                                SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i8]
                                        .setVisibility(0);
                                SecZenScheduleRepeatButton.this.setSelectionMarkAnimator(i8, true);
                                SecZenScheduleRepeatButton.this.mRepeatBtn[i8].setStateDescription(
                                        SecZenScheduleRepeatButton.getDayOfWeekString(
                                                        SecZenScheduleRepeatButton.this.mContext,
                                                        i8 + 1)
                                                + ","
                                                + SecZenScheduleRepeatButton.this.mContext
                                                        .getString(R.string.nfc_radiobtn_select));
                            } else {
                                Log.secD(
                                        "ZenScheduleRepeatButton",
                                        "mRepeatBtn[buttonNumber].isChecked() - false, buttonNumber"
                                            + " = "
                                                + i8);
                                SecZenScheduleRepeatButton.this.setSelectionMarkAnimator(i8, false);
                                SecZenScheduleRepeatButton.this.mRepeatBtn[i8].setStateDescription(
                                        SecZenScheduleRepeatButton.getDayOfWeekString(
                                                        SecZenScheduleRepeatButton.this.mContext,
                                                        i8 + 1)
                                                + ","
                                                + SecZenScheduleRepeatButton.this.mContext
                                                        .getString(
                                                                R.string.nfc_radiobtn_not_select));
                            }
                            SecZenScheduleRepeatButton.this.clickRepeatButton();
                        }
                    });
        }
        sRepeatSelectItems = new SparseBooleanArray();
        TextView textView = (TextView) findViewById(R.id.repeat_text);
        this.mRepeatTextView = textView;
        setTextSize(
                this.mContext,
                textView,
                getResources()
                        .getDimensionPixelSize(R.dimen.sec_zen_mode_schedule_repeat_text_size));
        invalidate();
    }

    public final void setSelectionMarkAnimator(final int i, final boolean z) {
        int i2;
        final int i3 = 1;
        final int i4 = 0;
        Log.secD(
                "ZenScheduleRepeatButton",
                "setSelectionMarkAnimator = " + i + " , isShowAnim = " + z);
        if (z) {
            this.mStartValue = 0.0f;
            this.mEndValue = 1.0f;
            i2 = 300;
        } else {
            this.mStartValue = 1.0f;
            this.mEndValue = 0.0f;
            i2 = 200;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mStartValue, this.mEndValue);
        long j = i2;
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(getInterpolatorStyle());
        ofFloat.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton.3
                    public final /* synthetic */ SecZenScheduleRepeatButton this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        switch (i4) {
                            case 0:
                                SecZenScheduleRepeatButton secZenScheduleRepeatButton = this.this$0;
                                ToggleButton[] toggleButtonArr =
                                        secZenScheduleRepeatButton.mRepeatBtn;
                                int i5 = i;
                                ToggleButton toggleButton = toggleButtonArr[i5];
                                if (toggleButton != null
                                        && secZenScheduleRepeatButton.mRepeatAnimatingView[i5]
                                                != null) {
                                    if (toggleButton.isChecked() != z) {
                                        valueAnimator.cancel();
                                        if (!this.this$0.mRepeatBtn[i].isChecked()) {
                                            this.this$0.mRepeatAnimatingView[i].setVisibility(8);
                                        }
                                    }
                                    this.this$0.mRepeatAnimatingView[i].mSelectionRatio =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    break;
                                } else {
                                    valueAnimator.cancel();
                                    break;
                                }
                                break;
                            default:
                                SecZenScheduleRepeatButton secZenScheduleRepeatButton2 =
                                        this.this$0;
                                ToggleButton[] toggleButtonArr2 =
                                        secZenScheduleRepeatButton2.mRepeatBtn;
                                int i6 = i;
                                ToggleButton toggleButton2 = toggleButtonArr2[i6];
                                if (toggleButton2 != null
                                        && secZenScheduleRepeatButton2.mRepeatAnimatingView[i6]
                                                != null) {
                                    if (toggleButton2.isChecked() != z) {
                                        valueAnimator.cancel();
                                    }
                                    this.this$0.mRepeatAnimatingView[i].invalidate();
                                    break;
                                } else {
                                    valueAnimator.cancel();
                                    break;
                                }
                                break;
                        }
                    }
                });
        ofFloat.addListener(
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton.4
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        SecZenScheduleRepeatButton secZenScheduleRepeatButton =
                                SecZenScheduleRepeatButton.this;
                        ToggleButton[] toggleButtonArr = secZenScheduleRepeatButton.mRepeatBtn;
                        int i5 = i;
                        ToggleButton toggleButton = toggleButtonArr[i5];
                        if (toggleButton == null
                                || secZenScheduleRepeatButton.mRepeatAnimatingView[i5] == null) {
                            return;
                        }
                        if (!toggleButton.isChecked()) {
                            DrawRepeatCircleView drawRepeatCircleView =
                                    SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i];
                            drawRepeatCircleView.mSelectionRatio = 0.0f;
                            drawRepeatCircleView.setVisibility(8);
                        } else {
                            SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i].setVisibility(
                                    0);
                            DrawRepeatCircleView drawRepeatCircleView2 =
                                    SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i];
                            drawRepeatCircleView2.mSelectionRatio = 1.0f;
                            drawRepeatCircleView2.invalidate();
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        DrawRepeatCircleView drawRepeatCircleView;
                        SecZenScheduleRepeatButton secZenScheduleRepeatButton =
                                SecZenScheduleRepeatButton.this;
                        ToggleButton[] toggleButtonArr = secZenScheduleRepeatButton.mRepeatBtn;
                        int i5 = i;
                        ToggleButton toggleButton = toggleButtonArr[i5];
                        if (toggleButton == null
                                || (drawRepeatCircleView =
                                                secZenScheduleRepeatButton.mRepeatAnimatingView[i5])
                                        == null) {
                            return;
                        }
                        drawRepeatCircleView.mSelectionRatio = secZenScheduleRepeatButton.mEndValue;
                        if (!toggleButton.isChecked()) {
                            DrawRepeatCircleView drawRepeatCircleView2 =
                                    SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i];
                            drawRepeatCircleView2.mSelectionRatio = 0.0f;
                            drawRepeatCircleView2.setVisibility(8);
                        } else {
                            SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i].setVisibility(
                                    0);
                            DrawRepeatCircleView drawRepeatCircleView3 =
                                    SecZenScheduleRepeatButton.this.mRepeatAnimatingView[i];
                            drawRepeatCircleView3.mSelectionRatio = 1.0f;
                            drawRepeatCircleView3.invalidate();
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        SecZenScheduleRepeatButton secZenScheduleRepeatButton =
                                SecZenScheduleRepeatButton.this;
                        DrawRepeatCircleView drawRepeatCircleView =
                                secZenScheduleRepeatButton.mRepeatAnimatingView[i];
                        if (drawRepeatCircleView == null) {
                            animator.cancel();
                        } else {
                            drawRepeatCircleView.mSelectionRatio =
                                    secZenScheduleRepeatButton.mStartValue;
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {}
                });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.mStartValue, this.mEndValue);
        ofFloat2.setDuration(j);
        ofFloat2.setInterpolator(getInterpolatorStyle());
        ofFloat2.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton.3
                    public final /* synthetic */ SecZenScheduleRepeatButton this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        switch (i3) {
                            case 0:
                                SecZenScheduleRepeatButton secZenScheduleRepeatButton = this.this$0;
                                ToggleButton[] toggleButtonArr =
                                        secZenScheduleRepeatButton.mRepeatBtn;
                                int i5 = i;
                                ToggleButton toggleButton = toggleButtonArr[i5];
                                if (toggleButton != null
                                        && secZenScheduleRepeatButton.mRepeatAnimatingView[i5]
                                                != null) {
                                    if (toggleButton.isChecked() != z) {
                                        valueAnimator.cancel();
                                        if (!this.this$0.mRepeatBtn[i].isChecked()) {
                                            this.this$0.mRepeatAnimatingView[i].setVisibility(8);
                                        }
                                    }
                                    this.this$0.mRepeatAnimatingView[i].mSelectionRatio =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    break;
                                } else {
                                    valueAnimator.cancel();
                                    break;
                                }
                                break;
                            default:
                                SecZenScheduleRepeatButton secZenScheduleRepeatButton2 =
                                        this.this$0;
                                ToggleButton[] toggleButtonArr2 =
                                        secZenScheduleRepeatButton2.mRepeatBtn;
                                int i6 = i;
                                ToggleButton toggleButton2 = toggleButtonArr2[i6];
                                if (toggleButton2 != null
                                        && secZenScheduleRepeatButton2.mRepeatAnimatingView[i6]
                                                != null) {
                                    if (toggleButton2.isChecked() != z) {
                                        valueAnimator.cancel();
                                    }
                                    this.this$0.mRepeatAnimatingView[i].invalidate();
                                    break;
                                } else {
                                    valueAnimator.cancel();
                                    break;
                                }
                                break;
                        }
                    }
                });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.start();
    }

    public SecZenScheduleRepeatButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCheckedRepeatItems = new boolean[7];
        this.mColorSet =
                new int[] {
                    R.color.sec_zen_mode_schedule_repeat_toggle_btn_text_color_normal,
                    R.color.sec_zen_mode_schedule_repeat_saturday_color,
                    R.color.sec_zen_mode_schedule_repeat_sunday_color
                };
        this.mDayFormat = new SimpleDateFormat("EEE");
        this.mTouchIndex = -1;
        this.mColorValues = new int[7];
        this.mStartValue = 0.0f;
        this.mEndValue = 1.0f;
        init(context);
    }

    public SecZenScheduleRepeatButton(Context context) {
        super(context);
        this.mCheckedRepeatItems = new boolean[7];
        this.mColorSet =
                new int[] {
                    R.color.sec_zen_mode_schedule_repeat_toggle_btn_text_color_normal,
                    R.color.sec_zen_mode_schedule_repeat_saturday_color,
                    R.color.sec_zen_mode_schedule_repeat_sunday_color
                };
        this.mDayFormat = new SimpleDateFormat("EEE");
        this.mTouchIndex = -1;
        this.mColorValues = new int[7];
        this.mStartValue = 0.0f;
        this.mEndValue = 1.0f;
        setLayoutParams(
                new ViewGroup.LayoutParams(
                        -1,
                        getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_zen_mode_schedule_repeat_layout_height)));
        init(context);
    }
}
