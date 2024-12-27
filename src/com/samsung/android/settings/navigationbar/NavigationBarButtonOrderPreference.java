package com.samsung.android.settings.navigationbar;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarButtonOrderPreference extends LayoutPreference {
    public boolean mEnabled;
    public RadioButton mNormalMode;
    public LinearLayout mNormalModeContainer;
    public ImageView mNormalModeImg;
    public final NavigationBarButtonOrderPreference$$ExternalSyntheticLambda0
            mOnCheckedChangeListener;
    public final AnonymousClass1 mOnClickListener;
    public RadioButton mReverseMode;
    public LinearLayout mReverseModeContainer;
    public ImageView mReverseModeImg;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.navigationbar.NavigationBarButtonOrderPreference$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.navigationbar.NavigationBarButtonOrderPreference$$ExternalSyntheticLambda0] */
    public NavigationBarButtonOrderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnabled = true;
        this.mOnClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.navigationbar.NavigationBarButtonOrderPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int id = view.getId();
                        if (id == R.id.normal_mode_container) {
                            NavigationBarButtonOrderPreference.this.mNormalMode.setChecked(true);
                            NavigationBarButtonOrderPreference.this.mReverseMode.setChecked(false);
                        } else if (id == R.id.reverse_mode_container) {
                            NavigationBarButtonOrderPreference.this.mNormalMode.setChecked(false);
                            NavigationBarButtonOrderPreference.this.mReverseMode.setChecked(true);
                        }
                    }
                };
        this.mOnCheckedChangeListener =
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.navigationbar.NavigationBarButtonOrderPreference$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        NavigationBarButtonOrderPreference navigationBarButtonOrderPreference =
                                NavigationBarButtonOrderPreference.this;
                        int i =
                                Settings.Global.getInt(
                                        navigationBarButtonOrderPreference
                                                .getContext()
                                                .getContentResolver(),
                                        "navigationbar_key_order",
                                        0);
                        int id = compoundButton.getId();
                        if (id == R.id.normal_mode) {
                            if (z) {
                                if (i != 0) {
                                    Settings.Global.putInt(
                                            navigationBarButtonOrderPreference
                                                    .getContext()
                                                    .getContentResolver(),
                                            "navigationbar_key_order",
                                            0);
                                }
                                LoggingHelper.insertEventLogging(7470, 7483, 0L);
                                return;
                            }
                            return;
                        }
                        if (id == R.id.reverse_mode && z) {
                            if (i != 1) {
                                Settings.Global.putInt(
                                        navigationBarButtonOrderPreference
                                                .getContext()
                                                .getContentResolver(),
                                        "navigationbar_key_order",
                                        1);
                            }
                            LoggingHelper.insertEventLogging(7470, 7483, 1L);
                        }
                    }
                };
    }

    public final void buttonOrderPreferenceEnabled(boolean z) {
        if (KnoxUtils.isApplicationRestricted(getContext(), "button_order", "grayout")) {
            z = false;
        }
        LinearLayout linearLayout = this.mNormalModeContainer;
        if (linearLayout != null) {
            linearLayout.setEnabled(z);
            this.mNormalModeContainer.setAlpha(z ? 1.0f : 0.4f);
        }
        LinearLayout linearLayout2 = this.mReverseModeContainer;
        if (linearLayout2 != null) {
            linearLayout2.setEnabled(z);
            this.mReverseModeContainer.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        int i =
                Settings.Global.getInt(
                        getContext().getContentResolver(), "navigationbar_key_order", 0);
        if (this.mNormalModeContainer == null) {
            LinearLayout linearLayout =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.normal_mode_container);
            this.mNormalModeContainer = linearLayout;
            linearLayout.setOnClickListener(this.mOnClickListener);
        }
        if (this.mReverseModeContainer == null) {
            LinearLayout linearLayout2 =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.reverse_mode_container);
            this.mReverseModeContainer = linearLayout2;
            linearLayout2.setOnClickListener(this.mOnClickListener);
        }
        if (this.mNormalMode == null) {
            RadioButton radioButton =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.normal_mode);
            this.mNormalMode = radioButton;
            radioButton.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        if (this.mReverseMode == null) {
            RadioButton radioButton2 =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.reverse_mode);
            this.mReverseMode = radioButton2;
            radioButton2.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        if (this.mNormalModeImg == null) {
            this.mNormalModeImg =
                    (ImageView) preferenceViewHolder.findViewById(R.id.normal_mode_img);
        }
        if (this.mReverseModeImg == null) {
            this.mReverseModeImg =
                    (ImageView) preferenceViewHolder.findViewById(R.id.reverse_mode_img);
        }
        this.mNormalMode.setChecked(i == 0);
        this.mReverseMode.setChecked(i == 1);
        if (Settings.System.getInt(getContext().getContentResolver(), "display_night_theme", 0)
                == 1) {
            if (Utils.isRTL(getContext())) {
                this.mNormalModeImg.setImageResource(R.drawable.setting_navibar_set_d_01_rtl);
                this.mReverseModeImg.setImageResource(R.drawable.setting_navibar_set_d_02_rtl);
            } else {
                this.mNormalModeImg.setImageResource(R.drawable.setting_navibar_set_d_01);
                this.mReverseModeImg.setImageResource(R.drawable.setting_navibar_set_d_02);
            }
        } else if (Utils.isRTL(getContext())) {
            this.mNormalModeImg.setImageResource(R.drawable.setting_navibar_set_l_01_rtl);
            this.mReverseModeImg.setImageResource(R.drawable.setting_navibar_set_l_02_rtl);
        } else {
            this.mNormalModeImg.setImageResource(R.drawable.setting_navibar_set_l_01);
            this.mReverseModeImg.setImageResource(R.drawable.setting_navibar_set_l_02);
        }
        buttonOrderPreferenceEnabled(this.mEnabled);
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mEnabled = z;
        buttonOrderPreferenceEnabled(z);
    }
}
