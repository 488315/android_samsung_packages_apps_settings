package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppUsageEvent extends GeneratedMessageLite {
    private static final AppUsageEvent DEFAULT_INSTANCE;
    public static final int INSTANCE_ID_FIELD_NUMBER = 4;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int TASK_ROOT_PACKAGE_NAME_FIELD_NUMBER = 5;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    public static final int TYPE_FIELD_NUMBER = 2;
    public static final int UID_FIELD_NUMBER = 7;
    public static final int USER_ID_FIELD_NUMBER = 6;
    private int bitField0_;
    private int instanceId_;
    private String packageName_ = ApnSettings.MVNO_NONE;
    private String taskRootPackageName_ = ApnSettings.MVNO_NONE;
    private long timestamp_;
    private int type_;
    private long uid_;
    private long userId_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetInstanceId, reason: not valid java name */
    public static void m879$$Nest$msetInstanceId(AppUsageEvent appUsageEvent, int i) {
        appUsageEvent.bitField0_ |= 8;
        appUsageEvent.instanceId_ = i;
    }

    /* renamed from: -$$Nest$msetPackageName, reason: not valid java name */
    public static void m880$$Nest$msetPackageName(AppUsageEvent appUsageEvent, String str) {
        appUsageEvent.getClass();
        str.getClass();
        appUsageEvent.bitField0_ |= 4;
        appUsageEvent.packageName_ = str;
    }

    /* renamed from: -$$Nest$msetTaskRootPackageName, reason: not valid java name */
    public static void m881$$Nest$msetTaskRootPackageName(AppUsageEvent appUsageEvent, String str) {
        appUsageEvent.getClass();
        str.getClass();
        appUsageEvent.bitField0_ |= 16;
        appUsageEvent.taskRootPackageName_ = str;
    }

    /* renamed from: -$$Nest$msetTimestamp, reason: not valid java name */
    public static void m882$$Nest$msetTimestamp(AppUsageEvent appUsageEvent, long j) {
        appUsageEvent.bitField0_ |= 1;
        appUsageEvent.timestamp_ = j;
    }

    /* renamed from: -$$Nest$msetType, reason: not valid java name */
    public static void m883$$Nest$msetType(
            AppUsageEvent appUsageEvent, AppUsageEventType appUsageEventType) {
        appUsageEvent.getClass();
        appUsageEvent.type_ = appUsageEventType.getNumber();
        appUsageEvent.bitField0_ |= 2;
    }

    /* renamed from: -$$Nest$msetUid, reason: not valid java name */
    public static void m884$$Nest$msetUid(AppUsageEvent appUsageEvent, long j) {
        appUsageEvent.bitField0_ |= 64;
        appUsageEvent.uid_ = j;
    }

    /* renamed from: -$$Nest$msetUserId, reason: not valid java name */
    public static void m885$$Nest$msetUserId(AppUsageEvent appUsageEvent, long j) {
        appUsageEvent.bitField0_ |= 32;
        appUsageEvent.userId_ = j;
    }

    static {
        AppUsageEvent appUsageEvent = new AppUsageEvent();
        DEFAULT_INSTANCE = appUsageEvent;
        GeneratedMessageLite.registerDefaultInstance(AppUsageEvent.class, appUsageEvent);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(
            GeneratedMessageLite.MethodToInvoke methodToInvoke,
            GeneratedMessageLite generatedMessageLite) {
        switch (methodToInvoke.ordinal()) {
            case 0:
                return (byte) 1;
            case 1:
                return null;
            case 2:
                return new RawMessageInfo(
                        DEFAULT_INSTANCE,
                        "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဌ\u0001\u0003ဈ\u0002\u0004င\u0003\u0005ဈ\u0004\u0006ဂ\u0005\u0007ဂ\u0006",
                        new Object[] {
                            "bitField0_",
                            "timestamp_",
                            "type_",
                            AppUsageEventType.AppUsageEventTypeVerifier.INSTANCE,
                            "packageName_",
                            "instanceId_",
                            "taskRootPackageName_",
                            "userId_",
                            "uid_"
                        });
            case 3:
                return new AppUsageEvent();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (AppUsageEvent.class) {
                        try {
                            parser = PARSER;
                            if (parser == null) {
                                parser =
                                        new GeneratedMessageLite.DefaultInstanceBasedParser(
                                                DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        } finally {
                        }
                    }
                }
                return parser;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final int getInstanceId() {
        return this.instanceId_;
    }

    public final String getPackageName() {
        return this.packageName_;
    }

    public final String getTaskRootPackageName() {
        return this.taskRootPackageName_;
    }

    public final long getTimestamp() {
        return this.timestamp_;
    }

    public final AppUsageEventType getType() {
        AppUsageEventType forNumber = AppUsageEventType.forNumber(this.type_);
        return forNumber == null ? AppUsageEventType.UNKNOWN : forNumber;
    }

    public final long getUserId() {
        return this.userId_;
    }
}
