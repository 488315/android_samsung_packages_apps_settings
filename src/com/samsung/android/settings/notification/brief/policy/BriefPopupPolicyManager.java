package com.samsung.android.settings.notification.brief.policy;

import android.net.Uri;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BriefPopupPolicyManager {
    public static BriefPopupPolicyManager mInstance;
    public SparseArray mPolicyInfoData;
    public int mPolicyType;
    public long mPolicyVersion;

    static {
        Uri.withAppendedPath(PolicyClientContract.PolicyItems.CONTENT_URI, "EdgeLighting");
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Not initialized variable reg: 10, insn: 0x0049: MOVE (r7 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY]) (LINE:74), block:B:382:0x0048 */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x039c A[Catch: IOException -> 0x0352, TRY_ENTER, TRY_LEAVE, TryCatch #42 {IOException -> 0x0352, blocks: (B:115:0x034d, B:132:0x039c), top: B:101:0x031d }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0391 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0386 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0160 A[Catch: all -> 0x0159, IOException -> 0x015c, TRY_LEAVE, TryCatch #54 {IOException -> 0x015c, blocks: (B:237:0x0155, B:227:0x0160), top: B:236:0x0155, outer: #18 }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x017b A[Catch: IOException -> 0x0129, TRY_ENTER, TRY_LEAVE, TryCatch #18 {IOException -> 0x0129, blocks: (B:33:0x0124, B:208:0x0132, B:235:0x017b, B:233:0x0169, B:220:0x0118, B:32:0x0121, B:237:0x0155, B:227:0x0160), top: B:22:0x00f0, inners: #23, #54 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0155 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:255:0x03ed A[Catch: all -> 0x03e6, IOException -> 0x03e9, TRY_LEAVE, TryCatch #47 {IOException -> 0x03e9, blocks: (B:269:0x03e2, B:255:0x03ed), top: B:268:0x03e2, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:265:0x040e A[Catch: IOException -> 0x03fa, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x03fa, blocks: (B:265:0x040e, B:261:0x03f6, B:269:0x03e2, B:255:0x03ed), top: B:252:0x03e0, inners: #47 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x03e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010b A[Catch: all -> 0x010f, IOException -> 0x0114, LOOP:1: B:26:0x0105->B:29:0x010b, LOOP_END, TRY_LEAVE, TryCatch #15 {IOException -> 0x0114, blocks: (B:27:0x0105, B:29:0x010b), top: B:26:0x0105 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0116 A[EDGE_INSN: B:30:0x0116->B:31:0x0116 BREAK  A[LOOP:1: B:26:0x0105->B:29:0x010b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0099 A[Catch: all -> 0x0092, IOException -> 0x0095, TRY_LEAVE, TryCatch #13 {IOException -> 0x0095, blocks: (B:341:0x008e, B:319:0x0099), top: B:340:0x008e, outer: #49 }] */
    /* JADX WARN: Removed duplicated region for block: B:339:0x00b4 A[Catch: IOException -> 0x0061, TRY_ENTER, TRY_LEAVE, TryCatch #50 {IOException -> 0x0061, blocks: (B:19:0x005c, B:302:0x006b, B:339:0x00b4, B:325:0x00a2), top: B:6:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x008e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x00c5 A[Catch: all -> 0x00be, IOException -> 0x00c1, TRY_LEAVE, TryCatch #34 {IOException -> 0x00c1, blocks: (B:366:0x00ba, B:351:0x00c5), top: B:365:0x00ba, outer: #11 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x00e6 A[Catch: IOException -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #41 {IOException -> 0x00d2, blocks: (B:363:0x00e6, B:357:0x00ce), top: B:348:0x00b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:364:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01af A[Catch: JSONException -> 0x01b6, TRY_LEAVE, TryCatch #7 {JSONException -> 0x01b6, blocks: (B:41:0x0197, B:43:0x01af), top: B:40:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01e6 A[Catch: JSONException -> 0x0203, TryCatch #25 {JSONException -> 0x0203, blocks: (B:47:0x01c5, B:49:0x01e6, B:51:0x01fc, B:52:0x0207, B:54:0x020d, B:55:0x0217, B:57:0x021d, B:58:0x0226, B:60:0x0233, B:62:0x0244, B:65:0x024e, B:72:0x0259), top: B:46:0x01c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0287 A[Catch: JSONException -> 0x029a, TryCatch #43 {JSONException -> 0x029a, blocks: (B:75:0x0267, B:77:0x0287, B:79:0x0295, B:80:0x029f, B:82:0x02a5, B:83:0x02b3, B:85:0x02b9, B:87:0x02bd, B:92:0x02ca), top: B:74:0x0267 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02f5 A[Catch: JSONException -> 0x0308, LOOP:4: B:95:0x02f3->B:96:0x02f5, LOOP_END, TryCatch #29 {JSONException -> 0x0308, blocks: (B:94:0x02d5, B:96:0x02f5, B:98:0x030a), top: B:93:0x02d5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager
            getInstance(android.content.Context r25) {
        /*
            Method dump skipped, instructions count: 1045
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager.getInstance(android.content.Context):com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager");
    }

    public static long getJsonVersion(StringBuilder sb) {
        try {
            return new JSONObject(sb.toString()).getLong("policyVersion");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
