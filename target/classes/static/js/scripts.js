function navigateToCreateEvaluation() {
    location.href = '/createEvaluation';
}

function navigateToViewEvaluations() {
    location.href = '/viewEvaluations';
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

function validateSelection() {
    const selectElement = document.getElementById("inlineFormCustomSelect");
    if (selectElement.value === "") {
        alert("Please select a valid evaluation before submitting.");
        return false; // Prevent form submission
    }
    return true; // Allow form submission
}