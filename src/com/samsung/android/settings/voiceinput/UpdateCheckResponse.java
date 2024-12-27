package com.samsung.android.settings.voiceinput;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Root(name = "result", strict = false)
/* loaded from: classes3.dex */
public class UpdateCheckResponse {

    @Element public int contentSize;

    @Element public int resultCode;

    @Element public int versionCode;
}
