package com.android.settingslib.datastore;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settingslib/datastore/EntityBackupResult;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class EntityBackupResult {
    public static final /* synthetic */ EntityBackupResult[] $VALUES;
    public static final EntityBackupResult DELETE;
    public static final EntityBackupResult UPDATE;

    static {
        EntityBackupResult entityBackupResult = new EntityBackupResult("UPDATE", 0);
        UPDATE = entityBackupResult;
        EntityBackupResult entityBackupResult2 = new EntityBackupResult("INTACT", 1);
        EntityBackupResult entityBackupResult3 = new EntityBackupResult("DELETE", 2);
        DELETE = entityBackupResult3;
        EntityBackupResult[] entityBackupResultArr = {
            entityBackupResult, entityBackupResult2, entityBackupResult3
        };
        $VALUES = entityBackupResultArr;
        EnumEntriesKt.enumEntries(entityBackupResultArr);
    }

    public static EntityBackupResult valueOf(String str) {
        return (EntityBackupResult) Enum.valueOf(EntityBackupResult.class, str);
    }

    public static EntityBackupResult[] values() {
        return (EntityBackupResult[]) $VALUES.clone();
    }
}
