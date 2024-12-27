package com.samsung.android.settings.languagepack.appsstub;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class XmlParser {
    public static final String[] APPID_TAG = {"<appId>", "</appId>"};
    public static final String[] RETCODE_TAG = {"<resultCode>", "</resultCode>"};
    public static final String[] RETMSG_TAG = {"<resultMsg>", "</resultMsg>"};
    public static final String[] VERCODE_TAG = {"<versionCode>", "</versionCode>"};
    public static final String[] VERNAME_TAG = {"<versionName>", "</versionName>"};
    public static final String[] DOWNLOADURI_TAG = {"<downloadURI><![CDATA[", "]]></downloadURI>"};
    public static final String[] CONTENTSIZE_TAG = {"<contentSize>", "</contentSize>"};
    public static final String[] DELTA_DOWNLOADURI_TAG = {
        "<deltaDownloadURI><![CDATA[", "]]></deltaDownloadURI>"
    };
    public static final String[] DELTA_CONTENTSIZE_TAG = {
        "<deltaContentSize>", "</deltaContentSize>"
    };
    public static final String[] INSTALLED_BINARY_HASH_VALUE_TAG = {
        "<installedBinaryHashValue>", "</installedBinaryHashValue>"
    };
    public static final String[] UPDATE_BINARY_HASH_VALUE_TAG = {
        "<updateBinaryHashValue>", "</updateBinaryHashValue>"
    };
    public static final String[] PRODUCTID_TAG = {"<productId>", "</productId>"};
    public static final String[] PRODUCTNAME_TAG = {"<productName><![CDATA[", "]]></productName>"};
    public static final String[] SIGNATURE_TAG = {"<signature>", "</signature>"};

    public static String getParsedString(String str, String str2, String str3) {
        int length = str2.length() + str.indexOf(str2);
        int indexOf = str.indexOf(str3);
        return (length < 0 || length >= str.length() || indexOf < 0 || indexOf >= str.length())
                ? ApnSettings.MVNO_NONE
                : str.substring(length, indexOf);
    }
}
