package com.kawecki.transaction

import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
abstract class IntegrationSpec extends Specification {
}
