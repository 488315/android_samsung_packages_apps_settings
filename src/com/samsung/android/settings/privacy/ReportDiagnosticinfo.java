package com.samsung.android.settings.privacy;

import android.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReportDiagnosticinfo extends AlertActivity
        implements View.OnClickListener, DialogInterface.OnClickListener {
    public CheckedTextView mAgreeBtn;
    public CheckedTextView mCheckList_1;
    public LinearLayout mChnLayout;
    public TextView mDiagnosticsText;
    public LinearLayout mGlobalLayout;
    public LayoutInflater mInflater;
    public CheckedTextView mOtherInfoAgreeBtn;
    public LinearLayout mOtherInfoLayout;
    public TextView mOtherInfoText;
    public Button okButton;

    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        super.onApplyThemeResource(
                theme,
                (getResources().getConfiguration().uiMode & 48) == 32
                        ? R.style.Theme.DeviceDefault.Dialog.Alert
                        : R.style.Theme.DeviceDefault.Light.Dialog.Alert,
                z);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == com.android.settings.R.id.learn_more) {
            Intent intent = new Intent();
            intent.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.privacy.DiagnosticDataActivity");
            startActivity(intent);
            return;
        }
        if (id == com.android.settings.R.id.learn_more_2) {
            Intent intent2 = new Intent();
            intent2.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.privacy.InformationLinkingActivity");
            startActivity(intent2);
            return;
        }
        if (id == com.android.settings.R.id.agree_btn) {
            this.mAgreeBtn.setChecked(!r5.isChecked());
            if (this.mAgreeBtn.isChecked()) {
                return;
            }
            this.mOtherInfoAgreeBtn.setChecked(false);
            return;
        }
        if (id == com.android.settings.R.id.other_info_agree_Btn) {
            if (this.mOtherInfoAgreeBtn.isChecked()) {
                this.mOtherInfoAgreeBtn.setChecked(false);
                return;
            } else {
                this.mOtherInfoAgreeBtn.setChecked(true);
                this.mAgreeBtn.setChecked(true);
                return;
            }
        }
        if (id == com.android.settings.R.id.check_list_1) {
            this.mCheckList_1.setChecked(!r5.isChecked());
            this.okButton.setEnabled(this.mCheckList_1.isChecked());
        } else if (id == com.android.settings.R.id.check_list_1_detail) {
            Intent intent3 = new Intent();
            intent3.setClassName(
                    getPackageName(),
                    "com.samsung.android.settings.privacy.DiagnosticDataActivity");
            intent3.putExtra("swlp_option", 0);
            startActivity(intent3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mInflater = (LayoutInflater) getSystemService("layout_inflater");
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getText(com.android.settings.R.string.label_diagnostics_info);
        alertParams.mView =
                this.mInflater.inflate(
                        com.android.settings.R.layout.sec_report_diagnostic_info, (ViewGroup) null);
        alertParams.mPositiveButtonText =
                getResources().getString(com.android.settings.R.string.common_ok);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText =
                getResources().getString(com.android.settings.R.string.cancel);
        alertParams.mNegativeButtonListener = this;
        this.mDiagnosticsText =
                (TextView)
                        alertParams.mView.findViewById(
                                com.android.settings.R.id.diagnostics_info_message);
        this.mGlobalLayout =
                (LinearLayout)
                        alertParams.mView.findViewById(com.android.settings.R.id.global_layout);
        this.mChnLayout =
                (LinearLayout) alertParams.mView.findViewById(com.android.settings.R.id.chn_layout);
        if (Utils.isTablet()) {
            this.mDiagnosticsText.setText(
                    getString(
                            com.android.settings.R.string
                                    .label_diagnostics_info_text_dream_tablet));
        } else {
            this.mDiagnosticsText.setText(
                    getString(
                            com.android.settings.R.string.label_diagnostics_info_text_dream_phone));
        }
        if (Rune.SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA) {
            boolean z =
                    Settings.System.getInt(getContentResolver(), "samsung_errorlog_agree", 0) == 1;
            this.mGlobalLayout.setVisibility(8);
            CheckedTextView checkedTextView =
                    (CheckedTextView)
                            alertParams.mView.findViewById(com.android.settings.R.id.check_list_1);
            this.mCheckList_1 = checkedTextView;
            checkedTextView.setOnClickListener(this);
            this.mCheckList_1.setChecked(z);
            ((TextView)
                            alertParams.mView.findViewById(
                                    com.android.settings.R.id.check_list_1_detail))
                    .setOnClickListener(this);
        } else {
            this.mChnLayout.setVisibility(8);
            ((LinearLayout) alertParams.mView.findViewById(com.android.settings.R.id.learn_more))
                    .setOnClickListener(this);
            LinearLayout linearLayout =
                    (LinearLayout)
                            alertParams.mView.findViewById(com.android.settings.R.id.learn_more_2);
            this.mOtherInfoLayout = linearLayout;
            linearLayout.setOnClickListener(this);
            CheckedTextView checkedTextView2 =
                    (CheckedTextView)
                            alertParams.mView.findViewById(com.android.settings.R.id.agree_btn);
            this.mAgreeBtn = checkedTextView2;
            checkedTextView2.setOnClickListener(this);
            this.mAgreeBtn.setChecked(
                    Settings.System.getInt(getContentResolver(), "samsung_errorlog_agree", 0) == 1);
            CheckedTextView checkedTextView3 = this.mAgreeBtn;
            checkedTextView3.setText(
                    checkedTextView3.getText()
                            + getString(com.android.settings.R.string.diagnostic_optional));
            this.mAgreeBtn.setContentDescription(
                    ((Object) this.mAgreeBtn.getText())
                            + ", "
                            + getApplicationContext()
                                    .getResources()
                                    .getString(
                                            com.android.settings.R.string.sec_wifi_checkbox_tss));
            CheckedTextView checkedTextView4 =
                    (CheckedTextView)
                            alertParams.mView.findViewById(
                                    com.android.settings.R.id.other_info_agree_Btn);
            this.mOtherInfoAgreeBtn = checkedTextView4;
            checkedTextView4.setOnClickListener(this);
            this.mOtherInfoAgreeBtn.setChecked(
                    Settings.System.getInt(getContentResolver(), "samsung_other_info_agree", 0)
                            == 1);
            CheckedTextView checkedTextView5 = this.mOtherInfoAgreeBtn;
            checkedTextView5.setText(
                    checkedTextView5.getText()
                            + getString(com.android.settings.R.string.diagnostic_optional));
            ((TextView) alertParams.mView.findViewById(com.android.settings.R.id.learn_more_text))
                    .setText(getString(com.android.settings.R.string.diagnostic_data));
            TextView textView =
                    (TextView)
                            alertParams.mView.findViewById(
                                    com.android.settings.R.id.learn_more_2_text);
            this.mOtherInfoText = textView;
            textView.setText(getString(com.android.settings.R.string.diagnostic_other_info_linked));
            if (!SecurityUtils.isSupportLinkingInfo(this)) {
                this.mOtherInfoLayout.setVisibility(8);
                this.mOtherInfoText.setVisibility(8);
                this.mOtherInfoAgreeBtn.setVisibility(8);
            }
        }
        getWindow().setGravity(80);
        setupAlert();
    }

    public final void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.button1);
        this.okButton = button;
        if (Rune.SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA) {
            button.setEnabled(this.mCheckList_1.isChecked());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        boolean isChecked;
        if (i == -1) {
            boolean z =
                    Settings.System.getInt(getContentResolver(), "samsung_errorlog_agree", 0) == 1;
            boolean z2 = Rune.SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA;
            if (z2) {
                isChecked = this.mCheckList_1.isChecked();
            } else {
                isChecked = this.mAgreeBtn.isChecked();
            }
            if (z2) {
                SecurityUtils.setDiagnostic(this, this.mCheckList_1.isChecked(), false);
            } else {
                SecurityUtils.setDiagnostic(
                        this, this.mAgreeBtn.isChecked(), this.mOtherInfoAgreeBtn.isChecked());
            }
            if (z != isChecked) {
                LoggingHelper.insertEventLogging(56041, 8010, isChecked);
            }
        }
        finish();
    }
}
