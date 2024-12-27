package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArraySet;

import com.google.android.gms.signin.SignInOptions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ClientSettings {
    public final Account zaa;
    public final Set zab;
    public final Set zac;
    public final View zaf;
    public final String zag;
    public final String zah;
    public final SignInOptions zai;
    public Integer zaj;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public Account zaa;
        public ArraySet zab;
        public String zac;
        public String zad;
    }

    public ClientSettings(Account account, Set set, String str, String str2) {
        SignInOptions signInOptions = SignInOptions.zaa;
        this.zaa = account;
        Set emptySet = set == null ? Collections.emptySet() : Collections.unmodifiableSet(set);
        this.zab = emptySet;
        Map emptyMap = Collections.emptyMap();
        this.zag = str;
        this.zah = str2;
        this.zai = signInOptions;
        HashSet hashSet = new HashSet(emptySet);
        Iterator it = emptyMap.values().iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        this.zac = Collections.unmodifiableSet(hashSet);
    }
}
