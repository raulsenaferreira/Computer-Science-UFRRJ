/*
						***** Variaveis e Metodos Publicos *****
*/
vetorMemoria = {};
mapaLabel = {};
initOnly = false;
contadorGlobal = 0;
baseEndereco = hex2dec("400000");
//dicionario
var add ='add';
var addi ='addi';
var sub ='sub';
var subi ='subi';
var bne ='bne';
var beq ='beq';
var lw ='lw';
var sw ='sw';
var lh ='lh';
var lhu ='lhu';
var sh ='sh';
var lb ='lb';
var lbu ='lbu';
var sb ='sb';
var ll ='ll';
var sc ='sc';
var lui ='lui';
var and ='and';
var or ='or';
var nor ='nor';
var andi ='andi';
var ori ='ori';
var sll ='sll';
var srl ='srl';
var slt ='slt';
var sltu ='sltu';
var slti ='slti';
var sltiu ='sltiu';
var j ='j';
var jr ='jr';
var jal ='jal';
var mult = 'mult';
var mflo = 'mflo';
var ra = '\\$ra';
var save = '\\$s[0-7]';
var temp = '\\$t[0-9]';
var val = '\\$v[0-1]';
var arg = '\\$a[0-3]';
var sp = '\\$sp';
var zero = '\\$zero';
var espaco = '[ ]*[\t]*';
var sep = espaco+'[,]'+espaco;
var vi = '\\d+';//'\\d';//valores imediatos(dÃ­gitos)
var instLabel = '[a-zA-Z]+(?=:)';
var paramLabel = '[a-zA-Z]+';
var aP = espaco+'[(]'+espaco;
var fP = espaco+'[)]'+espaco;

//mascaras
var reg = '('+save+'|'+temp+'|'+val+'|'+arg+'|'+zero+'|'+sp+')';

