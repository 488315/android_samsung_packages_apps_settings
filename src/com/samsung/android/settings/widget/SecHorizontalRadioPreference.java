package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.android.settings.R$styleable;
import com.samsung.android.util.SemLog;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecHorizontalRadioPreference extends Preference {
    public final int SELECTED_COLOR;
    public final int UNSELECTED_COLOR;
    public ViewGroup mContainerLayout;
    public final MutableLiveData mCurrentValue;
    public boolean mIsColorFilterEnabled;
    public boolean mIsDividerEnabled;
    public boolean mIsIconTitleColorEnabled;
    public final HashMap mIsItemEnabledMap;
    public boolean mIsTouchEffectEnabled;
    public SecHorizontalRadioPreference$$ExternalSyntheticLambda3 mObserver;
    public RadioItemDataInfo mRadioItemDataInfo;
    public final int mType;
    public int paddingBottom;
    public int paddingStartEnd;
    public int paddingTop;

    public SecHorizontalRadioPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.SELECTED_COLOR = getContext().getColor(R.color.sec_widget_multi_button_selected_icon_color);
        this.UNSELECTED_COLOR = getContext().getColor(R.color.sec_widget_multi_button_unselected_icon_color);
        this.mIsItemEnabledMap = new HashMap();
        this.mCurrentValue = new MutableLiveData();
        this.mIsDividerEnabled = true;
        this.mIsColorFilterEnabled = false;
        this.mIsTouchEffectEnabled = true;
        this.mIsIconTitleColorEnabled = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        this.mType = obtainStyledAttributes.getInt(42, 0);
        obtainStyledAttributes.recycle();
        setSelectable(false);
        this.paddingStartEnd = (int) TypedValue.applyDimension(0, getContext().getResources().getDimension(R.dimen.sec_widget_multi_btn_preference_padding_start_end), getContext().getResources().getDisplayMetrics());
        this.paddingTop = (int) TypedValue.applyDimension(0, getContext().getResources().getDimension(R.dimen.sec_widget_multi_btn_preference_padding_top), getContext().getResources().getDisplayMetrics());
        this.paddingBottom = (int) TypedValue.applyDimension(0, getContext().getResources().getDimension(R.dimen.sec_widget_multi_radio_btn_preference_padding_bottom), getContext().getResources().getDisplayMetrics());
        setLayoutResource(R.layout.sec_radio_button_layout_horizontal);
    }

    public String getCurrentValue$1() {
        return (String) this.mCurrentValue.getValue();
    }

    public void invalidate(PreferenceViewHolder preferenceViewHolder) {
        int i;
        TextView textView = null;
        int i2 = 0;
        TextView textView2 = null;
        ImageView imageView = null;
        for (String str : this.mRadioItemDataInfo.mItemValueList) {
            View view = preferenceViewHolder.itemView;
            if (i2 == 0) {
                i = R.id.item1;
            } else if (i2 == 1) {
                i = R.id.item2;
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Out of index");
                }
                i = R.id.item3;
            }
            ViewGroup viewGroup = (ViewGroup) view.findViewById(i);
            RadioButton radioButton = (RadioButton) viewGroup.findViewById(R.id.radio_button);
            boolean equals = TextUtils.equals(str, (CharSequence) this.mCurrentValue.getValue());
            int i3 = this.mType;
            if (i3 == 1) {
                textView = (TextView) viewGroup.findViewById(R.id.title);
                textView2 = (TextView) viewGroup.findViewById(R.id.sub_title);
                viewGroup.findViewById(R.id.text_frame).setVisibility(0);
                textView.setTypeface(Typeface.create(Typeface.create("sec", 0), equals ? 600 : 400, false));
            } else if (i3 == 0) {
                ImageView imageView2 = (ImageView) viewGroup.findViewById(R.id.icon);
                TextView textView3 = (TextView) viewGroup.findViewById(R.id.icon_title);
                viewGroup.findViewById(R.id.image_frame).setVisibility(0);
                Typeface create = Typeface.create("sec", 0);
                if (this.mIsIconTitleColorEnabled) {
                    textView3.setTypeface(Typeface.create(create, equals ? 600 : 400, false));
                } else {
                    textView3.setTypeface(Typeface.create(create, 600, false));
                }
                imageView = imageView2;
                textView = textView3;
            }
            radioButton.setChecked(equals);
            if (!this.mIsTouchEffectEnabled) {
                radioButton.jumpDrawablesToCurrentState();
            }
            if (textView != null) {
                textView.setSelected(equals);
            }
            if (textView2 != null) {
                textView2.setSelected(equals);
            }
            if (this.mIsColorFilterEnabled && imageView != null) {
                imageView.setColorFilter(equals ? this.SELECTED_COLOR : this.UNSELECTED_COLOR);
            }
            boolean z = this.mIsItemEnabledMap.get(str) != Boolean.FALSE;
            viewGroup.setEnabled(z);
            viewGroup.setAlpha(z ? 1.0f : 0.6f);
            i2++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.lifecycle.Observer, com.samsung.android.settings.widget.SecHorizontalRadioPreference$$ExternalSyntheticLambda3] */
    @Override // androidx.preference.Preference
    public void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        int i;
        int i2;
        super.onBindViewHolder(preferenceViewHolder);
        RadioItemDataInfo radioItemDataInfo = this.mRadioItemDataInfo;
        if (radioItemDataInfo != null) {
            i = radioItemDataInfo.mItemValueList.size();
            if (i > 3) {
                throw new IllegalArgumentException("Out of index");
            }
        } else {
            i = 0;
        }
        ViewGroup viewGroup = (ViewGroup) preferenceViewHolder.itemView.findViewById(R.id.sec_radio_layout);
        this.mContainerLayout = viewGroup;
        ((SecHorizontalRadioViewContainer) viewGroup).mIsDividerEnabled = Boolean.valueOf(this.mIsDividerEnabled);
        RadioItemDataInfo radioItemDataInfo2 = this.mRadioItemDataInfo;
        if (radioItemDataInfo2 != null) {
            int i3 = 0;
            for (final String str : radioItemDataInfo2.mItemValueList) {
                ViewGroup viewGroup2 = this.mContainerLayout;
                if (i3 == 0) {
                    i2 = R.id.item1;
                } else if (i3 == 1) {
                    i2 = R.id.item2;
                } else {
                    if (i3 != 2) {
                        throw new IllegalArgumentException("Out of index");
                    }
                    i2 = R.id.item3;
                }
                ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(i2);
                int i4 = this.mType;
                if (i4 == 1) {
                    ((TextView) viewGroup3.findViewById(R.id.title)).setText((CharSequence) this.mRadioItemDataInfo.mTitleList.get(i3));
                    ((TextView) viewGroup3.findViewById(R.id.sub_title)).setText((CharSequence) this.mRadioItemDataInfo.mSubTitleList.get(i3));
                    viewGroup3.findViewById(R.id.text_frame).setVisibility(0);
                } else if (i4 == 0) {
                    ((ImageView) viewGroup3.findViewById(R.id.icon)).setImageResource(((Integer) this.mRadioItemDataInfo.mImageList.get(i3)).intValue());
                    ((TextView) viewGroup3.findViewById(R.id.icon_title)).setText((CharSequence) this.mRadioItemDataInfo.mTitleList.get(i3));
                    if (this.mRadioItemDataInfo.mTitleColorList != null) {
                        ((TextView) viewGroup3.findViewById(R.id.icon_title)).setTextColor(((Integer) this.mRadioItemDataInfo.mTitleColorList.get(i3)).intValue());
                    }
                    viewGroup3.findViewById(R.id.image_frame).setVisibility(0);
                }
                viewGroup3.setVisibility(0);
                if (!this.mIsTouchEffectEnabled) {
                    viewGroup3.setBackground(null);
                }
                viewGroup3.setOnTouchListener(new View.OnTouchListener() { // from class: com.samsung.android.settings.widget.SecHorizontalRadioPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        SecHorizontalRadioPreference secHorizontalRadioPreference = SecHorizontalRadioPreference.this;
                        secHorizontalRadioPreference.getClass();
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            if (secHorizontalRadioPreference.mIsTouchEffectEnabled) {
                                return false;
                            }
                            view.setAlpha(0.6f);
                            return false;
                        }
                        if (action == 1) {
                            if (!secHorizontalRadioPreference.mIsTouchEffectEnabled) {
                                view.setAlpha(1.0f);
                            }
                            view.callOnClick();
                            return false;
                        }
                        if (action != 3 || secHorizontalRadioPreference.mIsTouchEffectEnabled) {
                            return false;
                        }
                        view.setAlpha(1.0f);
                        return false;
                    }
                });
                viewGroup3.setOnKeyListener(new View.OnKeyListener() { // from class: com.samsung.android.settings.widget.SecHorizontalRadioPreference$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnKeyListener
                    public final boolean onKey(View view, int i5, KeyEvent keyEvent) {
                        SecHorizontalRadioPreference secHorizontalRadioPreference = SecHorizontalRadioPreference.this;
                        secHorizontalRadioPreference.getClass();
                        if (i5 != 66 && i5 != 160 && i5 != 23) {
                            return false;
                        }
                        int action = keyEvent.getAction();
                        if (action == 0) {
                            if (!secHorizontalRadioPreference.mIsTouchEffectEnabled) {
                                view.setAlpha(0.6f);
                            }
                            return true;
                        }
                        if (action != 1) {
                            return false;
                        }
                        if (!secHorizontalRadioPreference.mIsTouchEffectEnabled) {
                            view.setAlpha(1.0f);
                        }
                        view.playSoundEffect(0);
                        view.callOnClick();
                        return false;
                    }
                });
                viewGroup3.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.widget.SecHorizontalRadioPreference$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecHorizontalRadioPreference.this.setValue(str);
                    }
                });
                int i5 = this.paddingStartEnd;
                if (!this.mIsDividerEnabled) {
                    i5 = Math.round(i5 / 2.0f);
                }
                if (i3 == 0) {
                    viewGroup3.setPadding(this.paddingStartEnd, this.paddingTop, i5, this.paddingBottom);
                } else if (i3 == i - 1) {
                    viewGroup3.setPadding(i5, this.paddingTop, this.paddingStartEnd, this.paddingBottom);
                } else {
                    viewGroup3.setPadding(i5, this.paddingTop, i5, this.paddingBottom);
                }
                i3++;
            }
        }
        this.mCurrentValue.removeObserver(this.mObserver);
        MutableLiveData mutableLiveData = this.mCurrentValue;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) getContext();
        ?? r2 = new Observer() { // from class: com.samsung.android.settings.widget.SecHorizontalRadioPreference$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                SecHorizontalRadioPreference secHorizontalRadioPreference = SecHorizontalRadioPreference.this;
                secHorizontalRadioPreference.invalidate(preferenceViewHolder2);
                secHorizontalRadioPreference.callChangeListener(secHorizontalRadioPreference.mCurrentValue.getValue());
            }
        };
        this.mObserver = r2;
        mutableLiveData.observe(lifecycleOwner, r2);
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        super.onDetached();
        SecHorizontalRadioPreference$$ExternalSyntheticLambda3 secHorizontalRadioPreference$$ExternalSyntheticLambda3 = this.mObserver;
        if (secHorizontalRadioPreference$$ExternalSyntheticLambda3 != null) {
            this.mCurrentValue.removeObserver(secHorizontalRadioPreference$$ExternalSyntheticLambda3);
        }
    }

    public void setEntryEnabled(String str, boolean z) {
        this.mIsItemEnabledMap.put(str, Boolean.valueOf(z));
    }

    public void setRadioItemDataInfo(RadioItemDataInfo radioItemDataInfo) {
        if (radioItemDataInfo == null) {
            SemLog.d("SecHorizontalRadioPreference", "There are no ItemDataInfo for RadioItems");
        } else {
            this.mRadioItemDataInfo = radioItemDataInfo;
        }
    }

    public void setValue(String str) {
        if (TextUtils.equals(str, (CharSequence) this.mCurrentValue.getValue())) {
            return;
        }
        this.mCurrentValue.setValue(str);
    }
}
