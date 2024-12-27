package com.samsung.android.settings.accessibility.base.widget;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.settings.accessibility.base.StringBuilderUtils;
import com.samsung.android.settings.accessibility.base.widget.PickerCompat.InputTextFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PickerFragment extends InstrumentedFragment {
    public String mCurrentVal;
    public int mHorizontalPadding;
    public PickerCompat mPicker;
    public TextView mPickerDescription;
    public View mRootView;
    public Button mTestBtn;
    public TextInputEditText mTestEdit;
    public TextInputLayout mTestEditLayout;

    public int getLayoutId() {
        return R.layout.dexterity_picker_layout;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mCurrentVal = this.mPicker.mPickerValue.getText().toString();
        int applyDimension =
                (int)
                        TypedValue.applyDimension(
                                0,
                                getResources()
                                        .getDimension(
                                                R.dimen.touch_settings_bottom_btn_bottom_padding),
                                getResources().getDisplayMetrics());
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) this.mTestBtn.getLayoutParams();
        marginLayoutParams.setMargins(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                applyDimension);
        this.mTestBtn.setLayoutParams(marginLayoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        this.mHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        this.mPickerDescription =
                (TextView) this.mRootView.findViewById(R.id.picker_dialog_description);
        this.mPicker = (PickerCompat) this.mRootView.findViewById(R.id.picker);
        this.mTestBtn = (Button) this.mRootView.findViewById(R.id.test_btn);
        TextInputLayout textInputLayout =
                (TextInputLayout) this.mRootView.findViewById(R.id.test_edit_text_layout);
        this.mTestEditLayout = textInputLayout;
        TextInputEditText textInputEditText = (TextInputEditText) textInputLayout.editText;
        this.mTestEdit = textInputEditText;
        textInputEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1000)});
        this.mTestEdit.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.accessibility.base.widget.PickerFragment.1
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        if (editable.length() < 1000) {
                            PickerFragment.this.mTestEditLayout.setError(null);
                        } else {
                            PickerFragment pickerFragment = PickerFragment.this;
                            pickerFragment.mTestEditLayout.setError(
                                    pickerFragment
                                            .getResources()
                                            .getQuantityString(
                                                    R.plurals.max_char_reached_msg_plural,
                                                    1000,
                                                    1000));
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        this.mPicker.semSetRoundedCorners(15);
        this.mPicker.semSetRoundedCornerColor(
                15, requireContext().getColor(R.color.rounded_corner_color));
        View view = this.mRootView;
        int i = this.mHorizontalPadding;
        view.setPadding(i, 0, i, 0);
        this.mRootView.setScrollBarStyle(33554432);
        return this.mRootView;
    }

    public final void setButton(String str, View.OnClickListener onClickListener) {
        this.mTestBtn.setText(str);
        this.mTestBtn.setOnClickListener(onClickListener);
        this.mTestBtn.setVisibility(0);
    }

    public final void setSwitchBarPadding(SettingsMainSwitchBar settingsMainSwitchBar) {
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) settingsMainSwitchBar.getLayoutParams();
        int i = this.mHorizontalPadding;
        marginLayoutParams.setMargins(
                i, marginLayoutParams.topMargin, i, marginLayoutParams.bottomMargin);
        settingsMainSwitchBar.setLayoutParams(marginLayoutParams);
    }

    public final void setupPicker(String str, String str2) {
        PickerCompat pickerCompat = this.mPicker;
        pickerCompat.mDataType = "Decimal";
        pickerCompat.mPickerValue.setPrivateImeOptions("inputType=ipAddress;");
        pickerCompat.mPickerValue.setRawInputType(8192);
        PickerCompat pickerCompat2 = this.mPicker;
        if (pickerCompat2.mDataType.equalsIgnoreCase("Decimal")) {
            pickerCompat2.maxFloat = StringBuilderUtils.parseFloat(str2).floatValue();
        } else if (pickerCompat2.mDataType.equalsIgnoreCase("Integer")) {
            pickerCompat2.maxInt = Integer.parseInt(str2);
        }
        PickerCompat pickerCompat3 = this.mPicker;
        if (pickerCompat3.mDataType.equalsIgnoreCase("Decimal")) {
            pickerCompat3.minFloat = StringBuilderUtils.parseFloat("0.1").floatValue();
        } else if (pickerCompat3.mDataType.equalsIgnoreCase("Integer")) {
            pickerCompat3.minInt = Integer.parseInt("0.1");
        }
        PickerCompat pickerCompat4 = this.mPicker;
        TextView textView = pickerCompat4.mPickerValueRangeText;
        if (textView != null) {
            textView.setText(
                    pickerCompat4
                            .mContext
                            .getResources()
                            .getString(
                                    R.string.picker_set_number_range,
                                    StringBuilderUtils.convertNumberToArabicNumber(
                                            StringBuilderUtils.convertDefaultLocaleType("0.1")),
                                    StringBuilderUtils.convertNumberToArabicNumber(
                                            StringBuilderUtils.convertDefaultLocaleType(str2))));
        }
        PickerCompat pickerCompat5 = this.mPicker;
        String str3 = this.mCurrentVal;
        if (str3 != null) {
            str = str3;
        }
        pickerCompat5.setDefaultValue(str);
        this.mCurrentVal = null;
        PickerCompat pickerCompat6 = this.mPicker;
        pickerCompat6.mInputManager =
                (InputMethodManager) pickerCompat6.mContext.getSystemService("input_method");
        pickerCompat6.mPickerValue.setOnEditorActionListener(pickerCompat6.mEditActionListener);
        pickerCompat6.mPickerValue.setOnTouchListener(new PickerCompat.AnonymousClass6());
        pickerCompat6.mPickerValue.setOnClickListener(
                new PickerCompat.AnonymousClass2(pickerCompat6, 1));
        pickerCompat6.mPickerValue.setFilters(
                new InputFilter[] {pickerCompat6.new InputTextFilter()});
        pickerCompat6.mButtonMinus.setOnClickListener(pickerCompat6.mButtonClickListener);
        pickerCompat6.mButtonPlus.setOnClickListener(pickerCompat6.mButtonClickListener);
        PickerCompat pickerCompat7 = this.mPicker;
        pickerCompat7.mPickerContainer.setFocusable(true);
        pickerCompat7.mPickerContainer.setFocusableInTouchMode(true);
        pickerCompat7.mPickerContainer.setContentDescription(
                pickerCompat7.mContext.getResources().getString(R.string.action_menu_edit));
    }
}
