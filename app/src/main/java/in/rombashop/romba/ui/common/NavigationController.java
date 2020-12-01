package in.rombashop.romba.ui.common;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import in.rombashop.romba.Config;
import in.rombashop.romba.MainActivity;

import in.rombashop.romba.R;
import in.rombashop.romba.ui.basket.BasketListActivity;
import in.rombashop.romba.ui.basket.BasketListFragment;
import in.rombashop.romba.ui.blog.detail.BlogDetailActivity;
import in.rombashop.romba.ui.blog.list.BlogListActivity;
import in.rombashop.romba.ui.category.CategoryListActivity;
import in.rombashop.romba.ui.category.CategoryListFragment;
import in.rombashop.romba.ui.checkout.CheckoutActivity;
import in.rombashop.romba.ui.collection.CollectionActivity;
import in.rombashop.romba.ui.collection.productCollectionHeader.ProductCollectionHeaderListFragment;
import in.rombashop.romba.ui.comment.detail.CommentDetailActivity;
import in.rombashop.romba.ui.comment.list.CommentListActivity;
import in.rombashop.romba.ui.contactus.ContactUsFragment;
import in.rombashop.romba.ui.forceupdate.ForceUpdateActivity;
import in.rombashop.romba.ui.gallery.GalleryActivity;
import in.rombashop.romba.ui.gallery.detail.GalleryDetailActivity;
import in.rombashop.romba.ui.language.LanguageFragment;
import in.rombashop.romba.ui.notification.detail.NotificationActivity;
import in.rombashop.romba.ui.notification.list.NotificationListActivity;
import in.rombashop.romba.ui.privacyandpolicy.PrivacyPolicyActivity;
import in.rombashop.romba.ui.privacyandpolicy.PrivacyPolicyFragment;
import in.rombashop.romba.ui.product.MainFragment;
import in.rombashop.romba.ui.product.detail.ProductActivity;
import in.rombashop.romba.ui.product.favourite.FavouriteListActivity;
import in.rombashop.romba.ui.product.favourite.FavouriteListFragment;
import in.rombashop.romba.ui.product.filtering.FilteringActivity;
import in.rombashop.romba.ui.product.history.HistoryFragment;
import in.rombashop.romba.ui.product.history.UserHistoryListActivity;
import in.rombashop.romba.ui.product.list.ProductListActivity;
import in.rombashop.romba.ui.product.list.ProductListFragment;
import in.rombashop.romba.ui.product.search.SearchByCategoryActivity;
import in.rombashop.romba.ui.product.search.SearchFragment;
import in.rombashop.romba.ui.rating.RatingListActivity;
import in.rombashop.romba.ui.setting.AppInfoActivity;
import in.rombashop.romba.ui.setting.NotificationSettingActivity;
import in.rombashop.romba.ui.setting.SettingActivity;
import in.rombashop.romba.ui.setting.SettingFragment;
import in.rombashop.romba.ui.shop.ShopProfileFragment;
import in.rombashop.romba.ui.stripe.StripeActivity;
import in.rombashop.romba.ui.terms.TermsAndConditionsActivity;
import in.rombashop.romba.ui.transaction.detail.TransactionActivity;
import in.rombashop.romba.ui.transaction.list.TransactionListActivity;
import in.rombashop.romba.ui.transaction.list.TransactionListFragment;
import in.rombashop.romba.ui.user.PasswordChangeActivity;
import in.rombashop.romba.ui.user.ProfileEditActivity;
import in.rombashop.romba.ui.user.ProfileFragment;
import in.rombashop.romba.ui.user.UserForgotPasswordActivity;
import in.rombashop.romba.ui.user.UserForgotPasswordFragment;
import in.rombashop.romba.ui.user.UserLoginActivity;
import in.rombashop.romba.ui.user.UserLoginFragment;
import in.rombashop.romba.ui.user.UserRegisterActivity;
import in.rombashop.romba.ui.user.UserRegisterFragment;
import in.rombashop.romba.ui.user.phonelogin.PhoneLoginActivity;
import in.rombashop.romba.ui.user.phonelogin.PhoneLoginFragment;
import in.rombashop.romba.ui.user.verifyemail.VerifyEmailActivity;
import in.rombashop.romba.ui.user.verifyemail.VerifyEmailFragment;
import in.rombashop.romba.ui.user.verifyphone.VerifyMobileActivity;
import in.rombashop.romba.ui.user.verifyphone.VerifyMobileFragment;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.Basket;
import in.rombashop.romba.viewobject.Comment;
import in.rombashop.romba.viewobject.HistoryProduct;
import in.rombashop.romba.viewobject.Noti;
import in.rombashop.romba.viewobject.Product;
import in.rombashop.romba.viewobject.TransactionObject;
import in.rombashop.romba.viewobject.holder.ProductParameterHolder;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by Panacea-Soft on 11/17/17.
 * Contact Email : teamps.is.cool@gmail.com
 */

