'''
Created on Apr 9, 2017

@author: Nguyen Huu Hiep
'''

import numpy as np
cimport cython
from libc.math cimport sqrt
import numpy as np
from timeit import default_timer as timer

X = np.random.random((1000, 3))

@cython.boundscheck(False)
@cython.wraparound(False)
def pairwise_cython(double[:, ::1] X):
    cdef int M = X.shape[0]
    cdef int N = X.shape[1]
    cdef double tmp, d
    cdef double[:, ::1] D = np.empty((M, M), dtype=np.float64)
    for i in range(M):
        for j in range(M):
            d = 0.0
            for k in range(N):
                tmp = X[i, k] - X[j, k]
                d += tmp * tmp
            D[i, j] = sqrt(d)
    return np.asarray(D)

# timing (? s)
start = timer()
pairwise_cython(X)
end = timer()
print(end - start) 