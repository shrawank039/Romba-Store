package in.rombashop.romba.net;


import in.rombashop.romba.AddressList;

public class ServiceNames {
    // http://adminarea8199.rombashop.in/index.php/
    // https://www.matrixdeveloper.com/romba/index.php/

    public static String PRODUCTION_API = "http://adminarea8199.rombashop.in/index.php/";

    public static final String API_VERSION = "rest/";

    private static final String API = PRODUCTION_API + API_VERSION;

    public static final String PINCODE =  API + "shipping_zones/get_shipping_cost/teampsisthebest";
    public static final String USER_REGISTRATION = API +"users/add";
    public static final String USER_LOGIN = API +"users/login";
    public static final String CANCEL = API +"transactionheaders/cancelorder";
    public static final String RETURN = API +"transactionheaders/return";
    public static final String FAVORITE = API +"products/get_favourite/api_key/teampsisthebest/limit/0/offset/0/login_user_id/";
    public static final String ADD_FAVORITE = API +"products/get/api_key/teampsisthebest/id/prd00fbd886d946e00c7a792b98aa834df6/shop_id/shopd8b59013d41510b0b78483e477286803/login_user_id/c4ca4238a0b923820dcc509a6f75849b";
    public static final String SUB_CATEGORY_PRODUCTS = API +"products/get?sub_cat_id=";
    public static final String SEARCH_PRODUCT_LIST = API +"products/search";
    public static final String ADD_ADDRESS = API +"users/add_new_address";
    public static final String DELETE_ADDRESS = API +"users/deleteParticularAddress";
    public static final String GET_ADDRESS = API +"users/getAllAddress?user_id=";
    public static final String SHIPPING_METHOD = API +"shippings/get/api_key/teampsisthebest/api_key/teampsisthebest/shop_id/shopd8b59013d41510b0b78483e477286803";
    public static final String CONFIRM = API +"transactionheaders/submit";
    public static final String GET_ALL_IMAGE = API +"images/get/img_parent_id/";

    //forgot pass
    public static final String USER_CHECK  = API +"users/checkPhone";
    public static final String FORGOT_POST = API +"users/forgotPassword";

    public static final String RALL_CATEGORIES = API +"categories/get?shop_id=shopd8b59013d41510b0b78483e477286803";
    public static final String IMAGE_URL = "http://adminarea8199.rombashop.in/uploads/";
    public static final String COLLECTIONS = API +"collections/get/api_key/teampsisthebest/shop_id/shopd8b59013d41510b0b78483e477286803/limit/10/offset/0";
    public static final String DISCOUNTED_PRODUCTS = API +"products/get?is_discount=1&shop_id=shopd8b59013d41510b0b78483e477286803";
    public static final String FEATURES_PRODUCTS = API +"products/get?is_featured=1&shop_id=shopd8b59013d41510b0b78483e477286803";
    public static final String SUB_CATEGORIES = API +"subcategories/get?shop_id=shopd8b59013d41510b0b78483e477286803&cat_id=";
    public static final String TRANSACTION_DETAILS = API +"transactiondetails/get?shop_id=shopd8b59013d41510b0b78483e477286803&transactions_header_id=";
    public static final String TRANSACTION_HEADER = API +"transactionheaders/get?shop_id=shopd8b59013d41510b0b78483e477286803&user_id=";

    public static AddressList addressList;
    public static boolean sameDayDeliver = true;
    public static String user_id;
    public static String order_status = "Pending";

    // http://adminarea8199.rombashop.in/uploads/51nsuV9Kh7L._SL1500_.jpg
}