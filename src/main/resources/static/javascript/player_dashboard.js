const playerNameElement = document.getElementById('player-name');
const playerClubElement = document.getElementById('player-club');
const historyButton = document.getElementById('player-history-btn');
const currentClubButton = document.getElementById('current-club-btn');
const allClubsButton = document.getElementById('all-clubs-btn');

const params = new URLSearchParams(window.location.search);
const playerIdParam = params.get('playerId');
const playerNameParam = params.get('playerName');

if (playerIdParam) {
    sessionStorage.setItem('playerId', playerIdParam);
}

if (playerNameParam) {
    sessionStorage.setItem('playerName', playerNameParam);
}

let playerId = sessionStorage.getItem('playerId');
let playerName = sessionStorage.getItem('playerName');
let playerClubName = sessionStorage.getItem('playerClubName');

function updatePlayerCard() {
    if (playerName) {
        playerNameElement.textContent = `Welcome, ${playerName}`;
    } else {
        playerNameElement.textContent = 'Welcome, Player';
    }

    if (playerClubName) {
        playerClubElement.textContent = `Current club: ${playerClubName}`;
    } else {
        playerClubElement.textContent = 'Current club: Not available';
    }
}

function loadPlayerDetails() {
    if (playerId && playerName && playerClubName) {
        updatePlayerCard();
        return;
    }

    fetch('/api/players')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching players: ' + response.status);
            }
            return response.json();
        })
        .then(players => {
            let player = null;

            if (playerId) {
                player = players.find(item => String(item.id) === String(playerId));
            }

            if (!player && playerName) {
                player = players.find(item => item.name?.toLowerCase() === playerName.toLowerCase());
            }

            if (player) {
                playerId = playerId || player.id;
                playerName = playerName || player.name;
                playerClubName = player.club ? player.club.name : null;

                sessionStorage.setItem('playerId', playerId);
                if (playerName) {
                    sessionStorage.setItem('playerName', playerName);
                }
                if (playerClubName) {
                    sessionStorage.setItem('playerClubName', playerClubName);
                }
            }

            updatePlayerCard();
        })
        .catch(error => {
            console.error('Error loading player data:', error);
            updatePlayerCard();
        });
}

historyButton.addEventListener('click', () => {
    if (!playerId) {
        alert('We could not find your player profile yet.');
        return;
    }

    sessionStorage.setItem('selectedPlayerId', playerId);
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

loadPlayerDetails();
