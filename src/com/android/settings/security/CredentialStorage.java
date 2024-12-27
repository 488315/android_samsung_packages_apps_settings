package com.android.settings.security;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.vpn2.VpnUtils;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CredentialStorage extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Bundle mInstallBundle;
    public boolean mNeedCallResetDialog = true;
    public LockPatternUtils mUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InstallKeyInKeyChain extends AsyncTask {
        public final String mAlias;
        public final byte[] mCaListData;
        public final byte[] mCertData;
        public final byte[] mKeyData;
        public final int mUid;

        public InstallKeyInKeyChain(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
            this.mAlias = str;
            this.mKeyData = bArr;
            this.mCertData = bArr2;
            this.mCaListData = bArr3;
            this.mUid = i;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Boolean bool;
            try {
                KeyChain.KeyChainConnection bind = KeyChain.bind(CredentialStorage.this);
                try {
                    IKeyChainService service = bind.getService();
                    if (service.installKeyPair(
                            this.mKeyData,
                            this.mCertData,
                            this.mCaListData,
                            this.mAlias,
                            this.mUid)) {
                        int i = this.mUid;
                        if (i == 1000 || i == -1) {
                            service.setUserSelectable(this.mAlias, true);
                        }
                        bool = Boolean.TRUE;
                    } else {
                        Log.w("CredentialStorage", "Failed installing key " + this.mAlias);
                        bool = Boolean.FALSE;
                    }
                    bind.close();
                    return bool;
                } catch (Throwable th) {
                    if (bind != null) {
                        try {
                            bind.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (RemoteException e) {
                Log.w(
                        "CredentialStorage",
                        String.format(
                                "Failed to install key %s to uid %d",
                                this.mAlias, Integer.valueOf(this.mUid)),
                        e);
                return Boolean.FALSE;
            } catch (InterruptedException e2) {
                Log.w("CredentialStorage", "Interrupted while installing key " + this.mAlias, e2);
                Thread.currentThread().interrupt();
                return Boolean.FALSE;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            CredentialStorage credentialStorage = CredentialStorage.this;
            String str = this.mAlias;
            int i = this.mUid;
            boolean booleanValue = ((Boolean) obj).booleanValue();
            int i2 = CredentialStorage.$r8$clinit;
            credentialStorage.getClass();
            if (!booleanValue) {
                Log.w(
                        "CredentialStorage",
                        String.format(
                                "Error installing alias %s for uid %d", str, Integer.valueOf(i)));
                credentialStorage.finish();
            } else {
                Log.i(
                        "CredentialStorage",
                        String.format(
                                "Successfully installed alias %s to uid %d.",
                                str, Integer.valueOf(i)));
                credentialStorage.sendBroadcast(
                        new Intent("android.security.action.KEYCHAIN_CHANGED"));
                credentialStorage.setResult(-1);
                credentialStorage.finish();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResetDialog
            implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
        public boolean mResetConfirmed;

        public ResetDialog() {
            View inflate =
                    CredentialStorage.this
                            .getLayoutInflater()
                            .inflate(R.layout.sec_clear_credentials, (ViewGroup) null);
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(CredentialStorage.this, R.style.sec_credential_dialog);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setView(inflate);
            builder.setPositiveButton(R.string.common_remove, this);
            builder.setNegativeButton(android.R.string.cancel, this);
            AlertDialog create = builder.create();
            create.setOnDismissListener(this);
            create.show();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            this.mResetConfirmed = i == -1;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            if (!this.mResetConfirmed) {
                CredentialStorage.this.finish();
                return;
            }
            this.mResetConfirmed = false;
            if (!CredentialStorage.this.mUtils.isSecure(UserHandle.myUserId())) {
                CredentialStorage.this.new ResetKeyStoreAndKeyChain().execute(new Void[0]);
                return;
            }
            CredentialStorage credentialStorage = CredentialStorage.this;
            Resources resources = credentialStorage.getResources();
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(credentialStorage);
            builder.mRequestCode = 1;
            builder.mTitle = resources.getText(R.string.credentials_title);
            if (builder.show()) {
                return;
            }
            Log.w(
                    "CredentialStorage",
                    "Failed to launch credential confirmation for a secure user.");
            CredentialStorage.this.finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResetKeyStoreAndKeyChain extends AsyncTask {
        public ResetKeyStoreAndKeyChain() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            CredentialStorage.this.mUtils.resetKeyStore(UserHandle.myUserId());
            UserManager userManager = (UserManager) CredentialStorage.this.getSystemService("user");
            List<UserHandle> userProfiles = userManager.getUserProfiles();
            int size = userProfiles.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    try {
                        UserHandle userHandle = userProfiles.get(i2);
                        if (userManager.isUserUnlocked(userHandle.getIdentifier())) {
                            KeyChain.KeyChainConnection bindAsUser =
                                    KeyChain.bindAsUser(CredentialStorage.this, userHandle);
                            try {
                                try {
                                    if (bindAsUser.getService().reset()) {
                                        i++;
                                    }
                                } finally {
                                    bindAsUser.close();
                                }
                            } catch (RemoteException unused) {
                                Boolean bool = Boolean.FALSE;
                                bindAsUser.close();
                                return bool;
                            }
                        } else {
                            i++;
                        }
                    } catch (AssertionError | IllegalArgumentException unused2) {
                        Log.w("CredentialStorage", "CredentialStorage is destroyed");
                        return Boolean.FALSE;
                    }
                } catch (InterruptedException unused3) {
                    Thread.currentThread().interrupt();
                    return Boolean.FALSE;
                }
            }
            return Boolean.valueOf(size == i);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            if (((Boolean) obj).booleanValue()) {
                Toast.makeText(CredentialStorage.this, R.string.credentials_erased, 0).show();
                CredentialStorage credentialStorage = CredentialStorage.this;
                int i = CredentialStorage.$r8$clinit;
                if (VpnUtils.disconnectLegacyVpn(credentialStorage.getApplicationContext())) {
                    Toast.makeText(credentialStorage, R.string.vpn_disconnected, 0).show();
                }
            } else {
                Toast.makeText(CredentialStorage.this, R.string.credentials_not_erased, 0).show();
            }
            CredentialStorage.this.finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            if (i2 == -1) {
                new ResetKeyStoreAndKeyChain().execute(new Void[0]);
            } else {
                finish();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUtils = new LockPatternUtils(this);
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        this.mNeedCallResetDialog = true;
        Log.d("CredentialStorage", "onDestroy() called");
        super.onDestroy();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0094, code lost:

       if (getPackageManager().checkSignatures(getCallingPackage(), getPackageName()) == 0) goto L33;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00cc, code lost:

       if (r1.id == android.os.UserHandle.myUserId()) goto L33;
    */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.security.CredentialStorage.onResume():void");
    }
}
