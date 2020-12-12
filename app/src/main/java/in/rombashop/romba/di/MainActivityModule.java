package in.rombashop.romba.di;


import com.facebook.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import in.rombashop.romba.AddAdress;
import in.rombashop.romba.AddressActivity;
import in.rombashop.romba.ImageViewActivity;
import in.rombashop.romba.MainActivity;
import in.rombashop.romba.PaymentAddressFragment;
import in.rombashop.romba.ShippingAddressFragment;
import in.rombashop.romba.ui.apploading.AppLoadingActivity;
import in.rombashop.romba.ui.apploading.AppLoadingFragment;
import in.rombashop.romba.ui.basket.BasketListActivity;
import in.rombashop.romba.ui.basket.BasketListFragment;
import in.rombashop.romba.ui.blog.detail.BlogDetailActivity;
import in.rombashop.romba.ui.blog.detail.BlogDetailFragment;
import in.rombashop.romba.ui.blog.list.BlogListActivity;
import in.rombashop.romba.ui.blog.list.BlogListFragment;
import in.rombashop.romba.ui.category.CategoryListActivity;
import in.rombashop.romba.ui.category.CategoryListFragment;
import in.rombashop.romba.ui.category.TrendingCategoryFragment;
import in.rombashop.romba.ui.checkout.AddressSelectionFragment;
import in.rombashop.romba.ui.checkout.CheckoutActivity;
import in.rombashop.romba.ui.checkout.CheckoutFragment1;
import in.rombashop.romba.ui.checkout.CheckoutFragment2;
import in.rombashop.romba.ui.checkout.CheckoutFragment3;
import in.rombashop.romba.ui.checkout.CheckoutStatusFragment;
import in.rombashop.romba.ui.collection.CollectionActivity;
import in.rombashop.romba.ui.collection.CollectionFragment;
import in.rombashop.romba.ui.collection.productCollectionHeader.ProductCollectionHeaderListFragment;
import in.rombashop.romba.ui.comment.detail.CommentDetailActivity;
import in.rombashop.romba.ui.comment.detail.CommentDetailFragment;
import in.rombashop.romba.ui.comment.list.CommentListActivity;
import in.rombashop.romba.ui.comment.list.CommentListFragment;
import in.rombashop.romba.ui.contactus.ContactUsFragment;
import in.rombashop.romba.ui.forceupdate.ForceUpdateActivity;
import in.rombashop.romba.ui.forceupdate.ForceUpdateFragment;
import in.rombashop.romba.ui.gallery.GalleryActivity;
import in.rombashop.romba.ui.gallery.GalleryFragment;
import in.rombashop.romba.ui.gallery.detail.GalleryDetailActivity;
import in.rombashop.romba.ui.gallery.detail.GalleryDetailFragment;
import in.rombashop.romba.ui.language.LanguageFragment;
import in.rombashop.romba.ui.notification.detail.NotificationActivity;
import in.rombashop.romba.ui.notification.detail.NotificationFragment;
import in.rombashop.romba.ui.notification.list.NotificationListActivity;
import in.rombashop.romba.ui.notification.list.NotificationListFragment;
import in.rombashop.romba.ui.notification.setting.NotificationSettingFragment;
import in.rombashop.romba.ui.privacyandpolicy.PrivacyPolicyActivity;
import in.rombashop.romba.ui.privacyandpolicy.PrivacyPolicyFragment;
import in.rombashop.romba.ui.product.MainFragment;
import in.rombashop.romba.ui.product.detail.ProductActivity;
import in.rombashop.romba.ui.product.detail.ProductDetailFragment;
import in.rombashop.romba.ui.product.favourite.FavouriteListActivity;
import in.rombashop.romba.ui.product.favourite.FavouriteListFragment;
import in.rombashop.romba.ui.product.filtering.CategoryFilterFragment;
import in.rombashop.romba.ui.product.filtering.FilterFragment;
import in.rombashop.romba.ui.product.filtering.FilteringActivity;
import in.rombashop.romba.ui.product.history.HistoryFragment;
import in.rombashop.romba.ui.product.history.UserHistoryListActivity;
import in.rombashop.romba.ui.product.list.ProductListActivity;
import in.rombashop.romba.ui.product.list.ProductListFragment;
import in.rombashop.romba.ui.product.productbycatId.ProductListByCatIdActivity;
import in.rombashop.romba.ui.product.productbycatId.ProductListByCatIdFragment;
import in.rombashop.romba.ui.product.search.SearchByCategoryActivity;
import in.rombashop.romba.ui.product.search.SearchCategoryFragment;
import in.rombashop.romba.ui.product.search.SearchCityListFragment;
import in.rombashop.romba.ui.product.search.SearchCountryListFragment;
import in.rombashop.romba.ui.product.search.SearchFragment;
import in.rombashop.romba.ui.product.search.SearchSubCategoryFragment;
import in.rombashop.romba.ui.rating.RatingListActivity;
import in.rombashop.romba.ui.rating.RatingListFragment;
import in.rombashop.romba.ui.setting.AppInfoActivity;
import in.rombashop.romba.ui.setting.AppInfoFragment;
import in.rombashop.romba.ui.setting.NotificationSettingActivity;
import in.rombashop.romba.ui.setting.SettingActivity;
import in.rombashop.romba.ui.setting.SettingFragment;
import in.rombashop.romba.ui.shop.ShopProfileFragment;
import in.rombashop.romba.ui.stripe.StripeActivity;
import in.rombashop.romba.ui.stripe.StripeFragment;
import in.rombashop.romba.ui.terms.TermsAndConditionsActivity;
import in.rombashop.romba.ui.terms.TermsAndConditionsFragment;
import in.rombashop.romba.ui.transaction.detail.TransactionActivity;
import in.rombashop.romba.ui.transaction.detail.TransactionFragment;
import in.rombashop.romba.ui.transaction.list.TransactionListActivity;
import in.rombashop.romba.ui.transaction.list.TransactionListFragment;
import in.rombashop.romba.ui.user.MobileVerifyActivity;
import in.rombashop.romba.ui.user.PasswordChangeActivity;
import in.rombashop.romba.ui.user.PasswordChangeFragment;
import in.rombashop.romba.ui.user.ProfileEditActivity;
import in.rombashop.romba.ui.user.ProfileEditFragment;
import in.rombashop.romba.ui.user.ProfileFragment;
import in.rombashop.romba.ui.user.UserForgotPasswordActivity;
import in.rombashop.romba.ui.user.UserForgotPasswordFragment;
import in.rombashop.romba.ui.user.UserLoginActivity;
import in.rombashop.romba.ui.user.UserLoginFragment;
import in.rombashop.romba.ui.user.UserRegisterActivity;
import in.rombashop.romba.ui.user.UserRegisterFragment;
import in.rombashop.romba.ui.user.phonelogin.LoginActivity;
import in.rombashop.romba.ui.user.phonelogin.PhoneLoginActivity;
import in.rombashop.romba.ui.user.phonelogin.PhoneLoginFragment;
import in.rombashop.romba.ui.user.verifyemail.VerifyEmailActivity;
import in.rombashop.romba.ui.user.verifyemail.VerifyEmailFragment;
import in.rombashop.romba.ui.user.verifyphone.VerifyMobileActivity;
import in.rombashop.romba.ui.user.verifyphone.VerifyMobileFragment;

