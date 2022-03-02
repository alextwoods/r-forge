namespace forge.crt

@trait
string ctype

@trait
structure opaque {}

@trait
structure pointer {
    doublePointer: Boolean
}

@trait
structure const {}

@trait(selector: "operation")
structure crtBinding {
    definedIn: String,
    functionName: String
}