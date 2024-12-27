package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AboutAdvancedIntelligenceSettings extends SecDynamicFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AboutAdvancedIntelligenceSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 80000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_about_advanced_intelligence;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        enableAutoFlowLogging(false);
        SALogging.insertSALog("AI004");
        LoggingHelper.insertEventLogging("AI004", 9000);
        super.onCreate(bundle);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TextView textView =
                (TextView)
                        ((LayoutPreference) findPreference("about_advanced_intelligence"))
                                .mRootView.findViewById(R.id.linked_text);
        String string =
                getString(
                        R.string.sec_intelligence_service_about_advanced_intelligence_linked_text,
                        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                        DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
        int indexOf = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
        int indexOf2 = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
        String replaceAll =
                string.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                        .replaceAll(DATA.DM_FIELD_INDEX.SIP_TH_TIMER, ApnSettings.MVNO_NONE);
        final Typeface create = Typeface.create(Typeface.create("sec", 0), 600, false);
        ClickableSpan clickableSpan =
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.usefulfeature.intelligenceservice.AboutAdvancedIntelligenceSettings.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        try {
                            AboutAdvancedIntelligenceSettings.this.getContext();
                            AboutAdvancedIntelligenceSettings.this
                                    .getContext()
                                    .startActivity(
                                            new Intent(
                                                    "android.intent.action.VIEW",
                                                    Uri.parse(
                                                            UsefulfeatureUtils
                                                                    .getSamsungAccountTermsURI(
                                                                            1))));
                        } catch (ActivityNotFoundException e) {
                            Log.d(
                                    "AboutAdvancedIntelligenceSettings",
                                    "ActivityNotFoundException : " + e.getMessage());
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(true);
                        textPaint.setTypeface(create);
                        textPaint.setColor(
                                AboutAdvancedIntelligenceSettings.this
                                        .getResources()
                                        .getColor(
                                                R.color
                                                        .sec_intelligence_service_header_title_text_color));
                    }
                };
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(replaceAll);
        spannableStringBuilder.setSpan(clickableSpan, indexOf, indexOf2 - 2, 0);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableStringBuilder);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
}
