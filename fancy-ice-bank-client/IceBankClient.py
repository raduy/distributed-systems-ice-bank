__author__ = 'raduy'

import sys
import Ice
import Bank
from Commands.CreateNewAccountCommand import CreateNewAccountCommand
from Commands.ManageAccountCommand import ManageAccountCommand


class EmptyCommand(object):
    def execute(self):
        pass


class ExitCommand(object):
    def execute(self):
        print("Bye bye! See you next time.")
        sys.exit(0)


class RemoveAccountCommand(object):
    def __init__(self, bank_manager):
        self.bank_manager = bank_manager
        super(RemoveAccountCommand, self).__init__()

    def execute(self):
        account_id = raw_input("What account you want to remove? ")
        self.bank_manager.removeAccount(account_id)


class CommandRouter(object):
    def __init__(self, bank_manager):
        self.bank_manager = bank_manager
        super(CommandRouter, self).__init__()

        self.commands = {"-c": CreateNewAccountCommand(self.bank_manager),
                         "-e": ExitCommand(),
                         "-m": ManageAccountCommand(communicator),
                         "-r": RemoveAccountCommand(self.bank_manager)}

    def parse_command(self):
        cmd = raw_input()
        command = self.commands[cmd[:2]]
        if not command:
            print("No such command available :( Try again")
            return EmptyCommand()

        return command


def print_menu():
    print ("IceBank menu:\n"
           "-c Create new account\n"
           "-r Remove account\n"
           "-m Manage your account (e.g. check balance)\n"
           "-e Exit client\n"),


if __name__ == '__main__':
    communicator = Ice.initialize(sys.argv)
    manager = Bank.BankManagerPrx.checkedCast(communicator.propertyToProxy("BankManager.Proxy"))

    command_router = CommandRouter(manager)
    while True:
        print_menu()
        print ("pick one option:\n"
               "(IceBank)>"),  # this trailing comma cause no new line - python... hahah
        command = command_router.parse_command()
        command.execute()
