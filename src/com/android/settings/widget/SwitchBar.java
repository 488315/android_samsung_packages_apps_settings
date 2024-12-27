package com.android.settings.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public static final int[] XML_ATTRIBUTES = {
        R.attr.switchBarMarginStart,
        R.attr.switchBarMarginEnd,
        R.attr.switchBarBackgroundColor,
        R.attr.switchBarBackgroundActivatedColor,
        R.attr.switchBarRestrictionIcon
    };
    public final LinearLayout mBackground;
    public final int mBackgroundActivatedColor;
    public final int mBackgroundColor;
    public final SwitchBarDelegate mDelegate;
    public boolean mDisabledByAdmin;
    public final View mDivider;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final boolean mIsLightTheme;
    public String mLabel;
    public boolean mLoggingIntialized;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public String mMetricsTag;
    public final String mOffText;
    public final String mOnText;
    public final View mProgressBar;
    public final ImageView mRestrictedIcon;
    public String mSummary;
    public final TextAppearanceSpan mSummarySpan;
    public final ToggleSwitch mSwitch;
    public final List mSwitchChangeListeners;
    public final TextView mTextView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public boolean checked;
        public boolean visible;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.widget.SwitchBar$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState(parcel);
                savedState.checked = ((Boolean) parcel.readValue(null)).booleanValue();
                savedState.visible = ((Boolean) parcel.readValue(null)).booleanValue();
                return savedState;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SwitchBar.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" checked=");
            sb.append(this.checked);
            sb.append(" visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.visible, "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
                  // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.checked));
            parcel.writeValue(Boolean.valueOf(this.visible));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SwitchBarDelegate extends AccessibilityDelegateCompat {
        public String mSessionName;
        public ToggleSwitch mSwitch;
        public TextView mText;

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            String string =
                    view.getContext()
                            .getResources()
                            .getString(
                                    this.mSwitch.isChecked()
                                            ? R.string.switch_on_text
                                            : R.string.switch_off_text);
            StringBuilder sb = new StringBuilder();
            CharSequence text = this.mText.getText();
            if (!TextUtils.isEmpty(this.mSessionName)) {
                sb.append(this.mSessionName);
                sb.append(", ");
            }
            if (!TextUtils.equals(string, text) && !TextUtils.isEmpty(text)) {
                sb.append(text);
                sb.append(", ");
            }
            sb.append(string);
            if (view instanceof ToggleSwitch) {
                accessibilityNodeInfoCompat.setText(sb.toString());
            } else if (view instanceof LinearLayout) {
                String string2 = view.getContext().getResources().getString(R.string.sec_switch);
                sb.append(", ");
                sb.append(string2);
                view.setContentDescription(sb.toString());
            }
        }
    }

    public SwitchBar(Context context) {
        this(context, null);
    }

    private String getActivityTitle() {
        for (Context context = getContext();
                context instanceof ContextWrapper;
                context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                CharSequence title = ((Activity) context).getTitle();
                return title != null ? title.toString() : ApnSettings.MVNO_NONE;
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public View getDelegatingView() {
        return this.mDisabledByAdmin ? this.mRestrictedIcon : this.mSwitch;
    }

    public View getDivider() {
        return this.mDivider;
    }

    public final ToggleSwitch getSwitch() {
        return this.mSwitch;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mLoggingIntialized) {
            this.mMetricsFeatureProvider.action(
                    0,
                    853,
                    0,
                    z ? 1 : 0,
                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), this.mMetricsTag, "/switch_bar"));
        }
        this.mLoggingIntialized = true;
        int size = ((ArrayList) this.mSwitchChangeListeners).size();
        for (int i = 0; i < size; i++) {
            SwitchBar$$ExternalSyntheticLambda0 switchBar$$ExternalSyntheticLambda0 =
                    (SwitchBar$$ExternalSyntheticLambda0)
                            ((ArrayList) this.mSwitchChangeListeners).get(i);
            switchBar$$ExternalSyntheticLambda0.getClass();
            switchBar$$ExternalSyntheticLambda0.f$0.setTextViewLabelAndBackground(z);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setCheckedInternal(savedState.checked);
        setTextViewLabelAndBackground(savedState.checked);
        setVisibility(savedState.visible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.visible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.mSwitch.isChecked();
        savedState.visible = getVisibility() == 0;
        return savedState;
    }

    @Override // android.view.View
    public final boolean performClick() {
        return getDelegatingView().performClick();
    }

    public void setChecked(boolean z) {
        setTextViewLabelAndBackground(z);
        this.mSwitch.setChecked(z);
    }

    public void setCheckedInternal(boolean z) {
        setTextViewLabelAndBackground(z);
        this.mSwitch.setCheckedInternal(z);
    }

    public void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        this.mEnforcedAdmin = enforcedAdmin;
        if (enforcedAdmin == null) {
            this.mDisabledByAdmin = false;
            this.mSwitch.setVisibility(0);
            this.mRestrictedIcon.setVisibility(8);
            setEnabled(true);
            return;
        }
        super.setEnabled(true);
        this.mDisabledByAdmin = true;
        this.mTextView.setEnabled(false);
        this.mSwitch.setEnabled(false);
        this.mSwitch.setVisibility(8);
        this.mRestrictedIcon.setVisibility(0);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (z && this.mDisabledByAdmin) {
            setDisabledByAdmin(null);
            return;
        }
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        setTextViewLabelAndBackground(this.mSwitch.isChecked());
    }

    public void setMetricsTag(String str) {
        this.mMetricsTag = str;
    }

    public void setProgressBarVisible(boolean z) {
        try {
            this.mProgressBar.setVisibility(z ? 0 : 8);
        } catch (IndexOutOfBoundsException e) {
            Log.i("SetProgressBarVisible", "Invalid argument" + e);
        }
    }

    public void setSessionDescription(String str) {
        this.mDelegate.mSessionName = str;
    }

    public void setSummary(String str) {
        this.mSummary = str;
        updateText();
    }

    public void setTextViewLabel(boolean z) {
        this.mLabel =
                getResources().getString(z ? R.string.switch_on_text : R.string.switch_off_text);
        updateText();
    }

    public void setTextViewLabelAndBackground(boolean z) {
        this.mLabel = z ? this.mOnText : this.mOffText;
        int i =
                z
                        ? R.color.sec_widget_switchbar_on_text_color
                        : R.color.sec_widget_switchbar_off_text_color;
        this.mBackground
                .getBackground()
                .setTint(z ? this.mBackgroundActivatedColor : this.mBackgroundColor);
        this.mTextView.setTextColor(getResources().getColor(i));
        if (isEnabled()) {
            this.mTextView.setAlpha(1.0f);
        } else if (z && this.mIsLightTheme) {
            this.mTextView.setAlpha(0.55f);
        } else {
            this.mTextView.setAlpha(0.4f);
        }
        updateText();
    }

    public final void updateText() {
        if (TextUtils.isEmpty(this.mSummary)) {
            this.mTextView.setText(this.mLabel);
            return;
        }
        SpannableStringBuilder append = new SpannableStringBuilder(this.mLabel).append('\n');
        int length = append.length();
        append.append((CharSequence) this.mSummary);
        append.setSpan(this.mSummarySpan, length, append.length(), 0);
        this.mTextView.setText(append);
    }

    public SwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SwitchBar(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ArrayList arrayList = new ArrayList();
        this.mSwitchChangeListeners = arrayList;
        this.mEnforcedAdmin = null;
        LayoutInflater.from(context).inflate(R.layout.sec_switch_bar, this);
        setOrientation(1);
        setFocusable(true);
        setClickable(true);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, XML_ATTRIBUTES);
        int dimension = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        int dimension2 = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.mBackgroundColor =
                getResources().getColor(R.color.sec_widget_switchbar_background_color);
        this.mBackgroundActivatedColor =
                getResources().getColor(R.color.sec_widget_switchbar_checked_background_color);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        this.mIsLightTheme = typedValue.data != 0;
        Drawable drawable = obtainStyledAttributes.getDrawable(4);
        obtainStyledAttributes.recycle();
        TextView textView = (TextView) findViewById(R.id.switch_text);
        this.mTextView = textView;
        this.mSummarySpan = new TextAppearanceSpan(((LinearLayout) this).mContext, 2132084176);
        ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMarginStart(dimension);
        ToggleSwitch toggleSwitch = (ToggleSwitch) findViewById(R.id.switch_widget);
        this.mSwitch = toggleSwitch;
        toggleSwitch.setSaveEnabled(false);
        toggleSwitch.setFocusable(false);
        toggleSwitch.setClickable(false);
        ((ViewGroup.MarginLayoutParams) toggleSwitch.getLayoutParams()).setMarginEnd(dimension2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.switch_background);
        this.mBackground = linearLayout;
        linearLayout.setBackgroundResource(R.drawable.sec_switchbar_background);
        linearLayout.setOnClickListener(
                new View.OnClickListener() { // from class: com.android.settings.widget.SwitchBar.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SwitchBar switchBar = SwitchBar.this;
                        if (switchBar.mDisabledByAdmin) {
                            ImageView imageView = switchBar.mRestrictedIcon;
                            if (imageView != null) {
                                imageView.callOnClick();
                                return;
                            }
                            return;
                        }
                        ToggleSwitch toggleSwitch2 = switchBar.mSwitch;
                        if (toggleSwitch2 == null || !toggleSwitch2.isEnabled()) {
                            return;
                        }
                        SwitchBar.this.mSwitch.setChecked(!r1.isChecked());
                    }
                });
        this.mOnText = getResources().getString(R.string.switch_on_text);
        this.mOffText = getResources().getString(R.string.switch_off_text);
        setTextViewLabelAndBackground(toggleSwitch.isChecked());
        SwitchBar$$ExternalSyntheticLambda0 switchBar$$ExternalSyntheticLambda0 =
                new SwitchBar$$ExternalSyntheticLambda0(this);
        if (arrayList.contains(switchBar$$ExternalSyntheticLambda0)) {
            Log.w(
                    "SwitchBar",
                    "Cannot add twice the same OnSwitchChangeListener : "
                            + switchBar$$ExternalSyntheticLambda0);
        } else {
            arrayList.add(switchBar$$ExternalSyntheticLambda0);
        }
        ImageView imageView = (ImageView) findViewById(R.id.restricted_icon);
        this.mRestrictedIcon = imageView;
        imageView.setImageDrawable(drawable);
        imageView.setOnClickListener(
                new View.OnClickListener() { // from class: com.android.settings.widget.SwitchBar.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SwitchBar switchBar = SwitchBar.this;
                        if (switchBar.mDisabledByAdmin) {
                            switchBar.mMetricsFeatureProvider.action(
                                    0,
                                    853,
                                    0,
                                    1,
                                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                            new StringBuilder(),
                                            SwitchBar.this.mMetricsTag,
                                            "/switch_bar|restricted"));
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    context, SwitchBar.this.mEnforcedAdmin);
                        }
                    }
                });
        this.mProgressBar = findViewById(R.id.scanning_progress);
        this.mDivider = findViewById(R.id.sub_app_bar_divider);
        setVisibility(8);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
            SwitchBarDelegate switchBarDelegate = new SwitchBarDelegate();
            switchBarDelegate.mSessionName = ApnSettings.MVNO_NONE;
            switchBarDelegate.mText = (TextView) findViewById(R.id.switch_text);
            switchBarDelegate.mSwitch = (ToggleSwitch) findViewById(R.id.switch_widget);
            this.mDelegate = switchBarDelegate;
            ViewCompat.setAccessibilityDelegate(linearLayout, switchBarDelegate);
            ViewCompat.setAccessibilityDelegate(toggleSwitch, switchBarDelegate);
            return;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }
}
