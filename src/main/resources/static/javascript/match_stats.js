// match-stats.js

const SELECTORS = {
    title: 'match-stats-title',
    tableBody: 'player-stats-table-body',
    teamStats: 'team-stats',
    formation: 'formation',
    strategy: 'strategy',
};

const PLAYER_STATUSES = Object.freeze(['WHOLE_GAME', 'SUBSTITUTE', 'SUB_IN', 'SUB_OUT']);
const FORMATIONS = Object.freeze(['F4_3_3', 'F4_4_2', 'F5_3_2', 'F4_3_1_2']);
const STRATEGIES = Object.freeze(['ATTACKING', 'BALANCED', 'DEFENDING']);

const ROLES = Object.freeze({
    COACH: 'COACH',
    GUEST: 'GUEST',
    PLAYER: 'PLAYER',
    ADMIN: 'ADMIN',
    MANAGER: 'MANAGER',
});

const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
const role = currentUser.role || 'GUEST';
const userId = currentUser.id;
const isLoggedIn = !!currentUser.id && currentUser.isLoggedIn === true;

class MatchStatsController {
    // Private fields
    #players = [];
    #teamStats = null;
    #matchDate = null;

    #elements = {
        title: document.getElementById(SELECTORS.title),
        tableBody: document.getElementById(SELECTORS.tableBody),
        teamStats: document.getElementById(SELECTORS.teamStats),
        formation: document.getElementById(SELECTORS.formation),
        strategy: document.getElementById(SELECTORS.strategy),
    };

    #matchId = sessionStorage.getItem('selectedMatchId');
    #clubId = sessionStorage.getItem('selectedClubId');
    #clubName = sessionStorage.getItem('selectedClubName');

    constructor() {
        this.#initialize();
    }

    #initialize() {
        if (!this.#matchId || !this.#clubId) {
            this.#elements.title.textContent = 'Error: Match or club not selected';
            return;
        }

        this.#elements.title.textContent = `${this.#clubName} – Match details`;
        this.#loadMatchDetails();
    }

    async #loadMatchDetails() {
        try {
            const response = await fetch(`/api/matches/details/${this.#matchId}/${this.#clubId}`);
            if (!response.ok) throw new Error('Failed to load match details');

            const { players, teamStats, matchDate } = await response.json();

            this.#players = players;
            this.#teamStats = teamStats;
            this.#matchDate = new Date(matchDate);

            this.#render();
        } catch (error) {
            console.error('Error loading match details:', error);
            this.#elements.title.textContent = 'Failed to load match information';
        }
    }

        #canEdit() {
            return (
            role === ROLES.COACH &&
            this.#matchDate > new Date() &&
            Number(currentUser.club.id) === Number(this.#clubId)
        );
    }

    #render() {
        const canEdit = this.#canEdit();

        this.#renderTeamStats(canEdit);
        this.#renderPlayers(canEdit);
    }

    #isPastMatch() {
        return this.#matchDate && this.#matchDate < new Date();
    }

    #renderTeamStats(canEdit) {
        if (!this.#teamStats) return;

        const isPast = this.#isPastMatch();

        // Always show the team stats container (we need formation/strategy)
        this.#elements.teamStats.style.display = 'block';

        // 1. Control visibility of stat rows (possession, shots, passes, corners)
        const statRows = document.querySelectorAll('#team-stats .stat-row');
        statRows.forEach(row => {
            row.style.display = isPast ? '' : 'none';
        });

        // 2. Fill stats only when they are visible (past matches)
        if (isPast) {
            this.#setText('possession', this.#teamStats.possession ?? '-');
            this.#setText('shots',      this.#teamStats.shots      ?? '-');
            this.#setText('passes',     this.#teamStats.passes     ?? '-');
            this.#setText('corners',    this.#teamStats.corners    ?? '-');
        }
        // Note: we don't need to clear future values - they're hidden anyway

        // 3. Formation & Strategy - always visible
        this.#elements.formation.innerHTML = '';
        this.#elements.strategy.innerHTML = '';

        const canEditFormationStrategy = canEdit && !isPast; // coach + future match

        if (canEditFormationStrategy) {
            // Editable selects
            this.#elements.formation.appendChild(
                this.#createSelect(
                    FORMATIONS,
                    this.#teamStats.formation,
                    this.#updateFormation.bind(this),
                    formatFormation
                )
            );

            this.#elements.strategy.appendChild(
                this.#createSelect(
                    STRATEGIES,
                    this.#teamStats.strategy,
                    this.#updateStrategy.bind(this),
                    formatStrategy
                )
            );
        } else {
            // Read-only text
            this.#elements.formation.textContent = formatFormation(this.#teamStats.formation);
            this.#elements.strategy.textContent = formatStrategy(this.#teamStats.strategy);
        }
    }

    #renderPlayers(canEdit) {
        this.#elements.tableBody.innerHTML = '';

        for (const playerStats of this.#players) {
            const row = document.createElement('tr');

            row.append(
                this.#createCell(playerStats.player.name),
                this.#createCell(playerStats.position ?? '-'),
                this.#createStatusCell(playerStats, 'start', canEdit),
                this.#createStatusCell(playerStats, 'end', canEdit)
            );

            this.#elements.tableBody.appendChild(row);
        }
    }

    #createCell(content) {
        const td = document.createElement('td');
        td.textContent = content;
        return td;
    }

    #createStatusCell(playerStats, type, canEdit) {
        const cell = document.createElement('td');
        const value = type === 'start' ? playerStats.statusAtTheStartOfTheMatch : playerStats.statusAtTheEndOfTheMatch;

        if (canEdit) {
            cell.appendChild(this.#createPlayerStatusSelect(playerStats, type, value));
        } else {
            cell.textContent = formatPlayerStatus(value, type);
        }

        return cell;
    }

    #createSelect(options, currentValue, onChange, formatter = String) {
        const select = document.createElement('select');
        select.className = 'form-select form-select-sm';

        for (const opt of options) {
            const option = new Option(formatter(opt), opt, false, opt === currentValue);
            select.add(option);
        }

        select.addEventListener('change', () => onChange(select.value));
        return select;
    }

    #createPlayerStatusSelect(playerStats, type, currentValue) {
        return this.#createSelect(
            PLAYER_STATUSES,
            currentValue,
            (value) => this.#updatePlayerStatus(playerStats, type, value),
            (status) => formatPlayerStatus(status, type)
        );
    }

    async #updateFormation(value) {
        this.#teamStats.formation = value;
        await this.#saveTeamSetting('formation', value);
    }

    async #updateStrategy(value) {
        this.#teamStats.strategy = value;
        await this.#saveTeamSetting('strategy', value);
    }

    async #updatePlayerStatus(playerStats, type, value) {
        if (type === 'start') {
            playerStats.statusAtTheStartOfTheMatch = value;
        } else {
            playerStats.statusAtTheEndOfTheMatch = value;
        }

        await this.#savePlayerStatus(playerStats);
    }

    async #saveTeamSetting(field, value) {
        try {
            await fetch(`/api/coaches/match/${field}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'XUserId': String(currentUser.id),
                },
                body: JSON.stringify({
                    matchId: this.#matchId,
                    clubId: this.#clubId,
                    [field]: value,
                }),
            });
        } catch (err) {
            console.error(`Failed to save ${field}:`, err);
        }
    }

    async #savePlayerStatus(playerStats) {
        try {
            await fetch('/api/coaches/match/player-status', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'XUserId': String(currentUser.id),
                },
                body: JSON.stringify({
                    matchId: this.#matchId,
                    playerId: playerStats.player.id,
                    clubId: this.#clubId,
                    startStatus: playerStats.statusAtTheStartOfTheMatch,
                    endStatus: playerStats.statusAtTheEndOfTheMatch,
                }),
            });
        } catch (err) {
            console.error('Failed to save player status:', err);
        }
    }

    #setText(id, value) {
        const el = document.getElementById(id);
        if (el) el.textContent = value;
    }
}

// ------------------- FORMATTERS -------------------
const formatFormation = (f) => (f ? f.replace('F', '').replace(/_/g, '-') : '-');

const formatStrategy = (s) => (s ? s.charAt(0) + s.slice(1).toLowerCase() : '-');

function formatPlayerStatus(status, type) {
    if (!status) return '-';

    if (type === 'start' && status === 'WHOLE_GAME') return 'STARTER';

    const map = {
        SUBSTITUTE: 'UNUSED SUBSTITUTE',
        WHOLE_GAME: 'INTEGRALIST',
        SUB_IN: 'SUBBED IN',
        SUB_OUT: 'SUBBED OUT',
    };

    return map[status] ?? status;
}

new MatchStatsController();