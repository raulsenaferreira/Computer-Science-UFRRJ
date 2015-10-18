function Memoria(){
	var exibeMemoria;
	this.memoria;

	this.setContent = function(instrucaoConvertida){	
		this.memoria = instrucaoConvertida;
		//exibição
		//exibeMemoria = instrucaoConvertida.join("");
		//console.log(exibeMemoria);
	}

	this.getContent = function(){
		//console.log(this.memoria);
        return this.memoria;
    };
	
}