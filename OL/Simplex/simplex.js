// ******************* funções do simplex **********************
//Pivô
function pivot(InMatrix,rows,cols,theRow,theCol) {
	var thePivot = InMatrix[theRow][theCol];
	activeVars[theRow] = theCol;
	starred[theRow] = 0; 

	for (var i = 1; i <= cols; i++)
		InMatrix[theRow][i] = InMatrix[theRow][i]/thePivot;
	
	for (var i = 1; i <= rows; i++){
		if ( (i != theRow) && (InMatrix[i][theCol] != 0) ){
			var factr = InMatrix[i][theCol];
			
			for (var j = 1; j <= cols; j++)
				InMatrix[i][j] = InMatrix[i][j] - factr*InMatrix[theRow][j];
		}
	}

	return(InMatrix);
}

// SIMPLEX
function simplexMethod(InMatrix, rows, cols) {
	var negIndicator = false;
	var testRatio = new Array();
	var theRow = 0; singular = false;
	document.theSpreadsheet.expr.value = "processando...";

	while ( (phase1) && (TableauNumber <= maxSteps) ){
		var checkingForZeros = true;
		var foundAZero = false;

		while(checkingForZeros) {
			checkingForZeros = false;
			for (i = 1; i <= numRows-1; i++){
				if (starred[i] == 1) 
					break;
			}
			
			theRowx = i;

			if ((InMatrix[theRowx][cols] == 0)&&(starred[theRowx] == 1)){
				checkingForZeros = true;
				foundAZero = true;
				
				for (var j = 1; j <= cols-1; j++)
					InMatrix[theRowx][j] *= -1;

				starred[theRowx] = 0;
				TableauNumber +=1;
				document.theSpreadsheet.expr.value += "..";
				displayMatrix(1);
			}
		}

		phase1 = false;
		for (var i = 1; i <= numConstraints; i++) {
			if (starred[i] == 1) {
				phase1 = true; 
				break;
			}
		}

		if (phase1) {
			if(!foundAZero) {
				var rowmax = 0;
				for (i = 1; i <= numRows-1; i++){
					if (starred[i] == 1) 
						break;
				}

				theRowx = i;

				for (j = 1; j <= numCols-2; j++){
					numx = roundSigDig(InMatrix[i][j],10);
					if (numx > rowmax) {
						rowmax = numx; 
						theColx = j;
					}
				}

				if (rowmax == 0) {
					singular = true; 
					displayFinalStatus(); 
					return(InMatrix);
				}
				else{
					for (var i = 1; i <=rows-1; i++){
						testRatio[i] = -1;
						if (roundSigDig(InMatrix[i][theColx],maxSigDig) >0){
							if (Math.abs(InMatrix[i][cols]) < epsilon) 
								InMatrix[i][cols] = 0;
							testRatio[i] = InMatrix[i][cols]/ InMatrix[i][theColx];
						}
					}

					var minRatio = 10000000000000;
					theRow = 0;

					for (var i = 1; i <=rows-1; i++){
						if ((testRatio[i] >= 0) && (testRatio[i] < minRatio)){
							minRatio = testRatio[i];
							theRow = i;
						}
						else if ((testRatio[i] >= 0) && (testRatio[i] == minRatio)){
							if (starred[i] == 1) 
								theRow = i;
							else if (Math.random()>.5) 
								theRow = i;
						}
					}
					
					if (theRow == 0) {
						singular = true; 
						displayFinalStatus(); 
						return(InMatrix);
					}

					InMatrix = pivot(InMatrix,rows,cols,theRow,theColx);
				}

				TableauNumber +=1;
				document.theSpreadsheet.expr.value += "..";
				displayMatrix(1);
			}
		}
	}

	var testnum = 0;

	for (var i = 1; i <= cols-1; i++){
		testnum = roundSigDig(InMatrix[rows][i],10);
		if (testnum<0)
			negIndicator = true;
	}

	var theCol = 0;
	
	if (negIndicator){
		var minval = 0;
		
		for (i = 1; i <= cols-1; i++){
			testnum = roundSigDig(InMatrix[rows][i],10);
			if (testnum<minval){
				minval = testnum;
				theCol = i;
			}
		}
	}

	while ( (negIndicator) && (TableauNumber <= maxSteps) ){
		for (var i = 1; i <=rows-1; i++){
			testRatio[i] = -1;
			if (roundSigDig(InMatrix[i][theCol],maxSigDig) >0) {
				if (Math.abs(InMatrix[i][cols]) < epsilon) 
					InMatrix[i][cols] = 0;
	
				testRatio[i] = InMatrix[i][cols]/ InMatrix[i][theCol];
			}
		}
		var minRatio = 10000000000000;
		theRow = 0;

		for (var i = 1; i <=rows-1; i++){
			if ((testRatio[i] >= 0) && (testRatio[i] < minRatio)){
				minRatio = testRatio[i];
				theRow = i;
			}
			else if ((testRatio[i] >= 0) && (testRatio[i] == minRatio)){
				if (Math.random()>.5) 
					theRow = i;
			}
		}
	
		if (theRow == 0) {
			singular = true; 
			displayFinalStatus(); 
			return(InMatrix);
		}

		InMatrix = pivot(InMatrix,rows,cols,theRow,theCol);
		TableauNumber +=1;
		document.theSpreadsheet.expr.value += "..";
		displayMatrix(1);
		negIndicator = false;
		
		for (var i = 1; i <= cols-1; i++)
			if (roundSigDig(InMatrix[rows][i], 10) <0)
				negIndicator = true;
			
		if (negIndicator) {
			var minval = 0;
			for (i = 1; i <= cols-1; i++){
				testnum = roundSigDig(InMatrix[rows][i],10);
				if (testnum<minval){
					minval = testnum;
					theCol = i;
				}
			}
		}
	}
	
	displayFinalStatus();
	return(InMatrix);
}