/**
 * Created by Panacea-Soft on 11/15/17.
 * Contact Email : teamps.is.cool@gmail.com
 */


@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = TransactionModule.class)
    abstract TransactionListActivity contributeTransactionActivity();

    @ContributesAndroidInjector(modules = FavouriteListModule.class)
    abstract FavouriteListActivity contributeFavouriteListActivity();

    @ContributesAndroidInjector(modules = UserHistoryModule.class)
    abstract UserHistoryListActivity contributeUserHistoryListActivity();

    @ContributesAndroidInjector(modules = UserRegisterModule.class)
    abstract UserRegisterActivity contributeUserRegisterActivity();

    @ContributesAndroidInjector(modules = UserForgotPasswordModule.class)
    abstract UserForgotPasswordActivity contributeUserForgotPasswordActivity();

    @ContributesAndroidInjector(modules = UserLoginModule.class)
    abstract UserLoginActivity contributeUserLoginActivity();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = CheckoutActivityModule.class)
    abstract AddressActivity contributeAddressActivity();

    @ContributesAndroidInjector(modules = CheckoutActivityModule.class)
    abstract AddAdress contributeAddAddressActivity();

    @ContributesAndroidInjector(modules = UserRegisterModule.class)
    abstract MobileVerifyActivity contributeRegisterActivity();

    @ContributesAndroidInjector(modules = PasswordChangeModule.class)
    abstract PasswordChangeActivity contributePasswordChangeActivity();

    @ContributesAndroidInjector(modules = ProductListByCatIdModule.class)
    abstract ProductListByCatIdActivity productListByCatIdActivity();

    @ContributesAndroidInjector(modules = FilteringModule.class)
    abstract FilteringActivity filteringActivity();

    @ContributesAndroidInjector(modules = CommentDetailModule.class)
    abstract CommentDetailActivity commentDetailActivity();

    @ContributesAndroidInjector(modules = DiscountDetailModule.class)
    abstract ProductActivity discountDetailActivity();

    @ContributesAndroidInjector(modules = NotificationModule.class)
    abstract NotificationListActivity notificationActivity();

    @ContributesAndroidInjector(modules = HomeFilteringActivityModule.class)
    abstract ProductListActivity contributehomeFilteringActivity();

    @ContributesAndroidInjector(modules = NotificationDetailModule.class)
    abstract NotificationActivity notificationDetailActivity();

    @ContributesAndroidInjector(modules = TransactionDetailModule.class)
    abstract TransactionActivity transactionDetailActivity();

    @ContributesAndroidInjector(modules = CheckoutActivityModule.class)
    abstract CheckoutActivity checkoutActivity();

    @ContributesAndroidInjector(modules = CommentListActivityModule.class)
    abstract CommentListActivity commentListActivity();

    @ContributesAndroidInjector(modules = BasketlistActivityModule.class)
    abstract BasketListActivity basketListActivity();

    @ContributesAndroidInjector(modules = GalleryDetailActivityModule.class)
    abstract GalleryDetailActivity galleryDetailActivity();

    @ContributesAndroidInjector(modules = GalleryActivityModule.class)
    abstract GalleryActivity galleryActivity();

    @ContributesAndroidInjector(modules = GalleryActivityModule.class)
    abstract ImageViewActivity imageViewActivity();

    @ContributesAndroidInjector(modules = SearchByCategoryActivityModule.class)
    abstract SearchByCategoryActivity searchByCategoryActivity();

    @ContributesAndroidInjector(modules = TermsAndConditionsModule.class)
    abstract TermsAndConditionsActivity termsAndConditionsActivity();

    @ContributesAndroidInjector(modules = EditSettingModule.class)
    abstract SettingActivity editSettingActivity();

    @ContributesAndroidInjector(modules = LanguageChangeModule.class)
    abstract NotificationSettingActivity languageChangeActivity();

    @ContributesAndroidInjector(modules = ProfileEditModule.class)
    abstract ProfileEditActivity contributeProfileEditActivity();

    @ContributesAndroidInjector(modules = AppInfoModule.class)
    abstract AppInfoActivity AppInfoActivity();

    @ContributesAndroidInjector(modules = CategoryListActivityAppInfoModule.class)
    abstract CategoryListActivity categoryListActivity();

    @ContributesAndroidInjector(modules = RatingListActivityModule.class)
    abstract RatingListActivity ratingListActivity();

    @ContributesAndroidInjector(modules = CollectionModule.class)
    abstract CollectionActivity collectionActivity();

    @ContributesAndroidInjector(modules = StripeModule.class)
    abstract StripeActivity stripeActivity();

    @ContributesAndroidInjector(modules = BlogListActivityModule.class)
    abstract BlogListActivity BlogListActivity();

    @ContributesAndroidInjector(modules = BlogDetailModule.class)
    abstract BlogDetailActivity blogDetailActivity();

    @ContributesAndroidInjector(modules = forceUpdateModule.class)
    abstract ForceUpdateActivity forceUpdateActivity();

    @ContributesAndroidInjector(modules = appLoadingModule.class)
    abstract AppLoadingActivity appLoadingActivity();

    @ContributesAndroidInjector(modules = VerifyEmailModule.class)
    abstract VerifyEmailActivity contributeVerifyEmailActivity();

    @ContributesAndroidInjector(modules = PrivacyPolicyActivityModule.class)
    abstract PrivacyPolicyActivity contributePrivacyPolicyActivity();

    @ContributesAndroidInjector(modules = PhoneLoginActivityModule.class)
    abstract PhoneLoginActivity contributePhoneLoginActivity();

    @ContributesAndroidInjector(modules = VerifyMobileModule.class)
    abstract VerifyMobileActivity contributeVerifyMobileActivity();
}

