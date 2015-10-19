.data
.text

main:
	li $v0, 4
	add $a0, $v0, $zero
	jal fatorial
	jal end
	
fatorial:
	addi $sp, $sp, -8
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	
	add $s0, $a0, $zero
	
	slti $t0, $s0, 1
	beq $t0, $zero, loop
	li $v0, 1
	addi $sp, $sp, 8
	jr $ra

loop:
	addi $a0, $s0, -1
	jal fatorial
	lw $ra, 0($sp)
	lw $s0, 4($sp)
	addi $sp, $sp, 8
	
	mult $v0, $s0
	mflo $v0
	jr $ra
	
end:
	

	
	


