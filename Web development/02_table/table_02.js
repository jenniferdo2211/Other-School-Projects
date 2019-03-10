function finalGrade() {
    var table = document.getElementById("table");
    var allRows = table.rows;


    //for each row
    for (var i = 1; i < allRows.length; i++) {
        var sum = 0;
        var numAssigns = 0;
        var allCells = allRows[i].cells;

        //for each cell
        for (var j = 2; j < allCells.length - 1; j++) {
            var val = parseInt(allCells[j].innerHTML);
            if (!isNaN(val)) {
                if (val >= 0 && val <= 100) {
                    numAssigns += 1;
                    sum += val;
                } else {
                    alert("Grade at column " + (j + 1) + " row " + (i + 1) + " is " + val + " (Invalid grade).\nMin grade is 0 and max grade is 100!");
                    allCells[j].innerHTML = "-";
                }
            } else if (allCells[j].innerHTML != "" && allCells[j].innerHTML != "-") {
                alert("Grade is a number from 0 to 100");
                allCells[j].innerHTML = "-";
            }

        }

        var avg = 0;
        if (numAssigns != 0) {
            avg = Math.round(sum / numAssigns);
        }

        allCells[allCells.length - 1].innerHTML = avg;
        if (avg < 40) {
            allCells[allCells.length - 1].style.color = "white";
            allCells[allCells.length - 1].style.backgroundColor = "red";
        } else {
            allCells[allCells.length - 1].style.color = "black";
            allCells[allCells.length - 1].style.backgroundColor = "white";
        }

    }
}

//extra credit: number 2
function unSubmittedAssignment() {
    var table = document.getElementById("table");
    var allRows = table.rows;

    var numUnsubmittedAssigns = 0;
    //for each row
    for (var i = 1; i < allRows.length; i++) {
        var sum = 0;
        var allCells = allRows[i].cells;

        //for each cell
        for (var j = 2; j < allCells.length - 1; j++) {
            var val = parseInt(allCells[j].innerHTML);
            if (isNaN(val)) {
                numUnsubmittedAssigns += 1;
                allCells[j].style.backgroundColor = "yellow";
            }
        }
    }

    document.getElementById("unsubmittedReport").innerHTML = "Number of unsubmitted assignments: " + numUnsubmittedAssigns;
    document.getElementById("unsubmittedReport").style.display = "block";

}


//-------------------------------
// Assignment 02 LAB 4
function insertARow() {
    var table = document.getElementById("table");
    var row = table.insertRow(-1);
    row.setAttribute("onclick", "selectTheRow(this)")
    for (var i = 0; i < table.rows[0].cells.length; i++) {
        var cell = row.insertCell(i);
        cell.innerHTML = "-";
        cell.setAttribute("contenteditable", true);
        if (i == 0 | i == 1) {
            cell.style.textAlign = "left";
        } else {
            cell.style.textAlign = "right";
            if (i != table.rows[0].cells.length - 1) {
                cell.setAttribute("onkeyup", "finalGrade()");
            }
        }
    }
}

function saveChanges() {
    var table = document.getElementById("table");
    var noRow = table.rows.length;
    var noCell = table.rows[0].cells.length;
    document.cookie = "row=" + noRow;
    document.cookie = "cell=" + noCell;
    alert("Change is saved: row = " + noRow + " cell = " + noCell);
}

function insertAColumn() {
    var table = document.getElementById("table");
    var rows = table.rows;
    var position = rows[0].cells.length - 1;

    for (var i = 0; i < rows.length; i++) {
        var cell = rows[i].insertCell(position);
        cell.innerHTML = "-";
        cell.setAttribute("contenteditable", true);
        if (i == 0) {
            cell.style = "background-color: green; color: yellow; text-align: center;";
            cell.setAttribute("onclick", "selectTheColumn(" + position + ")");
        } else {
            cell.style.textAlign = "right";
        }

    }
}

function undoTable() {
    var currentData = new Array(0);
    var table = document.getElementById("table");
    var rows = table.rows;

    for (var i = 0; i < rows.length; i++) {
        for (var j = 0; j < rows[0].cells.length; j++) {
            currentData.push(rows[i].cells[j].innerHTML);
        }
    }

    //alert("Data: " + currentData.toString());

    var pairs = document.cookie.split(";");
    var cookieRow = 2,
        cookieCell = 4;

    for (var i = 0; i < 2; i++) {
        var pair = pairs[i];
        while (pair.charAt(0) == ' ') {
            pair = pair.substring(1, pair.length);
        }
        while (pair.charAt(pair.length - 1) == ' ') {
            pair = pair.substring(0, pair.length - 1);
        }
        if (pair.indexOf("row=") == 0) {
            cookieRow = parseInt(pair.substring(4, pair.length));
        } else if (pair.indexOf("cell=") == 0) {
            cookieCell = parseInt(pair.substring(5, pair.length));
        }  
       
    }

    //alert("row=" + cookieRow + ";" + "cell=" + cookieCell);

    var currentRow = table.rows.length;
    var currentCell = table.rows[0].cells.length;
    var position;

    while (currentRow > cookieRow) {
        table.deleteRow(currentRow - 1);
        currentRow -= 1;
    }

    while (currentCell > cookieCell) {
        for (var i = 0; i < rows.length; i++) {
            rows[i].deleteCell(currentCell - 2);
        }
        currentCell -= 1;
    }

    //alert("Current: row=" + currentRow + ";" + "cell=" + currentCell);  
	 alert("Back to previous state: row = " + cookieRow + " cell = " + cookieCell);
}

// Extra credit
function selectTheRow(element) {
    var table = document.getElementById("table");
    var rows = table.rows;

    for (var j = 0; j < rows[0].cells.length; j++) {
        rows[0].cells[j].style.backgroundColor = "green";
    }

    for (var i = 1; i < rows.length; i++) {
        for (var j = 0; j < rows[i].cells.length; j++) {
            rows[i].cells[j].style.backgroundColor = "white";
        }
    }

    for (var j = 0; j < element.cells.length; j++) {
        element.cells[j].style.backgroundColor = "#FFA500";
    }
}

// Extra credit
function selectTheColumn(indexCol) {
    var table = document.getElementById("table");
    var rows = table.rows;

    for (var j = 0; j < rows[0].cells.length; j++) {
        rows[0].cells[j].style.backgroundColor = "green";
    }
    rows[0].cells[indexCol].style.backgroundColor = "#FFA500";

    for (var i = 1; i < rows.length; i++) {
        var cells = rows[i].cells;
        for (var j = 0; j < cells.length; j++) {
            if (j != indexCol) {
                cells[j].style.backgroundColor = "white";
            } else {
                cells[j].style.backgroundColor = "#FFA500";
            }
        }
    }

}


// Extra credit
function deleteRow() {
    var table = document.getElementById("table");
    var rows = table.rows;
    var indexRow;

    for (var i = 1; i < rows.length; i++) {
        var cells = rows[i].cells;
        if (cells[0].style.backgroundColor === "rgb(255, 165, 0)" && cells[1].style.backgroundColor === "rgb(255, 165, 0)") {
            indexRow = i;
        }
    }

    if (!isNaN(indexRow)) {
        table.deleteRow(indexRow);
        alert("Successfully deleted the row");
    } else {
        alert("Please select a row to delete");
    }


}

//Extra credit
function deleteColumn() {
    var table = document.getElementById("table");
    var rows = table.rows;
    var indexCol;

    for (var j = 0; j < rows[0].cells.length; j++) {
        if (rows[0].cells[j].style.backgroundColor === "rgb(255, 165, 0)") {
            indexCol = j;
        }
    }
    if (!isNaN(indexCol) && indexCol != 0 && indexCol != 1 && indexCol != rows[0].cells.length - 1) {
        for (var i = 0; i < rows.length; i++) {
            rows[i].deleteCell(indexCol);
        }
        alert("Successfully deleted column");
        finalGrade();
    } else {
        alert("Please select a column to delete");
    }


}
