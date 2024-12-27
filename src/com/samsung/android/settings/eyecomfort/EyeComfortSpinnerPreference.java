package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecDropDownPreference;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortSpinnerPreference extends SecDropDownPreference {
    public ScheduleTypeArrayAdapter mAdapter;
    public CharSequence[] mBlueLightFilterTypeEntries;
    public final Context mContext;
    public final LayoutInflater mInflater;
    public onSpinnerItemClickListener mListener;
    public int mScheduleTypeIndex;
    public AppCompatSpinner mSpinner;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomScheduleHolder {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScheduleTypeArrayAdapter extends BaseAdapter {
        public ScheduleTypeArrayAdapter() {}

        @Override // android.widget.Adapter
        public final int getCount() {
            return 3;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return Integer.valueOf(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view =
                        EyeComfortSpinnerPreference.this.mInflater.inflate(
                                R.layout.sec_eye_comfort_spinner_item, (ViewGroup) null);
                EyeComfortSpinnerPreference eyeComfortSpinnerPreference =
                        EyeComfortSpinnerPreference.this;
                CustomScheduleHolder customScheduleHolder = new CustomScheduleHolder();
                CheckedTextView checkedTextView =
                        (CheckedTextView) view.findViewById(R.id.textView);
                checkedTextView.setText(eyeComfortSpinnerPreference.mBlueLightFilterTypeEntries[i]);
                if (i == eyeComfortSpinnerPreference.mScheduleTypeIndex) {
                    checkedTextView.setCheckMarkDrawable((Drawable) null);
                    checkedTextView.setTextColor(
                            eyeComfortSpinnerPreference
                                    .mContext
                                    .getResources()
                                    .getColor(
                                            R.color
                                                    .sec_eye_comfort_spinner_dropdown_selected_color));
                } else {
                    checkedTextView.setCheckMarkDrawable((Drawable) null);
                }
                view.setTag(customScheduleHolder);
            }
            view.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.eyecomfort.EyeComfortSpinnerPreference.ScheduleTypeArrayAdapter.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            AppCompatSpinner.SpinnerPopup spinnerPopup;
                            onSpinnerItemClickListener onspinneritemclicklistener =
                                    EyeComfortSpinnerPreference.this.mListener;
                            int i2 = i;
                            EyeComfortSettings eyeComfortSettings =
                                    (EyeComfortSettings) onspinneritemclicklistener;
                            if (i2 != 2) {
                                eyeComfortSettings.mSetSchedulePreference.setSummary(
                                        EyeComfortSettings.mBlueLightFilterScheduleSummaryString[
                                                i2]);
                                if (i2 != 1) {
                                    Settings.System.putInt(
                                            eyeComfortSettings.getContentResolver(),
                                            "blue_light_filter_type",
                                            0);
                                } else {
                                    if (Settings.Secure.getInt(
                                                    eyeComfortSettings.getContentResolver(),
                                                    "location_mode",
                                                    0)
                                            == 0) {
                                        eyeComfortSettings.showLocationOnDialog(2);
                                        ScheduleTypeArrayAdapter.this.notifyDataSetChanged();
                                        AppCompatSpinner appCompatSpinner =
                                                EyeComfortSpinnerPreference.this.mSpinner;
                                        spinnerPopup = appCompatSpinner.mPopup;
                                        if (spinnerPopup == null && spinnerPopup.isShowing()) {
                                            appCompatSpinner.mPopup.dismiss();
                                            return;
                                        }
                                    }
                                    Settings.System.putInt(
                                            eyeComfortSettings.getContentResolver(),
                                            "blue_light_filter_type",
                                            2);
                                }
                                eyeComfortSettings.updateDescriptionPreference();
                            } else if (eyeComfortSettings.getActivity() != null
                                    && eyeComfortSettings.getActivity().getSupportFragmentManager()
                                            != null) {
                                EyeComfortTimeSettingDialogFragment.mPreference =
                                        eyeComfortSettings.mSetSchedulePreference;
                                EyeComfortTimeSettingDialogFragment
                                        eyeComfortTimeSettingDialogFragment =
                                                new EyeComfortTimeSettingDialogFragment();
                                eyeComfortSettings.mTimeSettingDialog =
                                        eyeComfortTimeSettingDialogFragment;
                                eyeComfortTimeSettingDialogFragment.mTimeDialogDismissListener =
                                        eyeComfortSettings;
                                eyeComfortTimeSettingDialogFragment.show(
                                        eyeComfortSettings
                                                .getActivity()
                                                .getSupportFragmentManager(),
                                        "EyeComfortTimeSettingDialogFragment");
                            }
                            eyeComfortSettings.mSetSchedulePreference.setValueIndex(i2);
                            eyeComfortSettings.mSetSchedulePreference.mScheduleTypeIndex = i2;
                            LoggingHelper.insertEventLogging(4222, 4225, i2);
                            ScheduleTypeArrayAdapter.this.notifyDataSetChanged();
                            AppCompatSpinner appCompatSpinner2 =
                                    EyeComfortSpinnerPreference.this.mSpinner;
                            spinnerPopup = appCompatSpinner2.mPopup;
                            if (spinnerPopup == null) {}
                        }
                    });
            return view;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface onSpinnerItemClickListener {}

    public EyeComfortSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mListener = null;
        this.mScheduleTypeIndex = 0;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // androidx.preference.SecDropDownPreference, androidx.preference.DropDownPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mBlueLightFilterTypeEntries = this.mEntries;
        this.mSpinner = (AppCompatSpinner) preferenceViewHolder.itemView.findViewById(R.id.spinner);
        this.mSpinner.setAdapter((SpinnerAdapter) new ScheduleTypeArrayAdapter());
    }
}
