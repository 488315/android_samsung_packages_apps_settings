package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.settings.R;

import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class ChipTextInputComboView extends FrameLayout implements Checkable {
    public final Chip chip;
    public final EditText editText;
    public final TextFormatter watcher;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TextFormatter extends TextWatcherAdapter {
        public TextFormatter() {}

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView chipTextInputComboView = ChipTextInputComboView.this;
                chipTextInputComboView.chip.setText(
                        ChipTextInputComboView.access$100(chipTextInputComboView, "00"));
                return;
            }
            String access$100 =
                    ChipTextInputComboView.access$100(ChipTextInputComboView.this, editable);
            Chip chip = ChipTextInputComboView.this.chip;
            if (TextUtils.isEmpty(access$100)) {
                access$100 = ChipTextInputComboView.access$100(ChipTextInputComboView.this, "00");
            }
            chip.setText(access$100);
        }
    }

    public ChipTextInputComboView(Context context) {
        this(context, null);
    }

    public static String access$100(
            ChipTextInputComboView chipTextInputComboView, CharSequence charSequence) {
        Resources resources = chipTextInputComboView.getResources();
        Parcelable.Creator<TimeModel> creator = TimeModel.CREATOR;
        try {
            return String.format(
                    resources.getConfiguration().locale,
                    "%02d",
                    Integer.valueOf(Integer.parseInt(String.valueOf(charSequence))));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public CharSequence getChipText() {
        return this.chip.getText();
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.chip.isChecked();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.editText.setImeHintLocales(
                getContext().getResources().getConfiguration().getLocales());
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        this.chip.setChecked(z);
        this.editText.setVisibility(z ? 0 : 4);
        this.chip.setVisibility(z ? 8 : 0);
        if (this.chip.isChecked()) {
            final EditText editText = this.editText;
            editText.requestFocus();
            editText.post(
                    new Runnable() { // from class:
                                     // com.google.android.material.internal.ViewUtils$$ExternalSyntheticLambda0
                        public final /* synthetic */ boolean f$1 = false;

                        @Override // java.lang.Runnable
                        public final void run() {
                            View view = editText;
                            if (this.f$1) {
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                WindowInsetsControllerCompat windowInsetsController =
                                        ViewCompat.Api30Impl.getWindowInsetsController(view);
                                if (windowInsetsController != null) {
                                    WindowInsetsControllerCompat.Impl30 impl30 =
                                            windowInsetsController.mImpl;
                                    impl30.mSoftwareKeyboardControllerCompat.mImpl.show();
                                    impl30.mInsetsController.show(0);
                                    return;
                                }
                            }
                            ((InputMethodManager)
                                            view.getContext()
                                                    .getSystemService(InputMethodManager.class))
                                    .showSoftInput(view, 1);
                        }
                    });
        }
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.chip.setOnClickListener(onClickListener);
    }

    @Override // android.view.View
    public final void setTag(int i, Object obj) {
        this.chip.setTag(i, obj);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        this.chip.toggle();
    }

    public ChipTextInputComboView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChipTextInputComboView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater from = LayoutInflater.from(context);
        Chip chip = (Chip) from.inflate(R.layout.material_time_chip, (ViewGroup) this, false);
        this.chip = chip;
        chip.accessibilityClassName = "android.view.View";
        TextInputLayout textInputLayout =
                (TextInputLayout)
                        from.inflate(R.layout.material_time_input, (ViewGroup) this, false);
        EditText editText = textInputLayout.editText;
        this.editText = editText;
        editText.setVisibility(4);
        editText.addTextChangedListener(new TextFormatter());
        editText.setImeHintLocales(getContext().getResources().getConfiguration().getLocales());
        addView(chip);
        addView(textInputLayout);
        TextView textView = (TextView) findViewById(R.id.material_label);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        editText.setId(View.generateViewId());
        textView.setLabelFor(editText.getId());
        editText.setSaveEnabled(false);
        editText.setLongClickable(false);
    }
}
