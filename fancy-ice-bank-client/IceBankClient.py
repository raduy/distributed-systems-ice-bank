__author__ = 'raduy'

import sys
import Ice

if __name__ == '__main__':
    communicator = Ice.initialize(sys.argv)

    print "Hello there!"