function UCP () {
	
	this.tipo=null;
	this.entrada= null;
	var step=0;
	var halt=0;
	

	var registrador = new Registradores();
	var listaRegistradores = registrador.listaRegistradores;

	var backStep = new Array();

	var inst = new Instrucoes();
	var listaInstrucoes = inst.listaInstrucoes;
	
	var ula = new ULA();

	this.run = function(){
		entrada = this.entrada;
		if(!initOnly){
			initMemoria(this.entrada);
			initOnly = true;
		}
		limite = entrada.length;
		if(backStep.length < 1)
			guardaValoresbackstep();
		//se tipo 1 entao compila tudo
		if(this.tipo == 1 && step >= 0){
			try{
				//enquanto o halt nao for chamado ele funciona
				while(halt == 0){
					if(step < entrada.length){
						var montador = new Montador(entrada[step]);
						guardaValoresbackstep();
						incrementaPC();
						step++;
						contadorGlobal = step;//util.js
					}
					else if(step == entrada.length){
						halt = 1;
						$("#console").val($("#console").val() + "Terminated");
					}
				}	
			} catch(e){
				$("#console").val($("#console") + "Operacao nao permitida: "+e.message);
			}	
		}
		//se tipo 2 entao da um passo pra frente
		else if(this.tipo == 2 && step < limite){
			
			try{
				var montador = new Montador(entrada[step]);
				guardaValoresbackstep();
				step++;
				contadorGlobal = step;//util.js
				incrementaPC();
				selectTextareaLine(document.getElementById("input"), step);
				//$("#console").val("passo-> "+step);
			} catch(e){
				$("#console").val($("#console") + "Operacao nao permitida: "+e.message);
			}	
		}
		//senao, da um passo pra tras
		else if(this.tipo == 3 && step > 0){
			
			try{
				step--;
				setRegistradoresBackStep();
				if(step > 0){
					selectTextareaLine(document.getElementById("input"), step);
					var montador = new Montador(entrada[step - 1]);
				}
			} catch(e){
				$("#console").val($("#console") + "Operacao nao permitida: "+e.message);
			}	
		}
		
		else if(step == limite)
			step++;
	}

	this.controlador = function(memoria){

		if(memoria[0]==000000){
			var tipo = "R";
			decodifica(memoria, tipo);
			
		}
		else{
			if(memoria.length < 3){
				var tipo = "J";
				decodifica(memoria, tipo);
			}
			else{
				var tipo = "I";
				decodifica(memoria, tipo);
			}
		}
	};
	
	function decodifica(memoria, tipo){
		
		if(tipo == "R"){
			var arrayRegistradores = new Array();
			var nome = decodificaInstrucao(memoria[5], tipo);//nome da instrucao
			//$("#console").val(nome);
			if(nome == "mflo"){
				var source = decodificaRegistrador(memoria[2]);
				insereValor(memoria[3], source);
			}
			else{
				arrayRegistradores.unshift(decodificaRegistrador(memoria[1]));//source
				arrayRegistradores.unshift(decodificaRegistrador(memoria[2]));//source
				//$("#console").val(arrayRegistradores[0]+"Â§Â§Â§Â§"+arrayRegistradores[1]);
				result = ula.resolveOperacao(nome, arrayRegistradores);
				//$("#console").val("result->"+result+"   arrayRegistradores->"+arrayRegistradores+"   destino(R)->"+memoria[3]);
				insereValor(memoria[3], result);
			}
			
			limpaVetor(arrayRegistradores);
		}
		else if(tipo == "I"){
			var arrayRegistradores = new Array();
			var nome = decodificaInstrucao(memoria[0], tipo);//nome da instrucao

			if(nome == "bne" || nome == "beq"){
				arrayRegistradores.unshift(decodificaRegistrador(memoria[2]));//source
				arrayRegistradores.unshift(decodificaRegistrador(memoria[1]));//source
				//$("#console").val(arrayRegistradores);	
				result = ula.resolveOperacao(nome, arrayRegistradores);
				//$("#console").val("resultado ->"+result);
				if(result == 1){
					//$("#console").val("linha ->"+memoria[3]);
					limpaVetor(arrayRegistradores);
					jump(memoria[3]);
				}
			}
			else if(nome == "lw"){
				//$("#console").val("UCP-lw->"+vetorMemoria[memoria[3]]);
				insereValor(memoria[2], vetorMemoria[memoria[3]]);
			}
			else if(nome == "sw"){
				vetorMemoria[memoria[3]] = memoria[2];
				//$("#console").val("UCP-sw->"+vetorMemoria[memoria[3]]);
			}
			else{
				arrayRegistradores.unshift(decodificaRegistrador(memoria[2]));//source
				arrayRegistradores.unshift(memoria[3]);//valor imediato

				result = ula.resolveOperacao(nome, arrayRegistradores);
				insereValor(memoria[1], result);
			}
			//$("#console").val("result->"+result+"   arrayRegistradores->"+arrayRegistradores+"   destino(I)->"+memoria[2]);
			
			limpaVetor(arrayRegistradores);
		}
		else if(tipo == "J"){
			jump(memoria[1]);//address
		}
	}

	function decodificaInstrucao(instrucao, tipo){
		var result;
		$.each(listaInstrucoes, function (indice) {
			var opCode = listaInstrucoes[indice].opcodeFunction;
			var tipoInstrucao = listaInstrucoes[indice].tipo;

			if(opCode==instrucao && tipoInstrucao == tipo){
				result = listaInstrucoes[indice].instructionName;
			}
		});
		return result;
	}

	function decodificaRegistrador(registrador){
		var result;
		$.each(listaRegistradores, function(j){
			var binario = listaRegistradores[j].binario;

			if(binario == registrador){
				result = listaRegistradores[j].valor;
			}
		});
		//$("#console").val(result);
		return result;
	}	
		
	function insereValor(registrador, valor){
		//certificando que valores booleanos sejam discretos
		if(valor==true){
			valor = 1;
		}
		else if(valor==false){
			valor = 0;
		}
		else if (valor=="undefined") {
			return false;
		}
		$.each(listaRegistradores, function(j){
			var binario = listaRegistradores[j].binario;
			var idElemento = listaRegistradores[j].nome;
			if(binario == registrador){
				listaRegistradores[j].valor = valor;
				$("#"+idElemento).text(dec2hex(listaRegistradores[j].valor, true));
				//$("#console").val(listaRegistradores[j].nome+" -> "+listaRegistradores[j].valor);
			}
		});
	}
	
	function jump(linha){
		step = bin2dec(linha) / 4;

		var x = new UCP();
		x.tipo = this.tipo;
		x.entrada = this.entrada;
		x.run();
	}

	function guardaValoresbackstep(){
		var valoresAntigos = new Array();
		$.each(listaRegistradores, function(i){
			var valorAntigo = listaRegistradores[i].valor;
			valoresAntigos.push(valorAntigo);
		});
		backStep.push(valoresAntigos);
	}
	
	function setRegistradoresBackStep(){
		if(backStep.length > 1)
			backStep.pop();
		var valoresAntigos = peek(backStep);
		$.each(listaRegistradores, function(i){
			listaRegistradores[i].valor = valoresAntigos[i];
			var idElemento = listaRegistradores[i].nome;
			$("#"+idElemento).text(dec2hex(listaRegistradores[i].valor, true));
		});
		
	}
}
