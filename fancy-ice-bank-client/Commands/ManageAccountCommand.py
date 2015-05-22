__author__ = "raduy"

import Ice
import Bank


account_type_map = {"S": "Silver", "P": "Premium"}


class ManageAccountCommand(object):
    def __init__(self, communicator):
        self.communicator = communicator
        super(ManageAccountCommand, self).__init__()

    def parse_account_type(self):
        account_type = None
        while True:
            account_type = raw_input("What account type do you have? (S - silver/P - premium)")
            if account_type in ("S", "P"):
                break
            print "No such account type! Try again."
        return account_type

    def transfer_money(self, account):
        destination_account = raw_input("Destination Account number: ")
        amount = int(raw_input("How much?: "))

        try:
            account.transferMoney(destination_account, amount)
            print "Money (%d) transferred to %s" % (amount, destination_account)
        except Bank.IncorrectAccountNumber:
            print "Incorrect account number"
        except Bank.IncorrectAmount:
            print "Incorrect amount"

    def calculate_loan(self, account):
        def parse_currency():
            while True:
                currencies = {
                    'PLN': Bank.Currency.PLN,
                    'EUR': Bank.Currency.EUR,
                    'USD': Bank.Currency.USD,
                    'CHF': Bank.Currency.CHF
                }

                currency_cmd = raw_input("In what currency (PLN, EUR, USD, CHF) you are interested in? ")
                if currency_cmd in currencies:
                    return currencies[currency_cmd]

                print "Incorrect currency"

        try:
            amount = int(raw_input("How much do you want to borrow? "))
            currency = parse_currency()
            period = int(raw_input("What period do you want? "))
            return account.calculateLoan(amount, currency, period)

        except Bank.IncorrectData:
            print "Incorrect Data :( Try again"

    def parse_account_number(self, account_type):
        account_number = raw_input("Give me you account number:")
        try:
            if account_type == "P":
                return Bank.PremiumAccountPrx.checkedCast(
                    self.communicator.stringToProxy(
                        construct_proxy(self.communicator, account_number, account_type)
                    )
                )
            elif account_type == "S":
                return Bank.AccountPrx.checkedCast(
                    self.communicator.stringToProxy(
                        construct_proxy(self.communicator, account_number, account_type)
                    )
                )
        except Ice.ObjectNotExistException:
            print "No such account"

    def execute(self):
        account_type = self.parse_account_type()

        account = self.parse_account_number(account_type)
        if not account:
            return

        cmd_context = "(IceBank on %s %s... account)>" % (
            account_type_map[account_type], str(account.getAccountNumber())[:5])

        print (cmd_context),
        while True:
            print ("What would you like to do with your account?")
            print ("-b - get balance\n"
                   "-n - get account number\n"
                   "-t - transfer money\n"
                   "-l - calculate loan\n"
                   "-e - exit\n"),
            cmd = raw_input(cmd_context)

            if cmd.startswith("-b"):
                balance = account.getBalance()
                print "%sBalance is %d" % (cmd_context, balance)
            elif cmd.startswith("-n"):
                account_number = account.getAccountNumber()
                print "%sAccount number: %s" % (cmd_context, account_number)
            elif cmd.startswith("-t"):
                self.transfer_money(account)
                print "%sMoney transferred" % cmd_context
            elif cmd.startswith("-l"):
                interest_rate, total_cost = self.calculate_loan(account)
                print "Ice Bank offered You: interest rate of: %.3f and total cost of: %d" % (interest_rate, total_cost)
            elif cmd.startswith("-e"):
                break
            else:
                print("No such command! Try again :)")


def construct_proxy(communicator, account_id, account_type):
    proxies = {
        "S": "SilverAccounts.Proxy",
        "P": "PremiumAccounts.Proxy"
    }

    prefix, suffix = communicator.getProperties().getProperty(proxies[account_type]).split(":")

    return prefix + account_id + ":" + suffix