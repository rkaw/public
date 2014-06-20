package com.kawecki.transaction

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository extends JpaRepository<Transaction, String> {
}
