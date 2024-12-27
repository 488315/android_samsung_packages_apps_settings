package com.google.protobuf;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UnsafeUtil {
    public static final long BYTE_ARRAY_BASE_OFFSET;
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    public static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    public static final boolean IS_BIG_ENDIAN;
    public static final Android32MemoryAccessor MEMORY_ACCESSOR;
    public static final Class MEMORY_CLASS;
    public static final Unsafe UNSAFE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.protobuf.UnsafeUtil$1, reason: invalid class name */
    public final class AnonymousClass1 implements PrivilegedExceptionAction {
        @Override // java.security.PrivilegedExceptionAction
        public final Object run() {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (Unsafe.class.isInstance(obj)) {
                    return (Unsafe) Unsafe.class.cast(obj);
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Android32MemoryAccessor {
        public final /* synthetic */ int $r8$classId;
        public final Unsafe unsafe;

        public Android32MemoryAccessor(Unsafe unsafe, int i) {
            this.$r8$classId = i;
            this.unsafe = unsafe;
        }

        public final boolean getBoolean(long j, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    if (UnsafeUtil.IS_BIG_ENDIAN) {
                        if (UnsafeUtil.getByteBigEndian(j, obj) == 0) {}
                    } else if (UnsafeUtil.getByteLittleEndian(j, obj) == 0) {
                    }
                    break;
                default:
                    if (UnsafeUtil.IS_BIG_ENDIAN) {
                        if (UnsafeUtil.getByteBigEndian(j, obj) == 0) {}
                    } else if (UnsafeUtil.getByteLittleEndian(j, obj) == 0) {
                    }
                    break;
            }
            return false;
        }

        public final double getDouble(long j, Object obj) {
            switch (this.$r8$classId) {
            }
            return Double.longBitsToDouble(this.unsafe.getLong(obj, j));
        }

        public final float getFloat(long j, Object obj) {
            switch (this.$r8$classId) {
            }
            return Float.intBitsToFloat(this.unsafe.getInt(obj, j));
        }

        public final void putBoolean(Object obj, long j, boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    if (!UnsafeUtil.IS_BIG_ENDIAN) {
                        UnsafeUtil.putByteLittleEndian(obj, j, z ? (byte) 1 : (byte) 0);
                        break;
                    } else {
                        UnsafeUtil.putByteBigEndian(obj, j, z ? (byte) 1 : (byte) 0);
                        break;
                    }
                default:
                    if (!UnsafeUtil.IS_BIG_ENDIAN) {
                        UnsafeUtil.putByteLittleEndian(obj, j, z ? (byte) 1 : (byte) 0);
                        break;
                    } else {
                        UnsafeUtil.putByteBigEndian(obj, j, z ? (byte) 1 : (byte) 0);
                        break;
                    }
            }
        }

        public final void putDouble(Object obj, long j, double d) {
            switch (this.$r8$classId) {
                case 0:
                    this.unsafe.putLong(obj, j, Double.doubleToLongBits(d));
                    break;
                default:
                    this.unsafe.putLong(obj, j, Double.doubleToLongBits(d));
                    break;
            }
        }

        public final void putFloat(Object obj, long j, float f) {
            switch (this.$r8$classId) {
                case 0:
                    this.unsafe.putInt(obj, j, Float.floatToIntBits(f));
                    break;
                default:
                    this.unsafe.putInt(obj, j, Float.floatToIntBits(f));
                    break;
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:0|1|2|3|4|(15:(1:48)(1:(1:50))|7|(2:9|10)|11|(12:41|42|43|14|15|16|(5:18|19|20|(1:24)|25)|(1:30)|31|(1:33)|34|35)|13|14|15|16|(0)|(1:30)|31|(0)|34|35)|6|7|(0)|11|(13:39|41|42|43|14|15|16|(0)|(0)|31|(0)|34|35)|13|14|15|16|(0)|(0)|31|(0)|34|35) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x010e, code lost:

       r3 = null;
    */
    /* JADX WARN: Failed to find 'out' block for switch in B:10:0x003c. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x012a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
    static {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.protobuf.UnsafeUtil.<clinit>():void");
    }

    public static void access$000(Throwable th) {
        Logger.getLogger(UnsafeUtil.class.getName())
                .log(
                        Level.WARNING,
                        "platform method missing - proto runtime falling back to safer methods: "
                                + th);
    }

    public static Object allocateInstance(Class cls) {
        try {
            return UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int arrayBaseOffset(Class cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.unsafe.arrayBaseOffset(cls);
        }
        return -1;
    }

    public static void arrayIndexScale(Class cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            MEMORY_ACCESSOR.unsafe.arrayIndexScale(cls);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean determineAndroidSupportByAddressSize(Class cls) {
        Class cls2 = Android.MEMORY_CLASS;
        try {
            Class cls3 = MEMORY_CLASS;
            Class cls4 = Boolean.TYPE;
            cls3.getMethod("peekLong", cls, cls4);
            cls3.getMethod("pokeLong", cls, Long.TYPE, cls4);
            Class cls5 = Integer.TYPE;
            cls3.getMethod("pokeInt", cls, cls5, cls4);
            cls3.getMethod("peekInt", cls, cls4);
            cls3.getMethod("pokeByte", cls, Byte.TYPE);
            cls3.getMethod("peekByte", cls);
            cls3.getMethod("pokeByteArray", cls, byte[].class, cls5, cls5);
            cls3.getMethod("peekByteArray", cls, byte[].class, cls5, cls5);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static byte getByteBigEndian(long j, Object obj) {
        return (byte) ((getInt((-4) & j, obj) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    public static byte getByteLittleEndian(long j, Object obj) {
        return (byte) ((getInt((-4) & j, obj) >>> ((int) ((j & 3) << 3))) & 255);
    }

    public static int getInt(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getInt(obj, j);
    }

    public static long getLong(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getLong(obj, j);
    }

    public static Object getObject(long j, Object obj) {
        return MEMORY_ACCESSOR.unsafe.getObject(obj, j);
    }

    public static void putByte(byte[] bArr, long j, byte b) {
        long j2 = BYTE_ARRAY_BASE_OFFSET + j;
        switch (MEMORY_ACCESSOR.$r8$classId) {
            case 0:
                if (!IS_BIG_ENDIAN) {
                    putByteLittleEndian(bArr, j2, b);
                    break;
                } else {
                    putByteBigEndian(bArr, j2, b);
                    break;
                }
            default:
                if (!IS_BIG_ENDIAN) {
                    putByteLittleEndian(bArr, j2, b);
                    break;
                } else {
                    putByteBigEndian(bArr, j2, b);
                    break;
                }
        }
    }

    public static void putByteBigEndian(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = getInt(j2, obj);
        int i2 = ((~((int) j)) & 3) << 3;
        putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    public static void putByteLittleEndian(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        putInt(obj, j2, ((255 & b) << i) | (getInt(j2, obj) & (~(255 << i))));
    }

    public static void putInt(Object obj, long j, int i) {
        MEMORY_ACCESSOR.unsafe.putInt(obj, j, i);
    }

    public static void putObject(long j, Object obj, Object obj2) {
        MEMORY_ACCESSOR.unsafe.putObject(obj, j, obj2);
    }
}
