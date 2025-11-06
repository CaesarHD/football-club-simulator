
// Read parameters from URL for club and season
const urlParams = new URLSearchParams(window.location.search);
const clubName = urlParams.get('clubName');
let season = urlParams.get('season');

// DOM elements
const seasonSelect = document.getElementById('season-select');
const clubTitle = document.getElementById('club-name');
const tableBody = document.getElementById('matches-table').querySelector('tbody');

// Helper: format date nicely
function formatDate(isoString) {
    if (!isoString) return '-';
    const options = {year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit'};
    return new Date(isoString).toLocaleString(undefined, options);
}

// Load available seasons from backend
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

// Fetch matches for the selected club and season
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

// Render matches table
function renderMatches(matches) {
    tableBody.innerHTML = '';
    if (!matches || matches.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">No matches found.</td></tr>`;
        return;
    }

    matches.forEach(match => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>
                <a href="javascript:void(0)" 
                   onclick="openMatchStats(${match.id}, ${match.homeClub?.id}, '${match.homeClub?.name}')">
                   ${match.homeClub?.name ?? '-'}
                </a>
            </td>
            <td>
                <a href="javascript:void(0)" 
                   onclick="openGoalsPage(${match.id})">
                   ${match.homeTeamScore ?? '-'} - ${match.awayTeamScore ?? '-'}
                </a>
            </td>
            <td>
                <a href="javascript:void(0)" 
                   onclick="openMatchStats(${match.id}, ${match.awayClub?.id}, '${match.awayClub?.name}')">
                   ${match.awayClub?.name ?? '-'}
                </a>
            </td>
            <td>${formatDate(match.date)}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Update URL to keep season & club in query
function updateUrlQuery(season) {
    const newUrlParams = new URLSearchParams();
    newUrlParams.set('clubName', clubName);
    newUrlParams.set('season', season);
    const newUrl = `${window.location.pathname}?${newUrlParams.toString()}`;
    window.history.replaceState({}, '', newUrl);
}

// Reload matches when user changes season
seasonSelect.addEventListener('change', () => {
    season = seasonSelect.value;
    loadMatches(season);
});

// Open match stats (keeps URL clean)
function openMatchStats(matchId, clubId, clubName) {
    sessionStorage.setItem('selectedMatchId', matchId);
    sessionStorage.setItem('selectedClubId', clubId);
    sessionStorage.setItem('selectedClubName', clubName);
    window.location.href = 'match_stats.html';
}

// Open goals page (no matchId in URL)
function openGoalsPage(matchId) {
    sessionStorage.setItem('selectedMatchId', matchId);
    window.location.href = '../html/goals.html';
}
