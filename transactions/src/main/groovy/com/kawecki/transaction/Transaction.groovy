package com.kawecki.transaction

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'TRANSACTION')
class Transaction {

    static final STATIC_ID = '12345'

    @Id
    String id = STATIC_ID

    String details

}
