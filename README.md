# Scala Native interop with C, C++, and assembly

Live coded for the London Scala User Group on July 23rd, 2025.

## The app

This project implements a simple HTTP service with 2 endpoints,
mimicking a curtain store where you can place orders (inspired by my traumatic 
renovation experience).


## Exploring the code 

1. We use a C++ library called [httplib](https://github.com/yhirose/cpp-httplib) for our
   HTTP layer. The library in its entirety is a single header file which we place in 
   the special [folder](./src/main/resources/scala-native/) which Scala Native treats as the location for C/C++/assembly files 
   you want compiled alongside your application.

2. Scala Native cannot invoke C++ functions directly, only C ones. So we create a [glue layer in C](./src/main/resources/scala-native/httplib-glue.h), exposing 
   a minimal interface for our needs.

3. The C glue layer is then processed by [sn-bindgen](https://sn-bindgen.indoorvivants.com) into a set of [bindings](./src/main/scala/httplib.scala) for easier use 
   from Scala

4. We implement [our HTTP server in C++](./src/main/resources/scala-native/httplib-glue.cpp), relying on the C glue layer placeholders, implemented on the Scala side

5. As a fun exercise, we implement a [tiny part of the logic in Assembly](./src/main/resources/scala-native/rectangle_fits.S) (the variant here is ARM64 assembly, for MacOS clang).
   **if you struggle to build this application it might be down to this assembly part - assembly is not portable!**

## Building and running 

1. Start SBT shell by running `sbt`
2. Run `show nativeLink` - this will print the full path to the binary.
3. Run the binary
4. Test the server by running `curl http://localhost:8899/list` and `curl -v http://localhost:8899/create -d "width=99&height=100"`

## Playing with debugger

To see assembly in action, you can run the app using LLDB:

`lldb <path-to-binary>`

This will start LLDB shell. You can set a breakpoint on our assembly function:

```
(lldb) br set --name rectangle_fits_asm
(lldb) run
```

Then, in a separate terminal, run `curl -v http://localhost:8899/create -d "width=99&height=100"` - this 
will trigger the breakpoint, and in lldb you should be able to see something like this:

```
Starting server on http://localhost:8899
Creating order with width 99 and height 100
Process 76887 stopped
* thread #2, stop reason = breakpoint 1.1
    frame #0: 0x000000010018f82c root-83`rectangle_fits_asm
root-83`rectangle_fits_asm:
->  0x10018f82c <+0>:  cmp    w2, w0
    0x10018f830 <+4>:  b.hi   0x10018f844    ; return_zero
    0x10018f834 <+8>:  cmp    w3, w1
    0x10018f838 <+12>: b.hi   0x10018f844    ; return_zero
Target 0: (root-83) stopped.
```
