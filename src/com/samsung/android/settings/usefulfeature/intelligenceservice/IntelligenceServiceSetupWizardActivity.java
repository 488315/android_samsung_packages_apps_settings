package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.util.SemLog;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import com.sec.ims.configuration.DATA;

import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntelligenceServiceSetupWizardActivity extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LinearLayout mContainer;
    public IntelligenceServiceAppsManager mISAManager;
    public boolean mIsChildAccount;
    public boolean isFOTASetUpWizard = false;
    public boolean isSupportedSkipButton = false;
    public final AnonymousClass1 scrollChangeListener =
            new View
                    .OnScrollChangeListener() { // from class:
                                                // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.1
                @Override // android.view.View.OnScrollChangeListener
                public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                    IntelligenceServiceSetupWizardActivity intelligenceServiceSetupWizardActivity =
                            IntelligenceServiceSetupWizardActivity.this;
                    int i5 = IntelligenceServiceSetupWizardActivity.$r8$clinit;
                    int primaryActionButtonText =
                            intelligenceServiceSetupWizardActivity.getPrimaryActionButtonText();
                    FooterButton footerButton =
                            intelligenceServiceSetupWizardActivity.mPrimaryButton;
                    if (footerButton != null) {
                        footerButton.setText(
                                intelligenceServiceSetupWizardActivity.mContext,
                                primaryActionButtonText);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IntelligenceAppListLoader extends AsyncTask {
        public IntelligenceAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            TreeSet[] appList;
            synchronized (this) {
                appList = IntelligenceServiceSetupWizardActivity.this.mISAManager.getAppList(null);
            }
            return appList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            final int i = 1;
            final int i2 = 0;
            TreeSet[] treeSetArr = (TreeSet[]) obj;
            final IntelligenceServiceSetupWizardActivity intelligenceServiceSetupWizardActivity =
                    IntelligenceServiceSetupWizardActivity.this;
            int i3 = IntelligenceServiceSetupWizardActivity.$r8$clinit;
            LinearLayout linearLayout =
                    (LinearLayout)
                            intelligenceServiceSetupWizardActivity.findViewById(R.id.container);
            intelligenceServiceSetupWizardActivity.mContainer = linearLayout;
            linearLayout.removeAllViews();
            intelligenceServiceSetupWizardActivity.mContainer.addOnLayoutChangeListener(
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8,
                                int i9,
                                int i10,
                                int i11) {
                            final IntelligenceServiceSetupWizardActivity
                                    intelligenceServiceSetupWizardActivity2 =
                                            IntelligenceServiceSetupWizardActivity.this;
                            int i12 = IntelligenceServiceSetupWizardActivity.$r8$clinit;
                            intelligenceServiceSetupWizardActivity2.getClass();
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            IntelligenceServiceSetupWizardActivity
                                                    intelligenceServiceSetupWizardActivity3 =
                                                            IntelligenceServiceSetupWizardActivity
                                                                    .this;
                                            int i13 =
                                                    IntelligenceServiceSetupWizardActivity
                                                            .$r8$clinit;
                                            int primaryActionButtonText =
                                                    intelligenceServiceSetupWizardActivity3
                                                            .getPrimaryActionButtonText();
                                            FooterButton footerButton =
                                                    intelligenceServiceSetupWizardActivity3
                                                            .mPrimaryButton;
                                            if (footerButton != null) {
                                                footerButton.setText(
                                                        intelligenceServiceSetupWizardActivity3
                                                                .mContext,
                                                        primaryActionButtonText);
                                            }
                                        }
                                    });
                        }
                    });
            intelligenceServiceSetupWizardActivity.initCategoryLayout$1(
                    treeSetArr[0],
                    intelligenceServiceSetupWizardActivity.getString(
                            R.string.sec_intelligence_service_subtitle_communicate_more_easily));
            intelligenceServiceSetupWizardActivity.initCategoryLayout$1(
                    treeSetArr[1],
                    intelligenceServiceSetupWizardActivity.getString(
                            R.string.sec_intelligence_service_subtitle_unleash_your_productivity));
            intelligenceServiceSetupWizardActivity.initCategoryLayout$1(
                    treeSetArr[2],
                    intelligenceServiceSetupWizardActivity.getString(
                            R.string.sec_intelligence_service_subtitle_create_epic_images));
            intelligenceServiceSetupWizardActivity.initCategoryLayout$1(
                    treeSetArr[3],
                    intelligenceServiceSetupWizardActivity.getString(
                            R.string.sec_intelligence_service_subtitle_unlock_a_healthier_you));
            View inflate =
                    intelligenceServiceSetupWizardActivity
                            .getLayoutInflater()
                            .inflate(
                                    R.layout.sec_intelligence_service_suw_terms_layout,
                                    (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.terms_textview);
            String string =
                    intelligenceServiceSetupWizardActivity.getString(
                            R.string.sec_intelligence_service_terms);
            if (Rune.isChinaModel()) {
                String string2 =
                        intelligenceServiceSetupWizardActivity.getString(
                                R.string.sec_intelligence_service_terms_china,
                                new Object[] {
                                    DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                                    DATA.DM_FIELD_INDEX.SIP_TH_TIMER,
                                    DATA.DM_FIELD_INDEX.PREF_CSCF_PORT,
                                    DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE,
                                    DATA.DM_FIELD_INDEX.AMR_WB,
                                    DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED,
                                    DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF,
                                    DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING
                                });
                int indexOf = TextUtils.indexOf(string2, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
                int indexOf2 = TextUtils.indexOf(string2, DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
                String replaceAll =
                        string2.replaceAll(
                                        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.SIP_TH_TIMER, ApnSettings.MVNO_NONE);
                final Typeface create = Typeface.create(Typeface.create("sec", 0), 600, false);
                ClickableSpan clickableSpan =
                        new ClickableSpan() { // from class:
                                              // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.2
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 1);
                                        break;
                                    case 1:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 2);
                                        break;
                                    case 2:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 3);
                                        break;
                                    default:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 4);
                                        break;
                                }
                            }

                            @Override // android.text.style.ClickableSpan,
                                      // android.text.style.CharacterStyle
                            public final void updateDrawState(TextPaint textPaint) {
                                switch (i2) {
                                    case 0:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 1:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 2:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    default:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                }
                            }
                        };
                int indexOf3 = TextUtils.indexOf(replaceAll, DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);
                int indexOf4 =
                        TextUtils.indexOf(replaceAll, DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE);
                String replaceAll2 =
                        replaceAll
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.PREF_CSCF_PORT, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE,
                                        ApnSettings.MVNO_NONE);
                ClickableSpan clickableSpan2 =
                        new ClickableSpan() { // from class:
                                              // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.2
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 1);
                                        break;
                                    case 1:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 2);
                                        break;
                                    case 2:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 3);
                                        break;
                                    default:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 4);
                                        break;
                                }
                            }

                            @Override // android.text.style.ClickableSpan,
                                      // android.text.style.CharacterStyle
                            public final void updateDrawState(TextPaint textPaint) {
                                switch (i) {
                                    case 0:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 1:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 2:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    default:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                }
                            }
                        };
                int indexOf5 = TextUtils.indexOf(replaceAll2, DATA.DM_FIELD_INDEX.AMR_WB);
                int indexOf6 =
                        TextUtils.indexOf(replaceAll2, DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED);
                String replaceAll3 =
                        replaceAll2
                                .replaceAll(DATA.DM_FIELD_INDEX.AMR_WB, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED,
                                        ApnSettings.MVNO_NONE);
                int i4 = indexOf6 - 2;
                final int i5 = 2;
                ClickableSpan clickableSpan3 =
                        new ClickableSpan() { // from class:
                                              // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.2
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                switch (i5) {
                                    case 0:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 1);
                                        break;
                                    case 1:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 2);
                                        break;
                                    case 2:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 3);
                                        break;
                                    default:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 4);
                                        break;
                                }
                            }

                            @Override // android.text.style.ClickableSpan,
                                      // android.text.style.CharacterStyle
                            public final void updateDrawState(TextPaint textPaint) {
                                switch (i5) {
                                    case 0:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 1:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 2:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    default:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                }
                            }
                        };
                int indexOf7 = TextUtils.indexOf(replaceAll3, DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF);
                int indexOf8 =
                        TextUtils.indexOf(replaceAll3, DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING);
                String replaceAll4 =
                        replaceAll3
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING,
                                        ApnSettings.MVNO_NONE);
                final int i6 = 3;
                ClickableSpan clickableSpan4 =
                        new ClickableSpan() { // from class:
                                              // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.2
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                switch (i6) {
                                    case 0:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 1);
                                        break;
                                    case 1:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 2);
                                        break;
                                    case 2:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 3);
                                        break;
                                    default:
                                        IntelligenceServiceSetupWizardActivity
                                                .m1286$$Nest$mstartLaunchURI(
                                                        intelligenceServiceSetupWizardActivity, 4);
                                        break;
                                }
                            }

                            @Override // android.text.style.ClickableSpan,
                                      // android.text.style.CharacterStyle
                            public final void updateDrawState(TextPaint textPaint) {
                                switch (i6) {
                                    case 0:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 1:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    case 2:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                    default:
                                        textPaint.setUnderlineText(true);
                                        textPaint.setTypeface(create);
                                        textPaint.setColor(
                                                intelligenceServiceSetupWizardActivity
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_intelligence_service_header_title_text_color));
                                        break;
                                }
                            }
                        };
                SpannableStringBuilder spannableStringBuilder =
                        new SpannableStringBuilder(replaceAll4);
                spannableStringBuilder.setSpan(clickableSpan, indexOf, indexOf2 - 2, 0);
                spannableStringBuilder.setSpan(clickableSpan2, indexOf3, indexOf4 - 2, 0);
                spannableStringBuilder.setSpan(clickableSpan3, indexOf5, i4, 0);
                spannableStringBuilder.setSpan(clickableSpan4, indexOf7, indexOf8 - 2, 0);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(spannableStringBuilder);
                intelligenceServiceSetupWizardActivity.mContainer.addView(inflate);
            } else {
                textView.setText(string);
                intelligenceServiceSetupWizardActivity.mContainer.addView(inflate);
            }
            ScrollView scrollView =
                    intelligenceServiceSetupWizardActivity.mRootLayout.getScrollView();
            if (scrollView != null) {
                scrollView.setOnScrollChangeListener(
                        intelligenceServiceSetupWizardActivity.scrollChangeListener);
            }
            final int i7 = 0;
            intelligenceServiceSetupWizardActivity.setPrimaryActionButton(
                    intelligenceServiceSetupWizardActivity.getPrimaryActionButtonText(),
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.6
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i7) {
                                case 0:
                                    IntelligenceServiceSetupWizardActivity
                                            intelligenceServiceSetupWizardActivity2 =
                                                    intelligenceServiceSetupWizardActivity;
                                    int i8 = IntelligenceServiceSetupWizardActivity.$r8$clinit;
                                    if (!intelligenceServiceSetupWizardActivity2
                                            .isScrollBottomReached()) {
                                        IntelligenceServiceSetupWizardActivity
                                                intelligenceServiceSetupWizardActivity3 =
                                                        intelligenceServiceSetupWizardActivity;
                                        if (intelligenceServiceSetupWizardActivity3.mPrimaryButton
                                                .text.equals(
                                                intelligenceServiceSetupWizardActivity3.getString(
                                                        R.string
                                                                .sec_intelligence_service_button_more))) {
                                            ScrollView scrollView2 =
                                                    intelligenceServiceSetupWizardActivity
                                                            .mRootLayout.getScrollView();
                                            if (scrollView2 != null) {
                                                scrollView2.pageScroll(130);
                                            }
                                            if (!intelligenceServiceSetupWizardActivity
                                                    .isFOTASetUpWizard) {
                                                SALogging.insertSALog("AI001", "AI0024");
                                                break;
                                            } else {
                                                SALogging.insertSALog("AI005", "AI0026");
                                                break;
                                            }
                                        }
                                    }
                                    Settings.System.putInt(
                                            intelligenceServiceSetupWizardActivity
                                                    .getContentResolver(),
                                            "ai_info_confirmed",
                                            1);
                                    Intent intent = new Intent();
                                    intent.putExtra(
                                            "isChildAccount",
                                            intelligenceServiceSetupWizardActivity.mIsChildAccount);
                                    intelligenceServiceSetupWizardActivity.setResult(-1, intent);
                                    if (intelligenceServiceSetupWizardActivity.isFOTASetUpWizard) {
                                        SALogging.insertSALog("AI005", "AI0027");
                                    } else {
                                        SALogging.insertSALog("AI001", "AI0025");
                                    }
                                    intelligenceServiceSetupWizardActivity.finish();
                                    break;
                                default:
                                    Intent intent2 = new Intent();
                                    intent2.putExtra(
                                            "isChildAccount",
                                            intelligenceServiceSetupWizardActivity.mIsChildAccount);
                                    intelligenceServiceSetupWizardActivity.setResult(7, intent2);
                                    intelligenceServiceSetupWizardActivity.finish();
                                    break;
                            }
                        }
                    });
            if (intelligenceServiceSetupWizardActivity.isSupportedSkipButton) {
                final int i8 = 1;
                intelligenceServiceSetupWizardActivity.setSecondaryActionButton(
                        R.string.sec_intelligence_service_china_button_not_now,
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSetupWizardActivity.6
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i8) {
                                    case 0:
                                        IntelligenceServiceSetupWizardActivity
                                                intelligenceServiceSetupWizardActivity2 =
                                                        intelligenceServiceSetupWizardActivity;
                                        int i82 = IntelligenceServiceSetupWizardActivity.$r8$clinit;
                                        if (!intelligenceServiceSetupWizardActivity2
                                                .isScrollBottomReached()) {
                                            IntelligenceServiceSetupWizardActivity
                                                    intelligenceServiceSetupWizardActivity3 =
                                                            intelligenceServiceSetupWizardActivity;
                                            if (intelligenceServiceSetupWizardActivity3
                                                    .mPrimaryButton.text.equals(
                                                    intelligenceServiceSetupWizardActivity3
                                                            .getString(
                                                                    R.string
                                                                            .sec_intelligence_service_button_more))) {
                                                ScrollView scrollView2 =
                                                        intelligenceServiceSetupWizardActivity
                                                                .mRootLayout.getScrollView();
                                                if (scrollView2 != null) {
                                                    scrollView2.pageScroll(130);
                                                }
                                                if (!intelligenceServiceSetupWizardActivity
                                                        .isFOTASetUpWizard) {
                                                    SALogging.insertSALog("AI001", "AI0024");
                                                    break;
                                                } else {
                                                    SALogging.insertSALog("AI005", "AI0026");
                                                    break;
                                                }
                                            }
                                        }
                                        Settings.System.putInt(
                                                intelligenceServiceSetupWizardActivity
                                                        .getContentResolver(),
                                                "ai_info_confirmed",
                                                1);
                                        Intent intent = new Intent();
                                        intent.putExtra(
                                                "isChildAccount",
                                                intelligenceServiceSetupWizardActivity
                                                        .mIsChildAccount);
                                        intelligenceServiceSetupWizardActivity.setResult(
                                                -1, intent);
                                        if (intelligenceServiceSetupWizardActivity
                                                .isFOTASetUpWizard) {
                                            SALogging.insertSALog("AI005", "AI0027");
                                        } else {
                                            SALogging.insertSALog("AI001", "AI0025");
                                        }
                                        intelligenceServiceSetupWizardActivity.finish();
                                        break;
                                    default:
                                        Intent intent2 = new Intent();
                                        intent2.putExtra(
                                                "isChildAccount",
                                                intelligenceServiceSetupWizardActivity
                                                        .mIsChildAccount);
                                        intelligenceServiceSetupWizardActivity.setResult(
                                                7, intent2);
                                        intelligenceServiceSetupWizardActivity.finish();
                                        break;
                                }
                            }
                        });
            }
            IntelligenceServiceSetupWizardActivity.this.mContainer.setVisibility(0);
        }
    }

    /* renamed from: -$$Nest$mstartLaunchURI, reason: not valid java name */
    public static void m1286$$Nest$mstartLaunchURI(
            IntelligenceServiceSetupWizardActivity intelligenceServiceSetupWizardActivity, int i) {
        intelligenceServiceSetupWizardActivity.getClass();
        String str =
                new String(Base64.decode("aHR0cHM6Ly9wb2xpY2llcy5zYW1zdW5nLmNuL3Rlcm1zPw==", 2));
        String str2 = "&language=" + Locale.getDefault().getISO3Language();
        String str3 = "&region=" + Locale.getDefault().getISO3Country();
        if (i == 1) {
            str = str + "appKey=j5p7ll8g33&applicationRegion=CHN" + str2 + str3 + "&type=PN";
        } else if (i == 2) {
            str =
                    str
                            + "appKey=j5p7ll8g33&applicationRegion=CHN"
                            + str2
                            + str3
                            + "&type=PN#advancedIntelligence-collect";
        } else if (i == 3) {
            str =
                    str
                            + "appKey=j5p7ll8g33&applicationRegion=CHN"
                            + str2
                            + str3
                            + "&type=PN#advancedIntelligence-sharing";
        } else if (i == 4) {
            str = str + "appKey=j5p7ll8g33&applicationRegion=CHN" + str2 + str3 + "&type=TC";
        }
        SemLog.d("IntelligenceServiceSetupWizardActivity", "Url = " + str);
        try {
            Intent intent =
                    new Intent(
                            "com.samsung.android.samsungaccount.action.ACTION_WEBVIEW_SUW_EXTERNAL");
            intent.setPackage("com.osp.app.signin");
            intent.putExtra("ServerUrl", str);
            intelligenceServiceSetupWizardActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            SemLog.d(
                    "IntelligenceServiceSetupWizardActivity",
                    "ActivityNotFoundException : " + e.getMessage());
        }
    }

    public final int getPrimaryActionButtonText() {
        return isScrollBottomReached()
                ? Rune.isChinaModel()
                        ? R.string.sec_intelligence_service_china_button_agree
                        : R.string.sec_intelligence_service_button_next
                : R.string.sec_intelligence_service_button_more;
    }

    public final void initCategoryLayout$1(TreeSet treeSet, String str) {
        if (treeSet.size() > 0) {
            View inflate =
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_intelligence_service_suw_subtitle_layout,
                                    (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.sub_title)).setText(str);
            this.mContainer.addView(inflate);
            Iterator it = treeSet.iterator();
            int i = 0;
            while (it.hasNext()) {
                IntelligenceServiceAppInfo intelligenceServiceAppInfo =
                        (IntelligenceServiceAppInfo) it.next();
                View inflate2 =
                        getLayoutInflater()
                                .inflate(
                                        R.layout.sec_intelligence_service_suw_item_layout,
                                        (ViewGroup) null);
                ((ImageView) inflate2.findViewById(R.id.icon))
                        .setImageDrawable(intelligenceServiceAppInfo.icon);
                ((TextView) inflate2.findViewById(R.id.title))
                        .setText(intelligenceServiceAppInfo.title);
                TextView textView = (TextView) inflate2.findViewById(R.id.summary);
                if (!TextUtils.isEmpty(intelligenceServiceAppInfo.summary)) {
                    textView.setVisibility(0);
                    textView.setText(intelligenceServiceAppInfo.summary);
                }
                this.mContainer.addView(inflate2);
                i++;
                View inflate3 =
                        getLayoutInflater()
                                .inflate(
                                        R.layout.sec_intelligence_service_item_divider_layout,
                                        (ViewGroup) null);
                if (treeSet.size() != i) {
                    this.mContainer.addView(inflate3);
                }
            }
            this.mContainer.addView(
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_intelligence_service_divider_layout,
                                    (ViewGroup) null));
        }
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIsChildAccount = AccountUtils.isChildAccount(this);
        if (!UsefulfeatureUtils.isSupportedIntelligenceService(this)) {
            SemLog.i("IntelligenceServiceSetupWizardActivity", "not support IntelligenceService");
            Intent intent = new Intent();
            intent.putExtra("isChildAccount", this.mIsChildAccount);
            setResult(EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS, intent);
            finish();
        }
        this.isFOTASetUpWizard = getIntent().getBooleanExtra("fota_suw", false);
        if (Rune.isChinaModel()) {
            this.isSupportedSkipButton = true;
        }
        setContentView(R.layout.sec_intelligence_service_setup_wizard_layout, false);
        setHeaderIcon(getResources().getDrawable(R.drawable.sec_ic_intelligence));
        setHeaderTitle(R.string.sec_intelligence_service_title);
        this.mRootLayout
                .getHeaderTextView()
                .setTypeface(
                        Typeface.create(
                                Typeface.create("sec", 1),
                                KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED,
                                false));
        setDescriptionText(R.string.sec_intelligence_service_sub_title);
        this.mISAManager = new IntelligenceServiceAppsManager(this, UserHandle.myUserId());
        new IntelligenceAppListLoader()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }
}
