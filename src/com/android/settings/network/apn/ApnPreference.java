package com.android.settings.network.apn;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ApnPreference extends Preference
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public boolean mDefaultSelectable;
    public boolean mHideDetails;
    public boolean mIsChecked;
    public boolean mIsEditableMode;
    public boolean mProtectFromCheckedChange;
    public RadioButton mRadioButton;
    public int mSubId;

    public ApnPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsChecked = false;
        this.mRadioButton = null;
        this.mSubId = -1;
        this.mProtectFromCheckedChange = false;
        this.mDefaultSelectable = true;
        this.mHideDetails = false;
        this.mIsEditableMode = false;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ((RelativeLayout) preferenceViewHolder.findViewById(R.id.text_layout))
                .setOnClickListener(this);
        final RadioButton radioButton =
                (RadioButton) preferenceViewHolder.itemView.requireViewById(R.id.apn_radiobutton);
        this.mRadioButton = radioButton;
        if (!this.mDefaultSelectable) {
            radioButton.setVisibility(4);
            preferenceViewHolder
                    .findViewById(R.id.apn_radio_button_frame)
                    .setImportantForAccessibility(2);
            radioButton.setOnCheckedChangeListener(null);
            return;
        }
        preferenceViewHolder
                .itemView
                .requireViewById(R.id.apn_radio_button_frame)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.apn.ApnPreference$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                radioButton.performClick();
                            }
                        });
        radioButton.setOnCheckedChangeListener(this);
        preferenceViewHolder
                .findViewById(R.id.apn_radio_button_frame)
                .setImportantForAccessibility(1);
        this.mProtectFromCheckedChange = true;
        radioButton.setChecked(this.mIsChecked);
        this.mProtectFromCheckedChange = false;
        radioButton.setVisibility(0);
        radioButton.setContentDescription(getTitle());
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.i("ApnPreference", "ID: " + getKey() + " :" + z);
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("mProtectFromCheckedChange: "),
                this.mProtectFromCheckedChange,
                "ApnPreference");
        if (!this.mProtectFromCheckedChange && z) {
            callChangeListener(getKey());
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Context context = getContext();
        int parseInt = Integer.parseInt(getKey());
        if (context == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    parseInt, "No context available for pos=", "ApnPreference");
            return;
        }
        if (this.mHideDetails) {
            ApnPreference$$ExternalSyntheticOutline0.m(
                    context, R.string.cannot_change_apn_toast, context, 1);
            return;
        }
        Intent intent =
                new Intent(
                        "android.intent.action.EDIT",
                        ContentUris.withAppendedId(Telephony.Carriers.CONTENT_URI, parseInt));
        intent.putExtra("sub_id", this.mSubId);
        intent.addFlags(1);
        intent.putExtra("editable_mode", this.mIsEditableMode);
        context.startActivity(intent);
    }

    public ApnPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.apnPreferenceStyle);
    }
}
