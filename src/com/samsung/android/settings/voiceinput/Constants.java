package com.samsung.android.settings.voiceinput;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Constants {
    public static final String DICTATION_LANGPACK_REQUEST_TIME = "dictation_langpack_request_time";
    public static final String GALAXY_STORE_PACKAGE_NAME = "com.sec.android.app.samsungapps";
    public static final String IS_FIRST_FETCH_FROM_SERVER = "is_first_fetch_from_server";
    public static final String IS_OFFLINE = "is_offline";
    public static final String KEY_HIDE_OFFENSIVE_WORDS = "hide_offensive_word";
    public static final int RESULT_CODE_NEED_UPDATE = 2;
    public static final String SCS_PACKAGE_NAME = "com.samsung.android.scs";
    public static final Integer SCS_STABLE_VERSION = 140021200;
    public static final Integer SCS_LANGPKG_CHANGE = 140036200;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class DownloadStatus {
        private static final /* synthetic */ DownloadStatus[] $VALUES;
        public static final DownloadStatus DOWNLOADABLE;
        public static final DownloadStatus DOWNLOADED;
        public static final DownloadStatus NOTAVAILABLE;
        public static final DownloadStatus UPADATEABLE;
        public static final DownloadStatus[] values;

        static {
            DownloadStatus downloadStatus = new DownloadStatus("NOTAVAILABLE", 0);
            NOTAVAILABLE = downloadStatus;
            DownloadStatus downloadStatus2 = new DownloadStatus("DOWNLOADABLE", 1);
            DOWNLOADABLE = downloadStatus2;
            DownloadStatus downloadStatus3 = new DownloadStatus("UPADATEABLE", 2);
            UPADATEABLE = downloadStatus3;
            DownloadStatus downloadStatus4 = new DownloadStatus("DOWNLOADED", 3);
            DOWNLOADED = downloadStatus4;
            $VALUES =
                    new DownloadStatus[] {
                        downloadStatus, downloadStatus2, downloadStatus3, downloadStatus4
                    };
            values = values();
        }

        public static DownloadStatus valueOf(String str) {
            return (DownloadStatus) Enum.valueOf(DownloadStatus.class, str);
        }

        public static DownloadStatus[] values() {
            return (DownloadStatus[]) $VALUES.clone();
        }
    }
}
