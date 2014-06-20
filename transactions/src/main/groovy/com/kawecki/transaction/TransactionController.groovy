package com.kawecki.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import static com.kawecki.transaction.Transaction.STATIC_ID

@Controller
class TransactionController {

    private final TransactionRepository transactionRepository

    @Autowired
    TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository
    }

    @RequestMapping(value = "/transaction/{transaction}", method = RequestMethod.GET)
    String updatePassword(@PathVariable String transaction) {
        transactionRepository.save(new Transaction(details: transaction))
        return 'redirect:/transaction'
    }

    @ResponseBody
    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    String currentPassword(){
        return transactionRepository.findOne(STATIC_ID).details
    }
}
