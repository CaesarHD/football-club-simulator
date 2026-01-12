const playerNameElement = document.getElementById('player-name');
const playerClubElement = document.getElementById('player-club');
const historyButton = document.getElementById('player-history-btn');
const currentClubButton = document.getElementById('current-club-btn');
const allClubsButton = document.getElementById('all-clubs-btn');

// ---------- GET PLAYER INFO FROM SESSION ----------
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
let playerId = currentUser.id;
let playerName = currentUser.username || currentUser.profile?.name;
playerClubName = currentUser.profile?.club?.name || null;


// ---------- UPDATE PLAYER CARD ----------
function updatePlayerCard() {
    playerNameElement.textContent = playerName
        ? `Welcome, ${playerName}`
        : 'Welcome, Player';

    playerClubElement.textContent = playerClubName
        ? `Current club: ${playerClubName}`
        : 'Current club: Not available';
}

// ---------- LOAD PLAYER DETAILS (OPTIONAL FETCH) ----------
function loadPlayerDetails() {
    if (playerId && playerName && playerClubName) {
        updatePlayerCard();
        return;
    }

    // Fallback: fetch all players (rarely needed if login already has profile)
    fetch('/api/players')
        .then(res => {
            if (!res.ok) throw new Error('Error fetching players: ' + res.status);
            return res.json();
        })
        .then(players => {
            let player = null;

            if (playerId) player = players.find(p => String(p.id) === String(playerId));
            if (!player && playerName) player = players.find(p => p.name?.toLowerCase() === playerName.toLowerCase());

            if (player) {
                playerId = playerId || player.id;
                playerName = playerName || player.name;
                playerClubName = player.club?.name || null;

                // Save back to session for future use
                currentUser.player = player;
                sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
            }

            updatePlayerCard();
        })
        .catch(err => {
            console.error('Error loading player data:', err);
            updatePlayerCard();
        });
}

// ---------- BUTTON HANDLERS ----------
historyButton.addEventListener('click', () => {
    if (!playerId) {
        alert('We could not find your player profile yet.');
        return;
    }

    currentUser.selectedPlayerId = playerId;
    sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
    window.location.href = 'players_history.html';
});

currentClubButton.addEventListener('click', () => {
    if (!playerClubName) {
        alert('Your current club is not available yet.');
        return;
    }

    const clubParam = encodeURIComponent(playerClubName);
    window.location.href = `matches.html?clubName=${clubParam}`;
});

allClubsButton.addEventListener('click', () => {
    window.location.href = 'clubs.html';
});

// ---------- INIT ----------
loadPlayerDetails();
