package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureSSIDPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final String mErrSSID;
    public int mImeActionType;
    public boolean mIsRequestFocusPendingFlag;
    public String mSSID;
    public int mSSIDLen;
    public EditText mSsidEditText;
    public TextView mSsidErrorTextView;
    public TextView mSsidTitleTextView;
    public String mTempSsid;
    public boolean mViewAttached;
    public WifiApEditSettings mWifiApConfigureSettings;
    public final AnonymousClass1 ssidFocusChangeListener;
    public final AnonymousClass2 ssidWatcher;

    /* JADX WARN: Type inference failed for: r2v5, types: [com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference$2] */
    public WifiApConfigureSSIDPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mImeActionType = -2113929210;
        this.mTempSsid = null;
        this.mSSID = null;
        this.mErrSSID = "\tUSER#DEFINED#PWD#\n";
        this.mSSIDLen = 0;
        this.mViewAttached = false;
        this.ssidFocusChangeListener =
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference.1
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        int i3 = WifiApConfigureSSIDPreference.$r8$clinit;
                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                "hasFocus:", "WifiApConfigureSSIDPreference", z);
                        if (!z) {
                            new Handler()
                                    .postDelayed(
                                            new Runnable() { // from class:
                                                             // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference.1.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    WifiApSettingsUtils.hideKeyboard(
                                                            WifiApConfigureSSIDPreference.this
                                                                    .mContext);
                                                }
                                            },
                                            600L);
                            return;
                        }
                        String editable =
                                WifiApConfigureSSIDPreference.this
                                        .mSsidEditText
                                        .getText()
                                        .toString();
                        if (editable != null) {
                            WifiApConfigureSSIDPreference.this.mSsidEditText.setSelection(
                                    editable.length());
                        }
                    }
                };
        this.ssidWatcher =
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference.2
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference =
                                WifiApConfigureSSIDPreference.this;
                        wifiApConfigureSSIDPreference.mSSID =
                                wifiApConfigureSSIDPreference.mSsidEditText.getText().toString();
                        int length = WifiApConfigureSSIDPreference.this.mSSID.getBytes().length;
                        WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference2 =
                                WifiApConfigureSSIDPreference.this;
                        int i3 = wifiApConfigureSSIDPreference2.mSSIDLen;
                        if ((i3 < 1 && length >= 1)
                                || ((i3 >= 1 && length < 1)
                                        || wifiApConfigureSSIDPreference2.mSSID.equals(
                                                wifiApConfigureSSIDPreference2.mErrSSID))) {
                            int i4 = WifiApConfigureSSIDPreference.$r8$clinit;
                            Utils$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("mSSID:"),
                                    WifiApConfigureSSIDPreference.this.mSSID,
                                    "WifiApConfigureSSIDPreference");
                            WifiApConfigureSSIDPreference.this.mWifiApConfigureSettings
                                    .updateSaveButtonEnabling();
                        }
                        SALogging.insertSALog("TETH_011", "8010");
                        WifiApConfigureSSIDPreference.this.mSSIDLen = length;
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        if (charSequence == null
                                || charSequence.toString().getBytes().length > 32) {
                            return;
                        }
                        WifiApConfigureSSIDPreference.this.mTempSsid = charSequence.toString();
                        WifiApConfigureSSIDPreference.this.mSSIDLen =
                                charSequence.toString().getBytes().length;
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        if (charSequence.toString().getBytes().length <= 32) {
                            if (charSequence.toString().getBytes().length < 32) {
                                WifiApConfigureSSIDPreference.this.mSsidErrorTextView.setVisibility(
                                        8);
                                WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference =
                                        WifiApConfigureSSIDPreference.this;
                                wifiApConfigureSSIDPreference.mSsidTitleTextView.setTextColor(
                                        wifiApConfigureSSIDPreference.mContext.getColor(
                                                R.color.wifi_ap_primary_text_color));
                                WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference2 =
                                        WifiApConfigureSSIDPreference.this;
                                SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0
                                        .m(
                                                wifiApConfigureSSIDPreference2.mContext,
                                                R.color.sec_wifi_ap_edit_text_background_color,
                                                wifiApConfigureSSIDPreference2.mSsidEditText);
                                return;
                            }
                            return;
                        }
                        String str = WifiApConfigureSSIDPreference.this.mTempSsid;
                        if (str == null || str.getBytes().length > 32) {
                            WifiApConfigureSSIDPreference.this.mSsidEditText.setText(
                                    ApnSettings.MVNO_NONE);
                        } else if (charSequence.toString().length()
                                        - WifiApConfigureSSIDPreference.this.mTempSsid.length()
                                > 1) {
                            String charSequence2 = charSequence.toString();
                            if (charSequence2.getBytes().length > charSequence2.length()) {
                                int i6 = 0;
                                int i7 = 0;
                                int i8 = 0;
                                while (i6 <= 32) {
                                    i8 = Character.charCount(charSequence2.codePointAt(i7));
                                    int i9 = i7 + i8;
                                    i6 += charSequence2.substring(i7, i9).getBytes().length;
                                    i7 = i9;
                                }
                                int i10 = i7 - i8;
                                int i11 = i10 - 1;
                                char charAt = charSequence2.charAt(i11);
                                if (charAt == 9770) {
                                    WifiApConfigureSSIDPreference.this.mSsidEditText.setText(
                                            charSequence2.substring(0, i11));
                                } else if (charAt != 10013) {
                                    WifiApConfigureSSIDPreference.this.mSsidEditText.setText(
                                            charSequence2.substring(0, i10));
                                } else {
                                    WifiApConfigureSSIDPreference.this.mSsidEditText.setText(
                                            charSequence2.substring(0, i11));
                                }
                            } else {
                                WifiApConfigureSSIDPreference.this.mSsidEditText.setText(
                                        charSequence2.substring(0, 32));
                            }
                        } else {
                            WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference3 =
                                    WifiApConfigureSSIDPreference.this;
                            wifiApConfigureSSIDPreference3.mSsidEditText.setText(
                                    wifiApConfigureSSIDPreference3.mTempSsid);
                        }
                        WifiApConfigureSSIDPreference.this.mSsidErrorTextView.setText(
                                R.string.wifi_ssid_max_byte_error);
                        WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference4 =
                                WifiApConfigureSSIDPreference.this;
                        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                                wifiApConfigureSSIDPreference4.mContext,
                                R.color.sec_wifi_dialog_error_color,
                                wifiApConfigureSSIDPreference4.mSsidEditText);
                        WifiApConfigureSSIDPreference.this.mSsidErrorTextView.setVisibility(0);
                        WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference5 =
                                WifiApConfigureSSIDPreference.this;
                        wifiApConfigureSSIDPreference5.mSsidTitleTextView.setTextColor(
                                wifiApConfigureSSIDPreference5.mContext.getColor(
                                        R.color.wifi_ap_warning_color));
                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                WifiApConfigureSSIDPreference.this.mSsidEditText);
                    }
                };
        this.mContext = context;
        this.mSSID = WifiApSoftApUtils.getSoftApConfiguration(context).getSsid();
        setLayoutResource(R.layout.sec_wifi_ap_edit_ssid_settings);
        updateState(WifiApSoftApUtils.getSoftApConfiguration(context).getSecurityType());
    }

    public final boolean isValid() {
        if (TextUtils.isEmpty(this.mSSID)) {
            Log.i("WifiApConfigureSSIDPreference", "Network name is empty.");
            return false;
        }
        if (this.mSSID.getBytes().length < 1 || this.mSSID.getBytes().length > 32) {
            Log.i("WifiApConfigureSSIDPreference", "Network name length error.");
            return false;
        }
        if (!this.mSSID.equals(this.mErrSSID)) {
            return true;
        }
        Log.e("WifiApConfigureSSIDPreference", "Network name error.");
        return false;
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mViewAttached = true;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Log.i("WifiApConfigureSSIDPreference", "onBindViewHolder()");
        this.mSsidTitleTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.ssid_title_text_view);
        this.mSsidEditText = (EditText) preferenceViewHolder.findViewById(R.id.ssid_edit_text);
        this.mSsidErrorTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.ssid_error_text_view);
        this.mSsidEditText.setInputType(540672);
        this.mSsidEditText.setImeOptions(this.mImeActionType);
        this.mSsidEditText.addTextChangedListener(this.ssidWatcher);
        this.mSsidEditText.setText(this.mSSID);
        this.mSsidEditText.setOnFocusChangeListener(this.ssidFocusChangeListener);
        preferenceViewHolder.mDividerAllowedBelow = false;
        if (this.mViewAttached && this.mIsRequestFocusPendingFlag) {
            this.mSsidEditText.requestFocus();
            this.mIsRequestFocusPendingFlag = false;
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        this.mViewAttached = false;
        super.onDetached();
    }

    public final void setIme(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "setting other action key : ", "WifiApConfigureSSIDPreference");
        this.mImeActionType = i;
        EditText editText = this.mSsidEditText;
        if (editText == null) {
            notifyChanged();
        } else {
            editText.setImeOptions(i);
            ((InputMethodManager) getContext().getSystemService("input_method"))
                    .restartInput(this.mSsidEditText);
        }
    }

    public final void updateState(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "Updating state: securityType: ", "WifiApConfigureSSIDPreference");
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1)
                        != 1;
        boolean checkIfSecurityTypeIsAnyOpenVariant =
                WifiApSoftApUtils.checkIfSecurityTypeIsAnyOpenVariant(i);
        if (z) {
            if (checkIfSecurityTypeIsAnyOpenVariant) {
                setIme(-2147483642);
                return;
            } else {
                setIme(-2147483643);
                return;
            }
        }
        if (checkIfSecurityTypeIsAnyOpenVariant) {
            setIme(-2113929210);
        } else {
            setIme(-2113929211);
        }
    }

    public WifiApConfigureSSIDPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApConfigureSSIDPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApConfigureSSIDPreference(Context context) {
        this(context, null);
    }
}
