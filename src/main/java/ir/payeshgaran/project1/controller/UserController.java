package ir.payeshgaran.project1.controller;


import ir.payeshgaran.project1.dto.AccountDTO;
import ir.payeshgaran.project1.dto.TransactionDTO;
import ir.payeshgaran.project1.mapper.AccountMapper;
import ir.payeshgaran.project1.mapper.TransactionMapper;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.service.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountServiceImplementation accountService;

    @Autowired
    private TransactionServiceImplementation transactionService;



    @GetMapping("/accountBalance")
    public String getAccountBalance(Model model) {
        AccountMapper accountMapper = new AccountMapper(new User(), new AccountDTO());
        model.addAttribute("accountBalance", accountMapper.toDTO(accountService.findUserByAccountNumber(loggedInAccountDetails())).getBalance());
        return "accountBalance";
    }



    @GetMapping("/transaction")
    public String myTransactions(Model model) {
        Long userId = accountService.findUserByAccountNumber(loggedInAccountDetails()).getId();

        TransactionMapper transactionMapper = new TransactionMapper(new Transaction(), new TransactionDTO(), accountService);
        List<TransactionDTO> transactionDTOS = transactionMapper.toDTOS(transactionService.getUserTransactions(userId));
        model.addAttribute("transactions", transactionDTOS);
        model.addAttribute("accountBalance", accountService.findUserByAccountNumber(loggedInAccountDetails()).getAccountBalance());

        return "transactions";
    }



    public String loggedInAccountDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }


}
