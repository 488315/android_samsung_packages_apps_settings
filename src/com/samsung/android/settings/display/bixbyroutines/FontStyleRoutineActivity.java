package com.samsung.android.settings.display.bixbyroutines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.android.settings.R;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.flipfont.FontListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FontStyleRoutineActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mApplicationContext;
    public Context mContext;
    public String mCurrentFontName;
    public final ArrayList mFontList = new ArrayList();
    public FontListAdapter mFontListAdapter;
    public int mRadioButtonMinHeight;
    public int mRadioButtonPaddingLeft;
    public int mRadioButtonPaddingVertical;
    public int mRadioButtonTextSize;
    public RadioGroup mRadioGroup;
    public String mSelectedFontName;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getBaseContext();
        this.mApplicationContext = getApplicationContext();
        Resources resources = this.mContext.getResources();
        this.mRadioButtonMinHeight =
                resources.getDimensionPixelSize(
                        R.dimen.sec_font_style_routines_radio_button_min_height);
        this.mRadioButtonPaddingLeft =
                resources.getDimensionPixelSize(
                        R.dimen.sec_font_style_routines_radio_button_padding_left);
        this.mRadioButtonPaddingVertical =
                resources.getDimensionPixelSize(
                        R.dimen.sec_font_style_routines_radio_button_padding_vertical);
        this.mRadioButtonTextSize =
                resources.getDimensionPixelSize(
                        R.dimen.sec_font_style_routines_radio_button_text_size);
        this.mCurrentFontName = SecDisplayUtils.getCurrentFontName(this.mApplicationContext);
        if (getIntent().getExtras() != null) {
            this.mSelectedFontName =
                    ParameterValues.fromJsonString(
                                    getIntent()
                                            .getExtras()
                                            .getString(
                                                    ExtraKey.PARAMETER_VALUES.a,
                                                    ApnSettings.MVNO_NONE))
                            .getString("font_style_key", ApnSettings.MVNO_NONE);
        } else {
            this.mSelectedFontName = ApnSettings.MVNO_NONE;
        }
        if (this.mFontListAdapter == null) {
            FontListAdapter instanceFontListAdapter =
                    FontListAdapter.getInstanceFontListAdapter(this.mApplicationContext);
            this.mFontListAdapter = instanceFontListAdapter;
            instanceFontListAdapter.loadTypefaces();
        }
        this.mFontList.clear();
        for (int i = 0; i < this.mFontListAdapter.mFontNames.size(); i++) {
            this.mFontList.add(this.mFontListAdapter.getFontName(i));
        }
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.font_style_routine_activity_layout, (ViewGroup) null);
        this.mRadioGroup = (RadioGroup) inflate.findViewById(R.id.config_font_radio_group);
        for (int i2 = 0; i2 < this.mFontList.size(); i2++) {
            RadioGroup radioGroup = this.mRadioGroup;
            String str = (String) this.mFontList.get(i2);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            AppCompatRadioButton appCompatRadioButton = new AppCompatRadioButton(this.mContext);
            appCompatRadioButton.setText(str);
            appCompatRadioButton.setId(i2);
            appCompatRadioButton.setLayoutParams(layoutParams);
            appCompatRadioButton.setMinimumHeight(this.mRadioButtonMinHeight);
            int i3 = this.mRadioButtonPaddingLeft;
            int i4 = this.mRadioButtonPaddingVertical;
            appCompatRadioButton.setPadding(i3, i4, 0, i4);
            appCompatRadioButton.setTextSize(0, this.mRadioButtonTextSize);
            appCompatRadioButton.setSingleLine();
            appCompatRadioButton.setTextColor(
                    Utils.getColorAttr(this.mContext, android.R.attr.textColorPrimary));
            FontListAdapter fontListAdapter = this.mFontListAdapter;
            synchronized (fontListAdapter.mTypefaces) {
                if (i2 == 0) {
                    try {
                        appCompatRadioButton.setTypeface(Typeface.create("sec-no-flip", 0));
                    } finally {
                    }
                } else {
                    Typeface typeface = (Typeface) fontListAdapter.mTypefaces.get(i2);
                    if (typeface != null) {
                        appCompatRadioButton.setTypeface(typeface, 0);
                    }
                }
            }
            if ((str.equals(this.mCurrentFontName) && this.mSelectedFontName.isEmpty())
                    || this.mSelectedFontName.equals(str)) {
                appCompatRadioButton.setChecked(true);
            }
            radioGroup.addView(appCompatRadioButton);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sec_font_style_title);
        builder.setView(inflate);
        builder.P.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.display.bixbyroutines.FontStyleRoutineActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        FontStyleRoutineActivity fontStyleRoutineActivity =
                                FontStyleRoutineActivity.this;
                        int i5 = FontStyleRoutineActivity.$r8$clinit;
                        fontStyleRoutineActivity.finish();
                    }
                };
        final int i5 = 0;
        builder.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.bixbyroutines.FontStyleRoutineActivity$$ExternalSyntheticLambda1
                    public final /* synthetic */ FontStyleRoutineActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i6) {
                        int i7 = i5;
                        FontStyleRoutineActivity fontStyleRoutineActivity = this.f$0;
                        switch (i7) {
                            case 0:
                                int i8 = FontStyleRoutineActivity.$r8$clinit;
                                fontStyleRoutineActivity.finish();
                                break;
                            default:
                                int checkedRadioButtonId =
                                        fontStyleRoutineActivity.mRadioGroup
                                                .getCheckedRadioButtonId();
                                RadioButton radioButton =
                                        (RadioButton)
                                                fontStyleRoutineActivity.mRadioGroup.getChildAt(
                                                        checkedRadioButtonId);
                                HashMap hashMap = new HashMap();
                                hashMap.put(
                                        "font_style_key",
                                        new ParameterValues.ParameterValue(
                                                radioButton.getText().toString()));
                                hashMap.put(
                                        "font_package_name_key",
                                        new ParameterValues.ParameterValue(
                                                fontStyleRoutineActivity
                                                        .mFontListAdapter
                                                        .mFontPackageNames
                                                        .get(checkedRadioButtonId)
                                                        .toString()));
                                Intent intent = new Intent();
                                HashMap hashMap2 = new HashMap();
                                hashMap.entrySet()
                                        .forEach(
                                                new ParameterValues$$ExternalSyntheticLambda0(
                                                        hashMap2));
                                intent.putExtra(
                                        "intent_params", new JSONObject(hashMap2).toString());
                                fontStyleRoutineActivity.setResult(-1, intent);
                                fontStyleRoutineActivity.finish();
                                break;
                        }
                    }
                });
        final int i6 = 1;
        builder.setPositiveButton(
                R.string.done,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.bixbyroutines.FontStyleRoutineActivity$$ExternalSyntheticLambda1
                    public final /* synthetic */ FontStyleRoutineActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        int i7 = i6;
                        FontStyleRoutineActivity fontStyleRoutineActivity = this.f$0;
                        switch (i7) {
                            case 0:
                                int i8 = FontStyleRoutineActivity.$r8$clinit;
                                fontStyleRoutineActivity.finish();
                                break;
                            default:
                                int checkedRadioButtonId =
                                        fontStyleRoutineActivity.mRadioGroup
                                                .getCheckedRadioButtonId();
                                RadioButton radioButton =
                                        (RadioButton)
                                                fontStyleRoutineActivity.mRadioGroup.getChildAt(
                                                        checkedRadioButtonId);
                                HashMap hashMap = new HashMap();
                                hashMap.put(
                                        "font_style_key",
                                        new ParameterValues.ParameterValue(
                                                radioButton.getText().toString()));
                                hashMap.put(
                                        "font_package_name_key",
                                        new ParameterValues.ParameterValue(
                                                fontStyleRoutineActivity
                                                        .mFontListAdapter
                                                        .mFontPackageNames
                                                        .get(checkedRadioButtonId)
                                                        .toString()));
                                Intent intent = new Intent();
                                HashMap hashMap2 = new HashMap();
                                hashMap.entrySet()
                                        .forEach(
                                                new ParameterValues$$ExternalSyntheticLambda0(
                                                        hashMap2));
                                intent.putExtra(
                                        "intent_params", new JSONObject(hashMap2).toString());
                                fontStyleRoutineActivity.setResult(-1, intent);
                                fontStyleRoutineActivity.finish();
                                break;
                        }
                    }
                });
        builder.show();
    }
}
