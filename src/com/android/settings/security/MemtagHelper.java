package com.android.settings.security;

import android.os.SystemProperties;
import android.text.TextUtils;

import com.android.internal.os.Zygote;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MemtagHelper {
    public static int getSummary() {
        return TextUtils.equals(
                        "force_off",
                        SystemProperties.get(
                                "persist.device_config.runtime_native_boot.bootloader_override"))
                ? R.string.memtag_force_off
                : TextUtils.equals(
                                "force_on",
                                SystemProperties.get(
                                        "persist.device_config.runtime_native_boot.bootloader_override"))
                        ? R.string.memtag_force_on
                        : Zygote.nativeSupportsMemoryTagging()
                                ? isChecked() ? R.string.memtag_on : R.string.memtag_off_pending
                                : isChecked() ? R.string.memtag_on_pending : R.string.memtag_off;
    }

    public static boolean isChecked() {
        return Arrays.asList(
                        SystemProperties.get("arm64.memtag.bootctl", ApnSettings.MVNO_NONE)
                                .split(","))
                .contains("memtag");
    }
}
