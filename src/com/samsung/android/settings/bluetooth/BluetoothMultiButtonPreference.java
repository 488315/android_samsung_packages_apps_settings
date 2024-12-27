package com.samsung.android.settings.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailNoiseController$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothMultiButtonPreference extends Preference implements View.OnClickListener {
    public static boolean updateMidBarSizeFlag = false;
    public final int m4IconBg;
    public RelativeLayout mBottomLayout;
    public final MultiButtonItem mBtnDoubleHigh;
    public final MultiButtonItem mBtnHigh;
    public final MultiButtonItem mBtnLow;
    public final MultiButtonItem mBtnMid;
    public final Context mContext;
    public View mCustomView;
    public int mDoubleHigh4Icon;
    public String mDoubleHigh4Text;
    public boolean mDoubleHighEnabled;
    public int mDoubleHighVisible;
    public int mHigh4Icon;
    public String mHigh4Text;
    public boolean mHighEnabled;
    public final int mHighVisible;
    public SecBluetoothDetailNoiseController$$ExternalSyntheticLambda0 mListener;
    public int mLow4Icon;
    public String mLow4Text;
    public boolean mLowEnabled;
    public final int mLowVisible;
    public int mMid4Icon;
    public String mMid4Text;
    public boolean mMidEnabled;
    public final int mMidVisible;
    public boolean mOptionVisible;
    public int selectedPosition;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothMultiButtonPreference$1, reason: invalid class name */
    public final class AnonymousClass1 extends Thread {
        public AnonymousClass1() {}

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            RelativeLayout relativeLayout;
            int i = 1;
            while (true) {
                try {
                    View view = BluetoothMultiButtonPreference.this.mCustomView;
                    if ((view != null && view.getRight() != 0)
                            || ((relativeLayout = BluetoothMultiButtonPreference.this.mBottomLayout)
                                            != null
                                    && relativeLayout.getWidth() != 0)) {
                        break;
                    }
                    if (i > 200) {
                        Log.e(
                                "BluetoothMultiButtonPreference",
                                "mCustomView is abnormal and the waiting counter has exceeded 4000"
                                    + " ms.");
                        break;
                    } else {
                        Thread.sleep(20L);
                        i++;
                    }
                } catch (Exception unused) {
                    Log.e("BluetoothMultiButtonPreference", "Error on updateMidBarSize Thread");
                    BluetoothMultiButtonPreference.updateMidBarSizeFlag = false;
                    return;
                }
            }
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference =
                    BluetoothMultiButtonPreference.this;
            if (bluetoothMultiButtonPreference.mCustomView == null || i > 200) {
                return;
            }
            ((Activity) bluetoothMultiButtonPreference.getContext())
                    .runOnUiThread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.bluetooth.BluetoothMultiButtonPreference.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2;
                                    int i3;
                                    BluetoothMultiButtonPreference.updateMidBarSizeFlag = true;
                                    ViewGroup.LayoutParams layoutParams =
                                            BluetoothMultiButtonPreference.this
                                                    .mCustomView
                                                    .findViewById(R.id.mid_bar)
                                                    .getLayoutParams();
                                    int right =
                                            BluetoothMultiButtonPreference.this.mCustomView
                                                                    .getRight()
                                                            != 0
                                                    ? BluetoothMultiButtonPreference.this
                                                            .mCustomView.getRight()
                                                    : BluetoothMultiButtonPreference.this
                                                            .mBottomLayout.getWidth();
                                    BluetoothMultiButtonPreference bluetoothMultiButtonPreference2 =
                                            BluetoothMultiButtonPreference.this;
                                    if (bluetoothMultiButtonPreference2.mDoubleHighVisible == 0) {
                                        i2 = (right * 44) / 360;
                                        i3 = (right * FileType.DOCM) / 360;
                                    } else {
                                        i2 = (right * 52) / 360;
                                        i3 = (right * 308) / 360;
                                    }
                                    layoutParams.width = (i3 - i2) - 2;
                                    bluetoothMultiButtonPreference2
                                            .mCustomView
                                            .findViewById(R.id.mid_bar)
                                            .setLayoutParams(layoutParams);
                                    BluetoothMultiButtonPreference.this
                                            .mCustomView
                                            .findViewById(R.id.mid_bar)
                                            .requestLayout();
                                    BluetoothMultiButtonPreference.updateMidBarSizeFlag = false;
                                }
                            });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MultiButtonItem {
        public View mButton;
        public RadioButton mDot;
        public ImageView mImgIcon;
        public ImageView mImgIconBg;
        public TextView mTextView;
        public final int selectedColor;
        public final int selectedIcon4Color;
        public final int unSelectedColor;
        public final int unSelectedIcon4Color;

        public MultiButtonItem() {
            this.selectedColor =
                    BluetoothMultiButtonPreference.this.mContext.getColor(
                            R.color.sec_widget_multi_button_selected_color);
            this.unSelectedColor =
                    BluetoothMultiButtonPreference.this.mContext.getColor(
                            R.color.sec_widget_multi_button_unselected_color);
            BluetoothMultiButtonPreference.this.mContext.getColor(
                    R.color.sec_widget_switchbar_checked_background_color);
            this.unSelectedIcon4Color =
                    BluetoothMultiButtonPreference.this.mContext.getColor(
                            R.color.bt_device_icon_circle_stroke_color);
            this.selectedIcon4Color =
                    BluetoothMultiButtonPreference.this.mContext.getColor(
                            R.color.bluetooth_initial_text_color);
        }

        public final void bindView(View view, int i) {
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference =
                    BluetoothMultiButtonPreference.this;
            if (i == 0) {
                this.mButton = view.findViewById(R.id.low_button);
                this.mTextView = (TextView) view.findViewById(R.id.low_button_text);
                ImageView imageView =
                        (ImageView) view.findViewById(R.id.low_button_icon_4_items_bg);
                this.mImgIconBg = imageView;
                imageView.setImageResource(bluetoothMultiButtonPreference.m4IconBg);
                this.mImgIcon = (ImageView) view.findViewById(R.id.low_button_icon_4_items);
                RadioButton radioButton = (RadioButton) view.findViewById(R.id.low_button_dot);
                this.mDot = radioButton;
                radioButton.setVisibility(8);
                return;
            }
            if (i == 1) {
                this.mButton = view.findViewById(R.id.mid_button);
                this.mTextView = (TextView) view.findViewById(R.id.mid_button_text);
                this.mImgIcon = (ImageView) view.findViewById(R.id.mid_button_icon_4_items);
                ImageView imageView2 =
                        (ImageView) view.findViewById(R.id.mid_button_icon_4_items_bg);
                this.mImgIconBg = imageView2;
                imageView2.setImageResource(bluetoothMultiButtonPreference.m4IconBg);
                RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.mid_button_dot);
                this.mDot = radioButton2;
                radioButton2.setVisibility(8);
                return;
            }
            if (i == 2) {
                this.mButton = view.findViewById(R.id.high_button);
                this.mTextView = (TextView) view.findViewById(R.id.high_button_text);
                this.mImgIcon = (ImageView) view.findViewById(R.id.high_button_icon_4_items);
                ImageView imageView3 =
                        (ImageView) view.findViewById(R.id.high_button_icon_4_items_bg);
                this.mImgIconBg = imageView3;
                imageView3.setImageResource(bluetoothMultiButtonPreference.m4IconBg);
                RadioButton radioButton3 = (RadioButton) view.findViewById(R.id.high_button_dot);
                this.mDot = radioButton3;
                radioButton3.setVisibility(8);
                return;
            }
            if (i != 3) {
                Log.e("BluetoothMultiButtonPreference", "Wrong type in bindView()");
                return;
            }
            this.mButton = view.findViewById(R.id.double_high_button);
            this.mTextView = (TextView) view.findViewById(R.id.double_high_button_text);
            this.mImgIcon = (ImageView) view.findViewById(R.id.double_high_button_icon_4_items);
            ImageView imageView4 =
                    (ImageView) view.findViewById(R.id.double_high_button_icon_4_items_bg);
            this.mImgIconBg = imageView4;
            imageView4.setImageResource(bluetoothMultiButtonPreference.m4IconBg);
            RadioButton radioButton4 = (RadioButton) view.findViewById(R.id.double_high_button_dot);
            this.mDot = radioButton4;
            radioButton4.setVisibility(8);
        }

        public final void setSelected(boolean z) {
            this.mTextView.setTextColor(z ? this.selectedColor : this.unSelectedColor);
            this.mTextView.setTypeface(null, z ? 1 : 0);
            this.mImgIcon.setColorFilter(z ? this.selectedIcon4Color : this.unSelectedIcon4Color);
            this.mImgIconBg.setImageResource(
                    z
                            ? R.drawable.sec_bluetooth_icon_background_selected
                            : R.drawable.sec_bluetooth_icon_background_unselected);
            this.mDot.setChecked(z);
        }
    }

    public BluetoothMultiButtonPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                R.layout.sec_bluetooth_preference_multi_button_4_items,
                R.id.bluetooth_multi_button);
        this.mLow4Icon = 0;
        this.mMid4Icon = 0;
        this.mHigh4Icon = 0;
        this.mDoubleHigh4Icon = 0;
        this.m4IconBg = R.drawable.sec_bluetooth_icon_background_unselected;
        this.mLow4Text = ApnSettings.MVNO_NONE;
        this.mMid4Text = ApnSettings.MVNO_NONE;
        this.mHigh4Text = ApnSettings.MVNO_NONE;
        this.mDoubleHigh4Text = ApnSettings.MVNO_NONE;
        this.mLowEnabled = false;
        this.mMidEnabled = false;
        this.mHighEnabled = false;
        this.mDoubleHighEnabled = false;
        this.mLowVisible = 0;
        this.mMidVisible = 0;
        this.mHighVisible = 0;
        this.mDoubleHighVisible = 8;
        this.mOptionVisible = false;
        this.mContext = context;
        setLayoutResource(R.layout.sec_bluetooth_preference_multi_button_4_items);
        this.mBtnLow = new MultiButtonItem();
        this.mBtnMid = new MultiButtonItem();
        this.mBtnHigh = new MultiButtonItem();
        this.mBtnDoubleHigh = new MultiButtonItem();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mCustomView = view;
        this.mBtnLow.bindView(view, 0);
        this.mBtnMid.bindView(this.mCustomView, 1);
        this.mBtnHigh.bindView(this.mCustomView, 2);
        this.mBtnDoubleHigh.bindView(this.mCustomView, 3);
        TextView textView = (TextView) this.mCustomView.findViewById(R.id.option);
        if (textView != null) {
            boolean z = this.mOptionVisible;
            this.mOptionVisible = z;
            if (z) {
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
        this.mCustomView.setClickable(false);
        this.mCustomView.setFocusable(false);
        this.mCustomView.setFocusableInTouchMode(false);
        ImageView imageView =
                (ImageView) this.mCustomView.findViewById(R.id.low_button_icon_4_items);
        ImageView imageView2 =
                (ImageView) this.mCustomView.findViewById(R.id.mid_button_icon_4_items);
        ImageView imageView3 =
                (ImageView) this.mCustomView.findViewById(R.id.high_button_icon_4_items);
        ImageView imageView4 =
                (ImageView) this.mCustomView.findViewById(R.id.double_high_button_icon_4_items);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        MultiButtonItem multiButtonItem = this.mBtnLow;
        multiButtonItem.mTextView.setText(this.mLow4Text);
        MultiButtonItem multiButtonItem2 = this.mBtnMid;
        multiButtonItem2.mTextView.setText(this.mMid4Text);
        MultiButtonItem multiButtonItem3 = this.mBtnHigh;
        multiButtonItem3.mTextView.setText(this.mHigh4Text);
        MultiButtonItem multiButtonItem4 = this.mBtnDoubleHigh;
        multiButtonItem4.mTextView.setText(this.mDoubleHigh4Text);
        MultiButtonItem multiButtonItem5 = this.mBtnLow;
        multiButtonItem5.mButton.setEnabled(this.mLowEnabled);
        MultiButtonItem multiButtonItem6 = this.mBtnMid;
        multiButtonItem6.mButton.setEnabled(this.mMidEnabled);
        MultiButtonItem multiButtonItem7 = this.mBtnHigh;
        multiButtonItem7.mButton.setEnabled(this.mHighEnabled);
        MultiButtonItem multiButtonItem8 = this.mBtnDoubleHigh;
        multiButtonItem8.mButton.setEnabled(this.mDoubleHighEnabled);
        MultiButtonItem multiButtonItem9 = this.mBtnLow;
        multiButtonItem9.mButton.setVisibility(this.mLowVisible);
        MultiButtonItem multiButtonItem10 = this.mBtnMid;
        multiButtonItem10.mButton.setVisibility(this.mMidVisible);
        MultiButtonItem multiButtonItem11 = this.mBtnHigh;
        multiButtonItem11.mButton.setVisibility(this.mHighVisible);
        MultiButtonItem multiButtonItem12 = this.mBtnDoubleHigh;
        multiButtonItem12.mButton.setVisibility(this.mDoubleHighVisible);
        MultiButtonItem multiButtonItem13 = this.mBtnLow;
        multiButtonItem13.mImgIcon.setImageResource(this.mLow4Icon);
        multiButtonItem13.mImgIcon.setVisibility(0);
        MultiButtonItem multiButtonItem14 = this.mBtnMid;
        multiButtonItem14.mImgIcon.setImageResource(this.mMid4Icon);
        multiButtonItem14.mImgIcon.setVisibility(0);
        MultiButtonItem multiButtonItem15 = this.mBtnHigh;
        multiButtonItem15.mImgIcon.setImageResource(this.mHigh4Icon);
        multiButtonItem15.mImgIcon.setVisibility(0);
        MultiButtonItem multiButtonItem16 = this.mBtnDoubleHigh;
        multiButtonItem16.mImgIcon.setImageResource(this.mDoubleHigh4Icon);
        multiButtonItem16.mImgIcon.setVisibility(0);
        View findViewById = this.mCustomView.findViewById(R.id.frame_layout_main);
        if (this.mLowEnabled || this.mMidEnabled || this.mHighEnabled) {
            findViewById.setAlpha(1.0f);
        } else {
            findViewById.setAlpha(0.28f);
        }
        updateButtonStatus$1(this.selectedPosition);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        if (updateMidBarSizeFlag) {
            return;
        }
        View view2 = this.mCustomView;
        if (view2 != null) {
            view2.requestLayout();
        }
        anonymousClass1.start();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.low_button_icon_4_items) {
            this.selectedPosition = 0;
        } else if (view.getId() == R.id.mid_button_icon_4_items) {
            this.selectedPosition = 1;
        } else if (view.getId() == R.id.high_button_icon_4_items) {
            this.selectedPosition = 2;
        } else {
            if (view.getId() != R.id.double_high_button_icon_4_items) {
                Log.d("BluetoothMultiButtonPreference", "onClick: Invalid id");
                return;
            }
            this.selectedPosition = 3;
        }
        if (this.mCustomView != null) {
            updateButtonStatus$1(this.selectedPosition);
        }
        int i = this.selectedPosition;
        SecBluetoothDetailNoiseController$$ExternalSyntheticLambda0
                secBluetoothDetailNoiseController$$ExternalSyntheticLambda0 = this.mListener;
        if (secBluetoothDetailNoiseController$$ExternalSyntheticLambda0 != null) {
            secBluetoothDetailNoiseController$$ExternalSyntheticLambda0.f$0
                    .lambda$setNoiseControlView$0(this, i);
        }
    }

    public final void setButtonEnabled(int i, boolean z) {
        if (i == 0) {
            this.mLowEnabled = z;
        } else if (i == 1) {
            this.mMidEnabled = z;
        } else if (i == 2) {
            this.mHighEnabled = z;
        } else if (i == 3) {
            this.mDoubleHighEnabled = z;
        }
        notifyChanged();
    }

    public final void setSelectedPosition(int i) {
        this.selectedPosition = i;
        if (this.mCustomView != null) {
            updateButtonStatus$1(i);
        }
    }

    public final void updateButtonStatus$1(int i) {
        if (i == 0) {
            this.mBtnLow.setSelected(true);
            this.mBtnMid.setSelected(false);
            this.mBtnHigh.setSelected(false);
            this.mBtnDoubleHigh.setSelected(false);
            return;
        }
        if (i == 1) {
            this.mBtnLow.setSelected(false);
            this.mBtnMid.setSelected(true);
            this.mBtnHigh.setSelected(false);
            this.mBtnDoubleHigh.setSelected(false);
            return;
        }
        if (i == 2) {
            this.mBtnLow.setSelected(false);
            this.mBtnMid.setSelected(false);
            this.mBtnHigh.setSelected(true);
            this.mBtnDoubleHigh.setSelected(false);
            return;
        }
        if (i != 3) {
            return;
        }
        this.mBtnLow.setSelected(false);
        this.mBtnMid.setSelected(false);
        this.mBtnHigh.setSelected(false);
        this.mBtnDoubleHigh.setSelected(true);
    }
}
