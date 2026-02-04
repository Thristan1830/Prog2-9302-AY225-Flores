let studentData = [];

/* Render table function */
function renderTable() {
    const tbody = document.querySelector("#studentTable tbody");
    tbody.innerHTML = "";
    studentData.forEach((row, index) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td><input type="checkbox" data-index="${index}"></td>` +
                       row.map(val => `<td>${val}</td>`).join('');
        tbody.appendChild(tr);
    });
}

/* Parse uploaded CSV */
document.getElementById("uploadCSV").addEventListener("change", (event) => {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function(e) {
        const text = e.target.result;
        const lines = text.split(/\r?\n/);
        studentData = [];
        lines.forEach((line, i) => {
            if (i === 0 || line.trim() === "") return;
            const values = line.split(",").map(v => v.trim());
            if (values.length >= 8) studentData.push(values.slice(0,8));
        });
        renderTable();
    };
    reader.readAsText(file);
});

/* Add student */
document.getElementById("addBtn").addEventListener("click", () => {
    const newRow = [
        document.getElementById("studentId").value.trim(),
        document.getElementById("firstName").value.trim(),
        document.getElementById("lastName").value.trim(),
        document.getElementById("lab1").value.trim(),
        document.getElementById("lab2").value.trim(),
        document.getElementById("lab3").value.trim(),
        document.getElementById("prelim").value.trim(),
        document.getElementById("attendance").value.trim()
    ];
    if (newRow.some(val => val === "")) { alert("All fields must be filled!"); return; }
    studentData.push(newRow);
    renderTable();
    document.querySelectorAll("#inputs input[type='text']").forEach(input => input.value = "");
});

/* Delete selected */
document.getElementById("deleteBtn").addEventListener("click", () => {
    const checkboxes = document.querySelectorAll("#studentTable tbody input[type='checkbox']");
    studentData = studentData.filter((row, index) => !checkboxes[index].checked);
    renderTable();
});

/* Download CSV */
document.getElementById("saveBtn").addEventListener("click", () => {
    let csvContent = "StudentID,First Name,Last Name,LAB WORK 1,LAB WORK 2,LAB WORK 3,PRELIM EXAM,ATTENDANCE GRADE\n";
    studentData.forEach(row => { csvContent += row.join(",") + "\n"; });
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "StudentRecords.csv";
    a.click();
    URL.revokeObjectURL(url);
});

/* Initial render */
renderTable();