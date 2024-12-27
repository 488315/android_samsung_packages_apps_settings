package com.samsung.android.scloud.lib.storage.internal;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CommonClientManager extends IClientHelper {
    public final Map serviceHandlerMap = new HashMap();

    public static Bundle getFileDescriptor(Bundle bundle, String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        Bundle m =
                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                        "[", str, "] getFileDescriptor", "CommonClientManager");
        String string = bundle.getString("path");
        if (string != null) {
            LOG.i("CommonClientManager", "[" + str + "] RESTORE_FILE: path: " + string);
            LOG.i("FileTool", "openFile !!  path : ".concat(string));
            String[] split = string.split("/");
            LOG.i("FileTool", "filename !!  uri : " + split[split.length - 1]);
            File file = new File(string);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try {
                parcelFileDescriptor = ParcelFileDescriptor.open(file, 939524096);
            } catch (FileNotFoundException e) {
                LOG.e("FileTool", "Unable to open file ".concat(string), e);
                parcelFileDescriptor = null;
            }
            m.putParcelable("file_descriptor", parcelFileDescriptor);
            m.putBoolean("is_success", true);
        }
        return m;
    }

    public static Bundle getResult(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_success", z);
        return bundle;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final IServiceHandler getServiceHandler(String str) {
        LOG.i("CommonClientManager", "getServiceHandler " + this.serviceHandlerMap);
        return (IServiceHandler) ((HashMap) this.serviceHandlerMap).get(str);
    }
}
