// Baseado na implementação de STEFAN WANER - 2010
// Referência: http://www.zweigmedia.com/RealWorld/

window.onerror = myErrorTrap;
var epsilon = .00000000000001 // 10^-14
var maxSigDig = 13; 
var okToRoll = true; 

//escapando caracteres especiais do form
var tab = unescape( "%09" );
var cr = unescape( "%0D" );
var lf = unescape( "%0A" );
var symb = unescape( "%C5" );
var backSlash = unescape( "%5C" );
var gteSymbol = unescape( "%B3" );
var lteSymbol = unescape( "%B2" );
var lte = unescape ("%u2264");
var gte = unescape ("%u2265");

var singular = false;
var msFormat = false;
var maxRows = 15;
var maxCols = 30;
var numRows = 0;
var numCols = 0;
var numConstraints = 0;

var maximization = true; // problema de maximização
var phase1 = false; 
var objectiveName = "p";
var numVariables = 1;
var variables = [];
var theTableau = new makeArray2 (1,1);
var theStringTableau = new makeArray2 (1,1); // mostrar os passos
var starred = new makeArray(1); 
var TableauNumber = 1; // número de tableaus
var maxSteps = 50; // número máximo de tableaus
var numSigDigs = 6; // precisão padrão
var activeVars = new Array(); // variáveis ativas

var maxDenom = 1000; // para aproximação de fração
var tol = .000000001; //
var tooBigString = "Muitas matrizes na sua expressão," + cr + "ou sua expressão está muito complicada." + cr +"Simplique-a se possível.";
var theSampleLPString = "Max p = (1/2)x + 3y + z + 4w "+'\n'+"suj. a." + cr + "x + y + z + w <= 40" + cr + "2x + y - z - w >= 10" + cr + "w - y >= 10";

var fractionMode = false;
var integerMode = false;
var okToRoll = true;

function myErrorTrap(message,url,linenumber) {
	alert("Não foi possível processar esta entrada." + cr +" Aperte o botão 'Exemplo' para maiores detalhes.");
	return (true);
}

function sesame(url,hsize,vsize){
	var tb="toolbar=0,directories=0,status=0,menubar=0"
	tb+=",scrollbars=1,resizable=1,"
	var tbend="width="+hsize+",height="+vsize;

	if(tbend.indexOf("<undefined>")!=-1){
		tbend="width=550,height=400";
	}
	
	tb+=tbend
	Win_1 = window.open("","win1",tb);
	Win_1 = window.open(url,"win1",tb);
}

