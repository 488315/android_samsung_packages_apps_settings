package com.samsung.android.settings.autopoweronoff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.preference.SecPreference;

import com.android.settings.R;

import java.text.DateFormatSymbols;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoPowerOnOffDaysPreference extends SecPreference
        implements DialogInterface.OnClickListener {
    public AlertDialog mAlertDialog;
    public final AutoPowerOnOffSettings.DaysOfWeek mDaysOfWeek;
    public AnonymousClass1 mDaysSelection;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.autopoweronoff.AutoPowerOnOffDaysPreference$1, reason: invalid class name */
    public final class AnonymousClass1 extends ScrollView {
        public final int[] DAYS;
        public final CharSequence[] mDayList;
        public final CheckBox[] mDaysCheckBox;
        public boolean mDisableListeners;
        public final LinearLayout mLinearLayout;
        public final boolean[] mSelectedDays;
        public final AutoPowerOnOffSettings.DaysOfWeek mSelectionDaysOfWeek;

        public AnonymousClass1(Context context, AutoPowerOnOffSettings.DaysOfWeek daysOfWeek) {
            super(context);
            this.DAYS = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
            this.mDaysCheckBox = new CheckBox[8];
            AutoPowerOnOffSettings.DaysOfWeek daysOfWeek2 =
                    new AutoPowerOnOffSettings.DaysOfWeek(0);
            this.mSelectionDaysOfWeek = daysOfWeek2;
            daysOfWeek2.mDays = daysOfWeek.mDays;
            LinearLayout linearLayout = new LinearLayout(context);
            this.mLinearLayout = linearLayout;
            linearLayout.setPadding(0, 0, 0, 0);
            linearLayout.setOrientation(1);
            addView(linearLayout);
            boolean[] zArr = new boolean[8];
            for (int i = 1; i < 8; i++) {
                zArr[i] = (daysOfWeek2.mDays & (1 << i)) > 0;
            }
            this.mSelectedDays = zArr;
            this.mDayList =
                    context.getResources().getStringArray(R.array.sec_auto_power_repeat_entry);
            LayoutInflater from = LayoutInflater.from(context);
            int i2 = 0;
            while (true) {
                int[] iArr = this.DAYS;
                if (i2 >= iArr.length) {
                    return;
                }
                final int i3 = iArr[i2];
                LinearLayout linearLayout2 =
                        (LinearLayout)
                                from.inflate(
                                        R.layout.sec_auto_power_on_off_days_item,
                                        (ViewGroup) this,
                                        false);
                CheckBox checkBox = (CheckBox) linearLayout2.findViewById(R.id.day_item_checkbox);
                ((LinearLayout) linearLayout2.findViewById(R.id.day_item_layout))
                        .setOnClickListener(
                                new AutoPowerOnOffDaysPreference$AutoPowerOnOffDaysSelection$1());
                if (i2 != 0) {
                    checkBox.setText(this.mDayList[i3]);
                    checkBox.setChecked(this.mSelectedDays[i3]);
                    checkBox.setOnCheckedChangeListener(
                            new CompoundButton
                                    .OnCheckedChangeListener() { // from class:
                                                                 // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffDaysPreference$AutoPowerOnOffDaysSelection$2
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(
                                        CompoundButton compoundButton, boolean z) {
                                    AutoPowerOnOffDaysPreference.AnonymousClass1 anonymousClass1 =
                                            AutoPowerOnOffDaysPreference.AnonymousClass1.this;
                                    if (anonymousClass1.mDisableListeners) {
                                        return;
                                    }
                                    anonymousClass1.mDisableListeners = true;
                                    AutoPowerOnOffSettings.DaysOfWeek daysOfWeek3 =
                                            anonymousClass1.mSelectionDaysOfWeek;
                                    int i4 = i3;
                                    if (z) {
                                        daysOfWeek3.mDays |= 1 << i4;
                                    } else {
                                        daysOfWeek3.mDays &= ~(1 << i4);
                                    }
                                    Log.secD(
                                            "onCheckedChanged",
                                            "day = "
                                                    + i3
                                                    + ", mDaysOfWeek.getCoded() = "
                                                    + AutoPowerOnOffDaysPreference.this
                                                            .mDaysOfWeek
                                                            .mDays);
                                    AutoPowerOnOffDaysPreference.AnonymousClass1 anonymousClass12 =
                                            AutoPowerOnOffDaysPreference.AnonymousClass1.this;
                                    anonymousClass12.mDaysCheckBox[0].setChecked(
                                            anonymousClass12.mSelectionDaysOfWeek.mDays == 254);
                                    AutoPowerOnOffDaysPreference.AnonymousClass1.this
                                                    .mDisableListeners =
                                            false;
                                }
                            });
                } else {
                    checkBox.setText(this.mDayList[0]);
                    checkBox.setChecked(this.mSelectionDaysOfWeek.mDays == 254);
                    checkBox.setOnCheckedChangeListener(
                            new CompoundButton
                                    .OnCheckedChangeListener() { // from class:
                                                                 // com.samsung.android.settings.autopoweronoff.AutoPowerOnOffDaysPreference$AutoPowerOnOffDaysSelection$3
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(
                                        CompoundButton compoundButton, boolean z) {
                                    AutoPowerOnOffDaysPreference.AnonymousClass1 anonymousClass1 =
                                            AutoPowerOnOffDaysPreference.AnonymousClass1.this;
                                    if (anonymousClass1.mDisableListeners) {
                                        return;
                                    }
                                    anonymousClass1.mDisableListeners = true;
                                    int i4 = 1;
                                    while (true) {
                                        AutoPowerOnOffDaysPreference.AnonymousClass1
                                                anonymousClass12 =
                                                        AutoPowerOnOffDaysPreference.AnonymousClass1
                                                                .this;
                                        if (i4 >= anonymousClass12.DAYS.length) {
                                            anonymousClass12.mDisableListeners = false;
                                            return;
                                        }
                                        anonymousClass12.mDaysCheckBox[i4].setChecked(z);
                                        AutoPowerOnOffSettings.DaysOfWeek daysOfWeek3 =
                                                AutoPowerOnOffDaysPreference.AnonymousClass1.this
                                                        .mSelectionDaysOfWeek;
                                        if (z) {
                                            daysOfWeek3.mDays |= 1 << i4;
                                        } else {
                                            daysOfWeek3.mDays &= ~(1 << i4);
                                        }
                                        i4++;
                                    }
                                }
                            });
                }
                this.mDaysCheckBox[i2] = checkBox;
                this.mLinearLayout.addView(linearLayout2);
                if (i2 == 0) {
                    linearLayout2.requestFocus();
                }
                i2++;
            }
        }
    }

    public AutoPowerOnOffDaysPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDaysOfWeek = new AutoPowerOnOffSettings.DaysOfWeek(0);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            Context context = getContext();
            View inflate =
                    ((LayoutInflater) context.getSystemService("layout_inflater"))
                            .inflate(R.layout.sec_dialog_auto_power_on_off_days, (ViewGroup) null);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.list_area);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(context, this.mDaysOfWeek);
            this.mDaysSelection = anonymousClass1;
            linearLayout.addView(anonymousClass1);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(inflate);
            builder.setTitle(R.string.repeat_title);
            builder.setPositiveButton(R.string.sec_common_done, this);
            builder.setNegativeButton(R.string.cancel, this);
            AlertDialog create = builder.create();
            this.mAlertDialog = create;
            create.show();
        }
    }

    public final void updateSummary(AutoPowerOnOffSettings.DaysOfWeek daysOfWeek) {
        String sb;
        Context context = getContext();
        int i = daysOfWeek.mDays;
        if (i == 0) {
            sb = context.getText(R.string.sec_auto_power_days_one_time).toString();
        } else if (i == 254) {
            sb = context.getText(R.string.sec_auto_power_days_everyday).toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            String[] shortWeekdays = new DateFormatSymbols().getShortWeekdays();
            int i2 = 0;
            for (int i3 = 1; i3 < 8; i3++) {
                if ((daysOfWeek.mDays & (1 << i3)) != 0) {
                    if (i2 > 0) {
                        sb2.append(", ");
                    }
                    sb2.append(shortWeekdays[AutoPowerOnOffSettings.DaysOfWeek.DAY_MAP[i3]]);
                    i2++;
                }
            }
            sb = sb2.toString();
        }
        setSummary(sb);
    }

    public AutoPowerOnOffDaysPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDaysOfWeek = new AutoPowerOnOffSettings.DaysOfWeek(0);
    }

    public AutoPowerOnOffDaysPreference(Context context) {
        super(context, null);
        this.mDaysOfWeek = new AutoPowerOnOffSettings.DaysOfWeek(0);
    }

    public AutoPowerOnOffDaysPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDaysOfWeek = new AutoPowerOnOffSettings.DaysOfWeek(0);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        AutoPowerOnOffSettings.DaysOfWeek daysOfWeek = this.mDaysOfWeek;
        AutoPowerOnOffSettings.DaysOfWeek daysOfWeek2 = this.mDaysSelection.mSelectionDaysOfWeek;
        daysOfWeek.getClass();
        daysOfWeek.mDays = daysOfWeek2.mDays;
        updateSummary(this.mDaysOfWeek);
        Log.secD(
                "AutoPowerOnOffDaysPreference",
                "mDaysOfWeek.getCoded() = " + this.mDaysOfWeek.mDays);
        callChangeListener(this.mDaysOfWeek);
    }
}
