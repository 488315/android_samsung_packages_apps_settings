package com.google.protobuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ListFieldSchema {
    public static final ListFieldSchemaFull FULL_INSTANCE = new ListFieldSchemaFull();
    public static final ListFieldSchemaLite LITE_INSTANCE = new ListFieldSchemaLite();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ListFieldSchemaFull extends ListFieldSchema {
        public static final Class UNMODIFIABLE_LIST_CLASS =
                Collections.unmodifiableList(Collections.emptyList()).getClass();

        @Override // com.google.protobuf.ListFieldSchema
        public final void makeImmutableListAt(long j, Object obj) {
            Object unmodifiableList;
            List list = (List) UnsafeUtil.getObject(j, obj);
            if (list instanceof LazyStringList) {
                unmodifiableList = ((LazyStringList) list).getUnmodifiableView();
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if ((list instanceof PrimitiveNonBoxingCollection)
                        && (list instanceof Internal.ProtobufList)) {
                    AbstractProtobufList abstractProtobufList =
                            (AbstractProtobufList) ((Internal.ProtobufList) list);
                    if (abstractProtobufList.isMutable) {
                        abstractProtobufList.isMutable = false;
                        return;
                    }
                    return;
                }
                unmodifiableList = Collections.unmodifiableList(list);
            }
            UnsafeUtil.putObject(j, obj, unmodifiableList);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.ListFieldSchema
        public final void mergeListsAt(long j, Object obj, Object obj2) {
            LazyStringArrayList lazyStringArrayList;
            List list = (List) UnsafeUtil.getObject(j, obj2);
            int size = list.size();
            List list2 = (List) UnsafeUtil.getObject(j, obj);
            if (list2.isEmpty()) {
                list2 =
                        list2 instanceof LazyStringList
                                ? new LazyStringArrayList(size)
                                : ((list2 instanceof PrimitiveNonBoxingCollection)
                                                && (list2 instanceof Internal.ProtobufList))
                                        ? ((Internal.ProtobufList) list2)
                                                .mutableCopyWithCapacity(size)
                                        : new ArrayList(size);
                UnsafeUtil.putObject(j, obj, list2);
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list2.getClass())) {
                    ArrayList arrayList = new ArrayList(list2.size() + size);
                    arrayList.addAll(list2);
                    UnsafeUtil.putObject(j, obj, arrayList);
                    lazyStringArrayList = arrayList;
                } else if (list2 instanceof UnmodifiableLazyStringList) {
                    LazyStringArrayList lazyStringArrayList2 =
                            new LazyStringArrayList(list2.size() + size);
                    lazyStringArrayList2.addAll((UnmodifiableLazyStringList) list2);
                    UnsafeUtil.putObject(j, obj, lazyStringArrayList2);
                    lazyStringArrayList = lazyStringArrayList2;
                } else if ((list2 instanceof PrimitiveNonBoxingCollection)
                        && (list2 instanceof Internal.ProtobufList)) {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list2;
                    if (!((AbstractProtobufList) protobufList).isMutable) {
                        list2 = protobufList.mutableCopyWithCapacity(list2.size() + size);
                        UnsafeUtil.putObject(j, obj, list2);
                    }
                }
                list2 = lazyStringArrayList;
            }
            int size2 = list2.size();
            int size3 = list.size();
            if (size2 > 0 && size3 > 0) {
                list2.addAll(list);
            }
            if (size2 > 0) {
                list = list2;
            }
            UnsafeUtil.putObject(j, obj, list);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ListFieldSchemaLite extends ListFieldSchema {
        @Override // com.google.protobuf.ListFieldSchema
        public final void makeImmutableListAt(long j, Object obj) {
            ((AbstractProtobufList) ((Internal.ProtobufList) UnsafeUtil.getObject(j, obj)))
                            .isMutable =
                    false;
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final void mergeListsAt(long j, Object obj, Object obj2) {
            Internal.ProtobufList protobufList =
                    (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
            Internal.ProtobufList protobufList2 =
                    (Internal.ProtobufList) UnsafeUtil.getObject(j, obj2);
            int size = protobufList.size();
            int size2 = protobufList2.size();
            if (size > 0 && size2 > 0) {
                if (!((AbstractProtobufList) protobufList).isMutable) {
                    protobufList = protobufList.mutableCopyWithCapacity(size2 + size);
                }
                protobufList.addAll(protobufList2);
            }
            if (size > 0) {
                protobufList2 = protobufList;
            }
            UnsafeUtil.putObject(j, obj, protobufList2);
        }
    }

    public abstract void makeImmutableListAt(long j, Object obj);

    public abstract void mergeListsAt(long j, Object obj, Object obj2);
}
