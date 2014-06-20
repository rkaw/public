package com.kawecki.transaction

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import static com.kawecki.transaction.Transaction.STATIC_ID

class ApplicationSpec extends IntegrationSpec {

    @Autowired SessionFactory sessionFactory
    @Autowired TransactionRepository transactionRepository

    def 'setup'() {
        transactionRepository.save(new Transaction(details: 'my_transaction'))
        sessionFactory.getCurrentSession().flush()
    }

    def 'should save object in the db'() {
        expect:
            transactionRepository.findOne(STATIC_ID).details == 'my_transaction'
    }
}
