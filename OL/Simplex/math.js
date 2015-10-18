// ******************** Funções matemáticas ******************
function hcf (a,b) {
	var bigger = Math.abs(a);
	var smaller = Math.abs(b);
	var x = 0;
	var theResult = 1;

	if ( (a == 0) || (b == 0) ) 
		return(1);
	if (smaller > bigger) {
		x = bigger; 
		bigger = smaller; 
		smaller = x;
	}

	var testRatio = roundSigDig(bigger/smaller, 11);
	var testRatio2 = 0;

	if (testRatio == Math.floor(testRatio) ) 
		return (smaller);
	else{
		var found = false;
		var upperlimit = smaller;
		for (var i = upperlimit; i >= 2; i--){
			testRatio = roundSigDig(smaller/i, 10);
			testRatio2 = roundSigDig(bigger/i, 10);

			if ( (testRatio == Math.floor(testRatio) ) && (testRatio2 == Math.floor(testRatio2) ) ){
				smaller = Math.round(smaller/i);
				smaller = Math.round(bigger/i);
				return(theResult *hcf(bigger, smaller) );
			}
		}
		return(theResult);
	}
	alert("erro!");
	return(-1);
}

// mínimo múltiplo comum
function mmc(a,b) {
	var bigger = Math.abs(a);
	var smaller = Math.abs(b);
	var x = 0;

	if ( (a == 0) || (b == 0) ) 
		return(1);
	if (smaller > bigger) {
		x = bigger; 
		bigger = smaller; 
		smaller = x
	}

	var testRatio = roundSigDig(bigger/smaller, 11);

	if (testRatio == Math.floor(testRatio) ) 
		return (bigger);
	else{
		var found = false;
		
		for (var i = 2; i <= smaller; i++){
			if (i*i >= smaller) 
				break;

			testRatio = roundSigDig(smaller/i, 11);

			if (testRatio == Math.floor(testRatio) ){
				smaller = testRatio;
				bigger = bigger*i;
				return( mmc(bigger, smaller) );
			}
		}
		return(bigger*smaller);
	}
	alert("erro!");
	return(-1); 
}

// reduzindo fração
function reduce(fraction){
	with (Math){
		var HCF = hcf(fraction[1], fraction[2]);
		fraction[1] = Math.round(fraction[1]/HCF);
		fraction[2] = Math.round(fraction[2]/HCF);
	}
	return(fraction);
} 

//igual a função de cima só retorna um array ao invés de uma string
function toFracArr(x, maxDenom, tol) {
	var theFrac = new Array();
	theFrac[1] = 0;
	theFrac[2] = 0;
	var p1 = 1;
	var p2 = 0;
	var q1 = 0;
	var q2 = 1;
	var u =0;
	var t = 0;
	var flag = true;
	var negflag = false;
	var a = 0;
	var xIn = x;

	if (x >10000000000) 
		return(theFrac);

	while (flag){
		if (x<0) {
			x = -x; 
			negflag = true; 
			p1 = -p1
		}
		
		var intPart = Math.floor(x);
		var decimalPart = roundSigDig((x - intPart),15);
		x = decimalPart;
		a = intPart;
		t = a*p1 + p2;
		u = a*q1 + q2;
		
		if ( (Math.abs(t) > 10000000000 ) || (u > maxDenom ) ){
			n = p1;
			d = q1;
			break;
		}

		p = t;
		q = u;
		
		if ( x == 0 ){
			n = p;
			d = q;
			break;
		}

		p2 = p1;
		p1 = p;
		q2 = q1;
		q1 = q;
		x = 1/x;
	}
	
	theFrac[1] = n;
	theFrac[2] = d;
	
	return(theFrac);
}

