const playerNameElement = document.getElementById('player-name');
const playerClubElement = document.getElementById('player-club');
const historyButton = document.getElementById('player-history-btn');
const currentClubButton = document.getElementById('current-club-btn');
const allClubsButton = document.getElementById('all-clubs-btn');

// ---------- HELPER FUNCTIONS (From your snippet) ----------
function setText(id, val) {
    const el = document.getElementById(id);
    // If value is null/undefined, show '-'. Otherwise show value.
    if (el) el.textContent = (val !== undefined && val !== null) ? val : '-';
}

// ---------- GET PLAYER INFO FROM SESSION ----------
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');
let playerId = currentUser.id;
let playerName = currentUser.username || currentUser.profile?.name;
let playerClubName = currentUser.profile?.club?.name || null;

// ---------- UPDATE PLAYER CARD & STATS ----------
function updatePlayerCard() {
    playerNameElement.textContent = playerName
        ? `Welcome, ${playerName}`
        : 'Welcome, Player';

    playerClubElement.textContent = playerClubName
        ? `Current club: ${playerClubName}`
        : 'Current club: Not available';

    if (currentUser.player) {
        renderPlayerStats(currentUser.player);
    }
}

// ---------- RENDER STATS (Updated Logic) ----------
function renderPlayerStats(player) {
    // 1. Determine where stats are stored.
    // If 'player.skills' exists, use it. Otherwise, assume stats are on 'player' root.
    const s = player.skills || player;

    // 2. Position Badge (ID in HTML is 'player-position')
    // We check 'position' on 's' first, then fallback to 'player.position'
    const position = s.position || player.position;
    setText('player-position', position);

    // 3. Common Stats (IDs in HTML are 'val-pace', etc.)
    setText('val-pace', s.pace);
    setText('val-shooting', s.shooting);
    setText('val-passing', s.passing);
    setText('val-dribbling', s.dribbling);
    setText('val-defending', s.defending);
    setText('val-physical', s.physical);
    setText('val-stamina', s.stamina);

    // 4. Goalkeeper Logic
    const boxReflexes = document.getElementById('box-reflexes');
    const boxDiving = document.getElementById('box-diving');

    if (position === 'GOALKEEPER') {
        // Show the boxes
        if(boxReflexes) boxReflexes.style.display = 'block';
        if(boxDiving) boxDiving.style.display = 'block';

        // Set values using the helper
        setText('val-reflexes', s.reflexes);
        setText('val-diving', s.diving);
    } else {
        // Hide the boxes
        if(boxReflexes) boxReflexes.style.display = 'none';
        if(boxDiving) boxDiving.style.display = 'none';
    }
}

// ---------- LOAD PLAYER DETAILS ----------
function loadPlayerDetails() {
    if (playerId && playerName && playerClubName && currentUser.player) {
        updatePlayerCard();
        return;
    }

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

                // Save updated info to session
                currentUser.player = player;
                currentUser.id = player.id;
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
    if(currentUser.profile?.club?.id) {
        window.location.href = `matches.html?clubId=${currentUser.profile.club.id}`;
    } else {
        window.location.href = `matches.html?clubName=${clubParam}`;
    }
});

allClubsButton.addEventListener('click', () => {
    window.location.href = 'player_transfer_market.html';
});

// ---------- INIT ----------
loadPlayerDetails();

const logoutBtn = document.getElementById('logout-btn');

if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
        // 1. Ștergem datele utilizatorului din sesiune
        sessionStorage.clear();
        // SAU, dacă vrei să ștergi doar userul curent:
        // sessionStorage.removeItem('currentUser');

        // 2. Redirecționăm către pagina de login
        // Folosim '../index.html' presupunând că dashboard-ul e într-un subfolder 'html'
        window.location.href = '../index.html';
    });
}