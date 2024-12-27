package com.samsung.android.settings.eyecomfort;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;
import androidx.picker.widget.SeslTimePicker;
import androidx.picker.widget.SeslTimePickerSpinnerDelegate;
import androidx.preference.Preference;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortTimeSettingDialogFragment extends DialogFragment {
    public static Preference mPreference;
    public EyeComfortTimeSettingDialog mDialog;
    public onTimeDialogDismissListener mTimeDialogDismissListener = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface onTimeDialogDismissListener {}

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i;
        int customTimeToInt = EyeComfortTimeUtils.getCustomTimeToInt(getContext(), true);
        int customTimeToInt2 = EyeComfortTimeUtils.getCustomTimeToInt(getContext(), false);
        if (bundle != null) {
            customTimeToInt = bundle.getInt("StartTime");
            customTimeToInt2 = bundle.getInt("EndTime");
            i = bundle.getInt("Tab");
        } else {
            i = 0;
        }
        Context context = getContext();
        final EyeComfortTimeSettingDialog eyeComfortTimeSettingDialog =
                new EyeComfortTimeSettingDialog(context, R.style.EyeComfortTimePickerDialog);
        eyeComfortTimeSettingDialog.mTimePickerDialog = null;
        eyeComfortTimeSettingDialog.mContext = context;
        eyeComfortTimeSettingDialog.mStartTime = customTimeToInt;
        eyeComfortTimeSettingDialog.mEndTime = customTimeToInt2;
        eyeComfortTimeSettingDialog.mTabIndex = i;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_eye_comfort_time_picker_dialog, (ViewGroup) null);
        eyeComfortTimeSettingDialog.mTimePickerDialog = inflate;
        eyeComfortTimeSettingDialog.setView$1(inflate);
        SeslTimePicker seslTimePicker =
                (SeslTimePicker)
                        eyeComfortTimeSettingDialog.mTimePickerDialog.findViewById(
                                R.id.sec_eye_comfort_time_picker);
        eyeComfortTimeSettingDialog.mTimePicker = seslTimePicker;
        SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = seslTimePicker.mDelegate;
        seslTimePickerSpinnerDelegate.mOnEditTextModeChangedListener = eyeComfortTimeSettingDialog;
        seslTimePickerSpinnerDelegate.mOnTimeChangedListener = eyeComfortTimeSettingDialog;
        boolean is24HourModeEnabled = EyeComfortTimeUtils.is24HourModeEnabled(context);
        eyeComfortTimeSettingDialog.mIs24HourFormat = is24HourModeEnabled;
        eyeComfortTimeSettingDialog.mTimePicker.setIs24HourView(
                Boolean.valueOf(is24HourModeEnabled));
        TabLayout tabLayout =
                (TabLayout)
                        eyeComfortTimeSettingDialog.mTimePickerDialog.findViewById(
                                R.id.sec_eye_comfort_time_picker_tab);
        eyeComfortTimeSettingDialog.mTabLayout = tabLayout;
        tabLayout.seslSetSubTabStyle();
        TabLayout.Tab tabAt =
                eyeComfortTimeSettingDialog.mTabLayout.getTabAt(
                        eyeComfortTimeSettingDialog.mTabIndex);
        if (tabAt != null) {
            tabAt.select();
        }
        eyeComfortTimeSettingDialog.mTabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.eyecomfort.EyeComfortTimeSettingDialog.1
                    public AnonymousClass1() {}

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        EyeComfortTimeSettingDialog eyeComfortTimeSettingDialog2 =
                                EyeComfortTimeSettingDialog.this;
                        eyeComfortTimeSettingDialog2.mTimePicker.mDelegate.setEditTextMode(false);
                        int i2 = tab.position;
                        eyeComfortTimeSettingDialog2.mTabIndex = i2;
                        if (i2 == 0) {
                            eyeComfortTimeSettingDialog2.updatePicker$2(
                                    eyeComfortTimeSettingDialog2.mStartTime);
                        } else if (i2 == 1) {
                            eyeComfortTimeSettingDialog2.updatePicker$2(
                                    eyeComfortTimeSettingDialog2.mEndTime);
                        }
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        eyeComfortTimeSettingDialog.setButton(
                -1,
                context.getResources().getString(R.string.sec_eye_comfort_time_picker_dialog_done),
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.eyecomfort.EyeComfortTimeSettingDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        EyeComfortTimeSettingDialog eyeComfortTimeSettingDialog2 =
                                EyeComfortTimeSettingDialog.this;
                        eyeComfortTimeSettingDialog2.mTimePicker.clearFocus();
                        StringBuilder sb = new StringBuilder("save Time : start time = ");
                        sb.append(eyeComfortTimeSettingDialog2.mStartTime);
                        sb.append(", end time = ");
                        TooltipPopup$$ExternalSyntheticOutline0.m(
                                sb,
                                eyeComfortTimeSettingDialog2.mEndTime,
                                "EyeComfortTimeSettingDialog");
                        Settings.System.putLongForUser(
                                eyeComfortTimeSettingDialog2.mContext.getContentResolver(),
                                "blue_light_filter_on_time",
                                eyeComfortTimeSettingDialog2.mStartTime,
                                -2);
                        Settings.System.putLongForUser(
                                eyeComfortTimeSettingDialog2.mContext.getContentResolver(),
                                "blue_light_filter_off_time",
                                eyeComfortTimeSettingDialog2.mEndTime,
                                -2);
                        LoggingHelper.insertEventLogging(4222, 40205);
                        LoggingHelper.insertEventLogging(
                                4222, 7412, eyeComfortTimeSettingDialog2.mStartTime);
                        LoggingHelper.insertEventLogging(
                                4222, 7413, eyeComfortTimeSettingDialog2.mEndTime);
                    }
                });
        eyeComfortTimeSettingDialog.setButton(
                -2,
                context.getResources().getString(android.R.string.cancel),
                new EyeComfortTimeSettingDialog$$ExternalSyntheticLambda1());
        eyeComfortTimeSettingDialog.updateTimeSubTab(0, eyeComfortTimeSettingDialog.mStartTime);
        eyeComfortTimeSettingDialog.updateTimeSubTab(1, eyeComfortTimeSettingDialog.mEndTime);
        eyeComfortTimeSettingDialog.updatePicker$2(
                eyeComfortTimeSettingDialog.mTabIndex == 0
                        ? eyeComfortTimeSettingDialog.mStartTime
                        : eyeComfortTimeSettingDialog.mEndTime);
        this.mDialog = eyeComfortTimeSettingDialog;
        eyeComfortTimeSettingDialog.getWindow().setSoftInputMode(16);
        setDialogAnchor(mPreference);
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        onTimeDialogDismissListener ontimedialogdismisslistener = this.mTimeDialogDismissListener;
        if (ontimedialogdismisslistener != null) {
            EyeComfortSettings eyeComfortSettings =
                    (EyeComfortSettings) ontimedialogdismisslistener;
            Settings.System.putInt(
                    eyeComfortSettings.getContentResolver(), "blue_light_filter_type", 1);
            eyeComfortSettings.updateTimeSummary();
            eyeComfortSettings.mTimeSettingDialog = null;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        EyeComfortTimeSettingDialog eyeComfortTimeSettingDialog = this.mDialog;
        if (eyeComfortTimeSettingDialog != null) {
            bundle.putInt("StartTime", eyeComfortTimeSettingDialog.mStartTime);
            bundle.putInt("EndTime", this.mDialog.mEndTime);
            bundle.putInt("Tab", this.mDialog.mTabIndex);
        }
        super.onSaveInstanceState(bundle);
    }

    public final void setDialogAnchor(Preference preference) {
        if (preference == null) {
            return;
        }
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        int i = rect.left;
        int m = AbsActionBarView$$ExternalSyntheticOutline0.m(rect.right, i, 2, i);
        int i2 = rect.bottom;
        EyeComfortTimeSettingDialog eyeComfortTimeSettingDialog = this.mDialog;
        if (eyeComfortTimeSettingDialog != null) {
            eyeComfortTimeSettingDialog.semSetAnchor(m, i2);
        }
    }
}
