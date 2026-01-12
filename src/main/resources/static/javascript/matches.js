// matches.js
const urlParams = new URLSearchParams(window.location.search);

// Get current user
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
const userRole = currentUser.role || 'GUEST';
const userClubId = currentUser.profile?.club?.id ?? null;
const userClubName = currentUser.profile?.club?.name ?? null;

// Get clubId & season from URL
let clubId = urlParams.get('clubId')
    || (userRole === 'COACH' ? userClubId : sessionStorage.getItem('selectedClubId'));
let season = urlParams.get('season');

// DOM elements
const seasonSelect = document.getElementById('season-select');
const clubTitle = document.getElementById('club-name');
const tableBody = document.getElementById('matches-table').querySelector('tbody');

// Only require clubId for coaches
if (userRole === 'COACH' && !clubId) {
    clubTitle.textContent = 'Club not found';
    throw new Error('User club missing');
}

// ---------------- Helper ----------------
function formatDate(isoString) {
    if (!isoString) return '-';
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(isoString).toLocaleString(undefined, options);
}

// ---------------- Load seasons ----------------
fetch('/api/matches/seasons')
    .then(res => res.json())
    .then(data => {
        const firstSeason = data.firstSeason;
        const latestSeason = data.latestSeason;

        for (let year = latestSeason; year >= firstSeason; year--) {
            const option = document.createElement('option');
            option.value = year;
            option.textContent = year;
            seasonSelect.appendChild(option);
        }

        season = season || latestSeason;
        seasonSelect.value = season;

        loadMatches(season);
    })
    .catch(err => console.error('Failed to load seasons:', err));

// ---------------- Load matches ----------------
function loadMatches(season) {
    tableBody.innerHTML = `<tr><td colspan="5">Loading matches...</td></tr>`;

    if (userRole === 'GUEST' || userRole === 'PLAYER') {
        // Guests or players fetch matches for the selected club and season
        if (!clubId) {
            tableBody.innerHTML = `<tr><td colspan="5">Please select a club.</td></tr>`;
            return;
        }

        fetch('/api/matches/club', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ clubId: clubId, year: season })
        })
            .then(res => {
                if (!res.ok) throw new Error(`Error fetching matches: ${res.status}`);
                return res.json();
            })
            .then(matches => renderMatches(matches))
            .catch(err => {
                console.error('Failed to load matches:', err);
                tableBody.innerHTML = `<tr><td colspan="5">Failed to load matches.</td></tr>`;
            });

        clubTitle.textContent = `Season ${season} matches for ${userClubName || 'Selected Club'}`;

    } else {
        // Coaches fetch only their club matches via POST
        fetch('/api/matches/club', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ clubId: clubId, year: season })
        })
            .then(res => {
                if (!res.ok) throw new Error(`Error fetching matches: ${res.status}`);
                return res.json();
            })
            .then(matches => renderMatches(matches))
            .catch(err => {
                console.error('Failed to load matches:', err);
                tableBody.innerHTML = `<tr><td colspan="5">Failed to load matches.</td></tr>`;
            });

        clubTitle.textContent = `Season ${season} matches for ${userClubName || 'Unknown Club'}`;
    }

    updateUrlQuery(season);
}

// ---------------- Render matches ----------------
async function renderMatches(matches) {
    tableBody.innerHTML = '';
    if (!matches || matches.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">No matches found.</td></tr>`;
        return;
    }

    const now = new Date();

    // For coaches: filter only their club matches
    if (userRole === 'COACH' && userClubId) {
        matches = matches.filter(m =>
            m.homeClub?.id === userClubId || m.awayClub?.id === userClubId
        );
    }

    for (const match of matches) {
        const matchDate = new Date(match.date);
        const isFuture = matchDate > now;

        // Score cell
        let scoreCell = '';
        if (isFuture) {
            scoreCell = `<span>vs</span>`;
        } else {
            scoreCell = `<a href="javascript:void(0)" onclick="openGoalsStats(${match.id})">
                            ${match.homeTeamNoGoals ?? '-'} - ${match.awayTeamNoGoals ?? '-'}
                         </a>`;
        }

        // ---------------- Club cell ----------------
        // FIXED: GUEST can now click on club names too
        const createClubCell = (club) => {
            if (!club?.name) return '-';

            // ALL roles can click on club names now (GUEST, PLAYER, MANAGER, COACH)
            const safeName = club.name.replace(/'/g, "\\'");
            return `<a href="javascript:void(0)" onclick="openMatchStats(${match.id}, ${club.id}, '${safeName}')">${club.name}</a>`;
        };

        const homeClubContent = createClubCell(match.homeClub);
        const awayClubContent = createClubCell(match.awayClub);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${homeClubContent}<div class="goals-home" style="font-size:0.8em;color:grey;"></div></td>
            <td>${scoreCell}</td>
            <td>${awayClubContent}<div class="goals-away" style="font-size:0.8em;color:grey;"></div></td>
            <td>${formatDate(match.date)}</td>
        `;
        tableBody.appendChild(row);

        // ---------------- Load goals for past matches ----------------
        if (!isFuture) {
            try {
                const res = await fetch(`/api/matches/goals/${match.id}`);
                if (!res.ok) throw new Error('Failed to load goals');
                const goals = await res.json();

                const homeGoalsDiv = row.querySelector('.goals-home');
                const awayGoalsDiv = row.querySelector('.goals-away');

                const homeGoals = goals.filter(g => g.club?.id === match.homeClub?.id);
                const awayGoals = goals.filter(g => g.club?.id === match.awayClub?.id);

                if (homeGoals.length) {
                    homeGoalsDiv.innerHTML = homeGoals
                        .sort((a, b) => a.minute - b.minute)
                        .map(g => `⚽ ${g.minute}' ${g.player?.name ?? 'Unknown'}`)
                        .join('<br>');
                }

                if (awayGoals.length) {
                    awayGoalsDiv.innerHTML = awayGoals
                        .sort((a, b) => a.minute - b.minute)
                        .map(g => `⚽ ${g.minute}' ${g.player?.name ?? 'Unknown'}`)
                        .join('<br>');
                }
            } catch (err) {
                console.error('Failed to load goals for match', match.id, err);
            }
        }
    }
}

// ---------------- URL helper ----------------
function updateUrlQuery(season) {
    const newUrlParams = new URLSearchParams();
    if (clubId) newUrlParams.set('clubId', clubId);
    newUrlParams.set('season', season);
    const newUrl = `${window.location.pathname}?${newUrlParams.toString()}`;
    window.history.replaceState({}, '', newUrl);
}

// ---------------- Event listeners ----------------
seasonSelect.addEventListener('change', () => {
    season = seasonSelect.value;
    loadMatches(season);
});

function openMatchStats(matchId, clubId, clubName) {
    sessionStorage.setItem('selectedMatchId', matchId);
    sessionStorage.setItem('selectedClubId', clubId);
    sessionStorage.setItem('selectedClubName', clubName);
    window.location.href = 'match_stats.html';
}

function openGoalsStats(matchId) {
    sessionStorage.setItem('selectedMatchId', matchId);
    window.location.href = 'goals.html';
}