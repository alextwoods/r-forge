$version: "1.0"

namespace forge.crt

service Crc {
    version: "2022-03-01",
    operations: [
    AwsCrc32,
    AwsCrc32c
    ]
}

structure aws_checksums_crc32_input {
    input: String,
    previous: uint32_t
}

structure aws_checksums_crc32_output {
    ret: uint32_t
}

@crtBinding(definedIn: "aws/checksums/crc.h", functionName: "aws_checksums_crc32")
operation AwsCrc32 {
    input: aws_checksums_crc32_input,
    output: aws_checksums_crc32_output
}

structure aws_checksums_crc32c_input {
    input: String,
    previous: uint32_t
}

structure aws_checksums_crc32c_output {
    ret: uint32_t
}

@crtBinding(definedIn: "aws/checksums/crc.h", functionName: "aws_checksums_crc32c")
operation AwsCrc32c {
    input: aws_checksums_crc32c_input,
    output: aws_checksums_crc32c_output
}