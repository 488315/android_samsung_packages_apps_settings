package com.samsung.android.settings.connection.ethernet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.EthernetManager;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.ProxyInfo;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.R;
import com.android.settings.Utils;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.net.ExtendedEthernetManager;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EthernetConfigDialog extends AlertDialog implements DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    public final AnonymousClass3 ipTextWatcher;
    public final RadioButton mConTypeDhcp;
    public final RadioButton mConTypeManual;
    public final Context mContext;
    public final Spinner mDevList;
    public final EditText mDns;
    public final EthernetEnabler mEthEnabler;
    public final EthernetManager mEthManager;
    public ExtendedEthernetManager mExtendedEthernetManager;
    public final EditText mGw;
    public ProxyInfo mHttpProxy;
    public final EditText mIpaddr;
    public final EditText mMask;
    public final LinearLayout mOuterLayout;
    public TextView mProxyExclusionListView;
    public TextView mProxyHostView;
    public TextView mProxyPacView;
    public TextView mProxyPortView;
    public ProxyInfo mProxyProperties;
    public final LinearLayout mProxySettingLayout;
    public int mProxySettings;
    public final Spinner mProxySettingsSpinner;
    public final View mView;
    public final View staticInputView;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.text.TextWatcher, com.samsung.android.settings.connection.ethernet.EthernetConfigDialog$3] */
    public EthernetConfigDialog(FragmentActivity fragmentActivity, EthernetEnabler ethernetEnabler) {
        super(fragmentActivity);
        ?? r0 = new TextWatcher() { // from class: com.samsung.android.settings.connection.ethernet.EthernetConfigDialog.3
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                Log.d("EtherenetSettings", "afterTextChanged");
                EthernetConfigDialog.this.enableSaveIfAppropriate();
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Log.d("EtherenetSettings", "beforeTextChanged");
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Log.d("EtherenetSettings", "onTextChanged");
            }
        };
        this.ipTextWatcher = r0;
        this.mContext = fragmentActivity;
        new Handler();
        new EthernetLayer$1();
        this.mEthEnabler = ethernetEnabler;
        this.mEthManager = (EthernetManager) fragmentActivity.getSystemService("ethernet");
        View inflate = getLayoutInflater().inflate(R.layout.sec_eth_configure, (ViewGroup) null);
        this.mView = inflate;
        setView(inflate);
        this.staticInputView = this.mView.findViewById(R.id.enterprise_wrapper);
        Spinner spinner = (Spinner) this.mView.findViewById(R.id.eth_dev_spinner);
        this.mDevList = spinner;
        spinner.getBackground().setTint(fragmentActivity.getColor(R.color.sec_wifi_ap_dialog_spinner_icon_tint_color));
        this.mConTypeDhcp = (RadioButton) this.mView.findViewById(R.id.dhcp_radio);
        this.mProxySettingLayout = (LinearLayout) this.mView.findViewById(R.id.proxy_settings_fields);
        Spinner spinner2 = (Spinner) this.mView.findViewById(R.id.proxy_settings);
        this.mProxySettingsSpinner = spinner2;
        spinner2.getBackground().setTint(fragmentActivity.getColor(R.color.sec_wifi_ap_dialog_spinner_icon_tint_color));
        this.mProxySettingsSpinner.setOnItemSelectedListener(this);
        this.mConTypeManual = (RadioButton) this.mView.findViewById(R.id.manual_radio);
        EditText editText = (EditText) this.mView.findViewById(R.id.ipaddr_edit);
        this.mIpaddr = editText;
        editText.addTextChangedListener(r0);
        EditText editText2 = (EditText) this.mView.findViewById(R.id.netmask_edit);
        this.mMask = editText2;
        editText2.addTextChangedListener(r0);
        EditText editText3 = (EditText) this.mView.findViewById(R.id.eth_dns_edit);
        this.mDns = editText3;
        editText3.addTextChangedListener(r0);
        EditText editText4 = (EditText) this.mView.findViewById(R.id.eth_gw_edit);
        this.mGw = editText4;
        editText4.addTextChangedListener(r0);
        this.mOuterLayout = (LinearLayout) this.mView.findViewById(R.id.outerlayout);
        this.mConTypeDhcp.setChecked(true);
        Log.i("EtherenetSettings", "buildDialogContent mConTypeDhcp true");
        this.mConTypeManual.setChecked(false);
        this.mProxySettingLayout.setVisibility(0);
        if (Utils.isRTL(fragmentActivity)) {
            this.mConTypeDhcp.setTextDirection(4);
            this.mConTypeManual.setTextDirection(4);
            this.mDevList.setTextDirection(4);
            this.mDevList.setGravity(5);
        }
        this.mIpaddr.setEnabled(false);
        this.mMask.setEnabled(false);
        this.mDns.setEnabled(false);
        this.mGw.setEnabled(false);
        final int i = 0;
        this.mConTypeManual.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.settings.connection.ethernet.EthernetConfigDialog.1
            public final /* synthetic */ EthernetConfigDialog this$0;

            {
                this.this$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.this$0.staticInputView.setVisibility(0);
                        this.this$0.mIpaddr.setEnabled(true);
                        this.this$0.mIpaddr.requestFocus();
                        EthernetConfigDialog ethernetConfigDialog = this.this$0;
                        ethernetConfigDialog.mConTypeManual.setNextFocusDownId(ethernetConfigDialog.mIpaddr.getId());
                        this.this$0.getButton(-1).setNextFocusUpId(this.this$0.mGw.getId());
                        this.this$0.getButton(-2).setNextFocusUpId(this.this$0.mGw.getId());
                        ((InputMethodManager) this.this$0.getContext().getApplicationContext().getSystemService("input_method")).showSoftInput(this.this$0.mIpaddr, 1);
                        this.this$0.mDns.setEnabled(true);
                        this.this$0.mGw.setEnabled(true);
                        this.this$0.mMask.setEnabled(true);
                        this.this$0.enableSaveIfAppropriate();
                        break;
                    default:
                        this.this$0.staticInputView.setVisibility(8);
                        EthernetConfigDialog ethernetConfigDialog2 = this.this$0;
                        ethernetConfigDialog2.mConTypeManual.setNextFocusDownId(ethernetConfigDialog2.getButton(-2).getId());
                        this.this$0.getButton(-1).setNextFocusUpId(this.this$0.mConTypeManual.getId());
                        this.this$0.getButton(-2).setNextFocusUpId(this.this$0.mConTypeManual.getId());
                        ((InputMethodManager) this.this$0.getContext().getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(this.this$0.mIpaddr.getWindowToken(), 2);
                        this.this$0.enableSaveIfAppropriate();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mConTypeDhcp.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.settings.connection.ethernet.EthernetConfigDialog.1
            public final /* synthetic */ EthernetConfigDialog this$0;

            {
                this.this$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.this$0.staticInputView.setVisibility(0);
                        this.this$0.mIpaddr.setEnabled(true);
                        this.this$0.mIpaddr.requestFocus();
                        EthernetConfigDialog ethernetConfigDialog = this.this$0;
                        ethernetConfigDialog.mConTypeManual.setNextFocusDownId(ethernetConfigDialog.mIpaddr.getId());
                        this.this$0.getButton(-1).setNextFocusUpId(this.this$0.mGw.getId());
                        this.this$0.getButton(-2).setNextFocusUpId(this.this$0.mGw.getId());
                        ((InputMethodManager) this.this$0.getContext().getApplicationContext().getSystemService("input_method")).showSoftInput(this.this$0.mIpaddr, 1);
                        this.this$0.mDns.setEnabled(true);
                        this.this$0.mGw.setEnabled(true);
                        this.this$0.mMask.setEnabled(true);
                        this.this$0.enableSaveIfAppropriate();
                        break;
                    default:
                        this.this$0.staticInputView.setVisibility(8);
                        EthernetConfigDialog ethernetConfigDialog2 = this.this$0;
                        ethernetConfigDialog2.mConTypeManual.setNextFocusDownId(ethernetConfigDialog2.getButton(-2).getId());
                        this.this$0.getButton(-1).setNextFocusUpId(this.this$0.mConTypeManual.getId());
                        this.this$0.getButton(-2).setNextFocusUpId(this.this$0.mConTypeManual.getId());
                        ((InputMethodManager) this.this$0.getContext().getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(this.this$0.mIpaddr.getWindowToken(), 2);
                        this.this$0.enableSaveIfAppropriate();
                        break;
                }
            }
        });
        setInverseBackgroundForced(true);
        setButton(-1, fragmentActivity.getText(R.string.menu_save), this);
        setButton(-2, fragmentActivity.getText(R.string.menu_cancel), this);
    }

    public static int validateIpConfigField(EditText editText) {
        Inet4Address inet4Address;
        Log.d("EtherenetSettings", "validateIpConfigField");
        String editable = editText.getText().toString();
        if (TextUtils.isEmpty(editable)) {
            return 1;
        }
        try {
            inet4Address = (Inet4Address) InetAddresses.parseNumericAddress(editable);
        } catch (ClassCastException | IllegalArgumentException unused) {
            inet4Address = null;
        }
        return (inet4Address == null || Inet4Address.ANY.equals(inet4Address)) ? 2 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enableSaveIfAppropriate() {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.connection.ethernet.EthernetConfigDialog.enableSaveIfAppropriate():void");
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        Log.d("EtherenetSettings", "onItemSelected " + i + adapterView.toString());
        if (adapterView == this.mProxySettingsSpinner) {
            showProxyFields();
            enableSaveIfAppropriate();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        this.mEthEnabler.getClass();
        EthernetEnabler.setCheckBox(1);
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog
    public final void onStart() {
        int i;
        Log.d("EtherenetSettings", "onStart to initialize/re-set");
        ExtendedEthernetManager extendedEthernetManager = (ExtendedEthernetManager) this.mContext.getSystemService(ExtendedEthernetManager.class);
        this.mExtendedEthernetManager = extendedEthernetManager;
        IpConfiguration configuration = extendedEthernetManager.getConfiguration("eth0");
        Log.d("EtherenetSettings", "conf: " + configuration);
        EthernetManager ethernetManager = this.mEthManager;
        if (ethernetManager != null) {
            List interfaceList = ethernetManager.getInterfaceList();
            if (interfaceList.size() == 0) {
                return;
            }
            int size = interfaceList.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < interfaceList.size(); i2++) {
                strArr[i2] = (String) interfaceList.get(i2);
            }
            Log.i("EtherenetSettings", "found device: " + strArr[0]);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strArr);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.mDevList.setAdapter((SpinnerAdapter) arrayAdapter);
            String str = strArr[0];
            i = 0;
            while (i < size) {
                if (strArr[i].equals(str)) {
                    this.mDevList.setSelection(i);
                    break;
                } else {
                    this.mDevList.setSelection(0);
                    i++;
                }
            }
        }
        i = 0;
        if (configuration != null) {
            Log.i("EtherenetSettings", "conf.getIpAssignment() " + configuration.getIpAssignment());
            this.mProxyProperties = configuration.getHttpProxy();
            if (configuration.getIpAssignment() == IpConfiguration.IpAssignment.STATIC) {
                this.mIpaddr.setText(configuration.getStaticIpConfiguration().getIpAddress().getAddress().getHostAddress());
                this.mGw.setText(configuration.getStaticIpConfiguration().getGateway().getHostAddress());
                this.mDns.setText(configuration.getStaticIpConfiguration().getDnsServers().get(i).getHostAddress());
                Log.i("EtherenetSettings", "net mask: " + configuration.getStaticIpConfiguration().getIpAddress().getPrefixLength());
                EditText editText = this.mMask;
                int prefixLength = configuration.getStaticIpConfiguration().getIpAddress().getPrefixLength();
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(prefixLength, "invertGetNetMask:", "EtherenetSettings");
                String str2 = ApnSettings.MVNO_NONE;
                if (prefixLength != 0) {
                    int i3 = (-1) << (32 - prefixLength);
                    try {
                        InetAddress byAddress = InetAddress.getByAddress(new byte[]{(byte) (i3 >>> 24), (byte) ((i3 >> 16) & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255)});
                        Log.d("EtherenetSettings", "invertGetNetMask after:" + byAddress.getHostAddress());
                        str2 = byAddress.getHostAddress();
                    } catch (UnknownHostException unused) {
                        Log.d("EtherenetSettings", "invertGetNetMask exception:");
                    }
                }
                editText.setText(str2);
            }
            if (configuration.getProxySettings().ordinal() == IpConfiguration.ProxySettings.STATIC.ordinal()) {
                this.mProxySettingsSpinner.setSelection(1);
            } else if (configuration.getProxySettings().ordinal() == IpConfiguration.ProxySettings.PAC.ordinal()) {
                this.mProxySettingsSpinner.setSelection(2);
            } else {
                this.mProxySettingsSpinner.setSelection(0);
            }
            showProxyFields();
            if (configuration.getIpAssignment().equals(IpConfiguration.IpAssignment.STATIC)) {
                this.mConTypeDhcp.setChecked(false);
                this.mConTypeManual.setChecked(true);
                this.mIpaddr.setEnabled(true);
                this.mDns.setEnabled(true);
                this.mGw.setEnabled(true);
                this.mMask.setEnabled(true);
                enableSaveIfAppropriate();
            } else {
                this.mConTypeDhcp.setChecked(true);
                this.mConTypeManual.setChecked(false);
                this.staticInputView.setVisibility(8);
                this.mIpaddr.setEnabled(false);
                this.mDns.setEnabled(false);
                this.mGw.setEnabled(false);
                this.mMask.setEnabled(false);
                enableSaveIfAppropriate();
            }
        }
        enableSaveIfAppropriate();
        this.mOuterLayout.requestFocus();
        super.onStart();
    }

    public final void showProxyFields() {
        if (this.mProxySettingsSpinner.getSelectedItemPosition() != 1) {
            if (this.mProxySettingsSpinner.getSelectedItemPosition() != 2) {
                Log.d("EtherenetSettings", "None");
                findViewById(R.id.proxy_warning_limited_support).setVisibility(8);
                findViewById(R.id.proxy_fields).setVisibility(8);
                findViewById(R.id.proxy_pac_field).setVisibility(8);
                return;
            }
            Log.d("EtherenetSettings", "PROXY_PAC");
            findViewById(R.id.proxy_warning_limited_support).setVisibility(8);
            findViewById(R.id.proxy_fields).setVisibility(8);
            findViewById(R.id.proxy_pac_field).setVisibility(0);
            if (this.mProxyPacView == null) {
                TextView textView = (TextView) this.mView.findViewById(R.id.proxy_pac);
                this.mProxyPacView = textView;
                textView.addTextChangedListener(this.ipTextWatcher);
            }
            ProxyInfo proxyInfo = this.mProxyProperties;
            if (proxyInfo != null) {
                this.mProxyPacView.setText(proxyInfo.getPacFileUrl().toString());
                return;
            }
            return;
        }
        Log.d("EtherenetSettings", "PROXY_STATIC");
        findViewById(R.id.proxy_warning_limited_support).setVisibility(0);
        findViewById(R.id.proxy_fields).setVisibility(0);
        findViewById(R.id.proxy_pac_field).setVisibility(8);
        if (this.mProxyHostView == null) {
            TextView textView2 = (TextView) this.mView.findViewById(R.id.proxy_hostname);
            this.mProxyHostView = textView2;
            textView2.addTextChangedListener(this.ipTextWatcher);
            TextView textView3 = (TextView) this.mView.findViewById(R.id.proxy_port);
            this.mProxyPortView = textView3;
            textView3.addTextChangedListener(this.ipTextWatcher);
            TextView textView4 = (TextView) this.mView.findViewById(R.id.proxy_exclusionlist);
            this.mProxyExclusionListView = textView4;
            textView4.addTextChangedListener(this.ipTextWatcher);
        }
        ProxyInfo proxyInfo2 = this.mProxyProperties;
        if (proxyInfo2 != null) {
            this.mProxyHostView.setText(proxyInfo2.getHost());
            this.mProxyPortView.setText(Integer.toString(this.mProxyProperties.getPort()));
            TextView textView5 = this.mProxyExclusionListView;
            String[] exclusionList = this.mProxyProperties.getExclusionList();
            textView5.setText(exclusionList == null ? ApnSettings.MVNO_NONE : TextUtils.join(",", exclusionList));
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:29|30|31|(8:35|(4:37|(4:40|(2:42|43)(1:(2:48|49)(2:46|47))|44|38)|50|51)|52|53|54|55|56|57)(0)|60|54|55|56|57) */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x014a, code lost:
    
        r7 = null;
     */
    @Override // android.content.DialogInterface.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onClick(android.content.DialogInterface r19, int r20) {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.connection.ethernet.EthernetConfigDialog.onClick(android.content.DialogInterface, int):void");
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }
}
