.text
.global _rectangle_fits_asm

_rectangle_fits_asm:
    // w0 – curtain width
    // w1 - curtain height
    // w2 - order width
    // w3 - order height

    cmp w2, w0
    b.hi return_zero

    cmp w3, w1
    b.hi return_zero

    mov x0, #1
    ret

return_zero:
    mov x0, #0
    ret
