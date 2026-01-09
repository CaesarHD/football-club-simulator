const matchId = sessionStorage.getItem('selectedMatchId');
const clubId = sessionStorage.getItem('selectedClubId');
const clubName = sessionStorage.getItem('selectedClubName');

const tableBody = document.getElementById('player-stats-table-body');
const title = document.getElementById('match-stats-title');

window.currentStatsList = [];

const PlayerStatusInMatch = [
    "WHOLE_GAME",
    "SUBSTITUTE",
    "SUB_IN",
    "SUB_OUT"
];

if (!matchId || !clubId) {
    title.textContent = "Error: Match ID or Club ID is missing.";
    tableBody.innerHTML =
        `<tr><td colspan="4">Please navigate from the matches list.</td></tr>`;
} else {
    title.textContent = `${clubName} players stats in the match:`;
    loadMatchDetails(matchId, clubId);
}

function loadMatchDetails(matchId, clubId) {
    fetch(`/api/matches/details/${matchId}/${clubId}`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to load team & player stats");
            return res.json();
        })
        .then(data => {
            window.currentStatsList = data.players;
            renderTeamStats(data.teamStats);
            renderPlayerStats(data.players, data.matchDate);
        })
        .catch(err => {
            console.error(err);
            tableBody.innerHTML =
                `<tr><td colspan="4">Failed to load stats.</td></tr>`;
        });
}

function renderPlayerStats(statsList, matchDate) {
    tableBody.innerHTML = '';

    if (!statsList || statsList.length === 0) {
        tableBody.innerHTML =
            `<tr><td colspan="4">No player statistics found for this team.</td></tr>`;
        return;
    }

    const now = new Date();

    statsList.forEach(stats => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${stats.player?.name ?? '-'}</td>
            <td>${stats.position ?? '-'}</td>
            <td>${createStatusSelect(stats, "start", matchDate, now)}</td>
            <td>${createStatusSelect(stats, "end", matchDate, now)}</td>
        `;

        tableBody.appendChild(row);
    });
}

function createStatusSelect(stats, type, matchDate, now) {
    const value = type === "start"
        ? stats.statusAtTheStartOfTheMatch
        : stats.statusAtTheEndOfTheMatch;

    if (new Date(matchDate) <= now) {
        return mapStatusLabel(value, type);
    }

    return `
        <select class="form-select form-select-sm"
                onchange="onStatusChange(${stats.player.id}, this, '${type}')">
            ${PlayerStatusInMatch.map(status => `
                <option value="${status}" ${status === value ? "selected" : ""}>
                    ${mapStatusLabel(status, type)}
                </option>
            `).join("")}
        </select>
    `;
}

function mapStatusLabel(status, type) {
    if (!status) return "-";

    if (type === "start") {
        return status === "WHOLE_GAME" ? "STARTER" : status;
    }

    switch (status) {
        case "SUBSTITUTE": return "UNUSED SUBSTITUTE";
        case "WHOLE_GAME": return "INTEGRALIST";
        case "SUB_IN":     return "SUBBED IN";
        case "SUB_OUT":    return "SUBBED OUT";
        default:           return status;
    }
}

function onStatusChange(playerId, select, type) {
    const newValue = select.value; // ALWAYS enum name

    // Find the stats for this player
    const stats = window.currentStatsList.find(s => s.player.id === playerId);
    if (!stats) return;

    if (type === "start") {
        stats.statusAtTheStartOfTheMatch = newValue;
    } else {
        stats.statusAtTheEndOfTheMatch = newValue;
    }

    // Save immediately to backend
    savePlayerStatus(playerId);
}

function savePlayerStatus(playerId) {
    const stats = window.currentStatsList.find(s => s.player.id === playerId);
    if (!stats) return;

    const url = `/api/matches/${matchId}/${playerId}/${stats.statusAtTheStartOfTheMatch}/${stats.statusAtTheEndOfTheMatch}`;

    console.log("Calling PUT:", url);

    fetch(url, { method: "PUT" })
        .then(res => {
            if (!res.ok) throw new Error("Failed to save status");
            console.log("Saved status for player ID:", playerId);
        })
        .catch(err => console.error(err));
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
