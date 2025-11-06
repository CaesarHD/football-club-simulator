const playerId = sessionStorage.getItem('selectedPlayerId');
console.log("Loaded player ID:", playerId);

if (playerId) {
    fetch(`/players/history/${playerId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching player history: ' + response.status);
            }
            return response.json();
        })
        .then(historyList => {
            const tableBody = document.getElementById('players-history-table');

            if (!historyList || historyList.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="7">No history found for this player.</td></tr>';
                return;
            }

            historyList.forEach(history => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${history.player ? history.player.name : '-'}</td>
                    <td>${history.club ? history.club.name : '-'}</td>
                    <td>${history.noMatches ?? 0}</td>
                    <td>${history.noGoals ?? 0}</td>
                    <td>${history.noAssists ?? 0}</td>
                    <td>${history.joinedDate || '-'}</td>
                    <td>${history.leftDate || '-'}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching history:', error));
} else {
    console.error('No playerId found in sessionStorage.');
}
