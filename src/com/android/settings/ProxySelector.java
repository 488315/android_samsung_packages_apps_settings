package com.android.settings;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.ProxyInfo;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.net.module.util.ProxyUtils;
import com.android.settings.core.InstrumentedFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.Arrays;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ProxySelector extends InstrumentedFragment implements DialogCreatable {
    public Button mClearButton;
    public final AnonymousClass1 mClearHandler;
    public Button mDefaultButton;
    public final AnonymousClass1 mDefaultHandler;
    public SettingsPreferenceFragment.SettingsDialogFragment mDialogFragment;
    public EditText mExclusionListField;
    public EditText mHostnameField;
    public Button mOKButton;
    public final AnonymousClass1 mOKHandler;
    public final AnonymousClass4 mOnFocusChangeHandler = new AnonymousClass4();
    public EditText mPortField;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.ProxySelector$4, reason: invalid class name */
    public final class AnonymousClass4 implements View.OnFocusChangeListener {
        @Override // android.view.View.OnFocusChangeListener
        public final void onFocusChange(View view, boolean z) {
            if (z) {
                Selection.selectAll((Spannable) ((TextView) view).getText());
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.ProxySelector$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.ProxySelector$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.ProxySelector$1] */
    public ProxySelector() {
        final int i = 0;
        this.mOKHandler =
                new View.OnClickListener(this) { // from class: com.android.settings.ProxySelector.1
                    public final /* synthetic */ ProxySelector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                ProxySelector proxySelector = this.this$0;
                                String trim =
                                        proxySelector.mHostnameField.getText().toString().trim();
                                String trim2 = proxySelector.mPortField.getText().toString().trim();
                                String trim3 =
                                        proxySelector
                                                .mExclusionListField
                                                .getText()
                                                .toString()
                                                .trim();
                                int i2 = 0;
                                if (ProxySelector.validate(trim, trim2, trim3) == 0) {
                                    if (trim2.length() > 0) {
                                        try {
                                            i2 = Integer.parseInt(trim2);
                                        } catch (NumberFormatException unused) {
                                            return;
                                        }
                                    }
                                    ((ConnectivityManager)
                                                    proxySelector
                                                            .getActivity()
                                                            .getSystemService("connectivity"))
                                            .setGlobalProxy(
                                                    ProxyInfo.buildDirectProxy(
                                                            trim,
                                                            i2,
                                                            Arrays.asList(trim3.split(","))));
                                    this.this$0.getActivity().onBackPressed();
                                    break;
                                } else {
                                    if (proxySelector.mDialogFragment != null) {
                                        Log.e("ProxySelector", "Old dialog fragment not null!");
                                    }
                                    SettingsPreferenceFragment.SettingsDialogFragment newInstance =
                                            SettingsPreferenceFragment.SettingsDialogFragment
                                                    .newInstance(proxySelector, 0);
                                    proxySelector.mDialogFragment = newInstance;
                                    newInstance.show(
                                            proxySelector.getActivity().getSupportFragmentManager(),
                                            Integer.toString(0));
                                    break;
                                }
                            case 1:
                                this.this$0.mHostnameField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mPortField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mExclusionListField.setText(ApnSettings.MVNO_NONE);
                                break;
                            default:
                                this.this$0.populateFields();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mClearHandler =
                new View.OnClickListener(this) { // from class: com.android.settings.ProxySelector.1
                    public final /* synthetic */ ProxySelector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                ProxySelector proxySelector = this.this$0;
                                String trim =
                                        proxySelector.mHostnameField.getText().toString().trim();
                                String trim2 = proxySelector.mPortField.getText().toString().trim();
                                String trim3 =
                                        proxySelector
                                                .mExclusionListField
                                                .getText()
                                                .toString()
                                                .trim();
                                int i22 = 0;
                                if (ProxySelector.validate(trim, trim2, trim3) == 0) {
                                    if (trim2.length() > 0) {
                                        try {
                                            i22 = Integer.parseInt(trim2);
                                        } catch (NumberFormatException unused) {
                                            return;
                                        }
                                    }
                                    ((ConnectivityManager)
                                                    proxySelector
                                                            .getActivity()
                                                            .getSystemService("connectivity"))
                                            .setGlobalProxy(
                                                    ProxyInfo.buildDirectProxy(
                                                            trim,
                                                            i22,
                                                            Arrays.asList(trim3.split(","))));
                                    this.this$0.getActivity().onBackPressed();
                                    break;
                                } else {
                                    if (proxySelector.mDialogFragment != null) {
                                        Log.e("ProxySelector", "Old dialog fragment not null!");
                                    }
                                    SettingsPreferenceFragment.SettingsDialogFragment newInstance =
                                            SettingsPreferenceFragment.SettingsDialogFragment
                                                    .newInstance(proxySelector, 0);
                                    proxySelector.mDialogFragment = newInstance;
                                    newInstance.show(
                                            proxySelector.getActivity().getSupportFragmentManager(),
                                            Integer.toString(0));
                                    break;
                                }
                            case 1:
                                this.this$0.mHostnameField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mPortField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mExclusionListField.setText(ApnSettings.MVNO_NONE);
                                break;
                            default:
                                this.this$0.populateFields();
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mDefaultHandler =
                new View.OnClickListener(this) { // from class: com.android.settings.ProxySelector.1
                    public final /* synthetic */ ProxySelector this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                ProxySelector proxySelector = this.this$0;
                                String trim =
                                        proxySelector.mHostnameField.getText().toString().trim();
                                String trim2 = proxySelector.mPortField.getText().toString().trim();
                                String trim3 =
                                        proxySelector
                                                .mExclusionListField
                                                .getText()
                                                .toString()
                                                .trim();
                                int i22 = 0;
                                if (ProxySelector.validate(trim, trim2, trim3) == 0) {
                                    if (trim2.length() > 0) {
                                        try {
                                            i22 = Integer.parseInt(trim2);
                                        } catch (NumberFormatException unused) {
                                            return;
                                        }
                                    }
                                    ((ConnectivityManager)
                                                    proxySelector
                                                            .getActivity()
                                                            .getSystemService("connectivity"))
                                            .setGlobalProxy(
                                                    ProxyInfo.buildDirectProxy(
                                                            trim,
                                                            i22,
                                                            Arrays.asList(trim3.split(","))));
                                    this.this$0.getActivity().onBackPressed();
                                    break;
                                } else {
                                    if (proxySelector.mDialogFragment != null) {
                                        Log.e("ProxySelector", "Old dialog fragment not null!");
                                    }
                                    SettingsPreferenceFragment.SettingsDialogFragment newInstance =
                                            SettingsPreferenceFragment.SettingsDialogFragment
                                                    .newInstance(proxySelector, 0);
                                    proxySelector.mDialogFragment = newInstance;
                                    newInstance.show(
                                            proxySelector.getActivity().getSupportFragmentManager(),
                                            Integer.toString(0));
                                    break;
                                }
                            case 1:
                                this.this$0.mHostnameField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mPortField.setText(ApnSettings.MVNO_NONE);
                                this.this$0.mExclusionListField.setText(ApnSettings.MVNO_NONE);
                                break;
                            default:
                                this.this$0.populateFields();
                                break;
                        }
                    }
                };
    }

    public static int validate(String str, String str2, String str3) {
        int validate = ProxyUtils.validate(str, str2, str3);
        if (validate == 0) {
            return 0;
        }
        if (validate == 1) {
            return R.string.proxy_error_empty_host_set_port;
        }
        if (validate == 2) {
            return R.string.proxy_error_invalid_host;
        }
        if (validate == 3) {
            return R.string.proxy_error_empty_port;
        }
        if (validate == 4) {
            return R.string.proxy_error_invalid_port;
        }
        if (validate == 5) {
            return R.string.proxy_error_invalid_exclusion_list;
        }
        Log.e("ProxySelector", "Unknown proxy settings error");
        return -1;
    }

    @Override // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        return 574;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 82;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        boolean z =
                ((DevicePolicyManager) getActivity().getSystemService("device_policy"))
                                .getGlobalProxyAdmin()
                        == null;
        this.mHostnameField.setEnabled(z);
        this.mPortField.setEnabled(z);
        this.mExclusionListField.setEnabled(z);
        this.mOKButton.setEnabled(z);
        this.mClearButton.setEnabled(z);
        this.mDefaultButton.setEnabled(z);
    }

    @Override // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 0) {
            return null;
        }
        String string =
                getActivity()
                        .getString(
                                validate(
                                        this.mHostnameField.getText().toString().trim(),
                                        this.mPortField.getText().toString().trim(),
                                        this.mExclusionListField.getText().toString().trim()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.proxy_error);
        builder.setPositiveButton(
                R.string.proxy_error_dismiss, (DialogInterface.OnClickListener) null);
        builder.P.mMessage = string;
        return builder.create();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.proxy, viewGroup, false);
        this.mView = inflate;
        EditText editText = (EditText) inflate.findViewById(R.id.hostname);
        this.mHostnameField = editText;
        editText.setOnFocusChangeListener(this.mOnFocusChangeHandler);
        EditText editText2 = (EditText) inflate.findViewById(R.id.port);
        this.mPortField = editText2;
        editText2.setOnClickListener(this.mOKHandler);
        this.mPortField.setOnFocusChangeListener(this.mOnFocusChangeHandler);
        EditText editText3 = (EditText) inflate.findViewById(R.id.exclusionlist);
        this.mExclusionListField = editText3;
        editText3.setOnFocusChangeListener(this.mOnFocusChangeHandler);
        Button button = (Button) inflate.findViewById(R.id.action);
        this.mOKButton = button;
        button.setOnClickListener(this.mOKHandler);
        Button button2 = (Button) inflate.findViewById(R.id.clear);
        this.mClearButton = button2;
        button2.setOnClickListener(this.mClearHandler);
        Button button3 = (Button) inflate.findViewById(R.id.defaultView);
        this.mDefaultButton = button3;
        button3.setOnClickListener(this.mDefaultHandler);
        populateFields();
        return this.mView;
    }

    public final void populateFields() {
        String str;
        String str2;
        int i;
        FragmentActivity activity = getActivity();
        ProxyInfo globalProxy =
                ((ConnectivityManager) getActivity().getSystemService("connectivity"))
                        .getGlobalProxy();
        String str3 = ApnSettings.MVNO_NONE;
        if (globalProxy != null) {
            str2 = globalProxy.getHost();
            i = globalProxy.getPort();
            String[] exclusionList = globalProxy.getExclusionList();
            Pattern pattern = ProxyUtils.HOSTNAME_PATTERN;
            str =
                    exclusionList == null
                            ? ApnSettings.MVNO_NONE
                            : TextUtils.join(",", exclusionList);
        } else {
            str = ApnSettings.MVNO_NONE;
            str2 = str;
            i = -1;
        }
        if (str2 == null) {
            str2 = ApnSettings.MVNO_NONE;
        }
        this.mHostnameField.setText(str2);
        if (i != -1) {
            str3 = Integer.toString(i);
        }
        this.mPortField.setText(str3);
        this.mExclusionListField.setText(str);
        Intent intent = activity.getIntent();
        String stringExtra = intent.getStringExtra("button-label");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mOKButton.setText(stringExtra);
        }
        String stringExtra2 = intent.getStringExtra(UniversalCredentialUtil.AGENT_TITLE);
        if (TextUtils.isEmpty(stringExtra2)) {
            activity.setTitle(R.string.proxy_settings_title);
        } else {
            activity.setTitle(stringExtra2);
        }
    }
}
