package com.samsung.android.settings.analyzestorage.domain.entity;

import android.util.SparseArray;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FileInfoFactory {
    public static FileInfoGenerator sDefaultGenerator;
    public static Supplier sDefaultGeneratorSupplier;
    public static final List sGeneratorSupplierList = new ArrayList();
    public static final SparseArray sGeneratorMap = new SparseArray();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Args {
        public final Object[] mArgs;
        public final int mArgsPattern = 2008;

        public Args(Object[] objArr) {
            this.mArgs = objArr;
        }

        public final String toString() {
            StringBuilder sb =
                    new StringBuilder(
                            BackEventCompat$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Args pattern("), this.mArgsPattern, ')'));
            Object[] objArr = this.mArgs;
            if (objArr != null && objArr.length > 0) {
                sb.append(':');
                for (Object obj : objArr) {
                    sb.append('[');
                    sb.append(obj);
                    sb.append(']');
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface FileInfoGenerator {
        void checkArgs(Args args);

        FileInfo create(Args args);

        int[] supportDomainType();
    }
}
