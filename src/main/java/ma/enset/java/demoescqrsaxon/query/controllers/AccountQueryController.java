package ma.enset.java.demoescqrsaxon.query.controllers;


import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAccountByIdQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAllAccountsQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> getAllAccounts() {
        List<Account> accountList = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return accountList;
    }
    @GetMapping("/byId/{id}")
    public Account getAccountById(@PathVariable String id) {
        Account account = queryGateway.query(new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();
        return account;
    }
}
