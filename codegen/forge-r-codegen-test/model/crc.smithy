$version: "1.0"

namespace forge.crt

service Crc {
    version: "2022-03-01",
    operations: [
    aws_checksums_crc32,
    aws_checksums_crc32c
    ]
}

structure aws_checksums_crc32_input {
    input: String,
    previous: uint32_t
}

structure aws_checksums_crc32_output {
    ret: uint32_t
}

operation aws_checksums_crc32 {
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

operation aws_checksums_crc32c {
    input: aws_checksums_crc32c_input,
    output: aws_checksums_crc32c_output
}