listaDeMascarasDeInstrucao = [
{
	'instrucao': new RegExp(espaco+add+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'add'
},
{
	'instrucao' : new RegExp(espaco+sub+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'sub'
},
{
	'instrucao' : new RegExp(espaco+addi+espaco+reg+sep+reg+sep+vi+espaco),
	'acao' : 'imediato'
},
{
	'instrucao' : new RegExp(espaco+subi+espaco+reg+sep+reg+sep+vi+espaco),
	'acao' : 'imediato'
},
{
	'instrucao' : new RegExp(espaco+instLabel+espaco),
	'acao' : 'label'
},
{
	'instrucao' : new RegExp(espaco+bne+espaco+reg+sep+reg+sep+paramLabel+espaco),
	'acao' : 'branch'
},
{
	'instrucao' : new RegExp(espaco+beq+espaco+reg+sep+reg+sep+paramLabel+espaco),
	'acao' : 'branch'
},
{
	'instrucao' : new RegExp(espaco+jal+espaco+paramLabel),
	'acao' : 'jumpAndLink'
},
{
	'instrucao' : new RegExp(espaco+jr+espaco+ra),
	'acao' : 'jumpRegister'
},
{
	'instrucao' : new RegExp(espaco+j+espaco+paramLabel),
	'acao' : 'jump'
},
{
	'instrucao' : new RegExp(espaco+slt+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'slt'
},
{
	'instrucao' : new RegExp(espaco+slti+espaco+reg+sep+reg+sep+vi+espaco),
	'acao' : 'imediato'
},
{
	'instrucao' : new RegExp(espaco+and+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'and'
},
{
	'instrucao' : new RegExp(espaco+or+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'or'
},
{
	'instrucao' : new RegExp(espaco+ori+espaco+reg+sep+reg+sep+vi+espaco),
	'acao' : 'ori'
},
{
	'instrucao' : new RegExp(espaco+andi+espaco+reg+sep+reg+sep+vi+espaco),
	'acao' : 'andi'
},
{
	'instrucao' : new RegExp(espaco+nor+espaco+reg+sep+reg+sep+reg+espaco),
	'acao' : 'nor'
},
{
	'instrucao' : new RegExp(espaco+lw+espaco+reg+sep+vi+aP+reg+fP+espaco),
	'acao' : 'load'
},
{
	'instrucao' : new RegExp(espaco+sw+espaco+reg+sep+vi+aP+reg+fP+espaco),
	'acao' : 'store'
},
{
	'instrucao' : new RegExp(espaco+sll+espaco+reg+sep+vi+aP+reg+fP+espaco),
	'acao' : 'shift'
},
{
	'instrucao' : new RegExp(espaco+mult+espaco+reg+sep+reg+espaco),
	'acao' : 'mult'
},
{
	'instrucao' : new RegExp(espaco+mflo+espaco+reg+espaco),
	'acao' : 'mflo'
}
];

function insertTab(o, e)
{
	var kC = e.keyCode ? e.keyCode : e.charCode ? e.charCode : e.which;
	if (kC == 9 && !e.shiftKey && !e.ctrlKey && !e.altKey)
	{
		var oS = o.scrollTop;
		if (o.setSelectionRange)
		{
			var sS = o.selectionStart;
			var sE = o.selectionEnd;
			o.value = o.value.substring(0, sS) + "\t" + o.value.substr(sE);
			o.setSelectionRange(sS + 1, sS + 1);
			o.focus();
		}
		else if (o.createTextRange)
		{
			document.selection.createRange().text = "\t";
			e.returnValue = false;
		}
		o.scrollTop = oS;
		if (e.preventDefault)
		{
			e.preventDefault();
		}
		return false;
	}
	return true;
};

function dec2bin(numero, completar, qtdBits) 
{
	var bin = numero.toString(2);
	if(completar){
		var tamanho = bin.length
		for(var i = 0; i < qtdBits - tamanho; i++)
			bin = "0" + bin;
	}
	return bin;
};

function bin2dec(binario) 
{
	var numero = parseInt(binario, 2);
	return numero;
};

function hex2dec(hexa)
{
	var numero = parseInt(hexa, 16);
	return numero;
};

function dec2hex(numero, formatado)
{
	var hex = numero.toString(16);
	if(formatado){
		var tamanho = hex.length
		for(var i=0; i < 8 - tamanho; i++)
			hex = "0" + hex;
		hex = "0x" + hex;
	}
	return hex;
};

function selectTextareaLine(tarea,lineNum) {
    lineNum--; // array starts at 0
    var lines = tarea.value.split("\n");

    // calculate start/end
    var startPos = 0, endPos = tarea.value.length;
    for(var x = 0; x < lines.length; x++) {
        if(x == lineNum) {
            break;
        }
        startPos += (lines[x].length+1);

    }

    var endPos = lines[lineNum].length+startPos;
    $(".lined").selectedLine = lineNum;

    // do selection
    // Chrome / Firefox

    if(typeof(tarea.selectionStart) != "undefined") {
        tarea.focus();
        tarea.selectionStart = startPos;
        tarea.selectionEnd = endPos;
        return true;
    }

    // IE
     if (document.selection && document.selection.createRange) {
        tarea.focus();
        tarea.select();
        var range = document.selection.createRange();
        range.collapse(true);
        range.moveEnd("character", endPos);
        range.moveStart("character", startPos);
        range.select();
        return true;
    }

    return false;
}

/// debugging code
try{
	var sel = document.getElementById('lineSelector');
	var tarea = document.getElementById('tarea');
	
	sel.onchange = function() {
		selectTextareaLine(tarea,this.value);
	}
} catch(e){

}
//pega endereÃ§o das labels incrementando de 4 em 4 e armazena em um map
function mapeiaLabel(entry){
	for (var i = 1; i < entry.length; i++) {
		//console.log(i);
		$.each(listaDeMascarasDeInstrucao, function (indice) {
			var linhaTrue = listaDeMascarasDeInstrucao[indice].instrucao.test(entry[i]);
			linhaInput = listaDeMascarasDeInstrucao[indice].instrucao.exec(entry[i]);
			
			if(linhaTrue==true && listaDeMascarasDeInstrucao[indice].acao == "label"){
				//console.log("indice map"+linhaInput);
				linhaInput = trim(linhaInput[0]);
				mapaLabel[linhaInput]=dec2bin(i*4);
				//console.log("valor map"+mapaLabel[linhaInput]);			
			}
		});
	}	
}
function pula(step){

}
//limpa vetor com maior eficiencia
function limpaVetor(vetor){
	while(vetor.length > 0){
		vetor.pop();
	}
}
//retira espaÃ§o
function trim(digito) {
	return digito.replace(/^\s+|\s+$/g,"");
}
//incrementa o PC
function incrementaPC(){
	var pc = hex2dec($("#pc").text());
	pc += 4;
	$("#pc").text(dec2hex(pc, true));
}
function peek(a){
	 if (a.length>0) 
		 return a[a.length-1];
}

function initMemoria(entrada){
	
	for(var i=0; i < entrada.length; i++){
		if(entrada[i].indexOf("//") == -1 && entrada[i] != ""){
			var row = $('<tr>');
			row.append($('<td>').html(dec2hex(baseEndereco, true)));
			var td = $('<td>');
			td.html("");
			td.attr('id', "inst"+i);
			row.append(td);
			row.append($('<td>').html(trim(entrada[i])));
			$("#tabelaMemoria").append(row);
			baseEndereco += 4;
		}
	}
}

function insereCodigoMemoria(codigoBin, instrucao){
	var table = document.getElementById('tabelaMemoria');
	var rowLength = table.rows.length;
	for(var i=0; i<rowLength; i++){
	  var row = table.rows[i];
	  var cellInst = row.cells[2];
	  var cellCod = row.cells[1];
	  var data = trim(cellInst.innerHTML);
	  var inst = trim(instrucao[0].toString());
	  if(data == inst)
	    	cellCod.innerHTML = dec2hex(bin2dec(codigoBin), true);
	  
	}
}