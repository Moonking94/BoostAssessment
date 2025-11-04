package com.boost.wallet_service.repository.transactions;

public class TransactionSearchSqlConstant {

    public static String QUERY_PARAMS_email = "email";

    public static String NATIVE_GET_TRANSACTION_HISTORY = """
            select 
                row_id
                , transaction_type
                , amount
                , transaction_date
                , (select email from wallet.users where row_id = source_user_id) as sourceEmail
                , (select email from wallet.users where row_id = destination_user_id) as destinationEmail
            from wallet.transactions
            where 1=1
            	and (source_user_id = (select row_id from wallet.users where email = :{email})
            		or
            		destination_user_id = (select row_id from wallet.users where email = :{email})
            	)
            order by transaction_date desc
            """
            .replace("{email}", QUERY_PARAMS_email);

}