function SetupTableau() {
if (!okToRoll) return (666);

maximization = true;
	singular = false;
	var theString = document.theSpreadsheet.input.value;
	theString += cr;
	theString = stripSpaces(theString);
	theString = stripChar(theString,tab); 
	theString = stripChar(theString,":"); 
	theString = replaceSubstring(theString,lf, cr);
	theString = theString.toLowerCase();
	theString = replaceSubstring(theString, "a.", "a."+cr);
	theString = replaceSubstring(theString, ",", cr);
	theString = replaceSubstring(theString, cr+"suj.", "suj.");
	var doublecr = true;
	while (doublecr){
		if (checkString(theString,cr+cr,false) == -1) 
			doublecr = false;
		else 
			theString = replaceSubstring(theString,cr+cr,cr);
	}

	if (lastChar(theString) == cr) 
		theString = rightTrim(theString,1);
	
	theString = replaceSubstring(theString, "<=", lteSymbol);
	theString = replaceSubstring(theString, ">=", gteSymbol);
	theString = replaceSubstring(theString, lte, lteSymbol);
	theString = replaceSubstring(theString, gte, gteSymbol);

	var check = checkString(theString,"max",false)

	if (check == -1){
		check = checkString(theString,"min",false); 
		maximization = false; 
		phase1 = true;
	}
	if (check == -1) { 
		document.theSpreadsheet.expr.value = "???"; 
		document.theSpreadsheet.output.value = "Expressão não reconhecida como um PPL!" + cr + cr + "Aperte o botão Exemplo para obter um modelo de exemplo." ; 
		okToRoll = false; 
		return(666);
	}

	len = theString.length;
	theString = theString.substring(check,len);
	var tempAr = parser(theString,cr);
	var numConstTemp = tempAr[0]-1;

	for (var i = 2; i <= numConstTemp+1; i++) {
		if (tempAr[i] && tempAr[i].match(/=/)) {
			tempAr[i] = tempAr[i].replace(/=/, lteSymbol);
			tempAr[numConstTemp+2] = tempAr[i].replace(lteSymbol, gteSymbol);
			numConstTemp += 1;
			tempAr[0] += 1;
		}
	}

	var line1 = tempAr[1];
	check = checkString(line1,"suj.",true);

	if (check > 0) 
		line1 = line1.substring(0,check);

	check = checkString(line1,"=",false);

	if (check <=0) 
		return(666);

	objectiveName = line1.charAt(check-1);
	len = line1.length;
	var expression = line1.substring(check+1,len);
	var OBJ = parseLinearExpr(expression);
	variables = OBJ[0];
	numConstraints = tempAr[0]-1;
	numVariables = variables.length;
	numRows = numConstraints+1;
	numCols = numRows + numVariables + 1;
	theTableau = new makeArray2 (numRows,numCols);
	theStringTableau = new makeArray2 (numRows,numCols);

	if (phase1) 
		starred = new makeArray(numRows);

	for (var j = 1; j <= numCols; j++) 
		theTableau[numRows][j] = 0;
	for (var i = 1; i <= numVariables; i++){
		if (maximization) 
			theTableau[numRows][i] = -eval(OBJ[i]);
		else 
			theTableau[numRows][i] = eval(OBJ[i]);
	}

	theTableau[numRows][numCols-1] = 1;
	theTableau[numRows][numCols] = 0;
	theString = tempAr[2];
	var x = checkString(theString,"a.",false);
	len = theString.length;

	if (x != -1) 
		theString = theString.substring(x+2,len);

	tempAr[2] = theString;
	var GTE = false;

	for (var i = 1; i <= numConstraints; i++){
		activeVars[i] = i + numVariables;
		starred[i] = 0;
		GTE = false; 
		twoPart = parser(tempAr[1+i], lteSymbol);
		
		if (twoPart[0] < 2) {
			twoPart = parser(tempAr[1+i], gteSymbol); 
			phase1 = true; 
			GTE = true;
		}
		if (twoPart[0] <2){
			i += 1;
			okToRoll = false;
			document.theSpreadsheet.expr.value = "??? A expressão na linha " + i + " não parece ser uma desigualdade!";
			return (666);
		}

		var leftHandSide = parseLinearExpr(twoPart[1]);

		for (var j = 1; j <= numCols; j++) 
			theTableau[i][j] = 0; 

		theTableau[i][numCols] = eval(twoPart [2]);
		
		if (GTE) {
			theTableau[i][numVariables + i] = -1;
			starred[i] = 1;
			phase1 = true;
		}
		else 
			theTableau[i][numVariables + i] = 1;

		var theIndex = 0;

		for (var j = 1; j <= numVariables; j++){
			theVar = variables[j-1];
			theIndex = -1;

			for (var k = 0; k < leftHandSide[0].length; k++) {
				if (leftHandSide[0][k] == theVar) {
					theIndex = k;
					break;
				}
			}
			if (theIndex == -1) 
				theTableau[i][j] = 0;
			else 
				theTableau[i][j] = eval(leftHandSide[theIndex+1]);
		}
	}

	displayMatrix(1);
	return(1);
}

function displayFinalStatus() {
	if (TableauNumber > maxSteps) 
		document.theSpreadsheet.expr.value = "Nenhuma solução ótima foi encontrada mesmo depois de 50 passos. Abortando.";
	else if (singular) 
		document.theSpreadsheet.expr.value = "Não existe solução ótima para este problema.";
	else{
		document.theSpreadsheet.expr.value = "Solução ótima: " + objectiveName + " = ";
		var numx = 0; 
		var theRowx = 0; 
		var theColx = 0; 
		var count = 0; 
		var theChar = "";
		var theStr = "";
		var objectiveVal = theTableau[numRows][numCols];

		if (!maximization) 
			objectiveVal = - objectiveVal;
		if ((fractionMode) || (integerMode)) 
			document.theSpreadsheet.expr.value += toFrac (roundSigDig(objectiveVal,15), maxDenom, tol) + "; "; else

		document.theSpreadsheet.expr.value += roundSigDig(objectiveVal, numSigDigs).toString() + "; ";
		var thePivotPosn = new Array();
		var useThis = true;

		for (var j = 1; j <= numVariables; j++){
			useThis = true;
			count = 0;
			theRowx = 0;
			theChar = variables[j-1];
			thePivotPosn[j] = 0;
			useThis = true;
			document.theSpreadsheet.expr.value += theChar + " = ";
	
			for (var i = 1; i <= numRows; i++){
				numx = roundSigDig(theTableau[i][j],10);
				
				if (numx != 0){
					count++;

					if (numx != 0) 
						theRowx = i;
				}
			}

			if ((count == 1) && (roundSigDig(theTableau[theRowx][j],10)> 0)){
				thePivotPosn[j] = theRowx;

				for (var u = 1; u <= j-1; u++) 
					if (thePivotPosn[j] == thePivotPosn[u]) 
						useThis = false;

				if (useThis) {
					if ((fractionMode) || (integerMode)) 
						theStr = toFrac (roundSigDig((theTableau[theRowx][numCols]/theTableau[theRowx][j]),15), maxDenom, tol);
					else 
						theStr = roundSigDig((theTableau[theRowx][numCols]/theTableau[theRowx][j]),numSigDigs).toString();
				}
				else 
					theStr = "0";

				if (j < numVariables) 
					theStr += ", ";

				document.theSpreadsheet.expr.value += theStr;
			}
			else{
				theStr = "0";
				if (j < numVariables) 
					theStr += ", "; document.theSpreadsheet.expr.value += theStr;
			}
		}
	}
}

