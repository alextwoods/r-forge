$version: "1.0"

namespace forge.crt

service Common {
    version: "2022-03-01",
    operations: [
    AwsLastError,
    AwsErrorStr,
    AwsErrorName,
    AwsErrorDebugStr,
    AwsResetError,
    AwsTestError
    ]
}

// AWS_CRT_API int aws_crt_last_error(void);
structure aws_crt_last_error_output {
    ret: int
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_last_error")
operation AwsLastError {
    input: void,
    output: aws_crt_last_error_output
}

// AWS_CRT_API const char *aws_crt_error_str(int err);
structure aws_crt_error_str_input {
    err: int
}

structure aws_crt_error_str_output {
    @const
    ret: String
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_error_str")
operation AwsErrorStr {
    input: aws_crt_error_str_input,
    output: aws_crt_error_str_output
}

// AWS_CRT_API const char *aws_crt_error_name(int err);
structure aws_crt_error_name_input {
    err: int
}

structure aws_crt_error_name_output {
    @const
    ret: String
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_error_name")
operation AwsErrorName {
    input: aws_crt_error_name_input,
    output: aws_crt_error_name_output
}

// AWS_CRT_API const char *aws_crt_error_debug_str(int err);
structure aws_crt_error_debug_str_input {
    err: int
}

structure aws_crt_error_debug_str_output {
    @const
    ret: String
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_error_debug_str")
operation AwsErrorDebugStr {
    input: aws_crt_error_debug_str_input,
    output: aws_crt_error_debug_str_output
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_reset_error")
operation AwsResetError {
    input: void,
    output: void
}

structure aws_crt_test_error_input {
    err: int
}

structure aws_crt_test_error_output {
    ret: int
}

@crtBinding(definedIn: "aws/common/error.h", functionName: "aws_raise_error")
operation AwsTestError {
    input: aws_crt_test_error_input,
    output: aws_crt_test_error_output
}