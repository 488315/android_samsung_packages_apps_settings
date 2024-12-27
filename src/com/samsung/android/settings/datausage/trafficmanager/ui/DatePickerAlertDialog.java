package com.samsung.android.settings.datausage.trafficmanager.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.picker.app.SeslDatePickerDialog;
import androidx.picker.widget.SeslDatePicker;

import com.android.settings.R;
import com.android.settings.datausage.AppDataUsage;
import com.android.settings.datausage.AppDataUsageCycleController;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DatePickerAlertDialog extends SeslDatePickerDialog {
    public final AnonymousClass1 mBtnFocusChangeListener;
    public final SeslDatePicker mDatePicker;
    public final AppDataUsage.AnonymousClass2 mDateSetListener;
    public long mEndTime;
    public final InputMethodManager mImm;
    public long mStartTime;
    public final TabHost mTabHost;
    public final AnonymousClass2 mTabListener;
    public Toast mToast;
    public final AnonymousClass3 mValidationCallback;
    public final Context themeContext;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.datausage.trafficmanager.ui.DatePickerAlertDialog$1] */
    public DatePickerAlertDialog(
            FragmentActivity fragmentActivity,
            AppDataUsage.AnonymousClass2 anonymousClass2,
            int i,
            int i2,
            int i3) {
        super(fragmentActivity, null, i, i2, i3);
        this.mToast = null;
        this.mBtnFocusChangeListener =
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.datausage.trafficmanager.ui.DatePickerAlertDialog.1
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        if (DatePickerAlertDialog.this.mDatePicker.isEditTextMode() && z) {
                            DatePickerAlertDialog.this.mDatePicker.setEditTextMode();
                        }
                    }
                };
        TabHost.OnTabChangeListener onTabChangeListener =
                new TabHost
                        .OnTabChangeListener() { // from class:
                                                 // com.samsung.android.settings.datausage.trafficmanager.ui.DatePickerAlertDialog.2
                    @Override // android.widget.TabHost.OnTabChangeListener
                    public final void onTabChanged(String str) {
                        String currentTabTag =
                                DatePickerAlertDialog.this.mTabHost.getCurrentTabTag();
                        String upperCase =
                                DatePickerAlertDialog.this
                                        .getContext()
                                        .getString(R.string.edit_event_start_short)
                                        .toUpperCase(Locale.getDefault());
                        Log.i(
                                "DatePickerAlertDialog",
                                "onTabChanged, currentTab is:" + currentTabTag);
                        if (upperCase.equals(currentTabTag)) {
                            DatePickerAlertDialog.this.mDatePicker.setMinDate(0L);
                            Date date = new Date(DatePickerAlertDialog.this.mStartTime);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            DatePickerAlertDialog.this.mDatePicker.updateDate(
                                    calendar.get(1), calendar.get(2), calendar.get(5));
                            return;
                        }
                        DatePickerAlertDialog.this.mDatePicker.setMinDate(0L);
                        DatePickerAlertDialog datePickerAlertDialog = DatePickerAlertDialog.this;
                        datePickerAlertDialog.mDatePicker.setMinDate(
                                datePickerAlertDialog.mStartTime);
                        DatePickerAlertDialog datePickerAlertDialog2 = DatePickerAlertDialog.this;
                        if (datePickerAlertDialog2.mStartTime >= datePickerAlertDialog2.mEndTime) {
                            Date date2 = new Date(DatePickerAlertDialog.this.mStartTime);
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(date2);
                            DatePickerAlertDialog.this.mDatePicker.updateDate(
                                    calendar2.get(1), calendar2.get(2), calendar2.get(5));
                            return;
                        }
                        Date date3 = new Date(DatePickerAlertDialog.this.mEndTime);
                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.setTime(date3);
                        calendar3.set(5, calendar3.get(5) - 1);
                        Log.i(
                                "DatePickerAlertDialog",
                                "c.get(Calendar.MONTH):"
                                        + calendar3.get(2)
                                        + "c.get(Calendar.DAY_OF_MONTH):"
                                        + calendar3.get(5));
                        DatePickerAlertDialog.this.mDatePicker.updateDate(
                                calendar3.get(1), calendar3.get(2), calendar3.get(5));
                    }
                };
        SeslDatePicker.ValidationCallback validationCallback =
                new SeslDatePicker
                        .ValidationCallback() { // from class:
                                                // com.samsung.android.settings.datausage.trafficmanager.ui.DatePickerAlertDialog.3
                    @Override // androidx.picker.widget.SeslDatePicker.ValidationCallback
                    public final void onValidationChanged(boolean z) {
                        Button button = DatePickerAlertDialog.this.getButton(-1);
                        if (button != null) {
                            button.setEnabled(z);
                        }
                    }
                };
        Log.i("DatePickerAlertDialog", "initialize()");
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Log.i(
                "DatePickerAlertDialog",
                "initialize(), the current time:" + calendar.getTimeInMillis());
        this.mStartTime = calendar.getTimeInMillis();
        calendar.set(5, calendar.get(5) + 1);
        Log.i("DatePickerAlertDialog", "init end month:" + calendar.get(2));
        Log.i("DatePickerAlertDialog", "init end day:" + calendar.get(5));
        this.mEndTime = calendar.getTimeInMillis();
        this.mDateSetListener = anonymousClass2;
        Context context = getContext();
        this.themeContext = context;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_data_usage_date_picker, (ViewGroup) null);
        setView$1(inflate);
        getWindow().setGravity(80);
        getWindow().setLayout(-2, -2);
        getWindow().setDimAmount(0.3f);
        getWindow().addFlags(2);
        setButton(-1, context.getString(R.string.common_done), this);
        setButton(-2, context.getString(R.string.common_cancel), this);
        SeslDatePicker seslDatePicker = (SeslDatePicker) inflate.findViewById(R.id.sem_datePicker);
        this.mDatePicker = seslDatePicker;
        seslDatePicker.init(i, i2, i3, this);
        seslDatePicker.mValidationCallback = validationCallback;
        this.mImm = (InputMethodManager) context.getSystemService("input_method");
        TabHost tabHost = (TabHost) inflate.findViewById(R.id.from_to_container);
        this.mTabHost = tabHost;
        tabHost.setup();
        String upperCase =
                getContext()
                        .getString(R.string.edit_event_start_short)
                        .toUpperCase(Locale.getDefault());
        TabHost.TabSpec newTabSpec = this.mTabHost.newTabSpec(upperCase);
        newTabSpec.setContent(android.R.id.tabcontent);
        newTabSpec.setIndicator(upperCase);
        this.mTabHost.addTab(newTabSpec);
        String upperCase2 =
                getContext()
                        .getString(R.string.edit_event_end_short)
                        .toUpperCase(Locale.getDefault());
        TabHost.TabSpec newTabSpec2 = this.mTabHost.newTabSpec(upperCase2);
        newTabSpec2.setContent(android.R.id.tabcontent);
        newTabSpec2.setIndicator(upperCase2);
        this.mTabHost.addTab(newTabSpec2);
        this.mTabHost.setOnTabChangedListener(onTabChangeListener);
    }

    @Override // androidx.picker.app.SeslDatePickerDialog,
              // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        AppDataUsageCycleController appDataUsageCycleController;
        InputMethodManager inputMethodManager = this.mImm;
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getWindow().getDecorView().getWindowToken(), 0);
        }
        if (i == -2) {
            cancel();
            return;
        }
        if (i == -1 && this.mDateSetListener != null) {
            this.mDatePicker.clearFocus();
            AppDataUsage.AnonymousClass2 anonymousClass2 = this.mDateSetListener;
            long j = this.mStartTime;
            long j2 = this.mEndTime;
            anonymousClass2.getClass();
            Log.i("AppDataUsage", "onDateSet(), mStartTime:" + j + ", mEndTime:" + j2);
            AppDataUsage appDataUsage = AppDataUsage.this;
            if (!appDataUsage.mIsCHNDataUsage
                    || (appDataUsageCycleController = appDataUsage.mCycleController) == null) {
                return;
            }
            appDataUsageCycleController.updateView(j, j2, true);
        }
    }

    @Override // androidx.picker.app.SeslDatePickerDialog, androidx.appcompat.app.AlertDialog,
              // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getButton(-1).setOnFocusChangeListener(this.mBtnFocusChangeListener);
        getButton(-2).setOnFocusChangeListener(this.mBtnFocusChangeListener);
    }

    @Override // androidx.picker.app.SeslDatePickerDialog,
              // androidx.picker.widget.SeslDatePicker.OnDateChangedListener
    public final void onDateChanged(int i, int i2, int i3) {
        Log.i("DatePickerAlertDialog", "onDateChanged()");
        Log.i("DatePickerAlertDialog", "year:" + i + " month:" + i2 + " day:" + i3);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        String upperCase =
                getContext()
                        .getString(R.string.edit_event_start_short)
                        .toUpperCase(Locale.getDefault());
        String upperCase2 =
                getContext()
                        .getString(R.string.edit_event_start_short)
                        .toUpperCase(Locale.getDefault());
        if (this.mTabHost != null) {
            Log.i("DatePickerAlertDialog", "mTabHost is not null");
            upperCase2 = this.mTabHost.getCurrentTabTag();
        }
        Log.i("DatePickerAlertDialog", "currentTab is:" + upperCase2);
        if (upperCase.equals(upperCase2)) {
            Log.i("DatePickerAlertDialog", "current time is start time");
            this.mStartTime = calendar.getTimeInMillis();
        } else {
            calendar.set(5, i3 + 1);
            Log.i("DatePickerAlertDialog", "current time is end time,  month:" + calendar.get(2));
            Log.i("DatePickerAlertDialog", "current time is end time, day:" + calendar.get(5));
            this.mEndTime = calendar.getTimeInMillis();
        }
        if (this.mStartTime >= this.mEndTime) {
            if (getButton(-1) != null) {
                getButton(-1).setEnabled(false);
            }
            String string = getContext().getString(R.string.end_date_latter_start_date_warning);
            Context context = this.themeContext;
            if (context != null) {
                Toast toast = this.mToast;
                if (toast == null) {
                    this.mToast = Toast.makeText(context, string, 0);
                } else {
                    toast.setText(string);
                }
                this.mToast.show();
            }
        } else if (getButton(-1) != null) {
            getButton(-1).setEnabled(true);
        }
        Log.i(
                "DatePickerAlertDialog",
                "mStartTime:" + this.mStartTime + ", mEndTime:" + this.mEndTime);
    }

    @Override // androidx.picker.app.SeslDatePickerDialog, android.app.Dialog
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePicker.init(
                bundle.getInt("year"), bundle.getInt("month"), bundle.getInt("day"), this);
    }

    @Override // androidx.picker.app.SeslDatePickerDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt("year", this.mDatePicker.mCurrentDate.get(1));
        onSaveInstanceState.putInt("month", this.mDatePicker.mCurrentDate.get(2));
        onSaveInstanceState.putInt("day", this.mDatePicker.mCurrentDate.get(5));
        return onSaveInstanceState;
    }
}
