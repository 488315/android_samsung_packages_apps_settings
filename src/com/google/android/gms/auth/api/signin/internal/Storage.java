package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.internal.Preconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Storage {
    public static final Lock zaa = new ReentrantLock();
    public static Storage zab;
    public final Lock zac = new ReentrantLock();
    public final SharedPreferences zad;

    public Storage(Context context) {
        this.zad = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static Storage getInstance(Context context) {
        Preconditions.checkNotNull(context);
        ReentrantLock reentrantLock = (ReentrantLock) zaa;
        reentrantLock.lock();
        try {
            if (zab == null) {
                zab = new Storage(context.getApplicationContext());
            }
            Storage storage = zab;
            reentrantLock.unlock();
            return storage;
        } catch (Throwable th) {
            ((ReentrantLock) zaa).unlock();
            throw th;
        }
    }

    public final String zaa(String str) {
        ((ReentrantLock) this.zac).lock();
        try {
            return this.zad.getString(str, null);
        } finally {
            ((ReentrantLock) this.zac).unlock();
        }
    }
}