@Module
abstract class CheckoutActivityModule {

    @ContributesAndroidInjector
    abstract CheckoutFragment1 checkoutFragment1();

    @ContributesAndroidInjector
    abstract LanguageFragment languageFragment();

    @ContributesAndroidInjector
    abstract CheckoutFragment2 checkoutFragment2();

    @ContributesAndroidInjector
    abstract CheckoutFragment3 checkoutFragment3();

    @ContributesAndroidInjector
    abstract CheckoutStatusFragment checkoutStatusFragment();

    @ContributesAndroidInjector
    abstract ShippingAddressFragment contributeShippingAddressFragment();

    @ContributesAndroidInjector
    abstract PaymentAddressFragment contributePaymentAddressFragment();

    @ContributesAndroidInjector
    abstract AddressSelectionFragment contributeAddressSelectionFragment();
}

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract ProductListFragment contributefeaturedProductFragment();

    @ContributesAndroidInjector
    abstract MainFragment contributeSelectedShopFragment();

    @ContributesAndroidInjector
    abstract CategoryListFragment contributeCategoryFragment();

    @ContributesAndroidInjector
    abstract CategoryFilterFragment contributeTypeFilterFragment();

    @ContributesAndroidInjector
    abstract ProductCollectionHeaderListFragment contributeProductCollectionHeaderListFragment();

    @ContributesAndroidInjector
    abstract ContactUsFragment contributeContactUsFragment();

    @ContributesAndroidInjector
    abstract UserLoginFragment contributeUserLoginFragment();

    @ContributesAndroidInjector
    abstract UserForgotPasswordFragment contributeUserForgotPasswordFragment();

    @ContributesAndroidInjector
    abstract UserRegisterFragment contributeUserRegisterFragment();

    @ContributesAndroidInjector
    abstract NotificationSettingFragment contributeNotificationSettingFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract LanguageFragment contributeLanguageFragment();

    @ContributesAndroidInjector
    abstract FavouriteListFragment contributeFavouriteListFragment();

    @ContributesAndroidInjector
    abstract TransactionListFragment contributTransactionListFragment();

    @ContributesAndroidInjector
    abstract SettingFragment contributEditSettingFragment();

    @ContributesAndroidInjector
    abstract HistoryFragment historyFragment();

    @ContributesAndroidInjector
    abstract ShopProfileFragment contributeAboutUsFragmentFragment();

    @ContributesAndroidInjector
    abstract BasketListFragment basketFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract NotificationListFragment contributeNotificationFragment();

    @ContributesAndroidInjector
    abstract AppInfoFragment contributeAppInfoFragment();

    @ContributesAndroidInjector
    abstract VerifyEmailFragment contributeVerifyEmailFragment();

    @ContributesAndroidInjector
    abstract PrivacyPolicyFragment contributePrivacyPolicyFragment();

    @ContributesAndroidInjector
    abstract PhoneLoginFragment contributePhoneLoginFragment();

    @ContributesAndroidInjector
    abstract VerifyMobileFragment contributeVerifyMobileFragment();
}


