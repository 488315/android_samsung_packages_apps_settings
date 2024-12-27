package com.google.android.gms.common.internal;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Objects {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ToStringHelper {
        public final List zza;
        public final Object zzb;

        public /* synthetic */ ToStringHelper(Object obj) {
            Preconditions.checkNotNull(obj);
            this.zzb = obj;
            this.zza = new ArrayList();
        }

        public final void add(Object obj, String str) {
            List list = this.zza;
            String valueOf = String.valueOf(obj);
            ArrayList arrayList = (ArrayList) list;
            arrayList.add(
                    BackStackRecord$$ExternalSyntheticOutline0.m(
                            new StringBuilder(str.length() + 1 + valueOf.length()),
                            str,
                            "=",
                            valueOf));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(100);
            sb.append(this.zzb.getClass().getSimpleName());
            sb.append('{');
            int size = ((ArrayList) this.zza).size();
            for (int i = 0; i < size; i++) {
                sb.append((String) ((ArrayList) this.zza).get(i));
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }

    public static boolean equal(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }
}
