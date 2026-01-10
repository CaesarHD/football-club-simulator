// ----------------- INIT -----------------
const matchId = sessionStorage.getItem("selectedMatchId");
const clubId = sessionStorage.getItem("selectedClubId");
const clubName = sessionStorage.getItem("selectedClubName");

const title = document.getElementById("match-stats-title");
const tableBody = document.getElementById("player-stats-table-body");
const teamStatsDiv = document.getElementById("team-stats");
const formationCell = document.getElementById("formation");
const strategyCell = document.getElementById("strategy");

const PlayerStatusInMatch = ["WHOLE_GAME","SUBSTITUTE","SUB_IN","SUB_OUT"];
const Formations = ["F4_3_3","F4_4_2","F5_3_2","F4_3_1_2"];
const MatchStrategies = ["ATTACKING","BALANCED","DEFENDING"];

let currentPlayers = [];
let currentTeamStats = null;
let matchDate = null;

if (!matchId || !clubId) {
    title.textContent = "Error: Match or club not selected";
} else {
    title.textContent = `${clubName} – Match details`;
    loadMatchDetails();
}

function loadMatchDetails() {
    fetch(`/api/matches/details/${matchId}/${clubId}`)
        .then(res => res.json())
        .then(data => {
            currentPlayers = data.players;
            currentTeamStats = data.teamStats;
            matchDate = new Date(data.matchDate);

            renderTeamStats();
            renderPlayers();
        })
        .catch(err => console.error(err));
}

function renderTeamStats() {
    if (!currentTeamStats) return;

    teamStatsDiv.style.display = "block";
    document.getElementById("possession").textContent = currentTeamStats.possession ?? "-";
    document.getElementById("shots").textContent = currentTeamStats.shots ?? "-";
    document.getElementById("passes").textContent = currentTeamStats.passes ?? "-";
    document.getElementById("corners").textContent = currentTeamStats.corners ?? "-";

    formationCell.innerHTML = renderSelectOrText(
        currentTeamStats.formation, Formations, onFormationChange, formatFormation
    );
    strategyCell.innerHTML = renderSelectOrText(
        currentTeamStats.strategy, MatchStrategies, onStrategyChange, formatStrategy
    );
}

function renderSelectOrText(value, options, onChangeHandler, formatter) {
    if (matchDate <= new Date()) return formatter(value);

    return `<select class="form-select form-select-sm" onchange="(${onChangeHandler.name})(this)">
        ${options.map(o => `<option value="${o}" ${o === value ? "selected" : ""}>${formatter(o)}</option>`).join("")}
    </select>`;
}

function onFormationChange(select) {
    currentTeamStats.formation = select.value;
    fetch('/api/coaches/match/formation', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ matchId, clubId, formation: select.value })
    }).catch(err => console.error(err));
}

function onStrategyChange(select) {
    currentTeamStats.strategy = select.value;
    fetch('/api/coaches/match/strategy', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ matchId, clubId, strategy: select.value })
    }).catch(err => console.error(err));
}

function renderPlayers() {
    tableBody.innerHTML = "";
    currentPlayers.forEach(p => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${p.player.name}</td>
            <td>${p.position ?? "-"}</td>
            <td>${renderPlayerSelectOrText(p, "start")}</td>
            <td>${renderPlayerSelectOrText(p, "end")}</td>
        `;
        tableBody.appendChild(row);
    });
}

function renderPlayerSelectOrText(playerStats, type) {
    const value = type === "start" ? playerStats.statusAtTheStartOfTheMatch : playerStats.statusAtTheEndOfTheMatch;
    if (matchDate <= new Date()) return formatPlayerStatus(value, type);

    return `<select class="form-select form-select-sm" onchange="onPlayerStatusChange(${playerStats.player.id}, '${type}', this)">
        ${PlayerStatusInMatch.map(s => `<option value="${s}" ${s === value ? "selected" : ""}>${formatPlayerStatus(s, type)}</option>`).join("")}
    </select>`;
}

function onPlayerStatusChange(playerId, type, select) {
    const stats = currentPlayers.find(p => p.player.id === playerId);
    if (!stats) return;
    if (type === "start") stats.statusAtTheStartOfTheMatch = select.value;
    else stats.statusAtTheEndOfTheMatch = select.value;

    fetch('/api/matches/player-status', {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            matchId,
            playerId,
            startStatus: stats.statusAtTheStartOfTheMatch,
            endStatus: stats.statusAtTheEndOfTheMatch
        })
    }).catch(err => console.error(err));
}

function formatFormation(f) { return f ? f.replace("F", "").replaceAll("_", "-") : "-"; }
function formatStrategy(s) { return s ? s.charAt(0) + s.slice(1).toLowerCase() : "-"; }

function formatPlayerStatus(status, type) {
    if (!status) return "-";
    if (type === "start" && status === "WHOLE_GAME") return "STARTER";
    switch (status) {
        case "SUBSTITUTE": return "UNUSED SUBSTITUTE";
        case "WHOLE_GAME": return "INTEGRALIST";
        case "SUB_IN": return "SUBBED IN";
        case "SUB_OUT": return "SUBBED OUT";
        default: return status;
    }
}
