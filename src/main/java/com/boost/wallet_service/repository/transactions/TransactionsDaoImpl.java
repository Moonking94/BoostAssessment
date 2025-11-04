package com.boost.wallet_service.repository.transactions;

import com.boost.wallet_service.dto.TransactionBean;
import com.boost.wallet_service.repository.AbstractDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.boost.wallet_service.repository.transactions.TransactionSearchSqlConstant.NATIVE_GET_TRANSACTION_HISTORY;
import static com.boost.wallet_service.repository.transactions.TransactionSearchSqlConstant.QUERY_PARAMS_email;

public class TransactionsDaoImpl extends AbstractDao implements TransactionsDaoCustom {

    private static final Logger log = LoggerFactory.getLogger(TransactionsDaoImpl.class);

    @PersistenceContext
    private EntityManager em;

    public TransactionsDaoImpl() {}

    @Override
    public List<TransactionBean> getTransactionHistory(String email) {
        Query query = em.createNativeQuery(NATIVE_GET_TRANSACTION_HISTORY);
        query.setParameter(QUERY_PARAMS_email, email);
        List<Object[]> results = query.getResultList();
        List<TransactionBean> list = new ArrayList<>();
        for (Object[] row : results) {
            TransactionBean bean = new TransactionBean();
            bean.setTransactionId((UUID) row[0]);
            bean.setTransactionType((String) row[1]);
            bean.setAmount((BigDecimal) row[2]);
            bean.setTimestamp(((Timestamp) row[3]).toLocalDateTime());
            bean.setFromEmail((String) row[4]);
            bean.setToEmail((String) row[5]);

            list.add(bean);
        }

        return list;
    }

}
