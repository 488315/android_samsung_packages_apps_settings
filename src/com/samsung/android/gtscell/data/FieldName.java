package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0014\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b,\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\r"
                + "\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                + "\u0000¨\u00060"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/FieldName;",
            ApnSettings.MVNO_NONE,
            "()V",
            "CATEGORY",
            ApnSettings.MVNO_NONE,
            "CONFIG",
            "CONFIG_BUILD_CHARACTERISTICS",
            "CONFIG_CSC_COUNTRY_CODE",
            "CONFIG_CSC_SALES_CODE",
            "CONFIG_DEVICE_NAME",
            "CONFIG_EXTRA",
            "CONFIG_OS_VERSION",
            "CONFIG_PACKAGE_NAME",
            "CONFIG_PACKAGE_VERSION_CODE",
            "CONFIG_PACKAGE_VERSION_NAME",
            "CONFIG_PRODUCT_NAME",
            "CONFIG_SEP_VERSION",
            "GROUPS",
            "GROUP_EXPRESSIONS",
            "GROUP_ITEMS",
            "GROUP_NAME",
            "ITEMS",
            "ITEM_EMBEDDED_ITEMS",
            "ITEM_EXPRESSION",
            "ITEM_EXPRESSION_EXTRA",
            "ITEM_EXPRESSION_SUB_TITLE",
            "ITEM_EXPRESSION_TITLE",
            "ITEM_EXPRESSION_TYPE",
            "ITEM_FORMAT",
            "ITEM_KEY",
            "ITEM_STORE_CONTENTS",
            "ITEM_STORE_CONTENT_TYPE",
            "ITEM_STORE_PACKAGE",
            "ITEM_STORE_PACKAGES",
            "ITEM_STORE_TYPE",
            "ITEM_SUB_EXPRESSIONS",
            "ITEM_TAGS",
            "ITEM_TIMEOUT",
            "ITEM_TYPE",
            "ITEM_VALUE",
            "PRIVATE_CATEGORY",
            "PROVIDER_INFO",
            "PROVIDER_INFO_AUTHORITY",
            "PROVIDER_INFO_ICON",
            "PROVIDER_INFO_LABEL",
            "PROVIDER_INFO_PACKAGE_NAME",
            "REVISION",
            "VERSION",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class FieldName {
    public static final String CATEGORY = "category";
    public static final String CONFIG = "config";
    public static final String CONFIG_BUILD_CHARACTERISTICS = "config_build_char";
    public static final String CONFIG_CSC_COUNTRY_CODE = "config_csc_country_code";
    public static final String CONFIG_CSC_SALES_CODE = "config_csc_sales_code";
    public static final String CONFIG_DEVICE_NAME = "config_device_name";
    public static final String CONFIG_EXTRA = "config_extra";
    public static final String CONFIG_OS_VERSION = "config_os_version";
    public static final String CONFIG_PACKAGE_NAME = "config_package_name";
    public static final String CONFIG_PACKAGE_VERSION_CODE = "config_package_version_code";
    public static final String CONFIG_PACKAGE_VERSION_NAME = "config_package_version_name";
    public static final String CONFIG_PRODUCT_NAME = "config_product_name";
    public static final String CONFIG_SEP_VERSION = "config_sep_version";
    public static final String GROUPS = "gts_groups";
    public static final String GROUP_EXPRESSIONS = "group_expressions";
    public static final String GROUP_ITEMS = "group_items";
    public static final String GROUP_NAME = "group_name";
    public static final FieldName INSTANCE = new FieldName();
    public static final String ITEMS = "items";
    public static final String ITEM_EMBEDDED_ITEMS = "item_embedded_items";
    public static final String ITEM_EXPRESSION = "item_expression";
    public static final String ITEM_EXPRESSION_EXTRA = "item_expression_extra";
    public static final String ITEM_EXPRESSION_SUB_TITLE = "item_expression_sub_title";
    public static final String ITEM_EXPRESSION_TITLE = "item_expression_title";
    public static final String ITEM_EXPRESSION_TYPE = "item_expression_type";
    public static final String ITEM_FORMAT = "item_format";
    public static final String ITEM_KEY = "item_key";
    public static final String ITEM_STORE_CONTENTS = "item_store_contents";
    public static final String ITEM_STORE_CONTENT_TYPE = "item_store_content_type";
    public static final String ITEM_STORE_PACKAGE = "item_store_pkg";
    public static final String ITEM_STORE_PACKAGES = "item_store_packages";
    public static final String ITEM_STORE_TYPE = "item_store_type";
    public static final String ITEM_SUB_EXPRESSIONS = "item_sub_expressions";
    public static final String ITEM_TAGS = "item_tags";
    public static final String ITEM_TIMEOUT = "item_timeout";
    public static final String ITEM_TYPE = "item_type";
    public static final String ITEM_VALUE = "item_value";
    public static final String PRIVATE_CATEGORY = "private_category";
    public static final String PROVIDER_INFO = "gts_provider_info";
    public static final String PROVIDER_INFO_AUTHORITY = "gpi_authority";
    public static final String PROVIDER_INFO_ICON = "gpi_icon";
    public static final String PROVIDER_INFO_LABEL = "gpi_label";
    public static final String PROVIDER_INFO_PACKAGE_NAME = "gpi_package_name";
    public static final String REVISION = "revision";
    public static final String VERSION = "version";

    private FieldName() {}
}
