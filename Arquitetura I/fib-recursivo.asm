.data
.text
main:
	li $s0, 5
	move $a0, $s0
	jal fibonacci
	jal fim
	
fibonacci:
	addi $a1, $a0, -1
	
	addi $sp, $sp, -12
	sw $ra, 8($sp)
	sw $a0, 4($sp)
	sw $a1, 0($sp)
	
	slti $t0, $a0, 2
	beq $t0, $zero, L1
	addi $v0, $zero, 1
	addi $sp, $sp, 12
	jr $ra
	
L1:
	addi $a0, $a0, -1
	addi $a1, $a1, -1
	jal fibonacci
	
	lw $ra, 8($sp)
	lw $a0, 4($sp)
	lw $a1, 0($sp)
	addi $sp, $sp, 12
	
	add $v0, $a0, $a1
	
	jr $ra
	
fim: