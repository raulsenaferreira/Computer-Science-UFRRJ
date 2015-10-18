function ULA(){

	this.resolveOperacao = function(nome, arrayRegistradores){

		var source1;
		var source2;
		//console.log(nome);
		//console.log(arrayRegistradores);
		if(nome=="add"){
			source1 = arrayRegistradores[0];
			source2 = arrayRegistradores[1];
			return (source1+source2);
		}
		else if(nome=="addi"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			return (source1+source2);
		}
		else if(nome=="sub"){
			source1 = arrayRegistradores[0];
			source2 = arrayRegistradores[1];
			return (source1-source2);
		}
		else if(nome=="subi"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			return (source1-source2);
		}
		else if(nome=="and"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];

			if(source1 && source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="or"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			if(source1 || source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="andi"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];

			if(source1 && source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="ori"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			return source1 || source2;
		}
		else if(nome=="nor"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			if(source1 || source2)
				return 0;
			else
				return 1;
		}
		else if(nome=="beq"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			if(source1 == source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="bne"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			if(source1 != source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="slt"){
			source1 = arrayRegistradores[0];
			source2 = arrayRegistradores[1];
			
			if(source1 < source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="slti"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			if(source1 < source2)
				return 1;
			else
				return 0;
		}
		else if(nome=="mult"){
			source1 = arrayRegistradores[1];
			source2 = arrayRegistradores[0];
			
			return source1 * source2;
		}
	}
	
}
