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
import android.text.style.BulletSpan;
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
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAutoGroupingFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public Context mContext;
    public SettingsMainSwitchBar mSwitchBar;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_auto_grouping_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.SecAutoGroupingFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int i =
                    Settings.System.getInt(
                            context.getContentResolver(), "noti_auto_more_grouping", 0);
            String valueOf = String.valueOf(36421);
            String valueOf2 = String.valueOf(i);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecAutoGroupingFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_auto_grouping_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        this.mContext = getContext();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "noti_auto_more_grouping", z ? 1 : 0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TextView textView =
                (TextView)
                        ((LayoutPreference) findPreference("auto_group_preview"))
                                .mRootView.findViewById(R.id.linked_text);
        TextView textView2 =
                (TextView)
                        ((LayoutPreference) findPreference("auto_group_preview"))
                                .mRootView.findViewById(R.id.bullet_view);
        String string = getString(R.string.noti_intelligence_auto_group_desc_text2);
        String string2 = getString(R.string.noti_intelligence_auto_group_desc_text3);
        String string3 = getString(R.string.noti_intelligence_auto_group_desc_text4);
        String string4 = getString(R.string.noti_intelligence_auto_group_desc_text5);
        int length = string.length();
        int length2 = string2.length();
        int length3 = string3.length();
        int length4 = string4.length();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (Locale.getDefault().getLanguage().equals("ko")) {
            spannableStringBuilder.append((CharSequence) string).append((CharSequence) "\n");
            spannableStringBuilder.append((CharSequence) string2).append((CharSequence) "\n");
            spannableStringBuilder.append((CharSequence) string3).append((CharSequence) "\n");
            spannableStringBuilder.append((CharSequence) string4).append((CharSequence) "\n");
            spannableStringBuilder.setSpan(new BulletSpan(20), 0, length, 33);
            int i = length + 1;
            int i2 = length2 + i;
            spannableStringBuilder.setSpan(new BulletSpan(20), i, i2, 33);
            int i3 = i2 + 1;
            int i4 = length3 + i3;
            spannableStringBuilder.setSpan(new BulletSpan(20), i3, i4, 33);
            int i5 = i4 + 1;
            spannableStringBuilder.setSpan(new BulletSpan(20), i5, length4 + i5, 33);
        } else {
            spannableStringBuilder.append((CharSequence) string2).append((CharSequence) "\n");
            spannableStringBuilder.append((CharSequence) string3).append((CharSequence) "\n");
            spannableStringBuilder.append((CharSequence) string4).append((CharSequence) "\n");
            spannableStringBuilder.setSpan(new BulletSpan(20), 0, length2, 33);
            int i6 = length2 + 1;
            int i7 = length3 + i6;
            spannableStringBuilder.setSpan(new BulletSpan(20), i6, i7, 33);
            int i8 = i7 + 1;
            spannableStringBuilder.setSpan(new BulletSpan(20), i8, length4 + i8, 33);
        }
        textView2.setText(spannableStringBuilder);
        String string5 =
                getString(
                        R.string.noti_intelligence_terms_condition,
                        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                        DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
        int indexOf = TextUtils.indexOf(string5, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
        int indexOf2 = TextUtils.indexOf(string5, DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
        String replaceAll =
                string5.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                        .replaceAll(DATA.DM_FIELD_INDEX.SIP_TH_TIMER, ApnSettings.MVNO_NONE);
        final Typeface create = Typeface.create(Typeface.create("sec", 0), 600, false);
        ClickableSpan clickableSpan =
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.notification.SecAutoGroupingFragment.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        try {
                            SecAutoGroupingFragment.this.getContext();
                            SecAutoGroupingFragment.this
                                    .getContext()
                                    .startActivity(
                                            new Intent(
                                                    "android.intent.action.VIEW",
                                                    Uri.parse(
                                                            UsefulfeatureUtils
                                                                    .getSamsungAccountTermsURI(
                                                                            0))));
                        } catch (ActivityNotFoundException e) {
                            Log.d(
                                    "SecAutoGroupingFragment",
                                    "ActivityNotFoundException : " + e.getMessage());
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(true);
                        textPaint.setTypeface(create);
                        textPaint.setColor(
                                SecAutoGroupingFragment.this
                                        .getResources()
                                        .getColor(
                                                R.color
                                                        .sec_intelligence_service_header_title_text_color));
                    }
                };
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(replaceAll);
        spannableStringBuilder2.setSpan(clickableSpan, indexOf, indexOf2 - 2, 0);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableStringBuilder2);
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
        this.mSwitchBar.setChecked(
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "noti_auto_more_grouping", 0)
                        != 0);
    }
}
