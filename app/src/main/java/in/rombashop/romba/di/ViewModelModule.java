package in.rombashop.romba.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.rombashop.romba.viewmodel.aboutus.AboutUsViewModel;
import in.rombashop.romba.viewmodel.blog.BlogViewModel;
import in.rombashop.romba.viewmodel.category.CategoryViewModel;
import in.rombashop.romba.viewmodel.city.CityViewModel;
import in.rombashop.romba.viewmodel.clearalldata.ClearAllDataViewModel;
import in.rombashop.romba.viewmodel.collection.ProductCollectionProductViewModel;
import in.rombashop.romba.viewmodel.collection.ProductCollectionViewModel;
import in.rombashop.romba.viewmodel.comment.CommentDetailListViewModel;
import in.rombashop.romba.viewmodel.comment.CommentListViewModel;
import in.rombashop.romba.viewmodel.common.NotificationViewModel;
import in.rombashop.romba.viewmodel.common.PSNewsViewModelFactory;
import in.rombashop.romba.viewmodel.contactus.ContactUsViewModel;
import in.rombashop.romba.viewmodel.country.CountryViewModel;
import in.rombashop.romba.viewmodel.coupondiscount.CouponDiscountViewModel;
import in.rombashop.romba.viewmodel.homelist.HomeFeaturedProductViewModel;
import in.rombashop.romba.viewmodel.homelist.HomeLatestProductViewModel;
import in.rombashop.romba.viewmodel.homelist.HomeSearchProductViewModel;
import in.rombashop.romba.viewmodel.homelist.HomeTrendingCategoryListViewModel;
import in.rombashop.romba.viewmodel.homelist.HomeTrendingProductViewModel;
import in.rombashop.romba.viewmodel.image.ImageViewModel;
import in.rombashop.romba.viewmodel.notification.NotificationsViewModel;
import in.rombashop.romba.viewmodel.paypal.PaypalViewModel;
import in.rombashop.romba.viewmodel.product.BasketViewModel;
import in.rombashop.romba.viewmodel.product.HistoryProductViewModel;
import in.rombashop.romba.viewmodel.product.ProductAttributeDetailViewModel;
import in.rombashop.romba.viewmodel.product.ProductAttributeHeaderViewModel;
import in.rombashop.romba.viewmodel.product.ProductColorViewModel;
import in.rombashop.romba.viewmodel.product.ProductDetailViewModel;
import in.rombashop.romba.viewmodel.product.ProductFavouriteViewModel;
import in.rombashop.romba.viewmodel.product.ProductListByCatIdViewModel;
import in.rombashop.romba.viewmodel.product.ProductRelatedViewModel;
import in.rombashop.romba.viewmodel.product.ProductSpecsViewModel;
import in.rombashop.romba.viewmodel.product.TouchCountViewModel;
import in.rombashop.romba.viewmodel.apploading.AppLoadingViewModel;
import in.rombashop.romba.viewmodel.rating.RatingViewModel;
import in.rombashop.romba.viewmodel.shippingmethod.ShippingMethodViewModel;
import in.rombashop.romba.viewmodel.shop.ShopViewModel;
import in.rombashop.romba.viewmodel.subcategory.SubCategoryViewModel;
import in.rombashop.romba.viewmodel.transaction.TransactionListViewModel;
import in.rombashop.romba.viewmodel.transaction.TransactionOrderViewModel;
import in.rombashop.romba.viewmodel.user.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by matrixdeveloper on 11/16/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

