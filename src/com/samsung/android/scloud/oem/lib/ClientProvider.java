package com.samsung.android.scloud.oem.lib;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.samsung.android.scloud.lib.storage.internal.BackupClientManager;
import com.samsung.android.scloud.lib.storage.internal.SyncClientManager;
import com.samsung.android.scloud.oem.lib.backup.ReuseDBHelper;
import com.samsung.android.scloud.oem.lib.backup.file.FileClientManager;
import com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager;
import com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper;
import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient;
import com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper;
import com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager;
import com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager;
import com.samsung.android.settings.scloud.SCloudWifiConfigSyncImpl;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ClientProvider extends ContentProvider {
    public Context context;
    public static final Map REGISTER_MAP = new HashMap();
    public static final Object LOCK = new Object();
    public static final Map CLIENT_MAP = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.scloud.oem.lib.ClientProvider$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(int i) {
            this.$r8$classId = i;
        }

        private final void execute$com$samsung$android$scloud$oem$lib$ClientProvider$3(
                Context context, XmlResourceParser xmlResourceParser) {
            try {
                String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                String attributeValue2 =
                        xmlResourceParser.getAttributeValue(null, "client_impl_class");
                String attributeValue3 =
                        xmlResourceParser.getAttributeValue(null, "client_data_directory");
                Map map = ClientProvider.REGISTER_MAP;
                LOG.i(
                        "ClientProvider",
                        "register - xml5 : "
                                + attributeValue
                                + ", "
                                + attributeValue2
                                + ", clientDataDirectory : "
                                + attributeValue3);
                try {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                            Class.forName(attributeValue2).newInstance());
                    RecordClientManager recordClientManager = new RecordClientManager();
                    recordClientManager.dataDirectory = attributeValue3;
                    ((HashMap) ClientProvider.CLIENT_MAP)
                            .put("record_" + attributeValue, recordClientManager);
                } catch (ClassCastException e) {
                    Map map2 = ClientProvider.REGISTER_MAP;
                    LOG.e("ClientProvider", "failed cast to BNRClient~!! ", e);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e2) {
                e2.printStackTrace();
            }
        }

        private final void execute$com$samsung$android$scloud$oem$lib$ClientProvider$4(
                Context context, XmlResourceParser xmlResourceParser) {
            try {
                String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                String attributeValue2 =
                        xmlResourceParser.getAttributeValue(null, "client_impl_class");
                Map map = ClientProvider.REGISTER_MAP;
                LOG.i(
                        "ClientProvider",
                        "register - xml5 : " + attributeValue + ", " + attributeValue2);
                try {
                    LOG.i(
                            "ClientProvider",
                            "register - xml7 has_file : "
                                    + attributeValue
                                    + ", "
                                    + attributeValue2);
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                            Class.forName(attributeValue2).newInstance());
                    ((HashMap) ClientProvider.CLIENT_MAP)
                            .put("file_" + attributeValue, new FileClientManager());
                } catch (ClassCastException e) {
                    Map map2 = ClientProvider.REGISTER_MAP;
                    LOG.e("ClientProvider", "failed cast to BNRClient~!! ", e);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e2) {
                e2.printStackTrace();
            }
        }

        private final void execute$com$samsung$android$scloud$oem$lib$ClientProvider$5(
                Context context, XmlResourceParser xmlResourceParser) {
            try {
                String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                String attributeValue2 =
                        xmlResourceParser.getAttributeValue(null, "client_impl_class");
                try {
                    Map map = ClientProvider.REGISTER_MAP;
                    LOG.d(
                            "ClientProvider",
                            "register - xml5 : " + attributeValue + ", v :" + attributeValue2);
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                            Class.forName(attributeValue2).newInstance());
                    ((HashMap) ClientProvider.CLIENT_MAP)
                            .put(attributeValue, new RecordSyncManager());
                } catch (ClassCastException e) {
                    Map map2 = ClientProvider.REGISTER_MAP;
                    LOG.e("ClientProvider", "failed cast to SyncClient ", e);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e2) {
                e2.printStackTrace();
            }
        }

        private final void execute$com$samsung$android$scloud$oem$lib$ClientProvider$6(
                Context context, XmlResourceParser xmlResourceParser) {
            try {
                String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                String attributeValue2 =
                        xmlResourceParser.getAttributeValue(null, "client_impl_class");
                try {
                    Map map = ClientProvider.REGISTER_MAP;
                    LOG.d(
                            "ClientProvider",
                            "register - xml5 : " + attributeValue + ", v :" + attributeValue2);
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                            Class.forName(attributeValue2).newInstance());
                    ((HashMap) ClientProvider.CLIENT_MAP)
                            .put("sync_" + attributeValue, new FileSyncManager());
                } catch (ClassCastException e) {
                    Map map2 = ClientProvider.REGISTER_MAP;
                    LOG.e("ClientProvider", "failed cast to SyncClient ", e);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e2) {
                e2.printStackTrace();
            }
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x01f3 -> B:40:0x01fc). Please report as a decompilation issue!!! */
        public final void execute(Context context, XmlResourceParser xmlResourceParser) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                        String attributeValue2 =
                                xmlResourceParser.getAttributeValue(null, "contents_id");
                        String attributeValue3 =
                                xmlResourceParser.getAttributeValue(null, "client_impl_class");
                        String attributeValue4 =
                                xmlResourceParser.getAttributeValue(null, "category");
                        Map map = ClientProvider.REGISTER_MAP;
                        LOG.d(
                                "ClientProvider",
                                "register - xml5 : "
                                        + attributeValue
                                        + ", "
                                        + attributeValue2
                                        + ", "
                                        + attributeValue3
                                        + ", "
                                        + attributeValue4);
                        try {
                            if ("true"
                                    .equals(
                                            xmlResourceParser.getAttributeValue(
                                                    null, "quick_backup"))) {
                                LOG.i(
                                        "ClientProvider",
                                        "register - xml6 quick_backup : "
                                                + attributeValue
                                                + ", "
                                                + attributeValue2
                                                + ", "
                                                + attributeValue3);
                                ((HashMap) ClientProvider.CLIENT_MAP)
                                        .put(
                                                attributeValue,
                                                new QBNRClientHelper(
                                                        (ISCloudQBNRClient)
                                                                Class.forName(attributeValue3)
                                                                        .newInstance()));
                            } else {
                                LOG.i(
                                        "ClientProvider",
                                        "register - xml6 : "
                                                + attributeValue
                                                + ", "
                                                + attributeValue2
                                                + ", "
                                                + attributeValue3);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                        Class.forName(attributeValue3).newInstance());
                                ((HashMap) ClientProvider.CLIENT_MAP)
                                        .put(attributeValue, new BNRClientHelper());
                            }
                        } catch (ClassCastException e) {
                            Map map2 = ClientProvider.REGISTER_MAP;
                            LOG.e("ClientProvider", "failed cast to BNRClient~!! ", e);
                        }
                        break;
                    } catch (ClassNotFoundException
                            | IllegalAccessException
                            | InstantiationException e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 1:
                    try {
                        String attributeValue5 = xmlResourceParser.getAttributeValue(null, "name");
                        String attributeValue6 =
                                xmlResourceParser.getAttributeValue(null, "contents_id");
                        String attributeValue7 =
                                xmlResourceParser.getAttributeValue(null, "client_impl_class");
                        String attributeValue8 =
                                xmlResourceParser.getAttributeValue(null, "category");
                        Map map3 = ClientProvider.REGISTER_MAP;
                        LOG.d(
                                "ClientProvider",
                                "register - xml5 : "
                                        + attributeValue5
                                        + ", "
                                        + attributeValue6
                                        + ", "
                                        + attributeValue7
                                        + ", "
                                        + attributeValue8);
                        try {
                            LOG.i(
                                    "ClientProvider",
                                    "register - xml6 quick_backup : "
                                            + attributeValue5
                                            + ", "
                                            + attributeValue6
                                            + ", "
                                            + attributeValue7);
                            ((HashMap) ClientProvider.CLIENT_MAP)
                                    .put(
                                            attributeValue5,
                                            new QBNRClientHelper(
                                                    (ISCloudQBNRClient)
                                                            Class.forName(attributeValue7)
                                                                    .newInstance()));
                        } catch (ClassCastException e3) {
                            Map map4 = ClientProvider.REGISTER_MAP;
                            LOG.e("ClientProvider", "failed cast to BNRClient~!! ", e3);
                        }
                        break;
                    } catch (ClassNotFoundException
                            | IllegalAccessException
                            | InstantiationException e4) {
                        e4.printStackTrace();
                        return;
                    }
                case 2:
                    execute$com$samsung$android$scloud$oem$lib$ClientProvider$3(
                            context, xmlResourceParser);
                    break;
                case 3:
                    execute$com$samsung$android$scloud$oem$lib$ClientProvider$4(
                            context, xmlResourceParser);
                    break;
                case 4:
                    execute$com$samsung$android$scloud$oem$lib$ClientProvider$5(
                            context, xmlResourceParser);
                    break;
                case 5:
                    execute$com$samsung$android$scloud$oem$lib$ClientProvider$6(
                            context, xmlResourceParser);
                    break;
                default:
                    try {
                        String attributeValue9 = xmlResourceParser.getAttributeValue(null, "type");
                        String attributeValue10 = xmlResourceParser.getAttributeValue(null, "name");
                        String attributeValue11 =
                                xmlResourceParser.getAttributeValue(null, "client_impl_class");
                        if ("sync".equals(attributeValue9)) {
                            Map map5 = ClientProvider.REGISTER_MAP;
                            LOG.i(
                                    "ClientProvider",
                                    "register - xml5 : "
                                            + attributeValue9
                                            + ", "
                                            + attributeValue10
                                            + ", "
                                            + attributeValue11);
                            try {
                                ((HashMap) ClientProvider.CLIENT_MAP)
                                        .put(
                                                attributeValue10,
                                                new SyncClientManager(
                                                        context,
                                                        (SCloudWifiConfigSyncImpl)
                                                                Class.forName(attributeValue11)
                                                                        .newInstance()));
                            } catch (ClassCastException e5) {
                                Map map6 = ClientProvider.REGISTER_MAP;
                                LOG.e(
                                        "ClientProvider",
                                        "failed cast SyncClientManager class to"
                                            + " IRecordDataClient~!! ",
                                        e5);
                            }
                        } else {
                            Map map7 = ClientProvider.REGISTER_MAP;
                            LOG.i(
                                    "ClientProvider",
                                    "register - xml5 : "
                                            + attributeValue9
                                            + ", "
                                            + attributeValue10
                                            + ", "
                                            + attributeValue11);
                            try {
                                ((HashMap) ClientProvider.CLIENT_MAP)
                                        .put(
                                                "multi_" + attributeValue10,
                                                new BackupClientManager(
                                                        context,
                                                        (SCloudWifiConfigSyncImpl)
                                                                Class.forName(attributeValue11)
                                                                        .newInstance()));
                            } catch (ClassCastException e6) {
                                Map map8 = ClientProvider.REGISTER_MAP;
                                LOG.e(
                                        "ClientProvider",
                                        "failed cast BackupClientManager class to"
                                            + " IRecordDataClient~!! ",
                                        e6);
                            }
                        }
                        break;
                    } catch (ClassNotFoundException
                            | IllegalAccessException
                            | InstantiationException e7) {
                        e7.printStackTrace();
                        return;
                    }
            }
        }
    }

    public static void register(Context context) {
        try {
            LOG.i("ClientProvider", "register");
            ApplicationInfo applicationInfo =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                LOG.i("ClientProvider", "failed to get ApplicationInfo with meta-data");
                throw new Exception("failed to get ApplicationInfo with meta-data");
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle == null) {
                LOG.i("ClientProvider", "<meta> tag is empty");
                throw new Exception("failed to get <meta> tag in Manifest.xml");
            }
            int i = 2;
            int i2 = 3;
            if (bundle.containsKey("backup_name") && bundle.containsKey("backup_content_uri")) {
                XmlResourceParser xml = context.getResources().getXml(bundle.getInt("backup_name"));
                try {
                    LOG.d("ClientProvider", "register - xml1 : " + xml.getName());
                    xml.next();
                    LOG.d("ClientProvider", "register - xml2 : " + xml.getName());
                    xml.next();
                    LOG.d("ClientProvider", "register - xml3 : " + xml.getName());
                    if (xml.getName().equals("backup_items")) {
                        while (true) {
                            if (xml.next() == i2 && xml.getName().equals("backup_items")) {
                                break;
                            }
                            LOG.d("ClientProvider", "register - xml4 : " + xml.getName());
                            if (xml.getName().equals("backup_item") && xml.getEventType() == i) {
                                String attributeValue = xml.getAttributeValue(null, "interface");
                                if (attributeValue == null) {
                                    try {
                                        ((AnonymousClass1)
                                                        ((HashMap) REGISTER_MAP)
                                                                .get("ISCloudBNRClient"))
                                                .execute(context, xml);
                                    } catch (Exception e) {
                                        LOG.e(
                                                "ClientProvider",
                                                "backup interfaceName is incorrect, "
                                                        + attributeValue,
                                                e);
                                    }
                                } else {
                                    ((AnonymousClass1) ((HashMap) REGISTER_MAP).get(attributeValue))
                                            .execute(context, xml);
                                }
                                i = 2;
                                i2 = 3;
                            }
                        }
                    }
                } catch (IOException | XmlPullParserException e2) {
                    e2.printStackTrace();
                }
            }
            if (!bundle.containsKey("scloud_support_authority")
                    && !bundle.containsKey("scloud_lib_provider_authority")) {
                return;
            }
            XmlResourceParser xml2 =
                    context.getResources()
                            .getXml(
                                    context.getResources()
                                            .getIdentifier(
                                                    "sync_item", "xml", context.getPackageName()));
            LOG.d("ClientProvider", "register - xml1 : " + xml2.getName());
            xml2.next();
            LOG.d("ClientProvider", "register - xml2 : " + xml2.getName());
            xml2.next();
            LOG.d("ClientProvider", "register - xml3 : " + xml2.getName());
            if (!xml2.getName().equals("sync_items")) {
                return;
            }
            while (true) {
                if (xml2.next() == 3 && xml2.getName().equals("sync_items")) {
                    return;
                }
                LOG.d("ClientProvider", "register - xml4 : " + xml2.getName());
                if (xml2.getName().equals("sync_item") && xml2.getEventType() == 2) {
                    String attributeValue2 = xml2.getAttributeValue(null, "interface");
                    LOG.d("ClientProvider", "register - interface : " + attributeValue2);
                    if (attributeValue2 == null) {
                        try {
                            ((AnonymousClass1) ((HashMap) REGISTER_MAP).get("IFileSyncClient"))
                                    .execute(context, xml2);
                        } catch (Exception e3) {
                            LOG.e(
                                    "ClientProvider",
                                    "sync interfaceName is incorrect, " + attributeValue2,
                                    e3);
                        }
                    } else {
                        ((AnonymousClass1) ((HashMap) REGISTER_MAP).get(attributeValue2))
                                .execute(context, xml2);
                    }
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        Map map;
        LOG.i("ClientProvider", "call: version: 2.4.0.5, method: " + str + ", arg: " + str2);
        try {
            synchronized (LOCK) {
                try {
                    map = CLIENT_MAP;
                    if (((HashMap) map).get(str2) == null) {
                        register(this.context);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            LOG.i("ClientProvider", "CLIENT_MAP " + map);
            return ((IClientHelper) ((HashMap) map).get(str2))
                    .handleRequest(this.context, str, str2, bundle);
        } catch (Exception e) {
            LOG.e(
                    "ClientProvider",
                    MotionLayout$$ExternalSyntheticOutline0.m(
                            "[content:", str2, "][", str, "][STORAGE] Application Exception error"),
                    e);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("exception", e);
            return bundle2;
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.context = getContext();
        HashMap hashMap = (HashMap) REGISTER_MAP;
        hashMap.put("ISCloudBNRClient", new AnonymousClass1(0));
        hashMap.put("ISCloudQBNRClient", new AnonymousClass1(1));
        hashMap.put("IRecordClient", new AnonymousClass1(2));
        hashMap.put("IFileClient", new AnonymousClass1(3));
        hashMap.put("IRecordSyncClient", new AnonymousClass1(4));
        hashMap.put("IFileSyncClient", new AnonymousClass1(5));
        hashMap.put("IRecordDataClient", new AnonymousClass1(6));
        return true;
    }

    @Override // android.content.ContentProvider
    public final ParcelFileDescriptor openFile(Uri uri, String str) {
        LOG.i("ClientProvider", "openFile: mode: " + str);
        Uri.Builder buildUpon = uri.buildUpon();
        String path = uri.getPath();
        if (buildUpon != null
                && buildUpon.build().getQueryParameter("encode") != null
                && buildUpon.build().getQueryParameter("encode").equals("path")) {
            path = buildUpon.build().getEncodedPath();
        }
        LOG.d("ClientProvider", "openFile: uri: " + path);
        String[] split = path.split("/");
        String str2 = split[split.length - 1];
        if (path.lastIndexOf("/") < 1) {
            path = this.context.getFilesDir() + path;
        }
        File file = new File(path);
        if (str == null || !str.equals("restore")) {
            if (TextUtils.isEmpty(str2)) {
                throw new UnsupportedOperationException();
            }
        } else if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "openFile: real path: ", path, ", fileExist: ");
        m.append(file.exists());
        LOG.d("ClientProvider", m.toString());
        try {
            return "backup".equals(str)
                    ? ParcelFileDescriptor.open(new File(path), 268435456)
                    : "sync".equals(str)
                            ? ParcelFileDescriptor.open(new File(path), 805306368)
                            : ParcelFileDescriptor.open(new File(path), 939524096);
        } catch (FileNotFoundException unused) {
            LOG.e("ClientProvider", "openFile: Unable to open file: " + path, null);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String queryParameter = uri.getQueryParameter("command");
        if (TextUtils.isEmpty(queryParameter)) {
            LOG.i(
                    "ClientProvider",
                    String.format(Locale.US, "query but command is null skip!! [%s]", uri));
            return null;
        }
        queryParameter.getClass();
        if (!queryParameter.equals("checkAndUpdateReuseDB")) {
            return null;
        }
        ((IClientHelper) ((HashMap) CLIENT_MAP).get(str))
                .handleRequest(this.context, queryParameter, str, null);
        return ReuseDBHelper.getInstance(this.context)
                .getReadableDatabase()
                .query("reuse_files", null, "sourcekey = ?", new String[] {str}, null, null, null);
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
