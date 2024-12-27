package com.android.settings.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.telephony.MccTable;
import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.network.helper.SelectableSubscriptions;
import com.android.settings.network.helper.SubscriptionAnnotation;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SubscriptionUtil {
    static final String KEY_UNIQUE_SUBSCRIPTION_DISPLAYNAME = "unique_subscription_displayName";
    public static final Pattern REGEX_DISPLAY_NAME_SUFFIX_PATTERN = Pattern.compile("\\s[0-9]+");
    static final String SUB_ID = "sub_id";
    public static List sActiveResultsForTesting;
    public static List sAvailableResultsForTesting;
    public static Boolean sEnableRacDialogForTesting;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.SubscriptionUtil$1DisplayInfo, reason: invalid class name */
    public final class C1DisplayInfo {
        public CharSequence originalName;
        public SubscriptionInfo subscriptionInfo;
        public CharSequence uniqueName;
    }

    public static List getActiveSubscriptions(SubscriptionManager subscriptionManager) {
        List list = sActiveResultsForTesting;
        if (list != null) {
            return list;
        }
        if (subscriptionManager == null) {
            return Collections.emptyList();
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                subscriptionManager.getActiveSubscriptionInfoList();
        return activeSubscriptionInfoList == null
                ? new ArrayList()
                : (List)
                        activeSubscriptionInfoList.stream()
                                .filter(new SubscriptionUtil$$ExternalSyntheticLambda2(0))
                                .collect(Collectors.toList());
    }

    public static List getAvailableSubscriptions(Context context) {
        List list = sAvailableResultsForTesting;
        return list != null
                ? list
                : new ArrayList(
                        CollectionUtils.emptyIfNull(
                                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(
                                        context)));
    }

    public static String getBidiFormattedPhoneNumber(
            Context context, SubscriptionInfo subscriptionInfo) {
        String formattedPhoneNumber = getFormattedPhoneNumber(context, subscriptionInfo);
        return formattedPhoneNumber == null
                ? formattedPhoneNumber
                : BidiFormatter.getInstance()
                        .unicodeWrap(formattedPhoneNumber, TextDirectionHeuristics.LTR);
    }

    public static SubscriptionAnnotation getDefaultSubscriptionSelection(List list) {
        if (list == null) {
            return null;
        }
        return (SubscriptionAnnotation)
                list.stream()
                        .filter(new SubscriptionUtil$$ExternalSyntheticLambda2(2))
                        .filter(new SubscriptionUtil$$ExternalSyntheticLambda2(3))
                        .findFirst()
                        .orElse(null);
    }

    public static SubscriptionInfo getFirstRemovableSubscription(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        List<UiccCardInfo> uiccCardsInfo = telephonyManager.getUiccCardsInfo();
        if (uiccCardsInfo == null) {
            Log.w("SubscriptionUtil", "UICC cards info list is empty.");
            return null;
        }
        List<SubscriptionInfo> allSubscriptionInfoList =
                subscriptionManager.getAllSubscriptionInfoList();
        if (allSubscriptionInfoList == null) {
            Log.w("SubscriptionUtil", "All subscription info list is empty.");
            return null;
        }
        for (UiccCardInfo uiccCardInfo : uiccCardsInfo) {
            if (uiccCardInfo == null) {
                Log.w("SubscriptionUtil", "Got null card.");
            } else if (!uiccCardInfo.isRemovable() || uiccCardInfo.getCardId() == -1) {
                Log.i(
                        "SubscriptionUtil",
                        "Skip embedded card or invalid cardId on slot: "
                                + uiccCardInfo.getPhysicalSlotIndex());
            } else {
                Log.i("SubscriptionUtil", "Target removable cardId :" + uiccCardInfo.getCardId());
                for (SubscriptionInfo subscriptionInfo : allSubscriptionInfoList) {
                    if (uiccCardInfo.getCardId() == subscriptionInfo.getCardId()) {
                        return subscriptionInfo;
                    }
                }
            }
        }
        return null;
    }

    public static String getFormattedPhoneNumber(
            Context context, SubscriptionInfo subscriptionInfo) {
        String str;
        if (subscriptionInfo == null) {
            Log.e("SubscriptionUtil", "Invalid subscription.");
            return null;
        }
        try {
            str =
                    ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                            .getPhoneNumber(subscriptionInfo.getSubscriptionId());
        } catch (IllegalStateException e) {
            Log.e("SubscriptionUtil", "Subscription service unavailable : " + e);
            str = ApnSettings.MVNO_NONE;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return PhoneNumberUtils.formatNumber(
                str,
                MccTable.countryCodeForMcc(subscriptionInfo.getMccString())
                        .toUpperCase(Locale.ROOT));
    }

    public static SubscriptionInfo getSubById(SubscriptionManager subscriptionManager, int i) {
        if (i == -1) {
            return null;
        }
        return subscriptionManager.getAllSubscriptionInfoList().stream()
                .filter(new SubscriptionUtil$$ExternalSyntheticLambda4(i, 0))
                .findFirst()
                .orElse(null);
    }

    public static SubscriptionInfo getSubscriptionOrDefault(Context context, int i) {
        SubscriptionUtil$$ExternalSyntheticLambda1 subscriptionUtil$$ExternalSyntheticLambda1 =
                i != -1 ? null : new SubscriptionUtil$$ExternalSyntheticLambda1(0);
        List<SubscriptionAnnotation> call = new SelectableSubscriptions(context).call();
        Log.d("SubscriptionUtil", "get subId=" + i + " from " + call);
        SubscriptionAnnotation orElse =
                call.stream()
                        .filter(new SubscriptionUtil$$ExternalSyntheticLambda2(2))
                        .filter(new SubscriptionUtil$$ExternalSyntheticLambda4(i, 2))
                        .findFirst()
                        .orElse(null);
        if (orElse == null && subscriptionUtil$$ExternalSyntheticLambda1 != null) {
            orElse = getDefaultSubscriptionSelection(call);
        }
        if (orElse == null) {
            return null;
        }
        return orElse.getSubInfo();
    }

    public static CharSequence getUniqueSubscriptionDisplayName(
            Context context, SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null) {
            return ApnSettings.MVNO_NONE;
        }
        return getUniqueSubscriptionDisplayNames(context)
                .getOrDefault(
                        Integer.valueOf(subscriptionInfo.getSubscriptionId()),
                        ApnSettings.MVNO_NONE);
    }

    public static Map<Integer, CharSequence> getUniqueSubscriptionDisplayNames(
            final Context context) {
        Supplier supplier =
                new Supplier() { // from class:
                                 // com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Context context2 = context;
                        return SubscriptionUtil.getAvailableSubscriptions(context2).stream()
                                .filter(new SubscriptionUtil$$ExternalSyntheticLambda2(1))
                                .map(new SubscriptionUtil$$ExternalSyntheticLambda10(1, context2));
                    }
                };
        HashSet hashSet = new HashSet();
        final Set set =
                (Set)
                        ((Stream) supplier.get())
                                .filter(new SubscriptionUtil$$ExternalSyntheticLambda6(0, hashSet))
                                .map(new SubscriptionUtil$$ExternalSyntheticLambda1(3))
                                .collect(Collectors.toSet());
        hashSet.clear();
        return (Map)
                ((Stream) supplier.get())
                        .map(
                                new Function() { // from class:
                                    // com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda19
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        String str;
                                        Context context2 = context;
                                        Set set2 = set;
                                        SubscriptionUtil.C1DisplayInfo c1DisplayInfo =
                                                (SubscriptionUtil.C1DisplayInfo) obj;
                                        int subscriptionId =
                                                c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                                        String str2 = ApnSettings.MVNO_NONE;
                                        String string =
                                                context2.getSharedPreferences(
                                                                "unique_subscription_displayName",
                                                                0)
                                                        .getString(
                                                                "sub_id" + subscriptionId,
                                                                ApnSettings.MVNO_NONE);
                                        if (SubscriptionUtil.isValidCachedDisplayName(
                                                string,
                                                ((String) c1DisplayInfo.originalName).toString())) {
                                            Log.d(
                                                    "SubscriptionUtil",
                                                    "use cached display name : for subId : "
                                                            + subscriptionId
                                                            + "cached display name : "
                                                            + string);
                                            c1DisplayInfo.uniqueName = string;
                                        } else {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                    subscriptionId,
                                                    "remove cached display name : ",
                                                    "SubscriptionUtil");
                                            context2.getSharedPreferences(
                                                            "unique_subscription_displayName", 0)
                                                    .edit()
                                                    .remove("sub_id" + subscriptionId)
                                                    .commit();
                                            if (set2.contains(c1DisplayInfo.originalName)) {
                                                try {
                                                    str =
                                                            ((SubscriptionManager)
                                                                            context2
                                                                                    .getSystemService(
                                                                                            SubscriptionManager
                                                                                                    .class))
                                                                    .getPhoneNumber(subscriptionId);
                                                } catch (IllegalStateException
                                                        | SecurityException
                                                        | UnsupportedOperationException e) {
                                                    Log.w(
                                                            "SubscriptionUtil",
                                                            "get number error." + e);
                                                    str = ApnSettings.MVNO_NONE;
                                                }
                                                if (str != null) {
                                                    if (str.length() > 4) {
                                                        str = str.substring(str.length() - 4);
                                                    }
                                                    str2 = str;
                                                }
                                                if (TextUtils.isEmpty(str2)) {
                                                    c1DisplayInfo.uniqueName =
                                                            c1DisplayInfo.originalName;
                                                } else {
                                                    c1DisplayInfo.uniqueName =
                                                            ((Object) c1DisplayInfo.originalName)
                                                                    + " "
                                                                    + str2;
                                                    Log.d(
                                                            "SubscriptionUtil",
                                                            "Cache display name ["
                                                                    + ((Object)
                                                                            c1DisplayInfo
                                                                                    .uniqueName)
                                                                    + "] for sub id "
                                                                    + subscriptionId);
                                                    context2.getSharedPreferences(
                                                                    "unique_subscription_displayName",
                                                                    0)
                                                            .edit()
                                                            .putString(
                                                                    SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    subscriptionId,
                                                                                    "sub_id"),
                                                                    String.valueOf(
                                                                            c1DisplayInfo
                                                                                    .uniqueName))
                                                            .apply();
                                                }
                                            } else {
                                                c1DisplayInfo.uniqueName =
                                                        c1DisplayInfo.originalName;
                                            }
                                        }
                                        return c1DisplayInfo;
                                    }
                                })
                        .map(
                                new SubscriptionUtil$$ExternalSyntheticLambda10(
                                        0,
                                        (Set)
                                                ((Stream) supplier.get())
                                                        .map(
                                                                new Function() { // from class:
                                                                    // com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda19
                                                                    @Override // java.util.function.Function
                                                                    public final Object apply(
                                                                            Object obj) {
                                                                        String str;
                                                                        Context context2 = context;
                                                                        Set set2 = set;
                                                                        SubscriptionUtil
                                                                                        .C1DisplayInfo
                                                                                c1DisplayInfo =
                                                                                        (SubscriptionUtil
                                                                                                        .C1DisplayInfo)
                                                                                                obj;
                                                                        int subscriptionId =
                                                                                c1DisplayInfo
                                                                                        .subscriptionInfo
                                                                                        .getSubscriptionId();
                                                                        String str2 =
                                                                                ApnSettings
                                                                                        .MVNO_NONE;
                                                                        String string =
                                                                                context2.getSharedPreferences(
                                                                                                "unique_subscription_displayName",
                                                                                                0)
                                                                                        .getString(
                                                                                                "sub_id"
                                                                                                        + subscriptionId,
                                                                                                ApnSettings
                                                                                                        .MVNO_NONE);
                                                                        if (SubscriptionUtil
                                                                                .isValidCachedDisplayName(
                                                                                        string,
                                                                                        ((String)
                                                                                                        c1DisplayInfo
                                                                                                                .originalName)
                                                                                                .toString())) {
                                                                            Log.d(
                                                                                    "SubscriptionUtil",
                                                                                    "use cached"
                                                                                        + " display"
                                                                                        + " name :"
                                                                                        + " for subId"
                                                                                        + " : "
                                                                                            + subscriptionId
                                                                                            + "cached"
                                                                                            + " display"
                                                                                            + " name"
                                                                                            + " : "
                                                                                            + string);
                                                                            c1DisplayInfo
                                                                                            .uniqueName =
                                                                                    string;
                                                                        } else {
                                                                            ListPopupWindow$$ExternalSyntheticOutline0
                                                                                    .m1m(
                                                                                            subscriptionId,
                                                                                            "remove"
                                                                                                + " cached"
                                                                                                + " display"
                                                                                                + " name"
                                                                                                + " : ",
                                                                                            "SubscriptionUtil");
                                                                            context2.getSharedPreferences(
                                                                                            "unique_subscription_displayName",
                                                                                            0)
                                                                                    .edit()
                                                                                    .remove(
                                                                                            "sub_id"
                                                                                                    + subscriptionId)
                                                                                    .commit();
                                                                            if (set2.contains(
                                                                                    c1DisplayInfo
                                                                                            .originalName)) {
                                                                                try {
                                                                                    str =
                                                                                            ((SubscriptionManager)
                                                                                                            context2
                                                                                                                    .getSystemService(
                                                                                                                            SubscriptionManager
                                                                                                                                    .class))
                                                                                                    .getPhoneNumber(
                                                                                                            subscriptionId);
                                                                                } catch (IllegalStateException
                                                                                        | SecurityException
                                                                                        | UnsupportedOperationException
                                                                                                e) {
                                                                                    Log.w(
                                                                                            "SubscriptionUtil",
                                                                                            "get number"
                                                                                                + " error."
                                                                                                    + e);
                                                                                    str =
                                                                                            ApnSettings
                                                                                                    .MVNO_NONE;
                                                                                }
                                                                                if (str != null) {
                                                                                    if (str.length()
                                                                                            > 4) {
                                                                                        str =
                                                                                                str
                                                                                                        .substring(
                                                                                                                str
                                                                                                                                .length()
                                                                                                                        - 4);
                                                                                    }
                                                                                    str2 = str;
                                                                                }
                                                                                if (TextUtils
                                                                                        .isEmpty(
                                                                                                str2)) {
                                                                                    c1DisplayInfo
                                                                                                    .uniqueName =
                                                                                            c1DisplayInfo
                                                                                                    .originalName;
                                                                                } else {
                                                                                    c1DisplayInfo
                                                                                                    .uniqueName =
                                                                                            ((Object)
                                                                                                            c1DisplayInfo
                                                                                                                    .originalName)
                                                                                                    + " "
                                                                                                    + str2;
                                                                                    Log.d(
                                                                                            "SubscriptionUtil",
                                                                                            "Cache"
                                                                                                + " display"
                                                                                                + " name"
                                                                                                + " ["
                                                                                                    + ((Object)
                                                                                                            c1DisplayInfo
                                                                                                                    .uniqueName)
                                                                                                    + "] for"
                                                                                                    + " sub id"
                                                                                                    + " "
                                                                                                    + subscriptionId);
                                                                                    context2.getSharedPreferences(
                                                                                                    "unique_subscription_displayName",
                                                                                                    0)
                                                                                            .edit()
                                                                                            .putString(
                                                                                                    SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                                                            .m(
                                                                                                                    subscriptionId,
                                                                                                                    "sub_id"),
                                                                                                    String
                                                                                                            .valueOf(
                                                                                                                    c1DisplayInfo
                                                                                                                            .uniqueName))
                                                                                            .apply();
                                                                                }
                                                                            } else {
                                                                                c1DisplayInfo
                                                                                                .uniqueName =
                                                                                        c1DisplayInfo
                                                                                                .originalName;
                                                                            }
                                                                        }
                                                                        return c1DisplayInfo;
                                                                    }
                                                                })
                                                        .filter(
                                                                new SubscriptionUtil$$ExternalSyntheticLambda6(
                                                                        1, hashSet))
                                                        .map(
                                                                new SubscriptionUtil$$ExternalSyntheticLambda1(
                                                                        4))
                                                        .collect(Collectors.toSet())))
                        .collect(
                                Collectors.toMap(
                                        new SubscriptionUtil$$ExternalSyntheticLambda1(1),
                                        new SubscriptionUtil$$ExternalSyntheticLambda1(2)));
    }

    public static boolean isCarrierRac(Context context, final int i) {
        return Arrays.stream(context.getResources().getIntArray(R.array.config_carrier_use_rac))
                .anyMatch(
                        new IntPredicate() { // from class:
                                             // com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntPredicate
                            public final boolean test(int i2) {
                                return i2 == i;
                            }
                        });
    }

    public static boolean isConnectedToMobileDataWithDifferentSubId(Context context, int i) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return (networkCapabilities == null
                        || !networkCapabilities.hasTransport(0)
                        || i == SubscriptionManager.getActiveDataSubscriptionId())
                ? false
                : true;
    }

    public static boolean isConvertedPsimSubscription(Context context, int i) {
        for (SubscriptionInfo subscriptionInfo :
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .getAllSubscriptionInfoList()) {
            if (subscriptionInfo != null
                    && subscriptionInfo.getSubscriptionId() == i
                    && isConvertedPsimSubscription(subscriptionInfo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmbeddedSubscriptionVisible(SubscriptionInfo subscriptionInfo) {
        return (subscriptionInfo.isEmbedded()
                        && (subscriptionInfo.getProfileClass() == 1
                                || subscriptionInfo.isOnlyNonTerrestrialNetwork()))
                ? false
                : true;
    }

    public static boolean isInactiveInsertedPSim(UiccSlotInfo uiccSlotInfo) {
        return (uiccSlotInfo == null
                        || uiccSlotInfo.getIsEuicc()
                        || ((UiccPortInfo) uiccSlotInfo.getPorts().stream().findFirst().get())
                                .isActive()
                        || uiccSlotInfo.getCardStateInfo() != 2)
                ? false
                : true;
    }

    public static boolean isSimHardwareVisible(Context context) {
        return context.getResources().getBoolean(R.bool.config_show_sim_info);
    }

    public static boolean isSubscriptionVisible(
            SubscriptionManager subscriptionManager,
            Context context,
            SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null || !isEmbeddedSubscriptionVisible(subscriptionInfo)) {
            return false;
        }
        if (subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic()) {
            return true;
        }
        return ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(subscriptionInfo.getSubscriptionId())
                        .hasCarrierPrivileges()
                || subscriptionManager.canManageSubscription(subscriptionInfo);
    }

    public static boolean isValidCachedDisplayName(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !str.startsWith(str2)) {
            return false;
        }
        return REGEX_DISPLAY_NAME_SUFFIX_PATTERN.matcher(str.substring(str2.length())).matches();
    }

    public static void setActiveSubscriptionsForTesting(List<SubscriptionInfo> list) {
        sActiveResultsForTesting = list;
    }

    public static void setAvailableSubscriptionsForTesting(List<SubscriptionInfo> list) {
        sAvailableResultsForTesting = list;
    }

    public static void setEnableRacDialogForTesting(boolean z) {
        sEnableRacDialogForTesting = Boolean.valueOf(z);
    }

    public static boolean shouldShowRacDialogWhenErasingAllEsims(Context context) {
        Boolean bool = sEnableRacDialogForTesting;
        if (bool != null) {
            return bool.booleanValue();
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities == null || !networkCapabilities.hasTransport(1)) {
            final List availableSubscriptions = getAvailableSubscriptions(context);
            if (Arrays.stream(context.getResources().getIntArray(R.array.config_carrier_use_rac))
                    .anyMatch(
                            new IntPredicate() { // from class:
                                                 // com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda3
                                @Override // java.util.function.IntPredicate
                                public final boolean test(int i) {
                                    return availableSubscriptions.stream()
                                            .anyMatch(
                                                    new SubscriptionUtil$$ExternalSyntheticLambda4(
                                                            i, 1));
                                }
                            })) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldShowRacDialogWhenErasingEsim(Context context, int i, int i2) {
        if (isCarrierRac(context, i2)) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
            NetworkCapabilities networkCapabilities =
                    connectivityManager.getNetworkCapabilities(
                            connectivityManager.getActiveNetwork());
            if ((networkCapabilities == null || !networkCapabilities.hasTransport(1))
                    && !isConnectedToMobileDataWithDifferentSubId(context, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isConvertedPsimSubscription(SubscriptionInfo subscriptionInfo) {
        Log.d(
                "SubscriptionUtil",
                "isConvertedPsimSubscription: isEmbedded " + subscriptionInfo.isEmbedded());
        Log.d(
                "SubscriptionUtil",
                "isConvertedPsimSubscription: getTransferStatus "
                        + subscriptionInfo.getTransferStatus());
        return !subscriptionInfo.isEmbedded() && subscriptionInfo.getTransferStatus() == 2;
    }
}
