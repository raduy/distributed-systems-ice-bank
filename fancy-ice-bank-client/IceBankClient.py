__author__ = 'raduy'

import sys
import Ice
import Bank


class EmptyCommand(object):
    def execute(self):
        pass


class ExitCommand(object):
    def execute(self):
        print("Bye bye! See you next time.")
        sys.exit(0)


class CreateNewAccountCommand(object):
    def __init__(self, bank_manager):
        self.bank_manager = bank_manager
        super(CreateNewAccountCommand, self).__init__()

    def parse_account_type(self):
        while True:
            account_type_str = raw_input("Pick account type:\n"
                                         "S - SILVER\n"
                                         "P - PREMIUM\n")
            if account_type_str == 'S':
                return Bank.accountType.SILVER
            elif account_type_str == 'P':
                return Bank.accountType.PREMIUM

            print("Wrong account type! Try Again!")

    def execute(self):
        name = raw_input("What is your name? ")
        surname = raw_input("What is your surname? ")
        nationality = raw_input("What is your nationality? ")
        id_number = raw_input("What is your PESEL? ")
        account_type = self.parse_account_type()

        personal_data = Bank.PersonalData(firstName=name,
                                          lastName=surname,
                                          nationality=nationality,
                                          nationalIDNumber=id_number)

        account_number = self.bank_manager.createAccount(personal_data, account_type)
        print("Account with number %s created" % account_number)


class CommandRouter(object):
    def __init__(self, bank_manager):
        self.bank_manager = bank_manager
        super(CommandRouter, self).__init__()

    def parse_command(self):
        cmd = raw_input()
        if cmd.startswith("-c"):
            return CreateNewAccountCommand(self.bank_manager)
        elif cmd.startswith("-e"):
            return ExitCommand()
        else:
            print("No such command available :( Try again")
            return EmptyCommand()

if __name__ == '__main__':
    communicator = Ice.initialize(sys.argv)
    manager = Bank.BankManagerPrx.checkedCast(communicator.propertyToProxy("BankManager.Proxy"))

    print "IceBank menu:\n" \
          "-c Create new account\n" \
          "-e Exit client\n"

    command_router = CommandRouter(manager)
    while True:
        print ("pick one option:\n"
               "(IceBank)>"),  # this trailing comma cause no new line - python... hahah
        command = command_router.parse_command()
        command.execute()
