// matches-list.js

const urlParams = new URLSearchParams(window.location.search);
const clubName = urlParams.get('clubName');
let season = urlParams.get('season');

const currentUser = JSON.parse(sessionStorage.getItem('user') || '{}');
const userRole = currentUser.role || 'GUEST';

const seasonSelect = document.getElementById('season-select');
const clubTitle = document.getElementById('club-name');
const tableBody = document.getElementById('matches-table').querySelector('tbody');

function formatDate(isoString) {
    if (!isoString) return '-';
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(isoString).toLocaleString(undefined, options);
}

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

function loadMatches(season) {
    fetch(`/api/matches/club/${clubName}/${season}`)
        .then(res => {
            if (!res.ok) throw new Error(`Error fetching matches: ${res.status}`);
            return res.json();
        })
        .then(matches => renderMatches(matches))
        .catch(err => {
            console.error('Failed to load matches:', err);
            tableBody.innerHTML = `<tr><td colspan="5">Failed to load matches.</td></tr>`;
        });

    clubTitle.textContent = `Season ${season} matches for ${clubName}`;
    updateUrlQuery(season);
}

async function renderMatches(matches) {
    tableBody.innerHTML = '';
    if (!matches || matches.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">No matches found.</td></tr>`;
        return;
    }

    const now = new Date();

    for (const match of matches) {
        const matchDate = new Date(match.date);
        const isFuture = matchDate > now;

        const isClickable =
            userRole === 'PLAYER' ||
            userRole === 'COACH' ||
            userRole === 'MANAGER';

        let scoreCell = '';
        if (isFuture) {
            scoreCell = `<span>vs</span>`;
        } else {
            scoreCell = `<a href="javascript:void(0)" onclick="openGoalsStats(${match.id})">
                            ${match.homeTeamNoGoals ?? '-'} - ${match.awayTeamNoGoals ?? '-'}
                         </a>`;
        }

        // Helper to create club cell content (link only for players)
        const createClubCell = (club) => {
            if (!club?.name) return '-';

            if (!isClickable) {
                const safeName = club.name.replace(/'/g, "\\'");
                return `
                    <a href="javascript:void(0)" 
                       onclick="openMatchStats(${match.id}, ${club.id}, '${safeName}')">
                       ${club.name}
                    </a>
                `;
            }

            return club.name;
        };

        const homeClubContent = createClubCell(match.homeClub);
        const awayClubContent = createClubCell(match.awayClub);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>
                ${homeClubContent}
                <div class="goals-home" style="font-size: 0.8em; color: grey;"></div>
            </td>
            <td>${scoreCell}</td>
            <td>
                ${awayClubContent}
                <div class="goals-away" style="font-size: 0.8em; color: grey;"></div>
            </td>
            <td>${formatDate(match.date)}</td>
        `;
        tableBody.appendChild(row);

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
                        .map(g => `<span style="color: grey;">⚽</span> ${g.minute}' ${g.player?.name ?? 'Unknown'}`)
                        .join('<br>');
                }

                if (awayGoals.length) {
                    awayGoalsDiv.innerHTML = awayGoals
                        .sort((a, b) => a.minute - b.minute)
                        .map(g => `<span style="color: grey;">⚽</span> ${g.minute}' ${g.player?.name ?? 'Unknown'}`)
                        .join('<br>');
                }
            } catch (err) {
                console.error('Failed to load goals for match', match.id, err);
            }
        }
    }
}

function updateUrlQuery(season) {
    const newUrlParams = new URLSearchParams();
    newUrlParams.set('clubName', clubName);
    newUrlParams.set('season', season);
    const newUrl = `${window.location.pathname}?${newUrlParams.toString()}`;
    window.history.replaceState({}, '', newUrl);
}

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