@Module
abstract class ProfileEditModule {
    @ContributesAndroidInjector
    abstract ProfileEditFragment contributeProfileEditFragment();
}

@Module
abstract class TransactionModule {
    @ContributesAndroidInjector
    abstract TransactionListFragment contributeTransactionListFragment();
}


@Module
abstract class FavouriteListModule {
    @ContributesAndroidInjector
    abstract FavouriteListFragment contributeFavouriteFragment();
}

@Module
abstract class UserRegisterModule {
    @ContributesAndroidInjector
    abstract UserRegisterFragment contributeUserRegisterFragment();
}

@Module
abstract class UserForgotPasswordModule {
    @ContributesAndroidInjector
    abstract UserForgotPasswordFragment contributeUserForgotPasswordFragment();
}

@Module
abstract class UserLoginModule {
    @ContributesAndroidInjector
    abstract UserLoginFragment contributeUserLoginFragment();
}

@Module
abstract class LoginModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
}

@Module
abstract class PasswordChangeModule {
    @ContributesAndroidInjector
    abstract PasswordChangeFragment contributePasswordChangeFragment();
}

@Module
abstract class CommentDetailModule {
    @ContributesAndroidInjector
    abstract CommentDetailFragment commentDetailFragment();
}

@Module
abstract class DiscountDetailModule {
    @ContributesAndroidInjector
    abstract ProductDetailFragment discountDetailFragment();
}

@Module
abstract class NotificationModule {
    @ContributesAndroidInjector
    abstract NotificationListFragment notificationFragment();


}


@Module
abstract class NotificationDetailModule {
    @ContributesAndroidInjector
    abstract NotificationFragment notificationDetailFragment();
}

@Module
abstract class TransactionDetailModule {
    @ContributesAndroidInjector
    abstract TransactionFragment transactionDetailFragment();
}

@Module
abstract class UserHistoryModule {
    @ContributesAndroidInjector
    abstract HistoryFragment contributeHistoryFragment();
}

@Module
abstract class AppInfoModule {
    @ContributesAndroidInjector
    abstract AppInfoFragment contributeAppInfoFragment();
}

