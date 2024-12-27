package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeyTips extends SettingsPreferenceFragment {
    public String mActivityHistory = "default";
    public Context mContext;
    public LayoutPreference mFunctionKeyTipsLayout1;
    public LayoutPreference mFunctionKeyTipsLayout2;
    public LayoutPreference mFunctionKeyTipsLayout3;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7618;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_function_key_tips;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Bundle arguments;
        super.onCreate(bundle);
        this.mContext = getContext();
        Intent intent = getIntent();
        if (intent != null) {
            this.mActivityHistory = intent.getStringExtra("type");
        }
        if (TextUtils.isEmpty(this.mActivityHistory) && (arguments = getArguments()) != null) {
            this.mActivityHistory = arguments.getString("type", "default");
        }
        this.mFunctionKeyTipsLayout1 =
                (LayoutPreference) findPreference("function_key_tips_layout_1");
        this.mFunctionKeyTipsLayout2 =
                (LayoutPreference) findPreference("function_key_tips_layout_2");
        this.mFunctionKeyTipsLayout3 =
                (LayoutPreference) findPreference("function_key_tips_layout_3");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        LayoutPreference layoutPreference = this.mFunctionKeyTipsLayout1;
        if (layoutPreference != null) {
            LinearLayout linearLayout =
                    (LinearLayout) layoutPreference.mRootView.findViewById(R.id.layout_container);
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            ImageView imageView =
                    (ImageView) this.mFunctionKeyTipsLayout1.mRootView.findViewById(R.id.animation);
            ((TextView) this.mFunctionKeyTipsLayout1.mRootView.findViewById(R.id.description_title))
                    .setText(R.string.sec_function_key_tips_quick_panel_title);
            ((TextView)
                            this.mFunctionKeyTipsLayout1.mRootView.findViewById(
                                    R.id.description_summary))
                    .setText(R.string.sec_function_key_tips_quick_panel_desc);
            imageView.setImageResource(R.drawable.sec_tips_for_power_quickpanel);
        }
        LayoutPreference layoutPreference2 = this.mFunctionKeyTipsLayout2;
        if (layoutPreference2 != null) {
            LinearLayout linearLayout2 =
                    (LinearLayout) layoutPreference2.mRootView.findViewById(R.id.layout_container);
            linearLayout2.semSetRoundedCorners(15);
            linearLayout2.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            LinearLayout linearLayout3 =
                    (LinearLayout)
                            this.mFunctionKeyTipsLayout2.mRootView.findViewById(
                                    R.id.description_container);
            linearLayout3.setPaddingRelative(
                    linearLayout3.getPaddingStart(),
                    this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.sec_widget_body_text_padding_bottom)
                            + linearLayout3.getPaddingTop(),
                    linearLayout3.getPaddingEnd(),
                    linearLayout3.getPaddingBottom());
            ImageView imageView2 =
                    (ImageView) this.mFunctionKeyTipsLayout2.mRootView.findViewById(R.id.animation);
            ((TextView) this.mFunctionKeyTipsLayout2.mRootView.findViewById(R.id.description_title))
                    .setText(R.string.sec_function_key_tips_side_and_volume_down_key_title);
            ((TextView)
                            this.mFunctionKeyTipsLayout2.mRootView.findViewById(
                                    R.id.description_summary))
                    .setText(R.string.sec_function_key_tips_side_and_volume_down_key_desc);
            imageView2.setImageResource(R.drawable.sec_tips_for_power_sidekey);
        }
        LayoutPreference layoutPreference3 = this.mFunctionKeyTipsLayout3;
        if (layoutPreference3 != null) {
            LinearLayout linearLayout4 =
                    (LinearLayout) layoutPreference3.mRootView.findViewById(R.id.layout_container);
            linearLayout4.semSetRoundedCorners(15);
            linearLayout4.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            LinearLayout linearLayout5 =
                    (LinearLayout)
                            this.mFunctionKeyTipsLayout3.mRootView.findViewById(
                                    R.id.description_container);
            linearLayout5.setPaddingRelative(
                    linearLayout5.getPaddingStart(),
                    this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.sec_widget_body_text_padding_bottom)
                            + linearLayout5.getPaddingTop(),
                    linearLayout5.getPaddingEnd(),
                    linearLayout5.getPaddingBottom());
            ImageView imageView3 =
                    (ImageView) this.mFunctionKeyTipsLayout3.mRootView.findViewById(R.id.animation);
            TextView textView =
                    (TextView)
                            this.mFunctionKeyTipsLayout3.mRootView.findViewById(
                                    R.id.description_title);
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.isRTL(this.mContext) ? "\u200f" : ApnSettings.MVNO_NONE);
            sb.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_function_key_tips_bixby_title));
            textView.setText(sb.toString());
            TextView textView2 =
                    (TextView)
                            this.mFunctionKeyTipsLayout3.mRootView.findViewById(
                                    R.id.description_summary);
            Context context = this.mContext;
            textView2.setText(
                    context.getString(
                            R.string.sec_function_key_tips_say_to_bixby,
                            context.getString(R.string.sec_function_key_tips_bixby_desc)));
            imageView3.setImageResource(R.drawable.sec_tips_for_power_bixby);
            this.mFunctionKeyTipsLayout3
                    .mRootView
                    .findViewById(R.id.blank_background)
                    .setVisibility(0);
        }
        getListView().mDrawLastRoundedCorner = false;
        getListView().semSetRoundedCorners(15);
        getListView()
                .semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_widget_round_and_bgcolor));
        getListView().seslSetFillBottomEnabled(true);
    }
}