function displayMatrix(number) {
	var theString = "Tableau #" + TableauNumber + cr;
	
	if (singular) 
		theString += "undefined";
	else{
		var RowNum = numRows;
		var ColNum = numCols;
		var maxLength = 1;
		var x = "", i=0, j=0, k=0;
		var xLen = 0;

		if (integerMode) 
			theStringTableau = makeInteger(theTableau, RowNum, ColNum,true);
		else {
			for (i = 1; i <= RowNum; i++){
				for (j = 1; j <= ColNum; j++){
					if (fractionMode) 
						x = toFrac (roundSigDig(theTableau[i][j],15) , maxDenom, tol);
					else 
						x = roundSigDig(theTableau[i][j], numSigDigs).toString();
	
					xLen = x.length;
	
					if (xLen > maxLength) 
						maxLength = xLen;
					
					theStringTableau[i][j] = x;
				}
			}
		}
	
		if (maxLength < 6) 
			maxLength = 6;
	
		var spaceString = "";
	
		for (i = 0; i <= RowNum; i++){
			for (j = 1; j <= ColNum; j++){
				if (i == 0){
					if (j <= numVariables) 
						x = variables[j-1];
					else if (j == numVariables + numConstraints + 1) {
						x = objectiveName; 

						if (!maximization) 
							x = "-"+x;
					}
					else if (j < ColNum) { 
						var mmm = j - numVariables ; 
						x = "s" + mmm.toString();
					}
					else if (j == ColNum) 
						x = " ";
				}
				else 
					x = theStringTableau[i][j];
	
				sp = maxLength - x.length;
				spaceString = "";
	
				for (k = 0; k <= sp; k++) 
					spaceString += " ";

				theString += x + spaceString;
			}
			
			theString += cr;
		}
	}

	document.theSpreadsheet.output.value += theString + cr;
	return(0);
}

function makeArray3 (X,Y,Z){
	var count;
	this.length = X+1;

	for (var count = 1; count <= X+1; count++)
		this[count] = new makeArray2(Y,Z);
}

function makeArray2 (X,Y){
	var count;
	this.length = X+1;

	for (var count = 0; count <= X+1; count++)
		this[count] = new makeArray(Y);
}

function makeArray (Y){
	var count;
	this.length = Y+1;

	for (var count = 1; count <= Y+1; count++)
		this[count] = 0;
} 

function stripSpaces (InString) {
	OutString="";

	for (Count=0; Count < InString.length; Count++) {
		TempChar=InString.substring (Count, Count+1);
		
		if (TempChar!=" ")
			OutString=OutString+TempChar;
	}

	return (OutString);
}

function stripChar (InString,symbol) {
	OutString="";

	for (Count=0; Count < InString.length; Count++) {
		TempChar=InString.substring (Count, Count+1);

		if (TempChar!=symbol)
			OutString=OutString+TempChar;
	}

	return (OutString);
}

function doIt(){
	fractionMode = false;
	integerMode = false;
	var theMode = document.theSpreadsheet.Mode.selectedIndex;

	if (document.theSpreadsheet.Mode.options[theMode].text == "Fração") 
		fractionMode = true;
	else if (document.theSpreadsheet.Mode.options[theMode].text == "Inteiro") 
		integerMode = true;

	var num = doIt.arguments[0];

	if (num == 1){
		if (okToRoll){
			TableauNumber = 1;
			document.theSpreadsheet.output.value = "";
			SetupTableau();
		}
		if (okToRoll){
			theTableau = simplexMethod(theTableau,numRows,numCols);
		}
	} 
	else if (num == 2){
		okToRoll = true;
		var accuracydig = document.theSpreadsheet.acc.value;

		if ( (accuracydig == "") || (!looksLikeANumber(accuracydig)) ) { 
			document.theSpreadsheet.expr.value = "Colocar um valor de precisão de 1-13 dígitos."; 
			okToRoll = false;
		}
		if (okToRoll){
			var thenum = eval(accuracydig);
			if ((thenum < 1) || (thenum > 14)) {
				document.theSpreadsheet.expr.value = "O valor de precisão tem de ser de 1 a 13."; 
				okToRoll = false;
			}
			else 
				numSigDigs = thenum;
			if (document.theSpreadsheet.input.value == "") {
				document.theSpreadsheet.expr.value = "Coloque um PPL (ou aperte o botão Exemplo)."; 
				okToRoll = false;
			}
		}
	}
	else if (num == 3){
		document.theSpreadsheet.input.value = "";
		document.theSpreadsheet.output.value = "";
		document.theSpreadsheet.expr.value = "";
	}
	else if (num == 4){
	}
	else if (num == 5){
		document.theSpreadsheet.input.value = theSampleLPString;

		if (document.theSpreadsheet.acc.value == "") 
			document.theSpreadsheet.acc.value = numSigDigs;

		document.theSpreadsheet.expr.value = "Aperte 'resolver' para calcular a solução.";
	}
	else if (num == 6){
	}
}
