import numpy as N
from numpy.ctypeslib import load_library
from numpyctypes import c_ndarray

mylib = load_library('libDiffBoundingBoxes.so', '.')       # '.' is the directory of the C++ lib  

def DbbWrapper(array1, array2, aShape):
    arg1 = c_ndarray(array1, dtype=N.uint8, ndim = 2, shape = aShape)
    arg2 = c_ndarray(array2, dtype=N.uint8, ndim = 2, shape = aShape)
    return mylib.DbbWrapper(arg1, arg2)
