#Client setup: (on Ubuntu)
1. Install *Ice for python*
$ sudo apt-get install python-zeroc-ice

2. Generate stubs:
$ slice2py slice/*.ice

3. Run
$ python IceBankClient.py --Ice.Config=config.client