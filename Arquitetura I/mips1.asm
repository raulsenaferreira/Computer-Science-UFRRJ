	.data
resultado:   .word
	.text

main:

# set coefficients

li $s0, 10 # c0 == 10

li $s1, 7 # c1 == 7

li $s2, 5 # c2 == 5

li $s3, 3 # set x == 3

# evaluate polynomial

add $s4, $zero, $s2 # calculate c2

mul $s4, $s4, $s3 # c2 * x

add $s4, $s4, $s1 # c2 * x + c1

mul $s4, $s4, $s3 # (c2 * x + c1) * x

add $s4, $s4, $s0 # (c2 * x + c1) * x + c0

sw $s4, resultado

lw $a0, resultado
li $v0, 1 # exit program

syscall