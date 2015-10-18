function Instrucoes(){
	
	this.listaInstrucoes = [
		// add - OK
		{
			'instructionRegex':/add(?= )/,
			'opcodeFunction':'100000',
			'tipo':'R',
			'instructionName':'add'
		},
		// sub - OK
		{
			'instructionRegex':/sub(?= )/,
			'opcodeFunction':'100010',
			'tipo':'R',
			'instructionName':'sub'
		},
		// mult - OK
		{
			'instructionRegex':/mult(?= )/,
			'opcodeFunction':'011000',
			'tipo':'R',
			'instructionName':'mult'
		},
		{
			'instructionRegex':/mflo(?= )/,
			'opcodeFunction':'010010',
			'tipo':'R',
			'instructionName':'mflo'
		},
		// addi - OK
		{
			'instructionRegex':/addi(?= )/,
			'opcodeFunction':'001000',
			'tipo':'I',
			'instructionName':'addi'
		},
		//lw
		{
			'instructionRegex':/lw(?= )/,
			'opcodeFunction':'100011',
			'tipo':'I',
			'instructionName':'lw'
		},
		//sw
		{
			'instructionRegex':/sw(?= )/,
			'opcodeFunction':'101011',
			'tipo':'I',
			'instructionName':'sw'
		},
		//subi - OK
		{
			'instructionRegex':/subi(?= )/,
			'opcodeFunction':'001001',
			'tipo':'I',
			'instructionName':'subi'
		},
		// lh 
		{
			'instructionRegex':/lh(?= )/,
			'opcodeFunction':'100001',
			'tipo':'I',
			'instructionName':'lh'
		},
		// lhu
		{
			'instructionRegex':/lhu(?= )/,
			'opcodeFunction':'100101',
			'tipo':'I',
			'instructionName':'lhu'
		},
		// sh
		{
			'instructionRegex':/sh(?= )/,
			'opcodeFunction':'101001',
			'tipo':'I',
			'instructionName':'sh'
		},
		// lb
		{
			'instructionRegex':/lb(?= )/,
			'opcodeFunction':'100000',
			'tipo':'I',
			'instructionName':'lb '
		},
		// lbu
		{
			'instructionRegex':/lbu(?= )/,
			'opcodeFunction':'100100',
			'tipo':'I',
			'instructionName':'lbu'
		},
		// sb
		{
			'instructionRegex':/sb(?= )/,
			'opcodeFunction':'101000',
			'tipo':'I',
			'instructionName':'sb'
		},
		// ll
		{
			'instructionRegex':/ll(?= )/,
			'opcodeFunction':'110000',
			'tipo':'I',
			'instructionName':'ll'
		},
		// sc
		{
			'instructionRegex':/sc(?= )/,
			'opcodeFunction':'111000',
			'tipo':'I',
			'instructionName':'sc'
		},
		// lui
		{
			'instructionRegex':/lui(?= )/,
			'opcodeFunction':'001111',
			'tipo':'I',
			'instructionName':'lui'
		},
		// and - OK
		{
			'instructionRegex':/and(?= )/,
			'opcodeFunction':'100100',
			'tipo':'R',
			'instructionName':'and'
		},
		// or - OK
		{
			'instructionRegex':/or(?= )/,
			'opcodeFunction':'100101',
			'tipo':'R',
			'instructionName':'or'
		},
		// nor - OK
		{
			'instructionRegex':/nor(?= )/,
			'opcodeFunction':'100111',
			'tipo':'R',
			'instructionName':'nor'
		},
		// andi - OK
		{
			'instructionRegex':/andi(?= )/,
			'opcodeFunction':'001100',
			'tipo':'I',
			'instructionName':'andi'
		},
		// ori - OK
		{
			'instructionRegex':/ori(?= )/,
			'opcodeFunction':'001101',
			'tipo':'I',
			'instructionName':'ori'
		},
		// sll
		{
			'instructionRegex':/sll(?= )/,
			'opcodeFunction':'000000',
			'tipo':'R',
			'instructionName':'sll'
		},
		// srl
		{
			'instructionRegex':/srl(?= )/,
			'opcodeFunction':'000010',
			'tipo':'R',
			'instructionName':'srl'
		},
		// beq - OK
		{
			'instructionRegex':/beq(?= )/,
			'opcodeFunction':'000100',
			'tipo':'I',
			'instructionName':'beq'
		},
		// bne - OK
		{
			'instructionRegex':/bne(?= )/,
			'opcodeFunction':'000101',
			'tipo':'I',
			'instructionName':'bne'
		},
		// slt - OK
		{
			'instructionRegex':/slt(?= )/,
			'opcodeFunction':'101010',
			'tipo':'R',
			'instructionName':'slt'
		},
		// sltu
		{
			'instructionRegex':/sltu(?= )/,
			'opcodeFunction':'101011',
			'tipo':'R',
			'instructionName':'sltu'
		},
		// slti
		{
			'instructionRegex':/slti(?= )/,
			'opcodeFunction':'001010',
			'tipo':'I',
			'instructionName':'slti'
		},
		// sltiu
		{
			'instructionRegex':/sltiu(?= )/,
			'opcodeFunction':'001011',
			'tipo':'I',
			'instructionName':'sltiu'
		},
		// j
		{
			'instructionRegex':/j(?= )/,
			'opcodeFunction':'000101',
			'tipo':'J',
			'instructionName':'j'
		},
		// jr
		{
			'instructionRegex':/jr(?= )/,
			'opcodeFunction':'001000',
			'tipo':'J',
			'instructionName':'jr'
		},
		// jal
		{
			'instructionRegex':/jal(?= )/,
			'opcodeFunction':'000011',
			'tipo':'J',
			'instructionName':'jal'
		}
	];

}
