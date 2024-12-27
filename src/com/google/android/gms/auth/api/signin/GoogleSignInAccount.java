package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zab();
    public static Clock zaa = DefaultClock.zza;
    public final int zab;
    public final List zac;
    public final String zad;
    public final String zae;
    public final String zaf;
    public final String zag;
    public final Uri zah;
    public String zai;
    public final long zaj;
    public final String zak;
    public final String zal;
    public final String zam;
    public final Set zan = new HashSet();

    public GoogleSignInAccount(
            int i,
            String str,
            String str2,
            String str3,
            String str4,
            Uri uri,
            String str5,
            long j,
            String str6,
            List list,
            String str7,
            String str8) {
        this.zab = i;
        this.zad = str;
        this.zae = str2;
        this.zaf = str3;
        this.zag = str4;
        this.zah = uri;
        this.zai = str5;
        this.zaj = j;
        this.zak = str6;
        this.zac = list;
        this.zal = str7;
        this.zam = str8;
    }

    public static GoogleSignInAccount zab(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString("photoUrl");
        Uri parse = !TextUtils.isEmpty(optString) ? Uri.parse(optString) : null;
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(1, jSONArray.getString(i)));
        }
        String optString2 = jSONObject.optString("id");
        String optString3 = jSONObject.has("tokenId") ? jSONObject.optString("tokenId") : null;
        String optString4 = jSONObject.has("email") ? jSONObject.optString("email") : null;
        String optString5 =
                jSONObject.has(Account.DISPLAY_NAME)
                        ? jSONObject.optString(Account.DISPLAY_NAME)
                        : null;
        String optString6 = jSONObject.has("givenName") ? jSONObject.optString("givenName") : null;
        String optString7 =
                jSONObject.has("familyName") ? jSONObject.optString("familyName") : null;
        String string = jSONObject.getString("obfuscatedIdentifier");
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        GoogleSignInAccount googleSignInAccount =
                new GoogleSignInAccount(
                        3,
                        optString2,
                        optString3,
                        optString4,
                        optString5,
                        parse,
                        null,
                        parseLong,
                        string,
                        new ArrayList(hashSet),
                        optString6,
                        optString7);
        googleSignInAccount.zai =
                jSONObject.has("serverAuthCode") ? jSONObject.optString("serverAuthCode") : null;
        return googleSignInAccount;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        if (googleSignInAccount.zak.equals(this.zak)) {
            HashSet hashSet = new HashSet(googleSignInAccount.zac);
            hashSet.addAll(googleSignInAccount.zan);
            HashSet hashSet2 = new HashSet(this.zac);
            hashSet2.addAll(this.zan);
            if (hashSet.equals(hashSet2)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int m =
                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        FileType.TORRENT, 31, this.zak);
        HashSet hashSet = new HashSet(this.zac);
        hashSet.addAll(this.zan);
        return hashSet.hashCode() + m;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zab;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeString(parcel, 2, this.zad);
        SafeParcelWriter.writeString(parcel, 3, this.zae);
        SafeParcelWriter.writeString(parcel, 4, this.zaf);
        SafeParcelWriter.writeString(parcel, 5, this.zag);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zah, i);
        SafeParcelWriter.writeString(parcel, 7, this.zai);
        long j = this.zaj;
        SafeParcelWriter.zzc(parcel, 8, 8);
        parcel.writeLong(j);
        SafeParcelWriter.writeString(parcel, 9, this.zak);
        SafeParcelWriter.writeTypedList(parcel, this.zac, 10);
        SafeParcelWriter.writeString(parcel, 11, this.zal);
        SafeParcelWriter.writeString(parcel, 12, this.zam);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
