
const matchId = sessionStorage.getItem('selectedMatchId');
const clubId = sessionStorage.getItem('selectedClubId');
const clubName = sessionStorage.getItem('selectedClubName');
const tableBody = document.getElementById('player-stats-table-body');
const title = document.getElementById('match-stats-title');

if (!matchId || !clubId) {
    title.textContent = "Error: Match ID or Club ID is missing.";
    tableBody.innerHTML = `<tr><td colspan="4">Please navigate from the matches list.</td></tr>`;
} else {
    title.textContent = `${clubName} players stats in the match:`;

    loadPlayerStats(matchId, clubId);
}

function loadPlayerStats(matchId, clubId) {

    fetch(`/api/matches/stats/${matchId}/${clubId}`)
        .then(res => {
            if (!res.ok) throw new Error(`Error fetching player stats: ${res.status}`);
            return res.json();
        })
        .then(statsList => renderPlayerStats(statsList))
        .catch(err => {
            console.error('Failed to load player stats:', err);
            tableBody.innerHTML = `<tr><td colspan="4">Failed to load player stats.</td></tr>`;
        });
}

function renderPlayerStats(statsList) {
    tableBody.innerHTML = '';

    if (!statsList || statsList.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4">No player statistics found for this team.</td></tr>`;
        return;
    }

    statsList.forEach(stats => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${stats.player?.id ?? '-'}</td>
            <td>${stats.player?.name ?? '-'}</td>
            <td>${stats.position ?? '-'}</td>
            <td>${stats.status ?? '-'}</td>
        `;
        tableBody.appendChild(row);
    });
}