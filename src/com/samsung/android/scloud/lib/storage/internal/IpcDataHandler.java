package com.samsung.android.scloud.lib.storage.internal;

import android.os.ParcelFileDescriptor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class IpcDataHandler {
    public static boolean write(ParcelFileDescriptor parcelFileDescriptor, Object obj) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.close();
                    fileOutputStream.close();
                    return true;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
