package com.samsung.android.settings.analyzestorage.data.database.repository;

import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RepositoryFactory {
    public static Supplier sGeneratorSupplier;
    public static RepositoryFactoryImpl sRepositoryGenerator;

    public static RepositoryFactoryImpl getGenerator() {
        if (sRepositoryGenerator == null) {
            sRepositoryGenerator = (RepositoryFactoryImpl) sGeneratorSupplier.get();
        }
        return sRepositoryGenerator;
    }
}