function toFrac(x, maxDenom, tol) {
	var theFrac = new Array();
	theFrac[1] = 0;
	theFrac[2] = 0;
	var p1 = 1;
	var p2 = 0;
	var q1 = 0;
	var q2 = 1;
	var u =0;
	var t = 0;
	var flag = true;
	var negflag = false;
	var a = 0;
	var xIn = x; // variable for later
	
	if (x >10000000000) 
		return(theFrac);
	
	while (flag){
		if (x<0) {
			x = -x; 
			negflag = true; 
			p1 = -p1
		}

		var intPart = Math.floor(x);
		var decimalPart = roundSigDig((x - intPart),15);
		x = decimalPart;
		a = intPart;
		t = a*p1 + p2;
		u = a*q1 + q2;
		
		if ( (Math.abs(t) > 10000000000 ) || (u > maxDenom ) ){
			n = p1;
			d = q1;
			break;
		}
	
		p = t;
		q = u;
	
		if ( x == 0 ){
			n = p;
			d = q;
			break;
		}

		p2 = p1;
		p1 = p;
		q2 = q1;
		q1 = q;
		x = 1/x;
	}

	theFrac[1] = n;
	theFrac[2] = d;
	
	if (theFrac[2] == 1) 
		return (theFrac[1].toString());
	else 
		return (theFrac[1] + "/" + theFrac[2]);
} 

function lastChar(theString) {
	if (theString == "") 
		return(theString);

	var len = theString.length;
	
	return theString.charAt(len-1);
}

function isCharHere (InString, RefString) {
	if(InString.length!=1)
		return (false);
	if (RefString.indexOf (InString, 0)==-1)
		return (false);
	
	return (true);
}

function roundSix(theNumber) {
	var x = (Math.round(1000000*theNumber))/1000000;
	return(x);
}

function shiftRight(theNumber, k) {
	if (k == 0) 
		return (theNumber);
	else {
		var k2 = 1;
		var num = k;
		
		if (num < 0) 
			num = -num;
		
		for (var i = 1; i <= num; i++)
			k2 = k2*10;
	}

	if (k>0){
		return(k2*theNumber);
	}
	else{
		return(theNumber/k2);
	}
}

function roundSigDig(theNumber, numDigits) {
	numDigits = numDigits -1
	with (Math)	{
		if (theNumber == 0) 
			return(0);
		else if(abs(theNumber) < 0.000000000001)
			return(0);
		else{
			var k = floor(log(abs(theNumber))/log(10))-numDigits
			var k2 = shiftRight(round(shiftRight(abs(theNumber),-k)),k)
			
			if (theNumber > 0) 
				return(k2);
			else 
				return(-k2);
		}
	}
}

function looksLikeANumber(theString) {
	var result = true;
	var length = theString.length;
	var x = ""
	var y = "1234567890-+^*./ "
	var yLength = y.length;

	for (var i = 0; i <= length; i++){
		x = theString.charAt(i);
		result = false;
		
		for (var j = 0; j <= yLength; j++)
			if (x == y.charAt(j)) {
				result = true; 
				break;
			}

		if (result == false) 
			return(false);
	}

	return(result);
}

//matriz de inteiros de mmc
function makeInteger(theMatrix, RowNum, ColNum,Strings) {
	var rowArray = new makeArray2(ColNum,2);
	var outArray = new makeArray2(RowNum,ColNum);

	for (var i = 1; i <= RowNum; i++){
		for (var j = 1; j <= ColNum; j++)
			for (var k = 1; k <= 2; k++) 
				rowArray[j][k] = toFracArr(theMatrix[i][j],maxDenom, tol)[k];

		var rowLcm = 1;

		for (j = 1; j <= ColNum; j++) 
			rowLcm = mmc(rowLcm,rowArray[j][2]);
		
		var x = 0;
		
		for (j = 1; j <= ColNum; j++){
			x = rowLcm*rowArray[j][1]/rowArray[j][2];
			
			if (!Strings) 
				outArray[i][j] = Math.round(x);
			else 
				outArray[i][j] = Math.round(x).toString();
		}

		outArray[0][j] = rowLcm;
	}

	return(outArray);
}