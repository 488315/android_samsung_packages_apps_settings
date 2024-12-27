package com.samsung.android.settings.asbase.widget;

import android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecListPreference;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoundModeDropDownPreference extends SecListPreference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayAdapter mAdapter;
    public final AudioManager mAudioManager;
    public boolean mCancelFlag;
    public final Context mContext;
    public boolean mCustomHourFlag;
    public final AnonymousClass2 mItemSelectedListener;
    public final ReselectionSpinner mSpinner;
    public boolean mUserClicked;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ReselectionSpinner extends AppCompatSpinner {
        public ReselectionSpinner(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            SoundModeDropDownPreference.this.mUserClicked = false;
        }

        @Override // android.widget.AbsSpinner, android.widget.AdapterView
        public final void setSelection(int i) {
            if (TextUtils.isEmpty(SoundModeDropDownPreference.this.getEntry())) {
                return;
            }
            super.setSelection(i);
            SoundModeDropDownPreference soundModeDropDownPreference =
                    SoundModeDropDownPreference.this;
            if (soundModeDropDownPreference.mUserClicked) {
                if (i >= 0) {
                    String charSequence = soundModeDropDownPreference.mEntryValues[i].toString();
                    if (SoundModeDropDownPreference.this.callChangeListener(charSequence)) {
                        SoundModeDropDownPreference.this.setValue(charSequence);
                    }
                }
                SoundModeDropDownPreference.this.mUserClicked = false;
            }
        }
    }

    public SoundModeDropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, R.attr.dialogPreferenceStyle, i2);
        this.mUserClicked = false;
        this.mCancelFlag = false;
        this.mCustomHourFlag = false;
        AdapterView.OnItemSelectedListener onItemSelectedListener =
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.samsung.android.settings.asbase.widget.SoundModeDropDownPreference.2
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view, int i3, long j) {
                        if (TextUtils.isEmpty(SoundModeDropDownPreference.this.getEntry())) {
                            return;
                        }
                        SoundModeDropDownPreference soundModeDropDownPreference =
                                SoundModeDropDownPreference.this;
                        if (!soundModeDropDownPreference.mCancelFlag && i3 >= 0) {
                            soundModeDropDownPreference.setCustomHourFlag(false);
                            String charSequence =
                                    SoundModeDropDownPreference.this.mEntryValues[i3].toString();
                            if (charSequence.equals(SoundModeDropDownPreference.this.mValue)
                                    || !SoundModeDropDownPreference.this.callChangeListener(
                                            charSequence)) {
                                return;
                            }
                            SoundModeDropDownPreference.this.setValue(charSequence);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {}
                };
        this.mContext = context;
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        context, com.android.settings.R.layout.sec_mute_for_spinner_dropdown_item);
        this.mAdapter = arrayAdapter;
        ReselectionSpinner reselectionSpinner = new ReselectionSpinner(context, attributeSet);
        this.mSpinner = reselectionSpinner;
        reselectionSpinner.setVisibility(4);
        reselectionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        reselectionSpinner.setOnItemSelectedListener(onItemSelectedListener);
        reselectionSpinner.setDropDownHorizontalOffset(
                context.getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen
                                        .sec_sound_settings_temporary_mute_duration_width));
        reselectionSpinner.setDropDownVerticalOffset(
                context.getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen
                                        .sec_sound_settings_temporary_mute_duration_height));
        reselectionSpinner.setPadding(
                context.getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen
                                        .sec_sound_settings_temporary_mute_duration_padding),
                0,
                0,
                0);
        setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.asbase.widget.SoundModeDropDownPreference.1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        ArrayAdapter arrayAdapter2;
                        SoundModeDropDownPreference soundModeDropDownPreference =
                                SoundModeDropDownPreference.this;
                        if (soundModeDropDownPreference.mSpinner == null
                                || (arrayAdapter2 = soundModeDropDownPreference.mAdapter) == null
                                || arrayAdapter2.getCount() <= 0) {
                            int i3 = SoundModeDropDownPreference.$r8$clinit;
                            Log.e("SoundModeDropDownPreference", "onClick(): spinner is null");
                            return true;
                        }
                        soundModeDropDownPreference.mUserClicked = true;
                        soundModeDropDownPreference.mCancelFlag = false;
                        soundModeDropDownPreference.mSpinner.setSoundEffectsEnabled(false);
                        soundModeDropDownPreference.mSpinner.performClick();
                        return true;
                    }
                });
        updateEntries$2();
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        super.notifyChanged();
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        preferenceViewHolder.itemView.setBackgroundResource(typedValue.resourceId);
        if (preferenceViewHolder.itemView.equals(this.mSpinner.getParent())) {
            return;
        }
        if (this.mSpinner.getParent() != null) {
            ((ViewGroup) this.mSpinner.getParent()).removeView(this.mSpinner);
        }
        ((ViewGroup) preferenceViewHolder.itemView).addView(this.mSpinner, 0);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mSpinner.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = 0;
            layoutParams.gravity = 48;
            layoutParams.topMargin =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen.dropdown_list_item_padding);
            this.mSpinner.setLayoutParams(layoutParams);
        }
        boolean z =
                this.mContext
                        .getSharedPreferences("customHour", 0)
                        .getBoolean("customHourFlag", false);
        this.mCustomHourFlag = z;
        if (z) {
            setFocusWithoutExecute(4);
        } else {
            this.mSpinner.setSelection(findIndexOfValue(this.mValue));
        }
    }

    public final void setCustomHourFlag(boolean z) {
        this.mCustomHourFlag = z;
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("customHour", 0).edit();
        edit.putBoolean("customHourFlag", this.mCustomHourFlag);
        edit.commit();
    }

    @Override // androidx.preference.ListPreference
    public final void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        updateEntries$2();
    }

    public final void setFocusWithoutExecute(int i) {
        this.mUserClicked = false;
        this.mCancelFlag = true;
        this.mSpinner.setSelection(i - 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0056, code lost:

       if (r5 == 0) goto L19;
    */
    @Override // androidx.preference.ListPreference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setValue(java.lang.String r5) {
        /*
            r4 = this;
            super.setValue(r5)
            int r5 = r4.findIndexOfValue(r5)
            java.lang.CharSequence[] r0 = r4.mEntries
            if (r0 == 0) goto L72
            r1 = 3
            if (r5 != r1) goto L6a
            android.media.AudioManager r5 = r4.mAudioManager
            int r5 = r5.getMuteInterval()
            int r0 = r5 / 60
            int r5 = r5 % 60
            android.content.Context r1 = r4.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131886109(0x7f12001d, float:1.9406788E38)
            java.lang.String r1 = r1.getQuantityString(r2, r0)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            java.lang.Object[] r2 = new java.lang.Object[]{r2}
            java.lang.String r1 = java.lang.String.format(r1, r2)
            android.content.Context r2 = r4.mContext
            android.content.res.Resources r2 = r2.getResources()
            r3 = 2131886110(0x7f12001e, float:1.940679E38)
            java.lang.String r2 = r2.getQuantityString(r3, r5)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r3 = new java.lang.Object[]{r3}
            java.lang.String r2 = java.lang.String.format(r2, r3)
            if (r0 != 0) goto L4e
            if (r5 > 0) goto L52
        L4e:
            if (r0 <= 0) goto L59
            if (r5 != 0) goto L59
        L52:
            if (r0 != 0) goto L56
            r1 = r2
            goto L66
        L56:
            if (r5 != 0) goto L64
            goto L66
        L59:
            if (r0 <= 0) goto L64
            if (r5 <= 0) goto L64
            java.lang.String r5 = " "
            java.lang.String r1 = androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(r1, r5, r2)
            goto L66
        L64:
            java.lang.String r1 = ""
        L66:
            r4.setSummary(r1)
            goto L72
        L6a:
            r1 = -1
            if (r5 == r1) goto L72
            r5 = r0[r5]
            r4.setSummary(r5)
        L72:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.widget.SoundModeDropDownPreference.setValue(java.lang.String):void");
    }

    @Override // androidx.preference.ListPreference
    public final void setValueIndex(int i) {
        this.mSpinner.setSelection(i);
        setValue(this.mEntryValues[i].toString());
    }

    public final void updateEntries$2() {
        this.mAdapter.clear();
        CharSequence[] charSequenceArr = this.mEntries;
        if (charSequenceArr != null) {
            for (CharSequence charSequence : charSequenceArr) {
                this.mAdapter.add(charSequence.toString());
            }
        }
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void onClick() {}

    public SoundModeDropDownPreference(Context context) {
        this(context, null);
    }

    public SoundModeDropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SoundModeDropDownPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, R.attr.dialogPreferenceStyle, 0);
    }
}
