package com.google.android.gms.common.api.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zat;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.signin.internal.zaf;
import com.google.android.gms.signin.internal.zak;
import com.google.android.gms.signin.zad;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import org.json.JSONException;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zact extends com.google.android.gms.signin.internal.zac
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final com.google.android.gms.signin.zaa zaa = zad.zac;
    public final Context zab;
    public final Handler zac;
    public final com.google.android.gms.signin.zaa zad;
    public final Set zae;
    public final ClientSettings zaf;
    public SignInClientImpl zag;
    public zabu zah;

    public zact(Context context, Handler handler, ClientSettings clientSettings) {
        com.google.android.gms.signin.zaa zaaVar = zaa;
        attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
        this.zab = context;
        this.zac = handler;
        this.zaf = clientSettings;
        this.zae = clientSettings.zab;
        this.zad = zaaVar;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected() {
        GoogleSignInAccount googleSignInAccount;
        Parcel obtain;
        Parcel obtain2;
        SignInClientImpl signInClientImpl = this.zag;
        signInClientImpl.getClass();
        Preconditions.checkNotNull(this, "Expecting a valid ISignInCallbacks");
        try {
            Account account = signInClientImpl.zac.zaa;
            if (account == null) {
                account = new Account("<<default account>>", RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
            }
            try {
                if ("<<default account>>".equals(account.name)) {
                    Storage storage = Storage.getInstance(signInClientImpl.zzl);
                    String zaa2 = storage.zaa("defaultGoogleSignInAccount");
                    if (!TextUtils.isEmpty(zaa2)) {
                        StringBuilder sb = new StringBuilder(20 + String.valueOf(zaa2).length());
                        sb.append("googleSignInAccount:");
                        sb.append(zaa2);
                        String zaa3 = storage.zaa(sb.toString());
                        if (zaa3 != null) {
                            try {
                                googleSignInAccount = GoogleSignInAccount.zab(zaa3);
                            } catch (JSONException unused) {
                            }
                            Integer num = signInClientImpl.zae;
                            Preconditions.checkNotNull(num);
                            zat zatVar = new zat(2, account, num.intValue(), googleSignInAccount);
                            zaf zafVar = (zaf) signInClientImpl.getService();
                            com.google.android.gms.signin.internal.zai zaiVar =
                                    new com.google.android.gms.signin.internal.zai(1, zatVar);
                            obtain = Parcel.obtain();
                            obtain.writeInterfaceToken(zafVar.zab);
                            int i = com.google.android.gms.internal.base.zac.$r8$clinit;
                            obtain.writeInt(1);
                            zaiVar.writeToParcel(obtain, 0);
                            obtain.writeStrongBinder(this);
                            obtain2 = Parcel.obtain();
                            zafVar.zaa.transact(12, obtain, obtain2, 0);
                            obtain2.readException();
                            obtain.recycle();
                            obtain2.recycle();
                            return;
                        }
                    }
                }
                zafVar.zaa.transact(12, obtain, obtain2, 0);
                obtain2.readException();
                obtain.recycle();
                obtain2.recycle();
                return;
            } catch (Throwable th) {
                obtain.recycle();
                obtain2.recycle();
                throw th;
            }
            googleSignInAccount = null;
            Integer num2 = signInClientImpl.zae;
            Preconditions.checkNotNull(num2);
            zat zatVar2 = new zat(2, account, num2.intValue(), googleSignInAccount);
            zaf zafVar2 = (zaf) signInClientImpl.getService();
            com.google.android.gms.signin.internal.zai zaiVar2 =
                    new com.google.android.gms.signin.internal.zai(1, zatVar2);
            obtain = Parcel.obtain();
            obtain.writeInterfaceToken(zafVar2.zab);
            int i2 = com.google.android.gms.internal.base.zac.$r8$clinit;
            obtain.writeInt(1);
            zaiVar2.writeToParcel(obtain, 0);
            obtain.writeStrongBinder(this);
            obtain2 = Parcel.obtain();
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                this.zac.post(new zacr(this, new zak(1, new ConnectionResult(8, null), null)));
            } catch (RemoteException unused2) {
                Log.wtf(
                        "SignInClientImpl",
                        "ISignInCallbacks#onSignInComplete should be executed from the same"
                            + " process, unexpected RemoteException.",
                        e);
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zah.zae(connectionResult);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        this.zag.disconnect();
    }
}
