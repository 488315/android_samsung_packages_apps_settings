package com.samsung.android.settings.encryption;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.security.DirEncryptionWrapper;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CryptSDCardSettings extends SettingsPreferenceFragment
        implements View.OnClickListener, DialogInterface.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
    public final BigDecimal GB;
    public final BigDecimal MB;
    public String fromWhere;
    public View mContentView;
    public LinearLayout mDescriptionLayout;
    public LinearLayout mDescriptionLayoutExceptButton;
    public DirEncryptListner mDirEncryptListner;
    public boolean mDoEncrypt;
    public Button mDoneButton;
    public final AnonymousClass1 mHandler;
    public ImageView mImgIconLock;
    public boolean mIsDisabledByAdmin;
    public Button mLeftButton;
    public Activity mParent;
    public ProgressBar mProgressBar;
    public RelativeLayout mProgressLayout;
    public TextView mProgressStoarge;
    public int mProgressValue;
    public Button mRightButton;
    public SemSdCardEncryption mSse;
    public int mStorageValue;
    public final Object mSync;
    public TextView mTextDesc;
    public TextView mTextEncMessages;
    public boolean serviceBusy;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.encryption.CryptSDCardSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            if (context == null || Rune.isLDUModel()) {
                return null;
            }
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            Resources resources = context.getResources();
            if (new SemSdCardEncryption(context).isEncryptionSupported()) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key =
                        SDCardEncryptStatusPreferenceController.PREF_KEY_SDCARD_ENCRYPT_DETAIL_PAGE;
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.other_security_settings);
                ((SearchIndexableData) searchIndexableRaw).className =
                        SecurityAdvancedSettings.class.getName();
                BaseSearchIndexProvider baseSearchIndexProvider =
                        CryptSDCardSettings.SEARCH_INDEX_DATA_PROVIDER;
                searchIndexableRaw.title =
                        resources.getString(R.string.sdcard_encrypt_or_decrypt_title);
                ((ArrayList) dynamicRawDataToIndex).add(searchIndexableRaw);
            }
            return dynamicRawDataToIndex;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DirEncryptListner extends IDirEncryptServiceListener.Stub {
        public String mStatus = SignalSeverity.NONE;

        public DirEncryptListner() {}

        public final void onEncryptionStatusChanged(
                String str, int i, String str2, int i2, int i3) {
            StringBuilder sb = new StringBuilder("onEncryptionStatusChanged: operation, volId = [");
            sb.append(i);
            sb.append(", ");
            sb.append(str);
            sb.append("] ");
            Utils$$ExternalSyntheticOutline0.m(sb, str2, "CryptKeeperSDSettings");
            if (str != null) {
                if ("free".equals(str2) && !this.mStatus.equals(str2)) {
                    String volumeState = CryptSDCardSettings.this.mSse.getVolumeState();
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "status free: ", volumeState, "CryptKeeperSDSettings");
                    if (volumeState == null || "ejecting".equals(volumeState)) {
                        sendEmptyMessage(5);
                    } else {
                        CryptSDCardSettings cryptSDCardSettings = CryptSDCardSettings.this;
                        if (cryptSDCardSettings.serviceBusy) {
                            cryptSDCardSettings.mHandler.sendEmptyMessage(1);
                        }
                    }
                } else if ("busy".equals(str2)) {
                    Log.i("CryptKeeperSDSettings", "status busy");
                    CryptSDCardSettings cryptSDCardSettings2 = CryptSDCardSettings.this;
                    cryptSDCardSettings2.mProgressValue = i2;
                    if (cryptSDCardSettings2.mStorageValue == 0) {
                        cryptSDCardSettings2.mStorageValue = i3;
                    }
                    if (i == 2) {
                        cryptSDCardSettings2.mDoEncrypt = true;
                    }
                    if (cryptSDCardSettings2.isResumed()) {
                        removeMessages(2);
                        sendEmptyMessage(2);
                    }
                } else if ("Mount".equals(str2)) {
                    Log.i("CryptKeeperSDSettings", "status mount");
                    CryptSDCardSettings cryptSDCardSettings3 = CryptSDCardSettings.this;
                    if (cryptSDCardSettings3.serviceBusy) {
                        cryptSDCardSettings3.mHandler.sendEmptyMessage(4);
                        if ("MainClearConfirm".equals(CryptSDCardSettings.this.fromWhere)) {
                            Log.i(
                                    "CryptKeeperSDSettings",
                                    "MainClearConfirm Case , Device will be Reset to Factory"
                                        + " Default !!!");
                            CryptSDCardSettings.this.getActivity().setResult(-1, new Intent());
                            CryptSDCardSettings.this.getActivity().finish();
                        } else {
                            if (i == 2) {
                                CryptSDCardSettings.this.mDoEncrypt = true;
                            }
                            Log.i(
                                    "CryptKeeperSDSettings",
                                    CryptSDCardSettings.this.mDoEncrypt
                                            ? "Encrypt Success Case"
                                            : "Decrypt Success Case");
                            CryptSDCardSettings.this.getClass();
                            LoggingHelper.insertEventLogging(
                                    4500, CryptSDCardSettings.this.mDoEncrypt ? 9711 : 9712);
                        }
                    }
                } else {
                    Log.i("CryptKeeperSDSettings", "ignore status");
                }
            }
            this.mStatus = str2;
        }
    }

    /* renamed from: -$$Nest$mfinishProgress, reason: not valid java name */
    public static void m1228$$Nest$mfinishProgress(CryptSDCardSettings cryptSDCardSettings) {
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("finishProgress + "),
                cryptSDCardSettings.mDoEncrypt,
                "CryptKeeperSDSettings");
        if (cryptSDCardSettings.mProgressBar == null) {
            cryptSDCardSettings.initProgress();
        }
        Integer.toString(cryptSDCardSettings.mStorageValue);
        cryptSDCardSettings.mStorageValue = 0;
        cryptSDCardSettings.mProgressBar.setIndeterminate(false);
        cryptSDCardSettings.mProgressBar.setProgress(100);
        cryptSDCardSettings.mProgressStoarge.setText("100%");
        cryptSDCardSettings.serviceBusy = false;
        Log.i("CryptKeeperSDSettings", "updateResultUI");
        if (cryptSDCardSettings.mParent == null) {
            Log.i(
                    "CryptKeeperSDSettings",
                    "parent activity is null, cannot display UI, removing fragment");
            cryptSDCardSettings.getFragmentManager().popBackStack();
            return;
        }
        LinearLayout linearLayout = cryptSDCardSettings.mDescriptionLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        RelativeLayout relativeLayout = cryptSDCardSettings.mProgressLayout;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        Resources resources = cryptSDCardSettings.mParent.getResources();
        StringBuilder sb = new StringBuilder();
        if (cryptSDCardSettings.mDoEncrypt) {
            sb.append(
                    String.format(
                            resources.getString(R.string.sdcard_encrypt_complete), new Object[0]));
        } else {
            sb.append(
                    String.format(
                            resources.getString(R.string.sdcard_decrypt_complete), new Object[0]));
        }
        if (cryptSDCardSettings.mSse.getLastError() == 8) {
            sb.append("\n\n");
            if (cryptSDCardSettings.mDoEncrypt) {
                sb.append(
                        String.format(
                                resources.getString(R.string.sdcard_encrypt_complete_page),
                                new Object[0]));
            } else {
                sb =
                        new StringBuilder(
                                String.format(
                                        resources.getString(R.string.sdcard_decrypt_complete_page),
                                        new Object[0]));
            }
        }
        cryptSDCardSettings.mTextDesc.setText(sb.toString());
        cryptSDCardSettings.mDoneButton.setVisibility(0);
        cryptSDCardSettings.mRightButton.setVisibility(8);
        cryptSDCardSettings.mLeftButton.setVisibility(8);
        Log.i("CryptKeeperSDSettings", "disableButtonUI");
        cryptSDCardSettings.mLeftButton.setEnabled(false);
        cryptSDCardSettings.mRightButton.setEnabled(false);
    }

    /* renamed from: -$$Nest$mupdateProgress, reason: not valid java name */
    public static void m1229$$Nest$mupdateProgress(CryptSDCardSettings cryptSDCardSettings) {
        StringBuilder sb = new StringBuilder("updateProgress + ");
        sb.append(cryptSDCardSettings.mDoEncrypt);
        sb.append(" value : ");
        TooltipPopup$$ExternalSyntheticOutline0.m(
                sb, cryptSDCardSettings.mProgressValue, "CryptKeeperSDSettings");
        if (cryptSDCardSettings.mProgressBar == null) {
            cryptSDCardSettings.initProgress();
        }
        if (cryptSDCardSettings.mProgressValue == 0) {
            cryptSDCardSettings.mProgressBar.setIndeterminate(true);
            return;
        }
        Integer.toString(cryptSDCardSettings.mStorageValue);
        Integer.toString(
                (cryptSDCardSettings.mStorageValue * cryptSDCardSettings.mProgressValue) / 100);
        cryptSDCardSettings.mProgressBar.setIndeterminate(false);
        cryptSDCardSettings.mProgressBar.setProgress(cryptSDCardSettings.mProgressValue);
        String format =
                String.format(
                        cryptSDCardSettings
                                .mParent
                                .getResources()
                                .getString(R.string.crypt_keeper_setup_description_vzw),
                        Integer.valueOf(cryptSDCardSettings.mProgressValue));
        String format2 =
                String.format(
                        cryptSDCardSettings
                                .mParent
                                .getResources()
                                .getString(R.string.crypt_keeper_setup_description_dec_vzw),
                        Integer.valueOf(cryptSDCardSettings.mProgressValue));
        cryptSDCardSettings.mParent.setTitle(R.string.sdcard_encrypt_title);
        cryptSDCardSettings.mTextEncMessages.setText(R.string.sdcard_encrypt_progress);
        cryptSDCardSettings.mImgIconLock.setImageResource(R.drawable.ic_3_encrypting);
        if (cryptSDCardSettings.mDoEncrypt) {
            cryptSDCardSettings.mProgressStoarge.setText(format);
            return;
        }
        cryptSDCardSettings.mParent.setTitle(R.string.sdcard_decrypt_title);
        cryptSDCardSettings.mProgressStoarge.setText(format2);
        cryptSDCardSettings.mTextEncMessages.setText(R.string.sdcard_decrypt_progress);
        cryptSDCardSettings.mImgIconLock.setImageResource(R.drawable.ic_3_decrypting);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.samsung.android.settings.encryption.CryptSDCardSettings$1] */
    public CryptSDCardSettings() {
        BigDecimal.valueOf(1024L);
        this.MB = BigDecimal.valueOf(1048576L);
        this.GB = BigDecimal.valueOf(1073741824L);
        this.mParent = null;
        this.mDescriptionLayout = null;
        this.mDescriptionLayoutExceptButton = null;
        this.mProgressLayout = null;
        this.fromWhere = ApnSettings.MVNO_NONE;
        this.mSse = null;
        this.mImgIconLock = null;
        this.mTextEncMessages = null;
        this.mProgressStoarge = null;
        this.mProgressBar = null;
        this.mDirEncryptListner = null;
        this.serviceBusy = false;
        this.mProgressValue = 0;
        this.mStorageValue = 0;
        this.mSync = new Object();
        this.mHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.encryption.CryptSDCardSettings.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        synchronized (CryptSDCardSettings.this.mSync) {
                            try {
                                int i = message.what;
                                if (i == 1) {
                                    CryptSDCardSettings cryptSDCardSettings =
                                            CryptSDCardSettings.this;
                                    int lastError = cryptSDCardSettings.mSse.getLastError();
                                    SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    lastError,
                                                    "checkProgress LastError: ",
                                                    " Encrypted: "),
                                            cryptSDCardSettings.mDoEncrypt,
                                            "CryptKeeperSDSettings");
                                    if (lastError == 4) {
                                        cryptSDCardSettings.showAlert(1);
                                    } else if (lastError == 5) {
                                        cryptSDCardSettings.showAlert(4);
                                    } else if (lastError == 6) {
                                        cryptSDCardSettings.showAlert(5);
                                    } else if (lastError == 7) {
                                        cryptSDCardSettings.showAlert(3);
                                    } else if (lastError == 11) {
                                        cryptSDCardSettings.showAlert(2);
                                    }
                                } else if (i == 2) {
                                    CryptSDCardSettings.m1229$$Nest$mupdateProgress(
                                            CryptSDCardSettings.this);
                                } else if (i == 4) {
                                    CryptSDCardSettings.m1228$$Nest$mfinishProgress(
                                            CryptSDCardSettings.this);
                                } else if (i == 5) {
                                    if (!Utils.isTablet()) {
                                        CryptSDCardSettings.this.mParent.finish();
                                    } else if (CryptSDCardSettings.this.isResumed()) {
                                        if (CryptSDCardSettings.this
                                                        .getFragmentManager()
                                                        .getBackStackEntryCount()
                                                > 1) {
                                            CryptSDCardSettings.this
                                                    .getActivity()
                                                    .getFragmentManager()
                                                    .popBackStack();
                                        } else {
                                            CryptSDCardSettings.this.mParent.finish();
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                };
    }

    public final void doEncryptSDCard() {
        this.serviceBusy = true;
        LoggingHelper.insertEventLogging(4500, 4502, this.mDoEncrypt ? 1L : 0L);
        initProgress();
        int encryptStorage = this.mSse.encryptStorage(this.mDoEncrypt);
        if (encryptStorage == 202) {
            Log.i(
                    "CryptKeeperSDSettings",
                    "Error SD card not mounted while encrypting " + encryptStorage);
            showAlert(3);
            this.serviceBusy = false;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4500;
    }

    public final void initProgress() {
        Log.i("CryptKeeperSDSettings", "initProgress");
        LinearLayout linearLayout = this.mDescriptionLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        RelativeLayout relativeLayout = this.mProgressLayout;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        this.mImgIconLock =
                (ImageView) this.mContentView.findViewById(R.id.sdcard_enc_decr_lock_icon);
        this.mTextEncMessages =
                (TextView) this.mContentView.findViewById(R.id.sdcard_enc_confirm_message_title);
        this.mProgressStoarge =
                (TextView) this.mContentView.findViewById(R.id.sdcard_enc_progress_storage);
        this.mProgressBar = (ProgressBar) this.mContentView.findViewById(R.id.progress_bar);
        this.mParent.setTitle(R.string.sdcard_encrypt_title);
        this.mTextEncMessages.setText(R.string.sdcard_encrypt_checking);
        if (this.mDoEncrypt) {
            return;
        }
        this.mParent.setTitle(R.string.sdcard_decrypt_title);
    }

    public final boolean isAdminApplied() {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mParent.getSystemService("device_policy");
        if (devicePolicyManager == null
                || !devicePolicyManager.semGetRequireStorageCardEncryption(null)) {
            return false;
        }
        Log.i("CryptKeeperSDSettings", "isAdminApplied : true");
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        String volumeState = this.mSse.getVolumeState();
        if (i != 55) {
            return;
        }
        if (i2 != -1 || intent == null || volumeState == null) {
            restorePrefs();
        } else {
            doEncryptSDCard();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mParent = activity;
        Log.i("CryptKeeperSDSettings", "onAttach");
        if (this.mSse == null) {
            this.mSse = new SemSdCardEncryption(this.mParent);
        }
        if (this.mDirEncryptListner == null) {
            DirEncryptListner dirEncryptListner = new DirEncryptListner();
            this.mDirEncryptListner = dirEncryptListner;
            this.mSse.registerListener(dirEncryptListner);
            Log.i("CryptKeeperSDSettings", "registerListener:" + this.mDirEncryptListner);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mLeftButton) {
            this.mDoEncrypt = false;
            runKeyguardConfirmation$1();
            return;
        }
        if (view == this.mRightButton) {
            this.mDoEncrypt = true;
            runKeyguardConfirmation$1();
        } else if (view == this.mDoneButton) {
            if (!Utils.isTablet()
                    || getFragmentManager() == null
                    || getFragmentManager().getBackStackEntryCount() <= 0) {
                getActivity().finish();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView =
                layoutInflater.inflate(R.layout.sec_crypt_keeper_settings_sd, (ViewGroup) null);
        Log.i("CryptKeeperSDSettings", "onCreateView");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        Intent intent = getIntent();
        if (intent != null) {
            this.fromWhere = intent.getStringExtra("fromWhere");
        }
        this.mTextDesc = (TextView) this.mContentView.findViewById(R.id.desc);
        this.mLeftButton = (Button) this.mContentView.findViewById(R.id.left_button);
        this.mRightButton = (Button) this.mContentView.findViewById(R.id.right_button);
        this.mDoneButton = (Button) this.mContentView.findViewById(R.id.done_button);
        this.mDescriptionLayout =
                (LinearLayout) this.mContentView.findViewById(R.id.description_layout);
        this.mDescriptionLayoutExceptButton =
                (LinearLayout) this.mContentView.findViewById(R.id.description_layout_exceptbutton);
        this.mProgressLayout =
                (RelativeLayout) this.mContentView.findViewById(R.id.progress_layout);
        LinearLayout linearLayout = this.mDescriptionLayoutExceptButton;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            this.mDescriptionLayoutExceptButton.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mLeftButton.setOnClickListener(this);
        this.mRightButton.setOnClickListener(this);
        this.mDoneButton.setOnClickListener(this);
        Log.i("CryptKeeperSDSettings", "initialize Variables");
        this.mProgressValue = 0;
        this.mDoEncrypt = false;
        this.mIsDisabledByAdmin = false;
        return this.mContentView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        if (this.mDirEncryptListner != null) {
            Log.i("CryptKeeperSDSettings", "unregisterListener:" + this.mDirEncryptListner);
            this.mSse.unregisterListener(this.mDirEncryptListner);
            this.mDirEncryptListner = null;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1 != null) {
            anonymousClass1.removeMessages(1);
            removeMessages(2);
            removeMessages(3);
            removeMessages(4);
            removeMessages(5);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("CryptKeeperSDSettings", "onResume");
        if ("unmounted".equals(this.mSse.getVolumeState()) && isAdminApplied()) {
            Log.i("CryptKeeperSDSettings", "SD card is unmounted, mount SD card");
            this.mSse.setMountSDcardToHelper(true);
            this.mSse.mountVolume();
        }
        if (this.mSse.getPolicyChanged()) {
            this.mSse.setPolicyChanged(false);
        }
        restorePrefs();
        SemSdCardEncryption semSdCardEncryption = this.mSse;
        if (semSdCardEncryption != null) {
            if (semSdCardEncryption.getCurrentStatus() == 2 || this.mSse.getCurrentStatus() == 3) {
                this.serviceBusy = true;
            }
        }
    }

    public final void restorePrefs() {
        Log.i("CryptKeeperSDSettings", "restorePrefs");
        Intent intent = this.mParent.getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if (action == null || !action.equals("com.sec.app.action.START_SDCARD_ENCRYPTION")) {
                Log.i("CryptKeeperSDSettings", "CryptSDCardSettings started by user");
            } else {
                "1".equals(intent.getStringExtra("adminStart"));
            }
        } else {
            Log.i("CryptKeeperSDSettings", "CryptSDCardSettings started by user");
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mParent.getSystemService("device_policy");
        if (devicePolicyManager != null
                && devicePolicyManager.semGetRequireStorageCardEncryption(null)) {
            Log.i("CryptKeeperSDSettings", "Admin Policies is true");
            this.mIsDisabledByAdmin = true;
            Log.i("CryptKeeperSDSettings", "Disabled by Admin");
        }
        if (this.serviceBusy) {
            return;
        }
        this.mLeftButton.setVisibility(0);
        this.mLeftButton.setEnabled(true);
        this.mRightButton.setVisibility(0);
        this.mRightButton.setEnabled(true);
        Log.i("CryptKeeperSDSettings", "updatePrefUI");
        Activity activity = this.mParent;
        if (activity == null) {
            Log.i(
                    "CryptKeeperSDSettings",
                    "parent activity is null, cannot display UI, removing fragment");
            getFragmentManager().popBackStack();
            return;
        }
        activity.setTitle(R.string.sdcard_encrypt_title);
        if (this.mSse.getCurrentStatus() != 0) {
            Log.i("CryptKeeperSDSettings", "Service is busy: Disabling UI");
            Log.i("CryptKeeperSDSettings", "disableButtonUI");
            this.mLeftButton.setEnabled(false);
            this.mRightButton.setEnabled(false);
            initProgress();
        } else if (((ProgressBar) this.mContentView.findViewById(R.id.progress_bar)).getProgress()
                == 100) {
            Log.i("CryptKeeperSDSettings", "already finish enc/dec");
            sendEmptyMessage(4);
            return;
        }
        Resources resources = this.mParent.getResources();
        if (this.mSse.isSdCardEncryped()) {
            StringBuilder sb = new StringBuilder();
            this.mParent.setTitle(R.string.sdcard_decrypt_title);
            this.mLeftButton.setText(R.string.sdcard_decrypt_button);
            sb.append(
                    String.format(
                            resources.getString(
                                    R.string.sdcard_decrypt_description_tap_button_decrypt),
                            new Object[0]));
            sb.append("\n\n");
            sb.append(
                    String.format(
                            resources.getString(
                                    R.string.sdcard_decrypt_description_take_long_time_warning),
                            new Object[0]));
            sb.append("\n\n");
            sb.append(
                    String.format(
                            resources.getString(
                                    R.string
                                            .sdcard_decrypt_description_cant_use_sdcard_during_decrypt),
                            new Object[0]));
            this.mTextDesc.setText(sb.toString());
            this.mSse.getVolumeState();
            if (this.mSse.isSdCardEncryped()) {
                this.mRightButton.setVisibility(8);
            }
        } else {
            this.mTextDesc.setText(
                    String.format(
                                    resources.getString(R.string.sdcard_encrypt_description),
                                    new Object[0])
                            + "\n\n"
                            + String.format(
                                    resources.getString(
                                            R.string
                                                    .sdcard_encrypt_description_take_long_time_warning),
                                    new Object[0]));
            this.mRightButton.setText(R.string.sdcard_encrypt_button);
            this.mLeftButton.setVisibility(8);
        }
        this.mDoneButton.setVisibility(8);
        if (this.mIsDisabledByAdmin) {
            this.mSse.getVolumeState();
            if (this.mSse.isSdCardEncryped()) {
                this.mLeftButton.setEnabled(false);
            }
        }
        String volumeState = this.mSse.getVolumeState();
        if (volumeState == null) {
            if (volumeState == null) {
                this.mTextDesc.setText(R.string.sdcard_not_inserted);
                this.mRightButton.setText(R.string.sdcard_encrypt_button);
            } else {
                this.mTextDesc.setText(
                        Utils.isTablet()
                                ? R.string.sdcard_mount_failed_block_tablet
                                : R.string.sdcard_mount_failed_block);
                this.mRightButton.setText(R.string.sdcard_format_sd_card);
            }
            this.mLeftButton.setVisibility(8);
            this.mRightButton.setVisibility(0);
            this.mRightButton.setEnabled(volumeState != null);
        } else if (!"removed".equals(volumeState)) {
            if ("HiddenMount".equals(volumeState) && !this.serviceBusy) {
                this.mTextDesc.setText(R.string.sdcard_enc_dec_select);
                this.mLeftButton.setText(R.string.sdcard_decrypt_select_button);
                this.mRightButton.setText(R.string.sdcard_encrypt_select_button);
                SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                        new StringBuilder("updatePrefUI : "),
                        this.mDoEncrypt,
                        "CryptKeeperSDSettings");
                this.mRightButton.setVisibility(0);
                this.mLeftButton.setVisibility(0);
                this.mRightButton.setEnabled(true);
                this.mLeftButton.setEnabled(true);
            }
            if (this.mSse.getLastError() == 8) {
                LoggingHelper.insertEventLogging(4500, 9713);
                this.mTextDesc.setText(R.string.sdcard_decrypt_error_otherdevice);
                this.mRightButton.setVisibility(8);
                this.mLeftButton.setVisibility(0);
                this.mLeftButton.setText(R.string.sdcard_decrypt_button);
                this.mLeftButton.setEnabled(false);
            } else if (this.mIsDisabledByAdmin) {
                this.mTextDesc.setText(R.string.sdcard_policy_need_encrypt);
                this.mLeftButton.setVisibility(8);
                this.mLeftButton.setEnabled(false);
            }
        }
        if (this.mLeftButton.isEnabled()) {
            this.mLeftButton.requestFocus();
        } else {
            this.mRightButton.requestFocus();
        }
    }

    public final boolean runKeyguardConfirmation$1() {
        Resources resources = this.mParent.getResources();
        if (new LockPatternUtils(this.mParent)
                        .getKeyguardStoredPasswordQuality(UserHandle.myUserId())
                == 0) {
            doEncryptSDCard();
            return true;
        }
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(this.mParent, this);
        builder.mRequestCode = 55;
        builder.mTitle = resources.getText(R.string.crypt_keeper_encrypt_title);
        builder.mReturnCredentials = true;
        return builder.show();
    }

    public final void showAlert(int i) {
        long j;
        String str;
        Log.i(
                "CryptKeeperSDSettings",
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        i, this.mDoEncrypt ? "Encrypt Fail Case :" : "Decrypt Fail Case : "));
        int i2 = R.string.sdcard_decrypt_err_title;
        switch (i) {
            case 1:
                double additionalSpaceRequired = this.mSse.getAdditionalSpaceRequired() / 1024.0d;
                if (additionalSpaceRequired < 1.0d) {
                    additionalSpaceRequired = 1.0d;
                }
                if (this.mDoEncrypt) {
                    i2 = R.string.sdcard_encrypt_err_title;
                }
                Log.i(
                        "CryptKeeperSDSettings",
                        "AlertDialog storage warning - need over the "
                                + additionalSpaceRequired
                                + "MB");
                LoggingHelper.insertEventLogging(4500, this.mDoEncrypt ? 9714 : 9715, 1L);
                new AlertDialog.Builder(getActivity())
                        .setTitle(i2)
                        .setMessage(
                                getString(
                                        R.string.sdcard_encrypt_error_storage,
                                        String.format(
                                                "%.2f", Double.valueOf(additionalSpaceRequired)),
                                        "MB"))
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, this)
                        .show();
                return;
            case 2:
                Log.i("CryptKeeperSDSettings", "AlertDialog - File opening fail");
                LoggingHelper.insertEventLogging(4500, this.mDoEncrypt ? 9714 : 9715, 2L);
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sdcard_encrypt_err_title)
                        .setMessage(R.string.sdcard_encrypt_error)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, this)
                        .show();
                return;
            case 3:
                boolean z = this.mDoEncrypt;
                if (z) {
                    i2 = R.string.sdcard_encrypt_err_title;
                }
                int i3 =
                        z
                                ? R.string.sdcard_encrypt_error_mount
                                : R.string.sdcard_decrypt_error_mount;
                Log.i("CryptKeeperSDSettings", "AlertDialog warning - mount fail");
                LoggingHelper.insertEventLogging(4500, this.mDoEncrypt ? 9714 : 9715, 3L);
                new AlertDialog.Builder(getActivity())
                        .setTitle(i2)
                        .setMessage(i3)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, this)
                        .show();
                return;
            case 4:
                Log.i("CryptKeeperSDSettings", "AlertDialog warning - encrypt error");
                LoggingHelper.insertEventLogging(4500, 9714, 10L);
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sdcard_encrypt_err_title)
                        .setMessage(R.string.sdcard_encrypt_error)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, this)
                        .show();
                return;
            case 5:
                Log.i("CryptKeeperSDSettings", "AlertDialog warning - decrypt error");
                LoggingHelper.insertEventLogging(4500, 9715, 10L);
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sdcard_decrypt_err_title)
                        .setMessage(R.string.sdcard_decrypt_error)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, this)
                        .show();
                return;
            case 6:
                Log.i("CryptKeeperSDSettings", "REMOVE_USERDATA_WARN_DIALOG");
                View inflate =
                        View.inflate(
                                getActivity(),
                                R.layout.sec_crypt_keeper_settings_sd_warning_dialog,
                                null);
                TextView textView =
                        (TextView) inflate.findViewById(R.id.sdcard_enc_warning_dialog_content);
                DirEncryptionWrapper dirEncryptionWrapper = new DirEncryptionWrapper(this.mParent);
                if (!isAdminApplied() || "mounted".equals(dirEncryptionWrapper.getVolumeState())) {
                    String externalSdPath = dirEncryptionWrapper.getExternalSdPath();
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "statfs path: ", externalSdPath, "CryptKeeperSDSettings");
                    String volumeState = this.mSse.getVolumeState();
                    if (externalSdPath == null
                            || volumeState == null
                            || !"mounted".equals(volumeState)) {
                        j = 0;
                    } else {
                        StatFs statFs = new StatFs(externalSdPath);
                        long totalBytes = statFs.getTotalBytes();
                        long availableBytes = statFs.getAvailableBytes();
                        long j2 = totalBytes - availableBytes;
                        StringBuilder m =
                                SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                                        totalBytes, "totalBytes: ", " availableBytes: ");
                        m.append(availableBytes);
                        Log.i("CryptKeeperSDSettings", m.toString());
                        j = j2;
                    }
                } else {
                    Log.i("CryptKeeperSDSettings", "Admin Policy is Applied");
                    j = dirEncryptionWrapper.getExternalSDvolUsedSize();
                }
                Log.i(
                        "CryptKeeperSDSettings",
                        "AlertDialog REMOVE_USERDATA_WARN_DIALOG - usedsize : " + j);
                if (j < 1) {
                    textView.setText(
                            getActivity()
                                    .getString(
                                            R.string.sdcard_encrypt_warning_description_invalid));
                } else {
                    BigDecimal bigDecimal = new BigDecimal(j);
                    if (bigDecimal.compareTo(this.GB) < 0) {
                        str =
                                bigDecimal.divide(this.MB, 2, 6).toString()
                                        + getActivity()
                                                .getResources()
                                                .getString(R.string.data_usage_display_mb);
                    } else {
                        str =
                                bigDecimal.divide(this.GB, 2, 6).toString()
                                        + getActivity()
                                                .getResources()
                                                .getString(R.string.data_usage_display_gb);
                    }
                    Log.i(
                            "CryptKeeperSDSettings",
                            "AlertDialog REMOVE_USERDATA_WARN_DIALOG - usedSizeText :" + str);
                    textView.setText(
                            getActivity()
                                    .getString(
                                            R.string.sdcard_encrypt_warning_description,
                                            new Object[] {str}));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.sdcard_encrypt_warning_title);
                builder.setView(inflate);
                builder.setCancelable(false);
                builder.setPositiveButton(
                        R.string.format,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.encryption.CryptSDCardSettings.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                CryptSDCardSettings cryptSDCardSettings = CryptSDCardSettings.this;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        CryptSDCardSettings.SEARCH_INDEX_DATA_PROVIDER;
                                if (cryptSDCardSettings.runKeyguardConfirmation$1()) {
                                    return;
                                }
                                Log.e(
                                        "CryptKeeperSDSettings",
                                        "Alert::Keyguard confirmation failed");
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new AnonymousClass3());
                Resources resources = this.mParent.getResources();
                AlertDialog create = builder.create();
                create.show();
                create.getButton(-1).setEnabled(true);
                create.getButton(-1)
                        .setTextColor(
                                resources.getColor(R.color.button_format_wanning_dialog_color));
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this.mParent.finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.encryption.CryptSDCardSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}
