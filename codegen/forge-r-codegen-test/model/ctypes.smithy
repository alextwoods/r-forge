namespace forge.crt

// /* Ctypes */

@ctype("uint8_t")
structure uint8_t {
    value: Integer
}

@ctype("uint16_t")
structure uint16_t {
    value: Integer
}

@ctype("int32_t")
structure int32_t {
    value: Integer
}

@ctype("int64_t")
structure int64_t {
    value: BigInteger
}

@ctype("uint64_t")
structure uint64_t {
    value: BigInteger
}

@ctype("uint32_t")
structure uint32_t {
    value: BigInteger
}

@ctype("size_t")
structure size_t {
    value: BigInteger
}

@ctype("void")
structure void {}
