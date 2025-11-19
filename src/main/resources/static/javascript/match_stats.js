
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

    loadMatchDetails(matchId, clubId);
}

function renderPlayerStats(statsList) {
    tableBody.innerHTML = '';

    if (!statsList || statsList.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">No player statistics found for this team.</td></tr>`;
        return;
    }

    statsList.forEach(stats => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${stats.player?.name ?? '-'}</td>
            <td>${stats.position ?? '-'}</td>
            <td>${mapStartStatus(stats.statusAtTheStartOfTheMatch)}</td>
            <td>${mapEndStatus(stats.statusAtTheEndOfTheMatch)}</td>
        `;
        tableBody.appendChild(row);
    });
}


function mapStartStatus(status) {
    if (!status) return "-";

    switch (status) {
        case "WHOLE_GAME":
            return "STARTER";
        default:
            return status;
    }
}

function mapEndStatus(status) {
    if (!status) return "-";

    switch (status) {
        case "SUBSTITUTE":
            return "UNUSED SUBSTITUTE";
        case "WHOLE_GAME":
            return "INTEGRALIST";
        case "SUB_IN":
            return "SUBBED IN";
        case "SUB_OUT":
            return "SUBBED OUT";
        default:
            return status;
    }
}

function loadMatchDetails(matchId, clubId) {
    fetch(`/api/matches/details/${matchId}/${clubId}`)
        .then(res => {
            if (!res.ok) throw new Error("Failed team & player stats");
            return res.json();
        })
        .then(data => {
            renderTeamStats(data.teamStats);
            renderPlayerStats(data.players);
        })
        .catch(err => {
            console.error(err);
            tableBody.innerHTML = `<tr><td colspan="4">Failed to load stats.</td></tr>`;
        });
}

function renderTeamStats(stats) {
    const container = document.getElementById("team-stats");
    container.style.display = "block";

    document.getElementById("possession").textContent = stats.possession + "%";
    document.getElementById("shots").textContent = stats.shots;
    document.getElementById("passes").textContent = stats.passes;
    document.getElementById("corners").textContent = stats.corners;
    document.getElementById("formation").textContent = formatFormation(stats.formation);
    document.getElementById("strategy").textContent = stats.strategy;
}

function formatFormation(formation) {
    if (!formation) return "-";
    return formation.replace("F", "").replace(/_/g, "-");
}