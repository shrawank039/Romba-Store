package in.rombashop.romba.db;

import in.rombashop.romba.viewobject.TransactionDetail;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactionOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionDetail transactionDetail);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(TransactionDetail transactionDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTransactionOrderList(List<TransactionDetail> transactionDetailList);

    @Query("DELETE FROM TransactionDetail WHERE transactionsHeaderId = :transactionsHeaderId")
    void deleteAllTransactionOrderList(String transactionsHeaderId);

    @Query("SELECT * FROM TransactionDetail WHERE transactionsHeaderId = :transactionsHeaderId")
    LiveData<List<TransactionDetail>> getAllTransactionOrderList(String transactionsHeaderId);

    @Query("DELETE FROM TransactionDetail")
    void deleteAll();
}
