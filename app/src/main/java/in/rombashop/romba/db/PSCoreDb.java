package in.rombashop.romba.db;

import in.rombashop.romba.db.common.Converters;
import in.rombashop.romba.viewobject.AboutUs;
import in.rombashop.romba.viewobject.Basket;
import in.rombashop.romba.viewobject.Blog;
import in.rombashop.romba.viewobject.Category;
import in.rombashop.romba.viewobject.CategoryMap;
import in.rombashop.romba.viewobject.City;
import in.rombashop.romba.viewobject.Comment;
import in.rombashop.romba.viewobject.CommentDetail;
import in.rombashop.romba.viewobject.Country;
import in.rombashop.romba.viewobject.DeletedObject;
import in.rombashop.romba.viewobject.DiscountProduct;
import in.rombashop.romba.viewobject.FavouriteProduct;
import in.rombashop.romba.viewobject.FeaturedProduct;
import in.rombashop.romba.viewobject.HistoryProduct;
import in.rombashop.romba.viewobject.Image;
import in.rombashop.romba.viewobject.LatestProduct;
import in.rombashop.romba.viewobject.LikedProduct;
import in.rombashop.romba.viewobject.Noti;
import in.rombashop.romba.viewobject.PSAppInfo;
import in.rombashop.romba.viewobject.PSAppVersion;
import in.rombashop.romba.viewobject.Product;
import in.rombashop.romba.viewobject.ProductAttributeDetail;
import in.rombashop.romba.viewobject.ProductAttributeHeader;
import in.rombashop.romba.viewobject.ProductCollection;
import in.rombashop.romba.viewobject.ProductCollectionHeader;
import in.rombashop.romba.viewobject.ProductColor;
import in.rombashop.romba.viewobject.ProductListByCatId;
import in.rombashop.romba.viewobject.ProductMap;
import in.rombashop.romba.viewobject.ProductSpecs;
import in.rombashop.romba.viewobject.Rating;
import in.rombashop.romba.viewobject.RelatedProduct;
import in.rombashop.romba.viewobject.ShippingMethod;
import in.rombashop.romba.viewobject.Shop;
import in.rombashop.romba.viewobject.ShopByTagId;
import in.rombashop.romba.viewobject.ShopMap;
import in.rombashop.romba.viewobject.ShopTag;
import in.rombashop.romba.viewobject.SubCategory;
import in.rombashop.romba.viewobject.TransactionDetail;
import in.rombashop.romba.viewobject.TransactionObject;
import in.rombashop.romba.viewobject.User;
import in.rombashop.romba.viewobject.UserLogin;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


/**
 * Created by Panacea-Soft on 11/20/17.
 * Contact Email : teamps.is.cool@gmail.com
 */

@Database(entities = {
        Image.class,
        Category.class,
        User.class,
        UserLogin.class,
        AboutUs.class,
        Product.class,
        LatestProduct.class,
        DiscountProduct.class,
        FeaturedProduct.class,
        SubCategory.class,
        ProductListByCatId.class,
        Comment.class,
        CommentDetail.class,
        ProductColor.class,
        ProductSpecs.class,
        RelatedProduct.class,
        FavouriteProduct.class,
        LikedProduct.class,
        ProductAttributeHeader.class,
        ProductAttributeDetail.class,
        Noti.class,
        TransactionObject.class,
        ProductCollectionHeader.class,
        ProductCollection.class,
        TransactionDetail.class,
        Basket.class,
        HistoryProduct.class,
        Shop.class,
        ShopTag.class,
        Blog.class,
        Rating.class,
        ShippingMethod.class,
        ShopByTagId.class,
        ProductMap.class,
        ShopMap.class,
        CategoryMap.class,
        PSAppInfo.class,
        PSAppVersion.class,
        DeletedObject.class,
        Country.class,
        City.class

}, version = 7, exportSchema = false)
//V2.4 = DBV 7
//V2.3 = DBV 7
//V2.2 = DBV 7
//V2.1 = DBV 7
//V2.0 = DBV 7
//V1.9 = DBV 7
//V1.8 = DBV 7
//V1.7 = DBV 6
//V1.6 = DBV 5
//V1.5 = DBV 4
//V1.4 = DBV 3
//V1.3 = DBV 2


@TypeConverters({Converters.class})

public abstract class PSCoreDb extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public ProductColorDao productColorDao();

    abstract public ProductSpecsDao productSpecsDao();

    abstract public ProductAttributeHeaderDao productAttributeHeaderDao();

    abstract public ProductAttributeDetailDao productAttributeDetailDao();

    abstract public BasketDao basketDao();

    abstract public HistoryDao historyDao();

    abstract public AboutUsDao aboutUsDao();

    abstract public ImageDao imageDao();

    abstract public CountryDao countryDao();

    abstract public CityDao cityDao();

    abstract public RatingDao ratingDao();

    abstract public CommentDao commentDao();

    abstract public CommentDetailDao commentDetailDao();

    abstract public ProductDao productDao();

    abstract public CategoryDao categoryDao();

    abstract public SubCategoryDao subCategoryDao();

    abstract public NotificationDao notificationDao();

    abstract public ProductCollectionDao productCollectionDao();

    abstract public TransactionDao transactionDao();

    abstract public TransactionOrderDao transactionOrderDao();

    abstract public ShopDao shopDao();

    abstract public BlogDao blogDao();

    abstract public ShippingMethodDao shippingMethodDao();

    abstract public ProductMapDao productMapDao();

    abstract public CategoryMapDao categoryMapDao();

    abstract public PSAppInfoDao psAppInfoDao();

    abstract public PSAppVersionDao psAppVersionDao();

    abstract public DeletedObjectDao deletedObjectDao();


//    /**
//     * Migrate from:
//     * version 1 - using Room
//     * to
//     * version 2 - using Room where the {@link } has an extra field: addedDateStr
//     */
//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE news "
//                    + " ADD COLUMN addedDateStr INTEGER NOT NULL DEFAULT 0");
//        }
//    };

    /* More migration write here */
}