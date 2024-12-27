package com.android.settings.accounts;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddAccountSettings extends Activity {
    public PendingIntent mPendingIntent;
    public UserHandle mUserHandle;
    public boolean mAddAccountDisplayed = false;
    public final AnonymousClass1 mCallback =
            new AccountManagerCallback() { // from class:
                                           // com.android.settings.accounts.AddAccountSettings.1
                @Override // android.accounts.AccountManagerCallback
                public final void run(AccountManagerFuture accountManagerFuture) {
                    boolean z = true;
                    try {
                        try {
                            Bundle bundle = (Bundle) accountManagerFuture.getResult();
                            Intent intent = (Intent) bundle.get("intent");
                            if (intent != null) {
                                try {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "pendingIntent",
                                            AddAccountSettings.this.mPendingIntent);
                                    Context applicationContext =
                                            AddAccountSettings.this.getApplicationContext();
                                    StringBuilder sb = Utils.sBuilder;
                                    if (((UserManager)
                                                            applicationContext.getSystemService(
                                                                    UserManager.class))
                                                    .getUsers()
                                                    .size()
                                            <= 1) {
                                        z = false;
                                    }
                                    bundle2.putBoolean("hasMultipleUsers", z);
                                    bundle2.putParcelable(
                                            "android.intent.extra.USER",
                                            AddAccountSettings.this.mUserHandle);
                                    intent.putExtras(bundle2)
                                            .addFlags(268435456)
                                            .addFlags(
                                                    NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                    if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(
                                            AddAccountSettings.this.getApplicationContext())) {
                                        intent.removeFlags(268468224);
                                    }
                                    AddAccountSettings.this.startActivityForResultAsUser(
                                            new Intent(intent),
                                            2,
                                            AddAccountSettings.this.mUserHandle);
                                    z = false;
                                } catch (AuthenticatorException e) {
                                    e = e;
                                    z = false;
                                    if (Log.isLoggable("AddAccountSettings", 2)) {
                                        Log.v("AddAccountSettings", "addAccount failed: " + e);
                                    }
                                    if (z) {
                                        AddAccountSettings.this.finish();
                                    }
                                    return;
                                } catch (OperationCanceledException unused) {
                                    z = false;
                                    if (Log.isLoggable("AddAccountSettings", 2)) {
                                        Log.v("AddAccountSettings", "addAccount was canceled");
                                    }
                                    if (!z) {
                                        return;
                                    }
                                    AddAccountSettings.this.finish();
                                } catch (IOException e2) {
                                    e = e2;
                                    z = false;
                                    if (Log.isLoggable("AddAccountSettings", 2)) {
                                        Log.v("AddAccountSettings", "addAccount failed: " + e);
                                    }
                                    if (z) {
                                        AddAccountSettings.this.finish();
                                    }
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    z = false;
                                    if (z) {
                                        AddAccountSettings.this.finish();
                                    }
                                    throw th;
                                }
                            } else {
                                AddAccountSettings.this.setResult(-1);
                                PendingIntent pendingIntent =
                                        AddAccountSettings.this.mPendingIntent;
                                if (pendingIntent != null) {
                                    pendingIntent.cancel();
                                    AddAccountSettings.this.mPendingIntent = null;
                                }
                            }
                            if (Log.isLoggable("AddAccountSettings", 2)) {
                                Log.v("AddAccountSettings", "account added: " + bundle);
                            }
                            if (!z) {
                                return;
                            }
                        } catch (AuthenticatorException e3) {
                            e = e3;
                        } catch (OperationCanceledException unused2) {
                        } catch (IOException e4) {
                            e = e4;
                        }
                        AddAccountSettings.this.finish();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            };
    public boolean mAddAccountCalled = false;

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                if (i2 == -1) {
                    requestChooseAccount();
                    return;
                } else {
                    finish();
                    return;
                }
            }
            setResult(i2);
            PendingIntent pendingIntent = this.mPendingIntent;
            if (pendingIntent != null) {
                pendingIntent.cancel();
                this.mPendingIntent = null;
            }
            finish();
            return;
        }
        if (i2 == 0) {
            if (intent != null) {
                startActivityAsUser(intent, this.mUserHandle);
            }
            setResult(i2);
            finish();
            return;
        }
        String stringExtra = intent.getStringExtra("selected_account");
        Bundle bundle = new Bundle();
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("SHOULDN'T RESOLVE!", "SHOULDN'T RESOLVE!"));
        intent2.setAction("SHOULDN'T RESOLVE!");
        intent2.addCategory("SHOULDN'T RESOLVE!");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent2, 67108864);
        this.mPendingIntent = broadcast;
        bundle.putParcelable("pendingIntent", broadcast);
        Context applicationContext = getApplicationContext();
        StringBuilder sb = Utils.sBuilder;
        bundle.putBoolean(
                "hasMultipleUsers",
                ((UserManager) applicationContext.getSystemService(UserManager.class))
                                .getUsers()
                                .size()
                        > 1);
        AccountManager.get(this)
                .addAccountAsUser(
                        stringExtra,
                        null,
                        null,
                        bundle,
                        null,
                        this.mCallback,
                        null,
                        this.mUserHandle);
        this.mAddAccountCalled = true;
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mAddAccountCalled = bundle.getBoolean("AddAccountCalled");
            this.mAddAccountDisplayed = bundle.getBoolean("key_account_displayed");
            if (Log.isLoggable("AddAccountSettings", 2)) {
                Log.v("AddAccountSettings", "restored");
            }
        }
        UserManager userManager = (UserManager) getSystemService("user");
        UserHandle secureTargetUser =
                Utils.getSecureTargetUser(
                        getActivityToken(), userManager, null, getIntent().getExtras());
        this.mUserHandle = secureTargetUser;
        if (userManager.hasUserRestriction("no_modify_accounts", secureTargetUser)) {
            Toast.makeText(getApplicationContext(), R.string.user_cannot_add_accounts_message, 1)
                    .show();
            finish();
            return;
        }
        if (this.mAddAccountCalled || this.mAddAccountDisplayed) {
            Log.d(
                    "AddAccountSettings",
                    "Cannot keep this activity, mAddAccountDisplayed = "
                            + this.mAddAccountDisplayed);
            finish();
            return;
        }
        if (Utils.startQuietModeDialogIfNecessary(
                getApplicationContext(), userManager, this.mUserHandle.getIdentifier())) {
            finish();
            return;
        }
        if (userManager.isUserUnlocked(this.mUserHandle)) {
            requestChooseAccount();
            return;
        }
        ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
        builder.mRequestCode = 3;
        builder.mTitle = getString(R.string.sec_unlock_set_unlock_launch_picker_title);
        builder.mUserId = this.mUserHandle.getIdentifier();
        if (builder.show()) {
            return;
        }
        requestChooseAccount();
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("AddAccountCalled", this.mAddAccountCalled);
        bundle.putBoolean("key_account_displayed", this.mAddAccountDisplayed);
        if (Log.isLoggable("AddAccountSettings", 2)) {
            Log.v("AddAccountSettings", "saved");
        }
    }

    public final void requestChooseAccount() {
        String[] stringArrayExtra = getIntent().getStringArrayExtra("authorities");
        String[] stringArrayExtra2 = getIntent().getStringArrayExtra("account_types");
        Intent intent = new Intent(this, (Class<?>) Settings.ChooseAccountActivity.class);
        if (stringArrayExtra != null) {
            intent.putExtra("authorities", stringArrayExtra);
        }
        if (stringArrayExtra2 != null) {
            intent.putExtra("account_types", stringArrayExtra2);
        }
        intent.putExtra("android.intent.extra.USER", this.mUserHandle);
        startActivityForResult(intent, 1);
        this.mAddAccountDisplayed = true;
    }
}
