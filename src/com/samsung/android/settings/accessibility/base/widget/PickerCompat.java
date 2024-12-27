package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.base.StringBuilderUtils;
import com.sec.ims.configuration.DATA;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PickerCompat extends LinearLayout {
    public float defFloat;
    public int defInt;
    public final AnonymousClass2 mButtonClickListener;
    public final ImageButton mButtonMinus;
    public final ImageButton mButtonPlus;
    public final Context mContext;
    public String mCurrentValue;
    public String mDataType;
    public final AnonymousClass1 mEditActionListener;
    public InputMethodManager mInputManager;
    public final LinearLayout mPickerContainer;
    public final LinearLayout mPickerDialogLayout;
    public final EditText mPickerValue;
    public final TextView mPickerValueRangeText;
    public final TextView mSecText;
    public setOnValueChangeListener mValueChangeListener;
    public float maxFloat;
    public int maxInt;
    public float minFloat;
    public int minInt;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.base.widget.PickerCompat$2, reason: invalid class name */
    public final class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PickerCompat this$0;

        public /* synthetic */ AnonymousClass2(PickerCompat pickerCompat, int i) {
            this.$r8$classId = i;
            this.this$0 = pickerCompat;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    InputMethodManager inputMethodManager = this.this$0.mInputManager;
                    if (inputMethodManager != null && inputMethodManager.semIsInputMethodShown()) {
                        this.this$0.mInputManager.semForceHideSoftInput();
                    }
                    if (!TextUtils.isEmpty(this.this$0.mPickerValue.getText())) {
                        if (this.this$0.mDataType.equalsIgnoreCase("decimal")) {
                            PickerCompat pickerCompat = this.this$0;
                            int id = view.getId();
                            float convertStringToFloat =
                                    StringBuilderUtils.convertStringToFloat(
                                            pickerCompat.mPickerValue.getText().toString());
                            if (Float.compare(convertStringToFloat, 0.0f) == 0) {
                                pickerCompat.mPickerValue.setText(
                                        StringBuilderUtils.convertDefaultLocaleType(
                                                pickerCompat.defFloat));
                            } else {
                                if (Float.compare(pickerCompat.maxFloat, convertStringToFloat) >= 0
                                        && Float.compare(
                                                        pickerCompat.minFloat, convertStringToFloat)
                                                <= 0) {
                                    if (id == pickerCompat.mButtonPlus.getId()) {
                                        if (Float.compare(
                                                        pickerCompat.maxFloat, convertStringToFloat)
                                                > 0) {
                                            convertStringToFloat += 0.1f;
                                        }
                                    } else if (id == pickerCompat.mButtonMinus.getId()
                                            && Float.compare(
                                                            pickerCompat.minFloat,
                                                            convertStringToFloat)
                                                    < 0) {
                                        convertStringToFloat -= 0.1f;
                                    }
                                }
                                pickerCompat.mPickerValue.setText(
                                        StringBuilderUtils.convertDefaultLocaleType(
                                                convertStringToFloat));
                                setOnValueChangeListener setonvaluechangelistener =
                                        pickerCompat.mValueChangeListener;
                                if (setonvaluechangelistener != null) {
                                    setonvaluechangelistener.onValueChange(
                                            Float.toString(
                                                    Float.parseFloat(
                                                            String.format(
                                                                    Locale.US,
                                                                    "%.1f",
                                                                    Float.valueOf(
                                                                            convertStringToFloat)))));
                                }
                                pickerCompat.mCurrentValue =
                                        StringBuilderUtils.convertDefaultLocaleType(
                                                convertStringToFloat);
                            }
                        } else if (this.this$0.mDataType.equalsIgnoreCase("integer")) {
                            PickerCompat pickerCompat2 = this.this$0;
                            int id2 = view.getId();
                            int parseInt =
                                    Integer.parseInt(
                                            String.valueOf(pickerCompat2.mPickerValue.getText()));
                            if (pickerCompat2.maxInt >= parseInt
                                    && pickerCompat2.minInt <= parseInt) {
                                if (id2 == pickerCompat2.mButtonPlus.getId()) {
                                    if (pickerCompat2.maxInt > parseInt) {
                                        parseInt++;
                                    }
                                } else if (id2 == pickerCompat2.mButtonMinus.getId()
                                        && pickerCompat2.minInt < parseInt) {
                                    parseInt--;
                                }
                            }
                            pickerCompat2.mPickerValue.setText(
                                    StringBuilderUtils.convertDefaultLocaleType(parseInt));
                            setOnValueChangeListener setonvaluechangelistener2 =
                                    pickerCompat2.mValueChangeListener;
                            if (setonvaluechangelistener2 != null) {
                                setonvaluechangelistener2.onValueChange(Integer.toString(parseInt));
                            }
                            pickerCompat2.mCurrentValue = Integer.toString(parseInt);
                        }
                    }
                    view.announceForAccessibility(
                            this.this$0.mCurrentValue
                                    + this.this$0
                                            .mContext
                                            .getResources()
                                            .getString(R.string.picker_time_sec));
                    break;
                default:
                    this.this$0.mPickerValue.selectAll();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.base.widget.PickerCompat$6, reason: invalid class name */
    public final class AnonymousClass6 implements View.OnTouchListener {
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(view instanceof EditText)
                    || !view.hasFocus()
                    || motionEvent.getActionMasked() != 0) {
                return false;
            }
            ((EditText) view).selectAll();
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InputTextFilter extends NumberKeyListener {
        public boolean isAutoDecimalPoint = false;

        public InputTextFilter() {}

        @Override // android.text.method.NumberKeyListener, android.text.InputFilter
        public final CharSequence filter(
                CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            String valueOf = String.valueOf(charSequence.subSequence(i, i2));
            String str =
                    String.valueOf(spanned.subSequence(0, i3))
                            + ((Object) valueOf)
                            + ((Object) spanned.subSequence(i4, spanned.length()));
            PickerCompat.this.mPickerValue.setTextDirection(3);
            if (PickerCompat.this.mDataType.equalsIgnoreCase("decimal")) {
                if (this.isAutoDecimalPoint) {
                    if (str.contains("..")) {
                        this.isAutoDecimalPoint = false;
                        return ApnSettings.MVNO_NONE;
                    }
                    if (i2 > 0 && str.charAt(i2 - 1) != '.') {
                        this.isAutoDecimalPoint = false;
                    }
                }
                try {
                    float convertStringToFloat = StringBuilderUtils.convertStringToFloat(str);
                    if (str.length()
                            > ((PickerCompat.this.maxFloat < 10.0f || convertStringToFloat < 10.0f)
                                    ? 3
                                    : 4)) {
                        PickerCompat.this.mPickerValue.requestFocus();
                        PickerCompat.this.mPickerValue.selectAll();
                        return ApnSettings.MVNO_NONE;
                    }
                    if (convertStringToFloat <= 0.0f) {
                        if (valueOf.equals(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) && str.length() == 1) {
                            str = "0.";
                            this.isAutoDecimalPoint = true;
                            PickerCompat.this.mPickerValue.setText("0.");
                            PickerCompat.this.mPickerValue.setSelection(2);
                        }
                        if (str.length() == 3
                                && (Float.compare(convertStringToFloat, PickerCompat.this.maxFloat)
                                                > 0
                                        || Float.compare(
                                                        convertStringToFloat,
                                                        PickerCompat.this.minFloat)
                                                < 0)) {
                            PickerCompat.this.mPickerValue.requestFocus();
                            PickerCompat.this.mPickerValue.selectAll();
                            return ApnSettings.MVNO_NONE;
                        }
                    } else if (Float.compare(convertStringToFloat, PickerCompat.this.maxFloat) > 0
                            || Float.compare(convertStringToFloat, PickerCompat.this.minFloat)
                                    < 0) {
                        PickerCompat.this.mPickerValue.requestFocus();
                        PickerCompat.this.mPickerValue.selectAll();
                        return ApnSettings.MVNO_NONE;
                    }
                } catch (NumberFormatException unused) {
                    PickerCompat pickerCompat = PickerCompat.this;
                    pickerCompat.mPickerValue.setText(pickerCompat.mCurrentValue);
                    PickerCompat.this.mPickerValue.requestFocus();
                    PickerCompat.this.mPickerValue.selectAll();
                    return ApnSettings.MVNO_NONE;
                }
            } else if (PickerCompat.this.mDataType.equalsIgnoreCase("integer")) {
                try {
                    int parseInt = Integer.parseInt(str);
                    PickerCompat pickerCompat2 = PickerCompat.this;
                    if (parseInt <= pickerCompat2.maxInt) {
                        if (parseInt < pickerCompat2.minInt) {}
                    }
                } catch (NumberFormatException unused2) {
                }
                return ApnSettings.MVNO_NONE;
            }
            return valueOf;
        }

        @Override // android.text.method.NumberKeyListener
        public final char[] getAcceptedChars() {
            return new char[0];
        }

        @Override // android.text.method.KeyListener
        public final int getInputType() {
            return 1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface setOnValueChangeListener {
        void onValueChange(String str);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settings.accessibility.base.widget.PickerCompat$1] */
    public PickerCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEditActionListener =
                new TextView
                        .OnEditorActionListener() { // from class:
                                                    // com.samsung.android.settings.accessibility.base.widget.PickerCompat.1
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(
                            TextView textView, int i, KeyEvent keyEvent) {
                        String charSequence = textView.getText().toString();
                        if (i != 6 && (keyEvent == null || keyEvent.getKeyCode() != 66)) {
                            return false;
                        }
                        if (PickerCompat.this.mInputManager.semIsInputMethodShown()) {
                            PickerCompat.this.mInputManager.semForceHideSoftInput();
                        }
                        if (TextUtils.isEmpty(charSequence)) {
                            Utils$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("EditActionListener_PreviousValue::"),
                                    PickerCompat.this.mCurrentValue,
                                    "PickerCompat");
                        } else {
                            if (PickerCompat.this.mDataType.equalsIgnoreCase("decimal")) {
                                if (charSequence.endsWith(".")) {
                                    charSequence =
                                            charSequence.concat(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                } else if (charSequence.startsWith(".")) {
                                    charSequence =
                                            DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.concat(charSequence);
                                } else if (!charSequence.contains(".")) {
                                    try {
                                        charSequence = Integer.parseInt(charSequence) + ".0";
                                    } catch (NumberFormatException unused) {
                                        charSequence = "0.0";
                                    }
                                }
                                try {
                                    if (Float.compare(Float.parseFloat(charSequence), 0.0f) == 0) {
                                        PickerCompat pickerCompat = PickerCompat.this;
                                        pickerCompat.mPickerValue.setText(
                                                pickerCompat.mCurrentValue);
                                        return true;
                                    }
                                } catch (NumberFormatException unused2) {
                                    Log.w("PickerCompat", "Invalid value");
                                    PickerCompat pickerCompat2 = PickerCompat.this;
                                    pickerCompat2.mPickerValue.setText(pickerCompat2.mCurrentValue);
                                    return true;
                                }
                            } else {
                                PickerCompat.this.mDataType.equalsIgnoreCase("integer");
                            }
                            setOnValueChangeListener setonvaluechangelistener =
                                    PickerCompat.this.mValueChangeListener;
                            if (setonvaluechangelistener != null) {
                                setonvaluechangelistener.onValueChange(charSequence);
                            }
                            PickerCompat.this.mCurrentValue =
                                    StringBuilderUtils.convertDefaultLocaleType(charSequence);
                        }
                        PickerCompat pickerCompat3 = PickerCompat.this;
                        pickerCompat3.setDefaultValue(pickerCompat3.mCurrentValue);
                        return true;
                    }
                };
        this.mButtonClickListener = new AnonymousClass2(this, 0);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_picker, this);
        this.mButtonMinus = (ImageButton) findViewById(R.id.picker_dialog_button_minus);
        this.mButtonPlus = (ImageButton) findViewById(R.id.picker_dialog_button_plus);
        this.mPickerContainer = (LinearLayout) findViewById(R.id.picker_container);
        this.mPickerDialogLayout = (LinearLayout) findViewById(R.id.picker_dialog_layout);
        this.mPickerValue = (EditText) findViewById(R.id.picker_dialog_edittext);
        this.mPickerValueRangeText = (TextView) findViewById(R.id.picker_dialog_range_text);
        this.mPickerContainer
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // com.samsung.android.settings.accessibility.base.widget.PickerCompat.3
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                Rect rect = new Rect();
                                PickerCompat.this.mPickerContainer.getWindowVisibleDisplayFrame(
                                        rect);
                                int height =
                                        PickerCompat.this
                                                .mPickerContainer
                                                .getRootView()
                                                .getHeight();
                                if (height - (rect.bottom - rect.top) >= height / 5
                                        || PickerCompat.this.mPickerValue.getText().length() != 0) {
                                    return;
                                }
                                PickerCompat pickerCompat = PickerCompat.this;
                                pickerCompat.mPickerValue.setText(pickerCompat.mCurrentValue);
                                PickerCompat.this.mPickerValue.selectAll();
                            }
                        });
        this.mPickerValue.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.accessibility.base.widget.PickerCompat.4
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        if (z) {
                            return;
                        }
                        PickerCompat pickerCompat = PickerCompat.this;
                        pickerCompat.mPickerValue.setText(pickerCompat.mCurrentValue);
                    }
                });
        this.mPickerDialogLayout.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.samsung.android.settings.accessibility.base.widget.PickerCompat.5
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.setContentDescription(
                                PickerCompat.this.mCurrentValue
                                        + PickerCompat.this
                                                .mContext
                                                .getResources()
                                                .getString(R.string.picker_time_sec));
                        accessibilityNodeInfo.setClassName("android.widget.EditText");
                        accessibilityNodeInfo.setEditable(true);
                    }
                });
    }

    public final void setDefaultValue(String str) {
        this.mCurrentValue = StringBuilderUtils.convertDefaultLocaleType(str);
        float convertStringToFloat = StringBuilderUtils.convertStringToFloat(str);
        if (this.mDataType.equalsIgnoreCase("decimal")) {
            if (Float.compare(this.maxFloat, convertStringToFloat) < 0
                    || Float.compare(this.minFloat, convertStringToFloat) > 0) {
                this.defFloat = 0.5f;
            } else {
                this.defFloat = convertStringToFloat;
            }
            this.mPickerValue.setText(StringBuilderUtils.convertDefaultLocaleType(this.defFloat));
            return;
        }
        if (this.mDataType.equalsIgnoreCase("integer")) {
            int round = Math.round(convertStringToFloat);
            if (round <= this.maxInt || round >= this.minInt) {
                this.defInt = round;
            } else {
                this.defInt = 5;
            }
            this.mPickerValue.setText(StringBuilderUtils.convertDefaultLocaleType(this.defInt));
        }
    }
}
