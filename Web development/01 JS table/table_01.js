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
                }
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
    document.getElementById("unsubmittedReport").style.visibility = "visible";

}
