package com.android.settings.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.settings.R;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ColorSelectorLayout extends LinearLayout {
    public int mCheckedId;
    public final CheckedStateTracker mChildOnCheckedChangeListener;
    public final List mColorList;
    public OnCheckedChangeListener mOnCheckedChangeListener;
    public final List mRadioButtonResourceIds;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public CheckedStateTracker() {}

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                ColorSelectorLayout colorSelectorLayout = ColorSelectorLayout.this;
                int i = colorSelectorLayout.mCheckedId;
                if (i != -1) {
                    View findViewById = colorSelectorLayout.findViewById(i);
                    if (findViewById instanceof RadioButton) {
                        ((RadioButton) findViewById).setChecked(false);
                    }
                }
                ColorSelectorLayout.this.setCheckedId(compoundButton.getId());
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnCheckedChangeListener {}

    public ColorSelectorLayout(Context context) {
        super(context);
        this.mCheckedId = -1;
        this.mRadioButtonResourceIds =
                Arrays.asList(
                        Integer.valueOf(R.id.color_radio_button_00),
                        Integer.valueOf(R.id.color_radio_button_01),
                        Integer.valueOf(R.id.color_radio_button_02),
                        Integer.valueOf(R.id.color_radio_button_03),
                        Integer.valueOf(R.id.color_radio_button_04),
                        Integer.valueOf(R.id.color_radio_button_05),
                        Integer.valueOf(R.id.color_radio_button_06),
                        Integer.valueOf(R.id.color_radio_button_07),
                        Integer.valueOf(R.id.color_radio_button_08),
                        Integer.valueOf(R.id.color_radio_button_09),
                        Integer.valueOf(R.id.color_radio_button_10),
                        Integer.valueOf(R.id.color_radio_button_11));
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        LinearLayout.inflate(
                ((LinearLayout) this).mContext, R.layout.sec_layout_color_selector, this);
        init();
        this.mColorList =
                (List)
                        Arrays.stream(
                                        ((LinearLayout) this)
                                                .mContext
                                                .getResources()
                                                .getIntArray(
                                                        R.array
                                                                .screen_flash_notification_preset_opacity_colors))
                                .boxed()
                                .collect(Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(int i) {
        this.mCheckedId = i;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            ScreenFlashNotificationColorDialogFragment screenFlashNotificationColorDialogFragment =
                    (ScreenFlashNotificationColorDialogFragment) onCheckedChangeListener;
            screenFlashNotificationColorDialogFragment.getClass();
            screenFlashNotificationColorDialogFragment.mCurrentColor =
                    getCheckedColor(FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR);
            if (screenFlashNotificationColorDialogFragment.mIsPreview.booleanValue()) {
                screenFlashNotificationColorDialogFragment.showColor();
            }
        }
    }

    public final int getCheckedColor(int i) {
        int indexOf = this.mRadioButtonResourceIds.indexOf(Integer.valueOf(this.mCheckedId));
        return (indexOf < 0 || indexOf >= this.mColorList.size())
                ? i
                : ((Integer) this.mColorList.get(indexOf)).intValue();
    }

    public final void init() {
        Iterator it = this.mRadioButtonResourceIds.iterator();
        while (it.hasNext()) {
            RadioButton radioButton = (RadioButton) findViewById(((Integer) it.next()).intValue());
            if (radioButton != null) {
                radioButton.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
            }
        }
    }

    public void setCheckedColor(int i) {
        int indexOf = this.mColorList.indexOf(Integer.valueOf(i));
        int intValue =
                (indexOf < 0 || indexOf >= this.mRadioButtonResourceIds.size())
                        ? -1
                        : ((Integer) this.mRadioButtonResourceIds.get(indexOf)).intValue();
        if (intValue == -1 || intValue != this.mCheckedId) {
            int i2 = this.mCheckedId;
            if (i2 != -1) {
                View findViewById = findViewById(i2);
                if (findViewById instanceof RadioButton) {
                    ((RadioButton) findViewById).setChecked(false);
                }
            }
            if (intValue != -1) {
                View findViewById2 = findViewById(intValue);
                if (findViewById2 instanceof RadioButton) {
                    ((RadioButton) findViewById2).setChecked(true);
                }
            }
            setCheckedId(intValue);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public ColorSelectorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCheckedId = -1;
        this.mRadioButtonResourceIds =
                Arrays.asList(
                        Integer.valueOf(R.id.color_radio_button_00),
                        Integer.valueOf(R.id.color_radio_button_01),
                        Integer.valueOf(R.id.color_radio_button_02),
                        Integer.valueOf(R.id.color_radio_button_03),
                        Integer.valueOf(R.id.color_radio_button_04),
                        Integer.valueOf(R.id.color_radio_button_05),
                        Integer.valueOf(R.id.color_radio_button_06),
                        Integer.valueOf(R.id.color_radio_button_07),
                        Integer.valueOf(R.id.color_radio_button_08),
                        Integer.valueOf(R.id.color_radio_button_09),
                        Integer.valueOf(R.id.color_radio_button_10),
                        Integer.valueOf(R.id.color_radio_button_11));
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        LinearLayout.inflate(
                ((LinearLayout) this).mContext, R.layout.sec_layout_color_selector, this);
        init();
        this.mColorList =
                (List)
                        Arrays.stream(
                                        ((LinearLayout) this)
                                                .mContext
                                                .getResources()
                                                .getIntArray(
                                                        R.array
                                                                .screen_flash_notification_preset_opacity_colors))
                                .boxed()
                                .collect(Collectors.toList());
    }
}
