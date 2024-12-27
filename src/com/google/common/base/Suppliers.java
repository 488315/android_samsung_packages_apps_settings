package com.google.common.base;

import java.io.Serializable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Suppliers {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class MemoizingSupplier<T> implements Supplier, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier delegate;
        public transient volatile boolean initialized;
        public transient Object value;

        public MemoizingSupplier(Supplier supplier) {
            this.delegate = supplier;
        }

        @Override // com.google.common.base.Supplier
        public final Object get() {
            if (!this.initialized) {
                synchronized (this) {
                    try {
                        if (!this.initialized) {
                            Object obj = this.delegate.get();
                            this.value = obj;
                            this.initialized = true;
                            return obj;
                        }
                    } finally {
                    }
                }
            }
            return this.value;
        }

        public final String toString() {
            Object obj;
            StringBuilder sb = new StringBuilder("Suppliers.memoize(");
            if (this.initialized) {
                obj = "<supplier that returned " + this.value + ">";
            } else {
                obj = this.delegate;
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NonSerializableMemoizingSupplier implements Supplier {
        public static final Suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0
                SUCCESSFULLY_COMPUTED =
                        new Suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0();
        public volatile Supplier delegate;
        public Object value;

        @Override // com.google.common.base.Supplier
        public final Object get() {
            Supplier supplier = this.delegate;
            Suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0
                    suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0 =
                            SUCCESSFULLY_COMPUTED;
            if (supplier != suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0) {
                synchronized (this) {
                    try {
                        if (this.delegate
                                != suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0) {
                            Object obj = this.delegate.get();
                            this.value = obj;
                            this.delegate =
                                    suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0;
                            return obj;
                        }
                    } finally {
                    }
                }
            }
            return this.value;
        }

        public final String toString() {
            Object obj = this.delegate;
            StringBuilder sb = new StringBuilder("Suppliers.memoize(");
            if (obj == SUCCESSFULLY_COMPUTED) {
                obj = "<supplier that returned " + this.value + ">";
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    public static Supplier memoize(Supplier supplier) {
        if ((supplier instanceof NonSerializableMemoizingSupplier)
                || (supplier instanceof MemoizingSupplier)) {
            return supplier;
        }
        if (supplier instanceof Serializable) {
            return new MemoizingSupplier(supplier);
        }
        NonSerializableMemoizingSupplier nonSerializableMemoizingSupplier =
                new NonSerializableMemoizingSupplier();
        nonSerializableMemoizingSupplier.delegate = supplier;
        return nonSerializableMemoizingSupplier;
    }
}
