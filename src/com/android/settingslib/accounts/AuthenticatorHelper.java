package com.android.settingslib.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AuthenticatorHelper extends BroadcastReceiver {
    public final Context mContext;
    public final OnAccountsUpdateListener mListener;
    public boolean mListeningToAccountUpdates;
    public final UserHandle mUserHandle;
    public final Map mTypeToAuthDescription = new HashMap();
    public final ArrayList mEnabledAccountTypes = new ArrayList();
    public final Map mAccTypeIconCache = new HashMap();
    public final HashMap mAccountTypeToAuthorities = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAccountsUpdateListener {
        void onAccountsUpdate(UserHandle userHandle);
    }

    public AuthenticatorHelper(
            Context context,
            UserHandle userHandle,
            OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mListener = onAccountsUpdateListener;
        onAccountsUpdated(null);
        onAccountsUpdated(null);
    }

    public final Drawable getDrawableForType(Context context, String str) {
        synchronized (this.mAccTypeIconCache) {
            try {
                if (this.mAccTypeIconCache.containsKey(str)) {
                    return (Drawable) this.mAccTypeIconCache.get(str);
                }
                Drawable drawable = null;
                if (this.mTypeToAuthDescription.containsKey(str)) {
                    try {
                        AuthenticatorDescription authenticatorDescription =
                                (AuthenticatorDescription) this.mTypeToAuthDescription.get(str);
                        Context createPackageContextAsUser =
                                context.createPackageContextAsUser(
                                        authenticatorDescription.packageName, 0, this.mUserHandle);
                        PackageManager packageManager = this.mContext.getPackageManager();
                        drawable =
                                packageManager.semShouldPackIntoIconTray(
                                                authenticatorDescription.packageName)
                                        ? packageManager.getUserBadgedIcon(
                                                packageManager.semGetDrawableForIconTray(
                                                        createPackageContextAsUser.getDrawable(
                                                                authenticatorDescription.iconId),
                                                        1),
                                                this.mUserHandle)
                                        : packageManager.getUserBadgedIcon(
                                                createPackageContextAsUser.getDrawable(
                                                        authenticatorDescription.iconId),
                                                this.mUserHandle);
                        synchronized (this.mAccTypeIconCache) {
                            this.mAccTypeIconCache.put(str, drawable);
                        }
                    } catch (PackageManager.NameNotFoundException
                            | Resources.NotFoundException unused) {
                    }
                }
                return drawable == null
                        ? context.getPackageManager().getDefaultActivityIcon()
                        : drawable;
            } finally {
            }
        }
    }

    public final CharSequence getLabelForType(Context context, String str) {
        if (this.mTypeToAuthDescription.containsKey(str)) {
            try {
                AuthenticatorDescription authenticatorDescription =
                        (AuthenticatorDescription) this.mTypeToAuthDescription.get(str);
                return context.createPackageContextAsUser(
                                authenticatorDescription.packageName, 0, this.mUserHandle)
                        .getResources()
                        .getText(authenticatorDescription.labelId);
            } catch (PackageManager.NameNotFoundException unused) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "No label name for account type ", str, "AuthenticatorHelper");
            } catch (Resources.NotFoundException unused2) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "No label icon for account type ", str, "AuthenticatorHelper");
            }
        }
        return null;
    }

    public final void listenToAccountUpdates() {
        if (this.mListeningToAccountUpdates) {
            return;
        }
        this.mContext.registerReceiverAsUser(
                this,
                this.mUserHandle,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.accounts.LOGIN_ACCOUNTS_CHANGED",
                        "android.intent.action.DEVICE_STORAGE_OK"),
                null,
                null);
        this.mListeningToAccountUpdates = true;
    }

    public final void onAccountsUpdated(Account[] accountArr) {
        updateAuthDescriptions(this.mContext);
        if (accountArr == null) {
            accountArr =
                    AccountManager.get(this.mContext)
                            .getAccountsAsUser(this.mUserHandle.getIdentifier());
        }
        this.mEnabledAccountTypes.clear();
        this.mAccTypeIconCache.clear();
        for (Account account : accountArr) {
            if (!this.mEnabledAccountTypes.contains(account.type)) {
                this.mEnabledAccountTypes.add(account.type);
            }
        }
        this.mAccountTypeToAuthorities.clear();
        for (SyncAdapterType syncAdapterType :
                ContentResolver.getSyncAdapterTypesAsUser(this.mUserHandle.getIdentifier())) {
            ArrayList arrayList =
                    (ArrayList) this.mAccountTypeToAuthorities.get(syncAdapterType.accountType);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mAccountTypeToAuthorities.put(syncAdapterType.accountType, arrayList);
            }
            if (Log.isLoggable("AuthenticatorHelper", 2)) {
                Log.v(
                        "AuthenticatorHelper",
                        "Added authority "
                                + syncAdapterType.authority
                                + " to accountType "
                                + syncAdapterType.accountType);
            }
            arrayList.add(syncAdapterType.authority);
        }
        if (this.mListeningToAccountUpdates) {
            this.mListener.onAccountsUpdate(this.mUserHandle);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        onAccountsUpdated(
                AccountManager.get(this.mContext)
                        .getAccountsAsUser(this.mUserHandle.getIdentifier()));
    }

    public final void updateAuthDescriptions(Context context) {
        for (AuthenticatorDescription authenticatorDescription :
                AccountManager.get(context)
                        .getAuthenticatorTypesAsUser(this.mUserHandle.getIdentifier())) {
            this.mTypeToAuthDescription.put(
                    authenticatorDescription.type, authenticatorDescription);
        }
    }
}
