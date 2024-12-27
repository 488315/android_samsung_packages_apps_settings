package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000>\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u001c\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010!\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u001e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010(\n"
                + "\u0002\b\b\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001!B\u0005¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010"
                + "\tJ\u001b\u0010\u0007\u001a\u00020\n"
                + "2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\r"
                + "J \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010J\r"
                + "\u0010\u0011\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0012J\u0006\u0010\u0013\u001a\u00020\u0014J\u0015\u0010\u0015\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\u0018J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u001aH\u0096\u0002J\u0013\u0010\u001b\u001a\u00020\n"
                + "2\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0015\u0010\u001d\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\u0016J\u001d\u0010\u001e\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0002\u0010\u001fJ\u0006\u0010"
                + " \u001a\u00020\fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\""
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsListBuilder;",
            ImsProfile.TIMER_NAME_B,
            "T",
            ApnSettings.MVNO_NONE,
            "()V",
            "_list",
            ApnSettings.MVNO_NONE,
            "add",
            "builder",
            "(Ljava/lang/Object;)Lcom/samsung/android/gtscell/data/GtsListBuilder;",
            ApnSettings.MVNO_NONE,
            "index",
            ApnSettings.MVNO_NONE,
            "(ILjava/lang/Object;)Z",
            "addAll",
            "elements",
            ApnSettings.MVNO_NONE,
            "build",
            "()Ljava/lang/Object;",
            "clear",
            ApnSettings.MVNO_NONE,
            "getAt",
            "(I)Ljava/lang/Object;",
            "indexOf",
            "(Ljava/lang/Object;)I",
            "iterator",
            ApnSettings.MVNO_NONE,
            "remove",
            "(Ljava/lang/Object;)Z",
            "removeAt",
            "set",
            "(ILjava/lang/Object;)Ljava/lang/Object;",
            "size",
            "IteratorImpl",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public abstract class GtsListBuilder<B, T> implements Iterable<B>, KMappedMarker {
    private final List<B> _list = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u001c\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010(\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0005\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\b\u0003\b\u0092\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J"
                    + "\t\u0010\t\u001a\u00020\n"
                    + "H\u0096\u0002J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsListBuilder$IteratorImpl;",
                ApnSettings.MVNO_NONE,
                "(Lcom/samsung/android/gtscell/data/GtsListBuilder;)V",
                "index",
                ApnSettings.MVNO_NONE,
                "getIndex",
                "()I",
                "setIndex",
                "(I)V",
                "hasNext",
                ApnSettings.MVNO_NONE,
                "next",
                "()Ljava/lang/Object;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public class IteratorImpl implements Iterator<B>, KMappedMarker {
        private int index;

        public IteratorImpl() {}

        public final int getIndex() {
            return this.index;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < GtsListBuilder.this.size();
        }

        @Override // java.util.Iterator
        public B next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            GtsListBuilder gtsListBuilder = GtsListBuilder.this;
            int i = this.index;
            this.index = i + 1;
            B b = (B) gtsListBuilder.getAt(i);
            if (b != null) {
                return b;
            }
            Intrinsics.throwNpe();
            throw null;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException(
                    "Operation is not supported for read-only collection");
        }

        public final void setIndex(int i) {
            this.index = i;
        }
    }

    public final GtsListBuilder<B, T> add(B builder) {
        this._list.add(builder);
        return this;
    }

    public final GtsListBuilder<B, T> addAll(Collection<? extends B> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        this._list.addAll(elements);
        return this;
    }

    public abstract T build();

    public final void clear() {
        this._list.clear();
    }

    public final B getAt(int index) {
        if (index >= this._list.size() || index < 0) {
            return null;
        }
        return this._list.get(index);
    }

    public final int indexOf(B builder) {
        return this._list.indexOf(builder);
    }

    @Override // java.lang.Iterable
    public Iterator<B> iterator() {
        return new IteratorImpl();
    }

    public final boolean remove(B builder) {
        int indexOf = indexOf(builder);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    public final B removeAt(int index) {
        if (index >= this._list.size() || index < 0) {
            return null;
        }
        return this._list.remove(index);
    }

    public final B set(int index, B builder) {
        if (index >= this._list.size() || index < 0) {
            return null;
        }
        B b = this._list.get(index);
        this._list.set(index, builder);
        return b;
    }

    public final int size() {
        return this._list.size();
    }

    public final boolean add(int index, B builder) {
        if (index > this._list.size() || index < 0) {
            return false;
        }
        this._list.add(index, builder);
        return true;
    }
}
