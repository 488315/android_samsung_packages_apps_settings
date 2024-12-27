package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigurePassWordPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final String mErrPassWord;
    public int mImeActionType;
    public boolean mIsRequestFocusPendingFlag;
    public String mPassWord;
    public EditText mPasswordEditText;
    public TextView mPasswordErrorTextView;
    public TextView mPasswordTitleTextView;
    public String mTempPassWord;
    public boolean mViewAttached;
    public WifiApEditSettings mWifiApConfigureSettings;
    public final AnonymousClass2 passwordWatcher;

    /* renamed from: -$$Nest$msetErrorText, reason: not valid java name */
    public static void m1392$$Nest$msetErrorText(
            WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference, String str) {
        if (str != null) {
            wifiApConfigurePassWordPreference.getClass();
            if (!str.isEmpty()) {
                wifiApConfigurePassWordPreference.mPasswordTitleTextView.setTextColor(
                        wifiApConfigurePassWordPreference.mContext.getColor(
                                R.color.wifi_ap_warning_color));
                wifiApConfigurePassWordPreference.mPasswordErrorTextView.setText(str);
                SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                        wifiApConfigurePassWordPreference.mContext,
                        R.color.sec_wifi_dialog_error_color,
                        wifiApConfigurePassWordPreference.mPasswordEditText);
                wifiApConfigurePassWordPreference.mPasswordErrorTextView.setVisibility(0);
                return;
            }
        }
        wifiApConfigurePassWordPreference.mPasswordTitleTextView.setTextColor(
                wifiApConfigurePassWordPreference.mContext.getColor(
                        R.color.wifi_ap_primary_text_color));
        wifiApConfigurePassWordPreference.mPasswordErrorTextView.setVisibility(8);
        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                wifiApConfigurePassWordPreference.mContext,
                R.color.sec_wifi_ap_edit_text_background_color,
                wifiApConfigurePassWordPreference.mPasswordEditText);
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference$2] */
    public WifiApConfigurePassWordPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mImeActionType = -2113929210;
        this.mTempPassWord = null;
        this.mPassWord = null;
        this.mErrPassWord = "\tUSER#DEFINED#PWD#\n";
        this.mIsRequestFocusPendingFlag = false;
        this.passwordWatcher =
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference.2
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        SALogging.insertSALog("TETH_011", "8012");
                        WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference =
                                WifiApConfigurePassWordPreference.this;
                        wifiApConfigurePassWordPreference.mPassWord =
                                wifiApConfigurePassWordPreference
                                        .mPasswordEditText
                                        .getText()
                                        .toString();
                        WifiApConfigurePassWordPreference.this.mWifiApConfigureSettings
                                .updateSaveButtonEnabling();
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        if (charSequence == null
                                || charSequence.toString().getBytes().length > 63) {
                            return;
                        }
                        WifiApConfigurePassWordPreference.this.mTempPassWord =
                                charSequence.toString();
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        int length = charSequence.toString().getBytes().length;
                        if (length < 8) {
                            WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference =
                                    WifiApConfigurePassWordPreference.this;
                            WifiApConfigurePassWordPreference.m1392$$Nest$msetErrorText(
                                    wifiApConfigurePassWordPreference,
                                    wifiApConfigurePassWordPreference.mContext.getString(
                                            R.string.wifi_ap_credentials_password_too_short));
                        }
                        if (length <= 63) {
                            if (length < 8 || length >= 63) {
                                return;
                            }
                            WifiApConfigurePassWordPreference.m1392$$Nest$msetErrorText(
                                    WifiApConfigurePassWordPreference.this, null);
                            return;
                        }
                        String str = WifiApConfigurePassWordPreference.this.mTempPassWord;
                        if (str == null || str.getBytes().length > 63) {
                            WifiApConfigurePassWordPreference.this.mPasswordEditText.setText(
                                    ApnSettings.MVNO_NONE);
                        } else if (charSequence.toString().length()
                                        - WifiApConfigurePassWordPreference.this.mTempPassWord
                                                .length()
                                > 1) {
                            String charSequence2 = charSequence.toString();
                            if (charSequence2.getBytes().length > charSequence2.length()) {
                                int i6 = 0;
                                int i7 = 0;
                                int i8 = 0;
                                while (i6 <= 63) {
                                    i8 = Character.charCount(charSequence2.codePointAt(i7));
                                    int i9 = i7 + i8;
                                    i6 += charSequence2.substring(i7, i9).getBytes().length;
                                    i7 = i9;
                                }
                                WifiApConfigurePassWordPreference.this.mPasswordEditText.setText(
                                        charSequence2.substring(0, i7 - i8));
                            } else {
                                WifiApConfigurePassWordPreference.this.mPasswordEditText.setText(
                                        charSequence2.substring(0, 63));
                            }
                        } else {
                            WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference2 =
                                    WifiApConfigurePassWordPreference.this;
                            wifiApConfigurePassWordPreference2.mPasswordEditText.setText(
                                    wifiApConfigurePassWordPreference2.mTempPassWord);
                        }
                        WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference3 =
                                WifiApConfigurePassWordPreference.this;
                        WifiApConfigurePassWordPreference.m1392$$Nest$msetErrorText(
                                wifiApConfigurePassWordPreference3,
                                wifiApConfigurePassWordPreference3.mContext.getString(
                                        R.string.wifi_ssid_max_byte_error));
                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                WifiApConfigurePassWordPreference.this.mPasswordEditText);
                    }
                };
        this.mContext = context;
        this.mPassWord = WifiApSoftApUtils.getSoftApConfiguration(context).getPassphrase();
        setLayoutResource(R.layout.sec_wifi_ap_edit_password_settings);
        updateState(WifiApSoftApUtils.getSoftApConfiguration(context).getSecurityType());
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mViewAttached = true;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mPasswordTitleTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.password_title_text_view);
        this.mPasswordEditText =
                (EditText) preferenceViewHolder.findViewById(R.id.password_edit_text);
        this.mPasswordErrorTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.password_error_text_view);
        this.mPasswordEditText.setSelectAllOnFocus(true);
        this.mPasswordEditText.setInputType(145);
        this.mPasswordEditText.setImeOptions(this.mImeActionType);
        EditText editText = this.mPasswordEditText;
        editText.setFilters(new InputFilter[] {new Utils.EmojiInputFilter(editText.getContext())});
        this.mPasswordEditText.addTextChangedListener(this.passwordWatcher);
        if (isVisible()
                && (TextUtils.isEmpty(this.mPassWord)
                        || this.mPassWord.equals("\tUSER#DEFINED#PWD#\n"))) {
            Log.d(
                    "WifiApConfigurePassWordPreference",
                    "onBindViewHolder : passphrase is empty. requesting focus");
            this.mPasswordEditText.requestFocus();
        } else if (this.mViewAttached && this.mIsRequestFocusPendingFlag) {
            this.mPasswordEditText.requestFocus();
            this.mIsRequestFocusPendingFlag = false;
            this.mPasswordEditText.setText(this.mPassWord);
            this.mPasswordEditText.setSelection(this.mPassWord.length());
        } else {
            this.mPasswordEditText.setText(this.mPassWord);
            this.mPasswordEditText.setSelection(this.mPassWord.length());
        }
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mPasswordEditText.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference.1
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        int i = WifiApConfigurePassWordPreference.$r8$clinit;
                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                "hasFocus:", "WifiApConfigurePassWordPreference", z);
                        if (z) {
                            return;
                        }
                        new Handler()
                                .postDelayed(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference.1.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                WifiApSettingsUtils.hideKeyboard(
                                                        WifiApConfigurePassWordPreference.this
                                                                .mContext);
                                            }
                                        },
                                        600L);
                    }
                });
        if (Utils.isTalkBackEnabled(this.mContext)) {
            this.mPasswordEditText.setHint(
                    this.mContext.getString(R.string.wifi_ap_enter_password));
        } else {
            this.mPasswordEditText.setHint((CharSequence) null);
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        this.mViewAttached = false;
        super.onDetached();
    }

    public final void updateState(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "Updating state: securityType: ", "WifiApConfigurePassWordPreference");
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1)
                        != 1;
        if (WifiApSoftApUtils.checkIfSecurityTypeIsAnyOpenVariant(i)) {
            setVisible(false);
        } else {
            setVisible(true);
        }
        if (z) {
            Log.i("WifiApConfigurePassWordPreference", "setting other action key : -2147483642");
            this.mImeActionType = -2147483642;
            notifyChanged();
        } else {
            Log.i("WifiApConfigurePassWordPreference", "setting other action key : -2113929210");
            this.mImeActionType = -2113929210;
            notifyChanged();
        }
    }

    public WifiApConfigurePassWordPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApConfigurePassWordPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApConfigurePassWordPreference(Context context) {
        this(context, null);
    }
}
