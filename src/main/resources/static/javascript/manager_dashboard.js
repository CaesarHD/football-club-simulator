const welcomeMsg = document.getElementById('manager-welcome');
const clubInfoTxt = document.getElementById('club-info');
const logoutBtn = document.getElementById('logout-btn');

// Navigation Buttons
const myClubBtn = document.getElementById('my-club-btn');
const transfersBtn = document.getElementById('transfers-btn');
const stadiumBtn = document.getElementById('stadium-btn');
const myPlayersBtn = document.getElementById('my-players-btn');

// Session Data
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');

// ---------- INIT ----------
async function initDashboard() {
    if (!currentUser.id || currentUser.role !== 'MANAGER') {
        alert("Access denied.");
        window.location.href = '../index.html';
        return;
    }

    try {
        // Fetch Manager Profile using the User ID
        const response = await fetch('/api/managers/manager_profile', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: currentUser.id }) // Sending UserInfo object structure
        });

        if (!response.ok) throw new Error('Failed to load manager profile');

        const manager = await response.json();

        // Save manager details to session for other pages
        currentUser.manager = manager;
        // Important: Save Club ID specifically for transfers/matches
        currentUser.profile = { club: manager.club, name: manager.name };
        sessionStorage.setItem('currentUser', JSON.stringify(currentUser));

        // Update UI
        welcomeMsg.textContent = `Welcome, ${manager.name}`;

        if (manager.club) {
            clubInfoTxt.textContent = `Managing: ${manager.club.name} | Budget: ${manager.club.budget}M`;
        } else {
            clubInfoTxt.textContent = "No club assigned.";
        }

    } catch (err) {
        console.error(err);
        welcomeMsg.textContent = "Welcome, Manager (Error loading profile)";
    }
}

// ---------- HANDLERS ----------

// 1. My Club -> Matches Page
myClubBtn.addEventListener('click', () => {
    if (currentUser.manager?.club) {
        // Reuse existing matches page logic
        sessionStorage.setItem('selectedClubId', currentUser.manager.club.id);
        sessionStorage.setItem('selectedClubName', currentUser.manager.club.name);
        window.location.href = `matches.html?clubId=${currentUser.manager.club.id}`;
    } else {
        alert("You do not have a club assigned yet.");
    }
});

// 2. Transfers -> New Manager Transfers Page
transfersBtn.addEventListener('click', () => {
    if (currentUser.manager?.club) {
        window.location.href = 'manager_transfer.html';
    } else {
        alert("You need a club to access transfers.");
    }
});

// 3. Stadium -> Stadium Page
stadiumBtn.addEventListener('click', () => {
    if (currentUser.manager?.club) {
        window.location.href = 'stadium.html';
    } else {
        alert("You need a club to access stadium management.");
    }
});

// Logout
logoutBtn.addEventListener('click', () => {
    sessionStorage.clear();
    window.location.href = '../index.html';
});

if (myPlayersBtn) {
    myPlayersBtn.addEventListener('click', () => {
        if (currentUser.manager?.club) {
            // Save the club ID so players.js knows which players to load (if your players.js supports filtering)
            // Or typically, you might redirect to a specific URL like: players.html?clubId=...

            // Assuming your players.html logic checks 'selectedClubId' or similar from session
            sessionStorage.setItem('selectedClubId', currentUser.manager.club.id);
            sessionStorage.setItem('selectedClubName', currentUser.manager.club.name);

            // Redirect
            window.location.href = `players.html?clubId=${currentUser.manager.club.id}`;
        } else {
            alert("You need a club to view players.");
        }
    });
}

// Run
initDashboard();