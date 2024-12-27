package com.samsung.android.settings.bluetooth.lebroadcast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothLeBroadcastSourceInfoController extends BasePreferenceController
        implements Preference.OnPreferenceClickListener {
    private static final boolean DEBUG = Debug.semIsProductDev();
    private static final int MAX_CODE_LENGTH = 16;
    private static final int MAX_NAME_LENGTH = 32;
    private static final int MIN_NAME_CODE_LENGTH = 4;
    private static final String TAG = "SecBluetoothLeBroadcastSourceInfoController";
    private static int mEncryptedBroadcast;
    private final int ENCRYPTED_BROADCAST;
    private final String KEY_PREF_BR_CODE;
    private final String KEY_PREF_BR_NAME;
    private final int UNENCRYPTED_BROADCAST;
    private AlertDialog mAlertDialog;
    private String mBroadcastCode;
    private Button mBroadcastCodeButton;
    private EditText mBroadcastCodeEditText;
    private TextView mBroadcastCodeErrorTextView;
    private TextWatcher mBroadcastCodeWatcher;
    private String mBroadcastName;
    private Button mBroadcastNameButton;
    private EditText mBroadcastNameEditText;
    private TextView mBroadcastNameErrorTextView;
    private TextWatcher mBroadcastNameWatcher;
    private final Context mContext;
    private TextView mDialogLabelTextView;
    LayoutPreference mLayoutPreference;
    private String mPrefKey;
    private String mScreenId;
    private TextView mSubTextView;
    private String mTempBroadcastCode;
    private String mTempBroadcastName;
    private TextView mTitleTextView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnCancelListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass2(int i) {
            this.$r8$classId = i;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    Log.d(SecBluetoothLeBroadcastSourceInfoController.TAG, "canceled");
                    break;
                default:
                    Log.d(SecBluetoothLeBroadcastSourceInfoController.TAG, "canceled");
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$3, reason: invalid class name */
    public final class AnonymousClass3 implements TextView.OnEditorActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecBluetoothLeBroadcastSourceInfoController this$0;

        public /* synthetic */ AnonymousClass3(
                SecBluetoothLeBroadcastSourceInfoController
                        secBluetoothLeBroadcastSourceInfoController,
                int i) {
            this.$r8$classId = i;
            this.this$0 = secBluetoothLeBroadcastSourceInfoController;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (this.$r8$classId) {
                case 0:
                    if (i == 6) {
                        if (this.this$0.mBroadcastCodeButton != null
                                && this.this$0.mBroadcastCodeButton.isEnabled()) {
                            this.this$0.saveInputText(false);
                            this.this$0.setEncryptedBroadcast(true);
                            this.this$0.dismissAlertDialog();
                            break;
                        } else {
                            Log.e(
                                    SecBluetoothLeBroadcastSourceInfoController.TAG,
                                    "Can't save with done");
                            break;
                        }
                    }
                    break;
                default:
                    if (i == 6) {
                        if (this.this$0.mBroadcastNameButton != null
                                && this.this$0.mBroadcastNameButton.isEnabled()) {
                            this.this$0.saveInputText(true);
                            this.this$0.dismissAlertDialog();
                            break;
                        } else {
                            Log.e(
                                    SecBluetoothLeBroadcastSourceInfoController.TAG,
                                    "Can't save with done");
                            break;
                        }
                    }
                    break;
            }
            return false;
        }
    }

    public SecBluetoothLeBroadcastSourceInfoController(Context context, String str) {
        super(context, str);
        this.KEY_PREF_BR_NAME = "key_broadcast_name";
        this.KEY_PREF_BR_CODE = "key_broadcast_code";
        this.mAlertDialog = null;
        this.mBroadcastName = null;
        this.mBroadcastCode = null;
        this.mTempBroadcastName = null;
        this.mTempBroadcastCode = null;
        this.UNENCRYPTED_BROADCAST = 0;
        this.ENCRYPTED_BROADCAST = 1;
        final int i = 0;
        this.mBroadcastNameWatcher =
                new TextWatcher(
                        this) { // from class:
                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController.6
                    public final /* synthetic */ SecBluetoothLeBroadcastSourceInfoController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        switch (i) {
                            case 0:
                                SecBluetoothLeBroadcastSourceInfoController
                                        secBluetoothLeBroadcastSourceInfoController = this.this$0;
                                secBluetoothLeBroadcastSourceInfoController.mBroadcastName =
                                        secBluetoothLeBroadcastSourceInfoController
                                                .mBroadcastNameEditText
                                                .getText()
                                                .toString();
                                if (this.this$0.mBroadcastNameButton == null) {
                                    Log.e(
                                            SecBluetoothLeBroadcastSourceInfoController.TAG,
                                            "mBroadcastNameButton is null");
                                    break;
                                } else {
                                    this.this$0.mBroadcastNameButton.setEnabled(
                                            editable.length() >= 4);
                                    break;
                                }
                            default:
                                SecBluetoothLeBroadcastSourceInfoController
                                        secBluetoothLeBroadcastSourceInfoController2 = this.this$0;
                                secBluetoothLeBroadcastSourceInfoController2.mBroadcastCode =
                                        secBluetoothLeBroadcastSourceInfoController2
                                                .mBroadcastCodeEditText
                                                .getText()
                                                .toString();
                                if (this.this$0.mBroadcastCodeButton == null) {
                                    Log.e(
                                            SecBluetoothLeBroadcastSourceInfoController.TAG,
                                            "mBroadcastCodeButton is null");
                                    break;
                                } else {
                                    this.this$0.mBroadcastCodeButton.setEnabled(
                                            editable.toString().getBytes().length >= 4);
                                    break;
                                }
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i2, int i3, int i4) {
                        switch (i) {
                            case 0:
                                if (charSequence.toString().length() <= 32) {
                                    this.this$0.mTempBroadcastName = charSequence.toString();
                                    break;
                                }
                                break;
                            default:
                                if (charSequence.toString().getBytes().length <= 16) {
                                    this.this$0.mTempBroadcastCode = charSequence.toString();
                                    break;
                                }
                                break;
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i2, int i3, int i4) {
                        switch (i) {
                            case 0:
                                int length = charSequence.toString().length();
                                if (length >= 4) {
                                    if (length <= 32) {
                                        this.this$0.setErrorText(null, true);
                                        break;
                                    } else {
                                        if (this.this$0.mTempBroadcastName == null
                                                || this.this$0.mTempBroadcastName.length() > 32) {
                                            this.this$0.mBroadcastNameEditText.setText(
                                                    charSequence.toString().substring(0, 32));
                                        } else {
                                            this.this$0.mBroadcastNameEditText.setText(
                                                    this.this$0.mTempBroadcastName);
                                        }
                                        SecBluetoothLeBroadcastSourceInfoController
                                                secBluetoothLeBroadcastSourceInfoController =
                                                        this.this$0;
                                        secBluetoothLeBroadcastSourceInfoController.setErrorText(
                                                secBluetoothLeBroadcastSourceInfoController.mContext
                                                        .getString(
                                                                R.string
                                                                        .sec_auracast_max_name_error_text,
                                                                32),
                                                true);
                                        break;
                                    }
                                } else {
                                    SecBluetoothLeBroadcastSourceInfoController
                                            secBluetoothLeBroadcastSourceInfoController2 =
                                                    this.this$0;
                                    secBluetoothLeBroadcastSourceInfoController2.setErrorText(
                                            secBluetoothLeBroadcastSourceInfoController2.mContext
                                                    .getString(
                                                            R.string
                                                                    .sec_auracast_min_name_error_text),
                                            true);
                                    break;
                                }
                                break;
                            default:
                                if (charSequence.toString().getBytes().length <= 16) {
                                    this.this$0.setErrorText(null, false);
                                    break;
                                } else {
                                    if (this.this$0.mTempBroadcastCode == null
                                            || this.this$0.mTempBroadcastCode.getBytes().length
                                                    > 16) {
                                        this.this$0.mBroadcastCodeEditText.setText(
                                                ApnSettings.MVNO_NONE);
                                    } else if (charSequence.toString().length()
                                                    - this.this$0.mTempBroadcastCode.length()
                                            > 1) {
                                        String charSequence2 = charSequence.toString();
                                        if (charSequence2.getBytes().length
                                                > charSequence2.length()) {
                                            int i5 = 0;
                                            int i6 = 0;
                                            int i7 = 0;
                                            while (i5 <= 16) {
                                                i7 =
                                                        Character.charCount(
                                                                charSequence2.codePointAt(i6));
                                                int i8 = i6 + i7;
                                                i5 +=
                                                        charSequence2
                                                                .substring(i6, i8)
                                                                .getBytes()
                                                                .length;
                                                i6 = i8;
                                            }
                                            this.this$0.mBroadcastCodeEditText.setText(
                                                    charSequence2.substring(0, i6 - i7));
                                        } else {
                                            this.this$0.mBroadcastCodeEditText.setText(
                                                    charSequence2.substring(0, 16));
                                        }
                                    } else {
                                        this.this$0.mBroadcastCodeEditText.setText(
                                                this.this$0.mTempBroadcastCode);
                                    }
                                    SecBluetoothLeBroadcastSourceInfoController
                                            secBluetoothLeBroadcastSourceInfoController3 =
                                                    this.this$0;
                                    secBluetoothLeBroadcastSourceInfoController3.setErrorText(
                                            secBluetoothLeBroadcastSourceInfoController3.mContext
                                                    .getString(
                                                            R.string
                                                                    .sec_auracast_max_code_error_text,
                                                            16),
                                            false);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mBroadcastCodeWatcher =
                new TextWatcher(
                        this) { // from class:
                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController.6
                    public final /* synthetic */ SecBluetoothLeBroadcastSourceInfoController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        switch (i2) {
                            case 0:
                                SecBluetoothLeBroadcastSourceInfoController
                                        secBluetoothLeBroadcastSourceInfoController = this.this$0;
                                secBluetoothLeBroadcastSourceInfoController.mBroadcastName =
                                        secBluetoothLeBroadcastSourceInfoController
                                                .mBroadcastNameEditText
                                                .getText()
                                                .toString();
                                if (this.this$0.mBroadcastNameButton == null) {
                                    Log.e(
                                            SecBluetoothLeBroadcastSourceInfoController.TAG,
                                            "mBroadcastNameButton is null");
                                    break;
                                } else {
                                    this.this$0.mBroadcastNameButton.setEnabled(
                                            editable.length() >= 4);
                                    break;
                                }
                            default:
                                SecBluetoothLeBroadcastSourceInfoController
                                        secBluetoothLeBroadcastSourceInfoController2 = this.this$0;
                                secBluetoothLeBroadcastSourceInfoController2.mBroadcastCode =
                                        secBluetoothLeBroadcastSourceInfoController2
                                                .mBroadcastCodeEditText
                                                .getText()
                                                .toString();
                                if (this.this$0.mBroadcastCodeButton == null) {
                                    Log.e(
                                            SecBluetoothLeBroadcastSourceInfoController.TAG,
                                            "mBroadcastCodeButton is null");
                                    break;
                                } else {
                                    this.this$0.mBroadcastCodeButton.setEnabled(
                                            editable.toString().getBytes().length >= 4);
                                    break;
                                }
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i22, int i3, int i4) {
                        switch (i2) {
                            case 0:
                                if (charSequence.toString().length() <= 32) {
                                    this.this$0.mTempBroadcastName = charSequence.toString();
                                    break;
                                }
                                break;
                            default:
                                if (charSequence.toString().getBytes().length <= 16) {
                                    this.this$0.mTempBroadcastCode = charSequence.toString();
                                    break;
                                }
                                break;
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i22, int i3, int i4) {
                        switch (i2) {
                            case 0:
                                int length = charSequence.toString().length();
                                if (length >= 4) {
                                    if (length <= 32) {
                                        this.this$0.setErrorText(null, true);
                                        break;
                                    } else {
                                        if (this.this$0.mTempBroadcastName == null
                                                || this.this$0.mTempBroadcastName.length() > 32) {
                                            this.this$0.mBroadcastNameEditText.setText(
                                                    charSequence.toString().substring(0, 32));
                                        } else {
                                            this.this$0.mBroadcastNameEditText.setText(
                                                    this.this$0.mTempBroadcastName);
                                        }
                                        SecBluetoothLeBroadcastSourceInfoController
                                                secBluetoothLeBroadcastSourceInfoController =
                                                        this.this$0;
                                        secBluetoothLeBroadcastSourceInfoController.setErrorText(
                                                secBluetoothLeBroadcastSourceInfoController.mContext
                                                        .getString(
                                                                R.string
                                                                        .sec_auracast_max_name_error_text,
                                                                32),
                                                true);
                                        break;
                                    }
                                } else {
                                    SecBluetoothLeBroadcastSourceInfoController
                                            secBluetoothLeBroadcastSourceInfoController2 =
                                                    this.this$0;
                                    secBluetoothLeBroadcastSourceInfoController2.setErrorText(
                                            secBluetoothLeBroadcastSourceInfoController2.mContext
                                                    .getString(
                                                            R.string
                                                                    .sec_auracast_min_name_error_text),
                                            true);
                                    break;
                                }
                                break;
                            default:
                                if (charSequence.toString().getBytes().length <= 16) {
                                    this.this$0.setErrorText(null, false);
                                    break;
                                } else {
                                    if (this.this$0.mTempBroadcastCode == null
                                            || this.this$0.mTempBroadcastCode.getBytes().length
                                                    > 16) {
                                        this.this$0.mBroadcastCodeEditText.setText(
                                                ApnSettings.MVNO_NONE);
                                    } else if (charSequence.toString().length()
                                                    - this.this$0.mTempBroadcastCode.length()
                                            > 1) {
                                        String charSequence2 = charSequence.toString();
                                        if (charSequence2.getBytes().length
                                                > charSequence2.length()) {
                                            int i5 = 0;
                                            int i6 = 0;
                                            int i7 = 0;
                                            while (i5 <= 16) {
                                                i7 =
                                                        Character.charCount(
                                                                charSequence2.codePointAt(i6));
                                                int i8 = i6 + i7;
                                                i5 +=
                                                        charSequence2
                                                                .substring(i6, i8)
                                                                .getBytes()
                                                                .length;
                                                i6 = i8;
                                            }
                                            this.this$0.mBroadcastCodeEditText.setText(
                                                    charSequence2.substring(0, i6 - i7));
                                        } else {
                                            this.this$0.mBroadcastCodeEditText.setText(
                                                    charSequence2.substring(0, 16));
                                        }
                                    } else {
                                        this.this$0.mBroadcastCodeEditText.setText(
                                                this.this$0.mTempBroadcastCode);
                                    }
                                    SecBluetoothLeBroadcastSourceInfoController
                                            secBluetoothLeBroadcastSourceInfoController3 =
                                                    this.this$0;
                                    secBluetoothLeBroadcastSourceInfoController3.setErrorText(
                                            secBluetoothLeBroadcastSourceInfoController3.mContext
                                                    .getString(
                                                            R.string
                                                                    .sec_auracast_max_code_error_text,
                                                            16),
                                            false);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mContext = context;
        this.mPrefKey = str;
        this.mScreenId = context.getResources().getString(R.string.screen_broadcast_source);
        getEncryptedBroadcast();
    }

    private void getEncryptedBroadcast() {
        mEncryptedBroadcast =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "bluetooth_le_encrypted_broadcast", 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$launchBroadcastCodeDialog$0(
            String str, DialogInterface dialogInterface, int i) {
        SALogging.insertSALog(
                this.mScreenId,
                str,
                this.mContext.getResources().getString(R.string.detail_popup_cancel));
        Log.d(TAG, "cancel button: clicked");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$launchBroadcastCodeDialog$1(
            String str, DialogInterface dialogInterface, int i) {
        SALogging.insertSALog(
                this.mScreenId,
                str,
                this.mContext
                        .getResources()
                        .getString(R.string.detail_code_popup_not_use_password));
        setSubText(this.mContext.getResources().getString(R.string.sec_auracast_no_password));
        setEncryptedBroadcast(false);
        Log.d(TAG, "Don't use password");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$launchBroadcastCodeDialog$2(
            String str, DialogInterface dialogInterface, int i) {
        SALogging.insertSALog(
                this.mScreenId,
                str,
                this.mContext.getResources().getString(R.string.detail_code_popup_save));
        saveInputText(false);
        setEncryptedBroadcast(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$launchBroadcastNameDialog$3(
            String str, DialogInterface dialogInterface, int i) {
        SALogging.insertSALog(
                this.mScreenId,
                str,
                this.mContext.getResources().getString(R.string.detail_popup_cancel));
        Log.d(TAG, "cancel button: clicked");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$launchBroadcastNameDialog$4(
            String str, DialogInterface dialogInterface, int i) {
        SALogging.insertSALog(
                this.mScreenId,
                str,
                this.mContext.getResources().getString(R.string.detail_name_popup_save));
        saveInputText(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchBroadcastCodeDialog() {
        Log.d(TAG, "launchBroadcastCodeDialog");
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_bluetooth_broadcast_source_code_dialog,
                                (ViewGroup) null);
        this.mDialogLabelTextView = (TextView) inflate.requireViewById(R.id.password_name_text);
        ((TextView) inflate.requireViewById(R.id.dialog_message_text)).setVisibility(0);
        this.mBroadcastCodeEditText = (EditText) inflate.requireViewById(R.id.broadcast_edit_text);
        this.mBroadcastCodeErrorTextView =
                (TextView) inflate.requireViewById(R.id.broadcast_error_text);
        String string =
                this.mContext.getResources().getString(R.string.event_broadcast_code_popup_button);
        this.mDialogLabelTextView.setText(R.string.sec_bluetooth_source_password);
        EditText editText = this.mBroadcastCodeEditText;
        editText.setFilters(new InputFilter[] {new Utils.EmojiInputFilter(editText.getContext())});
        this.mBroadcastCodeEditText.addTextChangedListener(this.mBroadcastCodeWatcher);
        if (isEncryptedBroadcast()) {
            this.mBroadcastCodeEditText.setText(this.mSubTextView.getText().toString());
            this.mBroadcastCodeEditText.selectAll();
        }
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.sec_bluetooth_source_dialog_password_title)
                        .setView(inflate)
                        .setNeutralButton(
                                R.string.sec_bluetooth_source_dialog_cancel,
                                new SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
                                        this, string, 0))
                        .setNegativeButton(
                                R.string.sec_auracast_dialog_no_password,
                                new SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
                                        this, string, 1))
                        .setPositiveButton(
                                R.string.sec_bluetooth_source_dialog_save,
                                new SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
                                        this, string, 2))
                        .setOnCancelListener(new AnonymousClass2(0))
                        .create();
        this.mAlertDialog = create;
        if (create.getWindow() != null) {
            this.mAlertDialog.getWindow().setSoftInputMode(21);
        }
        this.mAlertDialog.show();
        this.mBroadcastCodeButton = this.mAlertDialog.getButton(-1);
        if (!isEncryptedBroadcast()) {
            this.mBroadcastCodeButton.setEnabled(false);
        }
        this.mBroadcastCodeEditText.setOnEditorActionListener(new AnonymousClass3(this, 0));
        this.mBroadcastCodeEditText.requestFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(this.mBroadcastCodeEditText, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchBroadcastNameDialog() {
        Log.d(TAG, "launchBroadcastNameDialog");
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_bluetooth_broadcast_source_code_dialog,
                                (ViewGroup) null);
        this.mDialogLabelTextView = (TextView) inflate.requireViewById(R.id.password_name_text);
        this.mBroadcastNameEditText = (EditText) inflate.requireViewById(R.id.broadcast_edit_text);
        this.mBroadcastNameErrorTextView =
                (TextView) inflate.requireViewById(R.id.broadcast_error_text);
        String string =
                this.mContext.getResources().getString(R.string.event_broadcast_name_popup_button);
        this.mDialogLabelTextView.setText(R.string.sec_bluetooth_source_broadcast_name);
        this.mBroadcastNameEditText.addTextChangedListener(this.mBroadcastNameWatcher);
        this.mBroadcastNameEditText.setText(
                this.mSubTextView
                        .getText()
                        .toString()
                        .replaceAll("\\u200E", ApnSettings.MVNO_NONE));
        this.mBroadcastNameEditText.selectAll();
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.sec_bluetooth_source_dialog_name_title)
                        .setView(inflate)
                        .setNeutralButton(
                                R.string.sec_bluetooth_source_dialog_cancel,
                                new SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
                                        this, string, 3))
                        .setPositiveButton(
                                R.string.sec_bluetooth_source_dialog_save,
                                new SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
                                        this, string, 4))
                        .setOnCancelListener(new AnonymousClass2(1))
                        .create();
        this.mAlertDialog = create;
        if (create.getWindow() != null) {
            this.mAlertDialog.getWindow().setSoftInputMode(21);
        }
        this.mAlertDialog.show();
        this.mBroadcastNameButton = this.mAlertDialog.getButton(-1);
        this.mBroadcastNameEditText.setOnEditorActionListener(new AnonymousClass3(this, 1));
        this.mBroadcastNameEditText.requestFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(this.mBroadcastNameEditText, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveInputText(boolean z) {
        if (z) {
            if (this.mBroadcastName.length() < 4) {
                return;
            }
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("save input text: "), this.mBroadcastName, TAG);
            Settings.Secure.putString(
                    this.mContext.getContentResolver(),
                    "bluetooth_le_broadcast_name",
                    this.mBroadcastName);
            setSubText(this.mBroadcastName);
            return;
        }
        if (this.mBroadcastCode.getBytes().length < 4) {
            return;
        }
        Utils$$ExternalSyntheticOutline0.m(
                new StringBuilder("save input text: "), this.mBroadcastCode, TAG);
        Settings.Secure.putString(
                this.mContext.getContentResolver(),
                "bluetooth_le_broadcast_code",
                this.mBroadcastCode);
        setSubText(this.mBroadcastCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEncryptedBroadcast(boolean z) {
        mEncryptedBroadcast = z ? 1 : 0;
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "bluetooth_le_encrypted_broadcast",
                mEncryptedBroadcast);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorText(String str, boolean z) {
        if (z) {
            if (str == null || str.isEmpty()) {
                this.mBroadcastNameErrorTextView.setVisibility(8);
                SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                        this.mContext,
                        R.color.sec_wifi_ap_edit_text_background_color,
                        this.mBroadcastNameEditText);
                this.mDialogLabelTextView.setTextColor(
                        this.mContext
                                .getResources()
                                .getColorStateList(
                                        R.color.sec_bluetooth_source_code_dialog_name_text_color));
                return;
            }
            this.mBroadcastNameErrorTextView.setText(str);
            this.mBroadcastNameErrorTextView.setVisibility(0);
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    this.mContext,
                    R.color.sec_wifi_dialog_error_color,
                    this.mBroadcastNameEditText);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mBroadcastNameEditText);
            this.mDialogLabelTextView.setTextColor(
                    this.mContext
                            .getResources()
                            .getColorStateList(R.color.sec_wifi_dialog_error_color));
            return;
        }
        if (str == null || str.isEmpty()) {
            this.mBroadcastCodeErrorTextView.setVisibility(8);
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    this.mContext,
                    R.color.sec_wifi_ap_edit_text_background_color,
                    this.mBroadcastCodeEditText);
            this.mDialogLabelTextView.setTextColor(
                    this.mContext
                            .getResources()
                            .getColorStateList(
                                    R.color.sec_bluetooth_source_code_dialog_name_text_color));
            return;
        }
        this.mBroadcastCodeErrorTextView.setText(str);
        this.mBroadcastCodeErrorTextView.setVisibility(0);
        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                this.mContext, R.color.sec_wifi_dialog_error_color, this.mBroadcastCodeEditText);
        SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mBroadcastCodeEditText);
        this.mDialogLabelTextView.setTextColor(
                this.mContext
                        .getResources()
                        .getColorStateList(R.color.sec_wifi_dialog_error_color));
    }

    public void disableEditImageButton() {
        ImageView imageView =
                (ImageView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_header_code_edit);
        LinearLayout linearLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header);
        imageView.setVisibility(8);
        linearLayout.setClickable(false);
    }

    public void dismissAlertDialog() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        Log.e(TAG, " dismiss dialog " + this);
        this.mAlertDialog.dismiss();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(this.mPrefKey);
        this.mLayoutPreference = layoutPreference;
        LinearLayout linearLayout =
                (LinearLayout) layoutPreference.mRootView.findViewById(R.id.main_layout);
        if ("key_broadcast_code".equals(this.mPrefKey)) {
            linearLayout.semSetRoundedCorners(12);
            linearLayout.semSetRoundedCornerColor(
                    12,
                    this.mContext
                            .getResources()
                            .getColor(R.color.sec_bluetooth_auracast_background_color));
            enableEditImageButton();
        } else if ("key_broadcast_name".equals(this.mPrefKey)) {
            linearLayout.semSetRoundedCorners(3);
            linearLayout.semSetRoundedCornerColor(
                    3,
                    this.mContext
                            .getResources()
                            .getColor(R.color.sec_bluetooth_auracast_background_color));
        }
    }

    public void enableEditImageButton() {
        ImageView imageView =
                (ImageView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_header_code_edit);
        LinearLayout linearLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header);
        imageView.setVisibility(0);
        linearLayout.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if ("key_broadcast_code"
                                .equals(
                                        SecBluetoothLeBroadcastSourceInfoController.this
                                                .mPrefKey)) {
                            SALogging.insertSALog(
                                    SecBluetoothLeBroadcastSourceInfoController.this.mScreenId,
                                    SecBluetoothLeBroadcastSourceInfoController.this
                                            .mContext
                                            .getResources()
                                            .getString(R.string.event_edit_broadcast_code));
                            SecBluetoothLeBroadcastSourceInfoController.this
                                    .launchBroadcastCodeDialog();
                        } else if ("key_broadcast_name"
                                .equals(
                                        SecBluetoothLeBroadcastSourceInfoController.this
                                                .mPrefKey)) {
                            SALogging.insertSALog(
                                    SecBluetoothLeBroadcastSourceInfoController.this.mScreenId,
                                    SecBluetoothLeBroadcastSourceInfoController.this
                                            .mContext
                                            .getResources()
                                            .getString(R.string.event_edit_broadcast_name));
                            SecBluetoothLeBroadcastSourceInfoController.this
                                    .launchBroadcastNameDialog();
                        }
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init() {
        this.mTitleTextView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_header_title);
        this.mSubTextView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_header_summary);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isEncryptedBroadcast() {
        getEncryptedBroadcast();
        return mEncryptedBroadcast == 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Log.d(TAG, "onPreferenceClick :: key = " + preference.getKey());
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSubText(String str) {
        TextView textView = this.mSubTextView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setTitle(String str) {
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
