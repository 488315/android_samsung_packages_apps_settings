package com.samsung.android.settings.notification;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSummarizeContentFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_summarize_content_settings);
    public Context mContext;
    public boolean mPath;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecSummarizeContentFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_summarize_content_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mPath) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "noti_intelligence_summarize_content",
                    z ? 1 : 0);
            Log.secD("SecSummarizeContentFragment", "setSummarizeContent : " + z);
            return;
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "noti_intelligence_priority_conversation",
                z ? 1 : 0);
        Log.secD("SecSummarizeContentFragment", "setPriorityConversation : " + z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.mContext = getContext();
        this.mPath = arguments != null ? arguments.getBoolean("path") : false;
        getActivity()
                .setTitle(
                        this.mContext
                                .getResources()
                                .getString(
                                        this.mPath
                                                ? R.string.noti_intelligence_summarize_notifications
                                                : R.string
                                                        .noti_intelligence_priority_notification));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context;
        int i;
        LayoutPreference layoutPreference = (LayoutPreference) findPreference("summarize_content");
        TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.linked_text);
        layoutPreference.mRootView.findViewById(R.id.help_animation_container).setVisibility(8);
        TextView textView2 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.help_description_text);
        TextView textView3 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.process_description_text);
        textView2.setPadding(
                textView2.getPaddingLeft(),
                0,
                textView2.getPaddingRight(),
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.helppage_tipabox_margin_bottom));
        if (this.mPath) {
            context = this.mContext;
            i = R.string.noti_intelligence_summarize_content_description_text;
        } else {
            context = this.mContext;
            i = R.string.noti_intelligence_priority_converstation_description_text;
        }
        textView2.setText(context.getString(i));
        String string =
                getString(
                        R.string.noti_intelligence_terms_condition,
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
                                      // com.samsung.android.settings.notification.SecSummarizeContentFragment.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        try {
                            SecSummarizeContentFragment.this.getContext();
                            SecSummarizeContentFragment.this
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
                                    "SecSummarizeContentFragment",
                                    "ActivityNotFoundException : " + e.getMessage());
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(true);
                        textPaint.setTypeface(create);
                        textPaint.setColor(
                                SecSummarizeContentFragment.this
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
        textView.setVisibility(this.mPath ? 0 : 8);
        textView3.setVisibility(this.mPath ? 0 : 8);
        if (!this.mPath) {
            removePreference("more_ai_feature");
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchBar.show();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        boolean z = true;
        if (!this.mPath
                ? Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "noti_intelligence_priority_conversation",
                                0)
                        == 0
                : Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "noti_intelligence_summarize_content",
                                0)
                        == 0) {
            z = false;
        }
        settingsMainSwitchBar.setChecked(z);
    }
}
