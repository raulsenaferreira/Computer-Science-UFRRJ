function Montador(input){

	var registrador = new Registradores();
	var listaRegistradores = registrador.listaRegistradores;

	var instrucao = new Instrucoes();
	var listaInstrucoes = instrucao.listaInstrucoes;

	var tipo;
	var vetBinario = new Array();
	var vetAux = new Array();
	var vetIndice = new Array();

	var linhaInput;
	var qtdComandos = input.length;
	
	
	$.each(listaDeMascarasDeInstrucao, function (indice) {

		var linhaTrue = listaDeMascarasDeInstrucao[indice].instrucao.test(input);
		linhaInput = listaDeMascarasDeInstrucao[indice].instrucao.exec(input);

		if(linhaTrue==true && linhaInput[0]==input){
			//tratando casos especiais
			//pegando o dígito(addi, subi e etc) caso precise
			if(listaDeMascarasDeInstrucao[indice].acao == "imediato"){
				var digito = linhaInput[0].split(",");
				digito = trim(digito[2]);
				var valor = parseInt(digito);
				vetBinario.push(valor);
			}
			//pegando o endereço do label já mapeado para dar jump
			else if(listaDeMascarasDeInstrucao[indice].acao == "jump"){
				var linhaJump = linhaInput[0].split(" ");
				//console.log(linhaJump);
				linhaJump = trim(linhaJump[1]);
				//console.log(linhaJump);
				var linha = mapaLabel[linhaJump];
				vetBinario.push(linha);
				//console.log(linha);
			}
			//pegando o endereço do label já mapeado para dar jump
			else if(listaDeMascarasDeInstrucao[indice].acao == "jumpAndLink"){
				var linhaJump = linhaInput[0].split(" ");
				//console.log(linhaJump);
				linhaJump = trim(linhaJump[1]);
				//console.log(linhaJump);
				var linha = mapaLabel[linhaJump];
				vetBinario.push(linha);
				var ra = contadorGlobal*4+4;
				console.log("ra->"+ra);
				console.log("jal->"+linha);
				linha = dec2bin(ra);
				console.log(linha);
				//linha = linha+4;
				//console.log(linha);
				//linha = dec2bin(linha);
				$('#ra').text(linha);
				
				//console.log(linha);
			}
			//pegando o endereço do label já mapeado para dar jump
			else if(listaDeMascarasDeInstrucao[indice].acao == "jumpRegister"){
				var linhaJump = linhaInput[0].split(" ");
				//console.log(linhaJump);
				linhaJump = trim(linhaJump[1]);
				//console.log(linhaJump);
				//var linha = mapaLabel[linhaJump];
				var linha = $('#ra').text();
				//console.log("jr->"+linha);
				vetBinario.push(linha);
				//console.log(linha);
			}
			//pegando o endereço do label já mapeado para dar jump
			else if(listaDeMascarasDeInstrucao[indice].acao == "branch"){
				var linhaJump = linhaInput[0].split(",");
				//console.log("branch->"+linhaJump);
				linhaJump = trim(linhaJump[2]);
				//console.log(linhaJump);
				var linha = mapaLabel[linhaJump];
				vetBinario.push(linha);
				//console.log(linha);
			}
			//tratando o lw
			else if(listaDeMascarasDeInstrucao[indice].acao == "load"){
				return trata_Lw_Sw(listaDeMascarasDeInstrucao[indice].acao, linhaInput[0]);
			}
			//tratando o sw
			else if(listaDeMascarasDeInstrucao[indice].acao == "store"){
				return trata_Lw_Sw(listaDeMascarasDeInstrucao[indice].acao, linhaInput[0]);
			}
			//tratando o mult
			else if(listaDeMascarasDeInstrucao[indice].acao == "mult"){
				vetBinario.unshift("011000");
				return moveToLo();
				//console.log(linha);
			}
			else if (listaDeMascarasDeInstrucao[indice].acao == "mflo") {
				vetBinario.unshift("010010");
				return moveFromLo();
			}

			return init();
		}
		else{
			//console.log("ERROR!");
		}
	});

	//confere se instrução digitada existe, seta o tipo de arquitetura e dá início a montagem
	function init(){
		$.each(listaInstrucoes, function(i){
			var isInstrucao = listaInstrucoes[i].instructionRegex.test(input);

			if(isInstrucao == true){
				vetBinario.unshift(listaInstrucoes[i].opcodeFunction);
				return registerToBinary(listaInstrucoes[i].tipo);
			}
		});
	}

	//transforma para binário segundo o tipo da instrução identificado e salva na memória
	function registerToBinary(tipo){
		//armazena em uma pilha os binarios convertidos
		for(var i = 1; i< linhaInput.length; i++){
			for(var j = 0; j < listaRegistradores.length; j++){
				var isRegistrador = listaRegistradores[j].registradorRegex.test(linhaInput[i]);

				if(isRegistrador == true){
					vetBinario.push(listaRegistradores[j].binario);
				}
			}	
		}		

		if(tipo == 'R'){
			vetAux[0] = "000000";//op code
			vetAux[5] = vetBinario.shift();//function
			vetAux[3] = vetBinario.shift();//destino
			vetAux[2] = vetBinario.shift();//source
			vetAux[1] = vetBinario.shift();//source
			vetAux[4] = "00000";//shift

			var memoria = new Memoria();
			memoria.setContent(vetAux);
			insereCodigoMemoria(vetAux.join(""), linhaInput);
			//console.log("R->"+memoria.getContent());
			ucp.controlador(memoria.getContent());
			limpaVetor(vetAux);
		}

		if(tipo == 'I'){
			vetAux[0] = vetBinario.shift();//opcodeFunction
			vetAux[3] = vetBinario.shift();//destino
			vetAux[1] = vetBinario.shift();//source
			vetAux[2] = vetBinario.shift();//valor imediato

			var memoria = new Memoria();
			memoria.setContent(vetAux);
			insereCodigoMemoria(vetAux.join(""), linhaInput);
			//console.log("I->"+memoria.getContent());
			ucp.controlador(memoria.getContent());
			limpaVetor(vetAux);
		}

		else if(tipo == 'J'){
			vetAux[0] = vetBinario.shift();//address
			vetAux[1] = vetBinario.shift();//opcodeFunction

			var memoria = new Memoria();
			memoria.setContent(vetAux);
			insereCodigoMemoria(vetAux.join(""), linhaInput);
			//console.log("J->"+memoria.getContent());
			ucp.controlador(memoria.getContent());
			limpaVetor(vetAux);
		}
	}

	//inicio - funções especiais para a multiplicação
	function moveToLo(){
		//armazena em uma pilha os binarios convertidos
		for(var i = 1; i< linhaInput.length; i++){
			for(var j = 0; j < listaRegistradores.length; j++){
				var isRegistrador = listaRegistradores[j].registradorRegex.test(linhaInput[i]);

				if(isRegistrador == true){
					vetBinario.push(listaRegistradores[j].binario);
				}
			}	
		}
		vetAux[0] = "000000";//op code
		vetAux[5] = vetBinario.shift();//function
		vetAux[3] = "111111";//destino($lo)
		vetAux[2] = vetBinario.shift();//source
		vetAux[1] = vetBinario.shift();//source
		vetAux[4] = "00000";//shift

		var memoria = new Memoria();
		memoria.setContent(vetAux);
		//console.log("mToLo"+memoria.getContent());
		ucp.controlador(memoria.getContent());
		limpaVetor(vetAux);
	}

	function moveFromLo(){
		//armazena em uma pilha os binarios convertidos
		for(var i = 1; i< linhaInput.length; i++){
			for(var j = 0; j < listaRegistradores.length; j++){
				var isRegistrador = listaRegistradores[j].registradorRegex.test(linhaInput[i]);

				if(isRegistrador == true){
					vetBinario.push(listaRegistradores[j].binario);
				}
			}	
		}
		vetAux[0] = "000000";//op code
		vetAux[5] = vetBinario.shift();//function
		vetAux[3] = vetBinario.shift();//destino
		vetAux[2] = "111111";//source
		vetAux[1] = "00000";//source
		vetAux[4] = "00000";//shift

		var memoria = new Memoria();
		memoria.setContent(vetAux);
		//console.log("mFromLo"+memoria.getContent());
		ucp.controlador(memoria.getContent());
		limpaVetor(vetAux);
	}
	//fim - funções especiais para a multiplicação
	//função para o lw e sw
	function trata_Lw_Sw(comando, linhaInput){
		var indiceRegistrador1;
		var indiceRegistrador2;
		var valRegistrador1;
		var valRegistrador2;
		//pegando registrador de destino
		var linhaLoad = linhaInput.split(",");
		var register1 = linhaLoad[0].split(" ");
		register1 = trim(register1[1]);
		//console.log("register->"+register);
		//pegando o offset
		linhaLoad = linhaLoad[1].split("(");
		var offset = trim(linhaLoad[0]);
		//offset = parseInt(offset);
		//offset = offset / 4;
		//offset+=""+offset;
		//console.log("offset->"+offset);
		//pegando registrador do parâmetro
		linhaLoad = linhaLoad[1].split(")");
		var register2 = trim(linhaLoad[0]);
		//address
		var endereco;
		//var endereco = register2+offset;
		//console.log("endereco->"+endereco);
		//load ou store?
		if(comando=="load"){
			for(var t = 0; t < listaRegistradores.length; t++){
				var isRegistrador = listaRegistradores[t].registradorRegex.test(register1);
				var isRegistrador2 = listaRegistradores[t].registradorRegex.test(register2);

				if(isRegistrador == true){
					//listaRegistradores[t].valor = vetorMemoria[endereco];
					indiceRegistrador1 = t; //listaRegistradores[t].binario;
					//console.log("load->"+listaRegistradores[t].valor);
				}
				if(isRegistrador2 == true){
					valRegistrador2 = register2;//listaRegistradores[t].valor;
				}
			}
			endereco = offset + valRegistrador2;
			//listaRegistradores[indiceRegistrador1].valor = vetorMemoria[endereco];
			vetAux[1] = "00000";//source
			//vetAux[3] = dec2bin(endereco);//endereço
			vetAux[3] = endereco;
			vetAux[2] = listaRegistradores[indiceRegistrador1].binario;
			vetAux[0] = "100011";//opcodeFunction	
		}

		else if(comando=="store"){
			for(var t = 0; t < listaRegistradores.length; t++){
				var isRegistrador = listaRegistradores[t].registradorRegex.test(register1);
				var isRegistrador2 = listaRegistradores[t].registradorRegex.test(register2);

				if(isRegistrador == true){
					valRegistrador1 = $('#'+listaRegistradores[t].nome).text();
					valRegistrador1 = hex2dec(valRegistrador1);
					//console.log("store(valor)->register1="+valRegistrador1);
					//console.log("store->"+listaRegistradores[t].valor);
				}
				if(isRegistrador2 == true){
					valRegistrador2 = register2;//listaRegistradores[t].valor;
					//indiceRegistrador2 = t;
				}
			}
			endereco = offset + valRegistrador2;
			//vetorMemoria[endereco] = valRegistrador1;
			vetAux[1] = "00000";//source
			//vetAux[3] = dec2bin(endereco);//endereço
			vetAux[3] = endereco;
			vetAux[2] = valRegistrador1;
			vetAux[0] = "101011";//opcodeFunction
		}

		var memoria = new Memoria();
		memoria.setContent(vetAux);
		//console.log("lw & sw->"+memoria.getContent());
		ucp.controlador(memoria.getContent());
		limpaVetor(vetAux);
	}
}