@Module
abstract class CategoryListActivityAppInfoModule {
    @ContributesAndroidInjector
    abstract CategoryListFragment contributeCategoryFragment();

    @ContributesAndroidInjector
    abstract TrendingCategoryFragment contributeTrendingCategoryFragment();
}

@Module
abstract class RatingListActivityModule {
    @ContributesAndroidInjector
    abstract RatingListFragment contributeRatingListFragment();
}

@Module
abstract class TermsAndConditionsModule {
    @ContributesAndroidInjector
    abstract TermsAndConditionsFragment TermsAndConditionsFragment();
}

@Module
abstract class EditSettingModule {
    @ContributesAndroidInjector
    abstract SettingFragment EditSettingFragment();
}

@Module
abstract class LanguageChangeModule {
    @ContributesAndroidInjector
    abstract NotificationSettingFragment notificationSettingFragment();
}

@Module
abstract class EditProfileModule {
    @ContributesAndroidInjector
    abstract ProfileFragment ProfileFragment();
}

@Module
abstract class ProductListByCatIdModule {
    @ContributesAndroidInjector
    abstract ProductListByCatIdFragment contributeProductListByCatIdFragment();

}

@Module
abstract class FilteringModule {

    @ContributesAndroidInjector
    abstract CategoryFilterFragment contributeTypeFilterFragment();

    @ContributesAndroidInjector
    abstract FilterFragment contributeSpecialFilteringFragment();
}

@Module
abstract class HomeFilteringActivityModule {
    @ContributesAndroidInjector
    abstract ProductListFragment contributefeaturedProductFragment();

    @ContributesAndroidInjector
    abstract CategoryListFragment contributeCategoryFragment();

    @ContributesAndroidInjector
    abstract CategoryFilterFragment contributeTypeFilterFragment();

    @ContributesAndroidInjector
    abstract CollectionFragment contributeCollectionFragment();
}

@Module
abstract class CommentListActivityModule {
    @ContributesAndroidInjector
    abstract CommentListFragment contributeCommentListFragment();
}

@Module
abstract class BasketlistActivityModule {
    @ContributesAndroidInjector
    abstract BasketListFragment contributeBasketListFragment();
}

@Module
abstract class GalleryDetailActivityModule {
    @ContributesAndroidInjector
    abstract GalleryDetailFragment contributeGalleryDetailFragment();
}

@Module
abstract class GalleryActivityModule {
    @ContributesAndroidInjector
    abstract GalleryFragment contributeGalleryFragment();
}

@Module
abstract class SearchByCategoryActivityModule {

    @ContributesAndroidInjector
    abstract SearchCategoryFragment contributeSearchCategoryFragment();

    @ContributesAndroidInjector
    abstract SearchSubCategoryFragment contributeSearchSubCategoryFragment();

    @ContributesAndroidInjector
    abstract SearchCityListFragment contributeSearchCityListFragment();

    @ContributesAndroidInjector
    abstract SearchCountryListFragment contributeSearchCountryListFragment();

}

@Module
abstract class CollectionModule {

    @ContributesAndroidInjector
    abstract CollectionFragment contributeCollectionFragment();

}

@Module
abstract class StripeModule {

    @ContributesAndroidInjector
    abstract StripeFragment contributeStripeFragment();

}

@Module
abstract class BlogListActivityModule {

    @ContributesAndroidInjector
    abstract BlogListFragment contributeBlogListFragment();

}

@Module
abstract class BlogDetailModule {

    @ContributesAndroidInjector
    abstract BlogDetailFragment contributeBlogDetailFragment();
}

@Module
abstract class forceUpdateModule {

    @ContributesAndroidInjector
    abstract ForceUpdateFragment contributeForceUpdateFragment();
}

@Module
abstract class appLoadingModule {

    @ContributesAndroidInjector
    abstract AppLoadingFragment contributeAppLoadingFragment();
}

@Module
abstract class VerifyEmailModule {
    @ContributesAndroidInjector
    abstract VerifyEmailFragment contributeVerifyEmailFragment();
}

@Module
abstract class PrivacyPolicyActivityModule {
    @ContributesAndroidInjector
    abstract PrivacyPolicyFragment contributePrivacyPolicyFragment();
}

@Module
abstract class PhoneLoginActivityModule {
    @ContributesAndroidInjector
    abstract PhoneLoginFragment cameraPhoneLoginFragment();
}

@Module
abstract class VerifyMobileModule {
    @ContributesAndroidInjector
    abstract VerifyMobileFragment contributeVerifyMobileFragment();
}

