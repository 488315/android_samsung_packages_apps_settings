package com.samsung.android.settings.analyzestorage.external.database.datasource;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MediaProviderDataSource {
    public static final Uri MEDIA_PROVIDER_URI = MediaStore.Files.getContentUri("external");
    public final Context mContext;

    public MediaProviderDataSource(Context context) {
        this.mContext = context;
    }
}
