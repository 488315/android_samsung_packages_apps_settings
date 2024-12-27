package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.picker.widget.SeslTimePicker;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EyeComfortTimeSettingDialog extends AlertDialog
        implements SeslTimePicker.OnTimeChangedListener,
                SeslTimePicker.OnEditTextModeChangedListener {
    public Context mContext;
    public int mEndTime;
    public boolean mIs24HourFormat;
    public int mStartTime;
    public int mTabIndex;
    public TabLayout mTabLayout;
    public SeslTimePicker mTimePicker;
    public View mTimePickerDialog;

    @Override // androidx.picker.widget.SeslTimePicker.OnTimeChangedListener
    public final void onTimeChanged(int i, int i2) {
        int i3 = (i * 60) + i2;
        int i4 = this.mTabIndex;
        if (i4 == 0) {
            this.mStartTime = i3;
        } else {
            this.mEndTime = i3;
        }
        updateTimeSubTab(i4, i3);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        View view;
        super.onWindowFocusChanged(z);
        if (!z || (view = this.mTimePickerDialog) == null) {
            return;
        }
        view.requestLayout();
    }

    public final void updatePicker$2(int i) {
        this.mTimePicker.setHour(i / 60);
        this.mTimePicker.setMinute(i % 60);
    }

    public final void updateTimeSubTab(int i, int i2) {
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(this.mIs24HourFormat ? 11 : 10, i3);
        calendar.set(12, i4);
        sb.append(
                DateFormat.getTimeFormat(this.mContext)
                        .format(new Date(calendar.getTimeInMillis())));
        TabLayout.Tab tabAt = this.mTabLayout.getTabAt(i);
        tabAt.subText = sb;
        tabAt.updateView();
    }
}
