package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungKnoxPrivacyNotice extends AppCompatActivity implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Button mAgreeButton;
    public String mFields;
    public String mPPRegion;
    public SamsungKnoxPrivacyNotice context = null;
    public TextView textView = null;
    public ProgressBar mProgress = null;
    public AsyncTask asynctask = null;
    public int newPPVersion = -1;
    public boolean newPPType = false;
    public boolean isReAgreement = false;
    public boolean mPPClientAvailable = true;
    public boolean mSendMinorBroadcast = false;
    public String activityTitle = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KnoxLinkMovementMethod extends LinkMovementMethod {
        public static KnoxLinkMovementMethod sInstance;

        @Override // android.text.method.LinkMovementMethod,
                  // android.text.method.ScrollingMovementMethod,
                  // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public final boolean onTouchEvent(
                TextView textView, Spannable spannable, MotionEvent motionEvent) {
            boolean z;
            Context context = textView.getContext();
            try {
                z = super.onTouchEvent(textView, spannable, motionEvent);
                if (z) {
                    try {
                        if (motionEvent.getAction() == 1) {
                            CharSequence text = textView.getText();
                            URLSpan[] uRLSpanArr =
                                    (URLSpan[])
                                            ((Spannable) text)
                                                    .getSpans(0, text.length(), URLSpan.class);
                            if (uRLSpanArr.length != 0) {
                                String charSequence =
                                        text.subSequence(
                                                        ((Spannable) text)
                                                                .getSpanStart(uRLSpanArr[0]),
                                                        ((Spannable) text)
                                                                .getSpanEnd(uRLSpanArr[0]))
                                                .toString();
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.setData(Uri.parse(charSequence));
                                ApplicationPolicy applicationPolicy =
                                        EnterpriseKnoxManager.getInstance()
                                                .getKnoxContainerManager(
                                                        context, UserHandle.semGetMyUserId())
                                                .getApplicationPolicy();
                                Iterator<ResolveInfo> it =
                                        context.getPackageManager()
                                                .queryIntentActivities(intent, 65536)
                                                .iterator();
                                boolean z2 = false;
                                while (it.hasNext()) {
                                    if (applicationPolicy.getApplicationStateEnabled(
                                            it.next().activityInfo.packageName)) {
                                        z2 = true;
                                    }
                                }
                                if (!z2) {
                                    throw new ActivityNotFoundException();
                                }
                            }
                        }
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(
                                        context,
                                        context.getResources()
                                                .getString(R.string.no_browser_enabled),
                                        0)
                                .show();
                        return z;
                    }
                }
            } catch (ActivityNotFoundException unused2) {
                z = false;
            }
            return z;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PPRetrieverTask extends AsyncTask {
        public PPRetrieverTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                return SamsungKnoxPrivacyNotice.this.getPPFromClient();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            String str = (String) obj;
            super.onPostExecute(str);
            SamsungKnoxPrivacyNotice samsungKnoxPrivacyNotice = SamsungKnoxPrivacyNotice.this;
            int i = SamsungKnoxPrivacyNotice.$r8$clinit;
            samsungKnoxPrivacyNotice.getClass();
            if (SamsungKnoxPrivacyNotice.isChinaModel$1()) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.klmsagent.SettingsPrivacyPolicyAction");
                intent.setComponent(
                        new ComponentName(
                                EdmConstants.KLMS_PKG_NAME,
                                "com.samsung.klmsagent.activities.KnoxPPDetailsActivity"));
                intent.putExtra("pp_text", str);
                intent.putExtra("pp_fields", SamsungKnoxPrivacyNotice.this.mFields);
                SamsungKnoxPrivacyNotice.this.startActivityForResult(intent, 0);
                SamsungKnoxPrivacyNotice.this.mProgress.setVisibility(8);
                SamsungKnoxPrivacyNotice.this.finish();
                return;
            }
            if (str == null) {
                Log.e("KnoxNotice", "PPRetrieverTask onPostExecute: no PP text found");
                SamsungKnoxPrivacyNotice samsungKnoxPrivacyNotice2 = SamsungKnoxPrivacyNotice.this;
                samsungKnoxPrivacyNotice2.setTitle(samsungKnoxPrivacyNotice2.activityTitle);
                SamsungKnoxPrivacyNotice.this.finish();
                return;
            }
            try {
                Log.d("KnoxNotice", "PPRetrieverTask onPostExecute: PP text Received");
                SamsungKnoxPrivacyNotice.this.mProgress.setVisibility(8);
                SamsungKnoxPrivacyNotice.this.getClass();
                String tagContent =
                        SamsungKnoxPrivacyNotice.getTagContent(
                                str, UniversalCredentialUtil.AGENT_TITLE);
                SamsungKnoxPrivacyNotice.this.getClass();
                String tagContent2 =
                        SamsungKnoxPrivacyNotice.getTagContent(str, PhoneRestrictionPolicy.BODY);
                Log.d("KnoxNotice", "result: ".concat(str));
                if (tagContent != null) {
                    SamsungKnoxPrivacyNotice.this.setTitle(tagContent);
                } else {
                    SamsungKnoxPrivacyNotice samsungKnoxPrivacyNotice3 =
                            SamsungKnoxPrivacyNotice.this;
                    samsungKnoxPrivacyNotice3.setTitle(samsungKnoxPrivacyNotice3.activityTitle);
                }
                if (tagContent2 != null) {
                    SamsungKnoxPrivacyNotice.this.textView.setText(
                            Html.fromHtml(
                                    "<!DOCTYPE html><html><head><title></title></head><body>"
                                            + tagContent2
                                            + "</body></html>",
                                    0));
                } else {
                    SamsungKnoxPrivacyNotice.this.textView.setText(Html.fromHtml(str, 0));
                }
            } catch (Exception e) {
                SamsungKnoxPrivacyNotice.this.textView.setText(Html.fromHtml(str, 0));
                Log.e("KnoxNotice", "Exception in getting Title and Body", e);
            }
            SamsungKnoxPrivacyNotice samsungKnoxPrivacyNotice4 = SamsungKnoxPrivacyNotice.this;
            if (samsungKnoxPrivacyNotice4.newPPType) {
                samsungKnoxPrivacyNotice4.mAgreeButton.setText(
                        samsungKnoxPrivacyNotice4.getAgreeButtonText());
                SamsungKnoxPrivacyNotice.this.mAgreeButton.setVisibility(0);
            }
        }
    }

    public static String getTagContent(String str, String str2) {
        String m = ComposerKt$$ExternalSyntheticOutline0.m("<", str2, ">");
        String m2 = ComposerKt$$ExternalSyntheticOutline0.m("</", str2, ">");
        int indexOf = str.indexOf(m);
        int indexOf2 = str.indexOf(m2);
        return (indexOf == -1 || indexOf2 == -1)
                ? ApnSettings.MVNO_NONE
                : str.substring(m.length() + indexOf, indexOf2).trim();
    }

    public static boolean isChinaModel$1() {
        return "CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"))
                || "china".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
    }

    public final void displayContent() {
        if (this.isReAgreement) {
            return;
        }
        try {
            this.asynctask = new PPRetrieverTask().execute(new Void[0]);
        } catch (Exception e) {
            Log.e("KnoxNotice", "Exception in PPRetrieverTask", e);
        }
    }

    public final String getAgreeButtonText() {
        return (this.mPPRegion.equalsIgnoreCase("GDPR") || this.mPPRegion.equalsIgnoreCase("LGPD"))
                ? this.context.getResources().getString(R.string.pp_knox_continue_text)
                : this.context.getResources().getString(R.string.pp_knox_agree_text);
    }

    public final String getPPFromClient() {
        new Bundle();
        Bundle call =
                getContentResolver()
                        .call(
                                Uri.parse(
                                        "content://com.samsung.android.ppclient.PPClientProvider"),
                                "getPPInfo",
                                getApplicationContext().getPackageName(),
                                (Bundle) null);
        if (call != null) {
            r4 = call.containsKey("pp_text") ? call.getString("pp_text") : null;
            if (call.containsKey("pp_region")) {
                this.mPPRegion = call.getString("pp_region");
            }
            if (call.containsKey("pp_version")) {
                this.newPPVersion = call.getInt("pp_version");
            }
            if (call.containsKey("pp_fields")) {
                this.mFields = call.getString("pp_fields", ApnSettings.MVNO_NONE);
            }
        }
        return r4;
    }

    public final boolean isPossibleNetwork() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager =
                (ConnectivityManager)
                        this.context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null
                || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.knox_pp_agree_button) {
            Intent intent = new Intent();
            intent.setPackage("com.samsung.android.mdm");
            intent.setAction("com.samsung.android.ppclient.PP_REAGREEMENT_ACCEPTED");
            intent.putExtra("pp_accepted", true);
            intent.putExtra("pp_version", this.newPPVersion);
            intent.setFlags(32);
            this.context.sendBroadcast(
                    intent, "com.samsung.android.knox.ppclient.permission.KNOX_PRIVACY_POLICY");
            finishAndRemoveTask();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (isPossibleNetwork() || !isChinaModel$1()) {
            displayContent();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = this;
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.ppclient.PP_REPORT_ACCEPTANCE");
        Iterator<ResolveInfo> it =
                getPackageManager().queryBroadcastReceivers(intent, 131072).iterator();
        while (true) {
            if (!it.hasNext()) {
                this.mPPClientAvailable = false;
                break;
            } else if ("com.samsung.android.mdm".equals(it.next().getComponentInfo().packageName)) {
                break;
            }
        }
        boolean isPossibleNetwork = isPossibleNetwork();
        Intent intent2 = getIntent();
        boolean z = intent2 != null && intent2.hasExtra("not_settings");
        if (isChinaModel$1() && !isPossibleNetwork && !z) {
            Toast.makeText(
                            new ContextThemeWrapper(
                                    this.context, android.R.style.Theme.DeviceDefault.Light),
                            R.string.data_connection_error_toast_notification,
                            0)
                    .show();
            finish();
        }
        setContentView(R.layout.sec_samsung_knox_privacy_notice);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pp_update_progressbar);
        this.mProgress = progressBar;
        progressBar.setVisibility(0);
        this.textView = (TextView) findViewById(R.id.knox_pp_content);
        this.activityTitle = getResources().getString(R.string.knox_privacy_notice_title);
        this.mAgreeButton = (Button) findViewById(R.id.knox_pp_agree_button);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        Button button = this.mAgreeButton;
        if (button != null) {
            button.setOnClickListener(this);
            this.mAgreeButton.setVisibility(8);
        }
        if (this.mPPClientAvailable) {
            if (isChinaModel$1() && getSupportActionBar() != null) {
                getSupportActionBar().hide();
            } else if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                setTitle(ApnSettings.MVNO_NONE);
            }
            setupReAgreementData(intent2);
            if (SemSystemProperties.getCountryIso().equalsIgnoreCase("KR")
                    && (z || this.isReAgreement)) {
                this.activityTitle = getResources().getString(R.string.knox_privacy_consent_title);
            }
        }
        if (!SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            this.textView.setMovementMethod(LinkMovementMethod.getInstance());
            return;
        }
        TextView textView = this.textView;
        if (KnoxLinkMovementMethod.sInstance == null) {
            KnoxLinkMovementMethod.sInstance = new KnoxLinkMovementMethod();
        }
        textView.setMovementMethod(KnoxLinkMovementMethod.sInstance);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        AsyncTask asyncTask = this.asynctask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        if (this.mPPClientAvailable) {
            setupReAgreementData(intent);
        }
        super.onNewIntent(intent);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (isPossibleNetwork() || !isChinaModel$1()) {
            displayContent();
        }
    }

    public final void setupReAgreementData(Intent intent) {
        if (intent == null || !intent.hasExtra("show_button")) {
            return;
        }
        this.mProgress.setVisibility(8);
        this.newPPType = intent.getBooleanExtra("show_button", false);
        String stringExtra = intent.getStringExtra("pp_text");
        this.newPPVersion = intent.getIntExtra("pp_version", -1);
        this.mPPRegion = intent.getStringExtra("pp_region");
        if (stringExtra != null) {
            try {
                String tagContent = getTagContent(stringExtra, UniversalCredentialUtil.AGENT_TITLE);
                String tagContent2 = getTagContent(stringExtra, PhoneRestrictionPolicy.BODY);
                Log.d("KnoxNotice", "result: ".concat(stringExtra));
                if (tagContent != null) {
                    setTitle(tagContent);
                } else {
                    setTitle(this.activityTitle);
                }
                if (tagContent2 != null) {
                    this.textView.setText(
                            Html.fromHtml(
                                    "<!DOCTYPE html><html><head><title></title></head><body>"
                                            + tagContent2
                                            + "</body></html>",
                                    0));
                } else {
                    this.textView.setText(Html.fromHtml(stringExtra, 0));
                }
            } catch (Exception e) {
                this.textView.setText(Html.fromHtml(stringExtra, 0));
                Log.e("KnoxNotice", "Exception in getting Title and Body", e);
            }
            if (this.newPPType) {
                this.mAgreeButton.setText(getAgreeButtonText());
                this.mAgreeButton.setVisibility(0);
            }
        } else {
            this.asynctask = new PPRetrieverTask().execute(new Void[0]);
        }
        if (!this.newPPType && !this.mSendMinorBroadcast) {
            this.mSendMinorBroadcast = true;
            Intent intent2 = new Intent();
            intent2.setPackage("com.samsung.android.mdm");
            intent2.setAction("com.samsung.android.ppclient.PP_REAGREEMENT_ACCEPTED");
            intent2.putExtra("pp_accepted", true);
            intent2.putExtra("ppupdate_type", false);
            intent2.putExtra("pp_version", this.newPPVersion);
            intent2.setFlags(32);
            this.context.sendBroadcast(
                    intent2, "com.samsung.android.knox.ppclient.permission.KNOX_PRIVACY_POLICY");
        }
        this.isReAgreement = true;
    }
}