@Module
abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PSNewsViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutUsViewModel.class)
    abstract ViewModel bindAboutUsViewModel(AboutUsViewModel aboutUsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel.class)
    abstract ViewModel bindImageViewModel(ImageViewModel imageViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RatingViewModel.class)
    abstract ViewModel bindRatingViewModel(RatingViewModel ratingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    abstract ViewModel bindNotificationViewModel(NotificationViewModel notificationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel.class)
    abstract ViewModel bindCountryViewModel(CountryViewModel countryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CityViewModel.class)
    abstract ViewModel bindCityViewModel(CityViewModel cityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContactUsViewModel.class)
    abstract ViewModel bindContactUsViewModel(ContactUsViewModel contactUsViewModel);

  /*  @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel.class)
    abstract ViewModel bindProductViewModel(ProductViewModel productViewModel);*/

    @Binds
    @IntoMap
    @ViewModelKey(CommentListViewModel.class)
    abstract ViewModel bindCommentViewModel(CommentListViewModel commentListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommentDetailListViewModel.class)
    abstract ViewModel bindCommentDetailViewModel(CommentDetailListViewModel commentDetailListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel.class)
    abstract ViewModel bindProductDetailViewModel(ProductDetailViewModel productDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TouchCountViewModel.class)
    abstract ViewModel bindTouchCountViewModel(TouchCountViewModel touchCountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductColorViewModel.class)
    abstract ViewModel bindProductColorViewModel(ProductColorViewModel productColorViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductSpecsViewModel.class)
    abstract ViewModel bindProductSpecsViewModel(ProductSpecsViewModel productSpecsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BasketViewModel.class)
    abstract ViewModel bindBasketViewModel(BasketViewModel basketViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryProductViewModel.class)
    abstract ViewModel bindHistoryProductViewModel(HistoryProductViewModel historyProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductAttributeHeaderViewModel.class)
    abstract ViewModel bindProductAttributeHeaderViewModel(ProductAttributeHeaderViewModel productAttributeHeaderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductAttributeDetailViewModel.class)
    abstract ViewModel bindProductAttributeDetailViewModel(ProductAttributeDetailViewModel productAttributeDetailViewModel);
/*

    @Binds
    @IntoMap
    @ViewModelKey(DiscountProductViewModel.class)
    abstract ViewModel bindDiscountProductViewModel(DiscountProductViewModel discountProductViewModel);
*/

    @Binds
    @IntoMap
    @ViewModelKey(ProductRelatedViewModel.class)
    abstract ViewModel bindRelatedProductViewModel(ProductRelatedViewModel productRelatedViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductFavouriteViewModel.class)
    abstract ViewModel bindProductFavouriteViewModel(ProductFavouriteViewModel productFavouriteViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductLikedViewModel.class)
//    abstract ViewModel bindProductLikedViewModel(ProductLikedViewModel productLikedViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    abstract ViewModel bindCategoryViewModel(CategoryViewModel categoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SubCategoryViewModel.class)
    abstract ViewModel bindSubCategoryViewModel(SubCategoryViewModel subCategoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductListByCatIdViewModel.class)
    abstract ViewModel bindProductListByCatIdViewModel(ProductListByCatIdViewModel productListByCatIdViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(TrendingProductsViewModel.class)
//    abstract ViewModel bindTrendingProductsViewModel(TrendingProductsViewModel trendingProductsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeLatestProductViewModel.class)
    abstract ViewModel bindHomeLatestProductViewModel(HomeLatestProductViewModel homeLatestProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeSearchProductViewModel.class)
    abstract ViewModel bindHomeFeaturedProductViewModel(HomeSearchProductViewModel homeSearchProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeTrendingProductViewModel.class)
    abstract ViewModel bindHomeTrendingProductViewModel(HomeTrendingProductViewModel homeTrendingProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeFeaturedProductViewModel.class)
    abstract ViewModel bindHomeCategory1ProductViewModel(HomeFeaturedProductViewModel homeFeaturedProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductCollectionViewModel.class)
    abstract ViewModel bindProductCollectionViewModel(ProductCollectionViewModel productCollectionViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel.class)
    abstract ViewModel bindNotificationListViewModel(NotificationsViewModel notificationListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TransactionListViewModel.class)
    abstract ViewModel bindTransactionListViewModel(TransactionListViewModel transactionListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TransactionOrderViewModel.class)
    abstract ViewModel bindTransactionOrderViewModel(TransactionOrderViewModel transactionOrderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeTrendingCategoryListViewModel.class)
    abstract ViewModel bindHomeTrendingCategoryListViewModel(HomeTrendingCategoryListViewModel transactionListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductCollectionProductViewModel.class)
    abstract ViewModel bindProductCollectionProductViewModel(ProductCollectionProductViewModel productCollectionProductViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ShopViewModel.class)
    abstract ViewModel bindShopViewModel(ShopViewModel shopViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel.class)
    abstract ViewModel bindNewsFeedViewModel(BlogViewModel blogViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ShippingMethodViewModel.class)
    abstract ViewModel bindShippingMethodViewModel(ShippingMethodViewModel shippingMethodViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PaypalViewModel.class)
    abstract ViewModel bindPaypalViewModel(PaypalViewModel paypalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CouponDiscountViewModel.class)
    abstract ViewModel bindCouponDiscountViewModel(CouponDiscountViewModel couponDiscountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AppLoadingViewModel.class)
    abstract ViewModel bindPSAppInfoViewModel(AppLoadingViewModel psAppInfoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ClearAllDataViewModel.class)
    abstract ViewModel bindClearAllDataViewModel(ClearAllDataViewModel clearAllDataViewModel);
}


