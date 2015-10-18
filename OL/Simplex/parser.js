//*************** parsers em geral ****************
function checkString(InString,subString,backtrack){
	var found = -1;
	var theString = InString;
	var Length = theString.length;
	var symbLength = subString.length;
	
	for (var i = Length- symbLength; i >-1; i--){
		TempChar=theString.substring (i, i+ symbLength);
		
		if (TempChar == subString){
			found = i;
			if (backtrack) 
				i = -1;
		}
	}

	return(found);
}

function parser (InString, Sep) {
	var NumSeps=0; 
	var Count = 0;
	var location = new Array;
	location[0] = -1;
	var len = InString.length;

	for (Count=0; Count < len; Count++) {
		if (InString.charAt(Count)==Sep){
			NumSeps++;
			location[NumSeps] = Count;
		}
	}

	var parse = new makeArray (NumSeps+2);
	
	if (NumSeps == 0) {
		parse[0] = 1; 
		parse[1] = InString; 
		return(parse);
	}

	parse[0] = NumSeps + 1;
	
	for (var i = 1; i <=NumSeps; i++){
		parse[i] = InString.substring(location[i-1]+1, location[i]);
	}

	parse[NumSeps+1] = InString.substring(location[NumSeps]+1, len);

	return (parse);
}

function parseLinearExpr(InString) {
	InString = stripChar(InString,"(");
	InString = stripChar(InString,")");
	var stringlen = InString.length;

	if (!looksLikeANumber(InString.charAt(0))) 
		InString = "1" + InString;
	if (InString.charAt(0) != "-") 
		InString = "+"+ InString;

	var variableList = "";
	InString = replaceSubstring (InString,"+","_+");
	InString = replaceSubstring (InString,"-","_-");
	var ch = "_";
	var Ar = parser(InString, ch);
	var parsd = new makeArray (Ar[0]+1, "");

	for (var i = 1; i < Ar[0]; i++){
		parsd[i] = stripChar(Ar[i+1],"_");
	}
	
	var vars = [];
	
	for (var i = 1; i < Ar[0]; i++){
		vars[i-1] = /([a-zA-Z].*)/.exec(parsd[i])[1];
		parsd[i] = parsd[i].replace(/[a-zA-Z].*/, '');
	
		if (parsd[i] == "+") 
			parsd[i] = "1";
		else if (parsd[i] == "-") 
			parsd[i] = "-1";
	
		parsd[i] = stripChar(parsd[i],"+");
	}

	parsd[0] = vars;
	return (parsd);
}

function rightString (InString, num) {
	OutString=InString.substring (InString.length-num, InString.length);
	return (OutString);
}

function rightTrim (InString) {
	var length = InString.length;
	OutString=InString.substring(0,length-1);
	return (OutString);
}

function replaceChar (InString,oldSymbol,newSymbol) {
	var OutString="";
	var TempChar = "";
	
	for (Count=0; Count < InString.length; Count++) {
		TempChar=InString.substring (Count, Count+1);
		
		if (TempChar!=oldSymbol)
			OutString=OutString+TempChar;
		else 
			OutString=OutString+newSymbol;
	}

	return (OutString);
}

function replaceSubstring (InString,oldSubstring,newSubstring) {
	OutString="";
	var sublength = oldSubstring.length;
	
	for (Count=0; Count < InString.length; Count++) {
		TempStr=InString.substring (Count, Count+sublength);
		TempChar=InString.substring (Count, Count+1);
	
		if (TempStr!= oldSubstring)
			OutString=OutString+TempChar;
		else{
			OutString=OutString+ newSubstring;
			Count +=sublength-1;
		}
	}

	return (OutString);
}