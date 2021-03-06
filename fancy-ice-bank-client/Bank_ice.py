# **********************************************************************
#
# Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
#
# This copy of Ice is licensed to you under the terms described in the
# ICE_LICENSE file included in this distribution.
#
# **********************************************************************
#
# Ice version 3.5.1
#
# <auto-generated>
#
# Generated from file `Bank.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

import Ice, IcePy

# Start of module Bank
_M_Bank = Ice.openModule('Bank')
__name__ = 'Bank'

if 'IncorrectData' not in _M_Bank.__dict__:
    _M_Bank.IncorrectData = Ice.createTempClass()
    class IncorrectData(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_name = 'Bank::IncorrectData'

    _M_Bank._t_IncorrectData = IcePy.defineException('::Bank::IncorrectData', IncorrectData, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    IncorrectData._ice_type = _M_Bank._t_IncorrectData

    _M_Bank.IncorrectData = IncorrectData
    del IncorrectData

if 'RequestRejected' not in _M_Bank.__dict__:
    _M_Bank.RequestRejected = Ice.createTempClass()
    class RequestRejected(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_name = 'Bank::RequestRejected'

    _M_Bank._t_RequestRejected = IcePy.defineException('::Bank::RequestRejected', RequestRejected, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    RequestRejected._ice_type = _M_Bank._t_RequestRejected

    _M_Bank.RequestRejected = RequestRejected
    del RequestRejected

if 'IncorrectAccountNumber' not in _M_Bank.__dict__:
    _M_Bank.IncorrectAccountNumber = Ice.createTempClass()
    class IncorrectAccountNumber(Ice.UserException):
        def __init__(self):
            pass

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_name = 'Bank::IncorrectAccountNumber'

    _M_Bank._t_IncorrectAccountNumber = IcePy.defineException('::Bank::IncorrectAccountNumber', IncorrectAccountNumber, (), False, None, ())
    IncorrectAccountNumber._ice_type = _M_Bank._t_IncorrectAccountNumber

    _M_Bank.IncorrectAccountNumber = IncorrectAccountNumber
    del IncorrectAccountNumber

if 'IncorrectAmount' not in _M_Bank.__dict__:
    _M_Bank.IncorrectAmount = Ice.createTempClass()
    class IncorrectAmount(Ice.UserException):
        def __init__(self):
            pass

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_name = 'Bank::IncorrectAmount'

    _M_Bank._t_IncorrectAmount = IcePy.defineException('::Bank::IncorrectAmount', IncorrectAmount, (), False, None, ())
    IncorrectAmount._ice_type = _M_Bank._t_IncorrectAmount

    _M_Bank.IncorrectAmount = IncorrectAmount
    del IncorrectAmount

if 'NoSuchAccount' not in _M_Bank.__dict__:
    _M_Bank.NoSuchAccount = Ice.createTempClass()
    class NoSuchAccount(Ice.UserException):
        def __init__(self):
            pass

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_name = 'Bank::NoSuchAccount'

    _M_Bank._t_NoSuchAccount = IcePy.defineException('::Bank::NoSuchAccount', NoSuchAccount, (), False, None, ())
    NoSuchAccount._ice_type = _M_Bank._t_NoSuchAccount

    _M_Bank.NoSuchAccount = NoSuchAccount
    del NoSuchAccount

if 'Currency' not in _M_Bank.__dict__:
    _M_Bank.Currency = Ice.createTempClass()
    class Currency(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    Currency.PLN = Currency("PLN", 0)
    Currency.EUR = Currency("EUR", 1)
    Currency.USD = Currency("USD", 2)
    Currency.CHF = Currency("CHF", 3)
    Currency._enumerators = { 0:Currency.PLN, 1:Currency.EUR, 2:Currency.USD, 3:Currency.CHF }

    _M_Bank._t_Currency = IcePy.defineEnum('::Bank::Currency', Currency, (), Currency._enumerators)

    _M_Bank.Currency = Currency
    del Currency

if 'accountType' not in _M_Bank.__dict__:
    _M_Bank.accountType = Ice.createTempClass()
    class accountType(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    accountType.SILVER = accountType("SILVER", 0)
    accountType.PREMIUM = accountType("PREMIUM", 1)
    accountType._enumerators = { 0:accountType.SILVER, 1:accountType.PREMIUM }

    _M_Bank._t_accountType = IcePy.defineEnum('::Bank::accountType', accountType, (), accountType._enumerators)

    _M_Bank.accountType = accountType
    del accountType

if 'Account' not in _M_Bank.__dict__:
    _M_Bank.Account = Ice.createTempClass()
    class Account(Ice.Object):
        def __init__(self):
            if Ice.getType(self) == _M_Bank.Account:
                raise RuntimeError('Bank.Account is an abstract class')

        def ice_ids(self, current=None):
            return ('::Bank::Account', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Bank::Account'

        def ice_staticId():
            return '::Bank::Account'
        ice_staticId = staticmethod(ice_staticId)

        def getBalance(self, current=None):
            pass

        def getAccountNumber(self, current=None):
            pass

        def transferMoney(self, accountNumber, amount, current=None):
            pass

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_Account)

        __repr__ = __str__

    _M_Bank.AccountPrx = Ice.createTempClass()
    class AccountPrx(Ice.ObjectPrx):

        def getBalance(self, _ctx=None):
            return _M_Bank.Account._op_getBalance.invoke(self, ((), _ctx))

        def begin_getBalance(self, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.Account._op_getBalance.begin(self, ((), _response, _ex, _sent, _ctx))

        def end_getBalance(self, _r):
            return _M_Bank.Account._op_getBalance.end(self, _r)

        def getAccountNumber(self, _ctx=None):
            return _M_Bank.Account._op_getAccountNumber.invoke(self, ((), _ctx))

        def begin_getAccountNumber(self, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.Account._op_getAccountNumber.begin(self, ((), _response, _ex, _sent, _ctx))

        def end_getAccountNumber(self, _r):
            return _M_Bank.Account._op_getAccountNumber.end(self, _r)

        def transferMoney(self, accountNumber, amount, _ctx=None):
            return _M_Bank.Account._op_transferMoney.invoke(self, ((accountNumber, amount), _ctx))

        def begin_transferMoney(self, accountNumber, amount, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.Account._op_transferMoney.begin(self, ((accountNumber, amount), _response, _ex, _sent, _ctx))

        def end_transferMoney(self, _r):
            return _M_Bank.Account._op_transferMoney.end(self, _r)

        def checkedCast(proxy, facetOrCtx=None, _ctx=None):
            return _M_Bank.AccountPrx.ice_checkedCast(proxy, '::Bank::Account', facetOrCtx, _ctx)
        checkedCast = staticmethod(checkedCast)

        def uncheckedCast(proxy, facet=None):
            return _M_Bank.AccountPrx.ice_uncheckedCast(proxy, facet)
        uncheckedCast = staticmethod(uncheckedCast)

    _M_Bank._t_AccountPrx = IcePy.defineProxy('::Bank::Account', AccountPrx)

    _M_Bank._t_Account = IcePy.defineClass('::Bank::Account', Account, -1, (), True, False, None, (), ())
    Account._ice_type = _M_Bank._t_Account

    Account._op_getBalance = IcePy.Operation('getBalance', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_int, False, 0), ())
    Account._op_getAccountNumber = IcePy.Operation('getAccountNumber', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())
    Account._op_transferMoney = IcePy.Operation('transferMoney', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), IcePy._t_int, False, 0)), (), None, (_M_Bank._t_IncorrectAccountNumber, _M_Bank._t_IncorrectAmount))

    _M_Bank.Account = Account
    del Account

    _M_Bank.AccountPrx = AccountPrx
    del AccountPrx

if 'PremiumAccount' not in _M_Bank.__dict__:
    _M_Bank.PremiumAccount = Ice.createTempClass()
    class PremiumAccount(_M_Bank.Account):
        def __init__(self):
            if Ice.getType(self) == _M_Bank.PremiumAccount:
                raise RuntimeError('Bank.PremiumAccount is an abstract class')

        def ice_ids(self, current=None):
            return ('::Bank::Account', '::Bank::PremiumAccount', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Bank::PremiumAccount'

        def ice_staticId():
            return '::Bank::PremiumAccount'
        ice_staticId = staticmethod(ice_staticId)

        def calculateLoan(self, amount, curr, period, current=None):
            pass

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_PremiumAccount)

        __repr__ = __str__

    _M_Bank.PremiumAccountPrx = Ice.createTempClass()
    class PremiumAccountPrx(_M_Bank.AccountPrx):

        def calculateLoan(self, amount, curr, period, _ctx=None):
            return _M_Bank.PremiumAccount._op_calculateLoan.invoke(self, ((amount, curr, period), _ctx))

        def begin_calculateLoan(self, amount, curr, period, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.PremiumAccount._op_calculateLoan.begin(self, ((amount, curr, period), _response, _ex, _sent, _ctx))

        def end_calculateLoan(self, _r):
            return _M_Bank.PremiumAccount._op_calculateLoan.end(self, _r)

        def checkedCast(proxy, facetOrCtx=None, _ctx=None):
            return _M_Bank.PremiumAccountPrx.ice_checkedCast(proxy, '::Bank::PremiumAccount', facetOrCtx, _ctx)
        checkedCast = staticmethod(checkedCast)

        def uncheckedCast(proxy, facet=None):
            return _M_Bank.PremiumAccountPrx.ice_uncheckedCast(proxy, facet)
        uncheckedCast = staticmethod(uncheckedCast)

    _M_Bank._t_PremiumAccountPrx = IcePy.defineProxy('::Bank::PremiumAccount', PremiumAccountPrx)

    _M_Bank._t_PremiumAccount = IcePy.defineClass('::Bank::PremiumAccount', PremiumAccount, -1, (), True, False, None, (_M_Bank._t_Account,), ())
    PremiumAccount._ice_type = _M_Bank._t_PremiumAccount

    PremiumAccount._op_calculateLoan = IcePy.Operation('calculateLoan', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0), ((), _M_Bank._t_Currency, False, 0), ((), IcePy._t_int, False, 0)), (((), IcePy._t_float, False, 0), ((), IcePy._t_int, False, 0)), None, (_M_Bank._t_IncorrectData,))

    _M_Bank.PremiumAccount = PremiumAccount
    del PremiumAccount

    _M_Bank.PremiumAccountPrx = PremiumAccountPrx
    del PremiumAccountPrx

if 'PersonalData' not in _M_Bank.__dict__:
    _M_Bank.PersonalData = Ice.createTempClass()
    class PersonalData(object):
        def __init__(self, firstName='', lastName='', nationality='', nationalIDNumber=''):
            self.firstName = firstName
            self.lastName = lastName
            self.nationality = nationality
            self.nationalIDNumber = nationalIDNumber

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.firstName)
            _h = 5 * _h + Ice.getHash(self.lastName)
            _h = 5 * _h + Ice.getHash(self.nationality)
            _h = 5 * _h + Ice.getHash(self.nationalIDNumber)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Bank.PersonalData):
                return NotImplemented
            else:
                if self.firstName is None or other.firstName is None:
                    if self.firstName != other.firstName:
                        return (-1 if self.firstName is None else 1)
                else:
                    if self.firstName < other.firstName:
                        return -1
                    elif self.firstName > other.firstName:
                        return 1
                if self.lastName is None or other.lastName is None:
                    if self.lastName != other.lastName:
                        return (-1 if self.lastName is None else 1)
                else:
                    if self.lastName < other.lastName:
                        return -1
                    elif self.lastName > other.lastName:
                        return 1
                if self.nationality is None or other.nationality is None:
                    if self.nationality != other.nationality:
                        return (-1 if self.nationality is None else 1)
                else:
                    if self.nationality < other.nationality:
                        return -1
                    elif self.nationality > other.nationality:
                        return 1
                if self.nationalIDNumber is None or other.nationalIDNumber is None:
                    if self.nationalIDNumber != other.nationalIDNumber:
                        return (-1 if self.nationalIDNumber is None else 1)
                else:
                    if self.nationalIDNumber < other.nationalIDNumber:
                        return -1
                    elif self.nationalIDNumber > other.nationalIDNumber:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_PersonalData)

        __repr__ = __str__

    _M_Bank._t_PersonalData = IcePy.defineStruct('::Bank::PersonalData', PersonalData, (), (
        ('firstName', (), IcePy._t_string),
        ('lastName', (), IcePy._t_string),
        ('nationality', (), IcePy._t_string),
        ('nationalIDNumber', (), IcePy._t_string)
    ))

    _M_Bank.PersonalData = PersonalData
    del PersonalData

if 'BankManager' not in _M_Bank.__dict__:
    _M_Bank.BankManager = Ice.createTempClass()
    class BankManager(Ice.Object):
        def __init__(self):
            if Ice.getType(self) == _M_Bank.BankManager:
                raise RuntimeError('Bank.BankManager is an abstract class')

        def ice_ids(self, current=None):
            return ('::Bank::BankManager', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Bank::BankManager'

        def ice_staticId():
            return '::Bank::BankManager'
        ice_staticId = staticmethod(ice_staticId)

        def createAccount(self, data, type, current=None):
            pass

        def removeAccount(self, accountID, current=None):
            pass

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_BankManager)

        __repr__ = __str__

    _M_Bank.BankManagerPrx = Ice.createTempClass()
    class BankManagerPrx(Ice.ObjectPrx):

        def createAccount(self, data, type, _ctx=None):
            return _M_Bank.BankManager._op_createAccount.invoke(self, ((data, type), _ctx))

        def begin_createAccount(self, data, type, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.BankManager._op_createAccount.begin(self, ((data, type), _response, _ex, _sent, _ctx))

        def end_createAccount(self, _r):
            return _M_Bank.BankManager._op_createAccount.end(self, _r)

        def removeAccount(self, accountID, _ctx=None):
            return _M_Bank.BankManager._op_removeAccount.invoke(self, ((accountID, ), _ctx))

        def begin_removeAccount(self, accountID, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_Bank.BankManager._op_removeAccount.begin(self, ((accountID, ), _response, _ex, _sent, _ctx))

        def end_removeAccount(self, _r):
            return _M_Bank.BankManager._op_removeAccount.end(self, _r)

        def checkedCast(proxy, facetOrCtx=None, _ctx=None):
            return _M_Bank.BankManagerPrx.ice_checkedCast(proxy, '::Bank::BankManager', facetOrCtx, _ctx)
        checkedCast = staticmethod(checkedCast)

        def uncheckedCast(proxy, facet=None):
            return _M_Bank.BankManagerPrx.ice_uncheckedCast(proxy, facet)
        uncheckedCast = staticmethod(uncheckedCast)

    _M_Bank._t_BankManagerPrx = IcePy.defineProxy('::Bank::BankManager', BankManagerPrx)

    _M_Bank._t_BankManager = IcePy.defineClass('::Bank::BankManager', BankManager, -1, (), True, False, None, (), ())
    BankManager._ice_type = _M_Bank._t_BankManager

    BankManager._op_createAccount = IcePy.Operation('createAccount', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Bank._t_PersonalData, False, 0), ((), _M_Bank._t_accountType, False, 0)), (((), IcePy._t_string, False, 0),), None, (_M_Bank._t_IncorrectData, _M_Bank._t_RequestRejected))
    BankManager._op_removeAccount = IcePy.Operation('removeAccount', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0),), (), None, (_M_Bank._t_IncorrectData, _M_Bank._t_NoSuchAccount))

    _M_Bank.BankManager = BankManager
    del BankManager

    _M_Bank.BankManagerPrx = BankManagerPrx
    del BankManagerPrx

# End of module Bank
