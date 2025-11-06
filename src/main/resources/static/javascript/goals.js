// ------------------------
// Goals Page JS
// ------------------------

// Get DOM element
const tableBody = document.getElementById('goals-table');

// Get match ID from sessionStorage
const matchId = sessionStorage.getItem('selectedMatchId');

// Check if matchId exists
if (!matchId) {
    console.error('No match selected!');
    tableBody.innerHTML = '<tr><td colspan="3">No match selected.</td></tr>';
} else {
    // Load goals for this match
    loadGoals(matchId);
}

// Function to load goals from server
function loadGoals(matchId) {
    fetch(`/api/matches/goals?matchId=${matchId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching goals: ' + response.status);
            }
            return response.json();
        })
        .then(goals => {
            renderGoals(goals);
        })
        .catch(error => {
            console.error('Failed to load goals:', error);
            tableBody.innerHTML = '<tr><td colspan="3">Failed to load goals.</td></tr>';
        });
}

// Function to render goals in table
function renderGoals(goals) {
    tableBody.innerHTML = '';

    if (!goals || goals.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="3">No goals recorded.</td></tr>';
        return;
    }

    goals.forEach(goal => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${goal.minute ?? '-'}</td>
            <td>${goal.player?.name ?? 'Unknown'}</td>
            <td>${goal.goalType ?? '-'}</td>
        `;
        tableBody.appendChild(row);
    });
}
