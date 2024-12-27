package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecRoundButtonView;
import com.samsung.android.util.SemLog;
import com.sec.ims.configuration.DATA;

import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntelligenceServiceActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mCallPackage = ApnSettings.MVNO_NONE;
    public LinearLayout mContainer;
    public IntelligenceServiceAppsManager mISAManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IntelligenceAppListLoader extends AsyncTask {
        public IntelligenceAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            TreeSet[] appList;
            synchronized (this) {
                IntelligenceServiceActivity intelligenceServiceActivity =
                        IntelligenceServiceActivity.this;
                appList =
                        intelligenceServiceActivity.mISAManager.getAppList(
                                intelligenceServiceActivity.mCallPackage);
            }
            return appList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            final int i = 1;
            final int i2 = 0;
            TreeSet[] treeSetArr = (TreeSet[]) obj;
            IntelligenceServiceActivity intelligenceServiceActivity =
                    IntelligenceServiceActivity.this;
            int i3 = IntelligenceServiceActivity.$r8$clinit;
            LinearLayout linearLayout =
                    (LinearLayout) intelligenceServiceActivity.findViewById(R.id.container);
            intelligenceServiceActivity.mContainer = linearLayout;
            linearLayout.removeAllViews();
            if (intelligenceServiceActivity.mISAManager.mPriorityCategory.equals(
                    "com.samsung.android.settings.is.category.productivity")) {
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[1],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_unleash_your_productivity));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[0],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_communicate_more_easily));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[2],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_create_epic_images));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[3],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_unlock_a_healthier_you));
            } else if (intelligenceServiceActivity.mISAManager.mPriorityCategory.equals(
                    "com.samsung.android.settings.is.category.communicate")) {
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[0],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_communicate_more_easily));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[1],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_unleash_your_productivity));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[2],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_create_epic_images));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[3],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_unlock_a_healthier_you));
            } else if (intelligenceServiceActivity.mISAManager.mPriorityCategory.equals(
                    "com.samsung.android.settings.is.category.image")) {
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[2],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_create_epic_images));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[0],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_communicate_more_easily));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[1],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_unleash_your_productivity));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[3],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_unlock_a_healthier_you));
            } else {
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[3],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_unlock_a_healthier_you));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[0],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_communicate_more_easily));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[1],
                        intelligenceServiceActivity.getString(
                                R.string
                                        .sec_intelligence_service_subtitle_unleash_your_productivity));
                intelligenceServiceActivity.initCategoryLayout(
                        treeSetArr[2],
                        intelligenceServiceActivity.getString(
                                R.string.sec_intelligence_service_subtitle_create_epic_images));
            }
            final IntelligenceServiceActivity intelligenceServiceActivity2 =
                    IntelligenceServiceActivity.this;
            SecRoundButtonView secRoundButtonView =
                    (SecRoundButtonView)
                            intelligenceServiceActivity2.findViewById(R.id.button_continue);
            SecRoundButtonView secRoundButtonView2 =
                    (SecRoundButtonView)
                            intelligenceServiceActivity2.findViewById(R.id.button_agree);
            SecRoundButtonView secRoundButtonView3 =
                    (SecRoundButtonView)
                            intelligenceServiceActivity2.findViewById(R.id.button_not_now);
            LinearLayout linearLayout2 =
                    (LinearLayout) intelligenceServiceActivity2.findViewById(R.id.button_layout);
            if (!Rune.isChinaModel()) {
                linearLayout2.setVisibility(8);
                secRoundButtonView.setVisibility(0);
                final int i4 = 2;
                secRoundButtonView.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i5 = i4;
                                IntelligenceServiceActivity intelligenceServiceActivity3 =
                                        intelligenceServiceActivity2;
                                switch (i5) {
                                    case 0:
                                        int i6 = IntelligenceServiceActivity.$r8$clinit;
                                        intelligenceServiceActivity3.setResult(0);
                                        intelligenceServiceActivity3.finish();
                                        break;
                                    case 1:
                                        int i7 = IntelligenceServiceActivity.$r8$clinit;
                                        Settings.System.putInt(
                                                intelligenceServiceActivity3.getContentResolver(),
                                                "ai_info_confirmed",
                                                1);
                                        intelligenceServiceActivity3.setResult(-1);
                                        SALogging.insertSALog("AI002", "AI0003");
                                        intelligenceServiceActivity3.finish();
                                        break;
                                    default:
                                        int i8 = IntelligenceServiceActivity.$r8$clinit;
                                        Settings.System.putInt(
                                                intelligenceServiceActivity3.getContentResolver(),
                                                "ai_info_confirmed",
                                                1);
                                        intelligenceServiceActivity3.setResult(-1);
                                        SALogging.insertSALog("AI002", "AI0003");
                                        intelligenceServiceActivity3.finish();
                                        break;
                                }
                            }
                        });
                return;
            }
            TextView textView =
                    (TextView) intelligenceServiceActivity2.findViewById(R.id.terms_textview);
            String string =
                    intelligenceServiceActivity2.getString(
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
            int indexOf = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
            int indexOf2 = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SIP_TH_TIMER);
            String replaceAll =
                    string.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                            .replaceAll(DATA.DM_FIELD_INDEX.SIP_TH_TIMER, ApnSettings.MVNO_NONE);
            final Typeface create = Typeface.create(Typeface.create("sec", 0), 600, false);
            ClickableSpan clickableSpan =
                    new ClickableSpan() { // from class:
                                          // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity.3
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            switch (i2) {
                                case 0:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 1);
                                    break;
                                case 1:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 2);
                                    break;
                                case 2:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 3);
                                    break;
                                default:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 4);
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
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 1:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 2:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                default:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                            }
                        }
                    };
            int indexOf3 = TextUtils.indexOf(replaceAll, DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);
            int indexOf4 = TextUtils.indexOf(replaceAll, DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE);
            String replaceAll2 =
                    replaceAll
                            .replaceAll(DATA.DM_FIELD_INDEX.PREF_CSCF_PORT, ApnSettings.MVNO_NONE)
                            .replaceAll(
                                    DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE,
                                    ApnSettings.MVNO_NONE);
            ClickableSpan clickableSpan2 =
                    new ClickableSpan() { // from class:
                                          // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity.3
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            switch (i) {
                                case 0:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 1);
                                    break;
                                case 1:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 2);
                                    break;
                                case 2:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 3);
                                    break;
                                default:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 4);
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
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 1:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 2:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                default:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                            }
                        }
                    };
            int indexOf5 = TextUtils.indexOf(replaceAll2, DATA.DM_FIELD_INDEX.AMR_WB);
            int indexOf6 = TextUtils.indexOf(replaceAll2, DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED);
            String replaceAll3 =
                    replaceAll2
                            .replaceAll(DATA.DM_FIELD_INDEX.AMR_WB, ApnSettings.MVNO_NONE)
                            .replaceAll(
                                    DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED, ApnSettings.MVNO_NONE);
            final int i5 = 2;
            ClickableSpan clickableSpan3 =
                    new ClickableSpan() { // from class:
                                          // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity.3
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            switch (i5) {
                                case 0:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 1);
                                    break;
                                case 1:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 2);
                                    break;
                                case 2:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 3);
                                    break;
                                default:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 4);
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
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 1:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 2:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                default:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                            }
                        }
                    };
            int indexOf7 = TextUtils.indexOf(replaceAll3, DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF);
            int indexOf8 = TextUtils.indexOf(replaceAll3, DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING);
            String replaceAll4 =
                    replaceAll3
                            .replaceAll(DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF, ApnSettings.MVNO_NONE)
                            .replaceAll(
                                    DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING, ApnSettings.MVNO_NONE);
            final int i6 = 3;
            ClickableSpan clickableSpan4 =
                    new ClickableSpan() { // from class:
                                          // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity.3
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            switch (i6) {
                                case 0:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 1);
                                    break;
                                case 1:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 2);
                                    break;
                                case 2:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 3);
                                    break;
                                default:
                                    IntelligenceServiceActivity.m1285$$Nest$mstartLaunchURI(
                                            intelligenceServiceActivity2, 4);
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
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 1:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                case 2:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                                default:
                                    textPaint.setUnderlineText(true);
                                    textPaint.setTypeface(create);
                                    textPaint.setColor(
                                            intelligenceServiceActivity2
                                                    .getResources()
                                                    .getColor(
                                                            R.color
                                                                    .sec_intelligence_service_header_title_text_color));
                                    break;
                            }
                        }
                    };
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(replaceAll4);
            final int i7 = 0;
            spannableStringBuilder.setSpan(clickableSpan, indexOf, indexOf2 - 2, 0);
            spannableStringBuilder.setSpan(clickableSpan2, indexOf3, indexOf4 - 2, 0);
            spannableStringBuilder.setSpan(clickableSpan3, indexOf5, indexOf6 - 2, 0);
            spannableStringBuilder.setSpan(clickableSpan4, indexOf7, indexOf8 - 2, 0);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spannableStringBuilder);
            linearLayout2.setVisibility(0);
            secRoundButtonView3.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i52 = i7;
                            IntelligenceServiceActivity intelligenceServiceActivity3 =
                                    intelligenceServiceActivity2;
                            switch (i52) {
                                case 0:
                                    int i62 = IntelligenceServiceActivity.$r8$clinit;
                                    intelligenceServiceActivity3.setResult(0);
                                    intelligenceServiceActivity3.finish();
                                    break;
                                case 1:
                                    int i72 = IntelligenceServiceActivity.$r8$clinit;
                                    Settings.System.putInt(
                                            intelligenceServiceActivity3.getContentResolver(),
                                            "ai_info_confirmed",
                                            1);
                                    intelligenceServiceActivity3.setResult(-1);
                                    SALogging.insertSALog("AI002", "AI0003");
                                    intelligenceServiceActivity3.finish();
                                    break;
                                default:
                                    int i8 = IntelligenceServiceActivity.$r8$clinit;
                                    Settings.System.putInt(
                                            intelligenceServiceActivity3.getContentResolver(),
                                            "ai_info_confirmed",
                                            1);
                                    intelligenceServiceActivity3.setResult(-1);
                                    SALogging.insertSALog("AI002", "AI0003");
                                    intelligenceServiceActivity3.finish();
                                    break;
                            }
                        }
                    });
            final int i8 = 1;
            secRoundButtonView2.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i52 = i8;
                            IntelligenceServiceActivity intelligenceServiceActivity3 =
                                    intelligenceServiceActivity2;
                            switch (i52) {
                                case 0:
                                    int i62 = IntelligenceServiceActivity.$r8$clinit;
                                    intelligenceServiceActivity3.setResult(0);
                                    intelligenceServiceActivity3.finish();
                                    break;
                                case 1:
                                    int i72 = IntelligenceServiceActivity.$r8$clinit;
                                    Settings.System.putInt(
                                            intelligenceServiceActivity3.getContentResolver(),
                                            "ai_info_confirmed",
                                            1);
                                    intelligenceServiceActivity3.setResult(-1);
                                    SALogging.insertSALog("AI002", "AI0003");
                                    intelligenceServiceActivity3.finish();
                                    break;
                                default:
                                    int i82 = IntelligenceServiceActivity.$r8$clinit;
                                    Settings.System.putInt(
                                            intelligenceServiceActivity3.getContentResolver(),
                                            "ai_info_confirmed",
                                            1);
                                    intelligenceServiceActivity3.setResult(-1);
                                    SALogging.insertSALog("AI002", "AI0003");
                                    intelligenceServiceActivity3.finish();
                                    break;
                            }
                        }
                    });
            secRoundButtonView.setVisibility(8);
        }
    }

    /* renamed from: -$$Nest$mstartLaunchURI, reason: not valid java name */
    public static void m1285$$Nest$mstartLaunchURI(
            IntelligenceServiceActivity intelligenceServiceActivity, int i) {
        intelligenceServiceActivity.getClass();
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
        SemLog.d("IntelligenceServiceActivity", "Url = " + str);
        try {
            intelligenceServiceActivity.startActivity(
                    new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException e) {
            SemLog.d(
                    "IntelligenceServiceActivity", "ActivityNotFoundException : " + e.getMessage());
        }
    }

    public final void initCategoryLayout(TreeSet treeSet, String str) {
        if (treeSet.size() > 0) {
            View inflate =
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_intelligence_service_subtitle_layout,
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
                                        R.layout.sec_intelligence_service_item_layout,
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
                View inflate3 =
                        getLayoutInflater()
                                .inflate(
                                        R.layout.sec_intelligence_service_item_divider_layout,
                                        (ViewGroup) null);
                i++;
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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        if (!UsefulfeatureUtils.isSupportedIntelligenceService(this)) {
            Log.i("IntelligenceServiceActivity", "not support IntelligenceService");
            if (AccountUtils.isChildAccount(this)) {
                int i = -1;
                try {
                    Bundle call =
                            getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.samsungaccount.accountmanagerprovider"),
                                            "getFamilyServiceInfo",
                                            "s5d189ajvs",
                                            (Bundle) null);
                    if (call != null) {
                        int i2 = call.getInt("result_code", 1);
                        String string = call.getString("result_message", ApnSettings.MVNO_NONE);
                        if (i2 == 0) {
                            Bundle bundle2 = call.getBundle("result_bundle");
                            if (bundle2 != null) {
                                i = bundle2.getInt("childGraduateAge", -1);
                            }
                        } else {
                            DialogFragment$$ExternalSyntheticOutline0.m(
                                    "Failure message : resultMessage = ", string, "AccountUtils");
                        }
                    } else {
                        Log.d("AccountUtils", "Result bundle is null");
                    }
                } catch (Exception e) {
                    Log.d("AccountUtils", "Exception Error : " + e.getMessage());
                }
                if (i > 0) {
                    Toast.makeText(
                                    this,
                                    getResources()
                                            .getQuantityString(
                                                    R.plurals
                                                            .sec_intelligence_service_child_popup_message,
                                                    i,
                                                    Integer.valueOf(i)),
                                    1)
                            .show();
                }
            }
            finish();
            return;
        }
        int i3 = getResources().getConfiguration().semDisplayDeviceType;
        boolean semIsPopOver = getResources().getConfiguration().semIsPopOver();
        if (isInMultiWindowMode() || semIsPopOver) {
            setContentView(R.layout.sec_intelligence_service_layout);
        } else if (Utils.isTablet()) {
            setContentView(R.layout.sec_intelligence_service_layout_tablet);
        } else {
            String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            setContentView(R.layout.sec_intelligence_service_layout);
        }
        TextView textView = (TextView) findViewById(R.id.header_title);
        TextView textView2 = (TextView) findViewById(R.id.header_description);
        if (UserHandle.myUserId() == UsefulfeatureUtils.getManagedProfileId(this)) {
            textView.setText(R.string.sec_intelligence_service_for_work_title);
            textView2.setText(R.string.sec_intelligence_service_for_work_sub_title);
        }
        this.mISAManager = new IntelligenceServiceAppsManager(this, UserHandle.myUserId());
        String stringExtra = getIntent().getStringExtra("key_calling_package");
        this.mCallPackage = stringExtra;
        if (stringExtra == null) {
            str = "Calling package is null";
        } else {
            str = "Calling package = " + this.mCallPackage;
        }
        SemLog.d("IntelligenceServiceActivity", str);
        getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback() { // from class:
                                                      // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity.2
                            @Override // androidx.activity.OnBackPressedCallback
                            public final void handleOnBackPressed() {
                                IntelligenceServiceActivity intelligenceServiceActivity =
                                        IntelligenceServiceActivity.this;
                                intelligenceServiceActivity.setResult(0);
                                setEnabled(false);
                                SALogging.insertSALog("AI002", "AI0002");
                                intelligenceServiceActivity
                                        .getOnBackPressedDispatcher()
                                        .onBackPressed();
                                if (intelligenceServiceActivity.isFinishing()
                                        || intelligenceServiceActivity.isDestroyed()) {
                                    return;
                                }
                                intelligenceServiceActivity.finish();
                            }
                        });
        new IntelligenceAppListLoader()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (((UiModeManager) getSystemService("uimode")).getNightMode() == 2) {
            getWindow().getDecorView().setSystemUiVisibility(256);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(16);
        }
    }
}
