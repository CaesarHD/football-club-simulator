const matchId = sessionStorage.getItem('selectedMatchId');
console.log("Loaded match ID:", matchId);

if (!matchId) {
    document.body.insertAdjacentHTML('beforeend',
        '<p style="color:red">Error: No matchId in URL</p>');
} else {
    fetch(`/api/matches/goals/${matchId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching goals: ' + response.status);
            }
            return response.json();
        })
        .then(goals => {
            const tableBody = document.getElementById('goals-table');

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
                    <td>${goal.club?.name ?? 'Unknown'}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