public class NavigationController {

    //region Variables

    private final int containerId;
    private RegFragments currentFragment;

    //endregion


    //region Constructor
    @Inject
    public NavigationController() {

        // This setup is for MainActivity
        this.containerId = R.id.content_frame;
    }

    //endregion


    //region default navigation

    public void navigateToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToUserLogin(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_USER_LOGIN)) {
            try {
                UserLoginFragment fragment = new UserLoginFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToBasket(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_BASKET)) {
            try {
                BasketListFragment fragment = new BasketListFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToUserProfile(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_USER_LOGIN)) {
            try {
                ProfileFragment fragment = new ProfileFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToFavourite(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_FAVOURITE)) {
            try {
                FavouriteListFragment fragment = new FavouriteListFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }


    public void navigateToTransaction(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_TRANSACTION)) {
            try {
                TransactionListFragment fragment = new TransactionListFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToHistory(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_HISTORY)) {
            try {
                HistoryFragment fragment = new HistoryFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }


    public void navigateToUserRegister(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_USER_REGISTER)) {
            try {
                UserRegisterFragment fragment = new UserRegisterFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToUserForgotPassword(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_USER_FOGOT_PASSWORD)) {
            try {
                UserForgotPasswordFragment fragment = new UserForgotPasswordFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToSetting(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_SETTING)) {
            try {
                SettingFragment fragment = new SettingFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }


    public void navigateToShopProfile(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_ABOUTUS)) {
            try {
                ShopProfileFragment fragment = new ShopProfileFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToLanguageSetting(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_LANGUAGE_SETTING)) {
            try {
                LanguageFragment fragment = new LanguageFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToHome(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_HOME)) {
            try {
                MainFragment fragment = new MainFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToLatestProducts(MainActivity mainActivity, ProductParameterHolder productParameterHolder) {
        if (checkFragmentChange(RegFragments.HOME_LATEST_PRODUCTS)) {
            try {
                ProductListFragment fragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT_PARAM_HOLDER_KEY, productParameterHolder);
                fragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToDiscountProduct(MainActivity mainActivity, ProductParameterHolder productParameterHolder) {
        if (checkFragmentChange(RegFragments.HOME_DISCOUNT)) {
            try {
                ProductListFragment fragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT_PARAM_HOLDER_KEY, productParameterHolder);
                fragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();

            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToFeatureProducts(MainActivity mainActivity, ProductParameterHolder productParameterHolder) {
        if (checkFragmentChange(RegFragments.HOME_FEATURED_PRODUCTS)) {
            try {
                ProductListFragment fragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT_PARAM_HOLDER_KEY, productParameterHolder);
                fragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToTrendingProducts(MainActivity mainActivity, ProductParameterHolder productParameterHolder) {
        if (checkFragmentChange(RegFragments.HOME_TRENDINGPRODUCTS)) {
            try {
                ProductListFragment fragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT_PARAM_HOLDER_KEY, productParameterHolder);
                fragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToCategory(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_CATEGORY)) {
            try {
                CategoryListFragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CATEGORY_TYPE, Constants.CATEGORY);
                fragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToCollectionList(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_PRODUCT_COLLECTION)) {
            try {
                ProductCollectionHeaderListFragment fragment = new ProductCollectionHeaderListFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToSearch(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_SEARCH)) {
            try {
                SearchFragment fragment = new SearchFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToGalleryActivity(Activity activity, String imgType, String imgParentId) {
        Intent intent = new Intent(activity, GalleryActivity.class);

        if (!imgType.equals("")) {
            intent.putExtra(Constants.IMAGE_TYPE, imgType);
        }

        if (!imgParentId.equals("")) {
            intent.putExtra(Constants.IMAGE_PARENT_ID, imgParentId);
        }

        activity.startActivity(intent);

    }

    public void navigateToDetailGalleryActivity(Activity activity, String imgType, String newsId, String imgId) {
        Intent intent = new Intent(activity, GalleryDetailActivity.class);

        if (!imgType.equals("")) {
            intent.putExtra(Constants.IMAGE_TYPE, imgType);
        }

        if (!newsId.equals("")) {
            intent.putExtra(Constants.PRODUCT_ID, newsId);
        }

        if (!imgId.equals("")) {
            intent.putExtra(Constants.IMAGE_ID, imgId);
        }

        activity.startActivity(intent);

    }

    public void navigateToCommentListActivity(Activity activity, Product product) {
        Intent intent = new Intent(activity, CommentListActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, product.id);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE__PRODUCT_FRAGMENT);
    }

    public void navigateToSettingActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE__PROFILE_FRAGMENT);
    }

    public void navigateToNotificationSettingActivity(Activity activity) {
        Intent intent = new Intent(activity, NotificationSettingActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToEditProfileActivity(Activity activity) {
        Intent intent = new Intent(activity, ProfileEditActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToAppInfoActivity(Activity activity) {
        Intent intent = new Intent(activity, AppInfoActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToProfileEditActivity(Activity activity) {
        Intent intent = new Intent(activity, ProfileEditActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToTermsAndConditionsActivity(Activity activity, String flag) {
        Intent intent = new Intent(activity, TermsAndConditionsActivity.class);
        intent.putExtra(Constants.SHOP_TERMS_FLAG, flag);
        activity.startActivity(intent);
    }

    public void navigateToVerifyEmailActivity(Activity activity) {
        Intent intent = new Intent(activity, VerifyEmailActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToVerifyEmail(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_USER_EMAIL_VERIFY)) {
            try {
                VerifyEmailFragment fragment = new VerifyEmailFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToTransactionDetailActivity(Activity activity) {
        Intent intent = new Intent(activity, TransactionListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToUserLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, UserLoginActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToUserRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, UserRegisterActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToUserForgotPasswordActivity(Activity activity) {
        Intent intent = new Intent(activity, UserForgotPasswordActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToPasswordChangeActivity(Activity activity) {
        Intent intent = new Intent(activity, PasswordChangeActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToNotificationList(Activity activity) {
        Intent intent = new Intent(activity, NotificationListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToBasketList(Activity activity) {
        Intent intent = new Intent(activity, BasketListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToRatingList(Activity activity, String productId) {
        Intent intent = new Intent(activity, RatingListActivity.class);

        intent.putExtra(Constants.PRODUCT_ID, productId);

        activity.startActivity(intent);
    }


    public void navigateToNotificationDetail(Activity activity, Noti noti, String token) {
        Intent intent = new Intent(activity, NotificationActivity.class);
        intent.putExtra(Constants.NOTI_ID, noti.id);
        intent.putExtra(Constants.NOTI_TOKEN, token);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE__NOTIFICATION_LIST_FRAGMENT);
    }

    public void navigateToTransactionDetail(Activity activity, TransactionObject transactionObject) {
        Intent intent = new Intent(activity, TransactionActivity.class);
        intent.putExtra(Constants.TRANSACTION_ID, transactionObject.id);
        intent.putExtra(Constants.TRANSACTION_IS_ZONE_SHIPPING, transactionObject.isZoneShipping);
        activity.startActivity(intent);
    }

    public void navigateToCommentDetailActivity(Activity activity, Comment comment) {
        Intent intent = new Intent(activity, CommentDetailActivity.class);
        intent.putExtra(Constants.COMMENT_ID, comment.id);

        activity.startActivityForResult(intent, Constants.REQUEST_CODE__COMMENT_LIST_FRAGMENT);

    }

    public void navigateToDetailActivity(Activity activity, Product product) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, product.id);
        intent.putExtra(Constants.PRODUCT_NAME, product.name);
        intent.putExtra(Constants.HISTORY_FLAG, Constants.ONE);

        intent.putExtra(Constants.BASKET_FLAG, Constants.ZERO);

        activity.startActivity(intent);
    }

    public void navigateToProductDetailActivity(Activity activity, HistoryProduct historyProduct) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, historyProduct.id);
        intent.putExtra(Constants.PRODUCT_NAME, historyProduct.historyName);
        intent.putExtra(Constants.HISTORY_FLAG, Constants.ZERO);

        intent.putExtra(Constants.BASKET_FLAG, Constants.ZERO);
        activity.startActivity(intent);
    }

    public void navigateToProductDetailActivity(Activity activity, Basket basket) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, basket.productId);
        intent.putExtra(Constants.PRODUCT_NAME, basket.product.name);

        intent.putExtra(Constants.HISTORY_FLAG, Constants.ZERO);

        intent.putExtra(Constants.BASKET_FLAG, Constants.ONE);
        intent.putExtra(Constants.PRODUCT_PRICE, String.valueOf(basket.basketPrice));
        intent.putExtra(Constants.PRODUCT_ATTRIBUTE, basket.selectedAttributes);
        intent.putExtra(Constants.PRODUCT_COUNT, String.valueOf(basket.count));
        intent.putExtra(Constants.PRODUCT_SELECTED_COLOR, basket.selectedColorId);
        intent.putExtra(Constants.BASKET_ID, basket.id);

        activity.startActivityForResult(intent, Constants.REQUEST_CODE__BASKET_FRAGMENT);
    }

    public void navigateToUserHistoryActivity(Activity activity) {
        Intent intent = new Intent(activity, UserHistoryListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToCheckoutActivity(Activity activity) {
        Intent intent = new Intent(activity, CheckoutActivity.class);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE__BASKET_FRAGMENT);
    }

    public void navigateBackToBasketActivity(Activity activity) {
        Intent intent = new Intent(activity, CheckoutActivity.class);

        activity.setResult(Constants.RESULT_CODE__REFRESH_BASKET_LIST, intent);
    }

    public void navigateToFavouriteActivity(Activity activity) {
        Intent intent = new Intent(activity, FavouriteListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToCategoryActivity(Activity activity, String flag) {
        Intent intent = new Intent(activity, CategoryListActivity.class);
        intent.putExtra(Constants.CATEGORY_TYPE, flag);
        activity.startActivity(intent);
    }

    public void navigateToTypeFilterFragment(FragmentActivity mainActivity, String catId, String subCatId, ProductParameterHolder productParameterHolder, String name) {

        if (name.equals(Constants.FILTERING_TYPE_FILTER)) {
            Intent intent = new Intent(mainActivity, FilteringActivity.class);
            intent.putExtra(Constants.CATEGORY_ID, catId);
            if (subCatId == null || subCatId.equals("")) {
                subCatId = Constants.ZERO;
            }
            intent.putExtra(Constants.SUBCATEGORY_ID, subCatId);
            intent.putExtra(Constants.FILTERING_FILTER_NAME, name);

            mainActivity.startActivityForResult(intent, Constants.REQUEST_CODE__PRODUCT_LIST_FRAGMENT);
        } else if (name.equals(Constants.FILTERING_SPECIAL_FILTER)) {
            Intent intent = new Intent(mainActivity, FilteringActivity.class);
            intent.putExtra(Constants.FILTERING_HOLDER, productParameterHolder);


            intent.putExtra(Constants.FILTERING_FILTER_NAME, name);

            mainActivity.startActivityForResult(intent, Constants.REQUEST_CODE__PRODUCT_LIST_FRAGMENT);
        }

    }

    public void navigateBackToCommentListFragment(FragmentActivity commentDetailActivity, String comment_headerId) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COMMENT_HEADER_ID, comment_headerId);

        commentDetailActivity.setResult(Constants.RESULT_CODE__REFRESH_COMMENT_LIST, intent);
    }

    public void navigateBackToProductDetailFragment(FragmentActivity productDetailActivity, String product_id) {
        Intent intent = new Intent();
        intent.putExtra(Constants.PRODUCT_ID, product_id);

        productDetailActivity.setResult(Constants.RESULT_CODE__REFRESH_COMMENT_LIST, intent);
    }

    public void navigateBackToHomeFeaturedFragment(FragmentActivity mainActivity, String catId, String subCatId) {
        Intent intent = new Intent();

        intent.putExtra(Constants.CATEGORY_ID, catId);
        intent.putExtra(Constants.SUBCATEGORY_ID, subCatId);

        mainActivity.setResult(Constants.RESULT_CODE__CATEGORY_FILTER, intent);
    }

    public void navigateBackToHomeFeaturedFragmentFromFiltering(FragmentActivity mainActivity, ProductParameterHolder productParameterHolder) {
        Intent intent = new Intent();
        intent.putExtra(Constants.FILTERING_HOLDER, productParameterHolder);

        mainActivity.setResult(Constants.RESULT_CODE__SPECIAL_FILTER, intent);
    }

    public void navigateToCollectionProductList(FragmentActivity fragmentActivity, String id, String Name, String image_path) {
        Intent intent = new Intent(fragmentActivity, CollectionActivity.class);
        intent.putExtra(Constants.COLLECTION_ID, id);
        intent.putExtra(Constants.COLLECTION_NAME, Name);
        intent.putExtra(Constants.COLLECTION_IMAGE, image_path);

        fragmentActivity.startActivity(intent);
    }

    public void navigateToStripeActivity(Activity fragmentActivity) {
        Intent intent = new Intent(fragmentActivity, StripeActivity.class);

        fragmentActivity.startActivityForResult(intent, Constants.REQUEST_CODE__STRIPE_ACTIVITY);
    }

    public void navigateBackToCheckoutFragment(Activity activity, String stripeToken) {
        Intent intent = new Intent();

        intent.putExtra(Constants.PAYMENT_TOKEN, stripeToken);

        activity.setResult(Constants.RESULT_CODE__STRIPE_ACTIVITY, intent);
    }

    public void navigateToHomeFilteringActivity(FragmentActivity mainActivity, ProductParameterHolder productParameterHolder, String title) {

        Intent intent = new Intent(mainActivity, ProductListActivity.class);

        intent.putExtra(Constants.SHOP_TITLE, title);
        intent.putExtra(Constants.PRODUCT_PARAM_HOLDER_KEY, productParameterHolder);
        mainActivity.startActivity(intent);
    }

    public void navigateToSearchActivityCategoryFragment(FragmentActivity fragmentActivity, String fragName, String catId, String subCatId, String countryId, String cityId) {
        Intent intent = new Intent(fragmentActivity, SearchByCategoryActivity.class);
        intent.putExtra(Constants.CATEGORY_FLAG, fragName);

        if (!catId.equals(Constants.NO_DATA)) {
            intent.putExtra(Constants.CATEGORY_ID, catId);
        }

        if (!subCatId.equals(Constants.NO_DATA)) {
            intent.putExtra(Constants.SUBCATEGORY_ID, subCatId);
        }

        if (!countryId.equals(Constants.NO_DATA)) {
            intent.putExtra(Constants.COUNTRY_ID, countryId);
        }

        if (!cityId.equals(Constants.NO_DATA)) {
            intent.putExtra(Constants.CITY_ID, cityId);
        }

        fragmentActivity.startActivityForResult(intent, Constants.REQUEST_CODE__SEARCH_FRAGMENT);
    }

    public void navigateBackToSearchFragment(FragmentActivity fragmentActivity, String catId, String cat_Name) {
        Intent intent = new Intent();
        intent.putExtra(Constants.CATEGORY_NAME, cat_Name);
        intent.putExtra(Constants.CATEGORY_ID, catId);

        fragmentActivity.setResult(Constants.RESULT_CODE__SEARCH_WITH_CATEGORY, intent);
    }

    public void navigateBackToSearchFragmentFromSubCategory(FragmentActivity fragmentActivity, String sub_id, String sub_Name) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SUBCATEGORY_NAME, sub_Name);
        intent.putExtra(Constants.SUBCATEGORY_ID, sub_id);

        fragmentActivity.setResult(Constants.RESULT_CODE__SEARCH_WITH_SUBCATEGORY, intent);
    }

    public void navigateBackToSearchFragmentFromCountry(FragmentActivity fragmentActivity, String countryId, String countryName) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COUNTRY_NAME, countryName);
        intent.putExtra(Constants.COUNTRY_ID, countryId);

        fragmentActivity.setResult(Constants.RESULT_CODE__SEARCH_WITH_COUNTRY, intent);
    }

    public void navigateBackToSearchFragmentFromCity(FragmentActivity fragmentActivity, String cityId, String cityName) {
        Intent intent = new Intent();
        intent.putExtra(Constants.CITY_NAME, cityName);
        intent.putExtra(Constants.CITY_ID, cityId);

        fragmentActivity.setResult(Constants.RESULT_CODE__SEARCH_WITH_CITY, intent);
    }

    public void navigateBackToProfileFragment(FragmentActivity fragmentActivity) {
        Intent intent = new Intent();

        fragmentActivity.setResult(Constants.RESULT_CODE__LOGOUT_ACTIVATED, intent);
    }

    public void navigateToBlogList(FragmentActivity fragmentActivity) {

        Intent intent = new Intent(fragmentActivity, BlogListActivity.class);
        fragmentActivity.startActivity(intent);
    }

    public void navigateToBlogDetailActivity(FragmentActivity fragmentActivity, String blogId) {

        Intent intent = new Intent(fragmentActivity, BlogDetailActivity.class);

        intent.putExtra(Constants.BLOG_ID, blogId);

        fragmentActivity.startActivity(intent);
    }

    public void navigateToForceUpdateActivity(FragmentActivity fragmentActivity, String title, String msg) {

        Intent intent = new Intent(fragmentActivity, ForceUpdateActivity.class);

        intent.putExtra(Constants.APPINFO_FORCE_UPDATE_MSG, msg);
        intent.putExtra(Constants.APPINFO_FORCE_UPDATE_TITLE, title);

        fragmentActivity.startActivity(intent);
    }

    public void navigateToPlayStore(FragmentActivity fragmentActivity) {
//        try {
//            fragmentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.PLAYSTORE_MARKET_URL)));
//        } catch (android.content.ActivityNotFoundException anfe) {
//            fragmentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.PLAYSTORE_HTTP_URL)));
//        }
        Uri uri = Uri.parse(Config.PLAY_STORE_MARKET_URL_FIX + fragmentActivity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            fragmentActivity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            fragmentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.PLAY_STORE_HTTP_URL_FIX + fragmentActivity.getPackageName())));
        }
    }

    public void navigateToContactUs(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_CONTACTUS)) {
            try {
                ContactUsFragment fragment = new ContactUsFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToPrivacyPolicy(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_PRIVACY_POLICY
        )) {
            try {
                PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    //region Private methods
    private Boolean checkFragmentChange(RegFragments regFragments) {
        if (currentFragment != regFragments) {
            currentFragment = regFragments;
            return true;
        }

        return false;
    }

    public void navigateToPrivacyPolicyActivity(Activity activity) {
        Intent intent = new Intent(activity, PrivacyPolicyActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToPhoneVerifyFragment(MainActivity mainActivity, String number, String userName) {
        if (checkFragmentChange(RegFragments.HOME_PHONE_VERIFY)) {
            try {
                VerifyMobileFragment fragment = new VerifyMobileFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();

                Bundle args = new Bundle();
                args.putString(Constants.USER_PHONE, number);
                args.putString(Constants.USER_NAME, userName);
                fragment.setArguments(args);
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToPhoneVerifyActivity(Activity activity,String number,String userName) {
        Intent intent = new Intent(activity, VerifyMobileActivity.class);
        intent.putExtra(Constants.USER_PHONE, number);
        intent.putExtra(Constants.USER_NAME, userName);
        activity.startActivity(intent);
    }


    public void navigateToPhoneLoginFragment(MainActivity mainActivity) {
        if (checkFragmentChange(RegFragments.HOME_PHONE_LOGIN)) {
            try {
                PhoneLoginFragment fragment = new PhoneLoginFragment();
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commitAllowingStateLoss();
            } catch (Exception e) {
                Utils.psErrorLog("Error! Can't replace fragment.", e);
            }
        }
    }

    public void navigateToPhoneLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, PhoneLoginActivity.class);
        activity.startActivity(intent);
    }
    /**
     * Remark : This enum is only for MainActivity,
     * For the other fragments, no need to register here
     **/
    private enum RegFragments {
        HOME_FRAGMENT,
        HOME_USER_LOGIN,
        HOME_FB_USER_REGISTER,
        HOME_BASKET,
        HOME_USER_REGISTER,
        HOME_USER_FOGOT_PASSWORD,
        HOME_ABOUTUS,
        HOME_CONTACTUS,
        HOME_NOTI_SETTING,
        HOME_APP_INFO,
        HOME_LANGUAGE_SETTING,
        HOME_LATEST_PRODUCTS,
        HOME_DISCOUNT,
        HOME_FEATURED_PRODUCTS,
        HOME_CATEGORY,
        HOME_SUBCATEGORY,
        HOME_HOME,
        HOME_TRENDINGPRODUCTS,
        HOME_COMMENTLISTS,
        HOME_SEARCH,
        HOME_NOTIFICATION,
        HOME_PRODUCT_COLLECTION,
        HOME_TRANSACTION,
        HOME_HISTORY,
        HOME_SETTING,
        HOME_FAVOURITE,
        HOME_SHOP_LIST,
        HOME_SHOP_MENU,
        HOME_USER_EMAIL_VERIFY,
        HOME_PRIVACY_POLICY,
        HOME_PHONE_VERIFY,
        HOME_PHONE_LOGIN
    }

    //endregion
}
