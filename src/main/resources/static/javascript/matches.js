const urlParams = new URLSearchParams(window.location.search);
const clubName = urlParams.get('clubName');
let season = urlParams.get('season');

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
                          <td><a href="javascript:void(0)" 
                                 onclick="openGoalsStats(${match.id})" > ${match.homeTeamNoGoals ?? '-'} - ${match.awayTeamNoGoals ?? '-'}</a></td>
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
    sessionStorage.removeItem('selectedMatchId');
    sessionStorage.removeItem('selectedClubId');
    sessionStorage.removeItem('selectedClubName');


    sessionStorage.setItem('selectedMatchId', matchId);
    sessionStorage.setItem('selectedClubId', clubId);
    sessionStorage.setItem('selectedClubName', clubName);
    window.location.href = 'match_stats.html';
}

function openGoalsStats(matchId) {
    sessionStorage.removeItem('selectedMatchId');

    sessionStorage.setItem('selectedMatchId', matchId);
    window.location.href = 'goals.html';
}