package com.android.settings.network;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivitySettingsManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.google.common.net.InternetDomainName;
import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateDnsModeDialogPreference extends CustomDialogPreferenceCompat
        implements DialogInterface.OnClickListener, TextWatcher {
    static final String HOSTNAME_KEY = "private_dns_specifier";
    static final String MODE_KEY = "private_dns_mode";
    public static final Map PRIVATE_DNS_MAP;
    EditText mEditText;
    int mMode;

    static {
        HashMap hashMap = new HashMap();
        PRIVATE_DNS_MAP = hashMap;
        hashMap.put(1, Integer.valueOf(R.id.private_dns_mode_off));
        hashMap.put(2, Integer.valueOf(R.id.private_dns_mode_opportunistic));
        hashMap.put(3, Integer.valueOf(R.id.private_dns_mode_provider));
    }

    public PrivateDnsModeDialogPreference(Context context) {
        super(context);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        updateDialogInfo();
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onBindDialogView(View view) {
        Context context = getContext();
        ContentResolver contentResolver = context.getContentResolver();
        this.mMode = ConnectivitySettingsManager.getPrivateDnsMode(context);
        EditText editText = (EditText) view.findViewById(R.id.private_dns_mode_provider_hostname);
        this.mEditText = editText;
        editText.addTextChangedListener(this);
        this.mEditText.setText(Settings.Global.getString(contentResolver, HOSTNAME_KEY));
        final RadioButton radioButton = (RadioButton) view.findViewById(R.id.private_dns_mode_off);
        final RadioButton radioButton2 =
                (RadioButton) view.findViewById(R.id.private_dns_mode_opportunistic);
        final RadioButton radioButton3 =
                (RadioButton) view.findViewById(R.id.private_dns_mode_provider);
        Integer num =
                (Integer)
                        ((HashMap) PRIVATE_DNS_MAP)
                                .getOrDefault(
                                        Integer.valueOf(this.mMode),
                                        Integer.valueOf(R.id.private_dns_mode_opportunistic));
        if (num.intValue() == R.id.private_dns_mode_off) {
            radioButton.setChecked(true);
        } else if (num.intValue() == R.id.private_dns_mode_opportunistic) {
            radioButton2.setChecked(true);
        } else if (num.intValue() == R.id.private_dns_mode_provider) {
            radioButton3.setChecked(true);
        }
        final View findViewById = view.findViewById(R.id.private_dns_mode_off_layout);
        final View findViewById2 = view.findViewById(R.id.private_dns_mode_opportunistic_layout);
        final View findViewById3 = view.findViewById(R.id.private_dns_mode_provider_layout);
        View.OnClickListener onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.network.PrivateDnsModeDialogPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        if (view2 == findViewById || view2 == radioButton) {
                            radioButton.setChecked(true);
                            radioButton2.setChecked(false);
                            radioButton3.setChecked(false);
                            PrivateDnsModeDialogPreference.this.mMode = 1;
                        }
                        if (view2 == findViewById2 || view2 == radioButton2) {
                            radioButton2.setChecked(true);
                            radioButton.setChecked(false);
                            radioButton3.setChecked(false);
                            PrivateDnsModeDialogPreference.this.mMode = 2;
                        }
                        if (view2 == findViewById3 || view2 == radioButton3) {
                            radioButton3.setChecked(true);
                            radioButton2.setChecked(false);
                            radioButton.setChecked(false);
                            PrivateDnsModeDialogPreference.this.mMode = 3;
                        }
                        PrivateDnsModeDialogPreference privateDnsModeDialogPreference =
                                PrivateDnsModeDialogPreference.this;
                        Map map = PrivateDnsModeDialogPreference.PRIVATE_DNS_MAP;
                        privateDnsModeDialogPreference.updateDialogInfo();
                    }
                };
        findViewById.setOnClickListener(onClickListener);
        radioButton.setOnClickListener(onClickListener);
        findViewById2.setOnClickListener(onClickListener);
        radioButton2.setOnClickListener(onClickListener);
        findViewById3.setOnClickListener(onClickListener);
        radioButton3.setOnClickListener(onClickListener);
        TextView textView = (TextView) view.findViewById(R.id.private_dns_help_info);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        AnnotationSpan.LinkInfo linkInfo =
                new AnnotationSpan.LinkInfo(
                        context,
                        HelpUtils.getHelpIntent(
                                context,
                                context.getString(R.string.help_uri_private_dns),
                                context.getClass().getName()));
        if (linkInfo.mActionable.booleanValue()) {
            textView.setText(
                    AnnotationSpan.linkify(
                            context.getText(R.string.private_dns_help_message), linkInfo));
        } else {
            textView.setVisibility(8);
        }
        SALogging.insertSALog(String.valueOf(110), String.valueOf(7143));
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getContext(), UserHandle.myUserId(), "disallow_config_private_dns")
                != null) {
            preferenceViewHolder.itemView.setEnabled(true);
        }
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Context context = getContext();
            if (this.mMode == 3) {
                ConnectivitySettingsManager.setPrivateDnsHostname(
                        context, this.mEditText.getText().toString());
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(context, 1249, this.mMode);
            ConnectivitySettingsManager.setPrivateDnsMode(context, this.mMode);
            if ("on"
                    .equals(
                            Settings.Global.getString(
                                    context.getContentResolver(), "private_dns_by_swifi"))) {
                int i2 = this.mMode;
                Log.i("PrivateDnsModeDialog", "NotifyDnsConfigChanged mode:" + i2);
                Intent intent = new Intent("com.samsung.android.intent.action.PRIVATE_DNS_CHANGED");
                intent.setClassName(
                        "com.samsung.android.fast",
                        "com.samsung.android.fast.common.SecureWifiReceiver");
                intent.putExtra(MODE_KEY, i2);
                context.sendBroadcast(intent, "android.permission.WRITE_SECURE_SETTINGS");
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getContext(), UserHandle.myUserId(), "disallow_config_private_dns");
        if (checkIfRestrictionEnforced == null) {
            super.performClick();
        } else {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getContext(), checkIfRestrictionEnforced);
        }
    }

    public final void updateDialogInfo() {
        boolean z = false;
        boolean z2 = true;
        boolean z3 = 3 == this.mMode;
        EditText editText = this.mEditText;
        if (editText != null) {
            editText.setEnabled(z3);
        }
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button button = alertDialog == null ? null : alertDialog.getButton(-1);
        if (button != null) {
            if (z3) {
                String editable = this.mEditText.getText().toString();
                try {
                    editable.getClass();
                    new InternetDomainName(editable);
                    z = true;
                } catch (IllegalArgumentException unused) {
                }
                z2 = z;
            }
            button.setEnabled(z2);
        }
    }

    public PrivateDnsModeDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PrivateDnsModeDialogPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public PrivateDnsModeDialogPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}
