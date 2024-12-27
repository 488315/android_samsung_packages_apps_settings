package com.google.common.base;

import androidx.preference.Preference;

import java.io.IOException;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Splitter {
    public final AnonymousClass1 strategy;
    public final CharMatcher trimmer = CharMatcher.None.INSTANCE;
    public final int limit = Preference.DEFAULT_ORDER;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.common.base.Splitter$5, reason: invalid class name */
    public final class AnonymousClass5 implements Iterable {
        public final /* synthetic */ CharSequence val$sequence;

        public AnonymousClass5(CharSequence charSequence) {
            this.val$sequence = charSequence;
        }

        @Override // java.lang.Iterable
        public final Iterator iterator() {
            Splitter splitter = Splitter.this;
            CharSequence charSequence = this.val$sequence;
            AnonymousClass1 anonymousClass1 = splitter.strategy;
            anonymousClass1.getClass();
            return anonymousClass1.new C00501(splitter, charSequence);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Iterator it = iterator();
            try {
                if (it.hasNext()) {
                    Object next = it.next();
                    java.util.Objects.requireNonNull(next);
                    sb.append(next instanceof CharSequence ? (CharSequence) next : next.toString());
                    while (it.hasNext()) {
                        sb.append((CharSequence) ", ");
                        Object next2 = it.next();
                        java.util.Objects.requireNonNull(next2);
                        sb.append(
                                next2 instanceof CharSequence
                                        ? (CharSequence) next2
                                        : next2.toString());
                    }
                }
                sb.append(']');
                return sb.toString();
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public Splitter(AnonymousClass1 anonymousClass1) {
        this.strategy = anonymousClass1;
    }
}
