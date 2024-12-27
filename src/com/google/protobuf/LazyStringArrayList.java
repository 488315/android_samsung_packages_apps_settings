package com.google.protobuf;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LazyStringArrayList extends AbstractProtobufList
        implements LazyStringList, RandomAccess {
    public final List list;

    static {
        new LazyStringArrayList(10).isMutable = false;
    }

    public LazyStringArrayList(int i) {
        this(new ArrayList(i));
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        ensureIsMutable();
        this.list.add(i, (String) obj);
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractCollection,
              // java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(this.list.size(), collection);
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList,
              // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        ensureIsMutable();
        this.list.clear();
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        String str;
        Object obj = this.list.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            byteString.getClass();
            Charset charset = Internal.UTF_8;
            if (byteString.size() == 0) {
                str = ApnSettings.MVNO_NONE;
            } else {
                ByteString.LiteralByteString literalByteString =
                        (ByteString.LiteralByteString) byteString;
                str =
                        new String(
                                literalByteString.bytes,
                                literalByteString.getOffsetIntoBytes(),
                                literalByteString.size(),
                                charset);
            }
            ByteString.LiteralByteString literalByteString2 =
                    (ByteString.LiteralByteString) byteString;
            int offsetIntoBytes = literalByteString2.getOffsetIntoBytes();
            if (Utf8.processor.isValidUtf8(
                    literalByteString2.bytes,
                    offsetIntoBytes,
                    literalByteString2.size() + offsetIntoBytes)) {
                this.list.set(i, str);
            }
        } else {
            byte[] bArr = (byte[]) obj;
            str = new String(bArr, Internal.UTF_8);
            if (Utf8.processor.isValidUtf8(bArr, 0, bArr.length)) {
                this.list.set(i, str);
            }
        }
        return str;
    }

    @Override // com.google.protobuf.LazyStringList
    public final Object getRaw(int i) {
        return this.list.get(i);
    }

    @Override // com.google.protobuf.LazyStringList
    public final List getUnderlyingElements() {
        return Collections.unmodifiableList(this.list);
    }

    @Override // com.google.protobuf.LazyStringList
    public final LazyStringList getUnmodifiableView() {
        return this.isMutable ? new UnmodifiableLazyStringList(this) : this;
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    public final Internal.ProtobufList mutableCopyWithCapacity(int i) {
        if (i < this.list.size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.list);
        return new LazyStringArrayList(arrayList);
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        String str;
        ensureIsMutable();
        Object remove = this.list.remove(i);
        ((AbstractList) this).modCount++;
        if (remove instanceof String) {
            return (String) remove;
        }
        if (remove instanceof ByteString) {
            ByteString byteString = (ByteString) remove;
            byteString.getClass();
            Charset charset = Internal.UTF_8;
            if (byteString.size() != 0) {
                ByteString.LiteralByteString literalByteString =
                        (ByteString.LiteralByteString) byteString;
                return new String(
                        literalByteString.bytes,
                        literalByteString.getOffsetIntoBytes(),
                        literalByteString.size(),
                        charset);
            }
            str = ApnSettings.MVNO_NONE;
        } else {
            str = new String((byte[]) remove, Internal.UTF_8);
        }
        return str;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        ensureIsMutable();
        Object obj2 = this.list.set(i, (String) obj);
        if (obj2 instanceof String) {
            return (String) obj2;
        }
        if (!(obj2 instanceof ByteString)) {
            return new String((byte[]) obj2, Internal.UTF_8);
        }
        ByteString byteString = (ByteString) obj2;
        byteString.getClass();
        Charset charset = Internal.UTF_8;
        if (byteString.size() == 0) {
            return ApnSettings.MVNO_NONE;
        }
        ByteString.LiteralByteString literalByteString = (ByteString.LiteralByteString) byteString;
        return new String(
                literalByteString.bytes,
                literalByteString.getOffsetIntoBytes(),
                literalByteString.size(),
                charset);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.list.size();
    }

    public LazyStringArrayList(ArrayList arrayList) {
        this.list = arrayList;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        ensureIsMutable();
        if (collection instanceof LazyStringList) {
            collection = ((LazyStringList) collection).getUnderlyingElements();
        }
        boolean addAll = this.list.addAll(i, collection);
        ((AbstractList) this).modCount++;
        return addAll;
    }
}
